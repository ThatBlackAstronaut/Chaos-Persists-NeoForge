package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.core.ChaosPersists;
import net.minecraft.world.World;

/**
 * Spawn checks shared by mobs that should naturally appear in the crystal dimension (dim 5).
 */
public final class CrystalDimensionSpawnHelper {

    private CrystalDimensionSpawnHelper() {
    }

    public static boolean isCrystalDimension(World world) {
        return world != null && world.provider != null
            && world.provider.getDimension() == ChaosPersists.getDimension(5);
    }
}
