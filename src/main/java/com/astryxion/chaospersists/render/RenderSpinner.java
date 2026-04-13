/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.RenderSpinner
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.entity.Render
 *  net.minecraft.client.renderer.entity.RenderManager
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.astryxion.chaospersists.render;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class RenderSpinner
extends Render {
    public int spinItemIconIndex = 160;
    private static final ResourceLocation texture = new ResourceLocation("chaospersists", "textures/entity/spinners.png");

    public RenderSpinner(RenderManager manager) {
        super(manager);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)((float)par2), (float)((float)par4), (float)((float)par6));
        GL11.glEnable((int)32826);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.func_77026_a(this.spinItemIconIndex, par1Entity.rotationPitch);
        GL11.glDisable((int)32826);
        GL11.glPopMatrix();
    }

    private void func_77026_a(int par2, float par3) {
        float var3 = (float)(par2 % 16 * 16 + 0) / 256.0f;
        float var4 = (float)(par2 % 16 * 16 + 16) / 256.0f;
        float var5 = (float)(par2 / 16 * 16 + 0) / 256.0f;
        float var6 = (float)(par2 / 16 * 16 + 16) / 256.0f;
        float var7 = 1.0f;
        float var8 = 0.5f;
        float var9 = 0.25f;
        GL11.glRotatef((float)(180.0f - this.renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(- this.renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glRotatef((float)par3, (float)0.0f, (float)0.0f, (float)1.0f);
        BufferBuilder buf = Tessellator.getInstance().getBuffer();
        buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
        buf.pos((double)(0.0f - var8), (double)(0.0f - var9), 0.0).tex((double)var3, (double)var6).normal(0.0f, 1.0f, 0.0f).endVertex();
        buf.pos((double)(var7 - var8), (double)(0.0f - var9), 0.0).tex((double)var4, (double)var6).normal(0.0f, 1.0f, 0.0f).endVertex();
        buf.pos((double)(var7 - var8), (double)(var7 - var9), 0.0).tex((double)var4, (double)var5).normal(0.0f, 1.0f, 0.0f).endVertex();
        buf.pos((double)(0.0f - var8), (double)(var7 - var9), 0.0).tex((double)var3, (double)var5).normal(0.0f, 1.0f, 0.0f).endVertex();
        Tessellator.getInstance().draw();
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}

