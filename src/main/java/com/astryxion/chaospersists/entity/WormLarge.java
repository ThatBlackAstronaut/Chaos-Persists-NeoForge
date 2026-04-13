/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.MyEntityAIWanderALot
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.WormLarge
 *  com.astryxion.chaospersists.WormMedium
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAIMoveThroughVillage
 *  net.minecraft.entity.ai.EntityAISwimming
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
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
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.util.MyEntityAIWanderALot;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.WormMedium;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/*
 * Exception performing whole class analysis ignored.
 */
public class WormLarge
extends EntityMob {
    private int wormsSpawned = 0;

    public WormLarge(World par1World) {
        super(par1World);
        this.setSize(1.55f, 2.5f);
                this.experienceValue = 2050;
        this.noClip = true;
        this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        this.tasks.addTask(1, (EntityAIBase)new EntityAIMoveThroughVillage((EntityCreature)this, 1.0, false));
        this.tasks.addTask(2, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 16, 1.0));
        this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ChaosPersists.WormLarge_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
    }

    protected boolean canDespawn() {
        return false;
    }

    protected float getSoundVolume() {
        return 0.5f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return com.astryxion.chaospersists.core.ChaosSounds.BIG_SPLAT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return com.astryxion.chaospersists.core.ChaosSounds.ALO_DEATH;
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    public int mygetMaxHealth() {
        return ChaosPersists.WormLarge_stats.health;
    }

    public int getTotalArmorValue() {
        return ChaosPersists.WormLarge_stats.defense;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public void pointAtEntity(EntityLivingBase e) {
        float f2;
        double d1 = e.posX - this.posX;
        double d2 = e.posZ - this.posZ;
        float d = (float)Math.atan2(d2, d1);
        this.rotationYaw = this.rotationYawHead = (f2 = (float)((double)d * 180.0 / 3.141592653589793) - 90.0f);
    }

    public void onLivingUpdate() {
        Block bid;
        EntityPlayer target = null;
        WormMedium worms = null;
        EntityCreature newent = null;
        super.onLivingUpdate();
        worms = (WormMedium)this.world.findNearestEntityWithinAABB(WormMedium.class, this.getEntityBoundingBox().expand(8.0, 8.0, 8.0), (Entity)this);
        if (worms == null) {
            target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(8.0, 8.0, 8.0), (Entity)this);
        }
        if (worms == null && target != null || ChaosPersists.PlayNicely != 0) {
            if (target != null) {
                this.pointAtEntity((EntityLivingBase)target);
            }
            if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock()) == Blocks.TALLGRASS) {
                bid = Blocks.AIR;
            }
            if (bid != Blocks.AIR) {
                this.motionY += 0.25;
                this.posY += 0.10000000149011612;
            } else {
                this.noClip = false;
            }
        } else {
            this.noClip = true;
            bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)(this.posY + 3.5), (int)this.posZ)).getBlock();
            if (bid == Blocks.TALLGRASS) {
                bid = Blocks.AIR;
            }
            if (bid != Blocks.AIR) {
                this.motionY += 0.10000000149011612;
                this.posY += 0.05000000074505806;
                if (bid != Blocks.GRASS && bid != Blocks.DIRT && bid != Blocks.STONE) {
                    this.setDead();
                }
            }
        }
        if (this.noClip) {
            this.motionY -= 0.01;
            this.motionX = 0.0;
            this.motionZ = 0.0;
            this.moveForward = 0.0f;
        }
        if (this.world.isRemote) {
            return;
        }
        if (this.wormsSpawned != 0) {
            return;
        }
        this.wormsSpawned = 1;
        for (int i = 0; i < 20; ++i) {
            newent = (EntityCreature)WormLarge.spawnCreature((World)this.world, (String)"Small Worm", (double)(this.posX + (double)this.world.rand.nextInt(6) - (double)this.world.rand.nextInt(6)), (double)this.posY, (double)(this.posZ + (double)this.world.rand.nextInt(6) - (double)this.world.rand.nextInt(6)));
            newent = (EntityCreature)WormLarge.spawnCreature((World)this.world, (String)"Medium Worm", (double)(this.posX + (double)this.world.rand.nextInt(5) - (double)this.world.rand.nextInt(5)), (double)this.posY, (double)(this.posZ + (double)this.world.rand.nextInt(5) - (double)this.world.rand.nextInt(5)));
        }
    }

    public void onUpdate() {
        if (this.isNoDespawnRequired()) {
            this.noClip = false;
        }
        super.onUpdate();
        this.motionY *= 0.85;
    }

    protected void updateAITasks() {
        int bid = 0;
        EntityPlayer target = null;
        WormMedium worms = null;
        if (this.isDead) {
            return;
        }
        if (!this.noClip) {
            super.updateAITasks();
        }
        if (ChaosPersists.PlayNicely != 0) {
            return;
        }
        worms = (WormMedium)this.world.findNearestEntityWithinAABB(WormMedium.class, this.getEntityBoundingBox().expand(8.0, 8.0, 8.0), (Entity)this);
        if (worms != null) {
            return;
        }
        target = (EntityPlayer)this.world.findNearestEntityWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(8.0, 6.0, 8.0), (Entity)this);
        if (target != null && target.capabilities.isCreativeMode) {
            target = null;
        }
        if (target != null) {
            this.pointAtEntity((EntityLivingBase)target);
            this.getNavigator().tryMoveToXYZ(target.posX, target.posY, target.posZ, 1.0);
            if (this.world.rand.nextInt(10) == 1 && (double)this.getDistance((Entity)target) < 3.0) {
                EntityItem var3;
                ItemStack boots;
                super.attackEntityAsMob((Entity)target);
                if (this.world.rand.nextInt(4) == 1) {
                    boots = target.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET);
                    if (boots != null && !boots.isEmpty()) {
                        target.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.FEET, ItemStack.EMPTY);
                        bid = boots.getMaxDamage() - boots.getItemDamage();
                        bid = bid > 10 ? (bid /= 10) : 1;
                        boots.damageItem(bid, (EntityLivingBase)this);
                        var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(5) - (double)ChaosPersists.ChaosRand.nextInt(5), this.posY + 3.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(5) - (double)ChaosPersists.ChaosRand.nextInt(5), boots);
                        this.world.spawnEntity((Entity)var3);
                    } else {
                        boots = target.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.LEGS);
                        if (boots != null && !boots.isEmpty()) {
                            target.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
                            bid = boots.getMaxDamage() - boots.getItemDamage();
                            bid = bid > 10 ? (bid /= 10) : 1;
                            boots.damageItem(bid, (EntityLivingBase)this);
                            var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(5) - (double)ChaosPersists.ChaosRand.nextInt(5), this.posY + 3.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(5) - (double)ChaosPersists.ChaosRand.nextInt(5), boots);
                            this.world.spawnEntity((Entity)var3);
                        }
                    }
                }
                if (this.world.rand.nextInt(4) == 1 && (boots = target.getItemStackFromSlot(net.minecraft.inventory.EntityEquipmentSlot.MAINHAND)) != null && !boots.isEmpty()) {
                    target.setItemStackToSlot(net.minecraft.inventory.EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
                    bid = boots.getMaxDamage() - boots.getItemDamage();
                    bid = bid > 10 ? (bid /= 10) : 1;
                    boots.damageItem(bid, (EntityLivingBase)this);
                    var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(5) - (double)ChaosPersists.ChaosRand.nextInt(5), this.posY + 3.0, this.posZ + (double)ChaosPersists.ChaosRand.nextInt(5) - (double)ChaosPersists.ChaosRand.nextInt(5), boots);
                    this.world.spawnEntity((Entity)var3);
                }
            }
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public void fall(float par1, float par2) {
        if (!this.noClip) {
            super.fall(par1, par2);
        }
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
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
                    if (s == null || !s.equals("Large Worm")) continue;
                    this.wormsSpawned = 1;
                    return true;
                }
            }
        }
        if (this.posY < 50.0) {
            return false;
        }
        WormLarge target = null;
        target = (WormLarge)this.world.findNearestEntityWithinAABB(WormLarge.class, this.getEntityBoundingBox().expand(32.0, 8.0, 32.0), (Entity)this);
        if (target != null) {
            return false;
        }
        for (i = -6; i <= 6; ++i) {
            for (j = -6; j <= 6; ++j) {
                for (k = -2; k >= -8; --k) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j)).getBlock();
                    if (bid != Blocks.AIR) continue;
                    return false;
                }
            }
        }
        for (i = -6; i <= 6; ++i) {
            for (j = -6; j <= 6; ++j) {
                for (k = 2; k <= 8; ++k) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + i, (int)this.posY + k, (int)this.posZ + j)).getBlock();
                    if (bid == Blocks.AIR) continue;
                    return false;
                }
            }
        }
        return true;
    }

    public void initCreature() {
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean ret = false;
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return ret;
        }
        ret = super.attackEntityFrom(par1DamageSource, par2);
        return ret;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("wormsSpawned", this.wormsSpawned);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.wormsSpawned = par1NBTTagCompound.getInteger("wormsSpawned");
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        var8 = EntityList.createEntityByIDFromName(new net.minecraft.util.ResourceLocation("chaospersists", par1.toLowerCase().replace(" ", "_")), (World)par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
        }
        return var8;
    }

    protected Item getDropItem() {
        return Items.ROTTEN_FLESH;
    }

    private void dropItemRand(Item index, int par1) {
        EntityItem var3 = new EntityItem(this.world, this.posX + (double)ChaosPersists.ChaosRand.nextInt(4) - (double)ChaosPersists.ChaosRand.nextInt(4), this.posY + 2.5 + (double)this.world.rand.nextInt(4), this.posZ + (double)ChaosPersists.ChaosRand.nextInt(4) - (double)ChaosPersists.ChaosRand.nextInt(4), new ItemStack(index, par1, 0));
        this.world.spawnEntity((Entity)var3);
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var4;
        this.dropItemRand(ChaosPersists.WormTooth, 1);
        this.dropItemRand(Items.ITEM_FRAME, 1);
        for (var4 = 0; var4 < 6; ++var4) {
            this.dropItemRand(Items.ROTTEN_FLESH, 1);
        }
        for (var4 = 0; var4 < 6; ++var4) {
            this.dropItemRand(Items.LEATHER, 1);
        }
        for (var4 = 0; var4 < 8; ++var4) {
            this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DIRT), 1);
        }
        for (var4 = 0; var4 < 16; ++var4) {
            this.dropItemRand(Items.GOLD_NUGGET, 1);
        }
        for (var4 = 0; var4 < 5; ++var4) {
            this.dropItemRand(Items.DIAMOND, 1);
        }
        for (var4 = 0; var4 < 4; ++var4) {
            this.dropItemRand(ChaosPersists.UraniumNugget, 1);
        }
        for (var4 = 0; var4 < 4; ++var4) {
            this.dropItemRand(ChaosPersists.TitaniumNugget, 1);
        }
    }
}

