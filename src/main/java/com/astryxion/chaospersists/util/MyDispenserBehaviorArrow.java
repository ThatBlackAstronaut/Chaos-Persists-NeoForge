/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.IrukandjiArrow
 *  com.astryxion.chaospersists.MyDispenserBehaviorArrow
 *  net.minecraft.dispenser.BehaviorProjectileDispense
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.item.IrukandjiArrow;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public final class MyDispenserBehaviorArrow
extends BehaviorProjectileDispense {
    public MyDispenserBehaviorArrow() {
    }

    @Override
    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition, ItemStack par3ItemStack) {
        IrukandjiArrow entityarrow = new IrukandjiArrow(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        entityarrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
        return entityarrow;
    }

    @Override
    public ItemStack dispenseStack(IBlockSource par1IBlockSource, ItemStack par2ItemStack) {
        World world = par1IBlockSource.getWorld();
        IPosition iposition = BlockDispenser.getDispensePosition((IBlockSource) par1IBlockSource);
        EnumFacing enumfacing = par1IBlockSource.getBlockState().getValue(BlockDispenser.FACING);

        // Arrow entities are NOT EntityThrowable, so default dispenser logic will not shoot them.
        IrukandjiArrow arrow = new IrukandjiArrow(world, iposition.getX(), iposition.getY(), iposition.getZ());
        arrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
        arrow.shoot(
                (double) enumfacing.getXOffset(),
                (double) ((float) enumfacing.getYOffset() + 0.1f),
                (double) enumfacing.getZOffset(),
                1.1f,
                6.0f
        );

        world.spawnEntity((Entity) arrow);
        par2ItemStack.splitStack(1);
        return par2ItemStack;
    }
}
