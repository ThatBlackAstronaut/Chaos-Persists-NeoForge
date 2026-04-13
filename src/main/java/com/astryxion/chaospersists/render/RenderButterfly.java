/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EntityButterfly
 *  com.astryxion.chaospersists.EntityLunaMoth
 *  com.astryxion.chaospersists.ModelButterfly
 *  com.astryxion.chaospersists.Mothra
 *  com.astryxion.chaospersists.RenderButterfly
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.entity.RenderLiving
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.EntityButterfly;
import com.astryxion.chaospersists.entity.EntityLunaMoth;
import com.astryxion.chaospersists.model.ModelButterfly;
import com.astryxion.chaospersists.entity.Mothra;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderButterfly
extends RenderLiving {
    protected ModelButterfly model;
    private float scale = 1.0f;
    private static final ResourceLocation texture = new ResourceLocation("textures/entity/creeper/creeper_armor.png");

    public RenderButterfly(net.minecraft.client.renderer.entity.RenderManager manager, ModelButterfly par1ModelBase, float par2, float par3) {
        super(manager, (ModelBase)par1ModelBase, par2 * par3);
        this.model = (ModelButterfly)this.mainModel;
        this.scale = par3;
        this.addLayer(new LayerRenderer<EntityLivingBase>() {
            @Override
            public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                boolean doit = false;
                if (entity instanceof Mothra) {
                    doit = true;
                } else if (entity instanceof EntityLunaMoth && ((EntityLunaMoth)entity).moth_type == 0) {
                    doit = true;
                }
                if (doit) {
                    RenderButterfly.this.bindTexture(RenderButterfly.texture);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glLoadIdentity();
                    float var5 = (entity.ticksExisted + partialTicks) * 0.01f;
                    GL11.glTranslatef(var5, var5, 0.0f);
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glColor4f(0.5f, 0.5f, 0.5f, 1.0f);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
                    RenderButterfly.this.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                }
            }
            @Override
            public boolean shouldCombineTextures() {
                return false;
            }
        });
    }

    public void renderButterfly(EntityButterfly par1EntityButterfly, double par2, double par4, double par6, float par8, float par9) {
        super.doRender((EntityLiving)par1EntityButterfly, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderButterfly((EntityButterfly)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderButterfly((EntityButterfly)par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(EntityButterfly par1Entity, float par2) {
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((EntityButterfly)par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        EntityButterfly a = (EntityButterfly)entity;
        return a.getTexture(a);
    }
}

