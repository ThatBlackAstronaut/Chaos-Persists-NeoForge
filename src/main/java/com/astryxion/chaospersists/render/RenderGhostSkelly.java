package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.GhostSkelly;
import com.astryxion.chaospersists.model.ModelGhostSkelly;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;

public class RenderGhostSkelly extends MobRenderer<GhostSkelly, ModelGhostSkelly> {
    private static final net.minecraft.resources.ResourceLocation TEXTURE =
            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                    "chaospersists", "textures/entity/ghostskellytexture.png");
    private final float scale;

    public RenderGhostSkelly(EntityRendererProvider.Context context, ModelGhostSkelly model, float shadow, float scale) {
        super(context, model, shadow * scale);
        this.scale = scale;
    }

    @Override
    protected void scale(GhostSkelly entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(this.scale, this.scale, this.scale);
    }

    @Override
    public net.minecraft.resources.ResourceLocation getTextureLocation(GhostSkelly entity) {
        return TEXTURE;
    }

    @Override
    public void render(
            GhostSkelly entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight) {
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        this.model.renderToBuffer(
                poseStack, vertexconsumer, packedLight, net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY, 0.75F, 0.75F, 0.75F, 0.25F);
    }
}
