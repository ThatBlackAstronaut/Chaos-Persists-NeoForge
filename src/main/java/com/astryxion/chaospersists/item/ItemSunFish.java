/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ItemSunFish
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSunFish
extends ItemFood {
    public ItemSunFish(int par2, float par3, boolean par4) {
        super(par2, par3, par4);
        this.setAlwaysEdible();
    }

    public void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        if (!par2World.isRemote && this == ChaosPersists.MySunFish) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.FIRE_RESISTANCE, 6000, 0));
        }
        if (!par2World.isRemote && this == ChaosPersists.MyButterCandy) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.SPEED, 2000, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.JUMP_BOOST, 2000, 0));
        }
        if (!par2World.isRemote && this == ChaosPersists.MyBacon) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.REGENERATION, 2000, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.STRENGTH, 2000, 0));
        }
        if (!par2World.isRemote && this == ChaosPersists.MyCrystalApple) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.REGENERATION, 3000, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.STRENGTH, 3000, 0));
        }
        if (!par2World.isRemote && this == ChaosPersists.MyLove) {
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.REGENERATION, 6000, 3));
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.STRENGTH, 6000, 2));
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.FIRE_RESISTANCE, 6000, 2));
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.RESISTANCE, 6000, 1));
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.SPEED, 5000, 0));
            par3EntityPlayer.addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.JUMP_BOOST, 5000, 0));
        }
    }}

