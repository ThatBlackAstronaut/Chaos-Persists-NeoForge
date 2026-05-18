package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.AntRobot;
import com.astryxion.chaospersists.entity.SpiderRobot;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import org.joml.Vector3f;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

/**
 * Same behavior as OreSpawn 1.7.10: left-click specific robots to dismantle and drop a damaged kit.
 */
public class ItemWrench extends Item {
    private static final DustParticleOptions RED_DUST =
            new DustParticleOptions(new Vector3f(1.0f, 0.0f, 0.0f), 1.0f);

    public ItemWrench(int i) {
        super(new Properties().durability(100));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity == null || entity instanceof Player) {
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

        Level level = player.level();
        if (!level.isClientSide) {
            if (ant) {
                AntRobot e = (AntRobot) entity;
                if (e.getOwned() == 0) {
                    e.setOwned();
                }
            }
            LivingEntity e = (LivingEntity) entity;
            float damageTaken = e.getMaxHealth() - e.getHealth();
            e.discard();
            if (spider) {
                dropKit(level, e, ChaosPersists.SpiderRobotKit, damageTaken);
            } else {
                dropKit(level, e, ChaosPersists.AntRobotKit, damageTaken);
            }
            stack.hurtAndBreak(2, player, p -> p.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            clearSlotIfBroken(player, stack);
        }

        playDismantleEffects(level, entity);
        return true;
    }

    private static void playDismantleEffects(Level level, Entity entity) {
        if (!(level instanceof ServerLevel server)) {
            return;
        }
        for (int i = 0; i < 8; ++i) {
            float f1 = server.random.nextFloat() * 3.0f - server.random.nextFloat() * 3.0f;
            float f2 = 0.25f + server.random.nextFloat() * 2.0f;
            float f3 = server.random.nextFloat() * 3.0f - server.random.nextFloat() * 3.0f;
            server.sendParticles(
                    ParticleTypes.SMOKE,
                    entity.getX() + f1,
                    entity.getY() + f2,
                    entity.getZ() + f3,
                    1,
                    0.0,
                    0.0,
                    0.0,
                    0.0);
            f1 = server.random.nextFloat() * 3.0f - server.random.nextFloat() * 3.0f;
            f2 = 0.25f + server.random.nextFloat() * 2.0f;
            f3 = server.random.nextFloat() * 3.0f - server.random.nextFloat() * 3.0f;
            server.sendParticles(
                    ParticleTypes.EXPLOSION,
                    entity.getX() + f1,
                    entity.getY() + f2,
                    entity.getZ() + f3,
                    1,
                    0.0,
                    0.0,
                    0.0,
                    0.0);
            f1 = server.random.nextFloat() * 3.0f - server.random.nextFloat() * 3.0f;
            f2 = 0.25f + server.random.nextFloat() * 2.0f;
            f3 = server.random.nextFloat() * 3.0f - server.random.nextFloat() * 3.0f;
            server.sendParticles(
                    RED_DUST,
                    entity.getX() + f1,
                    entity.getY() + f2,
                    entity.getZ() + f3,
                    1,
                    0.0,
                    0.0,
                    0.0,
                    0.0);
        }
        server.playSound(
                null,
                entity.getX(),
                entity.getY(),
                entity.getZ(),
                SoundEvents.GENERIC_EXPLODE,
                SoundSource.PLAYERS,
                0.5f,
                1.5f);
    }

    private static void dropKit(Level level, LivingEntity e, Item kit, float damageMeta) {
        if (level.isClientSide) {
            return;
        }
        ItemStack drop = new ItemStack(kit, 1);
        drop.setDamageValue((int) damageMeta);
        level.addFreshEntity(new ItemEntity(level, e.getX(), e.getY() + 1.0, e.getZ(), drop));
    }

    private static void clearSlotIfBroken(Player player, ItemStack stack) {
        if (!stack.isEmpty()) {
            return;
        }
        if (player.getMainHandItem() == stack) {
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        } else if (player.getOffhandItem() == stack) {
            player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
        } else {
            player.getInventory().setItem(player.getInventory().selected, ItemStack.EMPTY);
        }
    }
}
