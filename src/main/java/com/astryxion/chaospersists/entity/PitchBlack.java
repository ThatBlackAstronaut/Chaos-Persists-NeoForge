/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.CreepingHorror
 *  com.astryxion.chaospersists.EnderReaper
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.Godzilla
 *  com.astryxion.chaospersists.GodzillaHead
 *  com.astryxion.chaospersists.Island
 *  com.astryxion.chaospersists.IslandToo
 *  com.astryxion.chaospersists.LeafMonster
 *  com.astryxion.chaospersists.LurkingTerror
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.MyEntityAIWanderALot
 *  com.astryxion.chaospersists.MyUtils
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.PitchBlack
 *  com.astryxion.chaospersists.RenderInfo
 *  com.astryxion.chaospersists.TerribleTerror
 *  com.astryxion.chaospersists.Triffid
 *  net.minecraft.block.Block
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveThroughVillage
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.boss.EntityDragon
 *  net.minecraft.entity.boss.EntityDragonPart
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.pathfinding.Path
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.tileentity.MobSpawnerBaseLogic
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.ChunkCoordinates
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.Explosion
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.CreepingHorror;
import com.astryxion.chaospersists.entity.EnderReaper;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.entity.Godzilla;
import com.astryxion.chaospersists.entity.GodzillaHead;
import com.astryxion.chaospersists.entity.Island;
import com.astryxion.chaospersists.entity.IslandToo;
import com.astryxion.chaospersists.entity.LeafMonster;
import com.astryxion.chaospersists.entity.LurkingTerror;
import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.util.MyEntityAIWanderALot;
import com.astryxion.chaospersists.util.MyUtils;
import com.astryxion.chaospersists.util.SpawnerFixHelper;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.render.RenderInfo;
import com.astryxion.chaospersists.entity.TerribleTerror;
import com.astryxion.chaospersists.entity.Triffid;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

