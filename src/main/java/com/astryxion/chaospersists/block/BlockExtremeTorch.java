package com.astryxion.chaospersists.block;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockExtremeTorch extends BlockTorch {

    public BlockExtremeTorch() {
        super();
        this.setCreativeTab(CreativeTabs.REDSTONE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        EnumFacing facing = stateIn.getValue(FACING);
        double d0 = (double) pos.getX() + 0.5D;
        double d1 = (double) pos.getY() + 0.7D;
        double d2 = (double) pos.getZ() + 0.5D;

        if (facing.getAxis().isHorizontal()) {
            EnumFacing attach = facing.getOpposite();
            d0 += (double) attach.getXOffset() * 0.3D;
            d1 += 0.22D;
            d2 += (double) attach.getZOffset() * 0.3D;
        } else if (facing == EnumFacing.UP) {
            d1 -= 0.1D;
        } else {
            d1 += 0.15D;
        }

        worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        int par2 = pos.getX();
        int par3 = pos.getY();
        int par4 = pos.getZ();
        int x = par2;
        int y = par3;
        int z = par4;
        boolean found = false;

        if (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == ChaosPersists.MyEyeOfEnderBlock) {
            block0:
            for (int tries = 0; tries < 100 && !found; ++tries) {
                x = world.rand.nextInt(2) == 0 ? par2 + 4 + world.rand.nextInt(3) - world.rand.nextInt(3) : par2 - 4 + world.rand.nextInt(3) - world.rand.nextInt(3);
                z = world.rand.nextInt(2) == 0 ? par4 + 4 + world.rand.nextInt(3) - world.rand.nextInt(3) : par4 - 4 + world.rand.nextInt(3) - world.rand.nextInt(3);
                for (y = par3 - 2; y <= par3 + 2; ++y) {
                    BlockPos below = new BlockPos(x, y - 1, z);
                    IBlockState belowState = world.getBlockState(below);
                    if (!belowState.getBlock().getMaterial(belowState).isSolid()
                            || world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR
                            || world.getBlockState(new BlockPos(x, y + 1, z)).getBlock() != Blocks.AIR) {
                        continue;
                    }
                    found = true;
                    continue block0;
                }
            }
            if (found) {
                if (!world.isRemote) {
                    spawnCreature(world, new ResourceLocation("chaospersists", "cephadrome"), (double) x + 0.5D, (double) y + 0.01D, (double) z + 0.5D);
                } else {
                    for (int var3 = 0; var3 < 16; ++var3) {
                        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) ((float) par2 + world.rand.nextFloat() - world.rand.nextFloat()), (double) ((float) par3 + world.rand.nextFloat()), (double) ((float) par4 + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                        world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (double) ((float) par2 + world.rand.nextFloat() - world.rand.nextFloat()), (double) ((float) par3 + world.rand.nextFloat()), (double) ((float) par4 + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                        world.spawnParticle(EnumParticleTypes.REDSTONE, (double) ((float) par2 + world.rand.nextFloat() - world.rand.nextFloat()), (double) ((float) par3 + world.rand.nextFloat()), (double) ((float) par4 + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                    }
                }
                if (placer != null) {
                    world.playSound(null, placer.posX, placer.posY, placer.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.2f + 0.9f);
                } else {
                    world.playSound(null, (double) par2, (double) par3, (double) par4, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.2f + 0.9f);
                }
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    private static Entity spawnCreature(World world, ResourceLocation entityId, double px, double py, double pz) {
        Entity entity = EntityList.createEntityByIDFromName(entityId, world);
        if (entity != null) {
            entity.setLocationAndAngles(px, py, pz, world.rand.nextFloat() * 360.0f, 0.0f);
            world.spawnEntity(entity);
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).playLivingSound();
            }
        }
        return entity;
    }
}
