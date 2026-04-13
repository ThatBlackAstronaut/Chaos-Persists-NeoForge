/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.BiomeGenUtopianPlains
 *  com.astryxion.chaospersists.ChunkProviderChaos4
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.WorldProviderChaos4
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 *  net.minecraft.world.WorldServer
 *  net.minecraft.world.biome.Biome
 *  net.minecraft.world.biome.WorldChunkManager
 *  net.minecraft.world.biome.WorldChunkManagerHell
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.storage.WorldInfo
 *  net.minecraftforge.common.DimensionManager
 */
package com.astryxion.chaospersists.world.dimension.worldprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.chunkprovider.ChunkProviderChaos4;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class WorldProviderChaos4
extends WorldProvider {

    private BiomeProviderSingle dangerBiomeProvider;

    private Biome resolveDangerBiome() {
        if (ChaosPersists.DANGER_BIOME != null) {
            return ChaosPersists.DANGER_BIOME;
        }
        Biome registered = ForgeRegistries.BIOMES.getValue(new ResourceLocation("chaospersists", "danger_dimension"));
        return registered != null ? registered : Biomes.PLAINS;
    }
    @Override
    public net.minecraft.world.DimensionType getDimensionType() {
        return net.minecraftforge.common.DimensionManager.getProviderType(this.getDimension());
    }

    public String getDimensionName() {
        return "Dimension-Islands";
    }

    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        if (this.dangerBiomeProvider == null) {
            this.dangerBiomeProvider = new BiomeProviderSingle(this.resolveDangerBiome());
        }
        return this.dangerBiomeProvider;
    }

    public void registerWorldChunkManager() {
        if (this.dangerBiomeProvider == null) {
            this.dangerBiomeProvider = new BiomeProviderSingle(this.resolveDangerBiome());
        }
        this.biomeProvider = this.dangerBiomeProvider;
    }

    public void setWorldTime(long time) {
        WorldServer ws = DimensionManager.getWorld((int)this.getDimension());
        if (ws != null) {
            WorldInfo w = ws.getWorldInfo();
            if (w != null) {
                if (time % 24000L > 12000L && ws.areAllPlayersAsleep()) {
                    long i = time + 24000L;
                    i -= i % 24000L;
                    for (Integer dimId : DimensionManager.getIDs()) {
                        WorldServer worldServer = DimensionManager.getWorld(dimId);
                        if (worldServer != null) worldServer.setWorldTime(i);
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
        return new ChunkProviderChaos4(this.world, this.world.getSeed(), true);
    }
}

