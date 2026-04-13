/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EntityAnt
 *  com.astryxion.chaospersists.EntityRainbowAnt
 *  com.astryxion.chaospersists.EntityRedAnt
 *  com.astryxion.chaospersists.EntityUnstableAnt
 *  com.astryxion.chaospersists.MyEntityAIWanderALot
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.ChaosTeleporter
 *  com.astryxion.chaospersists.Termite
 *  net.minecraft.entity.EntityAgeable
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAIPanic
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.passive.EntityAnimal
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.EntityPlayerMP
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.management.PlayerList
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.Teleporter
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 */
package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.entity.EntityRainbowAnt;
import com.astryxion.chaospersists.entity.EntityRedAnt;
import com.astryxion.chaospersists.entity.EntityUnstableAnt;
import com.astryxion.chaospersists.util.MyEntityAIWanderALot;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.core.ChaosTeleporter;
import com.astryxion.chaospersists.entity.Termite;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityAnt
extends EntityAnimal {
    public double moveSpeed = 0.15000000596046448;
    private static final ResourceLocation texture1 = new ResourceLocation("chaospersists", "textures/entity/ant.png");
    private static final ResourceLocation texture2 = new ResourceLocation("chaospersists", "textures/entity/red_ant.png");
    private static final ResourceLocation texture3 = new ResourceLocation("chaospersists", "textures/entity/rainbow_ant.png");
    private static final ResourceLocation texture4 = new ResourceLocation("chaospersists", "textures/entity/unstableant.png");
    private static final ResourceLocation texture5 = new ResourceLocation("chaospersists", "textures/entity/termite.png");

    public EntityAnt(World par1World) {
        super(par1World);
        this.setSize(0.1f, 0.1f);
        this.experienceValue = 0;
                this.tasks.addTask(0, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.4));
        this.tasks.addTask(1, (EntityAIBase)new MyEntityAIWanderALot((EntityCreature)this, 9, 1.0));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)this.mygetMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    public ResourceLocation getTexture(EntityAnt a) {
        if (a instanceof EntityRedAnt) {
            return texture2;
        }
        if (a instanceof EntityRainbowAnt) {
            return texture3;
        }
        if (a instanceof EntityUnstableAnt) {
            return texture4;
        }
        if (a instanceof Termite) {
            return texture5;
        }
        return texture1;
    }

    protected boolean canDespawn() {
        if (this.isNoDespawnRequired()) {
            return false;
        }
        return true;
    }

    public void onUpdate() {
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.moveSpeed);
        super.onUpdate();
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
        if (par1EntityPlayer.dimension != ChaosPersists.getDimension()) {
            net.minecraft.server.MinecraftServer server = this.world.getMinecraftServer();
            if (server != null) server.getPlayerList().transferPlayerToDimension((EntityPlayerMP)par1EntityPlayer, ChaosPersists.getDimension(), (Teleporter)new ChaosTeleporter(server.getWorld(ChaosPersists.getDimension()), ChaosPersists.getDimension(), this.world));
        } else {
            net.minecraft.server.MinecraftServer server = this.world.getMinecraftServer();
            if (server != null) server.getPlayerList().transferPlayerToDimension((EntityPlayerMP)par1EntityPlayer, 0, (Teleporter)new ChaosTeleporter(server.getWorld(0), 0, this.world));
        }
        return true;
    }

    public boolean isAIEnabled() {
        return true;
    }

    public int mygetMaxHealth() {
        return 1;
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

    protected float getSoundVolume() {
        return 0.0f;
    }

    protected void playStepSound(int par1, int par2, int par3, int par4) {
    }

    protected void dropFewItems(boolean par1, int par2) {
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    public boolean getCanSpawnHere() {
        if (this.posY < 50.0) {
            return false;
        }
        if (this.findBuddies() > 4) {
            return false;
        }
        return true;
    }

    private int findBuddies() {
        List var5 = this.world.getEntitiesWithinAABB(EntityAnt.class, this.getEntityBoundingBox().expand(20.0, 10.0, 20.0));
        return var5.size();
    }

    public void updateAITasks() {
        if (this.world.rand.nextInt(200) == 1) {
            this.setRevengeTarget(null);
        }
        super.updateAITasks();
    }
}

