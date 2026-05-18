package com.astryxion.chaospersists.world.ore;

import com.astryxion.chaospersists.entity.EntityAnt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class OreSalt extends Block {

    public OreSalt() {
        super(Block.Properties.of()
                .strength(5.0f, 2.0f)
                .sound(SoundType.STONE)
                .requiresCorrectToolForDrops());
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof EntityAnt) {
            entity.hurt(level.damageSources().cactus(), 5.0f);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (entity instanceof EntityAnt) {
            entity.hurt(level.damageSources().cactus(), 5.0f);
        }
        super.stepOn(level, pos, state, entity);
    }
}
