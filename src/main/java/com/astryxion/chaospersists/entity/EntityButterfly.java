/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EntityButterfly
 *  com.astryxion.chaospersists.EntityLunaMoth
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.Mothra
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.ChaosTeleporter
 *  net.minecraft.block.Block
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.passive.EntityAmbientCreature
 *  net.minecraft.entity.passive.EntityHorse
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.management.PlayerList
 *  net.minecraft.tileentity.MobSpawnerBaseLogic
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.tileentity.TileEntityMobSpawner
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.ChunkCoordinates
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldProvider
 *  net.minecraft.world.WorldServer
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.EntityLunaMoth;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.entity.Mothra;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.core.ChaosTeleporter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public class EntityButterfly
extends EntityAmbientCreature {
    private static final DataParameter<Integer> BUTTERFLY_TYPE = EntityDataManager.createKey(EntityButterfly.class, DataSerializers.VARINT);
    private static final ResourceLocation texture1 = new ResourceLocation("chaospersists", "textures/entity/butterfly.png");
    private static final ResourceLocation texture2 = new ResourceLocation("chaospersists", "textures/entity/butterfly2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("chaospersists", "textures/entity/butterfly3.png");
    private static final ResourceLocation texture4 = new ResourceLocation("chaospersists", "textures/entity/butterfly4.png");
    private static final ResourceLocation texture5 = new ResourceLocation("chaospersists", "textures/entity/eyemoth.png");
    private static final ResourceLocation texture6 = new ResourceLocation("chaospersists", "textures/entity/lunamoth.png");
    private static final ResourceLocation texture7 = new ResourceLocation("chaospersists", "textures/entity/darkmoth.png");
    private static final ResourceLocation texture8 = new ResourceLocation("chaospersists", "textures/entity/firemoth.png");
    private static final ResourceLocation texture9 = new ResourceLocation("chaospersists", "textures/entity/vbutterfly1.png");
    public int butterfly_type = ChaosPersists.ChaosRand.nextInt(4);
    private int attack_delay = 0;
    private GenericTargetSorter TargetSorter = null;
    private int force_sync = 25;
    private BlockPos currentFlightTarget = null;

    public EntityButterfly(World par1World) {
        super(par1World);
        this.setSize(0.4f, 0.4f);
                this.TargetSorter = new GenericTargetSorter((Entity)this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10000000149011612);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    public ResourceLocation getTexture(EntityButterfly a) {
        if (a instanceof Mothra) {
            return texture5;
        }
        if (a instanceof EntityLunaMoth) {
            if (((EntityLunaMoth)a).moth_type == 1) {
                return texture5;
            }
            if (((EntityLunaMoth)a).moth_type == 2) {
                return texture7;
            }
            if (((EntityLunaMoth)a).moth_type == 3) {
                return texture8;
            }
            return texture6;
        }
        if (this.butterfly_type == 1) {
            if (this.world.provider.getDimension() == ChaosPersists.getDimension(4)) {
                return texture9;
            }
            return texture2;
        }
        if (this.butterfly_type == 2) {
            return texture3;
        }
        if (this.butterfly_type == 3) {
            return texture4;
        }
        return texture1;
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(BUTTERFLY_TYPE, this.butterfly_type);
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return true;
    }

    protected float getSoundVolume() {
        return 0.0f;
    }

    protected float getSoundPitch() {
        return 1.0f;
    }

    protected net.minecraft.util.SoundEvent getAmbientSound() {
        return null;
    }

    protected net.minecraft.util.SoundEvent getHurtSound(net.minecraft.util.DamageSource damageSource) {
        return null;
    }

    protected net.minecraft.util.SoundEvent getDeathSound() {
        return null;
    }

    public boolean canBePushed() {
        return true;
    }

    protected void collideWithEntity(Entity par1Entity) {
    }

    protected void collideWithNearbyEntities() {
    }

    public int mygetMaxHealth() {
        return 2;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    protected void updateAITasks() {
        int keep_trying = 25;
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.currentFlightTarget == null) {
            this.currentFlightTarget = new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ);
        }
        if (this.rand.nextInt(100) == 0 || this.currentFlightTarget.distanceSq(this.posX, this.posY, this.posZ) < 4.0f) {
            Block bid = Blocks.STONE;
            while (bid != Blocks.AIR && keep_trying != 0) {
                this.currentFlightTarget = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
                bid = this.world.getBlockState(this.currentFlightTarget).getBlock();
                --keep_trying;
            }
        } else if (this.rand.nextInt(10) == 0 && this.world.provider.getDimension() == ChaosPersists.getDimension(4) && this.butterfly_type == 1 && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
            EntityLivingBase e = null;
            e = this.findSomethingToAttack();
            if (e != null) {
                this.currentFlightTarget = new BlockPos((int)e.posX, (int)(e.posY + 1.0), (int)e.posZ);
                if (this.getDistanceSq((Entity)e) < 6.0) {
                    this.attackEntityAsMob((Entity)e);
                }
            }
        }
        double var1 = (double)this.currentFlightTarget.getX() + 0.5 - this.posX;
        double var3 = (double)this.currentFlightTarget.getY() + 0.1 - this.posY;
        double var5 = (double)this.currentFlightTarget.getZ() + 0.5 - this.posZ;
        this.motionX += (Math.signum(var1) * 0.5 - this.motionX) * 0.10000000149011612;
        this.motionY += (Math.signum(var3) * 0.699999988079071 - this.motionY) * 0.10000000149011612;
        this.motionZ += (Math.signum(var5) * 0.5 - this.motionZ) * 0.10000000149011612;
        float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0 / 3.141592653589793) - 90.0f;
        float var8 = MathHelper.wrapDegrees((float)(var7 - this.rotationYaw));
        this.moveForward = 0.5f;
        this.rotationYaw += var8;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (ChaosPersists.ChaosRand.nextInt(2) != 0) {
            return false;
        }
        if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
            return false;
        }
        boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 1.0f);
        return var4;
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
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
            return true;
        }
        if (par1EntityLiving instanceof EntityHorse) {
            return true;
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(8.0, 5.0, 8.0));
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

    public void onUpdate() {
        super.onUpdate();
        this.motionY *= 0.6000000238418579;
        --this.force_sync;
        if (this.force_sync < 0) {
            this.force_sync = 25;
            if (this.world.isRemote) {
                this.butterfly_type = this.getDataManager().get(BUTTERFLY_TYPE).intValue();
            } else {
                this.getDataManager().set(BUTTERFLY_TYPE, this.butterfly_type);
            }
        }
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    /** Called on right-click in 1.12.2; delegates to interact() so teleport runs. */
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        if (player != null && (player.getHeldItem(hand) == null || player.getHeldItem(hand).isEmpty())) {
            return this.interact(player);
        }
        return super.processInteract(player, hand);
    }

    public boolean interact(EntityPlayer par1EntityPlayer) {
        if (par1EntityPlayer == null) {
            return false;
        }
        if (!(par1EntityPlayer instanceof EntityPlayerMP)) {
            return false;
        }
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getCount() <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
            var2 = null;
        }
        if (var2 != null) {
            return false;
        }
        net.minecraft.server.MinecraftServer server = this.world.getMinecraftServer();
        if (server != null) {
            if (par1EntityPlayer.dimension != ChaosPersists.getDimension(6)) {
                server.getPlayerList().transferPlayerToDimension((EntityPlayerMP)par1EntityPlayer, ChaosPersists.getDimension(6), (Teleporter)new ChaosTeleporter(server.getWorld(ChaosPersists.getDimension(6)), ChaosPersists.getDimension(6), this.world));
            } else {
                server.getPlayerList().transferPlayerToDimension((EntityPlayerMP)par1EntityPlayer, 0, (Teleporter)new ChaosTeleporter(server.getWorld(0), 0, this.world));
            }
        }
        return true;
    }

    public boolean getCanSpawnHere() {
        Block bid;
        for (int k = -3; k < 3; ++k) {
            for (int j = -3; j < 3; ++j) {
                for (int i = 0; i < 5; ++i) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k)).getBlock();
                    if (bid != Blocks.MOB_SPAWNER) continue;
                    TileEntityMobSpawner tileentitymobspawner = null;
                    tileentitymobspawner = (TileEntityMobSpawner)this.world.getTileEntity(new net.minecraft.util.math.BlockPos((int)this.posX + j, (int)this.posY + i, (int)this.posZ + k));
                                        String s = null;
                    net.minecraft.util.ResourceLocation id = com.astryxion.chaospersists.util.SpawnerFixHelper.getMobSpawnerEntityId(tileentitymobspawner.getSpawnerBaseLogic());
                    if (id != null) s = id.getPath();
                    if (s == null || !s.equals("Butterfly")) continue;
                    this.butterfly_type = 1;
                    return true;
                }
            }
        }
        bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock();
        if (bid != Blocks.AIR) {
            return false;
        }
        if (!this.world.isDaytime()) {
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

    public void initCreature() {
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("ButterflyType", this.butterfly_type);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.butterfly_type = par1NBTTagCompound.getInteger("ButterflyType");
    }
}

