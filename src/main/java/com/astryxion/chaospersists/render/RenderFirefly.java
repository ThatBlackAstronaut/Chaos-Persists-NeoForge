package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.Firefly;
import com.astryxion.chaospersists.model.ModelFirefly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderFirefly extends MobRenderer<Firefly, ModelFirefly> {
    private final float scale;

    public RenderFirefly(EntityRendererProvider.Context context, ModelFirefly model, float shadow, float scale) {
        super(context, model, shadow * scale);
        this.scale = scale;
    }

    @Override
    protected void scale(Firefly entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(this.scale, this.scale, this.scale);
    }

    @Override
    public ResourceLocation getTextureLocation(Firefly entity) {
        return entity.getTexture(entity);
    }

    @Override
    public void render(
            Firefly entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
        this.model.renderTailLight(
                poseStack, vertexconsumer, entity, packedLight, net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
