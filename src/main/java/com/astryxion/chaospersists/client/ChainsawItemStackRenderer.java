package com.astryxion.chaospersists.client;

import com.astryxion.chaospersists.client.model.ModelChainsaw;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

/**
 * 3D animated chainsaw in first/third person; flat JSON model everywhere else (GUI, ground, frame).
 */
public class ChainsawItemStackRenderer extends BlockEntityWithoutLevelRenderer {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/chainsawtexture.png");

    private final BakedModel flatModel;
    private final ModelChainsaw modelChainsaw = new ModelChainsaw();

    public ChainsawItemStackRenderer(BakedModel flatModel) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.flatModel = flatModel;
    }

    public ChainsawItemStackRenderer(
            BakedModel flatModel,
            BlockEntityRenderDispatcher dispatcher,
            net.minecraft.client.model.geom.EntityModelSet modelSet) {
        super(dispatcher, modelSet);
        this.flatModel = flatModel;
    }

    @Override
    public void renderByItem(
            ItemStack stack,
            ItemDisplayContext ctx,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        if (ctx == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || ctx == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND) {
            renderFirstPerson(ctx == ItemDisplayContext.FIRST_PERSON_LEFT_HAND, poseStack, buffer, packedLight, packedOverlay);
        } else if (ctx == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || ctx == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            renderThirdPerson(ctx == ItemDisplayContext.THIRD_PERSON_LEFT_HAND, poseStack, buffer, packedLight, packedOverlay);
        } else {
            Minecraft.getInstance()
                    .getItemRenderer()
                    .render(stack, ctx, false, poseStack, buffer, packedLight, packedOverlay, this.flatModel);
        }
    }

    /**
     * Hand pose for first person (after vanilla in-hand matrix). Shared with {@link StaticBigWeaponItemStackRenderer}
     * for non-sword weapons (axes, zooka, hammy, chainsaw).
     */
    public static void applyHandFirstPersonTransforms(PoseStack poseStack) {
        poseStack.translate(0.8f, -0.2f, 0.2f);
        poseStack.scale(0.18f, 0.18f, 0.18f);
        poseStack.mulPose(Axis.XP.rotationDegrees(110.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
    }

    /**
     * First person for Attitude Adjuster ({@link com.astryxion.chaospersists.model.ModelHammy}).
     */
    public static void applyHammyFirstPersonTransforms(PoseStack poseStack) {
        poseStack.translate(0.36f, 0.02f, 0.1f);
        poseStack.scale(0.09f, 0.09f, 0.09f);
        poseStack.mulPose(Axis.YP.rotationDegrees(-24.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(48.0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(46.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
    }

    /**
     * Hand pose for third person (after vanilla equipped matrix). Shared with {@link StaticBigWeaponItemStackRenderer}.
     */
    public static void applyHandThirdPersonTransforms(PoseStack poseStack) {
        poseStack.translate(0.5f, -0.4f, 0.0f);
        poseStack.scale(0.18f, 0.18f, 0.18f);
        poseStack.mulPose(Axis.XP.rotationDegrees(-15.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(-35.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
    }

    private void renderFirstPerson(boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        poseStack.pushPose();
        if (leftHand) {
            poseStack.scale(-1.0f, 1.0f, 1.0f);
        }
        applyHandFirstPersonTransforms(poseStack);
        drawModel(poseStack, buffer, light, overlay);
        poseStack.popPose();
    }

    private void renderThirdPerson(boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        poseStack.pushPose();
        if (leftHand) {
            poseStack.scale(-1.0f, 1.0f, 1.0f);
        }
        applyHandThirdPersonTransforms(poseStack);
        drawModel(poseStack, buffer, light, overlay);
        poseStack.popPose();
    }

    private void drawModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.modelChainsaw.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
    }
}
