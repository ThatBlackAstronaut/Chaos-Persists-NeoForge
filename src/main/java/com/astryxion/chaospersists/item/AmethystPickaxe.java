package com.astryxion.chaospersists.item;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class AmethystPickaxe extends PickaxeItem {
    private static final int WEAPON_DAMAGE = 12;

    public AmethystPickaxe(Tier tier) {
        super(
                tier,
                (int)(WEAPON_DAMAGE - tier.getAttackDamageBonus()),
                -2.8f,
                new Properties().stacksTo(1).durability(2000));
    }

    public String getMaterialName() {
        return "Amethyst";
    }
}
