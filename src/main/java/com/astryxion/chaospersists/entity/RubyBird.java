/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Cockateil
 *  com.astryxion.chaospersists.RubyBird
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.Cockateil;
import net.minecraft.world.World;

public class RubyBird
extends Cockateil {
    public RubyBird(World par1World) {
        super(par1World);
    }

    protected void entityInit() {
        super.entityInit();
        this.birdtype = 5;
        this.setBirdType(this.birdtype);
        this.setFlyUp();
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        if (this.world.isDaytime() && !this.world.isRaining()) {
            return com.astryxion.chaospersists.core.ChaosSounds.RUBYBIRD;
        }
        return null;
    }

    public boolean getCanSpawnHere() {
        return true;
    }
}

