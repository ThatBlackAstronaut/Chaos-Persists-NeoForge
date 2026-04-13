/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.RedCow
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.passive.EntityCow
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import java.util.Random;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RedCow
extends EntityCow {
    public RedCow(World world) {
        super(world);
    }

    /** Return null so dropFewItems is used instead of the vanilla cow loot table (leather/beef only). */
    @Override
    protected ResourceLocation getLootTable() {
        return null;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int appleCount = 1 + this.rand.nextInt(2 + par2);
        for (int i = 0; i < appleCount; i++) {
            this.dropItem(Items.APPLE, 1);
        }
        this.dropItem(Items.LEATHER, 1 + this.rand.nextInt(1 + par2));
        this.dropItem(Items.BEEF, 1 + this.rand.nextInt(2 + par2));
    }

    public EntityCow createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public RedCow spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return new RedCow(this.world);
    }

    protected void updateAITick() {
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        super.updateAITasks();
    }

    protected boolean canDespawn() {
        return false;
    }
}
