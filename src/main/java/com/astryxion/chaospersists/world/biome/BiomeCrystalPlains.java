package com.astryxion.chaospersists.world.biome;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.Cockateil;
import com.astryxion.chaospersists.entity.Crab;
import com.astryxion.chaospersists.entity.CrystalCow;
import com.astryxion.chaospersists.entity.DungeonBeast;
import com.astryxion.chaospersists.entity.EntityButterfly;
import com.astryxion.chaospersists.entity.EntityLunaMoth;
import com.astryxion.chaospersists.entity.Fairy;
import com.astryxion.chaospersists.entity.Flounder;
import com.astryxion.chaospersists.entity.Frog;
import com.astryxion.chaospersists.entity.Irukandji;
import com.astryxion.chaospersists.entity.Mantis;
import com.astryxion.chaospersists.entity.Peacock;
import com.astryxion.chaospersists.entity.Rat;
import com.astryxion.chaospersists.entity.Rotator;
import com.astryxion.chaospersists.entity.Skate;
import com.astryxion.chaospersists.entity.Urchin;
import com.astryxion.chaospersists.entity.Vortex;
import com.astryxion.chaospersists.entity.Whale;
import net.minecraft.world.biome.Biome;

public class BiomeCrystalPlains extends BiomeGenUtopianPlains {
  public BiomeCrystalPlains() {
    super(ChaosPersists.BiomeCrystalID, "Crystal", 353825, 0.7f, 0.5f, 0.1f, 0.5f);
    this.spawnableCreatureList.clear();
    this.spawnableMonsterList.clear();
    this.spawnableWaterCreatureList.clear();
    this.spawnableCaveCreatureList.clear();

    this.spawnableCreatureList.add(new Biome.SpawnListEntry(CrystalCow.class, 1, 1, 4));

    this.spawnableMonsterList.add(new Biome.SpawnListEntry(Rotator.class, 4, 1, 2));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(Vortex.class, 3, 1, 2));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(Urchin.class, 15, 2, 4));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(DungeonBeast.class, 30, 4, 6));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(Rat.class, 40, 4, 6));

    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Fairy.class, 10, 4, 8));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Peacock.class, 5, 4, 8));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Mantis.class, 1, 1, 1));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 10, 2, 4));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cockateil.class, 4, 1, 2));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityLunaMoth.class, 4, 1, 2));

    this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Whale.class, 1, 1, 2));
    this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Crab.class, 1, 1, 2));
    this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Flounder.class, 5, 6, 8));
    this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Irukandji.class, 4, 2, 3));
    this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Skate.class, 2, 3, 6));
    this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Frog.class, 1, 3, 5));

    this.decorator.flowersPerChunk = -999;
    this.decorator.grassPerChunk = -999;
    this.decorator.treesPerChunk = -999;
    this.decorator.bigMushroomsPerChunk = -999;
    this.decorator.mushroomsPerChunk = -999;
    this.decorator.reedsPerChunk = -999;
  }
}
