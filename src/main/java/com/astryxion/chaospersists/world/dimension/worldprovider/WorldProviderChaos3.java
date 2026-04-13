package com.astryxion.chaospersists.world.dimension.worldprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.chunkprovider.ChunkProviderChaos3;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class WorldProviderChaos3 extends net.minecraft.world.WorldProvider {

    private BiomeProviderSingle villageBiomeProvider;

    private Biome resolveVillageBiome() {
        if (ChaosPersists.VILLAGE_BIOME != null) {
            return ChaosPersists.VILLAGE_BIOME;
        }
        Biome registered = ForgeRegistries.BIOMES.getValue(new ResourceLocation("chaospersists", "village_dimension"));
        return registered != null ? registered : Biomes.PLAINS;
    }

    @Override
    public void init() {
        super.init();
        this.hasSkyLight = true;
        if (this.villageBiomeProvider == null) {
            this.villageBiomeProvider = new BiomeProviderSingle(this.resolveVillageBiome());
        }
        this.biomeProvider = this.villageBiomeProvider;
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        if (this.villageBiomeProvider == null) {
            this.villageBiomeProvider = new BiomeProviderSingle(this.resolveVillageBiome());
        }
        return this.villageBiomeProvider;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public net.minecraft.world.DimensionType getDimensionType() {
        return DimensionManager.getProviderType(this.getDimension());
    }

    public String getDimensionName() {
        return "Dimension-VillageMania";
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
        return new ChunkProviderChaos3(this.world, this.world.getSeed(), true);
    }
}
