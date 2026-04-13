/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ContainerCrystalFurnace
 *  com.astryxion.chaospersists.CrystalFurnaceGUI
 *  com.astryxion.chaospersists.TileEntityCrystalFurnace
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package com.astryxion.chaospersists.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.astryxion.chaospersists.tileentity.TileEntityCrystalFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.client.gui.inventory.GuiFurnace;

@SideOnly(value=Side.CLIENT)
public class CrystalFurnaceGUI
extends GuiFurnace {

    public CrystalFurnaceGUI(InventoryPlayer par1InventoryPlayer, TileEntityCrystalFurnace par2TileEntityCrystalFurnace) {
        super(par1InventoryPlayer, par2TileEntityCrystalFurnace);
    }
}

