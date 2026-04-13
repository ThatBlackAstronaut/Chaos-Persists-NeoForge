package com.astryxion.chaospersists.block;

import com.astryxion.chaospersists.core.ChaosPersists;

import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrystalWorkbench extends BlockWorkbench {

    public CrystalWorkbench(float hardness, float resistance) {
        super();
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setHardness(hardness);
        this.setResistance(resistance);
    }

    // ===== RIGHT CLICK =====

    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
                                    IBlockState state,
                                    EntityPlayer player,
                                    EnumHand hand,
                                    EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {

        if (world.isRemote) {
            return true;
        }

        player.openGui(ChaosPersists.instance, 1, world,
                pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    // ===== TRANSPARENCY =====

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
