package com.astryxion.chaospersists.item;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ExperienceCatcher extends Item {

    public ExperienceCatcher(int i) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
                                      World world,
                                      BlockPos pos,
                                      EnumHand hand,
                                      EnumFacing facing,
                                      float hitX,
                                      float hitY,
                                      float hitZ) {

        ItemStack stack = player.getHeldItem(hand);
        player.swingArm(hand);

        if (!world.isRemote) {

            AxisAlignedBB bb = new AxisAlignedBB(
                    pos.getX() - 0.5D + hitX,
                    pos.getY(),
                    pos.getZ() - 0.5D + hitZ,
                    pos.getX() + 0.5D + hitX,
                    pos.getY() + 2.0D,
                    pos.getZ() + 0.5D + hitZ
            );

            List<EntityXPOrb> xpOrbs = world.getEntitiesWithinAABB(EntityXPOrb.class, bb);

            for (EntityXPOrb orb : xpOrbs) {

                if (orb.getXpValue() < 3 || world.rand.nextInt(5) == 1) {
                    continue;
                }

                orb.setDead();

                spawnItem(world, pos, hitX, hitZ, new ItemStack(Items.EXPERIENCE_BOTTLE));
                spawnItem(world, pos, hitX, hitZ, new ItemStack(Items.STRING));
                spawnItem(world, pos, hitX, hitZ, new ItemStack(Items.STICK));

                if (!player.capabilities.isCreativeMode) {
                    stack.shrink(1);
                }

                return EnumActionResult.SUCCESS;
            }

            // No XP found — drop the catcher itself
            spawnItem(world, pos, hitX, hitZ,
                    new ItemStack(ChaosPersists.MyExperienceCatcher));

            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
        }

        return EnumActionResult.SUCCESS;
    }

    private void spawnItem(World world, BlockPos pos, float hitX, float hitZ, ItemStack stack) {
        EntityItem entityItem = new EntityItem(
                world,
                pos.getX() + hitX,
                pos.getY() + 1.0D,
                pos.getZ() + hitZ,
                stack
        );
        world.spawnEntity(entityItem);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world,
                                                    EntityPlayer player,
                                                    EnumHand hand) {
        player.swingArm(hand);
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }
}
