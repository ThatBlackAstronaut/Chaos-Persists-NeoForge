/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.Elevator
 *  com.astryxion.chaospersists.ItemChaosArmor
 *  com.astryxion.chaospersists.MyEntityAIFollowOwner
 *  com.astryxion.chaospersists.MyEntityAIJealousy
 *  com.astryxion.chaospersists.MyEntityAINearestAttackableTarget
 *  com.astryxion.chaospersists.MyEntityAIWander
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.Shoes
 *  com.astryxion.chaospersists.UltimateArrow
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockDeadBush
 *  net.minecraft.block.BlockFlower
 *  net.minecraft.command.IEntitySelector
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.IRangedAttackMob
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIArrowAttack
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveIndoors
 *  net.minecraft.entity.ai.EntityAIOpenDoor
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntityAITempt
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityCreeper
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.entity.projectile.EntityArrow
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemFood
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.potion.Potion
 *  net.minecraft.potion.PotionEffect
 *  net.minecraft.tileentity.MobSpawnerBaseLogic
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.ChatComponentText
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.IChatComponent
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.item.Elevator;
import com.astryxion.chaospersists.util.MyEntityAIFollowOwner;
import com.astryxion.chaospersists.util.MyEntityAIJealousy;
import com.astryxion.chaospersists.util.MyEntityAINearestAttackableTarget;
import com.astryxion.chaospersists.util.MyEntityAIWander;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.item.Shoes;
import com.astryxion.chaospersists.item.UltimateArrow;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDeadBush;
import net.minecraft.block.BlockFlower;
import com.google.common.base.Predicate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumHand;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;

