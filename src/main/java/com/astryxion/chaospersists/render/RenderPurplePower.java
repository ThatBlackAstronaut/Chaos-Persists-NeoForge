package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.item.PurplePower;
import com.astryxion.chaospersists.model.ModelPurplePower;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderPurplePower extends LivingEntityRenderer<PurplePower, ModelPurplePower> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/purplepowertexture.png");
    private static final ResourceLocation TEXTURE2 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/purplepowertexture2.png");
    private static final ResourceLocation TEXTURE3 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/purplepowertexture3.png");
    private static final ResourceLocation TEXTURE4 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/purplepowertexture4.png");
    private static final ResourceLocation TEXTURE10 =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/purplepowertexture10.png");
    private final float scale;

    public RenderPurplePower(
            EntityRendererProvider.Context context, ModelPurplePower model, float shadow, float scale) {
        super(context, model, shadow * scale);
        this.scale = scale;
    }

    @Override
    protected void scale(PurplePower entity, PoseStack poseStack, float partialTick) {
        float localScale = this.scale;
        if (entity.getPurpleType() != 0) {
            localScale = 0.55f;
        }
        poseStack.scale(localScale, localScale, localScale);
    }

    @Override
    protected boolean shouldShowName(PurplePower entity) {
        return false;
    }

    @Override
    public ResourceLocation getTextureLocation(PurplePower entity) {
        return switch (entity.getPurpleType()) {
            case 1 -> TEXTURE2;
            case 2 -> TEXTURE3;
            case 3 -> TEXTURE4;
            case 10 -> TEXTURE10;
            default -> TEXTURE;
        };
    }
}
