/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BasiliskMaze
 *  com.astryxion.chaospersists.DungeonSpawnerBlock
 *  com.astryxion.chaospersists.GenericDungeon
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RubyBirdDungeon
 *  com.astryxion.chaospersists.Trees
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockReed
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.world.dimension.structure.BasiliskMaze;
import com.astryxion.chaospersists.world.dimension.structure.GenericDungeon;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.world.dimension.structure.RubyBirdDungeon;
import com.astryxion.chaospersists.util.Trees;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class DungeonSpawnerBlock
extends BlockReed {
    private static final float var3 = 0.375f;

    public DungeonSpawnerBlock() {
        this(0);
    }

    protected DungeonSpawnerBlock(int par1) {
        this.setTickRandomly(true);
    }

    @Override
    public net.minecraft.util.math.AxisAlignedBB getBoundingBox(net.minecraft.block.state.IBlockState state, net.minecraft.world.IBlockAccess source, net.minecraft.util.math.BlockPos pos) {
        return new net.minecraft.util.math.AxisAlignedBB(0.5 - var3, 0.0, 0.5 - var3, 0.5 + var3, 1.0, 0.5 + var3);
    }

    public boolean canPlaceBlockAt(World par1World, net.minecraft.util.math.BlockPos pos) {
        net.minecraft.util.math.BlockPos down = pos.down();
        return par1World.getBlockState(down).getMaterial().isSolid();
    }

    public void randomDisplayTick(net.minecraft.block.state.IBlockState stateIn, World par1World, net.minecraft.util.math.BlockPos pos, Random par5Random) {
        for (int j1 = 0; j1 < 5; ++j1) {
            par1World.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, (double)((float)pos.getX() + par1World.rand.nextFloat()), (double)pos.getY() + (double)par1World.rand.nextFloat(), (double)((float)pos.getZ() + par1World.rand.nextFloat()), (double)(par1World.rand.nextFloat() - par1World.rand.nextFloat()) / 4.0, (double)par1World.rand.nextFloat() / 2.0, (double)(par1World.rand.nextFloat() - par1World.rand.nextFloat()) / 4.0);
        }
    }

    public void onBlockAdded(World world, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state) {
        if (world.isRemote) {
            return;
        }
        world.scheduleBlockUpdate(pos, (Block)this, 400, 0);
    }

    public void onBlockHarvested(World world, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, net.minecraft.entity.player.EntityPlayer player) {
        super.onBlockHarvested(world, pos, state, player);
    }

    public void updateTick(World world, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, Random par5Random) {
        if (world.isRemote) {
            return;
        }
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        int clickedX = pos.getX();
        int clickedY = pos.getY();
        int clickedZ = pos.getZ();
        int type = world.rand.nextInt(50);
        if (type == 0) {
            ChaosPersists.chaospersistsTrees.FairyTree(world, clickedX, clickedY, clickedZ);
        }
        if (type == 1) {
            ChaosPersists.chaospersistsTrees.FairyCastleTree(world, clickedX, clickedY, clickedZ);
        }
        if (type == 2) {
            ChaosPersists.MyDungeon.makeEnormousCastle(world, clickedX, clickedY, clickedZ);
        }
        if (type == 3) {
            ChaosPersists.MyDungeon.makeRotatorStation(world, clickedX, clickedY, clickedZ);
        }
        if (type == 4) {
            ChaosPersists.MyDungeon.makeBeeHive(world, clickedX, clickedY, clickedZ);
        }
        if (type == 5) {
            ChaosPersists.MyDungeon.makeHauntedHouse(world, clickedX, clickedY, clickedZ);
        }
        if (type == 6) {
            ChaosPersists.MyDungeon.makeMantisHive(world, clickedX, clickedY, clickedZ);
        }
        if (type == 7) {
            ChaosPersists.MyDungeon.makeKyuubiDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 8) {
            ChaosPersists.MyDungeon.makeSmallBeeHive(world, clickedX, clickedY, clickedZ);
        }
        if (type == 9) {
            ChaosPersists.MyDungeon.makeShadowDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 10) {
            ChaosPersists.MyDungeon.makeAlienWTFDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 11) {
            ChaosPersists.MyDungeon.makeEnderKnightDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 12) {
            ChaosPersists.MyDungeon.makePlayPool(world, clickedX, clickedY, clickedZ);
        }
        if (type == 13) {
            ChaosPersists.MyDungeon.makeWaterDragonLair(world, clickedX, clickedY, clickedZ);
        }
        if (type == 14) {
            ChaosPersists.MyDungeon.makeCloudSharkDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 15) {
            ChaosPersists.MyDungeon.makeLeafMonsterDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 16) {
            ChaosPersists.MyDungeon.makeMiniDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 17) {
            ChaosPersists.MyDungeon.makeGoldFishBowl(world, clickedX, clickedY, clickedZ);
        }
        if (type == 18) {
            ChaosPersists.MyDungeon.makeEnderReaperGraveyard(world, clickedX, clickedY, clickedZ);
        }
        if (type == 19) {
            ChaosPersists.MyDungeon.makeSpitBugLair(world, clickedX, clickedY, clickedZ);
        }
        if (type == 20) {
            ChaosPersists.MyDungeon.makeIgloo(world, clickedX, clickedY, clickedZ);
        }
        if (type == 21) {
            ChaosPersists.MyDungeon.makeDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 22) {
            ChaosPersists.RubyDungeon.makeDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 23) {
            ChaosPersists.BMaze.buildBasiliskMaze(world, clickedX, clickedY, clickedZ);
        }
        if (type == 24) {
            ChaosPersists.MyDungeon.makeEnderDragonHospital(world, clickedX, clickedY, clickedZ);
        }
        if (type == 25) {
            ChaosPersists.MyDungeon.makeCrystalHauntedHouse(world, clickedX, clickedY, clickedZ);
        }
        if (type == 26) {
            ChaosPersists.MyDungeon.makeBouncyCastle(world, clickedX, clickedY, clickedZ);
        }
        if (type == 27) {
            ChaosPersists.MyDungeon.makeEnderCastle(world, clickedX, clickedY, clickedZ);
        }
        if (type == 28) {
            ChaosPersists.MyDungeon.makeDamselInDistress(world, clickedX, clickedY, clickedZ);
        }
        if (type == 29) {
            ChaosPersists.MyDungeon.makeIncaPyramid(world, clickedX, clickedY, clickedZ);
        }
        if (type == 30) {
            ChaosPersists.MyDungeon.makeRobotLab(world, clickedX, clickedY, clickedZ);
        }
        if (type == 31) {
            ChaosPersists.MyDungeon.makeKingAltar(world, clickedX, clickedY, clickedZ);
        }
        if (type == 32) {
            ChaosPersists.MyDungeon.makeLeonNest(world, clickedX, clickedY, clickedZ);
        }
        if (type == 33) {
            ChaosPersists.MyDungeon.makeCrystalBattleTower(world, clickedX, clickedY, clickedZ);
        }
        if (type == 34) {
            ChaosPersists.MyDungeon.makeCephadromeAltar(world, clickedX, clickedY, clickedZ);
        }
        if (type == 35) {
            ChaosPersists.MyDungeon.makeGirlfriendIsland(world, clickedX, clickedY, clickedZ);
        }
        if (type == 36) {
            ChaosPersists.MyDungeon.makeGreenhouseDungeon(world, clickedX, clickedY, clickedZ);
        }
        if (type == 37) {
            ChaosPersists.MyDungeon.makeMonsterIsland(world, clickedX, clickedY, clickedZ);
        }
        if (type == 38) {
            ChaosPersists.MyDungeon.makeNightmareRookery(world, clickedX, clickedY, clickedZ);
        }
        if (type == 39) {
            ChaosPersists.MyDungeon.makeStinkyHouse(world, clickedX, clickedY, clickedZ);
        }
        if (type == 40) {
            ChaosPersists.MyDungeon.makeRubberDuckyPond(world, clickedX, clickedY, clickedZ);
        }
        if (type == 41) {
            ChaosPersists.MyDungeon.makeWhiteHouse(world, clickedX, clickedY, clickedZ);
        }
        if (type == 42) {
            ChaosPersists.MyDungeon.makeQueenAltar(world, clickedX, clickedY, clickedZ);
        }
        if (type == 43) {
            ChaosPersists.MyDungeon.makeFrogPond(world, clickedX, clickedY + 1, clickedZ);
        }
        if (type == 44) {
            ChaosPersists.MyDungeon.makePumpkin(world, clickedX, clickedY + 1, clickedZ);
        }
        if (type == 45) {
            ChaosPersists.MyDungeon.makeRoundRotator(world, clickedX, clickedY + 1, clickedZ);
        }
        if (type == 46) {
            ChaosPersists.MyDungeon.makeRainbow(world, clickedX, clickedY, clickedZ);
        }
        if (type == 47) {
            ChaosPersists.MyDungeon.makeEnormousCastleQ(world, clickedX, clickedY, clickedZ);
        }
        if (type == 48) {
            ChaosPersists.MyDungeon.makeSpiderHangout(world, clickedX, clickedY, clickedZ);
        }
        if (type == 49) {
            ChaosPersists.MyDungeon.makeRedAntHangout(world, clickedX, clickedY, clickedZ);
        }
    }

    @SideOnly(value=Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return ChaosPersists.RandomDungeon;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return ChaosPersists.RandomDungeon;
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        return true;
    }}

