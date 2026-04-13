package com.astryxion.chaospersists.world.biome;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.CliffRacer;
import com.astryxion.chaospersists.entity.CloudShark;
import com.astryxion.chaospersists.entity.Cockateil;
import com.astryxion.chaospersists.entity.CreepingHorror;
import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.entity.EnderReaper;
import com.astryxion.chaospersists.entity.EntityButterfly;
import com.astryxion.chaospersists.entity.EntityLunaMoth;
import com.astryxion.chaospersists.entity.Firefly;
import com.astryxion.chaospersists.entity.GoldFish;
import com.astryxion.chaospersists.entity.HerculesBeetle;
import com.astryxion.chaospersists.entity.LeafMonster;
import com.astryxion.chaospersists.entity.LurkingTerror;
import com.astryxion.chaospersists.entity.PitchBlack;
import com.astryxion.chaospersists.entity.Stinky;
import com.astryxion.chaospersists.entity.TerribleTerror;
import net.minecraft.world.biome.Biome;

public class BiomeDangerPlains extends BiomeGenUtopianPlains {
  public BiomeDangerPlains() {
    super(ChaosPersists.BiomeIslandsID, "Islands", 353825, 0.7f, 0.5f);
    this.spawnableCreatureList.clear();
    this.spawnableMonsterList.clear();
    this.spawnableWaterCreatureList.clear();
    this.spawnableCaveCreatureList.clear();

    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 5, 2, 6));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cockateil.class, 4, 1, 2));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityLunaMoth.class, 5, 2, 4));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Firefly.class, 10, 4, 8));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Dragon.class, 1, 1, 2));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Stinky.class, 2, 1, 2));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(CliffRacer.class, 20, 3, 6));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(CloudShark.class, 1, 1, 1));
    this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(GoldFish.class, 5, 2, 4));

    this.spawnableMonsterList.add(new Biome.SpawnListEntry(CreepingHorror.class, 60, 4, 8));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(TerribleTerror.class, 25, 3, 6));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(LurkingTerror.class, 1, 1, 1));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(PitchBlack.class, 15, 3, 6));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(LeafMonster.class, 35, 2, 4));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(EnderReaper.class, 25, 2, 4));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(HerculesBeetle.class, 5, 1, 2));
  }
}
