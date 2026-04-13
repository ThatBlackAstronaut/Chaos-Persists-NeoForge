/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EasterBunny
 *  com.astryxion.chaospersists.MyEntityAIWanderALot
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIAvoidEntity
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMate
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.util.MyEntityAIWanderALot;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class EasterBunny
extends EntityAnimal {
    private float moveSpeed = 0.45f;

    public EasterBunny(World par1World) {
        super(par1World);
        this.setSize(0.5f, 0.75f);
        this.moveSpeed = 0.45f;
                this.experienceValue = 5;
                this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMate((EntityAnimal)this, 1.0));
        this.tasks.addTask(2, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityMob.class, 8.0f, 1.0, 1.399999976158142));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityPlayer.class, 8.0f, 1.0, 1.399999976158142));
        this.tasks.addTask(4, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.5));
        this.tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0f));
        this.tasks.addTask(6, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0);
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        super.onUpdate();
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        if (!this.world.isDaytime()) {
            return false;
        }
        EasterBunny target = null;
        target = (EasterBunny)this.world.findNearestEntityWithinAABB(EasterBunny.class, this.getEntityBoundingBox().expand(32.0, 8.0, 32.0), (Entity)this);
        if (target != null) {
            return false;
        }
        return true;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public boolean canBreatheUnderwater() {
        return false;
    }

    public int mygetMaxHealth() {
        return 10;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return com.astryxion.chaospersists.core.ChaosSounds.DUCK_HURT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.DUCK_HURT;
    }

    protected float getSoundVolume() {
        return 0.4f;
    }

    protected Item getDropItem() {
        return Items.CHICKEN;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var3 = 0;
        var3 = this.rand.nextInt(3);
        for (int var4 = 0; var4 < (var3 += 2); ++var4) {
            this.dropItem(Items.CHICKEN, 1);
        }
    }

    protected void updateAITasks() {
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        super.updateAITasks();
        if (this.world.rand.nextInt(600) == 1) {
            this.LayAnEgg(1 + this.world.rand.nextInt(3));
        }
    }

    private ItemStack LayAnEgg(int par1) {
        EntityItem var3 = null;
        int i = 0;
        Item index = null;
        int val = 0;
        ItemStack is = null;
        i = this.world.rand.nextInt(115);
        switch (i) {
            case 5: {
                index = ChaosPersists.GirlfriendEgg;
                break;
            }
            case 6: {
                index = ChaosPersists.RedCowEgg;
                break;
            }
            case 7: {
                index = ChaosPersists.GoldCowEgg;
                break;
            }
            case 8: {
                index = ChaosPersists.EnchantedCowEgg;
                break;
            }
            case 9: {
                index = ChaosPersists.MOTHRAEgg;
                break;
            }
            case 10: {
                index = ChaosPersists.AloEgg;
                break;
            }
            case 11: {
                index = ChaosPersists.CryoEgg;
                break;
            }
            case 12: {
                index = ChaosPersists.CamaEgg;
                break;
            }
            case 13: {
                index = ChaosPersists.VeloEgg;
                break;
            }
            case 14: {
                index = ChaosPersists.HydroEgg;
                break;
            }
            case 15: {
                index = ChaosPersists.BasilEgg;
                break;
            }
            case 16: {
                index = ChaosPersists.DragonflyEgg;
                break;
            }
            case 17: {
                index = ChaosPersists.EmperorScorpionEgg;
                break;
            }
            case 18: {
                index = ChaosPersists.ScorpionEgg;
                break;
            }
            case 19: {
                index = ChaosPersists.CaveFisherEgg;
                break;
            }
            case 20: {
                index = ChaosPersists.SpyroEgg;
                break;
            }
            case 21: {
                index = ChaosPersists.BaryonyxEgg;
                break;
            }
            case 22: {
                index = ChaosPersists.GammaMetroidEgg;
                break;
            }
            case 23: {
                index = ChaosPersists.CockateilEgg;
                break;
            }
            case 24: {
                index = ChaosPersists.KyuubiEgg;
                break;
            }
            case 25: {
                index = ChaosPersists.AlienEgg;
                break;
            }
            case 26: {
                index = ChaosPersists.AttackSquidEgg;
                break;
            }
            case 27: {
                index = ChaosPersists.WaterDragonEgg;
                break;
            }
            case 28: {
                index = ChaosPersists.CephadromeEgg;
                break;
            }
            case 29: {
                index = ChaosPersists.DragonEgg;
                break;
            }
            case 30: {
                index = ChaosPersists.KrakenEgg;
                break;
            }
            case 31: {
                index = ChaosPersists.LizardEgg;
                break;
            }
            case 32: {
                index = ChaosPersists.BeeEgg;
                break;
            }
            case 33: {
                index = ChaosPersists.TrooperBugEgg;
                break;
            }
            case 34: {
                index = ChaosPersists.SpitBugEgg;
                break;
            }
            case 35: {
                index = ChaosPersists.StinkBugEgg;
                break;
            }
            case 36: {
                index = ChaosPersists.OstrichEgg;
                break;
            }
            case 37: {
                index = ChaosPersists.GazelleEgg;
                break;
            }
            case 38: {
                index = ChaosPersists.ChipmunkEgg;
                break;
            }
            case 39: {
                index = ChaosPersists.CreepingHorrorEgg;
                break;
            }
            case 40: {
                index = ChaosPersists.TerribleTerrorEgg;
                break;
            }
            case 41: {
                index = ChaosPersists.CliffRacerEgg;
                break;
            }
            case 42: {
                index = ChaosPersists.TriffidEgg;
                break;
            }
            case 43: {
                index = ChaosPersists.PitchBlackEgg;
                break;
            }
            case 44: {
                index = ChaosPersists.LurkingTerrorEgg;
                break;
            }
            case 45: {
                index = ChaosPersists.GodzillaEgg;
                break;
            }
            case 46: {
                index = ChaosPersists.SmallWormEgg;
                break;
            }
            case 47: {
                index = ChaosPersists.MediumWormEgg;
                break;
            }
            case 48: {
                index = ChaosPersists.LargeWormEgg;
                break;
            }
            case 49: {
                index = ChaosPersists.CassowaryEgg;
                break;
            }
            case 50: {
                index = ChaosPersists.CloudSharkEgg;
                break;
            }
            case 51: {
                index = ChaosPersists.GoldFishEgg;
                break;
            }
            case 52: {
                index = ChaosPersists.LeafMonsterEgg;
                break;
            }
            case 53: {
                index = ChaosPersists.TshirtEgg;
                break;
            }
            case 54: {
                index = ChaosPersists.EnderKnightEgg;
                break;
            }
            case 55: {
                index = ChaosPersists.EnderReaperEgg;
                break;
            }
            case 56: {
                index = ChaosPersists.BeaverEgg;
                break;
            }
            case 57: {
                index = ChaosPersists.RotatorEgg;
                break;
            }
            case 58: {
                index = ChaosPersists.VortexEgg;
                break;
            }
            case 59: {
                index = ChaosPersists.PeacockEgg;
                break;
            }
            case 60: {
                index = ChaosPersists.FairyEgg;
                break;
            }
            case 61: {
                index = ChaosPersists.DungeonBeastEgg;
                break;
            }
            case 62: {
                index = ChaosPersists.RatEgg;
                break;
            }
            case 63: {
                index = ChaosPersists.FlounderEgg;
                break;
            }
            case 64: {
                index = ChaosPersists.WhaleEgg;
                break;
            }
            case 65: {
                index = ChaosPersists.IrukandjiEgg;
                break;
            }
            case 66: {
                index = ChaosPersists.SkateEgg;
                break;
            }
            case 67: {
                index = ChaosPersists.UrchinEgg;
                break;
            }
            case 68: {
                index = ChaosPersists.Robot1Egg;
                break;
            }
            case 69: {
                index = ChaosPersists.Robot2Egg;
                break;
            }
            case 70: {
                index = ChaosPersists.Robot3Egg;
                break;
            }
            case 71: {
                index = ChaosPersists.Robot4Egg;
                break;
            }
            case 72: {
                index = ChaosPersists.GhostEgg;
                break;
            }
            case 73: {
                index = ChaosPersists.GhostSkellyEgg;
                break;
            }
            case 74: {
                index = ChaosPersists.BrownAntEgg;
                break;
            }
            case 75: {
                index = ChaosPersists.RedAntEgg;
                break;
            }
            case 76: {
                index = ChaosPersists.RainbowAntEgg;
                break;
            }
            case 77: {
                index = ChaosPersists.UnstableAntEgg;
                break;
            }
            case 78: {
                index = ChaosPersists.TermiteEgg;
                break;
            }
            case 79: {
                index = ChaosPersists.ButterflyEgg;
                break;
            }
            case 80: {
                index = ChaosPersists.MothEgg;
                break;
            }
            case 81: {
                index = ChaosPersists.MosquitoEgg;
                break;
            }
            case 82: {
                index = ChaosPersists.FireflyEgg;
                break;
            }
            case 83: {
                index = ChaosPersists.TRexEgg;
                break;
            }
            case 84: {
                index = ChaosPersists.HerculesEgg;
                break;
            }
            case 85: {
                index = ChaosPersists.MantisEgg;
                break;
            }
            case 86: {
                index = ChaosPersists.StinkyEgg;
                break;
            }
            case 87: {
                index = ChaosPersists.Robot5Egg;
                break;
            }
            case 88: {
                index = ChaosPersists.CoinEgg;
                break;
            }
            case 89: {
                index = ChaosPersists.BoyfriendEgg;
                break;
            }
            case 90: {
                index = ChaosPersists.TheKingEgg;
                break;
            }
            case 91: {
                index = ChaosPersists.ThePrinceEgg;
                break;
            }
            case 92: {
                index = ChaosPersists.EasterBunnyEgg;
                break;
            }
            case 93: {
                index = ChaosPersists.MolenoidEgg;
                break;
            }
            case 94: {
                index = ChaosPersists.SeaMonsterEgg;
                break;
            }
            case 95: {
                index = ChaosPersists.SeaViperEgg;
                break;
            }
            case 96: {
                index = ChaosPersists.CaterKillerEgg;
                break;
            }
            case 97: {
                index = ChaosPersists.LeonEgg;
                break;
            }
            case 98: {
                index = ChaosPersists.HammerheadEgg;
                break;
            }
            case 99: {
                index = ChaosPersists.RubberDuckyEgg;
                break;
            }
            case 100: {
                index = ChaosPersists.CrystalCowEgg;
                break;
            }
            case 101: {
                index = ChaosPersists.CriminalEgg;
                break;
            }
            case 102: {
                index = ChaosPersists.TheQueenEgg;
                break;
            }
            case 103: {
                index = ChaosPersists.BrutalflyEgg;
                break;
            }
            case 104: {
                index = ChaosPersists.NastysaurusEgg;
                break;
            }
            case 105: {
                index = ChaosPersists.PointysaurusEgg;
                break;
            }
            case 106: {
                index = ChaosPersists.CricketEgg;
                break;
            }
            case 107: {
                index = ChaosPersists.ThePrincessEgg;
                break;
            }
            case 108: {
                index = ChaosPersists.FrogEgg;
                break;
            }
            case 109: {
                index = ChaosPersists.JefferyEgg;
                break;
            }
            case 110: {
                index = ChaosPersists.AntRobotEgg;
                break;
            }
            case 111: {
                index = ChaosPersists.SpiderRobotEgg;
                break;
            }
            case 112: {
                index = ChaosPersists.SpiderDriverEgg;
                break;
            }
            case 113: {
                index = ChaosPersists.CrabEgg;
                break;
            }
            default: {
                index = null;
            }
        }
        if (index == null) {
            return null;
        }
        is = new ItemStack(index, par1, val);
        var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(2) - (double)ChaosPersists.ChaosRand.nextInt(2), this.posY + 1.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(2) - (double)ChaosPersists.ChaosRand.nextInt(2), is);
        if (var3 != null) {
            this.world.spawnEntity((Entity)var3);
        }
        return is;
    }

    protected boolean canDespawn() {
        if (this.isChild()) {
            this.enablePersistence();
            return false;
        }
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return true;
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.spawnBabyAnimal(entityageable);
    }

    public EasterBunny spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return new EasterBunny(this.world);
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.APPLE;
    }

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() == ChaosPersists.MyCrystalApple;
    }
}

