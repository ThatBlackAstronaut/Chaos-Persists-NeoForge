package com.astryxion.chaospersists.compat.forge.common;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Legacy 1.12 registry iteration and {@code getRegistryName()} for Chaos Persists. */
public final class RegistryCompat {

  private RegistryCompat() {}

  public static final class BlockRegistry {
    private BlockRegistry() {}

    public static Iterable<Block> iterable() {
      return BuiltInRegistries.BLOCK;
    }
  }

  public static final class ItemRegistry {
    private ItemRegistry() {}

    public static Iterable<Item> iterable() {
      return BuiltInRegistries.ITEM;
    }
  }

  public static final BlockRegistry REGISTRY = new BlockRegistry();

  public static ResourceLocation getRegistryName(Block block) {
    return block == null ? null : BuiltInRegistries.BLOCK.getKey(block);
  }

  public static ResourceLocation getRegistryName(Item item) {
    return item == null ? null : BuiltInRegistries.ITEM.getKey(item);
  }

  /** Legacy 1.12 {@link Block#getStateFromMeta(int)} for worldgen fast paths. */
  public static BlockState getStateFromMeta(Block block, int meta) {
    if (block == null || block == Blocks.AIR) {
      return Blocks.AIR.defaultBlockState();
    }
    List<BlockState> states = block.getStateDefinition().getPossibleStates();
    if (states.isEmpty()) {
      return Blocks.AIR.defaultBlockState();
    }
    int idx = meta & 15;
    if (idx >= 0 && idx < states.size()) {
      return states.get(idx);
    }
    return block.defaultBlockState();
  }
}
