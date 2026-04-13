/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.InstantGarden
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockSand
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockSand;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InstantGarden
extends Item {
    public InstantGarden(int i) {
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
        int height = 10;
        int width = 7;
        int length = 18;
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
            int y = pposy;
            int z = cposz;
            if (x - pposx < 0) {
                deltax = -1;
            }
            if (x - pposx > 0) {
                deltax = 1;
            }
            if (z - pposz < 0) {
                deltaz = -1;
            }
            if (z - pposz > 0) {
                deltaz = 1;
            }
            if (deltax == 0 && deltaz == 0) {
                return EnumActionResult.FAIL;
            }
            if (deltax != 0 && deltaz != 0) {
                return EnumActionResult.FAIL;
            }
            Player.world.playSound(Player.posX, Player.posY, Player.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0f, 1.5f, false);
            if (world.isRemote) {
                return EnumActionResult.SUCCESS;
            }
            for (i = 0; i < height; ++i) {
                for (k = 0; k < length; ++k) {
                    for (j = - width; j <= width; ++j) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax), Blocks.AIR.getDefaultState(), 2);
                        if (i != 0) continue;
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y + i - 1, z + k * deltaz + j * deltax), Blocks.GRASS.getDefaultState(), 2);
                    }
                }
            }
            for (k = 1; k < length - 1; ++k) {
                i = 0;
                for (j = - width; j <= width; ++j) {
                    if (i == 1) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), ChaosPersists.MyRadishPlant.getDefaultState(), 2);
                    }
                    if (i == 2) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), ChaosPersists.MyLettucePlant1.getDefaultState(), 2);
                    }
                    if (i == 3) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.CARROTS.getDefaultState(), 2);
                    }
                    if (i == 4) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.WATER.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 2, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getDefaultState(), 2);
                    }
                    if (i == 5) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.POTATOES.getDefaultState(), 2);
                    }
                    if (i == 6) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.WHEAT.getDefaultState(), 2);
                    }
                    if (i == 7) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), ChaosPersists.MyTomatoPlant1.getDefaultState(), 2);
                    }
                    if (i == 8) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.WATER.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 2, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getDefaultState(), 2);
                    }
                    if (i == 9) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), ChaosPersists.MyCornPlant1.getDefaultState(), 2);
                    }
                    if (i == 10) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), ChaosPersists.MyStrawberryPlant.getDefaultState(), 2);
                    }
                    if (i == 11) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 2, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.SAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.REEDS.getDefaultState(), 2);
                    }
                    if (i == 12) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.WATER.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 2, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getDefaultState(), 2);
                    }
                    if (i == 13) {
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y - 1, z + k * deltaz + j * deltax), Blocks.FARMLAND.getDefaultState(), 2);
                        world.setBlockState(new BlockPos(x + k * deltax + j * deltaz, y, z + k * deltaz + j * deltax), Blocks.MELON_STEM.getDefaultState(), 2);
                    }
                    ++i;
                }
            }
            if (!Player.capabilities.isCreativeMode) {
                par1ItemStack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }}

