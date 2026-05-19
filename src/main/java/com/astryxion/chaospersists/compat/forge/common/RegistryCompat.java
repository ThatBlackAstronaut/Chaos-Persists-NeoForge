package com.astryxion.chaospersists.compat.forge.common;

import com.astryxion.chaospersists.block.ChaosDirectionalTorchBlock;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

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

  /**
   * 1.12 {@code BlockTorch} metadata: 1=east, 2=west, 3=south, 4=north, 5=up. Structure code passes
   * {@code 0} everywhere, which behaved as a floor torch in practice.
   */
  public static Direction legacyTorchMetaToFacing(int meta) {
    return switch (meta & 7) {
      case 1 -> Direction.EAST;
      case 2 -> Direction.WEST;
      case 3 -> Direction.SOUTH;
      case 4 -> Direction.NORTH;
      case 5, 0 -> Direction.UP;
      default -> Direction.UP;
    };
  }

  /** Legacy 1.12 {@link Block#getStateFromMeta(int)} for worldgen fast paths. */
  public static BlockState getStateFromMeta(Block block, int meta) {
    if (block == null || block == Blocks.AIR) {
      return Blocks.AIR.defaultBlockState();
    }

    if (block instanceof ChaosDirectionalTorchBlock) {
      return block.defaultBlockState()
          .setValue(ChaosDirectionalTorchBlock.FACING, legacyTorchMetaToFacing(meta));
    }

    if (block == Blocks.GRASS_BLOCK || block == Blocks.MYCELIUM || block == Blocks.PODZOL) {
      return block.defaultBlockState().setValue(BlockStateProperties.SNOWY, false);
    }

    if (block == Blocks.TORCH) {
      Direction facing = legacyTorchMetaToFacing(meta);
      if (facing.getAxis().isHorizontal()) {
        return Blocks.WALL_TORCH
            .defaultBlockState()
            .setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
      }
      return Blocks.TORCH.defaultBlockState();
    }

    if (block == Blocks.REDSTONE_TORCH) {
      Direction facing = legacyTorchMetaToFacing(meta);
      if (facing.getAxis().isHorizontal()) {
        return Blocks.REDSTONE_WALL_TORCH
            .defaultBlockState()
            .setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
      }
      return Blocks.REDSTONE_TORCH.defaultBlockState();
    }

    if (block == Blocks.WALL_TORCH || block == Blocks.REDSTONE_WALL_TORCH) {
      Direction facing = legacyTorchMetaToFacing(meta);
      if (facing.getAxis().isVertical()) {
        facing = Direction.NORTH;
      }
      return block.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
    }

    if (meta == 0) {
      return block.defaultBlockState();
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
