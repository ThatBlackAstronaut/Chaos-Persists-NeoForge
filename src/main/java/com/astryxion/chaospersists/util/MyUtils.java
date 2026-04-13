/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.Cephadrome
 *  com.astryxion.chaospersists.Cockateil
 *  com.astryxion.chaospersists.Cricket
 *  com.astryxion.chaospersists.Dragon
 *  com.astryxion.chaospersists.Dragonfly
 *  com.astryxion.chaospersists.Elevator
 *  com.astryxion.chaospersists.EntityAnt
 *  com.astryxion.chaospersists.EntityButterfly
 *  com.astryxion.chaospersists.EntityMosquito
 *  com.astryxion.chaospersists.Firefly
 *  com.astryxion.chaospersists.GammaMetroid
 *  com.astryxion.chaospersists.Ghost
 *  com.astryxion.chaospersists.GhostSkelly
 *  com.astryxion.chaospersists.Girlfriend
 *  com.astryxion.chaospersists.KingHead
 *  com.astryxion.chaospersists.Leon
 *  com.astryxion.chaospersists.Mothra
 *  com.astryxion.chaospersists.MyUtils
 *  com.astryxion.chaospersists.PurplePower
 *  com.astryxion.chaospersists.QueenHead
 *  com.astryxion.chaospersists.RockBase
 *  com.astryxion.chaospersists.Spyro
 *  com.astryxion.chaospersists.Stinky
 *  com.astryxion.chaospersists.Termite
 *  com.astryxion.chaospersists.TheKing
 *  com.astryxion.chaospersists.ThePrince
 *  com.astryxion.chaospersists.ThePrinceAdult
 *  com.astryxion.chaospersists.ThePrinceTeen
 *  com.astryxion.chaospersists.ThePrincess
 *  com.astryxion.chaospersists.TheQueen
 *  com.astryxion.chaospersists.WaterDragon
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityVillager
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Cephadrome;
import com.astryxion.chaospersists.entity.Cockateil;
import com.astryxion.chaospersists.entity.Cricket;
import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.entity.Dragonfly;
import com.astryxion.chaospersists.item.Elevator;
import com.astryxion.chaospersists.entity.EntityAnt;
import com.astryxion.chaospersists.entity.EntityButterfly;
import com.astryxion.chaospersists.entity.EntityMosquito;
import com.astryxion.chaospersists.entity.Firefly;
import com.astryxion.chaospersists.entity.GammaMetroid;
import com.astryxion.chaospersists.entity.Ghost;
import com.astryxion.chaospersists.entity.GhostSkelly;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.entity.KingHead;
import com.astryxion.chaospersists.entity.Leon;
import com.astryxion.chaospersists.entity.Mothra;
import com.astryxion.chaospersists.item.PurplePower;
import com.astryxion.chaospersists.entity.QueenHead;
import com.astryxion.chaospersists.entity.RockBase;
import com.astryxion.chaospersists.entity.Spyro;
import com.astryxion.chaospersists.entity.Stinky;
import com.astryxion.chaospersists.entity.Termite;
import com.astryxion.chaospersists.entity.TheKing;
import com.astryxion.chaospersists.entity.ThePrince;
import com.astryxion.chaospersists.entity.ThePrinceAdult;
import com.astryxion.chaospersists.entity.ThePrinceTeen;
import com.astryxion.chaospersists.entity.ThePrincess;
import com.astryxion.chaospersists.entity.TheQueen;
import com.astryxion.chaospersists.entity.WaterDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;

/*
 * Exception performing whole class analysis ignored.
 */
public class MyUtils {
    public MyUtils() {
    }

    public static boolean isRoyalty(Entity e) {
        if (!(e instanceof EntityLivingBase)) {
            return false;
        }
        if (e instanceof ThePrince) {
            return true;
        }
        if (e instanceof ThePrinceTeen) {
            return true;
        }
        if (e instanceof ThePrinceAdult) {
            return true;
        }
        if (e instanceof ThePrincess) {
            return true;
        }
        if (e instanceof TheKing) {
            return true;
        }
        if (e instanceof KingHead) {
            return true;
        }
        if (e instanceof TheQueen) {
            return true;
        }
        if (e instanceof QueenHead) {
            return true;
        }
        if (e instanceof PurplePower) {
            return true;
        }
        return false;
    }

    public static boolean isAttackableNonMob(EntityLivingBase par1EntityLiving) {
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof Mothra) {
            return true;
        }
        if (par1EntityLiving instanceof Leon) {
            return true;
        }
        if (par1EntityLiving instanceof Dragon) {
            return true;
        }
        if (par1EntityLiving instanceof Spyro) {
            return true;
        }
        if (MyUtils.isRoyalty((Entity)par1EntityLiving)) {
            return true;
        }
        if (par1EntityLiving instanceof GammaMetroid) {
            return true;
        }
        if (par1EntityLiving instanceof Cephadrome) {
            return true;
        }
        if (par1EntityLiving instanceof WaterDragon) {
            return true;
        }
        if (par1EntityLiving instanceof Girlfriend) {
            return true;
        }
        if (par1EntityLiving instanceof Boyfriend) {
            return true;
        }
        if (par1EntityLiving instanceof EntityVillager) {
            return true;
        }
        if (par1EntityLiving instanceof Stinky) {
            return true;
        }
        return false;
    }

    public static boolean isIgnoreable(EntityLivingBase par1EntityLiving) {
        if (par1EntityLiving instanceof RockBase) {
            return true;
        }
        if (par1EntityLiving instanceof EntityAnt) {
            return true;
        }
        if (par1EntityLiving instanceof EntityButterfly) {
            return true;
        }
        if (par1EntityLiving instanceof EntityMosquito) {
            return true;
        }
        if (par1EntityLiving instanceof Dragonfly) {
            return true;
        }
        if (par1EntityLiving instanceof Firefly) {
            return true;
        }
        if (par1EntityLiving instanceof Cricket) {
            return true;
        }
        if (par1EntityLiving instanceof Cockateil) {
            return true;
        }
        if (par1EntityLiving instanceof Termite) {
            return true;
        }
        if (par1EntityLiving instanceof Ghost) {
            return true;
        }
        if (par1EntityLiving instanceof GhostSkelly) {
            return true;
        }
        if (par1EntityLiving instanceof Elevator) {
            return true;
        }
        return false;
    }

    /**
     * Prince-family mounts: while nobody is riding, never leave noClip/gravity-off on or stay inside blocks.
     */
    public static void enforceDragonMountGroundSafety(EntityLiving entity) {
        if (entity == null || entity.world == null || entity.world.isRemote) {
            return;
        }
        if (!entity.getPassengers().isEmpty()) {
            return;
        }
        entity.noClip = false;
        entity.setNoGravity(false);
        int n = 0;
        while (entity.isEntityInsideOpaqueBlock() && n++ < 48) {
            entity.setPosition(entity.posX, Math.min(252.0, entity.posY + 0.5), entity.posZ);
        }
    }
}

