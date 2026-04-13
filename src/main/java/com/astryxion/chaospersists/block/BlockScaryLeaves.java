package com.astryxion.chaospersists.block;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.astryxion.chaospersists.core.ChaosPersists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockScaryLeaves extends BlockLeaves {

    public BlockScaryLeaves() {
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setDefaultState(
                this.blockState.getBaseState()
                        .withProperty(DECAYABLE, true)
                        .withProperty(CHECK_DECAY, true)
        );
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DECAYABLE, CHECK_DECAY);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(DECAYABLE, (meta & 8) != 0)
                .withProperty(CHECK_DECAY, (meta & 4) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(CHECK_DECAY)) i |= 4;
        if (state.getValue(DECAYABLE)) i |= 8;
        return i;
    }

    @Override
    public net.minecraft.block.BlockPlanks.EnumType getWoodType(int meta) {
        return net.minecraft.block.BlockPlanks.EnumType.OAK;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return Collections.singletonList(new ItemStack(this));
    }

    /** OreSpawn 1.7.10: 1/25 cherry or peach at {@code dropPos}. */
    private void maybeDropCherryOrPeach(World world, BlockPos dropPos, Random rand) {
        if (world.isRemote || rand.nextInt(25) != 1) {
            return;
        }
        if (this == ChaosPersists.MyCherryLeaves) {
            Block.spawnAsEntity(world, dropPos, new ItemStack(ChaosPersists.MyCherry));
        } else if (this == ChaosPersists.MyPeachLeaves) {
            Block.spawnAsEntity(world, dropPos, new ItemStack(ChaosPersists.MyPeach));
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
        maybeDropCherryOrPeach(world, pos, world.rand);
        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);
    }

    /**
     * Cherry/peach leaves are not oak: skip vanilla saplings/sticks/apples from {@link BlockLeaves#dropApple}.
     * Fruit is handled in {@link #dropBlockAsItemWithChance} via {@link #maybeDropCherryOrPeach}.
     */
    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int fortune) {
        if (this == ChaosPersists.MyCherryLeaves || this == ChaosPersists.MyPeachLeaves) {
            return;
        }
        super.dropApple(worldIn, pos, state, fortune);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int par2 = pos.getX(), par3 = pos.getY(), par4 = pos.getZ();
        int var7 = 2;
        if (!world.isRemote && world.isAreaLoaded(pos.add(-var7, -var7, -var7), pos.add(var7, var7, var7))) {
            for (int var12 = -var7; var12 <= var7; ++var12) {
                for (int var13 = -var7; var13 <= 0; ++var13) {
                    for (int var14 = -var7; var14 <= var7; ++var14) {
                        Block bid;
                        BlockPos off = new BlockPos(par2 + var12, par3 + var13, par4 + var14);
                        int totaldist = Math.abs(var12) + Math.abs(var13) + Math.abs(var14);
                        if (totaldist > 3
                                || (bid = world.getBlockState(off).getBlock()) == null
                                || !bid.canSustainLeaves(world.getBlockState(off), world, off)) {
                            continue;
                        }
                        long t = world.getWorldTime();
                        if (this == ChaosPersists.MyScaryLeaves && (t %= 24000L) < 12000L) {
                            ChaosPersists.setBlockFast(world, par2, par3, par4, ChaosPersists.MyAppleLeaves, 0, 3);
                        }
                        if (world.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock() == Blocks.AIR
                                && world.rand.nextInt(20) == 3) {
                            maybeDropCherryOrPeach(world, new BlockPos(par2, par3 - 1, par4), world.rand);
                        }
                        return;
                    }
                }
            }
            removeLeaves(world, par2, par3, par4);
        }
    }

    private void removeLeaves(World world, int par2, int par3, int par4) {
        BlockPos pos = new BlockPos(par2, par3, par4);
        IBlockState st = world.getBlockState(pos);
        dropBlockAsItemWithChance(world, pos, st, 1.0F, 0);
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return ChaosPersists.FastGraphicsLeaves != 0;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        Block block = world.getBlockState(pos.offset(side)).getBlock();
        return ChaosPersists.FastGraphicsLeaves == 0 || block != this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
