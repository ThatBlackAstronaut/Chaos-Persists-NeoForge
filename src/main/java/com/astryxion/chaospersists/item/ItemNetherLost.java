package com.astryxion.chaospersists.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Display name: Nether Tracker. Shapeless: nether star + netherrack.
 * <p>
 * While held (main or off hand) in the Nether standing on netherrack, each tick replaces the
 * netherrack block under your feet with quartz block. Also keeps Sharpness II on the stack
 * (matches OreSpawn 1.7.10; a previous 1.12 port mistakenly used Fire Aspect).
 */
public class ItemNetherLost extends Item {

    public ItemNetherLost(int par1) {
        this.setMaxStackSize(1);
        this.setMaxDamage(3000);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantments.SHARPNESS, 2);
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.SHARPNESS, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.SHARPNESS, 2);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        this.onUsingTick(stack, null, 0);
        if (world == null || entity == null || !(entity instanceof EntityPlayer)) {
            return;
        }
        EntityPlayer player = (EntityPlayer) entity;
        boolean holding = player.getHeldItemMainhand() == stack || player.getHeldItemOffhand() == stack;
        if (!holding) {
            return;
        }
        if (world.provider.getDimension() != -1) {
            return;
        }
        BlockPos below = new BlockPos((int) player.posX, (int) player.posY - 1, (int) player.posZ);
        if (world.getBlockState(below).getBlock() != Blocks.NETHERRACK) {
            return;
        }
        if (!world.isRemote) {
            world.setBlockState(below, Blocks.QUARTZ_BLOCK.getDefaultState(), 3);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 3000;
    }
}
