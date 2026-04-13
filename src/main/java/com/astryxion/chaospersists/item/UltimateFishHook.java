/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.UltimateFishHook
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLiquid
 *  net.minecraft.block.BlockTripWireHook
 *  net.minecraft.block.material.Material
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.item.EntityXPOrb
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.projectile.EntityFishHook
 *  net.minecraft.init.Blocks
 *  net.minecraft.init.Items
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemArmor
 *  net.minecraft.item.ItemBow
 *  net.minecraft.item.ItemFishFood
 *  net.minecraft.item.ItemFishFood$FishType
 *  net.minecraft.item.ItemFishingRod
 *  net.minecraft.item.ItemPotion
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.stats.StatBase
 *  net.minecraft.stats.StatList
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.MathHelper
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.util.WeightedRandom
 *  net.minecraft.util.WeightedRandomFishable
 *  net.minecraft.world.World
 *  net.minecraft.world.WorldServer
 */
package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.util.WeightedRandomFishable;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class UltimateFishHook
extends EntityFishHook {
    private static final List field_146039_d = Arrays.asList(new WeightedRandomFishable[]{new WeightedRandomFishable(new ItemStack((Item)Items.LEATHER_BOOTS), 10).func_150709_a(0.9f), new WeightedRandomFishable(new ItemStack(Items.LEATHER), 10), new WeightedRandomFishable(new ItemStack(Items.BONE), 10), new WeightedRandomFishable(new ItemStack((Item)Items.POTIONITEM), 10), new WeightedRandomFishable(new ItemStack(Items.STRING), 5), new WeightedRandomFishable(new ItemStack((Item)Items.FISHING_ROD), 2).func_150709_a(0.9f), new WeightedRandomFishable(new ItemStack(Items.BOWL), 10), new WeightedRandomFishable(new ItemStack(Items.STICK), 5), new WeightedRandomFishable(new ItemStack(Items.DYE, 10, 0), 1), new WeightedRandomFishable(new ItemStack((Block)Blocks.TRIPWIRE_HOOK), 10), new WeightedRandomFishable(new ItemStack(Items.ROTTEN_FLESH), 10)});
    private static final List field_146041_e = Arrays.asList(new WeightedRandomFishable[]{new WeightedRandomFishable(new ItemStack(Blocks.WATERLILY), 1), new WeightedRandomFishable(new ItemStack(Items.NAME_TAG), 1), new WeightedRandomFishable(new ItemStack(Items.SADDLE), 1), new WeightedRandomFishable(new ItemStack((Item)Items.BOW), 1).func_150709_a(0.25f).func_150707_a(), new WeightedRandomFishable(new ItemStack((Item)Items.FISHING_ROD), 1).func_150709_a(0.25f).func_150707_a(), new WeightedRandomFishable(new ItemStack(Items.BOOK), 1).func_150707_a()});
    private static final List field_146036_f = Arrays.asList(new WeightedRandomFishable[]{new WeightedRandomFishable(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.COD.getMetadata()), 60), new WeightedRandomFishable(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.SALMON.getMetadata()), 25), new WeightedRandomFishable(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()), 2), new WeightedRandomFishable(new ItemStack(Items.FISH, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()), 13)});
    private static final List chaospersists_lava_fish = Arrays.asList(new WeightedRandomFishable[]{new WeightedRandomFishable(new ItemStack(ChaosPersists.MySunspotUrchin), 25), new WeightedRandomFishable(new ItemStack(ChaosPersists.MyLavaEel), 10), new WeightedRandomFishable(new ItemStack(ChaosPersists.MySunFish), 15), new WeightedRandomFishable(new ItemStack(ChaosPersists.MySparkFish), 10), new WeightedRandomFishable(new ItemStack(ChaosPersists.MyFireFish), 15)});
    private static final List chaospersists_fish = Arrays.asList(new WeightedRandomFishable[]{new WeightedRandomFishable(new ItemStack(ChaosPersists.MyBlueFish), 25), new WeightedRandomFishable(new ItemStack(ChaosPersists.MyPinkFish), 10), new WeightedRandomFishable(new ItemStack(ChaosPersists.MyRockFish), 15), new WeightedRandomFishable(new ItemStack(ChaosPersists.MyWoodFish), 10), new WeightedRandomFishable(new ItemStack(ChaosPersists.MyGreyFish), 15)});
    private int field_146037_g = -1;
    private int field_146048_h = -1;
    private int field_146050_i = -1;
    private Block field_146046_j;
    private boolean field_146051_au;
    private int field_146049_av;
    private int field_146047_aw;
    private int fish_on_hook;
    private int fish_wait_time;
    private int ticks_catchable;
    private float fish_direction;
    public Entity field_146043_c;
    private int field_146055_aB;
    private double field_146056_aC;
    private double field_146057_aD;
    private double field_146058_aE;
    private double field_146059_aF;
    private double field_146060_aG;
    @SideOnly(value=Side.CLIENT)
    private double field_146061_aH;
    @SideOnly(value=Side.CLIENT)
    private double field_146052_aI;
    @SideOnly(value=Side.CLIENT)
    private double field_146053_aJ;
    private int fishing_in_lava = 0;
    private int hookShake = 0;

    public UltimateFishHook(World par1World) {
        // This constructor is used by Forge's spawn packet (client-side construction).
        // EntityFishHook's init path does not tolerate a null angler in this mappings set,
        // so we provide a safe placeholder player.
        super(par1World, resolveAnglerForSpawn(par1World));
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
        this.isImmuneToFire = true;
    }

    private static EntityPlayer resolveAnglerForSpawn(World world) {
        if (world == null) {
            return null;
        }
        // Server side: use a FakePlayer (WorldServer only).
        if (!world.isRemote && world instanceof WorldServer) {
            return FakePlayerFactory.getMinecraft((WorldServer)world);
        }
        // Client side: reflectively grab Minecraft.getMinecraft().player without hard-linking client classes.
        if (world.isRemote) {
            try {
                Class<?> mcClass = Class.forName("net.minecraft.client.Minecraft");
                Object mc = mcClass.getMethod("getMinecraft").invoke(null);
                Object player = mcClass.getField("player").get(mc);
                if (player instanceof EntityPlayer) {
                    return (EntityPlayer)player;
                }
            } catch (Throwable ignored) {
            }
        }
        // Fallback: any player in the world.
        try {
            if (world.playerEntities != null && !world.playerEntities.isEmpty()) {
                Object p = world.playerEntities.get(0);
                if (p instanceof EntityPlayer) {
                    return (EntityPlayer)p;
                }
            }
        } catch (Throwable ignored) {
        }
        return null;
    }

    @SideOnly(value=Side.CLIENT)
    public UltimateFishHook(World par1World, double par2, double par4, double par6, EntityPlayer par8EntityPlayer) {
        super(par1World, par8EntityPlayer, par2, par4, par6);
        this.setSize(0.25f, 0.25f);
        this.setPosition(par2, par4, par6);
        this.ignoreFrustumCheck = true;
        par8EntityPlayer.fishEntity = this;
                this.isImmuneToFire = true;
    }

    public UltimateFishHook(World par1World, EntityPlayer par2EntityPlayer) {
        super(par1World, par2EntityPlayer);
        this.ignoreFrustumCheck = true;
        par2EntityPlayer.fishEntity = this;
        this.setSize(0.25f, 0.25f);
        double eyeY = par2EntityPlayer.posY + (double) par2EntityPlayer.getEyeHeight() - 0.1D;
        this.setLocationAndAngles(par2EntityPlayer.posX, eyeY, par2EntityPlayer.posZ, par2EntityPlayer.rotationYaw, par2EntityPlayer.rotationPitch);
        this.posX -= (double)(MathHelper.cos((float)(this.rotationYaw / 180.0f * 3.1415927f)) * 0.16f);
        this.posY -= 0.10000000149011612;
        this.posZ -= (double)(MathHelper.sin((float)(this.rotationYaw / 180.0f * 3.1415927f)) * 0.16f);
        this.setPosition(this.posX, this.posY, this.posZ);
        float f = 0.4f;
        this.motionX = (- MathHelper.sin((float)(this.rotationYaw / 180.0f * 3.1415927f))) * MathHelper.cos((float)(this.rotationPitch / 180.0f * 3.1415927f)) * f;
        this.motionZ = MathHelper.cos((float)(this.rotationYaw / 180.0f * 3.1415927f)) * MathHelper.cos((float)(this.rotationPitch / 180.0f * 3.1415927f)) * f;
        this.motionY = (- MathHelper.sin((float)(this.rotationPitch / 180.0f * 3.1415927f))) * f;
        this.func_146035_c(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
        this.isImmuneToFire = true;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.isImmuneToFire = true;
    }

    private static ItemStack getUltimateRodStack(EntityPlayer player) {
        if (player == null) {
            return ItemStack.EMPTY;
        }
        ItemStack main = player.getHeldItemMainhand();
        if (!main.isEmpty() && main.getItem() == ChaosPersists.MyUltimateFishingRod) {
            return main;
        }
        ItemStack off = player.getHeldItemOffhand();
        if (!off.isEmpty() && off.getItem() == ChaosPersists.MyUltimateFishingRod) {
            return off;
        }
        return ItemStack.EMPTY;
    }

    private static boolean isHoldingUltimateRod(EntityPlayer player) {
        return !getUltimateRodStack(player).isEmpty();
    }

    public void func_146035_c(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_) {
        float f2 = MathHelper.sqrt((double)(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_));
        p_146035_1_ /= (double)f2;
        p_146035_3_ /= (double)f2;
        p_146035_5_ /= (double)f2;
        p_146035_1_ += this.rand.nextGaussian() * 0.007499999832361937 * (double)p_146035_8_;
        p_146035_3_ += this.rand.nextGaussian() * 0.007499999832361937 * (double)p_146035_8_;
        p_146035_5_ += this.rand.nextGaussian() * 0.007499999832361937 * (double)p_146035_8_;
        this.motionX = p_146035_1_ *= (double)p_146035_7_;
        this.motionY = p_146035_3_ *= (double)p_146035_7_;
        this.motionZ = p_146035_5_ *= (double)p_146035_7_;
        float f3 = MathHelper.sqrt((double)(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_));
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0 / 3.141592653589793);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_146035_3_, f3) * 180.0 / 3.141592653589793);
        this.field_146049_av = 0;
    }

    @SideOnly(value=Side.CLIENT)
    public boolean isInRangeToRenderDist(double par1) {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        return par1 < (d1 *= 64.0) * d1;
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
        this.field_146056_aC = par1;
        this.field_146057_aD = par3;
        this.field_146058_aE = par5;
        this.field_146059_aF = par7;
        this.field_146060_aG = par8;
        this.field_146055_aB = par9;
        this.motionX = this.field_146061_aH;
        this.motionY = this.field_146052_aI;
        this.motionZ = this.field_146053_aJ;
    }

    @SideOnly(value=Side.CLIENT)
    public void setVelocity(double par1, double par3, double par5) {
        this.field_146061_aH = this.motionX = par1;
        this.field_146052_aI = this.motionY = par3;
        this.field_146053_aJ = this.motionZ = par5;
    }

    public void onUpdate() {
        if (this.field_146055_aB > 0) {
            double d7 = this.posX + (this.field_146056_aC - this.posX) / (double)this.field_146055_aB;
            double d8 = this.posY + (this.field_146057_aD - this.posY) / (double)this.field_146055_aB;
            double d9 = this.posZ + (this.field_146058_aE - this.posZ) / (double)this.field_146055_aB;
            double d1 = MathHelper.wrapDegrees((double)(this.field_146059_aF - (double)this.rotationYaw));
            this.rotationYaw = (float)((double)this.rotationYaw + d1 / (double)this.field_146055_aB);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.field_146060_aG - (double)this.rotationPitch) / (double)this.field_146055_aB);
            --this.field_146055_aB;
            this.setPosition(d7, d8, d9);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        } else {
            double d2;
            if (!this.world.isRemote) {
                EntityPlayer angler = this.getAngler();
                if (angler == null || angler.isDead || !angler.isEntityAlive() || !isHoldingUltimateRod(angler)
                        || this.getDistanceSq((Entity) angler) > 1024.0) {
                    this.setDead();
                    if (angler != null) {
                        angler.fishEntity = null;
                    }
                    return;
                }
                if (this.field_146043_c != null) {
                    if (!this.field_146043_c.isDead) {
                        this.posX = this.field_146043_c.posX;
                        this.posY = this.field_146043_c.getEntityBoundingBox().minY + (double)this.field_146043_c.height * 0.8;
                        this.posZ = this.field_146043_c.posZ;
                        return;
                    }
                    this.field_146043_c = null;
                }
            }
            if (this.hookShake > 0) {
                --this.hookShake;
            }
            if (this.field_146051_au) {
                if (this.world.getBlockState(new net.minecraft.util.math.BlockPos(this.field_146037_g, this.field_146048_h, this.field_146050_i)).getBlock() == this.field_146046_j) {
                    ++this.field_146049_av;
                    if (this.field_146049_av == 1200) {
                        this.setDead();
                    }
                    return;
                }
                this.field_146051_au = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2f);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2f);
                this.field_146049_av = 0;
                this.field_146047_aw = 0;
            } else {
                ++this.field_146047_aw;
            }
            Vec3d vec31 = new Vec3d((double)this.posX, (double)this.posY, (double)this.posZ);
            Vec3d Vec3d = new Vec3d((double)(this.posX + this.motionX), (double)(this.posY + this.motionY), (double)(this.posZ + this.motionZ));
            RayTraceResult RayTraceResult = this.world.rayTraceBlocks(vec31, Vec3d);
            vec31 = new Vec3d((double)this.posX, (double)this.posY, (double)this.posZ);
            Vec3d = new Vec3d((double)(this.posX + this.motionX), (double)(this.posY + this.motionY), (double)(this.posZ + this.motionZ));
            if (RayTraceResult != null) {
                Vec3d = new Vec3d((double)RayTraceResult.hitVec.x, (double)RayTraceResult.hitVec.y, (double)RayTraceResult.hitVec.z);
            }
            Entity entity = null;
            List list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).expand(1.0, 1.0, 1.0));
            double d0 = 0.0;
            for (int i = 0; i < list.size(); ++i) {
                RayTraceResult movingobjectposition1;
                float f;
                AxisAlignedBB axisalignedbb;
                Entity entity1 = (Entity)list.get(i);
                if (!entity1.canBeCollidedWith() || entity1 == this.getAngler() && this.field_146047_aw < 5 || (movingobjectposition1 = (axisalignedbb = entity1.getEntityBoundingBox().expand((double)(f = 0.3f), (double)f, (double)f)).calculateIntercept(vec31, Vec3d)) == null || (d2 = vec31.distanceTo(movingobjectposition1.hitVec)) >= d0 && d0 != 0.0) continue;
                entity = entity1;
                d0 = d2;
            }
            if (entity != null) {
                RayTraceResult = new RayTraceResult(entity);
            }
            if (RayTraceResult != null) {
                if (RayTraceResult.entityHit != null) {
                    if (RayTraceResult.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)this.getAngler()), 0.0f)) {
                        this.field_146043_c = RayTraceResult.entityHit;
                    }
                } else {
                    this.field_146051_au = true;
                }
            }
            if (!this.field_146051_au) {
                this.move(net.minecraft.entity.MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                float f5 = MathHelper.sqrt((double)(this.motionX * this.motionX + this.motionZ * this.motionZ));
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0 / 3.141592653589793);
                this.rotationPitch = (float)(Math.atan2(this.motionY, f5) * 180.0 / 3.141592653589793);
                while (this.rotationPitch - this.prevRotationPitch < -180.0f) {
                    this.prevRotationPitch -= 360.0f;
                }
                while (this.rotationPitch - this.prevRotationPitch >= 180.0f) {
                    this.prevRotationPitch += 360.0f;
                }
                while (this.rotationYaw - this.prevRotationYaw < -180.0f) {
                    this.prevRotationYaw -= 360.0f;
                }
                while (this.rotationYaw - this.prevRotationYaw >= 180.0f) {
                    this.prevRotationYaw += 360.0f;
                }
                this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2f;
                this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2f;
                float f6 = 0.92f;
                if (this.onGround || this.collidedHorizontally) {
                    f6 = 0.5f;
                }
                int b0 = 5;
                double d10 = 0.0;
                for (int j = 0; j < b0; ++j) {
                    double d3 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(j + 0) / (double)b0 - 0.125 + 0.125;
                    double d4 = this.getEntityBoundingBox().minY + (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) * (double)(j + 1) / (double)b0 - 0.125 + 0.125;
                    AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(this.getEntityBoundingBox().minX, d3, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().maxX, d4, this.getEntityBoundingBox().maxZ);
                    if (this.world.isMaterialInBB(axisalignedbb1, Material.WATER)) {
                        d10 += 1.0 / (double)b0;
                    }
                    if (!this.world.isMaterialInBB(axisalignedbb1, Material.LAVA)) continue;
                    d10 += 1.0 / (double)b0;
                }
                if (!this.world.isRemote && d10 > 0.0) {
                    WorldServer worldserver = (WorldServer)this.world;
                    int k = 1;
                    net.minecraft.util.math.BlockPos pos = new net.minecraft.util.math.BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY) + 1, MathHelper.floor(this.posZ));
                    if (this.rand.nextFloat() < 0.25f && this.world.isRainingAt(pos)) {
                        k = 2;
                    }
                    if (this.rand.nextFloat() < 0.5f && !this.world.canSeeSky(pos)) {
                        --k;
                    }
                    if (this.fish_on_hook > 0) {
                        --this.fish_on_hook;
                        if (this.fish_on_hook <= 0) {
                            this.fish_wait_time = 0;
                            this.ticks_catchable = 0;
                        }
                    } else if (this.ticks_catchable > 0) {
                        this.ticks_catchable -= k;
                        if (this.ticks_catchable <= 0) {
                            this.motionY -= 0.20000000298023224;
                            this.playSound(net.minecraft.init.SoundEvents.ENTITY_BOAT_PADDLE_WATER, 0.25f, 1.0f + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4f);
                            float f1 = (float)MathHelper.floor(this.getEntityBoundingBox().minY);
                            worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_BUBBLE, this.posX, (double)(f1 + 1.0f), this.posZ, (int)(1.0f + this.width * 20.0f), (double)this.width, 0.0, (double)this.width, 0.20000000298023224);
                            worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_WAKE, this.posX, (double)(f1 + 1.0f), this.posZ, (int)(1.0f + this.width * 20.0f), (double)this.width, 0.0, (double)this.width, 0.20000000298023224);
                            this.fish_on_hook = this.rand.nextInt(21) + 10;
                        } else {
                            this.fish_direction = (float)((double)this.fish_direction + this.rand.nextGaussian() * 4.0);
                            float f1 = this.fish_direction * 0.017453292f;
                            float f7 = MathHelper.sin((float)f1);
                            float f2 = MathHelper.cos((float)f1);
                            double d11 = this.posX + (double)(f7 * (float)this.ticks_catchable * 0.1f);
                            double d5 = (float)MathHelper.floor((double)this.getEntityBoundingBox().minY) + 1.0f;
                            double d6 = this.posZ + (double)(f2 * (float)this.ticks_catchable * 0.1f);
                            if (this.rand.nextFloat() < 0.15f) {
                                worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_BUBBLE, d11, d5 - 0.10000000149011612, d6, 1, (double)f7, 0.1, (double)f2, 0.0);
                            }
                            float f3 = f7 * 0.04f;
                            float f4 = f2 * 0.04f;
                            worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_WAKE, d11, d5, d6, 0, (double)f4, 0.01, (double)(- f3), 1.0);
                            worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_WAKE, d11, d5, d6, 0, (double)(- f4), 0.01, (double)f3, 1.0);
                        }
                    } else if (this.fish_wait_time > 0) {
                        this.fish_wait_time -= k;
                        float f1 = 0.15f;
                        if (this.fish_wait_time < 20) {
                            f1 = (float)((double)f1 + (double)(20 - this.fish_wait_time) * 0.05);
                        } else if (this.fish_wait_time < 40) {
                            f1 = (float)((double)f1 + (double)(40 - this.fish_wait_time) * 0.02);
                        } else if (this.fish_wait_time < 60) {
                            f1 = (float)((double)f1 + (double)(60 - this.fish_wait_time) * 0.01);
                        }
                        if (this.rand.nextFloat() < f1) {
                            float f7 = (this.rand.nextFloat() * 360.0f) * 0.017453292f;
                            float f2 = this.rand.nextFloat() * 35.0f + 25.0f;
                            double d11 = this.posX + (double)(MathHelper.sin((float)f7) * f2 * 0.1f);
                            double d5 = (float)MathHelper.floor((double)this.getEntityBoundingBox().minY) + 1.0f;
                            double d6 = this.posZ + (double)(MathHelper.cos((float)f7) * f2 * 0.1f);
                            worldserver.spawnParticle(net.minecraft.util.EnumParticleTypes.WATER_SPLASH, d11, d5, d6, 2 + this.rand.nextInt(2), 0.10000000149011612, 0.0, 0.10000000149011612, 0.0);
                        }
                        if (this.fish_wait_time <= 0) {
                            this.fish_direction = this.rand.nextFloat() * 360.0f;
                            this.ticks_catchable = this.rand.nextInt(101) + 100;
                        }
                    } else {
                        this.fish_wait_time = this.rand.nextInt(251) + 50;
                        this.fish_wait_time -= EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.LURE, getUltimateRodStack(this.getAngler())) * 20 * 5;
                    }
                    if (this.fish_on_hook > 0) {
                        this.motionY -= (double)(this.rand.nextFloat() * this.rand.nextFloat() * this.rand.nextFloat()) * 0.2;
                    }
                }
                d2 = d10 * 2.0 - 1.0;
                this.motionY += 0.03999999910593033 * d2;
                if (d10 > 0.0) {
                    f6 = (float)((double)f6 * 0.9);
                    this.motionY *= 0.8;
                }
                this.motionX *= (double)f6;
                this.motionY *= (double)f6;
                this.motionZ *= (double)f6;
                this.setPosition(this.posX, this.posY, this.posZ);
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.setShort("xTile", (short)this.field_146037_g);
        par1NBTTagCompound.setShort("yTile", (short)this.field_146048_h);
        par1NBTTagCompound.setShort("zTile", (short)this.field_146050_i);
        par1NBTTagCompound.setByte("inTile", (byte)Block.getIdFromBlock((Block)this.field_146046_j));
        par1NBTTagCompound.setByte("shake", (byte)this.hookShake);
        par1NBTTagCompound.setByte("inGround", (byte)(this.field_146051_au ? 1 : 0));
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        this.field_146037_g = par1NBTTagCompound.getShort("xTile");
        this.field_146048_h = par1NBTTagCompound.getShort("yTile");
        this.field_146050_i = par1NBTTagCompound.getShort("zTile");
        this.field_146046_j = Block.getBlockById((int)(par1NBTTagCompound.getByte("inTile") & 255));
        this.hookShake = par1NBTTagCompound.getByte("shake") & 255;
        this.field_146051_au = par1NBTTagCompound.getByte("inGround") == 1;
    }

    @SideOnly(value=Side.CLIENT)
    public float getShadowSize() {
        return 0.0f;
    }

    public int handleHookRetraction() {
        if (this.world.isRemote) {
            return 0;
        }
        int b0 = 0;
        if (this.field_146043_c != null) {
            double d0 = this.getAngler().posX - this.posX;
            double d2 = this.getAngler().posY - this.posY;
            double d4 = this.getAngler().posZ - this.posZ;
            double d6 = MathHelper.sqrt((double)(d0 * d0 + d2 * d2 + d4 * d4));
            double d8 = 0.1;
            this.field_146043_c.motionX += d0 * d8;
            this.field_146043_c.motionY += d2 * d8 + (double)MathHelper.sqrt((double)d6) * 0.08;
            this.field_146043_c.motionZ += d4 * d8;
            b0 = 3;
        } else if (this.fish_on_hook > 0) {
            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + 1.25, this.posZ, this.func_146033_f());
            double d1 = this.getAngler().posX - this.posX;
            double d3 = this.getAngler().posY - this.posY;
            double d5 = this.getAngler().posZ - this.posZ;
            double d7 = MathHelper.sqrt((double)(d1 * d1 + d3 * d3 + d5 * d5));
            double d9 = 0.1;
            entityitem.motionX = d1 * d9;
            entityitem.motionY = d3 * d9 + (double)MathHelper.sqrt((double)d7) * 0.08;
            entityitem.motionZ = d5 * d9;
            entityitem.setPickupDelay(0);
            this.world.spawnEntity((Entity)entityitem);
            this.getAngler().world.spawnEntity((Entity)new EntityXPOrb(this.getAngler().world, this.getAngler().posX, this.getAngler().posY + 0.5, this.getAngler().posZ + 0.5, this.rand.nextInt(6) + 1));
            b0 = 1;
        }
        if (this.field_146051_au) {
            b0 = 2;
        }
        this.setDead();
        this.getAngler().fishEntity = null;
        return b0;
    }

    private ItemStack func_146033_f() {
        float f = this.world.rand.nextFloat();
        int i = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.LUCK_OF_THE_SEA, getUltimateRodStack(this.getAngler()));
        int j = EnchantmentHelper.getEnchantmentLevel(net.minecraft.init.Enchantments.LURE, getUltimateRodStack(this.getAngler()));
        float f1 = 0.1f - (float)i * 0.025f - (float)j * 0.01f;
        float f2 = 0.05f + (float)i * 0.01f - (float)j * 0.01f;
        f1 = MathHelper.clamp((float)f1, (float)0.0f, (float)1.0f);
        f2 = MathHelper.clamp((float)f2, (float)0.0f, (float)1.0f);
        Block bid = this.world.getBlockState(new net.minecraft.util.math.BlockPos((int)this.posX, (int)this.posY, (int)this.posZ)).getBlock();
        if (this.isInLava() || bid == Blocks.LAVA || bid == Blocks.FLOWING_LAVA) {
            this.getAngler().addStat(StatList.FISH_CAUGHT, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (java.util.List)chaospersists_lava_fish)).getItemStack(this.rand);
        }
        if (f < f1) {
            this.getAngler().addStat(StatList.FISH_CAUGHT, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (java.util.List)field_146039_d)).getItemStack(this.rand);
        }
        if ((f -= f1) < f2) {
            this.getAngler().addStat(StatList.FISH_CAUGHT, 1);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (java.util.List)field_146041_e)).getItemStack(this.rand);
        }
        float f3 = this.world.rand.nextFloat();
        this.getAngler().addStat(StatList.FISH_CAUGHT, 1);
        if (f3 < 0.5f) {
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (java.util.List)field_146036_f)).getItemStack(this.rand);
        }
        return ((WeightedRandomFishable)WeightedRandom.getRandomItem((Random)this.rand, (java.util.List)chaospersists_fish)).getItemStack(this.rand);
    }

    public void setDead() {
        super.setDead();
        if (this.getAngler() != null) {
            this.getAngler().fishEntity = null;
        }
    }
}

