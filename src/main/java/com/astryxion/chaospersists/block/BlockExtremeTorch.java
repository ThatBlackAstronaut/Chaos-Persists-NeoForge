package com.astryxion.chaospersists.block;

import com.astryxion.chaospersists.util.MyUtils;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import org.joml.Vector3f;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockExtremeTorch extends ChaosDirectionalTorchBlock {

    private static final DustParticleOptions RED_DUST =
            new DustParticleOptions(new Vector3f(1.0f, 0.0f, 0.0f), 1.0f);

    public BlockExtremeTorch() {
        super(
                net.minecraft.world.level.block.Block.Properties.of().noCollission().instabreak().lightLevel(state -> 15).sound(net.minecraft.world.level.block.SoundType.WOOD),
                ParticleTypes.FLAME);
    }

    @Override
    public String getDescriptionId() {
        return Util.makeDescriptionId("block", BuiltInRegistries.BLOCK.getKey(this));
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, net.minecraft.util.RandomSource rand) {
        Direction facing = stateIn.getValue(FACING);
        double d0 = pos.getX() + 0.5D;
        double d1 = pos.getY() + 0.7D;
        double d2 = pos.getZ() + 0.5D;
        if (facing.getAxis().isHorizontal()) {
            Direction attach = facing.getOpposite();
            d0 += attach.getStepX() * 0.3D;
            d1 += 0.22D;
            d2 += attach.getStepZ() * 0.3D;
        } else if (facing == Direction.UP) {
            d1 -= 0.1D;
        } else {
            d1 += 0.15D;
        }
        worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        worldIn.addParticle(RED_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        int par2 = pos.getX();
        int par3 = pos.getY();
        int par4 = pos.getZ();
        int x = par2;
        int y = par3;
        int z = par4;
        boolean found = false;

        Block eyeBlock =
                ForgeRegistries.BLOCKS.getValue(net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("chaospersists", "eyeofender"));
        if (eyeBlock != null && world.getBlockState(new BlockPos(x, y - 1, z)).is(eyeBlock)) {
            block0:
            for (int tries = 0; tries < 100 && !found; ++tries) {
                x = world.getRandom().nextInt(2) == 0
                        ? par2 + 4 + world.getRandom().nextInt(3) - world.getRandom().nextInt(3)
                        : par2 - 4 + world.getRandom().nextInt(3) - world.getRandom().nextInt(3);
                z = world.getRandom().nextInt(2) == 0
                        ? par4 + 4 + world.getRandom().nextInt(3) - world.getRandom().nextInt(3)
                        : par4 - 4 + world.getRandom().nextInt(3) - world.getRandom().nextInt(3);
                for (y = par3 - 2; y <= par3 + 2; ++y) {
                    BlockPos below = new BlockPos(x, y - 1, z);
                    BlockState belowState = world.getBlockState(below);
                    if (!belowState.isFaceSturdy(world, below, Direction.UP)
                            || world.getBlockState(new BlockPos(x, y, z)).getBlock() != Blocks.AIR
                            || world.getBlockState(new BlockPos(x, y + 1, z)).getBlock() != Blocks.AIR) {
                        continue;
                    }
                    found = true;
                    continue block0;
                }
            }
            if (found) {
                if (!world.isClientSide) {
                    spawnCreature(world, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("chaospersists", "cephadrome"), (double) x + 0.5D, (double) y + 0.01D, (double) z + 0.5D);
                } else {
                    for (int var3 = 0; var3 < 16; ++var3) {
                        world.addParticle(
                                ParticleTypes.SMOKE,
                                (float) par2 + world.getRandom().nextFloat() - world.getRandom().nextFloat(),
                                (float) par3 + world.getRandom().nextFloat(),
                                (float) par4 + world.getRandom().nextFloat() - world.getRandom().nextFloat(),
                                0.0,
                                0.0,
                                0.0);
                        world.addParticle(
                                ParticleTypes.EXPLOSION,
                                (float) par2 + world.getRandom().nextFloat() - world.getRandom().nextFloat(),
                                (float) par3 + world.getRandom().nextFloat(),
                                (float) par4 + world.getRandom().nextFloat() - world.getRandom().nextFloat(),
                                0.0,
                                0.0,
                                0.0);
                        world.addParticle(
                                RED_DUST,
                                (float) par2 + world.getRandom().nextFloat() - world.getRandom().nextFloat(),
                                (float) par3 + world.getRandom().nextFloat(),
                                (float) par4 + world.getRandom().nextFloat() - world.getRandom().nextFloat(),
                                0.0,
                                0.0,
                                0.0);
                    }
                }
                if (placer != null) {
                    world.playSound(null, placer.getX(), placer.getY(), placer.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.2f + 0.9f);
                } else {
                    world.playSound(null, par2, par3, par4, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.2f + 0.9f);
                }
                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
        super.setPlacedBy(world, pos, state, placer, stack);
    }

    private static Entity spawnCreature(Level world, net.minecraft.resources.ResourceLocation entityId, double px, double py, double pz) {
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(entityId);
        if (type == null || !(world instanceof ServerLevel serverLevel)) {
            return null;
        }
        Entity entity = type.create(serverLevel);
        if (entity != null) {
            entity.moveTo(px, py, pz, world.getRandom().nextFloat() * 360.0f, 0.0f);
            serverLevel.addFreshEntity(entity);
            if (entity instanceof Mob mob) {
                MyUtils.playAmbientSound(mob);
            }
        }
        return entity;
    }
}
