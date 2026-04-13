/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.OreUranium
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.world.ore;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OreUranium
extends Block {
    private boolean glowing = false;
    private int glowcount = 0;

    public OreUranium() { super(Material.ROCK);
        this.setHardness(10.0f);
        this.setResistance(1.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setTickRandomly(true);
        this.glowing = false;
    }

    public int tickRate() {
        return 30;
    }

    public void onBlockClicked(World par1World, BlockPos pos, EntityPlayer par5EntityPlayer) {
        this.glow(par1World, pos.getX(), pos.getY(), pos.getZ());
        super.onBlockClicked(par1World, pos, par5EntityPlayer);
    }

    public void onEntityWalk(World par1World, BlockPos pos, Entity par5Entity) {
        this.glow(par1World, pos.getX(), pos.getY(), pos.getZ());
        super.onEntityWalk(par1World, pos, par5Entity);
    }

    public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, net.minecraft.util.EnumFacing facing, float par7, float par8, float par9) {
        this.glow(par1World, pos.getX(), pos.getY(), pos.getZ());
        return super.onBlockActivated(par1World, pos, state, par5EntityPlayer, hand, facing, par7, par8, par9);
    }

    private void glow(World par1World, int par2, int par3, int par4) {
        this.glowing = true;
        this.glowcount = 10;
        this.sparkle(par1World, par2, par3, par4);
    }

    public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
    }

    @SideOnly(value=Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World par1World, BlockPos pos, Random par5Random) {
        int par2 = pos.getX(), par3 = pos.getY(), par4 = pos.getZ();
        if (this.glowing) {
            this.sparkle(par1World, par2, par3, par4);
            if (this.glowcount > 0) {
                --this.glowcount;
            } else {
                this.glowing = false;
            }
        }
    }

    private void sparkle(World par1World, int par2, int par3, int par4) {
        Random var5 = par1World.rand;
        double var6 = 0.0625;
        for (int var8 = 0; var8 < 6; ++var8) {
            double var9 = (float)par2 + var5.nextFloat();
            double var11 = (float)par3 + var5.nextFloat();
            double var13 = (float)par4 + var5.nextFloat();
            if (var8 == 0 && !par1World.getBlockState(new BlockPos(par2, par3 + 1, par4)).isFullCube()) {
                var11 = (double)(par3 + 1) + var6;
            }
            if (var8 == 1 && !par1World.getBlockState(new BlockPos(par2, par3 - 1, par4)).isFullCube()) {
                var11 = (double)(par3 + 0) - var6;
            }
            if (var8 == 2 && !par1World.getBlockState(new BlockPos(par2, par3, par4 + 1)).isFullCube()) {
                var13 = (double)(par4 + 1) + var6;
            }
            if (var8 == 3 && !par1World.getBlockState(new BlockPos(par2, par3, par4 - 1)).isFullCube()) {
                var13 = (double)(par4 + 0) - var6;
            }
            if (var8 == 4 && !par1World.getBlockState(new BlockPos(par2 + 1, par3, par4)).isFullCube()) {
                var9 = (double)(par2 + 1) + var6;
            }
            if (var8 == 5 && !par1World.getBlockState(new BlockPos(par2 - 1, par3, par4)).isFullCube()) {
                var9 = (double)(par2 + 0) - var6;
            }
            if (var9 >= (double)par2 && var9 <= (double)(par2 + 1) && var11 >= 0.0 && var11 <= (double)(par3 + 1) && var13 >= (double)par4 && var13 <= (double)(par4 + 1)) continue;
            par1World.spawnParticle(EnumParticleTypes.REDSTONE, var9, var11, var13, 0.0, 0.0, 0.0);
        }
    }

    public void dropBlockAsItemWithChance(World par1World, BlockPos pos, IBlockState state, float par6, int par7) {
        super.dropBlockAsItemWithChance(par1World, pos, state, par6, par7);
        int j1 = 5 + par1World.rand.nextInt(5) + par1World.rand.nextInt(10);
        if (pos.getY() < 40) {
            this.dropXpOnBlockBreak(par1World, pos, j1);
        }
    }

}

