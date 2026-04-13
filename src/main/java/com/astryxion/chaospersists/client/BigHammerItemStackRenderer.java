package com.astryxion.chaospersists.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

/**
 * Flat handheld hammer texture with in-hand scale/pose tuned separately from {@link ChainsawItemStackRenderer}
 * (swords/axes use that path; hammer head is offset to reduce clipping through the player).
 */
public class BigHammerItemStackRenderer extends TileEntityItemStackRenderer {

    private final IBakedModel flatModel;

    public BigHammerItemStackRenderer(IBakedModel flatModel) {
        this.flatModel = flatModel;
    }

    @Override
    public void renderByItem(ItemStack stack) {
        TransformType transform = TeisrHandTransformHolder.get();
        try {
            Minecraft mc = Minecraft.getMinecraft();
            if (transform == TransformType.FIRST_PERSON_LEFT_HAND
                || transform == TransformType.FIRST_PERSON_RIGHT_HAND) {
                boolean left = transform == TransformType.FIRST_PERSON_LEFT_HAND;
                GlStateManager.pushMatrix();
                if (left) {
                    GlStateManager.scale(-1.0f, 1.0f, 1.0f);
                }
                applyFirstPerson();
                mc.getRenderItem().renderItem(stack, flatModel);
                GlStateManager.popMatrix();
            } else if (transform == TransformType.THIRD_PERSON_LEFT_HAND
                || transform == TransformType.THIRD_PERSON_RIGHT_HAND) {
                boolean left = transform == TransformType.THIRD_PERSON_LEFT_HAND;
                GlStateManager.pushMatrix();
                if (left) {
                    GlStateManager.scale(-1.0f, 1.0f, 1.0f);
                }
                applyThirdPerson();
                mc.getRenderItem().renderItem(stack, flatModel);
                GlStateManager.popMatrix();
            } else {
                mc.getRenderItem().renderItem(stack, flatModel);
            }
        } finally {
            TeisrHandTransformHolder.clear();
        }
    }

    private static void applyFirstPerson() {
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(102.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-8.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(0.32f, 0.32f, 0.32f);
        GlStateManager.translate(0.55f, -0.12f, 0.18f);
    }

    private static void applyThirdPerson() {
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-38.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-22.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.38f, 0.38f, 0.38f);
        GlStateManager.translate(0.42f, -0.42f, 0.52f);
    }
}
