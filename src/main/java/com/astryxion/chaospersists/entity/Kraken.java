/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.AttackSquid
 *  com.astryxion.chaospersists.Cephadrome
 *  com.astryxion.chaospersists.Chipmunk
 *  com.astryxion.chaospersists.Dragon
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.ItemChaosArmor
 *  com.astryxion.chaospersists.Kraken
 *  com.astryxion.chaospersists.Leon
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.Mothra
 *  com.astryxion.chaospersists.MyUtils
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RenderInfo
 *  com.astryxion.chaospersists.Spyro
 *  com.astryxion.chaospersists.StinkBug
 *  com.astryxion.chaospersists.ThePrinceAdult
 *  com.astryxion.chaospersists.ThePrinceTeen
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockTallGrass
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
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.effect.EntityLightningBolt
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityChicken
 *  net.minecraft.entity.passive.EntitySquid
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.ChunkCoordinates
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 *  net.minecraft.world.storage.WorldInfo
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.AttackSquid;
import com.astryxion.chaospersists.entity.Cephadrome;
import com.astryxion.chaospersists.entity.Chipmunk;
import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.item.ItemChaosArmor;
import com.astryxion.chaospersists.entity.Leon;
import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.entity.Mothra;
import com.astryxion.chaospersists.util.MyUtils;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.render.RenderInfo;
import com.astryxion.chaospersists.entity.Spyro;
import com.astryxion.chaospersists.entity.StinkBug;
import com.astryxion.chaospersists.entity.ThePrinceAdult;
import com.astryxion.chaospersists.entity.ThePrinceTeen;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
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
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

/*
 * Exception performing whole class analysis ignored.
 */
