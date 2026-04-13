package com.astryxion.chaospersists.world.ore;

import java.util.Random;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OreGenericEgg extends BlockFalling {

    public OreGenericEgg() {
        this(0);
    }

    public OreGenericEgg(int oldid) {
        super();
        this.setHardness(0.6f);
        this.setResistance(3.0f);
        this.setSoundType(SoundType.GROUND);
        this.setHarvestLevel("shovel", 0);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    // ===== XP DROP =====

    public void dropBlockAsItemWithChance(World world, BlockPos pos,
                                          IBlockState state, float chance, int fortune) {

        super.dropBlockAsItemWithChance(world, pos, state, chance, fortune);

        int xp = 5 + world.rand.nextInt(3) + world.rand.nextInt(3);

        if (world.rand.nextInt(2) == 1) {
            this.dropXpOnBlockBreak(world, pos, xp);
        }
    }

    // ===== RENDERING (1.12.2 FIX) =====

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
}
