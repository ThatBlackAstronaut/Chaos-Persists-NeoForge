/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.OreRuby
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.item.Item
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.world.ore;

import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OreRuby extends Block {

    public OreRuby() {
        this(0);
    }

    public OreRuby(int i) {
        super(Material.ROCK);
        this.setHardness(10.0f);
        this.setResistance(4.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ChaosPersists.MyRuby;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int count = 1 + random.nextInt(2);
        if (fortune > 0) {
            count += random.nextInt(fortune + 1);
        }
        return Math.max(1, count);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        if (world instanceof World) {
            Random r = ((World) world).rand;
            return 5 + r.nextInt(5) + r.nextInt(5);
        }
        return 5;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        return true;
    }
}

