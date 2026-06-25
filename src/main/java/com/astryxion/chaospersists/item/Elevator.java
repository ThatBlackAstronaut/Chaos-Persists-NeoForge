package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.core.ChaosSounds;
import java.util.List;
import org.joml.Vector3f;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MoverType;
import java.util.Collections;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Elevator extends Mob {
    private static final EntityDataAccessor<Integer> I20 =
            SynchedEntityData.defineId(Elevator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> I21 =
            SynchedEntityData.defineId(Elevator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> I22 =
            SynchedEntityData.defineId(Elevator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> I23 =
            SynchedEntityData.defineId(Elevator.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> F24 =
            SynchedEntityData.defineId(Elevator.class, EntityDataSerializers.FLOAT);

    private static final ResourceLocation TEXTURE1 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator1.png");
    private static final ResourceLocation TEXTURE2 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator2.png");
    private static final ResourceLocation TEXTURE3 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator3.png");
    private static final ResourceLocation TEXTURE4 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator4.png");
    private static final ResourceLocation TEXTURE5 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator5.png");
    private static final ResourceLocation TEXTURE6 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator6.png");
    private static final ResourceLocation TEXTURE7 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator7.png");
    private static final ResourceLocation TEXTURE8 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator8.png");
    private static final ResourceLocation TEXTURE9 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator9.png");
    private static final ResourceLocation TEXTURE10 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/elevator10.png");

    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private int exploding = 0;
    private int color = 1;
    private int playing = 0;

    public Elevator(EntityType<? extends Elevator> type, Level level) {
        super(type, level);
        this.setNoGravity(true);
        this.setNoAi(true);
        this.setPersistenceRequired();
    }

    public Elevator(Level level, double x, double y, double z) {
        this(ChaosPersists.ENTITY_TYPE_ELEVATOR.get(), level);
        this.moveTo(x, y + this.getMyRidingOffset(), z, 0.0f, 0.0f);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public static AttributeSupplier.Builder createAttributes() {
        // Mob.createNavigation() requires FOLLOW_RANGE during construction.
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.MOVEMENT_SPEED, 1.3300000429153442)
                .add(Attributes.ATTACK_DAMAGE, 0.0);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {}

    public ResourceLocation getTexture() {
        return switch (this.getColor()) {
            case 2 -> TEXTURE2;
            case 3 -> TEXTURE3;
            case 4 -> TEXTURE4;
            case 5 -> TEXTURE5;
            case 6 -> TEXTURE6;
            case 7 -> TEXTURE7;
            case 8 -> TEXTURE8;
            case 9 -> TEXTURE9;
            case 10 -> TEXTURE10;
            default -> TEXTURE1;
        };
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(I22, 0);
        this.entityData.define(I23, 1);
        this.entityData.define(F24, 0.0f);
        this.entityData.define(I20, 0);
        this.entityData.define(I21, 1);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.5;
    }

    @Override
    public boolean canAddPassenger(Entity passenger) {
        return this.getPassengers().isEmpty();
    }

    @Override
    public LivingEntity getControllingPassenger() {
        Entity passenger = this.getFirstPassenger();
        return passenger instanceof LivingEntity living ? living : null;
    }

    private Player getRiderPlayer() {
        Entity rider = this.getControllingPassenger();
        if (rider instanceof Player player) {
            return player;
        }
        if (rider != null
                && !rider.getPassengers().isEmpty()
                && rider.getPassengers().get(0) instanceof Player player) {
            return player;
        }
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    private LocalPlayer getRiderPlayerClient() {
        Player rider = this.getRiderPlayer();
        return rider instanceof LocalPlayer local ? local : null;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        if (this.hasPassenger(passenger)) {
            moveFunction.accept(
                    passenger,
                    this.getX(),
                    this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset(),
                    this.getZ());
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        // When mounted, we handle movement in `tick()`; still copy rider input so
        // `this.zza/this.xxa` reflect the player's controls (needed by the tick logic).
        Player rider = this.getRiderPlayer();
        if (rider != null) {
            this.xxa = rider.xxa;
            this.zza = rider.zza;
            return;
        }
        super.travel(travelVector);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean p = source.getEntity() instanceof Player;
        if (this.getControllingPassenger() != null && !p) {
            return false;
        }
        if (source.getMsgId().equals("inWall")) {
            return false;
        }
        if (!this.level().isClientSide && !this.isRemoved()) {
            this.setForwardDirection(-this.getForwardDirection());
            this.setTimeSinceHit(10);
            this.setDamageTaken(this.getDamageTaken() + amount * 10.0f);
            boolean creative =
                    source.getEntity() instanceof Player player && player.getAbilities().instabuild;
            if (creative || this.getDamageTaken() > 40.0f) {
                if (this.getControllingPassenger() != null) {
                    this.ejectPassengers();
                }
                if (!creative && ChaosPersists.MyElevator != null) {
                    this.spawnAtLocation(ChaosPersists.MyElevator);
                }
                this.discard();
            }
            return true;
        }
        return true;
    }

    @Override
    public void animateHurt(float yaw) {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0f);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int steps, boolean interpolate) {
        this.boatPosRotationIncrements = this.getControllingPassenger() != null ? steps + 8 : 6;
        this.boatX = x;
        this.boatY = y;
        this.boatZ = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
        Vec3 m = this.getDeltaMovement();
        this.velocityX = m.x;
        this.velocityY = m.y;
        this.velocityZ = m.z;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void lerpMotion(double x, double y, double z) {
        this.velocityX = x;
        this.velocityY = y;
        this.velocityZ = z;
        this.setDeltaMovement(x, y, z);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {}

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void updateWalkAnimation(float partialTick) {}

    @Override
    public void tick() {
        if (this.isRemoved()) {
            return;
        }
        super.tick();
        int k;
        double d5;
        Block bid;
        int i;
        double d4;
        double d6;
        double d7;
        List<Entity> list = null;
        Vec3 motion = this.getDeltaMovement();
        double mx = motion.x;
        double my = motion.y;
        double mz = motion.z;
        double velocity = Math.sqrt(mx * mx + mz * mz);
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.85;
        double gh = 0.75;
        this.setNoGravity(true);
        if (this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0f) {
            this.setDamageTaken(this.getDamageTaken() - 1.0f);
        }
        if (this.playing > 0) {
            --this.playing;
        }
        if (this.getControllingPassenger() != null
                && this.playing == 0
                && this.getRandom().nextInt(80) == 1) {
            this.level()
                    .playSound(
                            null,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            ChaosSounds.HOVER,
                            SoundSource.NEUTRAL,
                            0.45f,
                            1.0f);
            this.playing = 55;
        }
        if (!this.level().isClientSide) {
            if (this.exploding > 0) {
                --this.exploding;
            }
            if (this.exploding == 0 && velocity > 0.65 && this.getRandom().nextInt(20000) == 1) {
                this.exploding = 45;
                this.playing = 50;
            }
            this.setExploding(this.exploding);
        } else {
            this.exploding = this.getExploding();
        }
        if (this.getExploding() > 0 && this.getControllingPassenger() != null) {
            if (this.getRandom().nextInt(10) == 1) {
                this.level()
                        .playSound(
                                null,
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                SoundEvents.GENERIC_EXPLODE,
                                SoundSource.NEUTRAL,
                                0.55f,
                                0.75f + this.getRandom().nextFloat());
            }
            for (i = 0; i < 15; ++i) {
                this.level()
                        .addParticle(
                                ParticleTypes.EXPLOSION,
                                (int) (this.getX()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 4.0f)),
                                (int) (this.getY()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 4.0f)),
                                (int) (this.getZ()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 4.0f)),
                                mx,
                                0.0,
                                mz);
                this.level()
                        .addParticle(
                                ParticleTypes.EXPLOSION_EMITTER,
                                (int) (this.getX()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 2.0f)),
                                (int) (this.getY()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 2.0f)),
                                (int) (this.getZ()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 2.0f)),
                                mx,
                                0.0,
                                mz);
                this.level()
                        .addParticle(
                                ParticleTypes.SMOKE,
                                (int) (this.getX()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 5.0f)),
                                (int) (this.getY()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 5.0f)),
                                (int) (this.getZ()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 5.0f)),
                                mx,
                                0.0,
                                mz);
                this.level()
                        .addParticle(
                                ParticleTypes.LARGE_SMOKE,
                                (int) (this.getX()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 3.0f)),
                                (int) (this.getY()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 3.0f)),
                                (int) (this.getZ()
                                        + (double) ((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 3.0f)),
                                mx,
                                0.0,
                                mz);
            }
        }
        if (this.level().isClientSide) {
            if (this.getControllingPassenger() == null) {
                bid = this.level()
                        .getBlockState(new BlockPos((int) this.getX(), (int) ((float) this.getY() - (float) gh), (int) this.getZ()))
                        .getBlock();
                if (bid != Blocks.AIR) {
                    my += 0.06;
                    this.setPos(this.getX(), this.getY() + 0.07, this.getZ());
                    this.boatY += 0.07;
                } else {
                    my -= 0.003;
                }
            }
            LocalPlayer pp = this.getRiderPlayerClient();
            if (pp != null) {
                pp.connection.send(
                        new ServerboundMovePlayerPacket.Rot(pp.getYRot(), pp.getXRot(), pp.onGround()));
                pp.connection.send(
                        new ServerboundPlayerInputPacket(
                                pp.xxa, pp.zza, pp.input.jumping, pp.input.shiftKeyDown));
            }
            if (this.boatPosRotationIncrements > 0) {
                d4 = this.getX() + (this.boatX - this.getX()) / (double) this.boatPosRotationIncrements;
                d5 = this.getY() + (this.boatY - this.getY()) / (double) this.boatPosRotationIncrements;
                double d11 = this.getZ() + (this.boatZ - this.getZ()) / (double) this.boatPosRotationIncrements;
                this.setPos(d4, d5, d11);
                this.setXRot(
                        (float)
                                ((double) this.getXRot()
                                        + (this.boatPitch - (double) this.getXRot())
                                                / (double) this.boatPosRotationIncrements));
                double d10 = Mth.wrapDegrees(this.boatYaw - (double) this.getYRot());
                if (this.getControllingPassenger() != null) {
                    d10 = Mth.wrapDegrees(
                            (double) this.getControllingPassenger().getYRot() - (double) this.getYRot());
                }
                this.setYRot((float) ((double) this.getYRot() + d10 / (double) this.boatPosRotationIncrements));
                this.setRot(this.getYRot(), this.getXRot());
                --this.boatPosRotationIncrements;
            } else {
                d4 = this.getX() + mx;
                d5 = this.getY() + my;
                double d11 = this.getZ() + mz;
                this.setPos(d4, d5, d11);
                mx *= 0.99;
                my *= 0.95;
                mz *= 0.99;
            }
            this.setDeltaMovement(mx, my, mz);
        } else {
            if (this.getControllingPassenger() != null) {
                gh = 1.25;
            }
            bid = this.level()
                    .getBlockState(new BlockPos((int) this.getX(), (int) ((float) this.getY() - (float) gh), (int) this.getZ()))
                    .getBlock();
            if (bid != Blocks.AIR) {
                my += 0.06;
                this.setPos(this.getX(), this.getY() + 0.1, this.getZ());
                if (bid == Blocks.TALL_GRASS
                        && this.getControllingPassenger() != null
                        && this.getRandom().nextInt(200) == 1
                        && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    this.level()
                            .setBlock(
                                    new BlockPos((int) this.getX(), (int) (this.getY() - gh), (int) this.getZ()),
                                    Blocks.AIR.defaultBlockState(),
                                    2);
                }
                if (bid == Blocks.GRASS_BLOCK
                        && this.getControllingPassenger() != null
                        && this.getRandom().nextInt(200) == 1
                        && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    this.level()
                            .setBlock(
                                    new BlockPos((int) this.getX(), (int) (this.getY() - gh), (int) this.getZ()),
                                    Blocks.DIRT.defaultBlockState(),
                                    2);
                }
            } else {
                my -= 0.01;
            }
            Player pp = this.getRiderPlayer();
            if (pp != null) {
                double rdv;
                if (pp.isShiftKeyDown()) {
                    pp.stopRiding();
                    this.setDeltaMovement(mx, my, mz);
                    return;
                }
                obstruction_factor = 0.0;
                int scanDepth = 3 + (int) (Math.max(0.0, velocity) * 8.0);
                if (scanDepth > 24) {
                    scanDepth = 24;
                }
                for (k = 1; k < scanDepth; ++k) {
                    for (i = 1; i < scanDepth * 2; ++i) {
                        double dz = (double) i * Math.sin(Math.toRadians(this.getYRot() + 90.0f));
                        double dx = (double) i * Math.cos(Math.toRadians(this.getYRot() + 90.0f));
                        bid = this.level()
                                .getBlockState(
                                        new BlockPos((int) (this.getX() + dx), (int) this.getY() - k, (int) (this.getZ() + dz)))
                                .getBlock();
                        if (bid == Blocks.AIR) {
                            continue;
                        }
                        obstruction_factor += 0.05;
                    }
                }
                my += obstruction_factor * 0.11;
                this.setPos(this.getX(), this.getY() + obstruction_factor * 0.11, this.getZ());
                d4 = pp.getYRot();
                d4 %= 360.0;
                while (d4 < 0.0) {
                    d4 += 360.0;
                }
                d5 = this.getYRot();
                d5 %= 360.0;
                while (d5 < 0.0) {
                    d5 += 360.0;
                }
                for (relative_g = (d4 - d5) % 180.0; relative_g < 0.0; relative_g += 180.0) {
                    // 1.12 empty for-loop condition body
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
                    this.setYRot(pp.getYRot() + (float) (relative_g * d4));
                } else {
                    this.setYRot(pp.getYRot());
                }
                relative_g = Math.abs(relative_g) * velocity;
                if (relative_g > 50.0) {
                    relative_g = 0.0;
                }
                this.setXRot(10.0f * (float) velocity);
                this.setRot(this.getYRot(), this.getXRot());
                double newvelocity = Math.sqrt(mx * mx + mz * mz);
                if (this.exploding != 0 && (newvelocity -= 0.05) < 0.0) {
                    newvelocity = 0.0;
                }
                double rhm = Math.atan2(mz, mx);
                double rhdir = Math.toRadians((pp.getYRot() + 90.0f) % 360.0f);
                double pi = 3.1415926545;
                double deltav = 0.0;
                float im = Math.abs(this.zza) > 0.001f ? this.zza : pp.zza;
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
                    newvelocity = -newvelocity;
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
                        mx = Math.cos(Math.toRadians(this.getYRot() + 90.0f)) * newvelocity;
                        mz = Math.sin(Math.toRadians(this.getYRot() + 90.0f)) * newvelocity;
                    } else {
                        if (newvelocity < -max_speed) {
                            newvelocity = -max_speed;
                        }
                        newvelocity = -newvelocity;
                        mx = Math.cos(Math.toRadians(this.getYRot() + 270.0f)) * newvelocity;
                        mz = Math.sin(Math.toRadians(this.getYRot() + 270.0f)) * newvelocity;
                    }
                } else if (newvelocity >= 0.0) {
                    mx = Math.cos(Math.toRadians(this.getYRot() + 90.0f)) * newvelocity;
                    mz = Math.sin(Math.toRadians(this.getYRot() + 90.0f)) * newvelocity;
                } else {
                    mx = Math.cos(Math.toRadians(this.getYRot() + 270.0f)) * (newvelocity * -1.0);
                    mz = Math.sin(Math.toRadians(this.getYRot() + 270.0f)) * (newvelocity * -1.0);
                }
            } else if (this.getControllingPassenger() == null) {
                mx = 0.0;
                mz = 0.0;
            }
            this.move(MoverType.SELF, new Vec3(mx, my, mz));
            motion = this.getDeltaMovement();
            mx = motion.x;
            my = motion.y;
            mz = motion.z;
            velocity = Math.sqrt(mx * mx + mz * mz);
            if (this.horizontalCollision && velocity > 0.75) {
                this.discard();
                int p = this.getRandom().nextInt(10);
                for (k = 0; k < 6 + p; ++k) {
                    this.spawnAtLocation(new ItemStack(net.minecraft.world.item.Items.STICK));
                }
                for (k = 0; k < 2; ++k) {
                    this.spawnAtLocation(new ItemStack(net.minecraft.world.item.Items.DIAMOND));
                }
            } else {
                mx *= 0.98;
                my *= 0.94;
                mz *= 0.98;
            }
            list = this.level().getEntities(this, this.getBoundingBox().inflate(0.25, 0.0, 0.25));
            if (!list.isEmpty()) {
                for (Entity entity : list) {
                    if (entity == this.getControllingPassenger()
                            || !entity.isPushable()
                            || "Girlfriend".equals(entity.getClass().getSimpleName())
                            || "Boyfriend".equals(entity.getClass().getSimpleName())) {
                        continue;
                    }
                    entity.push(this);
                }
            }
            if (this.getControllingPassenger() != null && !this.getControllingPassenger().isAlive()) {
                this.ejectPassengers();
            }
            this.setDeltaMovement(mx, my, mz);
        }
        double horizSpeed =
                Math.sqrt(
                        (this.getX() - this.xo) * (this.getX() - this.xo)
                                + (this.getZ() - this.zo) * (this.getZ() - this.zo));
        if (horizSpeed > 0.15 && this.getControllingPassenger() != null) {
            d4 = Math.cos(Math.toRadians(this.getYRot() + 270.0f));
            d5 = Math.sin(Math.toRadians(this.getYRot() + 270.0f));
            bid = Blocks.AIR;
            for (i = 1; i < 10
                    && (bid = this.level()
                                    .getBlockState(new BlockPos((int) this.getX(), (int) this.getY() - i, (int) this.getZ()))
                                    .getBlock())
                            == Blocks.AIR;
                    ++i) {
            }
            int j = 0;
            DustParticleOptions dust = new DustParticleOptions(new Vector3f(1.0f, 0.0f, 0.0f), 1.0f);
            while ((double) j < 1.0 + horizSpeed * 10.0) {
                double d9;
                double d8;
                d6 = this.getRandom().nextFloat() * 2.0f - 1.0f;
                d7 = (double) (this.getRandom().nextInt(2) * 2 - 1) * 0.7;
                if (this.getRandom().nextBoolean()) {
                    d8 = this.getX() - d4 * d6 * 0.8 + d5 * d7;
                    d9 = this.getZ() - d5 * d6 * 0.8 - d4 * d7;
                    if (this.getRandom().nextBoolean()) {
                        this.level().addParticle(ParticleTypes.SMOKE, d8, this.getY() - 0.25, d9, mx, my, mz);
                    } else {
                        this.level().addParticle(dust, d8, this.getY() - 0.25, d9, mx, my, mz);
                    }
                } else {
                    d8 = this.getX() + d4 + d5 * d6 * 0.7;
                    d9 = this.getZ() + d5 - d4 * d6 * 0.7;
                    if (this.getRandom().nextBoolean()) {
                        this.level().addParticle(ParticleTypes.SMOKE, d8, this.getY() - 0.225, d9, mx, my, mz);
                    } else {
                        this.level().addParticle(dust, d8, this.getY() - 0.225, d9, mx, my, mz);
                    }
                }
                if (bid == Blocks.WATER) {
                    for (k = 0; k < 5; ++k) {
                        this.level()
                                .addParticle(
                                        ParticleTypes.SPLASH,
                                        this.getX() + (double) this.getRandom().nextFloat(),
                                        this.getY() - (double) i + 1.25,
                                        this.getZ() + (double) this.getRandom().nextFloat(),
                                        mx / 2.0,
                                        my + horizSpeed,
                                        mz / 2.0);
                    }
                }
                ++j;
            }
        }
        this.clearFire();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("HoverColor", this.getColor());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.color = tag.getInt("HoverColor");
        if (this.color < 1) {
            this.color = 1;
        }
        if (this.color > 10) {
            this.color = 10;
        }
        this.setColor(this.color);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && stack.getCount() <= 0) {
            player.setItemInHand(hand, ItemStack.EMPTY);
            stack = ItemStack.EMPTY;
        }
        if (!stack.isEmpty()
                && stack.getItem() == ChaosPersists.MyUltimateSword
                && player.distanceToSqr(this) < 16.0) {
            if (!this.level().isClientSide) {
                int c = this.getColor() + 1;
                if (c > 10) {
                    c = 1;
                }
                this.setColor(c);
            }
            return InteractionResult.SUCCESS;
        }
        if (this.getControllingPassenger() instanceof Player rider
                && rider != player) {
            return InteractionResult.SUCCESS;
        }
        if (!this.level().isClientSide) {
            player.startRiding(this);
        }
        return InteractionResult.SUCCESS;
    }

    public void setDamageTaken(float f) {
        this.entityData.set(F24, f);
    }

    public float getDamageTaken() {
        return this.entityData.get(F24);
    }

    public void setTimeSinceHit(int par1) {
        this.entityData.set(I22, par1);
    }

    public int getTimeSinceHit() {
        return this.entityData.get(I22);
    }

    public void setForwardDirection(int par1) {
        this.entityData.set(I23, par1);
    }

    public int getForwardDirection() {
        return this.entityData.get(I23);
    }

    public void setExploding(int par1) {
        this.entityData.set(I20, par1);
    }

    public int getExploding() {
        return this.entityData.get(I20);
    }

    public void setColor(int par1) {
        this.entityData.set(I21, par1);
    }

    public int getColor() {
        return this.entityData.get(I21);
    }
}
