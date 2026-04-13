package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.item.UltimateArrow;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class UltimateBow extends Item {

    public UltimateBow(int par1) {
        this.maxStackSize = 1;
        this.setMaxDamage(1000);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        applyEnchantments(stack);
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase entity, int count) {
        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) <= 0) {
            applyEnchantments(stack);
        }
    }

    private void applyEnchantments(ItemStack stack) {
        stack.addEnchantment(Enchantments.POWER, 5);
        stack.addEnchantment(Enchantments.FLAME, 3);
        stack.addEnchantment(Enchantments.PUNCH, 2);
        stack.addEnchantment(Enchantments.INFINITY, 1);
    }

    // 🔥 Instant Full Power Shot BUT Keep Bow Animation
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);

        player.setActiveHand(hand); // keeps bow animation

        if (!world.isRemote) {

            UltimateArrow arrow = new UltimateArrow(world, player, 3.0f);

            if (world.rand.nextInt(4) == 1) {
                arrow.setIsCritical(true);
            }

            int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
            if (punchLevel > 0) {
                arrow.setKnockbackStrength(punchLevel);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                arrow.setFire(100);
            }

            arrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;

            world.spawnEntity(arrow);
        }

        world.playSound(
                null,
                player.posX,
                player.posY,
                player.posZ,
                SoundEvents.ENTITY_ARROW_SHOOT,
                player.getSoundCategory(),
                1.0f,
                1.0f / (itemRand.nextFloat() * 0.4f + 1.2f) + 0.5f
        );

        stack.damageItem(1, player);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW; // keeps bow draw animation
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000; // standard bow duration (animation)
    }

    @Override
    public int getItemEnchantability() {
        return 50;
    }
}
