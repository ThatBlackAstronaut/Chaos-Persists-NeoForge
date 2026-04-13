package com.astryxion.chaospersists.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class UltimateFishingRod extends ItemFishingRod {

    public UltimateFishingRod(int par1) {
        super();
        this.setMaxDamage(3000);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par1ItemStack.addEnchantment(Enchantments.UNBREAKING, 2);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack);
        if (lvl <= 0) {
            stack.addEnchantment(Enchantments.UNBREAKING, 2);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (player.fishEntity != null) {
            int dmg = player.fishEntity.handleHookRetraction();
            stack.damageItem(dmg, (EntityLivingBase) player);
            player.swingArm(hand);
        } else {
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT,
                    player.getSoundCategory(), 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
            if (!world.isRemote) {
                world.spawnEntity(new UltimateFishHook(world, player));
            }
            player.swingArm(hand);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}

