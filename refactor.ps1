$ErrorActionPreference = "Stop"
$base = "c:\ModProjects\Chaos Persists\src\main\java\com\astryxion\chaospersists"
Set-Location $base

$map = @{}
function add($subpkg, [string[]]$names) {
    foreach ($n in $names) { $map[$n + ".java"] = $subpkg }
}

add "core" "ChaosPersists","ChaosConstants","ChaosWorld","ChaosSounds","ChaosGUIHandler","ChaosTeleporter"
add "proxy" "ClientProxyChaos","CommonProxyChaos"
add "command" "CommandMining","CommandUtopia"
add "world/biome" "BiomeGenUtopianPlains","BiomeMiningDimension"
add "world/ore" "ChunkOreGenerator","OreAmethyst","OreRuby","OreTitanium","OreUranium","OreCrystalCrystal","OreBasicStone","OreSalt","OreGenericEgg","OreCrystal"
add "world/dimension/worldprovider" "WorldProviderChaos","WorldProviderChaos2","WorldProviderChaos3","WorldProviderChaos4","WorldProviderChaos5","WorldProviderChaos6"
add "world/dimension/chunkprovider" "ChunkProviderChaos","ChunkProviderChaos2","ChunkProviderChaos3","ChunkProviderChaos4","ChunkProviderChaos5","ChunkProviderChaos6"
add "world/dimension/teleporter" "TeleporterMining","TeleporterUtopia"
add "world/dimension/structure" "MapGenMoreVillages","GenericDungeon","NightmareDungeon","RubyBirdDungeon","BasiliskMaze"

Get-ChildItem -Path $base -Filter "Block*.java" -File | ForEach-Object { $map[$_.Name] = "block" }
add "block" "AntBlock","CrystalAntBlock","RockBlock","IslandBlock","QueenSpawnerBlock","KingSpawnerBlock","DungeonSpawnerBlock","CrystalFurnace","CrystalGrass","CrystalMaze","CrystalWood","RTPBlock","StepUp","StepDown","StepAccross"

Get-ChildItem -Path $base -Filter "Item*.java" -File | ForEach-Object { $map[$_.Name] = "item" }
add "item" "AmethystAxe","AmethystHoe","AmethystPickaxe","AmethystShovel","AmethystSword","CrystalAxe","CrystalHoe","CrystalPickaxe","CrystalShovel","CrystalSword","EmeraldAxe","EmeraldHoe","EmeraldPickaxe","EmeraldShovel","EmeraldSword","RubyAxe","RubyHoe","RubyPickaxe","RubyShovel","RubySword","UltimateArrow","UltimateAxe","UltimateBow","UltimateFishHook","UltimateFishingRod","UltimateHoe","UltimatePickaxe","UltimateShovel","UltimateSword","ExperienceCatcher","ExperienceSword","FairySword","BigHammer","Coin","CreeperRepellent","IngotTitanium","IngotUranium","InkSack","IrukandjiArrow","SkateBow","ItemChaosArmor","BetterFireball","IceBall","LaserBall","ThunderBolt","WaterBall","Acid","BandP","CritterCage","InstantGarden","InstantShelter","Elevator","Shoes","Tshirt","Slice","PurplePower","ZooCage"
$map["CrystalWorkbench.java"] = "block"

Get-ChildItem -Path $base -Filter "Entity*.java" -File | ForEach-Object { $map[$_.Name] = "entity" }
add "entity" "Alien","Alosaurus","AntRobot","AttackSquid","Baryonyx","Basilisk","Beaver","Bee","Bertha","BerthaHit","Boyfriend","Brutalfly","Camarasaurus","Cassowary","CaterKiller","CaveFisher","Cephadrome","Chipmunk","CliffRacer","CloudShark","Cockateil","Crab","CreepingHorror","Cricket","CrystalCow","Cryolophosaurus","DeadIrukandji","Dragon","Dragonfly","DungeonBeast","EasterBunny","EnchantedCow","EnderKnight","EnderReaper","EmperorScorpion","Fairy","Firefly","Flounder","Frog","GammaMetroid","Gazelle","Ghost","GhostSkelly","GiantRobot","Girlfriend","Godzilla","GodzillaHead","GoldCow","GoldFish","Hammerhead","HerculesBeetle","Hydrolisc","Irukandji","Island","IslandToo","Kyuubi","Kraken","LeafMonster","Leon","Lizard","LurkingTerror","Mantis","Molenoid","Mothra","Nastysaurus","Ostrich","Peacock","PitchBlack","Pointysaurus","Rat","Robot1","Robot2","Robot3","Robot4","Robot5","RockBase","Rotator","RubberDucky","RubyBird","Scorpion","SeaMonster","SeaViper","Skate","SpiderDriver","SpiderRobot","SpitBug","Spyro","StinkBug","Stinky","SunspotUrchin","Termite","TerribleTerror","TheKing","ThePrince","ThePrinceAdult","ThePrincess","ThePrinceTeen","TheQueen","TRex","Triffid","TrooperBug","Urchin","VelocityRaptor","Vortex","WaterDragon","Whale","WormLarge","WormMedium","WormSmall","QueenHead","KingHead"

Get-ChildItem -Path $base -Filter "Model*.java" -File | ForEach-Object { $map[$_.Name] = "model" }
Get-ChildItem -Path $base -Filter "Render*.java" -File | ForEach-Object { $map[$_.Name] = "render" }

add "container" "ContainerCrystalFurnace","ContainerCrystalWorkbench"
add "tileentity" "TileEntityCrystalFurnace"
add "network" "RiderControl","RiderControlMessage","RiderControlMessageHandler"
add "util" "ArmorStats","MobStats","OreStats","WeaponStats","MyUtils","GenericTargetSorter","MyEntityAITarget","MyEntityAINearestAttackableTarget","MyEntityAINearestAttackableTargetSorter","MyEntityAIFollowOwner","MyEntityAIDance","MyDispenserBehaviorRock","MyBlockFlower","MyValentineTarget","MyEntityAIJealousy","WeightedRandomChestContent","WeightedRandomFishable","IItemRenderer","Trees","GirlfriendOverlayGui","CrystalFurnaceGUI","CrystalWorkbenchGUI","DispenserBehaviorChaosEgg"

$moved = 0
foreach ($entry in $map.GetEnumerator()) {
    $fname = $entry.Key
    $subpkg = $entry.Value
    $src = Join-Path $base $fname
    if (-not (Test-Path $src)) { continue }
    $newPkg = "com.astryxion.chaospersists." + ($subpkg -replace "/", ".")
    $destDir = Join-Path $base ($subpkg -replace "/", [System.IO.Path]::DirectorySeparatorChar)
    $dest = Join-Path $destDir $fname
    New-Item -ItemType Directory -Path $destDir -Force | Out-Null
    $content = Get-Content -Path $src -Raw -Encoding UTF8
    $content = $content -replace 'package\s+com\.astryxion\.chaospersists\s*;', "package $newPkg;"
    [System.IO.File]::WriteAllText($dest, $content, [System.Text.UTF8Encoding]::new($false))
    Remove-Item $src -Force
    $moved++
}
Write-Host "Moved $moved files"
