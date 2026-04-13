package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.entity.WaterDragon;
import com.astryxion.chaospersists.entity.AttackSquid;
import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.core.ChaosPersists;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class WaterBall extends EntityThrowable
{
  private float my_rotation = 0.0F;
  private int my_index = 49;

  public WaterBall(World par1World)
  {
    super(par1World);
  }

  public WaterBall(World par1World, EntityLivingBase par2EntityLiving)
  {
    super(par1World, par2EntityLiving);
  }

  public WaterBall(World worldObj, double d, double e, double f)
  {
    super(worldObj, d, e, f);
  }

  public int getWaterBallIndex()
  {
    return this.my_index;
  }

  protected void onImpact(RayTraceResult par1MovingObjectPosition)
  {
    if (par1MovingObjectPosition.entityHit != null)
    {
      float var2 = 2.0F;

      if ((par1MovingObjectPosition.entityHit instanceof EntityCreeper))
      {
        var2 = 5.0F;
      }
      if ((par1MovingObjectPosition.entityHit instanceof WaterDragon))
      {
        return;
      }
      if ((par1MovingObjectPosition.entityHit instanceof AttackSquid))
      {
        return;
      }
      if ((par1MovingObjectPosition.entityHit instanceof Dragon))
      {
        Dragon d = (Dragon)par1MovingObjectPosition.entityHit;
        if (d.getDragonType() != 0) {
          return;
        }
      }
      if ((par1MovingObjectPosition.entityHit instanceof EntityPlayer))
      {
        EntityPlayer d = (EntityPlayer)par1MovingObjectPosition.entityHit;
        if (d.getRidingEntity() != null) {
          return;
        }
      }
      par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), var2);
      if (this.world.rand.nextInt(10) == 1) par1MovingObjectPosition.entityHit.dropItem(ChaosPersists.MyWaterBall, 1);
      par1MovingObjectPosition.entityHit.extinguish();
    }

    for (int var3 = 0; var3 < 8; var3++)
    {
      this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_BUBBLE, this.posX + this.rand.nextFloat() - this.rand.nextFloat(), this.posY + this.rand.nextFloat() - this.rand.nextFloat(), this.posZ + this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
      this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_SPLASH, this.posX + this.rand.nextFloat() - this.rand.nextFloat(), this.posY + this.rand.nextFloat() - this.rand.nextFloat(), this.posZ + this.rand.nextFloat() - this.rand.nextFloat(), 0.0D, 0.0D, 0.0D);
    }
    playSound(net.minecraft.init.SoundEvents.ENTITY_GENERIC_SPLASH, 0.5F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);

    if (!this.world.isRemote)
    {
      setDead();
    }
  }

  public void onUpdate()
  {
    super.onUpdate();
    this.my_rotation += 30.0F;

    while (this.my_rotation > 360.0F) {
      this.my_rotation -= 360.0F;
    }

    this.rotationPitch = (this.prevRotationPitch = this.my_rotation);

    this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_SPLASH, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
  }
}