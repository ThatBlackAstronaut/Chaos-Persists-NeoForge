package com.astryxion.chaospersists.item;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class CrystalPickaxe extends PickaxeItem {

    public CrystalPickaxe(Tier tier) {
        super(tier, 1, -2.8f, new Properties().stacksTo(1));
    }
}
