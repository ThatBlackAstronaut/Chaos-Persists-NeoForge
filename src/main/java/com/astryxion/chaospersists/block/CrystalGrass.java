package com.astryxion.chaospersists.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalGrass extends Block {

    public CrystalGrass(float hardness, float resistance) {
        super(Material.GRASS);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world,
                                   BlockPos pos, EnumFacing direction,
                                   IPlantable plantable) {
        return true;
    }

    /**
     * Non-opaque for rendering, so Forge's default {@code canCreatureSpawn} (top side solid) is false
     * and natural spawning never runs. Match vanilla grass so surface mobs can spawn.
     */
    @Override
    public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos,
                                    EntityLiving.SpawnPlacementType type) {
        return Blocks.GRASS.canCreatureSpawn(Blocks.GRASS.getDefaultState(), world, pos, type);
    }

    // 🔥 Transparency Fixes

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

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        IBlockState adjacent = world.getBlockState(pos.offset(side));
        if (adjacent.getBlock() == this) {
            return false;
        }
        return super.shouldSideBeRendered(state, world, pos, side);
    }
}
