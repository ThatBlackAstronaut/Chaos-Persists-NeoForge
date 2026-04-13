package com.astryxion.chaospersists.world.dimension.worldprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.chunkprovider.ChunkProviderChaos6;
import net.minecraft.init.Biomes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class WorldProviderChaos6 extends WorldProvider {

    private BiomeProviderSingle chaosBiomeProvider;

    private Biome resolveChaosBiome() {
        if (ChaosPersists.CHAOS_BIOME != null) {
            return ChaosPersists.CHAOS_BIOME;
        }
        Biome registered = ForgeRegistries.BIOMES.getValue(new ResourceLocation("chaospersists", "chaos_dimension"));
        return registered != null ? registered : Biomes.PLAINS;
    }

    @Override
    public net.minecraft.world.DimensionType getDimensionType() {
        return DimensionManager.getProviderType(this.getDimension());
    }

    public String getDimensionName() {
        return "Dimension-Chaos";
    }

    @Override
    public void init() {
        super.init();
        this.hasSkyLight = true;
        if (this.chaosBiomeProvider == null) {
            this.chaosBiomeProvider = new BiomeProviderSingle(this.resolveChaosBiome());
        }
        this.biomeProvider = this.chaosBiomeProvider;
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        if (this.chaosBiomeProvider == null) {
            this.chaosBiomeProvider = new BiomeProviderSingle(this.resolveChaosBiome());
        }
        return this.chaosBiomeProvider;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public boolean canRespawnHere() {
        return true;
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
        return new ChunkProviderChaos6(this.world, this.world.getSeed());
    }
}
