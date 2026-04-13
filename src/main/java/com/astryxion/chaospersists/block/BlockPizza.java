/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockPizza
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.FoodStats
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.FoodStats;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPizza
extends Block {
    public static final net.minecraft.block.properties.PropertyInteger SLICES = net.minecraft.block.properties.PropertyInteger.create("slices", 0, 5);

    public BlockPizza() {
        super(Material.CAKE);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SLICES, 0));
    }

    protected net.minecraft.block.state.BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(this, SLICES);
    }

    public net.minecraft.block.state.IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(SLICES, Math.min(5, meta));
    }

    public int getMetaFromState(net.minecraft.block.state.IBlockState state) {
        return state.getValue(SLICES);
    }

    public AxisAlignedBB getCollisionBoundingBox(net.minecraft.block.state.IBlockState state, net.minecraft.world.IBlockAccess worldIn, net.minecraft.util.math.BlockPos pos) {
        int l = this.getMetaFromState(state);
        float f = 0.0625f;
        float f1 = (float)(1 + l * 2) / 16.0f;
        float f2 = 0.25f;
        return new AxisAlignedBB((double)f1, 0.0, (double)f, (double)(1.0f - f), (double)(f2 - f), (double)(1.0f - f)).offset(pos);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(net.minecraft.block.state.IBlockState state, World par1World, net.minecraft.util.math.BlockPos pos) {
        int l = this.getMetaFromState(state);
        float f = 0.0625f;
        float f1 = (float)(1 + l * 2) / 16.0f;
        float f2 = 0.25f;
        return new AxisAlignedBB((double)f1, 0.0, (double)f, (double)(1.0f - f), (double)f2, (double)(1.0f - f)).offset(pos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.SOLID;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, net.minecraft.util.math.BlockPos pos, EnumFacing facing,
                                              float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        // Placement is driven by ItemPizza via getStateForPlacement; default to 0 slices.
        return this.getDefaultState().withProperty(SLICES, 0);
    }

    public boolean onBlockActivated(World par1World, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, EntityPlayer par5EntityPlayer, net.minecraft.util.EnumHand hand, net.minecraft.util.EnumFacing facing, float par7, float par8, float par9) {
        this.eatPizzaSlice(par1World, pos, par5EntityPlayer);
        return true;
    }

    public void onBlockClicked(World par1World, net.minecraft.util.math.BlockPos pos, EntityPlayer par5EntityPlayer) {
        this.eatPizzaSlice(par1World, pos, par5EntityPlayer);
    }

    private void eatPizzaSlice(World par1World, net.minecraft.util.math.BlockPos pos, EntityPlayer par5EntityPlayer) {
        if (par5EntityPlayer.canEat(false)) {
            par5EntityPlayer.getFoodStats().addStats(4, 0.2f);
            int l = this.getMetaFromState(par1World.getBlockState(pos)) + 1;
            if (l >= 6) {
                par1World.setBlockToAir(pos);
            } else {
                par1World.setBlockState(pos, this.getStateFromMeta(l), 2);
            }
        }
    }

    public boolean canPlaceBlockAt(World par1World, net.minecraft.util.math.BlockPos pos) {
        return !super.canPlaceBlockAt(par1World, pos) ? false : this.canBlockStay(par1World, pos.getX(), pos.getY(), pos.getZ());
    }

    public void onNeighborBlockChange(World par1World, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, net.minecraft.block.Block neighborBlock) {
        if (!this.canBlockStay(par1World, pos.getX(), pos.getY(), pos.getZ())) {
            par1World.setBlockToAir(pos);
        }
    }

    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        net.minecraft.block.state.IBlockState below = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4));
        return below.getBlock().isNormalCube(below, par1World, new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4));
    }

    public int quantityDropped(Random par1Random) {
        return 0;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return ChaosPersists.MyPizzaItem;
    }
}

