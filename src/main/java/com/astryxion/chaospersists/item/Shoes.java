/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.Girlfriend
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.Shoes
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Shoes
extends EntityThrowable {
    private static final DataParameter<Integer> SHOE_ID = EntityDataManager.createKey(Shoes.class, DataSerializers.VARINT);
    public int ShoeId = 0;
    private float my_rotation = 0.0f;

    public Shoes(World par1World) {
        super(par1World);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.getDataManager().set(SHOE_ID, this.ShoeId);
    }

    public Shoes(World par1World, int par2) {
        super(par1World);
        this.ShoeId = par2;
        this.getDataManager().set(SHOE_ID, this.ShoeId);
    }

    public Shoes(World par1World, EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.getDataManager().set(SHOE_ID, this.ShoeId);
    }

    public Shoes(World par1World, EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
        this.ShoeId = par3;
        this.getDataManager().set(SHOE_ID, this.ShoeId);
    }

    public Shoes(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
        this.ShoeId = this.rand.nextInt(4) + 2;
        this.getDataManager().set(SHOE_ID, this.ShoeId);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SHOE_ID, 0);
    }

    public int getShoeId() {
        return this.getDataManager().get(SHOE_ID).intValue();
    }

    /**
     * Same trajectory as {@link com.astryxion.chaospersists.entity.Boyfriend#attackEntityWithRangedAttack} /
     * {@link com.astryxion.chaospersists.entity.Girlfriend#attackEntityWithRangedAttack} (1.7.10 behavior).
     */
    public static void shootTowardTarget(EntityLivingBase thrower, EntityLivingBase target, int shoeId) {
        World world = thrower.world;
        if (world.isRemote || target == null || target == thrower) {
            return;
        }
        Shoes shoes = new Shoes(world, thrower, shoeId);
        double dx = target.posX - thrower.posX;
        double dy = target.posY + (double) target.getEyeHeight() - 1.1 - shoes.posY;
        double dz = target.posZ - thrower.posZ;
        float lift = MathHelper.sqrt(dx * dx + dz * dz) * 0.2f;
        shoes.shoot(dx, dy + (double) lift, dz, 1.8f, 4.0f);
        world.spawnEntity(shoes);
        world.playSound(null, thrower.posX, thrower.posY, thrower.posZ, SoundEvents.ENTITY_SKELETON_SHOOT,
                SoundCategory.NEUTRAL, 0.75f, 1.0f / (world.rand.nextFloat() * 0.4f + 0.8f));
    }

    protected void onImpact(RayTraceResult par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            float var2 = 2.0f;
            if (this.getShoeId() == 6) {
                var2 = 6.0f;
            }
            if (par1MovingObjectPosition.entityHit instanceof EntityCreeper) {
                var2 += 4.0f;
            }
            if (par1MovingObjectPosition.entityHit instanceof Girlfriend) {
                var2 = 1.0f;
            }
            if (par1MovingObjectPosition.entityHit instanceof Boyfriend) {
                var2 = 1.0f;
            }
            if (par1MovingObjectPosition.entityHit instanceof EntityPlayer) {
                var2 = 0.0f;
            }
            if (ChaosPersists.valentines_day != 0) {
                var2 = 10.0f;
            }
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), var2);
        }
        for (int var3 = 0; var3 < 4; ++var3) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.CLOUD, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
        }
        if (!this.world.isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.my_rotation += 20.0f;
        while (this.my_rotation > 360.0f) {
            this.my_rotation -= 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
    }
}

