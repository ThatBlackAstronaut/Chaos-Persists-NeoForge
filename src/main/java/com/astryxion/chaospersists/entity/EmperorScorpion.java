/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EmperorScorpion
 *  com.astryxion.chaospersists.EnderKnight
 *  com.astryxion.chaospersists.EnderReaper
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.MyEntityAIWanderALot
 *  com.astryxion.chaospersists.MyUtils
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RenderInfo
 *  com.astryxion.chaospersists.Scorpion
 *  net.minecraft.block.Block
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIHurtByTarget
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveThroughVillage
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.monster.EntityEnderman
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.tileentity.MobSpawnerBaseLogic
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.EnderKnight;
import com.astryxion.chaospersists.entity.EnderReaper;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.util.MyEntityAIWanderALot;
import com.astryxion.chaospersists.util.MyUtils;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.render.RenderInfo;
import com.astryxion.chaospersists.entity.Scorpion;
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
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/*
 * Exception performing whole class analysis ignored.
 */
public class EmperorScorpion
extends EntityMob {
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(EmperorScorpion.class, DataSerializers.BYTE);
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private int hurt_timer = 0;
    private float moveSpeed = 0.35f;

    public EmperorScorpion(World par1World) {
        super(par1World);
        this.setSize(3.5f, 3.0f);
                this.experienceValue = 200;
                this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 0.8999999761581421, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 14, 1.0));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
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

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ChaosPersists.EmperorScorpion_stats.attack);
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return true;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public int mygetMaxHealth() {
        return ChaosPersists.EmperorScorpion_stats.health;
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
        return ChaosPersists.EmperorScorpion_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    protected void jump() {
        super.jump();
        this.motionY += 0.25;
        this.posY += 0.5;
    }

    public int getEmperorScorpionHealth() {
        return (int)this.getHealth();
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return com.astryxion.chaospersists.core.ChaosSounds.ALO_HURT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.EMPERORSCORPION_DEATH;
    }

    protected float getSoundVolume() {
        return 1.5f;
    }

    protected float getSoundPitch() {
        return 1.0f;
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
        this.dropItemRand(ChaosPersists.MyEmperorScorpionScale, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int i = 4 + this.world.rand.nextInt(5);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Item.getItemFromBlock((Block)Blocks.OBSIDIAN), 1);
        }
        i = 4 + this.world.rand.nextInt(8);
        for (var4 = 0; var4 < i; ++var4) {
            this.dropItemRand(Items.BEEF, 1);
        }
        i = 1 + this.world.rand.nextInt(5);
        block17 : for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(20);
            ItemStack is;
            switch (var3) 
            {
                case 0: {
                    is = this.dropItemRand(ChaosPersists.MyUltimateSword, 1);
                    continue block17;
                }
                case 1: {
                    is = this.dropItemRand(Items.DIAMOND, 1);
                    continue block17;
                }
                case 2: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIAMOND_BLOCK), 1);
                    continue block17;
                }
                case 3: {
                    is = this.dropItemRand(Items.DIAMOND_SWORD, 1);
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
                    is = this.dropItemRand(Items.DIAMOND_SHOVEL, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 5: {
                    is = this.dropItemRand(Items.DIAMOND_PICKAXE, 1);
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
                    is = this.dropItemRand(Items.DIAMOND_AXE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 7: {
                    is = this.dropItemRand(Items.DIAMOND_HOE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block17;
                }
                case 8: {
                    is = this.dropItemRand((Item)Items.DIAMOND_HELMET, 1);
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
                    is = this.dropItemRand((Item)Items.DIAMOND_CHESTPLATE, 1);
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
                    is = this.dropItemRand((Item)Items.DIAMOND_LEGGINGS, 1);
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
                    is = this.dropItemRand((Item)Items.DIAMOND_BOOTS, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(2), 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block17;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block17;
                }
                case 12: {
                    is = this.dropItemRand(ChaosPersists.MyUltimateBow, 1);
                    break;
                }
            }
        }
    }

    public boolean interact(EntityPlayer par1EntityPlayer) {
        return false;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        double ks = 3.0;
        double inair = 0.2;
        int var2 = 6;
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
                if (this.world.getDifficulty() == EnumDifficulty.EASY) {
                    var2 = 8;
                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                        var2 = 10;
                    } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                        var2 = 12;
                    }
                }
                if (this.world.rand.nextInt(3) == 1) {
                    ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.getPotionById(19), var2 * 15, 0));
                }
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
        if (this.hurt_timer > 0) {
            return false;
        }
        if (!par1DamageSource.getDamageType().equals("cactus")) {
            ret = super.attackEntityFrom(par1DamageSource, par2);
            this.hurt_timer = 30;
            Entity e = par1DamageSource.getTrueSource();
            if (e != null && e instanceof EntityLiving) {
                this.setAttackTarget((EntityLivingBase)((EntityLiving)e));
                this.setAttackTarget(e instanceof net.minecraft.entity.EntityLivingBase ? (net.minecraft.entity.EntityLivingBase)e : null);
                this.getNavigator().tryMoveToEntityLiving((Entity)((EntityLiving)e), 1.2);
            }
        }
        return ret;
    }

    protected void updateAITasks() {
        EntityLivingBase e = null;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.world.rand.nextInt(4) == 0) {
            e = this.getAttackTarget();
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (this.world.rand.nextInt(100) == 0) {
                this.setAttackTarget(null);
            }
            if (e == null) {
                e = this.findSomethingToAttack();
            }
            if (e != null) {
                this.faceEntity((Entity)e, 10.0f, 10.0f);
                if (this.getDistanceSq((Entity)e) < (double)((6.0f + e.width / 2.0f) * (6.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    if (this.world.rand.nextInt(4) == 0 || this.world.rand.nextInt(6) == 1) {
                        this.attackEntityAsMob((Entity)e);
                        if (!this.world.isRemote) {
                            if (this.world.rand.nextInt(3) == 1) {
                                this.world.playSound(null, e.posX, e.posY, e.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("chaospersists", "scorpion_attack")), net.minecraft.util.SoundCategory.HOSTILE, 1.4f, 1.0f);
                            } else {
                                this.world.playSound(null, e.posX, e.posY, e.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("chaospersists", "scorpion_living")), net.minecraft.util.SoundCategory.HOSTILE, 1.0f, 1.0f);
                            }
                        }
                    }
                } else {
                    this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.2);
                }
                if (this.world.rand.nextInt(20) == 1) {
                    EntityCreature newent = (EntityCreature)EmperorScorpion.spawnCreature((World)this.world, (String)"Scorpion", (double)((this.posX + e.posX) / 2.0 + (double)this.world.rand.nextInt(5) - (double)this.world.rand.nextInt(5)), (double)((this.posY + e.posY) / 2.0 + 1.01), (double)((this.posZ + e.posZ) / 2.0 + (double)this.world.rand.nextInt(5) - (double)this.world.rand.nextInt(5)));
                }
            } else {
                this.setAttacking(0);
            }
        }
        if (this.world.rand.nextInt(100) == 1 && this.getHealth() < (float)this.mygetMaxHealth()) {
            this.heal(2.0f);
        }
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

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
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
        if (MyUtils.isIgnoreable((EntityLivingBase)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof EntityEnderman) {
            return false;
        }
        if (par1EntityLiving instanceof EnderKnight) {
            return false;
        }
        if (par1EntityLiving instanceof EnderReaper) {
            return false;
        }
        if (par1EntityLiving instanceof EntityCreeper) {
            return false;
        }
        if (par1EntityLiving instanceof Scorpion) {
            return false;
        }
        if (par1EntityLiving instanceof EmperorScorpion) {
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

    private EntityLivingBase findSomethingToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(24.0, 6.0, 24.0));
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

    public final int getAttacking() {
        return this.getDataManager().get(ATTACKING).intValue();
    }

    public final void setAttacking(int par1) {
        this.getDataManager().set(ATTACKING, (byte)par1);
    }

    public boolean getCanSpawnHere() {
        for (int k = -2; k < 2; ++k) {
            for (int j = -2; j < 2; ++j) {
                for (int i = 2; i < 5; ++i) {
                    Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid == Blocks.MOB_SPAWNER) {
                        TileEntityMobSpawner tileentitymobspawner = null;
                        tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));
                                            String s = null;
                    net.minecraft.util.ResourceLocation id = com.astryxion.chaospersists.util.SpawnerFixHelper.getMobSpawnerEntityId(tileentitymobspawner.getSpawnerBaseLogic());
                    if (id != null) s = id.getPath();
                        if (s != null && s.equals("Emperor Scorpion")) {
                            return true;
                        }
                    }
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        if (!this.isValidLightLevel()) {
            return false;
        }
        if (this.world.isDaytime()) {
            return false;
        }
        if (this.posY < 50.0) {
            return false;
        }
        EmperorScorpion target = null;
        target = (EmperorScorpion)this.world.findNearestEntityWithinAABB(EmperorScorpion.class, this.getEntityBoundingBox().expand(20.0, 6.0, 20.0), (Entity)this);
        if (target != null) {
            return false;
        }
        return true;
    }
}

