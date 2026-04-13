package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ItemRandomDungeon extends Item {

    Random rand = ChaosPersists.ChaosRand;

    public ItemRandomDungeon(int i) {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        stack.addEnchantment(Enchantments.FORTUNE, 2);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.FORTUNE, 2);
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        Block clicked = world.getBlockState(pos).getBlock();
        if (clicked != Blocks.STONE && clicked != Blocks.COBBLESTONE && clicked != Blocks.GRASS && clicked != Blocks.DIRT) {
            return EnumActionResult.FAIL;
        }
        if (pos.getY() < 40) {
            return EnumActionResult.FAIL;
        }
        if (!world.isRemote) {
            world.setBlockState(pos.up(), ChaosPersists.MyDungeonSpawnerBlock.getDefaultState(), 2);
        }
        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }
        return EnumActionResult.SUCCESS;
    }
}
