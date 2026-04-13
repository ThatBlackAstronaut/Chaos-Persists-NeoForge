package com.astryxion.chaospersists.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;

public class BigHammer extends ItemSword {

    public BigHammer(Item.ToolMaterial material) {
        super(material);

        this.setMaxStackSize(1);
        this.setMaxDamage(9000);
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    /**
     * Big hammer launches enemies upward on hit
     */
    @Override
    public boolean hitEntity(ItemStack stack,
                             EntityLivingBase target,
                             EntityLivingBase attacker) {

        if (!target.world.isRemote) {

            // Strong vertical launch
            target.motionY += 1.2D;

            // Slight horizontal knockback away from attacker
            double dx = target.posX - attacker.posX;
            double dz = target.posZ - attacker.posZ;

            double distance = MathHelper.sqrt(dx * dx + dz * dz);
            if (distance > 0) {
                target.motionX += (dx / distance) * 0.8D;
                target.motionZ += (dz / distance) * 0.8D;
            }

            target.velocityChanged = true;
        }

        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 3000;
    }
}
