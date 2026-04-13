/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BlockQuinoa
 *  com.astryxion.chaospersists.ChaosPersists
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
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;

public class BlockQuinoa
extends BlockReed {
    private int myMaxHeight = 0;

    public BlockQuinoa() { this(0); }
    protected BlockQuinoa(int par1) {
        this.setTickRandomly(true);
    }

    /** Same as {@link BlockCorn}: {@link BlockReed} AGE is 0–15; clamp meta so blockstates always match. */
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return super.getStateFromMeta(meta & 15);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockReed.AGE).intValue();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
        Block bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 - 1, par4)).getBlock();
        if (bid == Blocks.AIR) {
            return false;
        }
        if (bid == ChaosPersists.MyQuinoaPlant1 || bid == ChaosPersists.MyQuinoaPlant2 || bid == ChaosPersists.MyQuinoaPlant3 || bid == ChaosPersists.MyQuinoaPlant4 || bid == Blocks.GRASS || bid == Blocks.DIRT || bid == Blocks.FARMLAND || bid == ChaosPersists.CrystalGrass) {
            return true;
        }
        return false;
    }

    public void updateTick(World par1World, net.minecraft.util.math.BlockPos pos, net.minecraft.block.state.IBlockState state, Random par5Random) {
        Block bid;
        int Height = 1;
        boolean dontGrow = false;
        if (par1World.isRemote) {
            return;
        }
        if (this != ChaosPersists.MyQuinoaPlant1 && this != ChaosPersists.MyQuinoaPlant3) {
            return;
        }
        int par2 = pos.getX(), par3 = pos.getY(), par4 = pos.getZ();
        int var7 = this.getMetaFromState(state);
        this.myMaxHeight = var7 >> 8;
        var7 &= 255;
        if (this.myMaxHeight == 0) {
            this.myMaxHeight = 2 + ChaosPersists.ChaosRand.nextInt(3);
        }
        if ((bid = par1World.getBlockState(pos.up()).getBlock()) == Blocks.AIR) {
            for (int var6 = 1; var6 < 10 && ((bid = par1World.getBlockState(pos.down(var6)).getBlock()) == ChaosPersists.MyQuinoaPlant1 || bid == ChaosPersists.MyQuinoaPlant2 || bid == ChaosPersists.MyQuinoaPlant3 || bid == ChaosPersists.MyQuinoaPlant4); ++var6) {
                ++Height;
                if (bid != ChaosPersists.MyQuinoaPlant3 && bid != ChaosPersists.MyQuinoaPlant4) continue;
                dontGrow = true;
            }
            if (dontGrow) {
                this.myMaxHeight = Height;
            }
            int metaVal = this.myMaxHeight << 8;
            if (var7 >= 5 - this.myMaxHeight / 3) {
                if (Height < this.myMaxHeight) {
                    par1World.setBlockState(pos.up(), ChaosPersists.MyQuinoaPlant1.getDefaultState(), 2);
                    par1World.setBlockState(pos, ChaosPersists.MyQuinoaPlant2.getDefaultState(), 2);
                } else {
                    bid = par1World.getBlockState(pos).getBlock();
                    if (bid == ChaosPersists.MyQuinoaPlant1) {
                        par1World.setBlockState(pos, ChaosPersists.MyQuinoaPlant3.getDefaultState(), 2);
                    } else if (bid == ChaosPersists.MyQuinoaPlant3) {
                        par1World.setBlockState(pos, ChaosPersists.MyQuinoaPlant4.getDefaultState(), 2);
                    }
                    par1World.setBlockState(pos, par1World.getBlockState(pos).getBlock().getDefaultState(), 2);
                }
            } else {
                bid = par1World.getBlockState(pos).getBlock();
                par1World.setBlockState(pos, bid.getDefaultState(), 2);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ChaosPersists.MyQuinoa;
    }

    public int quantityDropped(Random par1Random) {
        if (this == ChaosPersists.MyQuinoaPlant4) {
            return 3 + par1Random.nextInt(3);
        }
        return 0;
    }

    public Item itemPicked(World par1World, int par2, int par3, int par4) {
        return ChaosPersists.MyQuinoa;
    }

    protected Item getSeedItem() {
        return ChaosPersists.MyQuinoa;
    }

    protected Item getCropItem() {
        return ChaosPersists.MyQuinoa;
    }}

