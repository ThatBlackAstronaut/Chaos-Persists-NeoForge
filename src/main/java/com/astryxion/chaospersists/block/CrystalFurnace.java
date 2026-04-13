package com.astryxion.chaospersists.block;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.tileentity.TileEntityCrystalFurnace;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class CrystalFurnace extends Block implements ITileEntityProvider {
    private static boolean keepInventory;

    public static final PropertyDirection FACING =
            PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public static final PropertyBool LIT =
            PropertyBool.create("lit");

    public CrystalFurnace(float hardness, float resistance) {
        super(Material.ROCK);

        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setTickRandomly(true);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH)
                .withProperty(LIT, false));
    }

    // ================= BLOCKSTATE =================

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, LIT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        boolean lit = (meta & 4) != 0;

        return this.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(LIT, lit);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();
        if (state.getValue(LIT)) meta |= 4;
        return meta;
    }

    // ================= PLACEMENT =================

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos,
                                 IBlockState state,
                                 EntityLivingBase placer,
                                 ItemStack stack) {

        EnumFacing facing = placer.getHorizontalFacing().getOpposite();
        world.setBlockState(pos,
                state.withProperty(FACING, facing), 2);
    }

    // ================= GUI =================

    @Override
    public boolean onBlockActivated(World world, BlockPos pos,
                                    IBlockState state,
                                    EntityPlayer player,
                                    EnumHand hand,
                                    EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {

        if (world.isRemote) return true;

        player.openGui(ChaosPersists.instance, 0,
                world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    // ================= PARTICLES =================

    @Override
    public void randomDisplayTick(IBlockState state,
                                  World world,
                                  BlockPos pos,
                                  Random rand) {

        if (!state.getValue(LIT)) return;

        EnumFacing facing = state.getValue(FACING);

        double x = pos.getX() + 0.5;
        double y = pos.getY() + rand.nextDouble() * 0.6;
        double z = pos.getZ() + 0.5;
        double offset = 0.52;
        double randomOffset = rand.nextDouble() * 0.6 - 0.3;

        switch (facing) {
            case WEST:
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL,
                        x - offset, y, z + randomOffset, 0, 0, 0);
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME,
                        x - offset, y, z + randomOffset, 0, 0, 0);
                break;
            case EAST:
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL,
                        x + offset, y, z + randomOffset, 0, 0, 0);
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME,
                        x + offset, y, z + randomOffset, 0, 0, 0);
                break;
            case NORTH:
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL,
                        x + randomOffset, y, z - offset, 0, 0, 0);
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME,
                        x + randomOffset, y, z - offset, 0, 0, 0);
                break;
            case SOUTH:
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL,
                        x + randomOffset, y, z + offset, 0, 0, 0);
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME,
                        x + randomOffset, y, z + offset, 0, 0, 0);
                break;
        }
    }

    // ================= RENDER =================

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getLightValue(IBlockState state) {
        return state.getValue(LIT) ? 13 : 0;
    }

    // ================= TILE ENTITY =================

    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCrystalFurnace();
    }

    // ================= COMPARATOR =================

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state,
                                          World world,
                                          BlockPos pos) {

        return Container.calcRedstoneFromInventory(
                (IInventory) world.getTileEntity(pos));
    }

    public static void setKeepInventory(boolean keep) {
        keepInventory = keep;
    }

    /**
     * Vanilla furnace TE swaps vanilla blocks; crystal furnace must swap only its own lit property.
     */
    public static void setState(boolean active, World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof CrystalFurnace)) {
            return;
        }

        TileEntity te = world.getTileEntity(pos);
        keepInventory = true;

        world.setBlockState(pos, state.withProperty(LIT, active), 3);

        keepInventory = false;
        if (te != null) {
            te.validate();
            world.setTileEntity(pos, te);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepInventory) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof IInventory) {
                InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
                world.updateComparatorOutputLevel(pos, this);
            }
        }
        super.breakBlock(world, pos, state);
    }
}
