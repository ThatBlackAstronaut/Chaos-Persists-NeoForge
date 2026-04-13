/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.MyUtils
 *  com.astryxion.chaospersists.ThunderBolt
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.effect.EntityLightningBolt
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.GameRules
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.util.MyUtils;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ThunderBolt
extends EntityThrowable {
    public ThunderBolt(World par1World) {
        super(par1World);
    }

    public ThunderBolt(World par1World, EntityLivingBase par3EntityPlayer) {
        super(par1World, par3EntityPlayer);
    }

    public ThunderBolt(World par1World, EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
    }

    public ThunderBolt(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    protected void onImpact(RayTraceResult par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            float var2 = 40.0f;
            if (MyUtils.isRoyalty((Entity)par1MovingObjectPosition.entityHit)) {
                this.setDead();
                return;
            }
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), var2 / 2.0f);
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.getThrower()), var2 / 2.0f);
            par1MovingObjectPosition.entityHit.setFire(1);
        }
        int mx = 20;
        for (int var3 = 0; var3 < mx; ++var3) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posY + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posZ + (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, this.posX + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posY + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), this.posZ + (double)this.rand.nextFloat() - (double)this.rand.nextFloat(), 0.0, 0.0, 0.0);
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, this.world.rand.nextGaussian(), this.world.rand.nextGaussian(), this.world.rand.nextGaussian());
        }
        this.playSound(net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, 0.5f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5f);
        if (!this.world.isRemote) {
            this.world.createExplosion((Entity)this, this.posX, this.posY, this.posZ, 3.0f, this.world.getGameRules().getBoolean("mobGriefing"));
        }
        this.world.addWeatherEffect((Entity)new net.minecraft.entity.effect.EntityLightningBolt(this.world, this.posX, this.posY + 1.0, this.posZ, false));
        this.setDead();
    }

    public void onUpdate() {
        super.onUpdate();
        int mx = 4;
        for (int i = 0; i < mx; ++i) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, this.world.rand.nextGaussian() / 10.0, this.world.rand.nextGaussian() / 10.0, this.world.rand.nextGaussian() / 10.0);
        }
    }
}

