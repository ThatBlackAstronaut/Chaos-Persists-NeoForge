package com.astryxion.chaospersists.world.dimension.chunkprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.structure.MapGenMoreVillages;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderChaos3 implements IChunkGenerator {

    private final Random rand;
    private NoiseGeneratorOctaves field_147431_j;
    private NoiseGeneratorOctaves field_147432_k;
    private NoiseGeneratorOctaves field_147429_l;
    private NoiseGeneratorPerlin field_147430_m;
    public NoiseGeneratorOctaves noiseGen5;
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private final World world;
    private final boolean mapFeaturesEnabled;
    private final WorldType field_147435_p;
    private final double[] field_147434_q;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    private MapGenBase caveGenerator = new MapGenCaves();
    private MapGenStronghold strongholdGenerator = new MapGenStronghold();
    private MapGenMoreVillages villageGenerator = new MapGenMoreVillages();
    private MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
    private MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
    private MapGenBase ravineGenerator = new MapGenRavine();
    private Biome[] biomesForGeneration;
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;

    public ChunkProviderChaos3(World par1World, long par2, boolean par4) {
        this.world = par1World;
        this.mapFeaturesEnabled = par4;
        this.field_147435_p = par1World.getWorldInfo().getTerrainType();
        this.rand = new Random(par2);
        this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147434_q = new double[825];
        this.parabolicField = new float[25];
        for (int j = -2; j <= 2; ++j) {
            for (int k = -2; k <= 2; ++k) {
                float f = 10.0f / MathHelper.sqrt((float) (j * j + k * k) + 0.2f);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }
        this.caveGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(this.caveGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE);
        this.ravineGenerator = net.minecraftforge.event.terraingen.TerrainGen.getModdedMapGen(this.ravineGenerator, net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE);
    }

    private void generateTerrain(int p_147424_1_, int p_147424_2_, ChunkPrimer primer) {
        int b0 = 63;
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, p_147424_1_ * 4 - 2, p_147424_2_ * 4 - 2, 10, 10);
        this.func_147423_a(p_147424_1_ * 4, 0, p_147424_2_ * 4);
        for (int k = 0; k < 4; ++k) {
            int l = k * 5;
            int i1 = (k + 1) * 5;
            for (int j1 = 0; j1 < 4; ++j1) {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;
                for (int k2 = 0; k2 < 32; ++k2) {
                    double d0 = 0.125;
                    double d1 = this.field_147434_q[k1 + k2];
                    double d2 = this.field_147434_q[l1 + k2];
                    double d3 = this.field_147434_q[i2 + k2];
                    double d4 = this.field_147434_q[j2 + k2];
                    double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;
                    for (int l2 = 0; l2 < 8; ++l2) {
                        double d9 = 0.25;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int i3 = 0; i3 < 4; ++i3) {
                            double d14 = 0.25;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;
                            for (int k3 = 0; k3 < 4; ++k3) {
                                d15 += d16;
                                int x = i3 + k * 4;
                                int y = k2 * 8 + l2;
                                int z = j1 * 4 + k3;
                                IBlockState state = d15 > 0.0 ? Blocks.STONE.getDefaultState() : (y < b0 ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState());
                                primer.setBlockState(x, y, z, state);
                            }
                            d10 += d12;
                            d11 += d13;
                        }
                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, ChunkPrimer primer, Biome[] p_147422_5_) {
        double d0 = 0.03125;
        this.stoneNoise = this.field_147430_m.getRegion(this.stoneNoise, (double) (p_147422_1_ * 16), (double) (p_147422_2_ * 16), 16, 16, d0 * 2.0, d0 * 2.0, 1.0);
        for (int k = 0; k < 16; ++k) {
            for (int l = 0; l < 16; ++l) {
                Biome biomegenbase = p_147422_5_[l + k * 16];
                biomegenbase.genTerrainBlocks(this.world, this.rand, primer, p_147422_1_ * 16 + k, p_147422_2_ * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }

    private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_) {
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0, 200.0, 0.5);
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001, 4.277575000000001, 8.555150000000001);
        this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412, 684.412, 684.412);
        this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412, 684.412, 684.412);
        int l = 0;
        int i1 = 0;
        for (int j1 = 0; j1 < 5; ++j1) {
            for (int k1 = 0; k1 < 5; ++k1) {
                float f = 0.0f;
                float f1 = 0.0f;
                float f2 = 0.0f;
                int b0 = 2;
                Biome biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];
                for (int l1 = -b0; l1 <= b0; ++l1) {
                    for (int i2 = -b0; i2 <= b0; ++i2) {
                        Biome biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.getBaseHeight();
                        float f4 = biomegenbase1.getHeightVariation();
                        if (this.field_147435_p == WorldType.AMPLIFIED && f3 > 0.0f) {
                            f3 = 1.0f + f3 * 2.0f;
                            f4 = 1.0f + f4 * 4.0f;
                        }
                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0f);
                        if (biomegenbase1.getBaseHeight() > biomegenbase.getBaseHeight()) {
                            f5 /= 2.0f;
                        }
                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }
                f /= f2;
                f1 /= f2;
                f = f * 0.9f + 0.1f;
                f1 = (f1 * 4.0f - 1.0f) / 8.0f;
                double d13 = this.field_147426_g[i1] / 8000.0;
                if (d13 < 0.0) {
                    d13 = (-d13) * 0.3;
                }
                if ((d13 = d13 * 3.0 - 2.0) < 0.0) {
                    if ((d13 /= 2.0) < -1.0) {
                        d13 = -1.0;
                    }
                    d13 /= 1.4;
                    d13 /= 2.0;
                } else {
                    if (d13 > 1.0) {
                        d13 = 1.0;
                    }
                    d13 /= 8.0;
                }
                ++i1;
                double d12 = f1;
                double d14 = f;
                d12 += d13 * 0.2;
                d12 = d12 * 8.5 / 8.0;
                double d5 = 8.5 + d12 * 4.0;
                for (int j2 = 0; j2 < 33; ++j2) {
                    double d6 = ((double) j2 - d5) * 12.0 * 128.0 / 256.0 / d14;
                    if (d6 < 0.0) {
                        d6 *= 4.0;
                    }
                    double d7 = this.field_147428_e[l] / 512.0;
                    double d8 = this.field_147425_f[l] / 512.0;
                    double d9 = (this.field_147427_d[l] / 10.0 + 1.0) / 2.0;
                    double d10 = (d9 < 0.0 ? d7 : (d9 > 1.0 ? d8 : d7 + (d8 - d7) * d9)) - d6;
                    if (j2 > 29) {
                        double d11 = (float) (j2 - 29) / 3.0f;
                        d10 = d10 * (1.0 - d11) + -10.0 * d11;
                    }
                    this.field_147434_q[l] = d10;
                    ++l;
                }
            }
        }
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.rand.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkPrimer primer = new ChunkPrimer();
        this.generateTerrain(x, z, primer);
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16, false);
        this.replaceBlocksForBiome(x, z, primer, this.biomesForGeneration);
        this.caveGenerator.generate(this.world, x, z, primer);
        this.ravineGenerator.generate(this.world, x, z, primer);
        if (this.mapFeaturesEnabled) {
            this.villageGenerator.generate(this.world, x, z, primer);
            this.scatteredFeatureGenerator.generate(this.world, x, z, primer);
        }
        Chunk chunk = new Chunk(this.world, primer, x, z);
        byte[] biomeBytes = chunk.getBiomeArray();
        for (int i = 0; i < biomeBytes.length; ++i) {
            biomeBytes[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
        }
        ChaosPersists.Chunker.generateOresInChunk(this.world, this.rand, x * 16, z * 16, chunk);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        BlockFalling.fallInstantly = true;
        int k = x * 16;
        int l = z * 16;
        Biome biomegenbase = this.world.getBiome(new BlockPos(k + 16, 0, l + 16));
        this.rand.setSeed(this.world.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long) x * i1 + (long) z * j1 ^ this.world.getSeed());
        boolean flag = false;
        if (this.mapFeaturesEnabled) {
            ChunkPos chunkpos = new ChunkPos(x, z);
            this.mineshaftGenerator.generateStructure(this.world, this.rand, chunkpos);
            flag = this.villageGenerator.generateStructure(this.world, this.rand, chunkpos);
            this.strongholdGenerator.generateStructure(this.world, this.rand, chunkpos);
            this.scatteredFeatureGenerator.generateStructure(this.world, this.rand, chunkpos);
        }
        if (biomegenbase != net.minecraft.init.Biomes.DESERT && biomegenbase != net.minecraft.init.Biomes.DESERT_HILLS && !flag && this.rand.nextInt(4) == 0 && TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.LAKE)) {
            int k1 = k + this.rand.nextInt(16) + 8;
            int l1 = this.rand.nextInt(256);
            int i2 = l + this.rand.nextInt(16) + 8;
            new WorldGenLakes(Blocks.WATER).generate(this.world, this.rand, new BlockPos(k1, l1, i2));
        }
        if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.LAVA) && !flag && this.rand.nextInt(8) == 0) {
            int k1 = k + this.rand.nextInt(16) + 8;
            int l1 = this.rand.nextInt(this.rand.nextInt(248) + 8);
            int i2 = l + this.rand.nextInt(16) + 8;
            if (l1 < 63 || this.rand.nextInt(10) == 0) {
                new WorldGenLakes(Blocks.LAVA).generate(this.world, this.rand, new BlockPos(k1, l1, i2));
            }
        }
        boolean doGen = TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.DUNGEON);
        for (int k1 = 0; doGen && k1 < 8; ++k1) {
            int l1 = k + this.rand.nextInt(16) + 8;
            int i2 = this.rand.nextInt(256);
            int j2 = l + this.rand.nextInt(16) + 8;
            new WorldGenDungeons().generate(this.world, this.rand, new BlockPos(l1, i2, j2));
        }
        biomegenbase.decorate(this.world, this.rand, new BlockPos(k, 0, l));
        k += 8;
        l += 8;
        doGen = TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.ICE);
        for (int k1 = 0; doGen && k1 < 16; ++k1) {
            for (int l1 = 0; l1 < 16; ++l1) {
                BlockPos pos = new BlockPos(k + k1, 0, l + l1);
                BlockPos heightPos = this.world.getPrecipitationHeight(pos);
                int precipY = heightPos.getY();
                if (this.world.canBlockFreezeBody(new BlockPos(k + k1, precipY - 1, l + l1), false)) {
                    this.world.setBlockState(new BlockPos(k + k1, precipY - 1, l + l1), Blocks.ICE.getDefaultState(), 2);
                }
                if (this.world.canSnowAt(new BlockPos(k + k1, precipY, l + l1), true)) {
                    this.world.setBlockState(new BlockPos(k + k1, precipY, l + l1), Blocks.SNOW_LAYER.getDefaultState(), 2);
                }
            }
        }
        BlockFalling.fallInstantly = false;
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        if ("Village".equals(structureName)) {
            return this.villageGenerator.isInsideStructure(pos);
        }
        if ("Temple".equals(structureName)) {
            return this.scatteredFeatureGenerator.isInsideStructure(pos);
        }
        return false;
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        if ("Stronghold".equals(structureName) && this.strongholdGenerator != null) {
            return this.strongholdGenerator.getNearestStructurePos(worldIn, position, findUnexplored);
        }
        return null;
    }

    public void recreateStructures(Chunk chunkIn, int x, int z) {
        if (this.mapFeaturesEnabled) {
            ChunkPrimer dummy = new ChunkPrimer();
            this.mineshaftGenerator.generate(this.world, x, z, dummy);
            this.villageGenerator.generate(this.world, x, z, dummy);
            this.strongholdGenerator.generate(this.world, x, z, dummy);
            this.scatteredFeatureGenerator.generate(this.world, x, z, dummy);
        }
    }

    public boolean isChunkGeneratedAt(int x, int z) {
        return false;
    }

    public boolean tick() {
        return false;
    }
}
