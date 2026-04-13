package com.astryxion.chaospersists.world.dimension.chunkprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.fml.common.FMLLog;

public class ChunkProviderChaos6 implements net.minecraft.world.gen.IChunkGenerator {

    private final Random hellRNG;
    private final Random random;
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;
    private final World world;
    private double[] noiseField;
    private final double[] slowsandNoise = new double[256];
    private final double[] gravelNoise = new double[256];
    private final double[] netherrackExclusivityNoise = new double[256];
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    private Biome[] biomesForGeneration;

    public ChunkProviderChaos6(World worldIn, long seed) {
        this.world = worldIn;
        this.hellRNG = new Random(seed);
        this.random = new Random(seed);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
    }

    private void generateTerrain(int chunkX, int chunkZ, ChunkPrimer primer) {
        int b0 = 4;
        int k = b0 + 1;
        int b2 = 17;
        int l = b0 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, chunkX * b0, 0, chunkZ * b0, k, b2, l);
        for (int i1 = 0; i1 < b0; ++i1) {
            for (int j1 = 0; j1 < b0; ++j1) {
                for (int k1 = 0; k1 < 16; ++k1) {
                    double d0 = 0.125;
                    double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
                    double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
                    double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
                    double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
                    double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;
                    for (int l1 = 0; l1 < 8; ++l1) {
                        double d9 = 0.25;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;
                        for (int i2 = 0; i2 < 4; ++i2) {
                            double d14 = 0.25;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;
                            for (int k2 = 0; k2 < 4; ++k2) {
                                int x = i2 + i1 * 4;
                                int y = k1 * 8 + l1;
                                int z = j1 * 4 + k2;
                                IBlockState state = d15 > 0.0 ? Blocks.AIR.getDefaultState() : Blocks.STONE.getDefaultState();
                                primer.setBlockState(x, y, z, state);
                                d15 += d16;
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

    private void replaceBiomeBlocks(int chunkX, int chunkZ, ChunkPrimer primer) {
        byte b0 = 64;
        double d0 = 0.03125D;
        this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, chunkX * 16, 109, chunkZ * 16, 16, 1, 16, d0, 1.0D, d0);
        this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.netherrackExclusivityNoise, chunkX * 16, chunkZ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                boolean flag = this.slowsandNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.gravelNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2D > 0.0D;
                int i1 = (int) (this.netherrackExclusivityNoise[k + l * 16] / 3.0D + 3.0D + this.hellRNG.nextDouble() * 0.25D);
                int j1 = -1;
                IBlockState block = Blocks.GRASS.getDefaultState();
                IBlockState block1 = Blocks.DIRT.getDefaultState();

                for (int k1 = 127; k1 >= 0; k1--) {
                    if ((k1 < 127 - this.hellRNG.nextInt(5)) && (k1 > 0 + this.hellRNG.nextInt(5))) {
                        IBlockState block2 = primer.getBlockState(k, k1, l);

                        if ((block2.getBlock() != Blocks.AIR) && (block2.getMaterial() != Material.AIR)) {
                            if (block2.getBlock() == Blocks.STONE) {
                                if (j1 == -1) {
                                    if (i1 <= 0) {
                                        block = Blocks.AIR.getDefaultState();
                                        block1 = Blocks.STONE.getDefaultState();
                                    } else if ((k1 >= b0 - 4) && (k1 <= b0 + 1)) {
                                        block = Blocks.STONE.getDefaultState();
                                        block1 = Blocks.STONE.getDefaultState();

                                        if (flag1) {
                                            block = Blocks.GRASS.getDefaultState();
                                            block1 = Blocks.DIRT.getDefaultState();
                                        }

                                        if (flag) {
                                            block = Blocks.GRASS.getDefaultState();
                                            block1 = Blocks.DIRT.getDefaultState();
                                        }
                                    }

                                    if ((k1 < b0) && (block.getBlock() == Blocks.AIR)) {
                                        block = Blocks.WATER.getDefaultState();
                                    }

                                    j1 = i1;

                                    if (k1 >= b0 - 1) {
                                        primer.setBlockState(k, k1, l, block);
                                    } else {
                                        primer.setBlockState(k, k1, l, block1);
                                    }
                                } else if (j1 > 0) {
                                    j1--;
                                    primer.setBlockState(k, k1, l, block1);
                                }
                            }
                        } else {
                            j1 = -1;
                        }
                    } else {
                        primer.setBlockState(k, k1, l, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
    }

    private double[] initializeNoiseField(double[] p_73164_1_, int p_73164_2_, int p_73164_3_, int p_73164_4_, int p_73164_5_, int p_73164_6_, int p_73164_7_) {
        int i2;
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[p_73164_6_];
        if (p_73164_1_ == null) {
            p_73164_1_ = new double[p_73164_5_ * p_73164_6_ * p_73164_7_];
        }
        double d0 = 684.412;
        double d1 = 2053.236;
        this.noiseData4 = this.netherNoiseGen6.generateNoiseOctaves(this.noiseData4, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 1.0, 0.0, 1.0);
        this.noiseData5 = this.netherNoiseGen7.generateNoiseOctaves(this.noiseData5, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, 1, p_73164_7_, 100.0, 0.0, 100.0);
        this.noiseData1 = this.netherNoiseGen3.generateNoiseOctaves(this.noiseData1, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0 / 80.0, d1 / 60.0, d0 / 80.0);
        this.noiseData2 = this.netherNoiseGen1.generateNoiseOctaves(this.noiseData2, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2.generateNoiseOctaves(this.noiseData3, p_73164_2_, p_73164_3_, p_73164_4_, p_73164_5_, p_73164_6_, p_73164_7_, d0, d1, d0);
        for (i2 = 0; i2 < p_73164_6_; ++i2) {
            adouble1[i2] = Math.cos((double) i2 * 3.141592653589793 * 6.0 / (double) p_73164_6_) * 2.0;
            double d2 = i2;
            if (i2 > p_73164_6_ / 2) {
                d2 = p_73164_6_ - 1 - i2;
            }
            if (d2 >= 4.0) continue;
            d2 = 4.0 - d2;
            double[] arrd = adouble1;
            int n = i2;
            arrd[n] = arrd[n] - d2 * d2 * d2 * 10.0;
        }
        for (i2 = 0; i2 < p_73164_5_; ++i2) {
            for (int k2 = 0; k2 < p_73164_7_; ++k2) {
                double d3 = (this.noiseData4[l1] + 256.0) / 512.0;
                if (d3 > 1.0) {
                    d3 = 1.0;
                }
                double d4 = 0.0;
                double d5 = this.noiseData5[l1] / 8000.0;
                if (d5 < 0.0) {
                    d5 = -d5;
                }
                if ((d5 = d5 * 3.0 - 3.0) < 0.0) {
                    if ((d5 /= 2.0) < -1.0) {
                        d5 = -1.0;
                    }
                    d5 /= 1.4;
                    d5 /= 2.0;
                    d3 = 0.0;
                } else {
                    if (d5 > 1.0) {
                        d5 = 1.0;
                    }
                    d5 /= 6.0;
                }
                d3 += 0.5;
                d5 = d5 * (double) p_73164_6_ / 16.0;
                ++l1;
                for (int j2 = 0; j2 < p_73164_6_; ++j2) {
                    double d11;
                    double d6 = 0.0;
                    double d7 = adouble1[j2];
                    double d8 = this.noiseData2[k1] / 512.0;
                    double d9 = this.noiseData3[k1] / 512.0;
                    double d10 = (this.noiseData1[k1] / 10.0 + 1.0) / 2.0;
                    d6 = d10 < 0.0 ? d8 : (d10 > 1.0 ? d9 : d8 + (d9 - d8) * d10);
                    d6 -= d7;
                    if (j2 > p_73164_6_ - 4) {
                        d11 = (float) (j2 - (p_73164_6_ - 4)) / 3.0f;
                        d6 = d6 * (1.0 - d11) + -10.0 * d11;
                    }
                    if ((double) j2 < d4) {
                        d11 = (d4 - (double) j2) / 4.0;
                        if (d11 < 0.0) {
                            d11 = 0.0;
                        }
                        if (d11 > 1.0) {
                            d11 = 1.0;
                        }
                        d6 = d6 * (1.0 - d11) + -10.0 * d11;
                    }
                    p_73164_1_[k1] = d6;
                    ++k1;
                }
            }
        }
        return p_73164_1_;
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        this.hellRNG.setSeed((long) x * 341873128712L + (long) z * 132897987541L);
        ChunkPrimer primer = new ChunkPrimer();
        this.generateTerrain(x, z, primer);
        this.replaceBiomeBlocks(x, z, primer);
        Chunk chunk = new Chunk(this.world, primer, x, z);
        this.biomesForGeneration = this.world.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16, false);
        byte[] biomeBytes = chunk.getBiomeArray();
        for (int i = 0; i < biomeBytes.length; i++) {
            biomeBytes[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
        }
        ChaosPersists.Chunker.generateOresInChunk(this.world, this.world.rand, x * 16, z * 16, chunk);
        this.addScragglyTrees(this.world, x * 16, z * 16, chunk);
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        BlockFalling.fallInstantly = false;
        int var4 = x * 16;
        int var5 = z * 16;
        Biome var6 = this.world.getBiome(new BlockPos(var4 + 16, 0, var5 + 16));
        if (var6 == null || var6.decorator == null) {
            FMLLog.log.warn("ChaosPersists: skipping chaos biome decorate due to null biome/decorator at chunk {}, {}", x, z);
            return;
        }
        int originalTrees = var6.decorator.treesPerChunk;
        try {
            var6.decorator.treesPerChunk = 0;
            var6.decorate(this.world, this.world.rand, new BlockPos(var4, 0, var5));
        } catch (Throwable t) {
            FMLLog.log.warn("ChaosPersists: chaos biome decorate failed at chunk {}, {} biome={}", x, z, var6.getRegistryName(), t);
        } finally {
            var6.decorator.treesPerChunk = originalTrees;
        }
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
        return false;
    }

    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {
    }

    public void addScragglyTrees(World world, int chunkX, int chunkZ, Chunk chunk) {
        int howmany = 1 + world.rand.nextInt(5);
        if (world.rand.nextInt(4) != 0) {
            return;
        }
        if (ChaosPersists.LessLag == 1) {
            howmany /= 2;
        }
        if (ChaosPersists.LessLag == 2) {
            howmany /= 4;
        }
        if (howmany == 0) {
            return;
        }
        block0:
        for (int i = 0; i < howmany; ++i) {
            int posX = 2 + chunkX + this.random.nextInt(12);
            int posZ = 2 + chunkZ + this.random.nextInt(12);
            for (int posY = 120; posY > 50; --posY) {
                if (ChaosPersists.getBlockIDInChunk(chunk, posX, posY - 1, posZ) != Blocks.GRASS) continue;
                this.ScragglyTreeWithBranches(world, posX, posY, posZ, chunk);
                continue block0;
            }
        }
    }

    public void makeScragglyBranch(World world, int x, int y, int z, int len, int biasx, int biasz, Chunk chunk) {
        for (int k = 0; k < len; ++k) {
            int iy;
            Block bid;
            int ix = this.random.nextInt(2) - this.random.nextInt(2) + biasx;
            int iz = this.random.nextInt(2) - this.random.nextInt(2) + biasz;
            if (ix > 1) ix = 1;
            if (ix < -1) ix = -1;
            if (iz > 1) iz = 1;
            if (iz < -1) iz = -1;
            if ((bid = ChaosPersists.getBlockIDInChunk(chunk, x += ix, y += (iy = this.random.nextInt(3) > 0 ? 1 : 0), z += iz)) != Blocks.AIR && bid != Blocks.LOG && bid != ChaosPersists.MyAppleLeaves) {
                return;
            }
            ChaosPersists.setBlockIDWithMetadataInChunk(chunk, x, y, z, Blocks.LOG, 0);
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (this.random.nextInt(2) != 1 || (bid = ChaosPersists.getBlockIDInChunk(chunk, x + m, y, z + n)) != Blocks.AIR) continue;
                    ChaosPersists.setBlockIDWithMetadataInChunk(chunk, x + m, y, z + n, ChaosPersists.MyAppleLeaves, 0);
                }
            }
            if (this.random.nextInt(2) != 1 || (bid = ChaosPersists.getBlockIDInChunk(chunk, x, y + 1, z)) != Blocks.AIR) continue;
            ChaosPersists.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, ChaosPersists.MyAppleLeaves, 0);
        }
    }

    public void ScragglyTreeWithBranches(World world, int x, int y, int z, Chunk chunk) {
        int k;
        Block bid;
        int i = 1 + this.random.nextInt(3);
        int j = i + this.random.nextInt(12);
        for (k = 0; k < i; ++k) {
            bid = ChaosPersists.getBlockIDInChunk(chunk, x, y + k, z);
            if (k >= 1 && bid != Blocks.AIR && bid != Blocks.LOG && bid != ChaosPersists.MyAppleLeaves) {
                return;
            }
            ChaosPersists.setBlockIDWithMetadataInChunk(chunk, x, y + k, z, Blocks.LOG, 0);
        }
        y += i - 1;
        for (k = i; k < j; ++k) {
            int ix = this.random.nextInt(2) - this.random.nextInt(2);
            int iz = this.random.nextInt(2) - this.random.nextInt(2);
            int iy = this.random.nextInt(4) > 0 ? 1 : 0;
            bid = ChaosPersists.getBlockIDInChunk(chunk, x += ix, y += iy, z += iz);
            if (bid != Blocks.AIR && bid != Blocks.LOG && bid != ChaosPersists.MyAppleLeaves) break;
            ChaosPersists.setBlockIDWithMetadataInChunk(chunk, x, y, z, Blocks.LOG, 0);
            if (this.random.nextInt(4) == 1) {
                this.makeScragglyBranch(world, x, y, z, this.random.nextInt(1 + j - k), this.random.nextInt(2) - this.random.nextInt(2), this.random.nextInt(2) - this.random.nextInt(2), chunk);
            }
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (this.random.nextInt(2) != 1 || (bid = ChaosPersists.getBlockIDInChunk(chunk, x + m, y, z + n)) != Blocks.AIR) continue;
                    ChaosPersists.setBlockIDWithMetadataInChunk(chunk, x + m, y, z + n, ChaosPersists.MyAppleLeaves, 0);
                }
            }
            if (this.random.nextInt(2) != 1 || (bid = ChaosPersists.getBlockIDInChunk(chunk, x, y + 1, z)) != Blocks.AIR) continue;
            ChaosPersists.setBlockIDWithMetadataInChunk(chunk, x, y + 1, z, ChaosPersists.MyAppleLeaves, 0);
        }
    }
}
