package com.astryxion.chaospersists.proxy;

import com.astryxion.chaospersists.client.BigHammerItemStackRenderer;
import com.astryxion.chaospersists.client.ChainsawItemStackRenderer;
import com.astryxion.chaospersists.client.StaticBigWeaponItemStackRenderer;
import com.astryxion.chaospersists.client.TeisrHandBakedModelWrapper;
import com.astryxion.chaospersists.block.BlockAppleLeaves;
import com.astryxion.chaospersists.block.BlockButterflyPlant;
import com.astryxion.chaospersists.block.BlockCorn;
import com.astryxion.chaospersists.block.BlockCrystal;
import com.astryxion.chaospersists.block.BlockCrystalLeaves;
import com.astryxion.chaospersists.block.BlockCrystalPlant;
import com.astryxion.chaospersists.block.BlockCrystalTorch;
import com.astryxion.chaospersists.block.BlockCrystalTreeLog;
import com.astryxion.chaospersists.block.BlockExperienceLeaves;
import com.astryxion.chaospersists.block.BlockExperiencePlant;
import com.astryxion.chaospersists.block.BlockExtremeTorch;
import com.astryxion.chaospersists.block.BlockFireflyPlant;
import com.astryxion.chaospersists.block.BlockLettuce;
import com.astryxion.chaospersists.block.BlockMosquitoPlant;
import com.astryxion.chaospersists.block.BlockMothPlant;
import com.astryxion.chaospersists.block.BlockQuinoa;
import com.astryxion.chaospersists.block.BlockRadish;
import com.astryxion.chaospersists.block.BlockRice;
import com.astryxion.chaospersists.block.BlockScaryLeaves;
import com.astryxion.chaospersists.block.BlockStrawberry;
import com.astryxion.chaospersists.block.BlockTomato;
import com.astryxion.chaospersists.block.CrystalAntBlock;
import com.astryxion.chaospersists.block.CrystalFurnace;
import com.astryxion.chaospersists.block.CrystalGrass;
import com.astryxion.chaospersists.block.CrystalWood;
import com.astryxion.chaospersists.block.CrystalWorkbench;
import com.astryxion.chaospersists.block.ChaosDirectionalTorchBlock;
import com.astryxion.chaospersists.block.DungeonSpawnerBlock;
import com.astryxion.chaospersists.block.IslandBlock;
import com.astryxion.chaospersists.block.KingSpawnerBlock;
import com.astryxion.chaospersists.block.KrakenRepellent;
import com.astryxion.chaospersists.block.QueenSpawnerBlock;
import com.astryxion.chaospersists.item.CreeperRepellent;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.core.ChaosSounds;
import com.astryxion.chaospersists.util.MyBlockFlower;
import com.astryxion.chaospersists.world.ore.OreBasicStone;
import com.astryxion.chaospersists.world.ore.OreCrystal;
import com.astryxion.chaospersists.world.ore.OreCrystalCrystal;
import com.astryxion.chaospersists.world.ore.OreGenericEgg;
import com.astryxion.chaospersists.model.ModelBattleAxe;
import com.astryxion.chaospersists.model.ModelBertha;
import com.astryxion.chaospersists.model.ModelHammy;
import com.astryxion.chaospersists.model.ModelQueenBattleAxe;
import com.astryxion.chaospersists.model.ModelSlice;
import com.astryxion.chaospersists.model.ModelSquidZooka;
import com.astryxion.chaospersists.network.RiderControl;
import com.astryxion.chaospersists.util.GirlfriendOverlayGui;
import com.astryxion.chaospersists.util.KeyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import com.astryxion.chaospersists.compat.forge.common.util.EnumHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientProxyChaos extends CommonProxyChaos {

    @SuppressWarnings("unchecked")
    private static Block cpBlock(Object block) {
        return (Block) block;
    }

    @Override
    public void registerBlockRenderLayers() {
        RenderType cutout = RenderType.cutout();
        RenderType translucent = RenderType.translucent();

        for (Block block : BuiltInRegistries.BLOCK) {
            ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
            if (id == null || !ChaosPersists.MODID.equals(id.getNamespace())) {
                continue;
            }

            if (block instanceof OreGenericEgg
                    || block instanceof OreCrystal
                    || block instanceof OreCrystalCrystal
                    || block instanceof CrystalWood
                    || block instanceof CrystalGrass
                    || block instanceof CrystalWorkbench
                    || block instanceof CrystalFurnace
                    || block instanceof CrystalAntBlock
                    || block instanceof IslandBlock
                    || block instanceof BlockCrystal
                    || block instanceof BlockCrystalTreeLog
                    || block instanceof BlockCrystalPlant
                    || block instanceof BlockExperiencePlant
                    || block instanceof MyBlockFlower
                    || block instanceof BlockTomato
                    || block instanceof BlockStrawberry
                    || block instanceof BlockRice
                    || block instanceof BlockRadish
                    || block instanceof BlockQuinoa
                    || block instanceof BlockMothPlant
                    || block instanceof BlockMosquitoPlant
                    || block instanceof BlockLettuce
                    || block instanceof BlockFireflyPlant
                    || block instanceof BlockButterflyPlant
                    || block instanceof BlockCorn
                    || block instanceof BlockCrystalTorch
                    || block instanceof BlockExtremeTorch
                    || block instanceof ChaosDirectionalTorchBlock
                    || block instanceof CreeperRepellent
                    || block instanceof KrakenRepellent
                    || block instanceof KingSpawnerBlock
                    || block instanceof QueenSpawnerBlock
                    || block instanceof DungeonSpawnerBlock) {
                ItemBlockRenderTypes.setRenderLayer(block, cutout);
            } else if (block instanceof OreBasicStone) {
                String path = id.getPath();
                if ("crystalstone".equals(path)
                        || "crystalrat".equals(path)
                        || "crystalfairy".equals(path)) {
                    ItemBlockRenderTypes.setRenderLayer(block, cutout);
                }
            } else if (block instanceof BlockScaryLeaves
                    || block instanceof BlockExperienceLeaves
                    || block instanceof BlockCrystalLeaves
                    || block instanceof BlockAppleLeaves) {
                ItemBlockRenderTypes.setRenderLayer(block, translucent);
            }
        }
    }

    @Override
    public void registerBlockModels() {
        // Item/block JSON models; pizza/duct-tape slice variants via blockstates.
        // Big-weapon TEISR wrapping matches 1.12 ModelBakeEvent behavior.
        MinecraftForge.EVENT_BUS.register(
                new Object() {
                    @SubscribeEvent
                    public void onModelBake(ModelEvent.ModifyBakingResult event) {
                        wrapTeisrHandItem(event, ChaosPersists.MyChainsaw, ChainsawItemStackRenderer::new);

                        ModelBertha berthaModel = new ModelBertha();
                        wrapTeisrHandItem(
                                event,
                                ChaosPersists.MyBertha,
                                flat ->
                                        new StaticBigWeaponItemStackRenderer(
                                                flat,
                                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                                                        "chaospersists", "textures/entity/berthatexture.png"),
                                                StaticBigWeaponItemStackRenderer.Style.BERTHA,
                                                berthaModel::render));

                        ModelHammy hammyModel = new ModelHammy();
                        wrapTeisrHandItem(
                                event,
                                ChaosPersists.MyHammy,
                                flat ->
                                        new StaticBigWeaponItemStackRenderer(
                                                flat,
                                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                                                        "chaospersists", "textures/entity/attitudeadjustertexture.png"),
                                                StaticBigWeaponItemStackRenderer.Style.HAMMY,
                                                hammyModel::render));

                        ModelSlice sliceModel = new ModelSlice();
                        wrapTeisrHandItem(
                                event,
                                ChaosPersists.MySlice,
                                flat ->
                                        new StaticBigWeaponItemStackRenderer(
                                                flat,
                                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                                                        "chaospersists", "textures/entity/slicetexture.png"),
                                                StaticBigWeaponItemStackRenderer.Style.SLICE,
                                                sliceModel::render));

                        ModelSlice royalModel = new ModelSlice();
                        wrapTeisrHandItem(
                                event,
                                ChaosPersists.MyRoyal,
                                flat ->
                                        new StaticBigWeaponItemStackRenderer(
                                                flat,
                                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                                                        "chaospersists", "textures/entity/royaltexture.png"),
                                                StaticBigWeaponItemStackRenderer.Style.ROYAL,
                                                royalModel::render));

                        ModelBattleAxe battleAxeModel = new ModelBattleAxe();
                        wrapTeisrHandItem(
                                event,
                                ChaosPersists.MyBattleAxe,
                                flat ->
                                        new StaticBigWeaponItemStackRenderer(
                                                flat,
                                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                                                        "chaospersists", "textures/entity/battleaxetexture.png"),
                                                StaticBigWeaponItemStackRenderer.Style.BATTLE_AXE,
                                                battleAxeModel::render));

                        ModelQueenBattleAxe queenAxeModel = new ModelQueenBattleAxe();
                        wrapTeisrHandItem(
                                event,
                                ChaosPersists.MyQueenBattleAxe,
                                flat ->
                                        new StaticBigWeaponItemStackRenderer(
                                                flat,
                                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                                                        "chaospersists", "textures/entity/queenbattleaxetexture.png"),
                                                StaticBigWeaponItemStackRenderer.Style.QUEEN_BATTLE_AXE,
                                                queenAxeModel::render));

                        ModelSquidZooka squidModel = new ModelSquidZooka();
                        wrapTeisrHandItem(
                                event,
                                ChaosPersists.MySquidZooka,
                                flat ->
                                        new StaticBigWeaponItemStackRenderer(
                                                flat,
                                                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                                                        "chaospersists", "textures/entity/squidzookatexture.png"),
                                                StaticBigWeaponItemStackRenderer.Style.SQUID_ZOOKA,
                                                squidModel::render));

                        wrapTeisrHandItem(event, ChaosPersists.MyBigHammer, BigHammerItemStackRenderer::new);
                    }
                });
    }

    private static void wrapTeisrHandItem(
            ModelEvent.ModifyBakingResult event,
            Item item,
            java.util.function.Function<
                            net.minecraft.client.resources.model.BakedModel,
                            net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer>
                    createRenderer) {
        net.minecraft.resources.ResourceLocation itemId =
                net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item);
        if (itemId == null) {
            return;
        }
        ModelResourceLocation mrl = new ModelResourceLocation(itemId, "inventory");
        java.util.Map<net.minecraft.resources.ResourceLocation, BakedModel> models = event.getModels();
        BakedModel baked = models.get(mrl);
        BakedModel original =
                baked instanceof TeisrHandBakedModelWrapper
                        ? ((TeisrHandBakedModelWrapper) baked).getInner()
                        : baked;
        if (original != null) {
            models.put(mrl, new TeisrHandBakedModelWrapper(original));
            TeisrHandBakedModelWrapper.registerCustomRenderer(item, createRenderer.apply(original));
        }
    }

    @Override
    public void registerBlockColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        blockColors.register(
                (BlockState state, net.minecraft.world.level.BlockAndTintGetter level, net.minecraft.core.BlockPos pos, int tintIndex) -> {
                    if (tintIndex == 0 && level != null && pos != null) {
                        return net.minecraft.client.renderer.BiomeColors.getAverageGrassColor(level, pos);
                    }
                    return -1;
                },
                cpBlock(ChaosPersists.MyAntBlock),
                cpBlock(ChaosPersists.MyRedAntBlock),
                cpBlock(ChaosPersists.MyRainbowAntBlock),
                cpBlock(ChaosPersists.MyUnstableAntBlock),
                cpBlock(ChaosPersists.TermiteBlock),
                cpBlock(ChaosPersists.CrystalTermiteBlock));
    }

    @Override
    public void registerLeafColors() {
        Minecraft.getInstance()
                .getBlockColors()
                .register(
                        (BlockState state, net.minecraft.world.level.BlockAndTintGetter level, net.minecraft.core.BlockPos pos, int tintIndex) -> {
                            if (level != null && pos != null) {
                                return net.minecraft.client.renderer.BiomeColors.getAverageFoliageColor(level, pos);
                            }
                            return FoliageColor.getDefaultColor();
                        },
                        cpBlock(ChaosPersists.MyAppleLeaves),
                        cpBlock(ChaosPersists.MyExperienceLeaves),
                        cpBlock(ChaosPersists.MyScaryLeaves),
                        cpBlock(ChaosPersists.MyCherryLeaves),
                        cpBlock(ChaosPersists.MyPeachLeaves));
    }

    @Override
    public void registerItemColors() {
        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        itemColors.register(
                (stack, tintIndex) -> {
                    if (tintIndex == 0) {
                        return GrassColor.get(0.5D, 1.0D);
                    }
                    return -1;
                },
                EnumHelper.getItemFromBlock(ChaosPersists.MyAntBlock),
                EnumHelper.getItemFromBlock(ChaosPersists.MyRedAntBlock),
                EnumHelper.getItemFromBlock(ChaosPersists.MyRainbowAntBlock),
                EnumHelper.getItemFromBlock(ChaosPersists.MyUnstableAntBlock),
                EnumHelper.getItemFromBlock(ChaosPersists.TermiteBlock),
                EnumHelper.getItemFromBlock(ChaosPersists.CrystalTermiteBlock));

        itemColors.register(
                (stack, tintIndex) -> {
                    if (tintIndex == 0) {
                        return FoliageColor.getDefaultColor();
                    }
                    return -1;
                },
                EnumHelper.getItemFromBlock(ChaosPersists.MyAppleLeaves),
                EnumHelper.getItemFromBlock(ChaosPersists.MyExperienceLeaves),
                EnumHelper.getItemFromBlock(ChaosPersists.MyScaryLeaves),
                EnumHelper.getItemFromBlock(ChaosPersists.MyCherryLeaves),
                EnumHelper.getItemFromBlock(ChaosPersists.MyPeachLeaves));
    }

    @Override
    public void registerRenderThings() {
        MinecraftForge.EVENT_BUS.register(new GirlfriendOverlayGui(Minecraft.getInstance()));
        // Entity renderers: ChaosPersists.registerEntityRenderers (EntityRenderersEvent.RegisterRenderers)
    }

    public void registerSoundThings() {
        MinecraftForge.EVENT_BUS.register(new ChaosSounds());
    }

    public void registerKeyboardInput() {
        ChaosPersists.MyKeyhandler = new KeyHandler();
    }

    public void registerNetworkStuff() {
        super.registerNetworkStuff();
        MinecraftForge.EVENT_BUS.register(new RiderControl(this.getNetwork()));
    }

    public int setArmorPrefix(String string) {
        return 0;
    }
}
