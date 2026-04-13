package com.astryxion.chaospersists.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Places a hoverboard entity. 1.7.10 used {@code EntityList.createEntityByName("Hoverboard", world)};
 * we construct {@link Elevator} directly so spawn never depends on registry lookup succeeding.
 */
public class ItemElevator extends Item {

    public ItemElevator(int par1) {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.TRANSPORTATION);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote) {
            return EnumActionResult.SUCCESS;
        }
        Elevator elevator = new Elevator(world);
        double x = (double) pos.getX() + 0.5D;
        double y = (double) pos.getY() + 1.2D;
        double z = (double) pos.getZ() + 0.5D;
        elevator.setLocationAndAngles(x, y, z, world.rand.nextFloat() * 360.0f, 0.0f);
        world.spawnEntity(elevator);
        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }
        return EnumActionResult.SUCCESS;
    }
}
