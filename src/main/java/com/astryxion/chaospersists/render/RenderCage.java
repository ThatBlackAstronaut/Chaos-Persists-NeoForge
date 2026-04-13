/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.EntityCage
 *  com.astryxion.chaospersists.RenderCage
 *  com.astryxion.chaospersists.RenderSpinner
 *  net.minecraft.entity.Entity
 */
package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.EntityCage;
import com.astryxion.chaospersists.render.RenderSpinner;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public class RenderCage
extends RenderSpinner {
    public RenderCage(RenderManager manager) {
        super(manager);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.spinItemIconIndex = 160;
        if (par1Entity instanceof EntityCage) {
            EntityCage var2 = (EntityCage)par1Entity;
            this.spinItemIconIndex = var2.getCageIndex();
        }
        super.doRender(par1Entity, par2, par4, par6, par8, par9);
    }
}

