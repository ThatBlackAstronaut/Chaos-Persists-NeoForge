# -*- coding: utf-8 -*-
"""Move Java files to new package structure and update package declaration. Mixin not moved."""
import os
import re

BASE = os.path.join(os.path.dirname(__file__), "src", "main", "java", "com", "astryxion", "chaospersists")

# Map: filename (no path) -> subpackage path (e.g. "core", "world/biome")
FILE_TO_SUBPKG = {}

def add(subpkg, *names):
    for n in names:
        FILE_TO_SUBPKG[n + ".java"] = subpkg

add("core", "ChaosPersists", "ChaosConstants", "ChaosWorld", "ChaosSounds", "ChaosGUIHandler", "ChaosTeleporter")
add("proxy", "ClientProxyChaos", "CommonProxyChaos")
add("command", "CommandMining", "CommandUtopia")
add("world/biome", "BiomeGenUtopianPlains", "BiomeMiningDimension")
add("world/ore", "ChunkOreGenerator", "OreAmethyst", "OreRuby", "OreTitanium", "OreUranium",
    "OreCrystalCrystal", "OreBasicStone", "OreSalt", "OreGenericEgg", "OreCrystal")
add("world/dimension/worldprovider", "WorldProviderChaos", "WorldProviderChaos2", "WorldProviderChaos3",
    "WorldProviderChaos4", "WorldProviderChaos5", "WorldProviderChaos6")
add("world/dimension/chunkprovider", "ChunkProviderChaos", "ChunkProviderChaos2", "ChunkProviderChaos3",
    "ChunkProviderChaos4", "ChunkProviderChaos5", "ChunkProviderChaos6")
add("world/dimension/teleporter", "TeleporterMining", "TeleporterUtopia")
add("world/dimension/structure", "MapGenMoreVillages", "GenericDungeon", "NightmareDungeon", "RubyBirdDungeon", "BasiliskMaze")

# block: Block*.java + *Block + Crystal block classes
for f in os.listdir(BASE):
    if f.endswith(".java") and f.startswith("Block"):
        FILE_TO_SUBPKG[f] = "block"
add("block", "AntBlock", "CrystalAntBlock", "RockBlock", "IslandBlock", "QueenSpawnerBlock",
    "KingSpawnerBlock", "DungeonSpawnerBlock", "CrystalFurnace", "CrystalGrass", "CrystalMaze",
    "CrystalWood", "RTPBlock", "StepUp", "StepDown", "StepAccross")

# item: Item*.java + tools/weapons/armor
for f in os.listdir(BASE):
    if f.endswith(".java") and f.startswith("Item"):
        FILE_TO_SUBPKG[f] = "item"
add("item", "AmethystAxe", "AmethystHoe", "AmethystPickaxe", "AmethystShovel", "AmethystSword",
    "CrystalAxe", "CrystalHoe", "CrystalPickaxe", "CrystalShovel", "CrystalSword",
    "EmeraldAxe", "EmeraldHoe", "EmeraldPickaxe", "EmeraldShovel", "EmeraldSword",
    "RubyAxe", "RubyHoe", "RubyPickaxe", "RubyShovel", "RubySword",
    "UltimateArrow", "UltimateAxe", "UltimateBow", "UltimateFishHook", "UltimateFishingRod",
    "UltimateHoe", "UltimatePickaxe", "UltimateShovel", "UltimateSword",
    "ExperienceCatcher", "ExperienceSword", "FairySword", "BigHammer", "Coin", "CreeperRepellent",
    "IngotTitanium", "IngotUranium", "InkSack", "IrukandjiArrow", "SkateBow", "ItemChaosArmor",
    "BetterFireball", "IceBall", "LaserBall", "ThunderBolt", "WaterBall", "Acid", "BandP",
    "CritterCage", "InstantGarden", "InstantShelter", "Elevator", "Shoes", "Tshirt", "Slice",
    "PurplePower", "ZooCage", "CrystalWorkbench")  # CrystalWorkbench is block in spec? User said container + tileentity. CrystalWorkbench is a block. Put in block.
# Remove CrystalWorkbench from item if we put it in block
if "CrystalWorkbench.java" in FILE_TO_SUBPKG:
    FILE_TO_SUBPKG["CrystalWorkbench.java"] = "block"

# entity: Entity* + all mobs
for f in os.listdir(BASE):
    if f.endswith(".java") and f.startswith("Entity"):
        FILE_TO_SUBPKG[f] = "entity"
