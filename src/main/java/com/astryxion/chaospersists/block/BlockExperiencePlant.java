/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockExperiencePlant
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.Trees
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockReed
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockExperiencePlant
extends BlockReed {
    public BlockExperiencePlant() { this(0); }
    protected BlockExperiencePlant(int par1) {
        float var3 = 0.375f;
        this.setTickRandomly(true);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        Block bid = world.getBlockState(pos.down()).getBlock();
        if (bid == Blocks.AIR) {
            return false;
        }
        return bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.rand.nextInt(20) != 1) {
            return;
        }
        for (int j1 = 0; j1 < 20; ++j1) {
            worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                    (double) ((float) pos.getX() + worldIn.rand.nextFloat()),
                    (double) pos.getY() + (double) worldIn.rand.nextFloat(),
                    (double) ((float) pos.getZ() + worldIn.rand.nextFloat()),
                    0.0, 0.0, 0.0);
        }
    }

    /**
     * 1.7.10 grew via random tick; 1.12.2 must override {@code updateTick(World, BlockPos, IBlockState, Random)}.
     * Do not call super — {@link BlockReed} would apply sugar cane growth.
     */
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote) {
            return;
        }
        if (rand.nextInt(10) != 1) {
            return;
        }
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        ChaosPersists.chaospersistsTrees.ExperienceTree(world, pos.getX(), pos.getY() - 1, pos.getZ());
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock((Block)ChaosPersists.MyExperiencePlant);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public int idPicked(World par1World, int par2, int par3, int par4) {
        return 0;
    }

    protected Item getSeedItem() {
        return ChaosPersists.MyExperienceTreeSeed;
    }

    protected int getCropItem() {
        return 0;
    }}

