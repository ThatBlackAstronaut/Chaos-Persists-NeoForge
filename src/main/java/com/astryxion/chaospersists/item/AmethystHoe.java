package com.astryxion.chaospersists.item;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class AmethystHoe extends HoeItem {
    private static final int WEAPON_DAMAGE = 5;

    public AmethystHoe(Tier tier) {
        super(
                tier,
                (int)(WEAPON_DAMAGE - tier.getAttackDamageBonus()),
                -0.0f,
                new Properties().stacksTo(1).durability(2000));
    }

    public String getMaterialName() {
        return "Amethyst";
    }
}
