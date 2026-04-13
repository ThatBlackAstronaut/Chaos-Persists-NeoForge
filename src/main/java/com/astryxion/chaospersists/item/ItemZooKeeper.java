package com.astryxion.chaospersists.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;

/**
 * OreSpawn 1.7.10 "ZooKeeper Shard": left-click a mob with it to run {@link EntityLiving#enablePersistence()}
 * (mob no longer despawns). Same particles/sound as the original; uses one durability (max damage 1, takes 2 "damage steps").
 */
public class ItemZooKeeper extends Item {

    public ItemZooKeeper(int i) {
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setMaxDamage(1);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity == null) {
            return false;
        }

        playEffects(player, entity);

        if (!(entity instanceof EntityLiving)) {
            return false;
        }

        EntityLiving e = (EntityLiving) entity;
        if (!player.world.isRemote) {
            e.enablePersistence();
            stack.damageItem(2, player);
            clearSlotIfBroken(player, stack);
        }

        return true;
    }

    private static void playEffects(EntityPlayer player, Entity entity) {
        for (int i = 0; i < 8; ++i) {
            float f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            float f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
            float f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            player.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                    entity.posX + f1, entity.posY + f2, entity.posZ + f3, 0.0, 0.0, 0.0);
            f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
            f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            player.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                    entity.posX + f1, entity.posY + f2, entity.posZ + f3, 0.0, 0.0, 0.0);
            f1 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            f2 = 0.25f + player.world.rand.nextFloat() * 2.0f;
            f3 = player.world.rand.nextFloat() * 3.0f - player.world.rand.nextFloat() * 3.0f;
            player.world.spawnParticle(EnumParticleTypes.REDSTONE,
                    entity.posX + f1, entity.posY + f2, entity.posZ + f3, 0.0, 0.0, 0.0);
        }
        player.world.playSound(null, entity.posX, entity.posY, entity.posZ,
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f, 1.5f);
    }

    private static void clearSlotIfBroken(EntityPlayer player, ItemStack stack) {
        if (stack.getCount() > 0) {
            return;
        }
        if (player.getHeldItemMainhand() == stack) {
            player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
        } else if (player.getHeldItemOffhand() == stack) {
            player.setHeldItem(EnumHand.OFF_HAND, ItemStack.EMPTY);
        } else {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
        }
    }
}