public class Boyfriend
extends EntityTameable
implements IRangedAttackMob {

    @Override
    public void setSwingingArms(boolean swingingArms) {}
    private static final DataParameter<Integer> WHICH_GUY = EntityDataManager.createKey(Boyfriend.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> VOICE = EntityDataManager.createKey(Boyfriend.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> WHICH_WET_GUY = EntityDataManager.createKey(Boyfriend.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> VOICE_ENABLE = EntityDataManager.createKey(Boyfriend.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> IS_PRINCE = EntityDataManager.createKey(Boyfriend.class, DataSerializers.VARINT);
    private static final EntityEquipmentSlot[] BOYFRIEND_STRIP_SLOTS = new EntityEquipmentSlot[]{
        EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.FEET, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.HEAD, EntityEquipmentSlot.OFFHAND
    };
    public int which_guy = 0;
    public int which_wet_guy = 0;
    public int wet_count = 0;
    private int auto_heal = 200;
    private int force_sync = 50;
    private int fight_sound_ticker = 0;
    private int taunt_sound_ticker = 0;
    private int had_target = 0;
    private int voice = 0;
    private float moveSpeed = 0.3f;
    private int voice_enable = 1;
    public int passenger = 0;
    private int is_prince = 0;
    private static final ResourceLocation DryTexture0 = new ResourceLocation("chaospersists", "textures/entity/boyfriend0.png");
    private static final ResourceLocation DryTexture1 = new ResourceLocation("chaospersists", "textures/entity/boyfriend1.png");
    private static final ResourceLocation DryTexture2 = new ResourceLocation("chaospersists", "textures/entity/boyfriend2.png");
    private static final ResourceLocation DryTexture3 = new ResourceLocation("chaospersists", "textures/entity/boyfriend3.png");
    private static final ResourceLocation DryTexture4 = new ResourceLocation("chaospersists", "textures/entity/boyfriend4.png");
    private static final ResourceLocation DryTexture5 = new ResourceLocation("chaospersists", "textures/entity/boyfriend5.png");
    private static final ResourceLocation DryTexture6 = new ResourceLocation("chaospersists", "textures/entity/boyfriend6.png");
    private static final ResourceLocation DryTexture7 = new ResourceLocation("chaospersists", "textures/entity/boyfriend7.png");
    private static final ResourceLocation DryTexture8 = new ResourceLocation("chaospersists", "textures/entity/boyfriend8.png");
    private static final ResourceLocation DryTexture9 = new ResourceLocation("chaospersists", "textures/entity/boyfriend9.png");
    private static final ResourceLocation DryTexture10 = new ResourceLocation("chaospersists", "textures/entity/boyfriend10.png");
    private static final ResourceLocation DryTexture11 = new ResourceLocation("chaospersists", "textures/entity/boyfriend11.png");
    private static final ResourceLocation DryTexture12 = new ResourceLocation("chaospersists", "textures/entity/boyfriend12.png");
    private static final ResourceLocation DryTexture13 = new ResourceLocation("chaospersists", "textures/entity/boyfriend13.png");
    private static final ResourceLocation DryTexture14 = new ResourceLocation("chaospersists", "textures/entity/boyfriend14.png");
    private static final ResourceLocation DryTexture15 = new ResourceLocation("chaospersists", "textures/entity/boyfriend15.png");
    private static final ResourceLocation DryTexture16 = new ResourceLocation("chaospersists", "textures/entity/boyfriend16.png");
    private static final ResourceLocation DryTexture17 = new ResourceLocation("chaospersists", "textures/entity/boyfriend17.png");
    private static final ResourceLocation DryTexture18 = new ResourceLocation("chaospersists", "textures/entity/boyfriend18.png");
    private static final ResourceLocation DryTexture19 = new ResourceLocation("chaospersists", "textures/entity/boyfriend19.png");
    private static final ResourceLocation DryTexture20 = new ResourceLocation("chaospersists", "textures/entity/boyfriend20.png");
    private static final ResourceLocation DryTexture21 = new ResourceLocation("chaospersists", "textures/entity/boyfriend21.png");
    private static final ResourceLocation DryTexture22 = new ResourceLocation("chaospersists", "textures/entity/boyfriend22.png");
    private static final ResourceLocation DryTexture23 = new ResourceLocation("chaospersists", "textures/entity/boyfriend23.png");
    private static final ResourceLocation DryTexture24 = new ResourceLocation("chaospersists", "textures/entity/boyfriend24.png");
    private static final ResourceLocation DryTexture25 = new ResourceLocation("chaospersists", "textures/entity/boyfriend25.png");
    private static final ResourceLocation DryTexture26 = new ResourceLocation("chaospersists", "textures/entity/boyfriend26.png");
    private static final ResourceLocation DryTexture27 = new ResourceLocation("chaospersists", "textures/entity/boyfriend27.png");
    private static final ResourceLocation WetTexture0 = new ResourceLocation("chaospersists", "textures/entity/swimshorts0.png");
    private static final ResourceLocation WetTexture1 = new ResourceLocation("chaospersists", "textures/entity/swimshorts1.png");
    private static final ResourceLocation WetTexture2 = new ResourceLocation("chaospersists", "textures/entity/swimshorts2.png");
    private static final ResourceLocation WetTexture3 = new ResourceLocation("chaospersists", "textures/entity/swimshorts3.png");
    private static final ResourceLocation WetTexture4 = new ResourceLocation("chaospersists", "textures/entity/swimshorts4.png");
    private static final ResourceLocation WetTexture5 = new ResourceLocation("chaospersists", "textures/entity/swimshorts5.png");
    private static final ResourceLocation WetTexture6 = new ResourceLocation("chaospersists", "textures/entity/swimshorts6.png");
    private static final ResourceLocation WetTexture7 = new ResourceLocation("chaospersists", "textures/entity/swimshorts7.png");
    private static final ResourceLocation WetTexture8 = new ResourceLocation("chaospersists", "textures/entity/swimshorts8.png");
    private static final ResourceLocation WetTexture9 = new ResourceLocation("chaospersists", "textures/entity/swimshorts9.png");
    private static final ResourceLocation WetTexture10 = new ResourceLocation("chaospersists", "textures/entity/swimshorts10.png");
    private static final ResourceLocation WetTexture11 = new ResourceLocation("chaospersists", "textures/entity/swimshorts11.png");
    private static final ResourceLocation WetTexture12 = new ResourceLocation("chaospersists", "textures/entity/swimshorts12.png");
    private static final ResourceLocation WetTexture13 = new ResourceLocation("chaospersists", "textures/entity/swimshorts13.png");
    private static final ResourceLocation WetTexture14 = new ResourceLocation("chaospersists", "textures/entity/swimshorts14.png");
    private static final ResourceLocation WetTexture15 = new ResourceLocation("chaospersists", "textures/entity/swimshorts15.png");
    private static final ResourceLocation WetTexture16 = new ResourceLocation("chaospersists", "textures/entity/swimshorts16.png");
    private static final ResourceLocation WetTexture17 = new ResourceLocation("chaospersists", "textures/entity/swimshorts17.png");
    private static final ResourceLocation PrinceTexture1 = new ResourceLocation("chaospersists", "textures/entity/frogprince.png");
    private static final ResourceLocation PrinceTexture2 = new ResourceLocation("chaospersists", "textures/entity/frogprince2.png");

    public Boyfriend(World par1World) {
        super(par1World);
        this.which_guy = this.rand.nextInt(28);
        this.voice = this.rand.nextInt(10);
        this.which_wet_guy = this.rand.nextInt(18);
        this.setSize(0.5f, 1.6f);
        this.isImmuneToFire = true;
                        this.setSitting(false);
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIFollowOwner((EntityTameable)this, 1.4f, 12.0f, 1.5f));
        this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.25, Items.COOKED_BEEF, false));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackRanged((IRangedAttackMob)this, 1.25, 20, 10.0f));
        this.tasks.addTask(5, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(6, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.5));
        this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, (EntityAIBase)new MyEntityAIWander((EntityCreature)this, 0.75f));
        this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.tasks.addTask(10, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
        this.tasks.addTask(11, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
        if (ChaosPersists.PlayNicely == 0) {
            this.targetTasks.addTask(2, (EntityAIBase)new MyEntityAINearestAttackableTarget((EntityLiving)this, EntityCreeper.class, 20.0f, 0, true, true, com.google.common.base.Predicates.alwaysTrue()));
        }
        if (ChaosPersists.PlayNicely == 0) {
            this.targetTasks.addTask(3, (EntityAIBase)new MyEntityAINearestAttackableTarget((EntityLiving)this, EntityLiving.class, 15.0f, 0, true, true, com.google.common.base.Predicates.alwaysTrue()));
        }
        if (ChaosPersists.PlayNicely == 0) {
            this.targetTasks.addTask(4, (EntityAIBase)new MyEntityAIJealousy((EntityTameable)this, Boyfriend.class, 6.0f, 5, true));
        }
        if (ChaosPersists.PlayNicely == 0) {
            this.targetTasks.addTask(5, (EntityAIBase)new MyEntityAIJealousy((EntityTameable)this, Boyfriend.class, 3.0f, 15, true));
        }
        this.experienceValue = 0;
    }

    protected void entityInit() {
        super.entityInit();
        this.which_guy = this.rand.nextInt(28);
        this.getDataManager().register(WHICH_GUY, this.which_guy);
        this.wet_count = 0;
        this.which_wet_guy = this.rand.nextInt(18);
        this.getDataManager().register(WHICH_WET_GUY, this.which_wet_guy);
        this.voice = this.rand.nextInt(10);
        this.getDataManager().register(VOICE, this.voice);
        this.getDataManager().register(VOICE_ENABLE, this.voice_enable);
        this.getDataManager().register(IS_PRINCE, this.is_prince);
        this.auto_heal = 200;
        this.force_sync = 50;
        this.fight_sound_ticker = 0;
        this.taunt_sound_ticker = 0;
        this.had_target = 0;
        this.setSitting(false);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0);
    }

    public int getTotalArmorValue() {
        int i = 0;
        for (ItemStack itemstack : this.getEquipmentAndArmor()) {
            if (itemstack == null || !(itemstack.getItem() instanceof ItemArmor)) continue;
            int l = ((ItemArmor)itemstack.getItem()).damageReduceAmount;
            i += l;
        }
        if (i < 8) {
            i = 8;
        }
        if (i > 23) {
            i = 23;
        }
        return i;
    }

    public void onUpdate() {
        EntityLivingBase e;
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
        this.passenger = 0;
        if (this.isTamed() && !this.isSitting() && (e = this.getOwner()) != null && e instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)e;
            Entity r = e.getRidingEntity();
            if (r != null && r instanceof Elevator) {
                float f = -0.45f;
                this.setPosition(r.posX - (double)f * Math.sin(Math.toRadians(r.rotationYaw)), r.posY, r.posZ + (double)f * Math.cos(Math.toRadians(r.rotationYaw)));
                this.rotationYaw = r.rotationYaw;
                this.rotationPitch = r.rotationPitch;
                this.limbSwingAmount = 0.0f;
                this.limbSwing = 0.0f;
                this.fallDistance = 0.0f;
                this.passenger = 1;
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("GirlType", this.getTameSkin());
        par1NBTTagCompound.setInteger("WetGirlType", this.getWetTameSkin());
        par1NBTTagCompound.setInteger("GirlVoice", this.getDataManager().get(VOICE).intValue());
        par1NBTTagCompound.setInteger("GirlVoiceEnable", this.getDataManager().get(VOICE_ENABLE).intValue());
        par1NBTTagCompound.setInteger("IsPrince", this.getDataManager().get(IS_PRINCE).intValue());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.which_guy = par1NBTTagCompound.getInteger("GirlType");
        this.setTameSkin(this.which_guy);
        this.which_wet_guy = par1NBTTagCompound.getInteger("WetGirlType");
        this.setWetTameSkin(this.which_wet_guy);
        this.voice = par1NBTTagCompound.getInteger("GirlVoice");
        this.getDataManager().set(VOICE, this.voice);
        this.voice_enable = par1NBTTagCompound.getInteger("GirlVoiceEnable");
        this.getDataManager().set(VOICE_ENABLE, this.voice_enable);
        this.is_prince = par1NBTTagCompound.getInteger("IsPrince");
        this.getDataManager().set(IS_PRINCE, this.is_prince);
    }

    private int attackTime = 0;

    protected void updateAITick() {
        ItemStack stack = this.getHeldItemMainhand();
        EntityLivingBase victim = this.getAttackTarget();
        if (ChaosPersists.PlayNicely != 0) {
            victim = null;
        }
        if (this.world.rand.nextInt(100) == 1) {
            this.setRevengeTarget(null);
        }
        if (stack != null && !this.isSitting()) {
            if (victim != null) {
                if (victim instanceof EntityLivingBase && this.getHeldItem(net.minecraft.util.EnumHand.MAIN_HAND) != null) {
                    if (this.getDistance((Entity)victim) < 4.0f || stack.getItem() == ChaosPersists.MyBertha && this.getDistance((Entity)victim) < 10.0f) {
                        --this.attackTime;
                        if (this.attackTime <= 0) {
                            this.attackTime = 25;
                            this.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
                            this.attackTargetEntityWithCurrentItem((Entity)victim);
                            --this.fight_sound_ticker;
                            if (this.fight_sound_ticker <= 0) {
                                if (!this.world.isRemote && this.voice_enable != 0) {
                                    this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("chaospersists", "b_fight")), 0.5f, this.getSoundPitch());
                                }
                                this.fight_sound_ticker = 3;
                            }
                            this.had_target = 1;
                        }
                    } else if (this.getDistance((Entity)victim) < 7.0f && stack.getItem() != ChaosPersists.MyUltimateBow) {
                        --this.taunt_sound_ticker;
                        if (this.taunt_sound_ticker <= 0) {
                            if (!this.world.isRemote && this.voice_enable != 0) {
                                this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("chaospersists", "b_taunt")), 0.5f, this.getSoundPitch());
                            }
                            this.taunt_sound_ticker = 300;
                        }
                        this.getNavigator().tryMoveToEntityLiving((Entity)victim, 1.25);
                    }
                }
            } else {
                this.fight_sound_ticker = 0;
                this.attackTime = 0;
                if (this.had_target != 0) {
                    this.had_target = 0;
                    if (!this.world.isRemote && this.voice_enable != 0) {
                        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("chaospersists", "b_woohoo")), 0.4f, this.getSoundPitch());
                    }
                }
            }
        }
    }

    public void setPrince(int par1) {
        this.is_prince = par1;
    }

    public ResourceLocation getTexture() {
        if (this.wet_count <= 0) {
            int txture = this.getTameSkin();
            if (this.is_prince == 1) {
                return PrinceTexture1;
            }
            if (this.is_prince == 2) {
                return PrinceTexture2;
            }
            if (txture == 0) {
                return DryTexture0;
            }
            if (txture == 1) {
                return DryTexture1;
            }
            if (txture == 2) {
                return DryTexture2;
            }
            if (txture == 3) {
                return DryTexture3;
            }
            if (txture == 4) {
                return DryTexture4;
            }
            if (txture == 5) {
                return DryTexture5;
            }
            if (txture == 6) {
                return DryTexture6;
            }
            if (txture == 7) {
                return DryTexture7;
            }
            if (txture == 8) {
                return DryTexture8;
            }
            if (txture == 9) {
                return DryTexture9;
            }
            if (txture == 10) {
                return DryTexture10;
            }
            if (txture == 11) {
                return DryTexture11;
            }
            if (txture == 12) {
                return DryTexture12;
            }
            if (txture == 13) {
                return DryTexture13;
            }
            if (txture == 14) {
                return DryTexture14;
            }
            if (txture == 15) {
                return DryTexture15;
            }
            if (txture == 16) {
                return DryTexture16;
            }
            if (txture == 17) {
                return DryTexture17;
            }
            if (txture == 18) {
                return DryTexture18;
            }
            if (txture == 19) {
                return DryTexture19;
            }
            if (txture == 20) {
                return DryTexture20;
            }
            if (txture == 21) {
                return DryTexture21;
            }
            if (txture == 22) {
                return DryTexture22;
            }
            if (txture == 23) {
                return DryTexture23;
            }
            if (txture == 24) {
                return DryTexture24;
            }
            if (txture == 25) {
                return DryTexture25;
            }
            if (txture == 26) {
                return DryTexture26;
            }
            if (txture == 27) {
                return DryTexture27;
            }
        } else {
            int temp = this.getWetTameSkin();
            if (temp == 0) {
                return WetTexture0;
            }
            if (temp == 1) {
                return WetTexture1;
            }
            if (temp == 2) {
                return WetTexture2;
            }
            if (temp == 3) {
                return WetTexture3;
            }
            if (temp == 4) {
                return WetTexture4;
            }
            if (temp == 5) {
                return WetTexture5;
            }
            if (temp == 6) {
                return WetTexture6;
            }
            if (temp == 7) {
                return WetTexture7;
            }
            if (temp == 8) {
                return WetTexture8;
            }
            if (temp == 9) {
                return WetTexture9;
            }
            if (temp == 10) {
                return WetTexture10;
            }
            if (temp == 11) {
                return WetTexture11;
            }
            if (temp == 12) {
                return WetTexture12;
            }
            if (temp == 13) {
                return WetTexture13;
            }
            if (temp == 14) {
                return WetTexture14;
            }
            if (temp == 15) {
                return WetTexture15;
            }
            if (temp == 16) {
                return WetTexture16;
            }
            if (temp == 17) {
                return WetTexture17;
            }
        }
        return null;
    }

    public int getTameSkin() {
        return this.getDataManager().get(WHICH_GUY).intValue();
    }

    public int getVoice() {
        return this.getDataManager().get(VOICE).intValue();
    }

    public void setTameSkin(int par1) {
        this.getDataManager().set(WHICH_GUY, par1);
        this.which_guy = par1;
    }

    public int getWetTameSkin() {
        return this.getDataManager().get(WHICH_WET_GUY).intValue();
    }

    public void setWetTameSkin(int par1) {
        this.getDataManager().set(WHICH_WET_GUY, par1);
        this.which_wet_guy = par1;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return true;
    }

    protected void fall(float par1) {
        float i = (float)MathHelper.ceil((double)(par1 - 3.0f));
        if (i > 0.0f) {
            if (i > 3.0f) {
                this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.big_fall")), 1.0f, 1.0f);
                i = 3.0f;
            } else {
                this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.generic.small_fall")), 1.0f, 1.0f);
            }
            this.attackEntityFrom(DamageSource.FALL, i);
        }
    }

    public int mygetMaxHealth() {
        return 80;
    }

    public void onLivingUpdate() {
        this.updateArmSwingProgress();
        super.onLivingUpdate();
        if (this.isInWater() || this.isInLava()) {
            this.wet_count = 500;
        } else if (this.wet_count > 0) {
            --this.wet_count;
        }
        --this.auto_heal;
        if (this.auto_heal <= 0) {
            if (this.mygetMaxHealth() > this.getBoyfriendHealth()) {
                this.heal(1.0f);
            }
            this.auto_heal = 150;
        }
        --this.force_sync;
        if (this.force_sync <= 0) {
            this.force_sync = 20;
            if (!this.world.isRemote) {
                this.getDataManager().set(VOICE, this.voice);
                this.getDataManager().set(VOICE_ENABLE, this.voice_enable);
                this.getDataManager().set(IS_PRINCE, this.is_prince);
                this.setSitting(this.isSitting());
            } else {
                this.voice = this.getVoice();
                this.voice_enable = this.getDataManager().get(VOICE_ENABLE).intValue();
                this.is_prince = this.getDataManager().get(IS_PRINCE).intValue();
            }
        }
    }

    public int getBoyfriendHealth() {
        return (int)this.getHealth();
    }

    public boolean processInteract(EntityPlayer par1EntityPlayer, EnumHand hand) {
        ItemStack var2 = par1EntityPlayer.getHeldItem(hand);
        if (var2.isEmpty()) {
            var2 = null;
        }
        if (var2 != null && (var2.getItem() == Items.COOKED_BEEF || var2.getItem() == ChaosPersists.MyPeacock) && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            if (!this.isTamed()) {
                if (!this.world.isRemote) {
                    if (this.world.rand.nextInt(3) == 0) {
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
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DEADBUSH) && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.isOwner(par1EntityPlayer)) {
            if (!this.world.isRemote) {
                this.setTamed(false);
                this.setOwnerId((java.util.UUID)null);
                this.playTameEffect(false);
                this.world.setEntityState((Entity)this, (byte)6);
            }
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == ChaosPersists.MyRuby && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.isOwner(par1EntityPlayer)) {
            if (!this.world.isRemote) {
                this.voice_enable = 0;
                this.getDataManager().set(VOICE_ENABLE, this.voice_enable);
                this.playTameEffect(true);
                this.world.setEntityState((Entity)this, (byte)7);
            }
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == ChaosPersists.MyAmethyst && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.isOwner(par1EntityPlayer)) {
            if (!this.world.isRemote) {
                this.voice_enable = 1;
                this.getDataManager().set(VOICE_ENABLE, this.voice_enable);
                this.playTameEffect(true);
                this.world.setEntityState((Entity)this, (byte)7);
            }
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && (var2.getItem() == Items.LEATHER || var2.getItem() == ChaosPersists.MyPeacockFeather) && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.isOwner(par1EntityPlayer)) {
            if (!this.world.isRemote) {
                if (this.wet_count > 0 || this.isInWater() || this.isInLava()) {
                    ++this.which_wet_guy;
                    if (this.which_wet_guy > 17) {
                        this.which_wet_guy = 0;
                    }
                    this.setWetTameSkin(this.which_wet_guy);
                    this.world.setEntityState((Entity)this, (byte)7);
                    if (this.isInWater() || this.isInLava()) {
                        this.wet_count = 500;
                    }
                } else {
                    ++this.which_guy;
                    if (this.which_guy > 27) {
                        this.which_guy = 0;
                    }
                    this.setTameSkin(this.which_guy);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
            }
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && this.isOwner(par1EntityPlayer) && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            if (var2.getItem() instanceof ItemFood) {
                if (!this.world.isRemote) {
                    ItemFood var3 = (ItemFood)var2.getItem();
                    if ((float)this.mygetMaxHealth() > this.getHealth()) {
                        this.heal((float)(var3.getHealAmount(var2) * 5));
                    }
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                if (!par1EntityPlayer.capabilities.isCreativeMode) {
                    var2.shrink(1);
                    if (var2.getCount() <= 0) {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                    }
                }
            } else if (var2.getItem() instanceof ItemArmor) {
                if (!this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                ItemArmor armorItem = (ItemArmor) var2.getItem();
                EntityEquipmentSlot slot = armorItem.getEquipmentSlot();
                ItemStack oldArmor = this.getItemStackFromSlot(slot).copy();
                if (par1EntityPlayer.capabilities.isCreativeMode) {
                    ItemStack equipCopy = var2.copy();
                    equipCopy.setCount(1);
                    this.setItemStackToSlot(slot, equipCopy);
                } else {
                    ItemStack equipOne = var2.copy();
                    equipOne.setCount(1);
                    this.setItemStackToSlot(slot, equipOne);
                    var2.shrink(1);
                    if (var2.isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
                    }
                }
                if (!oldArmor.isEmpty()) {
                    if (par1EntityPlayer.getHeldItem(hand).isEmpty()) {
                        par1EntityPlayer.setHeldItem(hand, oldArmor);
                    } else if (!par1EntityPlayer.inventory.addItemStackToInventory(oldArmor)) {
                        par1EntityPlayer.dropItem(oldArmor, false);
                    }
                }
            } else {
                if (!this.world.isRemote) {
                    this.playTameEffect(true);
                    this.world.setEntityState((Entity)this, (byte)7);
                }
                ItemStack var3 = this.getCurrentEquippedItem();
                this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, var2);
                if (var2.getItem() == Items.DIAMOND) {
                    this.setSitting(true);
                } else {
                    this.setSitting(false);
                }
                if (var3 != null && !var3.isEmpty()) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, var3);
                } else {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Item.getItemFromBlock((Block)Blocks.DIAMOND_BLOCK) && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            this.setSitting(false);
            this.setTamed(true);
            this.setOwnerId(par1EntityPlayer.getUniqueID());
            this.playTameEffect(true);
            this.world.setEntityState((Entity)this, (byte)7);
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 != null && var2.getItem() == Items.NAME_TAG && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.isOwner(par1EntityPlayer)) {
            this.setCustomNameTag(var2.getDisplayName());
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.getCount() <= 0) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.isTamed() && var2 == null && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0 && this.isOwner(par1EntityPlayer)) {
            EntityEquipmentSlot slotToStrip = null;
            ItemStack toGive = ItemStack.EMPTY;
            for (EntityEquipmentSlot s : BOYFRIEND_STRIP_SLOTS) {
                ItemStack equipped = this.getItemStackFromSlot(s);
                if (!equipped.isEmpty()) {
                    toGive = equipped;
                    slotToStrip = s;
                    break;
                }
            }
            if (slotToStrip != null) {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, toGive);
                this.setItemStackToSlot(slotToStrip, ItemStack.EMPTY);
                this.setSitting(false);
                if (!this.world.isRemote) {
                    this.world.setEntityState((Entity)this, (byte)6);
                }
            } else if (!this.world.isRemote) {
                this.setSitting(false);
                String healthMessage = String.format("I have %d health. Thank you for asking!", this.getBoyfriendHealth());
                par1EntityPlayer.sendMessage(new TextComponentString(healthMessage));
            }
            return true;
        }
        return super.processInteract(par1EntityPlayer, hand);
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        if (par1ItemStack != null && (par1ItemStack.getItem() == Items.COOKED_BEEF || par1ItemStack.getItem() == ChaosPersists.MyPeacock)) {
            return true;
        }
        return false;
    }

    protected boolean canDespawn() {
        return false;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        if (this.isSitting() || this.voice_enable == 0) {
            return null;
        }
        if (ChaosPersists.bro_mode != 0 && this.world.rand.nextInt(2) == 1) {
            return null;
        }
        if (this.world.rand.nextInt(11) == 1) {
            EntityLivingBase victim = this.getAttackTarget();
            if (victim != null) {
                return null;
            }
            if (this.isInWater() || this.isInLava()) {
                return com.astryxion.chaospersists.core.ChaosSounds.B_WATER;
            }
            if (this.world.rand.nextInt(4) != 0) {
                if (this.posY < 60.0) {
                    return null;
                }
                if (this.world.isThundering()) {
                    return com.astryxion.chaospersists.core.ChaosSounds.B_THUNDER;
                }
                if (this.world.isRaining()) {
                    return com.astryxion.chaospersists.core.ChaosSounds.B_RAIN;
                }
                if (!this.world.isDaytime() && this.world.canSeeSky(new net.minecraft.util.math.BlockPos(this))) {
                    if (this.world.rand.nextInt(3) == 0) {
                        return com.astryxion.chaospersists.core.ChaosSounds.B_DARK;
                    }
                    return null;
                }
            }
            if (this.isTamed()) {
                if ((float)this.mygetMaxHealth() > this.getHealth()) {
                    return com.astryxion.chaospersists.core.ChaosSounds.B_HURT;
                }
                if (ChaosPersists.bro_mode != 0) {
                    return com.astryxion.chaospersists.core.ChaosSounds.BB_HAPPY;
                }
                return com.astryxion.chaospersists.core.ChaosSounds.B_HAPPY;
            }
            return null;
        }
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        if (this.voice_enable == 0) {
            return null;
        }
        if (ChaosPersists.bro_mode != 0 && this.world.rand.nextInt(2) == 1) {
            return null;
        }
        return com.astryxion.chaospersists.core.ChaosSounds.B_OW;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        if (ChaosPersists.bro_mode != 0) {
            return null;
        }
        return this.isTamed() ? com.astryxion.chaospersists.core.ChaosSounds.B_DEATH_BOYFRIEND : com.astryxion.chaospersists.core.ChaosSounds.B_DEATH_SINGLE;
    }

    protected float getSoundVolume() {
        return 0.3f;
    }

    protected Item getDropItem() {
        return Item.getItemFromBlock((Block)Blocks.RED_FLOWER);
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var3 = 0;
        if (this.isTamed()) {
            var3 = this.rand.nextInt(5) + 2;
            this.entityDropItem(new ItemStack(Item.getItemFromBlock((Block)Blocks.RED_FLOWER), var3), 0.0f);
        }
        Item v6 = ChaosPersists.MyItemGameController;
        var3 = this.world.rand.nextInt(26) + 10;
        this.entityDropItem(new ItemStack(v6, var3), 0.0f);
        if (this.isTamed()) {
            ItemStack var5 = this.getCurrentEquippedItem();
            if (var5 != null && var5.getCount() > 0) {
                this.entityDropItem(var5, 0.0f);
            }
            if ((var5 = this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.OFFHAND)) != null && var5.getCount() > 0) {
                this.entityDropItem(var5, 0.0f);
            }
            if ((var5 = this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET)) != null && var5.getCount() > 0) {
                this.entityDropItem(var5, 0.0f);
            }
            if ((var5 = this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.LEGS)) != null && var5.getCount() > 0) {
                this.entityDropItem(var5, 0.0f);
            }
            if ((var5 = this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.CHEST)) != null && var5.getCount() > 0) {
                this.entityDropItem(var5, 0.0f);
            }
        }
    }

    public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLiving) {
        ItemStack it = null;
        if (this.isSwingInProgress) {
            return;
        }
        it = this.getCurrentEquippedItem();
        if (it != null && it.getItem() == ChaosPersists.MyUltimateBow) {
            int var10;
            UltimateArrow var8 = new UltimateArrow(this.world, (EntityLiving)this, par1EntityLiving, 2.0f, 10.0f);
            if (this.world.rand.nextInt(4) == 1) {
                var8.setIsCritical(true);
            }
            if ((var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(19), (ItemStack)it)) > 0) {
                var8.setKnockbackStrength(var10);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(50), (ItemStack)it) > 0) {
                var8.setFire(100);
            }
            it.damageItem(1, (EntityLivingBase)this);
            this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.skeleton.shoot")), 1.0f, 1.0f / (this.world.rand.nextFloat() * 0.4f + 1.2f) + 0.5f);
            var8.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
            this.world.spawnEntity((Entity)var8);
        } else {
            Shoes.shootTowardTarget(this, par1EntityLiving, 6);
        }
        this.swingArm(net.minecraft.util.EnumHand.MAIN_HAND);
    }

    public ItemStack getCurrentEquippedItem() {
        return this.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.MAINHAND);
    }

    public void attackTargetEntityWithCurrentItem(Entity par1Entity) {
        ItemStack stack = this.getCurrentEquippedItem();
        if (stack != null) {
            float var2 = 0.0f;
            if (this.isPotionActive(net.minecraft.init.MobEffects.STRENGTH)) {
                var2 += (float)(3 << this.getActivePotionEffect(net.minecraft.init.MobEffects.STRENGTH).getAmplifier());
            }
            if (this.isPotionActive(net.minecraft.init.MobEffects.WEAKNESS)) {
                var2 -= (float)(2 << this.getActivePotionEffect(net.minecraft.init.MobEffects.WEAKNESS).getAmplifier());
            }
            int var3 = 0;
            float var4 = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            if (par1Entity instanceof EntityLiving) {
                var4 += EnchantmentHelper.getModifierForCreature(stack, ((EntityLiving)par1Entity).getCreatureAttribute());
                var3 += EnchantmentHelper.getKnockbackModifier((EntityLivingBase)this);
            }
            if (this.isSprinting()) {
                ++var3;
            }
            if (var2 > 0.0f || var4 > 0.0f) {
                int var8;
                boolean var6;
                boolean var5;
                boolean bl = var5 = this.fallDistance > 0.0f && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isInLava() && !this.isPotionActive(net.minecraft.init.MobEffects.BLINDNESS) && this.getRidingEntity() == null && par1Entity instanceof EntityLiving;
                if (var5) {
                    var2 += (float)this.world.rand.nextInt((int)var2 / 2 + 2);
                }
                if ((var6 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2 += var4)) && var3 > 0) {
                    par1Entity.addVelocity((double)((- MathHelper.sin((float)(this.rotationYaw * 3.1415927f / 180.0f))) * (float)var3 * 0.5f), 0.1, (double)(MathHelper.cos((float)(this.rotationYaw * 3.1415927f / 180.0f)) * (float)var3 * 0.5f));
                    this.motionX *= 0.6;
                    this.motionZ *= 0.6;
                    this.setSprinting(false);
                }
                ItemStack var7 = this.getCurrentEquippedItem();
                if (par1Entity instanceof EntityLiving && (var8 = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(20), (ItemStack)var7)) > 0 && var6) {
                    par1Entity.setFire(var8 * 4);
                }
            }
        }
    }

    protected float getSoundPitch() {
        return (float)(this.voice - 5) * 0.02f + 1.0f;
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    public void attackEntityWithRangedAttack(EntityLivingBase entityliving, float f) {
        this.attackEntityWithRangedAttack(entityliving);
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        float p2 = par2;
        if (p2 > 10.0f) {
            p2 = 10.0f;
        }
        if (!par1DamageSource.getDamageType().equals("cactus")) {
            int prevHurtTime = this.hurtTime;
            ret = super.attackEntityFrom(par1DamageSource, p2);
            if (!this.world.isRemote && ret && this.hurtTime > prevHurtTime) {
                net.minecraft.entity.Entity src = par1DamageSource.getTrueSource();
                if (src instanceof EntityLivingBase && src != this) {
                    Shoes.shootTowardTarget(this, (EntityLivingBase) src, 6);
                }
            }
        }
        return ret;
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
                    if (s == null || !s.equals("Boyfriend")) continue;
                    return true;
                }
            }
        }
        return super.getCanSpawnHere();
    }
}

