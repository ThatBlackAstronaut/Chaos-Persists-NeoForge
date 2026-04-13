/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.BerthaHit
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.Girlfriend
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.WeaponStats
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.GameRules
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.core.ChaosPersists;

import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.util.WeaponStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BerthaHit
extends EntityThrowable {
    private int hit_type = 0;

    public BerthaHit(World par1World) {
        super(par1World);
    }

    public BerthaHit(World par1World, int par2) {
        super(par1World);
    }

    public BerthaHit(World par1World, EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
        this.setSize(0.33f, 0.33f);
        this.setLocationAndAngles(par2EntityLiving.posX, par2EntityLiving.posY + (double)par2EntityLiving.getEyeHeight(), par2EntityLiving.posZ, par2EntityLiving.rotationYaw, par2EntityLiving.rotationPitch);
        this.posX -= (double)(MathHelper.cos((float)(this.rotationYaw / 180.0f * 3.1415927f)) * 0.16f);
        this.posY -= 0.1;
        this.posZ -= (double)(MathHelper.sin((float)(this.rotationYaw / 180.0f * 3.1415927f)) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        float f = 0.4f;
        this.motionX = (- MathHelper.sin((float)(this.rotationYaw / 180.0f * 3.1415927f))) * MathHelper.cos((float)(this.rotationPitch / 180.0f * 3.1415927f)) * f;
        this.motionZ = MathHelper.cos((float)(this.rotationYaw / 180.0f * 3.1415927f)) * MathHelper.cos((float)(this.rotationPitch / 180.0f * 3.1415927f)) * f;
        this.motionY = (- MathHelper.sin((float)(this.rotationPitch / 180.0f * 3.1415927f))) * f;
        this.shoot(this.motionX, this.motionY, this.motionZ, 0.4f, 0.1f);
    }

    public BerthaHit(World par1World, EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
    }

    public BerthaHit(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    public void setHitType(int i) {
        this.hit_type = i;
    }

    protected void onImpact(RayTraceResult par1MovingObjectPosition) {
        if (this.isDead) {
            return;
        }
        if (par1MovingObjectPosition.entityHit != null && this.getThrower() != null) {
            double inair;
            EntityTameable t;
            float f3;
            Entity e = par1MovingObjectPosition.entityHit;
            if (ChaosPersists.big_bertha_pvp == 0 && e instanceof EntityPlayer || e instanceof Girlfriend || e instanceof Boyfriend) {
                this.setDead();
                return;
            }
            if (ChaosPersists.big_bertha_pvp == 0 && e instanceof EntityTameable && (t = (EntityTameable)e).isTamed()) {
                this.setDead();
                return;
            }
            if (this.hit_type == 0 && this.getDistanceSq((Entity)this.getThrower()) < 81.0 && e != this.getThrower()) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)((EntityPlayer)this.getThrower())), (float)ChaosPersists.bertha_stats.damage);
                e.setFire(10);
                double ks = 2.25;
                inair = 0.35;
                f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.hit_type == 2 && this.getDistanceSq((Entity)this.getThrower()) < 101.0 && e != this.getThrower()) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)((EntityPlayer)this.getThrower())), (float)ChaosPersists.royal_stats.damage);
                double ks = 1.5;
                inair = 0.25;
                f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            if (this.hit_type == 3 && this.getDistanceSq((Entity)this.getThrower()) < 64.0 && e != this.getThrower()) {
                e.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)((EntityPlayer)this.getThrower())), (float)ChaosPersists.hammy_stats.damage);
                double ks = 1.25;
                inair = 0.65;
                f3 = (float)Math.atan2(e.posZ - this.getThrower().posZ, e.posX - this.getThrower().posX);
                if (e.isDead) {
                    inair *= 2.0;
                }
                e.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
                if (!this.world.isRemote && this.hit_type == 3 && this.getDistanceSq((Entity)this.getThrower()) < 64.0) {
                    this.world.newExplosion((Entity)null, this.posX, this.posY, this.posZ, 1.5f, true, this.world.getGameRules().getBoolean("mobGriefing"));
                }
            }
        } else if (!this.world.isRemote && this.hit_type == 3 && this.getDistanceSq((Entity)this.getThrower()) < 64.0) {
            this.world.newExplosion((Entity)null, this.posX, this.posY, this.posZ, 2.1f, true, this.world.getGameRules().getBoolean("mobGriefing"));
        }
        this.setDead();
    }

    public void onUpdate() {
        super.onUpdate();
    }
}

