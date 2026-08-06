package com.astryxion.chaospersists.world.dimension.teleporter;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Safe surface placement for dimension travel.
 *
 * <p>Uses collision shapes (not only {@code blocksMotion}) so translucent crystal blocks like
 * Crystal Grass are never treated as stand-inside air. Feet Y is always the cell <em>above</em>
 * the top solid block.
 */
public final class OreSpawnSurfaceFinder {
    private OreSpawnSurfaceFinder() {}

    public static Vec3 findSafeSpawn(ServerLevel world, double entityX, double entityZ) {
        int originX = (int) Math.floor(entityX);
        int originZ = (int) Math.floor(entityZ);
        world.getChunk(originX >> 4, originZ >> 4);

        int posX = originX;
        int posZ = originZ;
        int posY = findBestFeetY(world, posX, posZ);

        for (int i = 0; posY < 0 && i < 500; i++) {
            int spread = 2 + i / 10;
            posX = originX + world.random.nextInt(2 * spread + 1) - spread;
            posZ = originZ + world.random.nextInt(2 * spread + 1) - spread;
            if (i > 100) {
                posX += ChaosPersists.ChaosRand.nextInt(2 * spread + 1) - spread;
                posZ += ChaosPersists.ChaosRand.nextInt(2 * spread + 1) - spread;
            }
            world.getChunk(posX >> 4, posZ >> 4);
            posY = findBestFeetY(world, posX, posZ);
        }

        if (posY < 0) {
            posX = originX;
            posZ = originZ;
            int hm = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, posX, posZ);
            posY = bumpOutOfSolids(world, posX, Math.max(hm, world.getMinBuildHeight() + 2), posZ);
        }

        return new Vec3(posX + 0.5D, posY, posZ + 0.5D);
    }

    /**
     * Feet Y = first free cell above the topmost standable ground (collision underfoot, empty
     * collision at feet and head).
     */
    private static int findBestFeetY(ServerLevel level, int posX, int posZ) {
        int heightmapY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, posX, posZ);
        int fromHeightmap = feetYFromCandidate(level, posX, heightmapY, posZ);
        if (fromHeightmap >= 0) {
            return fromHeightmap;
        }

        int maxY = level.getMaxBuildHeight() - 2;
        int minY = level.getMinBuildHeight() + 1;
        for (int groundY = maxY - 1; groundY >= minY; groundY--) {
            if (!isStandableGround(level.getBlockState(new BlockPos(posX, groundY, posZ)), level, new BlockPos(posX, groundY, posZ))) {
                continue;
            }
            int feetY = groundY + 1;
            if (isSafeFeetY(level, posX, feetY, posZ)) {
                return feetY;
            }
        }
        return -1;
    }

    /**
     * Heightmap points at a free cell above motion-blocking terrain, but Crystal decoration can
     * put Crystal Grass in that cell — bump up until feet/head are actually empty.
     */
    private static int feetYFromCandidate(ServerLevel level, int x, int candidateFeetY, int z) {
        int feetY = bumpOutOfSolids(level, x, candidateFeetY, z);
        if (isSafeFeetY(level, x, feetY, z)) {
            return feetY;
        }
        if (isSafeFeetY(level, x, feetY + 1, z)) {
            return feetY + 1;
        }
        return -1;
    }

    private static int bumpOutOfSolids(ServerLevel level, int x, int feetY, int z) {
        int max = level.getMaxBuildHeight() - 2;
        int y = Math.max(feetY, level.getMinBuildHeight() + 2);
        while (y < max) {
            BlockPos feetPos = new BlockPos(x, y, z);
            BlockPos headPos = new BlockPos(x, y + 1, z);
            if (isPassable(level.getBlockState(feetPos), level, feetPos)
                    && isPassable(level.getBlockState(headPos), level, headPos)) {
                return y;
            }
            y++;
        }
        return feetY;
    }

    private static boolean isSafeFeetY(ServerLevel level, int x, int feetY, int z) {
        if (feetY <= level.getMinBuildHeight() + 1 || feetY >= level.getMaxBuildHeight() - 1) {
            return false;
        }
        BlockPos groundPos = new BlockPos(x, feetY - 1, z);
        BlockPos feetPos = new BlockPos(x, feetY, z);
        BlockPos headPos = new BlockPos(x, feetY + 1, z);
        return isStandableGround(level.getBlockState(groundPos), level, groundPos)
                && isPassable(level.getBlockState(feetPos), level, feetPos)
                && isPassable(level.getBlockState(headPos), level, headPos);
    }

    private static boolean isStandableGround(BlockState state, ServerLevel level, BlockPos pos) {
        if (state.isAir() || !state.getFluidState().isEmpty()) {
            return false;
        }
        if (state.is(BlockTags.LEAVES) || state.is(Blocks.SNOW) || state.is(Blocks.POWDER_SNOW)) {
            return false;
        }
        if (isCrystalSurfaceBlock(state.getBlock())) {
            return true;
        }
        VoxelShape shape = state.getCollisionShape(level, pos);
        return !shape.isEmpty();
    }

    private static boolean isPassable(BlockState state, ServerLevel level, BlockPos pos) {
        if (!state.getFluidState().isEmpty()) {
            return false;
        }
        if (state.isAir()) {
            return true;
        }
        // Never stand inside crystal surface / stone — even if motion flags are odd.
        if (isCrystalSurfaceBlock(state.getBlock())) {
            return false;
        }
        if (state.is(Blocks.SNOW)) {
            return true;
        }
        return state.getCollisionShape(level, pos).isEmpty();
    }

    private static boolean isCrystalSurfaceBlock(Block block) {
        return (ChaosPersists.CrystalGrass != null && block == ChaosPersists.CrystalGrass)
                || (ChaosPersists.CrystalStone != null && block == ChaosPersists.CrystalStone);
    }
}
