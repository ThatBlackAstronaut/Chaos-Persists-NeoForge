package com.astryxion.chaospersists.client;

import com.astryxion.chaospersists.client.model.ModelChainsaw;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * 3D animated chainsaw in first/third person; flat JSON model everywhere else (GUI, ground, frame).
 */
public class ChainsawItemStackRenderer extends TileEntityItemStackRenderer {

    /** Same layout as 1.7 orespawn:Chainsawtexture.png — place at assets/chaospersists/textures/entity/chainsawtexture.png */
    private static final ResourceLocation TEXTURE =
        new ResourceLocation("chaospersists", "textures/entity/chainsawtexture.png");

    private final IBakedModel flatModel;
    private final ModelChainsaw modelChainsaw = new ModelChainsaw();

    public ChainsawItemStackRenderer(IBakedModel flatModel) {
        this.flatModel = flatModel;
    }

    @Override
    public void renderByItem(ItemStack stack) {
        TransformType transform = TeisrHandTransformHolder.get();
        try {
            Minecraft mc = Minecraft.getMinecraft();
            if (transform == TransformType.FIRST_PERSON_LEFT_HAND
                || transform == TransformType.FIRST_PERSON_RIGHT_HAND) {
                renderFirstPerson(transform == TransformType.FIRST_PERSON_LEFT_HAND);
            } else if (transform == TransformType.THIRD_PERSON_LEFT_HAND
                || transform == TransformType.THIRD_PERSON_RIGHT_HAND) {
                renderThirdPerson(transform == TransformType.THIRD_PERSON_LEFT_HAND);
            } else {
                renderFlat(mc, stack);
            }
        } finally {
            TeisrHandTransformHolder.clear();
        }
    }

    /**
     * Hand pose for first person (after vanilla in-hand matrix). Shared with {@link StaticBigWeaponItemStackRenderer}
     * for non-sword weapons (axes, zooka, hammy, chainsaw).
     */
    public static void applyHandFirstPersonTransforms() {
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(110.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.18f, 0.18f, 0.18f);
        GlStateManager.translate(0.8f, -0.2f, 0.2f);
    }

    /**
     * First person for {@link com.astryxion.chaospersists.model.ModelBertha}. Shallow pitch + strong roll so the
     * blade reads vertical (not a flat banner across the view); scale 0.12 limits FOV blockage vs. chainsaw 0.18.
     */
    public static void applyBerthaSwordFirstPersonTransforms() {
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(50.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(52.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-22.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(0.12f, 0.12f, 0.12f);
        GlStateManager.translate(0.4f, 0.02f, 0.1f);
    }

    /**
     * First person for {@link com.astryxion.chaospersists.model.ModelSlice} (Slice + Royal Guardian): offset pieces
     * need a touch more yaw than Bertha so the flat faces are not edge-on.
     */
    public static void applySliceStyleSwordFirstPersonTransforms() {
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(54.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(50.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-26.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(0.12f, 0.12f, 0.12f);
        GlStateManager.translate(0.44f, 0.0f, 0.12f);
    }

    /**
     * First person for Attitude Adjuster ({@link com.astryxion.chaospersists.model.ModelHammy}): the chainsaw pose
     * scales the huge hammer toward the camera and fills the screen; use a smaller scale and upright-style roll.
     */
    public static void applyHammyFirstPersonTransforms() {
        GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(46.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(48.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-24.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(0.09f, 0.09f, 0.09f);
        GlStateManager.translate(0.36f, 0.02f, 0.1f);
    }

    /**
     * Hand pose for third person (after vanilla equipped matrix). Shared with {@link StaticBigWeaponItemStackRenderer}.
     */
    public static void applyHandThirdPersonTransforms() {
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.rotate(-35.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-15.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(0.18f, 0.18f, 0.18f);
        GlStateManager.translate(0.5f, -0.4f, 0.0f);
    }

    /** 1.7 {@code RenderChainsaw} case EQUIPPED_FIRST_PERSON → {@code renderSword}. */
    private void renderFirstPerson(boolean leftHand) {
        GlStateManager.pushMatrix();
        if (leftHand) {
            GlStateManager.scale(-1.0f, 1.0f, 1.0f);
        }
        applyHandFirstPersonTransforms();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        modelChainsaw.render();
        GlStateManager.popMatrix();
    }

    /** 1.7 {@code RenderChainsaw} case EQUIPPED → {@code renderSwordF5}. */
    private void renderThirdPerson(boolean leftHand) {
        GlStateManager.pushMatrix();
        if (leftHand) {
            GlStateManager.scale(-1.0f, 1.0f, 1.0f);
        }
        applyHandThirdPersonTransforms();
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        modelChainsaw.render();
        GlStateManager.popMatrix();
    }

    private void renderFlat(Minecraft mc, ItemStack stack) {
        mc.getRenderItem().renderItem(stack, flatModel);
    }
}
