package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.AntRobot;
import com.astryxion.chaospersists.entity.SpiderRobot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

/**
 * Same behavior as OreSpawn 1.7.10: left-click specific robots to dismantle and drop a damaged kit.
 */
public class ItemWrench extends Item {

    public ItemWrench(int i) {
        this.setCreativeTab(CreativeTabs.TOOLS);
        this.setMaxDamage(100);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity == null || entity instanceof EntityPlayer) {
            return false;
        }

        boolean spider = entity instanceof SpiderRobot && entity.getPassengers().isEmpty();
        boolean ant = entity instanceof AntRobot && entity.getPassengers().isEmpty();

        if (!spider && !ant) {
            return false;
        }

        if (ant) {
            AntRobot e = (AntRobot) entity;
            if (e.getOwned() == 0 && e.getHealth() / e.getMaxHealth() > 0.5f) {
                return false;
            }
        }

        if (!player.world.isRemote) {
            if (ant) {
                AntRobot e = (AntRobot) entity;
                if (e.getOwned() == 0) {
                    e.setOwned();
                }
            }
            EntityLiving e = (EntityLiving) entity;
            float damageTaken = e.getMaxHealth() - e.getHealth();
            e.setDead();
            if (spider) {
                dropKit(player.world, e, ChaosPersists.SpiderRobotKit, damageTaken);
            } else {
                dropKit(player.world, e, ChaosPersists.AntRobotKit, damageTaken);
            }
            stack.damageItem(2, player);
            clearSlotIfBroken(player, stack);
        }

        playDismantleEffects(player.world, entity);
        return true;
    }

    private static void playDismantleEffects(World world, Entity entity) {
        for (int i = 0; i < 8; ++i) {
            float f1 = world.rand.nextFloat() * 3.0f - world.rand.nextFloat() * 3.0f;
            float f2 = 0.25f + world.rand.nextFloat() * 2.0f;
            float f3 = world.rand.nextFloat() * 3.0f - world.rand.nextFloat() * 3.0f;
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                    entity.posX + f1, entity.posY + f2, entity.posZ + f3, 0.0, 0.0, 0.0);
            f1 = world.rand.nextFloat() * 3.0f - world.rand.nextFloat() * 3.0f;
            f2 = 0.25f + world.rand.nextFloat() * 2.0f;
            f3 = world.rand.nextFloat() * 3.0f - world.rand.nextFloat() * 3.0f;
            world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                    entity.posX + f1, entity.posY + f2, entity.posZ + f3, 0.0, 0.0, 0.0);
            f1 = world.rand.nextFloat() * 3.0f - world.rand.nextFloat() * 3.0f;
            f2 = 0.25f + world.rand.nextFloat() * 2.0f;
            f3 = world.rand.nextFloat() * 3.0f - world.rand.nextFloat() * 3.0f;
            world.spawnParticle(EnumParticleTypes.REDSTONE,
                    entity.posX + f1, entity.posY + f2, entity.posZ + f3, 0.0, 0.0, 0.0);
        }
        world.playSound(null, entity.posX, entity.posY, entity.posZ,
                SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5f, 1.5f);
    }

    private static void dropKit(World world, EntityLiving e, Item kit, float damageMeta) {
        if (world.isRemote) {
            return;
        }
        ItemStack drop = new ItemStack(kit, 1, 0);
        drop.setItemDamage((int) damageMeta);
        world.spawnEntity(new EntityItem(world, e.posX, e.posY + 1.0, e.posZ, drop));
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
