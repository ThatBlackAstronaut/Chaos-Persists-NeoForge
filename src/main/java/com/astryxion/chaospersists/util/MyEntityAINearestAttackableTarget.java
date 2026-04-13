/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Girlfriend
 *  com.astryxion.chaospersists.MyEntityAINearestAttackableTarget
 *  com.astryxion.chaospersists.MyEntityAINearestAttackableTargetSorter
 *  com.astryxion.chaospersists.MyEntityAITarget
 *  net.minecraft.command.IEntitySelector
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.util.MyEntityAINearestAttackableTargetSorter;
import com.astryxion.chaospersists.util.MyEntityAITarget;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.util.Random;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class MyEntityAINearestAttackableTarget
extends MyEntityAITarget {
    EntityLiving targetEntity;
    Class targetClass;
    int targetChance;
    private final Predicate<Entity> targetEntitySelector;
    private MyEntityAINearestAttackableTargetSorter theNearestAttackableTargetSorter;

    public MyEntityAINearestAttackableTarget(EntityLiving par1EntityLiving, Class par2Class, float par3, int par4, boolean par5) {
        this(par1EntityLiving, par2Class, par3, par4, par5, false);
    }

    public MyEntityAINearestAttackableTarget(EntityLiving par1EntityLiving, Class par2Class, float par3, int par4, boolean par5, boolean par6) {
        this(par1EntityLiving, par2Class, par3, par4, par5, par6, (Predicate<Entity>)null);
    }

    public MyEntityAINearestAttackableTarget(EntityLiving par1, Class par2, float par3, int par4, boolean par5, boolean par6, Predicate<Entity> par7IEntitySelector) {
        super(par1, par3, par5, par6);
        this.targetClass = par2;
        this.targetDistance = par3;
        this.targetChance = par4;
        this.theNearestAttackableTargetSorter = new MyEntityAINearestAttackableTargetSorter(this, (Entity)par1);
        this.targetEntitySelector = par7IEntitySelector != null ? par7IEntitySelector : Predicates.alwaysTrue();
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
      if (((this.taskOwner instanceof EntityTameable)) && (!((EntityTameable)this.taskOwner).isTamed()))
      {
        return false;
      }

      if (((this.taskOwner instanceof Girlfriend)) && (!((Girlfriend)this.taskOwner).isTamed()))
      {
        return false;
      }

      if (((this.taskOwner instanceof Girlfriend)) && (((Girlfriend)this.taskOwner).isSitting()))
      {
        return false;
      }

      if ((this.targetChance > 0) && (this.taskOwner.getRNG().nextInt(100) > this.targetChance))
      {
        return false;
      }

      List var5 = this.taskOwner.world.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(this.targetDistance, 4.0D, this.targetDistance), this.targetEntitySelector);
      Collections.sort(var5, this.theNearestAttackableTargetSorter);
      Iterator var2 = var5.iterator();

      while (var2.hasNext())
      {
        Entity var3 = (Entity)var2.next();
        EntityLiving var4 = (EntityLiving)var3;

        if (isSuitableTarget(var4, false))
        {
          this.targetEntity = var4;
          return true;
        }
      }

      this.targetEntity = null;
      return false;
    }


    public void startExecuting() {
        this.taskOwner.setAttackTarget((EntityLivingBase)this.targetEntity);
        super.startExecuting();
    }
}

