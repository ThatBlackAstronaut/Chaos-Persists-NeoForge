package com.astryxion.chaospersists.block;

import com.astryxion.chaospersists.entity.EntityAnt;
import com.astryxion.chaospersists.entity.EntityRedAnt;
import com.astryxion.chaospersists.entity.EntityRainbowAnt;
import com.astryxion.chaospersists.entity.EntityUnstableAnt;
import com.astryxion.chaospersists.entity.Termite;
import com.astryxion.chaospersists.core.ChaosPersists;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AntBlock extends BlockGrass {

    public AntBlock(int par1) {
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    /**
     * Official-style 1.12.2 passive nest spawning.
     * Daytime only, no player requirement, cap 20, spawn 2–7.
     */
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) return;

        // Block above must be air
        if (worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR) return;

        // Daytime only
        if (!worldIn.isDaytime()) return;

        String mobName = getMobNameForBlock();
        if (mobName == null) return;

        Class<? extends Entity> entityClass = getEntityClassForBlock();
        if (entityClass == null) return;

        int radius = 16;

        AxisAlignedBB aabb = new AxisAlignedBB(
                pos.getX() - radius, 0.0D, pos.getZ() - radius,
                pos.getX() + radius, 200.0D, pos.getZ() + radius
        );

        List<Entity> nearby = worldIn.getEntitiesWithinAABB(entityClass, aabb);

        if (nearby.size() > 20) return;

        int count = rand.nextInt(6) + 2;

        for (int i = 0; i < count; i++) {
            spawnCreature(
                    worldIn,
                    mobName,
                    pos.getX() + 0.5,
                    pos.getY() + 1.0,
                    pos.getZ() + 0.5
            );
        }
    }

    /** Returns spawn entity name, or null if disabled by config. */
    private String getMobNameForBlock() {
        if (this == ChaosPersists.MyAntBlock) {
            return ChaosPersists.BlackAntEnable != 0 ? "Ant" : null;
        }
        if (this == ChaosPersists.MyRedAntBlock) {
            return ChaosPersists.RedAntEnable != 0 ? "Red Ant" : null;
        }
        if (this == ChaosPersists.MyRainbowAntBlock) {
            return ChaosPersists.RainbowAntEnable != 0 ? "Rainbow Ant" : null;
        }
        if (this == ChaosPersists.MyUnstableAntBlock) {
            return ChaosPersists.UnstableAntEnable != 0 ? "Unstable Ant" : null;
        }
        if (this == ChaosPersists.TermiteBlock) {
            return ChaosPersists.TermiteEnable != 0 ? "Termite" : null;
        }
        return null;
    }

    /** Returns entity class for nearby count. */
    @SuppressWarnings("unchecked")
    private Class<? extends Entity> getEntityClassForBlock() {
        if (this == ChaosPersists.MyAntBlock) return EntityAnt.class;
        if (this == ChaosPersists.MyRedAntBlock) return EntityRedAnt.class;
        if (this == ChaosPersists.MyRainbowAntBlock) return EntityRainbowAnt.class;
        if (this == ChaosPersists.MyUnstableAntBlock) return EntityUnstableAnt.class;
        if (this == ChaosPersists.TermiteBlock) return Termite.class;
        return null;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public static Entity spawnCreature(World world, String name, double x, double y, double z) {
        Entity entity = null;

        net.minecraft.util.ResourceLocation loc =
                new net.minecraft.util.ResourceLocation("chaospersists",
                        name.toLowerCase().replace(" ", "_"));

        entity = EntityList.createEntityByIDFromName(loc, world);

        if (entity != null) {
            entity.setLocationAndAngles(x, y, z,
                    world.rand.nextFloat() * 360.0F, 0.0F);
            world.spawnEntity(entity);
            ((EntityLiving) entity).playLivingSound();
        }

        return entity;
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor() {
        return ColorizerGrass.getGrassColor(0.5D, 1.0D);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return this.getBlockColor();
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
        int r = 0;
        int g = 0;
        int b = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos sample = pos.add(dx, 0, dz);
                int color = world.getBiome(sample).getGrassColorAtPos(sample);

                r += (color >> 16) & 255;
                g += (color >> 8) & 255;
                b += color & 255;
            }
        }

        return ((r / 9) << 16) | ((g / 9) << 8) | (b / 9);
    }
}
