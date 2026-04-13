/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.Lavafoam
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class Lavafoam
extends Block {
    public Lavafoam() { super(Material.ROCK);
        this.setHardness(5.0f);
        this.setResistance(5.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setTickRandomly(true);
        this.slipperiness = 1.1f;
    }

    public int tickRate() {
        return 10;
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        if (par1World.rand.nextInt(20) == 0) {
            this.sparkle(par1World, par2, par3, par4);
        }
    }

    private void sparkle(World par1World, int par2, int par3, int par4) {
        Random var5 = par1World.rand;
        double var6 = 0.0625;
        for (int var8 = 0; var8 < 6; ++var8) {
            double var9 = (float)par2 + var5.nextFloat();
            double var11 = (float)par3 + var5.nextFloat();
            double var13 = (float)par4 + var5.nextFloat();
            if (var8 == 0 && !par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 + 1, par4)).getBlock().isFullCube(par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 + 1, par4)))) {
                var11 = (double)(par3 + 1) + var6;
            }
            if (var8 == 1 && !par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4)).getBlock().isFullCube(par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4)))) {
                var11 = (double)(par3 + 0) - var6;
            }
            if (var8 == 2 && !par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4 + 1)).getBlock().isFullCube(par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4 + 1)))) {
                var13 = (double)(par4 + 1) + var6;
            }
            if (var8 == 3 && !par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4 - 1)).getBlock().isFullCube(par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4 - 1)))) {
                var13 = (double)(par4 + 0) - var6;
            }
            if (var8 == 4 && !par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2 + 1, par3, par4)).getBlock().isFullCube(par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2 + 1, par3, par4)))) {
                var9 = (double)(par2 + 1) + var6;
            }
            if (var8 == 5 && !par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2 - 1, par3, par4)).getBlock().isFullCube(par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2 - 1, par3, par4)))) {
                var9 = (double)(par2 + 0) - var6;
            }
            if (var9 >= (double)par2 && var9 <= (double)(par2 + 1) && var11 >= 0.0 && var11 <= (double)(par3 + 1) && var13 >= (double)par4 && var13 <= (double)(par4 + 1)) continue;
            int which = par1World.rand.nextInt(10);
            if (which == 1) {
                par1World.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, var9, var11, var13, 0.0, 0.0, 0.0);
            }
            if (which != 2) continue;
            par1World.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, var9, var11, var13, 0.0, 0.0, 0.0);
        }
    }

    public void onEntityCollidedWithBlock(World par1World, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, Entity par5Entity) {
        double pi = 3.14159;
        double pi2 = pi / 2.0;
        double pi4 = pi / 4.0;
        int par2 = pos.getX();
        int par3 = pos.getY();
        int par4 = pos.getZ();
        if (par5Entity == null) {
            return;
        }
        if (!(par5Entity instanceof EntityLivingBase)) {
            return;
        }
        double d = Math.atan2(par5Entity.posX - (double)((float)par2 + 0.5f), par5Entity.posZ - (double)((float)par4 + 0.5f));
        if (d < 0.0) {
            d = pi * 2.0 + d;
        }
        if (d > pi2 - pi4 && d < pi2 + pi4) {
            par5Entity.motionX = 0.44999998807907104;
            par5Entity.motionZ *= 1.350000023841858;
        } else if (d > pi - pi4 && d < pi + pi4) {
            par5Entity.motionZ = -0.44999998807907104;
            par5Entity.motionX *= 1.350000023841858;
        } else if (d > pi + pi2 - pi4 && d < pi + pi2 + pi4) {
            par5Entity.motionX = -0.44999998807907104;
            par5Entity.motionZ *= 1.350000023841858;
        } else {
            par5Entity.motionZ = 0.44999998807907104;
            par5Entity.motionX *= 1.350000023841858;
        }
        d = Math.sqrt(par5Entity.motionZ * par5Entity.motionZ + par5Entity.motionX * par5Entity.motionX);
        if (d > 1.0) {
            par5Entity.attackEntityFrom(DamageSource.FALL, (float)d);
        }
    }

    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        super.dropBlockAsItemWithChance(par1World, new net.minecraft.util.math.BlockPos(par2, par3, par4), par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)), par6, par7);
        int j1 = 5 + par1World.rand.nextInt(5) + par1World.rand.nextInt(5);
        if (par1World.provider.getDimension() == -1) {
            this.dropXpOnBlockBreak(par1World, new net.minecraft.util.math.BlockPos(par2, par3, par4), j1);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        float f = 0.0125f;
        return new AxisAlignedBB((double)((float)par2 + f), (double)par3, (double)((float)par4 + f), (double)((float)(par2 + 1) - f), (double)(par3 + 1), (double)((float)(par4 + 1) - f));
    }}