public class PitchBlack
extends EntityMob {
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(PitchBlack.class, DataSerializers.BYTE);
    private static final DataParameter<Byte> ACTIVITY = EntityDataManager.createKey(PitchBlack.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> SCALE_INT = EntityDataManager.createKey(PitchBlack.class, DataSerializers.VARINT);
    private BlockPos currentFlightTarget = null;
    private GenericTargetSorter TargetSorter = null;
    /** When true, bypasses canDespawn (egg/command spawns persist). Natural spawns leave this false. */
    private boolean spawnedFromEgg = false;
    /** Spawner-spawned nightmares should also bypass daytime despawn rules. */
    private boolean spawnedFromSpawner = false;
    private RenderInfo renderdata = new RenderInfo();
    private float MyMoveSpeed = 0.2f;
    private int damage_ticker = 0;
    private int wing_sound = 0;

    public PitchBlack(World par1World) {
        super(par1World);
        this.setSize(2.0f, 3.0f);
                this.experienceValue = 200;
        this.isImmuneToFire = false;
                this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 10.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.MyMoveSpeed = 0.2f;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(this.MyMoveSpeed + 0.1f * this.getPitchBlackScale()));
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)(this.getPitchBlackScale() * (float)ChaosPersists.PitchBlack_stats.attack));
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ATTACKING, (byte)0);
        this.getDataManager().register(ACTIVITY, (byte)0);
        this.getDataManager().register(SCALE_INT, 0);
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
        float t = 0.5f;
        if (this.world != null) {
            if (this.world.rand.nextInt(4) == 1) {
                t = 1.0f;
            }
            if (this.world.rand.nextInt(8) == 2) {
                t = 2.0f;
            }
            if (this.world.rand.nextInt(32) == 3) {
                t = 3.0f;
            }
            if (this.world.rand.nextInt(64) == 4) {
                t = 4.0f;
            }
        } else {
            if (ChaosPersists.ChaosRand.nextInt(4) == 1) {
                t = 1.0f;
            }
            if (ChaosPersists.ChaosRand.nextInt(8) == 2) {
                t = 2.0f;
            }
            if (ChaosPersists.ChaosRand.nextInt(32) == 3) {
                t = 3.0f;
            }
            if (ChaosPersists.ChaosRand.nextInt(64) == 4) {
                t = 4.0f;
            }
        }
        if (ChaosPersists.NightmareSize == 1) {
            t = 0.5f;
        }
        if (ChaosPersists.NightmareSize == 2) {
            t = 1.0f;
        }
        if (ChaosPersists.NightmareSize == 3) {
            t = 2.0f;
        }
        if (ChaosPersists.NightmareSize == 4) {
            t = 3.0f;
        }
        if (ChaosPersists.NightmareSize == 5) {
            t = 4.0f;
        }
        this.setPitchBlackScale(t);
        this.experienceValue = (int)(100.0f * t);
        this.isImmuneToFire = true;
        this.setSize(2.5f * this.getPitchBlackScale(), 3.5f * this.getPitchBlackScale());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.spawnedFromEgg = par1NBTTagCompound.getBoolean("SpawnedFromEgg");
        this.spawnedFromSpawner = par1NBTTagCompound.getBoolean("SpawnedFromSpawner");
        this.setPitchBlackScale(par1NBTTagCompound.getFloat("Fscale"));
        this.setSize(2.5f * this.getPitchBlackScale(), 3.5f * this.getPitchBlackScale());
        this.experienceValue = (int)(100.0f * this.getPitchBlackScale());
        this.isImmuneToFire = true;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("SpawnedFromEgg", this.spawnedFromEgg);
        par1NBTTagCompound.setBoolean("SpawnedFromSpawner", this.spawnedFromSpawner);
        par1NBTTagCompound.setFloat("Fscale", this.getPitchBlackScale());
    }

    public final int getAttacking() {
        return this.getDataManager().get(ATTACKING).intValue();
    }

    public final void setAttacking(int par1) {
        this.getDataManager().set(ATTACKING, (byte)par1);
    }

    /**
     * Defensive fallback: if a Nightmare spawns near a Nightmare spawner but missed
     * the normal spawn flag handoff, mark it as spawner-spawned on early ticks.
     */
    private void syncSpawnerSpawnStateIfNeeded() {
        if (this.world == null || this.world.isRemote || this.spawnedFromSpawner || this.ticksExisted > 40) {
            return;
        }
        BlockPos base = new BlockPos(this.posX, this.posY, this.posZ);
        for (int dx = -8; dx <= 8; ++dx) {
            for (int dy = -4; dy <= 8; ++dy) {
                for (int dz = -8; dz <= 8; ++dz) {
                    TileEntity te = this.world.getTileEntity(base.add(dx, dy, dz));
                    if (!(te instanceof TileEntityMobSpawner)) {
                        continue;
                    }
                    String path = null;
                    net.minecraft.util.ResourceLocation id = SpawnerFixHelper.getMobSpawnerEntityId(((TileEntityMobSpawner) te).getSpawnerBaseLogic());
                    if (id != null) {
                        path = SpawnerFixHelper.normalizeSpawnerEntityId(id).getPath();
                    }
                    if (path != null && "nightmare".equalsIgnoreCase(path)) {
                        this.setSpawnedFromSpawner();
                        float t = this.getPitchBlackScale();
                        if (t > 1.0f) {
                            this.setPitchBlackScale(1.0f);
                            this.setSize(2.5f, 3.5f);
                        }
                        return;
                    }
                }
            }
        }
    }

    public final int getActivity() {
        return this.getDataManager().get(ACTIVITY).byteValue();
    }

    public final void setActivity(int par1) {
        this.getDataManager().set(ACTIVITY, (byte)par1);
    }

    public float getPitchBlackScale() {
        int i = this.getDataManager().get(SCALE_INT).intValue();
        float f = i;
        f /= 10.0f;
        // Defensive clamp: avoid zero/invalid scale causing invisible spawner previews.
        if (f < 0.5f) {
            return 0.5f;
        }
        return f;
    }

    public void setPitchBlackScale(float par1) {
        if (par1 < 0.5f) {
            par1 = 0.5f;
        }
        float f = par1 * 10.0001f;
        int i = (int)f;
        this.getDataManager().set(SCALE_INT, i);
    }

    public int getTotalArmorValue() {
        return ChaosPersists.PitchBlack_stats.defense + (int)(2.0f * this.getPitchBlackScale());
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

    /** Set when spawned via egg or /summon {SpawnedFromEgg:1b} — bypasses daytime despawn. */
    public void setSpawnedFromEgg() {
        this.spawnedFromEgg = true;
    }

    /** Set when spawned from a mob spawner — bypasses daytime despawn. */
    public void setSpawnedFromSpawner() {
        this.spawnedFromSpawner = true;
        this.enablePersistence();
    }

    protected boolean canDespawn() {
        if (this.spawnedFromEgg || this.spawnedFromSpawner) {
            return false;
        }
        // Avoid edge cases where the spawn flag arrives a tick late for spawner spawns.
        if (this.ticksExisted < 40) {
            return false;
        }
        if (this.isNoDespawnRequired()) {
            return false;
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        return true;
    }

    protected float getSoundVolume() {
        return 0.75f;
    }

    protected float getSoundPitch() {
        return 1.0f - 0.7f * (4.0f / this.getPitchBlackScale());
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        if (this.world.rand.nextInt(5) != 2) {
            return null;
        }
        return com.astryxion.chaospersists.core.ChaosSounds.PITCHBLACK_LIVING;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return com.astryxion.chaospersists.core.ChaosSounds.PITCHBLACK_HIT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.PITCHBLACK_DEAD;
    }

    public int mygetMaxHealth() {
        return (int)((float)ChaosPersists.PitchBlack_stats.health * this.getPitchBlackScale());
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onUpdate() {
        this.syncSpawnerSpawnStateIfNeeded();
        if (this.getPitchBlackScale() < 0.5f) {
            this.setPitchBlackScale(0.5f);
        }
        this.MyMoveSpeed = 0.2f;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(this.MyMoveSpeed + 0.1f * this.getPitchBlackScale()));
        super.onUpdate();
        this.setSize(2.5f * this.getPitchBlackScale(), 3.5f * this.getPitchBlackScale());
        ++this.wing_sound;
        if (this.wing_sound > 20) {
            if (!this.world.isRemote) {
                this.world.playSound(this.posX, this.posY, this.posZ, com.astryxion.chaospersists.core.ChaosSounds.MOTHRA_WINGS, this.getSoundCategory(), 1.0f, 1.0f, false);
            }
            this.wing_sound = 0;
        }
        this.motionY *= 0.6;
        if (!this.world.isRemote && this.world.rand.nextInt(250) == 1) {
            this.heal(1.0f + this.getPitchBlackScale());
            if (this.world.rand.nextInt(5) == 0) {
                Block bid = Blocks.AIR;
                if (this.posY > 10.0) {
                    for (int i = 0; i < 10 && (bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY - i, (int)this.posZ)).getBlock()) == Blocks.AIR; ++i) {
                    }
                } else {
                    bid = Blocks.STONE;
                }
                if (bid != Blocks.AIR) {
                    Entity e = null;
                    e = this.findSomethingToAttack();
                    if (e == null) {
                        this.setActivity(0);
                    }
                }
            } else {
                this.setActivity(1);
                this.getNavigator().setPath(null, 0.0);
            }
        }
        if (this.getActivity() == 0 && this.world.rand.nextInt(10) == 1) {
            Entity e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                this.setActivity(1);
                this.getNavigator().setPath(null, 0.0);
            }
        }
    }

    @Override
    public void setDead() {
        // Spawner/egg Nightmares must not be culled by generic despawn/peaceful cleanup.
        // Allow death from real damage (health <= 0).
        if (!this.world.isRemote && (this.spawnedFromEgg || this.spawnedFromSpawner) && this.getHealth() > 0.0f) {
            return;
        }
        super.setDead();
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4 = false;
        if (par1Entity != null && par1Entity instanceof EntityDragon) {
            EntityDragon dr = (EntityDragon)par1Entity;
            DamageSource var21 = null;
            var21 = DamageSource.causeExplosionDamage((Explosion)null);
            var21.setExplosion();
            if (this.world.rand.nextInt(8) == 1) {
                dr.attackEntityFromPart(dr.dragonPartHead, var21, (float)ChaosPersists.PitchBlack_stats.attack * this.getPitchBlackScale());
            } else {
                dr.attackEntityFromPart(dr.dragonPartBody, var21, (float)ChaosPersists.PitchBlack_stats.attack * this.getPitchBlackScale());
            }
            var4 = true;
        } else {
            var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)ChaosPersists.PitchBlack_stats.attack * this.getPitchBlackScale());
            if (var4 && par1Entity != null && par1Entity instanceof EntityLivingBase) {
                double ks = 1.15 * (double)this.getPitchBlackScale();
                double inair = 0.08 * (double)this.getPitchBlackScale();
                float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
                if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                    inair *= 2.0;
                }
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }
        return var4;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        if (this.damage_ticker > 0) {
            --this.damage_ticker;
        }
        if (this.getActivity() == 0) {
            super.updateAITasks();
            return;
        }
        if (this.isDead) {
            return;
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.getActivity() == 0) {
            return;
        }
        if (this.rand.nextInt(150) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 2.1f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying > 0) {
                zdir = this.rand.nextInt(20) + 5 * (int)this.getPitchBlackScale();
                xdir = this.rand.nextInt(20) + 5 * (int)this.getPitchBlackScale();
                if (this.rand.nextInt(2) == 0) {
                    zdir = - zdir;
                }
                if (this.rand.nextInt(2) == 0) {
                    xdir = - xdir;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(11) - 5, (int)this.posZ + zdir);
                bid = this.world.getBlockState(this.currentFlightTarget).getBlock();
                if (bid == Blocks.AIR && !this.canSeeTarget((double)this.currentFlightTarget.getX(), (double)this.currentFlightTarget.getY(), (double)this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
        } else if (this.rand.nextInt(8) == 0) {
            Entity e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                double d1 = 5.0 + (double)(e.width / 2.0f);
                d1 += (double)this.getPitchBlackScale();
                d1 *= d1;
                this.setAttacking(1);
                if (e instanceof EntityDragon && d1 < 100.0) {
                    d1 = 100.0;
                }
                if (e instanceof Godzilla && d1 < 100.0) {
                    d1 = 100.0;
                }
                if (e instanceof GodzillaHead && d1 < 100.0) {
                    d1 = 100.0;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + 2.0), (int)e.posZ);
                if (this.getDistanceSq(e) < d1) {
                    this.attackEntityAsMob(e);
                }
            } else {
                this.setAttacking(0);
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.4 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.4 - this.posZ;
        double myspeed = 0.5f + this.getPitchBlackScale() / 10.0f;
        this.motionX += (Math.signum(var1) * myspeed - this.motionX) * 0.33;
        this.motionY += (Math.signum(var3) * 0.699999988079071 - this.motionY) * 0.20000000149011612;
        this.motionZ += (Math.signum(var5) * myspeed - this.motionZ) * 0.33;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
        float var8 = MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.1f + (float)myspeed;
        this.rotationYaw += var8 / 5.0f;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (this.damage_ticker > 0) {
            return ret;
        }
        this.damage_ticker = 20;
        ret = super.attackEntityFrom(par1DamageSource, par2);
        Entity e = par1DamageSource.getTrueSource();
        if (e != null && this.currentFlightTarget != null) {
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)(e.posY + 2.0), (int)e.posZ);
        }
        this.setActivity(1);
        this.getNavigator().setPath(null, 0.0);
        return ret;
    }

    public boolean getCanSpawnHere() {
        Block bid;
        int j;
        int i;
        int k;
        for (k = -5; k <= 5; ++k) {
            for (j = -5; j <= 5; ++j) {
                for (i = -2; i <= 6; ++i) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    // Keep spawner checks mapping-agnostic (reflection on getEntityId can fail by runtime names).
                    Float t = Float.valueOf(this.getPitchBlackScale());
                    if (t.floatValue() > 1.0f) {
                        t = Float.valueOf(1.0f);
                    }
                    this.setPitchBlackScale(t.floatValue());
                    this.setSpawnedFromSpawner();
                    return true;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.isDaytime()) {
            return false;
        }
        if (this.world.provider.getDimension() == ChaosPersists.getDimension(6)) {
            PitchBlack target = null;
            target = (PitchBlack)this.world.findNearestEntityWithinAABB(PitchBlack.class, this.getEntityBoundingBox().expand(16.0, 16.0, 16.0), (Entity)this);
            if (target != null) {
                return false;
            }
        }
        if (this.getPitchBlackScale() < 1.1f) {
            return true;
        }
        int ix = 1;
        if (this.getPitchBlackScale() > 3.1f) {
            ix = 2;
        }
        int iy = ix * 3;
        for (k = - ix; k <= ix; ++k) {
            for (j = - ix; j <= ix; ++j) {
                for (i = 1; i <= iy; ++i) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!(par1EntityLiving instanceof EntityLivingBase)) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (MyUtils.isIgnoreable((EntityLivingBase)par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof PitchBlack) {
            return false;
        }
        if (par1EntityLiving instanceof EnderReaper) {
            return false;
        }
        if (par1EntityLiving instanceof LeafMonster) {
            return false;
        }
        if (par1EntityLiving instanceof TerribleTerror) {
            return false;
        }
        if (par1EntityLiving instanceof LurkingTerror) {
            return false;
        }
        if (par1EntityLiving instanceof CreepingHorror) {
            return false;
        }
        if (par1EntityLiving instanceof Island) {
            return false;
        }
        if (par1EntityLiving instanceof IslandToo) {
            return false;
        }
        if (par1EntityLiving instanceof Triffid) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
        }
        return true;
    }

    private Entity findSomethingToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        double d1 = 16.0 + (double)(this.getPitchBlackScale() * 6.0f);
        double d2 = 10.0 + (double)(this.getPitchBlackScale() * 4.0f);
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(d1, d2, d1));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        EntityLivingBase var3 = null;
        while (var2.hasNext()) {
            var3 = (EntityLivingBase)var2.next();
            if (!this.isSuitableTarget(var3, false)) continue;
            return var3;
        }
        return null;
    }

    protected Item getDropItem() {
        return ChaosPersists.MyNightmareScale;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)((float)ChaosPersists.ChaosRand.nextInt(5) * this.getPitchBlackScale()) - (double)((float)ChaosPersists.ChaosRand.nextInt(5) * this.getPitchBlackScale()), this.posY + 1.0, this.posZ + (double)((float)ChaosPersists.ChaosRand.nextInt(5) * this.getPitchBlackScale()) - (double)((float)ChaosPersists.ChaosRand.nextInt(5) * this.getPitchBlackScale()), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        int i = 3 + this.world.rand.nextInt(2 + (int)(5.0f * this.getPitchBlackScale()));
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.ROTTEN_FLESH, 1);
            int j = this.world.rand.nextInt(10);
            if (j == 0) {
                this.dropItemRand(Items.FEATHER, 1);
            }
            if (j == 1) {
                this.dropItemRand(Items.STRING, 1);
            }
            if (j == 2) {
                this.dropItemRand(Items.FLINT, 1);
            }
            if (j != 3) continue;
            this.dropItemRand(Items.BEEF, 1);
        }
        this.dropItemRand(ChaosPersists.MyNightmareScale, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        i = 2 + (int)this.getPitchBlackScale() + this.world.rand.nextInt(2 + (int)(5.0f * this.getPitchBlackScale()));
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(ChaosPersists.ZooKeeper, 1);
        }
    }
}

