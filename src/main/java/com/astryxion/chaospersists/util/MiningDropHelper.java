package com.astryxion.chaospersists.util;

import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public final class MiningDropHelper {

  private MiningDropHelper() {}

  public static List<ItemStack> selfDrops(Block block, LootParams.Builder builder) {
    ItemStack tool = builder.getOptionalParameter(LootContextParams.TOOL);
    if (tool != null && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, tool) > 0) {
      return Collections.singletonList(new ItemStack(block));
    }
    return Collections.singletonList(new ItemStack(block.asItem()));
  }
}
