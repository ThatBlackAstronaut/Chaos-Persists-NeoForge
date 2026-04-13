/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.IceBall
 *  com.astryxion.chaospersists.LaserBall
 *  com.astryxion.chaospersists.MyUtils
 *  net.minecraft.block.Block
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.item.LaserBall;
import com.astryxion.chaospersists.util.MyUtils;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IceBall
extends LaserBall {
    private int my_index = 84;
    private int icemaker = 0;

    public IceBall(World par1World) {
        super(par1World);
        super.setIceBall();
    }

    public IceBall(World par1World, int par2) {
        super(par1World);
        super.setIceBall();
    }

    public IceBall(World par1World, EntityLivingBase par2EntityLiving) {
        super(par1World, par2EntityLiving);
        super.setIceBall();
    }

    public IceBall(World par1World, EntityLivingBase par2EntityLiving, int par3) {
        super(par1World, par2EntityLiving);
        super.setIceBall();
    }

    public IceBall(World par1World, double par2, double par4, double par6) {
        super(par1World, par2, par4, par6);
        super.setIceBall();
    }

    public int getIceBallIndex() {
        return this.my_index;
    }

    public void setIceMaker(int i) {
        this.icemaker = i;
    }

    protected void onImpact(RayTraceResult par1MovingObjectPosition) {
        if (this.world.isRemote) {
            return;
        }
        if (par1MovingObjectPosition.entityHit != null && MyUtils.isRoyalty((Entity)par1MovingObjectPosition.entityHit)) {
            this.setDead();
            return;
        }
        super.onImpact(par1MovingObjectPosition);
        if (this.icemaker != 0) {
            for (int i = 0; i < 5; ++i) {
                int x = this.world.rand.nextInt(4);
                if (this.world.rand.nextInt(2) == 1) {
                    x = - x;
                }
                int y = this.world.rand.nextInt(4);
                if (this.world.rand.nextInt(2) == 1) {
                    y = - y;
                }
                int z = this.world.rand.nextInt(4);
                if (this.world.rand.nextInt(2) == 1) {
                    z = - z;
                }
                x = (int)((double)x + par1MovingObjectPosition.hitVec.x);
                y = (int)((double)y + par1MovingObjectPosition.hitVec.y);
                z = (int)((double)z + par1MovingObjectPosition.hitVec.z);
                this.world.setBlockState(new net.minecraft.util.math.BlockPos(x, y, z), Blocks.ICE.getDefaultState(), 3);
            }
        }
    }
}

