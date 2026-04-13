/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.RenderShoe
 *  com.astryxion.chaospersists.RenderSpinner
 *  com.astryxion.chaospersists.Shoes
 *  net.minecraft.entity.Entity
 */
package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.render.RenderSpinner;
import com.astryxion.chaospersists.item.Shoes;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public class RenderShoe
extends RenderSpinner {
    public RenderShoe(RenderManager manager) {
        super(manager);
    }

    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        if (par1Entity instanceof Shoes) {
            Shoes var2 = (Shoes)par1Entity;
            this.spinItemIconIndex = var2.getShoeId();
        }
        super.doRender(par1Entity, par2, par4, par6, par8, par9);
    }
}

