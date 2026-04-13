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
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAppleLeaves extends BlockLeaves {

    public BlockAppleLeaves() {
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);

        this.setDefaultState(
                this.blockState.getBaseState()
                        .withProperty(DECAYABLE, true)
                        .withProperty(CHECK_DECAY, true)
        );
    }

    // ===== STATE =====

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

    // ===== CREATIVE TAB FIX (1.12.2 REQUIRED SIGNATURE) =====

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

    // ===== SHEARING =====

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return Collections.singletonList(new ItemStack(this));
    }

    // ===== DROPS =====

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {

        if (!world.isRemote) {

            if (world.rand.nextInt(25) == 1) {
                spawnAsEntity(world, pos, new ItemStack(Items.APPLE));
            }

            if (world.rand.nextInt(500) == 2) {
                spawnAsEntity(world, pos, new ItemStack(Items.GOLDEN_APPLE));
            }

            if (world.rand.nextInt(1000) == 3) {
                spawnAsEntity(world, pos, new ItemStack(Items.GOLDEN_APPLE, 1, 1));
            }

            if (world.rand.nextInt(10000) == 4) {
                spawnAsEntity(world, pos, new ItemStack(ChaosPersists.MagicApple));
            }
        }
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    // ===== GRAPHICS =====

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return ChaosPersists.FastGraphicsLeaves != 0;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos,
                                        net.minecraft.util.EnumFacing side) {
        Block block = world.getBlockState(pos.offset(side)).getBlock();
        return ChaosPersists.FastGraphicsLeaves == 0 || block != this;
    }

    /** Modded leaves never receive vanilla {@code BlockLeaves#setGraphicsLevel}; force fancy cutout. */
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

}
