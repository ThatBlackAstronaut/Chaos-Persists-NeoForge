package com.astryxion.chaospersists.block;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.EntityMosquito;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMosquitoPlant extends BlockCrops {

    public BlockMosquitoPlant() {
        setDefaultState(blockState.getBaseState().withProperty(BlockCrops.AGE, Integer.valueOf(0)));
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

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.isRemote)
            return;

        float radius = 50.0F;
        AxisAlignedBB aabb = new AxisAlignedBB((pos.getX() - radius), 0.0D, (pos.getZ() - radius), (pos.getX() + radius), 200.0D, (pos.getZ() + radius));
        List<EntityMosquito> mosquitoList = worldIn.getEntitiesWithinAABB(EntityMosquito.class, aabb);
        if (mosquitoList.size() > 15)
            return;

        IBlockState st = worldIn.getBlockState(pos);
        int rate = st.getBlock().getMetaFromState(st);
        rate &= 0x7;
        rate = 6 - rate;
        if (rate > 1 && ChaosPersists.ChaosRand.nextInt(rate) != 0)
            return;

        if (worldIn.getBlockState(pos.up()).getBlock() == Blocks.AIR && worldIn.isDaytime()) {
            Entity mosquito = new EntityMosquito(worldIn);
            mosquito.setPosition(pos.getX(), pos.getY() + 1, pos.getZ());
            worldIn.spawnEntity(mosquito);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ChaosPersists.MyMosquitoSeed;
    }

    @Override
    protected Item getSeed() {
        return ChaosPersists.MyMosquitoSeed;
    }

    @Override
    protected Item getCrop() {
        return ChaosPersists.MyMosquitoSeed;
    }
}
