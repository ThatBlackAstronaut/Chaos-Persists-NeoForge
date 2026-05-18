package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.Kyuubi;
import com.astryxion.chaospersists.model.ModelKyuubi;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RenderKyuubi extends MobRenderer<Kyuubi, ModelKyuubi> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("chaospersists", "textures/entity/kyuubi.png");
    private final float scale;

    public RenderKyuubi(EntityRendererProvider.Context context, ModelKyuubi model, float shadow, float scale) {
        super(context, model, shadow * scale);
        this.scale = scale;
    }

    @Override
    public void render(
            Kyuubi entity,
            float entityYaw,
            float partialTicks,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight) {
        poseStack.pushPose();
        this.scale(entity, poseStack, partialTicks);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0f));
        float bodyRot = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
        float headRot = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
        this.model.setupAnim(
                entity,
                entity.walkAnimation.position(partialTicks),
                entity.walkAnimation.speed(partialTicks),
                entity.tickCount + partialTicks,
                headRot - bodyRot,
                entity.getViewXRot(partialTicks));
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(entity)));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
    }

    @Override
    protected void scale(Kyuubi entity, PoseStack poseStack, float partialTick) {
        poseStack.scale(this.scale, this.scale, this.scale);
    }

    @Override
    public ResourceLocation getTextureLocation(Kyuubi entity) {
        return TEXTURE;
    }
}
