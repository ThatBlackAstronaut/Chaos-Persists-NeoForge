package com.astryxion.chaospersists.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * 3D orespawn weapons in hand: third person matches {@link ChainsawItemStackRenderer}; first person uses
 * sword-specific poses (Bertha vs Slice/Royal), {@link ChainsawItemStackRenderer#applyHammyFirstPersonTransforms()}
 * for the Attitude Adjuster, and the chainsaw hand pose for axes and SquidZooka.
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
                ChainsawItemStackRenderer.applyBerthaSwordFirstPersonTransforms();
            } else if (style == Style.SLICE || style == Style.ROYAL) {
                ChainsawItemStackRenderer.applySliceStyleSwordFirstPersonTransforms();
            } else if (style == Style.HAMMY) {
                ChainsawItemStackRenderer.applyHammyFirstPersonTransforms();
            } else {
                ChainsawItemStackRenderer.applyHandFirstPersonTransforms();
            }
        } else {
            ChainsawItemStackRenderer.applyHandThirdPersonTransforms();
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        renderModel.run();
        GlStateManager.popMatrix();
    }
}
