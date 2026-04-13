/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.CrystalCow
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RedCow
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.passive.EntityCow
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.RedCow;
import java.util.Random;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class CrystalCow
extends RedCow {
    public CrystalCow(World world) {
        super(world);
        this.spawnableBlock = ChaosPersists.CrystalGrass;
    }

    /** Crystal cow drops exactly one crystal apple, 100% of the time. */
    @Override
    protected void dropFewItems(boolean par1, int par2) {
        this.dropItem(ChaosPersists.MyCrystalApple, 1);
    }

    public EntityCow createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public CrystalCow spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return new CrystalCow(this.world);
    }
}

