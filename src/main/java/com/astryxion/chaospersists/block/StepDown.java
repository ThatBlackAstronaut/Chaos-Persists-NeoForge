package com.astryxion.chaospersists.block;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StepDown extends Item {

    public StepDown(int i) {
        this.setMaxStackSize(16);
        this.setCreativeTab(CreativeTabs.TOOLS);
    }

    private static int stepOctantFromYaw(float yawDegrees) {
        float f = yawDegrees + 22.5f;
        f = (f % 360.0f + 360.0f) % 360.0f;
        return (int) (f / 45.0f);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer Player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = Player.getHeldItem(hand);
        int deltax = 0;
        int deltaz = 0;
        int length = 33;
        int x = pos.getX();
        int y = pos.getY() + 1;
        int z = pos.getZ();
        switch (stepOctantFromYaw(Player.rotationYaw)) {
            case 0:
                deltax = 0;
                deltaz = 1;
                break;
            case 1:
                deltax = -1;
                deltaz = 1;
                break;
            case 2:
                deltax = -1;
                deltaz = 0;
                break;
            case 3:
                deltax = -1;
                deltaz = -1;
                break;
            case 4:
                deltax = 0;
                deltaz = -1;
                break;
            case 5:
                deltax = 1;
                deltaz = -1;
                break;
            case 6:
                deltax = 1;
                deltaz = 0;
                break;
            case 7:
                deltax = 1;
                deltaz = 1;
                break;
            default:
                break;
        }
        if (deltax == 0 && deltaz == 0) {
            return EnumActionResult.FAIL;
        }
        world.playSound(null, Player.posX, Player.posY, Player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, Player.getSoundCategory(), 1.0f, 1.5f);
        if (world.isRemote) {
            for (int var3 = 0; var3 < 6; ++var3) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) ((float) x + world.rand.nextFloat() - world.rand.nextFloat()), (double) ((float) y + world.rand.nextFloat()), (double) ((float) z + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, (double) ((float) x + world.rand.nextFloat() - world.rand.nextFloat()), (double) ((float) y + world.rand.nextFloat()), (double) ((float) z + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
                world.spawnParticle(EnumParticleTypes.REDSTONE, (double) ((float) x + world.rand.nextFloat() - world.rand.nextFloat()), (double) ((float) y + world.rand.nextFloat()), (double) ((float) z + world.rand.nextFloat() - world.rand.nextFloat()), 0.0, 0.0, 0.0);
            }
            return EnumActionResult.SUCCESS;
        }
        Block bid;
        for (int k = 1; k < length && (bid = world.getBlockState(new BlockPos(x + k * deltax, y - k - 1, z + k * deltaz)).getBlock()) == Blocks.AIR; ++k) {
            world.setBlockState(new BlockPos(x + k * deltax, y - k - 1, z + k * deltaz), Blocks.COBBLESTONE.getDefaultState(), 2);
            if ((k - 1) % 8 != 0 || (bid = world.getBlockState(new BlockPos(x + k * deltax, y - k, z + k * deltaz)).getBlock()) != Blocks.AIR) {
                continue;
            }
            world.setBlockState(new BlockPos(x + k * deltax, y - k, z + k * deltaz), ChaosPersists.ExtremeTorch.getDefaultState(), 2);
        }
        if (!Player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }
        return EnumActionResult.SUCCESS;
    }
}
