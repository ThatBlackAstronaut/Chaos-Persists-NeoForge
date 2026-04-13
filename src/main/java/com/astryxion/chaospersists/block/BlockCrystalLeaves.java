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
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystalLeaves extends BlockLeaves {

    public BlockCrystalLeaves() {
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

    // ===== CREATIVE TAB FIX =====

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

            if (world.rand.nextInt(100) == 1) {
                spawnAsEntity(world, pos, new ItemStack(ChaosPersists.MyCrystalApple));
            }

            if (world.rand.nextInt(50) == 1) {

                if (this == ChaosPersists.MyCrystalLeaves) {
                    spawnAsEntity(world, pos, new ItemStack(ChaosPersists.MyCrystalPlant));
                }

                if (this == ChaosPersists.MyCrystalLeaves2) {
                    spawnAsEntity(world, pos, new ItemStack(ChaosPersists.MyCrystalPlant2));
                }

                if (this == ChaosPersists.MyCrystalLeaves3) {
                    spawnAsEntity(world, pos, new ItemStack(ChaosPersists.MyCrystalPlant3));
                }
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
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world,
                                        BlockPos pos, EnumFacing side) {

        Block block = world.getBlockState(pos.offset(side)).getBlock();
        return ChaosPersists.FastGraphicsLeaves == 0 || block != this;
    }

    // 🔥 Proper 1.12.2 Render Layer

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
