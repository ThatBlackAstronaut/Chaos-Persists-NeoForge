package com.astryxion.chaospersists.core;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.DimensionType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLLog;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;

import com.astryxion.chaospersists.util.ArmorStats;
import com.astryxion.chaospersists.util.Trees;
import com.astryxion.chaospersists.world.biome.BiomeChaosPlains;
import com.astryxion.chaospersists.world.biome.BiomeCrystalPlains;
import com.astryxion.chaospersists.world.biome.BiomeDangerPlains;
import com.astryxion.chaospersists.world.biome.BiomeGenUtopianPlains;
import com.astryxion.chaospersists.world.biome.BiomeMiningDimension;
import com.astryxion.chaospersists.world.biome.BiomeVillagePlains;
import com.astryxion.chaospersists.world.dimension.structure.BasiliskMaze;
import com.astryxion.chaospersists.world.dimension.structure.RubyBirdDungeon;
import com.astryxion.chaospersists.world.dimension.structure.GenericDungeon;
import com.astryxion.chaospersists.util.MyUtils;
import com.astryxion.chaospersists.world.ore.ChunkOreGenerator;
import com.astryxion.chaospersists.world.ore.OreGenericEgg;
import com.astryxion.chaospersists.block.CrystalFurnace;
import com.astryxion.chaospersists.util.WeaponStats;
import com.astryxion.chaospersists.item.ItemChaosArmor;
import com.astryxion.chaospersists.util.MobStats;
import com.astryxion.chaospersists.util.OreStats;
import com.astryxion.chaospersists.util.SpawnerFixHelper;
import com.astryxion.chaospersists.entity.RedCow;
import com.astryxion.chaospersists.world.ore.OreUranium;
import com.astryxion.chaospersists.world.ore.OreTitanium;
import com.astryxion.chaospersists.block.BlockUranium;
import com.astryxion.chaospersists.block.BlockTitanium;
import com.astryxion.chaospersists.block.BlockRuby;
import com.astryxion.chaospersists.block.BlockCrystal;
import com.astryxion.chaospersists.block.BlockPizza;
import com.astryxion.chaospersists.block.BlockDuctTape;
import com.astryxion.chaospersists.item.IngotUranium;
import com.astryxion.chaospersists.item.IngotTitanium;
import com.astryxion.chaospersists.block.Lavafoam;
import com.astryxion.chaospersists.item.ItemPizza;
import com.astryxion.chaospersists.item.ItemDuctTape;
import com.astryxion.chaospersists.item.UltimateSword;
import com.astryxion.chaospersists.item.UltimatePickaxe;
import com.astryxion.chaospersists.item.UltimateShovel;
import com.astryxion.chaospersists.item.UltimateHoe;
import com.astryxion.chaospersists.item.UltimateAxe;
import com.astryxion.chaospersists.item.UltimateBow;
import com.astryxion.chaospersists.item.NightmareSword;
import com.astryxion.chaospersists.item.PoisonSword;
import com.astryxion.chaospersists.item.RatSword;
import com.astryxion.chaospersists.item.FairySword;
import com.astryxion.chaospersists.item.MantisClaw;
import com.astryxion.chaospersists.item.BigHammer;
import com.astryxion.chaospersists.item.RubySword;
import com.astryxion.chaospersists.item.RubyPickaxe;
import com.astryxion.chaospersists.item.RubyShovel;
import com.astryxion.chaospersists.item.RubyHoe;
import com.astryxion.chaospersists.item.RubyAxe;
import com.astryxion.chaospersists.item.AmethystSword;
import com.astryxion.chaospersists.item.AmethystPickaxe;
import com.astryxion.chaospersists.item.AmethystShovel;
import com.astryxion.chaospersists.item.AmethystHoe;
import com.astryxion.chaospersists.item.AmethystAxe;
import com.astryxion.chaospersists.item.CrystalSword;
import com.astryxion.chaospersists.item.CrystalPickaxe;
import com.astryxion.chaospersists.item.CrystalShovel;
import com.astryxion.chaospersists.item.CrystalHoe;
import com.astryxion.chaospersists.item.CrystalAxe;
import com.astryxion.chaospersists.item.ExperienceSword;
import com.astryxion.chaospersists.item.ItemShoes;
import com.astryxion.chaospersists.entity.Bertha;
import com.astryxion.chaospersists.item.EmeraldSword;
import com.astryxion.chaospersists.item.EmeraldPickaxe;
import com.astryxion.chaospersists.item.EmeraldShovel;
import com.astryxion.chaospersists.item.EmeraldHoe;
import com.astryxion.chaospersists.item.EmeraldAxe;
import com.astryxion.chaospersists.item.SkateBow;
import com.astryxion.chaospersists.item.UltimateFishingRod;
import com.astryxion.chaospersists.item.ItemFireFish;
import com.astryxion.chaospersists.item.ItemSunFish;
import com.astryxion.chaospersists.item.ItemLavaEel;
import com.astryxion.chaospersists.item.ItemSalt;
import com.astryxion.chaospersists.item.ItemSpiderRobotKit;
import com.astryxion.chaospersists.item.ItemZooKeeper;
import com.astryxion.chaospersists.item.ItemCreeperLauncher;
import com.astryxion.chaospersists.item.ItemNetherLost;
import com.astryxion.chaospersists.item.ItemCrystalSticks;
import com.astryxion.chaospersists.item.ItemSunspotUrchin;
import com.astryxion.chaospersists.item.ItemSparkFish;
import com.astryxion.chaospersists.item.ItemWaterBall;
import com.astryxion.chaospersists.item.ItemLaserBall;
import com.astryxion.chaospersists.item.ItemIceBall;
import com.astryxion.chaospersists.item.ItemRock;
import com.astryxion.chaospersists.item.ItemRayGun;
import com.astryxion.chaospersists.item.ItemThunderStaff;
import com.astryxion.chaospersists.item.ItemWrench;
import com.astryxion.chaospersists.item.ItemAcid;
import com.astryxion.chaospersists.item.ItemIrukandji;
import com.astryxion.chaospersists.item.ItemIrukandjiArrow;
import com.astryxion.chaospersists.item.ItemGenericFish;
import com.astryxion.chaospersists.item.ItemSifter;
import com.astryxion.chaospersists.item.ItemSquidZooka;
import com.astryxion.chaospersists.item.ItemPopcorn;
import com.astryxion.chaospersists.item.ItemStrawberry;
import com.astryxion.chaospersists.item.ItemStrawberrySeed;
import com.astryxion.chaospersists.item.ItemButterflySeed;
import com.astryxion.chaospersists.item.ItemMothSeed;
import com.astryxion.chaospersists.item.ItemMosquitoSeed;
import com.astryxion.chaospersists.item.ItemFireflySeed;
import com.astryxion.chaospersists.item.ItemRadish;
import com.astryxion.chaospersists.item.ItemElevator;
import com.astryxion.chaospersists.item.ItemCornCob;
import com.astryxion.chaospersists.world.ore.OreSalt;
import com.astryxion.chaospersists.world.ore.OreRuby;
import com.astryxion.chaospersists.world.ore.OreAmethyst;
import com.astryxion.chaospersists.world.ore.OreBasicStone;
import com.astryxion.chaospersists.world.ore.OreCrystal;
import com.astryxion.chaospersists.world.ore.OreCrystalCrystal;
import com.astryxion.chaospersists.block.CrystalGrass;
import com.astryxion.chaospersists.block.CrystalWood;
import com.astryxion.chaospersists.block.CrystalWorkbench;
import com.astryxion.chaospersists.block.RTPBlock;
import com.astryxion.chaospersists.block.StepUp;
import com.astryxion.chaospersists.block.StepDown;
import com.astryxion.chaospersists.block.StepAccross;
import com.astryxion.chaospersists.block.MoleDirtBlock;
import com.astryxion.chaospersists.block.BlockStrawberry;
import com.astryxion.chaospersists.block.BlockButterflyPlant;
import com.astryxion.chaospersists.block.BlockMothPlant;
import com.astryxion.chaospersists.block.BlockMosquitoPlant;
import com.astryxion.chaospersists.block.BlockFireflyPlant;
import com.astryxion.chaospersists.block.BlockRadish;
import com.astryxion.chaospersists.block.BlockRice;
import com.astryxion.chaospersists.block.BlockCorn;
import com.astryxion.chaospersists.block.BlockQuinoa;
import com.astryxion.chaospersists.block.BlockTomato;
import com.astryxion.chaospersists.item.ItemTomato;
import com.astryxion.chaospersists.block.BlockLettuce;
import com.astryxion.chaospersists.item.ItemLettuce;
import com.astryxion.chaospersists.item.ItemMagicApple;
import com.astryxion.chaospersists.item.ItemMinersDream;
import com.astryxion.chaospersists.block.BlockExtremeTorch;
import com.astryxion.chaospersists.block.KrakenRepellent;
import com.astryxion.chaospersists.block.IslandBlock;
import com.astryxion.chaospersists.item.CreeperRepellent;
import com.astryxion.chaospersists.item.ZooCage;
import com.astryxion.chaospersists.item.InstantShelter;
import com.astryxion.chaospersists.item.InstantGarden;
import com.astryxion.chaospersists.block.BlockCrystalTorch;
import com.astryxion.chaospersists.block.KingSpawnerBlock;
import com.astryxion.chaospersists.block.QueenSpawnerBlock;
import com.astryxion.chaospersists.item.ItemRandomDungeon;
import com.astryxion.chaospersists.block.DungeonSpawnerBlock;
import com.astryxion.chaospersists.block.BlockAppleLeaves;
import com.astryxion.chaospersists.item.ItemAppleSeed;
import com.astryxion.chaospersists.block.BlockSkyTreeLog;
import com.astryxion.chaospersists.block.BlockDuplicatorLog;
import com.astryxion.chaospersists.block.BlockExperienceLeaves;
import com.astryxion.chaospersists.item.ExperienceCatcher;
import com.astryxion.chaospersists.item.ItemExperienceTreeSeed;
import com.astryxion.chaospersists.block.BlockExperiencePlant;
import com.astryxion.chaospersists.util.MyBlockFlower;
import com.astryxion.chaospersists.block.BlockScaryLeaves;
import com.astryxion.chaospersists.block.BlockCrystalLeaves;
import com.astryxion.chaospersists.block.BlockCrystalTreeLog;
import com.astryxion.chaospersists.block.BlockCrystalPlant;
import com.astryxion.chaospersists.item.UltimateFishHook;
import com.astryxion.chaospersists.entity.SunspotUrchin;
import com.astryxion.chaospersists.item.WaterBall;
import com.astryxion.chaospersists.item.InkSack;
import com.astryxion.chaospersists.item.LaserBall;
import com.astryxion.chaospersists.item.IceBall;
import com.astryxion.chaospersists.item.Acid;
import com.astryxion.chaospersists.entity.DeadIrukandji;
import com.astryxion.chaospersists.entity.BerthaHit;
import com.astryxion.chaospersists.item.PurplePower;
import com.astryxion.chaospersists.entity.EntityThrownRock;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.entity.GoldCow;
import com.astryxion.chaospersists.entity.EnchantedCow;
import com.astryxion.chaospersists.entity.EntityButterfly;
import com.astryxion.chaospersists.entity.EntityLunaMoth;
import com.astryxion.chaospersists.entity.EntityMosquito;
import com.astryxion.chaospersists.entity.Firefly;
import com.astryxion.chaospersists.entity.Bee;
import com.astryxion.chaospersists.entity.Mothra;
import com.astryxion.chaospersists.entity.EntityAnt;
import com.astryxion.chaospersists.entity.EntityRedAnt;
import com.astryxion.chaospersists.entity.EntityRainbowAnt;
import com.astryxion.chaospersists.entity.EntityUnstableAnt;
import com.astryxion.chaospersists.entity.Robot1;
import com.astryxion.chaospersists.entity.Robot2;
import com.astryxion.chaospersists.entity.Robot3;
import com.astryxion.chaospersists.entity.Robot4;
import com.astryxion.chaospersists.entity.Robot5;
import com.astryxion.chaospersists.entity.Alosaurus;
import com.astryxion.chaospersists.entity.Cryolophosaurus;
import com.astryxion.chaospersists.entity.Basilisk;
import com.astryxion.chaospersists.entity.Camarasaurus;
import com.astryxion.chaospersists.entity.Hydrolisc;
import com.astryxion.chaospersists.entity.VelocityRaptor;
import com.astryxion.chaospersists.entity.Dragonfly;
import com.astryxion.chaospersists.entity.EmperorScorpion;
import com.astryxion.chaospersists.entity.Scorpion;
import com.astryxion.chaospersists.entity.CaveFisher;
import com.astryxion.chaospersists.entity.Spyro;
import com.astryxion.chaospersists.entity.Baryonyx;
import com.astryxion.chaospersists.entity.GammaMetroid;
import com.astryxion.chaospersists.entity.Cockateil;
import com.astryxion.chaospersists.entity.RubyBird;
import com.astryxion.chaospersists.entity.Kyuubi;
import com.astryxion.chaospersists.entity.WaterDragon;
import com.astryxion.chaospersists.entity.AttackSquid;
import com.astryxion.chaospersists.entity.Alien;
import com.astryxion.chaospersists.item.Elevator;
import com.astryxion.chaospersists.entity.Kraken;
import com.astryxion.chaospersists.entity.Lizard;
import com.astryxion.chaospersists.entity.Cephadrome;
import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.entity.Chipmunk;
import com.astryxion.chaospersists.entity.Gazelle;
import com.astryxion.chaospersists.entity.Ostrich;
import com.astryxion.chaospersists.entity.TrooperBug;
import com.astryxion.chaospersists.entity.SpitBug;
import com.astryxion.chaospersists.entity.StinkBug;
import com.astryxion.chaospersists.item.Tshirt;
import com.astryxion.chaospersists.entity.Island;
import com.astryxion.chaospersists.entity.IslandToo;
import com.astryxion.chaospersists.entity.CreepingHorror;
import com.astryxion.chaospersists.entity.TerribleTerror;
import com.astryxion.chaospersists.entity.CliffRacer;
import com.astryxion.chaospersists.entity.Triffid;
import com.astryxion.chaospersists.entity.PitchBlack;
import com.astryxion.chaospersists.entity.LurkingTerror;
import com.astryxion.chaospersists.entity.Godzilla;
import com.astryxion.chaospersists.entity.Ghost;
import com.astryxion.chaospersists.entity.GhostSkelly;
import com.astryxion.chaospersists.entity.WormSmall;
import com.astryxion.chaospersists.entity.WormMedium;
import com.astryxion.chaospersists.entity.WormLarge;
import com.astryxion.chaospersists.entity.Cassowary;
import com.astryxion.chaospersists.entity.CloudShark;
import com.astryxion.chaospersists.entity.GoldFish;
import com.astryxion.chaospersists.entity.LeafMonster;
import com.astryxion.chaospersists.entity.GodzillaHead;
import com.astryxion.chaospersists.entity.EnderKnight;
import com.astryxion.chaospersists.entity.EnderReaper;
import com.astryxion.chaospersists.entity.Beaver;
import com.astryxion.chaospersists.entity.Termite;
import com.astryxion.chaospersists.entity.Fairy;
import com.astryxion.chaospersists.entity.Peacock;
import com.astryxion.chaospersists.entity.Rotator;
import com.astryxion.chaospersists.entity.Vortex;
import com.astryxion.chaospersists.entity.DungeonBeast;
import com.astryxion.chaospersists.entity.Rat;
import com.astryxion.chaospersists.entity.Flounder;
import com.astryxion.chaospersists.entity.Whale;
import com.astryxion.chaospersists.entity.Irukandji;
import com.astryxion.chaospersists.entity.Skate;
import com.astryxion.chaospersists.entity.Urchin;
import com.astryxion.chaospersists.entity.Mantis;
import com.astryxion.chaospersists.entity.HerculesBeetle;
import com.astryxion.chaospersists.entity.TRex;
import com.astryxion.chaospersists.entity.Stinky;
import com.astryxion.chaospersists.item.Coin;
import com.astryxion.chaospersists.entity.TheKing;
import com.astryxion.chaospersists.entity.KingHead;
import com.astryxion.chaospersists.entity.TheQueen;
import com.astryxion.chaospersists.entity.QueenHead;
import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.ThePrince;
import com.astryxion.chaospersists.entity.Molenoid;
import com.astryxion.chaospersists.entity.SeaMonster;
import com.astryxion.chaospersists.entity.SeaViper;
import com.astryxion.chaospersists.entity.EasterBunny;
import com.astryxion.chaospersists.entity.CaterKiller;
import com.astryxion.chaospersists.entity.CrystalCow;
import com.astryxion.chaospersists.entity.Leon;
import com.astryxion.chaospersists.entity.Hammerhead;
import com.astryxion.chaospersists.entity.RubberDucky;
import com.astryxion.chaospersists.entity.ThePrinceTeen;
import com.astryxion.chaospersists.item.BandP;
import com.astryxion.chaospersists.entity.RockBase;
import com.astryxion.chaospersists.entity.Brutalfly;
import com.astryxion.chaospersists.entity.Nastysaurus;
import com.astryxion.chaospersists.entity.Pointysaurus;
import com.astryxion.chaospersists.entity.Cricket;
import com.astryxion.chaospersists.entity.ThePrincess;
import com.astryxion.chaospersists.entity.Frog;
import com.astryxion.chaospersists.entity.ThePrinceAdult;
import com.astryxion.chaospersists.entity.SpiderRobot;
import com.astryxion.chaospersists.entity.SpiderDriver;
import com.astryxion.chaospersists.entity.GiantRobot;
import com.astryxion.chaospersists.entity.AntRobot;
import com.astryxion.chaospersists.entity.Crab;
import com.astryxion.chaospersists.item.Shoes;
import com.astryxion.chaospersists.entity.EntityCage;
import com.astryxion.chaospersists.item.UltimateArrow;
import com.astryxion.chaospersists.item.IrukandjiArrow;
import com.astryxion.chaospersists.world.dimension.worldprovider.WorldProviderChaos;
import com.astryxion.chaospersists.world.dimension.worldprovider.WorldProviderChaos2;
import com.astryxion.chaospersists.world.dimension.worldprovider.WorldProviderChaos3;
import com.astryxion.chaospersists.world.dimension.worldprovider.WorldProviderChaos4;
import com.astryxion.chaospersists.world.dimension.worldprovider.WorldProviderChaos5;
import com.astryxion.chaospersists.world.dimension.worldprovider.WorldProviderChaos6;
import com.astryxion.chaospersists.tileentity.TileEntityCrystalFurnace;
import com.astryxion.chaospersists.item.CritterCage;
import com.astryxion.chaospersists.item.ItemSpawnEgg;
import com.astryxion.chaospersists.util.DispenserBehaviorChaosEgg;
import com.astryxion.chaospersists.util.MyDispenserBehaviorArrow;
import com.astryxion.chaospersists.util.MyDispenserBehaviorWDCharge;
import com.astryxion.chaospersists.util.MyDispenserBehaviorSunspotUrchin;
import com.astryxion.chaospersists.util.MyDispenserBehaviorAcid;
import com.astryxion.chaospersists.util.MyDispenserBehaviorIceball;
import com.astryxion.chaospersists.util.MyDispenserBehaviorDeadIrukandji;
import com.astryxion.chaospersists.util.MyDispenserBehaviorLaserball;
import com.astryxion.chaospersists.util.MyDispenserBehaviorRock;
import com.astryxion.chaospersists.command.CommandDanger;
import com.astryxion.chaospersists.command.CommandCrystal;
import com.astryxion.chaospersists.command.CommandChaos;
import com.astryxion.chaospersists.command.CommandUtopia;
import com.astryxion.chaospersists.command.CommandVillageMania;
import com.astryxion.chaospersists.command.CommandMining;
import com.astryxion.chaospersists.block.AntBlock;
import com.astryxion.chaospersists.block.CrystalAntBlock;

@Mod(modid="chaospersists", name="OreSpawn", version="1.12.2-20.3")
public class ChaosPersists
{

  @SidedProxy(clientSide="com.astryxion.chaospersists.proxy.ClientProxyChaos", serverSide="com.astryxion.chaospersists.proxy.CommonProxyChaos")
  public static com.astryxion.chaospersists.proxy.CommonProxyChaos proxy;

  @Mod.Instance("chaospersists")
  public static ChaosPersists instance;
  public static com.astryxion.chaospersists.util.KeyHandler MyKeyhandler = null;
  public static int flyup_keystate = 0;

  public static int BaseBlockID = 2700;
  public static int BaseItemID = 9000;
  public static int BaseBiomeID = 120;
  public static int BaseDimensionID = 80;

  /** When true, logs resolved dimension numeric IDs at startup (see chaospersistsIDS). */
  public static boolean LogRegisteredDimensionIds = true;

  public static int BiomeUtopiaID = 0;
  public static int BiomeIslandsID = 0;
  public static int BiomeCrystalID = 0;
  public static int BiomeVillageID = 0;
  public static int BiomeChaosID = 0;
  public static int BiomeMiningID = 0;
  public static BiomeGenUtopianPlains UTOPIA_BIOME = null;
  public static BiomeVillagePlains VILLAGE_BIOME = null;
  public static BiomeDangerPlains DANGER_BIOME = null;
  public static BiomeCrystalPlains CRYSTAL_BIOME = null;
  public static BiomeChaosPlains CHAOS_BIOME = null;
  public static BiomeMiningDimension MINING_BIOME = null;
  public static int DimensionID = 0;
  public static int DimensionID2 = 0;
  public static int DimensionID3 = 0;
  public static int DimensionID4 = 0;
  public static int DimensionID5 = 0;
  public static int DimensionID6 = 0;

  /** Returns DimensionID for dimension index 1-6 (1=main chaospersists, 2-6=other dimensions). */
  public static int getDimension() { return DimensionID; }
  public static int getDimension(int n) {
    switch (n) {
      case 2: return DimensionID2;
      case 3: return DimensionID3;
      case 4: return DimensionID4;
      case 5: return DimensionID5;
      case 6: return DimensionID6;
      default: return DimensionID;
    }
  }

  public static int godzilla_has_spawned = 0;
  public static int current_dimension = 0;
  public static int valentines_day = 0;
  public static int easter_day = 0;
  public static int ultimate_sword_pvp = 0;
  public static int big_bertha_pvp = 0;
  public static int bro_mode = 0;
  public static int enableduplicatortree = 1;
  public static int RoyalGlideEnable = 1;
  public static int DragonflyHorseFriendly = 0;
  public static int PlayNicely = 0;
  public static int MinersDreamExpensive = 0;
  public static int DisableOverworldDungeons = 0;
  public static int FullPowerKingEnable = 0;

  public static ArmorStats Amethyst_armorstats = null;
  public static ArmorStats Emerald_armorstats = null;
  public static ArmorStats Experience_armorstats = null;
  public static ArmorStats MothScale_armorstats = null;
  public static ArmorStats LavaEel_armorstats = null;
  public static ArmorStats Ultimate_armorstats = null;
  public static ArmorStats Pink_armorstats = null;
  public static ArmorStats TigersEye_armorstats = null;
  public static ArmorStats Peacock_armorstats = null;
  public static ArmorStats Mobzilla_armorstats = null;
  public static ArmorStats Ruby_armorstats = null;
  public static ArmorStats Royal_armorstats = null;
  public static ArmorStats Lapis_armorstats = null;
  public static ArmorStats Queen_armorstats = null;

  public static int AllMobsDisable = 0;
  public static int MosquitoEnable = 1;
  public static int RockEnable = 1;
  public static int GhostEnable = 1;
  public static int GhostSkellyEnable = 1;
  public static int SpiderDriverEnable = 1;
  public static int JefferyEnable = 1;
  public static int MothraEnable = 1;
  public static int BrutalflyEnable = 1;
  public static int NastysaurusEnable = 1;
  public static int PointysaurusEnable = 1;
  public static int CricketEnable = 1;
  public static int FrogEnable = 1;
  public static int MothraPeaceful = 0;
  public static int BlackAntEnable = 1;
  public static int RedAntEnable = 1;
  public static int TermiteEnable = 1;
  public static int UnstableAntEnable = 1;
  public static int RainbowAntEnable = 1;
  public static int AlosaurusEnable = 1;
  public static int HammerheadEnable = 1;
  public static int LeonEnable = 1;
  public static int CaterKillerEnable = 1;
  public static int MolenoidEnable = 1;
  public static int TRexEnable = 1;
  public static int CriminalEnable = 1;
  public static int CryolophosaurusEnable = 1;
  public static int RatEnable = 1;
  public static int RatPlayerFriendly = 0;
  public static int RatPetFriendly = 0;
  public static int UrchinEnable = 1;
  public static int CamarasaurusEnable = 1;
  public static int ChipmunkEnable = 1;
  public static int OstrichEnable = 1;
  public static int GazelleEnable = 1;
  public static int VelocityRaptorEnable = 1;
  public static int HydroliscEnable = 1;
  public static int SpyroEnable = 1;
  public static int BaryonyxEnable = 1;
  public static int CockateilEnable = 1;
  public static int CassowaryEnable = 1;
  public static int EasterBunnyEnable = 1;
  public static int PeacockEnable = 1;
  public static int KyuubiEnable = 1;
  public static int CephadromeEnable = 1;
  public static int DragonEnable = 1;
  public static int GammaMetroidEnable = 1;
  public static int BasiliskEnable = 1;
  public static int DragonflyEnable = 1;
  public static int EmperorScorpionEnable = 1;
  public static int TrooperBugEnable = 1;
  public static int SpitBugEnable = 1;
  public static int StinkBugEnable = 1;
  public static int ScorpionEnable = 1;
  public static int CaveFisherEnable = 1;
  public static int AlienEnable = 1;
  public static int WaterDragonEnable = 1;
  public static int SeaMonsterEnable = 1;
  public static int SeaViperEnable = 1;
  public static int AttackSquidEnable = 1;
  public static int GodzillaEnable = 1;
  public static int LessOre = 0;
  public static int LessLag = 0;
  public static int Robot1Enable = 1;
  public static int Robot2Enable = 1;
  public static int Robot3Enable = 1;
  public static int Robot4Enable = 1;
  public static int Robot5Enable = 1;
  public static int RotatorEnable = 1;
  public static int VortexEnable = 1;
  public static int DungeonBeastEnable = 1;
  public static int KrakenEnable = 1;
  public static int LizardEnable = 1;
  public static int RubberDuckyEnable = 1;
  public static int GirlfriendEnable = 1;
  public static int BoyfriendEnable = 0;
  public static int FireflyEnable = 1;
  public static int FairyEnable = 1;
  public static int BeeEnable = 1;
  public static int TheKingEnable = 1;
  public static int TheQueenEnable = 1;
  public static int ThePrinceEnable = 1;
  public static int ThePrincessEnable = 1;
  public static int MantisEnable = 1;
  public static int StinkyEnable = 1;
  public static int HerculesBeetleEnable = 1;
  public static int CowEnable = 1;
  public static int ButterflyEnable = 1;
  public static int MothEnable = 1;
  public static int TshirtEnable = 1;
  public static int CoinEnable = 1;
  public static int CreepingHorrorEnable = 1;
  public static int TerribleTerrorEnable = 1;
  public static int CliffRacerEnable = 1;
  public static int TriffidEnable = 1;
  public static int UltimateSwordMagic = 10;
  public static int UltimateBowDamage = 10;
  public static int PitchBlackEnable = 1;
  public static int NightmareSize = 0;
  public static int LurkingTerrorEnable = 1;
  public static int IslandSpeedFactor = 2;
  public static int IslandSizeFactor = 2;
  public static int GinormousEmeraldTreeEnable = 1;
  public static int GuiOverlayEnable = 1;
  public static int FastGraphicsLeaves = 0;
  public static int WormEnable = 1;
  public static int CloudSharkEnable = 1;
  public static int GoldFishEnable = 1;
  public static int LeafMonsterEnable = 1;
  public static int EnderKnightEnable = 1;
  public static int EnderReaperEnable = 1;
  public static int BeaverEnable = 1;
  public static int IrukandjiEnable = 1;
  public static int SkateEnable = 1;
  public static int WhaleEnable = 1;
  public static int FlounderEnable = 1;
  public static int CrabEnable = 1;

  public ChaosWorld chaospersistsGen = new ChaosWorld();
  public static Random ChaosRand = new Random(151L);
  public static Trees chaospersistsTrees = null;
  public static BasiliskMaze BMaze;
  public static RubyBirdDungeon RubyDungeon;
  public static GenericDungeon MyDungeon;
  public static MyUtils chaospersistsUtils;
  public static ChunkOreGenerator Chunker;
  public static OreGenericEgg MySpiderSpawnBlock;
  public static OreGenericEgg MyBatSpawnBlock;
  public static OreGenericEgg MyCowSpawnBlock;
  public static OreGenericEgg MyPigSpawnBlock;
  public static OreGenericEgg MySquidSpawnBlock;
  public static OreGenericEgg MyChickenSpawnBlock;
  public static OreGenericEgg MyCreeperSpawnBlock;
  public static OreGenericEgg MySkeletonSpawnBlock;
  public static OreGenericEgg MyZombieSpawnBlock;
  public static OreGenericEgg MySlimeSpawnBlock;
  public static OreGenericEgg MyGhastSpawnBlock;
  public static OreGenericEgg MyZombiePigmanSpawnBlock;
  public static OreGenericEgg MyEndermanSpawnBlock;
  public static OreGenericEgg MyCaveSpiderSpawnBlock;
  public static OreGenericEgg MySilverfishSpawnBlock;
  public static OreGenericEgg MyMagmaCubeSpawnBlock;
  public static OreGenericEgg MyWitchSpawnBlock;
  public static OreGenericEgg MySheepSpawnBlock;
  public static OreGenericEgg MyWolfSpawnBlock;
  public static OreGenericEgg MyMooshroomSpawnBlock;
  public static OreGenericEgg MyOcelotSpawnBlock;
  public static OreGenericEgg MyBlazeSpawnBlock;
  public static OreGenericEgg MyWitherSkeletonSpawnBlock;
  public static OreGenericEgg MyEnderDragonSpawnBlock;
  public static OreGenericEgg MySnowGolemSpawnBlock;
  public static OreGenericEgg MyIronGolemSpawnBlock;
  public static OreGenericEgg MyWitherBossSpawnBlock;
  public static OreGenericEgg MyGirlfriendSpawnBlock;
  public static OreGenericEgg MyBoyfriendSpawnBlock;
  public static OreGenericEgg MyRedCowSpawnBlock;
  public static OreGenericEgg MyCrystalCowSpawnBlock;
  public static OreGenericEgg MyVillagerSpawnBlock;
  public static OreGenericEgg MyGoldCowSpawnBlock;
  public static OreGenericEgg MyEnchantedCowSpawnBlock;
  public static OreGenericEgg MyMOTHRASpawnBlock;
  public static OreGenericEgg MyAloSpawnBlock;
  public static OreGenericEgg MyCryoSpawnBlock;
  public static OreGenericEgg MyCamaSpawnBlock;
  public static OreGenericEgg MyVeloSpawnBlock;
  public static OreGenericEgg MyHydroSpawnBlock;
  public static OreGenericEgg MyBasilSpawnBlock;
  public static OreGenericEgg MyDragonflySpawnBlock;
  public static OreGenericEgg MyEmperorScorpionSpawnBlock;
  public static OreGenericEgg MyScorpionSpawnBlock;
  public static OreGenericEgg MyCaveFisherSpawnBlock;
  public static OreGenericEgg MySpyroSpawnBlock;
  public static OreGenericEgg MyBaryonyxSpawnBlock;
  public static OreGenericEgg MyGammaMetroidSpawnBlock;
  public static OreGenericEgg MyCockateilSpawnBlock;
  public static OreGenericEgg MyKyuubiSpawnBlock;
  public static OreGenericEgg MyAlienSpawnBlock;
  public static OreGenericEgg MyAttackSquidSpawnBlock;
  public static OreGenericEgg MyWaterDragonSpawnBlock;
  public static OreGenericEgg MyKrakenSpawnBlock;
  public static OreGenericEgg MyLizardSpawnBlock;
  public static OreGenericEgg MyCephadromeSpawnBlock;
  public static OreGenericEgg MyDragonSpawnBlock;
  public static OreGenericEgg MyBeeSpawnBlock;
  public static OreGenericEgg MyHorseSpawnBlock;
  public static OreGenericEgg MyTrooperBugSpawnBlock;
  public static OreGenericEgg MySpitBugSpawnBlock;
  public static OreGenericEgg MyStinkBugSpawnBlock;
  public static OreGenericEgg MyOstrichSpawnBlock;
  public static OreGenericEgg MyGazelleSpawnBlock;
  public static OreGenericEgg MyChipmunkSpawnBlock;
  public static OreGenericEgg MyCreepingHorrorSpawnBlock;
  public static OreGenericEgg MyTerribleTerrorSpawnBlock;
  public static OreGenericEgg MyCliffRacerSpawnBlock;
  public static OreGenericEgg MyTriffidSpawnBlock;
  public static OreGenericEgg MyPitchBlackSpawnBlock;
  public static OreGenericEgg MyLurkingTerrorSpawnBlock;
  public static OreGenericEgg MyGodzillaPartSpawnBlock;
  public static OreGenericEgg MyGodzillaSpawnBlock;
  public static OreGenericEgg MyTheKingPartSpawnBlock;
  public static OreGenericEgg MyTheQueenPartSpawnBlock;
  public static OreGenericEgg MyTheKingSpawnBlock;
  public static OreGenericEgg MyTheQueenSpawnBlock;
  public static OreGenericEgg MySmallWormSpawnBlock;
  public static OreGenericEgg MyMediumWormSpawnBlock;
  public static OreGenericEgg MyLargeWormSpawnBlock;
  public static OreGenericEgg MyCassowarySpawnBlock;
  public static OreGenericEgg MyCloudSharkSpawnBlock;
  public static OreGenericEgg MyGoldFishSpawnBlock;
  public static OreGenericEgg MyLeafMonsterSpawnBlock;
  public static OreGenericEgg MyTshirtSpawnBlock;
  public static OreGenericEgg MyEnderKnightSpawnBlock;
  public static OreGenericEgg MyEnderReaperSpawnBlock;
  public static OreGenericEgg MyBeaverSpawnBlock;
  public static OreGenericEgg MyUrchinSpawnBlock;
  public static OreGenericEgg MyFlounderSpawnBlock;
  public static OreGenericEgg MySkateSpawnBlock;
  public static OreGenericEgg MyRotatorSpawnBlock;
  public static OreGenericEgg MyPeacockSpawnBlock;
  public static OreGenericEgg MyFairySpawnBlock;
  public static OreGenericEgg MyDungeonBeastSpawnBlock;
  public static OreGenericEgg MyVortexSpawnBlock;
  public static OreGenericEgg MyRatSpawnBlock;
  public static OreGenericEgg MyWhaleSpawnBlock;
  public static OreGenericEgg MyIrukandjiSpawnBlock;
  public static OreGenericEgg MyTRexSpawnBlock;
  public static OreGenericEgg MyHerculesSpawnBlock;
  public static OreGenericEgg MyMantisSpawnBlock;
  public static OreGenericEgg MyStinkySpawnBlock;
  public static OreGenericEgg MyEasterBunnySpawnBlock;
  public static OreGenericEgg MyCaterKillerSpawnBlock;
  public static OreGenericEgg MyMolenoidSpawnBlock;
  public static OreGenericEgg MySeaMonsterSpawnBlock;
  public static OreGenericEgg MySeaViperSpawnBlock;
  public static OreGenericEgg MyLeonSpawnBlock;
  public static OreGenericEgg MyHammerheadSpawnBlock;
  public static OreGenericEgg MyRubberDuckySpawnBlock;
  public static OreGenericEgg MyCriminalSpawnBlock;
  public static OreGenericEgg MyBrutalflySpawnBlock;
  public static OreGenericEgg MyNastysaurusSpawnBlock;
  public static OreGenericEgg MyPointysaurusSpawnBlock;
  public static OreGenericEgg MyCricketSpawnBlock;
  public static OreGenericEgg MyFrogSpawnBlock;
  public static OreGenericEgg MySpiderDriverSpawnBlock;
  public static OreGenericEgg MyCrabSpawnBlock;
  public static Block MyOreUraniumBlock;
  public static Block MyOreTitaniumBlock;
  public static Item MyIngotUranium;
  public static Item MyIngotTitanium;
  public static Block MyBlockUraniumBlock;
  public static Block MyBlockTitaniumBlock;
  public static Block MyBlockMobzillaScaleBlock;
  public static Block MyBlockRubyBlock;
  public static Block MyBlockAmethystBlock;
  public static Block MyLavafoamBlock;
  public static Block MyPizzaBlock;
  public static Item MyPizzaItem;
  public static Block MyDuctTapeBlock;
  public static Item MyDuctTapeItem;
  public static Block MyAntBlock;
  public static Block MyRedAntBlock;
  public static Block TermiteBlock;
  public static Block CrystalTermiteBlock;
  public static Block MyRainbowAntBlock;
  public static Block MyUnstableAntBlock;
  public static Block MyFlowerPinkBlock;
  public static Block MyFlowerBlueBlock;
  public static Block MyFlowerBlackBlock;
  public static Block MyFlowerScaryBlock;
  public static Block CrystalFlowerRedBlock;
  public static Block CrystalFlowerGreenBlock;
  public static Block CrystalFlowerBlueBlock;
  public static Block CrystalFlowerYellowBlock;
  public static Block CrystalPlanksBlock;
  public static Block CrystalWorkbenchBlock;
  public static CrystalFurnace CrystalFurnaceBlock;
  public static Item MyUltimateSword;
  public static Item MyUltimatePickaxe;
  public static Item MyUltimateShovel;
  public static Item MyUltimateHoe;
  public static Item MyUltimateAxe;
  public static Item MyNightmareSword;
  public static Item MyBertha;
  public static Item MyHammy;
  public static Item MyBattleAxe;
  public static Item MyQueenBattleAxe;
  public static Item MyChainsaw;
  public static Item MySquidZooka;
  public static Item MySlice;
  public static Item MyRoyal;
  public static Item MyEmeraldSword;
  public static Item MyEmeraldPickaxe;
  public static Item MyEmeraldShovel;
  public static Item MyEmeraldHoe;
  public static Item MyEmeraldAxe;
  public static Item MyExperienceSword;
  public static Item MyPoisonSword;
  public static Item MyRatSword;
  public static Item MyFairySword;
  public static Item MyMantisClaw;
  public static Item MyBigHammer;
  public static Item MyRubySword;
  public static Item MyRubyPickaxe;
  public static Item MyRubyShovel;
  public static Item MyRubyHoe;
  public static Item MyRubyAxe;
  public static Item MyAmethystSword;
  public static Item MyAmethystPickaxe;
  public static Item MyAmethystShovel;
  public static Item MyAmethystHoe;
  public static Item MyAmethystAxe;
  public static Item MyRoseSword;
  static Item.ToolMaterial toolULTIMATE;
  static Item.ToolMaterial toolNIGHTMARE;
  static Item.ToolMaterial toolBERTHA;
  static Item.ToolMaterial toolCRYSTALWOOD;
  static Item.ToolMaterial toolCRYSTALSTONE;
  static Item.ToolMaterial toolCRYSTALPINK;
  static Item.ToolMaterial toolTIGERSEYE;
  static Item.ToolMaterial toolRUBY;
  static Item.ToolMaterial toolAMETHYST;
  static Item.ToolMaterial toolEMERALD;
  static Item.ToolMaterial toolROYAL;
  static Item.ToolMaterial toolHAMMY;
  static Item.ToolMaterial toolBATTLE;
  static Item.ToolMaterial toolCHAINSAW;
  static Item.ToolMaterial toolQUEENBATTLE;
  public static WeaponStats ultimate_stats = null;
  public static WeaponStats nightmare_stats = null;
  public static WeaponStats bertha_stats = null;
  public static WeaponStats crystalwood_stats = null;
  public static WeaponStats crystalstone_stats = null;
  public static WeaponStats crystalpink_stats = null;
  public static WeaponStats tigerseye_stats = null;
  public static WeaponStats ruby_stats = null;
  public static WeaponStats amethyst_stats = null;
  public static WeaponStats emerald_stats = null;
  public static WeaponStats royal_stats = null;
  public static WeaponStats hammy_stats = null;
  public static WeaponStats battleaxe_stats = null;
  public static WeaponStats queenbattleaxe_stats = null;
  public static WeaponStats chainsaw_stats = null;
  public static Item MyCrystalWoodSword;
  public static Item MyCrystalWoodPickaxe;
  public static Item MyCrystalWoodShovel;
  public static Item MyCrystalWoodHoe;
  public static Item MyCrystalWoodAxe;
  public static Item MyCrystalPinkSword;
  public static Item MyCrystalPinkPickaxe;
  public static Item MyCrystalPinkShovel;
  public static Item MyCrystalPinkHoe;
  public static Item MyCrystalPinkAxe;
  public static Item MyTigersEyeSword;
  public static Item MyTigersEyePickaxe;
  public static Item MyTigersEyeShovel;
  public static Item MyTigersEyeHoe;
  public static Item MyTigersEyeAxe;
  public static Item MyCrystalStoneSword;
  public static Item MyCrystalStonePickaxe;
  public static Item MyCrystalStoneShovel;
  public static Item MyCrystalStoneHoe;
  public static Item MyCrystalStoneAxe;
  public static Item MyCrystalPinkIngot;
  public static Block MyCrystalPinkBlock;
  public static Item MyTigersEyeIngot;
  public static Block MyTigersEyeBlock;
  public static Item MyItemShoes;
  public static Item MyItemShoes_1;
  public static Item MyItemShoes_2;
  public static Item MyItemShoes_3;
  public static Item MyItemGameController;
  public static Item MyUltimateBow;
  public static Item MySkateBow;
  public static Item MyUltimateFishingRod;
  public static ItemStack UltimateFishingRod;
  public static Item MyFireFish;
  public static Item MySunFish;
  public static Item MyLavaEel;
  public static Item MyMothScale;
  public static Item MyQueenScale;
  public static Item MyNightmareScale;
  public static Item MyEmperorScorpionScale;
  public static Item MyBasiliskScale;
  public static Item MyWaterDragonScale;
  public static Item MyJumpyBugScale;
  public static Item MyKrakenTooth;
  public static Item MyGodzillaScale;
  public static Item GreenGoo;
  public static Item SpiderRobotKit;
  public static Item AntRobotKit;
  public static Item ZooKeeper;
  public static Item CreeperLauncher;
  public static Item NetherLost;
  public static Item CrystalSticks;
  public static Item Sifter;
  public static Item MySunspotUrchin;
  public static Item MySparkFish;
  public static Item MyWaterBall;
  public static Item MyLaserBall;
  public static Item MyRayGun;
  public static Item MyThunderStaff;
  public static Item MyWrench;
  public static Item MyIceBall;
  public static Item MySmallRock;
  public static Item MyRock;
  public static Item MyRedRock;
  public static Item MyCrystalRedRock;
  public static Item MyCrystalGreenRock;
  public static Item MyCrystalBlueRock;
  public static Item MyCrystalTNTRock;
  public static Item MyBlueRock;
  public static Item MyGreenRock;
  public static Item MyPurpleRock;
  public static Item MySpikeyRock;
  public static Item MyTNTRock;
  public static Item MyAcid;
  public static Item MyIrukandji;
  public static Item MyIrukandjiArrow;
  public static Item MyGreenFish;
  public static Item MyBlueFish;
  public static Item MyPinkFish;
  public static Item MyRockFish;
  public static Item MyWoodFish;
  public static Item MyGreyFish;
  public static Item BerthaHandle;
  public static Item BerthaGuard;
  public static Item BerthaBlade;
  public static Item MolenoidNose;
  public static Item SeaMonsterScale;
  public static Item WormTooth;
  public static Item TRexTooth;
  public static Item CaterKillerJaw;
  public static Item SeaViperTongue;
  public static Item VortexEye;
  public static Item MyStepUp;
  public static Item MyStepDown;
  public static Item MyStepAccross;
  public static ItemArmor.ArmorMaterial armorULTIMATE;
  public static ItemArmor.ArmorMaterial armorMOBZILLA;
  public static ItemArmor.ArmorMaterial armorLAVAEEL;
  public static ItemArmor.ArmorMaterial armorMOTHSCALE;
  public static ItemArmor.ArmorMaterial armorEMERALD;
  public static ItemArmor.ArmorMaterial armorEXPERIENCE;
  public static ItemArmor.ArmorMaterial armorRUBY;
  public static ItemArmor.ArmorMaterial armorAMETHYST;
  public static ItemArmor.ArmorMaterial armorPINK;
  public static ItemArmor.ArmorMaterial armorTIGERSEYE;
  public static ItemArmor.ArmorMaterial armorPEACOCK;
  public static ItemArmor.ArmorMaterial armorROYAL;
  public static ItemArmor.ArmorMaterial armorLAPIS;
  public static ItemArmor.ArmorMaterial armorQUEEN;
  public static ItemChaosArmor UltimateHelmet;
  public static ItemChaosArmor UltimateBody;
  public static ItemChaosArmor UltimateLegs;
  public static ItemChaosArmor UltimateBoots;
  public static ItemChaosArmor LavaEelHelmet;
  public static ItemChaosArmor LavaEelBody;
  public static ItemChaosArmor LavaEelLegs;
  public static ItemChaosArmor LavaEelBoots;
  public static ItemChaosArmor MothScaleHelmet;
  public static ItemChaosArmor MothScaleBody;
  public static ItemChaosArmor MothScaleLegs;
  public static ItemChaosArmor MothScaleBoots;
  public static ItemChaosArmor EmeraldHelmet;
  public static ItemChaosArmor EmeraldBody;
  public static ItemChaosArmor EmeraldLegs;
  public static ItemChaosArmor EmeraldBoots;
  public static ItemChaosArmor ExperienceHelmet;
  public static ItemChaosArmor ExperienceBody;
  public static ItemChaosArmor ExperienceLegs;
  public static ItemChaosArmor ExperienceBoots;
  public static ItemChaosArmor RubyHelmet;
  public static ItemChaosArmor RubyBody;
  public static ItemChaosArmor RubyLegs;
  public static ItemChaosArmor RubyBoots;
  public static ItemChaosArmor AmethystHelmet;
  public static ItemChaosArmor AmethystBody;
  public static ItemChaosArmor AmethystLegs;
  public static ItemChaosArmor AmethystBoots;
  public static ItemChaosArmor CrystalPinkHelmet;
  public static ItemChaosArmor CrystalPinkBody;
  public static ItemChaosArmor CrystalPinkLegs;
  public static ItemChaosArmor CrystalPinkBoots;
  public static ItemChaosArmor TigersEyeHelmet;
  public static ItemChaosArmor TigersEyeBody;
  public static ItemChaosArmor TigersEyeLegs;
  public static ItemChaosArmor TigersEyeBoots;
  public static Block TigersEye;
  public static ItemChaosArmor PeacockFeatherBoots;
  public static ItemChaosArmor PeacockFeatherHelmet;
  public static ItemChaosArmor PeacockFeatherBody;
  public static ItemChaosArmor PeacockFeatherLegs;
  public static ItemChaosArmor MobzillaHelmet;
  public static ItemChaosArmor MobzillaBody;
  public static ItemChaosArmor MobzillaLegs;
  public static ItemChaosArmor MobzillaBoots;
  public static ItemChaosArmor RoyalHelmet;
  public static ItemChaosArmor RoyalBody;
  public static ItemChaosArmor RoyalLegs;
  public static ItemChaosArmor RoyalBoots;
  public static ItemChaosArmor LapisHelmet;
  public static ItemChaosArmor LapisBody;
  public static ItemChaosArmor LapisLegs;
  public static ItemChaosArmor LapisBoots;
  public static ItemChaosArmor QueenHelmet;
  public static ItemChaosArmor QueenBody;
  public static ItemChaosArmor QueenLegs;
  public static ItemChaosArmor QueenBoots;
  public static Block MyOreSaltBlock;
  public static Block MyRTPBlock;
  public static Block MyMoleDirtBlock;
  public static Item MySalt;
  public static Item MyPopcorn;
  public static Item MyButteredPopcorn;
  public static Item MyButteredSaltedPopcorn;
  public static Item MyPopcornBag;
  public static Item MyButter;
  public static Item MyCornDog;
  public static Item MyRawCornDog;
  public static Item MyPeacock;
  public static Item MyRawPeacock;
  public static Item MyElevator;
  public static Block MyOreRubyBlock;
  public static Item MyRuby;
  public static Item MyBacon;
  public static Item MyRawBacon;
  public static Item MyCrabMeat;
  public static Item MyRawCrabMeat;
  public static Item MyButterCandy;
  public static Block MyOreAmethystBlock;
  public static Item MyAmethyst;
  public static Item UraniumNugget;
  public static Item TitaniumNugget;
  public static Item MySalad;
  public static Item MyBLT;
  public static Item MyCrabbyPatty;
  public static Block CrystalStone;
  public static Block CrystalRat;
  public static Block CrystalFairy;
  public static Block CrystalCoal;
  public static Block CrystalGrass;
  public static Block CrystalCrystal;
  public static Block RedAntTroll;
  public static Block TermiteTroll;
  public static Item CageEmpty;
  public static Item CagedSpider;
  public static Item CagedBat;
  public static Item CagedCow;
  public static Item CagedPig;
  public static Item CagedSquid;
  public static Item CagedChicken;
  public static Item CagedCreeper;
  public static Item CagedSkeleton;
  public static Item CagedZombie;
  public static Item CagedSlime;
  public static Item CagedGhast;
  public static Item CagedZombiePigman;
  public static Item CagedEnderman;
  public static Item CagedCaveSpider;
  public static Item CagedSilverfish;
  public static Item CagedMagmaCube;
  public static Item CagedWitch;
  public static Item CagedSheep;
  public static Item CagedWolf;
  public static Item CagedMooshroom;
  public static Item CagedOcelot;
  public static Item CagedBlaze;
  public static Item CagedGirlfriend;
  public static Item CagedBoyfriend;
  public static Item CagedWitherSkeleton;
  public static Item CagedEnderDragon;
  public static Item CagedSnowGolem;
  public static Item CagedIronGolem;
  public static Item CagedWitherBoss;
  public static Item CagedRedCow;
  public static Item CagedCrystalCow;
  public static Item CagedVillager;
  public static Item CagedGoldCow;
  public static Item CagedEnchantedCow;
  public static Item CagedMOTHRA;
  public static Item CagedAlo;
  public static Item CagedCryo;
  public static Item CagedCama;
  public static Item CagedVelo;
  public static Item CagedHydro;
  public static Item CagedBasil;
  public static Item CagedDragonfly;
  public static Item CagedEmperorScorpion;
  public static Item CagedScorpion;
  public static Item CagedCaveFisher;
  public static Item CagedSpyro;
  public static Item CagedBaryonyx;
  public static Item CagedGammaMetroid;
  public static Item CagedCockateil;
  public static Item CagedKyuubi;
  public static Item CagedAlien;
  public static Item CagedAttackSquid;
  public static Item CagedWaterDragon;
  public static Item CagedCephadrome;
  public static Item CagedDragon;
  public static Item CagedKraken;
  public static Item CagedLizard;
  public static Item CagedBee;
  public static Item CagedHorse;
  public static Item CagedFirefly;
  public static Item CagedChipmunk;
  public static Item CagedGazelle;
  public static Item CagedOstrich;
  public static Item CagedTrooper;
  public static Item CagedSpit;
  public static Item CagedStink;
  public static Item CagedCreepingHorror;
  public static Item CagedTerribleTerror;
  public static Item CagedCliffRacer;
  public static Item CagedTriffid;
  public static Item CagedPitchBlack;
  public static Item CagedLurkingTerror;
  public static Item CagedSmallWorm;
  public static Item CagedMediumWorm;
  public static Item CagedLargeWorm;
  public static Item CagedCassowary;
  public static Item CagedCloudShark;
  public static Item CagedGoldFish;
  public static Item CagedLeafMonster;
  public static Item CagedEnderKnight;
  public static Item CagedEnderReaper;
  public static Item CagedBeaver;
  public static Item CagedUrchin;
  public static Item CagedFlounder;
  public static Item CagedSkate;
  public static Item CagedRotator;
  public static Item CagedPeacock;
  public static Item CagedFairy;
  public static Item CagedDungeonBeast;
  public static Item CagedVortex;
  public static Item CagedRat;
  public static Item CagedWhale;
  public static Item CagedIrukandji;
  public static Item CagedTRex;
  public static Item CagedHercules;
  public static Item CagedMantis;
  public static Item CagedStinky;
  public static Item CagedEasterBunny;
  public static Item CagedCaterKiller;
  public static Item CagedMolenoid;
  public static Item CagedSeaMonster;
  public static Item CagedSeaViper;
  public static Item CagedLeon;
  public static Item CagedHammerhead;
  public static Item CagedRubberDucky;
  public static Item CagedCriminal;
  public static Item CagedBrutalfly;
  public static Item CagedNastysaurus;
  public static Item CagedPointysaurus;
  public static Item CagedCricket;
  public static Item CagedFrog;
  public static Item CagedSpiderDriver;
  public static Item CagedCrab;
  public static Item WitherSkeletonEgg;
  public static Item EnderDragonEgg;
  public static Item SnowGolemEgg;
  public static Item IronGolemEgg;
  public static Item WitherBossEgg;
  public static Item GirlfriendEgg;
  public static Item RedCowEgg;
  public static Item CrystalCowEgg;
  public static Item GoldCowEgg;
  public static Item EnchantedCowEgg;
  public static Item MOTHRAEgg;
  public static Item AloEgg;
  public static Item CryoEgg;
  public static Item CamaEgg;
  public static Item VeloEgg;
  public static Item HydroEgg;
  public static Item BasilEgg;
  public static Item DragonflyEgg;
  public static Item EmperorScorpionEgg;
  public static Item ScorpionEgg;
  public static Item CaveFisherEgg;
  public static Item SpyroEgg;
  public static Item BaryonyxEgg;
  public static Item GammaMetroidEgg;
  public static Item CockateilEgg;
  public static Item KyuubiEgg;
  public static Item AlienEgg;
  public static Item AttackSquidEgg;
  public static Item WaterDragonEgg;
  public static Item CephadromeEgg;
  public static Item DragonEgg;
  public static Item KrakenEgg;
  public static Item LizardEgg;
  public static Item BeeEgg;
  public static Item TrooperBugEgg;
  public static Item SpitBugEgg;
  public static Item StinkBugEgg;
  public static Item OstrichEgg;
  public static Item GazelleEgg;
  public static Item ChipmunkEgg;
  public static Item CreepingHorrorEgg;
  public static Item TerribleTerrorEgg;
  public static Item CliffRacerEgg;
  public static Item TriffidEgg;
  public static Item PitchBlackEgg;
  public static Item LurkingTerrorEgg;
  public static Item GodzillaEgg;
  public static Item SmallWormEgg;
  public static Item MediumWormEgg;
  public static Item LargeWormEgg;
  public static Item CassowaryEgg;
  public static Item CloudSharkEgg;
  public static Item GoldFishEgg;
  public static Item LeafMonsterEgg;
  public static Item TshirtEgg;
  public static Item EnderKnightEgg;
  public static Item EnderReaperEgg;
  public static Item BeaverEgg;
  public static Item RotatorEgg;
  public static Item VortexEgg;
  public static Item PeacockEgg;
  public static Item FairyEgg;
  public static Item DungeonBeastEgg;
  public static Item RatEgg;
  public static Item FlounderEgg;
  public static Item WhaleEgg;
  public static Item IrukandjiEgg;
  public static Item SkateEgg;
  public static Item UrchinEgg;
  public static Item Robot1Egg;
  public static Item Robot2Egg;
  public static Item Robot3Egg;
  public static Item Robot4Egg;
  public static Item GhostEgg;
  public static Item GhostSkellyEgg;
  public static Item BrownAntEgg;
  public static Item RedAntEgg;
  public static Item RainbowAntEgg;
  public static Item UnstableAntEgg;
  public static Item TermiteEgg;
  public static Item ButterflyEgg;
  public static Item MothEgg;
  public static Item MosquitoEgg;
  public static Item FireflyEgg;
  public static Item TRexEgg;
  public static Item HerculesEgg;
  public static Item MantisEgg;
  public static Item StinkyEgg;
  public static Item Robot5Egg;
  public static Item CoinEgg;
  public static Item BoyfriendEgg;
  public static Item TheKingEgg;
  public static Item TheQueenEgg;
  public static Item ThePrinceEgg;
  public static Item EasterBunnyEgg;
  public static Item MolenoidEgg;
  public static Item SeaMonsterEgg;
  public static Item SeaViperEgg;
  public static Item CaterKillerEgg;
  public static Item LeonEgg;
  public static Item HammerheadEgg;
  public static Item RubberDuckyEgg;
  public static Item CriminalEgg;
  public static Item BrutalflyEgg;
  public static Item NastysaurusEgg;
  public static Item PointysaurusEgg;
  public static Item CricketEgg;
  public static Item ThePrincessEgg;
  public static Item FrogEgg;
  public static Item JefferyEgg;
  public static Item AntRobotEgg;
  public static Item SpiderRobotEgg;
  public static Item SpiderDriverEgg;
  public static Item CrabEgg;
  public static Item MyStrawberry;
  public static Item MyCrystalApple;
  public static Item MyLove;
  public static Item MyCheese;
  public static Item MyCherry;
  public static Item MyPeach;
  public static Item MyStrawberrySeed;
  public static Block MyStrawberryPlant;
  public static Item MyButterflySeed;
  public static Block MyButterflyPlant;
  public static Item MyMothSeed;
  public static Block MyMothPlant;
  public static Item MyMosquitoSeed;
  public static Block MyMosquitoPlant;
  public static Item MyFireflySeed;
  public static Block MyFireflyPlant;
  public static Item MyRadish;
  public static Item MyRice;
  public static Block MyRadishPlant;
  public static Block MyRicePlant;
  public static Block MyCornPlant1;
  public static Block MyCornPlant2;
  public static Block MyCornPlant3;
  public static Block MyCornPlant4;
  public static Item MyCornCob;
  public static Block MyQuinoaPlant1;
  public static Block MyQuinoaPlant2;
  public static Block MyQuinoaPlant3;
  public static Block MyQuinoaPlant4;
  public static Item MyQuinoa;
  public static Block MyTomatoPlant1;
  public static Block MyTomatoPlant2;
  public static Block MyTomatoPlant3;
  public static Block MyTomatoPlant4;
  public static Item MyTomato;
  public static Block MyLettucePlant1;
  public static Block MyLettucePlant2;
  public static Block MyLettucePlant3;
  public static Block MyLettucePlant4;
  public static Item MyLettuce;
  public static Item MagicApple;
  public static Item RandomDungeon;
  public static Item MinersDream;
  public static Block ExtremeTorch;
  public static Block MyEnderPearlBlock;
  public static Block MyEyeOfEnderBlock;
  public static Block MyExperiencePlant;
  public static Block KrakenRepellent;
  public static Block MyIslandBlock;
  public static Block CreeperRepellent;
  public static Item ZooCage2;
  public static Item ZooCage4;
  public static Item ZooCage6;
  public static Item ZooCage8;
  public static Item ZooCage10;
  public static Item InstantShelter;
  public static Item InstantGarden;
  public static Block CrystalTorch;
  public static Item MyPeacockFeather;
  public static Block MyKingSpawnerBlock;
  public static Block MyQueenSpawnerBlock;
  public static Block MyDungeonSpawnerBlock;
  public static Block MyCrystalPlant;
  public static Block MyCrystalPlant2;
  public static Block MyCrystalPlant3;
  public static Block MyAppleLeaves;
  public static Item MyAppleSeed;
  public static Item MyCherrySeed;
  public static Item MyPeachSeed;
  public static Block MySkyTreeLog;
  public static Block MyDT;
  public static Block MyExperienceLeaves;
  public static Block MyScaryLeaves;
  public static Block MyCherryLeaves;
  public static Block MyPeachLeaves;
  public static Item MyExperienceCatcher;
  public static Item MyExperienceTreeSeed;
  public static Item MyDeadStinkBug;
  public static Block MyCrystalLeaves;
  public static Block MyCrystalLeaves2;
  public static Block MyCrystalLeaves3;
  public static Block MyCrystalTreeLog;
  public static int GirlfriendID = 0;
  public static int BoyfriendID = 0;
  public static int RedCowID = 0;
  public static int GoldCowID = 0;
  public static int CrystalCowID = 0;
  public static int ButterflyID = 0;
  public static int FireflyID = 0;
  public static int FairyID = 0;
  public static int BeeID = 0;
  public static int TheKingID = 0;
  public static int TheQueenID = 0;
  public static int ThePrinceID = 0;
  public static int ThePrincessID = 0;
  public static int ThePrinceTeenID = 0;
  public static int ThePrinceAdultID = 0;
  public static int MantisID = 0;
  public static int StinkyID = 0;
  public static int HerculesBeetleID = 0;
  public static int LunaMothID = 0;
  public static int MosquitoID = 0;
  public static int GhostID = 0;
  public static int GhostSkellyID = 0;
  public static int SpiderRobotID = 0;
  public static int AntRobotID = 0;
  public static int JefferyID = 0;
  public static int SpiderDriverID = 0;
  public static int MothraID = 0;
  public static int BrutalflyID = 0;
  public static int NastysaurusID = 0;
  public static int PointysaurusID = 0;
  public static int CricketID = 0;
  public static int FrogID = 0;
  public static int EnchantedCowID = 0;
  public static int AntID = 0;
  public static int UnstableAntID = 0;
  public static int RedAntID = 0;
  public static int TermiteID = 0;
  public static int RockBaseID = 0;
  public static int RainbowAntID = 0;
  public static int AlosaurusID = 0;
  public static int LeonID = 0;
  public static int CaterKillerID = 0;
  public static int MolenoidID = 0;
  public static int TRexID = 0;
  public static int BandPID = 0;
  public static int CryolophosaurusID = 0;
  public static int RatID = 0;
  public static int UrchinID = 0;
  public static int CamarasaurusID = 0;
  public static int VelocityRaptorID = 0;
  public static int HydroliscID = 0;
  public static int SpyroID = 0;
  public static int BaryonyxID = 0;
  public static int CassowaryID = 0;
  public static int EasterBunnyID = 0;
  public static int PeacockID = 0;
  public static int CockateilID = 0;
  public static int RubyBirdID = 0;
  public static int KyuubiID = 0;
  public static int CephadromeID = 0;
  public static int DragonID = 0;
  public static int GammaMetroidID = 0;
  public static int BasiliskID = 0;
  public static int DragonflyID = 0;
  public static int EmperorScorpionID = 0;
  public static int TrooperBugID = 0;
  public static int SpitBugID = 0;
  public static int StinkBugID = 0;
  public static int ScorpionID = 0;
  public static int CaveFisherID = 0;
  public static int AlienID = 0;
  public static int WaterDragonID = 0;
  public static int SeaMonsterID = 0;
  public static int SeaViperID = 0;
  public static int AttackSquidID = 0;
  public static int ElevatorID = 0;
  public static int Robot1ID = 0;
  public static int Robot2ID = 0;
  public static int Robot3ID = 0;
  public static int Robot4ID = 0;
  public static int Robot5ID = 0;
  public static int RotatorID = 0;
  public static int VortexID = 0;
  public static int DungeonBeastID = 0;
  public static int KrakenID = 0;
  public static int LizardID = 0;
  public static int RubberDuckyID = 0;
  public static int ChipmunkID = 0;
  public static int OstrichID = 0;
  public static int GazelleID = 0;
  public static int TshirtID = 0;
  public static int CoinID = 0;
  public static int IslandID = 0;
  public static int IslandTooID = 0;
  public static int CreepingHorrorID = 0;
  public static int TerribleTerrorID = 0;
  public static int CliffRacerID = 0;
  public static int TriffidID = 0;
  public static int PitchBlackID = 0;
  public static int LurkingTerrorID = 0;
  public static int GodzillaID = 0;
  public static int WormSmallID = 0;
  public static int WormMediumID = 0;
  public static int WormLargeID = 0;
  public static int CloudSharkID = 0;
  public static int GoldFishID = 0;
  public static int LeafMonsterID = 0;
  public static int GodzillaHeadID = 0;
  public static int KingHeadID = 0;
  public static int QueenHeadID = 0;
  public static int EnderKnightID = 0;
  public static int EnderReaperID = 0;
  public static int BeaverID = 0;
  public static int SkateID = 0;
  public static int IrukandjiID = 0;
  public static int FlounderID = 0;
  public static int WhaleID = 0;
  public static int HammerheadID = 0;
  public static int CrabID = 0;

  public static MobStats Bee_stats = null;
  public static MobStats Mantis_stats = null;
  public static MobStats HerculesBeetle_stats = null;
  public static MobStats Mothra_stats = null;
  public static MobStats Brutalfly_stats = null;
  public static MobStats Nastysaurus_stats = null;
  public static MobStats Pointysaurus_stats = null;
  public static MobStats Alosaurus_stats = null;
  public static MobStats SpiderRobot_stats = null;
  public static MobStats AntRobot_stats = null;
  public static MobStats Jeffery_stats = null;
  public static MobStats Hammerhead_stats = null;
  public static MobStats Leon_stats = null;
  public static MobStats CaterKiller_stats = null;
  public static MobStats Molenoid_stats = null;
  public static MobStats TRex_stats = null;
  public static MobStats BandP_stats = null;
  public static MobStats Cryolophosaurus_stats = null;
  public static MobStats Rat_stats = null;
  public static MobStats Urchin_stats = null;
  public static MobStats Kyuubi_stats = null;
  public static MobStats GammaMetroid_stats = null;
  public static MobStats Basilisk_stats = null;
  public static MobStats EmperorScorpion_stats = null;
  public static MobStats TrooperBug_stats = null;
  public static MobStats SpitBug_stats = null;
  public static MobStats Alien_stats = null;
  public static MobStats WaterDragon_stats = null;
  public static MobStats SeaMonster_stats = null;
  public static MobStats SeaViper_stats = null;
  public static MobStats Robot2_stats = null;
  public static MobStats Robot3_stats = null;
  public static MobStats Robot4_stats = null;
  public static MobStats Robot5_stats = null;
  public static MobStats Rotator_stats = null;
  public static MobStats Vortex_stats = null;
  public static MobStats DungeonBeast_stats = null;
  public static MobStats Triffid_stats = null;
  public static MobStats LurkingTerror_stats = null;
  public static MobStats WormSmall_stats = null;
  public static MobStats WormMedium_stats = null;
  public static MobStats WormLarge_stats = null;
  public static MobStats EnderKnight_stats = null;
  public static MobStats EnderReaper_stats = null;
  public static MobStats Irukandji_stats = null;
  public static MobStats AttackSquid_stats = null;
  public static MobStats CaveFisher_stats = null;
  public static MobStats CloudShark_stats = null;
  public static MobStats CreepingHorror_stats = null;
  public static MobStats Godzilla_stats = null;
  public static MobStats Kraken_stats = null;
  public static MobStats LeafMonster_stats = null;
  public static MobStats PitchBlack_stats = null;
  public static MobStats Crab_stats = null;
  public static MobStats Scorpion_stats = null;
  public static MobStats Skate_stats = null;
  public static MobStats TerribleTerror_stats = null;
  public static MobStats TheKing_stats = null;
  public static MobStats TheQueen_stats = null;

  public static OreStats Ruby_stats = null;
  public static OreStats BlkRuby_stats = null;
  public static OreStats Uranium_stats = null;
  public static OreStats Titanium_stats = null;
  public static OreStats Amethyst_stats = null;
  public static OreStats Salt_stats = null;
  public static OreStats SpawnOres_stats = null;
  public static OreStats Diamond_stats = null;
  public static OreStats BlkDiamond_stats = null;
  public static OreStats Emerald_stats = null;
  public static OreStats BlkEmerald_stats = null;
  public static OreStats Gold_stats = null;
  public static OreStats BlkGold_stats = null;

  /** Raises vanilla {@code generic.maxHealth} cap (1024 in 1.12.2) so 1.7.10-scale boss HP applies. */
  private static void raiseVanillaMaxHealthCap()
  {
    try
    {
      IAttribute attr = SharedMonsterAttributes.MAX_HEALTH;
      if (!(attr instanceof RangedAttribute))
      {
        return;
      }
      RangedAttribute ranged = (RangedAttribute)attr;
      Field target = null;
      for (Field f : RangedAttribute.class.getDeclaredFields())
      {
        if (f.getType() != double.class || !Modifier.isFinal(f.getModifiers()))
        {
          continue;
        }
        f.setAccessible(true);
        double v = f.getDouble(ranged);
        if (Math.abs(v - 1024.0D) < 1.0E-6D)
        {
          target = f;
          break;
        }
      }
      if (target == null)
      {
        return;
      }
      Field modifiers = Field.class.getDeclaredField("modifiers");
      modifiers.setAccessible(true);
      modifiers.setInt(target, target.getModifiers() & ~Modifier.FINAL);
      target.setDouble(ranged, 1.0E9D);
    }
    catch (Throwable t)
    {
      FMLLog.log.error("ChaosPersists: failed to raise generic.maxHealth cap; boss HP may stay capped at 1024", t);
    }
  }

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event)
  {
    raiseVanillaMaxHealthCap();
    Configuration config = new Configuration(event.getSuggestedConfigurationFile());
    String ids = "chaospersistsIDS";
    String mobs = "chaospersistsMOBS";
    String tweaks = "chaospersistsTWEAKS";
    String weapons = "chaospersistsWEAPONS";
    String ores = "chaospersistsORES";

    config.load();

    config.setCategoryComment(ids,
        "Block / item / biome / dimension numeric IDs. Dimension conflicts: each mod needs a unique world ID. Vanilla uses Overworld 0, Nether -1, End 1. "
            + "Examples that often collide: AE2 spatial (2), Overworld Mirror (83), The Betweenlands (85). "
            + "Set BaseDimensionID to a free range, or set DimensionId_* entries explicitly (see each key). "
            + "Changing IDs after a world was created will strand dimension saves; backup before changing.");

    BaseBlockID = config.get(ids, "BaseBlockID", 2700).getInt();
    BaseItemID = config.get(ids, "BaseItemID", 9000).getInt();
    BaseBiomeID = config.get(ids, "BaseBiomeID", 120).getInt();

    configureDimensionIds(config, ids);

    getMobs(config, mobs);

    AllMobsDisable = config.get(tweaks, "AllMobsDisable", 0).getInt();
    LessOre = config.get(tweaks, "LessOre", 0).getInt();
    LessLag = config.get(tweaks, "LessLag", 0).getInt();
    RatPlayerFriendly = config.get(tweaks, "RatPlayerFriendly", 1).getInt();
    RatPetFriendly = config.get(tweaks, "RatPetFriendly", 1).getInt();
    NightmareSize = config.get(tweaks, "NightmareSize", 0).getInt();
    IslandSpeedFactor = config.get(tweaks, "IslandSpeedFactor", 2).getInt();
    IslandSizeFactor = config.get(tweaks, "IslandSizeFactor", 2).getInt();
    GinormousEmeraldTreeEnable = config.get(tweaks, "GinormousEmeraldTreeEnable", 1).getInt();
    GuiOverlayEnable = config.get(tweaks, "GuiOverlayEnable", 1).getInt();
    ultimate_sword_pvp = config.get(tweaks, "UltimateSwordPvp", 0).getInt();
    big_bertha_pvp = config.get(tweaks, "BigBerthaPvp", 0).getInt();
    bro_mode = config.get(tweaks, "BoyfriendBroMode", 0).getInt();
    enableduplicatortree = config.get(tweaks, "DuplicatorTreeEnable", 1).getInt();
    RoyalGlideEnable = config.get(tweaks, "RoyalGlideEnable", 1).getInt();
    DragonflyHorseFriendly = config.get(tweaks, "DragonflyHorseFriendly", 0).getInt();
    PlayNicely = config.get(tweaks, "PlayNicely", 0).getInt();
    MinersDreamExpensive = config.get(tweaks, "MinersDreamExpensive", 0).getInt();
    DisableOverworldDungeons = config.get(tweaks, "DisableOverworldDungeons", 0).getInt();
    FullPowerKingEnable = config.get(tweaks, "FullPowerKingEnable", 0).getInt();

    // 1.12.2 diamond armor is 3/6/8/3 with toughness 2; keep OreSpawn durability 100, match diamond protection + enchant tier.
    Amethyst_armorstats = get_armorstats(config, "Amethyst", 100, 3, 6, 8, 3, 10, 0, 0, 0, 0, 0, 0, 0, 0);
    Emerald_armorstats = get_armorstats(config, "Emerald", 60, 3, 8, 6, 3, 40, 0, 0, 0, 0, 0, 0, 0, 0);
    Experience_armorstats = get_armorstats(config, "Experience", 70, 5, 9, 7, 4, 50, 0, 0, 2, 0, 1, 0, 0, 1);
    MothScale_armorstats = get_armorstats(config, "MothScale", 50, 2, 7, 5, 2, 50, 0, 0, 3, 3, 3, 0, 0, 5);
    LavaEel_armorstats = get_armorstats(config, "LavaEel", 40, 2, 7, 5, 2, 35, 1, 2, 3, 2, 10, 0, 0, 2);
    Ultimate_armorstats = get_armorstats(config, "Ultimate", 200, 6, 12, 10, 6, 100, 2, 3, 5, 5, 5, 5, 0, 3);
    Pink_armorstats = get_armorstats(config, "Pink", 50, 3, 7, 5, 2, 40, 0, 0, 0, 0, 0, 0, 0, 0);
    TigersEye_armorstats = get_armorstats(config, "TigersEye", 80, 4, 8, 7, 4, 55, 0, 0, 0, 0, 0, 0, 0, 0);
    Peacock_armorstats = get_armorstats(config, "Peacock", 40, 2, 5, 4, 2, 30, 0, 0, 0, 0, 0, 0, 0, 10);
    Mobzilla_armorstats = get_armorstats(config, "Mobzilla", 1000, 7, 13, 11, 7, 150, 0, 0, 10, 10, 10, 10, 5, 10);
    Ruby_armorstats = get_armorstats(config, "Ruby", 90, 4, 9, 8, 4, 40, 0, 0, 0, 0, 0, 0, 0, 0);
    Royal_armorstats = get_armorstats(config, "Royal", 2000, 8, 14, 12, 8, 200, 1, 2, 10, 10, 10, 10, 5, 10);
    Lapis_armorstats = get_armorstats(config, "Lapis", 60, 2, 7, 5, 2, 60, 1, 1, 1, 0, 0, 1, 0, 0);
    Queen_armorstats = get_armorstats(config, "Queen", 1500, 9, 16, 14, 9, 150, 0, 0, 0, 0, 0, 0, 0, 0);

    ultimate_stats = get_weaponstats(config, weapons, "Ultimate", 10, 3000, 15, 36, 100);
    nightmare_stats = get_weaponstats(config, weapons, "Nightmare", 3, 1800, 12, 26, 60);
    bertha_stats = get_weaponstats(config, weapons, "Bertha", 3, 9000, 15, 496, 100);
    crystalwood_stats = get_weaponstats(config, weapons, "CrystalWood", 2, 300, 3, 2, 15);
    crystalstone_stats = get_weaponstats(config, weapons, "CrystalStone", 3, 800, 6, 5, 45);
    crystalpink_stats = get_weaponstats(config, weapons, "Pink", 4, 1100, 10, 7, 65);
    tigerseye_stats = get_weaponstats(config, weapons, "TigersEye", 4, 1600, 12, 8, 75);
    ruby_stats = get_weaponstats(config, weapons, "Ruby", 5, 1500, 11, 16, 85);
    amethyst_stats = get_weaponstats(config, weapons, "Amethyst", 4, 2000, 11, 11, 70);
    emerald_stats = get_weaponstats(config, weapons, "Emerald", 3, 1300, 10, 6, 75);
    royal_stats = get_weaponstats(config, weapons, "Royal", 3, 10000, 15, 746, 150);
    hammy_stats = get_weaponstats(config, weapons, "Attitude", 5, 2000, 15, 82, 100);
    battleaxe_stats = get_weaponstats(config, weapons, "BattleAxe", 3, 1500, 15, 46, 75);
    chainsaw_stats = get_weaponstats(config, weapons, "Chainsaw", 3, 1500, 10, 56, 75);
    queenbattleaxe_stats = get_weaponstats(config, weapons, "QueenBattleAxe", 3, 2200, 15, 662, 100);

    UltimateSwordMagic = config.get(weapons, "UltimateSwordEnchantmentLevel", 5).getInt();
    UltimateBowDamage = config.get(weapons, "UltimateBowDamage", 10).getInt();

    if (UltimateSwordMagic < 1) UltimateSwordMagic = 1;
    if (UltimateSwordMagic > 10) UltimateSwordMagic = 10;
    if (UltimateBowDamage < 2) UltimateBowDamage = 2;
    if (UltimateBowDamage > 20) UltimateBowDamage = 20;

    if (IslandSpeedFactor < 1) IslandSpeedFactor = 1;
    if (IslandSpeedFactor > 5) IslandSpeedFactor = 5;
    if (IslandSizeFactor < 1) IslandSizeFactor = 1;
    if (IslandSizeFactor > 5) IslandSizeFactor = 5;

    if (NightmareSize < 0) NightmareSize = 0;
    if (NightmareSize > 5) NightmareSize = 5;
    if (LessLag < 0) LessLag = 0;
    if (LessLag > 2) LessLag = 2;
    if (LessLag == 1) {
      if (IslandSizeFactor > 2) IslandSizeFactor = 2;
      if (IslandSpeedFactor > 2) IslandSpeedFactor = 2;
    }
    if (LessLag == 2) {
      if (IslandSizeFactor > 1) IslandSizeFactor = 1;
      if (IslandSpeedFactor > 1) IslandSpeedFactor = 1;
      LessOre = 1;
    }

    Ruby_stats = get_orestats(config, ores, "Ruby", 10, 1, 0, 50);
    BlkRuby_stats = get_orestats(config, ores, "BlockRuby", 1, 2, 0, 15);
    Uranium_stats = get_orestats(config, ores, "Uranium", 3, 4, 0, 30);
    Titanium_stats = get_orestats(config, ores, "Titanium", 3, 4, 0, 20);
    Amethyst_stats = get_orestats(config, ores, "Amethyst", 2, 6, 0, 25);
    Salt_stats = get_orestats(config, ores, "Salt", 5, 12, 50, 128);
    SpawnOres_stats = get_orestats(config, ores, "SpawnOres", 28, 4, 50, 128);
    Diamond_stats = get_orestats(config, ores, "Diamond", 4, 6, 0, 30);
    BlkDiamond_stats = get_orestats(config, ores, "BlockDiamond", 2, 4, 0, 20);
    Emerald_stats = get_orestats(config, ores, "Emerald", 4, 6, 0, 40);
    BlkEmerald_stats = get_orestats(config, ores, "BlockEmerald", 2, 4, 0, 20);
    Gold_stats = get_orestats(config, ores, "Gold", 4, 8, 0, 40);
    BlkGold_stats = get_orestats(config, ores, "BlockGold", 2, 4, 0, 25);

    config.save();

    if (AllMobsDisable != 0) {
      disableAllMobs();
    }

    BiomeUtopiaID = BaseBiomeID;
    BiomeIslandsID = BaseBiomeID + 1;
    BiomeCrystalID = BaseBiomeID + 2;
    BiomeVillageID = BaseBiomeID + 3;
    BiomeChaosID = BaseBiomeID + 4;
    BiomeMiningID = BaseBiomeID + 5;
    MinecraftForge.EVENT_BUS.register(instance);
    MinecraftForge.EVENT_BUS.register(chaospersistsGen);

    proxy.registerSoundThings();

    laySomeEggs();

    MyOreUraniumBlock = new OreUranium().setTranslationKey("oreuranium").setRegistryName("chaospersists", "oreuranium");
    MyOreTitaniumBlock = new OreTitanium().setTranslationKey("oretitanium").setRegistryName("chaospersists", "oretitanium");
    MyIngotUranium = new IngotUranium().setTranslationKey("ingoturanium").setRegistryName("chaospersists", "ingoturanium");
    MyIngotTitanium = new IngotTitanium().setTranslationKey("ingottitanium").setRegistryName("chaospersists", "ingottitanium");
    MyBlockUraniumBlock = new BlockUranium().setTranslationKey("blockuranium").setRegistryName("chaospersists", "blockuranium");
    MyBlockTitaniumBlock = new BlockTitanium().setTranslationKey("blocktitanium").setRegistryName("chaospersists", "blocktitanium");
    MyBlockMobzillaScaleBlock = new BlockRuby().setTranslationKey("blockmobzillascale").setRegistryName("chaospersists", "blockmobzillascale");
    MyLavafoamBlock = new Lavafoam().setTranslationKey("lavafoam").setRegistryName("chaospersists", "lavafoam");
    MyBlockRubyBlock = new BlockRuby().setTranslationKey("blockruby").setRegistryName("chaospersists", "blockruby");
    MyBlockAmethystBlock = new BlockRuby().setTranslationKey("blockamethyst").setRegistryName("chaospersists", "blockamethyst");
    MyCrystalPinkBlock = new BlockCrystal().setTranslationKey("crystalpink_block").setRegistryName("chaospersists", "crystalpink_block");
    MyCrystalPinkIngot = new IngotUranium().setTranslationKey("crystalpink_ingot").setRegistryName("chaospersists", "crystalpink_ingot");
    MyTigersEyeBlock = new BlockCrystal().setTranslationKey("tigerseye_block").setRegistryName("chaospersists", "tigerseye_block");
    MyTigersEyeIngot = new IngotUranium().setTranslationKey("tigerseye_ingot").setRegistryName("chaospersists", "tigerseye_ingot");

    MyPizzaBlock = new BlockPizza().setTranslationKey("pizza").setRegistryName("chaospersists", "pizza");
    MyPizzaItem = new ItemPizza(MyPizzaBlock).setMaxStackSize(1).setCreativeTab(CreativeTabs.FOOD).setTranslationKey("pizza").setRegistryName("chaospersists", "pizza");
    MyDuctTapeBlock = new BlockDuctTape().setTranslationKey("ducttape").setRegistryName("chaospersists", "ducttape");
    MyDuctTapeItem = new ItemDuctTape(MyDuctTapeBlock).setMaxStackSize(1).setCreativeTab(CreativeTabs.TOOLS).setTranslationKey("ducttape").setRegistryName("chaospersists", "ducttape");

    toolULTIMATE = EnumHelper.addToolMaterial("ULTIMATE", ultimate_stats.harvestlevel, ultimate_stats.maxuses, ultimate_stats.efficiency, ultimate_stats.damage, ultimate_stats.enchantability);

    toolNIGHTMARE = EnumHelper.addToolMaterial("NIGHTMARE", nightmare_stats.harvestlevel, nightmare_stats.maxuses, nightmare_stats.efficiency, nightmare_stats.damage, nightmare_stats.enchantability);

    toolEMERALD = EnumHelper.addToolMaterial("REALEMERALD", emerald_stats.harvestlevel, emerald_stats.maxuses, emerald_stats.efficiency, emerald_stats.damage, emerald_stats.enchantability);

    toolRUBY = EnumHelper.addToolMaterial("RUBY", ruby_stats.harvestlevel, ruby_stats.maxuses, ruby_stats.efficiency, ruby_stats.damage, ruby_stats.enchantability);

    toolAMETHYST = EnumHelper.addToolMaterial("AMETHYST", amethyst_stats.harvestlevel, amethyst_stats.maxuses, amethyst_stats.efficiency, amethyst_stats.damage, amethyst_stats.enchantability);

    toolBERTHA = EnumHelper.addToolMaterial("BERTHA", bertha_stats.harvestlevel, bertha_stats.maxuses, bertha_stats.efficiency, bertha_stats.damage, bertha_stats.enchantability);

    toolCRYSTALWOOD = EnumHelper.addToolMaterial("CRYSTALWOOD", crystalwood_stats.harvestlevel, crystalwood_stats.maxuses, crystalwood_stats.efficiency, crystalwood_stats.damage, crystalwood_stats.enchantability);

    toolCRYSTALSTONE = EnumHelper.addToolMaterial("CRYSTALSTONE", crystalstone_stats.harvestlevel, crystalstone_stats.maxuses, crystalstone_stats.efficiency, crystalstone_stats.damage, crystalstone_stats.enchantability);

    toolCRYSTALPINK = EnumHelper.addToolMaterial("CRYSTALPINK", crystalpink_stats.harvestlevel, crystalpink_stats.maxuses, crystalpink_stats.efficiency, crystalpink_stats.damage, crystalpink_stats.enchantability);

    toolTIGERSEYE = EnumHelper.addToolMaterial("TIGERSEYE", tigerseye_stats.harvestlevel, tigerseye_stats.maxuses, tigerseye_stats.efficiency, tigerseye_stats.damage, tigerseye_stats.enchantability);

    toolROYAL = EnumHelper.addToolMaterial("ROYAL", royal_stats.harvestlevel, royal_stats.maxuses, royal_stats.efficiency, royal_stats.damage, royal_stats.enchantability);

    toolHAMMY = EnumHelper.addToolMaterial("HAMMY", hammy_stats.harvestlevel, hammy_stats.maxuses, hammy_stats.efficiency, hammy_stats.damage, hammy_stats.enchantability);

    toolBATTLE = EnumHelper.addToolMaterial("BATTLE", battleaxe_stats.harvestlevel, battleaxe_stats.maxuses, battleaxe_stats.efficiency, battleaxe_stats.damage, battleaxe_stats.enchantability);

    toolCHAINSAW = EnumHelper.addToolMaterial("CHAINSAW", chainsaw_stats.harvestlevel, chainsaw_stats.maxuses, chainsaw_stats.efficiency, chainsaw_stats.damage, chainsaw_stats.enchantability);

    toolQUEENBATTLE = EnumHelper.addToolMaterial("QUEENBATTLE", queenbattleaxe_stats.harvestlevel, queenbattleaxe_stats.maxuses, queenbattleaxe_stats.efficiency, queenbattleaxe_stats.damage, queenbattleaxe_stats.enchantability);

    MyUltimateSword = new UltimateSword(toolULTIMATE).setTranslationKey("ultimatesword").setRegistryName("chaospersists", "ultimatesword");
    MyUltimatePickaxe = new UltimatePickaxe(toolULTIMATE).setTranslationKey("ultimatepickaxe").setRegistryName("chaospersists", "ultimatepickaxe");
    MyUltimatePickaxe.setHarvestLevel("pickaxe", ultimate_stats.harvestlevel);
    MyUltimateShovel = new UltimateShovel(toolULTIMATE).setTranslationKey("ultimateshovel").setRegistryName("chaospersists", "ultimateshovel");
    MyUltimateShovel.setHarvestLevel("shovel", ultimate_stats.harvestlevel);
    MyUltimateHoe = new UltimateHoe(toolULTIMATE).setTranslationKey("ultimatehoe").setRegistryName("chaospersists", "ultimatehoe");
    MyUltimateAxe = new UltimateAxe(toolULTIMATE).setTranslationKey("ultimateaxe").setRegistryName("chaospersists", "ultimateaxe");
    MyUltimateAxe.setHarvestLevel("axe", ultimate_stats.harvestlevel);
    MyNightmareSword = new NightmareSword(toolNIGHTMARE).setTranslationKey("nightmaresword").setRegistryName("chaospersists", "nightmaresword");
    MyBertha = new Bertha(toolBERTHA).setTranslationKey("berthasmall").setRegistryName("chaospersists", "berthasmall");
    MySlice = new Bertha(toolBERTHA).setTranslationKey("slicesmall").setRegistryName("chaospersists", "slicesmall");
    MyRoyal = new Bertha(toolROYAL).setTranslationKey("royalsmall").setRegistryName("chaospersists", "royalsmall");
    MyHammy = new Bertha(toolHAMMY).setTranslationKey("hammysmall").setRegistryName("chaospersists", "hammysmall");
    MyBattleAxe = new UltimateSword(toolBATTLE).setTranslationKey("battleaxesmall").setRegistryName("chaospersists", "battleaxesmall");
    MyChainsaw = new UltimateSword(toolCHAINSAW).setTranslationKey("chainsawsmall").setRegistryName("chaospersists", "chainsawsmall");
    MyQueenBattleAxe = new UltimateSword(toolQUEENBATTLE).setTranslationKey("queenbattleaxesmall").setRegistryName("chaospersists", "queenbattleaxesmall");

    MyEmeraldSword = new EmeraldSword(toolEMERALD).setTranslationKey("emeraldsword").setRegistryName("chaospersists", "emeraldsword");
    MyEmeraldPickaxe = new EmeraldPickaxe(toolEMERALD).setTranslationKey("emeraldpickaxe").setRegistryName("chaospersists", "emeraldpickaxe");
    MyEmeraldShovel = new EmeraldShovel(toolEMERALD).setTranslationKey("emeraldshovel").setRegistryName("chaospersists", "emeraldshovel");
    MyEmeraldHoe = new EmeraldHoe(toolEMERALD).setTranslationKey("emeraldhoe").setRegistryName("chaospersists", "emeraldhoe");
    MyEmeraldAxe = new EmeraldAxe(toolEMERALD).setTranslationKey("emeraldaxe").setRegistryName("chaospersists", "emeraldaxe");
    MyExperienceSword = new ExperienceSword(toolEMERALD).setTranslationKey("experiencesword").setRegistryName("chaospersists", "experiencesword");
    MyPoisonSword = new PoisonSword(toolEMERALD).setTranslationKey("poisonsword").setRegistryName("chaospersists", "poisonsword");
    MyRatSword = new RatSword(toolEMERALD).setTranslationKey("ratsword").setRegistryName("chaospersists", "ratsword");
    MyFairySword = new FairySword(toolEMERALD).setTranslationKey("fairysword").setRegistryName("chaospersists", "fairysword");
    MyMantisClaw = new MantisClaw(toolEMERALD).setTranslationKey("mantisclaw").setRegistryName("chaospersists", "mantisclaw");
    MyBigHammer = new BigHammer(toolAMETHYST).setTranslationKey("bighammer").setRegistryName("chaospersists", "bighammer");
    MyRubySword = new RubySword(toolRUBY).setTranslationKey("rubysword").setRegistryName("chaospersists", "rubysword");
    MyRubyPickaxe = new RubyPickaxe(toolRUBY).setTranslationKey("rubypickaxe").setRegistryName("chaospersists", "rubypickaxe");
    MyRubyPickaxe.setHarvestLevel("pickaxe", ruby_stats.harvestlevel);
    MyRubyShovel = new RubyShovel(toolRUBY).setTranslationKey("rubyshovel").setRegistryName("chaospersists", "rubyshovel");
    MyRubyShovel.setHarvestLevel("shovel", ruby_stats.harvestlevel);
    MyRubyHoe = new RubyHoe(toolRUBY).setTranslationKey("rubyhoe").setRegistryName("chaospersists", "rubyhoe");
    MyRubyAxe = new RubyAxe(toolRUBY).setTranslationKey("rubyaxe").setRegistryName("chaospersists", "rubyaxe");
    MyRubyAxe.setHarvestLevel("axe", ruby_stats.harvestlevel);
    MyAmethystSword = new AmethystSword(toolAMETHYST).setTranslationKey("amethystsword").setRegistryName("chaospersists", "amethystsword");
    MyAmethystPickaxe = new AmethystPickaxe(toolAMETHYST).setTranslationKey("amethystpickaxe").setRegistryName("chaospersists", "amethystpickaxe");
    MyAmethystPickaxe.setHarvestLevel("pickaxe", amethyst_stats.harvestlevel);
    MyAmethystShovel = new AmethystShovel(toolAMETHYST).setTranslationKey("amethystshovel").setRegistryName("chaospersists", "amethystshovel");
    MyAmethystShovel.setHarvestLevel("shovel", amethyst_stats.harvestlevel);
    MyAmethystHoe = new AmethystHoe(toolAMETHYST).setTranslationKey("amethysthoe").setRegistryName("chaospersists", "amethysthoe");
    MyAmethystAxe = new AmethystAxe(toolAMETHYST).setTranslationKey("amethystaxe").setRegistryName("chaospersists", "amethystaxe");
    MyAmethystAxe.setHarvestLevel("axe", amethyst_stats.harvestlevel);
    MyCrystalWoodSword = new CrystalSword(toolCRYSTALWOOD).setTranslationKey("crystalwoodsword").setRegistryName("chaospersists", "crystalwoodsword");
    MyCrystalWoodPickaxe = new CrystalPickaxe(toolCRYSTALWOOD).setTranslationKey("crystalwoodpickaxe").setRegistryName("chaospersists", "crystalwoodpickaxe");
    MyCrystalWoodShovel = new CrystalShovel(toolCRYSTALWOOD).setTranslationKey("crystalwoodshovel").setRegistryName("chaospersists", "crystalwoodshovel");
    MyCrystalWoodHoe = new CrystalHoe(toolCRYSTALWOOD).setTranslationKey("crystalwoodhoe").setRegistryName("chaospersists", "crystalwoodhoe");
    MyCrystalWoodAxe = new CrystalAxe(toolCRYSTALWOOD).setTranslationKey("crystalwoodaxe").setRegistryName("chaospersists", "crystalwoodaxe");
    MyCrystalPinkSword = new CrystalSword(toolCRYSTALPINK).setTranslationKey("crystalpinksword").setRegistryName("chaospersists", "crystalpinksword");
    MyCrystalPinkPickaxe = new CrystalPickaxe(toolCRYSTALPINK).setTranslationKey("crystalpinkpickaxe").setRegistryName("chaospersists", "crystalpinkpickaxe");
    MyCrystalPinkShovel = new CrystalShovel(toolCRYSTALPINK).setTranslationKey("crystalpinkshovel").setRegistryName("chaospersists", "crystalpinkshovel");
    MyCrystalPinkHoe = new CrystalHoe(toolCRYSTALPINK).setTranslationKey("crystalpinkhoe").setRegistryName("chaospersists", "crystalpinkhoe");
    MyCrystalPinkAxe = new CrystalAxe(toolCRYSTALPINK).setTranslationKey("crystalpinkaxe").setRegistryName("chaospersists", "crystalpinkaxe");
    MyCrystalStoneSword = new CrystalSword(toolCRYSTALSTONE).setTranslationKey("crystalstonesword").setRegistryName("chaospersists", "crystalstonesword");
    MyCrystalStonePickaxe = new CrystalPickaxe(toolCRYSTALSTONE).setTranslationKey("crystalstonepickaxe").setRegistryName("chaospersists", "crystalstonepickaxe");
    MyCrystalStoneShovel = new CrystalShovel(toolCRYSTALSTONE).setTranslationKey("crystalstoneshovel").setRegistryName("chaospersists", "crystalstoneshovel");
    MyCrystalStoneHoe = new CrystalHoe(toolCRYSTALSTONE).setTranslationKey("crystalstonehoe").setRegistryName("chaospersists", "crystalstonehoe");
    MyCrystalStoneAxe = new CrystalAxe(toolCRYSTALSTONE).setTranslationKey("crystalstoneaxe").setRegistryName("chaospersists", "crystalstoneaxe");
    MyTigersEyeSword = new CrystalSword(toolTIGERSEYE).setTranslationKey("tigerseye_sword").setRegistryName("chaospersists", "tigerseye_sword");
    MyTigersEyePickaxe = new CrystalPickaxe(toolTIGERSEYE).setTranslationKey("tigerseye_pickaxe").setRegistryName("chaospersists", "tigerseye_pickaxe");
    MyTigersEyeShovel = new CrystalShovel(toolTIGERSEYE).setTranslationKey("tigerseye_shovel").setRegistryName("chaospersists", "tigerseye_shovel");
    MyTigersEyeHoe = new CrystalHoe(toolTIGERSEYE).setTranslationKey("tigerseye_hoe").setRegistryName("chaospersists", "tigerseye_hoe");
    MyTigersEyeAxe = new CrystalAxe(toolTIGERSEYE).setTranslationKey("tigerseye_axe").setRegistryName("chaospersists", "tigerseye_axe");
    MyRoseSword = new EmeraldSword(toolEMERALD).setTranslationKey("rosesword").setRegistryName("chaospersists", "rosesword");

    MyItemShoes = new ItemShoes(2).setTranslationKey("redheels").setRegistryName("chaospersists", "redheels");
    MyItemShoes_1 = new ItemShoes(3).setTranslationKey("blackheels").setRegistryName("chaospersists", "blackheels");
    MyItemShoes_2 = new ItemShoes(4).setTranslationKey("slippers").setRegistryName("chaospersists", "slippers");
    MyItemShoes_3 = new ItemShoes(5).setTranslationKey("boots").setRegistryName("chaospersists", "boots");
    MyItemGameController = new ItemShoes(6).setTranslationKey("gamecontroller").setRegistryName("chaospersists", "gamecontroller");

    MyUltimateBow = new UltimateBow(BaseItemID + 303).setTranslationKey("ultimatebow").setRegistryName("chaospersists", "ultimatebow");
    MySkateBow = new SkateBow(BaseItemID + 373).setTranslationKey("skatebow").setRegistryName("chaospersists", "skatebow");

    MyUltimateFishingRod = new UltimateFishingRod(BaseItemID + 304).setTranslationKey("ultimatefishingrod").setRegistryName("chaospersists", "ultimatefishingrod");
    UltimateFishingRod = new ItemStack(MyUltimateFishingRod);

    MyFireFish = new ItemFireFish(4, 0.6F, false).setTranslationKey("firefish").setRegistryName("chaospersists", "firefish");
    MySunFish = new ItemSunFish(6, 0.6F, false).setTranslationKey("sunfish").setRegistryName("chaospersists", "sunfish");
    MyLavaEel = new ItemLavaEel(2, 0.6F, false).setTranslationKey("lavaeel").setRegistryName("chaospersists", "lavaeel");
    MyMothScale = new ItemSalt(BaseItemID + 156).setTranslationKey("mothscale").setRegistryName("chaospersists", "mothscale");
    MyQueenScale = new ItemSalt(BaseItemID + 453).setTranslationKey("queenscale").setRegistryName("chaospersists", "queenscale");
    MyNightmareScale = new ItemSalt(BaseItemID + 158).setTranslationKey("nightmarescale").setRegistryName("chaospersists", "nightmarescale");
    MyEmperorScorpionScale = new ItemSalt(BaseItemID + 159).setTranslationKey("emperorscorpionscale").setRegistryName("chaospersists", "emperorscorpionscale");
    MyBasiliskScale = new ItemSalt(BaseItemID + 160).setTranslationKey("basiliskscale").setRegistryName("chaospersists", "basiliskscale");
    MyWaterDragonScale = new ItemSalt(BaseItemID + 161).setTranslationKey("waterdragonscale").setRegistryName("chaospersists", "waterdragonscale");
    MyPeacockFeather = new ItemSalt(BaseItemID + 255).setTranslationKey("peacockfeather").setRegistryName("chaospersists", "peacockfeather");
    MyJumpyBugScale = new ItemSalt(BaseItemID + 162).setTranslationKey("jumpybugscale").setRegistryName("chaospersists", "jumpybugscale");
    MyKrakenTooth = new ItemSalt(BaseItemID + 163).setTranslationKey("krakentooth").setRegistryName("chaospersists", "krakentooth");
    MyGodzillaScale = new ItemSalt(BaseItemID + 164).setTranslationKey("godzillascale").setRegistryName("chaospersists", "godzillascale");
    GreenGoo = new ItemSalt(BaseItemID + 154).setTranslationKey("greengoo").setRegistryName("chaospersists", "greengoo");
    SpiderRobotKit = new ItemSpiderRobotKit(BaseItemID + 471).setTranslationKey("spiderrobotkit").setRegistryName("chaospersists", "spiderrobotkit");
    AntRobotKit = new ItemSpiderRobotKit(BaseItemID + 473).setTranslationKey("antrobotkit").setRegistryName("chaospersists", "antrobotkit");
    ZooKeeper = new ItemZooKeeper(BaseItemID + 230).setTranslationKey("zookeeper").setRegistryName("chaospersists", "zookeeper");
    CreeperLauncher = new ItemCreeperLauncher(BaseItemID + 252).setTranslationKey("creeperlauncher").setRegistryName("chaospersists", "creeperlauncher");
    NetherLost = new ItemNetherLost(BaseItemID + 253).setTranslationKey("netherlost").setRegistryName("chaospersists", "netherlost");
    CrystalSticks = new ItemCrystalSticks(BaseItemID + 254).setTranslationKey("crystalsticks").setRegistryName("chaospersists", "crystalsticks");
    MySunspotUrchin = new ItemSunspotUrchin(BaseItemID + 246).setTranslationKey("sunspoturchin").setRegistryName("chaospersists", "sunspoturchin");
    MySparkFish = new ItemSparkFish(1, 0.2F, false).setTranslationKey("sparkfish").setRegistryName("chaospersists", "sparkfish");
    MyWaterBall = new ItemWaterBall(BaseItemID + 244).setTranslationKey("waterball").setRegistryName("chaospersists", "waterball");
    MyLaserBall = new ItemLaserBall(BaseItemID + 242).setTranslationKey("laserball").setRegistryName("chaospersists", "laserball");
    MyIceBall = new ItemIceBall(BaseItemID + 239).setTranslationKey("iceball").setRegistryName("chaospersists", "iceball");
    MySmallRock = new ItemRock(BaseItemID + 436).setTranslationKey("rocksmall").setRegistryName("chaospersists", "rocksmall");
    MyRock = new ItemRock(BaseItemID + 435).setTranslationKey("rock").setRegistryName("chaospersists", "rock");
    MyRedRock = new ItemRock(BaseItemID + 437).setTranslationKey("rockred").setRegistryName("chaospersists", "rockred");
    MyCrystalRedRock = new ItemRock(BaseItemID + 443).setTranslationKey("rockcrystalred").setRegistryName("chaospersists", "rockcrystalred");
    MyCrystalGreenRock = new ItemRock(BaseItemID + 444).setTranslationKey("rockcrystalgreen").setRegistryName("chaospersists", "rockcrystalgreen");
    MyCrystalBlueRock = new ItemRock(BaseItemID + 445).setTranslationKey("rockcrystalblue").setRegistryName("chaospersists", "rockcrystalblue");
    MyCrystalTNTRock = new ItemRock(BaseItemID + 446).setTranslationKey("rockcrystaltnt").setRegistryName("chaospersists", "rockcrystaltnt");
    MyGreenRock = new ItemRock(BaseItemID + 438).setTranslationKey("rockgreen").setRegistryName("chaospersists", "rockgreen");
    MyBlueRock = new ItemRock(BaseItemID + 439).setTranslationKey("rockblue").setRegistryName("chaospersists", "rockblue");
    MyPurpleRock = new ItemRock(BaseItemID + 440).setTranslationKey("rockpurple").setRegistryName("chaospersists", "rockpurple");
    MySpikeyRock = new ItemRock(BaseItemID + 441).setTranslationKey("rockspikey").setRegistryName("chaospersists", "rockspikey");
    MyTNTRock = new ItemRock(BaseItemID + 442).setTranslationKey("rocktnt").setRegistryName("chaospersists", "rocktnt");
    MyRayGun = new ItemRayGun(BaseItemID + 243).setTranslationKey("raygun").setRegistryName("chaospersists", "raygun");
    MyThunderStaff = new ItemThunderStaff(BaseItemID + 240).setTranslationKey("thunderstaff").setRegistryName("chaospersists", "thunderstaff");
    MyWrench = new ItemWrench(BaseItemID + 472).setTranslationKey("wrench").setRegistryName("chaospersists", "wrench");
    MyAcid = new ItemAcid(BaseItemID + 247).setTranslationKey("acid").setRegistryName("chaospersists", "acid");
    MyIrukandji = new ItemIrukandji(BaseItemID + 258).setTranslationKey("deadirukandji").setRegistryName("chaospersists", "deadirukandji");
    MyIrukandjiArrow = new ItemIrukandjiArrow(BaseItemID + 372).setTranslationKey("irukandjiarrow").setRegistryName("chaospersists", "irukandjiarrow");
    MyGreenFish = new ItemGenericFish(3, 0.5F, false).setTranslationKey("greenfish").setRegistryName("chaospersists", "greenfish");
    MyBlueFish = new ItemGenericFish(4, 0.4F, false).setTranslationKey("bluefish").setRegistryName("chaospersists", "bluefish");
    MyPinkFish = new ItemGenericFish(4, 0.6F, false).setTranslationKey("pinkfish").setRegistryName("chaospersists", "pinkfish");
    MyRockFish = new ItemGenericFish(3, 0.7F, false).setTranslationKey("rockfish").setRegistryName("chaospersists", "rockfish");
    MyWoodFish = new ItemGenericFish(5, 0.7F, false).setTranslationKey("woodfish").setRegistryName("chaospersists", "woodfish");
    MyGreyFish = new ItemGenericFish(5, 0.5F, false).setTranslationKey("greyfish").setRegistryName("chaospersists", "greyfish");
    Sifter = new ItemSifter(BaseItemID + 325).setTranslationKey("sifter").setRegistryName("chaospersists", "sifter");
    MySquidZooka = new ItemSquidZooka(BaseItemID + 317).setTranslationKey("squidzookasmall").setRegistryName("chaospersists", "squidzookasmall");

    BerthaHandle = new ItemSalt(BaseItemID + 406).setTranslationKey("bbhandle").setRegistryName("chaospersists", "bbhandle");
    BerthaGuard = new ItemSalt(BaseItemID + 407).setTranslationKey("bbguard").setRegistryName("chaospersists", "bbguard");
    BerthaBlade = new ItemSalt(BaseItemID + 408).setTranslationKey("bbblade").setRegistryName("chaospersists", "bbblade");
    MolenoidNose = new ItemSalt(BaseItemID + 409).setTranslationKey("molenoidnose").setRegistryName("chaospersists", "molenoidnose");
    SeaMonsterScale = new ItemSalt(BaseItemID + 410).setTranslationKey("seamonsterscale").setRegistryName("chaospersists", "seamonsterscale");
    WormTooth = new ItemSalt(BaseItemID + 411).setTranslationKey("wormtooth").setRegistryName("chaospersists", "wormtooth");
    TRexTooth = new ItemSalt(BaseItemID + 412).setTranslationKey("trextooth").setRegistryName("chaospersists", "trextooth");
    CaterKillerJaw = new ItemSalt(BaseItemID + 413).setTranslationKey("caterkillerjaw").setRegistryName("chaospersists", "caterkillerjaw");
    SeaViperTongue = new ItemSalt(BaseItemID + 414).setTranslationKey("seavipertongue").setRegistryName("chaospersists", "seavipertongue");
    VortexEye = new ItemSalt(BaseItemID + 415).setTranslationKey("vortexeye").setRegistryName("chaospersists", "vortexeye");

    armorULTIMATE = EnumHelper.addArmorMaterial("ULTIMATE", "chaospersists", Ultimate_armorstats.durability, new int[] { Ultimate_armorstats.head_protection, Ultimate_armorstats.chest_protection, Ultimate_armorstats.leg_protection, Ultimate_armorstats.boot_protection }, Ultimate_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorMOBZILLA = EnumHelper.addArmorMaterial("MOBZILLA", "chaospersists", Mobzilla_armorstats.durability, new int[] { Mobzilla_armorstats.head_protection, Mobzilla_armorstats.chest_protection, Mobzilla_armorstats.leg_protection, Mobzilla_armorstats.boot_protection }, Mobzilla_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 4.0f);

    armorLAVAEEL = EnumHelper.addArmorMaterial("LAVAEEL", "chaospersists", LavaEel_armorstats.durability, new int[] { LavaEel_armorstats.head_protection, LavaEel_armorstats.chest_protection, LavaEel_armorstats.leg_protection, LavaEel_armorstats.boot_protection }, LavaEel_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorMOTHSCALE = EnumHelper.addArmorMaterial("MOTHSCALE", "chaospersists", MothScale_armorstats.durability, new int[] { MothScale_armorstats.head_protection, MothScale_armorstats.chest_protection, MothScale_armorstats.leg_protection, MothScale_armorstats.boot_protection }, MothScale_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorEMERALD = EnumHelper.addArmorMaterial("EMERALD", "chaospersists", Emerald_armorstats.durability, new int[] { Emerald_armorstats.head_protection, Emerald_armorstats.chest_protection, Emerald_armorstats.leg_protection, Emerald_armorstats.boot_protection }, Emerald_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorEXPERIENCE = EnumHelper.addArmorMaterial("EXPERIENCE", "chaospersists", Experience_armorstats.durability, new int[] { Experience_armorstats.head_protection, Experience_armorstats.chest_protection, Experience_armorstats.leg_protection, Experience_armorstats.boot_protection }, Experience_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorRUBY = EnumHelper.addArmorMaterial("RUBY", "chaospersists", Ruby_armorstats.durability, new int[] { Ruby_armorstats.head_protection, Ruby_armorstats.chest_protection, Ruby_armorstats.leg_protection, Ruby_armorstats.boot_protection }, Ruby_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.5f);

    armorAMETHYST = EnumHelper.addArmorMaterial("AMETHYST", "chaospersists", Amethyst_armorstats.durability, new int[] { Amethyst_armorstats.head_protection, Amethyst_armorstats.chest_protection, Amethyst_armorstats.leg_protection, Amethyst_armorstats.boot_protection }, Amethyst_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 2.0f);

    armorPINK = EnumHelper.addArmorMaterial("PINK", "chaospersists", Pink_armorstats.durability, new int[] { Pink_armorstats.head_protection, Pink_armorstats.chest_protection, Pink_armorstats.leg_protection, Pink_armorstats.boot_protection }, Pink_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorTIGERSEYE = EnumHelper.addArmorMaterial("TIGERSEYE", "chaospersists", TigersEye_armorstats.durability, new int[] { TigersEye_armorstats.head_protection, TigersEye_armorstats.chest_protection, TigersEye_armorstats.leg_protection, TigersEye_armorstats.boot_protection }, TigersEye_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorPEACOCK = EnumHelper.addArmorMaterial("PEACOCK", "chaospersists", Peacock_armorstats.durability, new int[] { Peacock_armorstats.head_protection, Peacock_armorstats.chest_protection, Peacock_armorstats.leg_protection, Peacock_armorstats.boot_protection }, Peacock_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorROYAL = EnumHelper.addArmorMaterial("ROYAL", "chaospersists", Royal_armorstats.durability, new int[] { Royal_armorstats.head_protection, Royal_armorstats.chest_protection, Royal_armorstats.leg_protection, Royal_armorstats.boot_protection }, Royal_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 5.0f);

    armorLAPIS = EnumHelper.addArmorMaterial("LAPIS", "chaospersists", Lapis_armorstats.durability, new int[] { Lapis_armorstats.head_protection, Lapis_armorstats.chest_protection, Lapis_armorstats.leg_protection, Lapis_armorstats.boot_protection }, Lapis_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    armorQUEEN = EnumHelper.addArmorMaterial("QUEEN", "chaospersists", Queen_armorstats.durability, new int[] { Queen_armorstats.head_protection, Queen_armorstats.chest_protection, Queen_armorstats.leg_protection, Queen_armorstats.boot_protection }, Queen_armorstats.enchantability, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0.0f);

    UltimateHelmet = (ItemChaosArmor)new ItemChaosArmor(armorULTIMATE, proxy.setArmorPrefix("ultimate"), 0).setTranslationKey("ultimate_helmet").setRegistryName("chaospersists", "ultimate_helmet");
    UltimateBody = (ItemChaosArmor)new ItemChaosArmor(armorULTIMATE, proxy.setArmorPrefix("ultimate"), 1).setTranslationKey("ultimate_chest").setRegistryName("chaospersists", "ultimate_chest");
    UltimateLegs = (ItemChaosArmor)new ItemChaosArmor(armorULTIMATE, proxy.setArmorPrefix("ultimate"), 2).setTranslationKey("ultimate_leggings").setRegistryName("chaospersists", "ultimate_leggings");
    UltimateBoots = (ItemChaosArmor)new ItemChaosArmor(armorULTIMATE, proxy.setArmorPrefix("ultimate"), 3).setTranslationKey("ultimate_boots").setRegistryName("chaospersists", "ultimate_boots");
    LavaEelHelmet = (ItemChaosArmor)new ItemChaosArmor(armorLAVAEEL, proxy.setArmorPrefix("lavaeel"), 0).setTranslationKey("lavaeel_helmet").setRegistryName("chaospersists", "lavaeel_helmet");
    LavaEelBody = (ItemChaosArmor)new ItemChaosArmor(armorLAVAEEL, proxy.setArmorPrefix("lavaeel"), 1).setTranslationKey("lavaeel_chest").setRegistryName("chaospersists", "lavaeel_chest");
    LavaEelLegs = (ItemChaosArmor)new ItemChaosArmor(armorLAVAEEL, proxy.setArmorPrefix("lavaeel"), 2).setTranslationKey("lavaeel_leggings").setRegistryName("chaospersists", "lavaeel_leggings");
    LavaEelBoots = (ItemChaosArmor)new ItemChaosArmor(armorLAVAEEL, proxy.setArmorPrefix("lavaeel"), 3).setTranslationKey("lavaeel_boots").setRegistryName("chaospersists", "lavaeel_boots");
    MothScaleHelmet = (ItemChaosArmor)new ItemChaosArmor(armorMOTHSCALE, proxy.setArmorPrefix("mothscale"), 0).setTranslationKey("mothscale_helmet").setRegistryName("chaospersists", "mothscale_helmet");
    MothScaleBody = (ItemChaosArmor)new ItemChaosArmor(armorMOTHSCALE, proxy.setArmorPrefix("mothscale"), 1).setTranslationKey("mothscale_chest").setRegistryName("chaospersists", "mothscale_chest");
    MothScaleLegs = (ItemChaosArmor)new ItemChaosArmor(armorMOTHSCALE, proxy.setArmorPrefix("mothscale"), 2).setTranslationKey("mothscale_leggings").setRegistryName("chaospersists", "mothscale_leggings");
    MothScaleBoots = (ItemChaosArmor)new ItemChaosArmor(armorMOTHSCALE, proxy.setArmorPrefix("mothscale"), 3).setTranslationKey("mothscale_boots").setRegistryName("chaospersists", "mothscale_boots");
    EmeraldHelmet = (ItemChaosArmor)new ItemChaosArmor(armorEMERALD, proxy.setArmorPrefix("emerald"), 0).setTranslationKey("emerald_helmet").setRegistryName("chaospersists", "emerald_helmet");
    EmeraldBody = (ItemChaosArmor)new ItemChaosArmor(armorEMERALD, proxy.setArmorPrefix("emerald"), 1).setTranslationKey("emerald_chest").setRegistryName("chaospersists", "emerald_chest");
    EmeraldLegs = (ItemChaosArmor)new ItemChaosArmor(armorEMERALD, proxy.setArmorPrefix("emerald"), 2).setTranslationKey("emerald_leggings").setRegistryName("chaospersists", "emerald_leggings");
    EmeraldBoots = (ItemChaosArmor)new ItemChaosArmor(armorEMERALD, proxy.setArmorPrefix("emerald"), 3).setTranslationKey("emerald_boots").setRegistryName("chaospersists", "emerald_boots");
    ExperienceHelmet = (ItemChaosArmor)new ItemChaosArmor(armorEXPERIENCE, proxy.setArmorPrefix("experience"), 0).setTranslationKey("experience_helmet").setRegistryName("chaospersists", "experience_helmet");
    ExperienceBody = (ItemChaosArmor)new ItemChaosArmor(armorEXPERIENCE, proxy.setArmorPrefix("experience"), 1).setTranslationKey("experience_chest").setRegistryName("chaospersists", "experience_chest");
    ExperienceLegs = (ItemChaosArmor)new ItemChaosArmor(armorEXPERIENCE, proxy.setArmorPrefix("experience"), 2).setTranslationKey("experience_leggings").setRegistryName("chaospersists", "experience_leggings");
    ExperienceBoots = (ItemChaosArmor)new ItemChaosArmor(armorEXPERIENCE, proxy.setArmorPrefix("experience"), 3).setTranslationKey("experience_boots").setRegistryName("chaospersists", "experience_boots");
    RubyHelmet = (ItemChaosArmor)new ItemChaosArmor(armorRUBY, proxy.setArmorPrefix("ruby"), 0).setTranslationKey("ruby_helmet").setRegistryName("chaospersists", "ruby_helmet");
    RubyBody = (ItemChaosArmor)new ItemChaosArmor(armorRUBY, proxy.setArmorPrefix("ruby"), 1).setTranslationKey("ruby_chest").setRegistryName("chaospersists", "ruby_chest");
    RubyLegs = (ItemChaosArmor)new ItemChaosArmor(armorRUBY, proxy.setArmorPrefix("ruby"), 2).setTranslationKey("ruby_leggings").setRegistryName("chaospersists", "ruby_leggings");
    RubyBoots = (ItemChaosArmor)new ItemChaosArmor(armorRUBY, proxy.setArmorPrefix("ruby"), 3).setTranslationKey("ruby_boots").setRegistryName("chaospersists", "ruby_boots");
    AmethystHelmet = (ItemChaosArmor)new ItemChaosArmor(armorAMETHYST, proxy.setArmorPrefix("amethyst"), 0).setTranslationKey("amethyst_helmet").setRegistryName("chaospersists", "amethyst_helmet");
    AmethystBody = (ItemChaosArmor)new ItemChaosArmor(armorAMETHYST, proxy.setArmorPrefix("amethyst"), 1).setTranslationKey("amethyst_chest").setRegistryName("chaospersists", "amethyst_chest");
    AmethystLegs = (ItemChaosArmor)new ItemChaosArmor(armorAMETHYST, proxy.setArmorPrefix("amethyst"), 2).setTranslationKey("amethyst_leggings").setRegistryName("chaospersists", "amethyst_leggings");
    AmethystBoots = (ItemChaosArmor)new ItemChaosArmor(armorAMETHYST, proxy.setArmorPrefix("amethyst"), 3).setTranslationKey("amethyst_boots").setRegistryName("chaospersists", "amethyst_boots");
    CrystalPinkHelmet = (ItemChaosArmor)new ItemChaosArmor(armorPINK, proxy.setArmorPrefix("pink"), 0).setTranslationKey("pink_helmet").setRegistryName("chaospersists", "pink_helmet");
    CrystalPinkBody = (ItemChaosArmor)new ItemChaosArmor(armorPINK, proxy.setArmorPrefix("pink"), 1).setTranslationKey("pink_chest").setRegistryName("chaospersists", "pink_chest");
    CrystalPinkLegs = (ItemChaosArmor)new ItemChaosArmor(armorPINK, proxy.setArmorPrefix("pink"), 2).setTranslationKey("pink_leggings").setRegistryName("chaospersists", "pink_leggings");
    CrystalPinkBoots = (ItemChaosArmor)new ItemChaosArmor(armorPINK, proxy.setArmorPrefix("pink"), 3).setTranslationKey("pink_boots").setRegistryName("chaospersists", "pink_boots");
    TigersEyeHelmet = (ItemChaosArmor)new ItemChaosArmor(armorTIGERSEYE, proxy.setArmorPrefix("tigerseye"), 0).setTranslationKey("tigerseye_helmet").setRegistryName("chaospersists", "tigerseye_helmet");
    TigersEyeBody = (ItemChaosArmor)new ItemChaosArmor(armorTIGERSEYE, proxy.setArmorPrefix("tigerseye"), 1).setTranslationKey("tigerseye_chest").setRegistryName("chaospersists", "tigerseye_chest");
    TigersEyeLegs = (ItemChaosArmor)new ItemChaosArmor(armorTIGERSEYE, proxy.setArmorPrefix("tigerseye"), 2).setTranslationKey("tigerseye_leggings").setRegistryName("chaospersists", "tigerseye_leggings");
    TigersEyeBoots = (ItemChaosArmor)new ItemChaosArmor(armorTIGERSEYE, proxy.setArmorPrefix("tigerseye"), 3).setTranslationKey("tigerseye_boots").setRegistryName("chaospersists", "tigerseye_boots");
    PeacockFeatherBoots = (ItemChaosArmor)new ItemChaosArmor(armorPEACOCK, proxy.setArmorPrefix("peacock"), 3).setTranslationKey("peacock_boots").setRegistryName("chaospersists", "peacock_boots");
    PeacockFeatherHelmet = (ItemChaosArmor)new ItemChaosArmor(armorPEACOCK, proxy.setArmorPrefix("peacock"), 0).setTranslationKey("peacock_helmet").setRegistryName("chaospersists", "peacock_helmet");
    PeacockFeatherBody = (ItemChaosArmor)new ItemChaosArmor(armorPEACOCK, proxy.setArmorPrefix("peacock"), 1).setTranslationKey("peacock_chest").setRegistryName("chaospersists", "peacock_chest");
    PeacockFeatherLegs = (ItemChaosArmor)new ItemChaosArmor(armorPEACOCK, proxy.setArmorPrefix("peacock"), 2).setTranslationKey("peacock_leggings").setRegistryName("chaospersists", "peacock_leggings");
    MobzillaHelmet = (ItemChaosArmor)new ItemChaosArmor(armorMOBZILLA, proxy.setArmorPrefix("mobzilla"), 0).setTranslationKey("mobzilla_helmet").setRegistryName("chaospersists", "mobzilla_helmet");
    MobzillaBody = (ItemChaosArmor)new ItemChaosArmor(armorMOBZILLA, proxy.setArmorPrefix("mobzilla"), 1).setTranslationKey("mobzilla_chest").setRegistryName("chaospersists", "mobzilla_chest");
    MobzillaLegs = (ItemChaosArmor)new ItemChaosArmor(armorMOBZILLA, proxy.setArmorPrefix("mobzilla"), 2).setTranslationKey("mobzilla_leggings").setRegistryName("chaospersists", "mobzilla_leggings");
    MobzillaBoots = (ItemChaosArmor)new ItemChaosArmor(armorMOBZILLA, proxy.setArmorPrefix("mobzilla"), 3).setTranslationKey("mobzilla_boots").setRegistryName("chaospersists", "mobzilla_boots");
    RoyalHelmet = (ItemChaosArmor)new ItemChaosArmor(armorROYAL, proxy.setArmorPrefix("royal"), 0).setTranslationKey("royal_helmet").setRegistryName("chaospersists", "royal_helmet");
    RoyalBody = (ItemChaosArmor)new ItemChaosArmor(armorROYAL, proxy.setArmorPrefix("royal"), 1).setTranslationKey("royal_chest").setRegistryName("chaospersists", "royal_chest");
    RoyalLegs = (ItemChaosArmor)new ItemChaosArmor(armorROYAL, proxy.setArmorPrefix("royal"), 2).setTranslationKey("royal_leggings").setRegistryName("chaospersists", "royal_leggings");
    RoyalBoots = (ItemChaosArmor)new ItemChaosArmor(armorROYAL, proxy.setArmorPrefix("royal"), 3).setTranslationKey("royal_boots").setRegistryName("chaospersists", "royal_boots");
    LapisHelmet = (ItemChaosArmor)new ItemChaosArmor(armorLAPIS, proxy.setArmorPrefix("lapis"), 0).setTranslationKey("lapis_helmet").setRegistryName("chaospersists", "lapis_helmet");
    LapisBody = (ItemChaosArmor)new ItemChaosArmor(armorLAPIS, proxy.setArmorPrefix("lapis"), 1).setTranslationKey("lapis_chest").setRegistryName("chaospersists", "lapis_chest");
    LapisLegs = (ItemChaosArmor)new ItemChaosArmor(armorLAPIS, proxy.setArmorPrefix("lapis"), 2).setTranslationKey("lapis_leggings").setRegistryName("chaospersists", "lapis_leggings");
    LapisBoots = (ItemChaosArmor)new ItemChaosArmor(armorLAPIS, proxy.setArmorPrefix("lapis"), 3).setTranslationKey("lapis_boots").setRegistryName("chaospersists", "lapis_boots");
    QueenHelmet = (ItemChaosArmor)new ItemChaosArmor(armorQUEEN, proxy.setArmorPrefix("queen"), 0).setTranslationKey("queen_helmet").setRegistryName("chaospersists", "queen_helmet");
    QueenBody = (ItemChaosArmor)new ItemChaosArmor(armorQUEEN, proxy.setArmorPrefix("queen"), 1).setTranslationKey("queen_chest").setRegistryName("chaospersists", "queen_chest");
    QueenLegs = (ItemChaosArmor)new ItemChaosArmor(armorQUEEN, proxy.setArmorPrefix("queen"), 2).setTranslationKey("queen_leggings").setRegistryName("chaospersists", "queen_leggings");
    QueenBoots = (ItemChaosArmor)new ItemChaosArmor(armorQUEEN, proxy.setArmorPrefix("queen"), 3).setTranslationKey("queen_boots").setRegistryName("chaospersists", "queen_boots");

    MyOreSaltBlock = new OreSalt().setTranslationKey("oresalt").setRegistryName("chaospersists", "oresalt");
    MySalt = new ItemSalt(BaseItemID + 178).setTranslationKey("salt").setRegistryName("chaospersists", "salt");
    MyPopcorn = new ItemPopcorn(1, 0.5F, false).setTranslationKey("popcorn").setRegistryName("chaospersists", "popcorn");
    MyButteredPopcorn = new ItemPopcorn(2, 0.6F, false).setTranslationKey("popcorn_buttered").setRegistryName("chaospersists", "popcorn_buttered");
    MyButteredSaltedPopcorn = new ItemPopcorn(3, 0.75F, false).setTranslationKey("popcorn_buttered_salted").setRegistryName("chaospersists", "popcorn_buttered_salted");
    MyPopcornBag = new ItemPopcorn(10, 1.25F, false).setTranslationKey("popcorn_bag").setRegistryName("chaospersists", "popcorn_bag");
    MyButter = new ItemPopcorn(1, 0.5F, false).setTranslationKey("butter").setRegistryName("chaospersists", "butter");
    MyCornDog = new ItemPopcorn(16, 2.5F, false).setTranslationKey("corndog_cooked").setRegistryName("chaospersists", "corndog_cooked");
    MyRawCornDog = new ItemPopcorn(4, 0.6F, false).setTranslationKey("corndog_raw").setRegistryName("chaospersists", "corndog_raw");
    MyButterCandy = new ItemSunFish(4, 0.5F, false).setTranslationKey("buttercandy").setRegistryName("chaospersists", "buttercandy");
    MyBacon = new ItemSunFish(14, 1.5F, false).setTranslationKey("cookedbacon").setRegistryName("chaospersists", "cookedbacon");
    MyRawBacon = new ItemPopcorn(8, 1.0F, false).setTranslationKey("bacon").setRegistryName("chaospersists", "bacon");
    MyCrabMeat = new ItemSunFish(6, 0.75F, false).setTranslationKey("cookedcrabmeat").setRegistryName("chaospersists", "cookedcrabmeat");
    MyRawCrabMeat = new ItemPopcorn(4, 0.25F, false).setTranslationKey("crabmeat").setRegistryName("chaospersists", "crabmeat");
    MyCheese = new ItemPopcorn(4, 0.5F, false).setTranslationKey("cheese").setRegistryName("chaospersists", "cheese");
    MySalad = new ItemPopcorn(10, 0.95F, false).setTranslationKey("salad").setRegistryName("chaospersists", "salad");
    MyBLT = new ItemPopcorn(12, 0.95F, false).setTranslationKey("blt_sandwich").setRegistryName("chaospersists", "blt_sandwich");
    MyCrabbyPatty = new ItemPopcorn(16, 2.35F, false).setTranslationKey("crabbypatty").setRegistryName("chaospersists", "crabbypatty");
    MyOreRubyBlock = new OreRuby().setTranslationKey("oreruby").setRegistryName("chaospersists", "oreruby");
    MyRuby = new ItemSalt(BaseItemID + 270).setTranslationKey("ruby").setRegistryName("chaospersists", "ruby");
    MyOreAmethystBlock = new OreAmethyst().setTranslationKey("oreamethyst").setRegistryName("chaospersists", "oreamethyst");
    MyAmethyst = new ItemSalt(BaseItemID + 260).setTranslationKey("amethyst").setRegistryName("chaospersists", "amethyst");
    UraniumNugget = new ItemSalt(BaseItemID + 150).setTranslationKey("uranium_nugget").setRegistryName("chaospersists", "uranium_nugget");
    TitaniumNugget = new ItemSalt(BaseItemID + 151).setTranslationKey("titanium_nugget").setRegistryName("chaospersists", "titanium_nugget");
    CrystalStone = new OreBasicStone(2.0F, 10.0F).setTranslationKey("crystalstone").setRegistryName("chaospersists", "crystalstone");
    CrystalCoal = new OreCrystal(0.6F, 6.0F, 20.0F).setTranslationKey("crystalcoal").setRegistryName("chaospersists", "crystalcoal");
    CrystalGrass = new CrystalGrass(0.6F, 2.0F).setTranslationKey("crystalgrass").setRegistryName("chaospersists", "crystalgrass");
    CrystalCrystal = new OreCrystalCrystal(0.4F, 12.0F, 40.0F).setTranslationKey("crystalcrystal").setRegistryName("chaospersists", "crystalcrystal");
    TigersEye = new OreCrystalCrystal(0.5F, 15.0F, 60.0F).setTranslationKey("tigerseye").setRegistryName("chaospersists", "tigerseye");
    CrystalPlanksBlock = new CrystalWood(1.5F, 4.0F).setTranslationKey("crystalplanks").setRegistryName("chaospersists", "crystalplanks");
    CrystalWorkbenchBlock = new CrystalWorkbench(1.0F, 5.0F).setTranslationKey("crystalworkbench").setRegistryName("chaospersists", "crystalworkbench");
    CrystalFurnaceBlock = new CrystalFurnace(2.0F, 10.0F);
    CrystalFurnaceBlock.setTranslationKey("crystalfurnace");
    CrystalFurnaceBlock.setRegistryName("chaospersists", "crystalfurnace");
    MyPeacock = new ItemPopcorn(12, 1.4F, false).setTranslationKey("cookedpeacock").setRegistryName("chaospersists", "cookedpeacock");
    MyRawPeacock = new ItemPopcorn(6, 0.7F, false).setTranslationKey("rawpeacock").setRegistryName("chaospersists", "rawpeacock");
    CrystalRat = new OreBasicStone(2.5F, 14.0F).setTranslationKey("crystalrat").setRegistryName("chaospersists", "crystalrat");
    CrystalFairy = new OreBasicStone(2.5F, 14.0F).setTranslationKey("crystalfairy").setRegistryName("chaospersists", "crystalfairy");
    RedAntTroll = new OreBasicStone(2.5F, 14.0F).setTranslationKey("redanttroll").setRegistryName("chaospersists", "redanttroll");
    TermiteTroll = new OreBasicStone(2.5F, 14.0F).setTranslationKey("termitetroll").setRegistryName("chaospersists", "termitetroll");

    MyRTPBlock = new RTPBlock().setTranslationKey("blockteleport").setRegistryName("chaospersists", "blockteleport");
    MyStepUp = new StepUp(BaseItemID + 232).setTranslationKey("step_up").setRegistryName("chaospersists", "step_up");
    MyStepDown = new StepDown(BaseItemID + 233).setTranslationKey("step_down").setRegistryName("chaospersists", "step_down");
    MyStepAccross = new StepAccross(BaseItemID + 234).setTranslationKey("step_accross").setRegistryName("chaospersists", "step_accross");
    MyMoleDirtBlock = new MoleDirtBlock().setHardness(0.6F).setTranslationKey("moledirt").setRegistryName("chaospersists", "moledirt");

    initializeCagesAndEggs();

    MyStrawberry = new ItemStrawberry(2, 0.65F, false).setTranslationKey("strawberry").setRegistryName("chaospersists", "strawberry");
    MyStrawberryPlant = new BlockStrawberry().setRegistryName("chaospersists", "strawberry_plant").setTranslationKey("strawberry_plant");
    MyStrawberrySeed = new ItemStrawberrySeed(MyStrawberryPlant, Blocks.FARMLAND).setTranslationKey("strawberry_seed").setRegistryName("chaospersists", "strawberry_seed");
    MyButterflyPlant = new BlockButterflyPlant().setRegistryName("chaospersists", "butterfly_plant").setTranslationKey("butterfly_plant");
    MyButterflySeed = new ItemButterflySeed(MyButterflyPlant, Blocks.FARMLAND).setTranslationKey("butterfly_seed").setRegistryName("chaospersists", "butterfly_seed");
    MyMothPlant = new BlockMothPlant().setRegistryName("chaospersists", "moth_plant").setTranslationKey("moth_plant");
    MyMothSeed = new ItemMothSeed(MyMothPlant, Blocks.FARMLAND).setTranslationKey("moth_seed").setRegistryName("chaospersists", "moth_seed");
    MyMosquitoPlant = new BlockMosquitoPlant().setRegistryName("chaospersists", "mosquito_plant").setTranslationKey("mosquito_plant");
    MyMosquitoSeed = new ItemMosquitoSeed(MyMosquitoPlant, Blocks.FARMLAND).setTranslationKey("mosquito_seed").setRegistryName("chaospersists", "mosquito_seed");
    MyFireflyPlant = new BlockFireflyPlant().setRegistryName("chaospersists", "firefly_plant").setTranslationKey("firefly_plant");
    MyFireflySeed = new ItemFireflySeed(MyFireflyPlant, Blocks.FARMLAND).setTranslationKey("firefly_seed").setRegistryName("chaospersists", "firefly_seed");
    MyRadishPlant = new BlockRadish().setTranslationKey("radish_plant").setRegistryName("chaospersists", "radish_plant");
    MyRadish = new ItemRadish(2, 0.45F, MyRadishPlant, Blocks.FARMLAND).setTranslationKey("radish").setRegistryName("chaospersists", "radish");
    MyCherry = new ItemStrawberry(3, 0.45F, false).setTranslationKey("cherries").setRegistryName("chaospersists", "cherries");
    MyPeach = new ItemStrawberry(4, 0.55F, false).setTranslationKey("peach").setRegistryName("chaospersists", "peach");
    MyCrystalApple = new ItemSunFish(5, 0.85F, false).setTranslationKey("crystalapple").setRegistryName("chaospersists", "crystalapple");
    MyLove = new ItemSunFish(8, 0.95F, false).setTranslationKey("heart").setRegistryName("chaospersists", "heart");
    MyRicePlant = new BlockRice().setTranslationKey("rice_plant").setRegistryName("chaospersists", "rice_plant");
    MyRice = new ItemRadish(5, 0.65F, MyRicePlant, CrystalGrass).setTranslationKey("rice").setRegistryName("chaospersists", "rice");

    MyElevator = new ItemElevator(BaseItemID + 235).setTranslationKey("elevator").setRegistryName("chaospersists", "elevator");

    MyCornPlant1 = new BlockCorn().setTranslationKey("corn_plant0").setRegistryName("chaospersists", "corn_plant0");
    MyCornPlant2 = new BlockCorn().setTranslationKey("corn_plant1").setRegistryName("chaospersists", "corn_plant1");
    MyCornPlant3 = new BlockCorn().setTranslationKey("corn_plant2").setRegistryName("chaospersists", "corn_plant2");
    MyCornPlant4 = new BlockCorn().setTranslationKey("corn_plant3").setRegistryName("chaospersists", "corn_plant3");
    MyCornCob = new ItemCornCob(6, 0.75F, MyCornPlant1, Blocks.FARMLAND).setTranslationKey("corn_seed").setRegistryName("chaospersists", "corn_seed");
    MyQuinoaPlant1 = new BlockQuinoa().setTranslationKey("quinoa_0").setRegistryName("chaospersists", "quinoa_0");
    MyQuinoaPlant2 = new BlockQuinoa().setTranslationKey("quinoa_1").setRegistryName("chaospersists", "quinoa_1");
    MyQuinoaPlant3 = new BlockQuinoa().setTranslationKey("quinoa_2").setRegistryName("chaospersists", "quinoa_2");
    MyQuinoaPlant4 = new BlockQuinoa().setTranslationKey("quinoa_3").setRegistryName("chaospersists", "quinoa_3");
    MyQuinoa = new ItemCornCob(7, 0.85F, MyQuinoaPlant1, CrystalGrass).setTranslationKey("quinoa").setRegistryName("chaospersists", "quinoa");

    MyTomatoPlant1 = new BlockTomato().setTranslationKey("tomato_plant0").setRegistryName("chaospersists", "tomato_plant0");
    MyTomatoPlant2 = new BlockTomato().setTranslationKey("tomato_plant1").setRegistryName("chaospersists", "tomato_plant1");
    MyTomatoPlant3 = new BlockTomato().setTranslationKey("tomato_plant2").setRegistryName("chaospersists", "tomato_plant2");
    MyTomatoPlant4 = new BlockTomato().setTranslationKey("tomato_plant3").setRegistryName("chaospersists", "tomato_plant3");
    MyTomato = new ItemTomato(4, 0.55F, MyTomatoPlant1, Blocks.FARMLAND).setTranslationKey("tomato_seed").setRegistryName("chaospersists", "tomato_seed");
    MyLettucePlant1 = new BlockLettuce().setTranslationKey("lettuce_0").setRegistryName("chaospersists", "lettuce_0");
    MyLettucePlant2 = new BlockLettuce().setTranslationKey("lettuce_1").setRegistryName("chaospersists", "lettuce_1");
    MyLettucePlant3 = new BlockLettuce().setTranslationKey("lettuce_2").setRegistryName("chaospersists", "lettuce_2");
    MyLettucePlant4 = new BlockLettuce().setTranslationKey("lettuce_3").setRegistryName("chaospersists", "lettuce_3");
    MyLettuce = new ItemLettuce(3, 0.45F, MyLettucePlant1, Blocks.FARMLAND).setTranslationKey("lettuce_seed").setRegistryName("chaospersists", "lettuce_seed");

    MagicApple = new ItemMagicApple(BaseItemID + 236).setTranslationKey("magicapple").setRegistryName("chaospersists", "magicapple");
    MinersDream = new ItemMinersDream(BaseItemID + 237).setTranslationKey("minersdream").setRegistryName("chaospersists", "minersdream");
    ExtremeTorch = new BlockExtremeTorch().setLightLevel(1.0F).setTranslationKey("extremetorch").setRegistryName("chaospersists", "extremetorch");
    KrakenRepellent = new KrakenRepellent().setLightLevel(0.8F).setTranslationKey("krakenrepellent").setRegistryName("chaospersists", "krakenrepellent");
    MyIslandBlock = new IslandBlock().setLightLevel(0.9F).setTranslationKey("island").setRegistryName("chaospersists", "island");
    CreeperRepellent = new CreeperRepellent().setLightLevel(0.8F).setTranslationKey("creeperrepellent").setRegistryName("chaospersists", "creeperrepellent");
    ZooCage2 = new ZooCage(0, 3).setTranslationKey("zoo2").setRegistryName("chaospersists", "zoo2");
    ZooCage4 = new ZooCage(0, 5).setTranslationKey("zoo4").setRegistryName("chaospersists", "zoo4");
    ZooCage6 = new ZooCage(0, 9).setTranslationKey("zoo6").setRegistryName("chaospersists", "zoo6");
    ZooCage8 = new ZooCage(0, 13).setTranslationKey("zoo8").setRegistryName("chaospersists", "zoo8");
    ZooCage10 = new ZooCage(0, 17).setTranslationKey("zoo10").setRegistryName("chaospersists", "zoo10");
    InstantShelter = new InstantShelter(BaseItemID + 327).setTranslationKey("instantshelter").setRegistryName("chaospersists", "instantshelter");
    InstantGarden = new InstantGarden(BaseItemID + 328).setTranslationKey("instantgarden").setRegistryName("chaospersists", "instantgarden");
    CrystalTorch = new BlockCrystalTorch().setLightLevel(0.99F).setTranslationKey("crystaltorch").setRegistryName("chaospersists", "crystaltorch");
    MyKingSpawnerBlock = new KingSpawnerBlock().setLightLevel(0.9F).setTranslationKey("kingspawner").setRegistryName("chaospersists", "kingspawner");
    MyQueenSpawnerBlock = new QueenSpawnerBlock().setLightLevel(0.9F).setTranslationKey("queenspawner").setRegistryName("chaospersists", "queenspawner");
    RandomDungeon = new ItemRandomDungeon(BaseItemID + 421).setTranslationKey("randomdungeon").setRegistryName("chaospersists", "randomdungeon");
    MyDungeonSpawnerBlock = new DungeonSpawnerBlock().setLightLevel(0.9F).setTranslationKey("dungeonspawner").setRegistryName("chaospersists", "dungeonspawner");

    MyAppleLeaves = (BlockAppleLeaves)new BlockAppleLeaves().setHardness(0.2F).setLightOpacity(1).setTranslationKey("leaves_apple").setRegistryName("chaospersists", "leaves_apple");
    MyAppleSeed = new ItemAppleSeed(BaseItemID + 211).setTranslationKey("appletree_seed").setRegistryName("chaospersists", "appletree_seed");
    MySkyTreeLog = (BlockSkyTreeLog)new BlockSkyTreeLog().setHardness(0.2F).setTranslationKey("skytreelog").setRegistryName("chaospersists", "skytreelog");

    MyDT = (BlockDuplicatorLog)new BlockDuplicatorLog().setHardness(0.2F).setTranslationKey("duplicatortreelog").setRegistryName("chaospersists", "duplicatortreelog");
    MyExperienceLeaves = (BlockExperienceLeaves)new BlockExperienceLeaves().setHardness(0.2F).setLightOpacity(1).setTranslationKey("leaves_experience").setRegistryName("chaospersists", "leaves_experience");
    MyExperienceCatcher = new ExperienceCatcher(BaseItemID + 238).setTranslationKey("experiencecatcher").setRegistryName("chaospersists", "experiencecatcher");
    MyExperienceTreeSeed = new ItemExperienceTreeSeed(BaseItemID + 216).setTranslationKey("experiencetree_seed").setRegistryName("chaospersists", "experiencetree_seed");
    MyExperiencePlant = new BlockExperiencePlant().setTranslationKey("experiencesapling").setRegistryName("chaospersists", "experiencesapling");
    MyDeadStinkBug = new ItemSalt(BaseItemID + 155).setTranslationKey("deadstinkbug").setRegistryName("chaospersists", "deadstinkbug");
    MyFlowerPinkBlock = (MyBlockFlower)new MyBlockFlower().setHardness(0.0F).setTranslationKey("flower_pink").setRegistryName("chaospersists", "flower_pink");
    MyFlowerBlueBlock = (MyBlockFlower)new MyBlockFlower().setHardness(0.0F).setTranslationKey("flower_blue").setRegistryName("chaospersists", "flower_blue");
    MyFlowerBlackBlock = (MyBlockFlower)new MyBlockFlower().setHardness(0.0F).setTranslationKey("flower_black").setRegistryName("chaospersists", "flower_black");
    MyFlowerScaryBlock = (MyBlockFlower)new MyBlockFlower().setHardness(0.0F).setTranslationKey("flower_scary").setRegistryName("chaospersists", "flower_scary");
    MyScaryLeaves = (BlockScaryLeaves)new BlockScaryLeaves().setHardness(0.2F).setLightOpacity(1).setTranslationKey("leaves_scary").setRegistryName("chaospersists", "leaves_scary");
    MyCherryLeaves = (BlockScaryLeaves)new BlockScaryLeaves().setHardness(0.15F).setLightOpacity(1).setTranslationKey("leaves_cherry").setRegistryName("chaospersists", "leaves_cherry");
    MyPeachLeaves = (BlockScaryLeaves)new BlockScaryLeaves().setHardness(0.15F).setLightOpacity(1).setTranslationKey("leaves_peach").setRegistryName("chaospersists", "leaves_peach");
    MyCherrySeed = new ItemAppleSeed(BaseItemID + 217).setTranslationKey("cherrytree_seed").setRegistryName("chaospersists", "cherrytree_seed");
    MyPeachSeed = new ItemAppleSeed(BaseItemID + 218).setTranslationKey("peachtree_seed").setRegistryName("chaospersists", "peachtree_seed");
    CrystalFlowerRedBlock = (MyBlockFlower)new MyBlockFlower().setHardness(0.0F).setTranslationKey("crystalflower_red").setRegistryName("chaospersists", "crystalflower_red");
    CrystalFlowerGreenBlock = (MyBlockFlower)new MyBlockFlower().setHardness(0.0F).setTranslationKey("crystalflower_green").setRegistryName("chaospersists", "crystalflower_green");
    CrystalFlowerBlueBlock = (MyBlockFlower)new MyBlockFlower().setHardness(0.0F).setTranslationKey("crystalflower_blue").setRegistryName("chaospersists", "crystalflower_blue");
    CrystalFlowerYellowBlock = (MyBlockFlower)new MyBlockFlower().setHardness(0.0F).setTranslationKey("crystalflower_yellow").setRegistryName("chaospersists", "crystalflower_yellow");
    MyCrystalLeaves = (BlockCrystalLeaves)new BlockCrystalLeaves().setHardness(0.2F).setLightOpacity(1).setTranslationKey("crystaltreeleaves").setRegistryName("chaospersists", "crystaltreeleaves");
    MyCrystalTreeLog = (BlockCrystalTreeLog)new BlockCrystalTreeLog().setHardness(0.2F).setTranslationKey("crystaltreelog").setRegistryName("chaospersists", "crystaltreelog");
    MyCrystalLeaves2 = (BlockCrystalLeaves)new BlockCrystalLeaves().setHardness(0.25F).setLightOpacity(1).setTranslationKey("crystaltreeleaves2").setRegistryName("chaospersists", "crystaltreeleaves2");
    MyCrystalLeaves3 = (BlockCrystalLeaves)new BlockCrystalLeaves().setHardness(0.25F).setLightOpacity(1).setTranslationKey("crystaltreeleaves3").setRegistryName("chaospersists", "crystaltreeleaves3");
    MyCrystalPlant = new BlockCrystalPlant().setTranslationKey("crystalsapling").setRegistryName("chaospersists", "crystalsapling");
    MyCrystalPlant2 = new BlockCrystalPlant().setTranslationKey("crystalsapling2").setRegistryName("chaospersists", "crystalsapling2");
    MyCrystalPlant3 = new BlockCrystalPlant().setTranslationKey("crystalsapling3").setRegistryName("chaospersists", "crystalsapling3");

    MyEnderPearlBlock = new OreGenericEgg().setTranslationKey("blockenderpearl").setRegistryName("chaospersists", "blockenderpearl");
    MyEyeOfEnderBlock = new OreGenericEgg().setTranslationKey("blockeyeofender").setRegistryName("chaospersists", "blockeyeofender");

    make_some_more_things();
    proxy.registerBlockModels();
  }

  private final Map<String, Integer> recipeNameUseCounts = new HashMap<String, Integer>();

  private ResourceLocation nextRecipeId(ResourceLocation baseId)
  {
    String key = baseId.toString();
    Integer seen = recipeNameUseCounts.get(key);
    if (seen == null) {
      recipeNameUseCounts.put(key, Integer.valueOf(1));
      return baseId;
    }
    int suffix = seen.intValue();
    recipeNameUseCounts.put(key, Integer.valueOf(suffix + 1));
    return new ResourceLocation(baseId.getNamespace(), baseId.getPath() + "_" + suffix);
  }

  private void addShapedRecipe(ResourceLocation name, ResourceLocation group, ItemStack output, Object... params)
  {
    GameRegistry.addShapedRecipe(nextRecipeId(name), group, output, params);
  }

  private void addShapelessRecipe(ResourceLocation name, ResourceLocation group, ItemStack output, Ingredient... ingredients)
  {
    GameRegistry.addShapelessRecipe(nextRecipeId(name), group, output, ingredients);
  }

  private ItemStack createVanillaSpawnEgg(String entityId)
  {
    ItemStack egg = new ItemStack(Items.SPAWN_EGG);
    ItemMonsterPlacer.applyEntityIdToItemStack(egg, new ResourceLocation("minecraft", entityId));
    return egg;
  }

  private void make_some_more_things()
  {
    recipeNameUseCounts.clear();
    GameRegistry.findRegistry(Block.class).register(MySpiderSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyBatSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCowSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyPigSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySquidSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyChickenSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCreeperSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySkeletonSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyZombieSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySlimeSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyGhastSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyZombiePigmanSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyEndermanSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCaveSpiderSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySilverfishSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyMagmaCubeSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyWitchSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySheepSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyWolfSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyMooshroomSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyWitherBossSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyGirlfriendSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyBoyfriendSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyRedCowSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCrystalCowSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyVillagerSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyGoldCowSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyEnchantedCowSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyMOTHRASpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyAloSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCryoSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCamaSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyVeloSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyHydroSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyBasilSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyDragonflySpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyEmperorScorpionSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyScorpionSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCaveFisherSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySpyroSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyBaryonyxSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyGammaMetroidSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCockateilSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyKyuubiSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyAlienSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyIronGolemSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySnowGolemSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyEnderDragonSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyOcelotSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyWitherSkeletonSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyBlazeSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyAttackSquidSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyWaterDragonSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCephadromeSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyKrakenSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyLizardSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyDragonSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyBeeSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyHorseSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTrooperBugSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySpitBugSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyStinkBugSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyOstrichSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyGazelleSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyChipmunkSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCreepingHorrorSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTerribleTerrorSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCliffRacerSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTriffidSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyPitchBlackSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyLurkingTerrorSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyGodzillaPartSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyGodzillaSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTheKingPartSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTheKingSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTheQueenPartSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTheQueenSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySmallWormSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyMediumWormSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyLargeWormSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCassowarySpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCloudSharkSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyGoldFishSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyLeafMonsterSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTshirtSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyEnderKnightSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyEnderReaperSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyBeaverSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyUrchinSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyFlounderSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySkateSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyRotatorSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyPeacockSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyFairySpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyDungeonBeastSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyVortexSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyRatSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyWhaleSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyIrukandjiSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyTRexSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyHerculesSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyMantisSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyStinkySpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyEasterBunnySpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCaterKillerSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyMolenoidSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySeaMonsterSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySeaViperSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyLeonSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyHammerheadSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyRubberDuckySpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCriminalSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyBrutalflySpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyNastysaurusSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyPointysaurusSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCricketSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyFrogSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MySpiderDriverSpawnBlock);
    GameRegistry.findRegistry(Block.class).register(MyCrabSpawnBlock);

    GameRegistry.findRegistry(Block.class).register(MyOreSaltBlock);
    GameRegistry.findRegistry(Block.class).register(MyRTPBlock);
    GameRegistry.findRegistry(Block.class).register(MyMoleDirtBlock);
    GameRegistry.findRegistry(Block.class).register(MyOreTitaniumBlock);
    GameRegistry.findRegistry(Block.class).register(MyOreUraniumBlock);
    GameRegistry.findRegistry(Block.class).register(MyBlockTitaniumBlock);
    GameRegistry.findRegistry(Block.class).register(MyBlockMobzillaScaleBlock);
    GameRegistry.findRegistry(Block.class).register(MyBlockUraniumBlock);
    GameRegistry.findRegistry(Block.class).register(MyLavafoamBlock);
    GameRegistry.findRegistry(Block.class).register(MyOreRubyBlock);
    GameRegistry.findRegistry(Block.class).register(MyBlockRubyBlock);
    GameRegistry.findRegistry(Block.class).register(MyOreAmethystBlock);
    GameRegistry.findRegistry(Block.class).register(MyBlockAmethystBlock);
    GameRegistry.findRegistry(Block.class).register(MyCrystalPinkBlock);
    GameRegistry.findRegistry(Block.class).register(MyTigersEyeBlock);
    GameRegistry.findRegistry(Block.class).register(MyPizzaBlock);
    GameRegistry.findRegistry(Block.class).register(MyDuctTapeBlock);
    GameRegistry.findRegistry(Block.class).register(CrystalStone);
    GameRegistry.findRegistry(Block.class).register(CrystalRat);
    GameRegistry.findRegistry(Block.class).register(RedAntTroll);
    GameRegistry.findRegistry(Block.class).register(TermiteTroll);
    GameRegistry.findRegistry(Block.class).register(CrystalFairy);
    GameRegistry.findRegistry(Block.class).register(CrystalCoal);
    GameRegistry.findRegistry(Block.class).register(CrystalGrass);
    GameRegistry.findRegistry(Block.class).register(CrystalCrystal);
    GameRegistry.findRegistry(Block.class).register(TigersEye);
    GameRegistry.findRegistry(Block.class).register(CrystalPlanksBlock);
    GameRegistry.findRegistry(Block.class).register(CrystalWorkbenchBlock);
    GameRegistry.findRegistry(Block.class).register(CrystalFurnaceBlock);

    GameRegistry.findRegistry(Block.class).register(MyStrawberryPlant);
    GameRegistry.findRegistry(Block.class).register(MyRadishPlant);
    GameRegistry.findRegistry(Block.class).register(MyRicePlant);
    GameRegistry.findRegistry(Block.class).register(MyButterflyPlant);
    GameRegistry.findRegistry(Block.class).register(MyMothPlant);
    GameRegistry.findRegistry(Block.class).register(MyMosquitoPlant);
    GameRegistry.findRegistry(Block.class).register(MyFireflyPlant);
    GameRegistry.findRegistry(Block.class).register(MyCornPlant1);
    GameRegistry.findRegistry(Block.class).register(MyCornPlant2);
    GameRegistry.findRegistry(Block.class).register(MyCornPlant3);
    GameRegistry.findRegistry(Block.class).register(MyCornPlant4);
    GameRegistry.findRegistry(Block.class).register(MyQuinoaPlant1);
    GameRegistry.findRegistry(Block.class).register(MyQuinoaPlant2);
    GameRegistry.findRegistry(Block.class).register(MyQuinoaPlant3);
    GameRegistry.findRegistry(Block.class).register(MyQuinoaPlant4);
    GameRegistry.findRegistry(Block.class).register(MyTomatoPlant1);
    GameRegistry.findRegistry(Block.class).register(MyTomatoPlant2);
    GameRegistry.findRegistry(Block.class).register(MyTomatoPlant3);
    GameRegistry.findRegistry(Block.class).register(MyTomatoPlant4);
    GameRegistry.findRegistry(Block.class).register(MyLettucePlant1);
    GameRegistry.findRegistry(Block.class).register(MyLettucePlant2);
    GameRegistry.findRegistry(Block.class).register(MyLettucePlant3);
    GameRegistry.findRegistry(Block.class).register(MyLettucePlant4);
    GameRegistry.findRegistry(Block.class).register(MyAppleLeaves);
    GameRegistry.findRegistry(Block.class).register(MyExperienceLeaves);
    GameRegistry.findRegistry(Block.class).register(MyScaryLeaves);
    GameRegistry.findRegistry(Block.class).register(MyCherryLeaves);
    GameRegistry.findRegistry(Block.class).register(MyPeachLeaves);
    GameRegistry.findRegistry(Block.class).register(MySkyTreeLog);
    GameRegistry.findRegistry(Block.class).register(MyDT);
    GameRegistry.findRegistry(Block.class).register(MyExperiencePlant);
    GameRegistry.findRegistry(Block.class).register(MyCrystalPlant);
    GameRegistry.findRegistry(Block.class).register(MyCrystalPlant2);
    GameRegistry.findRegistry(Block.class).register(MyCrystalPlant3);
    GameRegistry.findRegistry(Block.class).register(MyFlowerPinkBlock);
    GameRegistry.findRegistry(Block.class).register(MyFlowerBlueBlock);
    GameRegistry.findRegistry(Block.class).register(MyFlowerBlackBlock);
    GameRegistry.findRegistry(Block.class).register(MyFlowerScaryBlock);
    GameRegistry.findRegistry(Block.class).register(CrystalFlowerRedBlock);
    GameRegistry.findRegistry(Block.class).register(CrystalFlowerGreenBlock);
    GameRegistry.findRegistry(Block.class).register(CrystalFlowerBlueBlock);
    GameRegistry.findRegistry(Block.class).register(CrystalFlowerYellowBlock);
    GameRegistry.findRegistry(Block.class).register(MyCrystalLeaves);
    GameRegistry.findRegistry(Block.class).register(MyCrystalLeaves2);
    GameRegistry.findRegistry(Block.class).register(MyCrystalLeaves3);
    GameRegistry.findRegistry(Block.class).register(MyCrystalTreeLog);

    GameRegistry.findRegistry(Block.class).register(ExtremeTorch);
    GameRegistry.findRegistry(Block.class).register(CrystalTorch);
    GameRegistry.findRegistry(Block.class).register(KrakenRepellent);
    GameRegistry.findRegistry(Block.class).register(CreeperRepellent);
    GameRegistry.findRegistry(Block.class).register(MyIslandBlock);
    GameRegistry.findRegistry(Block.class).register(MyKingSpawnerBlock);
    GameRegistry.findRegistry(Block.class).register(MyQueenSpawnerBlock);
    GameRegistry.findRegistry(Block.class).register(MyDungeonSpawnerBlock);

    GameRegistry.findRegistry(Block.class).register(MyEnderPearlBlock);
    GameRegistry.findRegistry(Block.class).register(MyEyeOfEnderBlock);
    GameRegistry.findRegistry(Block.class).register(MyAntBlock);
    GameRegistry.findRegistry(Block.class).register(MyRedAntBlock);
    GameRegistry.findRegistry(Block.class).register(TermiteBlock);
    GameRegistry.findRegistry(Block.class).register(CrystalTermiteBlock);
    GameRegistry.findRegistry(Block.class).register(MyRainbowAntBlock);
    GameRegistry.findRegistry(Block.class).register(MyUnstableAntBlock);

    for (Block block : Block.REGISTRY) {
        ResourceLocation rl = block.getRegistryName();
        if (rl != null && "chaospersists".equals(rl.getNamespace())) {
            String path = rl.getPath();
            if (!"pizza".equals(path) && !"ducttape".equals(path) && !"island".equals(path)) {
                ItemBlock itemBlock = new ItemBlock(block);
                itemBlock.setRegistryName(rl);
                itemBlock.setTranslationKey(block.getTranslationKey());
                GameRegistry.findRegistry(Item.class).register(itemBlock);
            }
        }
    }

    GameRegistry.findRegistry(Item.class).register(MyPizzaItem);
    GameRegistry.findRegistry(Item.class).register(MyDuctTapeItem);
    IslandBlock.ItemIslandBlock islandItem = new IslandBlock.ItemIslandBlock(MyIslandBlock);
    islandItem.setRegistryName(MyIslandBlock.getRegistryName());
    islandItem.setTranslationKey(MyIslandBlock.getTranslationKey());
    GameRegistry.findRegistry(Item.class).register(islandItem);
    GameRegistry.findRegistry(Item.class).register(MyIngotUranium);
    GameRegistry.findRegistry(Item.class).register(MyCrystalPinkIngot);
    GameRegistry.findRegistry(Item.class).register(MyTigersEyeIngot);
    GameRegistry.findRegistry(Item.class).register(MyIngotTitanium);
    GameRegistry.findRegistry(Item.class).register(MyUltimateSword);
    GameRegistry.findRegistry(Item.class).register(MyNightmareSword);
    GameRegistry.findRegistry(Item.class).register(MyBertha);
    GameRegistry.findRegistry(Item.class).register(MyHammy);
    GameRegistry.findRegistry(Item.class).register(MySlice);
    GameRegistry.findRegistry(Item.class).register(MyRoyal);
    GameRegistry.findRegistry(Item.class).register(MyBattleAxe);
    GameRegistry.findRegistry(Item.class).register(MyQueenBattleAxe);
    GameRegistry.findRegistry(Item.class).register(MyChainsaw);
    GameRegistry.findRegistry(Item.class).register(MyUltimatePickaxe);
    GameRegistry.findRegistry(Item.class).register(MyUltimateShovel);
    GameRegistry.findRegistry(Item.class).register(MyUltimateHoe);
    GameRegistry.findRegistry(Item.class).register(MyUltimateAxe);
    GameRegistry.findRegistry(Item.class).register(MyEmeraldSword);
    GameRegistry.findRegistry(Item.class).register(MyRoseSword);
    GameRegistry.findRegistry(Item.class).register(MyExperienceSword);
    GameRegistry.findRegistry(Item.class).register(MyPoisonSword);
    GameRegistry.findRegistry(Item.class).register(MyRatSword);
    GameRegistry.findRegistry(Item.class).register(MyFairySword);
    GameRegistry.findRegistry(Item.class).register(MyMantisClaw);
    GameRegistry.findRegistry(Item.class).register(MyBigHammer);
    GameRegistry.findRegistry(Item.class).register(MyEmeraldPickaxe);
    GameRegistry.findRegistry(Item.class).register(MyEmeraldShovel);
    GameRegistry.findRegistry(Item.class).register(MyEmeraldHoe);
    GameRegistry.findRegistry(Item.class).register(MyEmeraldAxe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalWoodSword);
    GameRegistry.findRegistry(Item.class).register(MyCrystalWoodPickaxe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalWoodShovel);
    GameRegistry.findRegistry(Item.class).register(MyCrystalWoodHoe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalWoodAxe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalPinkSword);
    GameRegistry.findRegistry(Item.class).register(MyCrystalPinkPickaxe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalPinkShovel);
    GameRegistry.findRegistry(Item.class).register(MyCrystalPinkHoe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalPinkAxe);
    GameRegistry.findRegistry(Item.class).register(MyTigersEyeSword);
    GameRegistry.findRegistry(Item.class).register(MyTigersEyePickaxe);
    GameRegistry.findRegistry(Item.class).register(MyTigersEyeShovel);
    GameRegistry.findRegistry(Item.class).register(MyTigersEyeHoe);
    GameRegistry.findRegistry(Item.class).register(MyTigersEyeAxe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalStoneSword);
    GameRegistry.findRegistry(Item.class).register(MyCrystalStonePickaxe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalStoneShovel);
    GameRegistry.findRegistry(Item.class).register(MyCrystalStoneHoe);
    GameRegistry.findRegistry(Item.class).register(MyCrystalStoneAxe);
    GameRegistry.findRegistry(Item.class).register(MyRubySword);
    GameRegistry.findRegistry(Item.class).register(MyRubyPickaxe);
    GameRegistry.findRegistry(Item.class).register(MyRubyShovel);
    GameRegistry.findRegistry(Item.class).register(MyRubyHoe);
    GameRegistry.findRegistry(Item.class).register(MyRubyAxe);
    GameRegistry.findRegistry(Item.class).register(MyAmethystSword);
    GameRegistry.findRegistry(Item.class).register(MyAmethystPickaxe);
    GameRegistry.findRegistry(Item.class).register(MyAmethystShovel);
    GameRegistry.findRegistry(Item.class).register(MyAmethystHoe);
    GameRegistry.findRegistry(Item.class).register(MyAmethystAxe);
    GameRegistry.findRegistry(Item.class).register(MyItemShoes);
    GameRegistry.findRegistry(Item.class).register(MyItemShoes_1);
    GameRegistry.findRegistry(Item.class).register(MyItemShoes_2);
    GameRegistry.findRegistry(Item.class).register(MyItemShoes_3);
    GameRegistry.findRegistry(Item.class).register(MyItemGameController);
    GameRegistry.findRegistry(Item.class).register(MyUltimateBow);
    GameRegistry.findRegistry(Item.class).register(MySkateBow);
    GameRegistry.findRegistry(Item.class).register(MyUltimateFishingRod);
    GameRegistry.findRegistry(Item.class).register(MyFireFish);
    GameRegistry.findRegistry(Item.class).register(MySunFish);
    GameRegistry.findRegistry(Item.class).register(MyLavaEel);
    GameRegistry.findRegistry(Item.class).register(MyMothScale);
    GameRegistry.findRegistry(Item.class).register(MyQueenScale);
    GameRegistry.findRegistry(Item.class).register(MyNightmareScale);
    GameRegistry.findRegistry(Item.class).register(MyEmperorScorpionScale);
    GameRegistry.findRegistry(Item.class).register(MyBasiliskScale);
    GameRegistry.findRegistry(Item.class).register(MyWaterDragonScale);
    GameRegistry.findRegistry(Item.class).register(MyPeacockFeather);
    GameRegistry.findRegistry(Item.class).register(MyJumpyBugScale);
    GameRegistry.findRegistry(Item.class).register(MyKrakenTooth);
    GameRegistry.findRegistry(Item.class).register(MyGodzillaScale);
    GameRegistry.findRegistry(Item.class).register(GreenGoo);
    GameRegistry.findRegistry(Item.class).register(SpiderRobotKit);
    GameRegistry.findRegistry(Item.class).register(AntRobotKit);
    GameRegistry.findRegistry(Item.class).register(ZooKeeper);
    GameRegistry.findRegistry(Item.class).register(CreeperLauncher);
    GameRegistry.findRegistry(Item.class).register(NetherLost);
    GameRegistry.findRegistry(Item.class).register(CrystalSticks);
    GameRegistry.findRegistry(Item.class).register(Sifter);
    GameRegistry.findRegistry(Item.class).register(MySunspotUrchin);
    GameRegistry.findRegistry(Item.class).register(MyWaterBall);
    GameRegistry.findRegistry(Item.class).register(MyLaserBall);
    GameRegistry.findRegistry(Item.class).register(MyIceBall);
    GameRegistry.findRegistry(Item.class).register(MySmallRock);
    GameRegistry.findRegistry(Item.class).register(MyRock);
    GameRegistry.findRegistry(Item.class).register(MyRedRock);
    GameRegistry.findRegistry(Item.class).register(MyCrystalRedRock);
    GameRegistry.findRegistry(Item.class).register(MyCrystalGreenRock);
    GameRegistry.findRegistry(Item.class).register(MyCrystalBlueRock);
    GameRegistry.findRegistry(Item.class).register(MyCrystalTNTRock);
    GameRegistry.findRegistry(Item.class).register(MyGreenRock);
    GameRegistry.findRegistry(Item.class).register(MyBlueRock);
    GameRegistry.findRegistry(Item.class).register(MyPurpleRock);
    GameRegistry.findRegistry(Item.class).register(MySpikeyRock);
    GameRegistry.findRegistry(Item.class).register(MyTNTRock);
    GameRegistry.findRegistry(Item.class).register(MyAcid);
    GameRegistry.findRegistry(Item.class).register(MyIrukandji);
    GameRegistry.findRegistry(Item.class).register(MyIrukandjiArrow);
    GameRegistry.findRegistry(Item.class).register(MyRayGun);
    GameRegistry.findRegistry(Item.class).register(MySquidZooka);
    GameRegistry.findRegistry(Item.class).register(MySparkFish);
    GameRegistry.findRegistry(Item.class).register(MySalt);
    GameRegistry.findRegistry(Item.class).register(MyPopcorn);
    GameRegistry.findRegistry(Item.class).register(MyButteredPopcorn);
    GameRegistry.findRegistry(Item.class).register(MyButteredSaltedPopcorn);
    GameRegistry.findRegistry(Item.class).register(MyPopcornBag);
    GameRegistry.findRegistry(Item.class).register(MyButter);
    GameRegistry.findRegistry(Item.class).register(MyCornDog);
    GameRegistry.findRegistry(Item.class).register(MyCheese);
    GameRegistry.findRegistry(Item.class).register(MyRawCornDog);
    GameRegistry.findRegistry(Item.class).register(MyPeacock);
    GameRegistry.findRegistry(Item.class).register(MyRawPeacock);
    GameRegistry.findRegistry(Item.class).register(MyRuby);
    GameRegistry.findRegistry(Item.class).register(MyAmethyst);
    GameRegistry.findRegistry(Item.class).register(MyThunderStaff);
    GameRegistry.findRegistry(Item.class).register(MyWrench);
    GameRegistry.findRegistry(Item.class).register(MyRawBacon);
    GameRegistry.findRegistry(Item.class).register(MyBacon);
    GameRegistry.findRegistry(Item.class).register(MyRawCrabMeat);
    GameRegistry.findRegistry(Item.class).register(MyCrabMeat);
    GameRegistry.findRegistry(Item.class).register(MyButterCandy);
    GameRegistry.findRegistry(Item.class).register(UraniumNugget);
    GameRegistry.findRegistry(Item.class).register(TitaniumNugget);
    GameRegistry.findRegistry(Item.class).register(MyGreenFish);
    GameRegistry.findRegistry(Item.class).register(MyBlueFish);
    GameRegistry.findRegistry(Item.class).register(MyPinkFish);
    GameRegistry.findRegistry(Item.class).register(MyRockFish);
    GameRegistry.findRegistry(Item.class).register(MyWoodFish);
    GameRegistry.findRegistry(Item.class).register(MyGreyFish);
    GameRegistry.findRegistry(Item.class).register(MySalad);
    GameRegistry.findRegistry(Item.class).register(MyBLT);
    GameRegistry.findRegistry(Item.class).register(MyCrabbyPatty);

    GameRegistry.findRegistry(Item.class).register(BerthaHandle);
    GameRegistry.findRegistry(Item.class).register(BerthaGuard);
    GameRegistry.findRegistry(Item.class).register(BerthaBlade);
    GameRegistry.findRegistry(Item.class).register(MolenoidNose);
    GameRegistry.findRegistry(Item.class).register(SeaMonsterScale);
    GameRegistry.findRegistry(Item.class).register(WormTooth);
    GameRegistry.findRegistry(Item.class).register(TRexTooth);
    GameRegistry.findRegistry(Item.class).register(CaterKillerJaw);
    GameRegistry.findRegistry(Item.class).register(SeaViperTongue);
    GameRegistry.findRegistry(Item.class).register(VortexEye);

    GameRegistry.findRegistry(Item.class).register(WitherSkeletonEgg);
    GameRegistry.findRegistry(Item.class).register(EnderDragonEgg);
    GameRegistry.findRegistry(Item.class).register(SnowGolemEgg);
    GameRegistry.findRegistry(Item.class).register(IronGolemEgg);
    GameRegistry.findRegistry(Item.class).register(WitherBossEgg);
    GameRegistry.findRegistry(Item.class).register(GirlfriendEgg);
    GameRegistry.findRegistry(Item.class).register(BoyfriendEgg);
    GameRegistry.findRegistry(Item.class).register(TheKingEgg);
    GameRegistry.findRegistry(Item.class).register(TheQueenEgg);
    GameRegistry.findRegistry(Item.class).register(ThePrinceEgg);
    GameRegistry.findRegistry(Item.class).register(RedCowEgg);
    GameRegistry.findRegistry(Item.class).register(CrystalCowEgg);
    GameRegistry.findRegistry(Item.class).register(GoldCowEgg);
    GameRegistry.findRegistry(Item.class).register(EnchantedCowEgg);
    GameRegistry.findRegistry(Item.class).register(MOTHRAEgg);
    GameRegistry.findRegistry(Item.class).register(AloEgg);
    GameRegistry.findRegistry(Item.class).register(CryoEgg);
    GameRegistry.findRegistry(Item.class).register(CamaEgg);
    GameRegistry.findRegistry(Item.class).register(VeloEgg);
    GameRegistry.findRegistry(Item.class).register(HydroEgg);
    GameRegistry.findRegistry(Item.class).register(BasilEgg);
    GameRegistry.findRegistry(Item.class).register(DragonflyEgg);
    GameRegistry.findRegistry(Item.class).register(EmperorScorpionEgg);
    GameRegistry.findRegistry(Item.class).register(ScorpionEgg);
    GameRegistry.findRegistry(Item.class).register(CaveFisherEgg);
    GameRegistry.findRegistry(Item.class).register(SpyroEgg);
    GameRegistry.findRegistry(Item.class).register(BaryonyxEgg);
    GameRegistry.findRegistry(Item.class).register(GammaMetroidEgg);
    GameRegistry.findRegistry(Item.class).register(CockateilEgg);
    GameRegistry.findRegistry(Item.class).register(KyuubiEgg);
    GameRegistry.findRegistry(Item.class).register(AlienEgg);
    GameRegistry.findRegistry(Item.class).register(AttackSquidEgg);
    GameRegistry.findRegistry(Item.class).register(WaterDragonEgg);
    GameRegistry.findRegistry(Item.class).register(CephadromeEgg);
    GameRegistry.findRegistry(Item.class).register(KrakenEgg);
    GameRegistry.findRegistry(Item.class).register(LizardEgg);
    GameRegistry.findRegistry(Item.class).register(DragonEgg);
    GameRegistry.findRegistry(Item.class).register(BeeEgg);
    GameRegistry.findRegistry(Item.class).register(TrooperBugEgg);
    GameRegistry.findRegistry(Item.class).register(SpitBugEgg);
    GameRegistry.findRegistry(Item.class).register(StinkBugEgg);
    GameRegistry.findRegistry(Item.class).register(OstrichEgg);
    GameRegistry.findRegistry(Item.class).register(GazelleEgg);
    GameRegistry.findRegistry(Item.class).register(ChipmunkEgg);
    GameRegistry.findRegistry(Item.class).register(CreepingHorrorEgg);
    GameRegistry.findRegistry(Item.class).register(TerribleTerrorEgg);
    GameRegistry.findRegistry(Item.class).register(CliffRacerEgg);
    GameRegistry.findRegistry(Item.class).register(TriffidEgg);
    GameRegistry.findRegistry(Item.class).register(PitchBlackEgg);
    GameRegistry.findRegistry(Item.class).register(LurkingTerrorEgg);
    GameRegistry.findRegistry(Item.class).register(GodzillaEgg);
    GameRegistry.findRegistry(Item.class).register(SmallWormEgg);
    GameRegistry.findRegistry(Item.class).register(MediumWormEgg);
    GameRegistry.findRegistry(Item.class).register(LargeWormEgg);
    GameRegistry.findRegistry(Item.class).register(CassowaryEgg);
    GameRegistry.findRegistry(Item.class).register(CloudSharkEgg);
    GameRegistry.findRegistry(Item.class).register(GoldFishEgg);
    GameRegistry.findRegistry(Item.class).register(LeafMonsterEgg);
    GameRegistry.findRegistry(Item.class).register(TshirtEgg);
    GameRegistry.findRegistry(Item.class).register(EnderKnightEgg);
    GameRegistry.findRegistry(Item.class).register(EnderReaperEgg);
    GameRegistry.findRegistry(Item.class).register(BeaverEgg);
    GameRegistry.findRegistry(Item.class).register(DungeonBeastEgg);
    GameRegistry.findRegistry(Item.class).register(RotatorEgg);
    GameRegistry.findRegistry(Item.class).register(VortexEgg);
    GameRegistry.findRegistry(Item.class).register(PeacockEgg);
    GameRegistry.findRegistry(Item.class).register(FairyEgg);
    GameRegistry.findRegistry(Item.class).register(RatEgg);
    GameRegistry.findRegistry(Item.class).register(FlounderEgg);
    GameRegistry.findRegistry(Item.class).register(WhaleEgg);
    GameRegistry.findRegistry(Item.class).register(IrukandjiEgg);
    GameRegistry.findRegistry(Item.class).register(SkateEgg);
    GameRegistry.findRegistry(Item.class).register(UrchinEgg);
    GameRegistry.findRegistry(Item.class).register(Robot1Egg);
    GameRegistry.findRegistry(Item.class).register(Robot2Egg);
    GameRegistry.findRegistry(Item.class).register(Robot3Egg);
    GameRegistry.findRegistry(Item.class).register(Robot4Egg);
    GameRegistry.findRegistry(Item.class).register(GhostEgg);
    GameRegistry.findRegistry(Item.class).register(GhostSkellyEgg);
    GameRegistry.findRegistry(Item.class).register(BrownAntEgg);
    GameRegistry.findRegistry(Item.class).register(RedAntEgg);
    GameRegistry.findRegistry(Item.class).register(RainbowAntEgg);
    GameRegistry.findRegistry(Item.class).register(UnstableAntEgg);
    GameRegistry.findRegistry(Item.class).register(TermiteEgg);
    GameRegistry.findRegistry(Item.class).register(ButterflyEgg);
    GameRegistry.findRegistry(Item.class).register(MothEgg);
    GameRegistry.findRegistry(Item.class).register(MosquitoEgg);
    GameRegistry.findRegistry(Item.class).register(FireflyEgg);
    GameRegistry.findRegistry(Item.class).register(TRexEgg);
    GameRegistry.findRegistry(Item.class).register(HerculesEgg);
    GameRegistry.findRegistry(Item.class).register(MantisEgg);
    GameRegistry.findRegistry(Item.class).register(StinkyEgg);
    GameRegistry.findRegistry(Item.class).register(Robot5Egg);
    GameRegistry.findRegistry(Item.class).register(CoinEgg);
    GameRegistry.findRegistry(Item.class).register(EasterBunnyEgg);
    GameRegistry.findRegistry(Item.class).register(MolenoidEgg);
    GameRegistry.findRegistry(Item.class).register(SeaMonsterEgg);
    GameRegistry.findRegistry(Item.class).register(SeaViperEgg);
    GameRegistry.findRegistry(Item.class).register(CaterKillerEgg);
    GameRegistry.findRegistry(Item.class).register(RubberDuckyEgg);
    GameRegistry.findRegistry(Item.class).register(HammerheadEgg);
    GameRegistry.findRegistry(Item.class).register(LeonEgg);
    GameRegistry.findRegistry(Item.class).register(CriminalEgg);
    GameRegistry.findRegistry(Item.class).register(BrutalflyEgg);
    GameRegistry.findRegistry(Item.class).register(NastysaurusEgg);
    GameRegistry.findRegistry(Item.class).register(PointysaurusEgg);
    GameRegistry.findRegistry(Item.class).register(CricketEgg);
    GameRegistry.findRegistry(Item.class).register(ThePrincessEgg);
    GameRegistry.findRegistry(Item.class).register(FrogEgg);
    GameRegistry.findRegistry(Item.class).register(JefferyEgg);
    GameRegistry.findRegistry(Item.class).register(AntRobotEgg);
    GameRegistry.findRegistry(Item.class).register(SpiderRobotEgg);
    GameRegistry.findRegistry(Item.class).register(SpiderDriverEgg);
    GameRegistry.findRegistry(Item.class).register(CrabEgg);

    GameRegistry.findRegistry(Item.class).register(CageEmpty);
    GameRegistry.findRegistry(Item.class).register(CagedSpider);
    GameRegistry.findRegistry(Item.class).register(CagedBat);
    GameRegistry.findRegistry(Item.class).register(CagedCow);
    GameRegistry.findRegistry(Item.class).register(CagedPig);
    GameRegistry.findRegistry(Item.class).register(CagedSquid);
    GameRegistry.findRegistry(Item.class).register(CagedChicken);
    GameRegistry.findRegistry(Item.class).register(CagedCreeper);
    GameRegistry.findRegistry(Item.class).register(CagedSkeleton);
    GameRegistry.findRegistry(Item.class).register(CagedZombie);
    GameRegistry.findRegistry(Item.class).register(CagedSlime);
    GameRegistry.findRegistry(Item.class).register(CagedGhast);
    GameRegistry.findRegistry(Item.class).register(CagedZombiePigman);
    GameRegistry.findRegistry(Item.class).register(CagedEnderman);
    GameRegistry.findRegistry(Item.class).register(CagedCaveSpider);
    GameRegistry.findRegistry(Item.class).register(CagedSilverfish);
    GameRegistry.findRegistry(Item.class).register(CagedMagmaCube);
    GameRegistry.findRegistry(Item.class).register(CagedWitch);
    GameRegistry.findRegistry(Item.class).register(CagedSheep);
    GameRegistry.findRegistry(Item.class).register(CagedWolf);
    GameRegistry.findRegistry(Item.class).register(CagedMooshroom);
    GameRegistry.findRegistry(Item.class).register(CagedOcelot);
    GameRegistry.findRegistry(Item.class).register(CagedBlaze);
    GameRegistry.findRegistry(Item.class).register(CagedGirlfriend);
    GameRegistry.findRegistry(Item.class).register(CagedBoyfriend);
    GameRegistry.findRegistry(Item.class).register(CagedWitherSkeleton);
    GameRegistry.findRegistry(Item.class).register(CagedEnderDragon);
    GameRegistry.findRegistry(Item.class).register(CagedSnowGolem);
    GameRegistry.findRegistry(Item.class).register(CagedIronGolem);
    GameRegistry.findRegistry(Item.class).register(CagedWitherBoss);
    GameRegistry.findRegistry(Item.class).register(CagedRedCow);
    GameRegistry.findRegistry(Item.class).register(CagedCrystalCow);
    GameRegistry.findRegistry(Item.class).register(CagedVillager);
    GameRegistry.findRegistry(Item.class).register(CagedGoldCow);
    GameRegistry.findRegistry(Item.class).register(CagedEnchantedCow);
    GameRegistry.findRegistry(Item.class).register(CagedMOTHRA);
    GameRegistry.findRegistry(Item.class).register(CagedAlo);
    GameRegistry.findRegistry(Item.class).register(CagedCryo);
    GameRegistry.findRegistry(Item.class).register(CagedCama);
    GameRegistry.findRegistry(Item.class).register(CagedVelo);
    GameRegistry.findRegistry(Item.class).register(CagedHydro);
    GameRegistry.findRegistry(Item.class).register(CagedBasil);
    GameRegistry.findRegistry(Item.class).register(CagedDragonfly);
    GameRegistry.findRegistry(Item.class).register(CagedEmperorScorpion);
    GameRegistry.findRegistry(Item.class).register(CagedScorpion);
    GameRegistry.findRegistry(Item.class).register(CagedCaveFisher);
    GameRegistry.findRegistry(Item.class).register(CagedSpyro);
    GameRegistry.findRegistry(Item.class).register(CagedBaryonyx);
    GameRegistry.findRegistry(Item.class).register(CagedGammaMetroid);
    GameRegistry.findRegistry(Item.class).register(CagedCockateil);
    GameRegistry.findRegistry(Item.class).register(CagedKyuubi);
    GameRegistry.findRegistry(Item.class).register(CagedAlien);
    GameRegistry.findRegistry(Item.class).register(MyElevator);
    GameRegistry.findRegistry(Item.class).register(CagedAttackSquid);
    GameRegistry.findRegistry(Item.class).register(CagedWaterDragon);
    GameRegistry.findRegistry(Item.class).register(CagedCephadrome);
    GameRegistry.findRegistry(Item.class).register(CagedKraken);
    GameRegistry.findRegistry(Item.class).register(CagedLizard);
    GameRegistry.findRegistry(Item.class).register(CagedDragon);
    GameRegistry.findRegistry(Item.class).register(CagedBee);
    GameRegistry.findRegistry(Item.class).register(CagedHorse);
    GameRegistry.findRegistry(Item.class).register(CagedFirefly);
    GameRegistry.findRegistry(Item.class).register(CagedChipmunk);
    GameRegistry.findRegistry(Item.class).register(CagedGazelle);
    GameRegistry.findRegistry(Item.class).register(CagedOstrich);
    GameRegistry.findRegistry(Item.class).register(CagedTrooper);
    GameRegistry.findRegistry(Item.class).register(CagedSpit);
    GameRegistry.findRegistry(Item.class).register(CagedStink);
    GameRegistry.findRegistry(Item.class).register(CagedCreepingHorror);
    GameRegistry.findRegistry(Item.class).register(CagedTerribleTerror);
    GameRegistry.findRegistry(Item.class).register(CagedCliffRacer);
    GameRegistry.findRegistry(Item.class).register(CagedTriffid);
    GameRegistry.findRegistry(Item.class).register(CagedPitchBlack);
    GameRegistry.findRegistry(Item.class).register(CagedLurkingTerror);
    GameRegistry.findRegistry(Item.class).register(CagedSmallWorm);
    GameRegistry.findRegistry(Item.class).register(CagedMediumWorm);
    GameRegistry.findRegistry(Item.class).register(CagedLargeWorm);
    GameRegistry.findRegistry(Item.class).register(CagedCassowary);
    GameRegistry.findRegistry(Item.class).register(CagedCloudShark);
    GameRegistry.findRegistry(Item.class).register(CagedGoldFish);
    GameRegistry.findRegistry(Item.class).register(CagedLeafMonster);
    GameRegistry.findRegistry(Item.class).register(CagedEnderKnight);
    GameRegistry.findRegistry(Item.class).register(CagedEnderReaper);
    GameRegistry.findRegistry(Item.class).register(CagedBeaver);
    GameRegistry.findRegistry(Item.class).register(CagedUrchin);
    GameRegistry.findRegistry(Item.class).register(CagedFlounder);
    GameRegistry.findRegistry(Item.class).register(CagedSkate);
    GameRegistry.findRegistry(Item.class).register(CagedRotator);
    GameRegistry.findRegistry(Item.class).register(CagedPeacock);
    GameRegistry.findRegistry(Item.class).register(CagedFairy);
    GameRegistry.findRegistry(Item.class).register(CagedDungeonBeast);
    GameRegistry.findRegistry(Item.class).register(CagedVortex);
    GameRegistry.findRegistry(Item.class).register(CagedRat);
    GameRegistry.findRegistry(Item.class).register(CagedWhale);
    GameRegistry.findRegistry(Item.class).register(CagedIrukandji);
    GameRegistry.findRegistry(Item.class).register(CagedTRex);
    GameRegistry.findRegistry(Item.class).register(CagedHercules);
    GameRegistry.findRegistry(Item.class).register(CagedMantis);
    GameRegistry.findRegistry(Item.class).register(CagedStinky);
    GameRegistry.findRegistry(Item.class).register(CagedEasterBunny);
    GameRegistry.findRegistry(Item.class).register(CagedCaterKiller);
    GameRegistry.findRegistry(Item.class).register(CagedMolenoid);
    GameRegistry.findRegistry(Item.class).register(CagedSeaMonster);
    GameRegistry.findRegistry(Item.class).register(CagedSeaViper);
    GameRegistry.findRegistry(Item.class).register(CagedLeon);
    GameRegistry.findRegistry(Item.class).register(CagedHammerhead);
    GameRegistry.findRegistry(Item.class).register(CagedRubberDucky);
    GameRegistry.findRegistry(Item.class).register(CagedCriminal);
    GameRegistry.findRegistry(Item.class).register(CagedBrutalfly);
    GameRegistry.findRegistry(Item.class).register(CagedNastysaurus);
    GameRegistry.findRegistry(Item.class).register(CagedPointysaurus);
    GameRegistry.findRegistry(Item.class).register(CagedCricket);
    GameRegistry.findRegistry(Item.class).register(CagedFrog);
    GameRegistry.findRegistry(Item.class).register(CagedSpiderDriver);
    GameRegistry.findRegistry(Item.class).register(CagedCrab);

    GameRegistry.findRegistry(Item.class).register(MyStrawberry);
    GameRegistry.findRegistry(Item.class).register(MyCrystalApple);
    GameRegistry.findRegistry(Item.class).register(MyLove);
    GameRegistry.findRegistry(Item.class).register(MyCherry);
    GameRegistry.findRegistry(Item.class).register(MyPeach);
    GameRegistry.findRegistry(Item.class).register(MyRadish);
    GameRegistry.findRegistry(Item.class).register(MyRice);
    GameRegistry.findRegistry(Item.class).register(MyCornCob);
    GameRegistry.findRegistry(Item.class).register(MyQuinoa);
    GameRegistry.findRegistry(Item.class).register(MyTomato);
    GameRegistry.findRegistry(Item.class).register(MyLettuce);
    GameRegistry.findRegistry(Item.class).register(MyStrawberrySeed);
    GameRegistry.findRegistry(Item.class).register(MyButterflySeed);
    GameRegistry.findRegistry(Item.class).register(MyMothSeed);
    GameRegistry.findRegistry(Item.class).register(MyMosquitoSeed);
    GameRegistry.findRegistry(Item.class).register(MyFireflySeed);
    GameRegistry.findRegistry(Item.class).register(MagicApple);
    GameRegistry.findRegistry(Item.class).register(RandomDungeon);
    GameRegistry.findRegistry(Item.class).register(MinersDream);
    GameRegistry.findRegistry(Item.class).register(UltimateHelmet);
    GameRegistry.findRegistry(Item.class).register(UltimateBody);
    GameRegistry.findRegistry(Item.class).register(UltimateLegs);
    GameRegistry.findRegistry(Item.class).register(UltimateBoots);
    GameRegistry.findRegistry(Item.class).register(LavaEelHelmet);
    GameRegistry.findRegistry(Item.class).register(LavaEelBody);
    GameRegistry.findRegistry(Item.class).register(LavaEelLegs);
    GameRegistry.findRegistry(Item.class).register(LavaEelBoots);
    GameRegistry.findRegistry(Item.class).register(MothScaleHelmet);
    GameRegistry.findRegistry(Item.class).register(MothScaleBody);
    GameRegistry.findRegistry(Item.class).register(MothScaleLegs);
    GameRegistry.findRegistry(Item.class).register(MothScaleBoots);
    GameRegistry.findRegistry(Item.class).register(MyAppleSeed);
    GameRegistry.findRegistry(Item.class).register(MyCherrySeed);
    GameRegistry.findRegistry(Item.class).register(MyPeachSeed);
    GameRegistry.findRegistry(Item.class).register(MyStepUp);
    GameRegistry.findRegistry(Item.class).register(MyStepDown);
    GameRegistry.findRegistry(Item.class).register(MyStepAccross);
    GameRegistry.findRegistry(Item.class).register(EmeraldHelmet);
    GameRegistry.findRegistry(Item.class).register(EmeraldBody);
    GameRegistry.findRegistry(Item.class).register(EmeraldLegs);
    GameRegistry.findRegistry(Item.class).register(EmeraldBoots);
    GameRegistry.findRegistry(Item.class).register(MyExperienceCatcher);
    GameRegistry.findRegistry(Item.class).register(MyDeadStinkBug);
    GameRegistry.findRegistry(Item.class).register(MyExperienceTreeSeed);
    GameRegistry.findRegistry(Item.class).register(ExperienceHelmet);
    GameRegistry.findRegistry(Item.class).register(ExperienceBody);
    GameRegistry.findRegistry(Item.class).register(ExperienceLegs);
    GameRegistry.findRegistry(Item.class).register(ExperienceBoots);
    GameRegistry.findRegistry(Item.class).register(RubyHelmet);
    GameRegistry.findRegistry(Item.class).register(RubyBody);
    GameRegistry.findRegistry(Item.class).register(RubyLegs);
    GameRegistry.findRegistry(Item.class).register(RubyBoots);
    GameRegistry.findRegistry(Item.class).register(AmethystHelmet);
    GameRegistry.findRegistry(Item.class).register(AmethystBody);
    GameRegistry.findRegistry(Item.class).register(AmethystLegs);
    GameRegistry.findRegistry(Item.class).register(AmethystBoots);
    GameRegistry.findRegistry(Item.class).register(ZooCage2);
    GameRegistry.findRegistry(Item.class).register(ZooCage4);
    GameRegistry.findRegistry(Item.class).register(ZooCage6);
    GameRegistry.findRegistry(Item.class).register(ZooCage8);
    GameRegistry.findRegistry(Item.class).register(ZooCage10);
    GameRegistry.findRegistry(Item.class).register(InstantShelter);
    GameRegistry.findRegistry(Item.class).register(InstantGarden);
    GameRegistry.findRegistry(Item.class).register(CrystalPinkHelmet);
    GameRegistry.findRegistry(Item.class).register(CrystalPinkBody);
    GameRegistry.findRegistry(Item.class).register(CrystalPinkLegs);
    GameRegistry.findRegistry(Item.class).register(CrystalPinkBoots);
    GameRegistry.findRegistry(Item.class).register(TigersEyeHelmet);
    GameRegistry.findRegistry(Item.class).register(TigersEyeBody);
    GameRegistry.findRegistry(Item.class).register(TigersEyeLegs);
    GameRegistry.findRegistry(Item.class).register(TigersEyeBoots);
    GameRegistry.findRegistry(Item.class).register(PeacockFeatherBoots);
    GameRegistry.findRegistry(Item.class).register(PeacockFeatherHelmet);
    GameRegistry.findRegistry(Item.class).register(PeacockFeatherBody);
    GameRegistry.findRegistry(Item.class).register(PeacockFeatherLegs);
    GameRegistry.findRegistry(Item.class).register(MobzillaHelmet);
    GameRegistry.findRegistry(Item.class).register(MobzillaBody);
    GameRegistry.findRegistry(Item.class).register(MobzillaLegs);
    GameRegistry.findRegistry(Item.class).register(MobzillaBoots);
    GameRegistry.findRegistry(Item.class).register(RoyalHelmet);
    GameRegistry.findRegistry(Item.class).register(RoyalBody);
    GameRegistry.findRegistry(Item.class).register(RoyalLegs);
    GameRegistry.findRegistry(Item.class).register(RoyalBoots);
    GameRegistry.findRegistry(Item.class).register(LapisHelmet);
    GameRegistry.findRegistry(Item.class).register(LapisBody);
    GameRegistry.findRegistry(Item.class).register(LapisLegs);
    GameRegistry.findRegistry(Item.class).register(LapisBoots);
    GameRegistry.findRegistry(Item.class).register(QueenHelmet);
    GameRegistry.findRegistry(Item.class).register(QueenBody);
    GameRegistry.findRegistry(Item.class).register(QueenLegs);
    GameRegistry.findRegistry(Item.class).register(QueenBoots);

    ItemStack OreSpiderEggStack = new ItemStack(MySpiderSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_spider"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("spider"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSpiderEggStack));

    ItemStack OreBatEggStack = new ItemStack(MyBatSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_bat"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("bat"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreBatEggStack));

    ItemStack OreCowEggStack = new ItemStack(MyCowSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_cow"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("cow"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCowEggStack));

    ItemStack OrePigEggStack = new ItemStack(MyPigSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_pig"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("pig"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OrePigEggStack));

    ItemStack OreSquidEggStack = new ItemStack(MySquidSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_squid"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("squid"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSquidEggStack));

    ItemStack OreChickenEggStack = new ItemStack(MyChickenSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_chicken"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("chicken"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreChickenEggStack));

    ItemStack OreCreeperEggStack = new ItemStack(MyCreeperSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_creeper"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("creeper"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCreeperEggStack));

    ItemStack OreSkeletonEggStack = new ItemStack(MySkeletonSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_skeleton"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("skeleton"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSkeletonEggStack));

    ItemStack OreZombieEggStack = new ItemStack(MyZombieSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_zombie"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("zombie"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreZombieEggStack));

    ItemStack OreSlimeEggStack = new ItemStack(MySlimeSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_slime"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("slime"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSlimeEggStack));

    ItemStack OreGhastEggStack = new ItemStack(MyGhastSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_ghast"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("ghast"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreGhastEggStack));

    ItemStack OreZombiePigmanEggStack = new ItemStack(MyZombiePigmanSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_zombie_pigman"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("zombie_pigman"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreZombiePigmanEggStack));

    ItemStack OreEndermanEggStack = new ItemStack(MyEndermanSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_enderman"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("enderman"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreEndermanEggStack));

    ItemStack OreCaveSpiderEggStack = new ItemStack(MyCaveSpiderSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_cave_spider"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("cave_spider"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCaveSpiderEggStack));

    ItemStack OreSilverfishEggStack = new ItemStack(MySilverfishSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_silverfish"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("silverfish"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSilverfishEggStack));

    ItemStack OreMagmaCubeEggStack = new ItemStack(MyMagmaCubeSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_magma_cube"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("magma_cube"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreMagmaCubeEggStack));

    ItemStack OreWitchEggStack = new ItemStack(MyWitchSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_witch"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("witch"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreWitchEggStack));

    ItemStack OreSheepEggStack = new ItemStack(MySheepSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_sheep"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("sheep"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSheepEggStack));

    ItemStack OreWolfEggStack = new ItemStack(MyWolfSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_wolf"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("wolf"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreWolfEggStack));

    ItemStack OreMooshroomEggStack = new ItemStack(MyMooshroomSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mooshroom"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("mooshroom"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreMooshroomEggStack));

    ItemStack OreOcelotEggStack = new ItemStack(MyOcelotSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_ocelot"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("ocelot"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreOcelotEggStack));

    ItemStack OreBlazeEggStack = new ItemStack(MyBlazeSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_blaze"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("blaze"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreBlazeEggStack));

    ItemStack OreWitherSkeletonEggStack = new ItemStack(MyWitherSkeletonSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_wither_skeleton"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(WitherSkeletonEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreWitherSkeletonEggStack));

    ItemStack OreEnderDragonEggStack = new ItemStack(MyEnderDragonSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_ender_dragon"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(EnderDragonEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreEnderDragonEggStack));

    ItemStack OreSnowGolemEggStack = new ItemStack(MySnowGolemSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_snow_golem"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(SnowGolemEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSnowGolemEggStack));

    ItemStack OreIronGolemEggStack = new ItemStack(MyIronGolemSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_iron_golem"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(IronGolemEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreIronGolemEggStack));

    ItemStack OreWitherBossEggStack = new ItemStack(MyWitherBossSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_wither_boss"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(WitherBossEgg, 1, 64), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreWitherBossEggStack));

    ItemStack OreGirlfriendEggStack = new ItemStack(MyGirlfriendSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_girlfriend"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(GirlfriendEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreGirlfriendEggStack));

    ItemStack OreBoyfriendEggStack = new ItemStack(MyBoyfriendSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_boyfriend"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BoyfriendEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreBoyfriendEggStack));

    ItemStack OreRedCowEggStack = new ItemStack(MyRedCowSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_red_cow"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(RedCowEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreRedCowEggStack));

    ItemStack OreCrystalCowEggStack = new ItemStack(MyCrystalCowSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_crystal_cow"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CrystalCowEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCrystalCowEggStack));

    ItemStack OreVillagerEggStack = new ItemStack(MyVillagerSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_villager"), new ResourceLocation("chaospersists", "eggs"), createVanillaSpawnEgg("villager"), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreVillagerEggStack));

    ItemStack OreGoldCowEggStack = new ItemStack(MyGoldCowSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_gold_cow"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(GoldCowEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreGoldCowEggStack));

    ItemStack OreEnchantedCowEggStack = new ItemStack(MyEnchantedCowSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_enchanted_cow"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(EnchantedCowEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreEnchantedCowEggStack));

    ItemStack OreMOTHRAEggStack = new ItemStack(MyMOTHRASpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mothra"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MOTHRAEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreMOTHRAEggStack));

    ItemStack OreAloEggStack = new ItemStack(MyAloSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_alo"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(AloEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreAloEggStack));

    ItemStack OreCryoEggStack = new ItemStack(MyCryoSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_cryo"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CryoEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCryoEggStack));

    ItemStack OreCamaEggStack = new ItemStack(MyCamaSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_cama"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CamaEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCamaEggStack));

    ItemStack OreVeloEggStack = new ItemStack(MyVeloSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_velo"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(VeloEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreVeloEggStack));

    ItemStack OreHydroEggStack = new ItemStack(MyHydroSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_hydro"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(HydroEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreHydroEggStack));

    ItemStack OreBasilEggStack = new ItemStack(MyBasilSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_basil"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BasilEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreBasilEggStack));

    ItemStack OreDragonflyEggStack = new ItemStack(MyDragonflySpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_dragonfly"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(DragonflyEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreDragonflyEggStack));

    ItemStack OreEmperorScorpionEggStack = new ItemStack(MyEmperorScorpionSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_emperor_scorpion"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(EmperorScorpionEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreEmperorScorpionEggStack));

    ItemStack OreScorpionEggStack = new ItemStack(MyScorpionSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_scorpion"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ScorpionEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreScorpionEggStack));

    ItemStack OreCaveFisherEggStack = new ItemStack(MyCaveFisherSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_cave_fisher"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CaveFisherEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCaveFisherEggStack));

    ItemStack OreSpyroEggStack = new ItemStack(MySpyroSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_spyro"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(SpyroEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSpyroEggStack));

    ItemStack OreBaryonyxEggStack = new ItemStack(MyBaryonyxSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_baryonyx"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BaryonyxEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreBaryonyxEggStack));

    ItemStack OreGammaMetroidEggStack = new ItemStack(MyGammaMetroidSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_gamma_metroid"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(GammaMetroidEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreGammaMetroidEggStack));

    ItemStack OreCockateilEggStack = new ItemStack(MyCockateilSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_cockateil"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CockateilEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCockateilEggStack));

    ItemStack OreKyuubiEggStack = new ItemStack(MyKyuubiSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_kyuubi"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(KyuubiEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreKyuubiEggStack));

    ItemStack OreAlienEggStack = new ItemStack(MyAlienSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_alien"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(AlienEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreAlienEggStack));

    ItemStack OreAttackSquidEggStack = new ItemStack(MyAttackSquidSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(AttackSquidEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreAttackSquidEggStack));

    ItemStack OreWaterDragonEggStack = new ItemStack(MyWaterDragonSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(WaterDragonEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreWaterDragonEggStack));

    ItemStack OreKrakenEggStack = new ItemStack(MyKrakenSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(KrakenEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreKrakenEggStack));

    ItemStack OreLizardEggStack = new ItemStack(MyLizardSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(LizardEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreLizardEggStack));

    ItemStack OreCephadromeEggStack = new ItemStack(MyCephadromeSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CephadromeEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCephadromeEggStack));

    ItemStack OreDragonEggStack = new ItemStack(MyDragonSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(DragonEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreDragonEggStack));

    ItemStack OreBeeEggStack = new ItemStack(MyBeeSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BeeEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreBeeEggStack));

    ItemStack OreHorseEggStack = new ItemStack(MyHorseSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_horse"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(Items.SPAWN_EGG, 1, 100), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreHorseEggStack));

    ItemStack OreTrooperBugEggStack = new ItemStack(MyTrooperBugSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(TrooperBugEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreTrooperBugEggStack));

    ItemStack OreSpitBugEggStack = new ItemStack(MySpitBugSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(SpitBugEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSpitBugEggStack));

    ItemStack OreStinkBugEggStack = new ItemStack(MyStinkBugSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(StinkBugEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreStinkBugEggStack));

    ItemStack OreOstrichEggStack = new ItemStack(MyOstrichSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(OstrichEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreOstrichEggStack));

    ItemStack OreGazelleEggStack = new ItemStack(MyGazelleSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(GazelleEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreGazelleEggStack));

    ItemStack OreChipmunkEggStack = new ItemStack(MyChipmunkSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ChipmunkEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreChipmunkEggStack));
    ItemStack OreCreepingHorrorEggStack = new ItemStack(MyCreepingHorrorSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CreepingHorrorEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCreepingHorrorEggStack));
    ItemStack OreTerribleTerrorEggStack = new ItemStack(MyTerribleTerrorSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(TerribleTerrorEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreTerribleTerrorEggStack));
    ItemStack OreCliffRacerEggStack = new ItemStack(MyCliffRacerSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CliffRacerEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCliffRacerEggStack));
    ItemStack OreTriffidEggStack = new ItemStack(MyTriffidSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(TriffidEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreTriffidEggStack));
    ItemStack OrePitchBlackEggStack = new ItemStack(MyPitchBlackSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(PitchBlackEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OrePitchBlackEggStack));
    ItemStack OreLurkingTerrorEggStack = new ItemStack(MyLurkingTerrorSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(LurkingTerrorEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreLurkingTerrorEggStack));
    ItemStack OreEnderKnightEggStack = new ItemStack(MyEnderKnightSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(EnderKnightEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreEnderKnightEggStack));
    ItemStack OreEnderReaperEggStack = new ItemStack(MyEnderReaperSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(EnderReaperEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreEnderReaperEggStack));
    ItemStack OreGodzillaPartEggStack = new ItemStack(MyGodzillaPartSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "godzilla_spawn"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyGodzillaSpawnBlock), Ingredient.fromStacks(OreGodzillaPartEggStack), Ingredient.fromStacks(OreGodzillaPartEggStack), Ingredient.fromStacks(OreGodzillaPartEggStack), Ingredient.fromStacks(OreGodzillaPartEggStack), Ingredient.fromStacks(OreGodzillaPartEggStack), Ingredient.fromStacks(OreGodzillaPartEggStack), Ingredient.fromStacks(OreGodzillaPartEggStack), Ingredient.fromStacks(OreGodzillaPartEggStack), Ingredient.fromStacks(OreGodzillaPartEggStack));
    ItemStack OreGodzillaEggStack = new ItemStack(MyGodzillaSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(GodzillaEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreGodzillaEggStack));
    ItemStack OreTheKingPartEggStack = new ItemStack(MyTheKingPartSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "the_king_spawn"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyTheKingSpawnBlock), Ingredient.fromStacks(OreTheKingPartEggStack), Ingredient.fromStacks(OreTheKingPartEggStack), Ingredient.fromStacks(OreTheKingPartEggStack), Ingredient.fromStacks(OreTheKingPartEggStack), Ingredient.fromStacks(OreTheKingPartEggStack), Ingredient.fromStacks(OreTheKingPartEggStack), Ingredient.fromStacks(OreTheKingPartEggStack), Ingredient.fromStacks(OreTheKingPartEggStack), Ingredient.fromStacks(OreTheKingPartEggStack));
    ItemStack OreTheKingEggStack = new ItemStack(MyTheKingSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(TheKingEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreTheKingEggStack));
    ItemStack OreTheQueenPartEggStack = new ItemStack(MyTheQueenPartSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "the_queen_spawn"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyTheQueenSpawnBlock), Ingredient.fromStacks(OreTheQueenPartEggStack), Ingredient.fromStacks(OreTheQueenPartEggStack), Ingredient.fromStacks(OreTheQueenPartEggStack), Ingredient.fromStacks(OreTheQueenPartEggStack), Ingredient.fromStacks(OreTheQueenPartEggStack), Ingredient.fromStacks(OreTheQueenPartEggStack), Ingredient.fromStacks(OreTheQueenPartEggStack), Ingredient.fromStacks(OreTheQueenPartEggStack), Ingredient.fromStacks(OreTheQueenPartEggStack));
    ItemStack OreTheQueenEggStack = new ItemStack(MyTheQueenSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(TheQueenEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreTheQueenEggStack));
    ItemStack OreSmallWormEggStack = new ItemStack(MySmallWormSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(SmallWormEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSmallWormEggStack));
    ItemStack OreMediumWormEggStack = new ItemStack(MyMediumWormSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MediumWormEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreMediumWormEggStack));
    ItemStack OreLargeWormEggStack = new ItemStack(MyLargeWormSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(LargeWormEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreLargeWormEggStack));
    ItemStack OreCassowaryEggStack = new ItemStack(MyCassowarySpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CassowaryEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCassowaryEggStack));
    ItemStack OreCloudSharkEggStack = new ItemStack(MyCloudSharkSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CloudSharkEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCloudSharkEggStack));
    ItemStack OreGoldFishEggStack = new ItemStack(MyGoldFishSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(GoldFishEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreGoldFishEggStack));
    ItemStack OreLeafMonsterEggStack = new ItemStack(MyLeafMonsterSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(LeafMonsterEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreLeafMonsterEggStack));
    ItemStack OreTshirtEggStack = new ItemStack(MyTshirtSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(TshirtEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreTshirtEggStack));
    ItemStack OreBeaverEggStack = new ItemStack(MyBeaverSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BeaverEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreBeaverEggStack));
    ItemStack OreUrchinEggStack = new ItemStack(MyUrchinSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(UrchinEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreUrchinEggStack));
    ItemStack OreFlounderEggStack = new ItemStack(MyFlounderSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(FlounderEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreFlounderEggStack));
    ItemStack OreSkateEggStack = new ItemStack(MySkateSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(SkateEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSkateEggStack));
    ItemStack OreRotatorEggStack = new ItemStack(MyRotatorSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(RotatorEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreRotatorEggStack));
    ItemStack OrePeacockEggStack = new ItemStack(MyPeacockSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(PeacockEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OrePeacockEggStack));
    ItemStack OreFairyEggStack = new ItemStack(MyFairySpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(FairyEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreFairyEggStack));
    ItemStack OreDungeonBeastEggStack = new ItemStack(MyDungeonBeastSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(DungeonBeastEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreDungeonBeastEggStack));
    ItemStack OreVortexEggStack = new ItemStack(MyVortexSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(VortexEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreVortexEggStack));
    ItemStack OreRatEggStack = new ItemStack(MyRatSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(RatEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreRatEggStack));
    ItemStack OreWhaleEggStack = new ItemStack(MyWhaleSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(WhaleEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreWhaleEggStack));
    ItemStack OreIrukandjiEggStack = new ItemStack(MyIrukandjiSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(IrukandjiEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreIrukandjiEggStack));
    ItemStack OreTRexEggStack = new ItemStack(MyTRexSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(TRexEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreTRexEggStack));
    ItemStack OreHerculesEggStack = new ItemStack(MyHerculesSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(HerculesEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreHerculesEggStack));
    ItemStack OreMantisEggStack = new ItemStack(MyMantisSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MantisEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreMantisEggStack));
    ItemStack OreStinkyEggStack = new ItemStack(MyStinkySpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(StinkyEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreStinkyEggStack));
    ItemStack OreEasterBunnyEggStack = new ItemStack(MyEasterBunnySpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(EasterBunnyEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreEasterBunnyEggStack));
    ItemStack OreCriminalEggStack = new ItemStack(MyCriminalSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CriminalEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCriminalEggStack));
    ItemStack OreBrutalflyEggStack = new ItemStack(MyBrutalflySpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BrutalflyEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreBrutalflyEggStack));
    ItemStack OreNastysaurusEggStack = new ItemStack(MyNastysaurusSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(NastysaurusEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreNastysaurusEggStack));
    ItemStack OrePointysaurusEggStack = new ItemStack(MyPointysaurusSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(PointysaurusEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OrePointysaurusEggStack));
    ItemStack OreCricketEggStack = new ItemStack(MyCricketSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CricketEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCricketEggStack));
    ItemStack OreFrogEggStack = new ItemStack(MyFrogSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(FrogEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreFrogEggStack));
    ItemStack OreSpiderDriverEggStack = new ItemStack(MySpiderDriverSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(SpiderDriverEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSpiderDriverEggStack));
    ItemStack OreCrabEggStack = new ItemStack(MyCrabSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CrabEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCrabEggStack));
    ItemStack OreCaterKillerEggStack = new ItemStack(MyCaterKillerSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CaterKillerEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreCaterKillerEggStack));
    ItemStack OreMolenoidEggStack = new ItemStack(MyMolenoidSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MolenoidEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreMolenoidEggStack));
    ItemStack OreSeaMonsterEggStack = new ItemStack(MySeaMonsterSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(SeaMonsterEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSeaMonsterEggStack));
    ItemStack OreSeaViperEggStack = new ItemStack(MySeaViperSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(SeaViperEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreSeaViperEggStack));
    ItemStack OreRubberDuckyEggStack = new ItemStack(MyRubberDuckySpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(RubberDuckyEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreRubberDuckyEggStack));
    ItemStack OreHammerheadEggStack = new ItemStack(MyHammerheadSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(HammerheadEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreHammerheadEggStack));
    ItemStack OreLeonEggStack = new ItemStack(MyLeonSpawnBlock);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(LeonEgg), Ingredient.fromStacks(new ItemStack(Items.WATER_BUCKET)), Ingredient.fromStacks(OreLeonEggStack));

    ItemStack OreUraniumStack = new ItemStack(MyOreUraniumBlock);

    ItemStack OreTitaniumStack = new ItemStack(MyOreTitaniumBlock);

    ItemStack OreSaltStack = new ItemStack(MyOreSaltBlock);

    ItemStack OreCrystalStoneStack = new ItemStack(CrystalStone);

    ItemStack OreCrystalRatStack = new ItemStack(CrystalRat);

    ItemStack OreRedAntTrollStack = new ItemStack(RedAntTroll);

    ItemStack OreTermiteTrollStack = new ItemStack(TermiteTroll);

    ItemStack OreCrystalFairyStack = new ItemStack(CrystalFairy);

    ItemStack OreCrystalCrystalStack = new ItemStack(CrystalCrystal);

    ItemStack OreTigersEyeStack = new ItemStack(TigersEye);

    ItemStack OreCrystalCoalStack = new ItemStack(CrystalCoal);

    ItemStack OreCrystalGrassStack = new ItemStack(CrystalGrass);

    ItemStack OreRubyStack = new ItemStack(MyOreRubyBlock);

    ItemStack OreAmethystStack = new ItemStack(MyOreAmethystBlock);

    ItemStack BlockUraniumStack = new ItemStack(MyBlockUraniumBlock);

    ItemStack LavafoamStack = new ItemStack(MyLavafoamBlock);

    ItemStack BlockTitaniumStack = new ItemStack(MyBlockTitaniumBlock);

    ItemStack BlockMobzillaScaleStack = new ItemStack(MyBlockMobzillaScaleBlock);

    ItemStack BlockRubyStack = new ItemStack(MyBlockRubyBlock);

    ItemStack BlockAmethystStack = new ItemStack(MyBlockAmethystBlock);

    ItemStack BlockCrystalPinkStack = new ItemStack(MyCrystalPinkBlock);

    ItemStack BlockTigersEyeStack = new ItemStack(MyTigersEyeBlock);

    ItemStack EnderPearlStack = new ItemStack(MyEnderPearlBlock);

    ItemStack EyeOfEnderStack = new ItemStack(MyEyeOfEnderBlock);

    ItemStack CrystalPlanksStack = new ItemStack(CrystalPlanksBlock);

    ItemStack CrystalWorkbenchStack = new ItemStack(CrystalWorkbenchBlock);

    ItemStack CrystalFurnaceStack = new ItemStack(CrystalFurnaceBlock);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CrystalPlanksBlock, 4), Ingredient.fromStacks(new ItemStack(MyCrystalTreeLog)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CrystalWorkbenchBlock), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)));

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_furnace"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CrystalFurnaceBlock), "FFF", "F F", "FFF", 'F', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_chest"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(Blocks.CHEST), "FFF", "F F", "FFF", 'F', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_door_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(Items.OAK_DOOR), "FF ", "FF ", "FF ", 'F', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_door_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(Items.OAK_DOOR), " FF", " FF", " FF", 'F', CrystalPlanksBlock);

    GameRegistry.addSmelting(MyOreUraniumBlock, new ItemStack(UraniumNugget), 0.3F);
    GameRegistry.addSmelting(MyOreTitaniumBlock, new ItemStack(TitaniumNugget), 0.3F);
    GameRegistry.addSmelting(MyOreRubyBlock, new ItemStack(MyRuby, 1), 1.0F);
    GameRegistry.addSmelting(MyOreAmethystBlock, new ItemStack(MyAmethyst, 1), 1.0F);
    GameRegistry.addSmelting(MyOreSaltBlock, new ItemStack(MySalt, 8), 0.1F);
    GameRegistry.addSmelting(MyCornCob, new ItemStack(MyPopcorn), 0.1F);
    GameRegistry.addSmelting(MyRawCornDog, new ItemStack(MyCornDog), 0.4F);
    GameRegistry.addSmelting(MyRawBacon, new ItemStack(MyBacon), 0.2F);
    GameRegistry.addSmelting(CrystalCrystal, new ItemStack(MyCrystalPinkIngot), 0.3F);
    GameRegistry.addSmelting(TigersEye, new ItemStack(MyTigersEyeIngot), 0.3F);
    GameRegistry.addSmelting(MyRawPeacock, new ItemStack(MyPeacock), 0.4F);
    GameRegistry.addSmelting(MyRawCrabMeat, new ItemStack(MyCrabMeat), 0.2F);
    // 1.7.10 behavior: CrystalCoal is furnace fuel (20000 burn time). Smelting recipe is not required.

    GameRegistry.addSmelting(MyGreenFish, new ItemStack(Items.COOKED_FISH), 0.2F);
    GameRegistry.addSmelting(MyBlueFish, new ItemStack(Items.COOKED_FISH), 0.2F);
    GameRegistry.addSmelting(MyPinkFish, new ItemStack(Items.COOKED_FISH), 0.2F);
    GameRegistry.addSmelting(MyRockFish, new ItemStack(Items.COOKED_FISH), 0.2F);
    GameRegistry.addSmelting(MyWoodFish, new ItemStack(Items.COOKED_FISH), 0.2F);
    GameRegistry.addSmelting(MyGreyFish, new ItemStack(Items.COOKED_FISH), 0.2F);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_sword_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateSword), " T ", " U ", " I ", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_sword_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateSword), "T  ", "U  ", "I  ", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_sword_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateSword), "  T", "  U", "  I", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_pickaxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimatePickaxe), "TUT", " U ", " I ", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_shovel_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateShovel), " U ", " T ", " I ", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_shovel_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateShovel), "U  ", "T  ", "I  ", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_shovel_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateShovel), "  U", "  T", "  I", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_hoe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateHoe), "TU ", " I ", " I ", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_axe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateAxe), "TU ", "TI ", " I ", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_bow"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateBow), " TS", "I S", " US", 'S', Items.STRING, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "skate_bow"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MySkateBow), " TS", "T S", " TS", 'S', Items.STRING, 'T', CrystalSticks);

    addShapedRecipe(new ResourceLocation("chaospersists", "ultimate_fishing_rod"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyUltimateFishingRod), "  T", " US", "I S", 'S', Items.STRING, 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "nightmare_sword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyNightmareSword), "ODO", "RTR", "OIO", 'I', Items.IRON_INGOT, 'O', MyNightmareScale, 'D', Items.DIAMOND, 'R', Items.REDSTONE, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_sword_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldSword), " E ", " E ", " I ", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_sword_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldSword), "E  ", "E  ", "I  ", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_sword_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldSword), "  E", "  E", "  I", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "rose_sword_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRoseSword), " E ", " E ", " I ", 'I', Items.STICK, 'E', Blocks.RED_FLOWER);

    addShapedRecipe(new ResourceLocation("chaospersists", "rose_sword_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRoseSword), "E  ", "E  ", "I  ", 'I', Items.STICK, 'E', Blocks.RED_FLOWER);

    addShapedRecipe(new ResourceLocation("chaospersists", "rose_sword_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRoseSword), "  E", "  E", "  I", 'I', Items.STICK, 'E', Blocks.RED_FLOWER);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_pickaxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldPickaxe), "EEE", " I ", " I ", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_shovel_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldShovel), " E ", " I ", " I ", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_shovel_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldShovel), "E  ", "I  ", "I  ", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_shovel_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldShovel), "  E", "  I", "  I", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_hoe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldHoe), "EE ", " I ", " I ", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "emerald_axe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEmeraldAxe), "EE ", "EI ", " I ", 'I', Items.STICK, 'E', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "experience_sword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyExperienceSword), "EEE", "EIE", "EEE", 'I', MyEmeraldSword, 'E', Items.EXPERIENCE_BOTTLE);

    addShapedRecipe(new ResourceLocation("chaospersists", "poison_sword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyPoisonSword), "EEE", "EIE", "EEE", 'I', MyEmeraldSword, 'E', MyDeadStinkBug);

    addShapedRecipe(new ResourceLocation("chaospersists", "rat_sword_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRatSword), " E ", " E ", " I ", 'I', CrystalSticks, 'E', CrystalRat);

    addShapedRecipe(new ResourceLocation("chaospersists", "rat_sword_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRatSword), "E  ", "E  ", "I  ", 'I', CrystalSticks, 'E', CrystalRat);

    addShapedRecipe(new ResourceLocation("chaospersists", "rat_sword_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRatSword), "  E", "  E", "  I", 'I', CrystalSticks, 'E', CrystalRat);

    addShapedRecipe(new ResourceLocation("chaospersists", "fairy_sword_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyFairySword), " E ", " E ", " I ", 'I', CrystalSticks, 'E', CrystalFairy);

    addShapedRecipe(new ResourceLocation("chaospersists", "fairy_sword_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyFairySword), "E  ", "E  ", "I  ", 'I', CrystalSticks, 'E', CrystalFairy);

    addShapedRecipe(new ResourceLocation("chaospersists", "fairy_sword_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyFairySword), "  E", "  E", "  I", 'I', CrystalSticks, 'E', CrystalFairy);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_sword_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodSword), " E ", " E ", " I ", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_sword_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodSword), "E  ", "E  ", "I  ", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_sword_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodSword), "  E", "  E", "  I", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_pickaxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodPickaxe), "EEE", " I ", " I ", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_shovel_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodShovel), " E ", " I ", " I ", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_shovel_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodShovel), "E  ", "I  ", "I  ", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_shovel_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodShovel), "  E", "  I", "  I", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_hoe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodHoe), "EE ", " I ", " I ", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_wood_axe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalWoodAxe), "EE ", "EI ", " I ", 'I', CrystalSticks, 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_chest_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(Blocks.CHEST), "EEE", "E E", "EEE", 'E', CrystalPlanksBlock);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_sword_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkSword), " E ", " E ", " I ", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_sword_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkSword), "E  ", "E  ", "I  ", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_sword_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkSword), "  E", "  E", "  I", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_pickaxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkPickaxe), "EEE", " I ", " I ", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_shovel_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkShovel), " E ", " I ", " I ", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_shovel_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkShovel), "E  ", "I  ", "I  ", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_shovel_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkShovel), "  E", "  I", "  I", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_hoe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkHoe), "EE ", " I ", " I ", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_pink_axe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkAxe), "EE ", "EI ", " I ", 'I', CrystalSticks, 'E', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "crystal_bucket"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(Items.BUCKET), "   ", "I I", " I ", 'I', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeSword_1"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeSword), " E ", " E ", " I ", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeSword_2"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeSword), "E  ", "E  ", "I  ", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeSword_3"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeSword), "  E", "  E", "  I", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyePickaxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyePickaxe), "EEE", " I ", " I ", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeShovel), " E ", " I ", " I ", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeShovel), "E  ", "I  ", "I  ", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeShovel), "  E", "  I", "  I", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeHoe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeHoe), "EE ", " I ", " I ", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeAxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeAxe), "EE ", "EI ", " I ", 'I', CrystalSticks, 'E', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStoneSword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStoneSword), " E ", " E ", " I ", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStoneSword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStoneSword), "E  ", "E  ", "I  ", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStoneSword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStoneSword), "  E", "  E", "  I", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStonePickaxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStonePickaxe), "EEE", " I ", " I ", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStoneShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStoneShovel), " E ", " I ", " I ", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStoneShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStoneShovel), "E  ", "I  ", "I  ", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStoneShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStoneShovel), "  E", "  I", "  I", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStoneHoe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStoneHoe), "EE ", " I ", " I ", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalStoneAxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalStoneAxe), "EE ", "EI ", " I ", 'I', CrystalSticks, 'E', CrystalStone);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubySword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubySword), " E ", " E ", " I ", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubySword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubySword), "E  ", "E  ", "I  ", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubySword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubySword), "  E", "  E", "  I", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubyPickaxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubyPickaxe), "EEE", " I ", " I ", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubyShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubyShovel), " E ", " I ", " I ", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubyShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubyShovel), "E  ", "I  ", "I  ", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubyShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubyShovel), "  E", "  I", "  I", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubyHoe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubyHoe), "EE ", " I ", " I ", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyRubyAxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyRubyAxe), "EE ", "EI ", " I ", 'I', Items.STICK, 'E', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystSword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystSword), " E ", " E ", " I ", 'I', Items.STICK, 'E', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystSword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystSword), "E  ", "E  ", "I  ", 'I', Items.STICK, 'E', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystSword"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystSword), "  E", "  E", "  I", 'I', Items.STICK, 'E', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystPickaxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystPickaxe), "EEE", " I ", " I ", 'I', Items.STICK, 'E', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystShovel), " E ", " I ", " I ", 'I', Items.STICK, 'E', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystShovel), "E  ", "I  ", "I  ", 'I', Items.STICK, 'E', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystShovel"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystShovel), "  E", "  I", "  I", 'I', Items.STICK, 'E', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystHoe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystHoe), "EE ", " I ", " I ", 'I', Items.STICK, 'E', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyAmethystAxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyAmethystAxe), "EE ", "EI ", " I ", 'I', Items.STICK, 'E', MyAmethyst);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyHammy), Ingredient.fromStacks(new ItemStack(MyUltimateSword)), Ingredient.fromStacks(new ItemStack(MyUltimateSword)), Ingredient.fromStacks(new ItemStack(MyBigHammer)), Ingredient.fromStacks(new ItemStack(GreenGoo)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyBattleAxe), Ingredient.fromStacks(new ItemStack(MyUltimateSword)), Ingredient.fromStacks(new ItemStack(MyUltimateAxe)), Ingredient.fromStacks(new ItemStack(GreenGoo)));
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyChainsaw"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyChainsaw), "EEE", "EIE", "EEE", 'I', MyUltimateAxe, 'E', Blocks.REDSTONE_BLOCK);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyQueenBattleAxe"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyQueenBattleAxe), "EIE", "EIE", " I ", 'I', Items.IRON_INGOT, 'E', MyQueenScale);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyBertha), Ingredient.fromStacks(new ItemStack(BerthaHandle)), Ingredient.fromStacks(new ItemStack(BerthaGuard)), Ingredient.fromStacks(new ItemStack(BerthaBlade)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BerthaHandle), Ingredient.fromStacks(new ItemStack(MyRayGun)), Ingredient.fromStacks(new ItemStack(MyBigHammer)), Ingredient.fromStacks(new ItemStack(MyMantisClaw)), Ingredient.fromStacks(new ItemStack(MyWaterDragonScale)), Ingredient.fromStacks(new ItemStack(GreenGoo)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BerthaGuard), Ingredient.fromStacks(new ItemStack(MolenoidNose)), Ingredient.fromStacks(new ItemStack(SeaMonsterScale)), Ingredient.fromStacks(new ItemStack(MyMothScale)), Ingredient.fromStacks(new ItemStack(MyBasiliskScale)), Ingredient.fromStacks(new ItemStack(MyNightmareScale)), Ingredient.fromStacks(new ItemStack(MyEmperorScorpionScale)), Ingredient.fromStacks(new ItemStack(MyJumpyBugScale)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(BerthaBlade), Ingredient.fromStacks(new ItemStack(MyKrakenTooth)), Ingredient.fromStacks(new ItemStack(WormTooth)), Ingredient.fromStacks(new ItemStack(TRexTooth)), Ingredient.fromStacks(new ItemStack(MyUltimateSword)), Ingredient.fromStacks(new ItemStack(CaterKillerJaw)), Ingredient.fromStacks(new ItemStack(SeaViperTongue)), Ingredient.fromStacks(new ItemStack(VortexEye)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MySlice), Ingredient.fromStacks(new ItemStack(MyBertha)), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)));

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyIrukandjiArrow), Ingredient.fromStacks(new ItemStack(MyPeacockFeather)), Ingredient.fromStacks(new ItemStack(MyIrukandji)), Ingredient.fromStacks(new ItemStack(CrystalSticks)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(Items.BED), Ingredient.fromStacks(new ItemStack(MyPeacockFeather)), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)), Ingredient.fromStacks(new ItemStack(MyPeacockFeather)), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)), Ingredient.fromStacks(new ItemStack(MyPeacockFeather)), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MySquidZooka), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)), Ingredient.fromStacks(new ItemStack(Items.DYE)), Ingredient.fromStacks(new ItemStack(Items.DYE)), Ingredient.fromStacks(new ItemStack(Items.DYE)), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)), Ingredient.fromStacks(new ItemStack(Items.IRON_INGOT)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyIngotUranium"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyIngotUranium), "UUU", "UUU", "UUU", 'U', UraniumNugget);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(UraniumNugget, 9), Ingredient.fromStacks(new ItemStack(MyIngotUranium)));
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyIngotTitanium"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyIngotTitanium), "UUU", "UUU", "UUU", 'U', TitaniumNugget);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(TitaniumNugget, 9), Ingredient.fromStacks(new ItemStack(MyIngotTitanium)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyBlockUraniumBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyBlockUraniumBlock), "UUU", "UUU", "UUU", 'U', MyIngotUranium);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyIngotUranium, 9), Ingredient.fromStacks(new ItemStack(MyBlockUraniumBlock)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyBlockTitaniumBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyBlockTitaniumBlock), "TTT", "TTT", "TTT", 'T', MyIngotTitanium);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyIngotTitanium, 9), Ingredient.fromStacks(new ItemStack(MyBlockTitaniumBlock)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyBlockMobzillaScaleBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyBlockMobzillaScaleBlock), "TTT", "TTT", "TTT", 'T', MyGodzillaScale);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyGodzillaScale, 9), Ingredient.fromStacks(new ItemStack(MyBlockMobzillaScaleBlock)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyBlockRubyBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyBlockRubyBlock), "TTT", "TTT", "TTT", 'T', MyRuby);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyRuby, 9), Ingredient.fromStacks(new ItemStack(MyBlockRubyBlock)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyBlockAmethystBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyBlockAmethystBlock), "TTT", "TTT", "TTT", 'T', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyCrystalPinkBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyCrystalPinkBlock), "TTT", "TTT", "TTT", 'T', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyTigersEyeBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyTigersEyeBlock), "TTT", "TTT", "TTT", 'T', MyTigersEyeIngot);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyAmethyst, 9), Ingredient.fromStacks(new ItemStack(MyBlockAmethystBlock)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyCrystalPinkIngot, 9), Ingredient.fromStacks(new ItemStack(MyCrystalPinkBlock)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyTigersEyeIngot, 9), Ingredient.fromStacks(new ItemStack(MyTigersEyeBlock)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyEnderPearlBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEnderPearlBlock), "TTT", "TTT", "TTT", 'T', Items.ENDER_PEARL);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(Items.ENDER_PEARL, 9), Ingredient.fromStacks(new ItemStack(MyEnderPearlBlock)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyEyeOfEnderBlock"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyEyeOfEnderBlock), "TTT", "TTT", "TTT", 'T', Items.ENDER_EYE);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(Items.ENDER_EYE, 9), Ingredient.fromStacks(new ItemStack(MyEyeOfEnderBlock)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyThunderStaff"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyThunderStaff), "DR ", "RR ", "  R", 'D', Items.DIAMOND, 'R', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyWrench"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyWrench), "D D", " D ", " D ", 'D', Items.IRON_INGOT);

    ItemStack MilkBucket = new ItemStack(Items.MILK_BUCKET);
    ItemStack SomePaper = new ItemStack(Items.PAPER);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyButter, 4), Ingredient.fromStacks(MilkBucket), Ingredient.fromStacks(MilkBucket));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyCheese, 2), Ingredient.fromStacks(MilkBucket), Ingredient.fromStacks(MilkBucket), Ingredient.fromStacks(MilkBucket), Ingredient.fromStacks(MilkBucket));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyButteredPopcorn), Ingredient.fromStacks(new ItemStack(MyPopcorn)), Ingredient.fromStacks(new ItemStack(MyButter)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyButteredSaltedPopcorn), Ingredient.fromStacks(new ItemStack(MyButteredPopcorn)), Ingredient.fromStacks(new ItemStack(MySalt)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyButteredSaltedPopcorn), Ingredient.fromStacks(new ItemStack(MyPopcorn)), Ingredient.fromStacks(new ItemStack(MySalt)), Ingredient.fromStacks(new ItemStack(MyButter)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyPopcornBag), Ingredient.fromStacks(new ItemStack(MyButteredSaltedPopcorn)), Ingredient.fromStacks(new ItemStack(MyButteredSaltedPopcorn)), Ingredient.fromStacks(new ItemStack(MyButteredSaltedPopcorn)), Ingredient.fromStacks(new ItemStack(MyButteredSaltedPopcorn)), Ingredient.fromStacks(new ItemStack(MyButteredSaltedPopcorn)), Ingredient.fromStacks(new ItemStack(MyButteredSaltedPopcorn)), Ingredient.fromStacks(SomePaper), Ingredient.fromStacks(SomePaper), Ingredient.fromStacks(SomePaper));

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyRawCornDog, 4), Ingredient.fromStacks(new ItemStack(MyCornCob)), Ingredient.fromStacks(new ItemStack(Items.CHICKEN)), Ingredient.fromStacks(new ItemStack(Items.PORKCHOP)), Ingredient.fromStacks(new ItemStack(Items.STICK)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyRawBacon, 2), Ingredient.fromStacks(new ItemStack(MySalt)), Ingredient.fromStacks(new ItemStack(Items.PORKCHOP)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyButterCandy, 4), Ingredient.fromStacks(new ItemStack(MyButter)), Ingredient.fromStacks(new ItemStack(Items.SUGAR)));

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MySalad, 1), Ingredient.fromStacks(new ItemStack(MyLettuce)), Ingredient.fromStacks(new ItemStack(MyTomato)), Ingredient.fromStacks(new ItemStack(MyRadish)), Ingredient.fromStacks(new ItemStack(Items.CARROT)), Ingredient.fromStacks(new ItemStack(Items.BOWL)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyBLT, 1), Ingredient.fromStacks(new ItemStack(MyBacon)), Ingredient.fromStacks(new ItemStack(MyLettuce)), Ingredient.fromStacks(new ItemStack(MyTomato)), Ingredient.fromStacks(new ItemStack(MyButter)), Ingredient.fromStacks(new ItemStack(Items.BREAD)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyPizzaItem, 1), Ingredient.fromStacks(new ItemStack(MyTomato)), Ingredient.fromStacks(new ItemStack(MyCheese)), Ingredient.fromStacks(new ItemStack(MyBacon)), Ingredient.fromStacks(new ItemStack(Items.BREAD)));
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyDuctTapeItem"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyDuctTapeItem), "   ", "AAA", "RRR", 'R', Items.STRING, 'A', Items.SLIME_BALL);

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyCrabbyPatty, 1), Ingredient.fromStacks(new ItemStack(MyCrabMeat)), Ingredient.fromStacks(new ItemStack(MyLettuce)), Ingredient.fromStacks(new ItemStack(MyTomato)), Ingredient.fromStacks(new ItemStack(Items.BREAD)));

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ZooCage2), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.GLASS)), Ingredient.fromStacks(new ItemStack(Blocks.QUARTZ_BLOCK)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ZooCage4), Ingredient.fromStacks(new ItemStack(ZooCage2)), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.GLASS)), Ingredient.fromStacks(new ItemStack(Blocks.QUARTZ_BLOCK)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ZooCage6), Ingredient.fromStacks(new ItemStack(ZooCage4)), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.GLASS)), Ingredient.fromStacks(new ItemStack(Blocks.QUARTZ_BLOCK)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ZooCage8), Ingredient.fromStacks(new ItemStack(ZooCage6)), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.GLASS)), Ingredient.fromStacks(new ItemStack(Blocks.QUARTZ_BLOCK)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ZooCage10), Ingredient.fromStacks(new ItemStack(ZooCage8)), Ingredient.fromStacks(new ItemStack(Blocks.IRON_BLOCK)), Ingredient.fromStacks(new ItemStack(Blocks.GLASS)), Ingredient.fromStacks(new ItemStack(Blocks.QUARTZ_BLOCK)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(InstantShelter), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK)), Ingredient.fromStacks(new ItemStack(Items.STICK)), Ingredient.fromStacks(new ItemStack(Blocks.COBBLESTONE)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(InstantGarden), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK)), Ingredient.fromStacks(new ItemStack(Items.WHEAT)), Ingredient.fromStacks(new ItemStack(Items.GUNPOWDER)));

    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CreeperLauncher, 4), Ingredient.fromStacks(new ItemStack(Items.PAPER)), Ingredient.fromStacks(new ItemStack(Items.REDSTONE)), Ingredient.fromStacks(new ItemStack(Items.STICK)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(NetherLost, 1), Ingredient.fromStacks(new ItemStack(Items.NETHER_STAR)), Ingredient.fromStacks(new ItemStack(Blocks.NETHERRACK)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_Sifter"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(Sifter), "RRR", "RAR", "RRR", 'R', Items.STICK, 'A', Items.STRING);
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MagicApple"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MagicApple), "RRR", "RAR", "RRR", 'R', Blocks.REDSTONE_BLOCK, 'A', Items.APPLE);
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_RandomDungeon"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(RandomDungeon), "RRR", "RAR", "RRR", 'R', Blocks.REDSTONE_BLOCK, 'A', Items.COAL);

    if (MinersDreamExpensive == 0)
    {
      addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MinersDream"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MinersDream), "CCC", "RRR", "GGG", 'R', Blocks.REDSTONE_BLOCK, 'C', Blocks.CACTUS, 'G', Items.GUNPOWDER);
    }
    else
    {
      addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MinersDream"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MinersDream), "CCC", "RRR", "GGG", 'R', Blocks.REDSTONE_BLOCK, 'C', Blocks.CACTUS, 'G', Blocks.TNT);
    }
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_stepup"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyStepUp, 8), "GC ", " C ", " C ", 'C', Blocks.COBBLESTONE, 'G', Items.GUNPOWDER);
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_stepdown"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyStepDown, 8), " C ", " C ", "GC ", 'C', Blocks.COBBLESTONE, 'G', Items.GUNPOWDER);
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_stepaccross"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyStepAccross, 8), " C ", "GC ", " C ", 'C', Blocks.COBBLESTONE, 'G', Items.GUNPOWDER);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ExtremeTorch, 4), Ingredient.fromStacks(new ItemStack(Items.REDSTONE)), Ingredient.fromStacks(new ItemStack(Items.STICK)), Ingredient.fromStacks(new ItemStack(Items.COAL)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(ExtremeTorch, 1), Ingredient.fromStacks(new ItemStack(Items.REDSTONE)), Ingredient.fromStacks(new ItemStack(Blocks.TORCH)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CrystalSticks, 6), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)), Ingredient.fromStacks(new ItemStack(CrystalPlanksBlock)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(CrystalTorch, 6), Ingredient.fromStacks(new ItemStack(CrystalCoal)), Ingredient.fromStacks(new ItemStack(CrystalSticks)));
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_krakenrepellent"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(KrakenRepellent, 1), "D D", "STS", "D D", 'D', MyDeadStinkBug, 'T', ExtremeTorch, 'S', Items.STRING);
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_creeperrepellent"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CreeperRepellent, 1), "D D", "STS", "D D", 'D', GreenGoo, 'T', ExtremeTorch, 'S', Items.STRING);
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyAppleSeed, 6), Ingredient.fromStacks(new ItemStack(Items.APPLE)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyCherrySeed, 1), Ingredient.fromStacks(new ItemStack(MyCherry)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyPeachSeed, 1), Ingredient.fromStacks(new ItemStack(MyPeach)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "egg_mob"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyExperienceCatcher, 1), Ingredient.fromStacks(new ItemStack(Items.GLASS_BOTTLE)), Ingredient.fromStacks(new ItemStack(Items.STICK)), Ingredient.fromStacks(new ItemStack(Items.STRING)));
    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_experiencetreeseed"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyExperienceTreeSeed, 1), "EEE", "EAE", "EEE", 'A', MyAppleSeed, 'E', Items.EXPERIENCE_BOTTLE);

    int nextEntityId = 0;
    int hookid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ultimate_fish_hook"), UltimateFishHook.class, "UltimateFishHook", hookid, this, 64, 1, true);

    int urchinid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "sunspot_urchin"), SunspotUrchin.class, "SunspotUrchin", urchinid, this, 64, 1, true);

    int waterballid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "water_ball"), WaterBall.class, "WaterBall", waterballid, this, 64, 1, true);

    int inksackid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ink_sack"), InkSack.class, "InkSack", inksackid, this, 64, 1, true);

    int laserballid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "laser_ball"), LaserBall.class, "LaserBall", laserballid, this, 64, 1, true);

    int iceballid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ice_ball"), IceBall.class, "IceBall", iceballid, this, 64, 1, true);

    int acidid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "acid"), Acid.class, "Acid", acidid, this, 64, 1, true);

    int Irukandjiid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "dead_irukandji"), DeadIrukandji.class, "DeadIrukandji", Irukandjiid, this, 64, 1, true);

    int berthahitid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "bertha_hit"), BerthaHit.class, "BerthaHit", berthahitid, this, 64, 1, true);

    int purplepowerid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "purple_power"), PurplePower.class, "PurplePower", purplepowerid, this, 64, 1, true);

    int rockid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "thrown_rock"), EntityThrownRock.class, "EntityThrownRock", rockid, this, 64, 1, true);

    int thunderboltid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "thunder_bolt"), com.astryxion.chaospersists.item.ThunderBolt.class, "ThunderBolt", thunderboltid, this, 64, 1, true);

    ItemStack RayStack = new ItemStack(MyRayGun);
    RayStack.setItemDamage(32767);
    addShapelessRecipe(new ResourceLocation("chaospersists", "repair_raygun"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MyRayGun), Ingredient.fromStacks(new ItemStack(Blocks.REDSTONE_BLOCK)), Ingredient.fromStacks(RayStack));

    ItemStack SquidStack = new ItemStack(MySquidZooka);
    SquidStack.setItemDamage(32767);
    addShapelessRecipe(new ResourceLocation("chaospersists", "repair_squidzooka"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(MySquidZooka), Ingredient.fromStacks(new ItemStack(Items.DYE)), Ingredient.fromStacks(SquidStack));

    GirlfriendID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "girlfriend"), Girlfriend.class, "Girlfriend", GirlfriendID, this, 64, 1, false);

    RedCowID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "apple_cow"), RedCow.class, "Apple Cow", RedCowID, this, 64, 1, false);

    GoldCowID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "golden_apple_cow"), GoldCow.class, "Golden Apple Cow", GoldCowID, this, 64, 1, false);

    EnchantedCowID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "enchanted_golden_apple_cow"), EnchantedCow.class, "Enchanted Golden Apple Cow", EnchantedCowID, this, 64, 1, false);

    ButterflyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "butterfly"), EntityButterfly.class, "Butterfly", ButterflyID, this, 32, 1, false);

    LunaMothID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "moth"), EntityLunaMoth.class, "Moth", LunaMothID, this, 32, 1, false);

    MosquitoID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "mosquito"), EntityMosquito.class, "Mosquito", MosquitoID, this, 16, 1, false);

    FireflyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "firefly"), Firefly.class, "Firefly", FireflyID, this, 64, 1, false);

    BeeID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "bee"), Bee.class, "Bee", BeeID, this, 64, 1, false);

    MothraID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "mothra"), Mothra.class, "Mothra", MothraID, this, 128, 1, false);

    AntID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ant"), EntityAnt.class, "Ant", AntID, this, 16, 1, false);
    RedAntID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "red_ant"), EntityRedAnt.class, "Red Ant", RedAntID, this, 16, 1, false);
    RainbowAntID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "rainbow_ant"), EntityRainbowAnt.class, "Rainbow Ant", RainbowAntID, this, 16, 1, false);
    UnstableAntID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "unstable_ant"), EntityUnstableAnt.class, "Unstable Ant", UnstableAntID, this, 16, 1, false);

    Robot1ID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "bomb_omb"), Robot1.class, "Bomb-Omb", Robot1ID, this, 32, 1, false);
    Robot2ID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "robo_pounder"), Robot2.class, "Robo-Pounder", Robot2ID, this, 64, 1, false);
    Robot3ID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "robo_gunner"), Robot3.class, "Robo-Gunner", Robot3ID, this, 64, 1, false);
    Robot4ID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "robo_warrior"), Robot4.class, "Robo-Warrior", Robot4ID, this, 64, 1, false);
    Robot5ID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "robo_sniper"), Robot5.class, "Robo-Sniper", Robot5ID, this, 64, 1, false);

    AlosaurusID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "alosaurus"), Alosaurus.class, "Alosaurus", AlosaurusID, this, 64, 1, false);
    CryolophosaurusID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "cryolophosaurus"), Cryolophosaurus.class, "Cryolophosaurus", CryolophosaurusID, this, 64, 1, false);
    BasiliskID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "basilisk"), Basilisk.class, "Basilisk", BasiliskID, this, 64, 1, false);
    CamarasaurusID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "camarasaurus"), Camarasaurus.class, "Camarasaurus", CamarasaurusID, this, 64, 1, false);
    HydroliscID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "hydrolisc"), Hydrolisc.class, "Hydrolisc", HydroliscID, this, 64, 1, false);
    VelocityRaptorID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "velocity_raptor"), VelocityRaptor.class, "Velocity Raptor", VelocityRaptorID, this, 64, 1, false);

    DragonflyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "dragonfly"), Dragonfly.class, "Dragonfly", DragonflyID, this, 64, 1, false);

    EmperorScorpionID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "emperor_scorpion"), EmperorScorpion.class, "Emperor Scorpion", EmperorScorpionID, this, 64, 1, false);

    ScorpionID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "scorpion"), Scorpion.class, "Scorpion", ScorpionID, this, 32, 1, false);

    CaveFisherID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "cave_fisher"), CaveFisher.class, "CaveFisher", CaveFisherID, this, 32, 1, false);

    SpyroID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "baby_dragon"), Spyro.class, "Baby Dragon", SpyroID, this, 64, 1, false);

    BaryonyxID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "baryonyx"), Baryonyx.class, "Baryonyx", BaryonyxID, this, 64, 1, false);

    GammaMetroidID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "gamma_metroid"), GammaMetroid.class, "WTF?", GammaMetroidID, this, 64, 1, false);
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "wtf"), GammaMetroid.class, "WTF? Legacy", nextEntityId++, this, 64, 1, false);

    CockateilID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "bird"), Cockateil.class, "Bird", CockateilID, this, 32, 1, false);

    RubyBirdID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ruby_bird"), RubyBird.class, "Ruby Bird", RubyBirdID, this, 32, 1, false);

    KyuubiID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "kyuubi"), Kyuubi.class, "Kyuubi", KyuubiID, this, 64, 1, false);

    WaterDragonID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "water_dragon"), WaterDragon.class, "Water Dragon", WaterDragonID, this, 64, 1, false);

    AttackSquidID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "attack_squid"), AttackSquid.class, "Attack Squid", AttackSquidID, this, 32, 1, false);

    AlienID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "alien"), Alien.class, "Alien", AlienID, this, 64, 1, false);

    ElevatorID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "hoverboard"), Elevator.class, "Hoverboard", ElevatorID, this, 128, 1, true);

    KrakenID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "the_kraken"), Kraken.class, "The Kraken", KrakenID, this, 128, 1, false);

    LizardID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "lizard"), Lizard.class, "Lizard", LizardID, this, 64, 1, false);

    CephadromeID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "cephadrome"), Cephadrome.class, "Cephadrome", CephadromeID, this, 128, 1, true);

    DragonID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "dragon"), Dragon.class, "Dragon", DragonID, this, 128, 1, true);

    ChipmunkID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "chipmunk"), Chipmunk.class, "Chipmunk", ChipmunkID, this, 32, 1, false);

    GazelleID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "gazelle"), Gazelle.class, "Gazelle", GazelleID, this, 64, 1, false);

    OstrichID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ostrich"), Ostrich.class, "Ostrich", OstrichID, this, 64, 1, true);

    TrooperBugID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "jumpy_bug"), TrooperBug.class, "Jumpy Bug", TrooperBugID, this, 64, 1, false);

    SpitBugID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "spit_bug"), SpitBug.class, "Spit Bug", SpitBugID, this, 64, 1, false);

    StinkBugID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "stink_bug"), StinkBug.class, "Stink Bug", StinkBugID, this, 32, 1, false);

    TshirtID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "tshirt"), Tshirt.class, "T-Shirt", TshirtID, this, 32, 1, false);

    IslandID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "island"), Island.class, "Island", IslandID, this, 64, 1, false);

    IslandTooID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "island_too"), IslandToo.class, "IslandToo", IslandTooID, this, 64, 1, false);

    CreepingHorrorID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "creeping_horror"), CreepingHorror.class, "Creeping Horror", CreepingHorrorID, this, 64, 1, false);

    TerribleTerrorID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "terrible_terror"), TerribleTerror.class, "Terrible Terror", TerribleTerrorID, this, 64, 1, false);

    CliffRacerID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "cliff_racer"), CliffRacer.class, "Cliff Racer", CliffRacerID, this, 32, 1, false);

    TriffidID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "triffid"), Triffid.class, "Triffid", TriffidID, this, 64, 1, false);

    PitchBlackID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "nightmare"), PitchBlack.class, "Nightmare", PitchBlackID, this, 64, 1, false);

    LurkingTerrorID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "lurking_terror"), LurkingTerror.class, "Lurking Terror", LurkingTerrorID, this, 64, 1, false);

    GodzillaID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "mobzilla"), Godzilla.class, "Mobzilla", GodzillaID, this, 128, 1, false);

    GhostID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ghost"), Ghost.class, "Ghost", GhostID, this, 32, 1, false);

    GhostSkellyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ghost_pumpkin_skelly"), GhostSkelly.class, "Ghost Pumpkin Skelly", GhostSkellyID, this, 64, 1, false);

    WormSmallID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "small_worm"), WormSmall.class, "Small Worm", WormSmallID, this, 32, 1, false);

    WormMediumID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "medium_worm"), WormMedium.class, "Medium Worm", WormMediumID, this, 64, 1, false);

    WormLargeID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "large_worm"), WormLarge.class, "Large Worm", WormLargeID, this, 64, 1, false);

    CassowaryID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "cassowary"), Cassowary.class, "Cassowary", CassowaryID, this, 64, 1, false);

    CloudSharkID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "cloud_shark"), CloudShark.class, "Cloud Shark", CloudSharkID, this, 64, 1, false);

    GoldFishID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "gold_fish"), GoldFish.class, "Gold Fish", GoldFishID, this, 32, 1, false);

    LeafMonsterID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "leaf_monster"), LeafMonster.class, "Leaf Monster", LeafMonsterID, this, 64, 1, false);

    GodzillaHeadID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "mobzilla_head"), GodzillaHead.class, "MobzillaHead", GodzillaHeadID, this, 128, 10, true);

    EnderKnightID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ender_knight"), EnderKnight.class, "Ender Knight", EnderKnightID, this, 64, 1, false);

    EnderReaperID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ender_reaper"), EnderReaper.class, "Ender Reaper", EnderReaperID, this, 64, 1, false);

    BeaverID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "beaver"), Beaver.class, "Beaver", BeaverID, this, 64, 1, false);

    TermiteID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "termite"), Termite.class, "Termite", TermiteID, this, 32, 1, false);

    FairyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "fairy"), Fairy.class, "Fairy", FairyID, this, 32, 1, false);

    PeacockID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "peacock"), Peacock.class, "Peacock", PeacockID, this, 64, 1, false);

    RotatorID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "rotator"), Rotator.class, "Rotator", RotatorID, this, 64, 1, false);

    VortexID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "vortex"), Vortex.class, "Vortex", VortexID, this, 64, 1, false);

    DungeonBeastID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "dungeon_beast"), DungeonBeast.class, "Dungeon Beast", DungeonBeastID, this, 64, 1, false);

    RatID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "rat"), Rat.class, "Rat", RatID, this, 32, 1, false);

    FlounderID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "flounder"), Flounder.class, "Flounder", FlounderID, this, 32, 1, false);

    WhaleID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "whale"), Whale.class, "Whale", WhaleID, this, 64, 1, false);

    IrukandjiID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "irukandji"), Irukandji.class, "Irukandji", IrukandjiID, this, 32, 1, false);

    SkateID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "skate"), Skate.class, "Skate", SkateID, this, 32, 1, false);

    UrchinID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "crystal_urchin"), Urchin.class, "Crystal Urchin", UrchinID, this, 64, 1, false);

    MantisID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "mantis"), Mantis.class, "Mantis", MantisID, this, 64, 1, false);

    HerculesBeetleID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "hercules_beetle"), HerculesBeetle.class, "Hercules Beetle", HerculesBeetleID, this, 64, 1, false);

    TRexID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "trex"), TRex.class, "T. Rex", TRexID, this, 64, 1, false);
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "t._rex"), TRex.class, "T. Rex Legacy", nextEntityId++, this, 64, 1, false);

    StinkyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "stinky"), Stinky.class, "Stinky", StinkyID, this, 64, 1, false);

    CoinID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "coin"), Coin.class, "Coin", CoinID, this, 64, 1, false);

    TheKingID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "the_king"), TheKing.class, "The King", TheKingID, this, 128, 1, false);

    KingHeadID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "king_head"), KingHead.class, "KingHead", KingHeadID, this, 128, 10, true);

    TheQueenID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "the_queen"), TheQueen.class, "The Queen", TheQueenID, this, 128, 1, false);

    QueenHeadID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "queen_head"), QueenHead.class, "QueenHead", QueenHeadID, this, 128, 10, true);

    BoyfriendID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "boyfriend"), Boyfriend.class, "Boyfriend", BoyfriendID, this, 64, 1, false);

    ThePrinceID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "the_prince"), ThePrince.class, "The Prince", ThePrinceID, this, 64, 1, false);

    MolenoidID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "molenoid"), Molenoid.class, "Molenoid", MolenoidID, this, 64, 1, false);

    SeaMonsterID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "sea_monster"), SeaMonster.class, "Sea Monster", SeaMonsterID, this, 64, 1, false);

    SeaViperID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "sea_viper"), SeaViper.class, "Sea Viper", SeaViperID, this, 64, 1, false);

    EasterBunnyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "easter_bunny"), EasterBunny.class, "Easter Bunny", EasterBunnyID, this, 64, 1, false);

    CaterKillerID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "caterkiller"), CaterKiller.class, "CaterKiller", CaterKillerID, this, 64, 1, false);

    CrystalCowID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "crystal_apple_cow"), CrystalCow.class, "Crystal Apple Cow", CrystalCowID, this, 64, 1, false);

    LeonID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "leonopteryx"), Leon.class, "Leonopteryx", LeonID, this, 64, 1, false);

    HammerheadID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "hammerhead"), Hammerhead.class, "Hammerhead", HammerheadID, this, 64, 1, false);

    RubberDuckyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "rubber_ducky"), RubberDucky.class, "Rubber Ducky", RubberDuckyID, this, 64, 1, false);

    ThePrinceTeenID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "the_young_prince"), ThePrinceTeen.class, "The Young Prince", ThePrinceTeenID, this, 64, 1, false);

    BandPID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "criminal"), BandP.class, "Criminal", BandPID, this, 64, 1, false);

    RockBaseID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "rock"), RockBase.class, "Rock", RockBaseID, this, 32, 1, false);

    BrutalflyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "brutalfly"), Brutalfly.class, "Brutalfly", BrutalflyID, this, 128, 1, false);

    NastysaurusID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "nastysaurus"), Nastysaurus.class, "Nastysaurus", NastysaurusID, this, 128, 1, false);

    PointysaurusID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "pointysaurus"), Pointysaurus.class, "Pointysaurus", PointysaurusID, this, 64, 1, false);

    CricketID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "cricket"), Cricket.class, "Cricket", CricketID, this, 32, 1, false);

    ThePrincessID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "the_princess"), ThePrincess.class, "The Princess", ThePrincessID, this, 64, 1, false);

    FrogID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "frog"), Frog.class, "Frog", FrogID, this, 32, 1, false);

    ThePrinceAdultID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "the_young_adult_prince"), ThePrinceAdult.class, "The Young Adult Prince", ThePrinceAdultID, this, 128, 1, false);

    SpiderRobotID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "robot_spider"), SpiderRobot.class, "Robot Spider", SpiderRobotID, this, 128, 1, false);

    SpiderDriverID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "spider_driver"), SpiderDriver.class, "Spider Driver", SpiderDriverID, this, 64, 1, false);

    JefferyID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "jeffery"), GiantRobot.class, "Jeffery", JefferyID, this, 128, 1, false);

    AntRobotID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "robot_red_ant"), AntRobot.class, "Robot Red Ant", AntRobotID, this, 128, 1, false);

    CrabID = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "crab"), Crab.class, "Crab", CrabID, this, 64, 1, false);

    GregorianCalendar gcalendar = new GregorianCalendar();

    int nowmonth = gcalendar.get(2);
    int nowday = gcalendar.get(5);

    if ((nowmonth == 9) && (nowday == 31)) {
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BEACH });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.DESERT });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_ROCK });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_CLEAR_ROCK });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA_HILLS });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });

      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BEACH });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.DESERT });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_ROCK });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_CLEAR_ROCK });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA_HILLS });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA });
      EntityRegistry.addSpawn(Ghost.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }

    if ((nowmonth == 1) && (nowday == 14)) {
      valentines_day = 1;
    }

    if ((nowmonth == 3) && (nowday == 20)) {
      easter_day = 1;
    }

    if (GirlfriendEnable != 0) {
      EntityRegistry.addSpawn(Girlfriend.class, 30, 8, 15, EnumCreatureType.CREATURE, new Biome[] { Biomes.BEACH });
      EntityRegistry.addSpawn(Girlfriend.class, 10, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Girlfriend.class, 8, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Girlfriend.class, 5, 2, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(Girlfriend.class, 10, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(Girlfriend.class, 10, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.STONE_BEACH });
      EntityRegistry.addSpawn(Girlfriend.class, 5, 2, 4, EnumCreatureType.CREATURE, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Girlfriend.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Girlfriend.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Girlfriend.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(Girlfriend.class, 2, 1, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Girlfriend.class, 2, 1, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (BoyfriendEnable != 0) {
      EntityRegistry.addSpawn(Boyfriend.class, 30, 8, 15, EnumCreatureType.CREATURE, new Biome[] { Biomes.BEACH });
      EntityRegistry.addSpawn(Boyfriend.class, 10, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Boyfriend.class, 8, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Boyfriend.class, 5, 2, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(Boyfriend.class, 10, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(Boyfriend.class, 10, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.STONE_BEACH });
      EntityRegistry.addSpawn(Boyfriend.class, 5, 2, 4, EnumCreatureType.CREATURE, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Boyfriend.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Boyfriend.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Boyfriend.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(Boyfriend.class, 2, 1, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Boyfriend.class, 2, 1, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (BeaverEnable != 0) {
      EntityRegistry.addSpawn(Beaver.class, 10, 2, 4, EnumCreatureType.CREATURE, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(Beaver.class, 3, 2, 4, EnumCreatureType.CREATURE, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Beaver.class, 2, 2, 4, EnumCreatureType.CREATURE, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Beaver.class, 2, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Beaver.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Beaver.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.TAIGA });
    }

    if (CowEnable != 0)
    {
      EntityRegistry.addSpawn(RedCow.class, 8, 4, 8, EnumCreatureType.CREATURE, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(RedCow.class, 8, 4, 8, EnumCreatureType.CREATURE, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(RedCow.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(RedCow.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(RedCow.class, 8, 1, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(RedCow.class, 2, 1, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.SAVANNA_PLATEAU });

      EntityRegistry.addSpawn(GoldCow.class, 5, 2, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(GoldCow.class, 5, 2, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(GoldCow.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(GoldCow.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.TAIGA });

      EntityRegistry.addSpawn(EnchantedCow.class, 3, 2, 4, EnumCreatureType.CREATURE, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(EnchantedCow.class, 3, 2, 4, EnumCreatureType.CREATURE, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(EnchantedCow.class, 5, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(EnchantedCow.class, 15, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.MUSHROOM_ISLAND });
    }

    if (CriminalEnable != 0) {
      EntityRegistry.addSpawn(BandP.class, 20, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(BandP.class, 20, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.DESERT });
      EntityRegistry.addSpawn(BandP.class, 20, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
    }

    if (WormEnable != 0) {
      EntityRegistry.addSpawn(WormLarge.class, 25, 1, 1, EnumCreatureType.CREATURE, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(WormLarge.class, 15, 1, 1, EnumCreatureType.CREATURE, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(WormLarge.class, 10, 1, 1, EnumCreatureType.CREATURE, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (ButterflyEnable != 0) {
      EntityRegistry.addSpawn(EntityButterfly.class, 8, 5, 15, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BEACH });
      EntityRegistry.addSpawn(EntityButterfly.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(EntityButterfly.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(EntityButterfly.class, 30, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(EntityButterfly.class, 20, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(EntityButterfly.class, 20, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(EntityButterfly.class, 10, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(EntityButterfly.class, 20, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(EntityButterfly.class, 20, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(EntityButterfly.class, 20, 4, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(EntityButterfly.class, 15, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(EntityButterfly.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(EntityButterfly.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(EntityButterfly.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(EntityButterfly.class, 10, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(EntityButterfly.class, 10, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (MothEnable != 0) {
      EntityRegistry.addSpawn(EntityLunaMoth.class, 8, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 8, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 10, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 20, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 20, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 10, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 20, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 10, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 15, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 10, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(EntityLunaMoth.class, 10, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (CassowaryEnable != 0) {
      EntityRegistry.addSpawn(Cassowary.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(Cassowary.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(Cassowary.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_WITH_TREES });
      EntityRegistry.addSpawn(Cassowary.class, 5, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Cassowary.class, 5, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Cassowary.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Cassowary.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA_HILLS });
      EntityRegistry.addSpawn(Cassowary.class, 3, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Cassowary.class, 10, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if ((EasterBunnyEnable != 0) && (easter_day != 0)) {
      EntityRegistry.addSpawn(EasterBunny.class, 10, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(EasterBunny.class, 10, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(EasterBunny.class, 10, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(EasterBunny.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(EasterBunny.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(EasterBunny.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(EasterBunny.class, 8, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
    }

    if (FireflyEnable != 0) {
      EntityRegistry.addSpawn(Firefly.class, 15, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Firefly.class, 15, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Firefly.class, 10, 4, 8, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(Firefly.class, 15, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Firefly.class, 15, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(Firefly.class, 10, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.STONE_BEACH });
      EntityRegistry.addSpawn(Firefly.class, 15, 3, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Firefly.class, 15, 3, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Firefly.class, 15, 2, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Firefly.class, 15, 2, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(Firefly.class, 15, 2, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA_HILLS });
      EntityRegistry.addSpawn(Firefly.class, 10, 2, 8, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Firefly.class, 10, 2, 8, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (WhaleEnable != 0) {
      EntityRegistry.addSpawn(Whale.class, 1, 1, 2, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.DEEP_OCEAN });
    }

    if (BeeEnable != 0) {
      EntityRegistry.addSpawn(Bee.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Bee.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Bee.class, 5, 3, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Bee.class, 5, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(Bee.class, 3, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Bee.class, 3, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Bee.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Bee.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(Bee.class, 3, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Bee.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (MantisEnable != 0) {
      EntityRegistry.addSpawn(Mantis.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Mantis.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Mantis.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Mantis.class, 1, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(Mantis.class, 1, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(Mantis.class, 1, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Mantis.class, 1, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Mantis.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Mantis.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (HerculesBeetleEnable != 0) {
      EntityRegistry.addSpawn(HerculesBeetle.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(HerculesBeetle.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(HerculesBeetle.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(HerculesBeetle.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA_HILLS });
      EntityRegistry.addSpawn(HerculesBeetle.class, 5, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(HerculesBeetle.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA_HILLS });
      EntityRegistry.addSpawn(HerculesBeetle.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA_HILLS });
    }

    if (MolenoidEnable != 0) {
      EntityRegistry.addSpawn(Molenoid.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(Molenoid.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Molenoid.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (CaterKillerEnable != 0) {
      EntityRegistry.addSpawn(CaterKiller.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(CaterKiller.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(CaterKiller.class, 4, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(CaterKiller.class, 4, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(CaterKiller.class, 6, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(CaterKiller.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(CaterKiller.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(CaterKiller.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(CaterKiller.class, 10, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }

    if (ChipmunkEnable != 0) {
      EntityRegistry.addSpawn(Chipmunk.class, 8, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Chipmunk.class, 5, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Chipmunk.class, 4, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Chipmunk.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(Chipmunk.class, 5, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Chipmunk.class, 4, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Chipmunk.class, 10, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
      EntityRegistry.addSpawn(Chipmunk.class, 2, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Chipmunk.class, 6, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
    }

    if (OstrichEnable != 0) {
      EntityRegistry.addSpawn(Ostrich.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.DESERT });
      EntityRegistry.addSpawn(Ostrich.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.STONE_BEACH });
      EntityRegistry.addSpawn(Ostrich.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Ostrich.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (CephadromeEnable != 0) {
      EntityRegistry.addSpawn(Cephadrome.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ICE_PLAINS });
      EntityRegistry.addSpawn(Cephadrome.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA });
    }

    if (MosquitoEnable != 0) {
      EntityRegistry.addSpawn(EntityMosquito.class, 30, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(EntityMosquito.class, 20, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(EntityMosquito.class, 20, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(EntityMosquito.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }

    if (GhostEnable != 0) {
      EntityRegistry.addSpawn(Ghost.class, 15, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA });
      EntityRegistry.addSpawn(Ghost.class, 10, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA_HILLS });
      EntityRegistry.addSpawn(Ghost.class, 6, 4, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FROZEN_RIVER });
      EntityRegistry.addSpawn(Ghost.class, 2, 1, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Ghost.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }

    if (GhostSkellyEnable != 0) {
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA });
      EntityRegistry.addSpawn(GhostSkelly.class, 10, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA_HILLS });
      EntityRegistry.addSpawn(GhostSkelly.class, 6, 4, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FROZEN_RIVER });
      EntityRegistry.addSpawn(GhostSkelly.class, 2, 1, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(GhostSkelly.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }

    if (DragonflyEnable != 0) {
      EntityRegistry.addSpawn(Dragonfly.class, 5, 3, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(Dragonfly.class, 4, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.RIVER });
    }

    if (KyuubiEnable != 0) {
      EntityRegistry.addSpawn(Kyuubi.class, 10, 1, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
    }

    if (StinkyEnable != 0) {
      EntityRegistry.addSpawn(Stinky.class, 2, 1, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
      EntityRegistry.addSpawn(Stinky.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA });
      EntityRegistry.addSpawn(Stinky.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_CLEAR_ROCK });
      EntityRegistry.addSpawn(Stinky.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_ROCK });
    }

    if (CockateilEnable != 0) {
      EntityRegistry.addSpawn(Cockateil.class, 10, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BEACH });
      EntityRegistry.addSpawn(Cockateil.class, 10, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(Cockateil.class, 10, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(Cockateil.class, 25, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Cockateil.class, 20, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Cockateil.class, 35, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Cockateil.class, 25, 5, 10, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(Cockateil.class, 10, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(Cockateil.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(Cockateil.class, 5, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.STONE_BEACH });
      EntityRegistry.addSpawn(Cockateil.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Cockateil.class, 5, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Cockateil.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Cockateil.class, 15, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(Cockateil.class, 11, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Cockateil.class, 11, 1, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }

    if (HydroliscEnable != 0) {
      EntityRegistry.addSpawn(Hydrolisc.class, 25, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(Hydrolisc.class, 15, 2, 5, EnumCreatureType.CREATURE, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Hydrolisc.class, 10, 1, 3, EnumCreatureType.CREATURE, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(Hydrolisc.class, 5, 3, 6, EnumCreatureType.CREATURE, new Biome[] { Biomes.STONE_BEACH });
    }

    if (MothraEnable != 0) {
      EntityRegistry.addSpawn(Mothra.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(Mothra.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_WITH_TREES });
    }
    if (BrutalflyEnable != 0) {
      EntityRegistry.addSpawn(Brutalfly.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA_HILLS });
      EntityRegistry.addSpawn(Brutalfly.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_WITH_TREES });
      EntityRegistry.addSpawn(Brutalfly.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_CLEAR_ROCK });
    }
    if (WaterDragonEnable != 0) {
      EntityRegistry.addSpawn(WaterDragon.class, 5, 1, 1, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(WaterDragon.class, 3, 1, 1, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(WaterDragon.class, 2, 1, 1, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.OCEAN });
      EntityRegistry.addSpawn(WaterDragon.class, 2, 1, 1, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.STONE_BEACH });
    }
    if (SeaMonsterEnable != 0) {
      EntityRegistry.addSpawn(SeaMonster.class, 4, 1, 1, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.OCEAN });
      EntityRegistry.addSpawn(SeaMonster.class, 2, 1, 1, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.SWAMPLAND });
    }
    if (SeaViperEnable != 0) {
      EntityRegistry.addSpawn(SeaViper.class, 3, 1, 1, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.OCEAN });
      EntityRegistry.addSpawn(SeaViper.class, 2, 1, 1, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.STONE_BEACH });
    }
    if (CrabEnable != 0) {
      EntityRegistry.addSpawn(Crab.class, 2, 3, 6, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.OCEAN });
      EntityRegistry.addSpawn(Crab.class, 1, 3, 6, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(Crab.class, 1, 2, 4, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.STONE_BEACH });
    }
    if (AttackSquidEnable != 0) {
      EntityRegistry.addSpawn(AttackSquid.class, 12, 6, 10, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(AttackSquid.class, 10, 5, 9, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(AttackSquid.class, 7, 4, 8, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.OCEAN });
    }
    if (LizardEnable != 0) {
      EntityRegistry.addSpawn(Lizard.class, 5, 2, 4, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(Lizard.class, 4, 2, 4, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(Lizard.class, 2, 2, 4, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.OCEAN });
    }
    if (RubberDuckyEnable != 0) {
      EntityRegistry.addSpawn(RubberDucky.class, 10, 10, 20, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(RubberDucky.class, 4, 4, 6, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.STONE_BEACH });
    }
    if (BasiliskEnable != 0) {
      EntityRegistry.addSpawn(Basilisk.class, 3, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Basilisk.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(Basilisk.class, 4, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Basilisk.class, 15, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }
    if (EmperorScorpionEnable != 0) {
      EntityRegistry.addSpawn(EmperorScorpion.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.DESERT });
      EntityRegistry.addSpawn(EmperorScorpion.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
    }
    if (TrooperBugEnable != 0) {
      EntityRegistry.addSpawn(TrooperBug.class, 3, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(TrooperBug.class, 1, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA });
    }
    if (SpitBugEnable != 0) {
      EntityRegistry.addSpawn(SpitBug.class, 6, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
    }
    if (StinkBugEnable != 0) {
      EntityRegistry.addSpawn(StinkBug.class, 10, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(StinkBug.class, 8, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(StinkBug.class, 6, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(StinkBug.class, 4, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(StinkBug.class, 8, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
    }
    if (ScorpionEnable != 0) {
      EntityRegistry.addSpawn(Scorpion.class, 15, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.DESERT });
      EntityRegistry.addSpawn(Scorpion.class, 28, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
      EntityRegistry.addSpawn(Scorpion.class, 15, 3, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA });
      EntityRegistry.addSpawn(Scorpion.class, 15, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
      EntityRegistry.addSpawn(Scorpion.class, 6, 1, 3, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA });
      EntityRegistry.addSpawn(Scorpion.class, 4, 1, 3, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_CLEAR_ROCK });
      EntityRegistry.addSpawn(Scorpion.class, 5, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_ROCK });
    }

    if (LeafMonsterEnable != 0) {
      EntityRegistry.addSpawn(LeafMonster.class, 5, 2, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(LeafMonster.class, 5, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(LeafMonster.class, 3, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(LeafMonster.class, 3, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(LeafMonster.class, 3, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(LeafMonster.class, 2, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(LeafMonster.class, 2, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(LeafMonster.class, 2, 2, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
    }

    if (EnderKnightEnable != 0) {
      EntityRegistry.addSpawn(EnderKnight.class, 4, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(EnderKnight.class, 4, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(EnderKnight.class, 4, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(EnderKnight.class, 4, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(EnderKnight.class, 4, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(EnderKnight.class, 2, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(EnderKnight.class, 2, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(EnderKnight.class, 2, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.DESERT });
      EntityRegistry.addSpawn(EnderKnight.class, 20, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }
    if (EnderReaperEnable != 0) {
      EntityRegistry.addSpawn(EnderReaper.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS });
      EntityRegistry.addSpawn(EnderReaper.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.EXTREME_HILLS_EDGE });
      EntityRegistry.addSpawn(EnderReaper.class, 1, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(EnderReaper.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(EnderReaper.class, 2, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(EnderReaper.class, 1, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(EnderReaper.class, 1, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(EnderReaper.class, 1, 1, 2, EnumCreatureType.AMBIENT, new Biome[] { Biomes.DESERT });
      EntityRegistry.addSpawn(EnderReaper.class, 38, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }

    if (CoinEnable != 0) {
      EntityRegistry.addSpawn(Coin.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(Coin.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Coin.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Coin.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Coin.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.COLD_TAIGA });
      EntityRegistry.addSpawn(Coin.class, 2, 1, 1, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
    }

    if (CricketEnable != 0) {
      EntityRegistry.addSpawn(Cricket.class, 3, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST });
      EntityRegistry.addSpawn(Cricket.class, 2, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.FOREST_HILLS });
      EntityRegistry.addSpawn(Cricket.class, 3, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Cricket.class, 2, 3, 5, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE_HILLS });
      EntityRegistry.addSpawn(Cricket.class, 3, 4, 8, EnumCreatureType.AMBIENT, new Biome[] { Biomes.PLAINS });
      EntityRegistry.addSpawn(Cricket.class, 2, 2, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST });
      EntityRegistry.addSpawn(Cricket.class, 2, 2, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.BIRCH_FOREST_HILLS });
      EntityRegistry.addSpawn(Cricket.class, 3, 1, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
      EntityRegistry.addSpawn(Cricket.class, 2, 1, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.REDWOOD_TAIGA });
      EntityRegistry.addSpawn(Cricket.class, 2, 1, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
      EntityRegistry.addSpawn(Cricket.class, 1, 1, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SAVANNA_PLATEAU });
    }
    if (FrogEnable != 0) {
      EntityRegistry.addSpawn(Frog.class, 20, 3, 6, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(Frog.class, 3, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.RIVER });
      EntityRegistry.addSpawn(Frog.class, 3, 3, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.JUNGLE });
      EntityRegistry.addSpawn(Frog.class, 20, 2, 6, EnumCreatureType.WATER_CREATURE, new Biome[] { Biomes.SWAMPLAND });
      EntityRegistry.addSpawn(Frog.class, 2, 2, 6, EnumCreatureType.AMBIENT, new Biome[] { Biomes.SWAMPLAND });
    }

    if (PeacockEnable != 0) {
      EntityRegistry.addSpawn(Peacock.class, 1, 1, 3, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA });
      EntityRegistry.addSpawn(Peacock.class, 1, 1, 3, EnumCreatureType.AMBIENT, new Biome[] { Biomes.MESA_CLEAR_ROCK });
    }

    if (FairyEnable != 0) {
      EntityRegistry.addSpawn(Fairy.class, 25, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }
    if (RatEnable != 0) {
      EntityRegistry.addSpawn(Rat.class, 35, 10, 20, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
      EntityRegistry.addSpawn(Rat.class, 25, 2, 8, EnumCreatureType.AMBIENT, new Biome[] { Biomes.TAIGA });
    }
    if (DungeonBeastEnable != 0) {
      EntityRegistry.addSpawn(DungeonBeast.class, 20, 2, 4, EnumCreatureType.AMBIENT, new Biome[] { Biomes.ROOFED_FOREST });
    }

    int shoeid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "shoes"), Shoes.class, "Shoes", shoeid, this, 64, 1, true);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_UltimateHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(UltimateHelmet), "   ", "TIT", "U U", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_UltimateHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(UltimateHelmet), "TIT", "U U", "   ", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_UltimateBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(UltimateBody), "I I", "TTT", "UUU", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_UltimateLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(UltimateLegs), "III", "T T", "U U", 'I', Items.IRON_INGOT, 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_UltimateBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(UltimateBoots), "   ", "T T", "U U", 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_UltimateBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(UltimateBoots), "T T", "U U", "   ", 'U', MyIngotUranium, 'T', MyIngotTitanium);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LavaEelHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LavaEelHelmet), "   ", "***", "* *", '*', MyLavaEel);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LavaEelHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LavaEelHelmet), "***", "* *", "   ", '*', MyLavaEel);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LavaEelBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LavaEelBody), "* *", "***", "***", '*', MyLavaEel);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LavaEelLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LavaEelLegs), "***", "* *", "* *", '*', MyLavaEel);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LavaEelBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LavaEelBoots), "   ", "* *", "* *", '*', MyLavaEel);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MothScaleHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MothScaleHelmet), "   ", "***", "* *", '*', MyMothScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MothScaleHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MothScaleHelmet), "***", "* *", "   ", '*', MyMothScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MothScaleBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MothScaleBody), "* *", "***", "***", '*', MyMothScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MothScaleLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MothScaleLegs), "***", "* *", "* *", '*', MyMothScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MothScaleBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MothScaleBoots), "   ", "* *", "* *", '*', MyMothScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_EmeraldHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(EmeraldHelmet), "   ", "***", "* *", '*', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_EmeraldHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(EmeraldHelmet), "***", "* *", "   ", '*', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_EmeraldBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(EmeraldBody), "* *", "***", "***", '*', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_EmeraldLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(EmeraldLegs), "***", "* *", "* *", '*', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_EmeraldBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(EmeraldBoots), "   ", "* *", "* *", '*', Items.EMERALD);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_RubyHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(RubyHelmet), "   ", "***", "* *", '*', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_RubyHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(RubyHelmet), "***", "* *", "   ", '*', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_RubyBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(RubyBody), "* *", "***", "***", '*', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_RubyLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(RubyLegs), "***", "* *", "* *", '*', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_RubyBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(RubyBoots), "   ", "* *", "* *", '*', MyRuby);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_AmethystHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(AmethystHelmet), "   ", "***", "* *", '*', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_AmethystHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(AmethystHelmet), "***", "* *", "   ", '*', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_AmethystBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(AmethystBody), "* *", "***", "***", '*', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_AmethystLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(AmethystLegs), "***", "* *", "* *", '*', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_AmethystBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(AmethystBoots), "   ", "* *", "* *", '*', MyAmethyst);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_CrystalPinkHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CrystalPinkHelmet), "   ", "***", "* *", '*', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_CrystalPinkHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CrystalPinkHelmet), "***", "* *", "   ", '*', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_CrystalPinkBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CrystalPinkBody), "* *", "***", "***", '*', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_CrystalPinkLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CrystalPinkLegs), "***", "* *", "* *", '*', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_CrystalPinkBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CrystalPinkBoots), "   ", "* *", "* *", '*', MyCrystalPinkIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MobzillaHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MobzillaHelmet), "   ", "***", "* *", '*', MyGodzillaScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MobzillaHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MobzillaHelmet), "***", "* *", "   ", '*', MyGodzillaScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MobzillaBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MobzillaBody), "* *", "***", "***", '*', MyGodzillaScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MobzillaLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MobzillaLegs), "***", "* *", "* *", '*', MyGodzillaScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MobzillaBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MobzillaBoots), "   ", "* *", "* *", '*', MyGodzillaScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LapisHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LapisHelmet), "   ", "***", "* *", '*', Blocks.LAPIS_BLOCK);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LapisHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LapisHelmet), "***", "* *", "   ", '*', Blocks.LAPIS_BLOCK);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LapisBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LapisBody), "* *", "***", "***", '*', Blocks.LAPIS_BLOCK);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LapisLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LapisLegs), "***", "* *", "* *", '*', Blocks.LAPIS_BLOCK);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_LapisBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(LapisBoots), "   ", "* *", "* *", '*', Blocks.LAPIS_BLOCK);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_QueenHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(QueenHelmet), "   ", "***", "* *", '*', MyQueenScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_QueenHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(QueenHelmet), "***", "* *", "   ", '*', MyQueenScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_QueenBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(QueenBody), "* *", "***", "***", '*', MyQueenScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_QueenLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(QueenLegs), "***", "* *", "* *", '*', MyQueenScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_QueenBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(QueenBoots), "   ", "* *", "* *", '*', MyQueenScale);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_PeacockFeatherHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(PeacockFeatherHelmet), "   ", "***", "* *", '*', MyPeacockFeather);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_PeacockFeatherHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(PeacockFeatherHelmet), "***", "* *", "   ", '*', MyPeacockFeather);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_PeacockFeatherBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(PeacockFeatherBody), "* *", "***", "***", '*', MyPeacockFeather);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_PeacockFeatherLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(PeacockFeatherLegs), "***", "* *", "* *", '*', MyPeacockFeather);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_PeacockFeatherBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(PeacockFeatherBoots), "   ", "* *", "* *", '*', MyPeacockFeather);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_TigersEyeHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(TigersEyeHelmet), "   ", "***", "* *", '*', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_TigersEyeHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(TigersEyeHelmet), "***", "* *", "   ", '*', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_TigersEyeBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(TigersEyeBody), "* *", "***", "***", '*', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_TigersEyeLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(TigersEyeLegs), "***", "* *", "* *", '*', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_TigersEyeBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(TigersEyeBoots), "   ", "* *", "* *", '*', MyTigersEyeIngot);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_ExperienceHelmet"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(ExperienceHelmet), "EEE", "EAE", "EEE", 'A', EmeraldHelmet, 'E', Items.EXPERIENCE_BOTTLE);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_ExperienceBody"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(ExperienceBody), "EEE", "EAE", "EEE", 'A', EmeraldBody, 'E', Items.EXPERIENCE_BOTTLE);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_ExperienceLegs"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(ExperienceLegs), "EEE", "EAE", "EEE", 'A', EmeraldLegs, 'E', Items.EXPERIENCE_BOTTLE);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_ExperienceBoots"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(ExperienceBoots), "EEE", "EAE", "EEE", 'A', EmeraldBoots, 'E', Items.EXPERIENCE_BOTTLE);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_Blocks.WEB"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(Blocks.WEB), "***", "* *", "***", '*', Items.STRING);

    int cageid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "entity_cage"), EntityCage.class, "EntityCage", cageid, this, 64, 1, true);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_cageempty_iron"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CageEmpty, 2), "IWI", "W W", "IWI", 'W', Items.STICK, 'I', Items.IRON_INGOT);

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_cageempty_crystal"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(CageEmpty, 2), "IWI", "W W", "IWI", 'W', CrystalSticks, 'I', MyCrystalPinkIngot);

    int arrowid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "ultimate_arrow"), UltimateArrow.class, "UltimateArrow", arrowid, this, 64, 1, true);

    int irukandiarrowid = nextEntityId++;
    EntityRegistry.registerModEntity(new ResourceLocation("chaospersists", "irukandji_arrow"), IrukandjiArrow.class, "IrukandjiArrow", irukandiarrowid, this, 64, 1, true);
    addShapelessRecipe(new ResourceLocation("chaospersists", "planks_skytree"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(Blocks.PLANKS, 4), Ingredient.fromStacks(new ItemStack(MySkyTreeLog)));
    addShapelessRecipe(new ResourceLocation("chaospersists", "planks_duplicator"), new ResourceLocation("chaospersists", "eggs"), new ItemStack(Blocks.PLANKS, 4), Ingredient.fromStacks(new ItemStack(MyDT)));

    addShapedRecipe(new ResourceLocation("chaospersists", "recipe_MyElevator"), new ResourceLocation("chaospersists", "chaospersists"), new ItemStack(MyElevator), "   ", "WWW", "DRD", 'W', Blocks.PLANKS, 'R', Items.REDSTONE, 'D', Items.DIAMOND);

    GameRegistry.registerWorldGenerator(this.chaospersistsGen, 10);

    proxy.registerRenderThings();

    proxy.registerKeyboardInput();

    proxy.registerNetworkStuff();

    DimensionType chaospersistsType = DimensionType.register("chaospersists", "_chaospersists", DimensionID, WorldProviderChaos.class, true);
    DimensionManager.registerDimension(DimensionID, chaospersistsType);

    DimensionType chaospersistsType2 = DimensionType.register("chaospersists2", "_chaospersists2", DimensionID2, WorldProviderChaos2.class, true);
    DimensionManager.registerDimension(DimensionID2, chaospersistsType2);

    DimensionType chaospersistsType3 = DimensionType.register("chaospersists3", "_chaospersists3", DimensionID3, WorldProviderChaos3.class, true);
    DimensionManager.registerDimension(DimensionID3, chaospersistsType3);

    DimensionType chaospersistsType4 = DimensionType.register("chaospersists4", "_chaospersists4", DimensionID4, WorldProviderChaos4.class, true);
    DimensionManager.registerDimension(DimensionID4, chaospersistsType4);

    DimensionType chaospersistsType5 = DimensionType.register("chaospersists5", "_chaospersists5", DimensionID5, WorldProviderChaos5.class, true);
    DimensionManager.registerDimension(DimensionID5, chaospersistsType5);

    DimensionType chaospersistsType6 = DimensionType.register("chaospersists6", "_chaospersists6", DimensionID6, WorldProviderChaos6.class, true);
    DimensionManager.registerDimension(DimensionID6, chaospersistsType6);

    GameRegistry.registerTileEntity(TileEntityCrystalFurnace.class, new ResourceLocation("chaospersists", "crystalfurnace"));
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new ChaosGUIHandler());

    DoDispenserRegistrations();
  }

  @SubscribeEvent
  public void onRegisterBiomes(RegistryEvent.Register<Biome> event) {
    UTOPIA_BIOME = new BiomeGenUtopianPlains(BiomeUtopiaID);
    UTOPIA_BIOME.setRegistryName(new ResourceLocation("chaospersists", "utopia"));
    event.getRegistry().register(UTOPIA_BIOME);

    VILLAGE_BIOME = new BiomeVillagePlains();
    VILLAGE_BIOME.setRegistryName(new ResourceLocation("chaospersists", "village_dimension"));
    event.getRegistry().register(VILLAGE_BIOME);

    DANGER_BIOME = new BiomeDangerPlains();
    DANGER_BIOME.setRegistryName(new ResourceLocation("chaospersists", "danger_dimension"));
    event.getRegistry().register(DANGER_BIOME);

    CRYSTAL_BIOME = new BiomeCrystalPlains();
    CRYSTAL_BIOME.setRegistryName(new ResourceLocation("chaospersists", "crystal_dimension"));
    event.getRegistry().register(CRYSTAL_BIOME);

    CHAOS_BIOME = new BiomeChaosPlains();
    CHAOS_BIOME.setRegistryName(new ResourceLocation("chaospersists", "chaos_dimension"));
    event.getRegistry().register(CHAOS_BIOME);

    MINING_BIOME = new BiomeMiningDimension();
    MINING_BIOME.setRegistryName(new ResourceLocation("chaospersists", "mining_dimension"));
    event.getRegistry().register(MINING_BIOME);
  }

  @SubscribeEvent
  public void onLootTableLoad(LootTableLoadEvent event) {
    if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
      LootPool pool = event.getTable().getPool("main");
      if (pool != null) {
        addLegacyLootEntry(pool, MyRuby, 3, 1, 1, "chaospersists:ruby");
        addLegacyLootEntry(pool, MyAmethyst, 3, 1, 1, "chaospersists:amethyst");
        addLegacyLootEntry(pool, MyThunderStaff, 2, 1, 1, "chaospersists:thunderstaff");
      }
    } else if (event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)) {
      LootPool pool = event.getTable().getPool("main");
      if (pool != null) {
        addLegacyLootEntry(pool, MyRuby, 3, 1, 1, "chaospersists:ruby");
        addLegacyLootEntry(pool, MyAmethyst, 3, 1, 1, "chaospersists:amethyst");
        addLegacyLootEntry(pool, AntRobotKit, 3, 1, 1, "chaospersists:antrobotkit");
      }
    } else if (event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)) {
      LootPool pool = event.getTable().getPool("main");
      if (pool != null) {
        addLegacyLootEntry(pool, MyRuby, 2, 1, 1, "chaospersists:ruby");
        addLegacyLootEntry(pool, MyAmethyst, 2, 1, 1, "chaospersists:amethyst");
        addLegacyLootEntry(pool, SpiderRobotKit, 2, 1, 1, "chaospersists:spiderrobotkit");
      }
    }
  }

  private static void addLegacyLootEntry(LootPool pool, Item item, int weight, int minCount, int maxCount, String entryName) {
    if (pool == null || item == null) {
      return;
    }
    LootFunction[] functions = new LootFunction[]{
        new SetCount(new LootCondition[0], new RandomValueRange(minCount, maxCount))
    };
    pool.addEntry(new LootEntryItem(item, weight, 0, functions, new LootCondition[0], entryName));
  }

  @SubscribeEvent
  public void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
    if (event.getItemStack().isEmpty()) return;
    if (event.getItemStack().getItem() == Item.getItemFromBlock(CrystalCoal)) {
      event.setBurnTime(20000);
    }
  }

  /** Tamed Girlfriends and Boyfriends assist in combat like wolves when their owner damages a mob. */
  @SubscribeEvent
  public void onLivingHurtOwnerAssistGirlfriends(LivingHurtEvent event) {
    if (event == null || event.getEntityLiving() == null) {
      return;
    }
    EntityLivingBase victim = event.getEntityLiving();
    net.minecraft.util.DamageSource src = event.getSource();
    if (src == null) {
      return;
    }
    Entity attacker = src.getTrueSource();
    if (!(attacker instanceof EntityPlayer)) {
      return;
    }
    EntityPlayer player = (EntityPlayer) attacker;
    if (victim == player) {
      return;
    }
    if (victim.world == null || victim.world.isRemote) {
      return;
    }
    if (victim instanceof EntityTameable) {
      EntityTameable te = (EntityTameable) victim;
      if (te.isTamed() && player.getUniqueID().equals(te.getOwnerId())) {
        return;
      }
    }
    for (Girlfriend g : victim.world.getEntitiesWithinAABB(Girlfriend.class, player.getEntityBoundingBox().grow(16.0D))) {
      if (!g.isTamed() || g.isSitting() || !g.isOwner(player)) {
        continue;
      }
      g.setAttackTarget(victim);
    }
    for (Boyfriend b : victim.world.getEntitiesWithinAABB(Boyfriend.class, player.getEntityBoundingBox().grow(16.0D))) {
      if (!b.isTamed() || b.isSitting() || !b.isOwner(player)) {
        continue;
      }
      b.setAttackTarget(victim);
    }
  }

  /**
   * 1.7.10 parity: custom mobs used legacy armor reduction expectations.
   * In 1.12.2, high-damage hits penetrate armor more aggressively, making
   * high-defense mobs (e.g. Emperor Scorpion) take too much damage.
   *
   * This adjusts pre-armor incoming damage for ChaosPersists mobs so that
   * post-armor damage tracks the legacy model: damage * (1 - armor/25).
   */
  @SubscribeEvent
  public void onLivingHurtLegacyArmorParity(LivingHurtEvent event) {
    if (event == null || event.getEntityLiving() == null) {
      return;
    }
    EntityLivingBase living = event.getEntityLiving();
    if (living.world == null || living.world.isRemote) {
      return;
    }
    ResourceLocation id = EntityList.getKey(living);
    if (id == null || !"chaospersists".equals(id.getNamespace())) {
      return;
    }
    DamageSource source = event.getSource();
    if (source == null || source.isUnblockable()) {
      return;
    }
    float incoming = event.getAmount();
    if (incoming <= 0.0f) {
      return;
    }

    int armor = Math.max(0, Math.min(20, living.getTotalArmorValue()));
    if (armor <= 0) {
      return;
    }

    // 1.7.10-style final damage expectation.
    float legacyFinal = incoming * (25.0f - (float)armor) / 25.0f;

    float toughness = 0.0f;
    IAttributeInstance toughAttr = living.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS);
    if (toughAttr != null) {
      toughness = (float)toughAttr.getAttributeValue();
    }

    float vanillaFinalAtIncoming = net.minecraft.util.CombatRules.getDamageAfterAbsorb(incoming, (float)armor, toughness);
    if (vanillaFinalAtIncoming <= legacyFinal + 1.0e-4f) {
      return;
    }

    // Invert 1.12 armor curve by binary search for pre-armor amount.
    float low = 0.0f;
    float high = incoming;
    float cappedHigh = incoming * 8.0f + 40.0f;
    while (net.minecraft.util.CombatRules.getDamageAfterAbsorb(high, (float)armor, toughness) < legacyFinal && high < cappedHigh) {
      high *= 2.0f;
    }
    if (high > cappedHigh) {
      high = cappedHigh;
    }
    for (int i = 0; i < 14; ++i) {
      float mid = (low + high) * 0.5f;
      float out = net.minecraft.util.CombatRules.getDamageAfterAbsorb(mid, (float)armor, toughness);
      if (out < legacyFinal) {
        low = mid;
      } else {
        high = mid;
      }
    }

    event.setAmount(high);
  }

  private ResourceLocation getSpawnerEntityId(TileEntityMobSpawner spawner) {
    if (spawner == null) {
      return null;
    }
    return SpawnerFixHelper.getMobSpawnerEntityId(spawner.getSpawnerBaseLogic());
  }

  private void normalizeSpawnerId(TileEntityMobSpawner spawner) {
    if (spawner == null) {
      return;
    }
    ResourceLocation current = SpawnerFixHelper.getMobSpawnerEntityId(spawner.getSpawnerBaseLogic());
    ResourceLocation normalized = SpawnerFixHelper.normalizeSpawnerEntityId(current);
    if (normalized != null && (current == null || !normalized.equals(current))) {
      spawner.getSpawnerBaseLogic().setEntityId(normalized);
      spawner.markDirty();
    }
  }

  private boolean isEntityClassInBiomeSpawnListsForDebug(Biome biome, Class<?> entityClass) {
    return this.isEntityClassInBiomeSpawnListForDebug(biome.getSpawnableList(EnumCreatureType.MONSTER), entityClass)
            || this.isEntityClassInBiomeSpawnListForDebug(biome.getSpawnableList(EnumCreatureType.CREATURE), entityClass)
            || this.isEntityClassInBiomeSpawnListForDebug(biome.getSpawnableList(EnumCreatureType.AMBIENT), entityClass)
            || this.isEntityClassInBiomeSpawnListForDebug(biome.getSpawnableList(EnumCreatureType.WATER_CREATURE), entityClass);
  }

  private boolean isEntityClassInBiomeSpawnListForDebug(List<Biome.SpawnListEntry> entries, Class<?> entityClass) {
    if (entries == null || entries.isEmpty() || entityClass == null) {
      return false;
    }
    for (Biome.SpawnListEntry entry : entries) {
      if (entry != null && entry.entityClass != null && entry.entityClass.isAssignableFrom(entityClass)) {
        return true;
      }
    }
    return false;
  }

  @SubscribeEvent
  public void onLivingSpawnCheckDebug(LivingSpawnEvent.CheckSpawn event) {
    if (event == null || event.getWorld() == null || event.getWorld().isRemote || event.getEntityLiving() == null) {
      return;
    }
    if (event.getWorld().provider == null || event.getWorld().provider.getDimension() != getDimension()) {
      return;
    }
    EntityLivingBase entity = event.getEntityLiving();
    ResourceLocation id = EntityList.getKey(entity);
    if (id == null || !"chaospersists".equals(id.getNamespace())) {
      return;
    }
    Biome biome = event.getWorld().getBiome(entity.getPosition());
    boolean listed = biome != null && this.isEntityClassInBiomeSpawnListsForDebug(biome, entity.getClass());
    String biomeName = biome == null ? "null" : String.valueOf(biome.getRegistryName());
    FMLLog.log.info(
            "ChaosPersists DEBUG spawn-check dim={} id={} class={} biome={} listed={} fromSpawner={} result={} pos=({}, {}, {})",
            event.getWorld().provider.getDimension(),
            id,
            entity.getClass().getSimpleName(),
            biomeName,
            listed,
            event.isSpawner(),
            event.getResult(),
            (int) entity.posX,
            (int) entity.posY,
            (int) entity.posZ
    );
  }

  @SubscribeEvent
  public void onChunkLoadNormalizeSpawners(ChunkEvent.Load event) {
    if (event == null || event.getWorld() == null || event.getWorld().isRemote || event.getChunk() == null) {
      return;
    }
    java.util.Map<BlockPos, TileEntity> map = event.getChunk().getTileEntityMap();
    if (map == null || map.isEmpty()) {
      return;
    }
    for (TileEntity te : map.values()) {
      if (te instanceof TileEntityMobSpawner) {
        this.normalizeSpawnerId((TileEntityMobSpawner) te);
      }
    }
  }

  @SubscribeEvent
  public void onEntityJoinWorld(EntityJoinWorldEvent event) {
    if (event == null || event.getWorld() == null || event.getWorld().isRemote) {
      return;
    }
    if (event.getWorld().provider != null && event.getWorld().provider.getDimension() == getDimension()
            && event.getEntity() instanceof EntityLivingBase) {
      EntityLivingBase living = (EntityLivingBase) event.getEntity();
      ResourceLocation id = EntityList.getKey(living);
      if (id != null && "chaospersists".equals(id.getNamespace())) {
        Biome biome = event.getWorld().getBiome(living.getPosition());
        String biomeName = biome == null ? "null" : String.valueOf(biome.getRegistryName());
        FMLLog.log.info(
                "ChaosPersists DEBUG entity-join dim={} id={} class={} biome={} pos=({}, {}, {})",
                event.getWorld().provider.getDimension(),
                id,
                living.getClass().getSimpleName(),
                biomeName,
                (int) living.posX,
                (int) living.posY,
                (int) living.posZ
        );
      }
    }
    if (!(event.getEntity() instanceof PitchBlack)) {
      return;
    }

    PitchBlack nightmare = (PitchBlack) event.getEntity();
    BlockPos base = new BlockPos(nightmare.posX, nightmare.posY, nightmare.posZ);

    for (int dx = -8; dx <= 8; ++dx) {
      for (int dy = -4; dy <= 8; ++dy) {
        for (int dz = -8; dz <= 8; ++dz) {
          TileEntity te = event.getWorld().getTileEntity(base.add(dx, dy, dz));
          if (!(te instanceof TileEntityMobSpawner)) {
            continue;
          }

          String path = null;
          ResourceLocation id = SpawnerFixHelper.getMobSpawnerEntityId(((TileEntityMobSpawner) te).getSpawnerBaseLogic());
          if (id != null) {
            path = SpawnerFixHelper.normalizeSpawnerEntityId(id).getPath();
          }

          if (path != null && "nightmare".equalsIgnoreCase(path)) {
            nightmare.setSpawnedFromSpawner();
            return;
          }
        }
      }
    }
  }

  @SubscribeEvent
  public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
    if (event == null || event.getEntityLiving() == null) {
      return;
    }
    EntityLivingBase base = event.getEntityLiving();
    if (base.world == null || base.world.isRemote) {
      return;
    }
    if (!(base instanceof EntityLiving)) {
      return;
    }
    ResourceLocation key = EntityList.getKey(base);
    boolean chaosHostileMob = key != null
            && "chaospersists".equals(key.getNamespace())
            && base instanceof IMob;
    if (!this.isAlwaysHostileInLegacy(base.getClass()) && !chaosHostileMob) {
      return;
    }
    if (PlayNicely != 0 || base.world.getDifficulty() == net.minecraft.world.EnumDifficulty.PEACEFUL) {
      return;
    }

    EntityLiving mob = (EntityLiving) base;
    if (mob.ticksExisted % 5 != 0) {
      return;
    }
    EntityLivingBase current = mob.getAttackTarget();
    if (current != null && current.isEntityAlive()) {
      return;
    }

    EntityPlayer target = base.world.getClosestPlayer(base.posX, base.posY, base.posZ, 24.0, false);
    if (target == null || target.isCreative() || target.isSpectator()) {
      return;
    }

    mob.setAttackTarget(target);
    mob.setRevengeTarget(target);
    if (mob instanceof Bee) {
      ((Bee) mob).forceAttackTarget(target);
    }
  }

  private boolean isAlwaysHostileInLegacy(Class<?> c) {
    return c == PitchBlack.class
            || c == CreepingHorror.class
            || c == TerribleTerror.class
            || c == LurkingTerror.class
            || c == Basilisk.class
            || c == Scorpion.class
            || c == CaveFisher.class
            || c == Bee.class
            || c == Mantis.class
            || c == Rat.class
            || c == EnderKnight.class
            || c == EnderReaper.class;
  }

  public void initializeCagesAndEggs()
  {
    CageEmpty = new CritterCage(0, 160).setTranslationKey("cageempty").setRegistryName("chaospersists", "cageempty");
    CagedSpider = new CritterCage(0, 161).setTranslationKey("cagespider").setRegistryName("chaospersists", "cagespider");
    CagedBat = new CritterCage(0, 162).setTranslationKey("cagebat").setRegistryName("chaospersists", "cagebat");
    CagedCow = new CritterCage(0, 163).setTranslationKey("cagecow").setRegistryName("chaospersists", "cagecow");
    CagedPig = new CritterCage(0, 164).setTranslationKey("cagepig").setRegistryName("chaospersists", "cagepig");
    CagedSquid = new CritterCage(0, 165).setTranslationKey("cagesquid").setRegistryName("chaospersists", "cagesquid");
    CagedChicken = new CritterCage(0, 166).setTranslationKey("cagechicken").setRegistryName("chaospersists", "cagechicken");
    CagedCreeper = new CritterCage(0, 167).setTranslationKey("cagecreeper").setRegistryName("chaospersists", "cagecreeper");
    CagedSkeleton = new CritterCage(0, 168).setTranslationKey("cageskeleton").setRegistryName("chaospersists", "cageskeleton");
    CagedZombie = new CritterCage(0, 169).setTranslationKey("cagezombie").setRegistryName("chaospersists", "cagezombie");
    CagedSlime = new CritterCage(0, 170).setTranslationKey("cageslime").setRegistryName("chaospersists", "cageslime");
    CagedGhast = new CritterCage(0, 171).setTranslationKey("cageghast").setRegistryName("chaospersists", "cageghast");
    CagedZombiePigman = new CritterCage(0, 172).setTranslationKey("cagezombiepigman").setRegistryName("chaospersists", "cagezombiepigman");
    CagedEnderman = new CritterCage(0, 173).setTranslationKey("cageenderman").setRegistryName("chaospersists", "cageenderman");
    CagedCaveSpider = new CritterCage(0, 174).setTranslationKey("cagecavespider").setRegistryName("chaospersists", "cagecavespider");
    CagedSilverfish = new CritterCage(0, 175).setTranslationKey("cagesilverfish").setRegistryName("chaospersists", "cagesilverfish");
    CagedMagmaCube = new CritterCage(0, 176).setTranslationKey("cagemagmacube").setRegistryName("chaospersists", "cagemagmacube");
    CagedWitch = new CritterCage(0, 177).setTranslationKey("cagewitch").setRegistryName("chaospersists", "cagewitch");
    CagedSheep = new CritterCage(0, 178).setTranslationKey("cagesheep").setRegistryName("chaospersists", "cagesheep");
    CagedWolf = new CritterCage(0, 179).setTranslationKey("cagewolf").setRegistryName("chaospersists", "cagewolf");
    CagedMooshroom = new CritterCage(0, 180).setTranslationKey("cagemooshroom").setRegistryName("chaospersists", "cagemooshroom");
    CagedOcelot = new CritterCage(0, 181).setTranslationKey("cageocelot").setRegistryName("chaospersists", "cageocelot");
    CagedBlaze = new CritterCage(0, 182).setTranslationKey("cageblaze").setRegistryName("chaospersists", "cageblaze");
    CagedGirlfriend = new CritterCage(0, 183).setTranslationKey("cagegirlfriend").setRegistryName("chaospersists", "cagegirlfriend");
    CagedBoyfriend = new CritterCage(0, 215).setTranslationKey("cageboyfriend").setRegistryName("chaospersists", "cageboyfriend");
    CagedWitherSkeleton = new CritterCage(0, 188).setTranslationKey("cagewitherskeleton").setRegistryName("chaospersists", "cagewitherskeleton");
    CagedEnderDragon = new CritterCage(0, 184).setTranslationKey("cageenderdragon").setRegistryName("chaospersists", "cageenderdragon");
    CagedSnowGolem = new CritterCage(0, 185).setTranslationKey("cagesnowgolem").setRegistryName("chaospersists", "cagesnowgolem");
    CagedIronGolem = new CritterCage(0, 186).setTranslationKey("cageirongolem").setRegistryName("chaospersists", "cageirongolem");
    CagedWitherBoss = new CritterCage(0, 187).setTranslationKey("cagewitherboss").setRegistryName("chaospersists", "cagewitherboss");
    CagedRedCow = new CritterCage(0, 189).setTranslationKey("cageredcow").setRegistryName("chaospersists", "cageredcow");
    CagedGoldCow = new CritterCage(0, 190).setTranslationKey("cagegoldcow").setRegistryName("chaospersists", "cagegoldcow");
    CagedEnchantedCow = new CritterCage(0, 191).setTranslationKey("cageenchantedcow").setRegistryName("chaospersists", "cageenchantedcow");
    CagedMOTHRA = new CritterCage(0, 208).setTranslationKey("cagemothra").setRegistryName("chaospersists", "cagemothra");
    CagedAlo = new CritterCage(0, 209).setTranslationKey("cagealosaurus").setRegistryName("chaospersists", "cagealosaurus");
    CagedCryo = new CritterCage(0, 210).setTranslationKey("cagecryolophosaurus").setRegistryName("chaospersists", "cagecryolophosaurus");
    CagedCama = new CritterCage(0, 211).setTranslationKey("cagecamarasaurus").setRegistryName("chaospersists", "cagecamarasaurus");
    CagedVelo = new CritterCage(0, 212).setTranslationKey("cagevelocityraptor").setRegistryName("chaospersists", "cagevelocityraptor");
    CagedHydro = new CritterCage(0, 213).setTranslationKey("cagehydrolisc").setRegistryName("chaospersists", "cagehydrolisc");
    CagedBasil = new CritterCage(0, 214).setTranslationKey("cagebasilisc").setRegistryName("chaospersists", "cagebasilisc");
    CagedDragonfly = new CritterCage(0, 220).setTranslationKey("cagedragonfly").setRegistryName("chaospersists", "cagedragonfly");
    CagedEmperorScorpion = new CritterCage(0, 222).setTranslationKey("cageemperorscorpion").setRegistryName("chaospersists", "cageemperorscorpion");
    CagedScorpion = new CritterCage(0, 224).setTranslationKey("cagescorpion").setRegistryName("chaospersists", "cagescorpion");
    CagedCaveFisher = new CritterCage(0, 226).setTranslationKey("cagecavefisher").setRegistryName("chaospersists", "cagecavefisher");
    CagedSpyro = new CritterCage(0, 228).setTranslationKey("cagespyro").setRegistryName("chaospersists", "cagespyro");
    CagedBaryonyx = new CritterCage(0, 230).setTranslationKey("cagebaryonyx").setRegistryName("chaospersists", "cagebaryonyx");
    CagedGammaMetroid = new CritterCage(0, 232).setTranslationKey("cagegammametroid").setRegistryName("chaospersists", "cagegammametroid");
    CagedCockateil = new CritterCage(0, 234).setTranslationKey("cagecockateil").setRegistryName("chaospersists", "cagecockateil");
    CagedKyuubi = new CritterCage(0, 236).setTranslationKey("cagekyuubi").setRegistryName("chaospersists", "cagekyuubi");
    CagedAlien = new CritterCage(0, 238).setTranslationKey("cagealien").setRegistryName("chaospersists", "cagealien");
    CagedAttackSquid = new CritterCage(0, 240).setTranslationKey("cageattacksquid").setRegistryName("chaospersists", "cageattacksquid");
    CagedWaterDragon = new CritterCage(0, 242).setTranslationKey("cagewaterdragon").setRegistryName("chaospersists", "cagewaterdragon");
    CagedCephadrome = new CritterCage(0, 248).setTranslationKey("cagecephadrome").setRegistryName("chaospersists", "cagecephadrome");
    CagedKraken = new CritterCage(0, 244).setTranslationKey("cagekraken").setRegistryName("chaospersists", "cagekraken");
    CagedLizard = new CritterCage(0, 246).setTranslationKey("cagelizard").setRegistryName("chaospersists", "cagelizard");
    CagedDragon = new CritterCage(0, 250).setTranslationKey("cagedragon").setRegistryName("chaospersists", "cagedragon");
    CagedBee = new CritterCage(0, 252).setTranslationKey("cagebee").setRegistryName("chaospersists", "cagebee");
    CagedHorse = new CritterCage(0, 253).setTranslationKey("cagehorse").setRegistryName("chaospersists", "cagehorse");
    CagedFirefly = new CritterCage(0, 255).setTranslationKey("cagefirefly").setRegistryName("chaospersists", "cagefirefly");
    CagedChipmunk = new CritterCage(0, 256).setTranslationKey("cagechipmunk").setRegistryName("chaospersists", "cagechipmunk");
    CagedGazelle = new CritterCage(0, 257).setTranslationKey("cagegazelle").setRegistryName("chaospersists", "cagegazelle");
    CagedOstrich = new CritterCage(0, 258).setTranslationKey("cageostrich").setRegistryName("chaospersists", "cageostrich");
    CagedTrooper = new CritterCage(0, 259).setTranslationKey("cagetrooper").setRegistryName("chaospersists", "cagetrooper");
    CagedSpit = new CritterCage(0, 260).setTranslationKey("cagespit").setRegistryName("chaospersists", "cagespit");
    CagedStink = new CritterCage(0, 261).setTranslationKey("cagestink").setRegistryName("chaospersists", "cagestink");
    CagedCreepingHorror = new CritterCage(0, 268).setTranslationKey("cagecreepinghorror").setRegistryName("chaospersists", "cagecreepinghorror");
    CagedTerribleTerror = new CritterCage(0, 269).setTranslationKey("cageterribleterror").setRegistryName("chaospersists", "cageterribleterror");
    CagedCliffRacer = new CritterCage(0, 270).setTranslationKey("cagecliffracer").setRegistryName("chaospersists", "cagecliffracer");
    CagedTriffid = new CritterCage(0, 271).setTranslationKey("cagetriffid").setRegistryName("chaospersists", "cagetriffid");
    CagedPitchBlack = new CritterCage(0, 272).setTranslationKey("cagenightmare").setRegistryName("chaospersists", "cagenightmare");
    CagedLurkingTerror = new CritterCage(0, 273).setTranslationKey("cagelurkingterror").setRegistryName("chaospersists", "cagelurkingterror");
    CagedSmallWorm = new CritterCage(0, 281).setTranslationKey("cagesmallworm").setRegistryName("chaospersists", "cagesmallworm");
    CagedMediumWorm = new CritterCage(0, 282).setTranslationKey("cagemediumworm").setRegistryName("chaospersists", "cagemediumworm");
    CagedLargeWorm = new CritterCage(0, 283).setTranslationKey("cagelargeworm").setRegistryName("chaospersists", "cagelargeworm");
    CagedCassowary = new CritterCage(0, 284).setTranslationKey("cagecassowary").setRegistryName("chaospersists", "cagecassowary");
    CagedCloudShark = new CritterCage(0, 285).setTranslationKey("cagecloudshark").setRegistryName("chaospersists", "cagecloudshark");
    CagedGoldFish = new CritterCage(0, 286).setTranslationKey("cagegoldfish").setRegistryName("chaospersists", "cagegoldfish");
    CagedLeafMonster = new CritterCage(0, 287).setTranslationKey("cageleafmonster").setRegistryName("chaospersists", "cageleafmonster");
    CagedEnderKnight = new CritterCage(0, 296).setTranslationKey("cageenderknight").setRegistryName("chaospersists", "cageenderknight");
    CagedEnderReaper = new CritterCage(0, 297).setTranslationKey("cageenderreaper").setRegistryName("chaospersists", "cageenderreaper");
    CagedBeaver = new CritterCage(0, 300).setTranslationKey("cagebeaver").setRegistryName("chaospersists", "cagebeaver");
    CagedUrchin = new CritterCage(0, 323).setTranslationKey("cageurchin").setRegistryName("chaospersists", "cageurchin");
    CagedFlounder = new CritterCage(0, 319).setTranslationKey("cageflounder").setRegistryName("chaospersists", "cageflounder");
    CagedSkate = new CritterCage(0, 322).setTranslationKey("cageskate").setRegistryName("chaospersists", "cageskate");
    CagedRotator = new CritterCage(0, 313).setTranslationKey("cagerotator").setRegistryName("chaospersists", "cagerotator");
    CagedPeacock = new CritterCage(0, 315).setTranslationKey("cagepeacock").setRegistryName("chaospersists", "cagepeacock");
    CagedFairy = new CritterCage(0, 316).setTranslationKey("cagefairy").setRegistryName("chaospersists", "cagefairy");
    CagedDungeonBeast = new CritterCage(0, 317).setTranslationKey("cagedungeonbeast").setRegistryName("chaospersists", "cagedungeonbeast");
    CagedVortex = new CritterCage(0, 314).setTranslationKey("cagevortex").setRegistryName("chaospersists", "cagevortex");
    CagedRat = new CritterCage(0, 318).setTranslationKey("cagerat").setRegistryName("chaospersists", "cagerat");
    CagedWhale = new CritterCage(0, 320).setTranslationKey("cagewhale").setRegistryName("chaospersists", "cagewhale");
    CagedIrukandji = new CritterCage(0, 321).setTranslationKey("cageirukandji").setRegistryName("chaospersists", "cageirukandji");
    CagedTRex = new CritterCage(0, 345).setTranslationKey("cagetrex").setRegistryName("chaospersists", "cagetrex");
    CagedHercules = new CritterCage(0, 346).setTranslationKey("cagehercules").setRegistryName("chaospersists", "cagehercules");
    CagedMantis = new CritterCage(0, 347).setTranslationKey("cagemantis").setRegistryName("chaospersists", "cagemantis");
    CagedStinky = new CritterCage(0, 348).setTranslationKey("cagestinky").setRegistryName("chaospersists", "cagestinky");
    CagedEasterBunny = new CritterCage(0, 150).setTranslationKey("cageeasterbunny").setRegistryName("chaospersists", "cageeasterbunny");
    CagedCaterKiller = new CritterCage(0, 151).setTranslationKey("cagecaterkiller").setRegistryName("chaospersists", "cagecaterkiller");
    CagedMolenoid = new CritterCage(0, 152).setTranslationKey("cagemolenoid").setRegistryName("chaospersists", "cagemolenoid");
    CagedSeaMonster = new CritterCage(0, 153).setTranslationKey("cageseamonster").setRegistryName("chaospersists", "cageseamonster");
    CagedSeaViper = new CritterCage(0, 154).setTranslationKey("cageseaviper").setRegistryName("chaospersists", "cageseaviper");
    CagedLeon = new CritterCage(0, 357).setTranslationKey("cageleon").setRegistryName("chaospersists", "cageleon");
    CagedHammerhead = new CritterCage(0, 359).setTranslationKey("cagehammerhead").setRegistryName("chaospersists", "cagehammerhead");
    CagedRubberDucky = new CritterCage(0, 361).setTranslationKey("cagerubberducky").setRegistryName("chaospersists", "cagerubberducky");
    CagedCrystalCow = new CritterCage(0, 216).setTranslationKey("cagecrystalcow").setRegistryName("chaospersists", "cagecrystalcow");
    CagedVillager = new CritterCage(0, 217).setTranslationKey("cagevillager").setRegistryName("chaospersists", "cagevillager");
    CagedCriminal = new CritterCage(0, 218).setTranslationKey("cagecriminal").setRegistryName("chaospersists", "cagecriminal");
    CagedBrutalfly = new CritterCage(0, 373).setTranslationKey("cagebrutalfly").setRegistryName("chaospersists", "cagebrutalfly");
    CagedNastysaurus = new CritterCage(0, 374).setTranslationKey("cagenastysaurus").setRegistryName("chaospersists", "cagenastysaurus");
    CagedPointysaurus = new CritterCage(0, 375).setTranslationKey("cagepointysaurus").setRegistryName("chaospersists", "cagepointysaurus");
    CagedCricket = new CritterCage(0, 376).setTranslationKey("cagecricket").setRegistryName("chaospersists", "cagecricket");
    CagedFrog = new CritterCage(0, 377).setTranslationKey("cagefrog").setRegistryName("chaospersists", "cagefrog");
    CagedSpiderDriver = new CritterCage(0, 382).setTranslationKey("cagespiderdriver").setRegistryName("chaospersists", "cagespiderdriver");
    CagedCrab = new CritterCage(0, 384).setTranslationKey("cagecrab").setRegistryName("chaospersists", "cagecrab");

    WitherSkeletonEgg = new ItemSpawnEgg(0,192).setTranslationKey("eggwitherskeleton").setRegistryName("chaospersists", "eggwitherskeleton");
    EnderDragonEgg = new ItemSpawnEgg(0,193).setTranslationKey("eggenderdragon").setRegistryName("chaospersists", "eggenderdragon");
    SnowGolemEgg = new ItemSpawnEgg(0,194).setTranslationKey("eggsnowgolem").setRegistryName("chaospersists", "eggsnowgolem");
    IronGolemEgg = new ItemSpawnEgg(0,195).setTranslationKey("eggirongolem").setRegistryName("chaospersists", "eggirongolem");
    WitherBossEgg = new ItemSpawnEgg(0,196).setTranslationKey("eggwitherboss").setRegistryName("chaospersists", "eggwitherboss");
    GirlfriendEgg = new ItemSpawnEgg(0,197).setTranslationKey("egggirlfriend").setRegistryName("chaospersists", "egggirlfriend");
    RedCowEgg = new ItemSpawnEgg(0,198).setTranslationKey("eggredcow").setRegistryName("chaospersists", "eggredcow");
    CrystalCowEgg = new ItemSpawnEgg(0,363).setTranslationKey("eggcrystalcow").setRegistryName("chaospersists", "eggcrystalcow");
    GoldCowEgg = new ItemSpawnEgg(0,199).setTranslationKey("egggoldcow").setRegistryName("chaospersists", "egggoldcow");
    EnchantedCowEgg = new ItemSpawnEgg(0,200).setTranslationKey("eggenchantedcow").setRegistryName("chaospersists", "eggenchantedcow");
    MOTHRAEgg = new ItemSpawnEgg(0,201).setTranslationKey("eggmothra").setRegistryName("chaospersists", "eggmothra");
    AloEgg = new ItemSpawnEgg(0,202).setTranslationKey("eggalosaurus").setRegistryName("chaospersists", "eggalosaurus");
    CryoEgg = new ItemSpawnEgg(0,203).setTranslationKey("eggcryolophosaurus").setRegistryName("chaospersists", "eggcryolophosaurus");
    CamaEgg = new ItemSpawnEgg(0,204).setTranslationKey("eggcamarasaurus").setRegistryName("chaospersists", "eggcamarasaurus");
    VeloEgg = new ItemSpawnEgg(0,205).setTranslationKey("eggvelocityraptor").setRegistryName("chaospersists", "eggvelocityraptor");
    HydroEgg = new ItemSpawnEgg(0,206).setTranslationKey("egghydrolisc").setRegistryName("chaospersists", "egghydrolisc");
    BasilEgg = new ItemSpawnEgg(0,207).setTranslationKey("eggbasilisc").setRegistryName("chaospersists", "eggbasilisc");
    DragonflyEgg = new ItemSpawnEgg(0,221).setTranslationKey("eggdragonfly").setRegistryName("chaospersists", "eggdragonfly");
    EmperorScorpionEgg = new ItemSpawnEgg(0,223).setTranslationKey("eggemperorscorpion").setRegistryName("chaospersists", "eggemperorscorpion");
    ScorpionEgg = new ItemSpawnEgg(0,225).setTranslationKey("eggscorpion").setRegistryName("chaospersists", "eggscorpion");
    CaveFisherEgg = new ItemSpawnEgg(0,227).setTranslationKey("eggcavefisher").setRegistryName("chaospersists", "eggcavefisher");
    SpyroEgg = new ItemSpawnEgg(0,229).setTranslationKey("eggspyro").setRegistryName("chaospersists", "eggspyro");
    BaryonyxEgg = new ItemSpawnEgg(0,231).setTranslationKey("eggbaryonyx").setRegistryName("chaospersists", "eggbaryonyx");
    GammaMetroidEgg = new ItemSpawnEgg(0,233).setTranslationKey("egggammametroid").setRegistryName("chaospersists", "egggammametroid");
    CockateilEgg = new ItemSpawnEgg(0,235).setTranslationKey("eggcockateil").setRegistryName("chaospersists", "eggcockateil");
    KyuubiEgg = new ItemSpawnEgg(0,237).setTranslationKey("eggkyuubi").setRegistryName("chaospersists", "eggkyuubi");
    AlienEgg = new ItemSpawnEgg(0,239).setTranslationKey("eggalien").setRegistryName("chaospersists", "eggalien");
    AttackSquidEgg = new ItemSpawnEgg(0,241).setTranslationKey("eggattacksquid").setRegistryName("chaospersists", "eggattacksquid");
    WaterDragonEgg = new ItemSpawnEgg(0,243).setTranslationKey("eggwaterdragon").setRegistryName("chaospersists", "eggwaterdragon");
    CephadromeEgg = new ItemSpawnEgg(0,249).setTranslationKey("eggcephadrome").setRegistryName("chaospersists", "eggcephadrome");
    KrakenEgg = new ItemSpawnEgg(0,245).setTranslationKey("eggkraken").setRegistryName("chaospersists", "eggkraken");
    LizardEgg = new ItemSpawnEgg(0,247).setTranslationKey("egglizard").setRegistryName("chaospersists", "egglizard");
    DragonEgg = new ItemSpawnEgg(0,251).setTranslationKey("eggdragon").setRegistryName("chaospersists", "eggdragon");
    BeeEgg = new ItemSpawnEgg(0,254).setTranslationKey("eggbee").setRegistryName("chaospersists", "eggbee");
    TrooperBugEgg = new ItemSpawnEgg(0,262).setTranslationKey("eggtrooper").setRegistryName("chaospersists", "eggtrooper");
    SpitBugEgg = new ItemSpawnEgg(0,263).setTranslationKey("eggspit").setRegistryName("chaospersists", "eggspit");
    StinkBugEgg = new ItemSpawnEgg(0,264).setTranslationKey("eggstink").setRegistryName("chaospersists", "eggstink");
    OstrichEgg = new ItemSpawnEgg(0,265).setTranslationKey("eggostrich").setRegistryName("chaospersists", "eggostrich");
    GazelleEgg = new ItemSpawnEgg(0,266).setTranslationKey("egggazelle").setRegistryName("chaospersists", "egggazelle");
    ChipmunkEgg = new ItemSpawnEgg(0,267).setTranslationKey("eggchipmunk").setRegistryName("chaospersists", "eggchipmunk");
    CreepingHorrorEgg = new ItemSpawnEgg(0,274).setTranslationKey("eggcreepinghorror").setRegistryName("chaospersists", "eggcreepinghorror");
    TerribleTerrorEgg = new ItemSpawnEgg(0,275).setTranslationKey("eggterribleterror").setRegistryName("chaospersists", "eggterribleterror");
    CliffRacerEgg = new ItemSpawnEgg(0,276).setTranslationKey("eggcliffracer").setRegistryName("chaospersists", "eggcliffracer");
    TriffidEgg = new ItemSpawnEgg(0,277).setTranslationKey("eggtriffid").setRegistryName("chaospersists", "eggtriffid");
    PitchBlackEgg = new ItemSpawnEgg(0,278).setTranslationKey("eggnightmare").setRegistryName("chaospersists", "eggnightmare");
    LurkingTerrorEgg = new ItemSpawnEgg(0,279).setTranslationKey("egglurkingterror").setRegistryName("chaospersists", "egglurkingterror");
    GodzillaEgg = new ItemSpawnEgg(0,280).setTranslationKey("egggodzilla").setRegistryName("chaospersists", "egggodzilla");
    SmallWormEgg = new ItemSpawnEgg(0,288).setTranslationKey("eggsmallworm").setRegistryName("chaospersists", "eggsmallworm");
    MediumWormEgg = new ItemSpawnEgg(0,289).setTranslationKey("eggmediumworm").setRegistryName("chaospersists", "eggmediumworm");
    LargeWormEgg = new ItemSpawnEgg(0,290).setTranslationKey("egglargeworm").setRegistryName("chaospersists", "egglargeworm");
    CassowaryEgg = new ItemSpawnEgg(0,291).setTranslationKey("eggcassowary").setRegistryName("chaospersists", "eggcassowary");
    CloudSharkEgg = new ItemSpawnEgg(0,292).setTranslationKey("eggcloudshark").setRegistryName("chaospersists", "eggcloudshark");
    GoldFishEgg = new ItemSpawnEgg(0,293).setTranslationKey("egggoldfish").setRegistryName("chaospersists", "egggoldfish");
    LeafMonsterEgg = new ItemSpawnEgg(0,294).setTranslationKey("eggleafmonster").setRegistryName("chaospersists", "eggleafmonster");
    TshirtEgg = new ItemSpawnEgg(0,295).setTranslationKey("eggtshirt").setRegistryName("chaospersists", "eggtshirt");
    EnderKnightEgg = new ItemSpawnEgg(0,298).setTranslationKey("eggenderknight").setRegistryName("chaospersists", "eggenderknight");
    EnderReaperEgg = new ItemSpawnEgg(0,299).setTranslationKey("eggenderreaper").setRegistryName("chaospersists", "eggenderreaper");
    BeaverEgg = new ItemSpawnEgg(0,301).setTranslationKey("eggbeaver").setRegistryName("chaospersists", "eggbeaver");
    RotatorEgg = new ItemSpawnEgg(0,302).setTranslationKey("eggrotator").setRegistryName("chaospersists", "eggrotator");
    VortexEgg = new ItemSpawnEgg(0,303).setTranslationKey("eggvortex").setRegistryName("chaospersists", "eggvortex");
    PeacockEgg = new ItemSpawnEgg(0,304).setTranslationKey("eggpeacock").setRegistryName("chaospersists", "eggpeacock");
    FairyEgg = new ItemSpawnEgg(0,305).setTranslationKey("eggfairy").setRegistryName("chaospersists", "eggfairy");
    DungeonBeastEgg = new ItemSpawnEgg(0,306).setTranslationKey("eggdungeonbeast").setRegistryName("chaospersists", "eggdungeonbeast");
    RatEgg = new ItemSpawnEgg(0,307).setTranslationKey("eggrat").setRegistryName("chaospersists", "eggrat");
    FlounderEgg = new ItemSpawnEgg(0,308).setTranslationKey("eggflounder").setRegistryName("chaospersists", "eggflounder");
    WhaleEgg = new ItemSpawnEgg(0,309).setTranslationKey("eggwhale").setRegistryName("chaospersists", "eggwhale");
    IrukandjiEgg = new ItemSpawnEgg(0,310).setTranslationKey("eggirukandji").setRegistryName("chaospersists", "eggirukandji");
    SkateEgg = new ItemSpawnEgg(0,311).setTranslationKey("eggskate").setRegistryName("chaospersists", "eggskate");
    UrchinEgg = new ItemSpawnEgg(0,312).setTranslationKey("eggurchin").setRegistryName("chaospersists", "eggurchin");
    Robot1Egg = new ItemSpawnEgg(0,324).setTranslationKey("eggrobot1").setRegistryName("chaospersists", "eggrobot1");
    Robot2Egg = new ItemSpawnEgg(0,325).setTranslationKey("eggrobot2").setRegistryName("chaospersists", "eggrobot2");
    Robot3Egg = new ItemSpawnEgg(0,326).setTranslationKey("eggrobot3").setRegistryName("chaospersists", "eggrobot3");
    Robot4Egg = new ItemSpawnEgg(0,327).setTranslationKey("eggrobot4").setRegistryName("chaospersists", "eggrobot4");
    GhostEgg = new ItemSpawnEgg(0,328).setTranslationKey("eggghost").setRegistryName("chaospersists", "eggghost");
    GhostSkellyEgg = new ItemSpawnEgg(0,329).setTranslationKey("eggghostskelly").setRegistryName("chaospersists", "eggghostskelly");
    BrownAntEgg = new ItemSpawnEgg(0,330).setTranslationKey("eggbrownant").setRegistryName("chaospersists", "eggbrownant");
    RedAntEgg = new ItemSpawnEgg(0,331).setTranslationKey("eggredant").setRegistryName("chaospersists", "eggredant");
    RainbowAntEgg = new ItemSpawnEgg(0,332).setTranslationKey("eggrainbowant").setRegistryName("chaospersists", "eggrainbowant");
    UnstableAntEgg = new ItemSpawnEgg(0,333).setTranslationKey("eggunstableant").setRegistryName("chaospersists", "eggunstableant");
    TermiteEgg = new ItemSpawnEgg(0,334).setTranslationKey("eggtermite").setRegistryName("chaospersists", "eggtermite");
    ButterflyEgg = new ItemSpawnEgg(0,335).setTranslationKey("eggbutterfly").setRegistryName("chaospersists", "eggbutterfly");
    MothEgg = new ItemSpawnEgg(0,336).setTranslationKey("eggmoth").setRegistryName("chaospersists", "eggmoth");
    MosquitoEgg = new ItemSpawnEgg(0,337).setTranslationKey("eggmosquito").setRegistryName("chaospersists", "eggmosquito");
    FireflyEgg = new ItemSpawnEgg(0,338).setTranslationKey("eggfirefly").setRegistryName("chaospersists", "eggfirefly");
    TRexEgg = new ItemSpawnEgg(0,339).setTranslationKey("eggtrex").setRegistryName("chaospersists", "eggtrex");
    HerculesEgg = new ItemSpawnEgg(0,340).setTranslationKey("egghercules").setRegistryName("chaospersists", "egghercules");
    MantisEgg = new ItemSpawnEgg(0,341).setTranslationKey("eggmantis").setRegistryName("chaospersists", "eggmantis");
    StinkyEgg = new ItemSpawnEgg(0,342).setTranslationKey("eggstinky").setRegistryName("chaospersists", "eggstinky");
    Robot5Egg = new ItemSpawnEgg(0,343).setTranslationKey("eggrobot5").setRegistryName("chaospersists", "eggrobot5");
    CoinEgg = new ItemSpawnEgg(0,344).setTranslationKey("eggcoin").setRegistryName("chaospersists", "eggcoin");
    BoyfriendEgg = new ItemSpawnEgg(0,349).setTranslationKey("eggboyfriend").setRegistryName("chaospersists", "eggboyfriend");
    TheKingEgg = new ItemSpawnEgg(0,350).setTranslationKey("eggtheking").setRegistryName("chaospersists", "eggtheking");
    TheQueenEgg = new ItemSpawnEgg(0,366).setTranslationKey("eggthequeen").setRegistryName("chaospersists", "eggthequeen");
    ThePrinceEgg = new ItemSpawnEgg(0,351).setTranslationKey("eggtheprince").setRegistryName("chaospersists", "eggtheprince");
    EasterBunnyEgg = new ItemSpawnEgg(0,352).setTranslationKey("eggeasterbunny").setRegistryName("chaospersists", "eggeasterbunny");
    MolenoidEgg = new ItemSpawnEgg(0,353).setTranslationKey("eggmolenoid").setRegistryName("chaospersists", "eggmolenoid");
    SeaMonsterEgg = new ItemSpawnEgg(0,354).setTranslationKey("eggseamonster").setRegistryName("chaospersists", "eggseamonster");
    SeaViperEgg = new ItemSpawnEgg(0,355).setTranslationKey("eggseaviper").setRegistryName("chaospersists", "eggseaviper");
    CaterKillerEgg = new ItemSpawnEgg(0,356).setTranslationKey("eggcaterkiller").setRegistryName("chaospersists", "eggcaterkiller");
    RubberDuckyEgg = new ItemSpawnEgg(0,362).setTranslationKey("eggrubberducky").setRegistryName("chaospersists", "eggrubberducky");
    HammerheadEgg = new ItemSpawnEgg(0,360).setTranslationKey("egghammerhead").setRegistryName("chaospersists", "egghammerhead");
    LeonEgg = new ItemSpawnEgg(0,358).setTranslationKey("eggleon").setRegistryName("chaospersists", "eggleon");
    CriminalEgg = new ItemSpawnEgg(0,365).setTranslationKey("eggcriminal").setRegistryName("chaospersists", "eggcriminal");
    BrutalflyEgg = new ItemSpawnEgg(0,367).setTranslationKey("eggbrutalfly").setRegistryName("chaospersists", "eggbrutalfly");
    NastysaurusEgg = new ItemSpawnEgg(0,368).setTranslationKey("eggnastysaurus").setRegistryName("chaospersists", "eggnastysaurus");
    PointysaurusEgg = new ItemSpawnEgg(0,369).setTranslationKey("eggpointysaurus").setRegistryName("chaospersists", "eggpointysaurus");
    CricketEgg = new ItemSpawnEgg(0,370).setTranslationKey("eggcricket").setRegistryName("chaospersists", "eggcricket");
    ThePrincessEgg = new ItemSpawnEgg(0,371).setTranslationKey("eggtheprincess").setRegistryName("chaospersists", "eggtheprincess");
    FrogEgg = new ItemSpawnEgg(0,372).setTranslationKey("eggfrog").setRegistryName("chaospersists", "eggfrog");
    JefferyEgg = new ItemSpawnEgg(0,378).setTranslationKey("eggrobot6").setRegistryName("chaospersists", "eggrobot6");
    AntRobotEgg = new ItemSpawnEgg(0,379).setTranslationKey("eggantrobot").setRegistryName("chaospersists", "eggantrobot");
    SpiderRobotEgg = new ItemSpawnEgg(0,380).setTranslationKey("eggspiderrobot").setRegistryName("chaospersists", "eggspiderrobot");
    SpiderDriverEgg = new ItemSpawnEgg(0,381).setTranslationKey("eggspiderdriver").setRegistryName("chaospersists", "eggspiderdriver");
    CrabEgg = new ItemSpawnEgg(0,383).setTranslationKey("eggcrab").setRegistryName("chaospersists", "eggcrab");
  }

  private void DoDispenserRegistrations()
  {
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(LizardEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(WitherSkeletonEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(EnderDragonEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SnowGolemEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(IronGolemEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(WitherBossEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(GirlfriendEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BoyfriendEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TheKingEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TheQueenEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ThePrinceEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(RedCowEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CrystalCowEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(GoldCowEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(EnchantedCowEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MOTHRAEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(AloEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CryoEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CamaEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(VeloEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(HydroEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BasilEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(DragonflyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(EmperorScorpionEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ScorpionEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CaveFisherEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SpyroEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BaryonyxEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(GammaMetroidEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CockateilEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(KyuubiEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(AlienEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(AttackSquidEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(WaterDragonEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CephadromeEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(DragonEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(KrakenEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(LizardEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BeeEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TrooperBugEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SpitBugEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(StinkBugEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(OstrichEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(GazelleEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ChipmunkEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CreepingHorrorEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TerribleTerrorEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CliffRacerEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TriffidEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(PitchBlackEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(LurkingTerrorEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(GodzillaEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SmallWormEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MediumWormEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(LargeWormEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CassowaryEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CloudSharkEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(GoldFishEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(LeafMonsterEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TshirtEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(EnderKnightEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(EnderReaperEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BeaverEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(RotatorEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(VortexEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(PeacockEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(FairyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(DungeonBeastEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(RatEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(FlounderEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(WhaleEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(IrukandjiEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SkateEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(UrchinEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Robot1Egg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Robot2Egg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Robot3Egg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Robot4Egg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(GhostEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(GhostSkellyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BrownAntEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(RedAntEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(RainbowAntEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(UnstableAntEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TermiteEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ButterflyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MothEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MosquitoEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(FireflyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(TRexEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(HerculesEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MantisEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(StinkyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Robot5Egg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CoinEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(EasterBunnyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MolenoidEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SeaMonsterEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SeaViperEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CaterKillerEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(LeonEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(HammerheadEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(RubberDuckyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CriminalEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(BrutalflyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(NastysaurusEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(PointysaurusEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CricketEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ThePrincessEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(FrogEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(JefferyEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(AntRobotEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SpiderRobotEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(SpiderDriverEgg, new DispenserBehaviorChaosEgg());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(CrabEgg, new DispenserBehaviorChaosEgg());

    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyIrukandjiArrow, new MyDispenserBehaviorArrow());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyWaterBall, new MyDispenserBehaviorWDCharge());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MySunspotUrchin, new MyDispenserBehaviorSunspotUrchin());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyAcid, new MyDispenserBehaviorAcid());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyIceBall, new MyDispenserBehaviorIceball());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyIrukandji, new MyDispenserBehaviorDeadIrukandji());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyLaserBall, new MyDispenserBehaviorLaserball());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MySmallRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyRedRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyCrystalRedRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyCrystalGreenRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyCrystalBlueRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyCrystalTNTRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyBlueRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyGreenRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyPurpleRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MySpikeyRock, new MyDispenserBehaviorRock());
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(MyTNTRock, new MyDispenserBehaviorRock());
  }

  @Mod.EventHandler
  public void load(FMLInitializationEvent event)
  {
    applyChaosCreativeTabs();
    proxy.registerBlockColors();
    proxy.registerLeafColors();
    proxy.registerItemColors();
  }

  // ===== Creative Tabs Remap =====
  public static CreativeTabs tabChaosItems;
  public static CreativeTabs tabChaosBlocks;
  public static CreativeTabs tabChaosFoods;
  public static CreativeTabs tabChaosTools;
  public static CreativeTabs tabChaosWeapons;
  public static CreativeTabs tabChaosMobs;
  public static CreativeTabs tabChaosArmor;

  private static void ensureChaosCreativeTabs()
  {
    if (tabChaosItems != null) {
      return;
    }

    tabChaosItems = new CreativeTabs("chaos_items") {
      public ItemStack createIcon() {
        return new ItemStack(MinersDream);
      }
    };
    tabChaosBlocks = new CreativeTabs("chaos_blocks") {
      public ItemStack createIcon() {
        return new ItemStack(MyAntBlock);
      }
    };
    tabChaosFoods = new CreativeTabs("chaos_foods") {
      public ItemStack createIcon() {
        return new ItemStack(MyCornCob);
      }
    };
    tabChaosTools = new CreativeTabs("chaos_tools") {
      public ItemStack createIcon() {
        return new ItemStack(MyUltimatePickaxe);
      }
    };
    tabChaosWeapons = new CreativeTabs("chaos_weapons") {
      public ItemStack createIcon() {
        return new ItemStack(MyUltimateSword);
      }
    };
    tabChaosMobs = new CreativeTabs("chaos_mobs") {
      public ItemStack createIcon() {
        return new ItemStack(TheKingEgg);
      }
    };
    tabChaosArmor = new CreativeTabs("chaos_armor") {
      public ItemStack createIcon() {
        return new ItemStack(RoyalBody);
      }
    };
  }

  /**
   * Growth-stage crop blocks (lettuce_0..3, quinoa_0..3, *plant*, experiencesapling, etc.)
   * should not appear in Chaos tabs - only seed items should.
   */
  private static boolean isHiddenCropGrowthChaosBlockPath(String pathLower)
  {
    if (pathLower == null || pathLower.isEmpty()) {
      return false;
    }
    return pathLower.contains("plant")
        || pathLower.contains("sapling")
        || pathLower.startsWith("lettuce_")
        || pathLower.startsWith("quinoa_");
  }

  /**
   * Remaps all mod items/blocks onto Chaos creative tabs. Safe to call more than once (idempotent).
   */
  public static void applyChaosCreativeTabs()
  {
    ensureChaosCreativeTabs();

    // Put all chaospersists blocks into Chaos Blocks.
    for (Block block : Block.REGISTRY) {
      if (block == null) {
        continue;
      }
      ResourceLocation rl = block.getRegistryName();
      if (rl != null && "chaospersists".equals(rl.getNamespace())) {
        String path = rl.getPath();
        String lower = path == null ? "" : path.toLowerCase();
        // Don't show crop/plant/sapling blocks in Chaos Blocks; keep only their seed items.
        if (isHiddenCropGrowthChaosBlockPath(lower)) {
          continue;
        }
        block.setCreativeTab(tabChaosBlocks);
      }
    }

    // Put all chaospersists items into the requested tabs.
    for (Item item : Item.REGISTRY) {
      if (item == null) {
        continue;
      }
      ResourceLocation rl = item.getRegistryName();
      if (rl == null || !"chaospersists".equals(rl.getNamespace())) {
        continue;
      }

      String path = rl.getPath();
      String lower = path == null ? "" : path.toLowerCase();

      if (item instanceof ItemBlock) {
        // Hide plant/crop/sapling blocks from Chaos creative tabs.
        // Seeds remain because they are items, not block item forms.
        if (isHiddenCropGrowthChaosBlockPath(lower)) {
          item.setCreativeTab(null);
          continue;
        }

        item.setCreativeTab(tabChaosBlocks);
      } else if ("pizza".equals(lower)) {
        item.setCreativeTab(tabChaosFoods);
      } else if ("ducttape".equals(lower)) {
        item.setCreativeTab(tabChaosItems);
      } else if ("step_up".equals(lower) || "step_down".equals(lower) || "step_accross".equals(lower)) {
        item.setCreativeTab(tabChaosItems);
      } else if ("spiderrobotkit".equals(lower) || "antrobotkit".equals(lower)) {
        item.setCreativeTab(tabChaosItems);
      } else if (item instanceof ItemSpawnEgg) {
        item.setCreativeTab(tabChaosMobs);
      } else if (item instanceof ItemFood) {
        item.setCreativeTab(tabChaosFoods);
      } else if (item instanceof ItemArmor) {
        item.setCreativeTab(tabChaosArmor);
      } else {
        // Map vanilla tabs and item classes to Chaos Tools / Weapons. Must be idempotent: JEI (and any
        // second caller) re-runs this after tabs are already tabChaosTools/tabChaosWeapons — comparing
        // only to CreativeTabs.TOOLS/COMBAT would wrongly send everything to Chaos Items.
        CreativeTabs oldTab = item.getCreativeTab();
        ItemStack probe = new ItemStack(item);
        if (oldTab == tabChaosTools) {
          item.setCreativeTab(tabChaosTools);
        } else if (oldTab == tabChaosWeapons) {
          item.setCreativeTab(tabChaosWeapons);
        } else if (oldTab == CreativeTabs.TOOLS) {
          item.setCreativeTab(tabChaosTools);
        } else if (oldTab == CreativeTabs.COMBAT) {
          item.setCreativeTab(tabChaosWeapons);
        } else if (item instanceof ItemSword || item instanceof ItemBow) {
          item.setCreativeTab(tabChaosWeapons);
        } else if (item.getItemUseAction(probe) == EnumAction.BOW) {
          item.setCreativeTab(tabChaosWeapons);
        } else if (item instanceof ItemTool || item instanceof ItemHoe || item instanceof ItemFishingRod) {
          item.setCreativeTab(tabChaosTools);
        } else {
          item.setCreativeTab(tabChaosItems);
        }
      }
    }
  }

  /** Chaos Items tab (for JEI sub-item enumeration when an item has {@code creativeTab == null}). */
  public static CreativeTabs getChaosItemsCreativeTab()
  {
    ensureChaosCreativeTabs();
    return tabChaosItems;
  }

  @Mod.EventHandler
  public void serverStarting(FMLServerStartingEvent event)
  {
    event.registerServerCommand(new CommandUtopia());
    event.registerServerCommand(new CommandVillageMania());
    event.registerServerCommand(new CommandChaos());
    event.registerServerCommand(new CommandCrystal());
    event.registerServerCommand(new CommandDanger());
    event.registerServerCommand(new CommandMining());
  }

  @Mod.EventHandler
  public static void postInit(FMLPostInitializationEvent event)
  {
    BMaze = new BasiliskMaze();
    RubyDungeon = new RubyBirdDungeon();
    MyDungeon = new GenericDungeon();
    chaospersistsTrees = new Trees();
    chaospersistsUtils = new MyUtils();
    Chunker = new ChunkOreGenerator();
  }

  @SideOnly(Side.CLIENT)
  public Entity spawnEntity(int entityId, World world, double scaledX, double scaledY, double scaledZ)
  {
    return null;
  }

  public static Entity getPointedAtEntity(World world, EntityPlayer player, double dist) {
    Entity pointedAt = null;
    if (player != null)
    {
      if (world != null)
      {
        double d0 = dist;
        double d1 = dist;
        Vec3d vec3 = player.getPositionEyes(1.0F);
        Vec3d vec31 = player.getLook(1.0F);
        Vec3d vec32 = vec3.add(vec31.x * d0, vec31.y * d0, vec31.z * d0);
        pointedAt = null;
        float f1 = 1.0F;
        List list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().expand(vec31.x * d0, vec31.y * d0, vec31.z * d0).expand(f1, f1, f1));
        double d2 = d1;

        for (int i = 0; i < list.size(); i++)
        {
          Entity entity = (Entity)list.get(i);

          if (!entity.canBeCollidedWith())
            continue;
          float f2 = entity.getCollisionBorderSize();
          AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(f2, f2, f2);
          RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3, vec32);

          if (axisalignedbb.contains(vec3))
          {
            if ((0.0D >= d2) && (d2 != 0.0D))
              continue;
            pointedAt = entity;
            d2 = 0.0D;
          }
          else {
            if (raytraceresult == null)
              continue;
            double d3 = vec3.distanceTo(raytraceresult.hitVec);

            if ((d3 >= d2) && (d2 != 0.0D))
              continue;
            if ((entity == player.getRidingEntity()) && (!entity.canRiderInteract()))
            {
              if (d2 != 0.0D)
                continue;
              pointedAt = entity;
            }
            else
            {
              pointedAt = entity;
              d2 = d3;
            }
          }
        }
      }

    }

    return pointedAt;
  }

  public static boolean setBlockFast(World world, int par1, int par2, int par3, Block par4, int par5, int par6)
  {
    if ((par1 >= -30000000) && (par3 >= -30000000) && (par1 < 30000000) && (par3 < 30000000))
    {
      if (par2 < 0)
      {
        return false;
      }
      if (par2 >= 256)
      {
        return false;
      }

      Chunk chunk = world.getChunk(par1 >> 4, par3 >> 4);
      BlockPos pos = new BlockPos(par1, par2, par3);

      IBlockState oldState = Blocks.AIR.getDefaultState();
      if ((par6 & 0x1) != 0)
      {
        oldState = chunk.getBlockState(new BlockPos(par1 & 0xF, par2, par3 & 0xF));
      }

      boolean flag = setBlockIDWithMetadataFast(chunk, par1 & 0xF, par2, par3 & 0xF, par4, par5);

      if (flag)
      {
        if (((par6 & 0x2) != 0) && ((!world.isRemote) || ((par6 & 0x4) == 0)))
        {
          IBlockState newState = par4.getStateFromMeta(par5);
          world.notifyBlockUpdate(pos, oldState, newState, 3);
        }

        if ((!world.isRemote) && ((par6 & 0x1) != 0))
        {
          world.notifyNeighborsOfStateChange(pos, par4, true);
        }

        // Direct chunk writes skip vanilla lighting; without this, tall structures often render half-black (stale sky/block light).
        if (!world.isRemote)
        {
          if (world.provider.hasSkyLight())
          {
            world.checkLightFor(EnumSkyBlock.SKY, pos);
          }
          world.checkLightFor(EnumSkyBlock.BLOCK, pos);
        }

      }

      return flag;
    }

    return false;
  }

  public static boolean setBlockSuperFast(World world, int par1, int par2, int par3, Block par4, int par5, int par6, Chunk refChunk)
  {
    if ((par1 >= -30000000) && (par3 >= -30000000) && (par1 < 30000000) && (par3 < 30000000))
    {
      if (par2 < 0)
      {
        return false;
      }
      if (par2 >= 256)
      {
        return false;
      }

      Chunk chunk = world.getChunk(par1 >> 4, par3 >> 4);
      BlockPos pos = new BlockPos(par1, par2, par3);
      boolean flag = true;
      if (chunk != refChunk)
      {
        IBlockState oldState = Blocks.AIR.getDefaultState();
        if ((par6 & 0x1) != 0)
        {
          oldState = chunk.getBlockState(new BlockPos(par1 & 0xF, par2, par3 & 0xF));
        }

        flag = setBlockIDWithMetadataFast(chunk, par1 & 0xF, par2, par3 & 0xF, par4, par5);

        if (flag)
        {
          if (((par6 & 0x2) != 0) && ((!world.isRemote) || ((par6 & 0x4) == 0)))
          {
            IBlockState newState = par4.getStateFromMeta(par5);
            world.notifyBlockUpdate(pos, oldState, newState, 3);
          }

          if ((!world.isRemote) && ((par6 & 0x1) != 0))
          {
            world.notifyNeighborsOfStateChange(pos, par4, true);
          }

          if (!world.isRemote)
          {
            if (world.provider.hasSkyLight())
            {
              world.checkLightFor(EnumSkyBlock.SKY, pos);
            }
            world.checkLightFor(EnumSkyBlock.BLOCK, pos);
          }
        }
      }
      else {
        setBlockIDWithMetadataFast(chunk, par1 & 0xF, par2, par3 & 0xF, par4, par5);
        if (!world.isRemote)
        {
          if (world.provider.hasSkyLight())
          {
            world.checkLightFor(EnumSkyBlock.SKY, pos);
          }
          world.checkLightFor(EnumSkyBlock.BLOCK, pos);
        }
      }

      return flag;
    }

    return false;
  }

  public static boolean setBlockIDWithMetadataFast(Chunk chunk, int par1, int par2, int par3, Block par4, int par5) {
      if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000) {
          if (par2 < 0 || par2 > 255) {
              return false;
          }
          ExtendedBlockStorage[] mystorage = chunk.getBlockStorageArray();
          ExtendedBlockStorage extendedblockstorage = mystorage[par2 >> 4];
          if (extendedblockstorage == null) {
              if (par4 == Blocks.AIR) {
                  return false;
              }
              ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(par2 >> 4 << 4, chunk.getWorld().provider.hasSkyLight());
              mystorage[par2 >> 4] = extendedBlockStorage;
              extendedblockstorage = extendedBlockStorage;
          }
          extendedblockstorage.set(par1, par2 & 15, par3, par4.getStateFromMeta(par5));
          return true;
      }
      return false;
  }

  public static Block getBlockIDInChunk(Chunk chunk, int par1, int par2, int par3)
  {
    if ((par1 >= -30000000) && (par3 >= -30000000) && (par1 < 30000000) && (par3 < 30000000)) {
      if (par1 >> 4 != chunk.x) return Blocks.AIR;
      if (par3 >> 4 != chunk.z) return Blocks.AIR;
      if ((par2 < 0) || (par2 > 255)) return Blocks.AIR;
      return chunk.getBlockState(new BlockPos(par1 & 0xF, par2, par3 & 0xF)).getBlock();
    }
    return Blocks.AIR;
  }

  public static boolean setBlockIDWithMetadataInChunk(Chunk chunk, int par1, int par2, int par3, Block par4, int par5) {
      if (par1 >= -30000000 && par3 >= -30000000 && par1 < 30000000 && par3 < 30000000) {
          if (par1 >> 4 != chunk.x) {
              return false;
          }
          if (par3 >> 4 != chunk.z) {
              return false;
          }
          if (par2 < 0 || par2 > 255) {
              return false;
          }
          ExtendedBlockStorage[] mystorage = chunk.getBlockStorageArray();
          ExtendedBlockStorage extendedblockstorage = mystorage[par2 >> 4];
          par1 &= 15;
          par3 &= 15;
          if (extendedblockstorage == null) {
              if (par4 == Blocks.AIR || par4 == null) {
                  return false;
              }
              ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(par2 >> 4 << 4, chunk.getWorld().provider.hasSkyLight());
              mystorage[par2 >> 4] = extendedBlockStorage;
              extendedblockstorage = extendedBlockStorage;
          }
          extendedblockstorage.set(par1, par2 & 15, par3, par4.getStateFromMeta(par5));
          return true;
      }
      return false;
  }

  /**
   * Reads BaseDimensionID, optional per-world DimensionId_* overrides (-1 = BaseDimensionID + offset),
   * validates uniqueness, assigns DimensionID..DimensionID6, and optionally logs resolved IDs.
   */
  private static void configureDimensionIds(Configuration config, String ids) {
    Property logProp = config.get(ids, "LogRegisteredDimensionIds", true);
    logProp.setComment(
        "If true, logs every Chaos Persists dimension numeric ID at startup (INFO) so you can compare with other mods and fix collisions.");
    LogRegisteredDimensionIds = logProp.getBoolean();

    Property baseProp = config.get(ids, "BaseDimensionID", 80);
    baseProp.setComment(
        "First ID of the default contiguous block when a DimensionId_* entry is -1. Offsets: +0 Utopia, +1 Mining, +2 Village Mania, +3 Islands (danger), +4 Crystal, +5 Chaos. "
            + "Default 80 gives 80-85. If another mod already uses one of these numbers, raise BaseDimensionID (e.g. 100) or set explicit DimensionId_* below.");
    BaseDimensionID = baseProp.getInt();

    final String[] dimKeys = new String[] {
        "DimensionId_Utopia",
        "DimensionId_Mining",
        "DimensionId_VillageMania",
        "DimensionId_Islands",
        "DimensionId_Crystal",
        "DimensionId_Chaos"
    };
    final String[] dimLabels = new String[] {
        "Utopia (WorldProviderChaos)",
        "Mining (WorldProviderChaos2)",
        "Village Mania (WorldProviderChaos3)",
        "Islands / danger (WorldProviderChaos4)",
        "Crystal (WorldProviderChaos5)",
        "Chaos (WorldProviderChaos6)"
    };

    int[] resolved = new int[6];
    for (int i = 0; i < 6; i++) {
      Property p = config.get(ids, dimKeys[i], -1);
      p.setComment(
          "Numeric world ID for " + dimLabels[i] + ". Use -1 for automatic: BaseDimensionID+" + i + ". "
              + "Set a specific free ID to avoid conflicts (each Chaos Persists dimension must differ from every other mod).");
      int raw = p.getInt();
      resolved[i] = raw >= 0 ? raw : BaseDimensionID + i;
    }

    HashSet<Integer> seen = new HashSet<Integer>();
    for (int i = 0; i < 6; i++) {
      if (!seen.add(Integer.valueOf(resolved[i]))) {
        throw new IllegalStateException(
            "ChaosPersists: duplicate dimension ID "
                + resolved[i]
                + " in config category ["
                + ids
                + "]. Keys "
                + java.util.Arrays.toString(dimKeys)
                + " must all be unique (or -1 with distinct BaseDimensionID offsets).");
      }
    }

    DimensionID = resolved[0];
    DimensionID2 = resolved[1];
    DimensionID3 = resolved[2];
    DimensionID4 = resolved[3];
    DimensionID5 = resolved[4];
    DimensionID6 = resolved[5];

    if (LogRegisteredDimensionIds) {
      FMLLog.log.info("ChaosPersists dimension IDs (change in chaospersists.cfg -> [{}] if a mod conflicts):", ids);
      for (int i = 0; i < 6; i++) {
        FMLLog.log.info("  [{}] = {}  ({})", dimKeys[i], Integer.valueOf(resolved[i]), dimLabels[i]);
      }
    }
  }

  private ArmorStats get_armorstats(Configuration config, String s, int dura, int head, int chest, int leg, int boots, int enchant, int e_resp, int e_aqua, int e_prot, int e_fire, int e_blast, int e_proj, int e_unbreak, int e_feather)
  {
    ArmorStats a = new ArmorStats();
    String arm = "chaospersistsARMOR";

    a.durability = config.get(arm, s + "_durability", dura).getInt();
    if (a.durability < dura / 2) a.durability = (dura / 2);
    if (a.durability > dura * 2) a.durability = (dura * 2);
    a.head_protection = config.get(arm, s + "_head_damage_reduce", head).getInt();
    if (a.head_protection < head - 2) a.head_protection = (head - 2);
    a.chest_protection = config.get(arm, s + "_chest_damage_reduce", chest).getInt();
    if (a.chest_protection < chest - 2) a.chest_protection = (chest - 2);
    a.leg_protection = config.get(arm, s + "_leggings_damage_reduce", leg).getInt();
    if (a.leg_protection < leg - 2) a.leg_protection = (leg - 2);
    a.boot_protection = config.get(arm, s + "_boots_damage_reduce", boots).getInt();
    if (a.boot_protection < boots - 2) a.boot_protection = (boots - 2);
    a.enchantability = config.get(arm, s + "_enchantability", enchant).getInt();
    if (a.enchantability < enchant / 2) a.enchantability = (enchant / 2);
    if (a.enchantability > enchant * 2) a.enchantability = (enchant * 2);

    a.e_respiration = config.get(arm, s + "_enchant_respiration", e_resp).getInt();
    if (a.e_respiration < e_resp / 2) a.e_respiration = (e_resp / 2);
    a.e_aquaaffinity = config.get(arm, s + "_enchant_aquaaffinity", e_aqua).getInt();
    if (a.e_aquaaffinity < e_aqua / 2) a.e_aquaaffinity = (e_aqua / 2);
    a.e_protection = config.get(arm, s + "_enchant_protection", e_prot).getInt();
    if (a.e_protection < e_prot / 2) a.e_protection = (e_prot / 2);
    a.e_fireprotection = config.get(arm, s + "_enchant_fireprotection", e_fire).getInt();
    if (a.e_fireprotection < e_fire / 2) a.e_fireprotection = (e_fire / 2);
    a.e_blastprotection = config.get(arm, s + "_enchant_blastprotection", e_blast).getInt();
    if (a.e_blastprotection < e_blast / 2) a.e_blastprotection = (e_blast / 2);
    a.e_projectileprotection = config.get(arm, s + "_enchant_projectileprotection", e_proj).getInt();
    if (a.e_projectileprotection < e_proj / 2) a.e_projectileprotection = (e_proj / 2);
    a.e_unbreaking = config.get(arm, s + "_enchant_unbreaking", e_unbreak).getInt();
    if (a.e_unbreaking < e_unbreak / 2) a.e_unbreaking = (e_unbreak / 2);
    a.e_featherfalling = config.get(arm, s + "_enchant_featherfalling", e_feather).getInt();
    if (a.e_featherfalling < e_feather / 2) a.e_featherfalling = (e_feather / 2);

    return a;
  }

  private WeaponStats get_weaponstats(Configuration config, String arm, String s, int harvest, int maxuses, int efficiency, int damage, int enchantability)
  {
    WeaponStats w = new WeaponStats();

    w.harvestlevel = config.get(arm, s + "_harvestlevel", harvest).getInt();
    if (w.harvestlevel < harvest - 1) w.harvestlevel = harvest;
    w.maxuses = config.get(arm, s + "_maxuses", maxuses).getInt();
    if (w.maxuses < maxuses / 2) w.maxuses = (maxuses / 2);
    if (w.maxuses > maxuses * 2) w.maxuses = (maxuses * 2);
    w.efficiency = config.get(arm, s + "_efficiency", efficiency).getInt();
    if (w.efficiency < efficiency / 2) w.efficiency = (efficiency / 2);
    if (w.efficiency > efficiency * 2) w.efficiency = (efficiency * 2);
    w.damage = config.get(arm, s + "_damage", damage).getInt();
    if (w.damage < damage / 2) w.damage = (damage / 2);
    if (w.damage > damage * 2) w.damage = (damage * 2);
    w.enchantability = config.get(arm, s + "_enchantability", enchantability).getInt();
    if (w.enchantability < enchantability / 2) w.enchantability = (enchantability / 2);
    if (w.enchantability > enchantability * 2) w.enchantability = (enchantability * 2);

    return w;
  }

  private MobStats get_mobstats(Configuration config, String arm, String s, int health, int attack, int defense)
  {
    MobStats m = new MobStats();

    m.health = config.get(arm, s + "_health", health).getInt();
    if (m.health < health / 2) m.health = (health / 2);
    if (m.health > health * 2) m.health = (health * 2);
    m.attack = config.get(arm, s + "_attack", attack).getInt();
    if (m.attack < attack / 2) m.attack = (attack / 2);
    if (m.attack > attack * 2) m.attack = (attack * 2);
    m.defense = config.get(arm, s + "_defense", defense).getInt();
    if (m.defense < defense - 4) m.defense = (defense - 4);
    if (m.defense > defense + 4) m.defense = (defense + 4);
    if (m.defense > 22) m.defense = 22;
    if (m.defense < 0) m.defense = 0;

    return m;
  }

  private OreStats get_orestats(Configuration config, String arm, String s, int rate, int clumpsize, int min, int max)
  {
    OreStats o = new OreStats();

    o.rate = config.get(arm, s + "_rate", rate).getInt();
    if (o.rate < rate / 2) o.rate = (rate / 2);
    if (o.rate > rate * 2) o.rate = (rate * 2);
    o.clumpsize = config.get(arm, s + "_clumpsize", clumpsize).getInt();
    if (o.clumpsize < clumpsize / 2) o.clumpsize = (clumpsize / 2);
    if (o.clumpsize > clumpsize * 2) o.clumpsize = (clumpsize * 2);
    if (o.clumpsize < 1) o.clumpsize = 1;
    o.mindepth = config.get(arm, s + "_mindepth", min).getInt();
    if (o.mindepth < 0) o.mindepth = 0;
    o.maxdepth = config.get(arm, s + "_maxdepth", max).getInt();
    if (o.maxdepth < 0) o.maxdepth = 0;
    if (o.maxdepth - o.mindepth < 10) {
      o.mindepth = min;
      o.maxdepth = max;
    }
    return o;
  }

  private void disableAllMobs()
  {
    MosquitoEnable = 0;
    GhostEnable = 0;
    GhostSkellyEnable = 0;
    SpiderDriverEnable = 0;
    CrabEnable = 0;
    JefferyEnable = 0;
    MothraEnable = 0;
    BrutalflyEnable = 0;
    NastysaurusEnable = 0;
    PointysaurusEnable = 0;
    MothraPeaceful = 0;
    BlackAntEnable = 0;
    RedAntEnable = 0;
    TermiteEnable = 0;
    UnstableAntEnable = 0;
    RainbowAntEnable = 0;
    AlosaurusEnable = 0;
    HammerheadEnable = 0;
    LeonEnable = 0;
    CaterKillerEnable = 0;
    MolenoidEnable = 0;
    TRexEnable = 0;
    CriminalEnable = 0;
    CryolophosaurusEnable = 0;
    RatEnable = 0;
    UrchinEnable = 0;
    CamarasaurusEnable = 0;
    VelocityRaptorEnable = 0;
    HydroliscEnable = 0;
    SpyroEnable = 0;
    BaryonyxEnable = 0;
    CockateilEnable = 0;
    CassowaryEnable = 0;
    EasterBunnyEnable = 0;
    PeacockEnable = 0;
    KyuubiEnable = 0;
    CephadromeEnable = 0;
    DragonEnable = 0;
    GammaMetroidEnable = 0;
    BasiliskEnable = 0;
    DragonflyEnable = 0;
    EmperorScorpionEnable = 0;
    TrooperBugEnable = 0;
    SpitBugEnable = 0;
    StinkBugEnable = 0;
    ScorpionEnable = 0;
    CaveFisherEnable = 0;
    AlienEnable = 0;
    WaterDragonEnable = 0;
    SeaMonsterEnable = 0;
    SeaViperEnable = 0;
    AttackSquidEnable = 0;
    Robot1Enable = 0;
    Robot2Enable = 0;
    Robot3Enable = 0;
    Robot4Enable = 0;
    Robot5Enable = 0;
    RotatorEnable = 0;
    VortexEnable = 0;
    DungeonBeastEnable = 0;
    KrakenEnable = 0;
    LizardEnable = 0;
    RubberDuckyEnable = 0;
    GirlfriendEnable = 0;
    BoyfriendEnable = 0;
    FireflyEnable = 0;
    FairyEnable = 0;
    BeeEnable = 0;
    TheKingEnable = 0;
    TheQueenEnable = 0;
    MantisEnable = 0;
    StinkyEnable = 0;
    HerculesBeetleEnable = 0;
    ChipmunkEnable = 0;
    OstrichEnable = 0;
    GazelleEnable = 0;
    CowEnable = 0;
    ButterflyEnable = 0;
    MothEnable = 0;
    TshirtEnable = 0;
    CoinEnable = 0;
    CreepingHorrorEnable = 0;
    TerribleTerrorEnable = 0;
    CliffRacerEnable = 0;
    TriffidEnable = 0;
    WormEnable = 0;
    CloudSharkEnable = 0;
    GoldFishEnable = 0;
    LeafMonsterEnable = 0;
    EnderKnightEnable = 0;
    EnderReaperEnable = 0;
    BeaverEnable = 0;
    IrukandjiEnable = 0;
    SkateEnable = 0;
    WhaleEnable = 0;
    FlounderEnable = 0;
    PitchBlackEnable = 0;
    LurkingTerrorEnable = 0;
    GodzillaEnable = 0;
    CrabEnable = 0;
  }

  private void laySomeEggs()
  {
    MySpiderSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orespider").setRegistryName("chaospersists", "orespider");
    MyBatSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orebat").setRegistryName("chaospersists", "orebat");
    MyCowSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecow").setRegistryName("chaospersists", "orecow");
    MyPigSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orepig").setRegistryName("chaospersists", "orepig");
    MySquidSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oresquid").setRegistryName("chaospersists", "oresquid");
    MyChickenSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orechicken").setRegistryName("chaospersists", "orechicken");
    MyCreeperSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecreeper").setRegistryName("chaospersists", "orecreeper");
    MySkeletonSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreskeleton").setRegistryName("chaospersists", "oreskeleton");
    MyZombieSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orezombie").setRegistryName("chaospersists", "orezombie");
    MySlimeSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreslime").setRegistryName("chaospersists", "oreslime");
    MyGhastSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreghast").setRegistryName("chaospersists", "oreghast");
    MyZombiePigmanSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orezombiepigman").setRegistryName("chaospersists", "orezombiepigman");
    MyEndermanSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreenderman").setRegistryName("chaospersists", "oreenderman");
    MyCaveSpiderSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecavespider").setRegistryName("chaospersists", "orecavespider");
    MySilverfishSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oresilverfish").setRegistryName("chaospersists", "oresilverfish");
    MyMagmaCubeSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oremagmacube").setRegistryName("chaospersists", "oremagmacube");
    MyWitchSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orewitch").setRegistryName("chaospersists", "orewitch");
    MySheepSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oresheep").setRegistryName("chaospersists", "oresheep");
    MyWolfSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orewolf").setRegistryName("chaospersists", "orewolf");
    MyMooshroomSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oremooshroom").setRegistryName("chaospersists", "oremooshroom");
    MyOcelotSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreocelot").setRegistryName("chaospersists", "oreocelot");
    MyBlazeSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreblaze").setRegistryName("chaospersists", "oreblaze");
    MyWitherSkeletonSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orewitherskeleton").setRegistryName("chaospersists", "orewitherskeleton");
    MyEnderDragonSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreenderdragon").setRegistryName("chaospersists", "oreenderdragon");
    MySnowGolemSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oresnowgolem").setRegistryName("chaospersists", "oresnowgolem");
    MyIronGolemSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreirongolem").setRegistryName("chaospersists", "oreirongolem");
    MyWitherBossSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orewitherboss").setRegistryName("chaospersists", "orewitherboss");
    MyGirlfriendSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oregirlfriend").setRegistryName("chaospersists", "oregirlfriend");
    MyBoyfriendSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreboyfriend").setRegistryName("chaospersists", "oreboyfriend");
    MyRedCowSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreredcow").setRegistryName("chaospersists", "oreredcow");
    MyCrystalCowSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecrystalcow").setRegistryName("chaospersists", "orecrystalcow");
    MyVillagerSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orevillager").setRegistryName("chaospersists", "orevillager");
    MyGoldCowSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oregoldcow").setRegistryName("chaospersists", "oregoldcow");
    MyEnchantedCowSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreenchantedcow").setRegistryName("chaospersists", "oreenchantedcow");
    MyMOTHRASpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oremothra").setRegistryName("chaospersists", "oremothra");
    MyAntBlock = new AntBlock(0).setTranslationKey("AntBlock").setRegistryName("chaospersists", "AntBlock");
    MyRedAntBlock = new AntBlock(0).setTranslationKey("RedAntBlock").setRegistryName("chaospersists", "RedAntBlock");
    TermiteBlock = new AntBlock(0).setTranslationKey("TermiteBlock").setRegistryName("chaospersists", "TermiteBlock");
    CrystalTermiteBlock = new CrystalAntBlock(0).setTranslationKey("CrystalTermiteBlock").setRegistryName("chaospersists", "CrystalTermiteBlock");
    MyRainbowAntBlock = new AntBlock(0).setTranslationKey("RainbowAntBlock").setRegistryName("chaospersists", "RainbowAntBlock");
    MyUnstableAntBlock = new AntBlock(0).setTranslationKey("UnstableAntBlock").setRegistryName("chaospersists", "UnstableAntBlock");
    MyAloSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orealosaurus").setRegistryName("chaospersists", "orealosaurus");
    MyCryoSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecryolophosaurus").setRegistryName("chaospersists", "orecryolophosaurus");
    MyCamaSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecamarasaurus").setRegistryName("chaospersists", "orecamarasaurus");
    MyVeloSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orevelocityraptor").setRegistryName("chaospersists", "orevelocityraptor");
    MyHydroSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orehydrolisc").setRegistryName("chaospersists", "orehydrolisc");
    MyBasilSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orebasilisc").setRegistryName("chaospersists", "orebasilisc");
    MyDragonflySpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oredragonfly").setRegistryName("chaospersists", "oredragonfly");
    MyEmperorScorpionSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreemperorscorpion").setRegistryName("chaospersists", "oreemperorscorpion");
    MyScorpionSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orescorpion").setRegistryName("chaospersists", "orescorpion");
    MyCaveFisherSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecavefisher").setRegistryName("chaospersists", "orecavefisher");
    MySpyroSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orespyro").setRegistryName("chaospersists", "orespyro");
    MyBaryonyxSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orebaryonyx").setRegistryName("chaospersists", "orebaryonyx");
    MyGammaMetroidSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oregammametroid").setRegistryName("chaospersists", "oregammametroid");
    MyCockateilSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecockateil").setRegistryName("chaospersists", "orecockateil");
    MyKyuubiSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orekyuubi").setRegistryName("chaospersists", "orekyuubi");
    MyAlienSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orealien").setRegistryName("chaospersists", "orealien");
    MyAttackSquidSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreattacksquid").setRegistryName("chaospersists", "oreattacksquid");
    MyWaterDragonSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orewaterdragon").setRegistryName("chaospersists", "orewaterdragon");
    MyCephadromeSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecephadrome").setRegistryName("chaospersists", "orecephadrome");
    MyDragonSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oredragon").setRegistryName("chaospersists", "oredragon");
    MyKrakenSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orekraken").setRegistryName("chaospersists", "orekraken");
    MyLizardSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orelizard").setRegistryName("chaospersists", "orelizard");
    MyBeeSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orebee").setRegistryName("chaospersists", "orebee");
    MyHorseSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orehorse").setRegistryName("chaospersists", "orehorse");
    MyTrooperBugSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oretrooper").setRegistryName("chaospersists", "oretrooper");
    MySpitBugSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orespit").setRegistryName("chaospersists", "orespit");
    MyStinkBugSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orestink").setRegistryName("chaospersists", "orestink");
    MyOstrichSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreostrich").setRegistryName("chaospersists", "oreostrich");
    MyGazelleSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oregazelle").setRegistryName("chaospersists", "oregazelle");
    MyChipmunkSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orechipmunk").setRegistryName("chaospersists", "orechipmunk");
    MyCreepingHorrorSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecreepinghorror").setRegistryName("chaospersists", "orecreepinghorror");
    MyTerribleTerrorSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreterribleterror").setRegistryName("chaospersists", "oreterribleterror");
    MyCliffRacerSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecliffracer").setRegistryName("chaospersists", "orecliffracer");
    MyTriffidSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oretriffid").setRegistryName("chaospersists", "oretriffid");
    MyPitchBlackSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orenightmare").setRegistryName("chaospersists", "orenightmare");
    MyLurkingTerrorSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orelurkingterror").setRegistryName("chaospersists", "orelurkingterror");
    MyGodzillaPartSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oregodzillapart").setRegistryName("chaospersists", "oregodzillapart");
    MyGodzillaSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oregodzilla").setRegistryName("chaospersists", "oregodzilla");
    MySmallWormSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oresmallworm").setRegistryName("chaospersists", "oresmallworm");
    MyMediumWormSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oremediumworm").setRegistryName("chaospersists", "oremediumworm");
    MyLargeWormSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orelargeworm").setRegistryName("chaospersists", "orelargeworm");
    MyCassowarySpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecassowary").setRegistryName("chaospersists", "orecassowary");
    MyCloudSharkSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecloudshark").setRegistryName("chaospersists", "orecloudshark");
    MyGoldFishSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oregoldfish").setRegistryName("chaospersists", "oregoldfish");
    MyLeafMonsterSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreleafmonster").setRegistryName("chaospersists", "oreleafmonster");
    MyTshirtSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oretshirt").setRegistryName("chaospersists", "oretshirt");
    MyEnderKnightSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreenderknight").setRegistryName("chaospersists", "oreenderknight");
    MyEnderReaperSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreenderreaper").setRegistryName("chaospersists", "oreenderreaper");
    MyBeaverSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orebeaver").setRegistryName("chaospersists", "orebeaver");
    MyUrchinSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreurchin").setRegistryName("chaospersists", "oreurchin");
    MyFlounderSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreflounder").setRegistryName("chaospersists", "oreflounder");
    MySkateSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreskate").setRegistryName("chaospersists", "oreskate");
    MyRotatorSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orerotator").setRegistryName("chaospersists", "orerotator");
    MyPeacockSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orepeacock").setRegistryName("chaospersists", "orepeacock");
    MyFairySpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orefairy").setRegistryName("chaospersists", "orefairy");
    MyDungeonBeastSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oredungeonbeast").setRegistryName("chaospersists", "oredungeonbeast");
    MyVortexSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orevortex").setRegistryName("chaospersists", "orevortex");
    MyRatSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orerat").setRegistryName("chaospersists", "orerat");
    MyWhaleSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orewhale").setRegistryName("chaospersists", "orewhale");
    MyIrukandjiSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreirukandji").setRegistryName("chaospersists", "oreirukandji");
    MyTRexSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oretrex").setRegistryName("chaospersists", "oretrex");
    MyHerculesSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orehercules").setRegistryName("chaospersists", "orehercules");
    MyMantisSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oremantis").setRegistryName("chaospersists", "oremantis");
    MyStinkySpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orestinky").setRegistryName("chaospersists", "orestinky");
    MyTheKingPartSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orethekingpart").setRegistryName("chaospersists", "orethekingpart");
    MyTheKingSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oretheking").setRegistryName("chaospersists", "oretheking");
    MyTheQueenPartSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orethequeenpart").setRegistryName("chaospersists", "orethequeenpart");
    MyTheQueenSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orethequeen").setRegistryName("chaospersists", "orethequeen");
    MyEasterBunnySpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreeasterbunny").setRegistryName("chaospersists", "oreeasterbunny");
    MyCaterKillerSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecaterkiller").setRegistryName("chaospersists", "orecaterkiller");
    MyMolenoidSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oremolenoid").setRegistryName("chaospersists", "oremolenoid");
    MySeaMonsterSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreseamonster").setRegistryName("chaospersists", "oreseamonster");
    MySeaViperSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreseaviper").setRegistryName("chaospersists", "oreseaviper");
    MyLeonSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("oreleon").setRegistryName("chaospersists", "oreleon");
    MyHammerheadSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orehammerhead").setRegistryName("chaospersists", "orehammerhead");
    MyRubberDuckySpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orerubberducky").setRegistryName("chaospersists", "orerubberducky");
    MyCriminalSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecriminal").setRegistryName("chaospersists", "orecriminal");
    MyBrutalflySpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orebrutalfly").setRegistryName("chaospersists", "orebrutalfly");
    MyNastysaurusSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orenastysaurus").setRegistryName("chaospersists", "orenastysaurus");
    MyPointysaurusSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orepointysaurus").setRegistryName("chaospersists", "orepointysaurus");
    MyCricketSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecricket").setRegistryName("chaospersists", "orecricket");
    MyFrogSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orefrog").setRegistryName("chaospersists", "orefrog");
    MySpiderDriverSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orespiderdriver").setRegistryName("chaospersists", "orespiderdriver");
    MyCrabSpawnBlock = (OreGenericEgg)(OreGenericEgg)new OreGenericEgg().setTranslationKey("orecrab").setRegistryName("chaospersists", "orecrab");
  }

  private void getMobs(Configuration config, String mobs)
  {
    MosquitoEnable = config.get(mobs, "MosquitoEnable", 1).getInt();
    RockEnable = config.get(mobs, "RockEnable", 1).getInt();
    GhostEnable = config.get(mobs, "GhostEnable", 1).getInt();
    GhostSkellyEnable = config.get(mobs, "GhostSkellyEnable", 1).getInt();
    SpiderDriverEnable = config.get(mobs, "SpiderDriverEnable", 1).getInt();
    JefferyEnable = config.get(mobs, "JefferyEnable", 1).getInt();
    MothraEnable = config.get(mobs, "MothraEnable", 1).getInt();
    BrutalflyEnable = config.get(mobs, "BrutalflyEnable", 1).getInt();
    NastysaurusEnable = config.get(mobs, "NastysaurusEnable", 1).getInt();
    PointysaurusEnable = config.get(mobs, "PointysaurusEnable", 1).getInt();
    CricketEnable = config.get(mobs, "CricketEnable", 1).getInt();
    FrogEnable = config.get(mobs, "FrogEnable", 1).getInt();
    MothraPeaceful = config.get(mobs, "MothraPeaceful", 0).getInt();
    BlackAntEnable = config.get(mobs, "BlackAntEnable", 1).getInt();
    RedAntEnable = config.get(mobs, "RedAntEnable", 1).getInt();
    TermiteEnable = config.get(mobs, "TermiteEnable", 1).getInt();
    UnstableAntEnable = config.get(mobs, "UnstableAntEnable", 1).getInt();
    RainbowAntEnable = config.get(mobs, "RainbowedAntEnable", 1).getInt();
    AlosaurusEnable = config.get(mobs, "AlosaurusEnable", 1).getInt();
    HammerheadEnable = config.get(mobs, "HammerheadEnable", 1).getInt();
    LeonEnable = config.get(mobs, "LeonEnable", 1).getInt();
    CaterKillerEnable = config.get(mobs, "CaterKillerEnable", 1).getInt();
    MolenoidEnable = config.get(mobs, "MolenoidEnable", 1).getInt();
    TRexEnable = config.get(mobs, "TRexEnable", 1).getInt();
    CriminalEnable = config.get(mobs, "CriminalEnable", 1).getInt();
    CryolophosaurusEnable = config.get(mobs, "CryolophosaurusEnable", 1).getInt();
    RatEnable = config.get(mobs, "RatEnable", 1).getInt();
    UrchinEnable = config.get(mobs, "UrchinEnable", 1).getInt();
    CamarasaurusEnable = config.get(mobs, "CamarasaurusEnable", 1).getInt();
    VelocityRaptorEnable = config.get(mobs, "VelocityRaptorEnable", 1).getInt();
    HydroliscEnable = config.get(mobs, "HydroliscEnable", 1).getInt();
    SpyroEnable = config.get(mobs, "SpyroEnable", 1).getInt();
    BaryonyxEnable = config.get(mobs, "BaryonyxEnable", 1).getInt();
    CockateilEnable = config.get(mobs, "BirdEnable", 1).getInt();
    CassowaryEnable = config.get(mobs, "CassowaryEnable", 1).getInt();
    EasterBunnyEnable = config.get(mobs, "EasterBunnyEnable", 1).getInt();
    PeacockEnable = config.get(mobs, "PeacockEnable", 1).getInt();
    KyuubiEnable = config.get(mobs, "KyuubiEnable", 1).getInt();
    CephadromeEnable = config.get(mobs, "CephadromeEnable", 1).getInt();
    DragonEnable = config.get(mobs, "DragonEnable", 1).getInt();
    GammaMetroidEnable = config.get(mobs, "GammaMetroidEnable", 1).getInt();
    BasiliskEnable = config.get(mobs, "BasiliskEnable", 1).getInt();
    DragonflyEnable = config.get(mobs, "DragonflyEnable", 1).getInt();
    EmperorScorpionEnable = config.get(mobs, "EmperorScorpionEnable", 1).getInt();
    TrooperBugEnable = config.get(mobs, "TrooperBugEnable", 1).getInt();
    SpitBugEnable = config.get(mobs, "SpitBugEnable", 1).getInt();
    StinkBugEnable = config.get(mobs, "StinkBugEnable", 1).getInt();
    ScorpionEnable = config.get(mobs, "ScorpionEnable", 1).getInt();
    CaveFisherEnable = config.get(mobs, "CaveFisherEnable", 1).getInt();
    AlienEnable = config.get(mobs, "AlienEnable", 1).getInt();
    WaterDragonEnable = config.get(mobs, "WaterDragonEnable", 1).getInt();
    SeaMonsterEnable = config.get(mobs, "SeaMonsterEnable", 1).getInt();
    SeaViperEnable = config.get(mobs, "SeaViperEnable", 1).getInt();
    AttackSquidEnable = config.get(mobs, "AttackSquidEnable", 1).getInt();
    Robot1Enable = config.get(mobs, "Robot1Enable", 1).getInt();
    Robot2Enable = config.get(mobs, "Robot2Enable", 1).getInt();
    Robot3Enable = config.get(mobs, "Robot3Enable", 1).getInt();
    Robot4Enable = config.get(mobs, "Robot4Enable", 1).getInt();
    Robot5Enable = config.get(mobs, "Robot5Enable", 1).getInt();
    RotatorEnable = config.get(mobs, "RotatorEnable", 1).getInt();
    VortexEnable = config.get(mobs, "VortexEnable", 1).getInt();
    DungeonBeastEnable = config.get(mobs, "DungeonBeastEnable", 1).getInt();
    KrakenEnable = config.get(mobs, "KrakenEnable", 1).getInt();
    LizardEnable = config.get(mobs, "LizardEnable", 1).getInt();
    RubberDuckyEnable = config.get(mobs, "RubberDuckyEnable", 1).getInt();
    GirlfriendEnable = config.get(mobs, "GirlfriendEnable", 1).getInt();
    BoyfriendEnable = config.get(mobs, "BoyfriendEnable", 0).getInt();
    FireflyEnable = config.get(mobs, "FireflyEnable", 1).getInt();
    FairyEnable = config.get(mobs, "FairyEnable", 1).getInt();
    BeeEnable = config.get(mobs, "BeeEnable", 1).getInt();
    TheKingEnable = config.get(mobs, "TheKingEnable", 1).getInt();
    TheQueenEnable = config.get(mobs, "TheQueenEnable", 1).getInt();
    MantisEnable = config.get(mobs, "MantisEnable", 1).getInt();
    StinkyEnable = config.get(mobs, "StinkyEnable", 1).getInt();
    HerculesBeetleEnable = config.get(mobs, "HerculesBeetleEnable", 1).getInt();
    ChipmunkEnable = config.get(mobs, "ChipmunkEnable", 1).getInt();
    OstrichEnable = config.get(mobs, "OstrichEnable", 1).getInt();
    GazelleEnable = config.get(mobs, "GazelleEnable", 1).getInt();
    CowEnable = config.get(mobs, "CowEnable", 1).getInt();
    ButterflyEnable = config.get(mobs, "ButterflyEnable", 1).getInt();
    MothEnable = config.get(mobs, "MothEnable", 1).getInt();
    TshirtEnable = config.get(mobs, "TshirtEnable", 1).getInt();
    CoinEnable = config.get(mobs, "CoinEnable", 1).getInt();
    CreepingHorrorEnable = config.get(mobs, "CreepingHorrorEnable", 1).getInt();
    TerribleTerrorEnable = config.get(mobs, "TerribleTerrorEnable", 1).getInt();
    CliffRacerEnable = config.get(mobs, "CliffRacerEnable", 1).getInt();
    TriffidEnable = config.get(mobs, "TriffidEnable", 1).getInt();
    WormEnable = config.get(mobs, "WormEnable", 1).getInt();
    CloudSharkEnable = config.get(mobs, "CloudSharkEnable", 1).getInt();
    GoldFishEnable = config.get(mobs, "GoldFishEnable", 1).getInt();
    LeafMonsterEnable = config.get(mobs, "LeafMonsterEnable", 1).getInt();
    EnderKnightEnable = config.get(mobs, "EnderKnightEnable", 1).getInt();
    EnderReaperEnable = config.get(mobs, "EnderReaperEnable", 1).getInt();
    BeaverEnable = config.get(mobs, "BeaverEnable", 1).getInt();
    IrukandjiEnable = config.get(mobs, "IrukandjiEnable", 1).getInt();
    SkateEnable = config.get(mobs, "SkateEnable", 1).getInt();
    WhaleEnable = config.get(mobs, "WhaleEnable", 1).getInt();
    FlounderEnable = config.get(mobs, "FlounderEnable", 1).getInt();
    PitchBlackEnable = config.get(mobs, "NightmareEnable", 1).getInt();
    LurkingTerrorEnable = config.get(mobs, "LurkingTerrorEnable", 1).getInt();
    GodzillaEnable = config.get(mobs, "GodzillaEnable", 1).getInt();
    CrabEnable = config.get(mobs, "CrabEnable", 1).getInt();

    Bee_stats = get_mobstats(config, mobs, "Bee", 80, 12, 5);
    Mantis_stats = get_mobstats(config, mobs, "Mantis", 120, 16, 10);
    HerculesBeetle_stats = get_mobstats(config, mobs, "HerculesBeetle", 250, 30, 19);
    Mothra_stats = get_mobstats(config, mobs, "Mothra", 150, 12, 8);
    Brutalfly_stats = get_mobstats(config, mobs, "Brutalfly", 110, 10, 6);
    Nastysaurus_stats = get_mobstats(config, mobs, "Nastysaurus", 200, 32, 17);
    Pointysaurus_stats = get_mobstats(config, mobs, "Pointysaurus", 80, 10, 16);
    Alosaurus_stats = get_mobstats(config, mobs, "Alosaurus", 110, 18, 8);
    SpiderRobot_stats = get_mobstats(config, mobs, "SpiderRobot", 1500, 100, 16);
    AntRobot_stats = get_mobstats(config, mobs, "AntRobot", 300, 30, 16);
    Jeffery_stats = get_mobstats(config, mobs, "Jeffery", 550, 40, 18);
    Hammerhead_stats = get_mobstats(config, mobs, "Hammerhead", 240, 75, 20);
    Molenoid_stats = get_mobstats(config, mobs, "Molenoid", 200, 18, 12);
    TRex_stats = get_mobstats(config, mobs, "TRex", 160, 22, 14);
    BandP_stats = get_mobstats(config, mobs, "BandP", 100, 1, 18);
    CaterKiller_stats = get_mobstats(config, mobs, "CaterKiller", 450, 32, 19);
    Cryolophosaurus_stats = get_mobstats(config, mobs, "Cryolophosaurus", 10, 3, 1);
    Rat_stats = get_mobstats(config, mobs, "Rat", 5, 3, 1);
    Urchin_stats = get_mobstats(config, mobs, "Urchin", 25, 10, 4);
    Kyuubi_stats = get_mobstats(config, mobs, "Kyuubi", 125, 10, 10);
    GammaMetroid_stats = get_mobstats(config, mobs, "GammaMetroid", 100, 10, 12);
    Basilisk_stats = get_mobstats(config, mobs, "Basilisk", 200, 24, 15);
    EmperorScorpion_stats = get_mobstats(config, mobs, "EmperorScorpion", 350, 35, 20);
    TrooperBug_stats = get_mobstats(config, mobs, "TrooperBug", 200, 20, 15);
    SpitBug_stats = get_mobstats(config, mobs, "SpitBug", 100, 10, 12);
    Alien_stats = get_mobstats(config, mobs, "Alien", 100, 12, 8);
    WaterDragon_stats = get_mobstats(config, mobs, "WaterDragon", 150, 20, 8);
    SeaMonster_stats = get_mobstats(config, mobs, "SeaMonster", 110, 14, 8);
    SeaViper_stats = get_mobstats(config, mobs, "SeaViper", 160, 22, 12);
    Robot2_stats = get_mobstats(config, mobs, "Robot2", 200, 22, 18);
    Robot3_stats = get_mobstats(config, mobs, "Robot3", 80, 16, 14);
    Robot4_stats = get_mobstats(config, mobs, "Robot4", 170, 12, 18);
    Robot5_stats = get_mobstats(config, mobs, "Robot5", 20, 5, 6);
    Rotator_stats = get_mobstats(config, mobs, "Rotator", 35, 10, 8);
    Vortex_stats = get_mobstats(config, mobs, "Vortex", 150, 26, 10);
    DungeonBeast_stats = get_mobstats(config, mobs, "DungeonBeast", 65, 12, 6);
    Triffid_stats = get_mobstats(config, mobs, "Triffid", 100, 20, 12);
    LurkingTerror_stats = get_mobstats(config, mobs, "LurkingTerror", 30, 6, 5);
    WormSmall_stats = get_mobstats(config, mobs, "WormSmall", 10, 3, 0);
    WormMedium_stats = get_mobstats(config, mobs, "WormMedium", 30, 10, 8);
    WormLarge_stats = get_mobstats(config, mobs, "WormLarge", 90, 18, 14);
    EnderKnight_stats = get_mobstats(config, mobs, "EnderKnight", 60, 12, 6);
    EnderReaper_stats = get_mobstats(config, mobs, "EnderReaper", 90, 18, 8);
    Irukandji_stats = get_mobstats(config, mobs, "Irukandji", 1, 20, 0);
    AttackSquid_stats = get_mobstats(config, mobs, "AttackSquid", 10, 8, 0);
    CaveFisher_stats = get_mobstats(config, mobs, "CaveFisher", 10, 4, 4);
    CloudShark_stats = get_mobstats(config, mobs, "CloudShark", 15, 6, 5);
    CreepingHorror_stats = get_mobstats(config, mobs, "CreepingHorror", 10, 3, 2);
    Godzilla_stats = get_mobstats(config, mobs, "Mobzilla", 4000, 175, 21);
    Kraken_stats = get_mobstats(config, mobs, "Kraken", 1000, 40, 10);
    LeafMonster_stats = get_mobstats(config, mobs, "LeafMonster", 6, 2, 1);
    PitchBlack_stats = get_mobstats(config, mobs, "Nightmare", 250, 30, 10);
    Scorpion_stats = get_mobstats(config, mobs, "Scorpion", 15, 4, 10);
    Skate_stats = get_mobstats(config, mobs, "Skate", 8, 8, 4);
    TerribleTerror_stats = get_mobstats(config, mobs, "TerribleTerror", 10, 5, 3);
    TheKing_stats = get_mobstats(config, mobs, "TheKing", 7000, 350, 21);
    TheQueen_stats = get_mobstats(config, mobs, "TheQueen", 6000, 225, 21);
    Leon_stats = get_mobstats(config, mobs, "Leonopteryx", 150, 20, 8);
    Crab_stats = get_mobstats(config, mobs, "Crab", 180, 24, 16);
  }

  public String getVersion()
  {
    return "1.7.10.20.3";
  }
}
