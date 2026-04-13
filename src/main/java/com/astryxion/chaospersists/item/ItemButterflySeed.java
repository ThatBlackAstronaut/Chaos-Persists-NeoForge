package com.astryxion.chaospersists.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSeeds;

public class ItemButterflySeed extends ItemSeeds {

    public ItemButterflySeed(Block cropBlock, Block soilBlock) {
        super(cropBlock, soilBlock);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }
}
