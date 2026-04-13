/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.KingHead
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.TheKing
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.TheKing;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class KingHead
extends EntityLiving {
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    public KingHead(World par1World) {
        super(par1World);
        this.setSize(19.9f, 10.0f);
        this.noClip = true;
                this.isImmuneToFire = true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ChaosPersists.TheKing_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.3300000429153442);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    protected boolean canDespawn() {
        return false;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
        super.entityInit();
    }

    public boolean canBePushed() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return false;
        }
        Entity e = par1DamageSource.getTrueSource();
        if (e != null && (e instanceof TheKing || e instanceof KingHead)) {
            return false;
        }
        e = par1DamageSource.getImmediateSource();
        if (e != null && (e instanceof TheKing || e instanceof KingHead)) {
            return false;
        }
        List var5 = this.world.getEntitiesWithinAABB(TheKing.class, this.getEntityBoundingBox().expand(48.0, 32.0, 48.0));
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        TheKing var4 = null;
        if (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (TheKing)var3;
            ret = var4.attackEntityFrom(par1DamageSource, par2);
        }
        return ret;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.boatPosRotationIncrements = this.getControllingPassenger() != null ? par9 + 8 : 6;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        this.velocityX = this.motionX = par1;
        this.velocityY = this.motionY = par3;
        this.velocityZ = this.motionZ = par5;
    }

    public void onUpdate() {
        if (this.isDead) {
            return;
        }
        this.isAirBorne = true;
        this.setFire(0);
        if (this.world.isRemote) {
            if (this.boatPosRotationIncrements > 0) {
                double d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                double d10 = MathHelper.wrapDegrees(this.boatYaw - (double)this.rotationYaw);
                if (this.getControllingPassenger() != null) {
                    d10 = MathHelper.wrapDegrees((double)this.getControllingPassenger().rotationYaw - (double)this.rotationYaw);
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                --this.boatPosRotationIncrements;
            }
        } else {
            List var5 = this.world.getEntitiesWithinAABB(TheKing.class, this.getEntityBoundingBox().expand(32.0, 32.0, 32.0));
            Iterator var2 = var5.iterator();
            Entity var3 = null;
            TheKing var4 = null;
            if (var2.hasNext()) {
                var3 = (Entity)var2.next();
                var4 = (TheKing)var3;
                this.posY = var4.posY + 12.0;
                this.posX = var4.posX - 30.0 * Math.sin(Math.toRadians(var4.rotationYawHead));
                this.posZ = var4.posZ + 30.0 * Math.cos(Math.toRadians(var4.rotationYawHead));
                this.rotationYaw = var4.rotationYaw;
                this.rotationYawHead = var4.rotationYawHead;
                this.motionX = var4.motionX;
                this.motionY = var4.motionY;
                this.motionZ = var4.motionZ;
                this.setHealth(var4.getHealth());
            } else {
                this.setDead();
            }
        }
    }
}

