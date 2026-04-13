/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 *  com.astryxion.chaospersists.ContainerCrystalFurnace
 *  com.astryxion.chaospersists.TileEntityCrystalFurnace
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.ICrafting
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotFurnace
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.FurnaceRecipes
 */
package com.astryxion.chaospersists.container;

import com.astryxion.chaospersists.tileentity.TileEntityCrystalFurnace;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;

/**
 * Kept for compatibility with existing references, but now delegates fully to vanilla furnace logic.
 */
public class ContainerCrystalFurnace extends ContainerFurnace {

    public ContainerCrystalFurnace(InventoryPlayer playerInv, TileEntityCrystalFurnace furnace) {
        super(playerInv, furnace);
    }
}

