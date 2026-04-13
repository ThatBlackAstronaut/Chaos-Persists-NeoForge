/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockStrawberry
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.BlockCrops
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.item.Item
 *  net.minecraft.util.IIcon
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;

public class BlockStrawberry extends BlockCrops {

    public BlockStrawberry() {
        this(0);
    }

    public BlockStrawberry(int par1) {
        setDefaultState(blockState.getBaseState().withProperty(BlockCrops.AGE, Integer.valueOf(0)));
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

    public int quantityDropped(Random par1Random) {
        return 1 + par1Random.nextInt(5);
    }

    protected Item func_149866_i() {
        return ChaosPersists.MyStrawberrySeed;
    }

    protected Item func_149865_P() {
        return ChaosPersists.MyStrawberry;
    }

}

