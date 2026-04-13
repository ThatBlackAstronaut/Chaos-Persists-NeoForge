package com.astryxion.chaospersists.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZooCage extends Item {
    private int cage_size = 2;

    public ZooCage(int i, int j) {
        this.setMaxStackSize(16);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.cage_size = j;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer Player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = Player.getHeldItem(hand);
        int length;
        int dirx = 0;
        int dirz = 0;
        int width = length = this.cage_size / 2 + 1;
        int height = length;
        int cposx = pos.getX();
        int cposz = pos.getZ();
        if (cposx < 0) {
            dirx = -1;
        }
        if (cposz < 0) {
            dirz = -1;
        }
        int x = (int) (Player.posX + 0.99 * (double) dirx);
        int y = (int) Player.posY - 1;
        int z = (int) (Player.posZ + 0.99 * (double) dirz);
        world.playSound(null, Player.posX, Player.posY, Player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, Player.getSoundCategory(), 1.0f, 1.5f);
        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        }
        for (int i = -width; i <= width; ++i) {
            for (int j = -length; j <= length; ++j) {
                for (int k = 0; k <= height + 1; ++k) {
                    BlockPos bp = new BlockPos(x + i, y + k, z + j);
                    if (k == height + 1) {
                        world.setBlockState(bp, Blocks.QUARTZ_BLOCK.getDefaultState());
                        continue;
                    }
                    if (k == 0) {
                        world.setBlockState(bp, Blocks.QUARTZ_BLOCK.getDefaultState());
                        continue;
                    }
                    if (i == width || j == length || i == -width || j == -length) {
                        world.setBlockState(bp, Blocks.GLASS.getDefaultState());
                        continue;
                    }
                    world.setBlockState(bp, Blocks.AIR.getDefaultState());
                }
            }
        }
        if (!Player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }
        return EnumActionResult.SUCCESS;
    }
}
