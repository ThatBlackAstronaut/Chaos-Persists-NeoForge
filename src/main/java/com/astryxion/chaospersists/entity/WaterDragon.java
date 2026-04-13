/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.AttackSquid
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.MyEntityAIFollowOwner
 *  com.astryxion.chaospersists.MyEntityAIWanderALot
 *  com.astryxion.chaospersists.MyUtils
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RenderInfo
 *  com.astryxion.chaospersists.WaterBall
 *  com.astryxion.chaospersists.WaterDragon
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockDeadBush
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMate
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntityAITempt
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.entity.projectile.EntitySmallFireball
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.tileentity.MobSpawnerBaseLogic
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.AttackSquid;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.util.MyEntityAIFollowOwner;
import com.astryxion.chaospersists.util.MyEntityAIWanderALot;
import com.astryxion.chaospersists.util.MyUtils;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.render.RenderInfo;
import com.astryxion.chaospersists.item.WaterBall;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
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
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class WaterDragon
extends EntityTameable {
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(WaterDragon.class, DataSerializers.BYTE);
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int stream_count = 0;
    private int hurt_timer = 0;
    private float moveSpeed = 0.25f;
    private int closest = 99999;
    private int tx = 0;
    private int ty = 0;
    private int tz = 0;

    public WaterDragon(World par1World) {
        super(par1World);
        this.setSize(1.25f, 1.9f);
                this.experienceValue = 100;
                this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIFollowOwner((EntityTameable)this, 2.0f, 10.0f, 2.0f));
        this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.2000000476837158, Items.FISH, false));
        this.tasks.addTask(4, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ChaosPersists.WaterDragon_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ATTACKING, (byte)0);
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

    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.isEmpty()) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
            var2 = null;
        }
        if (super.processInteract(par1EntityPlayer, net.minecraft.util.EnumHand.MAIN_HAND)) {
            return true;
        }
        if (var2 != null && var2.getItem() == Items.FISH && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
            if (!this.isTamed()) {
                if (!this.world.isRemote) {
                    if (this.rand.nextInt(3) == 0) {
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
            } else if (this.isOwner(par1EntityPlayer)) {
                if (this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                if ((float)this.mygetMaxHealth() > this.getHealth()) {
                    this.heal((float)this.mygetMaxHealth() - this.getHealth());
                }
            }
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.isEmpty()) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DEADBUSH) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0 && this.isOwner(par1EntityPlayer)) {
            if (!this.world.isRemote) {
                this.setTamed(false);
                this.setOwnerId((java.util.UUID)null);
                this.playTameEffect(false);
                this.world.setEntityState((Entity)this, (byte)6);
            }
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.isEmpty()) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Items.NAME_TAG && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.isOwner(par1EntityPlayer)) {
            this.setCustomNameTag(var2.getDisplayName());
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.isEmpty()) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                }
            }
            return true;
        }
        if (this.isTamed() && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
            if (!this.isSitting()) {
                this.setSitting(true);
            } else {
                this.setSitting(false);
            }
            return true;
        }
        return false;
    }

    protected boolean canDespawn() {
        if (this.isChild()) {
            this.enablePersistence();
            return false;
        }
        if (this.isNoDespawnRequired()) {
            return false;
        }
        if (this.isTamed()) {
            return false;
        }
        return true;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return ChaosPersists.WaterDragon_stats.health;
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
        return ChaosPersists.WaterDragon_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.moveSpeed = this.isInWater() ? 0.55f : 0.25f;
    }

    public int getWaterDragonHealth() {
        return (int)this.getHealth();
    }

    public int getAttackStrength(Entity par1Entity) {
        int var2 = 4;
        if (this.world.getDifficulty() == EnumDifficulty.EASY) {
            var2 = 6;
            if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                var2 = 8;
            } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                var2 = 10;
            }
        }
        return var2;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return com.astryxion.chaospersists.core.ChaosSounds.WATERDRAGON_HURT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.WATERDRAGON_DEATH;
    }

    protected float getSoundVolume() {
        return 1.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.FISH;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(2) - (double)ChaosPersists.ChaosRand.nextInt(2), this.posY + 1.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(2) - (double)ChaosPersists.ChaosRand.nextInt(2), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(ChaosPersists.MyWaterDragonScale, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int var5 = 9 + this.world.rand.nextInt(6);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.FISH, 1);
        }
        var4 = this.world.rand.nextInt(20);
        switch (var4) {
            case 0: {
                is = this.dropItemRand(ChaosPersists.MyUltimateAxe, 1);
                break;
            }
            case 1: {
                is = this.dropItemRand(Items.IRON_INGOT, 1);
                break;
            }
            case 2: {
                is = this.dropItemRand(ChaosPersists.MyUltimatePickaxe, 1);
                break;
            }
            case 3: {
                is = this.dropItemRand(Items.IRON_SWORD, 1);
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
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(16), 1 + this.world.rand.nextInt(5));
                break;
            }
            case 4: {
                is = this.dropItemRand(Items.IRON_SHOVEL, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                break;
            }
            case 5: {
                is = this.dropItemRand(Items.IRON_PICKAXE, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(35), 1 + this.world.rand.nextInt(5));
                break;
            }
            case 6: {
                is = this.dropItemRand(Items.IRON_AXE, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                break;
            }
            case 7: {
                is = this.dropItemRand(Items.IRON_HOE, 1);
                if (this.world.rand.nextInt(2) == 1) {
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                }
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                break;
            }
            case 8: {
                is = this.dropItemRand((Item)Items.IRON_HELMET, 1);
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
                if (this.world.rand.nextInt(6) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(6), 1 + this.world.rand.nextInt(5));
                break;
            }
            case 9: {
                is = this.dropItemRand((Item)Items.IRON_CHESTPLATE, 1);
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
                if (this.world.rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                break;
            }
            case 10: {
                is = this.dropItemRand((Item)Items.IRON_LEGGINGS, 1);
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
                if (this.world.rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                break;
            }
            case 11: {
                is = this.dropItemRand((Item)Items.IRON_BOOTS, 1);
                if (this.world.rand.nextInt(6) == 1) {
                    is.addEnchantment(Enchantment.getEnchantmentByID(2), 5 + this.world.rand.nextInt(5));
                }
                if (this.world.rand.nextInt(2) != 1) break;
                is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                break;
            }
            case 12: {
                is = this.dropItemRand(ChaosPersists.MyUltimateShovel, 1);
                break;
            }
            case 13: {
                this.dropItemRand(Item.getItemFromBlock((Block)Blocks.IRON_BLOCK), 1);
                break;
            }
            case 14: {
                break;
            }
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)ChaosPersists.WaterDragon_stats.attack);
        if (var4) {
            if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
                double ks = 1.1;
                double inair = 0.14;
                float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
                if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                    inair *= 2.0;
                }
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
            return true;
        }
        return false;
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (par1DamageSource.getDamageType().equals("cactus")) {
            return false;
        }
        Entity e = par1DamageSource.getTrueSource();
        if (e != null && e instanceof WaterDragon) {
            return false;
        }
        if (e != null && e instanceof AttackSquid) {
            return false;
        }
        if (e != null && e instanceof WaterBall) {
            return false;
        }
        if (this.hurt_timer <= 0) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
            this.hurt_timer = 10;
        }
        if (e != null && e instanceof EntityLiving) {
            if (e instanceof AttackSquid) {
                return false;
            }
            if (e instanceof WaterDragon) {
                return false;
            }
            this.setAttackTarget((EntityLivingBase)((EntityLiving)e));
            this.getNavigator().tryMoveToEntityLiving((Entity)((EntityLiving)e), 1.2);
        }
        return ret;
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
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dx * dx + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + dx;
                    this.ty = y + i;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x - dx, y + i, z + j)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dx * dx + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dy * dy + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + dy;
                    this.tz = z + j;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y - dy, z + j)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dy * dy + j * j + i * i) >= this.closest) continue;
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
                if ((bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) && (d = dz * dz + j * j + i * i) < this.closest) {
                    this.closest = d;
                    this.tx = x + i;
                    this.ty = y + j;
                    this.tz = z + dz;
                    ++found;
                }
                if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos(x + i, y + j, z - dz)).getBlock()) != Blocks.WATER && bid != Blocks.FLOWING_WATER || (d = dz * dz + j * j + i * i) >= this.closest) continue;
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

    protected void updateAITasks() {
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (!this.isInWater() && this.world.rand.nextInt(25) == 0 && !this.isSitting()) {
            this.closest = 99999;
            this.tz = 0;
            this.ty = 0;
            this.tx = 0;
            for (int i = 1; i < 12; ++i) {
                int j = i;
                if (j > 10) {
                    j = 10;
                }
                if (this.scan_it((int)this.posX, (int)this.posY - 1, (int)this.posZ, i, j, i)) break;
                if (i < 5) continue;
                ++i;
            }
            if (this.closest < 99999) {
                this.getNavigator().tryMoveToXYZ((double)this.tx, (double)(this.ty - 1), (double)this.tz, 1.33);
            } else {
                if (this.world.rand.nextInt(50) == 1) {
                    this.heal(-1.0f);
                }
                if (this.getHealth() <= 0.0f) {
                    this.setDead();
                    return;
                }
            }
        }
        if (this.world.rand.nextInt(200) == 0) {
            this.setAttackTarget(null);
        }
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(5) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getDistanceSq((Entity)e) < (double)((4.0f + e.width / 2.0f) * (4.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(5) == 1) {
                        this.attackEntityAsMob((Entity)e);
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.0);
                    this.watercanon(e);
                }
            } else {
                this.setAttacking(0);
            }
        }
        if (this.world.rand.nextInt(100) == 1 && this.isInWater() && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.playSound(net.minecraft.init.SoundEvents.ENTITY_GENERIC_SPLASH, 1.5f, this.world.rand.nextFloat() * 0.2f + 0.9f);
            this.heal(1.0f);
        }
    }

    private void watercanon(EntityLivingBase e)
    {
      double yoff = 1.75D;
      double xzoff = 1.5D;

      if (this.stream_count > 0) {
        setAttacking(2);

        if (this.rand.nextInt(15) == 1) {
          EntitySmallFireball var2 = new EntitySmallFireball(this.world, this, e.posX - this.posX, e.posY + 0.75D - (this.posY + yoff), e.posZ - this.posZ);
          var2.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYawHead)), this.rotationYaw, this.rotationPitch);
          this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.init.SoundEvents.ENTITY_ARROW_SHOOT, this.getSoundCategory(), 0.75F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
          this.world.spawnEntity(var2);
        }

        WaterBall var2 = new WaterBall(this.world, e.posX - this.posX, e.posY + 0.75D - (this.posY + yoff), e.posZ - this.posZ);
        var2.setLocationAndAngles(this.posX - xzoff * Math.sin(Math.toRadians(this.rotationYawHead)), this.posY + yoff, this.posZ + xzoff * Math.cos(Math.toRadians(this.rotationYaw)), this.rotationYawHead, this.rotationPitch);
        double var3 = e.posX - var2.posX;
        double var5 = e.posY + 0.25D - var2.posY;
        double var7 = e.posZ - var2.posZ;
        float var9 = MathHelper.sqrt(var3 * var3 + var7 * var7) * 0.2F;
        var2.shoot(var3, var5 + var9, var7, 1.4F, 5.0F);
        this.world.playSound(null, this.posX, this.posY, this.posZ, net.minecraft.init.SoundEvents.ENTITY_ARROW_SHOOT, this.getSoundCategory(), 0.75F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(var2);
        this.stream_count -= 1;
      } else {
        setAttacking(0);
      }

      if ((this.stream_count <= 0) && (this.rand.nextInt(4) == 1)) this.stream_count = 8;
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
        if (par1EntityLiving instanceof WaterDragon) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (this.isTamed()) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
            return true;
        }
        if (MyUtils.isAttackableNonMob((EntityLivingBase)par1EntityLiving)) {
            return true;
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        if (this.isChild()) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(14.0, 4.0, 14.0));
        Collections.sort(var5, this.TargetSorter);
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        EntityLivingBase var4 = null;
        EntityLivingBase e = this.getAttackTarget();
        if (e != null && e.isEntityAlive()) {
            return e;
        }
        this.setAttackTarget(null);
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false)) continue;
            return var4;
        }
        return null;
    }

    public int getAttacking() {
        return this.getDataManager().get(ATTACKING).intValue();
    }

    public void setAttacking(int par1) {
        this.getDataManager().set(ATTACKING, (byte)par1);
    }

    public boolean getCanSpawnHere() {
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));
                                        String s = null;
                    net.minecraft.util.ResourceLocation id = com.astryxion.chaospersists.util.SpawnerFixHelper.getMobSpawnerEntityId(tileentitymobspawner.getSpawnerBaseLogic());
                    if (id != null) s = id.getPath();
                    if (s == null || !s.equals("Water Dragon")) continue;
                    return true;
                }
            }
        }
        WaterDragon target = null;
        if (this.posY < 50.0) {
            return false;
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        target = (WaterDragon)this.world.findNearestEntityWithinAABB(WaterDragon.class, this.getEntityBoundingBox().expand(16.0, 5.0, 16.0), (Entity)this);
        if (target != null) {
            return false;
        }
        return true;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public WaterDragon spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        WaterDragon w = new WaterDragon(this.world);
        if (this.isTamed()) {
            this.setOwnerId(this.getOwnerId());
            w.setTamed(true);
        }
        return w;
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.FISH;
    }

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == ChaosPersists.MyCrystalApple;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }
}

