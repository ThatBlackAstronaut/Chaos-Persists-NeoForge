/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ItemPizza
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
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemPizza
extends Item {
    private Block spawnID;

    public ItemPizza(Block par2Block) {
        this.spawnID = par2Block;
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
        if (!this.spawnID.canPlaceBlockAt(world, placePos)) {
            return EnumActionResult.FAIL;
        }
        IBlockState state = this.spawnID.getStateForPlacement(world, placePos, facing, hitX, hitY, hitZ, 0, player);
        if (state == null || !world.mayPlace(this.spawnID, placePos, false, facing, (Entity) null)) {
            return EnumActionResult.FAIL;
        }
        if (!world.setBlockState(placePos, state, 3)) {
            return EnumActionResult.FAIL;
        }
        if (world.getBlockState(placePos).getBlock() == this.spawnID) {
            this.spawnID.onBlockPlacedBy(world, placePos, state, player, stack);
        }
        SoundCategory sc = SoundCategory.BLOCKS;
        world.playSound(null, placePos, this.spawnID.getSoundType(state, world, placePos, player).getPlaceSound(), sc,
                (this.spawnID.getSoundType(state, world, placePos, player).getVolume() + 1.0F) / 2.0F,
                this.spawnID.getSoundType(state, world, placePos, player).getPitch() * 0.8F);
        stack.shrink(1);
        return EnumActionResult.SUCCESS;
    }
}

