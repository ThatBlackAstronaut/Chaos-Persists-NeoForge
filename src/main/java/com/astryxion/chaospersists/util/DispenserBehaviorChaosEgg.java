/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.DispenserBehaviorChaosEgg
 *  com.astryxion.chaospersists.ItemSpawnEgg
 *  net.minecraft.block.BlockDispenser
 *  net.minecraft.dispenser.BehaviorDefaultDispenseItem
 *  net.minecraft.dispenser.IBlockSource
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.item.ItemSpawnEgg;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class DispenserBehaviorChaosEgg
extends BehaviorDefaultDispenseItem {
    public DispenserBehaviorChaosEgg() {
    }

    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        EnumFacing enumfacing = par1IBlockSource.getBlockState().getValue(BlockDispenser.FACING);
        double d0 = par1IBlockSource.getX() + (double)enumfacing.getXOffset() * 2.0;
        double d1 = (float)par1IBlockSource.getBlockPos().getY() + 0.2f;
        double d2 = par1IBlockSource.getZ() + (double)enumfacing.getZOffset() * 2.0;
        Item it = par2ItemStack.getItem();
        if (it instanceof ItemSpawnEgg) {
            ItemSpawnEgg ise = (ItemSpawnEgg)it;
            Entity entity = ItemSpawnEgg.spawn_something((int)ise.my_id, (World)par1IBlockSource.getWorld(), (double)((int)d0), (double)((int)d1), (double)((int)d2));
            if (entity instanceof EntityLivingBase && par2ItemStack.hasDisplayName()) {
                ((EntityLiving)entity).setCustomNameTag(par2ItemStack.getDisplayName());
            }
        }
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
}

