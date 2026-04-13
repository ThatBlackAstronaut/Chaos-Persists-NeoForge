/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.RTPBlock
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.init.Blocks
 *  net.minecraft.network.NetHandlerPlayServer
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;

public class RTPBlock
extends Block {
    public RTPBlock() { this(0); }
    public RTPBlock(int i) {
        super(Material.ROCK);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }

    public void onEntityWalking(World world, int par2, int par3, int par4, Entity par5Entity) {
        if (par5Entity instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par5Entity;
            EntityPlayerMP mp = null;
            if (par5Entity instanceof EntityPlayerMP) {
                mp = (EntityPlayerMP)par5Entity;
            }
            int x = par2;
            int y = par3;
            int z = par4;
            boolean found = false;
            block0 : for (int tries = 0; tries < 1000 && !found; ++tries) {
                x = world.rand.nextInt(2) == 0 ? par2 + 16 + world.rand.nextInt(8) - world.rand.nextInt(8) : par2 - 16 + world.rand.nextInt(8) - world.rand.nextInt(8);
                z = world.rand.nextInt(2) == 0 ? par4 + 16 + world.rand.nextInt(8) - world.rand.nextInt(8) : par4 - 16 + world.rand.nextInt(8) - world.rand.nextInt(8);
                for (y = par3 - 4; y <= par3 + 4; ++y) {
                    net.minecraft.block.state.IBlockState stateBelow = world.getBlockState(new net.minecraft.util.math.BlockPos(x, y - 1, z));
                    if (!stateBelow.getBlock().getMaterial(stateBelow).isSolid() || world.getBlockState(new net.minecraft.util.math.BlockPos(x, y, z)).getBlock() != Blocks.AIR || world.getBlockState(new net.minecraft.util.math.BlockPos(x, y + 1, z)).getBlock() != Blocks.AIR) continue;
                    found = true;
                    continue block0;
                }
            }
            if (found) {
                if (mp != null) {
                    mp.connection.setPlayerLocation((double)((float)x + 0.5f), (double)y, (double)((float)z + 0.5f), p.rotationYaw, 0.0f);
                } else {
                    p.setLocationAndAngles((double)((float)x + 0.5f), (double)y, (double)((float)z + 0.5f), p.rotationYaw, 0.0f);
                }
                for (int var3 = 0; var3 < 6; ++var3) {
                    world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (double)((float)x + 0.5f), (double)((float)y + 2.25f), (double)((float)z + 0.5f), 0.0, 0.0, 0.0);
                    world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (double)((float)x + 0.5f), (double)((float)y + 2.25f), (double)((float)z + 0.5f), 0.0, 0.0, 0.0);
                    world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, (double)((float)x + 0.5f), (double)((float)y + 2.25f), (double)((float)z + 0.5f), 0.0, 0.0, 0.0);
                }
                p.world.playSound(null, p.posX, p.posY, p.posZ, net.minecraft.init.SoundEvents.ENTITY_GENERIC_EXPLODE, p.getSoundCategory(), 1.0f, 1.5f);
            }
        }
    }}

