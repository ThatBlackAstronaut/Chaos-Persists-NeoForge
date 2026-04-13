/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.Elevator
 *  com.astryxion.chaospersists.Girlfriend
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockGrass
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.client.entity.EntityClientPlayerMP
 *  net.minecraft.client.network.NetHandlerPlayClient
 *  net.minecraft.entity.DataWatcher
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.SharedMonsterAttributes
 *  net.minecraft.entity.ai.attributes.BaseAttributeMap
 *  net.minecraft.entity.ai.attributes.IAttribute
 *  net.minecraft.entity.ai.attributes.IAttributeInstance
 *  net.minecraft.entity.item.EntityItem
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
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.GameRules
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.SoundEvents;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class Elevator
extends EntityLiving {
    private static final DataParameter<Integer> I20 = EntityDataManager.createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> I21 = EntityDataManager.createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> I22 = EntityDataManager.createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> I23 = EntityDataManager.createKey(Elevator.class, DataSerializers.VARINT);
    private static final DataParameter<Float> F24 = EntityDataManager.createKey(Elevator.class, DataSerializers.FLOAT);
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private int damage_counter = 100;
    private int exploding = 0;
    private int color = 1;
    private int playing = 0;
    private static final ResourceLocation texture1 = new ResourceLocation("chaospersists", "textures/entity/elevator1.png");
    private static final ResourceLocation texture2 = new ResourceLocation("chaospersists", "textures/entity/elevator2.png");
    private static final ResourceLocation texture3 = new ResourceLocation("chaospersists", "textures/entity/elevator3.png");
    private static final ResourceLocation texture4 = new ResourceLocation("chaospersists", "textures/entity/elevator4.png");
    private static final ResourceLocation texture5 = new ResourceLocation("chaospersists", "textures/entity/elevator5.png");
    private static final ResourceLocation texture6 = new ResourceLocation("chaospersists", "textures/entity/elevator6.png");
    private static final ResourceLocation texture7 = new ResourceLocation("chaospersists", "textures/entity/elevator7.png");
    private static final ResourceLocation texture8 = new ResourceLocation("chaospersists", "textures/entity/elevator8.png");
    private static final ResourceLocation texture9 = new ResourceLocation("chaospersists", "textures/entity/elevator9.png");
    private static final ResourceLocation texture10 = new ResourceLocation("chaospersists", "textures/entity/elevator10.png");

    public Elevator(World par1World) {
        super(par1World);
        this.setSize(1.25f, 1.0f);
        this.setNoGravity(true);
        // No pathfinding / AI tasks fighting custom hover movement (1.12.2 EntityLiving still ticks AI unless disabled).
        this.setNoAI(true);
    }

    public Elevator(World par1World, double par2, double par4, double par6) {
        this(par1World);
        this.setPosition(par2, par4 + (double)this.getYOffset(), par6);
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    public ResourceLocation getTexture() {
        switch (this.getColor()) {
            case 1: {
                return texture1;
            }
            case 2: {
                return texture2;
            }
            case 3: {
                return texture3;
            }
            case 4: {
                return texture4;
            }
            case 5: {
                return texture5;
            }
            case 6: {
                return texture6;
            }
            case 7: {
                return texture7;
            }
            case 8: {
                return texture8;
            }
            case 9: {
                return texture9;
            }
            case 10: {
                return texture10;
            }
        }
        return texture1;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.3300000429153442);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0);
    }

    protected boolean canDespawn() {
        return false;
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

    protected void fall(float par1) {
    }

    protected void updateFallState(double par1, boolean par3) {
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(I22, 0);
        this.getDataManager().register(I23, 1);
        this.getDataManager().register(F24, Float.valueOf(0.0f));
        this.getDataManager().register(I20, 0);
        this.getDataManager().register(I21, 0);
        this.enablePersistence();
    }

    public boolean canBePushed() {
        return true;
    }

    public double getMountedYOffset() {
        return 0.5;
    }

    @Override
    public boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public boolean canPassengerSteer() {
        return true;
    }

    /**
     * Vanilla requires this for player movement input ({@code moveForward} / {@code moveStrafing}) to apply while riding
     * a living entity; otherwise the client/server treat the mount like a non-steerable mob.
     */
    @Override
    public boolean canBeSteered() {
        return true;
    }

    @Override
    public Entity getControllingPassenger() {
        if (this.getPassengers().isEmpty()) {
            return null;
        }
        Entity passenger = this.getPassengers().get(0);
        return passenger instanceof EntityLivingBase ? passenger : null;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            passenger.setPosition(this.posX, this.posY + this.getMountedYOffset() + passenger.getYOffset(), this.posZ);
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        boolean p = par1DamageSource.getTrueSource() instanceof EntityPlayer;
        if (this.getControllingPassenger() != null && !p) {
            return false;
        }
        if (par1DamageSource.getDamageType().equals("inWall")) {
            return false;
        }
        if (!this.world.isRemote && !this.isDead) {
            boolean flag;
            this.setForwardDirection(- this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + par2 * 10.0f);
            boolean bl = flag = par1DamageSource.getTrueSource() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getTrueSource()).capabilities.isCreativeMode;
            if (flag || this.getDamageTaken() > 40.0f) {
                if (this.getControllingPassenger() != null) {
                    this.removePassengers();
                }
                if (!flag) {
                    this.dropItem(ChaosPersists.MyElevator, 1);
                }
                this.setDead();
            }
            return true;
        }
        return true;
    }

    @SideOnly(value=Side.CLIENT)
    public void performHurtAnimation() {
        this.setForwardDirection(- this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    /**
     * 1.12.2 uses {@link Entity#setPositionAndRotationDirect} for server→client motion sync; 1.7's
     * {@code setPositionAndRotation2} is not invoked by the network layer anymore.
     */
    @SideOnly(value = Side.CLIENT)
    @Override
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.boatPosRotationIncrements = this.getControllingPassenger() != null ? posRotationIncrements + 8 : 6;
        this.boatX = x;
        this.boatY = y;
        this.boatZ = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        this.velocityX = this.motionX = par1;
        this.velocityY = this.motionY = par3;
        this.velocityZ = this.motionZ = par5;
    }

    /**
     * Vanilla copies the rider's {@code moveForward} / {@code moveStrafing} onto the mount inside
     * {@link EntityLivingBase#onLivingUpdate()} before {@link #travel(float, float, float)}. Skipping
     * {@code super} while ridden (old 1.7 port) left those fields at 0 on the server, so WASD did nothing.
     * We suppress default {@code travel} motion and still apply hover physics below.
     */
    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.getControllingPassenger() != null) {
            return;
        }
        super.travel(strafe, vertical, forward);
    }

    @Override
    public void onLivingUpdate() {
        if (this.isDead) {
            return;
        }
        super.onLivingUpdate();
        int k;
        double d5;
        Block bid;
        int i;
        double d4;
        List list = null;
        double velocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        double d6 = this.rand.nextFloat() * 2.0f - 1.0f;
        double d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.85;
        double gh = 0.75;
        int dist = 2;
        this.isAirBorne = true;
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.playing > 0) {
            --this.playing;
        }
        if (this.getControllingPassenger() != null && this.playing == 0 && this.world.rand.nextInt(80) == 1) {
            this.world.playSound(null, this.posX, this.posY, this.posZ, com.astryxion.chaospersists.core.ChaosSounds.HOVER, net.minecraft.util.SoundCategory.NEUTRAL, 0.45f, 1.0f);
            this.playing = 55;
        }
        if (!this.world.isRemote) {
            if (this.exploding > 0) {
                --this.exploding;
            }
            if (this.exploding == 0 && velocity > 0.65 && this.world.rand.nextInt(20000) == 1) {
                this.exploding = 45;
                this.playing = 50;
            }
            this.setExploding(this.exploding);
        } else {
            this.exploding = this.getExploding();
        }
        if (this.getExploding() > 0 && this.getControllingPassenger() != null) {
            if (this.world.rand.nextInt(10) == 1) {
                this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, net.minecraft.util.SoundCategory.NEUTRAL, 0.55f, 0.75f + this.world.rand.nextFloat());
            }
            for (i = 0; i < 15; ++i) {
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_NORMAL, (double)((int)(this.posX + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0f))), (double)((int)(this.posY + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0f))), (double)((int)(this.posZ + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 4.0f))), this.motionX, 0.0, this.motionZ);
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.EXPLOSION_LARGE, (double)((int)(this.posX + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0f))), (double)((int)(this.posY + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0f))), (double)((int)(this.posZ + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 2.0f))), this.motionX, 0.0, this.motionZ);
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, (double)((int)(this.posX + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0f))), (double)((int)(this.posY + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0f))), (double)((int)(this.posZ + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 5.0f))), this.motionX, 0.0, this.motionZ);
                this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_LARGE, (double)((int)(this.posX + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0f))), (double)((int)(this.posY + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0f))), (double)((int)(this.posZ + (double)((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 3.0f))), this.motionX, 0.0, this.motionZ);
            }
        }
        if (this.world.isRemote) {
            if (this.getControllingPassenger() == null) {
                bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock();
                if (bid != Blocks.AIR) {
                    this.motionY += 0.06;
                    this.posY += 0.07;
                    this.boatY += 0.07;
                } else {
                    this.motionY -= 0.003;
                }
            }

            // Mirror 1.7.10-style input sync: ensure the server sees WASD while riding.
            if (this.getControllingPassenger() instanceof EntityPlayerSP) {
                EntityPlayerSP pp = (EntityPlayerSP)this.getControllingPassenger();
                pp.connection.sendPacket(new CPacketPlayer.Rotation(pp.rotationYaw, pp.rotationPitch, pp.onGround));
                pp.connection.sendPacket(new CPacketInput(pp.moveStrafing, pp.moveForward, pp.movementInput.jump, pp.movementInput.sneak));
            }
            if (this.boatPosRotationIncrements > 0) {
                d4 = this.posX + (this.boatX - this.posX) / (double)this.boatPosRotationIncrements;
                d5 = this.posY + (this.boatY - this.posY) / (double)this.boatPosRotationIncrements;
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
                d4 = this.posX + this.motionX;
                d5 = this.posY + this.motionY;
                double d11 = this.posZ + this.motionZ;
                this.setPosition(d4, d5, d11);
                this.motionX *= 0.99;
                this.motionY *= 0.95;
                this.motionZ *= 0.99;
            }
        } else {
            if (this.getControllingPassenger() != null) {
                gh = 1.25;
            }
            if ((bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)((float)this.posY - (float)gh), (int)this.posZ)).getBlock()) != Blocks.AIR) {
                this.motionY += 0.06;
                this.posY += 0.1;
                if (bid == Blocks.TALLGRASS && this.getControllingPassenger() != null && this.world.rand.nextInt(200) == 1 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    this.world.setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)(this.posY - gh), (int)this.posZ), Blocks.AIR.getDefaultState());
                }
                if (bid == Blocks.GRASS && this.getControllingPassenger() != null && this.world.rand.nextInt(200) == 1 && this.world.getGameRules().getBoolean("mobGriefing")) {
                    this.world.setBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)(this.posY - gh), (int)this.posZ), Blocks.DIRT.getDefaultState());
                }
            } else {
                this.motionY -= 0.01;
            }
            if (this.getControllingPassenger() != null) {
                double rdv;
                EntityPlayer pp = (EntityPlayer)this.getControllingPassenger();
                // Let Shift always dismount immediately while riding.
                if (pp.isSneaking()) {
                    pp.dismountRidingEntity();
                    return;
                }
                obstruction_factor = 0.0;
                // IMPORTANT: do not mutate loop bounds inside the condition.
                // Old logic used: k < (dist += velocity*8), which can grow faster than k and stall the game.
                int scanDepth = 3 + (int)(Math.max(0.0, velocity) * 8.0);
                if (scanDepth > 24) {
                    scanDepth = 24;
                }
                for (k = 1; k < scanDepth; ++k) {
                    for (i = 1; i < scanDepth * 2; ++i) {
                        double dz;
                        double dx = (double)i * Math.cos(Math.toRadians(this.rotationYaw + 90.0f));
                        bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)(this.posX + dx), (int)this.posY - k, (int)(this.posZ + (dz = (double)i * Math.sin(Math.toRadians(this.rotationYaw + 90.0f)))))).getBlock();
                        if (bid == Blocks.AIR) continue;
                        obstruction_factor += 0.05;
                    }
                }
                this.motionY += obstruction_factor * 0.11;
                this.posY += obstruction_factor * 0.11;
                d4 = this.getControllingPassenger().rotationYaw;
                d4 %= 360.0;
                while (d4 < 0.0) {
                    d4 += 360.0;
                }
                d5 = this.rotationYaw;
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
                    this.rotationYaw = this.getControllingPassenger().rotationYaw + (float)(relative_g * d4);
                } else {
                    this.rotationYaw = this.getControllingPassenger().rotationYaw;
                }
                relative_g = Math.abs(relative_g) * velocity;
                if (relative_g > 50.0) {
                    relative_g = 0.0;
                }
                this.rotationPitch = 10.0f * (float)velocity;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                double newvelocity = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
                if (this.exploding != 0 && (newvelocity -= 0.05) < 0.0) {
                    newvelocity = 0.0;
                }
                double rr = Math.atan2(this.getControllingPassenger().motionZ, this.getControllingPassenger().motionX);
                double rhm = Math.atan2(this.motionZ, this.motionX);
                double rhdir = Math.toRadians((this.getControllingPassenger().rotationYaw + 90.0f) % 360.0f);
                double rt = 0.0;
                double pi = 3.1415926545;
                double deltav = 0.0;
                // Prefer mounted input copied onto this entity by vanilla riding flow.
                // Fallback to player input field for compatibility with prior packet sync behavior.
                float im = Math.abs(this.moveForward) > 0.001f ? this.moveForward : pp.moveForward;
                if (ChaosPersists.flyup_keystate != 0) {
                    max_speed += 1.0;
                }
                if ((rdv = Math.abs(rhm - rhdir) % (pi * 2.0)) > pi) {
                    rdv -= pi * 2.0;
                }
                rdv = Math.abs(rdv);
                if (Math.abs(newvelocity) < 0.01) {
                    rdv = 0.0;
                }
                if (rdv > 1.5) {
                    newvelocity = - newvelocity;
                }
                if (Math.abs(im) > 0.001f) {
                    if (im > 0.0f) {
                        deltav = 0.025;
                        if (max_speed > 1.0) {
                            deltav += 0.15;
                        }
                    } else {
                        max_speed = 0.35;
                        deltav = -0.02;
                    }
                    if ((newvelocity += deltav) >= 0.0) {
                        if (newvelocity > max_speed) {
                            newvelocity = max_speed;
                        }
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                    } else {
                        if (newvelocity < - max_speed) {
                            newvelocity = - max_speed;
                        }
                        newvelocity = - newvelocity;
                        this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                        this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * newvelocity;
                    }
                } else if (newvelocity >= 0.0) {
                    this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                    this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 90.0f)) * newvelocity;
                } else {
                    this.motionX = Math.cos(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                    this.motionZ = Math.sin(Math.toRadians(this.rotationYaw + 270.0f)) * (newvelocity * -1.0);
                }
            } else if (this.getControllingPassenger() == null) {
                this.motionX = 0.0;
                this.motionZ = 0.0;
            }
            this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            if (this.collidedHorizontally && velocity > 0.75) {
                this.setDead();
                int p = this.world.rand.nextInt(10);
                for (k = 0; k < 6 + p; ++k) {
                    this.dropItem(Items.STICK, 1);
                }
                for (k = 0; k < 2; ++k) {
                    this.dropItem(Items.DIAMOND, 1);
                }
            } else {
                this.motionX *= 0.98;
                this.motionY *= 0.94;
                this.motionZ *= 0.98;
            }
            if ((list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.getEntityBoundingBox().expand(0.25, 0.0, 0.25))) != null && !list.isEmpty()) {
                for (int l = 0; l < list.size(); ++l) {
                    Entity entity = (Entity)list.get(l);
                    if (entity == this.getControllingPassenger() || !entity.canBePushed() || entity instanceof Girlfriend || entity instanceof Boyfriend) continue;
                    entity.applyEntityCollision((Entity)this);
                }
            }
            if (this.getControllingPassenger() != null && this.getControllingPassenger().isDead) {
                this.removePassengers();
            }
        }
        // Motion is often ~0 on the client while position still updates (interpolation / packets). Use displacement for trail FX.
        double horizSpeed = Math.sqrt(
                (this.posX - this.prevPosX) * (this.posX - this.prevPosX)
                + (this.posZ - this.prevPosZ) * (this.posZ - this.prevPosZ));
        if (horizSpeed > 0.15 && this.getControllingPassenger() != null) {
            d4 = Math.cos(Math.toRadians(this.rotationYaw + 270.0f));
            d5 = Math.sin(Math.toRadians(this.rotationYaw + 270.0f));
            bid = Blocks.AIR;
            for (i = 1; i < 10 && (bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY - i, (int)this.posZ)).getBlock()) == Blocks.AIR; ++i) {
            }
            int j = 0;
            while ((double)j < 1.0 + horizSpeed * 10.0) {
                double d9;
                double d8;
                d6 = this.rand.nextFloat() * 2.0f - 1.0f;
                d7 = (double)(this.rand.nextInt(2) * 2 - 1) * 0.7;
                if (this.rand.nextBoolean()) {
                    d8 = this.posX - d4 * d6 * 0.8 + d5 * d7;
                    d9 = this.posZ - d5 * d6 * 0.8 - d4 * d7;
                    if (this.rand.nextBoolean()) {
                        this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, d8, this.posY - 0.25, d9, this.motionX, this.motionY, this.motionZ);
                    } else {
                        this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, d8, this.posY - 0.25, d9, this.motionX, this.motionY, this.motionZ);
                    }
                } else {
                    d8 = this.posX + d4 + d5 * d6 * 0.7;
                    d9 = this.posZ + d5 - d4 * d6 * 0.7;
                    if (this.rand.nextBoolean()) {
                        this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, d8, this.posY - 0.225, d9, this.motionX, this.motionY, this.motionZ);
                    } else {
                        this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.REDSTONE, d8, this.posY - 0.225, d9, this.motionX, this.motionY, this.motionZ);
                    }
                }
                if (bid == Blocks.WATER || bid == Blocks.FLOWING_WATER) {
                    for (k = 0; k < 5; ++k) {
                        this.world.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_SPLASH, this.posX + (double)this.rand.nextFloat(), this.posY - (double)i + 1.25, this.posZ + (double)this.rand.nextFloat(), this.motionX / 2.0, this.motionY + horizSpeed, this.motionZ / 2.0);
                    }
                }
                ++j;
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.setFire(0);
    }

    public void updateRiderPosition() {
        if (this.getControllingPassenger() != null) {
            this.getControllingPassenger().setPosition(this.posX, this.posY + this.getMountedYOffset() + this.getControllingPassenger().getYOffset(), this.posZ);
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setInteger("HoverColor", this.getColor());
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.color = par1NBTTagCompound.getInteger("HoverColor");
        if (this.color < 1) {
            this.color = 1;
        }
        if (this.color > 10) {
            this.color = 10;
        }
        this.setColor(this.color);
    }

    public float getShadowSize() {
        return 0.25f;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.getCount() <= 0) {
            player.setHeldItem(hand, ItemStack.EMPTY);
            stack = ItemStack.EMPTY;
        }
        if (!stack.isEmpty() && stack.getItem() == ChaosPersists.MyUltimateSword && player.getDistanceSq(this) < 16.0) {
            if (!this.world.isRemote) {
                int c = this.getColor() + 1;
                if (c > 10) {
                    c = 1;
                }
                this.setColor(c);
            }
            return true;
        }
        if (this.getControllingPassenger() != null && this.getControllingPassenger() instanceof EntityPlayer && this.getControllingPassenger() != player) {
            return true;
        }
        if (!this.world.isRemote) {
            player.startRiding(this);
        }
        return true;
    }

    public void setDamageTaken(float f) {
        this.getDataManager().set(F24, Float.valueOf(f));
    }

    public float getDamageTaken() {
        return this.getDataManager().get(F24).floatValue();
    }

    public void setTimeSinceHit(int par1) {
        this.getDataManager().set(I22, par1);
    }

    public int getTimeSinceHit() {
        return this.getDataManager().get(I22).intValue();
    }

    public void setForwardDirection(int par1) {
        this.getDataManager().set(I23, par1);
    }

    public int getForwardDirection() {
        return this.getDataManager().get(I23).intValue();
    }

    public void setExploding(int par1) {
        this.getDataManager().set(I20, par1);
    }

    public int getExploding() {
        return this.getDataManager().get(I20).intValue();
    }

    public void setColor(int par1) {
        this.getDataManager().set(I21, par1);
    }

    public int getColor() {
        return this.getDataManager().get(I21).intValue();
    }
}

