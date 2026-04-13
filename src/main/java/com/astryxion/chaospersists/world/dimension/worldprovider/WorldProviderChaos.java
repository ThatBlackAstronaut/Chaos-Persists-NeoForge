/*
 * Decompiled with CFR 0_125.
 */
package com.astryxion.chaospersists.world.dimension.worldprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.chunkprovider.ChunkProviderChaos;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class WorldProviderChaos
extends WorldProvider {
    private BiomeProviderSingle utopiaBiomeProvider;

    private Biome resolveUtopiaBiome() {
        if (ChaosPersists.UTOPIA_BIOME != null) {
            return ChaosPersists.UTOPIA_BIOME;
        }
        Biome registered = ForgeRegistries.BIOMES.getValue(new ResourceLocation("chaospersists", "utopia"));
        return registered != null ? registered : Biomes.PLAINS;
    }

    @Override
    public net.minecraft.world.DimensionType getDimensionType() {
        return net.minecraftforge.common.DimensionManager.getProviderType(this.getDimension());
    }

    public String getDimensionName() {
        return "Dimension-Utopia";
    }

    public boolean canRespawnHere() {
        return true;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }

    public void registerWorldChunkManager() {
        if (this.utopiaBiomeProvider == null) {
            this.utopiaBiomeProvider = new BiomeProviderSingle(this.resolveUtopiaBiome());
        }
        this.biomeProvider = this.utopiaBiomeProvider;
    }

    @Override
    public net.minecraft.world.biome.BiomeProvider getBiomeProvider() {
        if (this.utopiaBiomeProvider == null) {
            this.utopiaBiomeProvider = new BiomeProviderSingle(this.resolveUtopiaBiome());
        }
        return this.utopiaBiomeProvider;
    }

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
        // No mineshafts / strongholds / scattered features: vanilla structure spawners would bypass Utopia's strict spawn list.
        return new ChunkProviderChaos(this.world, this.world.getSeed(), false);
    }
}
