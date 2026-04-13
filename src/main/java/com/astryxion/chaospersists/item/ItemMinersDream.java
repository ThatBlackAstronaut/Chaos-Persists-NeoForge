/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ItemMinersDream
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
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
 *  net.minecraft.world.WorldProvider
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class ItemMinersDream
extends Item {
    public ItemMinersDream(int i) {
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
        int dirx = 0;
        int dirz = 0;
        int height = 5;
        int width = 5;
        int length = 64;
        int torches = 5;
        int solid_count = 0;
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
            Block bid;
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
            Player.world.playSound(Player.posX, Player.posY, Player.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.PLAYERS, 1.0f, 1.5f, false);
            if (world.isRemote) {
                return EnumActionResult.SUCCESS;
            }
            for (int i = 0; i < height; ++i) {
                for (k = 0; k < length; ++k) {
                    int j;
                    solid_count = 0;
                    for (j = - width; j <= width; ++j) {
                        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax)).getBlock();
                        if (bid == Blocks.STONE || bid == Blocks.DIRT || bid == Blocks.GRAVEL || bid == Blocks.FLOWING_WATER || bid == Blocks.WATER || bid == Blocks.FLOWING_LAVA || bid == Blocks.LAVA || bid == Blocks.NETHERRACK || bid == Blocks.END_STONE || bid == ChaosPersists.CrystalStone) {
                            world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i, z + k * deltaz + j * deltax), Blocks.AIR.getDefaultState(), 2);
                        }
                        if (i != height - 1) continue;
                        bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax)).getBlock();
                        if (bid != Blocks.AIR) {
                            ++solid_count;
                        }
                        if (bid != Blocks.AIR && bid != Blocks.GRAVEL && bid != Blocks.SAND && bid != Blocks.FLOWING_WATER && bid != Blocks.WATER && bid != Blocks.FLOWING_LAVA && bid != Blocks.LAVA) continue;
                        if (world.provider.getDimension() == ChaosPersists.getDimension(5)) {
                            world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax), ChaosPersists.CrystalStone.getDefaultState(), 2);
                            continue;
                        }
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax), Blocks.COBBLESTONE.getDefaultState(), 2);
                    }
                    if (i != height - 1 || solid_count != 0) continue;
                    for (j = - width; j <= width; ++j) {
                        world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax + j * deltaz, y + i + 1, z + k * deltaz + j * deltax), Blocks.AIR.getDefaultState(), 2);
                    }
                }
            }
            for (k = 0; k < length; k += torches) {
                bid = world.getBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax, y - 1, z + k * deltaz)).getBlock();
                if ((bid == Blocks.STONE || bid == Blocks.DIRT || bid == Blocks.GRAVEL || bid == Blocks.NETHERRACK || bid == Blocks.END_STONE || bid == Blocks.BEDROCK) && world.isAirBlock(new net.minecraft.util.math.BlockPos(x + k * deltax, y, z + k * deltaz))) {
                    world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax, y, z + k * deltaz), ChaosPersists.ExtremeTorch.getDefaultState(), 2);
                }
                if (bid != ChaosPersists.CrystalStone || !world.isAirBlock(new net.minecraft.util.math.BlockPos(x + k * deltax, y, z + k * deltaz))) continue;
                world.setBlockState(new net.minecraft.util.math.BlockPos(x + k * deltax, y, z + k * deltaz), ChaosPersists.CrystalTorch.getDefaultState(), 2);
            }
            if (!Player.capabilities.isCreativeMode) {
                par1ItemStack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }}

