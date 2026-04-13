package com.astryxion.chaospersists.world.dimension.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Minimal teleporter for /mining command. Places the entity at (targetX, 120, targetZ) in the target dimension.
 */
public class TeleporterMining extends Teleporter {
    private final double targetX;
    private final double targetZ;
    private static final double TARGET_Y = 120.0;

    public TeleporterMining(WorldServer worldIn, double targetX, double targetZ) {
        super(worldIn);
        this.targetX = targetX;
        this.targetZ = targetZ;
    }

    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        entityIn.setLocationAndAngles(this.targetX, TARGET_Y, this.targetZ, rotationYaw, entityIn.rotationPitch);
        entityIn.motionX = 0.0;
        entityIn.motionY = 0.0;
        entityIn.motionZ = 0.0;
    }
}
