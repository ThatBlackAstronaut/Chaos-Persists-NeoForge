package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.entity.EntityAnt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class CreeperRepellent extends BlockTorch {

    public CreeperRepellent() {
        this(0);
    }

    public CreeperRepellent(int par1) {
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        EnumFacing f = stateIn.getValue(FACING);
        double var7 = pos.getX() + 0.5D;
        double var9 = pos.getY() + 0.7D;
        double var11 = pos.getZ() + 0.5D;
        double var13 = 0.413D;
        double var15 = 0.271D;

        if (f == EnumFacing.EAST) {
            this.spawnRepellentParticles(worldIn, var7 - var15, var9 + var13, var11);
        } else if (f == EnumFacing.WEST) {
            this.spawnRepellentParticles(worldIn, var7 + var15, var9 + var13, var11);
        } else if (f == EnumFacing.NORTH) {
            this.spawnRepellentParticles(worldIn, var7, var9 + var13, var11 - var15);
        } else if (f == EnumFacing.SOUTH) {
            this.spawnRepellentParticles(worldIn, var7, var9 + var13, var11 + var15);
        } else {
            this.spawnRepellentParticles(worldIn, var7, var9 + 0.21D, var11);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnRepellentParticles(World worldIn, double x, double y, double z) {
        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public int tickRate(World worldIn) {
        return 10;
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isRemote) {
            this.findSomethingToRepell(worldIn, pos);
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
    }

    /** 1.7.10: repell Creepers, Ants, and PurplePower mobs (except type 10). */
    private void findSomethingToRepell(World world, BlockPos pos) {
        int par2 = pos.getX();
        int par3 = pos.getY();
        int par4 = pos.getZ();
        AxisAlignedBB bb = new AxisAlignedBB(
                (double) par2 - 20.0D, (double) par3 - 10.0D, (double) par4 - 20.0D,
                (double) par2 + 20.0D, (double) par3 + 10.0D, (double) par4 + 20.0D);
        List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, bb);
        for (EntityLivingBase var3 : list) {
            if (var3 != null && var3 instanceof EntityCreeper) {
                this.applyRepelPush(var3, par2, par3, par4);
            }
            if (var3 != null && var3 instanceof EntityAnt) {
                this.applyRepelPush(var3, par2, par3, par4);
            }
            if (var3 != null && var3 instanceof PurplePower) {
                PurplePower p = (PurplePower) var3;
                if (p.getPurpleType() == 10) {
                    return;
                }
                this.applyRepelPush(var3, par2, par3, par4);
            }
        }
    }

    private void applyRepelPush(EntityLivingBase var3, int par2, int par3, int par4) {
        double d1 = var3.posX - (double) par2;
        double d2 = var3.posY - (double) par3;
        double d3 = var3.posZ - (double) par4;
        double f = d1 * d1 + d2 * d2 + d3 * d3;
        f = Math.sqrt(f);
        f = 20.0D - f;
        if (f > 20.0D) {
            f = 20.0D;
        }
        if (f < 0.0D) {
            f = 0.0D;
        }
        double dir = Math.atan2(var3.posX - (double) par2, var3.posZ - (double) par4);
        f *= 0.4D;
        var3.motionX += f * Math.sin(dir);
        var3.motionZ += f * Math.cos(dir);
    }
}
