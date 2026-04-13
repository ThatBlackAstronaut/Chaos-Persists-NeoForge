/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockDuctTape
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.math.AxisAlignedBB
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
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDuctTape
extends Block {
    public static final PropertyInteger SLICES = PropertyInteger.create("slices", 0, 5);

    public BlockDuctTape() {
        super(Material.IRON);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(SLICES, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, SLICES);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(SLICES, Math.min(5, meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(SLICES);
    }

    public net.minecraft.util.math.AxisAlignedBB getBoundingBox(net.minecraft.block.state.IBlockState state, net.minecraft.world.IBlockAccess source, net.minecraft.util.math.BlockPos pos) {
        int l = this.getMetaFromState(state);
        float f = 0.0625f;
        float f1 = (float)(1 + l * 2) / 16.0f;
        float f2 = 0.25f;
        return new net.minecraft.util.math.AxisAlignedBB((double)f1, 0.0, (double)f, (double)(1.0f - f), (double)(f2 - f), (double)(1.0f - f));
    }

    public AxisAlignedBB getCollisionBoundingBox(net.minecraft.block.state.IBlockState state, net.minecraft.world.IBlockAccess worldIn, net.minecraft.util.math.BlockPos pos) {
        int l = this.getMetaFromState(state);
        float f = 0.0625f;
        float f1 = (float)(1 + l * 2) / 16.0f;
        float f2 = 0.25f;
        return new AxisAlignedBB((double)f1, 0.0, (double)f, (double)(1.0f - f), (double)(f2 - f), (double)(1.0f - f));
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
        return new AxisAlignedBB((double)f1, 0.0, (double)f, (double)(1.0f - f), (double)f2, (double)(1.0f - f));
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
        // Placement is driven by ItemDuctTape via getStateForPlacement; default to 0 slices.
        return this.getDefaultState().withProperty(SLICES, 0);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        this.eatDuctTapeSlice(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
        return true;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        this.eatDuctTapeSlice(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
    }

    private void eatDuctTapeSlice(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
        ItemStack var2;
        if (par5EntityPlayer != null && (var2 = par5EntityPlayer.inventory.getCurrentItem()) != null && var2.getCount() == 1) {
            int cd = var2.getMaxDamage();
            int fd = 0;
            if (cd > 0) {
                if ((cd /= 6) < 1) {
                    cd = 1;
                }
                if ((fd = var2.getItemDamage()) > 0) {
                    fd = fd > cd ? (fd -= cd) : 0;
                    var2.setItemDamage(fd);
                    net.minecraft.util.math.BlockPos pos = new net.minecraft.util.math.BlockPos(par2, par3, par4);
                    int l = this.getMetaFromState(par1World.getBlockState(pos)) + 1;
                    if (l >= 6) {
                        par1World.setBlockToAir(pos);
                    } else {
                        par1World.setBlockState(pos, this.getStateFromMeta(l), 2);
                    }
                }
            }
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(worldIn, pos.getX(), pos.getY(), pos.getZ())) {
            worldIn.setBlockToAir(pos);
        }
    }

    public boolean canBlockStay(World par1World, int par2, int par3, int par4) {
        return par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4)).getBlock().getMaterial(par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4))).isSolid();
    }

    public int quantityDropped(Random par1Random) {
        return 0;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @SideOnly(value=Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return ChaosPersists.MyDuctTapeItem;
    }
}

