package com.astryxion.chaospersists.util;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/**
 * OreSpawn sea creatures skim the water surface (1.7 / 1.12). Modern {@code FloatGoal} uses
 * jump bursts that look like hopping when {@code SWIM_SPEED} is raised; this applies smooth
 * buoyancy instead.
 */
public final class SurfaceWaterFloat {
    private SurfaceWaterFloat() {}

    /** Call after {@code super.aiStep()} so travel/gravity for this tick are corrected. */
    public static void keepOnSurface(LivingEntity mob) {
        if (!mob.isInWater()) {
            return;
        }
        double depth = mob.getFluidHeight(FluidTags.WATER);
        if (depth <= 0.0) {
            return;
        }

        Vec3 motion = mob.getDeltaMovement();
        double y = motion.y;
        if (depth > mob.getFluidJumpThreshold()) {
            // Submerged: climb toward the surface smoothly (no JumpControl).
            y = Math.max(y, 0.08);
            if (y > 0.2) {
                y = 0.2;
            }
        } else {
            // At the surface: hold skim height — no sinking, no hopping out.
            if (y < 0.0) {
                y = 0.0;
            } else if (y > 0.03) {
                y = 0.03;
            }
        }
        mob.setDeltaMovement(motion.x, y, motion.z);
    }
}
