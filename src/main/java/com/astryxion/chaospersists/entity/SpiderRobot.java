/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.GenericTargetSorter
 *  com.astryxion.chaospersists.MobStats
 *  com.astryxion.chaospersists.MyUtils
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.RenderSpiderRobotInfo
 *  com.astryxion.chaospersists.SpiderDriver
 *  com.astryxion.chaospersists.SpiderRobot
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockPistonBase
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.EntityAILookIdle
 *  net.minecraft.entity.ai.EntityAITasks
 *  net.minecraft.entity.ai.EntityAIWatchClosest
 *  net.minecraft.entity.ai.EntitySenses
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.monster.EntityCaveSpider
 *  net.minecraft.entity.monster.EntitySpider
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.entity.player.PlayerCapabilities
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.C03PacketPlayer
 *  net.minecraft.network.play.client.C03PacketPlayer$C05PacketPlayerLook
 *  net.minecraft.network.play.client.C0CPacketInput
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.MovementInput
 *  net.minecraft.world.EnumDifficulty
 *  net.minecraft.world.GameRules
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.entity;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.util.GenericTargetSorter;
import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.util.MyUtils;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.render.RenderSpiderRobotInfo;
import com.astryxion.chaospersists.entity.SpiderDriver;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class SpiderRobot
extends EntityLiving {
    private static final DataParameter<Byte> ATTACKING = EntityDataManager.createKey(SpiderRobot.class, DataSerializers.BYTE);
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private int playing = 0;
    private GenericTargetSorter TargetSorter = null;
    private float moveSpeed = 0.35f;
    private RenderSpiderRobotInfo renderdata = new RenderSpiderRobotInfo();
    private int didonce = 0;
    private int rideTicker = 0;

    public SpiderRobot(World par1World) {
        super(par1World);
        this.setSize(3.25f, 2.25f);
        this.TargetSorter = new GenericTargetSorter((Entity)this);
        this.tasks.addTask(0, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 12.0f));
        this.tasks.addTask(1, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.isImmuneToFire = true;
        this.experienceValue = ChaosPersists.SpiderRobot_stats.health / 2;
    }

    public SpiderRobot(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.getYOffset(), par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)ChaosPersists.SpiderRobot_stats.health);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)this.moveSpeed);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue((double)ChaosPersists.SpiderRobot_stats.attack);
    }

    protected boolean canDespawn() {
        return false;
    }

    public int getTotalArmorValue() {
        return ChaosPersists.SpiderRobot_stats.defense;
    }

    protected void updateAITasks() {
        if (this.isDead) {
            return;
        }
        if (this.getControllingPassenger() != null) {
            return;
        }
        super.updateAITasks();
    }

    protected void updateAITick() {
        if (this.getControllingPassenger() != null) {
            return;
        }
        super.updateEntityActionState();
    }

    private void initLegData() {
        if (this.renderdata == null) {
            this.renderdata = new RenderSpiderRobotInfo();
        }
        for (int i = 0; i < 8; ++i) {
            this.renderdata.ycurrentangle[i] = 0.0f;
            this.renderdata.ywantedangle[i] = 0.0f;
            this.renderdata.ydisplayangle[i] = 0.0f;
            this.renderdata.yvelocity[i] = 0.0f;
            this.renderdata.ymid[i] = 0.0f;
            this.renderdata.yoff[i] = 0.0f;
            this.renderdata.yrange[i] = 0.0f;
            this.renderdata.udcurrentangle[i] = 0.0f;
            this.renderdata.udwantedangle[i] = 0.0f;
            this.renderdata.uddisplayangle[i] = 0.0f;
            this.renderdata.udvelocity[i] = 0.0f;
            this.renderdata.p1xangle[i] = 0.7853981633974483;
            this.renderdata.p2xangle[i] = 0.0;
            this.renderdata.p3xangle[i] = -0.7853981633974483;
            this.renderdata.pxvelocity[i] = 0.0f;
            this.renderdata.foot_xpos[i] = (float)this.posX;
            this.renderdata.foot_ypos[i] = (float)this.posY;
            this.renderdata.foot_zpos[i] = (float)this.posZ;
            this.renderdata.realposx[i] = 0.0f;
            this.renderdata.realposy[i] = 0.0f;
            this.renderdata.realposz[i] = 0.0f;
            this.renderdata.legoff[i] = 0.0f;
            this.renderdata.footup[i] = 1;
            this.renderdata.uppoint[i] = 0.0f;
            this.renderdata.footingticker[i] = 0;
            this.renderdata.gpcounter = 0;
            if (i == 0) {
                this.renderdata.legoff[i] = 1.25f;
                this.renderdata.ymid[i] = -0.32f;
                this.renderdata.yrange[i] = 0.2617994f;
                this.renderdata.pairedwith[i] = 1;
                this.renderdata.yoff[i] = -0.3f;
            }
            if (i == 1) {
                this.renderdata.legoff[i] = 1.25f;
                this.renderdata.ymid[i] = 3.4615927f;
                this.renderdata.yrange[i] = -0.2617994f;
                this.renderdata.pairedwith[i] = 0;
                this.renderdata.yoff[i] = -0.3f;
            }
            if (i == 2) {
                this.renderdata.legoff[i] = 2.0f;
                this.renderdata.ymid[i] = -1.0f;
                this.renderdata.yrange[i] = 0.2617994f;
                this.renderdata.pairedwith[i] = 3;
                this.renderdata.yoff[i] = -0.1f;
            }
            if (i == 3) {
                this.renderdata.legoff[i] = 2.0f;
                this.renderdata.ymid[i] = 4.1415925f;
                this.renderdata.yrange[i] = -0.2617994f;
                this.renderdata.pairedwith[i] = 2;
                this.renderdata.yoff[i] = -0.1f;
            }
            if (i == 4) {
                this.renderdata.legoff[i] = 1.75f;
                this.renderdata.ymid[i] = 0.62831855f;
                this.renderdata.yrange[i] = 0.2617994f;
                this.renderdata.pairedwith[i] = 5;
                this.renderdata.yoff[i] = -0.3f;
            }
            if (i == 5) {
                this.renderdata.legoff[i] = 1.75f;
                this.renderdata.ymid[i] = 2.5132742f;
                this.renderdata.yrange[i] = -0.2617994f;
                this.renderdata.pairedwith[i] = 4;
                this.renderdata.yoff[i] = -0.3f;
            }
            if (i == 6) {
                this.renderdata.legoff[i] = 3.4f;
                this.renderdata.ymid[i] = 1.05f;
                this.renderdata.yrange[i] = 0.2617994f;
                this.renderdata.pairedwith[i] = 7;
                this.renderdata.yoff[i] = -0.1f;
            }
            if (i != 7) continue;
            this.renderdata.legoff[i] = 3.4f;
            this.renderdata.ymid[i] = 2.0915928f;
            this.renderdata.yrange[i] = -0.2617994f;
            this.renderdata.pairedwith[i] = 6;
            this.renderdata.yoff[i] = -0.1f;
        }
    }

    private float getNewVelocity(float v, float diff, float curval) {
        float tv = v;
        if ((tv *= 8.0f) < 1.0f) {
            tv = 1.0f;
        }
        if (tv > 4.0f) {
            tv = 4.0f;
        }
        if (diff > 0.0f) {
            if ((double)diff < 0.008726646259971648 * (double)tv) {
                curval = 0.0f;
            } else {
                curval = (float)((double)curval + 0.004363323129985824 * (double)tv);
                if ((double)diff < 0.06981317007977318 * (double)tv) {
                    curval = (float)(0.017453292519943295 * (double)tv);
                }
                if ((double)diff < 0.03490658503988659 * (double)tv) {
                    curval = (float)(0.008726646259971648 * (double)tv);
                }
                if ((double)curval > 0.06981317007977318 * (double)tv) {
                    curval = (float)(0.06981317007977318 * (double)tv);
                }
            }
        } else if ((double)diff > -0.008726646259971648 * (double)tv) {
            curval = 0.0f;
        } else {
            curval = (float)((double)curval - 0.004363323129985824 * (double)tv);
            if ((double)diff > -0.06981317007977318 * (double)tv) {
                curval = - (float)(0.017453292519943295 * (double)tv);
            }
            if ((double)diff > -0.03490658503988659 * (double)tv) {
                curval = - (float)(0.008726646259971648 * (double)tv);
            }
            if ((double)curval < -0.06981317007977318 * (double)tv) {
                curval = - (float)(0.06981317007977318 * (double)tv);
            }
        }
        return curval;
    }

    public void updateLegs() {
        if (!this.world.isRemote) {
            return;
        }
        this.rotationYaw %= 360.0f;
        while (this.rotationYaw < 0.0f) {
            this.rotationYaw += 360.0f;
        }
        ++this.renderdata.gpcounter;
        if (this.didonce == 0) {
            this.didonce = 1;
            this.initLegData();
        }
        float d1 = (float)(this.prevPosX - this.posX);
        float d2 = (float)(this.prevPosY - this.posY);
        float d3 = (float)(this.prevPosZ - this.posZ);
        float realv = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
        int i = 0;
        for (i = 0; i < 8; ++i) {
            double rdv;
            int fcount = 0;
            int[] arrn = this.renderdata.footingticker;
            int n = i;
            arrn[n] = arrn[n] + 1;
            this.renderdata.realposx[i] = (float)(this.posX - (double)this.renderdata.legoff[i] * Math.sin(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i]));
            this.renderdata.realposz[i] = (float)(this.posZ + (double)this.renderdata.legoff[i] * Math.cos(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i]));
            this.renderdata.realposy[i] = (float)this.posY + this.renderdata.yoff[i];
            int it = this.renderdata.footingticker[i] + this.renderdata.footingticker[this.renderdata.pairedwith[i]];
            if (it > 50 && this.renderdata.footingticker[i] > this.renderdata.footingticker[this.renderdata.pairedwith[i]]) {
                this.renderdata.footingticker[i] = 0;
            }
            d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
            d2 = this.renderdata.realposy[i] - this.renderdata.foot_ypos[i];
            d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
            float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            dd *= 16.0f;
            float da = (float)(Math.abs((double)this.renderdata.ycurrentangle[i] - (Math.toRadians(MathHelper.wrapDegrees((double)this.rotationYaw)) + (double)this.renderdata.ymid[i])) % 6.283185307179586);
            if ((double)da > 3.141592653589793) {
                da = (float)((double)da - 6.283185307179586);
            }
            if ((double)da < -3.141592653589793) {
                da = (float)((double)da + 6.283185307179586);
            }
            da = Math.abs(da);
            if (dd > 294.0f || dd < 32.0f || da > Math.abs(this.renderdata.yrange[i]) * 8.0f / 7.0f || (double)Math.abs(this.renderdata.udcurrentangle[i]) > 1.25 || this.renderdata.footingticker[i] == 0) {
                this.findNewFooting(i);
                d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
                d2 = this.renderdata.realposy[i] - this.renderdata.foot_ypos[i];
                d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
                dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                dd *= 16.0f;
            }
            float c1 = (float)(99.0 * Math.cos(this.renderdata.p2xangle[i] - this.renderdata.p1xangle[i]));
            float c2 = 99.0f;
            float c3 = (float)(99.0 * Math.cos(this.renderdata.p2xangle[i] - this.renderdata.p3xangle[i]));
            float cc = c1 + c2 + c3;
            float diff = cc - dd;
            this.renderdata.pxvelocity[i] = this.getNewVelocity(realv, (float)((double)diff * 3.141592653589793 / 360.0), this.renderdata.pxvelocity[i]);
            if (this.renderdata.pxvelocity[i] == 0.0f || Math.abs(diff) < 8.0f) {
                ++fcount;
            }
            double[] arrd = this.renderdata.p1xangle;
            int n2 = i;
            arrd[n2] = arrd[n2] + (double)this.renderdata.pxvelocity[i];
            this.renderdata.p2xangle[i] = 0.0;
            this.renderdata.p3xangle[i] = - this.renderdata.p1xangle[i];
            dd = this.renderdata.uppoint[i] != 0.0f ? (float)Math.atan2(dd, (double)(this.renderdata.realposy[i] - this.renderdata.uppoint[i]) * 16.0) : (float)Math.atan2(dd, (double)(this.renderdata.realposy[i] - this.renderdata.foot_ypos[i]) * 16.0);
            this.renderdata.udwantedangle[i] = (float)((double)dd - 1.5707963267948966);
            while ((double)this.renderdata.udwantedangle[i] > 3.141592653589793) {
                float[] arrf = this.renderdata.udwantedangle;
                int n3 = i;
                arrf[n3] = (float)((double)arrf[n3] - 6.283185307179586);
            }
            while ((double)this.renderdata.udwantedangle[i] < -3.141592653589793) {
                float[] arrf = this.renderdata.udwantedangle;
                int n4 = i;
                arrf[n4] = (float)((double)arrf[n4] + 6.283185307179586);
            }
            double rhm = this.renderdata.udwantedangle[i];
            double rhdir = this.renderdata.udcurrentangle[i];
            for (rdv = (rhm - rhdir) % 6.283185307179586; rdv > 3.141592653589793; rdv -= 6.283185307179586) {
            }
            while (rdv < -3.141592653589793) {
                rdv += 6.283185307179586;
            }
            diff = (float)rdv;
            this.renderdata.udvelocity[i] = this.getNewVelocity(realv * 2.0f, diff, this.renderdata.udvelocity[i]);
            if (this.renderdata.udvelocity[i] == 0.0f || (double)Math.abs(diff) < 0.03490658503988659) {
                this.renderdata.uppoint[i] = 0.0f;
                ++fcount;
            }
            rhdir += (double)this.renderdata.udvelocity[i];
            while (rhdir > 3.141592653589793) {
                rhdir -= 6.283185307179586;
            }
            while (rhdir < -3.141592653589793) {
                rhdir += 6.283185307179586;
            }
            this.renderdata.uddisplayangle[i] = dd = (this.renderdata.udcurrentangle[i] = (float)rhdir);
            d3 = this.renderdata.realposz[i] - this.renderdata.foot_zpos[i];
            d1 = this.renderdata.realposx[i] - this.renderdata.foot_xpos[i];
            dd = (float)Math.atan2(d3, d1);
            this.renderdata.ywantedangle[i] = dd;
            rhm = this.renderdata.ywantedangle[i];
            rdv = (rhm - (rhdir = (double)this.renderdata.ycurrentangle[i])) % 6.283185307179586;
            if (rdv > 3.141592653589793) {
                rdv -= 6.283185307179586;
            }
            if (rdv < -3.141592653589793) {
                rdv += 6.283185307179586;
            }
            diff = (float)rdv;
            this.renderdata.yvelocity[i] = this.getNewVelocity(realv, diff, this.renderdata.yvelocity[i]);
            if (this.renderdata.yvelocity[i] == 0.0f || (double)Math.abs(diff) < 0.03490658503988659) {
                ++fcount;
            }
            float[] arrf = this.renderdata.ycurrentangle;
            int n5 = i;
            arrf[n5] = arrf[n5] + this.renderdata.yvelocity[i];
            while ((double)this.renderdata.ycurrentangle[i] > 3.141592653589793) {
                float[] arrf2 = this.renderdata.ycurrentangle;
                int n6 = i;
                arrf2[n6] = (float)((double)arrf2[n6] - 6.283185307179586);
            }
            while ((double)this.renderdata.ycurrentangle[i] < -3.141592653589793) {
                float[] arrf3 = this.renderdata.ycurrentangle;
                int n7 = i;
                arrf3[n7] = (float)((double)arrf3[n7] + 6.283185307179586);
            }
            dd = (float)((double)this.renderdata.ycurrentangle[i] - Math.toRadians(MathHelper.wrapDegrees((double)this.rotationYaw)) - 1.5707963267948966);
            while ((double)dd > 3.141592653589793) {
                dd = (float)((double)dd - 6.283185307179586);
            }
            while ((double)dd < -3.141592653589793) {
                dd = (float)((double)dd + 6.283185307179586);
            }
            this.renderdata.ydisplayangle[i] = dd;
            if (fcount != 3) continue;
            this.renderdata.footup[i] = 0;
            Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.renderdata.foot_xpos[i], (int)this.renderdata.foot_ypos[i], (int)this.renderdata.foot_zpos[i])).getBlock();
            if (bid == Blocks.TALLGRASS && this.getControllingPassenger() != null && this.world.getGameRules().getBoolean("mobGriefing")) {
                this.world.setBlockState(new net.minecraft.util.math.BlockPos((int)this.renderdata.foot_xpos[i], (int)this.renderdata.foot_ypos[i], (int)this.renderdata.foot_zpos[i]), Blocks.AIR.getDefaultState());
            }
            if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.renderdata.foot_xpos[i], (int)this.renderdata.foot_ypos[i] - 1, (int)this.renderdata.foot_zpos[i])).getBlock()) != Blocks.GRASS || this.getControllingPassenger() == null || !this.world.getGameRules().getBoolean("mobGriefing")) continue;
            this.world.setBlockState(new net.minecraft.util.math.BlockPos((int)this.renderdata.foot_xpos[i], (int)this.renderdata.foot_ypos[i] - 1, (int)this.renderdata.foot_zpos[i]), Blocks.DIRT.getDefaultState());
        }
    }

    private void findNewFooting(int i) {
        float fx;
        float dd;
        float fz;
        float d2;
        float fy;
        float f = 16.0f;
        boolean found = false;
        float range = 0.0f;
        double rhdir = Math.toRadians((this.rotationYaw + 90.0f) % 360.0f);
        double pi = 3.1415926545;
        this.renderdata.footingticker[i] = 0;
        float d1 = (float)(this.posX - this.prevPosX);
        float d3 = (float)(this.posZ - this.prevPosZ);
        double rhm = Math.atan2(d3, d1);
        double velocity = Math.sqrt(d1 * d1 + d3 * d3);
        double rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
        if (rdv > pi) {
            rdv -= pi * 2.0;
        }
        rdv = Math.abs(rdv);
        if (Math.abs(velocity) < 0.01) {
            rdv = 0.0;
        }
        range = this.renderdata.yrange[i];
        range *= 0.875f;
        if (Math.abs((this.prevRotationYaw - this.rotationYaw) % 360.0f) > 0.75f) {
            range = 0.0f;
        }
        if (i >= 4) {
            f = 10.0f;
        }
        if (rdv > 1.5) {
            range = - range;
            f = 10.0f;
            if (i >= 4) {
                f = 16.0f;
            }
        }
        float deffx = fx = (float)((double)this.renderdata.realposx[i] - (double)(f / 2.0f) * Math.sin(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i]));
        float deffz = fz = (float)((double)this.renderdata.realposz[i] + (double)(f / 2.0f) * Math.cos(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i]));
        float deffy = fy = this.renderdata.realposy[i] - 1.0f;
        float oldf = f;
        int span = 1;
        while (!found && f > 3.5f) {
            fx = (float)((double)this.renderdata.realposx[i] - (double)f * Math.sin(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i] - (double)range));
            fz = (float)((double)this.renderdata.realposz[i] + (double)f * Math.cos(Math.toRadians(MathHelper.wrapDegrees((double)(this.rotationYaw + 90.0f))) + (double)this.renderdata.ymid[i] - (double)range));
            fy = this.renderdata.realposy[i];
            for (int j = 11; !found && j > -14; --j) {
                block2 : for (int m = - span; !found && m <= span; ++m) {
                    for (int n = - span; !found && n <= span; ++n) {
                        Block blk = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)fx + m, (int)fy + j, (int)fz + n)).getBlock();
                        if (blk == Blocks.AIR || !this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)fx + m, (int)fy + j, (int)fz + n)).getMaterial().isSolid()) continue;
                        fy += (float)(j + 1);
                        fx += (float)m;
                        fz += (float)n;
                        found = true;
                        continue block2;
                    }
                }
            }
            if (found) {
                d1 = this.renderdata.realposx[i] - fx;
                d2 = this.renderdata.realposy[i] - fy;
                d3 = this.renderdata.realposz[i] - fz;
                dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                if ((dd *= 16.0f) > 294.0f) {
                    found = false;
                }
            }
            if ((f -= 1.0f) >= 3.5f || range == 0.0f) continue;
            range = 0.0f;
            span = 3;
            f = oldf;
        }
        if (!found) {
            fx = deffx;
            fy = deffy;
            fz = deffz;
        }
        float sfx = this.renderdata.foot_xpos[i];
        float sfy = this.renderdata.foot_ypos[i];
        float sfz = this.renderdata.foot_zpos[i];
        this.renderdata.foot_xpos[i] = fx;
        this.renderdata.foot_ypos[i] = fy;
        this.renderdata.foot_zpos[i] = fz;
        if (this.renderdata.footup[i] == 0) {
            this.renderdata.footup[i] = 1;
            d1 = sfx - fx;
            d2 = sfy - fy;
            d3 = sfz - fz;
            dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            d1 = (sfy + fy) / 2.0f;
            if ((dd *= 16.0f) > 3.0f) {
                d1 += 1.0f;
            }
            if (dd > 48.0f) {
                d1 += 1.5f;
            }
            if (dd > 100.0f) {
                d1 += 1.5f;
            }
            this.renderdata.uppoint[i] = d1;
        }
    }

    public boolean shouldRiderSit() {
        return false;
    }

    public int getTrackingRange() {
        return 128;
    }

    public int getUpdateFrequency() {
        return 10;
    }

    public boolean sendsVelocityUpdates() {
        return true;
    }

    protected boolean canTriggerWalking() {
        return true;
    }

    protected void entityInit() {
        super.entityInit();
        this.enablePersistence();
        this.initLegData();
        this.getDataManager().register(ATTACKING, (byte)0);
    }

    public RenderSpiderRobotInfo getRenderSpiderRobotInfo() {
        return this.renderdata;
    }

    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBeSteered() {
        return true;
    }

    @Override
    public boolean canPassengerSteer() {
        return true;
    }

    @Override
    public boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public Entity getControllingPassenger() {
        if (this.getPassengers().isEmpty()) {
            return null;
        }
        return this.getPassengers().get(0);
    }

    private EntityPlayer getRiderPlayer() {
        Entity rider = this.getControllingPassenger();
        if (rider instanceof EntityPlayer) {
            return (EntityPlayer) rider;
        }
        if (rider != null && !rider.getPassengers().isEmpty() && rider.getPassengers().get(0) instanceof EntityPlayer) {
            return (EntityPlayer) rider.getPassengers().get(0);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayerSP getRiderPlayerClient() {
        EntityPlayer rider = this.getRiderPlayer();
        return rider instanceof EntityPlayerSP ? (EntityPlayerSP) rider : null;
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (!this.isBeingRidden()) {
            super.travel(strafe, vertical, forward);
            return;
        }
        Entity controller = this.getControllingPassenger();
        if (!(controller instanceof EntityLivingBase)) {
            super.travel(strafe, vertical, forward);
            return;
        }
        EntityLivingBase rider = (EntityLivingBase) controller;
        float moveStrafe = rider.moveStrafing;
        float moveForward = rider.moveForward;
        this.moveStrafing = moveStrafe;
        this.moveForward = moveForward;

        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double max_speed = 0.45;
        double relative_g = 0.0;
        double d4 = (double) rider.rotationYaw;
        d4 %= 360.0;
        while (d4 < 0.0) {
            d4 += 360.0;
        }
        double d5 = this.rotationYaw;
        d5 %= 360.0;
        while (d5 < 0.0) {
            d5 += 360.0;
        }
        for (relative_g = (d4 - d5) % 180.0; relative_g < 0.0; relative_g += 180.0) {
        }
        if (relative_g > 90.0) {
            relative_g -= 180.0;
        }
        if (velocity > 0.01) {
            d4 = 1.85 - velocity;
            if ((d4 = Math.abs(d4)) < 0.01) {
                d4 = 0.01;
            }
            if (d4 > 0.9) {
                d4 = 0.9;
            }
            this.rotationYaw = rider.rotationYaw + (float) (relative_g * d4);
        } else {
            this.rotationYaw = rider.rotationYaw;
        }
        relative_g = Math.abs(relative_g) * velocity;
        if (relative_g > 50.0) {
            relative_g = 0.0;
        }
        this.rotationPitch = 0.0f;
        this.setRotation(this.rotationYaw, this.rotationPitch);

        float forwardInput = moveForward;
        float strafeInput = moveStrafe;
        if (forwardInput < 0.0f) {
            max_speed = 0.25;
        }
        double yawRad = Math.toRadians(this.rotationYaw);
        double sin = Math.sin(yawRad);
        double cos = Math.cos(yawRad);
        double vx = (-sin * (double) forwardInput + cos * (double) strafeInput * 0.5) * max_speed;
        double vz = (cos * (double) forwardInput + sin * (double) strafeInput * 0.5) * max_speed;
        if (Math.abs(forwardInput) > 0.001f || Math.abs(strafeInput) > 0.001f) {
            this.motionX = vx;
            this.motionZ = vz;
        } else {
            this.motionX *= 0.85;
            this.motionZ *= 0.85;
        }
    }

    public double getMountedYOffset() {
        if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof SpiderDriver) {
            return 2.0;
        }
        return 2.625 + Math.cos((float)this.rideTicker * 0.19f) * 0.02;
    }

    public void updateRiderPosition() {
        if (this.getControllingPassenger() != null) {
            float f = -3.0f;
            f = (float)((double)f + Math.cos((float)this.rideTicker * 0.33f) * 0.05);
            this.getControllingPassenger().setPosition(this.posX - (double)f * Math.sin(Math.toRadians(this.rotationYaw)), this.posY + this.getMountedYOffset() + this.getControllingPassenger().getYOffset(), this.posZ + (double)f * Math.cos(Math.toRadians(this.rotationYaw)));
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            this.updateRiderPosition();
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("cactus")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("inFire")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("onFire")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("magic")) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("starve")) {
            return false;
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    public void fall(float distance, float damageMultiplier) {
    }

    protected void updateFallState(double y, boolean onGroundIn, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos) {
        fallDistance = 0.0f;
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @SideOnly(value=Side.CLIENT)
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.boatPosRotationIncrements = this.getControllingPassenger() != null ? posRotationIncrements + 8 : posRotationIncrements + 6;
        this.boatX = x;
        this.boatY = y;
        this.boatZ = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.boatPosRotationIncrements = this.getControllingPassenger() != null ? par9 + 8 : par9 + 6;
        this.boatX = par1;
        this.boatY = par3;
        this.boatZ = par5;
        this.boatYaw = par7;
        this.boatPitch = par8;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        if (this.getControllingPassenger() == null) {
            super.setVelocity(par1, par3, par5);
        }
    }

    public void onUpdate() {
        super.onUpdate();
        this.setFire(0);
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && !this.world.isRemote && this.getControllingPassenger() != null && this.world.rand.nextInt(40) == 0) {
            this.feetFindSomethingToHit();
        }
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL && !this.world.isRemote && this.getControllingPassenger() != null && this.world.rand.nextInt(15) == 0) {
            EntityLivingBase e = null;
            e = this.getAttackTarget();
            if (e != null && !e.isEntityAlive()) {
                this.setAttackTarget(null);
                e = null;
            }
            if (e == null) {
                e = this.findSomethingToAttack();
                if (e != null) {
                    this.setAttackTarget(e);
                }
            }
            if (e != null) {
                if (this.getDistanceSq((Entity)e) < (double)((12.0f + e.width / 2.0f) * (12.0f + e.width / 2.0f))) {
                    this.setAttacking(1);
                    this.attackEntityAsMob((Entity)e);
                }
            } else {
                this.setAttacking(0);
            }
        }
        float f = 8.0f;
        float dx = (float)((double)f * Math.cos(Math.toRadians(this.rotationYaw - 90.0f)));
        float dz = (float)((double)f * Math.sin(Math.toRadians(this.rotationYaw - 90.0f)));
        if (this.world.rand.nextInt(8) == 0) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, this.posX + (double)dx, this.posY + 2.0, this.posZ + (double)dz, (double)(dx / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0f), (double)(dz / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
        if (this.world.rand.nextInt(2) == 0) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, this.posX + (double)dx, this.posY + 2.0, this.posZ + (double)dz, (double)(dx / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 10.0f), (double)(dz / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
        if (this.world.rand.nextInt(10) == 0) {
            this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, this.posX + (double)dx, this.posY + 2.0, this.posZ + (double)dz, (double)(dx / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f), (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 5.0f), (double)(dz / f + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) / 20.0f));
        }
    }

    public void onLivingUpdate() {
        Object list = null;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d6 = this.rand.nextFloat() * 2.0f - 1.0f;
        double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.45;
        double gh = 1.55;
        int dist = 2;
        if (this.isDead) {
            return;
        }
        super.onLivingUpdate();
        if (this.motionY > 0.8500000238418579) {
            this.motionY = 0.8500000238418579;
        }
        if (this.motionY < -0.8500000238418579) {
            this.motionY = -0.8500000238418579;
        }
        if (this.motionX < -1.25) {
            this.motionX = -1.25;
        }
        if (this.motionX > 1.25) {
            this.motionX = 1.25;
        }
        if (this.motionZ < -1.25) {
            this.motionZ = -1.25;
        }
        if (this.motionZ > 1.25) {
            this.motionZ = 1.25;
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.rideTicker += this.world.rand.nextInt(3);
        if (this.playing > 0) {
            --this.playing;
        }
        if (this.getControllingPassenger() != null && this.playing == 0 && this.world.rand.nextInt(80) == 1) {
            this.world.playSound(null, this.posX, this.posY, this.posZ, com.astryxion.chaospersists.core.ChaosSounds.ROBOTSPIDER, this.getSoundCategory(), 0.45f, 1.0f);
            this.playing = 125;
        }
        if (this.world.isRemote) {
            if (this.getControllingPassenger() == null) {
                Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh + 1.0f), (int)this.posZ)).getBlock();
                if (bid == Blocks.AIR) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
                }
                if (bid != Blocks.AIR && bid != Blocks.WATER && bid != Blocks.FLOWING_WATER && bid != Blocks.LAVA && bid != Blocks.FLOWING_LAVA) {
                    this.motionY += 0.12;
                    this.posY += 0.12;
                    this.boatY += 0.12;
                } else {
                    this.motionY -= 0.002;
                }
            } else {
                EntityPlayerSP pp = this.getRiderPlayerClient();
                if (pp != null) {
                pp.connection.sendPacket(new CPacketPlayer.Rotation(pp.rotationYaw, pp.rotationPitch, pp.onGround));
                pp.connection.sendPacket(new CPacketInput(pp.moveStrafing, pp.moveForward, pp.movementInput.jump, pp.movementInput.sneak));
                }
            }
            if (this.boatPosRotationIncrements > 0) {
                double d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                double d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
                double d11 = this.posZ + (this.boatZ - this.posZ) / (double)this.boatPosRotationIncrements;
                this.setPosition(d4, d5, d11);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.boatPitch - (double)this.rotationPitch) / (double)this.boatPosRotationIncrements);
                double d10 = MathHelper.wrapDegrees((double)(this.boatYaw - (double)this.rotationYaw));
                if (this.getControllingPassenger() != null) {
                    d10 = MathHelper.wrapDegrees((double)((double)this.getControllingPassenger().rotationYaw - (double)this.rotationYaw));
                }
                this.rotationYaw = (float)((double)this.rotationYaw + d10 / (double)this.boatPosRotationIncrements);
                this.setRotation(this.rotationYaw, this.rotationPitch);
                --this.boatPosRotationIncrements;
            } else {
                double d4 = this.posX + this.motionX;
                double d5 = this.posY + this.motionY;
                double d11 = this.posZ + this.motionZ;
                this.setPosition(d4, d5, d11);
                this.motionX *= 0.99;
                this.motionY *= 0.95;
                this.motionZ *= 0.99;
            }
            this.updateLegs();
        } else {
            Block bid;
            if (this.getControllingPassenger() != null) {
                gh = 4.25;
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
                if (bid != Blocks.AIR && bid != Blocks.WATER && bid != Blocks.FLOWING_WATER && bid != Blocks.LAVA && bid != Blocks.FLOWING_LAVA) {
                    this.motionY += 0.06;
                    this.posY += 0.03;
                } else {
                    this.motionY -= 0.02;
                }
            } else {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh + 1.0f), (int)this.posZ)).getBlock();
                if (bid == Blocks.AIR) {
                    bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
                }
                if (bid != Blocks.AIR && bid != Blocks.WATER && bid != Blocks.FLOWING_WATER && bid != Blocks.LAVA && bid != Blocks.FLOWING_LAVA) {
                    this.motionY += 0.15;
                    this.posY += 0.15;
                    this.boatY += 0.15;
                } else {
                    this.motionY -= 0.002;
                }
            }
            EntityPlayer pp = this.getRiderPlayer();
            if (pp != null && pp.isSneaking()) {
                pp.dismountRidingEntity();
                return;
            }
            if (this.isBeingRidden()) {
                obstruction_factor = 0.0;
                int scanDepth = 3 + (int)(Math.max(0.0, velocity) * 6.0);
                if (scanDepth > 24) {
                    scanDepth = 24;
                }
                for (int k = 1; k < scanDepth; ++k) {
                    for (int i = 1; i < scanDepth * 3; ++i) {
                        for (int j = -90; j <= 90; j += 30) {
                            double dz;
                            double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f + (float)j));
                            bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f + (float)j)))))).getBlock();
                            if (bid == Blocks.AIR || bid == Blocks.WATER || bid == Blocks.FLOWING_WATER || bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) continue;
                            obstruction_factor += 0.03;
                        }
                    }
                }
                this.motionY += obstruction_factor * 0.05;
                this.posY += obstruction_factor * 0.05;
                this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.98;
                this.motionY *= 0.98;
                this.motionZ *= 0.98;
            } else {
                this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8;
                this.motionY *= 0.98;
                this.motionZ *= 0.8;
            }
            if (this.getControllingPassenger() != null && this.getControllingPassenger().isDead) {
                this.removePassengers();
            }
        }
    }

    public void goThisWay(double mx, double mz) {
        this.motionX = mx;
        this.motionZ = mz;
    }

    public boolean isAIEnabled() {
        if (this.getControllingPassenger() != null) {
            return false;
        }
        return true;
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }

    public float getShadowSize() {
        return 0.95f;
    }

    public boolean processInteract(EntityPlayer par1EntityPlayer, EnumHand hand) {
        return this.interact(par1EntityPlayer);
    }

    public boolean interact(EntityPlayer par1EntityPlayer) {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        if (var2 != null && var2.getCount() <= 0) {
            par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
            var2 = ItemStack.EMPTY;
        }
        if (var2 != null && var2.getItem() == Items.IRON_INGOT && par1EntityPlayer.getDistanceSq((Entity)this) < 25.0) {
            if (!this.world.isRemote) {
                float f = this.getMaxHealth() - this.getHealth();
                if (f > 100.0f) {
                    f = 100.0f;
                }
                if (f > 0.0f) {
                    this.heal(f);
                }
            }
            if (!par1EntityPlayer.capabilities.isCreativeMode) {
                var2.shrink(1);
                if (var2.isEmpty()) {
                    par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, ItemStack.EMPTY);
                }
            }
            return true;
        }
        if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger() != par1EntityPlayer) {
            return true;
        }
        if (!this.world.isRemote && this.getControllingPassenger() == null && par1EntityPlayer.getDistanceSq((Entity)this) < 16.0) {
            par1EntityPlayer.startRiding((Entity)this);
            this.world.playSound(null, this.posX, this.posY, this.posZ, com.astryxion.chaospersists.core.ChaosSounds.ROBOTSPIDERMOUNT, this.getSoundCategory(), 0.65f, 1.0f);
        }
        return true;
    }

    private void feetFindSomethingToHit() {
        if (ChaosPersists.PlayNicely != 0) {
            return;
        }
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(20.0, 8.0, 20.0));
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (!this.feetisSuitableTarget(var4, false)) continue;
            this.feetattackEntityAsMob((Entity)var4);
        }
    }

    private boolean feetisSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == this) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (par1EntityLiving instanceof SpiderRobot) {
            return false;
        }
        if (par1EntityLiving instanceof EntitySpider) {
            return false;
        }
        if (par1EntityLiving instanceof SpiderDriver) {
            return false;
        }
        if (par1EntityLiving instanceof EntityCaveSpider) {
            return false;
        }
        if (par1EntityLiving == this.getControllingPassenger()) {
            return false;
        }
        float d1 = (float)(par1EntityLiving.posX - this.posX);
        float d2 = (float)(par1EntityLiving.posY - this.posY);
        float d3 = (float)(par1EntityLiving.posZ - this.posZ);
        float dd = (float)Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
        if (dd > 18.0f) {
            return false;
        }
        if (dd < 12.0f) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
            return true;
        }
        return true;
    }

    public boolean feetattackEntityAsMob(Entity par1Entity) {
        boolean ret = false;
        if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
            double ks = 0.6;
            double inair = 0.1;
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)ChaosPersists.SpiderRobot_stats.attack / 10.0f);
            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                inair *= 2.0;
            }
            if (ret) {
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }
        return ret;
    }

    private EntityLivingBase findSomethingToAttack() {
        if (ChaosPersists.PlayNicely != 0) {
            return null;
        }
        List var5 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(20.0, 12.0, 20.0));
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
        if (par1EntityLiving instanceof SpiderRobot) {
            return false;
        }
        if (par1EntityLiving instanceof EntitySpider) {
            return false;
        }
        if (par1EntityLiving instanceof SpiderDriver) {
            return false;
        }
        if (par1EntityLiving instanceof EntityCaveSpider) {
            return false;
        }
        if (par1EntityLiving == this.getControllingPassenger()) {
            return false;
        }
        if (MyUtils.isIgnoreable((EntityLivingBase)par1EntityLiving)) {
            return false;
        }
        if (!this.getEntitySenses().canSee((Entity)par1EntityLiving)) {
            return false;
        }
        double rr = Math.atan2(par1EntityLiving.posZ - this.posZ, par1EntityLiving.posX - this.posX);
        double rhdir = Math.toRadians((this.rotationYaw + 90.0f) % 360.0f);
        double pi = 3.1415926545;
        double rdd = Math.abs(rr - rhdir) % (pi * 2.0);
        if (rdd > pi) {
            rdd -= pi * 2.0;
        }
        rdd = Math.abs(rdd);
        if (this.getDistanceSq((Entity)par1EntityLiving) < 36.0) {
            return true;
        }
        if (rdd > 0.75) {
            return false;
        }
        if (par1EntityLiving instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer)par1EntityLiving;
            if (p.capabilities.isCreativeMode) {
                return false;
            }
            return true;
        }
        return true;
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        boolean ret = false;
        if (par1Entity != null && par1Entity instanceof EntityLivingBase) {
            double ks = 1.2;
            double inair = 0.15;
            float f3 = (float)Math.atan2(par1Entity.posZ - this.posZ, par1Entity.posX - this.posX);
            ret = par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)ChaosPersists.SpiderRobot_stats.attack);
            if (par1Entity.isDead || par1Entity instanceof EntityPlayer) {
                inair *= 2.0;
            }
            if (ret) {
                par1Entity.addVelocity(Math.cos(f3) * ks, inair, Math.sin(f3) * ks);
            }
        }
        return ret;
    }

    public int getAttacking() {
        return this.getDataManager().get(ATTACKING).intValue();
    }

    public void setAttacking(int par1) {
        this.getDataManager().set(ATTACKING, (byte)par1);
    }

    protected Item getDropItem() {
        return null;
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
        ItemStack is = null;
        int i = 14 + this.world.rand.nextInt(14);
        block12 : for (int var4 = 0; var4 < i; ++var4) {
            int var3 = this.world.rand.nextInt(15);
            switch (var3) {
                case 0: {
                    is = this.dropItemRand(Items.REDSTONE, 1);
                    continue block12;
                }
                case 1: {
                    is = this.dropItemRand(Items.REPEATER, 1);
                    continue block12;
                }
                case 2: {
                    is = this.dropItemRand(Items.COMPARATOR, 1);
                    continue block12;
                }
                case 3: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.REDSTONE_BLOCK), 1);
                    continue block12;
                }
                case 4: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.DISPENSER), 1);
                    continue block12;
                }
                case 5: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.STICKY_PISTON), 1);
                    continue block12;
                }
                case 6: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.PISTON), 1);
                    continue block12;
                }
                case 7: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.LEVER), 1);
                    continue block12;
                }
                case 8: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.REDSTONE_BLOCK), 1);
                    continue block12;
                }
                case 9: {
                    is = this.dropItemRand(Item.getItemFromBlock((Block)Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), 1);
                    break;
                }
            }
        }
    }
}

