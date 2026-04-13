/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.Boyfriend
 *  com.astryxion.chaospersists.RenderBoyfriend
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.model.ModelBiped
 *  net.minecraft.client.renderer.entity.RenderBiped
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.util.ResourceLocation
 */
package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.Boyfriend;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBoyfriend
extends RenderBiped {
    protected ModelBiped model;

    public RenderBoyfriend(RenderManager manager, ModelBiped par1ModelBase, float par2) {
        super(manager, par1ModelBase, par2);
        this.model = (ModelBiped)this.mainModel;
        this.addLayer(new LayerBipedArmor(this) {
            @Override
            protected void initArmor() {
                this.modelLeggings = new ModelBiped(0.5F);
                this.modelArmor = new ModelBiped(1.0F);
            }
        });
    }

    public void renderBoyfriend(Boyfriend par1EntityBoyfriend, double par2, double par4, double par6, float par8, float par9) {
        super.doRender((EntityLiving)par1EntityBoyfriend, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderBoyfriend((Boyfriend)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderBoyfriend((Boyfriend)par1Entity, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Boyfriend g = (Boyfriend)entity;
        return g.getTexture();
    }
}

