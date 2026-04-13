/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.CrystalAntBlock
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/*
 * Exception performing whole class analysis ignored.
 */
public class CrystalAntBlock
extends Block {
    public CrystalAntBlock(int par1) {
        super(Material.GRASS);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    /**
     * 1.12.2 tick signature (the old int,int,int version is never called).
     * Matches the behavior used by {@link AntBlock}: daytime only, air above, cap nearby mobs, spawn 2-7.
     */
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isRemote) return;

        // Block above must be air
        if (worldIn.getBlockState(pos.up()).getBlock() != Blocks.AIR) return;

        // Daytime only (same as AntBlock fix)
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

        if (worldIn.getEntitiesWithinAABB(entityClass, aabb).size() > 20) return;

        int count = rand.nextInt(6) + 2;
        for (int i = 0; i < count; i++) {
            spawnCreature(worldIn, mobName,
                    pos.getX() + 0.5D,
                    pos.getY() + 1.01D,
                    pos.getZ() + 0.5D);
        }
    }

    /** Returns spawn entity name, or null if disabled by config. */
    private String getMobNameForBlock() {
        // CrystalAntBlock is only used for CrystalTermiteBlock in this 1.12.2 project right now.
        if (this == ChaosPersists.CrystalTermiteBlock) {
            return ChaosPersists.TermiteEnable != 0 ? "Termite" : null;
        }
        return null;
    }

    /** Returns entity class for nearby count. */
    @SuppressWarnings("unchecked")
    private Class<? extends Entity> getEntityClassForBlock() {
        if (this == ChaosPersists.CrystalTermiteBlock) {
            return com.astryxion.chaospersists.entity.Termite.class;
        }
        return null;
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        int howmany = 0;
        if (!par1World.isRemote) {
            if (par1World.isRaining()) {
                return;
            }
            Block bid = par1World.getBlockState(new net.minecraft.util.math.BlockPos(par2, par3 + 1, par4)).getBlock();
            if (bid == Blocks.AIR) {
                howmany = ChaosPersists.ChaosRand.nextInt(6) + 2;
                for (int i = 0; i < howmany; ++i) {
                    if (this == ChaosPersists.MyAntBlock) {
                        if (ChaosPersists.BlackAntEnable == 0) continue;
                        CrystalAntBlock.spawnCreature((World)par1World, (String)"Ant", (double)((double)par2 + 0.5), (double)((double)par3 + 1.01), (double)((double)par4 + 0.5));
                        continue;
                    }
                    if (this == ChaosPersists.MyRedAntBlock) {
                        if (ChaosPersists.RedAntEnable == 0) continue;
                        CrystalAntBlock.spawnCreature((World)par1World, (String)"Red Ant", (double)((double)par2 + 0.5), (double)((double)par3 + 1.01), (double)((double)par4 + 0.5));
                        continue;
                    }
                    if (this == ChaosPersists.MyUnstableAntBlock) {
                        if (ChaosPersists.UnstableAntEnable == 0) continue;
                        CrystalAntBlock.spawnCreature((World)par1World, (String)"Unstable Ant", (double)((double)par2 + 0.5), (double)((double)par3 + 1.01), (double)((double)par4 + 0.5));
                        continue;
                    }
                    if (this == ChaosPersists.TermiteBlock) {
                        if (ChaosPersists.TermiteEnable == 0) continue;
                        CrystalAntBlock.spawnCreature((World)par1World, (String)"Termite", (double)((double)par2 + 0.5), (double)((double)par3 + 1.01), (double)((double)par4 + 0.5));
                        continue;
                    }
                    if (this == ChaosPersists.CrystalTermiteBlock) {
                        if (ChaosPersists.TermiteEnable == 0) continue;
                        CrystalAntBlock.spawnCreature((World)par1World, (String)"Termite", (double)((double)par2 + 0.5), (double)((double)par3 + 1.01), (double)((double)par4 + 0.5));
                        continue;
                    }
                    if (ChaosPersists.RainbowAntEnable == 0) continue;
                    CrystalAntBlock.spawnCreature((World)par1World, (String)"Rainbow Ant", (double)((double)par2 + 0.5), (double)((double)par3 + 1.01), (double)((double)par4 + 0.5));
                }
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName(new net.minecraft.util.ResourceLocation("chaospersists", par1.toLowerCase().replace(" ", "_")), par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

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

