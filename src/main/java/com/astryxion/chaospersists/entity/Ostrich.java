package com.astryxion.chaospersists.entity;

import com.astryxion.chaospersists.util.MyUtils;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.core.ChaosSounds;
import com.astryxion.chaospersists.render.RenderInfo;
import com.astryxion.chaospersists.util.MyEntityAIAvoidEntity;
import com.astryxion.chaospersists.util.MyEntityAIFollowOwner;
import com.astryxion.chaospersists.util.MyEntityAIWander;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.MoverType;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class Ostrich extends EntityCannonFodder {
    private float moveSpeed = 0.2f;
    private RenderInfo renderdata = new RenderInfo();
    private int boatPosRotationIncrements;
    private double boatX;
    private double boatY;
    private double boatZ;
    private double boatYaw;
    private double boatPitch;
    private double boatYawHead;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    float deltasmooth = 0.0f;
    private int didjump = 0;

    public Ostrich(EntityType<? extends Ostrich> type, Level level) {
        super(type, level);
        this.moveSpeed = 0.38f;
        this.setOrderedToSit(false);
        this.xpReward = 10;
        this.renderdata = new RenderInfo();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(2, new MyEntityAIFollowOwner(this, 2.0f, 10.0f, 2.0f));
        this.goalSelector.addGoal(
                3, new MyEntityAIAvoidEntity(this, Monster.class, 8.0f, 1.0, 1.899999976158142));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2000000476837158, Ingredient.of(Items.APPLE), false));
        this.goalSelector.addGoal(5, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, LivingEntity.class, 5.0f));
        this.goalSelector.addGoal(8, new MyEntityAIWander(this, 1.0f));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        // 1.12 EntityAIMoveIndoors — class removed in 1.20.x; OpenDoorGoal preserves pathing through doors.
        this.goalSelector.addGoal(10, new OpenDoorGoal(this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.MOVEMENT_SPEED, 0.38)
                .add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.setOrderedToSit(false);
        if (this.renderdata == null) {
            this.renderdata = new RenderInfo();
        }
        this.renderdata.rf1 = 0.0f;
        this.renderdata.rf2 = 0.0f;
        this.renderdata.rf3 = 0.0f;
        this.renderdata.rf4 = 0.0f;
        this.renderdata.ri1 = 0;
        this.renderdata.ri2 = 0;
        this.renderdata.ri3 = 0;
        this.renderdata.ri4 = 0;
    }

    public RenderInfo getRenderInfo() {
        return this.renderdata;
    }

    public void setRenderInfo(RenderInfo r) {
        this.renderdata.rf1 = r.rf1;
        this.renderdata.rf2 = r.rf2;
        this.renderdata.rf3 = r.rf3;
        this.renderdata.rf4 = r.rf4;
        this.renderdata.ri1 = r.ri1;
        this.renderdata.ri2 = r.ri2;
        this.renderdata.ri3 = r.ri3;
        this.renderdata.ri4 = r.ri4;
    }

    @Override
    public void tick() {
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) this.moveSpeed);
        if (!this.isDeadOrDying() && this.getRandom().nextInt(200) == 1) {
            this.setLastHurtByMob(null);
        }
        if (!this.isDeadOrDying() && this.getRandom().nextInt(250) == 0) {
            this.heal(1.0f);
        }
        if (this.getControllingPassenger() != null) {
            this.tickRidden();
            if (!this.level().isClientSide) {
                return;
            }
        }
        super.tick();
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.getControllingPassenger() != null) {
            return;
        }
        super.travel(travelVector);
    }

    @Override
    public LivingEntity getControllingPassenger() {
        Entity e = this.getFirstPassenger();
        return e instanceof LivingEntity le ? le : null;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!source.is(DamageTypes.CACTUS)) {
            super.hurt(source, amount);
        }
        return false;
    }

    public int mygetMaxHealth() {
        return 25;
    }

    public int getOstrichHealth() {
        return (int) this.getHealth();
    }

    @Override
    public InteractionResult mobInteract(Player par1EntityPlayer, InteractionHand hand) {
        ItemStack var2 = par1EntityPlayer.getItemInHand(hand);
        if (!var2.isEmpty() && var2.getCount() <= 0) {
            par1EntityPlayer.setItemInHand(hand, ItemStack.EMPTY);
            var2 = ItemStack.EMPTY;
        }
        if (super.mobInteract(par1EntityPlayer, hand) == InteractionResult.SUCCESS) {
            return InteractionResult.SUCCESS;
        }
        if (!var2.isEmpty() && var2.is(Items.APPLE) && par1EntityPlayer.distanceToSqr(this) < 16.0) {
            if (!this.isTame()) {
                if (!this.level().isClientSide) {
                    if (this.getRandom().nextInt(2) == 0) {
                        this.setTame(true);
                        this.setOwnerUUID(par1EntityPlayer.getUUID());
                        spawnTamingParticles(true);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                        this.heal((float) this.mygetMaxHealth() - this.getHealth());
                    } else {
                        spawnTamingParticles(false);
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                }
            } else if (this.isOwnedBy(par1EntityPlayer)) {
                if (this.level().isClientSide) {
                    spawnTamingParticles(true);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                }
                if ((float) this.mygetMaxHealth() > this.getHealth()) {
                    this.heal((float) this.mygetMaxHealth() - this.getHealth());
                }
            }
            if (!par1EntityPlayer.getAbilities().instabuild) {
                var2.shrink(1);
                if (var2.isEmpty()) {
                    par1EntityPlayer.setItemInHand(hand, ItemStack.EMPTY);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (this.isTame()
                && !var2.isEmpty()
                && var2.is(Blocks.DEAD_BUSH.asItem())
                && par1EntityPlayer.distanceToSqr(this) < 16.0
                && this.isOwnedBy(par1EntityPlayer)) {
            if (!this.level().isClientSide) {
                this.setTame(false);
                this.setOwnerUUID(null);
                spawnTamingParticles(false);
                this.level().broadcastEntityEvent(this, (byte) 6);
            }
            if (!par1EntityPlayer.getAbilities().instabuild) {
                var2.shrink(1);
                if (var2.isEmpty()) {
                    par1EntityPlayer.setItemInHand(hand, ItemStack.EMPTY);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (!var2.isEmpty()
                && this.isTame()
                && this.isOwnedBy(par1EntityPlayer)
                && par1EntityPlayer.distanceToSqr(this) < 16.0) {
            if (!this.level().isClientSide) {
                if (!this.isInSittingPose()) {
                    Block bid =
                            this.level()
                                    .getBlockState(BlockPos.containing(this.getX(), this.getY() - 1.0, this.getZ()))
                                    .getBlock();
                    if (bid == Blocks.SAND
                            || bid == Blocks.GRAVEL
                            || bid == Blocks.DIRT
                            || bid == Blocks.FARMLAND
                            || bid == Blocks.GRASS_BLOCK) {
                        this.setOrderedToSit(true);
                    }
                } else {
                    this.setOrderedToSit(false);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (this.isTame()
                && !var2.isEmpty()
                && var2.is(Items.NAME_TAG)
                && par1EntityPlayer.distanceToSqr(this) < 16.0
                && this.isOwnedBy(par1EntityPlayer)) {
            this.setCustomName(var2.getHoverName());
            if (!par1EntityPlayer.getAbilities().instabuild) {
                var2.shrink(1);
                if (var2.isEmpty()) {
                    par1EntityPlayer.setItemInHand(hand, ItemStack.EMPTY);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (var2.isEmpty() && par1EntityPlayer.distanceToSqr(this) < 16.0) {
            if (!this.level().isClientSide) {
                par1EntityPlayer.startRiding(this);
                this.setOrderedToSit(false);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean shouldRiderSit() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isInSittingPose()) {
            return null;
        }
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ChaosSounds.CRYO_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ChaosSounds.CRYO_DEATH;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        if (this.isTame()) {
            int var3 = this.getRandom().nextInt(5);
            var3 += 2;
            for (int var4 = 0; var4 < var3; ++var4) {
                this.spawnAtLocation(Items.POPPY);
            }
        } else {
            super.dropCustomDeathLoot(source, looting, recentlyHit);
        }
    }

    @Override
    public float getVoicePitch() {
        return this.isBaby()
                ? (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1f + 1.5f
                : (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1f + 1.0f;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 1.4;
    }

    @Override
    protected void positionRider(Entity passenger, net.minecraft.world.entity.Entity.MoveFunction moveFunction) {
        if (this.getControllingPassenger() != null && passenger == this.getControllingPassenger()) {
            float f = -0.15f;
            double x = this.getX() - (double) f * Math.sin(Math.toRadians(this.getYRot()));
            double y = this.getY() + this.getPassengersRidingOffset() + passenger.getMyRidingOffset();
            double z = this.getZ() + (double) f * Math.cos(Math.toRadians(this.getYRot()));
            moveFunction.accept(passenger, x, y, z);
        } else {
            super.positionRider(passenger, moveFunction);
        }
    }

    @Override
    protected void jumpFromGround() {
        Vec3 v = this.getDeltaMovement();
        this.setDeltaMovement(v.x, v.y + 0.25, v.z);
        super.jumpFromGround();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int steps, boolean interpolate) {
        this.boatPosRotationIncrements = 10;
        this.boatX = x;
        this.boatY = y;
        this.boatZ = z;
        this.boatYaw = yaw;
        this.boatPitch = pitch;
        this.boatYawHead = steps;
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

    protected void tickRidden() {
        double obstruction_factor = 0.0;
        double relative_g = 0.0;
        double max_speed = 0.75;
        double pi = 3.1415926545;
        double deltav = 0.0;
        int dist = 2;
        if (this.getControllingPassenger() == null) {
            return;
        }
        if (this.isDeadOrDying()) {
            return;
        }
        if (this.level().isClientSide) {
            if (this.boatPosRotationIncrements > 0) {
                double d4 = this.getX() + (this.boatX - this.getX()) / (double) this.boatPosRotationIncrements;
                double d5 = this.getY() + (this.boatY - this.getY()) / (double) this.boatPosRotationIncrements;
                double d11 = this.getZ() + (this.boatZ - this.getZ()) / (double) this.boatPosRotationIncrements;
                this.setPos(d4, d5, d11);
                float newPitch =
                        (float)
                                ((double) this.getXRot()
                                        + (this.boatPitch - (double) this.getXRot())
                                                / (double) this.boatPosRotationIncrements);
                this.setXRot(newPitch);
                double d10 = Mth.wrapDegrees(this.boatYaw - (double) this.getYRot());
                if (this.getControllingPassenger() != null) {
                    d10 = Mth.wrapDegrees((double) this.getControllingPassenger().getYRot() - (double) this.getYRot());
                }
                this.setYRot((float) ((double) this.getYRot() + d10 / (double) this.boatPosRotationIncrements));
                this.setRot(this.getYRot(), this.getXRot());
                --this.boatPosRotationIncrements;
            }
        } else if (this.getControllingPassenger() != null) {
            double rdv;
            Player pp = (Player) this.getControllingPassenger();
            Vec3 dm = this.getDeltaMovement();
            double mx = dm.x;
            double my = dm.y;
            double mz = dm.z;
            if (mx < -2.0) {
                mx = -2.0;
            }
            if (mx > 2.0) {
                mx = 2.0;
            }
            if (mz < -2.0) {
                mz = -2.0;
            }
            if (mz > 2.0) {
                mz = 2.0;
            }
            double velocity = Math.sqrt(mx * mx + mz * mz);
            obstruction_factor = 0.0;
            dist = 1 + (int) (velocity * 10.0);
            for (int k = 0; k < dist; ++k) {
                for (int i = 1; i < dist * 2; ++i) {
                    double dz = (double) i * Math.sin(Math.toRadians(this.getYRot() + 90.0f));
                    double dx = (double) i * Math.cos(Math.toRadians(this.getYRot() + 90.0f));
                    BlockPos checkPos =
                            new BlockPos(
                                    (int) (this.getX() + dx),
                                    (int) this.getY() - 1 + k,
                                    (int) (this.getZ() + dz));
                    Block bid = this.level().getBlockState(checkPos).getBlock();
                    if (bid == Blocks.AIR) {
                        continue;
                    }
                    obstruction_factor += 0.075;
                }
            }
            my += obstruction_factor;
            this.setPos(this.getX(), this.getY() + obstruction_factor, this.getZ());
            if (my > 4.0) {
                my = 4.0;
            }
            double d4 = this.getControllingPassenger().getYRot();
            d4 %= 360.0;
            while (d4 < 0.0) {
                d4 += 360.0;
            }
            double d5 = this.getYRot();
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
                d4 = Math.abs(d4);
                if (d4 < 0.01) {
                    d4 = 0.01;
                }
                if (d4 > 0.9) {
                    d4 = 0.9;
                }
                this.setYRot(
                        this.getControllingPassenger().getYRot() + (float) (relative_g * d4));
            } else {
                this.setYRot(this.getControllingPassenger().getYRot());
            }
            this.setXRot(2.0f * (float) velocity);
            this.setRot(this.getYRot(), this.getXRot());
            double newvelocity = Math.sqrt(mx * mx + mz * mz);
            double rhm = Math.atan2(mz, mx);
            double rhdir = Math.toRadians((this.getControllingPassenger().getYRot() + 90.0f) % 360.0f);
            deltav = 0.0;
            float im = pp.zza;
            if (ChaosPersists.flyup_keystate != 0) {
                if (this.didjump == 0) {
                    my += 1.0;
                    my += velocity * 6.0;
                    this.didjump = 20;
                }
            } else if (this.didjump > 0) {
                --this.didjump;
            }
            rdv = Math.abs(rhm - rhdir) % (pi * 2.0);
            if (rdv > pi) {
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
                    deltav = 0.045;
                    if (this.deltasmooth < 0.0f) {
                        this.deltasmooth = 0.0f;
                    }
                    this.deltasmooth = (float) ((double) this.deltasmooth + deltav / 10.0);
                    if ((double) this.deltasmooth > deltav) {
                        this.deltasmooth = (float) deltav;
                    }
                } else {
                    max_speed = 0.25;
                    deltav = -0.03;
                    if (this.deltasmooth > 0.0f) {
                        this.deltasmooth = 0.0f;
                    }
                    this.deltasmooth = (float) ((double) this.deltasmooth + deltav / 10.0);
                    if ((double) this.deltasmooth < deltav) {
                        this.deltasmooth = (float) deltav;
                    }
                }
                if ((newvelocity += (double) this.deltasmooth) >= 0.0) {
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
            this.move(MoverType.SELF, new Vec3(mx, my, mz));
            my -= 0.25;
            mx *= 0.95;
            my *= 0.85;
            mz *= 0.95;
            this.setDeltaMovement(mx, my, mz);
            if (this.getControllingPassenger() != null && !this.getControllingPassenger().isAlive()) {
                this.ejectPassengers();
            }
        }
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        super.checkFallDamage(y, onGroundIn, state, pos);
        this.fallDistance = 0.0f;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        if (this.isBaby()) {
            this.setPersistenceRequired();
            return false;
        }
        if (!this.getPassengers().isEmpty()) {
            return false;
        }
        if (this.isPersistenceRequired()) {
            return false;
        }
        if (this.isTame()) {
            return false;
        }
        return true;
    }

    @Override
    public Ostrich getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return (Ostrich) this.getType().create(level);
    }

    public boolean isWheat(ItemStack par1ItemStack) {
        return !par1ItemStack.isEmpty() && par1ItemStack.is(Items.APPLE);
    }

    public boolean isBreedingItem(ItemStack par1ItemStack) {
        Item crystal =
                ForgeRegistries.ITEMS.getValue(ResourceLocation.fromNamespaceAndPath("chaospersists", "crystalapple"));
        if (crystal == null) {
            crystal = ChaosPersists.MyCrystalApple;
        }
        return crystal != null && par1ItemStack.is(crystal);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return this.isBreedingItem(stack);
    }

    public static boolean checkOstrichSpawnRules(
            EntityType<Ostrich> type,
            ServerLevelAccessor level,
            MobSpawnType spawnType,
            BlockPos pos,
            RandomSource random) {
        if (pos.getY() < 50) {
            return false;
        }
        if (!MyUtils.isDay(level)) {
            return false;
        }
        if (random.nextInt(4) != 1) {
            return false;
        }
        List<Ostrich> found =
                level.getLevel()
                        .getEntitiesOfClass(
                                Ostrich.class,
                                new AABB(pos).inflate(16.0, 6.0, 16.0));
        return found.isEmpty();
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnReason) {
        if (this.getY() < 50.0) {
            return false;
        }
        if (!this.level().isDay()) {
            return false;
        }
        if (this.getRandom().nextInt(4) != 1) {
            return false;
        }
        List<Ostrich> target =
                this.level()
                        .getEntitiesOfClass(
                                Ostrich.class, this.getBoundingBox().inflate(16.0, 6.0, 16.0));
        long others = target.stream().filter(e -> e != this).count();
        return others == 0;
    }
}
