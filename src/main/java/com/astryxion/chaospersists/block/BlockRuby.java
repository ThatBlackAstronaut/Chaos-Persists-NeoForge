/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockRuby
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BlockRuby
extends Block {
    public BlockRuby() { super(Material.ROCK);
        this.setHardness(4.0f);
        this.setResistance(4.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setLightLevel(0.4f);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return true;
    }

    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if (this == ChaosPersists.MyBlockMobzillaScaleBlock && par5Entity instanceof EntityLivingBase) {
            ((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.STRENGTH, 200, 0));
        }
    }

    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if (this == ChaosPersists.MyBlockMobzillaScaleBlock && par5Entity instanceof EntityLivingBase) {
            ((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(net.minecraft.init.MobEffects.STRENGTH, 200, 0));
        }
    }}

