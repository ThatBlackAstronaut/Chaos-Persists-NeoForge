package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.Fairy;
import com.astryxion.chaospersists.model.ModelFairy;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class RenderFairy extends MobRenderer<Fairy, ModelFairy> {
    private final float scale;

    public RenderFairy(EntityRendererProvider.Context context, ModelFairy model, float shadow, float scale) {
        super(context, model, shadow * scale);
        this.scale = scale;
    }

    @Override
    protected void scale(Fairy entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(this.scale, this.scale, this.scale);
    }

    @Override
    public ResourceLocation getTextureLocation(Fairy entity) {
        return entity.getTexture(entity);
    }

    @Override
    public void render(
            Fairy entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutout(this.getTextureLocation(entity)));
        int bodyLight = entity.getBlink() > 1.0f ? 15728880 : packedLight;
        this.model.renderBody(
                poseStack, vertexconsumer, bodyLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}
