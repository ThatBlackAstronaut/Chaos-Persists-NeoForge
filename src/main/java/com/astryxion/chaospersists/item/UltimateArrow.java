package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Girlfriend;

import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChangeGameState;
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

        if (!this.inGround && this.getIsCritical()) {
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
    }

    @Override
    protected void onHit(RayTraceResult result) {
        if (result.entityHit == null) {
            super.onHit(result);
            return;
        }

        Entity hit = result.entityHit;

        if (ChaosPersists.ultimate_sword_pvp == 0) {
            if (hit instanceof EntityPlayer || hit instanceof Girlfriend || hit instanceof Boyfriend) {
                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                if (hit instanceof EntityLivingBase) {
                    ((EntityLivingBase) hit).heal(1.0F);
                }
                this.setDead();
                return;
            }
            if (hit instanceof EntityTameable && ((EntityTameable) hit).isTamed()) {
                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                ((EntityTameable) hit).heal(1.0F);
                this.setDead();
                return;
            }
        }

        float velocity = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        int damage = MathHelper.ceil(velocity * (float) ChaosPersists.UltimateBowDamage);

        if (this.getIsCritical()) {
            damage += this.rand.nextInt(damage / 2 + 2);
        }

        DamageSource source = this.shootingEntity == null
                ? DamageSource.causeArrowDamage(this, this)
                : DamageSource.causeArrowDamage(this, this.shootingEntity);

        if (this.isBurning()) {
            hit.setFire(5);
        }

        if (hit.attackEntityFrom(source, damage)) {
            if (hit instanceof EntityLiving) {
                EntityLiving living = (EntityLiving) hit;
                if (!this.world.isRemote) {
                    living.setArrowCountInEntity(living.getArrowCountInEntity() + 1);
                }
            }

            applyKnockback(hit);

            if (this.shootingEntity instanceof EntityPlayerMP && hit instanceof EntityPlayer && hit != this.shootingEntity) {
                ((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
            }

            this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            this.setDead();
        } else {
            this.motionX *= -0.10000000149D;
            this.motionY *= -0.10000000149D;
            this.motionZ *= -0.10000000149D;
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
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
