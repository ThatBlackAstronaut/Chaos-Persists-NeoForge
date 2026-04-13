package com.astryxion.chaospersists.world.dimension.teleporter;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Teleporter for /danger command. Danger dimension is superflat with ground at Y=8.
 */
public class TeleporterDanger extends Teleporter {
    private final double targetX;
    private final double targetZ;
    private static final double TARGET_Y = 8.0;

    public TeleporterDanger(WorldServer worldIn, double targetX, double targetZ) {
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
