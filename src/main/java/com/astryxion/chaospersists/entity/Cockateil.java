/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Cockateil
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.util.ChunkCoordinates
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class Cockateil
extends EntityAnimal {
    private static final DataParameter<Integer> BIRD_TYPE = EntityDataManager.createKey(Cockateil.class, DataSerializers.VARINT);
    private BlockPos currentFlightTarget = null;
    public int birdtype;
    private boolean killedByPlayer = false;
    private static final ResourceLocation texture1 = new ResourceLocation("chaospersists", "textures/entity/bird1.png");
    private static final ResourceLocation texture2 = new ResourceLocation("chaospersists", "textures/entity/bird2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("chaospersists", "textures/entity/bird3.png");
    private static final ResourceLocation texture4 = new ResourceLocation("chaospersists", "textures/entity/bird4.png");
    private static final ResourceLocation texture5 = new ResourceLocation("chaospersists", "textures/entity/bird5.png");
    private static final ResourceLocation texture6 = new ResourceLocation("chaospersists", "textures/entity/bird6.png");
    private int stuck_count = 0;
    private int lastX = 0;
    private int lastZ = 0;
    private int flyup = 0;

    public Cockateil(World par1World) {
        super(par1World);
        this.setSize(0.5f, 0.5f);
                this.experienceValue = 2;
        this.isImmuneToFire = false;
            }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33000001311302185);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0);
    }

    public ResourceLocation getTexture() {
        this.birdtype = this.getBirdType();
        switch (this.birdtype) {
            case 0: {
                return texture1;
            }
            case 1: {
                return texture2;
            }
            case 2: {
                return texture3;
            }
            case 3: {
                return texture4;
            }
            case 4: {
                return texture5;
            }
            case 5: {
                return texture6;
            }
        }
        return null;
    }

    protected void entityInit() {
        super.entityInit();
        this.birdtype = this.rand.nextInt(6);
        this.getDataManager().register(BIRD_TYPE, this.birdtype);
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return true;
    }

    public int getBirdType() {
        return this.getDataManager().get(BIRD_TYPE).intValue();
    }

    public void setBirdType(int par1) {
        this.getDataManager().set(BIRD_TYPE, par1);
    }

    protected float getSoundVolume() {
        return 0.55f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        if (this.world.isDaytime() && !this.world.isRaining()) {
            return com.astryxion.chaospersists.core.ChaosSounds.BIRDS;
        }
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return com.astryxion.chaospersists.core.ChaosSounds.DUCK_HURT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.DUCK_HURT;
    }

    public boolean canBePushed() {
        return true;
    }

    public int mygetMaxHealth() {
        return 2;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Entity e = par1DamageSource.getTrueSource();
        if (e != null && e instanceof EntityPlayer) {
            this.killedByPlayer = true;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        } else {
            this.motionY = this.posY < (double)this.currentFlightTarget.getY() ? (this.motionY *= 0.7) : (this.motionY *= 0.5);
        }
    }

    public int getAttackStrength(Entity par1Entity) {
        return 1;
    }

    public void setFlyUp() {
        this.flyup = 2;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 35;
        int stayup = 0;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.world.provider.getDimension() == ChaosPersists.getDimension(4)) {
            stayup = 2;
        }
        if (this.lastX == (int)this.posX && this.lastZ == (int)this.posZ) {
            ++this.stuck_count;
        } else {
            this.stuck_count = 0;
            this.lastX = (int)this.posX;
            this.lastZ = (int)this.posZ;
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.stuck_count > 40 || this.rand.nextInt(250) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 4.1f) {
            Block bid = Blocks.STONE;
            this.stuck_count = 0;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.rand.nextInt(8) + 5 - this.flyup * 2;
                xdir = this.rand.nextInt(8) + 5 - this.flyup * 2;
                if (this.rand.nextInt(2) == 0) {
                    zdir = - zdir;
                }
                if (this.rand.nextInt(2) == 0) {
                    xdir = - xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(9 + stayup) - 5 + this.flyup, (int)this.posZ + zdir);
                bid = this.world.getBlockState(this.currentFlightTarget).getBlock();
                if (bid == Blocks.AIR && !this.canSeeTarget((double)this.currentFlightTarget.getX(), (double)this.currentFlightTarget.getY(), (double)this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.3 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.3 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.3 - this.motionX) * 0.25;
        this.motionY += (Math.signum(var3) * 0.699999 - this.motionY) * 0.200000001;
        this.motionZ += (Math.signum(var5) * 0.3 - this.motionZ) * 0.25;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
        float var8 = MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.8f;
        this.rotationYaw += var8 / 3.0f;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean getCanSpawnHere() {
        if (!this.world.isDaytime()) {
            return false;
        }
        if (this.world.provider.getDimension() == ChaosPersists.getDimension(4)) {
            return true;
        }
        if (this.posY < 50.0) {
            return false;
        }
        return true;
    }

    protected Item getDropItem() {
        this.birdtype = this.getBirdType();
        if (this.birdtype == 5 && this.killedByPlayer && this.world.rand.nextInt(3) == 1) {
            return ChaosPersists.MyRuby;
        }
        return Items.FEATHER;
    }

    public void initCreature() {
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("BirdType", this.getBirdType());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.birdtype = par1NBTTagCompound.getInteger("BirdType");
        this.setBirdType(this.birdtype);
    }
}

