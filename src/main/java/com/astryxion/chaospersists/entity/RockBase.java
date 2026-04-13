/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RockBase
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class RockBase
extends EntityLiving {
    private static final DataParameter<Integer> ROCK_TYPE = EntityDataManager.createKey(RockBase.class, DataSerializers.VARINT);
    public int rock_type = 0;
    private double dx;
    private double dz;

    public RockBase(World par1World) {
        super(par1World);
        this.setSize(0.25f, 0.15f);
                this.isImmuneToFire = true;
        this.dz = 0.0;
        this.dx = 0.0;
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ROCK_TYPE, 0);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Entity e = par1DamageSource.getTrueSource();
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return false;
        }
        if (e != null && e instanceof EntityLivingBase) {
            this.playSound(net.minecraft.init.SoundEvents.ENTITY_ITEM_PICKUP, 0.75f, 2.25f);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    public int getRockType() {
        return this.getDataManager().get(ROCK_TYPE).intValue();
    }

    public void setRockType(int par1) {
        if (this.world == null) {
            return;
        }
        if (this.world.isRemote) {
            return;
        }
        this.getDataManager().set(ROCK_TYPE, par1);
    }

    public void placeRock(int par1) {
        this.rock_type = par1;
        this.setRockType(par1);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(1 + this.rock_type / 4));
        this.setHealth((float)(1 + this.rock_type / 4));
    }

    public int getTotalArmorValue() {
        return 0;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    public void onUpdate() {
        if (this.dx == 0.0 && this.dz == 0.0) {
            this.dx = this.posX;
            this.dz = this.posZ;
        }
        super.onUpdate();
        this.rotationPitch = 0.0f;
        this.rotationYawHead = 0.0f;
        this.rotationYaw = 0.0f;
        if (this.world.isRemote) {
            this.rock_type = this.getRockType();
        }
        if (!this.world.isRemote && this.rock_type == 0) {
            if (this.world.provider.getDimension() != ChaosPersists.getDimension(5)) {
                this.rock_type = 1;
                if (this.world.rand.nextInt(10) == 0) {
                    this.rock_type = 2;
                }
                if (this.world.rand.nextInt(20) == 0) {
                    this.rock_type = 3;
                }
                if (this.world.rand.nextInt(30) == 0) {
                    this.rock_type = 4;
                }
                if (this.world.rand.nextInt(40) == 0) {
                    this.rock_type = 5;
                }
                if (this.world.rand.nextInt(50) == 0) {
                    this.rock_type = 6;
                }
                if (this.world.rand.nextInt(100) == 0) {
                    this.rock_type = 7;
                }
                if (this.world.rand.nextInt(200) == 0) {
                    this.rock_type = 8;
                }
                if (this.world.rand.nextInt(500) == 0) {
                    this.rock_type = 9;
                }
                if (this.world.rand.nextInt(500) == 0) {
                    this.rock_type = 10;
                }
                if (this.world.rand.nextInt(500) == 0) {
                    this.rock_type = 11;
                }
                if (this.world.rand.nextInt(1000) == 0) {
                    this.rock_type = 12;
                }
            } else {
                this.rock_type = 9;
                if (this.world.rand.nextInt(3) == 0) {
                    this.rock_type = 10;
                }
                if (this.world.rand.nextInt(5) == 0) {
                    this.rock_type = 11;
                }
                if (this.world.rand.nextInt(10) == 0) {
                    this.rock_type = 12;
                }
            }
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(1 + this.rock_type / 4));
            this.setHealth((float)(1 + this.rock_type / 4));
        }
        if (!this.world.isRemote) {
            this.setRockType(this.rock_type);
        }
        if (this.world.isRemote) {
            if (this.rock_type == 9 && this.world.rand.nextInt(20) == 0) {
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0f), (double)(this.world.rand.nextFloat() / 10.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0f));
            }
            if (this.rock_type == 10 && this.world.rand.nextInt(20) == 0) {
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.VILLAGER_HAPPY, this.posX, this.posY + 0.25, this.posZ, (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0f), (double)(this.world.rand.nextFloat() / 2.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0f));
            }
            if (this.rock_type == 11 && this.world.rand.nextInt(20) == 0) {
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0f), (double)(this.world.rand.nextFloat() / 10.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0f));
            }
            if (this.rock_type == 12 && this.world.rand.nextInt(20) == 0) {
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY + 0.25, this.posZ, (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0f), (double)(this.world.rand.nextFloat() / 5.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 60.0f));
            }
        }
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return null;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return null;
    }

    protected float getSoundVolume() {
        return 0.65f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return null;
    }

    public boolean canDespawn() {
        return false;
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        return true;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public boolean canBePushed() {
        return true;
    }

    public void performHurtAnimation() {
        this.maxHurtTime = 0;
        this.hurtTime = 0;
        this.attackedAtYaw = 0.0f;
    }

    protected void onDeathUpdate() {
        this.setDead();
    }

    public void onDeath(DamageSource par1DamageSource) {
        this.setDead();
        if (this.rock_type == 1) {
            this.dropItemRand(ChaosPersists.MySmallRock, 1);
        }
        if (this.rock_type == 2) {
            this.dropItemRand(ChaosPersists.MyRock, 1);
        }
        if (this.rock_type == 3) {
            this.dropItemRand(ChaosPersists.MyRedRock, 1);
        }
        if (this.rock_type == 4) {
            this.dropItemRand(ChaosPersists.MyGreenRock, 1);
        }
        if (this.rock_type == 5) {
            this.dropItemRand(ChaosPersists.MyBlueRock, 1);
        }
        if (this.rock_type == 6) {
            this.dropItemRand(ChaosPersists.MyPurpleRock, 1);
        }
        if (this.rock_type == 7) {
            this.dropItemRand(ChaosPersists.MySpikeyRock, 1);
        }
        if (this.rock_type == 8) {
            this.dropItemRand(ChaosPersists.MyTNTRock, 1);
        }
        if (this.rock_type == 9) {
            this.dropItemRand(ChaosPersists.MyCrystalRedRock, 1);
        }
        if (this.rock_type == 10) {
            this.dropItemRand(ChaosPersists.MyCrystalGreenRock, 1);
        }
        if (this.rock_type == 11) {
            this.dropItemRand(ChaosPersists.MyCrystalBlueRock, 1);
        }
        if (this.rock_type == 12) {
            this.dropItemRand(ChaosPersists.MyCrystalTNTRock, 1);
        }
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)((ChaosPersists.ChaosRand.nextFloat() - ChaosPersists.ChaosRand.nextFloat()) / 3.0f), this.posY + 0.25, this.posZ + (double)((ChaosPersists.ChaosRand.nextFloat() - ChaosPersists.ChaosRand.nextFloat()) / 3.0f), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("ButterflyType", this.rock_type);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.rock_type = par1NBTTagCompound.getInteger("ButterflyType");
    }
}

