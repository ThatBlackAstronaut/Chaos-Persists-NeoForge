package com.astryxion.chaospersists.world.biome;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

/**
 * Maps OreSpawn 1.7.10 / Chaos Persists 1.12.2 biome names to 1.20.1 biomes for spawn injection.
 * Uses specific biome lists where 1.12 registered explicit biomes; tags only where the original
 * mod used broad groups (ocean, jungle, savanna, etc.).
 */
public final class LegacyBiomeMatcher {
  private LegacyBiomeMatcher() {}

  public static boolean isModBiome(Holder<Biome> biome) {
    return biome.unwrapKey()
        .map(key -> "chaospersists".equals(key.location().getNamespace()))
        .orElse(false);
  }

  public static boolean matches(Holder<Biome> biome, ResourceLocation legacyGroupId) {
    if (biome == null || legacyGroupId == null) {
      return false;
    }
    if (!"minecraft".equals(legacyGroupId.getNamespace())) {
      return biome.unwrapKey().map(key -> key.location().equals(legacyGroupId)).orElse(false);
    }
    ResourceLocation current = biome.unwrapKey().map(key -> key.location()).orElse(null);
    if (legacyGroupId.equals(current)) {
      return true;
    }
    return switch (legacyGroupId.getPath()) {
      case "ocean" -> biome.is(BiomeTags.IS_OCEAN);
      case "deep_ocean" -> biome.is(BiomeTags.IS_DEEP_OCEAN);
      case "river" -> biome.is(BiomeTags.IS_RIVER);
      case "frozen_river" -> biome.is(Biomes.FROZEN_RIVER);
      case "plains" -> biome.is(Biomes.PLAINS) || biome.is(Biomes.SUNFLOWER_PLAINS);
      case "ice_plains" -> biome.is(Biomes.SNOWY_PLAINS) || biome.is(Biomes.ICE_SPIKES);
      case "desert", "desert_hills" -> biome.is(Biomes.DESERT);
      case "forest", "forest_hills" -> biome.is(Biomes.FOREST);
      case "birch_forest" ->
          biome.is(Biomes.BIRCH_FOREST) || biome.is(Biomes.OLD_GROWTH_BIRCH_FOREST);
      case "birch_forest_hills" -> biome.is(Biomes.BIRCH_FOREST);
      case "jungle" -> biome.is(BiomeTags.IS_JUNGLE);
      case "jungle_hills" -> biome.is(Biomes.SPARSE_JUNGLE);
      case "taiga", "taiga_hills" -> biome.is(Biomes.TAIGA);
      case "cold_taiga", "cold_taiga_hills" -> biome.is(Biomes.SNOWY_TAIGA);
      case "redwood_taiga", "redwood_taiga_hills" ->
          biome.is(Biomes.OLD_GROWTH_PINE_TAIGA);
      case "mega_taiga", "mega_taiga_hills" ->
          biome.is(Biomes.OLD_GROWTH_PINE_TAIGA)
              || biome.is(Biomes.OLD_GROWTH_SPRUCE_TAIGA);
      case "swampland" -> biome.is(Biomes.SWAMP) || biome.is(Biomes.MANGROVE_SWAMP);
      case "beach" -> biome.is(Biomes.BEACH);
      case "stone_beach" -> biome.is(Biomes.STONY_SHORE);
      case "savanna" -> biome.is(BiomeTags.IS_SAVANNA);
      case "savanna_plateau" ->
          biome.is(Biomes.SAVANNA_PLATEAU) || biome.is(Biomes.WINDSWEPT_SAVANNA);
      case "mesa", "mesa_rock", "mesa_plateau", "mesa_plateau_f" ->
          biome.is(Biomes.BADLANDS) || biome.is(Biomes.WOODED_BADLANDS);
      case "mesa_clear_rock" -> biome.is(Biomes.ERODED_BADLANDS);
      case "extreme_hills" ->
          biome.is(Biomes.WINDSWEPT_HILLS)
              || biome.is(Biomes.JAGGED_PEAKS)
              || biome.is(Biomes.FROZEN_PEAKS)
              || biome.is(Biomes.STONY_PEAKS);
      case "extreme_hills_edge" -> biome.is(Biomes.WINDSWEPT_GRAVELLY_HILLS);
      case "extreme_hills_with_trees" -> biome.is(Biomes.WINDSWEPT_FOREST);
      case "roofed_forest" -> biome.is(Biomes.DARK_FOREST);
      case "hell" -> biome.is(BiomeTags.IS_NETHER);
      case "mushroom_island" -> biome.is(Biomes.MUSHROOM_FIELDS);
      default -> false;
    };
  }
}
