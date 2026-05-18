package com.astryxion.chaospersists.item;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;

public class AmethystShovel extends ShovelItem {
    private static final float WEAPON_DAMAGE = 5.0f;

    public AmethystShovel(Tier tier) {
        super(
                tier,
                (int)(WEAPON_DAMAGE - tier.getAttackDamageBonus()),
                -3.0f,
                new Properties().stacksTo(1).durability(2000));
    }

    public String getMaterialName() {
        return "Amethyst";
    }
}
