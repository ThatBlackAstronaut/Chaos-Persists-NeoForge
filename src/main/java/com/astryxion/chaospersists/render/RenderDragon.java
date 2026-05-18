package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.Dragon;
import com.astryxion.chaospersists.model.ModelDragon;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderDragon extends MobRenderer<Dragon, ModelDragon> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/dragon.png");
    private static final ResourceLocation TEXTURE_WHITE =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/whitedragon.png");
    private final float scale;

    public RenderDragon(EntityRendererProvider.Context context, ModelDragon model, float shadow, float scale) {
        super(context, model, shadow * scale);
        this.scale = scale;
    }

    @Override
    protected void scale(Dragon entity, PoseStack poseStack, float partialTick) {
        float s = this.scale;
        poseStack.scale(s, s, s);
    }

    @Override
    public ResourceLocation getTextureLocation(Dragon entity) {
        if (entity.getDragonType() != 0) {
            return TEXTURE_WHITE;
        }
        return TEXTURE;
    }
}
