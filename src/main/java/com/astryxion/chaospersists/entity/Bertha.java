package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class Bertha extends SwordItem {

    public Bertha(Tier tier) {
        super(tier, 3, -2.4f, new Properties().stacksTo(1).durability(9000));
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, Player player) {
        if (this == ChaosPersists.MyRoyal) {
            stack.enchant(Enchantments.UNBREAKING, 5);
        } else if (this != ChaosPersists.MyHammy) {
            stack.enchant(Enchantments.KNOCKBACK, 5);
            stack.enchant(Enchantments.BANE_OF_ARTHROPODS, 1);
            stack.enchant(Enchantments.FIRE_ASPECT, 1);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        this.ensureEnchantments(stack);
    }

    private void ensureEnchantments(ItemStack stack) {
        int lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.KNOCKBACK, stack);
        if (lvl == 0) {
            lvl = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, stack);
        }
        if (lvl <= 0) {
            if (this == ChaosPersists.MyRoyal) {
                stack.enchant(Enchantments.UNBREAKING, 5);
            } else if (this != ChaosPersists.MyHammy) {
                stack.enchant(Enchantments.KNOCKBACK, 5);
                stack.enchant(Enchantments.BANE_OF_ARTHROPODS, 1);
                stack.enchant(Enchantments.FIRE_ASPECT, 1);
            }
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity != null && ChaosPersists.big_bertha_pvp == 0) {
            if (entity instanceof Player
                    || isGirlfriendOrBoyfriend(entity)
                    || (entity instanceof TamableAnimal t && t.isTame())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entityLiving) {
        if (entityLiving instanceof Player p && !entityLiving.level().isClientSide) {
            double xzoff = 2.0;
            double yoff = 1.55;
            BerthaHit lb = new BerthaHit(ChaosPersists.ENTITY_TYPE_BERTHA_HIT.get(), p, p.level());
            lb.moveTo(
                    p.getX() - xzoff * Mth.sin(p.getYHeadRot() * Mth.DEG_TO_RAD),
                    p.getY() + yoff,
                    p.getZ() + xzoff * Mth.cos(p.getYHeadRot() * Mth.DEG_TO_RAD),
                    p.getYHeadRot(),
                    p.getXRot());
            var dm = lb.getDeltaMovement();
            lb.setDeltaMovement(dm.x * 2.0, dm.y * 2.0, dm.z * 2.0);
            if (this == ChaosPersists.MyRoyal) {
                lb.setHitType(2);
            }
            if (this == ChaosPersists.MyHammy) {
                lb.setHitType(3);
            }
            p.level().addFreshEntity(lb);
            stack.hurtAndBreak(
                    1,
                    p,
                    e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return false;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 9000;
    }

    private static boolean isGirlfriendOrBoyfriend(Entity e) {
        String n = e.getClass().getSimpleName();
        return "Girlfriend".equals(n) || "Boyfriend".equals(n);
    }

    @Override
    public void initializeClient(java.util.function.Consumer<net.minecraftforge.client.extensions.common.IClientItemExtensions> consumer) {
        net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer renderer =
                com.astryxion.chaospersists.client.TeisrHandBakedModelWrapper.getCustomRenderer(this);
        if (renderer != null) {
            consumer.accept(new net.minecraftforge.client.extensions.common.IClientItemExtensions() {
                @Override
                public net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return renderer;
                }
            });
        }
    }
}
