package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Cephadrome;
import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.core.ChaosPersists;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class IrukandjiArrow extends EntityArrow {

    private static final DataParameter<Integer> CRIT =
            EntityDataManager.createKey(IrukandjiArrow.class, DataSerializers.VARINT);

    private int knockbackStrength;
    private int customTicksInGround = 0; // 🔥 replacement for private ticksInGround

    // Basic constructors
    public IrukandjiArrow(World world) {
        super(world);
    }

    public IrukandjiArrow(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    // 🔥 Player constructor (proper velocity)
    public IrukandjiArrow(World world, EntityPlayer player, float velocity) {
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

    // 🔥 Mob constructor (restored compatibility)
    public IrukandjiArrow(World world,
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
        super.entityInit(); // REQUIRED
        this.getDataManager().register(CRIT, 0);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ChaosPersists.MyIrukandjiArrow);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        // Reset custom counter if not in ground
        if (!this.inGround) {
            customTicksInGround = 0;
        }

        // Drop arrow after 50 ticks stuck in ground
        if (this.inGround && !this.world.isRemote) {
            customTicksInGround++;
            if (customTicksInGround >= 50) {
                this.entityDropItem(new ItemStack(ChaosPersists.MyIrukandjiArrow), 0.0F);
                this.setDead();
            }
        }

        // Critical particles
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

        if (result.entityHit != null) {

            EntityLivingBase target = (EntityLivingBase) result.entityHit;

            float damage = 100.0F; // Chaos-tier damage

            // PvP protection logic
            if (ChaosPersists.ultimate_sword_pvp == 0) {

                if (target instanceof EntityPlayer
                        || target instanceof Girlfriend
                        || target instanceof Boyfriend) {
                    this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.0F);
                    this.setDead();
                    return;
                }

                if (target instanceof EntityTameable) {
                    EntityTameable tame = (EntityTameable) target;
                    if (tame.isTamed()) {
                        this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.0F);
                        this.setDead();
                        return;
                    }
                }
            }

            if (this.getIsCritical()) {
                damage *= 1.5F;
            }

            DamageSource source = this.shootingEntity == null
                    ? DamageSource.causeArrowDamage(this, this)
                    : DamageSource.causeArrowDamage(this, this.shootingEntity);

            if (this.isBurning()) {
                target.setFire(5);
            }

            if (target.attackEntityFrom(source, damage)) {

                if (this.knockbackStrength > 0) {
                    float f = MathHelper.sqrt(
                            this.motionX * this.motionX +
                            this.motionZ * this.motionZ
                    );

                    if (f > 0.0F) {
                        target.addVelocity(
                                this.motionX * this.knockbackStrength * 0.6D / f,
                                0.1D,
                                this.motionZ * this.knockbackStrength * 0.6D / f
                        );
                    }
                }

                if (this.shootingEntity instanceof EntityPlayerMP
                        && target instanceof EntityPlayer) {
                    ((EntityPlayerMP) this.shootingEntity)
                            .connection
                            .sendPacket(new SPacketChangeGameState(6, 0.0F));
                }

                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.0F);
                this.setDead();
            }
        }
    }

    public void setKnockbackStrength(int strength) {
        this.knockbackStrength = strength;
    }

    @Override
    public double getDamage() {
        return 100.0;
    }
}
