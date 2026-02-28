# OreSpawn 1.7.10 → 1.12.2 Port – Build Notes

## Running the build

From a **normal Command Prompt or PowerShell** (not necessarily from inside Cursor), run:

```bat
cd c:\ModProjects\OreSpawn
gradlew.bat build
```

If you see **"Unable to get mutable Windows environment variable map"**, run `gradlew.bat` from a regular terminal (e.g. cmd.exe or Windows PowerShell) outside of Cursor’s sandbox. That error is a known Gradle/Windows environment issue in some contexts.

## What was done (mechanical port only)

- **Forge 1.12.2 workspace**: Added `build.gradle`, `gradle.properties`, `settings.gradle`, and Gradle wrapper for ForgeGradle 3.x and Minecraft 1.12.2-14.23.5.2859.
- **FML/Forge**: Replaced `cpw.mods.fml` with `net.minecraftforge.fml` across the codebase.
- **Minecraft APIs**:
  - `AxisAlignedBB`, `Vec3` → `net.minecraft.util.math.AxisAlignedBB`, `Vec3d` (and `Vec3` → `Vec3d`, coord access updated).
  - `MovingObjectPosition` → `RayTraceResult`; `blockX/Y/Z` → `getBlockPos().getX/Y/Z()`; `hitVec` unchanged.
  - `BiomeGenBase` → `Biome`.
  - `boundingBox` → `getEntityBoundingBox()`, `addCoord` → `expand`, `isVecInside` → `contains`.
  - `Blocks.air` → `Blocks.AIR`, `ridingEntity` → `getRidingEntity()`, `worldObj` → `world`.
  - `CreativeTabs.tab*` → `CreativeTabs.*` (e.g. `BUILDING_BLOCK`, `FOOD`, `TOOLS`).
  - `Material.rock` etc. → `Material.ROCK` etc.
  - `getBlock(x,y,z)` → `getBlockState(new BlockPos(x,y,z)).getBlock()`.
  - `isOpaqueCube()` → `isFullCube()`.
  - `BlockDispenser.dispenseBehaviorRegistry` → `BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY`.
  - `dropXpOnBlockBreak(World, int, int, int, int)` → `dropXpOnBlockBreak(World, BlockPos, int)`.
- **Registration**: Removed numeric block/item IDs from constructors; added `setRegistryName("orespawn", ...)` and `setTranslationKey`; `GameRegistry.registerBlock/registerItem(block, "name")` → `GameRegistry.findRegistry(Block.class).register(block)` (and same for Item). Removed `LanguageRegistry` (use lang files in 1.12). Removed `IRegistry` import.
- **Block/Item constructors**: Removed first `int` parameter from Block and Item subclasses (1.12 uses registry, no IDs).
- **Icons**: Removed `IIconRegister`/`IIcon` imports and `registerBlockIcons`/`registerIcons` methods (1.12 uses models/JSON).
- **Resources**: `mcmod.info` updated for OreSpawn 1.12.2.

## What you may still need to fix after first compile

- **Block/World API**: `chunk.getBlock`, `setBlockIDWithMetadataFast`, `markBlockForUpdate`, `notifyBlockChange`, and any other 1.7-style chunk/world setters need 1.12 equivalents (`BlockPos`, `IBlockState`, `World.setBlockState`, etc.).
- **Entity registration**: `EntityRegistry.registerModEntity` and related calls may need to be updated to 1.12 entity registration.
- **Config**: `Configuration` (Forge config) API may have small changes.
- **Rendering**: All `Render*` and `Model*` classes still use 1.7-style rendering; they need to be updated to 1.12’s `ModelLoader`, `BlockModelShapes`, and entity rendering.
- **Network**: `SimpleNetworkWrapper` and channel registration are similar but message handling may need 1.12 updates.
- **Biomes/Dimensions**: `BiomeGenBase`, `WorldProvider`, and `ChunkProvider` usages may need 1.12 biome/dimension registration and API tweaks.

Compilation is the current goal; runtime fixes can be done in a follow-up pass.

## runClient crash (NetworkRegistry NPE) – FIXED

`./gradlew runClient` used to crash with:

```text
NullPointerException at NetworkRegistry.newChannel(NetworkRegistry.java:207)
  at FMLNetworkHandler.registerChannel
  at FMLContainer.modConstruction
```

This was a **known ForgeGradle 5 / LegacyDev issue** (see [ForgeGradle #748](https://github.com/MinecraftForge/ForgeGradle/issues/748)): the dev classpath can add an extra value to the `Side` enum, so `NetworkRegistry` only has entries for `CLIENT`/`SERVER` and NPEs when it hits the third value.

**Fix:** A Mixin (`MixinNetworkRegistry`) redirects `Side.values()` to return only `CLIENT` and `SERVER` in the deobfuscated/dev environment, avoiding the NPE. Mixin is embedded in the build; no extra setup is needed for `runClient`.

**Alternative:** build the JAR and run it in a normal Minecraft+Forge 1.12.2 install:

```bat
gradlew.bat build
```

Then copy `build/libs/OreSpawn-1.12.2-20.3.jar` into the `mods` folder of a 1.12.2 Forge installation. The mod runs correctly in that environment as well.

In this project the network channel name was set to `"orespawn"` (mod ID) for 1.12.2 best practice; behavior is unchanged.
