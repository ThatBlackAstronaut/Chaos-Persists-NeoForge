package com.astryxion.chaospersists.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraft.util.EnumFacing;

public class MyBlockFlower extends BlockBush implements IPlantable {

    private static final AxisAlignedBB FLOWER_AABB =
            new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.6D, 0.7D);

    public MyBlockFlower() {
        super(Material.PLANTS);
        this.setSoundType(net.minecraft.block.SoundType.PLANT);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setLightOpacity(0);
    }

    // Bounding box
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FLOWER_AABB;
    }

    // Collision
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    // Rendering
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    // Placement
    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.GRASS
                || block == Blocks.DIRT
                || block == Blocks.FARMLAND
                || block == ChaosPersists.CrystalGrass;
    }

    public boolean canBlockStay(World worldIn, BlockPos pos) {
        BlockPos down = pos.down();
        return worldIn.getBlockState(down).getBlock().canSustainPlant(
                worldIn.getBlockState(down),
                worldIn,
                down,
                EnumFacing.UP,
                this
        );
    }

    // Plantable
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return this.getDefaultState();
    }
}
