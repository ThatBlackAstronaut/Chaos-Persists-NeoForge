package com.astryxion.chaospersists.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Used by mixin-injected spawn validation: must live outside the mixin package
 * so it can be referenced at runtime without triggering IllegalClassLoadError.
 */
public final class SpawnerFixHelper {
    private static final ResourceLocation FALLBACK = new ResourceLocation("chaospersists", "ant");
    private static final Map<String, String> LEGACY_PATH_ALIASES = new HashMap<String, String>();

    static {
        // Legacy / malformed ids seen in older dungeon logic.
        // normalizePath() maps "t._rex" / "t.rex" to "t_rex" (dots -> underscores), so keys must use normalized form.
        LEGACY_PATH_ALIASES.put("t._rex", "trex");
        LEGACY_PATH_ALIASES.put("t.rex", "trex");
        LEGACY_PATH_ALIASES.put("t_rex", "trex");
        // Legacy Nightmare naming used in older OreSpawn code/configs.
        LEGACY_PATH_ALIASES.put("pitchblack", "nightmare");
        LEGACY_PATH_ALIASES.put("pitch_black", "nightmare");
        LEGACY_PATH_ALIASES.put("wtf", "gamma_metroid");
        LEGACY_PATH_ALIASES.put("goldfish", "gold_fish");
        LEGACY_PATH_ALIASES.put("gold_fish", "gold_fish");
    }

    /** True if entity is from our entity package and on first tick (spawner spawn). */
    public static boolean isChaosEntityFirstTick(Entity entity) {
        Package pkg = entity.getClass().getPackage();
        return pkg != null
                && pkg.getName().startsWith("com.astryxion.chaospersists.entity")
                && entity.ticksExisted == 0;
    }

    /**
     * Makes spawner ids resilient to malformed legacy values.
     * Returns a registered id whenever possible, otherwise a safe fallback.
     */
    public static ResourceLocation normalizeSpawnerEntityId(ResourceLocation input) {
        if (input != null && EntityList.isRegistered(input)) {
            return input;
        }

        String namespace = input == null ? "chaospersists" : normalizeToken(input.getNamespace(), true);
        String rawPath = input == null ? "" : input.getPath();
        String normalizedPath = normalizePath(rawPath);

        ResourceLocation candidate = tryResolve(namespace, normalizedPath);
        if (candidate != null) return candidate;

        String alias = LEGACY_PATH_ALIASES.get(normalizedPath);
        if (alias != null) {
            candidate = tryResolve(namespace, alias);
            if (candidate != null) return candidate;
        }

        // If namespace was wrong/missing in legacy data, try both common domains.
        candidate = tryResolve("chaospersists", normalizedPath);
        if (candidate != null) return candidate;
        candidate = tryResolve("minecraft", normalizedPath);
        if (candidate != null) return candidate;

        if (alias != null) {
            candidate = tryResolve("chaospersists", alias);
            if (candidate != null) return candidate;
            candidate = tryResolve("minecraft", alias);
            if (candidate != null) return candidate;
        }

        return EntityList.isRegistered(FALLBACK) ? FALLBACK : new ResourceLocation("minecraft", "pig");
    }

    /**
     * Used at entity construction callsites (e.g. EntityList#createEntityByIDFromName).
     * Keeps spawners and any legacy loaders resilient even if setEntityId was never re-run.
     */
    public static ResourceLocation normalizeEntityLookupId(ResourceLocation input) {
        ResourceLocation direct = normalizeSpawnerEntityId(input);
        if (direct != null && EntityList.isRegistered(direct)) {
            return direct;
        }

        // Fuzzy fallback: match registered ids by normalized path signature.
        String wanted = compactSignature(input == null ? "" : normalizePath(input.getPath()));
        if (!wanted.isEmpty()) {
            Set<ResourceLocation> ids = EntityList.getEntityNameList();
            for (ResourceLocation id : ids) {
                if ("chaospersists".equals(id.getNamespace())
                        && wanted.equals(compactSignature(normalizePath(id.getPath())))) {
                    return id;
                }
            }
            for (ResourceLocation id : ids) {
                if (wanted.equals(compactSignature(normalizePath(id.getPath())))) {
                    return id;
                }
            }
        }
        return EntityList.isRegistered(FALLBACK) ? FALLBACK : new ResourceLocation("minecraft", "pig");
    }

    private static ResourceLocation tryResolve(String namespace, String path) {
        if (path == null || path.isEmpty()) return null;
        ResourceLocation id = new ResourceLocation(namespace, path);
        return EntityList.isRegistered(id) ? id : null;
    }

    private static String normalizeToken(String token, boolean allowMinecraftDefault) {
        if (token == null || token.isEmpty()) return allowMinecraftDefault ? "chaospersists" : "";
        String t = token.trim().toLowerCase(Locale.ROOT);
        if (t.equals("mod") || t.equals("orespawn")) return "chaospersists";
        return t;
    }

    private static String normalizePath(String path) {
        if (path == null) return "";
        String p = path.trim().toLowerCase(Locale.ROOT);
        p = p.replace(' ', '_').replace('-', '_').replace('.', '_');
        p = p.replaceAll("[^a-z0-9_/:]", "_");
        while (p.contains("__")) {
            p = p.replace("__", "_");
        }
        return p;
    }

    private static String compactSignature(String path) {
        return path == null ? "" : path.replace("_", "");
    }

    private static Method mobSpawnerGetEntityId;

    /**
     * Resolves duplicate/legacy registry ids (e.g. {@code chaospersists:t._rex} vs {@code chaospersists:trex})
     * to one canonical path so spawner checks match in production.
     */
    public static boolean entityIdsMatchForSpawner(ResourceLocation entityKey, ResourceLocation spawnerId) {
        if (entityKey == null || spawnerId == null) {
            return false;
        }
        String nsA = normalizeToken(entityKey.getNamespace(), true);
        String nsB = normalizeToken(spawnerId.getNamespace(), true);
        if (!nsA.equals(nsB)) {
            return false;
        }
        String pathA = canonicalEntityPathForSpawnerMatch(entityKey.getPath());
        String pathB = canonicalEntityPathForSpawnerMatch(spawnerId.getPath());
        return pathA.equalsIgnoreCase(pathB);
    }

    private static String canonicalEntityPathForSpawnerMatch(String rawPath) {
        String p = normalizePath(rawPath);
        String alias = LEGACY_PATH_ALIASES.get(p);
        return alias != null ? alias : p;
    }

    /**
     * Reads the spawner's entity id. {@link MobSpawnerBaseLogic#getEntityId()} is private; production uses SRG
     * method names, so {@code getDeclaredMethod("getEntityId")} fails outside the dev mappings. Forge's
     * {@link ReflectionHelper} resolves MCP + obfuscated names.
     */
    public static ResourceLocation getMobSpawnerEntityId(MobSpawnerBaseLogic logic) {
        if (logic == null) {
            return null;
        }
        try {
            if (mobSpawnerGetEntityId == null) {
                mobSpawnerGetEntityId = ReflectionHelper.findMethod(
                        MobSpawnerBaseLogic.class,
                        "getEntityId",
                        "func_190895_g");
            }
            return (ResourceLocation) mobSpawnerGetEntityId.invoke(logic);
        } catch (Exception e) {
            return null;
        }
    }

    private SpawnerFixHelper() {}
}
