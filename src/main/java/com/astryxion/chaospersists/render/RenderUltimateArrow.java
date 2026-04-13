package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.item.UltimateArrow;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderUltimateArrow extends RenderArrow<UltimateArrow> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/entity/projectiles/arrow.png");

    public RenderUltimateArrow(RenderManager manager) {
        super(manager);
    }

    @Override
    protected ResourceLocation getEntityTexture(UltimateArrow entity) {
        return TEXTURE;
    }
}
