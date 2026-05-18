package com.astryxion.chaospersists.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class RubyAxe extends AxeItem {
    private static final float WEAPON_DAMAGE = 12.0f;

    public RubyAxe(Tier tier) {
        super(
                tier,
                (int)(WEAPON_DAMAGE - tier.getAttackDamageBonus()),
                -3.0f,
                new Properties().stacksTo(1).durability(1500));
    }

    public String getMaterialName() {
        return "Ruby";
    }
}
