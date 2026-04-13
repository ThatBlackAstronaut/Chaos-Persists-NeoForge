package com.astryxion.chaospersists.world.dimension.chunkprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCaves;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderChaos2 implements IChunkGenerator {
  private Random rand;
  private World world;
  private final BiomeProvider biomeProvider;
  private final WorldType terrainType;
  private final double[] heightMap;
  private final float[] parabolicField;
  public NoiseGeneratorOctaves scale;
  public NoiseGeneratorOctaves depth;
  public NoiseGeneratorOctaves forest;
  private NoiseGeneratorOctaves field_185991_j;
  private NoiseGeneratorOctaves field_185992_k;
  private NoiseGeneratorOctaves field_185993_l;
  private NoiseGeneratorPerlin height;
  private MapGenBase caveGenerator;
  private double[] stoneNoise;
  private double[] field_186002_u = new double[256];
  private Biome[] biomesForGeneration;
  double[] field_185986_e;
  double[] field_185987_f;
  double[] field_185988_g;
  double[] field_185989_h;

  public ChunkProviderChaos2(World worldIn, long seed, BiomeProvider bp) {
    this.world = worldIn;
    this.terrainType = worldIn.getWorldInfo().getTerrainType();
    this.rand = new Random(seed);
    this.stoneNoise = new double[256];
    this.biomeProvider = bp;
    this.scale = new NoiseGeneratorOctaves(this.rand, 10);
    this.depth = new NoiseGeneratorOctaves(this.rand, 16);
    this.forest = new NoiseGeneratorOctaves(this.rand, 8);
    this.field_185991_j = new NoiseGeneratorOctaves(this.rand, 16);
    this.field_185992_k = new NoiseGeneratorOctaves(this.rand, 16);
    this.field_185993_l = new NoiseGeneratorOctaves(this.rand, 8);
    this.height = new NoiseGeneratorPerlin(this.rand, 4);
    this.heightMap = new double[825];
    this.parabolicField = new float[25];
    this.caveGenerator = TerrainGen.getModdedMapGen((MapGenBase)new MapGenCaves(), InitMapGenEvent.EventType.CAVE);
    for (int i = -2; i <= 2; i++) {
      for (int j = -2; j <= 2; j++) {
        float f = 10.0F / MathHelper.sqrt((i * i + j * j) + 0.2F);
        this.parabolicField[i + 2 + (j + 2) * 5] = f;
      }
    }
    InitNoiseGensEvent.ContextOverworld ctx = new InitNoiseGensEvent.ContextOverworld(this.field_185991_j, this.field_185992_k, this.field_185993_l, this.height, this.scale, this.depth, this.forest);
    ctx = (InitNoiseGensEvent.ContextOverworld)TerrainGen.getModdedNoiseGenerators(worldIn, this.rand, (InitNoiseGensEvent.Context)ctx);
    this.field_185991_j = ctx.getLPerlin1();
    this.field_185992_k = ctx.getLPerlin2();
    this.field_185993_l = ctx.getPerlin();
    this.height = ctx.getHeight();
    this.scale = ctx.getScale();
    this.depth = ctx.getDepth();
    this.forest = ctx.getForest();
  }

  public Chunk generateChunk(int x, int z) {
    this.rand.setSeed(x * 341873128712L + z * 132897987541L);
    ChunkPrimer chunkPrimer = new ChunkPrimer();
    setBlocksInChunk(x, z, chunkPrimer);
    this.biomesForGeneration = this.biomeProvider.getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
    replaceBiomeBlocks(x, z, chunkPrimer, this.biomesForGeneration);
    this.caveGenerator.generate(this.world, x, z, chunkPrimer);
    Chunk chunk = new Chunk(this.world, chunkPrimer, x, z);
    byte[] abyte = chunk.getBiomeArray();
    for (int i = 0; i < abyte.length; i++)
      abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
    // 1.7.10 parity: mining dimension generates Chaos ores via ChunkOreGenerator.
    ChaosPersists.Chunker.generateOresInChunk(this.world, this.rand, x * 16, z * 16, chunk);
    if (ChaosPersists.LessOre == 0) {
      ChaosPersists.Chunker.generateOresInChunk(this.world, this.rand, x * 16, z * 16, chunk);
      ChaosPersists.Chunker.generateOresInChunk(this.world, this.rand, x * 16, z * 16, chunk);
    }
    chunk.generateSkylightMap();
    return chunk;
  }

  private void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
    if (!ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world))
      return;
    double d0 = 0.03125D;
    this.field_186002_u = this.height.getRegion(this.field_186002_u, (x * 16), (z * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);
    for (int i = 0; i < 16; i++) {
      for (int j = 0; j < 16; j++) {
        Biome biome = biomesIn[j + i * 16];
        biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.field_186002_u[j + i * 16]);
      }
    }
  }

  private void generateBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal, Biome biome) {
    int seaLevel = 63;
    IBlockState topBlock = biome.topBlock;
    IBlockState fillerBlock = biome.fillerBlock;
    int j = -1;
    int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
    int l = x & 0xF;
    int i1 = z & 0xF;
    BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
    for (int y = 255; y >= 0; y--) {
      if (y == 0) {
        chunkPrimerIn.setBlockState(i1, y, l, Blocks.BEDROCK.getDefaultState());
      } else {
        IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, y, l);
        if (iblockstate2.getMaterial() == Material.AIR) {
          j = -1;
        } else if (iblockstate2.getBlock() == Blocks.STONE) {
          if (j == -1) {
            if (k <= 0) {
              topBlock = Blocks.AIR.getDefaultState();
              fillerBlock = Blocks.STONE.getDefaultState();
            } else if (y >= seaLevel - 4 && y <= seaLevel + 1) {
              topBlock = biome.topBlock;
              fillerBlock = biome.fillerBlock;
            }
            if (y < seaLevel && (topBlock == null || topBlock.getMaterial() == Material.AIR))
              fillerBlock = Blocks.WATER.getDefaultState();
            j = k;
            if (y >= seaLevel - 1) {
              chunkPrimerIn.setBlockState(i1, y, l, topBlock);
            } else if (y < seaLevel - 7 - k) {
              topBlock = Blocks.AIR.getDefaultState();
              fillerBlock = Blocks.STONE.getDefaultState();
              chunkPrimerIn.setBlockState(i1, y, l, Blocks.GRAVEL.getDefaultState());
            } else {
              chunkPrimerIn.setBlockState(i1, y, l, fillerBlock);
            }
          } else if (j > 0) {
            j--;
            chunkPrimerIn.setBlockState(i1, y, l, fillerBlock);
            if (j == 0 && fillerBlock.getBlock() == Blocks.SAND) {
              j = rand.nextInt(4);
              fillerBlock = Blocks.STONE.getDefaultState();
            }
          }
        }
      }
    }
  }

  private void setBlocksInChunk(int x, int z, ChunkPrimer chunkPrimer) {
    this.biomesForGeneration = this.biomeProvider.getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
    generateHeightmap(x * 4, 0, z * 4);
    for (int i = 0; i < 4; i++) {
      int j = i * 5;
      int k = (i + 1) * 5;
      for (int l = 0; l < 4; l++) {
        int i1 = (j + l) * 33;
        int j1 = (j + l + 1) * 33;
        int k1 = (k + l) * 33;
        int l1 = (k + l + 1) * 33;
        for (int i2 = 0; i2 < 32; i2++) {
          double d0 = 0.125D;
          double d1 = this.heightMap[i1 + i2];
          double d2 = this.heightMap[j1 + i2];
          double d3 = this.heightMap[k1 + i2];
          double d4 = this.heightMap[l1 + i2];
          double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125D;
          double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125D;
          double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125D;
          double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125D;
          for (int j2 = 0; j2 < 8; j2++) {
            double d9 = 0.25D;
            double d10 = d1;
            double d11 = d2;
            double d12 = (d3 - d1) * 0.25D;
            double d13 = (d4 - d2) * 0.25D;
            for (int k2 = 0; k2 < 4; k2++) {
              double d14 = 0.25D;
              double d16 = (d11 - d10) * 0.25D;
              double lvt_45_1_ = d10 - d16;
              for (int l2 = 0; l2 < 4; l2++) {
                if ((lvt_45_1_ += d16) > 0.0D) {
                  chunkPrimer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.STONE.getDefaultState());
                } else if (i2 * 8 + j2 < 63) {
                  chunkPrimer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, Blocks.WATER.getDefaultState());
                }
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

  private void generateHeightmap(int x, int y, int z) {
    this.field_185989_h = this.depth.generateNoiseOctaves(this.field_185989_h, x, z, 5, 5, 200.0D, 200.0D, 0.5D);
    float coordScale = 684.412F;
    float heightScale = 684.412F;
    this.field_185986_e = this.field_185993_l.generateNoiseOctaves(this.field_185986_e, x, y, z, 5, 33, 5, 8.55515D, 4.277575D, 8.55515D);
    this.field_185987_f = this.field_185991_j.generateNoiseOctaves(this.field_185987_f, x, y, z, 5, 33, 5, coordScale, heightScale, coordScale);
    this.field_185988_g = this.field_185992_k.generateNoiseOctaves(this.field_185988_g, x, y, z, 5, 33, 5, coordScale, heightScale, coordScale);
    int i = 0;
    int j = 0;
    for (int k = 0; k < 5; k++) {
      for (int l = 0; l < 5; l++) {
        float f2 = 0.0F;
        float f3 = 0.0F;
        float f4 = 0.0F;
        int i1 = 2;
        Biome surroundingBiome = this.biomesForGeneration[k + 2 + (l + 2) * 10];
        for (int j1 = -i1; j1 < i1; j1++) {
          for (int k1 = -i1; k1 <= i1; k1++) {
            Biome biome = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
            float baseHeight = biome.getBaseHeight();
            float heightVariation = biome.getHeightVariation();
            float f7 = this.parabolicField[j1 + 2 + (k1 + 2) * 5] / (baseHeight + 2.0F);
            if (biome.getBaseHeight() > surroundingBiome.getBaseHeight())
              f7 /= 2.0F;
            f2 += heightVariation * f7;
            f3 += baseHeight * f7;
            f4 += f7;
          }
        }
        f2 /= f4;
        f3 /= f4;
        f2 = f2 * 0.9F + 0.1F;
        f3 = (f3 * 4.0F - 1.0F) / 8.0F;
        double d7 = this.field_185989_h[j] / 8000.0D;
        if (d7 < 0.0D)
          d7 = -d7 * 0.3D;
        if (d7 < 0.0D) {
          d7 /= 2.0D;
          if (d7 < -1.0D)
            d7 = -1.0D;
          d7 /= 1.4D;
          d7 /= 2.0D;
        } else {
          if (d7 > 1.0D)
            d7 = 1.0D;
          d7 /= 8.0D;
        }
        j++;
        double d8 = f3;
        double d9 = f2;
        d8 += d7 * 0.2D;
        d8 = d8 * 8.5D / 8.0D;
        double d0 = 8.5D + d8 * 4.0D;
        for (int l1 = 0; l1 < 33; l1++) {
          double d1 = (l1 - d0) * 12.0D * 128.0D / 256.0D / d9;
          if (d1 < 0.0D)
            d1 *= 4.0D;
          double d2 = this.field_185987_f[i] / 512.0D;
          double d3 = this.field_185988_g[i] / 512.0D;
          double d4 = (this.field_185986_e[i] / 10.0D + 1.0D) / 2.0D;
          double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;
          if (l1 > 29) {
            double d6 = ((l1 - 29) / 3.0F);
            d5 = d5 * (1.0D - d6) + -10.0D * d6;
          }
          this.heightMap[i] = d5;
          i++;
        }
      }
    }
  }

  public boolean generateStructures(Chunk arg0, int arg1, int arg2) {
    return false;
  }

  public BlockPos getNearestStructurePos(World arg0, String arg1, BlockPos arg2, boolean arg3) {
    return null;
  }

  public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
    Biome biome = this.world.getBiome(pos);
    return biome.getSpawnableList(creatureType);
  }

  public boolean isInsideStructure(World arg0, String arg1, BlockPos arg2) {
    return false;
  }

  public void populate(int x, int z) {
    BlockFalling.fallInstantly = true;
    int i = x * 16;
    int j = z * 16;
    BlockPos blockpos = new BlockPos(i, 0, j);
    Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
    ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
    biome.decorate(this.world, this.rand, blockpos);
    ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
    BlockFalling.fallInstantly = false;
  }

  public void recreateStructures(Chunk arg0, int arg1, int arg2) {}
}
