/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EntityLavaLovingItem
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityLavaLovingItem
extends EntityItem {
    public EntityLavaLovingItem(World par1World, double par2, double par4, double par6, ItemStack par8ItemStack) {
        super(par1World, par2, par4, par6, par8ItemStack);
                this.isImmuneToFire = true;
        this.hurtResistantTime = 300;
    }

    public void noFire() {
                this.isImmuneToFire = true;
        this.hurtResistantTime = 300;
    }

    public void yesFire() {
                this.isImmuneToFire = false;
        this.hurtResistantTime = 0;
    }

    protected void dealFireDamage(float par1) {
        if (!this.isImmuneToFire) {
            this.attackEntityFrom(DamageSource.IN_FIRE, par1);
        }
    }
}

