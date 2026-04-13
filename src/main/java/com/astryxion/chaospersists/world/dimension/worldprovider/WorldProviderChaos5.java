package com.astryxion.chaospersists.world.dimension.worldprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.chunkprovider.ChunkProviderChaos5;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class WorldProviderChaos5 extends net.minecraft.world.WorldProvider {

    private BiomeProviderSingle crystalBiomeProvider;

    private Biome resolveCrystalBiome() {
        if (ChaosPersists.CRYSTAL_BIOME != null) {
            return ChaosPersists.CRYSTAL_BIOME;
        }
        Biome registered = ForgeRegistries.BIOMES.getValue(new ResourceLocation("chaospersists", "crystal_dimension"));
        return registered != null ? registered : Biomes.PLAINS;
    }
    @Override
    public net.minecraft.world.DimensionType getDimensionType() {
        return DimensionManager.getProviderType(this.getDimension());
    }

    public String getDimensionName() {
        return "Dimension-Crystal";
    }

    @Override
    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        if (this.crystalBiomeProvider == null) {
            this.crystalBiomeProvider = new BiomeProviderSingle(this.resolveCrystalBiome());
        }
        return this.crystalBiomeProvider;
    }

    public void registerWorldChunkManager() {
        if (this.crystalBiomeProvider == null) {
            this.crystalBiomeProvider = new BiomeProviderSingle(this.resolveCrystalBiome());
        }
        this.biomeProvider = this.crystalBiomeProvider;
    }

    @Override
    public void setWorldTime(long time) {
        WorldServer ws = DimensionManager.getWorld(this.getDimension());
        if (ws != null) {
            WorldInfo w = ws.getWorldInfo();
            if (w != null) {
                if (time % 24000L > 12000L && ws.areAllPlayersAsleep()) {
                    long i = time + 24000L;
                    i -= i % 24000L;
                    for (Integer dimId : DimensionManager.getIDs()) {
                        WorldServer worldServer = DimensionManager.getWorld(dimId);
                        if (worldServer != null) {
                            worldServer.setWorldTime(i);
                        }
                    }
                } else {
                    super.setWorldTime(time);
                }
            } else {
                super.setWorldTime(time);
            }
        } else {
            super.setWorldTime(time);
        }
    }

    @Override
    public net.minecraft.world.gen.IChunkGenerator createChunkGenerator() {
        return new ChunkProviderChaos5(this.world, this.world.getSeed(), true);
    }
}
