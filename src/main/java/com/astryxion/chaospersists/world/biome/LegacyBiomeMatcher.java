package com.astryxion.chaospersists.world.biome;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

/**
 * Maps OreSpawn 1.7.10 biome names to 1.20.1 biome tags / holders so spawns registered on
 * {@code ocean} also appear in warm/lukewarm/cold/frozen ocean variants (Alex's Mobs pattern).
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
      case "plains" ->
          biome.is(Biomes.PLAINS)
              || biome.is(Biomes.SUNFLOWER_PLAINS)
              || biome.is(Biomes.MEADOW);
      case "ice_plains" -> biome.is(Biomes.SNOWY_PLAINS) || biome.is(Biomes.ICE_SPIKES);
      case "desert", "desert_hills" -> biome.is(Biomes.DESERT);
      case "forest", "forest_hills", "extreme_hills_with_trees" -> biome.is(BiomeTags.IS_FOREST);
      case "birch_forest", "birch_forest_hills" ->
          biome.is(Biomes.BIRCH_FOREST) || biome.is(Biomes.OLD_GROWTH_BIRCH_FOREST);
      case "jungle", "jungle_hills" -> biome.is(BiomeTags.IS_JUNGLE);
      case "taiga", "taiga_hills", "cold_taiga", "cold_taiga_hills" -> biome.is(BiomeTags.IS_TAIGA);
      case "redwood_taiga", "redwood_taiga_hills", "mega_taiga", "mega_taiga_hills" ->
          biome.is(Biomes.OLD_GROWTH_PINE_TAIGA)
              || biome.is(Biomes.OLD_GROWTH_SPRUCE_TAIGA)
              || biome.is(BiomeTags.IS_TAIGA);
      case "swampland" -> biome.is(Biomes.SWAMP) || biome.is(Biomes.MANGROVE_SWAMP);
      case "beach", "stone_beach" -> biome.is(BiomeTags.IS_BEACH);
      case "savanna", "savanna_plateau" -> biome.is(BiomeTags.IS_SAVANNA);
      case "mesa", "mesa_rock", "mesa_plateau", "mesa_plateau_f" -> biome.is(BiomeTags.IS_BADLANDS);
      case "mesa_clear_rock" -> biome.is(Biomes.ERODED_BADLANDS);
      case "extreme_hills", "extreme_hills_edge" ->
          biome.is(BiomeTags.IS_HILL) || biome.is(BiomeTags.IS_MOUNTAIN);
      case "roofed_forest" -> biome.is(Biomes.DARK_FOREST);
      case "hell" -> biome.is(BiomeTags.IS_NETHER);
      case "mushroom_island" -> biome.is(Biomes.MUSHROOM_FIELDS);
      default -> false;
    };
  }
}
