package com.astryxion.chaospersists.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemThunderStaff extends Item {

    private int ticker = 50;

    public ItemThunderStaff(int i) {
        this.maxStackSize = 1;
        this.setMaxDamage(50);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);

        // Prevent use if almost broken (same logic)
        if (stack.getMaxDamage() - stack.getItemDamage() <= 1) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        double xzoff = 1.0;
        double yoff = 1.55;

        if (!world.isRemote) {
            ThunderBolt lb = new ThunderBolt(world, (EntityLivingBase) player);

            lb.setLocationAndAngles(
                    player.posX - xzoff * Math.sin(Math.toRadians(player.rotationYawHead + 45.0f)),
                    player.posY + yoff,
                    player.posZ + xzoff * Math.cos(Math.toRadians(player.rotationYawHead + 45.0f)),
                    player.rotationYawHead,
                    player.rotationPitch
            );

            lb.motionX *= 3.0;
            lb.motionY *= 3.0;
            lb.motionZ *= 3.0;

            world.spawnEntity(lb);
        }

        // Swing animation (correct hand)
        player.swingArm(hand);

        // Player knockback boost (same math)
        player.addVelocity(
                Math.cos(Math.toRadians(player.rotationYawHead - 90.0f)) * 0.5,
                0.15,
                Math.sin(Math.toRadians(player.rotationYawHead - 90.0f)) * 0.5
        );

        // Damage item
        stack.damageItem(1, player);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {

        if (world.isRaining() && world.isThundering()) {

            if (this.ticker > 0) {
                --this.ticker;
            }

            if (this.ticker <= 0 && stack.getItemDamage() > 0) {
                stack.setItemDamage(stack.getItemDamage() - 1);
                this.ticker = 50;
            }
        }
    }

    public String getMaterialName() {
        return "Unknown";
    }
}
