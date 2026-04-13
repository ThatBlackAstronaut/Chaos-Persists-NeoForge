/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.UltimateHoe
 *  net.minecraft.block.Block
 *  net.minecraft.block.Block$SoundType
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemHoe
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UltimateHoe
extends ItemHoe {
    public UltimateHoe(Item.ToolMaterial par2) {
        super(par2);
        this.maxStackSize = 1;
        this.setMaxDamage(3000);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(32), 2);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(32), (ItemStack)stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantment.getEnchantmentByID(32), 2);
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        this.onUsingTick(stack, (EntityPlayer)null, 0);
    }

    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
        net.minecraft.util.math.BlockPos pos = new net.minecraft.util.math.BlockPos(par4, par5, par6);
        if (!par2EntityPlayer.canPlayerEdit(pos, net.minecraft.util.EnumFacing.byIndex(par7), par1ItemStack)) {
            return false;
        }
        Block i1 = par3World.getBlockState(pos).getBlock();
        boolean air = par3World.isAirBlock(pos.up());
        if (par7 != 0 && air && (i1 == Blocks.GRASS || i1 == Blocks.DIRT)) {
            Block block = Blocks.FARMLAND;
            net.minecraft.block.state.IBlockState state = block.getDefaultState();
            net.minecraft.block.SoundType st = block.getSoundType(state, par3World, pos, null);
            par3World.playSound((double)((float)par4 + 0.5f), (double)((float)par5 + 0.5f), (double)((float)par6 + 0.5f), st.getBreakSound(), net.minecraft.util.SoundCategory.BLOCKS, (st.getVolume() + 1.0f) / 2.0f, st.getPitch() * 0.8f, false);
            if (par3World.isRemote) {
                return true;
            }
            for (int i = -1; i <= 1; ++i) {
                for (int k = -1; k <= 1; ++k) {
                    for (int j = -1; j <= 1; ++j) {
                        i1 = par3World.getBlockState(new net.minecraft.util.math.BlockPos(par4 + i, par5 + j, par6 + k)).getBlock();
                        air = par3World.isAirBlock(new net.minecraft.util.math.BlockPos(par4 + i, par5 + j + 1, par6 + k));
                        if (!air || i1 != Blocks.GRASS && i1 != Blocks.DIRT) continue;
                        par3World.setBlockState(new net.minecraft.util.math.BlockPos(par4 + i, par5 + j, par6 + k), block.getDefaultState(), 7);
                    }
                }
            }
            par1ItemStack.damageItem(1, (EntityLivingBase)par2EntityPlayer);
            return true;
        }
        return false;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }}

