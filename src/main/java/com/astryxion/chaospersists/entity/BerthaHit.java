package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BerthaHit extends ThrowableProjectile {
    private int hit_type = 0;

    public BerthaHit(EntityType<? extends BerthaHit> type, Level level) {
        super(type, level);
    }

    public BerthaHit(EntityType<? extends BerthaHit> type, Level level, int par2) {
        super(type, level);
    }

    public BerthaHit(EntityType<? extends BerthaHit> type, LivingEntity shooter, Level level) {
        super(type, shooter, level);
        this.setPos(
                shooter.getX(),
                shooter.getY() + shooter.getEyeHeight(),
                shooter.getZ());
        this.setYRot(shooter.getYRot());
        this.setXRot(shooter.getXRot());
        this.setPos(
                this.getX() - Mth.cos(this.getYRot() * Mth.DEG_TO_RAD) * 0.16f,
                this.getY() - 0.1,
                this.getZ() - Mth.sin(this.getYRot() * Mth.DEG_TO_RAD) * 0.16f);
        float f = 0.4f;
        float yawRad = this.getYRot() * Mth.DEG_TO_RAD;
        float pitchRad = this.getXRot() * Mth.DEG_TO_RAD;
        double mx = -Mth.sin(yawRad) * Mth.cos(pitchRad) * f;
        double mz = Mth.cos(yawRad) * Mth.cos(pitchRad) * f;
        double my = -Mth.sin(pitchRad) * f;
        this.shoot(mx, my, mz, 0.4f, 0.1f);
    }

    public BerthaHit(EntityType<? extends BerthaHit> type, LivingEntity shooter, Level level, int par3) {
        super(type, shooter, level);
    }

    public BerthaHit(EntityType<? extends BerthaHit> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
    }

    public void setHitType(int i) {
        this.hit_type = i;
    }

    @Override
    protected void onHit(HitResult result) {
        if (this.isRemoved()) {
            return;
        }
        if (result.getType() == HitResult.Type.ENTITY && this.getOwner() != null) {
            EntityHitResult entityHit = (EntityHitResult) result;
            Entity e = entityHit.getEntity();
            Entity owner = this.getOwner();
            if (ChaosPersists.big_bertha_pvp == 0 && e instanceof Player
                    || isGirlfriendOrBoyfriend(e)) {
                this.discard();
                return;
            }
            if (ChaosPersists.big_bertha_pvp == 0 && e instanceof TamableAnimal t && t.isTame()) {
                this.discard();
                return;
            }
            if (this.hit_type == 0
                    && this.distanceToSqr(owner) < 81.0
                    && e != owner
                    && owner instanceof Player player) {
                e.hurt(this.damageSources().playerAttack(player), (float) ChaosPersists.bertha_stats.damage);
                e.setRemainingFireTicks(200);
                double ks = 2.25;
                double inair = 0.35;
                float f3 = (float) Math.atan2(e.getZ() - owner.getZ(), e.getX() - owner.getX());
                if (!e.isAlive()) {
                    inair *= 2.0;
                }
                e.push(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.hit_type == 2
                    && this.distanceToSqr(owner) < 101.0
                    && e != owner
                    && owner instanceof Player player) {
                e.hurt(this.damageSources().playerAttack(player), (float) ChaosPersists.royal_stats.damage);
                double ks = 1.5;
                double inair = 0.25;
                float f3 = (float) Math.atan2(e.getZ() - owner.getZ(), e.getX() - owner.getX());
                if (!e.isAlive()) {
                    inair *= 2.0;
                }
                e.push(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.hit_type == 3
                    && this.distanceToSqr(owner) < 64.0
                    && e != owner
                    && owner instanceof Player player) {
                e.hurt(this.damageSources().playerAttack(player), (float) ChaosPersists.hammy_stats.damage);
                double ks = 1.25;
                double inair = 0.65;
                float f3 = (float) Math.atan2(e.getZ() - owner.getZ(), e.getX() - owner.getX());
                if (!e.isAlive()) {
                    inair *= 2.0;
                }
                e.push(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (!this.level().isClientSide
                        && this.hit_type == 3
                        && this.distanceToSqr(owner) < 64.0) {
                    this.level()
                            .explode(
                                    null,
                                    this.getX(),
                                    this.getY(),
                                    this.getZ(),
                                    1.5f,
                                    true,
                                    this.level()
                                                    .getGameRules()
                                                    .getBoolean(GameRules.RULE_MOBGRIEFING)
                                            ? ExplosionInteraction.MOB
                                            : ExplosionInteraction.NONE);
                }
            }
        } else if (!this.level().isClientSide
                && this.hit_type == 3
                && this.getOwner() != null
                && this.distanceToSqr(this.getOwner()) < 64.0) {
            this.level()
                    .explode(
                            null,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            2.1f,
                            true,
                            this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
                                    ? ExplosionInteraction.MOB
                                    : ExplosionInteraction.NONE);
        }
        this.discard();
    }

    private static boolean isGirlfriendOrBoyfriend(Entity e) {
        String n = e.getClass().getSimpleName();
        return "Girlfriend".equals(n) || "Boyfriend".equals(n);
    }

    @Override
    protected void defineSynchedData() {}
}
