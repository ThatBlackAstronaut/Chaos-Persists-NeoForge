package com.astryxion.chaospersists.client;

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
 * 3D orespawn weapons in hand: Bertha / Slice / Royal use sword-tuned first- and third-person transforms;
 * other styles reuse {@link ChainsawItemStackRenderer} hand poses (hammy, axes, zooka).
 */
public class StaticBigWeaponItemStackRenderer extends BlockEntityWithoutLevelRenderer {

    public enum Style {
        BERTHA,
        HAMMY,
        SLICE,
        ROYAL,
        BATTLE_AXE,
        QUEEN_BATTLE_AXE,
        SQUID_ZOOKA
    }

    @FunctionalInterface
    public interface WeaponModelDraw {
        void render(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay);
    }

    private final BakedModel flatModel;
    private final WeaponModelDraw renderModel;
    private final ResourceLocation texture;
    private final Style style;

    public StaticBigWeaponItemStackRenderer(
            BakedModel flatModel,
            ResourceLocation texture,
            Style style,
            WeaponModelDraw renderModel) {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        this.flatModel = flatModel;
        this.renderModel = renderModel;
        this.texture = texture;
        this.style = style;
    }

    public StaticBigWeaponItemStackRenderer(
            BakedModel flatModel,
            ResourceLocation texture,
            Style style,
            WeaponModelDraw renderModel,
            BlockEntityRenderDispatcher dispatcher,
            net.minecraft.client.model.geom.EntityModelSet modelSet) {
        super(dispatcher, modelSet);
        this.flatModel = flatModel;
        this.renderModel = renderModel;
        this.texture = texture;
        this.style = style;
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
            renderHand(true, ctx == ItemDisplayContext.FIRST_PERSON_LEFT_HAND, poseStack, buffer, packedLight, packedOverlay);
        } else if (ctx == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || ctx == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            renderHand(false, ctx == ItemDisplayContext.THIRD_PERSON_LEFT_HAND, poseStack, buffer, packedLight, packedOverlay);
        } else {
            Minecraft.getInstance()
                    .getItemRenderer()
                    .render(stack, ctx, false, poseStack, buffer, packedLight, packedOverlay, this.flatModel);
        }
    }

    private void renderHand(
            boolean firstPerson,
            boolean leftHand,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            int packedOverlay) {
        poseStack.pushPose();
        if (leftHand) {
            poseStack.scale(-1.0f, 1.0f, 1.0f);
        }
        if (firstPerson) {
            if (style == Style.BERTHA) {
                applyBerthaSwordFirstPersonTransformsTuned(poseStack);
            } else if (style == Style.SLICE || style == Style.ROYAL) {
                applySliceRoyalFirstPersonTransformsTuned(poseStack);
            } else if (style == Style.HAMMY) {
                ChainsawItemStackRenderer.applyHammyFirstPersonTransforms(poseStack);
            } else {
                ChainsawItemStackRenderer.applyHandFirstPersonTransforms(poseStack);
            }
        } else {
            if (style == Style.BERTHA || style == Style.SLICE || style == Style.ROYAL) {
                applySwordThirdPersonTransformsTuned(poseStack);
            } else {
                ChainsawItemStackRenderer.applyHandThirdPersonTransforms(poseStack);
            }
        }
        VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(texture));
        this.renderModel.render(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    private static void applyBerthaSwordFirstPersonTransformsTuned(PoseStack poseStack) {
        poseStack.translate(0.72f, -0.12f, 0.16f);
        poseStack.scale(0.19f, 0.19f, 0.19f);
        poseStack.mulPose(Axis.YP.rotationDegrees(-20.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(58.0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(58.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
    }

    private static void applySliceRoyalFirstPersonTransformsTuned(PoseStack poseStack) {
        poseStack.translate(0.74f, -0.12f, 0.16f);
        poseStack.scale(0.19f, 0.19f, 0.19f);
        poseStack.mulPose(Axis.YP.rotationDegrees(-24.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(56.0f));
        poseStack.mulPose(Axis.XP.rotationDegrees(60.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
    }

    private static void applySwordThirdPersonTransformsTuned(PoseStack poseStack) {
        poseStack.translate(0.85f, -0.06f, -0.14f);
        poseStack.scale(0.24f, 0.24f, 0.24f);
        poseStack.mulPose(Axis.XP.rotationDegrees(-46.0f));
        poseStack.mulPose(Axis.YP.rotationDegrees(-68.0f));
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
    }
}
