package com.astryxion.chaospersists.item;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class CrystalAxe extends AxeItem {

    public CrystalAxe(Tier tier) {
        super(tier, 8.0f + tier.getAttackDamageBonus(), -3.0f, new Properties().stacksTo(1));
    }
}
