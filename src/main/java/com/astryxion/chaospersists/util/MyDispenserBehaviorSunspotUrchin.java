/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.MyDispenserBehaviorSunspotUrchin
 *  com.astryxion.chaospersists.SunspotUrchin
 *  net.minecraft.dispenser.BehaviorProjectileDispense
 *  net.minecraft.dispenser.IPosition
 *  net.minecraft.entity.IProjectile
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.entity.SunspotUrchin;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public final class MyDispenserBehaviorSunspotUrchin
extends BehaviorProjectileDispense {
    public MyDispenserBehaviorSunspotUrchin() {
    }

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition, ItemStack par3ItemStack) {
        SunspotUrchin entityarrow = new SunspotUrchin(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        return entityarrow;
    }
}
