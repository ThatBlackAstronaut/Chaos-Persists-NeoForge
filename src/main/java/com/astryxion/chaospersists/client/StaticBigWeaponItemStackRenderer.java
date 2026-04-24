package com.astryxion.chaospersists.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * 3D orespawn weapons in hand: Bertha / Slice / Royal use sword-tuned first- and third-person transforms;
 * other styles reuse {@link ChainsawItemStackRenderer} hand poses (hammy, axes, zooka).
 */
public class StaticBigWeaponItemStackRenderer extends TileEntityItemStackRenderer {

    public enum Style {
        BERTHA,
        HAMMY,
        SLICE,
        ROYAL,
        BATTLE_AXE,
        QUEEN_BATTLE_AXE,
        SQUID_ZOOKA
    }

    private final IBakedModel flatModel;
    private final Runnable renderModel;
    private final ResourceLocation texture;
    private final Style style;

    /** {@code renderModel} should invoke the weapon model's no-arg {@code render()} method. */
    public StaticBigWeaponItemStackRenderer(IBakedModel flatModel, ResourceLocation texture, Style style, Runnable renderModel) {
        this.flatModel = flatModel;
        this.renderModel = renderModel;
        this.texture = texture;
        this.style = style;
    }

    @Override
    public void renderByItem(ItemStack stack) {
        TransformType transform = TeisrHandTransformHolder.get();
        try {
            Minecraft mc = Minecraft.getMinecraft();
            if (transform == TransformType.FIRST_PERSON_LEFT_HAND
                || transform == TransformType.FIRST_PERSON_RIGHT_HAND) {
                renderHand(true, transform == TransformType.FIRST_PERSON_LEFT_HAND);
            } else if (transform == TransformType.THIRD_PERSON_LEFT_HAND
                || transform == TransformType.THIRD_PERSON_RIGHT_HAND) {
                renderHand(false, transform == TransformType.THIRD_PERSON_LEFT_HAND);
            } else {
                mc.getRenderItem().renderItem(stack, flatModel);
            }
        } finally {
            TeisrHandTransformHolder.clear();
        }
    }

    private void renderHand(boolean firstPerson, boolean leftHand) {
        GlStateManager.pushMatrix();
        if (leftHand) {
            GlStateManager.scale(-1.0f, 1.0f, 1.0f);
        }
        if (firstPerson) {
            if (style == Style.BERTHA) {
                applyBerthaSwordFirstPersonTransformsTuned();
            } else if (style == Style.SLICE || style == Style.ROYAL) {
                applySliceRoyalFirstPersonTransformsTuned();
            } else if (style == Style.HAMMY) {
                ChainsawItemStackRenderer.applyHammyFirstPersonTransforms();
            } else {
                ChainsawItemStackRenderer.applyHandFirstPersonTransforms();
            }
        } else {
            if (style == Style.BERTHA || style == Style.SLICE || style == Style.ROYAL) {
                applySwordThirdPersonTransformsTuned();
            } else {
                ChainsawItemStackRenderer.applyHandThirdPersonTransforms();
            }
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        renderModel.run();
        GlStateManager.popMatrix();
    }

    /**
     * Sword-only first-person tuning for 1.12.2 TEISR hand matrices.
     * Keep blade visible (not buried at lower-right) while preserving a heavy two-handed feel.
     */
    private static void applyBerthaSwordFirstPersonTransformsTuned() {
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(58.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(58.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-20.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(0.19f, 0.19f, 0.19f);
        GlStateManager.translate(0.72f, -0.12f, 0.16f);
    }

    private static void applySliceRoyalFirstPersonTransformsTuned() {
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(60.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(56.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-24.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(0.19f, 0.19f, 0.19f);
        GlStateManager.translate(0.74f, -0.12f, 0.16f);
    }

    /**
     * Sword-only third-person tuning so giant blades sit out to the side like OreSpawn,
     * instead of drooping behind the leg.
     * Extra pitch lifts long blades off the ground (they otherwise read as stabbing the floor).
     */
    private static void applySwordThirdPersonTransformsTuned() {
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-68.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-46.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.24f, 0.24f, 0.24f);
        GlStateManager.translate(0.85f, -0.06f, -0.14f);
    }
}
