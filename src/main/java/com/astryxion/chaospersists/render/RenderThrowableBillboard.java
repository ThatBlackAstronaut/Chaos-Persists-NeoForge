package com.astryxion.chaospersists.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

/**
 * Same approach as {@link RenderThrownRock}: single flat texture billboard (full 16×16 sheet UVs),
 * which is reliable in 1.12 unlike {@link net.minecraft.client.renderer.RenderItem} inside entity passes.
 */
@SideOnly(Side.CLIENT)
public final class RenderThrowableBillboard extends Render<Entity> {

    private final ResourceLocation texture;

    public RenderThrowableBillboard(RenderManager manager, ResourceLocation texture) {
        super(manager);
        this.texture = texture;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.bindTexture(this.texture);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glEnable(32826);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawBillboardQuad(0, entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks);
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }

    /** par2 = sprite index in 16×16 grid; 0 uses the whole texture (same math as {@link RenderThrownRock}). */
    private void drawBillboardQuad(int spriteIndex, float spinDegrees) {
        float u0 = (float) (spriteIndex % 16 * 16) / 16.0f;
        float u1 = (float) (spriteIndex % 16 * 16 + 16) / 16.0f;
        float v0 = (float) (spriteIndex / 16 * 16) / 16.0f;
        float v1 = (float) (spriteIndex / 16 * 16 + 16) / 16.0f;
        float size = 1.0f;
        float hx = 0.5f;
        float hy = 0.25f;
        GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(spinDegrees, 0.0f, 0.0f, 1.0f);
        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
        buf.pos((double) (0.0f - hx), (double) (0.0f - hy), 0.0).tex((double) u0, (double) v1).normal(0.0f, 1.0f, 0.0f).endVertex();
        buf.pos((double) (size - hx), (double) (0.0f - hy), 0.0).tex((double) u1, (double) v1).normal(0.0f, 1.0f, 0.0f).endVertex();
        buf.pos((double) (size - hx), (double) (size - hy), 0.0).tex((double) u1, (double) v0).normal(0.0f, 1.0f, 0.0f).endVertex();
        buf.pos((double) (0.0f - hx), (double) (size - hy), 0.0).tex((double) u0, (double) v0).normal(0.0f, 1.0f, 0.0f).endVertex();
        Tessellator.getInstance().draw();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.texture;
    }
}
