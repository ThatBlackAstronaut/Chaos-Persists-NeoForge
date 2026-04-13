/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.LaserBall
 *  com.astryxion.chaospersists.MyDispenserBehaviorLaserball
 *  net.minecraft.dispenser.BehaviorProjectileDispense
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.item.LaserBall;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class MyDispenserBehaviorLaserball
extends BehaviorProjectileDispense {
    public MyDispenserBehaviorLaserball() {
    }

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition, ItemStack par3ItemStack) {
        LaserBall entityarrow = new LaserBall(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        return entityarrow;
    }
}
