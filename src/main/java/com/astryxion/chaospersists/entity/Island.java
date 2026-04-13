/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Island
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.Triffid
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockBush
 *  net.minecraft.block.BlockMycelium
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.Triffid;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockMycelium;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

/*
 * Exception performing whole class analysis ignored.
 */
public class Island
extends EntityAnimal {
    private float dir = 0.0f;
    private float speed = 0.1f;
    private int radius = 5;
    private int depth = 3;
    private int timer = 73;
    private int just_spawned = 1;
    private int ticker = 0;
    private int once = 1;
    private double myX;
    private double myY;
    private double myZ;
    private int dirchange;

    public Island(World par1World) {
        super(par1World);
        this.setSize(0.5f, 0.5f);
        this.ticker = par1World.rand.nextInt(50);
        this.dirchange = this.world.rand.nextInt(2500);
    }

    public void onUpdate() {
        super.onUpdate();
        this.motionZ = 0.0;
        this.motionY = 0.0;
        this.motionX = 0.0;
        if (this.world.isRemote) {
            return;
        }
        if (this.once != 0) {
            this.myX = this.posX;
            this.myY = this.posY;
            this.myZ = this.posZ;
            this.once = 0;
        }
        if (this.just_spawned != 0) {
            this.dir = this.world.rand.nextFloat() * 3.1415927f;
            if (this.world.rand.nextInt(2) == 1) {
                this.dir *= -1.0f;
            }
            if (this.world.rand.nextInt(40) != 1) {
                this.radius = 3 + this.world.rand.nextInt(4);
                this.depth = 2 + this.world.rand.nextInt(3);
                this.speed = this.world.rand.nextFloat() / 50.0f * (float)ChaosPersists.IslandSpeedFactor;
            } else {
                this.radius = 6 + this.world.rand.nextInt(5);
                this.depth = 3 + this.world.rand.nextInt(4);
                this.speed = this.world.rand.nextFloat() / 200.0f * (float)ChaosPersists.IslandSpeedFactor;
            }
            this.create_island();
            this.ticker = this.world.rand.nextInt(50);
            this.dirchange = this.world.rand.nextInt(10000);
        }
        ++this.ticker;
        if (this.ticker >= this.timer) {
            this.update_island();
            this.ticker = 0;
        }
        --this.dirchange;
        if (this.dirchange <= 0) {
            this.dirchange = this.world.rand.nextInt(5000);
            this.dir = this.world.rand.nextFloat() * 3.1415927f;
            if (this.world.rand.nextInt(2) == 1) {
                this.dir *= -1.0f;
            }
        }
        this.just_spawned = 0;
    }

    public void onLivingUpdate() {
        if (this.world.isRemote) {
            super.onLivingUpdate();
        }
    }

    protected void updateAITick() {
    }

    protected void updateAITasks() {
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    protected boolean canDespawn() {
        return false;
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.just_spawned = par1NBTTagCompound.getInteger("JustSpawned");
        this.depth = par1NBTTagCompound.getInteger("Idepth");
        this.radius = par1NBTTagCompound.getInteger("Iradius");
        this.speed = par1NBTTagCompound.getFloat("Ispeed");
        this.dir = par1NBTTagCompound.getFloat("Idir");
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("JustSpawned", this.just_spawned);
        par1NBTTagCompound.setInteger("Idepth", this.depth);
        par1NBTTagCompound.setInteger("Iradius", this.radius);
        par1NBTTagCompound.setFloat("Ispeed", this.speed);
        par1NBTTagCompound.setFloat("Idir", this.dir);
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }

    private void create_island() {
        double deltadir = 0.10471975333333333;
        double deltamag = 0.3499999940395355;
        int ixlast = 0;
        int izlast = 0;
        int xoff = 0;
        int zoff = 0;
        for (int i = 0; i < this.depth; ++i) {
            izlast = 0;
            ixlast = 0;
            for (double curdir = -3.1415926; curdir < 3.1415926; curdir += deltadir) {
                double tradius = this.radius;
                for (double h = 0.75; h < (tradius /= (double)(i + 1)); h += deltamag) {
                    int ix = (int)(this.posX + Math.cos(curdir + (double)this.dir) * h);
                    int iz = (int)(this.posZ + Math.sin(curdir + (double)this.dir) * h);
                    if (ix == ixlast && iz == izlast) continue;
                    ixlast = ix;
                    izlast = iz;
                    if (i == 0) {
                        Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 1, iz)).getBlock();
                        if (bid == Blocks.AIR) {
                            if (this.world.rand.nextInt(5000) == 1) {
                                this.world.setBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 1, iz), Blocks.LAVA.getDefaultState(), 3);
                                continue;
                            }
                            this.FastSetBlock(ix, (int)this.posY - i + 1, iz, (Block)Blocks.MYCELIUM);
                            if (this.world.rand.nextInt(20) != 1 || this.world.getBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 2, iz)).getBlock() != Blocks.AIR) continue;
                            if (this.world.rand.nextInt(2) == 1) {
                                this.world.setBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 2, iz), Blocks.BROWN_MUSHROOM.getDefaultState(), 3);
                                continue;
                            }
                            this.world.setBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 2, iz), Blocks.RED_MUSHROOM.getDefaultState(), 3);
                            continue;
                        }
                        if (bid != Blocks.BEDROCK) continue;
                        this.setDead();
                        return;
                    }
                    if (this.world.rand.nextInt(10) == 1) {
                        this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.DIAMOND_ORE);
                        continue;
                    }
                    this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.END_STONE);
                }
            }
        }
        if (this.posX < 0.0) {
            xoff = -1;
        }
        if (this.posZ < 0.0) {
            zoff = -1;
        }
        this.world.setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + xoff, (int)this.posY, (int)this.posZ + zoff), Blocks.AIR.getDefaultState(), 3);
        this.FastSetBlock((int)this.posX + xoff, (int)this.posY, (int)this.posZ + zoff, Blocks.AIR);
    }

    private void update_island() {
        AxisAlignedBB bb;
        Iterator var2;
        List var5;
        double deltadir = 0.10471975333333333;
        double deltamag = 0.3499999940395355;
        double pi2 = 1.57079632675;
        int ixlast = 0;
        int izlast = 0;
        int xoff = 0;
        int zoff = 0;
        this.myX += (double)this.speed * Math.cos(this.dir);
        this.myZ += (double)this.speed * Math.sin(this.dir);
        int mx = (int)this.myX;
        int mz = (int)this.myZ;
        int px = (int)this.posX;
        int pz = (int)this.posZ;
        if (mx != px || mz != pz) {
            double h;
            int ix;
            int i;
            double curdir;
            int iz;
            Block bid;
            double tradius;
            for (i = 0; i < this.depth; ++i) {
                izlast = 0;
                ixlast = 0;
                for (curdir = -3.3; curdir < 3.3; curdir += deltadir / 2.0) {
                    tradius = this.radius;
                    for (h = 0.75; h < (tradius /= (double)(i + 1)); h += deltamag) {
                    }
                    if ((h -= deltamag) < 0.75) {
                        h = 0.75;
                    }
                    while (h < tradius + deltamag) {
                        ix = (int)(this.posX + Math.cos(curdir + (double)this.dir) * h);
                        iz = (int)(this.posZ + Math.sin(curdir + (double)this.dir) * h);
                        if (ix != ixlast || iz != izlast) {
                            ixlast = ix;
                            izlast = iz;
                            if (i == 0 && ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY + 1 + 1, iz)).getBlock()) == Blocks.RED_MUSHROOM || bid == Blocks.BROWN_MUSHROOM)) {
                                this.FastSetBlock(ix, (int)this.posY + 1 + 1, iz, Blocks.AIR);
                            }
                            this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.AIR);
                        }
                        h += deltamag / 2.0;
                    }
                }
            }
            if (this.posX < 0.0) {
                xoff = -1;
            }
            if (this.posZ < 0.0) {
                zoff = -1;
            }
            this.world.setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + xoff, (int)this.posY, (int)this.posZ + zoff), Blocks.END_STONE.getDefaultState(), 3);
            this.posX = (int)this.myX;
            this.posX = this.myX < 0.0 ? (this.posX -= 0.5) : (this.posX += 0.5);
            this.posZ = (int)this.myZ;
            this.posZ = this.myZ < 0.0 ? (this.posZ -= 0.5) : (this.posZ += 0.5);
            for (i = 0; i < this.depth; ++i) {
                izlast = 0;
                ixlast = 0;
                for (curdir = -3.1415926; curdir < 3.1415926; curdir += deltadir) {
                    tradius = this.radius;
                    for (h = 0.75; h < (tradius /= (double)(i + 1)); h += deltamag) {
                    }
                    if ((h -= deltamag * 3.0) < 0.75) {
                        h = 0.75;
                    }
                    while (h < tradius) {
                        ix = (int)(this.posX + Math.cos(curdir + (double)this.dir) * h);
                        iz = (int)(this.posZ + Math.sin(curdir + (double)this.dir) * h);
                        if (ix != ixlast || iz != izlast) {
                            ixlast = ix;
                            izlast = iz;
                            if (i == 0) {
                                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 1, iz)).getBlock();
                                if (bid == Blocks.AIR) {
                                    if (this.world.rand.nextInt(5000) == 1) {
                                        this.world.setBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 1, iz), Blocks.LAVA.getDefaultState(), 3);
                                    } else {
                                        this.FastSetBlock(ix, (int)this.posY - i + 1, iz, (Block)Blocks.MYCELIUM);
                                        if (this.world.rand.nextInt(20) == 1 && this.world.getBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 2, iz)).getBlock() == Blocks.AIR) {
                                            if (this.world.rand.nextInt(2) == 1) {
                                                this.world.setBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 2, iz), Blocks.BROWN_MUSHROOM.getDefaultState(), 3);
                                            } else {
                                                this.world.setBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 2, iz), Blocks.RED_MUSHROOM.getDefaultState(), 3);
                                            }
                                        }
                                    }
                                } else if (bid == Blocks.BEDROCK) {
                                    this.setDead();
                                    return;
                                }
                            } else {
                                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(ix, (int)this.posY - i + 1, iz)).getBlock();
                                if (bid == Blocks.STONE) {
                                    if (!this.world.isRemote) {
                                        this.world.createExplosion((Entity)this, (double)ix, this.posY - (double)i + 1.0, (double)iz, 5.0f, true);
                                    }
                                } else if (this.world.rand.nextInt(10) == 1) {
                                    this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.DIAMOND_ORE);
                                } else {
                                    this.FastSetBlock(ix, (int)this.posY - i + 1, iz, Blocks.END_STONE);
                                }
                            }
                        }
                        h += deltamag;
                    }
                }
            }
            xoff = 0;
            if (this.posX < 0.0) {
                xoff = -1;
            }
            zoff = 0;
            if (this.posZ < 0.0) {
                zoff = -1;
            }
            this.world.setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + xoff, (int)this.posY, (int)this.posZ + zoff), Blocks.AIR.getDefaultState(), 3);
            this.FastSetBlock((int)this.posX + xoff, (int)this.posY, (int)this.posZ + zoff, Blocks.AIR);
        }
        if (this.world.rand.nextInt(2 + 2000 / this.timer) == 1 && !(var2 = (var5 = this.world.getEntitiesWithinAABB(Triffid.class, bb = new AxisAlignedBB((double)(this.posX - 10.0), (double)(this.posY - 5.0), (double)(this.posZ - 10.0), (double)(this.posX + 10.0), (double)(this.posY + 5.0), (double)(this.posZ + 10.0)))).iterator()).hasNext()) {
            EntityCreature newent = (EntityCreature)Island.spawnCreature((World)this.world, (String)"Triffid", (double)this.posX, (double)(this.posY + 2.01), (double)this.posZ);
        }
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName(new net.minecraft.util.ResourceLocation(par1), par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock((Block)ChaosPersists.MyIslandBlock);
    }

    public void FastSetBlock(int ix, int iy, int iz, Block id) {
        ChaosPersists.setBlockFast((World)this.world, (int)ix, (int)iy, (int)iz, (Block)id, (int)0, (int)3);
    }
}

