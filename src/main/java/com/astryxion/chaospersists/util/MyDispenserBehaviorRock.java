/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EntityThrownRock
 *  com.astryxion.chaospersists.MyDispenserBehaviorRock
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.BlockDispenser
 *  net.minecraft.dispenser.BehaviorProjectileDispense
 *  net.minecraft.dispenser.IBlockSource
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.entity.EntityThrownRock;
import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class MyDispenserBehaviorRock
extends BehaviorProjectileDispense {
    public MyDispenserBehaviorRock() {
    }

    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        World world = par1IBlockSource.getWorld();
        IPosition iposition = BlockDispenser.getDispensePosition((IBlockSource)par1IBlockSource);
        EnumFacing enumfacing = par1IBlockSource.getBlockState().getValue(BlockDispenser.FACING);
        IProjectile iprojectile = this.getProjectileEntity(world, iposition, par2ItemStack);
        ((net.minecraft.entity.projectile.EntityThrowable)iprojectile).shoot((double)enumfacing.getXOffset(), (double)((float)enumfacing.getYOffset() + 0.1f), (double)enumfacing.getZOffset(), 1.1f, 6.0f);
        EntityThrownRock r = (EntityThrownRock)iprojectile;
        if (par2ItemStack.getItem() == ChaosPersists.MySmallRock) {
            r.setRockType(1);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyRock) {
            r.setRockType(2);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyRedRock) {
            r.setRockType(3);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyGreenRock) {
            r.setRockType(4);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyBlueRock) {
            r.setRockType(5);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyPurpleRock) {
            r.setRockType(6);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MySpikeyRock) {
            r.setRockType(7);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyTNTRock) {
            r.setRockType(8);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyCrystalRedRock) {
            r.setRockType(9);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyCrystalGreenRock) {
            r.setRockType(10);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyCrystalBlueRock) {
            r.setRockType(11);
        }
        if (par2ItemStack.getItem() == ChaosPersists.MyCrystalTNTRock) {
            r.setRockType(12);
        }
        world.spawnEntity((Entity)iprojectile);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition, ItemStack par3ItemStack) {
        EntityThrownRock entityarrow = new EntityThrownRock(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        return entityarrow;
    }
}

