package com.astryxion.chaospersists.proxy;

import com.astryxion.chaospersists.client.BigHammerItemStackRenderer;
import com.astryxion.chaospersists.client.ChainsawItemStackRenderer;
import com.astryxion.chaospersists.client.StaticBigWeaponItemStackRenderer;
import com.astryxion.chaospersists.client.TeisrHandBakedModelWrapper;
import com.astryxion.chaospersists.core.ChaosPersists;
import com.astryxion.chaospersists.core.ChaosSounds;
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
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import com.astryxion.chaospersists.compat.forge.common.util.EnumHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientProxyChaos extends CommonProxyChaos {

    @SuppressWarnings("unchecked")
    private static Block cpBlock(Object block) {
        return (Block) block;
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
