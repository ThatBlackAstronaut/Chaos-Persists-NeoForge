/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.ChaosTeleporter
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockSand
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 *  net.minecraft.world.WorldServer
 */
package com.astryxion.chaospersists.core;

import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public class ChaosTeleporter
extends Teleporter {
    private WorldServer world;
    private World oldWorld;
    private Random random;
    private int newdim;

    public ChaosTeleporter(WorldServer par1WorldServer, int dim, World par2World) {
        super(par1WorldServer);
        this.world = par1WorldServer;
        this.oldWorld = par2World;
        this.random = new Random(par1WorldServer.getSeed());
        this.newdim = dim;
    }

    /** Called by transferPlayerToDimension in 1.12.2 — must override this for entity teleports to work. */
    @Override
    public void placeInPortal(Entity entityIn, float rotationYaw) {
        this.justPutMe(entityIn);
    }

    public void placeInPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        this.justPutMe(par1Entity);
    }

    public boolean placeInExistingPortal(Entity par1Entity, double par2, double par4, double par6, float par8) {
        this.justPutMe(par1Entity);
        return true;
    }

    public boolean makePortal(Entity par1Entity) {
        return true;
    }

    private boolean isGroundBlock(Block bid) {
        if (bid == Blocks.AIR) {
            return false;
        }
        if (bid == Blocks.DIRT) {
            return true;
        }
        if (bid == Blocks.GRASS) {
            return true;
        }
        if (bid == Blocks.STONE) {
            return true;
        }
        if (bid == Blocks.END_STONE) {
            return true;
        }
        if (bid == Blocks.NETHERRACK) {
            return true;
        }
        if (bid == Blocks.COBBLESTONE) {
            return true;
        }
        if (bid == Blocks.SAND) {
            return true;
        }
        if (bid == Blocks.SANDSTONE) {
            return true;
        }
        if (bid == Blocks.FARMLAND) {
            return true;
        }
        return false;
    }

    /**
     * Find a safe feet Y: block at (x, feetY-1, z) is solid and not liquid;
     * blocks at (x, feetY, z) and (x, feetY+1, z) are not solid and not liquid (standing + head room).
     */
    private int findSafeFeetY(int posX, int posZ) {
        int maxY = Math.min(255, this.world.getActualHeight() - 1);
        for (int feetY = maxY; feetY >= 2; feetY--) {
            BlockPos ground = new BlockPos(posX, feetY - 1, posZ);
            BlockPos stand = new BlockPos(posX, feetY, posZ);
            BlockPos head = new BlockPos(posX, feetY + 1, posZ);
            net.minecraft.block.state.IBlockState groundState = this.world.getBlockState(ground);
            net.minecraft.block.state.IBlockState standState = this.world.getBlockState(stand);
            net.minecraft.block.state.IBlockState headState = this.world.getBlockState(head);
            if (groundState.getMaterial().isSolid() && !groundState.getMaterial().isLiquid()
                    && !standState.getMaterial().isSolid() && !standState.getMaterial().isLiquid()
                    && !headState.getMaterial().isSolid() && !headState.getMaterial().isLiquid()) {
                return feetY;
            }
        }
        return -1;
    }

    public boolean justPutMe(Entity par1Entity) {
        if (this.world.isRemote) {
            return true;
        }
        int posX = (int) par1Entity.posX;
        int posZ = (int) par1Entity.posZ;
        int posY = findSafeFeetY(posX, posZ);

        for (int i = 0; posY < 0 && i < 200; i++) {
            int spread = 2 + i / 20;
            posX = (int) par1Entity.posX + this.world.rand.nextInt(2 * spread + 1) - spread;
            posZ = (int) par1Entity.posZ + this.world.rand.nextInt(2 * spread + 1) - spread;
            posY = findSafeFeetY(posX, posZ);
        }

        if (posY < 0 && this.newdim == ChaosPersists.getDimension(4)) {
            posX = (int) par1Entity.posX;
            posZ = (int) par1Entity.posZ;
            BlockPos ground8 = new BlockPos(posX, 8, posZ);
            if (this.world.getBlockState(ground8).getMaterial().isSolid()) {
                posY = 9;
            }
        }

        if (posY < 0) {
            posY = Math.min(255, this.world.getActualHeight() - 1);
        }

        double oldX = par1Entity.posX;
        double oldY = par1Entity.posY;
        double oldZ = par1Entity.posZ;
        double newX = posX + 0.5D;
        double newZ = posZ + 0.5D;
        double newY = (double) posY;

        par1Entity.setLocationAndAngles(newX, newY, newZ, par1Entity.rotationYaw, par1Entity.rotationPitch);
        par1Entity.motionX = 0.0D;
        par1Entity.motionY = 0.0D;
        par1Entity.motionZ = 0.0D;
        if (par1Entity instanceof EntityPlayerMP) {
            ((EntityPlayerMP) par1Entity).fallDistance = 0.0f;
        }

      MinecraftServer minecraftserver = this.oldWorld.getMinecraftServer();
      WorldServer worldserver = minecraftserver.getWorld(this.oldWorld.provider.getDimension());
      WorldServer worldserver1 = minecraftserver.getWorld(this.newdim);

      if ((par1Entity instanceof EntityPlayer)) {
        EntityPlayer ep = (EntityPlayer)par1Entity;
        AxisAlignedBB bb = new AxisAlignedBB(oldX - 24.0D, oldY - 12.0D, oldZ - 24.0D, oldX + 24.0D, oldY + 12.0D, oldZ + 24.0D);
        List var5 = this.oldWorld.getEntitiesWithinAABB(EntityTameable.class, bb);
        Iterator var2 = var5.iterator();

        while (var2.hasNext())
        {
          Entity var3 = (Entity)var2.next();
          EntityTameable et = (EntityTameable)var3;

          if (!et.isSitting())
          {
            String p1 = ep.getUniqueID().toString();
            UUID ownerId = et.getOwnerId();
            String p2 = ownerId != null ? ownerId.toString() : null;
            if (((p1 != null) && (p2 != null) && (p1.equals(p2))) || (et.isOwner(ep))) {
              sendToThisDimension(var3, newX, newY, newZ, (int)ep.rotationYaw);
            }

          }

        }

      }

      worldserver.resetUpdateEntityTick();
      worldserver1.resetUpdateEntityTick();
      return true;
    }

    public void sendToThisDimension(Entity e, double newX, double newY, double newZ, int ro) {
        if (this.oldWorld.isRemote) {
            return;
        }
        e.world.removeEntity(e);
        e.isDead = false;
        e.setLocationAndAngles(newX, newY, newZ, (float)ro, 0.0f);
        e.motionZ = 0.0;
        e.motionY = 0.0;
        e.motionX = 0.0;
        e.setWorld((World)this.world);
        ResourceLocation key = EntityList.getKey(e);
        Entity var6 = key != null ? EntityList.createEntityByIDFromName(key, this.world) : null;
        if (var6 != null) {
            NBTTagCompound nbt = new NBTTagCompound();
            e.writeToNBT(nbt);
            var6.readFromNBT(nbt);
            var6.setLocationAndAngles(newX, newY, newZ, (float)ro, 0.0f);
            var6.motionZ = 0.0;
            var6.motionY = 0.0;
            var6.motionX = 0.0;
            var6.setWorld((World)this.world);
            this.world.spawnEntity(var6);
        }
        e.isDead = true;
    }

    public void removeStalePortalLocations(long par1) {
    }
}

