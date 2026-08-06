package com.astryxion.chaospersists.world.dimension.teleporter;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

/**
 * Dimension teleporter that lands on a standable surface.
 *
 * <p>Forge applies {@link #getPortalInfo} during {@code repositionEntity}; without that override the
 * player keeps their old Y in the new dimension (inside stone / mid-air).
 */
public class SurfaceTeleporter implements ITeleporter {
    private final double targetX;
    private final double targetZ;

    public SurfaceTeleporter(double targetX, double targetZ) {
        this.targetX = targetX;
        this.targetZ = targetZ;
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(
            Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        int blockX = (int) Math.floor(this.targetX);
        int blockZ = (int) Math.floor(this.targetZ);
        ChunkPos chunkPos = new ChunkPos(blockX >> 4, blockZ >> 4);
        destWorld.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 1, entity.getId());
        destWorld.getChunk(chunkPos.x, chunkPos.z);

        Vec3 spawn = OreSpawnSurfaceFinder.findSafeSpawn(destWorld, this.targetX, this.targetZ);
        return new PortalInfo(spawn, Vec3.ZERO, entity.getYRot(), entity.getXRot());
    }

    @Override
    public Entity placeEntity(
            Entity entity,
            ServerLevel currentWorld,
            ServerLevel destWorld,
            float yaw,
            Function<Boolean, Entity> repositionEntity) {
        Entity moved = repositionEntity.apply(false);
        if (moved == null) {
            return null;
        }

        // Re-assert surface in case portal info was skipped; keep client in sync.
        Vec3 spawn = OreSpawnSurfaceFinder.findSafeSpawn(destWorld, this.targetX, this.targetZ);
        moved.moveTo(spawn.x, spawn.y, spawn.z, yaw, moved.getXRot());
        moved.setDeltaMovement(0.0D, 0.0D, 0.0D);
        moved.fallDistance = 0.0F;
        moved.setRemainingFireTicks(0);
        if (moved instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.teleport(spawn.x, spawn.y, spawn.z, yaw, moved.getXRot());
        }
        return moved;
    }
}
