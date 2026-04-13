package com.astryxion.chaospersists.block;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockCrystalTorch extends BlockTorch {

    public BlockCrystalTorch() {
        this(0);
    }

    public BlockCrystalTorch(int par1) {
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.rand.nextInt(4) != 1) {
            return;
        }
        EnumFacing var6 = stateIn.getValue(FACING);
        double var7 = pos.getX() + 0.5D;
        double var9 = pos.getY() + 0.7D;
        double var11 = pos.getZ() + 0.5D;
        double var13 = 0.213D;
        double var15 = 0.271D;

        if (var6 == EnumFacing.EAST) {
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, var7 - var15, var9 + var13, var11,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D, worldIn.rand.nextFloat() / 8.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, var7 - var15, var9 + var13, var11,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D, worldIn.rand.nextFloat() / 10.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D);
        } else if (var6 == EnumFacing.WEST) {
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, var7 + var15, var9 + var13, var11,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D, worldIn.rand.nextFloat() / 8.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, var7 + var15, var9 + var13, var11,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D, worldIn.rand.nextFloat() / 10.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D);
        } else if (var6 == EnumFacing.NORTH) {
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, var7, var9 + var13, var11 - var15,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D, worldIn.rand.nextFloat() / 8.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, var7, var9 + var13, var11 - var15,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D, worldIn.rand.nextFloat() / 10.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D);
        } else if (var6 == EnumFacing.SOUTH) {
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, var7, var9 + var13, var11 + var15,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D, worldIn.rand.nextFloat() / 8.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, var7, var9 + var13, var11 + var15,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D, worldIn.rand.nextFloat() / 10.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D);
        } else {
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, var7, var9, var11,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D, worldIn.rand.nextFloat() / 8.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 8.0D);
            worldIn.spawnParticle(EnumParticleTypes.FLAME, var7, var9, var11,
                    (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D, worldIn.rand.nextFloat() / 10.0D, (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) / 60.0D);
        }
    }

    private boolean isCrystalBlock(World world, BlockPos pos) {
        Block l = world.getBlockState(pos).getBlock();
        return l == ChaosPersists.CrystalStone
                || l == ChaosPersists.CrystalGrass
                || l == ChaosPersists.MyCrystalTreeLog
                || l == ChaosPersists.CrystalPlanksBlock;
    }

    private boolean isItSolidOnSide(World world, BlockPos neighborPos, EnumFacing side, boolean def) {
        if (this.isCrystalBlock(world, neighborPos)) {
            return true;
        }
        return world.isSideSolid(neighborPos, side, def);
    }

    private boolean canPlaceTorchOn(World world, BlockPos floorPos) {
        if (this.isCrystalBlock(world, floorPos)) {
            return true;
        }
        IBlockState st = world.getBlockState(floorPos);
        if (world.isSideSolid(floorPos, EnumFacing.UP, true)) {
            return true;
        }
        Block l = st.getBlock();
        return l.canPlaceTorchOnTop(st, world, floorPos);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return this.isItSolidOnSide(world, pos.west(), EnumFacing.EAST, true)
                || this.isItSolidOnSide(world, pos.east(), EnumFacing.WEST, true)
                || this.isItSolidOnSide(world, pos.north(), EnumFacing.SOUTH, true)
                || this.isItSolidOnSide(world, pos.south(), EnumFacing.NORTH, true)
                || this.canPlaceTorchOn(world, pos.down());
    }

    /**
     * 1.7.10 {@code onBlockPlaced}: allow wall / floor attachment to crystal blocks and normal solids.
     */
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing clickedFace, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        IBlockState def = this.getDefaultState();
        if (clickedFace == EnumFacing.UP && this.canPlaceTorchOn(world, pos.down())) {
            return def.withProperty(FACING, EnumFacing.UP);
        }
        if (clickedFace != EnumFacing.DOWN && clickedFace != EnumFacing.UP) {
            BlockPos support = pos.offset(clickedFace.getOpposite());
            if (world.isSideSolid(support, clickedFace, true) || this.isCrystalBlock(world, support)) {
                return def.withProperty(FACING, clickedFace);
            }
        }
        return super.getStateForPlacement(world, pos, clickedFace, hitX, hitY, hitZ, meta, placer, hand);
    }
}
