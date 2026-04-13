/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.InstantShelter
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockChest
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemEmptyMap
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityChest
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InstantShelter
extends Item {
    public InstantShelter(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer Player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack par1ItemStack = Player.getHeldItem(hand);
        int cposx = pos.getX();
        int cposy = pos.getY();
        int cposz = pos.getZ();
        int deltax = 0;
        int deltaz = 0;
        boolean bid = false;
        int dirx = 0;
        int dirz = 0;
        int stuffdir = 0;
        int length = 3;
        int width = 3;
        int height = 3;
        if (cposx < 0) {
            dirx = -1;
        }
        if (cposz < 0) {
            dirz = -1;
        }
        int pposx = (int)(Player.posX + 0.99 * (double)dirx);
        int pposy = (int)Player.posY;
        int pposz = (int)(Player.posZ + 0.99 * (double)dirz);
        if (cposx - pposx == 0 || cposz - pposz == 0) {
            int j;
            int i;
            int k;
            int x = cposx;
            int y = pposy - 1;
            int z = cposz;
            if (x - pposx < 0) {
                deltax = -1;
                stuffdir = 3;
            }
            if (x - pposx > 0) {
                deltax = 1;
                stuffdir = 2;
            }
            if (z - pposz < 0) {
                deltaz = -1;
                stuffdir = 5;
            }
            if (z - pposz > 0) {
                deltaz = 1;
                stuffdir = 4;
            }
            if (deltax == 0 && deltaz == 0) {
                return EnumActionResult.FAIL;
            }
            if (deltax != 0 && deltaz != 0) {
                return EnumActionResult.FAIL;
            }
            x = pposx;
            z = pposz;
            Player.world.playSound(Player.posX, Player.posY, Player.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.PLAYERS, 1.0f, 1.5f, false);
            if (world.isRemote) {
                return EnumActionResult.SUCCESS;
            }
            for (i = - width; i <= width; ++i) {
                for (j = - length; j <= length; ++j) {
                    for (k = 0; k <= height + 1; ++k) {
                        if (k == height + 1) {
                            world.setBlockState(new net.minecraft.util.math.BlockPos(x + i, y + k, z + j), Blocks.PLANKS.getDefaultState(), 3);
                            continue;
                        }
                        if (k == 0) {
                            world.setBlockState(new net.minecraft.util.math.BlockPos(x + i, y + k, z + j), Blocks.COBBLESTONE.getDefaultState(), 3);
                            continue;
                        }
                        if (i == width || j == length || i == - width || j == - length) {
                            if (k == height) {
                                world.setBlockState(new net.minecraft.util.math.BlockPos(x + i, y + k, z + j), Blocks.GLASS.getDefaultState(), 3);
                                continue;
                            }
                            if ((k == 1 || k == 2) && i == deltax * width && j == deltaz * length) {
                                world.setBlockState(new net.minecraft.util.math.BlockPos(x + i, y + k, z + j), Blocks.AIR.getDefaultState(), 3);
                                continue;
                            }
                            world.setBlockState(new net.minecraft.util.math.BlockPos(x + i, y + k, z + j), Blocks.PLANKS.getDefaultState(), 3);
                            continue;
                        }
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + i, y + k, z + j), Blocks.AIR.getDefaultState(), 3);
                    }
                }
            }
            i = 2;
            k = 1;
            j = length - 1;
            world.setBlockState(new net.minecraft.util.math.BlockPos(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax), Blocks.FURNACE.getDefaultState().withProperty(net.minecraft.block.BlockHorizontal.FACING, net.minecraft.util.EnumFacing.byHorizontalIndex(stuffdir)), 3);
            i = 1;
            world.setBlockState(new net.minecraft.util.math.BlockPos(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax), Blocks.CRAFTING_TABLE.getDefaultState(), 3);
            i = 0;
            world.setBlockState(new net.minecraft.util.math.BlockPos(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax), Blocks.CHEST.getDefaultState().withProperty(net.minecraft.block.BlockHorizontal.FACING, net.minecraft.util.EnumFacing.byHorizontalIndex(stuffdir)), 3);
            TileEntityChest chest = (TileEntityChest)world.getTileEntity(new net.minecraft.util.math.BlockPos(x + i * deltax + j * deltaz, y + k, z + i * deltaz + j * deltax));
            if (chest != null) {
                chest.setInventorySlotContents(0, new ItemStack(Items.COMPASS));
                chest.setInventorySlotContents(1, new ItemStack((Item)Items.MAP));
                chest.setInventorySlotContents(2, new ItemStack(Items.PORKCHOP, 8));
                chest.setInventorySlotContents(3, new ItemStack(Blocks.TORCH, 32));
                chest.setInventorySlotContents(4, new ItemStack(Items.COAL, 16));
                chest.setInventorySlotContents(5, new ItemStack(Items.BED));
                chest.setInventorySlotContents(6, new ItemStack(Items.BED));
                chest.setInventorySlotContents(7, new ItemStack(Items.OAK_DOOR));
                chest.setInventorySlotContents(8, new ItemStack(Items.IRON_PICKAXE));
                chest.setInventorySlotContents(9, new ItemStack(Items.IRON_SWORD));
                chest.setInventorySlotContents(10, new ItemStack(Items.IRON_AXE));
                chest.setInventorySlotContents(11, new ItemStack(Items.BUCKET));
                chest.setInventorySlotContents(12, new ItemStack(ChaosPersists.MyOreSaltBlock, 4));
                chest.setInventorySlotContents(13, new ItemStack((Block)Blocks.CHEST));
            }
            if (!Player.capabilities.isCreativeMode) {
                par1ItemStack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }}

