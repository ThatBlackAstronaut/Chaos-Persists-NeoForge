package com.astryxion.chaospersists.world.ore;

import com.astryxion.chaospersists.core.ChaosPersists;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreCrystalCrystal extends Block {

    public OreCrystalCrystal(float lightLevel, float hardness, float resistance) {
        super(Material.ROCK);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        this.setLightLevel(lightLevel);
        this.setTickRandomly(true);
    }

    // ===== PARTICLES =====

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
        if (world.rand.nextInt(20) == 0) {
            sparkle(world, pos);
        }
    }

    private void sparkle(World world, BlockPos pos) {

        float dx = 0.5f;
        float dy = 0.5f;
        float dz = 0.5f;

        if (this == ChaosPersists.TigersEye) {
            world.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME,
                    pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0f,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0f,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0f);
        } else {
            world.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK,
                    pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0f,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0f,
                    (world.rand.nextFloat() - world.rand.nextFloat()) / 4.0f);
        }
    }

    // ===== RENDERING (1.12.2 CORRECT) =====

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    // ===== EXPLOSION =====

    public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) {
        if (this == ChaosPersists.CrystalCrystal
                && !world.isRemote
                && world.rand.nextInt(10) == 1) {

            world.newExplosion((Entity) null,
                    pos.getX() + 0.5f,
                    pos.getY() + 0.5f,
                    pos.getZ() + 0.5f,
                    1.0f,
                    true,
                    world.getGameRules().getBoolean("mobGriefing"));
        }
    }

    // ===== XP DROP =====

    public void dropBlockAsItemWithChance(World world, BlockPos pos,
                                          IBlockState state, float chance, int fortune) {

        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

        int xp = 5 + world.rand.nextInt(5) + world.rand.nextInt(10);

        if (pos.getY() < 40) {
            this.dropXpOnBlockBreak(world, pos, xp);
        }
    }

    public int quantityDropped(Random random) {
        if (this != ChaosPersists.TigersEye) {
            return 1;
        }
        return random.nextInt(2);
    }
}
