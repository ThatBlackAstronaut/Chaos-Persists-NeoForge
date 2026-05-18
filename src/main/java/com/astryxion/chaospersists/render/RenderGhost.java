package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.Ghost;
import com.astryxion.chaospersists.model.ModelGhost;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RenderGhost extends MobRenderer<Ghost, ModelGhost> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/ghosttexture.png");
    private final float scale;

    public RenderGhost(EntityRendererProvider.Context context, ModelGhost model, float shadow, float scale) {
        super(context, model, shadow * scale);
        this.scale = scale;
    }

    @Override
    protected void scale(Ghost entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(this.scale, this.scale, this.scale);
    }

    @Override
    public ResourceLocation getTextureLocation(Ghost entity) {
        return TEXTURE;
    }

    @Override
    public void render(
            Ghost entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight) {
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY, 0.75F, 0.75F, 0.75F, 0.25F);
    }
}
