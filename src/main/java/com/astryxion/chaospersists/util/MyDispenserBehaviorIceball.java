/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.IceBall
 *  com.astryxion.chaospersists.MyDispenserBehaviorIceball
 *  net.minecraft.dispenser.BehaviorProjectileDispense
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.item.IceBall;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class MyDispenserBehaviorIceball
extends BehaviorProjectileDispense {
    public MyDispenserBehaviorIceball() {
    }

    @Override
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        World world = par1IBlockSource.getWorld();
        IPosition iposition = BlockDispenser.getDispensePosition(par1IBlockSource);
        EnumFacing enumfacing = par1IBlockSource.getBlockState().getValue(BlockDispenser.FACING);
        IProjectile iprojectile = this.getProjectileEntity(world, iposition, par2ItemStack);

        if (iprojectile instanceof EntityThrowable) {
            // Same velocity style as rock dispenser behavior.
            ((EntityThrowable)iprojectile).shoot(
                (double)enumfacing.getXOffset(),
                (double)((float)enumfacing.getYOffset() + 0.1f),
                (double)enumfacing.getZOffset(),
                1.1f,
                6.0f
            );
        }
        if (iprojectile instanceof IceBall) {
            ((IceBall)iprojectile).setIceMaker(1);
        }

        world.spawnEntity((Entity)iprojectile);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition, ItemStack par3ItemStack) {
        IceBall entityarrow = new IceBall(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        return entityarrow;
    }
}
