/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.ItemChaosArmor
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RubyBirdDungeon
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockChest
 *  net.minecraft.init.Blocks
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.item.Item
 *  net.minecraft.tileentity.MobSpawnerBaseLogic
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.WeightedRandomChestContent
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.world.dimension.structure;

import com.astryxion.chaospersists.item.ItemChaosArmor;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.util.WeightedRandomChestContent;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RubyBirdDungeon {
    private final WeightedRandomChestContent[] chestContentsList = new WeightedRandomChestContent[]{new WeightedRandomChestContent(ChaosPersists.CageEmpty, 0, 3, 10, 20), new WeightedRandomChestContent(ChaosPersists.MyRuby, 0, 2, 8, 15), new WeightedRandomChestContent(ChaosPersists.MyBacon, 0, 6, 12, 20), new WeightedRandomChestContent(ChaosPersists.MyButterCandy, 0, 6, 12, 20), new WeightedRandomChestContent(ChaosPersists.MyRubyPickaxe, 0, 1, 1, 15), new WeightedRandomChestContent(ChaosPersists.MyRubyShovel, 0, 1, 1, 15), new WeightedRandomChestContent(ChaosPersists.MyRubyHoe, 0, 1, 1, 15), new WeightedRandomChestContent(ChaosPersists.MyRubyAxe, 0, 1, 1, 15), new WeightedRandomChestContent(ChaosPersists.MyRubySword, 0, 1, 1, 15), new WeightedRandomChestContent((Item)ChaosPersists.RubyBody, 0, 1, 1, 15), new WeightedRandomChestContent((Item)ChaosPersists.RubyLegs, 0, 1, 1, 15), new WeightedRandomChestContent((Item)ChaosPersists.RubyHelmet, 0, 1, 1, 15), new WeightedRandomChestContent((Item)ChaosPersists.RubyBoots, 0, 1, 1, 15), new WeightedRandomChestContent(ChaosPersists.MyThunderStaff, 0, 1, 1, 5)};

    private void setThisBlock(World world, int cposx, int cposy, int cposz) {
        if (world.rand.nextInt(20) == 1) {
            this.FastSetBlock(world, cposx, cposy, cposz, ChaosPersists.MyOreRubyBlock);
        } else if (world.rand.nextInt(2) == 1) {
            this.FastSetBlock(world, cposx, cposy, cposz, Blocks.MOSSY_COBBLESTONE);
        } else {
            this.FastSetBlock(world, cposx, cposy, cposz, Blocks.COBBLESTONE);
        }
    }

    public void makeDungeon(World world, int cposx, int cposy, int cposz) {
        int i;
        int k;
        int j;
        int width = 10;
        int height = 5;
        for (i = 0; i < width; ++i) {
            for (j = 0; j < height; ++j) {
                for (k = 0; k < width; ++k) {
                    this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.AIR);
                }
            }
        }
        for (i = 0; i < width; ++i) {
            j = 0;
            for (k = 0; k < width; ++k) {
                this.FastSetBlock(world, cposx + i, cposy + j, cposz + k, Blocks.MOSSY_COBBLESTONE);
            }
        }
        for (i = 0; i < width; ++i) {
            j = height - 1;
            for (k = 0; k < width; ++k) {
                this.setThisBlock(world, cposx + i, cposy + j, cposz + k);
            }
        }
        for (i = 0; i < width; ++i) {
            for (j = 0; j < height; ++j) {
                k = 0;
                this.setThisBlock(world, cposx + i, cposy + j, cposz + k);
                k = width - 1;
                this.setThisBlock(world, cposx + i, cposy + j, cposz + k);
            }
        }
        for (k = 0; k < width; ++k) {
            for (j = 0; j < height; ++j) {
                i = 0;
                this.setThisBlock(world, cposx + i, cposy + j, cposz + k);
                i = width - 1;
                this.setThisBlock(world, cposx + i, cposy + j, cposz + k);
            }
        }
        BlockPos spawnerPos = new BlockPos(cposx + width / 2, cposy + 1, cposz + width / 2);
        world.setBlockState(spawnerPos, Blocks.MOB_SPAWNER.getDefaultState(), 2);
        TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(spawnerPos);
        if (tileentitymobspawner != null) {
            tileentitymobspawner.getSpawnerBaseLogic().setEntityId(new net.minecraft.util.ResourceLocation("chaospersists", "ruby_bird"));
        }
        TileEntityChest chest = null;
        BlockPos chestPos = new BlockPos(cposx + width / 2, cposy + 1, cposz + 1);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 2);
        chest = (TileEntityChest)world.getTileEntity(chestPos);
        if (chest != null) {
            WeightedRandomChestContent.generateChestContents((Random)world.rand, (WeightedRandomChestContent[])this.chestContentsList, (IInventory)chest, (int)(4 + world.rand.nextInt(7)));
        }
    }

    public void FastSetBlock(World world, int ix, int iy, int iz, Block id) {
        ChaosPersists.setBlockFast((World)world, (int)ix, (int)iy, (int)iz, (Block)id, (int)0, (int)2);
    }
}

