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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class ItemLaserBall
extends Item {
    public ItemLaserBall(int i) {
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }
        SoundEvent firework = SoundEvent.REGISTRY.getObject(new ResourceLocation("minecraft:entity.firework.launch"));
        if (firework != null) {
            world.playSound(null, player.posX, player.posY, player.posZ,
                    firework, SoundCategory.PLAYERS, 3.0F, 1.0F);
        }
        if (!world.isRemote) {
            LaserBall e = new LaserBall(world, (EntityLivingBase) player);
            e.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity((Entity) e);
        }
        player.swingArm(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
