/*
 * 1.12.2: throwable items use {@code onItemRightClick(World, EntityPlayer, EnumHand)} and must call
 * {@link net.minecraft.entity.projectile.EntityThrowable#shoot} after construction (same as snowballs / eggs).
 */
package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.EntityThrownRock;
import com.astryxion.chaospersists.entity.RockBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRock extends Item {

    public ItemRock(int i) {
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.COMBAT);
    }

    private int rockTypeForStack(ItemStack stack) {
        Item it = stack.getItem();
        if (it == ChaosPersists.MySmallRock) {
            return 1;
        }
        if (it == ChaosPersists.MyRock) {
            return 2;
        }
        if (it == ChaosPersists.MyRedRock) {
            return 3;
        }
        if (it == ChaosPersists.MyGreenRock) {
            return 4;
        }
        if (it == ChaosPersists.MyBlueRock) {
            return 5;
        }
        if (it == ChaosPersists.MyPurpleRock) {
            return 6;
        }
        if (it == ChaosPersists.MySpikeyRock) {
            return 7;
        }
        if (it == ChaosPersists.MyTNTRock) {
            return 8;
        }
        if (it == ChaosPersists.MyCrystalRedRock) {
            return 9;
        }
        if (it == ChaosPersists.MyCrystalGreenRock) {
            return 10;
        }
        if (it == ChaosPersists.MyCrystalBlueRock) {
            return 11;
        }
        if (it == ChaosPersists.MyCrystalTNTRock) {
            return 12;
        }
        return 0;
    }

    private void applyRockTypeToMob(RockBase r, ItemStack stack) {
        int t = rockTypeForStack(stack);
        if (t != 0) {
            r.placeRock(t);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        int type = rockTypeForStack(stack);
        if (type == 0) {
            return new ActionResult<>(EnumActionResult.PASS, stack);
        }

        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        world.playSound(null, player.posX, player.posY, player.posZ,
                SoundEvents.ENTITY_SNOWBALL_THROW,
                SoundCategory.NEUTRAL,
                0.5F,
                0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
            EntityThrownRock rock = new EntityThrownRock(world, player, type);
            rock.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(rock);
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        int x = pos.getX();
        int z = pos.getZ();
        if (x < 0) {
            ++x;
        }
        if (z < 0) {
            ++z;
        }

        if (!world.isRemote) {
            Entity e = spawnPlacedRock(world, (double) x, (double) pos.getY() + 1.01, (double) z);
            if (e instanceof RockBase) {
                applyRockTypeToMob((RockBase) e, stack);
            }
        }

        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        return EnumActionResult.SUCCESS;
    }

    /** Same centering as 1.7.10 {@code spawnCreature}; registry id {@code chaospersists:rock}. */
    private Entity spawnPlacedRock(World world, double par2, double par4, double par6) {
        Entity entity = EntityList.createEntityByIDFromName(new ResourceLocation("chaospersists", "rock"), world);
        if (entity == null) {
            return null;
        }
        if (par2 > 0.0) {
            par2 += 0.5;
        }
        if (par2 < 0.0) {
            par2 -= 0.5;
        }
        if (par6 > 0.0) {
            par6 += 0.5;
        }
        if (par6 < 0.0) {
            par6 -= 0.5;
        }
        entity.setLocationAndAngles(par2, par4 + 0.01, par6, world.rand.nextFloat() * 360.0F, 0.0F);
        world.spawnEntity(entity);
        if (entity instanceof EntityLiving) {
            ((EntityLiving) entity).playLivingSound();
        }
        return entity;
    }
}
