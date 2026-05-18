package com.astryxion.chaospersists.util;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

/** Crystal dimension removed; spawn checks always false until dimension 5 returns. */
public final class CrystalDimensionSpawnHelper {
    private CrystalDimensionSpawnHelper() {}

    public static boolean isCrystalDimension(Level world) {
        return false;
    }

    public static boolean isCrystalDimension(LevelAccessor world) {
        return false;
    }
}
