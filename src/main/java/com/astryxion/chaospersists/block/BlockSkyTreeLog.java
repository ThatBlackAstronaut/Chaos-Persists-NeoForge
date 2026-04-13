/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockSkyTreeLog
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSkyTreeLog
extends Block {
    public BlockSkyTreeLog() { this(0, 0); }
    protected BlockSkyTreeLog(int par1, int par2) {
        super(Material.WOOD);
        this.setSoundType(net.minecraft.block.SoundType.WOOD);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        par3List.add(new ItemStack(Item.getItemFromBlock((Block)this), 1, 0));
    }

    protected ItemStack createStackedBlock(int par1) {
        return new ItemStack(Item.getItemFromBlock((Block)this), 1, 0);
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock((Block)ChaosPersists.MySkyTreeLog);
    }

    public void breakRecursor(World world, int x, int y, int z, int xf, int yf, int zf, int recursion) {
        int var7 = 1;
        if (recursion > 1000) {
            return;
        }
        for (int var9 = - var7; var9 <= var7; ++var9) {
            for (int var10 = - var7; var10 <= var7; ++var10) {
                for (int var11 = - var7; var11 <= var7; ++var11) {
                    Block var12;
                    if (var9 == 0 && var10 == 0 && var11 == 0 || x + var9 == xf && y + var10 == yf && z + var11 == zf || recursion > 0 && x + var9 >= xf - var7 && x + var9 <= xf + var7 && y + var10 >= yf - var7 && y + var10 <= yf + var7 && z + var11 >= zf - var7 && z + var11 <= zf + var7 || (var12 = world.getBlockState(new net.minecraft.util.math.BlockPos(x + var9, y + var10, z + var11)).getBlock()) != this) continue;
                    net.minecraft.util.math.BlockPos p = new net.minecraft.util.math.BlockPos(x + var9, y + var10, z + var11);
                    net.minecraft.block.state.IBlockState oldState = world.getBlockState(p);
                    world.setBlockState(p, Blocks.AIR.getDefaultState(), 2);
                    this.dropBlockAsItem(world, p, oldState, 0);
                    this.breakRecursor(world, x + var9, y + var10, z + var11, x, y, z, recursion + 1);
                }
            }
        }
    }

    public void onBlockDestroyedByPlayer(World par1World, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state) {
        par1World.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        this.breakRecursor(par1World, pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ(), 0);
        this.dropBlockAsItem(par1World, pos, state, 0);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!world.isRemote) {
            this.breakRecursor(world, pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ(), 0);
        }
        super.onBlockHarvested(world, pos, state, player);
    }
}

