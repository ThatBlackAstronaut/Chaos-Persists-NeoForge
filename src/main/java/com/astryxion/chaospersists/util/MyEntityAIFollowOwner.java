package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.Girlfriend;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
public class MyEntityAIFollowOwner extends Goal {
    private final TamableAnimal thePet;
    private LivingEntity theOwner;
    private final Level theWorld;
    private final float field_75336_f;
    private final PathNavigation petPathfinder;
    private int field_75343_h;
    private final float maxDist;
    private final float minDist;

    public MyEntityAIFollowOwner(TamableAnimal par1EntityTameable, float par2, float par3, float par4) {
        this.thePet = par1EntityTameable;
        this.theWorld = par1EntityTameable.level();
        this.field_75336_f = par2;
        this.petPathfinder = par1EntityTameable.getNavigation();
        this.minDist = par4;
        this.maxDist = par3;
        this.setFlags(java.util.EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity var1 = this.thePet.getOwner();
        if (var1 == null) {
            return false;
        }
        this.theOwner = var1;
        if (this.thePet.isInSittingPose()) {
            return false;
        }
        if (Girlfriend.class.isInstance(this.thePet) && ChaosPersists.valentines_day != 0) {
            return false;
        }
        if (!(this.thePet.getY() >= 60.0 && MyUtils.isDay(this.theWorld))
                && this.thePet.distanceToSqr(this.theOwner) <= (double) (this.maxDist / 2.0f * (this.maxDist / 2.0f))) {
            return true;
        }
        if (this.thePet.distanceToSqr(this.theOwner) < (double) (this.maxDist * this.maxDist)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.thePet.isInSittingPose()) {
            return false;
        }
        if (this.petPathfinder.isDone()) {
            return false;
        }
        LivingEntity var1 = this.thePet.getOwner();
        if (var1 != null
                && (int) this.thePet.getZ() == (int) var1.getZ()
                && (int) this.thePet.getX() == (int) var1.getX()
                && (int) this.thePet.getY() < (int) var1.getY() + 2
                && (int) this.thePet.getY() > (int) var1.getY() - 2) {
            return false;
        }
        return this.thePet.distanceToSqr(this.theOwner) > (double) (this.minDist * this.minDist);
    }

    @Override
    public void start() {
        this.field_75343_h = 0;
    }

    @Override
    public void stop() {
        this.theOwner = null;
        this.petPathfinder.stop();
    }

    @Override
    public void tick() {
        this.thePet.getLookControl().setLookAt(this.theOwner, 10.0f, (float) this.thePet.getMaxHeadXRot());
        if (!this.thePet.isInSittingPose() && --this.field_75343_h <= 0) {
            this.field_75343_h = 10;
            if (!this.petPathfinder.moveTo(this.theOwner, (double) this.field_75336_f)
                    && this.thePet.distanceToSqr(this.theOwner) >= 144.0) {
                int var1 = Mth.floor(this.theOwner.getX()) - 2;
                int var2 = Mth.floor(this.theOwner.getZ()) - 2;
                int var3 = Mth.floor(this.theOwner.getBoundingBox().minY);
                for (int var4 = 0; var4 <= 4; ++var4) {
                    for (int var5 = 0; var5 <= 4; ++var5) {
                        BlockPos bp = new BlockPos(var1 + var4, var3, var2 + var5);
                        BlockPos bpDown = new BlockPos(var1 + var4, var3 - 1, var2 + var5);
                        if (var4 >= 1 && var5 >= 1 && var4 <= 3 && var5 <= 3) {
                            continue;
                        }
                        BlockState down = this.theWorld.getBlockState(bpDown);
                        BlockState here = this.theWorld.getBlockState(bp);
                        BlockPos abovePos = new BlockPos(var1 + var4, var3 + 1, var2 + var5);
                        BlockState above = this.theWorld.getBlockState(abovePos);
                        if (!down.isFaceSturdy(this.theWorld, bpDown, net.minecraft.core.Direction.UP)
                                || !here.getCollisionShape(this.theWorld, bp).isEmpty()
                                || !above.getCollisionShape(this.theWorld, abovePos).isEmpty()) {
                            continue;
                        }
                        this.thePet.moveTo(
                                (double) ((float) (var1 + var4) + 0.5f),
                                (double) var3,
                                (double) ((float) (var2 + var5) + 0.5f),
                                this.thePet.getYRot(),
                                this.thePet.getXRot());
                        this.petPathfinder.stop();
                        return;
                    }
                }
            }
        }
    }
}
