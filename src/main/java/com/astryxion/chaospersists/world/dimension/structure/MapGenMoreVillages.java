/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.MapGenMoreVillages
 *  net.minecraft.world.World
 *  net.minecraft.world.biome.WorldChunkManager
 *  net.minecraft.world.gen.structure.MapGenVillage
 */
package com.astryxion.chaospersists.world.dimension.structure;

import com.astryxion.chaospersists.world.dimension.worldprovider.WorldProviderChaos3;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenVillage;

public class MapGenMoreVillages
extends MapGenVillage {
    private int field_82665_g = 9;
    private int field_82666_h = 7;

    protected boolean canSpawnStructureAtCoords(int par1, int par2) {
        int var3 = par1;
        int var4 = par2;
        if (par1 < 0) {
            par1 -= this.field_82665_g - 1;
        }
        if (par2 < 0) {
            par2 -= this.field_82665_g - 1;
        }
        int var5 = par1 / this.field_82665_g;
        int var6 = par2 / this.field_82665_g;
        Random var7 = this.world.setRandomSeed(var5, var6, 10387312);
        var5 *= this.field_82665_g;
        var6 *= this.field_82665_g;
        if (var3 == (var5 += var7.nextInt(this.field_82665_g - this.field_82666_h)) && var4 == (var6 += var7.nextInt(this.field_82665_g - this.field_82666_h))) {
            if (this.world.provider instanceof WorldProviderChaos3) {
                return true;
            }
            List<Biome> villageBiomes = Arrays.asList(Biomes.PLAINS, Biomes.DESERT, Biomes.SAVANNA, Biomes.TAIGA);
            boolean var8 = this.world.getBiomeProvider().areBiomesViable(var3 * 16 + 8, 0, var4 * 16 + 8, villageBiomes);
            return var8;
        }
        return false;
    }
}

