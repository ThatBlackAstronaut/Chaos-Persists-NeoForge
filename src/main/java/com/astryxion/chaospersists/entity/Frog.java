/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.Cricket
 *  com.astryxion.chaospersists.EntityAnt
 *  com.astryxion.chaospersists.EntityButterfly
 *  com.astryxion.chaospersists.EntityMosquito
 *  com.astryxion.chaospersists.Firefly
 *  com.astryxion.chaospersists.Frog
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.Girlfriend
 *  com.astryxion.chaospersists.MyEntityAIWander
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.WormSmall
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Cricket;
import com.astryxion.chaospersists.entity.EntityAnt;
import com.astryxion.chaospersists.entity.EntityButterfly;
import com.astryxion.chaospersists.entity.EntityMosquito;
import com.astryxion.chaospersists.entity.Firefly;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.util.MyEntityAIWander;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.WormSmall;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/*
 * Exception performing whole class analysis ignored.
 */
public class Frog
extends EntityAnimal {
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(Frog.class, DataSerializers.BYTE);
    private GenericTargetSorter TargetSorter = null;
    public double moveSpeed = 0.10000000149011612;
    private int singing = 0;
    private int jumpcount = 0;

    public Frog(World par1World) {
        super(par1World);
        this.setSize(0.75f, 0.75f);
        this.experienceValue = 5;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
                this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.4));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWander((EntityCreature)this, 1.0f));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ATTACKING, (byte)0);
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return true;
    }

    public int getSinging() {
        return this.getDataManager().get(ATTACKING).intValue();
    }

    public void setSinging(int par1) {
        this.getDataManager().set(ATTACKING, (byte)par1);
    }

    private void jumpAround() {
        this.motionY += (double)(0.75f + Math.abs(this.world.rand.nextFloat() * 0.55f));
        this.posY += 0.3499999940395355;
        float f = 0.7f + Math.abs(this.world.rand.nextFloat() * 0.75f);
        float d = (float)Math.toRadians(this.rotationYaw);
        this.motionX -= (double)f * Math.sin(d);
        this.motionZ += (double)f * Math.cos(d);
        this.isAirBorne = true;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        super.onUpdate();
        if (!this.world.isRemote) {
            if (this.singing != 0) {
                --this.singing;
                if (this.singing <= 0) {
                    this.setSinging(0);
                }
            }
            if (this.jumpcount > 0) {
                --this.jumpcount;
            }
            if (this.jumpcount == 0 && this.world.rand.nextInt(70) == 1) {
                this.jumpAround();
                this.jumpcount = 50;
            }
        }
    }

    public boolean interact(EntityPlayer par1EntityPlayer) {
        block2 : {
            World world;
            block3 : {
                block4 : {
                    if (par1EntityPlayer == null || !par1EntityPlayer.isSneaking() || par1EntityPlayer.inventory.getCurrentItem() != null) break block2;
                    world = par1EntityPlayer.world;
                    this.setDead();
                    par1EntityPlayer.world.playSound(null, par1EntityPlayer.posX, par1EntityPlayer.posY, par1EntityPlayer.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("minecraft", "entity.generic.explode")), net.minecraft.util.SoundCategory.PLAYERS, 1.0f, world.rand.nextFloat() * 0.2f + 0.9f);
                    if (world.isRemote) break block3;
                    if (world.rand.nextInt(2) != 0) break block4;
                    Boyfriend ent = null;
                    ent = (Boyfriend)Frog.spawnCreature((World)world, (String)"Boyfriend", (double)this.posX, (double)(this.posY + 0.01), (double)this.posZ);
                    if (ent != null) {
                        ent.setPrince(1 + world.rand.nextInt(2));
                    }
                    break block2;
                }
                Girlfriend ent = null;
                ent = (Girlfriend)Frog.spawnCreature((World)world, (String)"Girlfriend", (double)this.posX, (double)(this.posY + 0.01), (double)this.posZ);
                if (ent == null) break block2;
                ent.setPrincess(1 + world.rand.nextInt(2));
                break block2;
            }
            for (int var3 = 0; var3 < 16; ++var3) {
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (double)((float)this.posX + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)this.posY + world.rand.nextFloat()), (double)((float)this.posZ + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (double)((float)this.posX + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)this.posY + world.rand.nextFloat()), (double)((float)this.posZ + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, (double)((float)this.posX + world.rand.nextFloat() - world.rand.nextFloat()), (double)((float)this.posY + world.rand.nextFloat()), (double)((float)this.posZ + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
            }
        }
        return false;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public int mygetMaxHealth() {
        return 8;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        if (!this.world.isRemote) {
            if (this.world.rand.nextInt(2) == 0) {
                return null;
            }
            this.singing = 35;
            this.setSinging(this.singing);
        }
        return com.astryxion.chaospersists.core.ChaosSounds.FROG;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource ds) {
        return com.astryxion.chaospersists.core.ChaosSounds.SCORPION_HIT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.BIG_SPLAT;
    }

    protected float getSoundVolume() {
        return 0.7f;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    protected void playStepSound(int par1, int par2, int par3, int par4) {
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(2) - (double)ChaosPersists.ChaosRand.nextInt(2), this.posY + 1.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(2) - (double)ChaosPersists.ChaosRand.nextInt(2), new ItemStack(index, par1, 0));
        this.world.spawnEntity((Entity)var3);
    }

    protected void dropFewItems(boolean par1, int par2) {
        for (int i = 0; i < 4; ++i) {
            this.dropItemRand(Items.SLIME_BALL, 1);
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0f);
        if (par1Entity.isDead) {
            this.heal(1.0f);
        }
        return var4;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        ret = super.attackEntityFrom(par1DamageSource, par2);
        if (!this.world.isRemote && this.jumpcount <= 0) {
            this.jumpAround();
            this.jumpcount = 25;
        }
        return ret;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.25), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    private int findBuddies() {
        List var5 = this.world.getEntitiesWithinAABB(Frog.class, this.getEntityBoundingBox().expand(20.0, 8.0, 20.0));
        return var5.size();
    }

    /**
     * Utopia / Village Mania register frogs as {@link net.minecraft.entity.EnumCreatureType#WATER_CREATURE} only.
     * Without a water check, {@code getCanSpawnHere} still passed on land (Y/daytime/buddies), so bad candidates could
     * flood the surface. Vanilla jungle/river also use {@code AMBIENT} frogs, which must still spawn on land.
     */
    private boolean chaosUtopiaOrVillageDimension() {
        if (this.world == null || this.world.provider == null) {
            return false;
        }
        int d = this.world.provider.getDimension();
        return d == ChaosPersists.getDimension() || d == ChaosPersists.getDimension(3);
    }

    private boolean feetInWater() {
        BlockPos base = new BlockPos(this.posX, this.posY, this.posZ);
        for (int k = 0; k <= 2; k++) {
            if (this.world.getBlockState(base.down(k)).getMaterial() == Material.WATER) {
                return true;
            }
        }
        return false;
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        if (this.chaosUtopiaOrVillageDimension()) {
            if (!this.feetInWater()) {
                return false;
            }
            if (this.findBuddies() > 2) {
                return false;
            }
        } else if (this.findBuddies() > 5) {
            return false;
        }
        return true;
    }

    protected void updateAITasks() {
        boolean xdir = true;
        boolean zdir = true;
        int keep_trying = 50;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.rand.nextInt(12) == 0 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            EntityLivingBase e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.25);
                if (this.getDistanceSq((Entity)e) < 6.0) {
                    this.attackEntityAsMob((Entity)e);
                }
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
        if (par1EntityLiving instanceof EntityAnt) {
            return true;
        }
        if (par1EntityLiving instanceof EntityButterfly) {
            return true;
        }
        if (par1EntityLiving instanceof Cricket) {
            return true;
        }
        if (par1EntityLiving instanceof EntityMosquito) {
            return true;
        }
        if (par1EntityLiving instanceof Firefly) {
            return true;
        }
        if (par1EntityLiving instanceof WormSmall) {
            return true;
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(8.0, 3.0, 8.0));
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

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName(new net.minecraft.util.ResourceLocation("chaospersists", par1), par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }
}

