package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;

import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UltimateArrow extends EntityArrow {

    private static final DataParameter<Byte> CRIT =
            EntityDataManager.createKey(UltimateArrow.class, DataSerializers.BYTE);

    private int knockbackStrength;

    // Basic constructors
    public UltimateArrow(World world) {
        super(world);
    }

    public UltimateArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    // 🔥 Player instant shot constructor
    public UltimateArrow(World world, EntityPlayer player, float velocity) {
        super(world, player);

        this.shoot(
                player,
                player.rotationPitch,
                player.rotationYaw,
                0.0F,
                velocity,
                1.0F
        );
    }

    // 🔥 Restored mob constructor (fixes compile errors)
    public UltimateArrow(World world,
                         EntityLiving shooter,
                         EntityLivingBase target,
                         float velocity,
                         float inaccuracy) {
        super(world, shooter);

        this.shoot(
                shooter,
                shooter.rotationPitch,
                shooter.rotationYaw,
                0.0F,
                velocity,
                inaccuracy
        );
    }

    @Override
    protected void entityInit() {
        super.entityInit(); // REQUIRED to prevent DataManager crash
        this.getDataManager().register(CRIT, (byte) 0);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(Items.ARROW);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.inGround) {

            if (this.getIsCritical()) {
                for (int i = 0; i < 4; ++i) {
                    this.world.spawnParticle(
                            EnumParticleTypes.CRIT,
                            this.posX + this.motionX * i / 4.0,
                            this.posY + this.motionY * i / 4.0,
                            this.posZ + this.motionZ * i / 4.0,
                            -this.motionX,
                            -this.motionY + 0.2,
                            -this.motionZ
                    );
                }
            }

            this.motionY -= 0.05F;
            this.motionX *= 0.99F;
            this.motionY *= 0.99F;
            this.motionZ *= 0.99F;

            setPosition(
                    this.posX + this.motionX,
                    this.posY + this.motionY,
                    this.posZ + this.motionZ
            );

            this.resetPositionToBB();
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {

        if (result.entityHit != null) {

            Entity hit = result.entityHit;

            float damage = (float) ChaosPersists.UltimateBowDamage;

            if (this.getIsCritical()) {
                damage *= 1.5F;
            }

            DamageSource source = this.shootingEntity == null
                    ? DamageSource.causeArrowDamage(this, this)
                    : DamageSource.causeArrowDamage(this, this.shootingEntity);

            if (hit instanceof EntityLivingBase) {
                EntityLivingBase target = (EntityLivingBase) hit;
                if (this.isBurning()) {
                    target.setFire(5);
                }
                target.attackEntityFrom(source, damage);
            } else {
                hit.attackEntityFrom(source, damage);
            }
            applyKnockback(hit);

            this.playSound(net.minecraft.init.SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.0F);
            this.setDead();
        }
    }

    private void applyKnockback(Entity target) {
        if (this.knockbackStrength > 0) {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            if (f > 0.0F) {
                target.addVelocity(
                        this.motionX * this.knockbackStrength * 0.6D / f,
                        0.1D,
                        this.motionZ * this.knockbackStrength * 0.6D / f
                );
            }
        }
    }

    public void setKnockbackStrength(int strength) {
        this.knockbackStrength = strength;
    }

    @Override
    public double getDamage() {
        return ChaosPersists.UltimateBowDamage;
    }
}
