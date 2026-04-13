package com.astryxion.chaospersists.world.dimension.worldprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.chunkprovider.ChunkProviderChaos2;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

public class WorldProviderChaos2 extends WorldProvider {
  private final BiomeProviderSingle miningBiomeProvider = new BiomeProviderSingle(ChaosPersists.MINING_BIOME);

  @Override
  public DimensionType getDimensionType() {
    return DimensionManager.getProviderType(this.getDimension());
  }

  public boolean canRespawnHere() {
    return true;
  }

  public void registerWorldChunkManager() {
    this.biomeProvider = this.miningBiomeProvider;
  }

  @Override
  public net.minecraft.world.biome.BiomeProvider getBiomeProvider() {
    return this.miningBiomeProvider;
  }

  @Override
  public IChunkGenerator createChunkGenerator() {
    return new ChunkProviderChaos2(this.world, this.world.getSeed(), this.getBiomeProvider());
  }

  public boolean isSurfaceWorld() {
    return true;
  }

  public void setWorldTime(long time) {
    WorldServer ws = DimensionManager.getWorld(this.getDimension());
    if (ws != null) {
      WorldInfo wi = ws.getWorldInfo();
      if (wi != null && time % 24000L > 12000L && ws.areAllPlayersAsleep()) {
        long newTime = time + 24000L;
        newTime -= newTime % 24000L;
        for (Integer dimId : DimensionManager.getIDs()) {
          WorldServer worldServer = DimensionManager.getWorld(dimId);
          if (worldServer != null) worldServer.setWorldTime(newTime);
        }
        return;
      }
    }
    super.setWorldTime(time);
  }
}
