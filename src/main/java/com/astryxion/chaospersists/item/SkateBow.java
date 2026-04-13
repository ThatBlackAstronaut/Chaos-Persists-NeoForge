package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
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

public class SkateBow extends Item {

    public SkateBow(int par1) {
        this.maxStackSize = 1;
        this.setMaxDamage(300);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft) {

        if (!(entity instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) entity;

        boolean creative = player.capabilities.isCreativeMode;

        if (!creative && countArrows(player) <= 0) {
            return;
        }

        int charge = this.getMaxItemUseDuration(stack) - timeLeft;
        // Same pull curve as ItemBow: f in [0, 1], then vanilla passes f * 3.0F to EntityArrow.shoot.
        float pull = charge / 20.0F;
        pull = (pull * pull + pull * 2.0F) / 3.0F;
        if (pull < 0.1F) return;
        if (pull > 1.0F) pull = 1.0F;
        float arrowSpeed = pull * 3.0F;

        IrukandjiArrow arrow = new IrukandjiArrow(world, player, arrowSpeed);
        arrow.pickupStatus = EntityArrow.PickupStatus.ALLOWED;

        if (world.rand.nextInt(20) == 1) {
            arrow.setIsCritical(true);
        }

        int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
        if (punchLevel > 0) {
            arrow.setKnockbackStrength(punchLevel);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
            arrow.setFire(100);
        }

        stack.damageItem(1, player);

        world.playSound(
                null,
                player.posX,
                player.posY,
                player.posZ,
                SoundEvents.ENTITY_ARROW_SHOOT,
                player.getSoundCategory(),
                1.0F,
                1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F
        );

        if (!creative) {
            consumeOneArrow(player);
        }

        if (!world.isRemote) {
            world.spawnEntity(arrow);
        }
    }

    @Override
    public int getItemEnchantability() {
        return 50;
    }

    private int countArrows(EntityPlayer player) {
        int count = 0;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack s = player.inventory.getStackInSlot(i);
            if (!s.isEmpty() && s.getItem() == ChaosPersists.MyIrukandjiArrow) {
                count += s.getCount();
            }
        }
        return count;
    }

    private void consumeOneArrow(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack s = player.inventory.getStackInSlot(i);
            if (!s.isEmpty() && s.getItem() == ChaosPersists.MyIrukandjiArrow) {
                s.shrink(1);
                break;
            }
        }
    }
}
