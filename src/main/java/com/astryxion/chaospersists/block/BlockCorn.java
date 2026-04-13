/*
 * Decompiled with CFR 0_125.
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockCorn
extends BlockReed {
    private int myMaxHeight = 0;

    public BlockCorn() { this(0); }
    protected BlockCorn(int par1) {
        float var3 = 0.375f;
        this.setTickRandomly(true);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    /** 1.12.2: BlockReed only allows AGE 0-15; clamp so we never pass combined metadata (myMaxHeight<<8|age). */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta & 15);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockReed.AGE).intValue();
    }

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        Block bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4)).getBlock();
        if (bid == Blocks.AIR) {
            return false;
        }
        if (bid == ChaosPersists.MyCornPlant1 || bid == ChaosPersists.MyCornPlant2 || bid == ChaosPersists.MyCornPlant3 || bid == ChaosPersists.MyCornPlant4 || bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND) {
            return true;
        }
        return false;
    }

    public void updateTick(World par1World, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, Random par5Random) {
        int par2 = pos.getX(), par3 = pos.getY(), par4 = pos.getZ();
        Block bid;
        int Height = 1;
        boolean dontGrow = false;
        if (par1World.isRemote) {
            return;
        }
        if (this != ChaosPersists.MyCornPlant1 && this != ChaosPersists.MyCornPlant2) {
            return;
        }
        int var7 = this.getMetaFromState(state);
        this.myMaxHeight = var7 >> 8;
        var7 &= 255;
        if (this.myMaxHeight == 0) {
            this.myMaxHeight = 4 + ChaosPersists.ChaosRand.nextInt(4);
        }
        if ((bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 + 1, par4)).getBlock()) == Blocks.AIR) {
            for (int var6 = 1; var6 < 10 && ((bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - var6, par4)).getBlock()) == ChaosPersists.MyCornPlant1 || bid == ChaosPersists.MyCornPlant2 || bid == ChaosPersists.MyCornPlant3 || bid == ChaosPersists.MyCornPlant4); ++var6) {
                ++Height;
                if (bid != ChaosPersists.MyCornPlant3 && bid != ChaosPersists.MyCornPlant4) continue;
                dontGrow = true;
            }
            if (dontGrow) {
                this.myMaxHeight = Height;
            }
            if (var7 >= 6 - this.myMaxHeight / 3) {
                if (Height < this.myMaxHeight) {
                    par1World.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3 + 1, par4), ChaosPersists.MyCornPlant1.getStateFromMeta(0), 2);
                    par1World.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4), ChaosPersists.MyCornPlant2.getStateFromMeta(0), 2);
                } else {
                    for (int i = 1; i < this.myMaxHeight - 1; ++i) {
                        bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - i, par4)).getBlock();
                        if (bid == ChaosPersists.MyCornPlant2) {
                            par1World.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - i, par4), ChaosPersists.MyCornPlant3.getStateFromMeta(0), 2);
                            continue;
                        }
                        if (bid != ChaosPersists.MyCornPlant3) continue;
                        par1World.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - i, par4), ChaosPersists.MyCornPlant4.getStateFromMeta(0), 2);
                    }
                    bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
                    par1World.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4), bid.getStateFromMeta(0), 2);
                }
            } else {
                bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4)).getBlock();
                par1World.setBlockState(new net.minecraft.util.math.BlockPos(par2, par3, par4), bid.getStateFromMeta(Math.min(15, var7 + 1)), 2);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ChaosPersists.MyCornCob;
    }

    public Item getItem(int par1, Random par2Random, int par3) {
        return ChaosPersists.MyCornCob;
    }

    public int quantityDropped(Random par1Random) {
        if (this == ChaosPersists.MyCornPlant4) {
            return 1 + par1Random.nextInt(2);
        }
        return 0;
    }
}
