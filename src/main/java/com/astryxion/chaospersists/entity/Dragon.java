package com.astryxion.chaospersists.entity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.item.BetterFireball;
import com.astryxion.chaospersists.entity.CreepingHorror;
import com.astryxion.chaospersists.entity.EnderReaper;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.item.IceBall;
import com.astryxion.chaospersists.entity.Kraken;
import com.astryxion.chaospersists.entity.LeafMonster;
import com.astryxion.chaospersists.entity.LurkingTerror;
import com.astryxion.chaospersists.entity.Mothra;
import com.astryxion.chaospersists.util.MyEntityAIFollowOwner;
import com.astryxion.chaospersists.util.MyEntityAIWander;
import com.astryxion.chaospersists.util.MyUtils;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.render.RenderInfo;
import com.astryxion.chaospersists.entity.Spyro;
import com.astryxion.chaospersists.entity.TerribleTerror;
import com.astryxion.chaospersists.entity.Triffid;
import com.astryxion.chaospersists.item.WaterBall;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockLiquid;
import com.google.common.base.Predicate;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.EntitySelectors;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;

/*
 * Exception performing whole class analysis ignored.
 */
public class Dragon
extends EntityTameable {
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(Dragon.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> STATE2 = EntityDataManager.createKey(Dragon.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> INT22 = EntityDataManager.createKey(Dragon.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> INT24 = EntityDataManager.createKey(Dragon.class, DataSerializers.VARINT);
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double boatYawHead;
    private int updateit = 1;
    private int color = 1;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private int wing_sound = 0;
    private BlockPos currentFlightTarget = null;
    private boolean target_in_sight = false;
    private int owner_flying = 0;
    private int flyaway = 0;
    private int stuck_count = 0;
    private int lastX = 0;
    private int lastZ = 0;
    private int unstick_timer = 0;
    private int fireballticker = 0;
    private float moveSpeed = 0.32f;
    private float deltasmooth = 0.0f;
    private int dragontype = 0;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public Dragon(World par1World) {
        super(par1World);
        this.setSize(1.5f, 1.25f);
                this.experienceValue = 100;
                this.isImmuneToFire = true;
        this.setSitting(false);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIFollowOwner((EntityTameable)this, 1.1f, 12.0f, 2.0f));
        this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.25, Items.BEEF, false));
        this.tasks.addTask(3, (EntityAIBase)new MyEntityAIWander((EntityCreature)this, 0.75f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 9.0f));
        this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
        if (ChaosPersists.PlayNicely == 0) {
            this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 0, true, false, new Predicate<EntityLivingBase>() { @Override public boolean apply(EntityLivingBase e) { return e instanceof IMob; } }));
        }
        this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
    }

    public Dragon(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.getYOffset(), par6);
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
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(35.0);
    }

    public boolean shouldRiderSit() {
        return true;
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

    /**
     * Same pipeline as {@link ThePrinceTeen} / {@link Cephadrome}: vanilla calls {@code travel} with rider
     * input synced from the client via {@code CPacketInput}. Flight must run here, not only in {@code onLivingUpdate},
     * or {@code moveForward} stays zero on the server and the mount cannot fly.
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
            double obstruction_factor;
            double relative_g;
            double max_speed = 0.95D;
            double gh;
            double pi = 3.1415926545D;
            double deltav;
            Block bid;
            BetterFireball bf;

            if (this.fireballticker > 0) {
                --this.fireballticker;
            }

            if (this.motionX < -2.0D) this.motionX = -2.0D;
            if (this.motionX > 2.0D) this.motionX = 2.0D;
            if (this.motionZ < -2.0D) this.motionZ = -2.0D;
            if (this.motionZ > 2.0D) this.motionZ = 2.0D;
            double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            gh = 1.25D;
            bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
            if (bid != Blocks.AIR) {
                this.motionY += 0.03D;
                this.posY += 0.1D;
            } else {
                this.motionY -= 0.018D;
            }

            obstruction_factor = 0.0D;
            int distLimit = 3 + (int)(velocity * 7.0D);
            if (distLimit < 3) {
                distLimit = 3;
            }
            if (distLimit > 24) {
                distLimit = 24;
            }
            int iMax = Math.min(distLimit * 2, 48);
            for (int k = 1; k < distLimit; k++) {
                for (int i = 1; i < iMax; i++) {
                    double dx = i * Math.cos(Math.toRadians(this.rotationYaw + 90.0F));
                    double dz = i * Math.sin(Math.toRadians(this.rotationYaw + 90.0F));
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + dz))).getBlock();
                    if (bid != Blocks.AIR) {
                        obstruction_factor += 0.05D;
                    }
                }
            }

            this.motionY += obstruction_factor * 0.07000000000000001D;
            this.posY += obstruction_factor * 0.07000000000000001D;
            if (this.motionY > 2.0D) this.motionY = 2.0D;

            double d4 = pp.rotationYaw;
            d4 %= 360.0D;
            while (d4 < 0.0D) d4 += 360.0D;
            double d5 = this.rotationYaw;
            d5 %= 360.0D;
            while (d5 < 0.0D) d5 += 360.0D;
            relative_g = (d4 - d5) % 180.0D;
            while (relative_g < 0.0D) relative_g += 180.0D;
            if (relative_g > 90.0D) relative_g -= 180.0D;

            if (velocity > 0.01D) {
                d4 = 1.85D - velocity;
                d4 = Math.abs(d4);
                if (d4 < 0.01D) d4 = 0.01D;
                if (d4 > 0.9D) d4 = 0.9D;
                this.rotationYaw = pp.rotationYaw + (float)(relative_g * d4);
            } else {
                this.rotationYaw = pp.rotationYaw;
            }
            relative_g = Math.abs(relative_g) * velocity;
            if (relative_g > 50.0D) relative_g = 0.0D;

            this.rotationPitch = (2.0F * (float)velocity);
            this.setRotation(this.rotationYaw, this.rotationPitch);

            double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            double rhm = Math.atan2(this.motionZ, this.motionX);
            double rhdir = Math.toRadians(((pp.rotationYaw + 90.0F) % 360.0F));
            float im = pp.moveForward;

            boolean riderJumping = false;
            try {
                Boolean b = ReflectionHelper.getPrivateValue(EntityLivingBase.class, pp, "isJumping", "field_70703_bu");
                if (b != null) {
                    riderJumping = b;
                }
            } catch (Exception ignored) {
            }
            if (riderJumping || ChaosPersists.flyup_keystate != 0) {
                this.motionY += 0.03D;
                this.motionY += velocity * 0.036D;
            }

            double rdv = Math.abs(rhm - rhdir) % (pi * 2.0D);
            if (rdv > pi) rdv -= pi * 2.0D;
            rdv = Math.abs(rdv);
            if (Math.abs(newvelocity) < 0.01D) rdv = 0.0D;

            if (rdv > 1.5D) newvelocity = -newvelocity;

            if (Math.abs(im) > 0.001F) {
                if (im > 0.0F) {
                    deltav = 0.025D;
                    if (max_speed > 1.0D) deltav += 0.05D;
                    if (this.deltasmooth < 0.0F) this.deltasmooth = 0.0F;
                    this.deltasmooth = (float)(this.deltasmooth + deltav / 10.0D);
                    if (this.deltasmooth > deltav) this.deltasmooth = (float)deltav;
                } else {
                    max_speed = 0.35D;
                    deltav = -0.02D;
                    if (this.deltasmooth > 0.0F) this.deltasmooth = 0.0F;
                    this.deltasmooth = (float)(this.deltasmooth + deltav / 10.0D);
                    if (this.deltasmooth < deltav) this.deltasmooth = (float)deltav;
                }

                newvelocity += this.deltasmooth;
                if (newvelocity >= 0.0D) {
                    if (newvelocity > max_speed) newvelocity = max_speed;
                    this.motionX = (Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity);
                    this.motionZ = (Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity);
                } else {
                    if (newvelocity < -max_speed) newvelocity = -max_speed;
                    newvelocity = -newvelocity;
                    this.motionX = (Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity);
                    this.motionZ = (Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * newvelocity);
                }

            } else if (newvelocity >= 0.0D) {
                this.motionX = (Math.cos(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity);
                this.motionZ = (Math.sin(Math.toRadians(this.rotationYaw + 90.0F)) * newvelocity);
            } else {
                this.motionX = (Math.cos(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0D));
                this.motionZ = (Math.sin(Math.toRadians(this.rotationYaw + 270.0F)) * (newvelocity * -1.0D));
            }

            if (this.fireballticker == 0) {
                double xzoff = 4.0D;
                double yoff = -0.25D;

                if (getDragonType() == 0) {
                    if (pp.moveStrafing > 0.001F) {
                        bf = new BetterFireball(this.world, this, 0.0D, 0.0D, 0.0D);
                        bf.setNotMe();
                        bf.setSmall();
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                        bf.setPosition(cx, this.posY + yoff, cz);
                        cx = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0F));
                        cz = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0F));
                        double cy = -Math.sin(Math.toRadians(pp.rotationPitch));

                        double d3 = MathHelper.sqrt(cx * cx + cy * cy + cz * cz);
                        bf.accelerationX = (cx / d3 * 0.15D);
                        bf.accelerationY = (cy / d3 * 0.15D);
                        bf.accelerationZ = (cz / d3 * 0.15D);
                        bf.motionX = this.motionX;
                        bf.motionY = this.motionY;
                        bf.motionZ = this.motionZ;
                        bf.posX -= this.motionX * 9.0D;
                        bf.posY -= this.motionY * 9.0D;
                        bf.posZ -= this.motionZ * 9.0D;
                        this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.bow")), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
                        this.world.spawnEntity(bf);
                        this.fireballticker = 10;
                    }
                    if (pp.moveStrafing < -0.001F) {
                        bf = new BetterFireball(this.world, this, 0.0D, 0.0D, 0.0D);
                        bf.setNotMe();
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                        bf.setPosition(cx, this.posY + yoff, cz);
                        cx = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0F));
                        cz = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0F));
                        double cy = -Math.sin(Math.toRadians(pp.rotationPitch));

                        double d3 = MathHelper.sqrt(cx * cx + cy * cy + cz * cz);
                        bf.accelerationX = (cx / d3 * 0.1D);
                        bf.accelerationY = (cy / d3 * 0.1D);
                        bf.accelerationZ = (cz / d3 * 0.1D);
                        bf.motionX = this.motionX;
                        bf.motionY = this.motionY;
                        bf.motionZ = this.motionZ;
                        bf.posX -= this.motionX * 9.0D;
                        bf.posY -= this.motionY * 9.0D;
                        bf.posZ -= this.motionZ * 9.0D;
                        this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.fuse")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
                        this.world.spawnEntity(bf);
                        this.fireballticker = 20;
                    }
                } else {
                    if (pp.moveStrafing > 0.001F) {
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                        WaterBall var2 = new WaterBall(this.world, cx, this.posY + yoff, cz);
                        var2.setLocationAndAngles(cx, this.posY + yoff, cz, pp.rotationYaw + 90.0F, pp.rotationPitch);
                        double var3 = Math.cos(Math.toRadians(pp.rotationYawHead + 90.0F));
                        double var5 = -Math.sin(Math.toRadians(pp.rotationPitch));
                        double var77 = Math.sin(Math.toRadians(pp.rotationYawHead + 90.0F));
                        float var9 = MathHelper.sqrt(var3 * var3 + var77 * var77) * 0.2F;
                        var2.shoot(var3, var5 + var9, var77, 1.4F, 5.0F);
                        var2.posX -= this.motionX * 7.0D;
                        var2.posY -= this.motionY * 7.0D;
                        var2.posZ -= this.motionZ * 7.0D;
                        this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.bow")), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
                        this.world.spawnEntity(var2);
                        this.fireballticker = 5;
                    }
                    if (pp.moveStrafing < -0.001F) {
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                        IceBall var2 = new IceBall(this.world, cx, this.posY + yoff, cz);
                        var2.setLocationAndAngles(cx, this.posY + yoff, cz, pp.rotationYaw + 90.0F, pp.rotationPitch);
                        var2.setSpecial();
                        var2.setIceBall();
                        double var3 = Math.cos(Math.toRadians(pp.rotationYaw + 90.0F));
                        double var5 = -Math.sin(Math.toRadians(pp.rotationPitch));
                        double var77 = Math.sin(Math.toRadians(pp.rotationYaw + 90.0F));
                        float var9 = MathHelper.sqrt(var3 * var3 + var77 * var77) * 0.2F;
                        var2.shoot(var3, var5 + var9, var77, 1.4F, 5.0F);
                        var2.posX -= this.motionX * 7.0D;
                        var2.posY -= this.motionY * 7.0D;
                        var2.posZ -= this.motionZ * 7.0D;
                        var2.motionX *= 2.0D;
                        var2.motionY *= 2.0D;
                        var2.motionZ *= 2.0D;
                        this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("fireworks.launch")), net.minecraft.util.SoundCategory.NEUTRAL, 0.75F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
                        this.world.spawnEntity(var2);
                        this.fireballticker = 15;
                    }
                }
            }

            this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

            this.motionX *= 0.985D;
            this.motionY *= 0.94D;
            this.motionZ *= 0.985D;

            if (!this.world.isRemote) {
                list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(2.25D, 2.0D, 2.25D));

                if ((list != null) && (!list.isEmpty())) {
                    for (int l = 0; l < list.size(); l++) {
                        listEntity = (Entity)list.get(l);

                        if ((listEntity == pp) || (listEntity.isDead) || (!listEntity.canBePushed()))
                            continue;
                        listEntity.applyEntityCollision(this);
                    }
                }
            }
            return;
        }
        this.setNoGravity(false);
        super.travel(strafe, vertical, forward);
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (!this.world.isRemote && this.getPassengers().isEmpty()) {
            this.setNoGravity(false);
            this.noClip = false;
            this.motionY = 0.0;
            this.pushOutOfBlocks(this.posX, this.posY, this.posZ);
            MyUtils.enforceDragonMountGroundSafety(this);
        }
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
        this.getDataManager().register(INT22, 0);
        this.getDataManager().register(INT24, 1);
        this.setActivity(0);
        this.setAttacking(0);
        this.setTamed(false);
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
        return 200;
    }

    public int getDragonHealth() {
        return (int)this.getHealth();
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
        return 14;
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
    }

    public boolean isAIEnabled() {
        return true;
    }

    private boolean scan_it(int x, int y, int z, int dx, int dy, int dz) {
        int i;
        Block bid;
        int d;
        int j;
        int found = 0;
        for (i = - dy; i <= dy; ++i) {
            for (j = - dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + dx, y + i, z + j)).getBlock();
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dx * dx + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x - dx;
                this.ty = y + i;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = - dx; i <= dx; ++i) {
            for (j = - dz; j <= dz; ++j) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + dy, z + j)).getBlock();
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dy * dy + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y - dy;
                this.tz = z + j;
                ++found;
            }
        }
        for (i = - dx; i <= dx; ++i) {
            for (j = - dy; j <= dy; ++j) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z + dz)).getBlock();
                if ((bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.LAVA && bid != Blocks.FLOWING_LAVA || (d = dz * dz + j * j + i * i) >= this.closest) continue;
                this.closest = d;
                this.tx = x + i;
                this.ty = y + j;
                this.tz = z - dz;
                ++found;
            }
        }
        if (found != 0) {
            return true;
        }
        return false;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        if (this.isSitting()) {
            return null;
        }
        if (this.getAttacking() == 1 && (this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)) == null) {
            return com.astryxion.chaospersists.core.ChaosSounds.ROAR;
        }
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return com.astryxion.chaospersists.core.ChaosSounds.ALO_HURT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.ALO_DEATH;
    }

    protected float getSoundVolume() {
        return 0.6f;
    }

    public float getSoundPitch() {
        return 0.75f;
    }

    public boolean canBePushed() {
        return false;
    }

    public double getMountedYOffset() {
        return 1.3;
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
        int i = 1 + this.world.rand.nextInt(6);
        for (int var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.BEEF, 1);
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        double ks = 1.75;
        double inair = 0.1;
        float iskraken = 1.0f;
        if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
            if (par1Entity instanceof Kraken) {
                iskraken = 2.0f;
            }
            par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), iskraken * 35.0f);
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                inair *= 2.0;
            }
            par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
        }
        return true;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        Entity e = null;
        if (this.hurt_timer > 0) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("cactus")) {
            return ret;
        }
        if (par1DamageSource.getDamageType().equals("inFire")) {
            return ret;
        }
        if (par1DamageSource.getDamageType().equals("onFire")) {
            return ret;
        }
        if (par1DamageSource.getDamageType().equals("lava")) {
            return ret;
        }
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return ret;
        }
        this.setSitting(false);
        this.setActivity(1);
        e = par1DamageSource.getTrueSource();
        if (e != null && e instanceof BetterFireball && this.dragontype == 0) {
            e.setDead();
            return ret;
        }
        if (e != null && e instanceof IceBall && this.dragontype != 0) {
            e.setDead();
            return ret;
        }
        if (e != null && e instanceof WaterBall && this.dragontype != 0) {
            e.setDead();
            return ret;
        }
        if (e != null && e instanceof EntitySmallFireball && this.dragontype == 0) {
            e.setDead();
            return ret;
        }
        if (e != null && e instanceof Dragon) {
            return false;
        }
        if (e != null && e instanceof Spyro) {
            return false;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        this.hurt_timer = 20;
        if (e != null && e instanceof EntityLivingBase) {
            if (this.isTamed() && e instanceof EntityPlayer) {
                return false;
            }
            this.setAttackTarget((EntityLivingBase)e);
            this.setAttackTarget(e instanceof EntityLivingBase ? (EntityLivingBase)e : null);
            this.getNavigator().tryMoveToEntityLiving((Entity)((EntityLivingBase)e), 1.2);
            ret = true;
        }
        return ret;
    }

    public void updateAITasks() {
        EntityLivingBase e = null;
        // Same as ThePrinceTeen: while ambient-flying (activity on, no rider), skip task AI so wander/follow/
        // move-indoors do not overwrite moveForward/moveStrafing after fly_without_rider — that caused random
        // backward flight vs. Prince which never runs those tasks in that state.
        if (this.getActivity() == 0 || !this.getPassengers().isEmpty()) {
            super.updateAITasks();
        }
        if (!this.isSitting() && this.getActivity() == 0 && this.getPassengers().isEmpty() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(10) == 1 && (e = this.findSomethingToAttack()) != null) {
            this.setActivity(1);
        }
    }

    public void always_do() {
        EntityPlayer p = null;
        if (this.world.rand.nextInt(250) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(2.0f);
        }
        if (this.isSitting()) {
            return;
        }
        this.owner_flying = 0;
        if (this.isTamed() && this.getOwner() != null && (this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)) == null && !this.isSitting()) {
            p = (EntityPlayer)this.getOwner();
            if (p.capabilities.isFlying) {
                this.owner_flying = 1;
                this.setActivity(1);
            }
        }
        if (this.isTamed() && this.getOwner() != null && !this.isSitting() && this.getDistanceSq((Entity)(p = (EntityPlayer)this.getOwner())) > 400.0) {
            this.setActivity(1);
        }
        if (this.world.rand.nextInt(50) == 1 && !this.isSitting() && !this.target_in_sight && (this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)) == null) {
            if (this.world.rand.nextInt(15) == 1) {
                this.setActivity(1);
            } else {
                this.setActivity(0);
            }
        }
        if (this.world.rand.nextInt(25) == 0 && !this.target_in_sight && (this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)) == null) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 1; i < 11; ++i) {
                int j = i;
                if (j > 4) {
                    j = 4;
                }
                if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) break;
                if (i < 6) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.setActivity(0);
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)(this.ty - 1), (double)this.tz, 1.0);
                if (this.isInLava()) {
                    this.heal(1.0f);
                    this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("entity.generic.splash")), 1.0f, this.world.rand.nextFloat() * 0.2f + 0.9f);
                }
            }
        }
    }

    public void fly_with_rider() {
        EntityLivingBase e = null;
        int freq = 7;
        if (this.isDead) {
            return;
        }
        if (this.isSitting()) {
            return;
        }
        if (this.world.isRemote) {
            return;
        }
        if (this.world.rand.nextInt(freq) == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            if (this.world.rand.nextInt(250) == 0) {
                this.setAttackTarget(null);
            }
            if ((e = this.getAttackTarget()) != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                this.setAttacking(1);
                if (this.getDistanceSq((Entity)e) < (double)((7.0f + e.width / 2.0f) * (7.0f + e.width / 2.0f))) {
                    this.attackEntityAsMob((Entity)e);
                }
                return;
            }
            this.setAttacking(0);
        }
    }

    protected void updateAITick() {
        if (!this.getPassengers().isEmpty()) {
            return;
        }
        // Must match Prince: do not run base task AI while ambient-flying. This path calls super.updateAITasks()
        // directly (bypassing our updateAITasks override), so without this guard wander/follow still ran.
        if (this.getActivity() != 0) {
            return;
        }
        super.updateAITasks();
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
        if (par1EntityLiving instanceof LurkingTerror) {
            return false;
        }
        if (par1EntityLiving instanceof EnderReaper) {
            return false;
        }
        if (par1EntityLiving instanceof TerribleTerror) {
            return false;
        }
        if (par1EntityLiving instanceof LeafMonster) {
            return false;
        }
        if (par1EntityLiving instanceof CreepingHorror) {
            return false;
        }
        if (par1EntityLiving instanceof Triffid) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof Mothra) {
            return true;
        }
        if (par1EntityLiving instanceof Kraken) {
            return true;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            return false;
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(20.0, 20.0, 20.0));
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

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean getCanSpawnHere() {
        Dragon target = null;
        if (!this.world.isDaytime()) {
            return false;
        }
        target = (Dragon)this.world.findNearestEntityWithinAABB(Dragon.class, this.getEntityBoundingBox().expand(16.0, 6.0, 16.0), (Entity)this);
        if (target != null) {
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

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        super.setPositionAndRotation(par1, par3, par5, par7, par8);
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
        EntityLivingBase e = null;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.noClip = false;
        if (!this.world.isRemote && this.getActivity() != 0 && this.getPassengers().isEmpty() && !this.isSitting()) {
            this.fly_without_rider();
        }
        super.onUpdate();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.getActivity() == 1) {
            ++this.wing_sound;
            if (this.wing_sound > 20) {
                if (!this.world.isRemote) {
                    this.world.playSound(null, this.posX, this.posY, this.posZ, com.astryxion.chaospersists.core.ChaosSounds.MOTHRA_WINGS, net.minecraft.util.SoundCategory.NEUTRAL, 0.5f, 1.0f);
                }
                this.wing_sound = 0;
            }
        }
        if (this.isInWater()) {
            this.motionY += 0.07;
        }
        if (this.world.isRemote) {
            return;
        }
        if (this.getActivity() == 0 && this.isTamed() && this.getOwner() != null && !this.isSitting() && this.getDistanceSq((Entity)(e = this.getOwner())) > 144.0) {
            this.setActivity(1);
        }
        MyUtils.enforceDragonMountGroundSafety(this);
        if (this.getPassengers().isEmpty()) {
            this.pushOutOfBlocks(this.posX, this.posY, this.posZ);
        }
    }

    private void fly_without_rider() {
        Block bid;
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        boolean do_new = false;
        double ox = 0.0;
        double oy = 0.0;
        double oz = 0.0;
        boolean has_owner = false;
        EntityLivingBase e = null;
        double speed_factor = 0.5;
        double var1 = 0.0;
        double var3 = 0.0;
        double var5 = 0.0;
        double yoff = 1.25;
        double xzoff = 2.25;
        double gh = 1.25;
        double obstruction_factor = 0.0;
        EntitySmallFireball sf = null;
        BetterFireball bf = null;
        IceBall ib = null;
        WaterBall wb = null;
        boolean toofar = false;
        if (this.currentFlightTarget == null) {
            do_new = true;
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.isSitting()) {
            return;
        }
        if ((this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)) != null) {
            return;
        }
        if (this.unstick_timer > 0) {
            --this.unstick_timer;
        }
        if (this.lastX == (int)this.posX && this.lastZ == (int)this.posZ) {
            ++this.stuck_count;
            if (this.stuck_count > 50) {
                this.stuck_count = 0;
                this.unstick_timer = 100;
                this.target_in_sight = false;
                this.setAttacking(0);
                do_new = true;
            }
        } else {
            this.stuck_count = 0;
            this.lastX = (int)this.posX;
            this.lastZ = (int)this.posZ;
        }
        this.motionY = this.posY < (double)this.currentFlightTarget.getY() + 2.0 ? (this.motionY *= 0.7) : (this.posY > (double)this.currentFlightTarget.getY() - 2.0 ? (this.motionY *= 0.5) : (this.motionY *= 0.61));
        if (this.world.rand.nextInt(300) == 1) {
            do_new = true;
        }
        if (this.isTamed() && this.getOwner() != null) {
            e = this.getOwner();
            has_owner = true;
            ox = e.posX;
            oy = e.posY;
            oz = e.posZ;
            if (this.getDistanceSq((Entity)e) > 144.0) {
                toofar = true;
                this.target_in_sight = false;
                this.setAttacking(0);
                this.flyaway = 0;
                do_new = true;
            }
        }
        if (this.flyaway > 0) {
            --this.flyaway;
        }
        if (!toofar && this.unstick_timer == 0 && this.flyaway == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(9) == 1) {
            e = this.findSomethingToAttack();
            if (e != null) {
                if (this.isTamed() && this.getHealth() / (float)this.mygetMaxHealth() < 0.25f) {
                    this.setActivity(1);
                    this.setAttacking(0);
                    this.target_in_sight = false;
                    do_new = false;
                    this.currentFlightTarget = new BlockPos((int)(this.posX + (this.posX - e.posX)), (int)(this.posY + 1.0), (int)(this.posZ + (this.posZ - e.posZ)));
                } else {
                    this.setActivity(1);
                    this.setAttacking(1);
                    this.target_in_sight = true;
                    this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                    do_new = false;
                    if (this.getDistanceSq((Entity)e) < (double)((5.0f + e.width / 2.0f) * (5.0f + e.width / 2.0f))) {
                        this.attackEntityAsMob((Entity)e);
                        this.flyaway = 5 + this.world.rand.nextInt(10);
                        do_new = true;
                    } else if (this.getDistanceSq((Entity)e) < 256.0 && !this.isInWater() && this.getDragonFire() >= 1) {
                        double var7;
                        float var9;
                        double cx = this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw));
                        double cz = this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw));
                        if (this.dragontype == 0) {
                            if (this.getDragonFire() == 1) {
                                sf = new EntitySmallFireball(this.world, (EntityLivingBase)this, e.posX - cx, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - cz);
                                sf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                                sf.setPosition(cx, this.posY + yoff, cz);
                                this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.bow")), net.minecraft.util.SoundCategory.NEUTRAL, 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                                this.world.spawnEntity((Entity)sf);
                            } else {
                                bf = new BetterFireball(this.world, (EntityLivingBase)this, e.posX - cx, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - cz);
                                bf.setLocationAndAngles(cx, this.posY + yoff, cz, this.rotationYaw, 0.0f);
                                bf.setPosition(cx, this.posY + yoff, cz);
                                this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.fuse")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                                this.world.spawnEntity((Entity)bf);
                            }
                        } else if (this.getDragonFire() == 1) {
                            wb = new WaterBall(this.world, e.posX - this.posX, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - this.posZ);
                            wb.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
                            var3 = e.posX - wb.posX;
                            var5 = e.posY + 0.25 - wb.posY;
                            var7 = e.posZ - wb.posZ;
                            var9 = MathHelper.sqrt((double)(var3 * var3 + var7 * var7)) * 0.2f;
                            wb.shoot(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
                            this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.bow")), net.minecraft.util.SoundCategory.NEUTRAL, 0.75f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                            this.world.spawnEntity((Entity)wb);
                        } else {
                            ib = new IceBall(this.world, e.posX - this.posX, e.posY + (double)(e.height / 2.0f) - (this.posY + yoff), e.posZ - this.posZ);
                            ib.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
                            ib.setSpecial();
                            ib.setIceBall();
                            var3 = e.posX - ib.posX;
                            var5 = e.posY + 0.25 - ib.posY;
                            var7 = e.posZ - ib.posZ;
                            var9 = MathHelper.sqrt((double)(var3 * var3 + var7 * var7)) * 0.2f;
                            ib.shoot(var3, var5 + (double)var9, var7, 1.4f, 5.0f);
                            this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("random.fuse")), net.minecraft.util.SoundCategory.NEUTRAL, 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
                            this.world.spawnEntity((Entity)ib);
                        }
                    }
                }
            } else {
                this.target_in_sight = false;
                this.flyaway = 0;
                this.setAttacking(0);
            }
        }
        if (this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 2.1f) {
            do_new = true;
        }
        if (do_new && !this.target_in_sight || do_new && this.flyaway != 0) {
            bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                int gox = (int)this.posX;
                int goy = (int)this.posY;
                int goz = (int)this.posZ;
                if (has_owner && this.unstick_timer == 0) {
                    gox = (int)ox;
                    goy = (int)oy;
                    goz = (int)oz;
                    if (this.owner_flying == 0) {
                        zdir = this.world.rand.nextInt(10) + 4;
                        xdir = this.world.rand.nextInt(10) + 4;
                    } else {
                        zdir = this.world.rand.nextInt(6);
                        xdir = this.world.rand.nextInt(6);
                    }
                } else {
                    zdir = this.world.rand.nextInt(10) + 16;
                    xdir = this.world.rand.nextInt(10) + 16;
                }
                if (this.world.rand.nextInt(2) == 1) {
                    zdir = - zdir;
                }
                if (this.world.rand.nextInt(2) == 1) {
                    xdir = - xdir;
                }
                this.currentFlightTarget = new BlockPos(gox + xdir, goy + this.world.rand.nextInt(9 + this.owner_flying * 2) - 4, goz + zdir);
                bid = this.world.getBlockState(this.currentFlightTarget).getBlock();
                if (bid == Blocks.AIR && !this.canSeeTarget((double)this.currentFlightTarget.getX(), (double)this.currentFlightTarget.getY(), (double)this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        }
        obstruction_factor = 0.0;
        // Match ThePrinceTeen: horizontal speed for kMax after targeting/damping, not at method entry.
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        // Match ThePrinceTeen: small inner loop only — large scanDist*2 loops stacked huge Y boosts (random "rocket up").
        int kMax = 2 + (int)(Math.max(0.0, velocity) * 4.0);
        if (kMax < 2) {
            kMax = 2;
        }
        if (kMax > 24) {
            kMax = 24;
        }
        for (int k = 1; k < kMax; ++k) {
            for (int i = 1; i < 4; ++i) {
                double dz;
                double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))))).getBlock();
                if (bid == Blocks.AIR) continue;
                obstruction_factor += 0.05;
            }
        }
        this.motionY += obstruction_factor * 0.05;
        this.posY += obstruction_factor * 0.05;
        speed_factor = 0.5;
        var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        if (this.owner_flying != 0) {
            speed_factor = 1.75;
            if (this.isTamed() && this.getOwner() != null && this.getDistanceSq((Entity)(e = this.getOwner())) > 49.0) {
                speed_factor = 3.5;
            }
        }
        this.motionX += (Math.signum(var1) - this.motionX) * 0.15 * speed_factor;
        this.motionY += (Math.signum(var3) - this.motionY) * 0.21 * speed_factor;
        this.motionZ += (Math.signum(var5) - this.motionZ) * 0.15 * speed_factor;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
        float var8 = MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = (float)(0.75 * speed_factor);
        this.rotationYaw += var8 / 4.0f;
        // Movement comes from vanilla travel() in the same tick (Prince pattern); extra move() here fought physics.
    }

    public void onLivingUpdate()
    {
      List list = null;
      Entity listEntity = null;

      double d6 = this.rand.nextFloat() * 2.0F - 1.0F;
      double d7 = (this.rand.nextInt(2) * 2 - 1) * 0.7D;

      double obstruction_factor = 0.0D;

      double relative_g = 0.0D;

      double max_speed = 0.95D;
      double gh = 1.0D;

      double rt = 0.0D;

      double pi = 3.1415926545D;
      double deltav = 0.0D;

      int dist = 2;

      BetterFireball bf = null;

      if (this.isDead) {
        super.onLivingUpdate();
        return;
      }
      super.onLivingUpdate();

      if (this.world.isRemote)
      {
        if (this.getActivity() != 0 && !this.getPassengers().isEmpty()) {
            Entity rider = this.getPassengers().get(0);
            if (rider instanceof EntityPlayerSP) {
                EntityPlayerSP pp = (EntityPlayerSP)rider;
                pp.connection.sendPacket(new CPacketPlayer.Rotation(pp.rotationYaw, pp.rotationPitch, pp.onGround));
                pp.connection.sendPacket(new CPacketInput(pp.moveStrafing, pp.moveForward, pp.movementInput.jump, pp.movementInput.sneak));
            }
        }
        if ((this.boatPosRotationIncrements > 0) && (getActivity() != 0))
        {
          double d4 = this.posX + (this.boatX - this.posX) / this.boatPosRotationIncrements;
          double d5 = this.posY + (this.boatY - this.posY) / this.boatPosRotationIncrements;
          double d11 = this.posZ + (this.boatZ - this.posZ) / this.boatPosRotationIncrements;
          setPosition(d4, d5, d11);

          this.rotationPitch = (float)(this.rotationPitch + (this.boatPitch - this.rotationPitch) / this.boatPosRotationIncrements);
          double d10 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);
          if ((this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)) != null) d10 = MathHelper.wrapDegrees((this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)).rotationYaw - this.rotationYaw);
          this.rotationYaw = (float)(this.rotationYaw + d10 / this.boatPosRotationIncrements);
          setRotation(this.rotationYaw, this.rotationPitch);
          this.rotationYawHead = this.rotationYaw;

          this.boatPosRotationIncrements -= 1;
        }

      }
      else
      {
        if (this.getActivity() != 0 && !this.getPassengers().isEmpty()) {
            this.fly_with_rider();
        }

        always_do();
      }
    }
    public void updateRiderPosition() {
        if ((this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)) != null) {
            float f = 0.65f;
            this.getPassengers().get(0).setPosition(this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + this.getMountedYOffset() + this.getPassengers().get(0).getYOffset(), this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)));
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
            this.world.spawnParticle("smoke".equals(s) ? net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL : net.minecraft.util.EnumParticleTypes.HEART, this.posX + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 2.5f), this.posY + 0.5 + (double)this.rand.nextFloat() * 1.5, this.posZ + (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 2.5f), d0, d1, d2);
        }
    }

    @Override
    public boolean processInteract(EntityPlayer par1EntityPlayer, EnumHand hand) {
        ItemStack var2 = par1EntityPlayer.getHeldItem(hand);
        if (var2.isEmpty()) {
            var2 = null;
        } else if (var2.getCount() <= 0) {
            par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
            var2 = null;
        }
        if (!this.isTamed()) {
            if (var2 != null && var2.getItem() == Items.BEEF && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (!this.world.isRemote) {
                    if (this.world.rand.nextInt(5) == 1) {
                        this.setTamed(true);
                        this.setOwnerId(par1EntityPlayer.getUniqueID());
                        this.playTameEffect(true);
                        this.world.setEntityState((Entity)this, (byte)7);
                        this.heal((float)this.mygetMaxHealth() - this.getHealth());
                    } else {
                        this.playTameEffect(false);
                        this.world.setEntityState((Entity)this, (byte)6);
                    }
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
        } else {
            if (!this.isOwner((EntityLivingBase)par1EntityPlayer)) {
                return super.processInteract(par1EntityPlayer, hand);
            }
            if (var2 == null && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
                if (!this.world.isRemote) {
                    par1EntityPlayer.startRiding(this);
                    this.setActivity(1);
                    this.setSitting(false);
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.BEEF && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                if ((float)this.mygetMaxHealth() > this.getHealth()) {
                    this.heal((float)this.mygetMaxHealth() - this.getHealth());
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DEADBUSH) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (!this.world.isRemote) {
                    this.setTamed(false);
                    this.setOwnerId((UUID)null);
                    this.playTameEffect(false);
                    this.world.setEntityState((Entity)this, (byte)6);
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.ICE) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.isOwner((EntityLivingBase)par1EntityPlayer)) {
                if (!this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)6);
                    this.setDragonFire(0);
                    par1EntityPlayer.sendMessage(new TextComponentString("Dragon fireballs extinguished."));
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.FLINT_AND_STEEL && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.isOwner((EntityLivingBase)par1EntityPlayer)) {
                if (!this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)6);
                    this.setDragonFire(1);
                    par1EntityPlayer.sendMessage(new TextComponentString("Dragon fireballs lit!"));
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.GUNPOWDER && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.isOwner((EntityLivingBase)par1EntityPlayer) && this.getDragonFire() > 0) {
                if (!this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)6);
                    this.setDragonFire(2);
                    par1EntityPlayer.sendMessage(new TextComponentString("Dragon fireballs supercharged!"));
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.SNOWBALL && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                this.dragontype = 1;
                this.setDragonType(this.dragontype);
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.COAL && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                this.dragontype = 0;
                this.setDragonType(this.dragontype);
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (var2 != null && var2.getItem() == Items.DIAMOND && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.isOwner((EntityLivingBase)par1EntityPlayer) && !this.world.isRemote) {
                Entity ent = null;
                Spyro d = null;
                ent = Dragon.spawnCreature((World)this.world, (String)"Baby Dragon", (double)this.posX, (double)this.posY, (double)this.posZ);
                if (ent != null) {
                    d = (Spyro)ent;
                    if (this.isTamed()) {
                        d.setTamed(true);
                        d.setOwnerId(par1EntityPlayer.getUniqueID());
                    }
                    this.setDead();
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (this.isTamed() && var2 != null && var2.getItem() == Items.NAME_TAG && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.isOwner((EntityLivingBase)par1EntityPlayer)) {
                this.setCustomNameTag(var2.getDisplayName());
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                return true;
            }
            if (var2 != null && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
                if (!this.isSitting()) {
                    this.setSitting(true);
                    this.setActivity(0);
                } else {
                    this.setSitting(false);
                    this.setActivity(0);
                }
                return true;
            }
        }
        return super.processInteract(par1EntityPlayer, hand);
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.BEEF;
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

    public int getDragonFire() {
        return this.getDataManager().get(INT24).intValue();
    }

    public void setDragonFire(int par1) {
        if (this.world.isRemote) {
            return;
        }
        this.getDataManager().set(INT24, par1);
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

    public void setDragonType(int par1) {
        this.getDataManager().set(INT22, par1);
    }

    public int getDragonType() {
        return this.getDataManager().get(INT22).intValue();
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        if ((this.getPassengers().isEmpty() ? null : this.getPassengers().get(0)) != null) {
            return false;
        }
        if (this.isTamed()) {
            return false;
        }
        return true;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("DragonAttacking", this.getAttacking());
        par1NBTTagCompound.setInteger("DragonActivity", this.getActivity());
        par1NBTTagCompound.setInteger("DragonFire", this.getDragonFire());
        par1NBTTagCompound.setInteger("DragonType", this.getDragonType());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setAttacking(par1NBTTagCompound.getInteger("DragonAttacking"));
        this.setActivity(par1NBTTagCompound.getInteger("DragonActivity"));
        this.setDragonFire(par1NBTTagCompound.getInteger("DragonFire"));
        this.dragontype = par1NBTTagCompound.getInteger("DragonType");
        this.setDragonType(this.dragontype);
    }
}

