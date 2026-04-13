/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Chipmunk
 *  com.astryxion.chaospersists.EntityCannonFodder
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.Lizard
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.VelocityRaptor
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityList
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.monster.EntityMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.Chipmunk;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.entity.Lizard;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.VelocityRaptor;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/*
 * Exception performing whole class analysis ignored.
 */
public class EntityCannonFodder
extends EntityTameable {
    private static final DataParameter<Integer> IS_ACTIVATED = EntityDataManager.createKey(EntityCannonFodder.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HAT_COLOR = EntityDataManager.createKey(EntityCannonFodder.class, DataSerializers.VARINT);
    String name_one = null;
    String name_two = null;
    private int is_activated = 0;
    private int hat_color = 0;
    private int syncer = 0;
    private int px = 0;
    private int pz = 0;
    private int py = 0;
    private GenericTargetSorter LocalTargetSorter = null;

    public EntityCannonFodder(World par1World) {
        super(par1World);
        this.LocalTargetSorter = new GenericTargetSorter((Entity)this);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(IS_ACTIVATED, 0);
        this.getDataManager().register(HAT_COLOR, 0);
    }

    public void onUpdate() {
        super.onUpdate();
        ++this.syncer;
        if (this.syncer > 5) {
            if (this.world.isRemote) {
                this.is_activated = this.getDataManager().get(IS_ACTIVATED).intValue();
                this.hat_color = this.getDataManager().get(HAT_COLOR).intValue();
            } else {
                this.getDataManager().set(IS_ACTIVATED, this.is_activated);
                this.getDataManager().set(HAT_COLOR, this.hat_color);
            }
            this.syncer = 0;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean processInteract(EntityPlayer par1EntityPlayer, net.minecraft.util.EnumHand hand) {
        ItemStack var2 = par1EntityPlayer.getHeldItem(hand);
        if (var2 != null && !var2.isEmpty() && var2.getCount() <= 0) {
            par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
            var2 = ItemStack.EMPTY;
        }
        if (super.processInteract(par1EntityPlayer, hand)) {
            return true;
        }
        if (this.name_one != null && this.isTamed()) {
            if (this.name_one.equals(par1EntityPlayer.getUniqueID().toString())) {
                if (this.name_two == null) {
                    this.name_two = this.name_one;
                    this.name_one = par1EntityPlayer.getUniqueID().toString();
                    this.setOwnerId(this.name_one != null && !this.name_one.isEmpty() ? java.util.UUID.fromString(this.name_one) : null);
                    this.is_activated = 2;
                }
            } else if (this.name_two != null) {
                if (!this.name_two.equals(par1EntityPlayer.getUniqueID().toString())) return true;
                this.name_two = this.name_one;
                this.name_one = par1EntityPlayer.getUniqueID().toString();
                this.setOwnerId(this.name_one != null && !this.name_one.isEmpty() ? java.util.UUID.fromString(this.name_one) : null);
                this.is_activated = 2;
            } else {
                this.name_two = this.name_one;
                this.name_one = par1EntityPlayer.getUniqueID().toString();
                this.setOwnerId(this.name_one != null && !this.name_one.isEmpty() ? java.util.UUID.fromString(this.name_one) : null);
                this.is_activated = 2;
            }
        }
        if (var2 != null && !var2.isEmpty() && var2.getItem() == Items.CARROT && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            this.hat_color = 1;
            if (this.name_one == null) {
                this.name_one = par1EntityPlayer.getUniqueID().toString();
            }
            if (this.is_activated == 0) {
                this.is_activated = 1;
            }
            this.setTamed(true);
            this.setOwnerId(this.name_one != null && !this.name_one.isEmpty() ? java.util.UUID.fromString(this.name_one) : null);
            this.playTameEffect(true);
            this.heal(this.getMaxHealth() - this.getHealth());
            this.setGrowingAge(-24000);
            if (par1EntityPlayer.capabilities.isCreativeMode) return true;
            var2.shrink(1);
            if (var2.getCount() > 0) return true;
            par1EntityPlayer.setHeldItem(hand, ItemStack.EMPTY);
            return true;
        }
        if (var2 != null && !var2.isEmpty() && var2.getItem() == Items.POTATO && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            this.hat_color = 3;
            if (this.name_one == null) {
                this.name_one = par1EntityPlayer.getUniqueID().toString();
            }
            if (this.is_activated == 0) {
                this.is_activated = 1;
            }
            this.setTamed(true);
            this.setOwnerId(this.name_one != null && !this.name_one.isEmpty() ? java.util.UUID.fromString(this.name_one) : null);
            this.playTameEffect(true);
            this.heal(this.getMaxHealth() - this.getHealth());
            this.setGrowingAge(-24000);
            if (par1EntityPlayer.capabilities.isCreativeMode) return true;
            var2.shrink(1);
            if (var2.getCount() > 0) return true;
            par1EntityPlayer.setHeldItem(net.minecraft.util.EnumHand.MAIN_HAND, ItemStack.EMPTY);
            return true;
        }
        if (var2 != null && !var2.isEmpty() && var2.getItem() == ChaosPersists.MyQuinoa && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            this.hat_color = 2;
            if (this.name_one == null) {
                this.name_one = par1EntityPlayer.getUniqueID().toString();
            }
            if (this.is_activated == 0) {
                this.is_activated = 1;
            }
            this.setTamed(true);
            this.setOwnerId(this.name_one != null && !this.name_one.isEmpty() ? java.util.UUID.fromString(this.name_one) : null);
            this.playTameEffect(true);
            this.heal(this.getMaxHealth() - this.getHealth());
            this.setGrowingAge(-24000);
            if (par1EntityPlayer.capabilities.isCreativeMode) return true;
            var2.shrink(1);
            if (var2.getCount() > 0) return true;
            par1EntityPlayer.setHeldItem(net.minecraft.util.EnumHand.MAIN_HAND, ItemStack.EMPTY);
            return true;
        }
        if (var2 != null && !var2.isEmpty() && this.is_activated == 2 && var2.getItem() == ChaosPersists.MyCornCob && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            Entity newent;
            String myname = "Ostrich";
            if (this instanceof Lizard) {
                myname = "Lizard";
            }
            if (this instanceof Chipmunk) {
                myname = "Chipmunk";
            }
            if (this instanceof VelocityRaptor) {
                myname = "Velocity Raptor";
            }
            if (!this.world.isRemote && (newent = EntityCannonFodder.spawnCreature((World)this.world, (String)myname, (double)(this.posX + (double)this.world.rand.nextFloat()), (double)(this.posY + 0.01), (double)(this.posZ + (double)this.world.rand.nextFloat()))) != null) {
                EntityCannonFodder cf = (EntityCannonFodder)newent;
                cf.setOwnerId(this.getOwnerId());
                cf.setTamed(true);
                cf.setStuff(this.hat_color, this.is_activated, this.name_one, this.name_two);
            }
            this.playTameEffect(true);
            par1EntityPlayer.playSound(net.minecraft.util.SoundEvent.REGISTRY.getObject(new net.minecraft.util.ResourceLocation("entity.generic.explode")), 0.75f, 2.0f);
            if (par1EntityPlayer.capabilities.isCreativeMode) return true;
            var2.shrink(1);
            if (var2.getCount() > 0) return true;
            par1EntityPlayer.setHeldItem(net.minecraft.util.EnumHand.MAIN_HAND, ItemStack.EMPTY);
            return true;
        }
        if (this.is_activated != 2 || par1EntityPlayer.getDistanceSq((Entity)this) >= 16.0) return false;
        if (this.isSitting()) {
            this.setSitting(false);
            this.playTameEffect(true);
            return true;
        } else {
            this.setSitting(true);
            this.playTameEffect(false);
            this.px = (int)this.posX;
            this.py = (int)this.posY;
            this.pz = (int)this.posZ;
        }
        return true;
    }

    public static Entity spawnCreature(World par0World, String par1, double par2, double par4, double par6) {
        Entity var8 = null;
        net.minecraft.util.ResourceLocation key = par1.indexOf(':') >= 0 ? new net.minecraft.util.ResourceLocation(par1) : new net.minecraft.util.ResourceLocation("chaospersists", par1);
        var8 = EntityList.createEntityByIDFromName(key, par0World);
        if (var8 != null) {
            var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0f, 0.0f);
            par0World.spawnEntity(var8);
            ((EntityLiving)var8).playLivingSound();
        }
        return var8;
    }

    public void setStuff(int hc, int ia, String s1, String s2) {
        this.hat_color = hc;
        this.is_activated = ia;
        this.name_one = s1;
        this.name_two = s2;
        this.setGrowingAge(-24000);
    }

    public int getHatColor() {
        return this.hat_color;
    }

    public int get_is_activated() {
        return this.is_activated;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        if (this.name_one == null) {
            par1NBTTagCompound.setString("NameOne", "");
        } else {
            par1NBTTagCompound.setString("NameOne", this.name_one);
        }
        if (this.name_two == null) {
            par1NBTTagCompound.setString("NameTwo", "");
        } else {
            par1NBTTagCompound.setString("NameTwo", this.name_two);
        }
        par1NBTTagCompound.setInteger("IsActivated", this.is_activated);
        par1NBTTagCompound.setInteger("HatColor", this.hat_color);
        par1NBTTagCompound.setInteger("PatrolX", this.px);
        par1NBTTagCompound.setInteger("PatrolY", this.py);
        par1NBTTagCompound.setInteger("PatrolZ", this.pz);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.name_one = par1NBTTagCompound.getString("NameOne");
        if (this.name_one != null && this.name_one.equals("")) {
            this.name_one = null;
        }
        this.name_two = par1NBTTagCompound.getString("NameTwo");
        if (this.name_two != null && this.name_two.equals("")) {
            this.name_two = null;
        }
        this.is_activated = par1NBTTagCompound.getInteger("IsActivated");
        this.hat_color = par1NBTTagCompound.getInteger("HatColor");
        this.px = par1NBTTagCompound.getInteger("PatrolX");
        this.py = par1NBTTagCompound.getInteger("PatrolY");
        this.pz = par1NBTTagCompound.getInteger("PatrolZ");
        if (this.name_one != null) {
            this.setTamed(true);
            this.setOwnerId(this.name_one != null && !this.name_one.isEmpty() ? java.util.UUID.fromString(this.name_one) : null);
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        double dx;
        double dy;
        double dz;
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
        if (this.isSitting() && (dx = (double)this.px - par1EntityLiving.posX) * dx + (dy = (double)this.py - par1EntityLiving.posY) * dy + (dz = (double)this.pz - par1EntityLiving.posZ) * dz > 144.0) {
            return false;
        }
        if (par1EntityLiving instanceof EntityMob) {
            return true;
        }
        if (par1EntityLiving instanceof EntityCannonFodder) {
            EntityCannonFodder cf = (EntityCannonFodder)par1EntityLiving;
            int i = cf.getHatColor();
            if (i != 0 && i != this.hat_color) {
                return true;
            }
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
            if (this.name_one != null && this.name_one.equals(p.getUniqueID().toString())) {
                return false;
            }
            if (this.name_two != null && this.name_two.equals(p.getUniqueID().toString())) {
                return false;
            }
            return true;
        }
        return false;
    }

    private EntityLivingBase findSomethingToAttack() {
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0, 4.0, 10.0));
        Collections.sort(var5, this.LocalTargetSorter);
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

    public int getTotalArmorValue() {
        if (this.is_activated == 2) {
            return 3;
        }
        return 0;
    }

    public void attackEntityAsFodder(Entity par1Entity, float f) {
        par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), f);
    }

    protected void updateAITasks() {
        if (this.isDead) {
            return;
        }
        super.updateAITasks();
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        if (this.is_activated != 2) {
            return;
        }
        int pfreq = 5;
        int sfreq = 7;
        float dm = 4.0f;
        if (this instanceof Chipmunk) {
            dm = 3.0f;
            sfreq = 6;
        }
        if (this instanceof Lizard) {
            dm = 6.0f;
            sfreq = 8;
        }
        if (this instanceof VelocityRaptor) {
            sfreq = 6;
            pfreq = 4;
        }
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.rand.nextInt(pfreq) == 1) {
            EntityLivingBase e = this.findSomethingToAttack();
            if (e != null) {
                this.getNavigator().tryMoveToEntityLiving((Entity)e, 1.25);
                if (this.getDistanceSq((Entity)e) < 9.0 && (this.rand.nextInt(sfreq + 1) == 0 || this.rand.nextInt(sfreq) == 1)) {
                    this.attackEntityAsFodder((Entity)e, dm);
                }
            } else if (this.isSitting()) {
                this.getNavigator().tryMoveToXYZ((double)this.px, (double)this.py, (double)this.pz, 0.6499999761581421);
            }
        }
        if (this.world.rand.nextInt(250) == 1) {
            this.heal(1.0f);
        }
    }

    public EntityAgeable createChild(EntityAgeable entityageable) {
        return null;
    }
}

