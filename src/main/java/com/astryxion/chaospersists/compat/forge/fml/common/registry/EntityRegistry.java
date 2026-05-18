package com.astryxion.chaospersists.compat.forge.fml.common.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import com.astryxion.chaospersists.compat.minecraft.world.biome.Biome;

/**
 * Legacy 1.12 {@code EntityRegistry.registerModEntity} — entities register via
 * {@code DeferredRegister} in 1.20.1; calls preserved for 1:1 init order in {@code ChaosPersists}.
 */
public final class EntityRegistry {

  private EntityRegistry() {}

  public static void registerModEntity(
      ResourceLocation id,
      Class<?> entityClass,
      String name,
      int id2,
      Object mod,
      int trackingRange,
      int updateFrequency,
      boolean sendsVelocityUpdates) {
    // No-op: ENTITY_TYPES DeferredRegister owns registration.
  }

  public static void addSpawn(
      Class<?> entityClass,
      int weight,
      int minGroup,
      int maxGroup,
      MobCategory category,
      Biome[] biomes) {
    // No-op: biome spawns are data-driven in 1.20.1; calls preserve 1.12 init order.
  }
}
