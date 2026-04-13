/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.ModelRockBase
 *  com.astryxion.chaospersists.RenderRockBase
 *  com.astryxion.chaospersists.RockBase
 *  net.minecraft.client.model.ModelBase
 *  net.minecraft.client.renderer.entity.RenderLiving
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLiving
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.model.ModelRockBase;
import com.astryxion.chaospersists.entity.RockBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderRockBase
extends RenderLiving {
    protected ModelRockBase model;
    private float scale = 1.0f;
    private static final ResourceLocation texture1 = new ResourceLocation("chaospersists", "textures/entity/rocktexture.png");
    private static final ResourceLocation texture2 = new ResourceLocation("chaospersists", "textures/entity/rocktexture.png");
    private static final ResourceLocation texture3 = new ResourceLocation("chaospersists", "textures/entity/rockredtexture.png");
    private static final ResourceLocation texture4 = new ResourceLocation("chaospersists", "textures/entity/rockgreentexture.png");
    private static final ResourceLocation texture5 = new ResourceLocation("chaospersists", "textures/entity/rockbluetexture.png");
    private static final ResourceLocation texture6 = new ResourceLocation("chaospersists", "textures/entity/rockpurpletexture.png");
    private static final ResourceLocation texture7 = new ResourceLocation("chaospersists", "textures/entity/rocktexture.png");
    private static final ResourceLocation texture8 = new ResourceLocation("chaospersists", "textures/entity/rocktnttexture.png");
    private static final ResourceLocation texture9 = new ResourceLocation("chaospersists", "textures/entity/rockcrystaltexture.png");
    private static final ResourceLocation texture10 = new ResourceLocation("chaospersists", "textures/entity/rockcrystalgreentexture.png");
    private static final ResourceLocation texture11 = new ResourceLocation("chaospersists", "textures/entity/rockcrystalbluetexture.png");
    private static final ResourceLocation texture12 = new ResourceLocation("chaospersists", "textures/entity/rockcrystaltnttexture.png");

    public RenderRockBase(net.minecraft.client.renderer.entity.RenderManager manager, ModelRockBase par1ModelBase, float par2, float par3) {
        super(manager, (ModelBase)par1ModelBase, par2 * par3);
        this.model = (ModelRockBase)this.mainModel;
        this.scale = par3;
    }

    public void renderRockBase(RockBase par1EntityRockBase, double par2, double par4, double par6, float par8, float par9) {
        super.doRender((EntityLiving)par1EntityRockBase, par2, par4, par6, par8, par9);
    }

    public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
        this.renderRockBase((RockBase)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderRockBase((RockBase)par1Entity, par2, par4, par6, par8, par9);
    }

    protected void preRenderScale(RockBase par1Entity, float par2) {
        GL11.glScalef((float)this.scale, (float)this.scale, (float)this.scale);
    }

    protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2) {
        this.preRenderScale((RockBase)par1EntityLiving, par2);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        RockBase r = (RockBase)entity;
        int i = r.getRockType();
        if (i == 1) {
            return texture1;
        }
        if (i == 2) {
            return texture2;
        }
        if (i == 3) {
            return texture3;
        }
        if (i == 4) {
            return texture4;
        }
        if (i == 5) {
            return texture5;
        }
        if (i == 6) {
            return texture6;
        }
        if (i == 7) {
            return texture7;
        }
        if (i == 8) {
            return texture8;
        }
        if (i == 9) {
            return texture9;
        }
        if (i == 10) {
            return texture10;
        }
        if (i == 11) {
            return texture11;
        }
        if (i == 12) {
            return texture12;
        }
        return texture1;
    }
}

