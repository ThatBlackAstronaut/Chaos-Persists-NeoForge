package com.astryxion.chaospersists.block;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.astryxion.chaospersists.core.ChaosPersists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockExperienceLeaves extends BlockLeaves {

    public BlockExperienceLeaves() {
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setDefaultState(
                this.blockState.getBaseState()
                        .withProperty(DECAYABLE, true)
                        .withProperty(CHECK_DECAY, true)
        );
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DECAYABLE, CHECK_DECAY);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(DECAYABLE, (meta & 8) != 0)
                .withProperty(CHECK_DECAY, (meta & 4) != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (state.getValue(CHECK_DECAY)) i |= 4;
        if (state.getValue(DECAYABLE)) i |= 8;
        return i;
    }

    @Override
    public net.minecraft.block.BlockPlanks.EnumType getWoodType(int meta) {
        return net.minecraft.block.BlockPlanks.EnumType.OAK;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return Collections.singletonList(new ItemStack(this));
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        int par2 = pos.getX(), par3 = pos.getY(), par4 = pos.getZ();
        int var7 = 2;
        if (!world.isRemote && world.isAreaLoaded(pos.add(-var7, -var7, -var7), pos.add(var7, var7, var7))) {
            for (int var12 = -var7; var12 <= var7; ++var12) {
                for (int var13 = -var7; var13 <= 0; ++var13) {
                    for (int var14 = -var7; var14 <= var7; ++var14) {
                        Block bid;
                        BlockPos off = new BlockPos(par2 + var12, par3 + var13, par4 + var14);
                        int totaldist = Math.abs(var12) + Math.abs(var13) + Math.abs(var14);
                        if (totaldist > 3
                                || (bid = world.getBlockState(off).getBlock()) == null
                                || !bid.canSustainLeaves(world.getBlockState(off), world, off)) {
                            continue;
                        }
                        long t = world.getWorldTime();
                        if ((t %= 24000L) < 14000L || t > 22000L) {
                            return;
                        }
                        if (world.rand.nextInt(65) == 1
                                && world.getBlockState(new BlockPos(par2, par3 + 1, par4)).getBlock() == Blocks.AIR) {
                            BlockPos dropPos = new BlockPos(par2, par3 + 2, par4);
                            Block.spawnAsEntity(world, dropPos, new ItemStack(Items.EXPERIENCE_BOTTLE));
                        }
                        if (world.rand.nextInt(75) == 1
                                && world.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock() == Blocks.AIR) {
                            EntityExpBottle entity = new EntityExpBottle(world, (double) par2, (double) (par3 - 1), (double) par4);
                            entity.setLocationAndAngles((double) par2, (double) (par3 - 1), (double) par4, 0.0f, 0.0f);
                            entity.shoot(
                                    (double) ((world.rand.nextFloat() - world.rand.nextFloat()) / 2.0f),
                                    -0.10000000149011612,
                                    (double) ((world.rand.nextFloat() - world.rand.nextFloat()) / 2.0f),
                                    0.4f,
                                    5.0f
                            );
                            world.spawnEntity(entity);
                        }
                        return;
                    }
                }
            }
            removeLeaves(world, par2, par3, par4);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int par2 = pos.getX(), par3 = pos.getY(), par4 = pos.getZ();
        long t = worldIn.getWorldTime();
        if ((t %= 24000L) < 13000L || t > 23000L) {
            return;
        }
        int rate = 0;
        if (t < 14000L) {
            rate = (14000 - (int) t) / 2;
        }
        if (t > 22000L) {
            rate = (int) (t - 22000L) / 2;
        }
        if (worldIn.rand.nextInt(200 + rate) == 1
                && worldIn.getBlockState(new BlockPos(par2, par3 + 1, par4)).getBlock() == Blocks.AIR) {
            for (int i = 0; i < 10; ++i) {
                worldIn.spawnParticle(
                        EnumParticleTypes.FIREWORKS_SPARK,
                        (double) par2,
                        (double) par3 + 1.25,
                        (double) par4,
                        worldIn.rand.nextGaussian(),
                        Math.abs(worldIn.rand.nextGaussian()),
                        worldIn.rand.nextGaussian()
                );
            }
        }
        if (worldIn.rand.nextInt(40 + rate) == 1
                && worldIn.getBlockState(new BlockPos(par2, par3 - 1, par4)).getBlock() == Blocks.AIR) {
            for (int i = 0; i < 4; ++i) {
                worldIn.spawnParticle(
                        EnumParticleTypes.FIREWORKS_SPARK,
                        (double) par2,
                        (double) par3 - 1.25,
                        (double) par4,
                        (double) (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()),
                        (double) (-Math.abs(worldIn.rand.nextFloat())),
                        (double) (worldIn.rand.nextFloat() - worldIn.rand.nextFloat())
                );
            }
        }
    }

    private void removeLeaves(World world, int par2, int par3, int par4) {
        BlockPos pos = new BlockPos(par2, par3, par4);
        this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return ChaosPersists.FastGraphicsLeaves != 0;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        Block block = world.getBlockState(pos.offset(side)).getBlock();
        return ChaosPersists.FastGraphicsLeaves == 0 || block != this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
