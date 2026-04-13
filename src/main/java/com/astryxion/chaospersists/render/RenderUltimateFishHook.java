package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.item.UltimateFishHook;
import com.astryxion.chaospersists.core.ChaosPersists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderUltimateFishHook extends Render<UltimateFishHook> {

    private static final ResourceLocation HOOK_TEXTURE = new ResourceLocation("textures/entity/fishing_hook.png");

    public RenderUltimateFishHook(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(UltimateFishHook entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5f, 0.5f, 0.5f);

        this.bindEntityTexture(entity);
        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        buffer.pos(-0.5, -0.5, 0.0).tex(0.0, 1.0).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(0.5, -0.5, 0.0).tex(1.0, 1.0).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(0.5, 0.5, 0.0).tex(1.0, 0.0).normal(0.0f, 1.0f, 0.0f).endVertex();
        buffer.pos(-0.5, 0.5, 0.0).tex(0.0, 0.0).normal(0.0f, 1.0f, 0.0f).endVertex();
        tessellator.draw();

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

        renderLine(entity, x, y, z, partialTicks);

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void renderLine(UltimateFishHook hook, double x, double y, double z, float partialTicks) {
        EntityPlayer angler = hook.getAngler();
        if (angler == null) {
            return;
        }

        int handDir = angler.getPrimaryHand() == EnumHandSide.RIGHT ? 1 : -1;
        ItemStack mainHand = angler.getHeldItemMainhand();
        boolean mainIsRod = !mainHand.isEmpty()
            && (mainHand.getItem() instanceof ItemFishingRod || mainHand.getItem() == ChaosPersists.MyUltimateFishingRod);
        if (!mainIsRod) {
            handDir = -handDir;
        }

        float swing = angler.getSwingProgress(partialTicks);
        float swingSin = MathHelper.sin(MathHelper.sqrt(swing) * (float)Math.PI);

        double anchorX;
        double anchorY;
        double anchorZ;

        if (this.renderManager.options != null
            && (this.renderManager.options.thirdPersonView > 0 || angler != Minecraft.getMinecraft().player)) {
            float bodyYaw = (angler.prevRenderYawOffset + (angler.renderYawOffset - angler.prevRenderYawOffset) * partialTicks)
                * 0.017453292F;
            double sinYaw = MathHelper.sin(bodyYaw);
            double cosYaw = MathHelper.cos(bodyYaw);
            double side = (double)handDir * 0.35D;
            anchorX = angler.prevPosX + (angler.posX - angler.prevPosX) * (double)partialTicks - cosYaw * side - sinYaw * 0.8D;
            anchorY = angler.prevPosY + (angler.posY - angler.prevPosY) * (double)partialTicks
                + (double)angler.getEyeHeight() - 0.45D;
            anchorZ = angler.prevPosZ + (angler.posZ - angler.prevPosZ) * (double)partialTicks - sinYaw * side + cosYaw * 0.8D;
        } else {
            Vec3d handOffset = new Vec3d((double)handDir * -0.36D, 0.03D, 0.35D);
            handOffset = handOffset.rotatePitch(-(angler.prevRotationPitch + (angler.rotationPitch - angler.prevRotationPitch) * partialTicks) * 0.017453292F);
            handOffset = handOffset.rotateYaw(-(angler.prevRotationYaw + (angler.rotationYaw - angler.prevRotationYaw) * partialTicks) * 0.017453292F);
            handOffset = handOffset.rotateYaw(swingSin * 0.5F);
            handOffset = handOffset.rotatePitch(-swingSin * 0.7F);
            anchorX = angler.prevPosX + (angler.posX - angler.prevPosX) * (double)partialTicks + handOffset.x;
            anchorY = angler.prevPosY + (angler.posY - angler.prevPosY) * (double)partialTicks + handOffset.y + (double)angler.getEyeHeight();
            anchorZ = angler.prevPosZ + (angler.posZ - angler.prevPosZ) * (double)partialTicks + handOffset.z;
        }

        double hookX = hook.prevPosX + (hook.posX - hook.prevPosX) * (double) partialTicks;
        double hookY = hook.prevPosY + (hook.posY - hook.prevPosY) * (double) partialTicks + 0.25D;
        double hookZ = hook.prevPosZ + (hook.posZ - hook.prevPosZ) * (double) partialTicks;

        double dx = anchorX - hookX;
        double dy = anchorY - hookY;
        double dz = anchorZ - hookZ;

        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableCull();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);

        int segments = 16;
        for (int i = 0; i <= segments; i++) {
            float t = (float)i / (float)segments;
            buffer.pos(
                x + dx * (double)t,
                y + dy * (double)(t * t + t) * 0.5D + 0.25D,
                z + dz * (double)t
            ).color(0, 0, 0, 255).endVertex();
        }
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
    }

    @Override
    protected ResourceLocation getEntityTexture(UltimateFishHook entity) {
        return HOOK_TEXTURE;
    }
}

