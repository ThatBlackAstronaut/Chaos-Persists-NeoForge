package com.astryxion.chaospersists.world.ore;

import com.astryxion.chaospersists.core.ChaosPersists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreBasicStone extends Block {

    public OreBasicStone(float hardness, float resistance) {
        super(Material.ROCK);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setTickRandomly(false);
    }

    // ===== BLOCK BREAK SPAWNING =====
    //
    // Forge 1.12.2 removes blocks via Block#removedByPlayer → World#setBlockState; Chunk then
    // calls Block#breakBlock on the old state. Player breaks do not reliably call
    // onBlockDestroyedByPlayer, so spawns must run in breakBlock.

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote && !world.restoringBlockSnapshots) {
            if (this == ChaosPersists.CrystalRat) {
                int num = 1 + world.rand.nextInt(10);
                for (int i = 0; i < num; ++i) {
                    spawnCreature(world, 0, "Rat",
                            pos.getX() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
                            pos.getY() + 0.01,
                            pos.getZ() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
                }
            }
            if (this == ChaosPersists.CrystalFairy) {
                int num = 1 + world.rand.nextInt(6);
                for (int i = 0; i < num; ++i) {
                    spawnCreature(world, 0, "Fairy",
                            pos.getX() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
                            pos.getY() + 0.01,
                            pos.getZ() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
                }
            }
            if (this == ChaosPersists.RedAntTroll) {
                int num = 15 + world.rand.nextInt(6);
                for (int i = 0; i < num; ++i) {
                    spawnCreature(world, 0, "Red Ant",
                            pos.getX() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
                            pos.getY() + 0.01,
                            pos.getZ() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
                }
            }
            if (this == ChaosPersists.TermiteTroll) {
                int num = 15 + world.rand.nextInt(6);
                for (int i = 0; i < num; ++i) {
                    spawnCreature(world, 0, "Termite",
                            pos.getX() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2,
                            pos.getY() + 0.01,
                            pos.getZ() + 0.5 + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2);
                }
            }
        }
        super.breakBlock(world, pos, state);
    }

    /**
     * Crystal variants use non-full cubes for rendering; default {@code canCreatureSpawn} would reject all
     * natural spawns on those blocks. Behave like vanilla stone for placement checks.
     */
    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos,
                                    EntityLiving.SpawnPlacementType type) {
        if (isCrystalBlock()) {
            return Blocks.STONE.canCreatureSpawn(Blocks.STONE.getDefaultState(), world, pos, type);
        }
        return super.canCreatureSpawn(state, world, pos, type);
    }

    // ===== RENDERING =====

    private boolean isCrystalBlock() {
        return this == ChaosPersists.CrystalStone
                || this == ChaosPersists.CrystalRat
                || this == ChaosPersists.CrystalFairy;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !isCrystalBlock();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return !isCrystalBlock();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return isCrystalBlock() ? BlockRenderLayer.CUTOUT : BlockRenderLayer.SOLID;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        if (isCrystalBlock()) {
            IBlockState adjacent = world.getBlockState(pos.offset(side));
            if (adjacent.getBlock() == this) {
                return false;
            }
        }
        return super.shouldSideBeRendered(state, world, pos, side);
    }

    // ===== SPAWN CREATURE =====

    /**
     * 1.7.10 used {@link EntityList#createEntityByName(String, World)} with legacy names like
     * {@code "Red Ant"}. In 1.12.2 entities are keyed by {@link ResourceLocation}
     * ({@code chaospersists:red_ant}, {@code chaospersists:termite}, …).
     */
    private static ResourceLocation legacySpawnNameToRegistry(String legacyName) {
        if (legacyName == null) {
            return null;
        }
        switch (legacyName) {
            case "Rat":
                return new ResourceLocation("chaospersists", "rat");
            case "Fairy":
                return new ResourceLocation("chaospersists", "fairy");
            case "Red Ant":
                return new ResourceLocation("chaospersists", "red_ant");
            case "Termite":
                return new ResourceLocation("chaospersists", "termite");
            default:
                return new ResourceLocation(
                        "chaospersists",
                        legacyName.toLowerCase(java.util.Locale.ROOT).replace(' ', '_'));
        }
    }

    public static Entity spawnCreature(World world, int id, String name,
                                        double x, double y, double z) {

        Entity entity = name == null
                ? EntityList.createEntityByID(id, world)
                : EntityList.createEntityByIDFromName(legacySpawnNameToRegistry(name), world);

        if (entity != null) {
            entity.setLocationAndAngles(x, y, z,
                    world.rand.nextFloat() * 360.0f, 0.0f);
            world.spawnEntity(entity);

            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).playLivingSound();
            }
        }

        return entity;
    }
}
