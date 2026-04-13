/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ExperienceSword
 *  com.astryxion.chaospersists.ItemChaosArmor
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.item.ItemChaosArmor;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ExperienceSword
extends ItemSword {
    private int weaponDamage;
    private final Item.ToolMaterial toolMaterial;
    private World worldObj = null;
    private World worldObjr = null;

    public ExperienceSword(Item.ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        this.toolMaterial = par2EnumToolMaterial;
        this.weaponDamage = 15;
        this.maxStackSize = 1;
        this.setMaxDamage(1400);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(16), 2);
        par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(34), 3);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(16), stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.getEnchantmentByID(16), 2);
            stack.addEnchantment(Enchantment.getEnchantmentByID(34), 3);
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        EntityLivingBase e = null;
        ItemChaosArmor ia = null;
        EntityPlayer p = null;
        this.onUsingTick(stack, (EntityPlayer)null, 0);
        if (!par2World.isRemote) {
            this.worldObj = par2World;
        }
        if (par2World.isRemote) {
            this.worldObjr = par2World;
        }
        if (par2World.rand.nextInt(60) == 1 && par3Entity != null && par3Entity instanceof EntityLivingBase) {
            e = (EntityLivingBase)par3Entity;
            if (e instanceof EntityPlayer) {
                p = (EntityPlayer)e;
            }
            block6 : for (int i = 1; i < 5 && p != null; ++i) {
                Item it;
                net.minecraft.inventory.EntityEquipmentSlot[] armorSlots = new net.minecraft.inventory.EntityEquipmentSlot[]{net.minecraft.inventory.EntityEquipmentSlot.FEET, net.minecraft.inventory.EntityEquipmentSlot.LEGS, net.minecraft.inventory.EntityEquipmentSlot.CHEST, net.minecraft.inventory.EntityEquipmentSlot.HEAD};
                ItemStack is = p.getItemStackFromSlot(armorSlots[i - 1]);
                if (is == null || (it = is.getItem()) == null || !(it instanceof ItemChaosArmor) || (ia = (ItemChaosArmor)it).get_armor_material() != 4) continue;
                switch (ia.get_armor_type()) {
                    case 0: {
                        if (!par2World.isRemote && p != null && par2World.rand.nextInt(10) == 1) {
                            p.addExperience(1);
                        }
                        par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, e.posX, e.posY + 1.5, e.posZ, par2World.rand.nextGaussian(), par2World.rand.nextGaussian(), par2World.rand.nextGaussian());
                        continue block6;
                    }
                    case 1: {
                        if (!par2World.isRemote && p != null && par2World.rand.nextInt(20) == 1) {
                            p.addExperience(1);
                        }
                        par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, e.posX, e.posY + 1.25, e.posZ, par2World.rand.nextGaussian(), par2World.rand.nextGaussian(), par2World.rand.nextGaussian());
                        continue block6;
                    }
                    case 2: {
                        if (!par2World.isRemote && p != null && par2World.rand.nextInt(30) == 1) {
                            p.addExperience(1);
                        }
                        par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, e.posX, e.posY + 0.75, e.posZ, par2World.rand.nextGaussian(), par2World.rand.nextGaussian(), par2World.rand.nextGaussian());
                        continue block6;
                    }
                    case 3: {
                        if (!par2World.isRemote && p != null && par2World.rand.nextInt(40) == 1) {
                            p.addExperience(1);
                        }
                        par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, e.posX, e.posY + 0.25, e.posZ, par2World.rand.nextGaussian(), par2World.rand.nextGaussian(), par2World.rand.nextGaussian());
                        break;
                    }
                }
            }
        }
    }

    public int getDamageVsEntity(Entity par1Entity) {
        return this.weaponDamage;
    }

    public String getMaterialName() {
        return "Emerald";
    }

    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving) {
        float i = 0.0f;
        EntityPlayer p = null;
        java.lang.Object l = null;
        if (par3EntityLiving instanceof EntityPlayer) {
            p = (EntityPlayer)par3EntityLiving;
        }
        if (par2EntityLiving != null && par2EntityLiving instanceof EntityLiving) {
            i = 10.0f;
        }
        if (i > 0.0f && p != null) {
            p.addExperience((int)i);
        }
        if (p != null && (i = (float)(p.experienceLevel / 2)) > 0.0f && par2EntityLiving != null) {
            par2EntityLiving.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)p), i);
        }
        if (this.worldObjr != null && par2EntityLiving != null) {
            int j = 0;
            while ((float)j <= i / 2.0f) {
                this.worldObjr.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, par2EntityLiving.posX, par2EntityLiving.posY + 1.0, par2EntityLiving.posZ, this.worldObjr.rand.nextGaussian(), this.worldObjr.rand.nextGaussian(), this.worldObjr.rand.nextGaussian());
                ++j;
            }
        }
        par1ItemStack.damageItem(1, par3EntityLiving);
        return true;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 3000;
    }}

