/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.QueenSpawnerBlock
 *  com.astryxion.chaospersists.TheQueen
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockReed
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.TheQueen;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockReed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
 * Exception performing whole class analysis ignored.
 */
public class QueenSpawnerBlock
extends BlockReed {
    public QueenSpawnerBlock() { this(0); }
    protected QueenSpawnerBlock(int par1) {
        float var3 = 0.375f;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getMaterial().isSolid();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (worldIn.rand.nextInt(20) != 1) {
            return;
        }
        for (int j1 = 0; j1 < 20; ++j1) {
            worldIn.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK,
                    (double)((float)pos.getX() + worldIn.rand.nextFloat()),
                    (double)pos.getY() + (double)worldIn.rand.nextFloat(),
                    (double)((float)pos.getZ() + worldIn.rand.nextFloat()),
                    0.0, 0.0, 0.0);
        }
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        if (world.isRemote) {
            return;
        }
        world.scheduleBlockUpdate(pos, (Block)this, 100, 0);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!world.isRemote && world.getBlockState(pos.up()).getBlock() == this) {
            world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (world.isRemote) {
            return;
        }
        if (ChaosPersists.TheQueenEnable != 0) {
            QueenSpawnerBlock.spawnTheQueen(world, (double)pos.getX(), (double)(pos.getY() + 8), (double)pos.getZ());
        }
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
    }

    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock((Block)ChaosPersists.MyQueenSpawnerBlock);
    }

    public int quantityDropped(Random par1Random) {
        return 1;
    }

    public static Entity spawnTheQueen(World par0World, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName(new net.minecraft.util.ResourceLocation("chaospersists", "the_queen"), par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
            ((TheQueen)var8).setGuardMode(1);
        }
        return var8;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos) {
        return true;
    }
}

