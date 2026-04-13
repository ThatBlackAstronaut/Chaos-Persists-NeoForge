/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.OreAmethyst
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

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class OreAmethyst
extends Block {
    public OreAmethyst() { this(0); }
    public OreAmethyst(int i) {
        super(Material.ROCK);
        this.setHardness(10.0f);
        this.setResistance(4.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void dropBlockAsItemWithChance(World par1World, BlockPos pos, IBlockState state, float par6, int par7) {
        super.dropBlockAsItemWithChance(par1World, pos, state, par6, par7);
        int j1 = 5 + par1World.rand.nextInt(5) + par1World.rand.nextInt(5);
        this.dropXpOnBlockBreak(par1World, pos, j1);
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return ChaosPersists.MyAmethyst;
    }

    public int quantityDroppedWithBonus(int par1, Random par2Random) {
        return 1 + par2Random.nextInt(2);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }}

