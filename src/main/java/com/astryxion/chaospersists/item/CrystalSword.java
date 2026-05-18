package com.astryxion.chaospersists.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class CrystalSword extends SwordItem {

    public CrystalSword(Tier tier) {
        super(tier, 3, -2.4f, new Properties().stacksTo(1));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 300;
    }
}
