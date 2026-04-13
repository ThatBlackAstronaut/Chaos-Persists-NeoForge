/*
 * Decompiled with CFR 0_125.
 *
 * 1.12.2: use EntityPlayer.onItemUse(BlockPos, EnumHand, EnumFacing) — the old int-coord
 * method is never called by the game (matches 1.7.10: place MyExperiencePlant above soil).
 */
package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemExperienceTreeSeed extends Item {

    public ItemExperienceTreeSeed(int i) {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
                                      World world,
                                      BlockPos pos,
                                      EnumHand hand,
                                      EnumFacing facing,
                                      float hitX,
                                      float hitY,
                                      float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        }

        ItemStack stack = player.getHeldItem(hand);
        Block ground = world.getBlockState(pos).getBlock();
        if (ground != Blocks.GRASS && ground != Blocks.DIRT && ground != Blocks.FARMLAND) {
            return EnumActionResult.FAIL;
        }

        BlockPos above = pos.up();
        if (!world.isAirBlock(above)) {
            return EnumActionResult.FAIL;
        }

        if (!world.isRemote) {
            world.setBlockState(above, ChaosPersists.MyExperiencePlant.getDefaultState(), 2);
            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
        } else {
            for (int j1 = 0; j1 < 10; ++j1) {
                world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                        (double) ((float) pos.getX() + world.rand.nextFloat()),
                        (double) pos.getY() + 1.0 + (double) world.rand.nextFloat(),
                        (double) ((float) pos.getZ() + world.rand.nextFloat()),
                        0.0, 0.0, 0.0);
            }
        }

        return EnumActionResult.SUCCESS;
    }
}
