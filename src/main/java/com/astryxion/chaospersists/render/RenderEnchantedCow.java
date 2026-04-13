/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.CrystalCow
 *  com.astryxion.chaospersists.EnchantedCow
 *  com.astryxion.chaospersists.GoldCow
 *  com.astryxion.chaospersists.RedCow
 *  com.astryxion.chaospersists.RenderEnchantedCow
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelCow
 *  net.minecraft.client.renderer.entity.RenderLiving
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.CrystalCow;
import com.astryxion.chaospersists.entity.EnchantedCow;
import com.astryxion.chaospersists.entity.GoldCow;
import com.astryxion.chaospersists.entity.RedCow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderEnchantedCow
extends RenderLiving<RedCow> {
    protected ModelCow model;
    private static final ResourceLocation texture3 = new ResourceLocation("chaospersists", "textures/entity/crystal_cow.png");
    private static final ResourceLocation texture1 = new ResourceLocation("chaospersists", "textures/entity/red_cow.png");
    private static final ResourceLocation texture2 = new ResourceLocation("chaospersists", "textures/entity/gold_cow.png");
    /** Vanilla enchantment glint texture - used for Enchanted Cow overlay (same as 1.7.10 shouldRenderPass return 31). */
    private static final ResourceLocation ENCHANTED_GLINT = new ResourceLocation("minecraft", "textures/misc/enchanted_item_glint.png");

    public RenderEnchantedCow(RenderManager manager, ModelCow par1ModelBase, float par2) {
        super(manager, (ModelBase)par1ModelBase, par2);
        this.model = (ModelCow)this.mainModel;
        this.addLayer(new LayerRenderer<RedCow>() {
            @Override
            public void doRenderLayer(RedCow entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                if (!(entity instanceof EnchantedCow)) return;
                RenderEnchantedCow.this.bindTexture(RenderEnchantedCow.ENCHANTED_GLINT);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glPushMatrix();
                float t = (entity.ticksExisted + partialTicks) * 0.01f;
                GL11.glTranslatef(t, t * 0.5f, 0.0f);
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                GL11.glColor4f(0.5f, 0.25f, 0.8f, 1.0f);
                GL11.glDepthMask(false);
                RenderEnchantedCow.this.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GL11.glDepthMask(true);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glMatrixMode(GL11.GL_TEXTURE);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
            }
            @Override
            public boolean shouldCombineTextures() {
                return false;
            }
        });
    }

    @Override
    public void doRender(RedCow entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.renderEnchantedCow(entity, x, y, z, entityYaw, partialTicks);
    }

    public void renderEnchantedCow(RedCow entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(RedCow entity) {
        if (entity instanceof EnchantedCow) {
            return texture2;
        }
        if (entity instanceof GoldCow) {
            return texture2;
        }
        if (entity instanceof CrystalCow) {
            return texture3;
        }
        return texture1;
    }
}

