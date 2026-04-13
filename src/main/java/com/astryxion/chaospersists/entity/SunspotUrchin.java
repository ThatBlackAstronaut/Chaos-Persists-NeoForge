/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.SunspotUrchin
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockFire
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityThrowable
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import static net.minecraft.util.EnumFacing.*;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SunspotUrchin
extends EntityThrowable {
    private float my_rotation = 0.0f;
    private int my_index = 50;

    public SunspotUrchin(World par1World) {
        super(par1World);
    }

    public SunspotUrchin(World par1World, int par2) {
        super(par1World);
    }

    public SunspotUrchin(World par1World, EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
    }

    public SunspotUrchin(World par1World, EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
    }

    public SunspotUrchin(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
    }

    public int getUrchinIndex() {
        return this.my_index;
    }

    protected void onImpact(RayTraceResult par1MovingObjectPosition) {
        if (par1MovingObjectPosition.entityHit != null) {
            float var2 = 3.0f;
            if (par1MovingObjectPosition.entityHit instanceof EntityCreeper) {
                var2 = 6.0f;
            }
            if (!(par1MovingObjectPosition.entityHit instanceof EntityPlayer)) {
                par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getThrower()), var2);
                if (!par1MovingObjectPosition.entityHit.isImmuneToFire()) {
                    par1MovingObjectPosition.entityHit.setFire(5);
                }
            }
        } else {
            int i = par1MovingObjectPosition.getBlockPos().getX();
            int j = par1MovingObjectPosition.getBlockPos().getY();
            int k = par1MovingObjectPosition.getBlockPos().getZ();
            switch (par1MovingObjectPosition.sideHit) {
                case DOWN:
                    --j;
                    break;
                case UP:
                    ++j;
                    break;
                case NORTH:
                    --k;
                    break;
                case SOUTH:
                    ++k;
                    break;
                case WEST:
                    --i;
                    break;
                case EAST:
                    ++i;
                    break;
                default:
                    break;
            }
            if (this.world.isAirBlock(new net.minecraft.util.math.BlockPos(i, j, k))) {
                this.world.setBlockState(new net.minecraft.util.math.BlockPos(i, j, k), Blocks.FIRE.getDefaultState());
            }
        }
        for (int var3 = 0; var3 < 5; ++var3) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, (double)this.world.rand.nextFloat(), (double)this.world.rand.nextFloat(), (double)this.world.rand.nextFloat());
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, (double)this.world.rand.nextFloat(), (double)this.world.rand.nextFloat(), (double)this.world.rand.nextFloat());
        }
        if (!this.world.isRemote) {
            this.setDead();
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.setFire(1);
        this.my_rotation += 30.0f;
        while (this.my_rotation > 360.0f) {
            this.my_rotation -= 360.0f;
        }
        this.rotationPitch = this.prevRotationPitch = this.my_rotation;
        this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
    }
}