public class Kraken
extends EntityMob {
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(Kraken.class, DataSerializers.BYTE);
    private static final DataParameter<Integer> PLAY_NICELY = EntityDataManager.createKey(Kraken.class, DataSerializers.VARINT);
    private GenericTargetSorter TargetSorter = null;
    private RenderInfo renderdata = new RenderInfo();
    private BlockPos currentFlightTarget = null;
    private EntityLivingBase caught = null;
    private int newtarget = 0;
    private int release = 0;
    private int weather_set = 10;
    private int long_enough = 3600;
    private int call_reinforcements = 0;
    private boolean hit_by_player = false;
    private int straight_down = 1;
    private int hurt_timer = 0;

    public Kraken(World par1World) {
        super(par1World);
        if (ChaosPersists.PlayNicely == 0) {
            this.setSize(4.0f, 15.0f);
        } else {
            this.setSize(1.3333334f, 5.0f);
        }
                this.experienceValue = 500;
                this.isImmuneToFire = true;
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.renderdata = new RenderInfo();
        this.tasks.addTask(1, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3700000047683716);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ChaosPersists.Kraken_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(ATTACKING, (byte)0);
        this.getDataManager().register(PLAY_NICELY, ChaosPersists.PlayNicely);
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

    public int getPlayNicely() {
        return this.getDataManager().get(PLAY_NICELY).intValue();
    }

    public int mygetMaxHealth() {
        return ChaosPersists.Kraken_stats.health;
    }

    public int getKrakenHealth() {
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
        return ChaosPersists.Kraken_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName(new net.minecraft.util.ResourceLocation("chaospersists", (String)par1), par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.isDead) {
            return;
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)(this.posY - 10.0), (int)this.posZ);
        } else {
            this.motionY = this.posY < (double)this.currentFlightTarget.getY() ? (this.motionY *= 0.72) : (this.motionY *= 0.5);
        }
        if (this.weather_set > 0 && ChaosPersists.PlayNicely == 0) {
            --this.weather_set;
            if (this.weather_set == 0 && !this.world.isRemote) {
                WorldInfo worldinfo = this.world.getWorldInfo();
                if (!this.world.isRaining()) {
                    worldinfo.setRainTime(300);
                    worldinfo.setThunderTime(300);
                    worldinfo.setRaining(true);
                    worldinfo.setThundering(true);
                } else {
                    worldinfo.setRainTime(300);
                    worldinfo.setThunderTime(300);
                }
                this.weather_set = 100;
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("LongEnough", this.long_enough);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.long_enough = par1NBTTagCompound.getInteger("LongEnough");
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        if (this.rand.nextInt(5) == 0) {
            return com.astryxion.chaospersists.core.ChaosSounds.KRAKEN_LIVING;
        }
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return null;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.ALO_DEATH;
    }

    protected float getSoundVolume() {
        return 2.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected Item getDropItem() {
        return Items.QUARTZ;
    }

    private ItemStack dropItemRand(Item index, int par1) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(8) - (double)ChaosPersists.ChaosRand.nextInt(8), this.posY + 1.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(8) - (double)ChaosPersists.ChaosRand.nextInt(8), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        ItemStack is = null;
        this.dropItemRand(ChaosPersists.MyKrakenTooth, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        int var5 = 120 + this.world.rand.nextInt(160);
        for (var4 = 0; var4 < var5; ++var4) {
            this.dropItemRand(Items.DYE, 1);
        }
        int i = 5 + this.world.rand.nextInt(10);
        block56 : for (var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(53);
            switch (var3) {
                case 0: {
                    is = this.dropItemRand(ChaosPersists.MyUltimateSword, 1);
                    continue block56;
                }
                case 1: {
                    is = this.dropItemRand(Items.DIAMOND, 1);
                    continue block56;
                }
                case 2: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIAMOND_BLOCK), 1);
                    continue block56;
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
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(16), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 4: {
                    is = this.dropItemRand(Items.DIAMOND_SHOVEL, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 5: {
                    is = this.dropItemRand(Items.DIAMOND_PICKAXE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(35), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 6: {
                    is = this.dropItemRand(Items.DIAMOND_AXE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 7: {
                    is = this.dropItemRand(Items.DIAMOND_HOE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
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
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(6), 1 + this.world.rand.nextInt(5));
                    continue block56;
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
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
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
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 11: {
                    is = this.dropItemRand((Item)Items.DIAMOND_BOOTS, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(2), 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 12: {
                    is = this.dropItemRand(ChaosPersists.MyUltimateBow, 1);
                    continue block56;
                }
                case 13: {
                    is = this.dropItemRand(ChaosPersists.MyUltimateAxe, 1);
                    continue block56;
                }
                case 14: {
                    is = this.dropItemRand(Items.IRON_INGOT, 1);
                    continue block56;
                }
                case 15: {
                    is = this.dropItemRand(ChaosPersists.MyUltimatePickaxe, 1);
                    continue block56;
                }
                case 16: {
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
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(16), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 17: {
                    is = this.dropItemRand(Items.IRON_SHOVEL, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 18: {
                    is = this.dropItemRand(Items.IRON_PICKAXE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(35), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 19: {
                    is = this.dropItemRand(Items.IRON_AXE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 20: {
                    is = this.dropItemRand(Items.IRON_HOE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 21: {
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
                        is.addEnchantment(Enchantment.getEnchantmentByID(5), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(6), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 22: {
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
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 23: {
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
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 24: {
                    is = this.dropItemRand((Item)Items.IRON_BOOTS, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(2), 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 25: {
                    is = this.dropItemRand(ChaosPersists.MyUltimateShovel, 1);
                    continue block56;
                }
                case 26: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.IRON_BLOCK), 1);
                    continue block56;
                }
                case 27: {
                    is = this.dropItemRand(Items.GOLD_NUGGET, 1);
                    continue block56;
                }
                case 28: {
                    is = this.dropItemRand(Items.GOLD_INGOT, 1);
                    continue block56;
                }
                case 29: {
                    is = this.dropItemRand(Items.GOLDEN_CARROT, 1);
                    continue block56;
                }
                case 30: {
                    is = this.dropItemRand(Items.GOLDEN_SWORD, 1);
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
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(16), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 31: {
                    is = this.dropItemRand(Items.GOLDEN_SHOVEL, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 32: {
                    is = this.dropItemRand(Items.GOLDEN_PICKAXE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(35), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 33: {
                    is = this.dropItemRand(Items.GOLDEN_AXE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 34: {
                    is = this.dropItemRand(Items.GOLDEN_HOE, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 35: {
                    is = this.dropItemRand((Item)Items.GOLDEN_HELMET, 1);
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
                        is.addEnchantment(Enchantment.getEnchantmentByID(5), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(6), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 36: {
                    is = this.dropItemRand((Item)Items.GOLDEN_CHESTPLATE, 1);
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
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 37: {
                    is = this.dropItemRand((Item)Items.GOLDEN_LEGGINGS, 1);
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
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 38: {
                    is = this.dropItemRand((Item)Items.GOLDEN_BOOTS, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(2), 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 39: {
                    this.dropItemRand(Items.GOLDEN_APPLE, 1);
                    continue block56;
                }
                case 40: {
                    this.dropItemRand(Item.getItemFromBlock((Block)Blocks.GOLD_BLOCK), 1);
                    continue block56;
                }
                case 41: {
                    EntityItem var33 = null;
                    is = new ItemStack(Items.GOLDEN_APPLE, 1, 1);
                    var33 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(3) - (double)ChaosPersists.ChaosRand.nextInt(3), this.posY + 1.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(3) - (double)ChaosPersists.ChaosRand.nextInt(3), is);
                    if (var33 == null) continue block56;
                    this.world.spawnEntity((Entity)var33);
                    continue block56;
                }
                case 42: {
                    is = this.dropItemRand(ChaosPersists.MyExperienceSword, 1);
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
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(16), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 43: {
                    is = this.dropItemRand((Item)ChaosPersists.ExperienceHelmet, 1);
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
                        is.addEnchantment(Enchantment.getEnchantmentByID(5), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(6), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 44: {
                    is = this.dropItemRand((Item)ChaosPersists.ExperienceBody, 1);
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
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 45: {
                    is = this.dropItemRand((Item)ChaosPersists.ExperienceLegs, 1);
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
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 46: {
                    is = this.dropItemRand((Item)ChaosPersists.ExperienceBoots, 1);
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(2), 5 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(2) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    continue block56;
                }
                case 47: {
                    is = this.dropItemRand(ChaosPersists.MyAmethystSword, 1);
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
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(16), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 48: {
                    is = this.dropItemRand(ChaosPersists.MyAmethystShovel, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 49: {
                    is = this.dropItemRand(ChaosPersists.MyAmethystPickaxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(35), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 50: {
                    is = this.dropItemRand(ChaosPersists.MyAmethystAxe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 51: {
                    is = this.dropItemRand(ChaosPersists.MyAmethystHoe, 1);
                    if (this.world.rand.nextInt(2) == 1) {
                        is.addEnchantment(Enchantment.getEnchantmentByID(34), 2 + this.world.rand.nextInt(4));
                    }
                    if (this.world.rand.nextInt(6) != 1) continue block56;
                    is.addEnchantment(Enchantment.getEnchantmentByID(32), 1 + this.world.rand.nextInt(5));
                    continue block56;
                }
                case 52: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)ChaosPersists.MyBlockAmethystBlock), 1);
                    break;
                }
            }
        }
    }

    public boolean interact(EntityPlayer par1EntityPlayer) {
        return false;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        if (this.long_enough <= 0) {
            return true;
        }
        if (this.posY > 150.0 && this.getHealth() < (float)(this.mygetMaxHealth() / 2)) {
            return true;
        }
        if (this.posY > 180.0 && this.long_enough <= 0) {
            this.setDead();
            return true;
        }
        return false;
    }

    public boolean canSeeTarget(double pX, double pY, double pZ) {
        return this.world.rayTraceBlocks(new Vec3d((double)this.posX, (double)(this.posY + 0.75), (double)this.posZ), new Vec3d((double)pX, (double)pY, (double)pZ), false) == null;
    }

    protected void updateAITasks() {
        int i;
        Block bid;
        int xdir = 1;
        int zdir = 1;
        int keep_trying = 50;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.hurt_timer > 0) {
            --this.hurt_timer;
        }
        if (this.long_enough > 0) {
            --this.long_enough;
        }
        this.getDataManager().set(PLAY_NICELY, ChaosPersists.PlayNicely);
        if (this.world.rand.nextInt(400) == 1 && ChaosPersists.PlayNicely == 0) {
            this.world.addWeatherEffect((Entity)new EntityLightningBolt(this.world, this.posX, this.posY - 16.0, this.posZ, false));
        }
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.newtarget != 0 || this.rand.nextInt(250) == 1 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 9.1) {
            int ground_dist;
            this.newtarget = 0;
            for (ground_dist = 0; ground_dist < 31; ++ground_dist) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY - ground_dist, (int)this.posZ)).getBlock();
                if (bid == Blocks.AIR) continue;
                this.straight_down = 0;
                break;
            }
            ground_dist = 20 - ground_dist;
            bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                zdir = this.world.rand.nextInt(6) + 12;
                xdir = this.world.rand.nextInt(6) + 12;
                if (this.world.rand.nextInt(2) == 0) {
                    zdir = - zdir;
                }
                if (this.world.rand.nextInt(2) == 0) {
                    xdir = - xdir;
                }
                if (this.straight_down != 0) {
                    xdir = 0;
                    zdir = 0;
                }
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX + xdir, (int)this.posY + ground_dist + this.rand.nextInt(9) - 6, (int)this.posZ + zdir);
                bid = this.world.getBlockState(this.currentFlightTarget).getBlock();
                if (bid == Blocks.AIR && !this.canSeeTarget((double)this.currentFlightTarget.getX(), (double)this.currentFlightTarget.getY(), (double)this.currentFlightTarget.getZ())) {
                    bid = Blocks.STONE;
                }
                --keep_trying;
            }
                if (this.long_enough <= 0 || this.posY < 200.0 && this.getHealth() < (float)(this.mygetMaxHealth() / 4)) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos(this.currentFlightTarget.getX(), this.currentFlightTarget.getY() + 30, this.currentFlightTarget.getZ());
                if (this.hit_by_player && this.call_reinforcements == 0 && this.getHealth() < (float)(this.mygetMaxHealth() / 8) && this.posY > 130.0) {
                    this.call_reinforcements = 1;
                    for (i = 0; i < 10; ++i) {
                        EntityCreature newent = (EntityCreature)Kraken.spawnCreature((World)this.world, (String)"The Kraken", (double)(this.posX + (double)this.world.rand.nextInt(10) - (double)this.world.rand.nextInt(10)), (double)170.0, (double)(this.posZ + (double)this.world.rand.nextInt(10) - (double)this.world.rand.nextInt(10)));
                    }
                }
            }
        } else if (this.caught == null && this.world.rand.nextInt(8) == 1 && ChaosPersists.PlayNicely == 0) {
            EntityPlayer target = null;
            target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(25.0, 40.0, 25.0), (Entity)this);
            if (target != null) {
                if (!target.capabilities.isCreativeMode) {
                    if (this.getEntitySenses().canSee((Entity)target)) {
                        this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)target.posX, (int)target.posY + 15, (int)target.posZ);
                        this.attackWithSomething((EntityLivingBase)target);
                    }
                } else {
                    target = null;
                }
            }
            if (target == null && this.world.rand.nextInt(2) == 0) {
                EntityLivingBase e = null;
                e = this.findSomethingToAttack();
                if (e != null) {
                    this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)e.posY + 15, (int)e.posZ);
                    this.attackWithSomething(e);
                }
            }
        }
        if (this.caught != null) {
            if (!this.caught.isDead) {
                this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)this.posX, 200, (int)this.posZ);
                if (this.posY > 190.0) {
                    this.release = 1;
                }
                this.caught.motionX = this.motionX;
                this.caught.motionZ = this.motionZ;
                this.caught.motionY = this.motionY;
                this.caught.posX = this.posX;
                if (this.posY - this.caught.posY > 16.0) {
                    this.caught.motionY += 0.25;
                }
                this.caught.posY = this.posY - 15.0;
                this.caught.posZ = this.posZ;
                this.caught.rotationYaw = this.rotationYaw;
                if (this.world.rand.nextInt(50) == 1) {
                    this.attackEntityAsMob((Entity)this.caught);
                }
                if (this.release != 0 || this.world.rand.nextInt(250) == 1) {
                    this.caught = null;
                    this.newtarget = 1;
                    this.release = 0;
                    this.setAttacking(0);
                }
            } else {
                this.caught = null;
                this.newtarget = 1;
                this.release = 0;
                this.setAttacking(0);
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.3 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.3 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.45 - this.motionX) * 0.15;
        this.motionY += (Math.signum(var3) * 0.70999 - this.motionY) * 0.202;
        this.motionZ += (Math.signum(var5) * 0.45 - this.motionZ) * 0.15;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
        float var8 = MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.4f;
        if (Math.abs(this.motionX) + Math.abs(this.motionZ) < 0.15) {
            var8 = 0.0f;
        }
        this.rotationYaw += var8 / 5.0f;
        double obstruction_factor = 0.0;
        double dx = 0.0;
        double dz = 0.0;
        int dist = 10;
        for (int k = -20; k < 18; k += 2) {
            for (i = 1; i < dist; i += 2) {
                dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)(this.posX + dx), (int)this.posY + k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))))).getBlock();
                if (bid == Blocks.AIR) continue;
                obstruction_factor += 0.1;
            }
        }
        this.motionY += obstruction_factor * 0.08;
        this.posY += obstruction_factor * 0.08;
        if (this.posY > 256.0 && !this.isNoDespawnRequired()) {
            this.setDead();
        }
    }

    private void attackWithSomething(EntityLivingBase par1) {
        if (this.caught != null) {
            return;
        }
        double dist = (this.posX - par1.posX) * (this.posX - par1.posX);
        dist += (this.posZ - par1.posZ) * (this.posZ - par1.posZ);
        if ((dist += (this.posY - par1.posY - 15.0) * (this.posY - par1.posY - 15.0)) < 30.0) {
            this.caught = par1;
            this.release = 0;
            this.setAttacking(1);
        }
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
        if (MyUtils.isIgnoreable((EntityLivingBase)par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
            if (p.capabilities.isFlying) {
                return false;
            }
            return true;
        }
        if (!par1EntityLiving.onGround && !par1EntityLiving.isInWater()) {
            return false;
        }
        if (par1EntityLiving instanceof EntitySquid) {
            return false;
        }
        if (par1EntityLiving instanceof AttackSquid) {
            return false;
        }
        if (par1EntityLiving instanceof Kraken) {
            return false;
        }
        if (par1EntityLiving instanceof Spyro) {
            return false;
        }
        if (par1EntityLiving instanceof Dragon) {
            Dragon c = (Dragon)par1EntityLiving;
            if (c.getControllingPassenger() != null) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof Cephadrome) {
            Cephadrome c = (Cephadrome)par1EntityLiving;
            if (c.getControllingPassenger() != null) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof Leon) {
            Leon c = (Leon)par1EntityLiving;
            if (c.getControllingPassenger() != null) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof ThePrinceTeen) {
            ThePrinceTeen c = (ThePrinceTeen)par1EntityLiving;
            if (c.getControllingPassenger() != null) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof ThePrinceAdult) {
            ThePrinceAdult c = (ThePrinceAdult)par1EntityLiving;
            if (c.getControllingPassenger() != null) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof EntityChicken) {
            return false;
        }
        if (par1EntityLiving instanceof Chipmunk) {
            return false;
        }
        if (par1EntityLiving instanceof StinkBug) {
            return false;
        }
        if (par1EntityLiving instanceof Mothra) {
            return false;
        }
        return true;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(20.0, 40.0, 20.0));
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

    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt) {
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        Entity e = par1DamageSource.getTrueSource();
        boolean ret = false;
        if (this.currentFlightTarget != null && e != null && e instanceof EntityPlayer && this.getHealth() > (float)(this.mygetMaxHealth() / 4)) {
            this.hit_by_player = true;
            this.currentFlightTarget = new net.minecraft.util.math.BlockPos((int)e.posX, (int)e.posY + 15, (int)e.posZ);
        }
        if (this.hurt_timer > 0) {
            return false;
        }
        this.hurt_timer = 30;
        ret = super.attackEntityFrom(par1DamageSource, par2);
        if (this.world.rand.nextInt(2) == 1) {
            this.release = 1;
        }
        return ret;
    }

    public final int getAttacking() {
        return this.getDataManager().get(ATTACKING).intValue();
    }

    public final void setAttacking(int par1) {
        this.getDataManager().set(ATTACKING, (byte)par1);
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        for (int k = -1; k < 2; ++k) {
            for (int j = -1; j < 1; ++j) {
                for (int i = 1; i < 6; ++i) {
                    Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid == Blocks.AIR || bid == Blocks.TALLGRASS) continue;
                    return false;
                }
            }
        }
        return true;
    }
}

