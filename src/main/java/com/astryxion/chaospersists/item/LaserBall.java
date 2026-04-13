/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Dragon
 *  com.astryxion.chaospersists.GiantRobot
 *  com.astryxion.chaospersists.LaserBall
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.Robot2
 *  com.astryxion.chaospersists.Robot3
 *  com.astryxion.chaospersists.Robot4
 *  com.astryxion.chaospersists.Robot5
 *  com.astryxion.chaospersists.SpitBug
 *  com.astryxion.chaospersists.TrooperBug
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.item.Item
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.GameRules
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.entity.GiantRobot;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.Robot2;
import com.astryxion.chaospersists.entity.Robot3;
import com.astryxion.chaospersists.entity.Robot4;
import com.astryxion.chaospersists.entity.Robot5;
import com.astryxion.chaospersists.entity.SpitBug;
import com.astryxion.chaospersists.entity.TrooperBug;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class LaserBall
extends EntityThrowable {
    private float my_rotation = 0.0f;
    private int my_index = 81;
    private int is_special = 0;
    private int is_iceball = 0;
    private int is_acid = 0;
    private int is_irukandji = 0;
    private int ticksalive = 0;

    public LaserBall(World par1World) {
        super(par1World);
    }

    public LaserBall(World par1World, int par2) {
        super(par1World);
    }

    public LaserBall(World par1World, EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }

    public LaserBall(World par1World, EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
    }

    public LaserBall(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    public int getLaserBallIndex() {
        return this.my_index;
    }

    public void setSpecial() {
        this.is_special = 1;
    }

    public void setIceBall() {
        this.is_iceball = 1;
    }

    public void setAcid() {
        this.is_acid = 1;
    }

    public void setIrukandji() {
        this.is_irukandji = 1;
        this.is_acid = 1;
    }

    protected void onImpact(RayTraceResult par1MovingObjectPosition)
    {
      if (this.world.isRemote) {
        return;
      }
      if (par1MovingObjectPosition.entityHit != null)
      {
        float var2 = 16.0F;

        if (this.is_irukandji != 0) {
          par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 100.0F);
          setDead();
          return;
        }

        if (this.is_acid != 0) {
          if ((par1MovingObjectPosition.entityHit instanceof TrooperBug))
          {
            setDead();
            return;
          }
          if ((par1MovingObjectPosition.entityHit instanceof SpitBug))
          {
            setDead();
            return;
          }
        }
        if ((this.is_iceball == 0) && (this.is_acid == 0)) {
          if ((par1MovingObjectPosition.entityHit instanceof Robot2))
          {
            setDead();
            return;
          }
          if ((par1MovingObjectPosition.entityHit instanceof Robot3))
          {
            setDead();
            return;
          }
          if ((par1MovingObjectPosition.entityHit instanceof Robot4))
          {
            setDead();
            return;
          }
          if ((par1MovingObjectPosition.entityHit instanceof Robot5))
          {
            setDead();
            return;
          }
          if ((par1MovingObjectPosition.entityHit instanceof GiantRobot))
          {
            setDead();
            return;
          }
        }
        if (((par1MovingObjectPosition.entityHit instanceof Dragon)) && (this.is_acid == 0))
        {
          Dragon d = (Dragon)par1MovingObjectPosition.entityHit;
          if (!d.getPassengers().isEmpty()) {
            setDead();
            return;
          }

          if ((d.getDragonType() != 0) && (this.is_iceball != 0)) {
            setDead();
            return;
          }
        }

        if (((par1MovingObjectPosition.entityHit instanceof EntityPlayer)) && (this.is_acid == 0))
        {
          EntityPlayer d = (EntityPlayer)par1MovingObjectPosition.entityHit;
          if (d.getRidingEntity() != null) {
            setDead();
            return;
          }
        }

        par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), var2);
        if (this.is_iceball == 0) par1MovingObjectPosition.entityHit.setFire(1);

      }
      else if ((this.is_irukandji != 0) && 
        (!this.world.isRemote)) {
        dropItem(ChaosPersists.MyIrukandji, 1);
      }

      if (this.is_acid == 0) {
        int mx = 10;
        if (this.is_special != 0) mx = 20;
        for (int var3 = 0; var3 < mx; var3++)
        {
          this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX + this.rand.nextFloat() - this.rand.nextFloat(), this.posY + this.rand.nextFloat() - this.rand.nextFloat(), this.posZ + this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
          this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, this.posX + this.rand.nextFloat() - this.rand.nextFloat(), this.posY + this.rand.nextFloat() - this.rand.nextFloat(), this.posZ + this.rand.nextFloat() - this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
          this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, this.world.rand.nextGaussian(), this.world.rand.nextGaussian(), this.world.rand.nextGaussian());
        }

        playSound(net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
        if ((!this.world.isRemote) && ((this.is_special != 0) || (this.is_iceball != 0))) {
          this.world.createExplosion(this, this.posX, this.posY, this.posZ, 3.0F, this.world.getGameRules().getBoolean("mobGriefing"));
        }
      }
      setDead();
    }

    public void onUpdate() {
        ++this.ticksalive;
        if (this.ticksalive > 200) {
            this.setDead();
            return;
        }
        super.onUpdate();
        this.my_rotation += 50.0f;
        while (this.my_rotation > 360.0f) {
            this.my_rotation -= 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
        if (this.is_acid != 0) {
            return;
        }
        int mx = 4;
        if (this.is_special != 0) {
            mx = 10;
        }
        if (this.is_iceball != 0 && this.is_special == 0) {
            mx = 2;
        }
        for (int i = 0; i < mx; ++i) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY, this.posZ, this.world.rand.nextGaussian() / 2.0, this.world.rand.nextGaussian() / 2.0, this.world.rand.nextGaussian() / 2.0);
            if (this.is_iceball != 0) continue;
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, this.world.rand.nextGaussian() / 10.0, this.world.rand.nextGaussian() / 10.0, this.world.rand.nextGaussian() / 10.0);
        }
    }
}

