/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.EntityAnt
 *  com.astryxion.chaospersists.OreSalt
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.world.ore;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.entity.EntityAnt;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class OreSalt
extends Block {
    public OreSalt() { super(Material.ROCK);
        this.setHardness(5.0f);
        this.setResistance(2.0f);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if (par5Entity instanceof EntityAnt) {
            par5Entity.attackEntityFrom(DamageSource.CACTUS, 5.0f);
        }
    }

    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        if (par5Entity instanceof EntityAnt) {
            par5Entity.attackEntityFrom(DamageSource.CACTUS, 5.0f);
        }
    }}

