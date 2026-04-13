package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.entity.AttackSquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSquidZooka extends Item {

    public ItemSquidZooka(int i) {
        this.maxStackSize = 1;
        this.setMaxDamage(100);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);

        // Prevent breaking
        if (stack.getMaxDamage() - stack.getItemDamage() <= 1) {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }

        // Play explosion sound
        world.playSound(
                player,
                player.posX,
                player.posY,
                player.posZ,
                net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE,
                SoundCategory.PLAYERS,
                0.5f,
                0.5f
        );

        if (!world.isRemote) {

            double xzoff = 2.5;
            double yoff = 1.65;

            Entity e = spawnCreature(
                    world,
                    player.posX - xzoff * Math.sin(Math.toRadians(player.rotationYawHead + 15.0f)),
                    player.posY + yoff,
                    player.posZ + xzoff * Math.cos(Math.toRadians(player.rotationYawHead + 15.0f))
            );

            if (e != null) {

                if (e instanceof AttackSquid) {
                    ((AttackSquid) e).setWasShot();
                }

                float f = 3.6f;

                e.motionX = (-MathHelper.sin(player.rotationYaw * 0.017453292F))
                        * MathHelper.cos(player.rotationPitch * 0.017453292F) * f;

                e.motionZ = (MathHelper.cos(player.rotationYaw * 0.017453292F))
                        * MathHelper.cos(player.rotationPitch * 0.017453292F) * f;

                e.motionY = (-MathHelper.sin(player.rotationPitch * 0.017453292F)) * f;

                // Add slight randomness
                e.motionX += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.05;
                e.motionY += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.05;
                e.motionZ += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.05;

                e.velocityChanged = true;
            } else {
                System.out.println("SquidZooka failed to spawn AttackSquid");
            }
        }

        player.swingArm(hand);

        // Apply recoil
        player.addVelocity(
                Math.cos(Math.toRadians(player.rotationYawHead - 90.0f)) * 0.45,
                0.1,
                Math.sin(Math.toRadians(player.rotationYawHead - 90.0f)) * 0.45
        );

        stack.damageItem(1, player);

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    public static Entity spawnCreature(World world, double x, double y, double z) {

        Entity entity = EntityList.createEntityByIDFromName(
                new ResourceLocation("chaospersists", "attack_squid"),
                world
        );

        if (entity != null) {
            entity.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0f, 0.0f);
            world.spawnEntity(entity);
        }

        return entity;
    }
}
