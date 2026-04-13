package com.astryxion.chaospersists.world.biome;

import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.entity.Gazelle;
import com.astryxion.chaospersists.entity.EntityButterfly;
import com.astryxion.chaospersists.entity.Cockateil;
import com.astryxion.chaospersists.entity.RedCow;
import com.astryxion.chaospersists.entity.GoldCow;
import com.astryxion.chaospersists.entity.EnchantedCow;
import com.astryxion.chaospersists.entity.EntityLunaMoth;
import com.astryxion.chaospersists.entity.Firefly;
import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.entity.Stinky;
import com.astryxion.chaospersists.entity.CliffRacer;
import com.astryxion.chaospersists.entity.CloudShark;
import com.astryxion.chaospersists.entity.GoldFish;
import com.astryxion.chaospersists.entity.CreepingHorror;
import com.astryxion.chaospersists.entity.TerribleTerror;
import com.astryxion.chaospersists.entity.LurkingTerror;
import com.astryxion.chaospersists.entity.PitchBlack;
import com.astryxion.chaospersists.entity.LeafMonster;
import com.astryxion.chaospersists.entity.EnderReaper;
import com.astryxion.chaospersists.entity.HerculesBeetle;
import com.astryxion.chaospersists.entity.CrystalCow;
import com.astryxion.chaospersists.entity.Fairy;
import com.astryxion.chaospersists.entity.Peacock;
import com.astryxion.chaospersists.entity.Mantis;
import com.astryxion.chaospersists.entity.Rotator;
import com.astryxion.chaospersists.entity.Vortex;
import com.astryxion.chaospersists.entity.Urchin;
import com.astryxion.chaospersists.entity.DungeonBeast;
import com.astryxion.chaospersists.entity.Rat;
import com.astryxion.chaospersists.entity.Whale;
import com.astryxion.chaospersists.entity.Crab;
import com.astryxion.chaospersists.entity.Flounder;
import com.astryxion.chaospersists.entity.Irukandji;
import com.astryxion.chaospersists.entity.Skate;
import com.astryxion.chaospersists.entity.Frog;
import com.astryxion.chaospersists.entity.Robot1;
import com.astryxion.chaospersists.entity.Robot2;
import com.astryxion.chaospersists.entity.Robot3;
import com.astryxion.chaospersists.entity.Robot4;
import com.astryxion.chaospersists.entity.Robot5;
import com.astryxion.chaospersists.entity.GiantRobot;
import com.astryxion.chaospersists.entity.SpiderDriver;
import com.astryxion.chaospersists.entity.Godzilla;
import com.astryxion.chaospersists.entity.Girlfriend;
import com.astryxion.chaospersists.entity.Boyfriend;
import com.astryxion.chaospersists.entity.Chipmunk;
import com.astryxion.chaospersists.entity.Cricket;
import com.astryxion.chaospersists.item.Tshirt;
import com.astryxion.chaospersists.item.Coin;
import com.astryxion.chaospersists.item.BandP;
import com.astryxion.chaospersists.entity.Baryonyx;
import com.astryxion.chaospersists.entity.Bee;
import com.astryxion.chaospersists.entity.CaterKiller;
import com.astryxion.chaospersists.entity.CaveFisher;
import com.astryxion.chaospersists.entity.Cryolophosaurus;
import com.astryxion.chaospersists.entity.EmperorScorpion;
import com.astryxion.chaospersists.entity.EnderKnight;
import com.astryxion.chaospersists.entity.Hammerhead;
import com.astryxion.chaospersists.entity.TrooperBug;
import com.astryxion.chaospersists.entity.Molenoid;
import com.astryxion.chaospersists.entity.Mothra;
import com.astryxion.chaospersists.entity.Brutalfly;
import com.astryxion.chaospersists.entity.Scorpion;
import com.astryxion.chaospersists.entity.SpitBug;
import com.astryxion.chaospersists.entity.Nastysaurus;
import com.astryxion.chaospersists.entity.TRex;
import com.astryxion.chaospersists.entity.Pointysaurus;
import com.astryxion.chaospersists.entity.Leon;
import com.astryxion.chaospersists.entity.GammaMetroid;
import com.astryxion.chaospersists.entity.Cassowary;
import com.astryxion.chaospersists.entity.Dragonfly;
import com.astryxion.chaospersists.entity.StinkBug;
import com.astryxion.chaospersists.entity.Ostrich;
import com.astryxion.chaospersists.entity.Beaver;
import com.astryxion.chaospersists.entity.Alosaurus;
import com.astryxion.chaospersists.entity.Basilisk;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenUtopianPlains
extends Biome {
    /**
     * Registered Utopia biome ({@code BiomeGenUtopianPlains(BiomeUtopiaID)}). OreSpawn 1.7.10 only adds spawns inside
     * {@code if (OreSpawnMain.*Enable != 0)} — the 1.12 port had mistakenly added the full table here unconditionally,
     * which ignores config and massively outpaces the mining dimension (small pack sizes there).
     */
    public BiomeGenUtopianPlains(int par1) {
        super(new Biome.BiomeProperties("Utopia").setTemperature(0.7f).setRainfall(0.5f).setWaterColor(353825).setBaseHeight(0.125f).setHeightVariation(0.05f));
        this.spawnableCreatureList = new ArrayList<>();
        this.spawnableMonsterList = new ArrayList<>();
        this.spawnableCaveCreatureList = new ArrayList<>();
        this.spawnableWaterCreatureList = new ArrayList<>();
        this.addUtopiaPlainsSpawnEntries();
        this.decorator.treesPerChunk = -999;
        this.decorator.flowersPerChunk = 4;
        this.decorator.grassPerChunk = 6;
    }

    /**
     * OreSpawn 1.7.10 {@code BiomeGenUtopianPlains(int)} spawn table — weights and min/max group sizes match
     * {@code src 1.7.10/.../BiomeGenUtopianPlains.java} constructor. Village Mania uses the same base via {@link #setVillageCreatures()}.
     */
    private void addUtopiaPlainsSpawnEntries() {
        if (ChaosPersists.GazelleEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(Gazelle.class, 10, 2, 4));
        }
        if (ChaosPersists.FireflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Firefly.class, 15, 3, 6));
        }
        if (ChaosPersists.GirlfriendEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(Girlfriend.class, 5, 2, 3));
        }
        if (ChaosPersists.BoyfriendEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(Boyfriend.class, 5, 2, 3));
        }
        if (ChaosPersists.CowEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(RedCow.class, 10, 4, 8));
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(GoldCow.class, 8, 2, 6));
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EnchantedCow.class, 5, 2, 4));
        }
        if (ChaosPersists.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 20, 3, 6));
        }
        if (ChaosPersists.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityLunaMoth.class, 10, 1, 5));
        }
        if (ChaosPersists.ChipmunkEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Chipmunk.class, 3, 1, 2));
        }
        if (ChaosPersists.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cockateil.class, 10, 2, 4));
        }
        if (ChaosPersists.GoldFishEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(GoldFish.class, 1, 1, 1));
        }
        if (ChaosPersists.WhaleEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Whale.class, 1, 1, 1));
        }
        if (ChaosPersists.FlounderEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Flounder.class, 2, 2, 4));
        }
        if (ChaosPersists.CoinEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Coin.class, 2, 1, 1));
        }
        if (ChaosPersists.CricketEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cricket.class, 5, 4, 6));
        }
        if (ChaosPersists.FrogEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Frog.class, 5, 4, 6));
        }
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
        return null;
    }

    public BiomeGenUtopianPlains(int par1, String name, int color, float temp, float rainfall) {
        super(new Biome.BiomeProperties(name).setTemperature(temp).setRainfall(rainfall).setWaterColor(color).setBaseHeight(0.125f).setHeightVariation(0.05f));
    }

    public BiomeGenUtopianPlains(int par1, String name, int color, float temp, float rainfall, float baseHeight, float heightVariation) {
        super(new Biome.BiomeProperties(name).setTemperature(temp).setRainfall(rainfall).setWaterColor(color).setBaseHeight(baseHeight).setHeightVariation(heightVariation));
    }

    public void setIslandCreatures() {
        this.spawnableCreatureList = new ArrayList<>();
        this.spawnableMonsterList = new ArrayList<>();
        this.spawnableWaterCreatureList = new ArrayList<>();
        this.spawnableCaveCreatureList = new ArrayList<>();
        if (ChaosPersists.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 5, 2, 6));
        }
        if (ChaosPersists.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cockateil.class, 4, 1, 2));
        }
        if (ChaosPersists.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityLunaMoth.class, 5, 2, 4));
        }
        if (ChaosPersists.FireflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Firefly.class, 10, 4, 8));
        }
        if (ChaosPersists.DragonEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Dragon.class, 1, 1, 2));
        }
        if (ChaosPersists.StinkyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Stinky.class, 2, 1, 2));
        }
        if (ChaosPersists.CliffRacerEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(CliffRacer.class, 20, 3, 6));
        }
        if (ChaosPersists.CloudSharkEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(CloudShark.class, 1, 1, 1));
        }
        if (ChaosPersists.GoldFishEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(GoldFish.class, 5, 2, 4));
        }
        if (ChaosPersists.CreepingHorrorEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(CreepingHorror.class, 60, 4, 8));
        }
        if (ChaosPersists.TerribleTerrorEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(TerribleTerror.class, 25, 3, 6));
        }
        if (ChaosPersists.LurkingTerrorEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(LurkingTerror.class, 1, 1, 1));
        }
        if (ChaosPersists.PitchBlackEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(PitchBlack.class, 15, 3, 6));
        }
        if (ChaosPersists.LeafMonsterEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(LeafMonster.class, 35, 2, 4));
        }
        if (ChaosPersists.EnderReaperEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(EnderReaper.class, 25, 2, 4));
        }
        if (ChaosPersists.HerculesBeetleEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(HerculesBeetle.class, 5, 1, 2));
        }
    }

    public void setCrystalCreatures() {
        this.spawnableCreatureList = new ArrayList<>();
        this.spawnableMonsterList = new ArrayList<>();
        this.spawnableWaterCreatureList = new ArrayList<>();
        this.spawnableCaveCreatureList = new ArrayList<>();
        if (ChaosPersists.CowEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(CrystalCow.class, 1, 1, 4));
        }
        if (ChaosPersists.FairyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Fairy.class, 10, 4, 8));
        }
        if (ChaosPersists.PeacockEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Peacock.class, 5, 4, 8));
        }
        if (ChaosPersists.MantisEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Mantis.class, 1, 1, 1));
        }
        if (ChaosPersists.RotatorEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Rotator.class, 4, 1, 2));
        }
        if (ChaosPersists.VortexEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Vortex.class, 3, 1, 2));
        }
        if (ChaosPersists.UrchinEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Urchin.class, 15, 2, 4));
        }
        if (ChaosPersists.DungeonBeastEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(DungeonBeast.class, 30, 4, 6));
        }
        if (ChaosPersists.RatEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Rat.class, 40, 4, 6));
        }
        if (ChaosPersists.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 10, 2, 4));
        }
        if (ChaosPersists.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cockateil.class, 4, 1, 2));
        }
        if (ChaosPersists.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityLunaMoth.class, 4, 1, 2));
        }
        if (ChaosPersists.WhaleEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Whale.class, 1, 1, 2));
        }
        if (ChaosPersists.CrabEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Crab.class, 1, 1, 2));
        }
        if (ChaosPersists.FlounderEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Flounder.class, 5, 6, 8));
        }
        if (ChaosPersists.IrukandjiEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Irukandji.class, 4, 2, 3));
        }
        if (ChaosPersists.SkateEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Skate.class, 2, 3, 6));
        }
        if (ChaosPersists.FrogEnable != 0) {
            this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(Frog.class, 1, 3, 5));
        }
        this.decorator.flowersPerChunk = -999;
        this.decorator.grassPerChunk = -999;
        this.decorator.treesPerChunk = -999;
        this.decorator.bigMushroomsPerChunk = -999;
        this.decorator.mushroomsPerChunk = -999;
        this.decorator.reedsPerChunk = -999;
    }

    /**
     * Village Mania dimension (1.7.10): same biome instance first gets {@link #addUtopiaPlainsSpawnEntries()} then these entries — we rebuild lists to match that cumulative table.
     */
    public void setVillageCreatures() {
        this.spawnableCreatureList = new ArrayList<>();
        this.spawnableMonsterList = new ArrayList<>();
        this.spawnableWaterCreatureList = new ArrayList<>();
        this.spawnableCaveCreatureList = new ArrayList<>();
        this.addUtopiaPlainsSpawnEntries();
        this.decorator.treesPerChunk = -999;
        this.decorator.flowersPerChunk = 4;
        this.decorator.grassPerChunk = 6;
        if (ChaosPersists.Robot1Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot1.class, 25, 4, 8));
        }
        if (ChaosPersists.Robot2Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot2.class, 16, 2, 8));
        }
        if (ChaosPersists.Robot3Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot3.class, 12, 2, 4));
        }
        if (ChaosPersists.Robot4Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot4.class, 8, 1, 2));
        }
        if (ChaosPersists.Robot5Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot5.class, 20, 4, 8));
        }
        if (ChaosPersists.JefferyEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(GiantRobot.class, 8, 1, 2));
        }
        if (ChaosPersists.SpiderDriverEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(SpiderDriver.class, 20, 3, 5));
        }
        if (ChaosPersists.GodzillaEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Godzilla.class, 2, 1, 1));
        }
        if (ChaosPersists.FireflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Firefly.class, 10, 3, 6));
        }
        if (ChaosPersists.GirlfriendEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(Girlfriend.class, 1, 2, 3));
        }
        if (ChaosPersists.BoyfriendEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(Boyfriend.class, 1, 2, 3));
        }
        if (ChaosPersists.CowEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(RedCow.class, 8, 4, 8));
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(GoldCow.class, 6, 2, 6));
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EnchantedCow.class, 4, 2, 4));
        }
        if (ChaosPersists.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 25, 3, 6));
        }
        if (ChaosPersists.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityLunaMoth.class, 20, 1, 5));
        }
        if (ChaosPersists.ChipmunkEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Chipmunk.class, 5, 1, 2));
        }
        if (ChaosPersists.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cockateil.class, 15, 2, 4));
        }
        if (ChaosPersists.TshirtEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Tshirt.class, 2, 1, 1));
        }
        if (ChaosPersists.CoinEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Coin.class, 2, 1, 1));
        }
        if (ChaosPersists.CriminalEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(BandP.class, 15, 1, 2));
        }
    }

    /**
     * OreSpawn 1.7.10 {@code BiomeGenUtopianPlains#setChaosCreatures()} (dimension 6). Weights and min/max groups match 1.7.10; water list stays empty.
     */
    public void setChaosCreatures() {
        this.spawnableCreatureList = new ArrayList<>();
        this.spawnableMonsterList = new ArrayList<>();
        this.spawnableWaterCreatureList = new ArrayList<>();
        this.spawnableCaveCreatureList = new ArrayList<>();
        this.decorator.flowersPerChunk = 2;
        this.decorator.grassPerChunk = 4;
        this.decorator.treesPerChunk = 1;
        this.decorator.bigMushroomsPerChunk = -999;
        this.decorator.mushroomsPerChunk = -999;
        this.decorator.reedsPerChunk = -999;
        if (ChaosPersists.ButterflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 20, 3, 6));
        }
        if (ChaosPersists.MothEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(EntityLunaMoth.class, 10, 1, 5));
        }
        if (ChaosPersists.CockateilEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cockateil.class, 10, 2, 4));
        }
        if (ChaosPersists.FireflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Firefly.class, 15, 3, 6));
        }
        if (ChaosPersists.CliffRacerEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(CliffRacer.class, 30, 3, 6));
        }
        if (ChaosPersists.CloudSharkEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(CloudShark.class, 2, 1, 1));
        }
        if (ChaosPersists.GoldFishEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(GoldFish.class, 10, 2, 4));
        }
        if (ChaosPersists.FairyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Fairy.class, 5, 2, 4));
        }
        if (ChaosPersists.BaryonyxEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Baryonyx.class, 2, 2, 4));
        }
        if (ChaosPersists.BeeEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Bee.class, 2, 2, 4));
        }
        if (ChaosPersists.CassowaryEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Cassowary.class, 2, 2, 4));
        }
        if (ChaosPersists.DragonflyEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Dragonfly.class, 2, 2, 4));
        }
        if (ChaosPersists.PeacockEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Peacock.class, 2, 2, 4));
        }
        if (ChaosPersists.StinkBugEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(StinkBug.class, 3, 2, 4));
        }
        if (ChaosPersists.OstrichEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Ostrich.class, 1, 1, 2));
        }
        if (ChaosPersists.ChipmunkEnable != 0) {
            this.spawnableCaveCreatureList.add(new Biome.SpawnListEntry(Chipmunk.class, 1, 1, 2));
        }
        if (ChaosPersists.BeaverEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(Beaver.class, 1, 1, 2));
        }
        if (ChaosPersists.CowEnable != 0) {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(RedCow.class, 3, 2, 4));
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(GoldCow.class, 2, 2, 4));
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EnchantedCow.class, 1, 2, 4));
        }
        if (ChaosPersists.VortexEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Vortex.class, 1, 1, 2));
        }
        if (ChaosPersists.PitchBlackEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(PitchBlack.class, 1, 1, 2));
        }
        if (ChaosPersists.TerribleTerrorEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(TerribleTerror.class, 4, 2, 6));
        }
        if (ChaosPersists.AlosaurusEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Alosaurus.class, 1, 1, 1));
        }
        if (ChaosPersists.BasiliskEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Basilisk.class, 1, 1, 1));
        }
        if (ChaosPersists.Robot1Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot1.class, 5, 2, 8));
        }
        if (ChaosPersists.Robot2Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot2.class, 2, 1, 4));
        }
        if (ChaosPersists.Robot3Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot3.class, 2, 1, 4));
        }
        if (ChaosPersists.Robot4Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot4.class, 1, 1, 2));
        }
        if (ChaosPersists.Robot5Enable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Robot5.class, 2, 3, 5));
        }
        if (ChaosPersists.CaterKillerEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(CaterKiller.class, 1, 1, 1));
        }
        if (ChaosPersists.CaveFisherEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(CaveFisher.class, 5, 1, 5));
        }
        if (ChaosPersists.CreepingHorrorEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(CreepingHorror.class, 5, 1, 5));
        }
        if (ChaosPersists.CryolophosaurusEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Cryolophosaurus.class, 5, 1, 5));
        }
        if (ChaosPersists.UrchinEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Urchin.class, 2, 1, 5));
        }
        if (ChaosPersists.DungeonBeastEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(DungeonBeast.class, 2, 1, 5));
        }
        if (ChaosPersists.EmperorScorpionEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(EmperorScorpion.class, 1, 1, 1));
        }
        if (ChaosPersists.EnderKnightEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(EnderKnight.class, 2, 1, 2));
        }
        if (ChaosPersists.EnderReaperEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(EnderReaper.class, 1, 1, 1));
        }
        if (ChaosPersists.HammerheadEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Hammerhead.class, 1, 1, 1));
        }
        if (ChaosPersists.HerculesBeetleEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(HerculesBeetle.class, 1, 1, 1));
        }
        if (ChaosPersists.TrooperBugEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(TrooperBug.class, 1, 1, 1));
        }
        if (ChaosPersists.MolenoidEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Molenoid.class, 1, 1, 1));
        }
        if (ChaosPersists.MothraEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Mothra.class, 1, 1, 1));
        }
        if (ChaosPersists.BrutalflyEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Brutalfly.class, 1, 1, 1));
        }
        if (ChaosPersists.RatEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Rat.class, 10, 1, 10));
        }
        if (ChaosPersists.RotatorEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Rotator.class, 1, 1, 3));
        }
        if (ChaosPersists.ScorpionEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Scorpion.class, 2, 1, 3));
        }
        if (ChaosPersists.SpitBugEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(SpitBug.class, 2, 1, 3));
        }
        if (ChaosPersists.NastysaurusEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Nastysaurus.class, 1, 1, 1));
        }
        if (ChaosPersists.TRexEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(TRex.class, 1, 1, 1));
        }
        if (ChaosPersists.LeafMonsterEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(LeafMonster.class, 2, 1, 4));
        }
        if (ChaosPersists.PointysaurusEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Pointysaurus.class, 2, 1, 4));
        }
        if (ChaosPersists.LeonEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Leon.class, 1, 1, 1));
        }
        if (ChaosPersists.MantisEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(Mantis.class, 1, 1, 1));
        }
        if (ChaosPersists.LurkingTerrorEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(LurkingTerror.class, 1, 1, 1));
        }
        if (ChaosPersists.GammaMetroidEnable != 0) {
            this.spawnableMonsterList.add(new Biome.SpawnListEntry(GammaMetroid.class, 1, 1, 1));
        }
    }
}
