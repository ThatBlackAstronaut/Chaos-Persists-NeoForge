/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.Girlfriend
 *  com.astryxion.chaospersists.ChaosPersists
 *  com.astryxion.chaospersists.UltimateSword
 *  com.astryxion.chaospersists.WeaponStats
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockLeaves
 *  net.minecraft.block.BlockTallGrass
 *  net.minecraft.block.material.Material
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  net.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Blocks
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.IIcon
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.util.WeaponStats;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class UltimateSword
extends ItemSword {
    private int swingtimer = 0;
    private boolean leaf = false;

    public UltimateSword(Item.ToolMaterial par2EnumToolMaterial) {
        super(par2EnumToolMaterial);
        this.maxStackSize = 1;
        this.setMaxDamage(3000);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (this == ChaosPersists.MyChainsaw) {
            return;
        }
        if (this != ChaosPersists.MyBattleAxe) {
            par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(16), ChaosPersists.UltimateSwordMagic);
            par1ItemStack.addEnchantment(net.minecraft.init.Enchantments.SMITE, ChaosPersists.UltimateSwordMagic);
            par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(18), ChaosPersists.UltimateSwordMagic);
            par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(19), 1 + ChaosPersists.UltimateSwordMagic / 2);
            par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(21), 1 + ChaosPersists.UltimateSwordMagic / 2);
            par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(34), 1 + ChaosPersists.UltimateSwordMagic / 2);
            par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(20), 1 + ChaosPersists.UltimateSwordMagic / 3);
        } else {
            par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(21), 1 + ChaosPersists.UltimateSwordMagic / 2);
            par1ItemStack.addEnchantment(Enchantment.getEnchantmentByID(34), 1 + ChaosPersists.UltimateSwordMagic / 2);
        }
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        if (this == ChaosPersists.MyChainsaw && entityLiving != null && this.swingtimer == 0) {
            entityLiving.playSound(com.astryxion.chaospersists.core.ChaosSounds.CHAINSAWSHORT, 1.0f, entityLiving.world.rand.nextFloat() * 0.2f + 0.9f);
            this.swingtimer = 50;
        }
        return false;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        if (this == ChaosPersists.MyChainsaw) {
            return;
        }
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(21), (ItemStack)stack);
        if (lvl <= 0) {
            if (this != ChaosPersists.MyBattleAxe) {
                stack.addEnchantment(Enchantment.getEnchantmentByID(16), ChaosPersists.UltimateSwordMagic);
                stack.addEnchantment(net.minecraft.init.Enchantments.SMITE, ChaosPersists.UltimateSwordMagic);
                stack.addEnchantment(Enchantment.getEnchantmentByID(18), ChaosPersists.UltimateSwordMagic);
                stack.addEnchantment(Enchantment.getEnchantmentByID(19), 1 + ChaosPersists.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.getEnchantmentByID(21), 1 + ChaosPersists.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.getEnchantmentByID(34), 1 + ChaosPersists.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.getEnchantmentByID(20), 1 + ChaosPersists.UltimateSwordMagic / 3);
            } else {
                stack.addEnchantment(Enchantment.getEnchantmentByID(21), 1 + ChaosPersists.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.getEnchantmentByID(34), 1 + ChaosPersists.UltimateSwordMagic / 2);
            }
        }
    }

    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
        if (this == ChaosPersists.MyChainsaw) {
            if (this.swingtimer > 0) {
                --this.swingtimer;
            }
            if (par2World.isRemote && this.swingtimer > 0) {
                float f = 1.0f;
                float dx = (float)((double)f * Math.cos(Math.toRadians(par3Entity.rotationYaw + 90.0f + 45.0f)));
                float dz = (float)((double)f * Math.sin(Math.toRadians(par3Entity.rotationYaw + 90.0f + 45.0f)));
                if (par2World.rand.nextInt(8) == 0) {
                    par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.FLAME, par3Entity.posX + (double)dx, par3Entity.posY, par3Entity.posZ + (double)dz, (double)((par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0f), (double)(par2World.rand.nextFloat() / 10.0f), (double)((par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0f));
                }
                if (par2World.rand.nextInt(2) == 0) {
                    par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.SMOKE_NORMAL, par3Entity.posX + (double)dx, par3Entity.posY, par3Entity.posZ + (double)dz, (double)((par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0f), (double)(par2World.rand.nextFloat() / 10.0f), (double)((par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0f));
                }
                if (par2World.rand.nextInt(10) == 0) {
                    par2World.spawnParticle(net.minecraft.util.EnumParticleTypes.FIREWORKS_SPARK, par3Entity.posX + (double)dx, par3Entity.posY, par3Entity.posZ + (double)dz, (double)((par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0f), (double)(par2World.rand.nextFloat() / 5.0f), (double)((par2World.rand.nextFloat() - par2World.rand.nextFloat()) / 20.0f));
                }
            }
            return;
        }
        int lvl = EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(21), (ItemStack)stack);
        if (lvl <= 0) {
            if (this != ChaosPersists.MyBattleAxe) {
                stack.addEnchantment(Enchantment.getEnchantmentByID(16), ChaosPersists.UltimateSwordMagic);
                stack.addEnchantment(net.minecraft.init.Enchantments.SMITE, ChaosPersists.UltimateSwordMagic);
                stack.addEnchantment(Enchantment.getEnchantmentByID(18), ChaosPersists.UltimateSwordMagic);
                stack.addEnchantment(Enchantment.getEnchantmentByID(19), 1 + ChaosPersists.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.getEnchantmentByID(21), 1 + ChaosPersists.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.getEnchantmentByID(34), 1 + ChaosPersists.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.getEnchantmentByID(20), 1 + ChaosPersists.UltimateSwordMagic / 3);
            } else {
                stack.addEnchantment(Enchantment.getEnchantmentByID(21), 1 + ChaosPersists.UltimateSwordMagic / 2);
                stack.addEnchantment(Enchantment.getEnchantmentByID(34), 1 + ChaosPersists.UltimateSwordMagic / 2);
            }
        }
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
        par1ItemStack.damageItem(1, (EntityLivingBase)par3EntityLiving);
        return true;
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        if (entity != null && ChaosPersists.ultimate_sword_pvp == 0) {
            EntityTameable t;
            if (entity instanceof EntityPlayer || entity instanceof Girlfriend || entity instanceof Boyfriend) {
                return true;
            }
            if (entity instanceof EntityTameable && (t = (EntityTameable)entity).isTamed()) {
                return true;
            }
        }
        if (this == ChaosPersists.MyChainsaw && player != null) {
            this.findSomethingToHit(player);
        }
        return false;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 9000;
    }

    private void findSomethingToHit(EntityPlayer player) {
        List var5 = player.world.getEntitiesWithinAABB(EntityLivingBase.class, player.getEntityBoundingBox().expand(5.0, 5.0, 5.0));
        Iterator var2 = var5.iterator();
        Entity var3 = null;
        EntityLivingBase var4 = null;
        while (var2.hasNext()) {
            var3 = (Entity)var2.next();
            var4 = (EntityLivingBase)var3;
            if (!this.isSuitableTarget(var4, false, player)) continue;
            var4.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)player), (float)ChaosPersists.chainsaw_stats.damage);
        }
    }

    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2, EntityPlayer player) {
        if (par1EntityLiving == null) {
            return false;
        }
        if (par1EntityLiving == player) {
            return false;
        }
        if (!par1EntityLiving.isEntityAlive()) {
            return false;
        }
        if (ChaosPersists.ultimate_sword_pvp == 0) {
            EntityTameable t;
            if (par1EntityLiving instanceof EntityPlayer || par1EntityLiving instanceof Girlfriend || par1EntityLiving instanceof Boyfriend) {
                return false;
            }
            if (par1EntityLiving instanceof EntityTameable && (t = (EntityTameable)par1EntityLiving).isTamed()) {
                return false;
            }
        }
        if (!this.MyCanSee(par1EntityLiving, player)) {
            return false;
        }
        return true;
    }

    public boolean MyCanSee(EntityLivingBase e, EntityPlayer player) {
        int nblks = 10;
        double cx = player.posX;
        double cz = player.posZ;
        float startx = (float)cx;
        float starty = (float)(player.posY + 1.399999976158142);
        float startz = (float)cz;
        float dx = (float)((e.posX - (double)startx) / 10.0);
        float dy = (float)((e.posY + (double)(e.height / 2.0f) - (double)starty) / 10.0);
        float dz = (float)((e.posZ - (double)startz) / 10.0);
        if ((double)Math.abs(dx) > 1.0) {
            dy /= Math.abs(dx);
            dz /= Math.abs(dx);
            nblks = (int)((float)nblks * Math.abs(dx));
            if (dx > 1.0f) {
                dx = 1.0f;
            }
            if (dx < -1.0f) {
                dx = -1.0f;
            }
        }
        if ((double)Math.abs(dy) > 1.0) {
            dx /= Math.abs(dy);
            dz /= Math.abs(dy);
            nblks = (int)((float)nblks * Math.abs(dy));
            if (dy > 1.0f) {
                dy = 1.0f;
            }
            if (dy < -1.0f) {
                dy = -1.0f;
            }
        }
        if ((double)Math.abs(dz) > 1.0) {
            dy /= Math.abs(dz);
            dx /= Math.abs(dz);
            nblks = (int)((float)nblks * Math.abs(dz));
            if (dz > 1.0f) {
                dz = 1.0f;
            }
            if (dz < -1.0f) {
                dz = -1.0f;
            }
        }
        for (int i = 0; i < nblks; ++i) {
            Block bid = player.world.getBlockState(new net.minecraft.util.math.BlockPos((int)(startx += dx), (int)(starty += dy), (int)(startz += dz))).getBlock();
            if (bid == Blocks.AIR) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        if (this == ChaosPersists.MyChainsaw) {
            return this.canCrush(state.getBlock());
        }
        return super.canHarvestBlock(state);
    }

    private boolean canCrush(Block blockID) {
        if (this == ChaosPersists.MyChainsaw) {
            if (blockID == Blocks.WEB) {
                return true;
            }
            if (blockID == Blocks.LOG || blockID == Blocks.LOG2) {
                return true;
            }
            if (blockID == Blocks.LEAVES || blockID == Blocks.LEAVES2) {
                return true;
            }
            if (blockID == Blocks.PLANKS) {
                return true;
            }
            if (blockID == Blocks.SAPLING) {
                return true;
            }
            if (blockID == Blocks.TALLGRASS) {
                return true;
            }
            if (blockID == Blocks.CACTUS) {
                return true;
            }
            if (blockID == ChaosPersists.CrystalPlanksBlock) {
                return true;
            }
            if (blockID == ChaosPersists.MyAppleLeaves) {
                return true;
            }
            if (blockID == ChaosPersists.MySkyTreeLog) {
                return true;
            }
            if (blockID == ChaosPersists.MyDT) {
                return true;
            }
            if (blockID == ChaosPersists.MyExperienceLeaves) {
                return true;
            }
            if (blockID == ChaosPersists.MyScaryLeaves) {
                return true;
            }
            if (blockID == ChaosPersists.MyCherryLeaves) {
                return true;
            }
            if (blockID == ChaosPersists.MyPeachLeaves) {
                return true;
            }
            if (blockID == ChaosPersists.MyCrystalLeaves) {
                return true;
            }
            if (blockID == ChaosPersists.MyCrystalLeaves2) {
                return true;
            }
            if (blockID == ChaosPersists.MyCrystalLeaves3) {
                return true;
            }
            if (blockID == ChaosPersists.MyCrystalTreeLog) {
                return true;
            }
            return false;
        }
        return blockID == Blocks.WEB;
    }

    private boolean isLeaves(Block blockID) {
        if (blockID == Blocks.WEB) {
            return true;
        }
        if (blockID == Blocks.LEAVES || blockID == Blocks.LEAVES2) {
            return true;
        }
        if (blockID == Blocks.SAPLING) {
            return true;
        }
        if (blockID == Blocks.TALLGRASS) {
            return true;
        }
        if (blockID == ChaosPersists.MyAppleLeaves) {
            return true;
        }
        if (blockID == ChaosPersists.MyExperienceLeaves) {
            return true;
        }
        if (blockID == ChaosPersists.MyScaryLeaves) {
            return true;
        }
        if (blockID == ChaosPersists.MyCherryLeaves) {
            return true;
        }
        if (blockID == ChaosPersists.MyPeachLeaves) {
            return true;
        }
        if (blockID == ChaosPersists.MyCrystalLeaves) {
            return true;
        }
        if (blockID == ChaosPersists.MyCrystalLeaves2) {
            return true;
        }
        if (blockID == ChaosPersists.MyCrystalLeaves3) {
            return true;
        }
        return false;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, net.minecraft.block.state.IBlockState state, net.minecraft.util.math.BlockPos pos, EntityLivingBase par7EntityLivingBase) {
        Block par3 = state.getBlock();
        int par4 = pos.getX();
        int par5 = pos.getY();
        int par6 = pos.getZ();
        if (this == ChaosPersists.MyChainsaw && !par2World.isRemote) {
            for (int i = -5; i <= 5; ++i) {
                for (int j = -5; j <= 10; ++j) {
                    for (int k = -5; k <= 5; ++k) {
                        Block bid = par2World.getBlockState(new net.minecraft.util.math.BlockPos(par4 + i, par5 + j, par6 + k)).getBlock();
                        if (this.leaf) {
                            if (!this.isLeaves(bid)) continue;
                            this.dropItemRand(par2World, Item.getItemFromBlock((Block)bid), 1, par4 + i, par5 + j, par6 + k);
                            par2World.setBlockState(new net.minecraft.util.math.BlockPos(par4 + i, par5 + j, par6 + k), Blocks.AIR.getDefaultState());
                            continue;
                        }
                        if (!this.canCrush(bid)) continue;
                        this.dropItemRand(par2World, Item.getItemFromBlock((Block)bid), 1, par4 + i, par5 + j, par6 + k);
                        par2World.setBlockState(new net.minecraft.util.math.BlockPos(par4 + i, par5 + j, par6 + k), Blocks.AIR.getDefaultState());
                    }
                }
            }
        }
        return super.onBlockDestroyed(par1ItemStack, par2World, state, pos, par7EntityLivingBase);
    }

    private ItemStack dropItemRand(World world, Item index, int par1, int x, int y, int z) {
        EntityItem var3 = null;
        ItemStack is = new ItemStack(index, par1, 0);
        var3 = new EntityItem(world, (double)(x + ChaosPersists.ChaosRand.nextInt(5) - ChaosPersists.ChaosRand.nextInt(5)), (double)y + 1.0 + (double)world.rand.nextInt(5), (double)(z + ChaosPersists.ChaosRand.nextInt(5) - ChaosPersists.ChaosRand.nextInt(5)), is);
        if (var3 != null) {
            world.spawnEntity((Entity)var3);
        }
        return is;
    }

    /**
     * 1.7.10 used {@code getStrVsBlock}; 1.12.2 uses {@link #getDestroySpeed(ItemStack, IBlockState)} for mining speed.
     */
    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        Block block = state.getBlock();
        if (this == ChaosPersists.MyChainsaw && block != null) {
            this.leaf = this.isLeaves(block);
            Material mat = state.getMaterial();
            if (mat == Material.WOOD || mat == Material.PLANTS || mat == Material.VINE) {
                return ChaosPersists.chainsaw_stats.efficiency;
            }
            if (this.canCrush(block)) {
                return ChaosPersists.chainsaw_stats.efficiency;
            }
        }
        return super.getDestroySpeed(stack, state);
    }
}