MOBS = [
    "Alien", "Alosaurus", "AntRobot", "AttackSquid", "Baryonyx", "Basilisk", "Beaver", "Bee", "Bertha", "BerthaHit",
    "Boyfriend", "Brutalfly", "Camarasaurus", "Cassowary", "CaterKiller", "CaveFisher", "Cephadrome",
    "Chipmunk", "CliffRacer", "CloudShark", "Cockateil", "Crab", "CreepingHorror", "Cricket", "CrystalCow",
    "Cryolophosaurus", "DeadIrukandji", "Dragon", "Dragonfly", "DungeonBeast", "EasterBunny", "EnchantedCow",
    "EnderKnight", "EnderReaper", "EmperorScorpion", "Fairy", "Firefly", "Flounder", "Frog", "GammaMetroid",
    "Gazelle", "Ghost", "GhostSkelly", "GiantRobot", "Girlfriend", "Godzilla", "GodzillaHead", "GoldCow",
    "GoldFish", "Hammerhead", "HerculesBeetle", "Hydrolisc", "Irukandji", "Island", "IslandToo", "Kyuubi",
    "Kraken", "LeafMonster", "Leon", "Lizard", "LurkingTerror", "Mantis", "Molenoid", "Mothra", "Nastysaurus",
    "Ostrich", "Peacock", "PitchBlack", "Pointysaurus", "Rat", "Robot1", "Robot2", "Robot3", "Robot4", "Robot5",
    "RockBase", "Rotator", "RubberDucky", "RubyBird", "Scorpion", "SeaMonster", "SeaViper", "Skate",
    "SpiderDriver", "SpiderRobot", "SpitBug", "Spyro", "StinkBug", "Stinky", "SunspotUrchin", "Termite",
    "TerribleTerror", "TheKing", "ThePrince", "ThePrinceAdult", "ThePrincess", "ThePrinceTeen", "TheQueen",
    "TRex", "Triffid", "TrooperBug", "Urchin", "VelocityRaptor", "Vortex", "WaterDragon", "Whale",
    "WormLarge", "WormMedium", "WormSmall", "QueenHead", "KingHead"
]
for m in MOBS:
    FILE_TO_SUBPKG[m + ".java"] = "entity"

# model
for f in os.listdir(BASE):
    if f.endswith(".java") and f.startswith("Model"):
        FILE_TO_SUBPKG[f] = "model"

# render
for f in os.listdir(BASE):
    if f.endswith(".java") and f.startswith("Render"):
        FILE_TO_SUBPKG[f] = "render"

add("container", "ContainerCrystalFurnace", "ContainerCrystalWorkbench")
add("tileentity", "TileEntityCrystalFurnace")
add("network", "RiderControl", "RiderControlMessage", "RiderControlMessageHandler")

# util
add("util", "ArmorStats", "MobStats", "OreStats", "WeaponStats", "MyUtils", "GenericTargetSorter",
    "MyEntityAITarget", "MyEntityAINearestAttackableTarget", "MyEntityAINearestAttackableTargetSorter",
    "MyEntityAIFollowOwner", "MyEntityAIDance", "MyDispenserBehaviorRock", "MyBlockFlower", "MyValentineTarget",
    "MyEntityAIJealousy", "WeightedRandomChestContent", "WeightedRandomFishable", "IItemRenderer",
    "Trees", "GirlfriendOverlayGui", "CrystalFurnaceGUI", "CrystalWorkbenchGUI", "DispenserBehaviorChaosEgg")

def main():
    moved = 0
    for fname, subpkg in list(FILE_TO_SUBPKG.items()):
        src = os.path.join(BASE, fname)
        if not os.path.isfile(src):
            continue
        new_pkg = "com.astryxion.chaospersists." + subpkg.replace("/", ".")
        dest_dir = os.path.join(BASE, *subpkg.split("/"))
        dest = os.path.join(dest_dir, fname)
        with open(src, "r", encoding="utf-8", errors="replace") as f:
            content = f.read()
        # Replace package declaration
        content = re.sub(r"\bpackage\s+com\.astryxion\.chaospersists\s*;",
                        "package " + new_pkg + ";", content, count=1)
        os.makedirs(dest_dir, exist_ok=True)
        with open(dest, "w", encoding="utf-8", newline="") as f:
            f.write(content)
        os.remove(src)
        moved += 1
        print("Moved %s -> %s" % (fname, subpkg))
    print("Total moved: %d" % moved)

if __name__ == "__main__":
    main()
