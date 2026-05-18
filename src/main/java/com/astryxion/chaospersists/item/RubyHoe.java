package com.astryxion.chaospersists.item;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;

public class RubyHoe extends HoeItem {
    private static final int WEAPON_DAMAGE = 5;

    public RubyHoe(Tier tier) {
        super(
                tier,
                (int)(WEAPON_DAMAGE - tier.getAttackDamageBonus()),
                -0.0f,
                new Properties().stacksTo(1).durability(1500));
    }

    public String getMaterialName() {
        return "Ruby";
    }
}
