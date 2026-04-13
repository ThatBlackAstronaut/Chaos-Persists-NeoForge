package com.astryxion.chaospersists.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.World;

public class ItemCreeperLauncher extends Item {

    public ItemCreeperLauncher(int i) {
        this.setCreativeTab(CreativeTabs.REDSTONE);
        this.setMaxStackSize(16);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);

        // Play launch sound
        world.playSound(null,
                player.posX,
                player.posY,
                player.posZ,
                SoundEvents.ENTITY_FIREWORK_LAUNCH,
                SoundCategory.PLAYERS,
                1.0f,
                1.0f);

        if (!world.isRemote) {

            EntityCreeper creeper = new EntityCreeper(world);

            // Spawn slightly in front of player
            creeper.setPosition(
                    player.posX,
                    player.posY + player.getEyeHeight(),
                    player.posZ
            );

            // Make it shoot forward like a projectile
            creeper.motionX = -Math.sin(Math.toRadians(player.rotationYaw)) * Math.cos(Math.toRadians(player.rotationPitch)) * 1.5;
            creeper.motionZ =  Math.cos(Math.toRadians(player.rotationYaw)) * Math.cos(Math.toRadians(player.rotationPitch)) * 1.5;
            creeper.motionY = -Math.sin(Math.toRadians(player.rotationPitch)) * 1.5;

            world.spawnEntity(creeper);
        }

        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
