package com.astryxion.chaospersists.world.biome;

import com.astryxion.chaospersists.entity.Alosaurus;
import com.astryxion.chaospersists.entity.TRex;
import com.astryxion.chaospersists.entity.Pointysaurus;
import com.astryxion.chaospersists.entity.Cryolophosaurus;
import com.astryxion.chaospersists.entity.Alien;
import com.astryxion.chaospersists.entity.Baryonyx;
import com.astryxion.chaospersists.entity.Camarasaurus;
import com.astryxion.chaospersists.entity.Cockateil;
import com.astryxion.chaospersists.entity.EntityButterfly;
import com.astryxion.chaospersists.entity.Spyro;
import com.astryxion.chaospersists.entity.GammaMetroid;
import com.astryxion.chaospersists.entity.Nastysaurus;
import com.astryxion.chaospersists.entity.VelocityRaptor;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHills;

// In 1.12.2, the vanilla Extreme Hills biome implementation class is BiomeHills.
public class BiomeMiningDimension extends BiomeHills {
  public BiomeMiningDimension() {
    super(BiomeHills.Type.NORMAL, (new Biome.BiomeProperties("Mining Biome")).setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.2F).setRainfall(0.3F));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(Alosaurus.class, 100, 1, 1));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(TRex.class, 100, 1, 1));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(Pointysaurus.class, 100, 1, 1));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(Cryolophosaurus.class, 100, 1, 1));
    this.spawnableMonsterList.add(new Biome.SpawnListEntry(Alien.class, 100, 1, 1));

    this.spawnableCreatureList.clear();
    this.spawnableCreatureList.add(new Biome.SpawnListEntry(Baryonyx.class, 200, 1, 1));
    this.spawnableCreatureList.add(new Biome.SpawnListEntry(Camarasaurus.class, 250, 1, 1));
    this.spawnableCreatureList.add(new Biome.SpawnListEntry(Cockateil.class, 255, 1, 2));
    this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 100, 1, 1));
    this.spawnableCreatureList.add(new Biome.SpawnListEntry(Spyro.class, 250, 1, 1));
    this.spawnableCreatureList.add(new Biome.SpawnListEntry(GammaMetroid.class, 200, 1, 1));
    this.spawnableCreatureList.add(new Biome.SpawnListEntry(Nastysaurus.class, 200, 1, 1));
    this.spawnableCreatureList.add(new Biome.SpawnListEntry(VelocityRaptor.class, 200, 1, 1));
  }
}
