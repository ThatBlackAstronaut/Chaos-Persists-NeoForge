package com.astryxion.chaospersists.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemRayGun extends Item {

    public ItemRayGun(int i) {
        this.maxStackSize = 1;
        this.setMaxDamage(50);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);

        // Prevent use if nearly broken (same logic)
        if (stack.getMaxDamage() - stack.getItemDamage() <= 1) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        // Play sound (same sound as original)
        SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation("minecraft", "entity.firework.launch"));
        if (sound != null) {
            world.playSound(
                    player,
                    player.posX,
                    player.posY,
                    player.posZ,
                    sound,
                    SoundCategory.PLAYERS,
                    3.5f,
                    0.5f
            );
        }

        if (!world.isRemote) {
            LaserBall lb = new LaserBall(world, player);
            lb.setSpecial();
            Vec3d look = player.getLookVec();
            double spawnDist = 0.65;
            double px = player.posX + look.x * spawnDist;
            double py = player.posY + player.getEyeHeight() + look.y * spawnDist;
            double pz = player.posZ + look.z * spawnDist;
            lb.setPosition(px, py, pz);
            double speed = 1.85;
            lb.motionX = look.x * speed;
            lb.motionY = look.y * speed;
            lb.motionZ = look.z * speed;
            world.spawnEntity(lb);
        }

        // Swing animation
        player.swingArm(hand);

        // Strong recoil boost (same math)
        player.addVelocity(
                Math.cos(Math.toRadians(player.rotationYaw - 90.0f)) * 1.5,
                0.3,
                Math.sin(Math.toRadians(player.rotationYaw - 90.0f)) * 1.5
        );

        // Damage item
        stack.damageItem(1, player);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public String getMaterialName() {
        return "Unknown";
    }
}
