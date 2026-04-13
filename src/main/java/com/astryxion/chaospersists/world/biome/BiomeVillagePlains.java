package com.astryxion.chaospersists.world.biome;

import com.astryxion.chaospersists.core.ChaosPersists;

/**
 * Village Mania dimension biome. OreSpawn 1.7.10 ({@code WorldProviderOreSpawn3}) creates one
 * {@code BiomeGenUtopianPlains(BiomeVillageID)} (flag-gated Utopia base), then calls {@link BiomeGenUtopianPlains#setVillageCreatures()}
 * for robots and extra weighted entries — not a second full copy of the Utopia table pasted twice in one constructor.
 */
public class BiomeVillagePlains extends BiomeGenUtopianPlains {
  public BiomeVillagePlains() {
    super(ChaosPersists.BiomeVillageID, "Villages", 353825, 0.7f, 0.5f);
    this.setVillageCreatures();
  }
}
