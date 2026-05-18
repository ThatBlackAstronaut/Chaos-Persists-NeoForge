/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.BerthaHit
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.Girlfriend
 *  com.astryxion.chaospersists.Slice
 *  net.minecraft.client.renderer.texture.IIconRegister
 *  com.astryxion.chaospersists.compat.minecraft.creativetab.CreativeTabs
 *  net.minecraft.enchantment.Enchantment
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.Item
 *  net.minecraft.item.Item$ToolMaterial
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.ItemSword
 *  net.minecraft.util.IIcon
 *  com.astryxion.chaospersists.compat.minecraft.world.World
 */
package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.BerthaHit;
import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Girlfriend;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Slice extends SwordItem {

    public Slice(Tier par2EnumToolMaterial) {
        super(par2EnumToolMaterial, 3, -2.4f, new Properties().stacksTo(1).durability(2600));
    }

    @Override
    public void onCraftedBy(ItemStack par1ItemStack, Level par2World, Player par3EntityPlayer) {
        ensureEnchantments(par1ItemStack);
    }

    private void ensureEnchantments(ItemStack stack) {
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SHARPNESS, stack) <= 0) {
            stack.enchant(Enchantments.SHARPNESS, 5);
            stack.enchant(Enchantments.BANE_OF_ARTHROPODS, 1);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level par2World, Entity par3Entity, int par4, boolean par5) {
        ensureEnchantments(stack);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity != null && (entity instanceof Player || entity instanceof Girlfriend || entity instanceof Boyfriend)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entityLiving) {
        if (entityLiving != null && entityLiving instanceof Player p) {
            double xzoff = 2.0;
            double yoff = 1.55;
            BerthaHit lb = new BerthaHit(ChaosPersists.ENTITY_TYPE_BERTHA_HIT.get(), p, p.level());
            lb.setPos(
                    p.getX() - xzoff * Mth.sin((float) Math.toRadians(p.getYHeadRot())),
                    p.getY() + yoff,
                    p.getZ() + xzoff * Mth.cos((float) Math.toRadians(p.getYHeadRot())));
            lb.setYRot(p.getYHeadRot());
            lb.setXRot(p.getXRot());
            Vec3 motion = lb.getDeltaMovement();
            lb.setDeltaMovement(motion.x * 2.0, motion.y * 2.0, motion.z * 2.0);
            p.level().addFreshEntity(lb);
            stack.hurtAndBreak(1, p, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return false;
    }

    public String getMaterialName() {
        return "Uranium/Titanium";
    }

    @Override
    public boolean hurtEnemy(ItemStack par1ItemStack, LivingEntity par2EntityLiving, LivingEntity par3EntityLiving) {
        par1ItemStack.hurtAndBreak(1, par3EntityLiving, e -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public int getUseDuration(ItemStack par1ItemStack) {
        return 9000;
    }
}
