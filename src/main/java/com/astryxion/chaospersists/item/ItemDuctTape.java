/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ItemDuctTape
 *  net.minecraft.block.Block
 *  net.minecraft.block.Block$SoundType
 *  net.minecraft.block.BlockDeadBush
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class ItemDuctTape
extends Item {
    private Block field_150935_a;

    public ItemDuctTape(Block par2Block) {
        this.field_150935_a = par2Block;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.isEmpty()) {
            return EnumActionResult.PASS;
        }
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        }
        BlockPos placePos = pos.up();
        if (!player.canPlayerEdit(placePos, facing, stack)) {
            return EnumActionResult.FAIL;
        }
        if (!this.field_150935_a.canPlaceBlockAt(world, placePos)) {
            return EnumActionResult.FAIL;
        }
        IBlockState state = this.field_150935_a.getStateForPlacement(world, placePos, facing, hitX, hitY, hitZ, 0, player);
        if (state == null || !world.mayPlace(this.field_150935_a, placePos, false, facing, (Entity) null)) {
            return EnumActionResult.FAIL;
        }
        if (!world.setBlockState(placePos, state, 3)) {
            return EnumActionResult.FAIL;
        }
        if (world.getBlockState(placePos).getBlock() == this.field_150935_a) {
            this.field_150935_a.onBlockPlacedBy(world, placePos, state, player, stack);
        }
        world.playSound(null, placePos, this.field_150935_a.getSoundType(state, world, placePos, player).getPlaceSound(),
                SoundCategory.BLOCKS,
                (this.field_150935_a.getSoundType(state, world, placePos, player).getVolume() + 1.0F) / 2.0F,
                this.field_150935_a.getSoundType(state, world, placePos, player).getPitch() * 0.8F);
        stack.shrink(1);
        return EnumActionResult.SUCCESS;
    }
}

