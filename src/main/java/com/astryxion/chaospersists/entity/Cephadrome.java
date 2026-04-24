/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.Cephadrome
 *  com.astryxion.chaospersists.GammaMetroid
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.ItemChaosArmor
 *  com.astryxion.chaospersists.Kraken
 *  com.astryxion.chaospersists.Leon
 *  com.astryxion.chaospersists.Mothra
 *  com.astryxion.chaospersists.MyEntityAIWanderALot
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RenderInfo
 *  com.astryxion.chaospersists.WaterDragon
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.boss.EntityDragonPart
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.tileentity.MobSpawnerBaseLogic
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.entity.GammaMetroid;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.item.ItemChaosArmor;
import com.astryxion.chaospersists.entity.Kraken;
import com.astryxion.chaospersists.entity.Leon;
import com.astryxion.chaospersists.entity.Mothra;
import com.astryxion.chaospersists.util.MyEntityAIWanderALot;
import com.astryxion.chaospersists.util.MyUtils;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.render.RenderInfo;
import com.astryxion.chaospersists.entity.WaterDragon;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Cephadrome
extends EntityCreature {
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(Cephadrome.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> STATE2 = EntityDataManager.createKey(Cephadrome.class, DataSerializers.BYTE);
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double boatYawHead;
    private int damage_counter = 100;
    private int updateit = 1;
    private int color = 1;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private int wasfed;
    private int shouldattack = 0;
    private int wing_sound = 0;
    private int hit_by_player = 0;
    private int badmood = 0;
    private float moveSpeed = 0.25f;

    private Entity getRiddenByEntity() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public Cephadrome(World par1World) {
        super(par1World);
        this.setSize(2.5f, 2.25f);
                this.experienceValue = 200;
                this.isImmuneToFire = false;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 9.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
    }

    public Cephadrome(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4, par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(70.0);
    }

    public boolean shouldRiderSit() {
        return true;
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean canBeSteered() {
        return true;
    }

    @Override
    public boolean canPassengerSteer() {
        return true;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float f = 0.75f;
            passenger.setPosition(
                this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)),
                this.posY + this.getMountedYOffset() + passenger.getYOffset(),
                this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (!this.world.isRemote && this.getPassengers().isEmpty()) {
            this.setActivity(0);
            this.motionY = 0.0;
            this.noClip = false;
            this.setNoGravity(false);
            this.pushOutOfBlocks(this.posX, this.posY, this.posZ);
            MyUtils.enforceDragonMountGroundSafety(this);
        }
    }

    /**
     * Same steering pipeline as {@link ThePrinceTeen}: vanilla calls {@code travel} with synced rider input
     * (via {@code CPacketInput} from client). Flight used to live only in {@code onLivingUpdate}, which never ran
     * through this path, so {@code moveForward} stayed wrong on the server.
     */
    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isBeingRidden() && this.getControllingPassenger() instanceof EntityPlayer && this.getActivity() != 0) {
            EntityPlayer pp = (EntityPlayer)this.getControllingPassenger();
            if (pp.isDead) {
                this.removePassengers();
                this.setNoGravity(false);
                super.travel(strafe, vertical, forward);
                return;
            }
            this.setNoGravity(true);
            List list;
            Entity listEntity;
            double obstruction_factor = 0.0;
            double relative_g = 0.0;
            double max_speed = 1.15;
            double gh = 1.0;
            double rt = 0.0;
            double pi = 3.1415926545;
            double deltav = 0.0;
            double rdv;
            if (this.motionX < -2.0) {
                this.motionX = -2.0;
            }
            if (this.motionX > 2.0) {
                this.motionX = 2.0;
            }
            if (this.motionZ < -2.0) {
                this.motionZ = -2.0;
            }
            if (this.motionZ > 2.0) {
                this.motionZ = 2.0;
            }
            double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            gh = 1.55;
            Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
            if (bid != Blocks.AIR) {
                this.motionY += 0.07;
                this.posY += 0.1;
            } else {
                this.motionY -= 0.018;
            }
            obstruction_factor = 0.0;
            // Scan radius from speed, fixed bound — do not mutate dist in the for-condition (was infinite loop).
            int scanDist = 2 + (int)(velocity * 6.0);
            if (scanDist < 2) {
                scanDist = 2;
            }
            if (scanDist > 32) {
                scanDist = 32;
            }
            for (int k = 1; k < scanDist; ++k) {
                for (int i = 1; i < scanDist * 2; ++i) {
                    double dz;
                    double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))))).getBlock();
                    if (bid == Blocks.AIR) continue;
                    obstruction_factor += 0.04;
                }
            }
            this.motionY += obstruction_factor * 0.09;
            this.posY += obstruction_factor * 0.09;
            if (this.motionY > 2.0) {
                this.motionY = 2.0;
            }
            double d4 = pp.rotationYaw;
            d4 %= 360.0;
            while (d4 < 0.0) {
                d4 += 360.0;
            }
            double d5 = this.rotationYaw;
            d5 %= 360.0;
            while (d5 < 0.0) {
                d5 += 360.0;
            }
            for (relative_g = (d4 - d5) % 180.0; relative_g < 0.0; relative_g += 180.0) {
            }
            if (relative_g > 90.0) {
                relative_g -= 180.0;
            }
            if (velocity > 0.1) {
                d4 = 1.5 - velocity;
                if ((d4 = Math.abs(d4)) < 0.01) {
                    d4 = 0.01;
                }
                if (d4 > 0.9) {
                    d4 = 0.9;
                }
                this.rotationYaw = pp.rotationYaw + (float)(relative_g * d4);
            } else {
                this.rotationYaw = pp.rotationYaw;
            }
            relative_g = Math.abs(relative_g) * velocity;
            if (relative_g > 50.0) {
                relative_g = 0.0;
            }
            this.rotationPitch = this.motionY > 0.0 ? 360.0f - 2.0f * (float)velocity : 2.0f * (float)velocity;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            double rhm = Math.atan2(this.motionZ, this.motionX);
            double rhdir = Math.toRadians((pp.rotationYaw + 90.0f) % 360.0f);
            rt = 0.0;
            pi = 3.1415926545;
            deltav = 0.0;
            double im = pp.moveForward;
            if (ChaosPersists.flyup_keystate != 0) {
                this.motionY += 0.04;
                this.motionY += velocity * 0.05;
            }
            if ((rdv = Math.abs(rhm - rhdir) % (pi * 2.0)) > pi) {
                rdv -= pi * 2.0;
            }
            rdv = Math.abs(rdv);
            if (Math.abs(newvelocity) < 0.01) {
                rdv = 0.0;
            }
            if (rdv > 1.5) {
                newvelocity = -newvelocity;
            }
            if (Math.abs(im) > 0.0010000000474974513) {
                if (im > 0.0) {
                    deltav = 0.03;
                    if (max_speed > 0.85) {
                        deltav += 0.05;
                    }
                } else {
                    max_speed = 0.35;
                    deltav = -0.03;
                }
                if ((newvelocity += deltav) >= 0.0) {
                    if (newvelocity > max_speed) {
                        newvelocity = max_speed;
                    }
                    this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                    this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                } else {
                    if (newvelocity < -max_speed) {
                        newvelocity = -max_speed;
                    }
                    newvelocity = -newvelocity;
                    this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                    this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                }
            } else if (newvelocity >= 0.0) {
                this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
            } else {
                this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
            }
            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.985;
            this.motionY *= 0.94;
            this.motionZ *= 0.985;
            if (!this.world.isRemote && (list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.getEntityBoundingBox().expand(2.25, 2.0, 2.25))) != null && !list.isEmpty()) {
                for (int l = 0; l < list.size(); ++l) {
                    listEntity = (Entity)list.get(l);
                    if (listEntity == pp || listEntity.isDead || !listEntity.canBePushed()) continue;
                    listEntity.applyEntityCollision((Entity)this);
                }
            }
            return;
        }
        this.setNoGravity(false);
        super.travel(strafe, vertical, forward);
    }

    public int getTrackingRange() {
        return 128;
    }

    public int getUpdateFrequency() {
        return 10;
    }

    public boolean sendsVelocityUpdates() {
        return true;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ATTACKING, (byte)0);
        this.getDataManager().register(STATE2, (byte)0);
        this.setActivity(0);
        this.setAttacking(0);
        if (this.renderdata == null) {
            this.renderdata = new RenderInfo();
        }
        this.renderdata.rf1 = 0.0f;
        this.renderdata.rf2 = 0.0f;
        this.renderdata.rf3 = 0.0f;
        this.renderdata.rf4 = 0.0f;
        this.renderdata.ri1 = 0;
        this.renderdata.ri2 = 0;
        this.renderdata.ri3 = 0;
        this.renderdata.ri4 = 0;
    }

    public int mygetMaxHealth() {
        return 300;
    }

    public RenderInfo getRenderInfo() {
        return this.renderdata;
    }

    public void setRenderInfo(RenderInfo r) {
        this.renderdata.rf1 = r.rf1;
        this.renderdata.rf2 = r.rf2;
        this.renderdata.rf3 = r.rf3;
        this.renderdata.rf4 = r.rf4;
        this.renderdata.ri1 = r.ri1;
        this.renderdata.ri2 = r.ri2;
        this.renderdata.ri3 = r.ri3;
        this.renderdata.ri4 = r.ri4;
    }

    public int getTotalArmorValue() {
        return 16;
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.1;
    }

    public boolean isAIEnabled() {
        return true;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        if (this.getActivity() != 1 && this.rand.nextInt(6) == 1) {
            return com.astryxion.chaospersists.core.ChaosSounds.MOTHRA_WINGS;
        }
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSourceIn) {
        return com.astryxion.chaospersists.core.ChaosSounds.ALO_HURT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.ALO_DEATH;
    }

    protected float getSoundVolume() {
        return 1.5f;
    }

    public float getSoundPitch() {
        return 1.0f;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 2.5;
    }

    protected Item getDropItem() {
        return Items.BEEF;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(5) - (double)ChaosPersists.ChaosRand.nextInt(5), this.posY + 1.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(5) - (double)ChaosPersists.ChaosRand.nextInt(5), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        int i = 4 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(ChaosPersists.UraniumNugget, 1);
        }
        i = 4 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(ChaosPersists.TitaniumNugget, 1);
        }
        i = 1 + this.world.rand.nextInt(5);
        block17 : for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(20);
            ItemStack is;
            switch (var3) 
            {
                case 0: {
                    is = this.dropItemRand(ChaosPersists.MyRubySword, 1);
                    continue block17;
                }
                case 1: {
                    is = this.dropItemRand(Items.DIAMOND, 1);
                    continue block17;
                }
                case 2: {
                    is = this.dropItemRand(ChaosPersists.MyThunderStaff, 1);
                    continue block17;
                }
                case 3: {
                    is = this.dropItemRand(ChaosPersists.MyRubySword, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(16), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(18), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(19), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(21), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(20), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(16), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 4: {
                    is = this.dropItemRand(ChaosPersists.MyRubyShovel, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 5: {
                    is = this.dropItemRand(ChaosPersists.MyRubyPickaxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(35), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 6: {
                    is = this.dropItemRand(ChaosPersists.MyRubyAxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 7: {
                    is = this.dropItemRand(ChaosPersists.MyRubyHoe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 8: {
                    is = this.dropItemRand((Item)ChaosPersists.RubyHelmet, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(0), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(3), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(1), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(4), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(5), 1 + this.world.rand.nextInt(2));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(6), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 9: {
                    is = this.dropItemRand((Item)ChaosPersists.RubyBody, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(0), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(3), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(1), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(4), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block17;
                }
                case 10: {
                    is = this.dropItemRand((Item)ChaosPersists.RubyLegs, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(0), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(3), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(1), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(4), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block17;
                }
                case 11: {
                    is = this.dropItemRand((Item)ChaosPersists.RubyBoots, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(2), 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block17;
                }
                case 12: 
                case 13: 
                case 14: 
                case 15: 
                case 16: 
                case 17: {
                    is = this.dropItemRand(ChaosPersists.MyRuby, 1);
                    break;
                }
            }
        }
    }

    public int getCephadromeHealth() {
        return (int)this.getHealth();
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        double ks = 2.5;
        double inair = 0.35;
        float iskraken = 1.0f;
        boolean ret = false;
        if (par1Entity != null && par1Entity instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon)par1Entity;
            DamageSource var21 = null;
            var21 = DamageSource.causeExplosionDamage((Explosion)null);
            if (this.world.rand.nextInt(6) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, var21, 70.0f);
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, var21, 70.0f);
            }
            ret = true;
        } else if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
            if (par1Entity instanceof Kraken) {
                iskraken = 1.5f;
            }
            ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), iskraken * 70.0f);
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                inair *= 2.0;
            }
            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return ret;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.hurt_timer > 0) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("cactus")) {
            return false;
        }
        boolean ret = super.attackEntityFrom(par1DamageSource, par2);
        this.hurt_timer = 25;
        Entity e = par1DamageSource.getTrueSource();
        if (!this.world.isRemote && e != null && e instanceof EntityLivingBase) {
            this.setAttackTarget((EntityLivingBase)e);
            if (this.getActivity() == 0) {
                this.getNavigator().tryMoveToEntityLiving((Entity)((EntityLivingBase)e), 1.2);
            }
        }
        if (e != null && e instanceof EntityPlayer && this.getHealth() < this.getMaxHealth() * 9.0f / 10.0f) {
            this.hit_by_player = 1;
        }
        return ret;
    }

    public double getHorizontalDistanceSqToEntity(Entity par1Entity) {
        double d0 = this.posX - par1Entity.posX;
        double d2 = this.posZ - par1Entity.posZ;
        return d0 * d0 + d2 * d2;
    }

    public void updateAITasks() {
        EntityLivingBase e = null;
        double maxdist = 10.0;
        if (this.isDead) {
            return;
        }
        if (this.updateit > 0) {
            --this.updateit;
        }
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.updateit <= 0 && !this.world.isRemote) {
            this.updateit = 30;
            if (this.getRiddenByEntity() != null) {
                this.setActivity(1);
            } else {
                this.setActivity(0);
            }
        }
        if (this.world.rand.nextInt(100) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(2.0f);
        }
        if (this.getActivity() == 0) {
            super.updateAITasks();
        }
        if (this.world.rand.nextInt(7) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            e = this.getAttackTarget();
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                if (this.getActivity() == 0) {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.7);
                    maxdist = 6.0;
                }
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                this.setAttacking(1);
                if (this.getDistanceSq((Entity)e) < (maxdist + (double)(e.width / 2.0f)) * (maxdist + (double)(e.width / 2.0f))) {
                    this.attackEntityAsMob((Entity)e);
                } else if (e instanceof Kraken && this.getHorizontalDistanceSqToEntity((Entity)e) < (maxdist + (double)(e.width / 2.0f)) * (maxdist + (double)(e.width / 2.0f))) {
                    this.attackEntityAsMob((Entity)e);
                }
            } else if (this.getAttacking() != 0) {
                this.setAttacking(0);
            }
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof Cephadrome) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof Mothra) {
            return true;
        }
        if (par1EntityLiving instanceof Leon) {
            EntityTameable et = (EntityTameable)par1EntityLiving;
            if (et.isTamed()) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof GammaMetroid) {
            EntityTameable et = (EntityTameable)par1EntityLiving;
            if (et.isTamed()) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof WaterDragon) {
            EntityTameable et = (EntityTameable)par1EntityLiving;
            if (et.isTamed()) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof EntityDragon) {
            return true;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
            if (this.hit_by_player != 0) {
                return true;
            }
            if (this.badmood != 0) {
                return true;
            }
            if (this.shouldattack > 0) {
                this.shouldattack = 0;
                return true;
            }
            return false;
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(16.0, 20.0, 16.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false)) continue;
            return var4;
        }
        return null;
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int j;
        int i;
        int k;
        for (k = -3; k < 3; ++k) {
            for (j = -3; j < 3; ++j) {
                for (i = 0; i < 5; ++i) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));
                                        String s = null;
                    net.minecraft.util.ResourceLocation id = com.astryxion.chaospersists.util.SpawnerFixHelper.getMobSpawnerEntityId(tileentitymobspawner.getSpawnerBaseLogic());
                    if (id != null) s = id.getPath();
                    if (s == null || !s.equals("Cephadrome")) continue;
                    this.badmood = 1;
                    return true;
                }
            }
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        if (this.posY < 50.0) {
            return false;
        }
        for (k = -2; k < 2; ++k) {
            for (j = -2; j < 2; ++j) {
                for (i = 1; i < 5; ++i) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        Cephadrome target = null;
        target = (Cephadrome)this.world.findNearestEntityWithinAABB(Cephadrome.class, this.getEntityBoundingBox().expand(16.0, 6.0, 16.0), (Entity)this);
        if (target != null) {
            return false;
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        super.setPositionAndRotationDirect(par1, par3, par5, par7, par8, par9, true);
        this.boatPosRotationIncrements = par9;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
        this.boatYawHead = par7;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        super.setVelocity(par1, par3, par5);
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
        this.noClip = this.getActivity() != 0;
        if (this.getActivity() == 1) {
            ++this.wing_sound;
            if (this.wing_sound > 22) {
                if (!this.world.isRemote) {
                    this.playSound(com.astryxion.chaospersists.core.ChaosSounds.MOTHRA_WINGS, 0.5f, 1.0f);
                }
                this.wing_sound = 0;
            }
        }
        if (ChaosPersists.PlayNicely == 0) {
            this.wasfed = 1;
        }
        if (!this.world.isRemote) {
            MyUtils.enforceDragonMountGroundSafety(this);
            if (this.getPassengers().isEmpty()) {
                this.pushOutOfBlocks(this.posX, this.posY, this.posZ);
            }
        }
    }

    public void onLivingUpdate() {
        if (this.isDead) {
            super.onLivingUpdate();
            return;
        }
        super.onLivingUpdate();
        if (this.world.isRemote) {
            if (this.getActivity() != 0 && !this.getPassengers().isEmpty()) {
                Entity rider = this.getPassengers().get(0);
                if (rider instanceof EntityPlayerSP) {
                    EntityPlayerSP pp = (EntityPlayerSP)rider;
                    pp.connection.sendPacket(new CPacketPlayer.Rotation(pp.rotationYaw, pp.rotationPitch, pp.onGround));
                    pp.connection.sendPacket(new CPacketInput(pp.moveStrafing, pp.moveForward, pp.movementInput.jump, pp.movementInput.sneak));
                }
            }
            if (this.boatPosRotationIncrements > 0 && this.getActivity() != 0) {
                double d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                double d10 = MathHelper.wrapDegrees((double)(this.boatYaw - (double)this.rotationYaw));
                if (!this.getPassengers().isEmpty()) {
                    d10 = MathHelper.wrapDegrees((double)((double)this.getPassengers().get(0).rotationYaw - (double)this.rotationYaw));
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.rotationYawHead = this.rotationYaw;
                --this.boatPosRotationIncrements;
            }
        }
    }

    public void updateRiderPosition() {
        if (this.getRiddenByEntity() != null) {
            float f = 0.75f;
            this.getRiddenByEntity().setPosition(this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + this.getMountedYOffset(), this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    protected void playTameEffect(boolean par1) {
        String s = "heart";
        if (!par1) {
            s = "smoke";
        }
        for (int i = 0; i < 20; ++i) {
            double d0 = this.rand.nextGaussian() * 0.08;
            double d1 = this.rand.nextGaussian() * 0.08;
            double d2 = this.rand.nextGaussian() * 0.08;
            this.world.spawnParticle(par1 ? net.minecraft.util.EnumParticleTypes.HEART : net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 2.5f), this.posY + 0.5 + (double)this.rand.nextFloat() * 1.5, this.posZ + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 2.5f), d0, d1, d2);
        }
    }

    public boolean processInteract(EntityPlayer par1EntityPlayer, net.minecraft.util.EnumHand hand) {
        ItemStack var2 = par1EntityPlayer.getHeldItem(hand);
        if (var2 == null) {
            var2 = ItemStack.EMPTY;
        }
        if (!var2.isEmpty() && var2.getCount() <= 0) {
            par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
            var2 = ItemStack.EMPTY;
        }
        if (!var2.isEmpty() && (var2.getItem() == Items.BEEF || var2.getItem() == Items.CHICKEN || var2.getItem() == Items.PORKCHOP) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
            if (!this.world.isRemote) {
                this.heal((float)this.mygetMaxHealth() - this.getHealth());
            }
            this.wasfed = 1;
            this.shouldattack = 0;
            this.playTameEffect(true);
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.isEmpty()) {
                    par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                }
            }
        } else {
            if (this.getRiddenByEntity() != null && this.getRiddenByEntity() instanceof EntityPlayer && this.getRiddenByEntity() != par1EntityPlayer) {
                return true;
            }
            if (var2.isEmpty() && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && !this.world.isRemote) {
                if (this.wasfed == 0) {
                    this.getNavigator().tryMoveToEntityLiving((Entity)par1EntityPlayer, 1.2);
                    this.shouldattack = 1;
                    return false;
                }
                par1EntityPlayer.startRiding(this);
                this.wasfed = 0;
                this.setActivity(1);
            }
            return true;
        }
        return false;
    }

    public int getAttacking() {
        return this.getDataManager().get(ATTACKING).intValue();
    }

    public void setAttacking(int par1) {
        if (this.world != null && this.world.isRemote) {
            return;
        }
        this.getDataManager().set(ATTACKING, (byte)par1);
    }

    public int getActivity() {
        return this.getDataManager().get(STATE2).byteValue();
    }

    public void setActivity(int par1) {
        if (this.world != null && this.world.isRemote) {
            return;
        }
        this.getDataManager().set(STATE2, (byte)par1);
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        if (this.getRiddenByEntity() != null) {
            return false;
        }
        return true;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CephaWasFed", this.wasfed);
        par1NBTTagCompound.setInteger("CephaAttacking", this.getAttacking());
        par1NBTTagCompound.setInteger("CephaActivity", this.getActivity());
        par1NBTTagCompound.setInteger("CephaHitByPlayer", this.hit_by_player);
        par1NBTTagCompound.setInteger("CephaBadMood", this.badmood);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.wasfed = par1NBTTagCompound.getInteger("CephaWasFed");
        this.hit_by_player = par1NBTTagCompound.getInteger("CephaHitByPlayer");
        this.badmood = par1NBTTagCompound.getInteger("CephaBadMood");
        this.setAttacking(par1NBTTagCompound.getInteger("CephaAttacking"));
        this.setActivity(par1NBTTagCompound.getInteger("CephaActivity"));
    }
}

