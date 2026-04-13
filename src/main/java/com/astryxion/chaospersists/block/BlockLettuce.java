/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockLettuce
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockReed
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockLettuce
extends BlockReed {
    public BlockLettuce() { this(0); }
    protected BlockLettuce(int par1) {
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

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        Block bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4)).getBlock();
        if (bid == Blocks.AIR) {
            return false;
        }
        if (bid == ChaosPersists.MyLettucePlant1 || bid == ChaosPersists.MyLettucePlant2 || bid == ChaosPersists.MyLettucePlant3 || bid == ChaosPersists.MyLettucePlant4 || bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND) {
            return true;
        }
        return false;
    }

    public void updateTick(World par1World, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, Random par5Random) {
        boolean dontGrow = false;
        if (par1World.isRemote) {
            return;
        }
        int par2 = pos.getX(), par3 = pos.getY(), par4 = pos.getZ();
        int var7 = this.getMetaFromState(state);
        if ((var7 &= 255) >= 4) {
            Block bid = par1World.getBlockState(pos).getBlock();
            if (bid == ChaosPersists.MyLettucePlant1) {
                par1World.setBlockState(pos, ChaosPersists.MyLettucePlant2.getDefaultState(), 2);
            } else if (bid == ChaosPersists.MyLettucePlant2) {
                par1World.setBlockState(pos, ChaosPersists.MyLettucePlant3.getDefaultState(), 2);
            } else if (bid == ChaosPersists.MyLettucePlant3) {
                par1World.setBlockState(pos, ChaosPersists.MyLettucePlant4.getDefaultState(), 2);
            }
        } else {
            Block bid = par1World.getBlockState(pos).getBlock();
            par1World.setBlockState(pos, bid.getStateFromMeta(var7 + 1), 2);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ChaosPersists.MyLettuce;
    }

    public int quantityDropped(Random par1Random) {
        if (this == ChaosPersists.MyLettucePlant4) {
            return 2 + par1Random.nextInt(3);
        }
        return 0;
    }}

