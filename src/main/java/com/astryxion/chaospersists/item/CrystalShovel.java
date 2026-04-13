/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.CrystalShovel
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockMycelium
 *  net.minecraft.block.BlockSand
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemTool
 *  net.minecraft.util.IIcon
 */
package com.astryxion.chaospersists.item;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockSand;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;

public class CrystalShovel
extends ItemTool {
    public static final Set blocksEffectiveAgainst = Sets.newHashSet((Object[])new Block[]{Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.CLAY, Blocks.FARMLAND, Blocks.MYCELIUM, ChaosPersists.CrystalGrass});

    public CrystalShovel(Item.ToolMaterial par2) {
        super(par2.getAttackDamage(), 1.0f, par2, blocksEffectiveAgainst);
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public boolean canHarvestBlock(Block par1Block) {
        return par1Block == Blocks.SNOW;
    }}

