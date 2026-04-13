/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.ChunkProviderChaos4
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.entity.EnumCreatureType
 *  net.minecraft.init.Blocks
 *  net.minecraft.util.IProgressUpdate
 *  net.minecraft.world.ChunkPosition
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 *  net.minecraft.world.biome.Biome
 *  net.minecraft.world.chunk.Chunk
 *  net.minecraft.world.chunk.IChunkProvider
 *  net.minecraft.world.chunk.storage.ExtendedBlockStorage
 */
package com.astryxion.chaospersists.world.dimension.chunkprovider;

import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.block.state.IBlockState;

public class ChunkProviderChaos4
implements IChunkProvider, net.minecraft.world.gen.IChunkGenerator {

    @Override
    public boolean isChunkGeneratedAt(int x, int z) { return false; }
    @Override
    public boolean tick() { return false; }
    @Override
    public Chunk getLoadedChunk(int x, int z) { return provideChunk(x, z); }

    private World worldObj;
    private Random random;
    private Biome[] biomesForChunk;
    private final Block[] cachedBlockIDs = new Block[256];
    private final byte[] cachedBlockMetadata = new byte[256];

    public ChunkProviderChaos4(World par1World, long par2, boolean par4) {
        this.worldObj = par1World;
        this.random = new Random(par2);
        for (int j = 0; j < 8; ++j) {
            this.cachedBlockIDs[j] = j == 0 ? Blocks.BEDROCK : (j == 7 ? Blocks.GRASS : Blocks.DIRT);
        }
    }

    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        return this.provideChunk(x, z);
    }

    @Override
    public boolean generateStructures(net.minecraft.world.chunk.Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public void populate(int x, int z) {
        this.populate(this, x, z);
    }

    @Override
    public boolean isInsideStructure(net.minecraft.world.World worldIn, String structureName, net.minecraft.util.math.BlockPos pos) {
        return false;
    }

    @Override
    public void recreateStructures(net.minecraft.world.chunk.Chunk chunkIn, int x, int z) {
    }

    @Override
    public net.minecraft.util.math.BlockPos getNearestStructurePos(net.minecraft.world.World worldIn, String structureName, net.minecraft.util.math.BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public java.util.List<net.minecraft.world.biome.Biome.SpawnListEntry> getPossibleCreatures(net.minecraft.entity.EnumCreatureType creatureType, net.minecraft.util.math.BlockPos pos) {
        return this.worldObj.getBiome(pos).getSpawnableList(creatureType);
    }

    public Chunk provideChunk(int par1, int par2) {
        ChunkPrimer primer = new ChunkPrimer();
        for (int k = 0; k < this.cachedBlockIDs.length; ++k) {
            Block block = this.cachedBlockIDs[k];
            if (block == null) continue;
            IBlockState state = block.getStateFromMeta(this.cachedBlockMetadata[k] & 15);
            for (int i1 = 0; i1 < 16; ++i1) {
                for (int j1 = 0; j1 < 16; ++j1) {
                    primer.setBlockState(i1, k, j1, state);
                }
            }
        }
        Chunk chunk = new Chunk(this.worldObj, primer, par1, par2);
        this.biomesForChunk = this.worldObj.getBiomeProvider().getBiomes(this.biomesForChunk, par1 * 16, par2 * 16, 16, 16, false);
        byte[] biomeBytes = chunk.getBiomeArray();
        for (int i = 0; i < biomeBytes.length; ++i) {
            biomeBytes[i] = (byte) Biome.getIdForBiome(this.biomesForChunk[i]);
        }
        this.addScragglyTrees(this.worldObj, par1 * 16, par2 * 16, chunk);
        chunk.generateSkylightMap();
        return chunk;
    }

    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        int k = par2 * 16;
        int l = par3 * 16;
        this.random.setSeed(this.worldObj.getSeed());
        long i1 = this.random.nextLong() / 2L * 2L + 1L;
        long j1 = this.random.nextLong() / 2L * 2L + 1L;
        this.random.setSeed((long)par2 * i1 + (long)par3 * j1 ^ this.worldObj.getSeed());
    }

    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    public void saveExtraData() {
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "DangerDimension";
    }

    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        net.minecraft.world.biome.Biome biomegenbase = this.worldObj.getBiome(new net.minecraft.util.math.BlockPos(par2, 0, par4));
        return biomegenbase.getSpawnableList(par1EnumCreatureType);
    }

    public net.minecraft.util.math.BlockPos func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int par1, int par2) {
    }

    public void addScragglyTrees(World world, int chunkX, int chunkZ, Chunk chunk) {
        int howmany = 1 + this.random.nextInt(10);
        if (ChaosPersists.LessLag == 1) {
            howmany /= 2;
        }
        if (ChaosPersists.LessLag == 2) {
            howmany /= 4;
        }
        if (howmany == 0) {
            return;
        }
        block0 : for (int i = 0; i < howmany; ++i) {
            int posX = 2 + chunkX + this.random.nextInt(12);
            int posZ = 2 + chunkZ + this.random.nextInt(12);
            for (int posY = 20; posY > 2; --posY) {
                if (ChaosPersists.getBlockIDInChunk((Chunk)chunk, (int)posX, (int)(posY - 1), (int)posZ) != Blocks.GRASS) continue;
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
            if (ix > 1) {
                ix = 1;
            }
            if (ix < -1) {
                ix = -1;
            }
            if (iz > 1) {
                iz = 1;
            }
            if (iz < -1) {
                iz = -1;
            }
            if ((bid = ChaosPersists.getBlockIDInChunk((Chunk)chunk, (int)(x += ix), (int)(y += (iy = this.random.nextInt(3) > 0 ? 1 : 0)), (int)(z += iz))) != Blocks.AIR && bid != Blocks.LOG && bid != ChaosPersists.MyAppleLeaves) {
                return;
            }
            ChaosPersists.setBlockIDWithMetadataInChunk((Chunk)chunk, (int)x, (int)y, (int)z, (Block)Blocks.LOG, (int)0);
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (this.random.nextInt(2) != 1 || (bid = ChaosPersists.getBlockIDInChunk((Chunk)chunk, (int)(x + m), (int)y, (int)(z + n))) != Blocks.AIR) continue;
                    ChaosPersists.setBlockIDWithMetadataInChunk((Chunk)chunk, (int)(x + m), (int)y, (int)(z + n), (Block)ChaosPersists.MyAppleLeaves, (int)0);
                }
            }
            if (this.random.nextInt(2) != 1 || (bid = ChaosPersists.getBlockIDInChunk((Chunk)chunk, (int)x, (int)(y + 1), (int)z)) != Blocks.AIR) continue;
            ChaosPersists.setBlockIDWithMetadataInChunk((Chunk)chunk, (int)x, (int)(y + 1), (int)z, (Block)ChaosPersists.MyAppleLeaves, (int)0);
        }
    }

    public void ScragglyTreeWithBranches(World world, int x, int y, int z, Chunk chunk) {
        int k;
        Block bid;
        int i = 1 + this.random.nextInt(3);
        int j = i + this.random.nextInt(12);
        for (k = 0; k < i; ++k) {
            bid = ChaosPersists.getBlockIDInChunk((Chunk)chunk, (int)x, (int)(y + k), (int)z);
            if (k >= 1 && bid != Blocks.AIR && bid != Blocks.LOG && bid != ChaosPersists.MyAppleLeaves) {
                return;
            }
            ChaosPersists.setBlockIDWithMetadataInChunk((Chunk)chunk, (int)x, (int)(y + k), (int)z, (Block)Blocks.LOG, (int)0);
        }
        y += i - 1;
        for (k = i; k < j; ++k) {
            int ix = this.random.nextInt(2) - this.random.nextInt(2);
            int iz = this.random.nextInt(2) - this.random.nextInt(2);
            int iy = this.random.nextInt(4) > 0 ? 1 : 0;
            bid = ChaosPersists.getBlockIDInChunk((Chunk)chunk, (int)(x += ix), (int)(y += iy), (int)(z += iz));
            if (bid != Blocks.AIR && bid != Blocks.LOG && bid != ChaosPersists.MyAppleLeaves) break;
            ChaosPersists.setBlockIDWithMetadataInChunk((Chunk)chunk, (int)x, (int)y, (int)z, (Block)Blocks.LOG, (int)0);
            if (this.random.nextInt(4) == 1) {
                this.makeScragglyBranch(world, x, y, z, this.random.nextInt(1 + j - k), this.random.nextInt(2) - this.random.nextInt(2), this.random.nextInt(2) - this.random.nextInt(2), chunk);
            }
            for (int m = -1; m < 2; ++m) {
                for (int n = -1; n < 2; ++n) {
                    if (this.random.nextInt(2) != 1 || (bid = ChaosPersists.getBlockIDInChunk((Chunk)chunk, (int)(x + m), (int)y, (int)(z + n))) != Blocks.AIR) continue;
                    ChaosPersists.setBlockIDWithMetadataInChunk((Chunk)chunk, (int)(x + m), (int)y, (int)(z + n), (Block)ChaosPersists.MyAppleLeaves, (int)0);
                }
            }
            if (this.random.nextInt(2) != 1 || (bid = ChaosPersists.getBlockIDInChunk((Chunk)chunk, (int)x, (int)(y + 1), (int)z)) != Blocks.AIR) continue;
            ChaosPersists.setBlockIDWithMetadataInChunk((Chunk)chunk, (int)x, (int)(y + 1), (int)z, (Block)ChaosPersists.MyAppleLeaves, (int)0);
        }
    }
}

