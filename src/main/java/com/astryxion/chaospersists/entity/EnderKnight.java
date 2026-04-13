/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EnderKnight
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.material.Material
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.AttributeModifier
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.tileentity.MobSpawnerBaseLogic
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.EntityDamageSourceIndirect
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnderKnight
extends EntityMob {
    private static final DataParameter<Byte> SCREAMING = EntityDataManager.createKey(EnderKnight.class, DataSerializers.BYTE);
    private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final AttributeModifier attackingSpeedBoostModifier = new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 6.199999809265137, 0).setSaved(false);
    private int teleportDelay;
    private int stareTimer;
    private Entity lastEntityToAttack;

    public EnderKnight(World par1World) {
        super(par1World);
        this.setSize(0.6f, 2.9f);
        this.stepHeight = 1.0f;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ChaosPersists.EnderKnight_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ChaosPersists.EnderKnight_stats.attack);
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(SCREAMING, (byte)0);
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    protected Entity findPlayerToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        EntityPlayer entityplayer = this.world.getNearestAttackablePlayer(this, 64.0, 64.0);
        if (entityplayer != null) {
            if (this.shouldAttackPlayer(entityplayer)) {
                if (this.stareTimer == 0) {
                    this.world.playSound(null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("minecraft", "entity.endermen.stare")), net.minecraft.util.SoundCategory.HOSTILE, 1.0f, 1.0f);
                }
                if (this.stareTimer++ == 5) {
                    this.stareTimer = 0;
                }
                this.setScreaming(true);
                return entityplayer;
            }
            this.stareTimer = 0;
            this.setScreaming(false);
        }
        return null;
    }

    private boolean shouldAttackPlayer(EntityPlayer par1EntityPlayer) {
        ItemStack itemstack = par1EntityPlayer.inventory.armorInventory.get(3);
        if (itemstack != null && !itemstack.isEmpty() && itemstack.getItem() == Item.getItemFromBlock((Block)Blocks.PUMPKIN)) {
            return false;
        }
        Vec3d Vec3d = par1EntityPlayer.getLook(1.0f).normalize();
        Vec3d vec31 = new Vec3d((double)(this.posX - par1EntityPlayer.posX), (double)(this.getEntityBoundingBox().minY + (double)(this.height / 2.0f) - (par1EntityPlayer.posY + (double)par1EntityPlayer.getEyeHeight())), (double)(this.posZ - par1EntityPlayer.posZ));
        double d0 = vec31.length();
        double d1 = Vec3d.dotProduct(vec31 = vec31.normalize());
        return d1 > 1.0 - 0.025 / d0 ? par1EntityPlayer.canEntityBeSeen((Entity)this) : false;
    }

    public void onLivingUpdate() {
        float f;
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0f);
        }
        if (this.lastEntityToAttack != this.getAttackTarget()) {
            IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
            attributeinstance.removeModifier(attackingSpeedBoostModifier);
            if (this.getAttackTarget() != null) {
                attributeinstance.applyModifier(attackingSpeedBoostModifier);
            }
        }
        this.lastEntityToAttack = this.getAttackTarget();
        for (int i = 0; i < 2; ++i) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25, this.posZ + (this.rand.nextDouble() - 0.5) * (double)this.width, (this.rand.nextDouble() - 0.5) * 2.0, - this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0);
        }
        if (this.world.isDaytime() && !this.world.isRemote && (f = this.getBrightness()) > 0.5f && this.world.canBlockSeeSky(new net.minecraft.util.math.BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ))) && this.rand.nextFloat() * 30.0f < (f - 0.4f) * 2.0f) {
            this.setAttackTarget(null);
            this.setScreaming(false);
            this.teleportRandomly();
        }
        if (this.isWet() || this.isBurning()) {
            this.setScreaming(false);
            this.teleportRandomly();
        }
        this.isJumping = false;
        if (this.getAttackTarget() != null) {
            this.faceEntity(this.getAttackTarget(), 100.0f, 100.0f);
        }
        if (!this.world.isRemote && this.isEntityAlive()) {
            if (this.getAttackTarget() != null) {
                if (this.getAttackTarget() instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer)this.getAttackTarget())) {
                    if (this.getAttackTarget().getDistanceSq((Entity)this) < 16.0) {
                        this.teleportRandomly();
                    }
                    this.teleportDelay = 0;
                } else if (this.getAttackTarget().getDistanceSq((Entity)this) > 256.0 && this.teleportDelay++ >= 30 && this.teleportToEntity(this.getAttackTarget())) {
                    this.teleportDelay = 0;
                }
            } else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }
        super.onLivingUpdate();
    }

    protected boolean teleportRandomly() {
        double d0 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
        double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
        double d2 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(d0, d1, d2);
    }

    protected boolean teleportToEntity(Entity par1Entity) {
        Vec3d Vec3d = new Vec3d((double)(this.posX - par1Entity.posX), (double)(this.getEntityBoundingBox().minY + (double)(this.height / 2.0f) - par1Entity.posY + (double)par1Entity.getEyeHeight()), (double)(this.posZ - par1Entity.posZ));
        Vec3d = Vec3d.normalize();
        double d0 = 16.0;
        double d1 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - Vec3d.x * d0;
        double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - Vec3d.y * d0;
        double d3 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - Vec3d.z * d0;
        return this.teleportTo(d1, d2, d3);
    }

    protected boolean teleportTo(double par1, double par3, double par5) {
        int j;
        int k;
        double d3 = this.posX;
        double d4 = this.posY;
        double d5 = this.posZ;
        this.posX = par1;
        this.posY = par3;
        this.posZ = par5;
        boolean flag = false;
        int i = MathHelper.floor(this.posX);
        net.minecraft.util.math.BlockPos blockPos = new net.minecraft.util.math.BlockPos(i, j = MathHelper.floor(this.posY), k = MathHelper.floor(this.posZ));
        if (this.world.isBlockLoaded(blockPos)) {
            boolean flag1 = false;
            while (!flag1 && j > 0) {
                Block l = this.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j - 1, k)).getBlock();
                if (l != Blocks.AIR && l.getMaterial(this.world.getBlockState(new net.minecraft.util.math.BlockPos(i, j - 1, k))).blocksMovement()) {
                    flag1 = true;
                    continue;
                }
                this.posY -= 1.0;
                --j;
            }
            if (flag1) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.isMaterialInBB(this.getEntityBoundingBox(), net.minecraft.block.material.Material.WATER) && !this.world.isMaterialInBB(this.getEntityBoundingBox(), net.minecraft.block.material.Material.LAVA)) {
                    flag = true;
                }
            }
        }
        if (!flag) {
            this.setPosition(d3, d4, d5);
            return false;
        }
        int short1 = 128;
        for (int lx = 0; lx < short1; ++lx) {
            double d6 = (double)lx / ((double)short1 - 1.0);
            float f = (this.rand.nextFloat() - 0.5f) * 0.2f;
            float f1 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            float f2 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5) * (double)this.width * 2.0;
            double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * (double)this.height;
            double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5) * (double)this.width * 2.0;
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.PORTAL, d7, d8, d9, (double)f, (double)f1, (double)f2);
        }
        this.world.playSound(null, d3, d4, d5, net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("minecraft", "entity.endermen.teleport")), net.minecraft.util.SoundCategory.HOSTILE, 1.0f, 1.0f);
        this.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("minecraft", "entity.endermen.teleport")), 1.0f, 1.0f);
        return true;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        return this.isScreaming() ? net.minecraft.init.SoundEvents.ENTITY_ENDERMEN_SCREAM : net.minecraft.init.SoundEvents.ENTITY_ENDERMEN_AMBIENT;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return net.minecraft.init.SoundEvents.ENTITY_ENDERMEN_HURT;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return net.minecraft.init.SoundEvents.ENTITY_ENDERMEN_DEATH;
    }

    public int getTotalArmorValue() {
        return ChaosPersists.EnderKnight_stats.defense;
    }

    protected Item getDropItem() {
        if (this.world.rand.nextInt(2) == 1) {
            return Items.ENDER_EYE;
        }
        return Items.ENDER_PEARL;
    }

    protected void dropFewItems(boolean par1, int par2) {
        Item j = this.getDropItem();
        if (j != null) {
            int k = this.rand.nextInt(2 + par2);
            for (int l = 0; l < k; ++l) {
                this.dropItem(j, 1);
            }
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (this.isEntityInvulnerable(par1DamageSource)) {
            return false;
        }
        this.setScreaming(true);
        if (par1DamageSource instanceof net.minecraft.util.EntityDamageSourceIndirect) {
            for (int i = 0; i < 16; ++i) {
                if (!this.teleportRandomly()) continue;
                return true;
            }
            return super.attackEntityFrom(par1DamageSource, par2);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
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
                    if (s == null || !s.equals("Ender Knight")) continue;
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
        if (this.posY < 30.0) {
            return false;
        }
        return true;
    }

    public boolean isScreaming() {
        return this.getDataManager().get(SCREAMING).byteValue() > 0;
    }

    public void setScreaming(boolean par1) {
        this.getDataManager().set(SCREAMING, (byte)(par1 ? 1 : 0));
    }
}

