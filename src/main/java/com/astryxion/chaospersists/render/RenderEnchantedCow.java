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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
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
                if (!(entity instanceof EnchantedCow)) {
                    return;
                }
                // Match vanilla item enchant glint: GlStateManager + depth equality so the glint only sits on
                // surfaces already drawn for this entity (avoids solid purple "shell"). Always restore state so
                // other mobs in the same frame are not tinted by leaked blend/color (raw GL11 breaks 1.12 tracking).
                RenderEnchantedCow.this.bindTexture(RenderEnchantedCow.ENCHANTED_GLINT);

                GlStateManager.enableBlend();
                GlStateManager.depthMask(false);
                GlStateManager.depthFunc(GL11.GL_EQUAL);
                GlStateManager.disableLighting();
                GlStateManager.tryBlendFuncSeparate(
                        SourceFactor.SRC_COLOR, DestFactor.ONE,
                        SourceFactor.ONE, DestFactor.ZERO);
                GlStateManager.color(0.38F, 0.19F, 0.608F, 1.0F);

                GlStateManager.matrixMode(GL11.GL_TEXTURE);
                GlStateManager.pushMatrix();
                float scroll = (entity.ticksExisted + partialTicks) * 0.01F;
                GlStateManager.translate(scroll, scroll * 0.5F, 0.0F);
                GlStateManager.matrixMode(GL11.GL_MODELVIEW);

                RenderEnchantedCow.this.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

                GlStateManager.matrixMode(GL11.GL_TEXTURE);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(GL11.GL_MODELVIEW);

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.tryBlendFuncSeparate(
                        SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA,
                        SourceFactor.ONE, DestFactor.ZERO);
                GlStateManager.enableLighting();
                GlStateManager.depthMask(true);
                GlStateManager.depthFunc(GL11.GL_LEQUAL);
                GlStateManager.disableBlend();
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

