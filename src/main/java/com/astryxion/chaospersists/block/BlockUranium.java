/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockUranium
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockUranium
extends Block {
    public BlockUranium() { super(Material.ROCK);
        this.setHardness(5.0f);
        this.setResistance(5.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setLightLevel(0.2f);
    }

    public int tickRate() {
        return 100;
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
            net.minecraft.block.state.IBlockState up = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 + 1, par4));
            if (var8 == 0 && !up.getBlock().isFullCube(up)) {
                var11 = (double)(par3 + 1) + var6;
            }
            net.minecraft.block.state.IBlockState down = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4));
            if (var8 == 1 && !down.getBlock().isFullCube(down)) {
                var11 = (double)(par3 + 0) - var6;
            }
            net.minecraft.block.state.IBlockState south = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4 + 1));
            if (var8 == 2 && !south.getBlock().isFullCube(south)) {
                var13 = (double)(par4 + 1) + var6;
            }
            net.minecraft.block.state.IBlockState north = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4 - 1));
            if (var8 == 3 && !north.getBlock().isFullCube(north)) {
                var13 = (double)(par4 + 0) - var6;
            }
            net.minecraft.block.state.IBlockState east = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2 + 1, par3, par4));
            if (var8 == 4 && !east.getBlock().isFullCube(east)) {
                var9 = (double)(par2 + 1) + var6;
            }
            net.minecraft.block.state.IBlockState west = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2 - 1, par3, par4));
            if (var8 == 5 && !west.getBlock().isFullCube(west)) {
                var9 = (double)(par2 + 0) - var6;
            }
            if (var9 >= (double)par2 && var9 <= (double)(par2 + 1) && var11 >= 0.0 && var11 <= (double)(par3 + 1) && var13 >= (double)par4 && var13 <= (double)(par4 + 1)) continue;
            int which = par1World.rand.nextInt(3);
            if (which == 0) {
                par1World.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, var9, var11, var13, 0.0, 0.0, 0.0);
            }
            if (which == 1) {
                par1World.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, var9, var11, var13, 0.0, 0.0, 0.0);
            }
            if (which != 2) continue;
            par1World.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, var9, var11, var13, 0.0, 0.0, 0.0);
        }
    }}

