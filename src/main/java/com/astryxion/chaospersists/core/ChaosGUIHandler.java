/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fml.common.network.IGuiHandler
 *  com.astryxion.chaospersists.ContainerCrystalFurnace
 *  com.astryxion.chaospersists.ContainerCrystalWorkbench
 *  com.astryxion.chaospersists.CrystalFurnaceGUI
 *  com.astryxion.chaospersists.CrystalWorkbenchGUI
 *  com.astryxion.chaospersists.ChaosGUIHandler
 *  com.astryxion.chaospersists.TileEntityCrystalFurnace
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.core;

import net.minecraftforge.fml.common.network.IGuiHandler;
import com.astryxion.chaospersists.container.ContainerCrystalWorkbench;
import com.astryxion.chaospersists.util.CrystalWorkbenchGUI;
import com.astryxion.chaospersists.tileentity.TileEntityCrystalFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.client.gui.inventory.GuiFurnace;

public class ChaosGUIHandler
implements IGuiHandler {
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
        switch (ID) {
            case 0: {
                if (!(tileEntity instanceof TileEntityCrystalFurnace)) break;
                return new ContainerFurnace(player.inventory, (TileEntityCrystalFurnace)tileEntity);
            }
            case 1: {
                return new ContainerCrystalWorkbench(player.inventory, world, x, y, z);
            }
        }
        return null;
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new net.minecraft.util.math.BlockPos(x, y, z));
        switch (ID) {
            case 0: {
                if (!(tileEntity instanceof TileEntityCrystalFurnace)) break;
                return new GuiFurnace(player.inventory, (TileEntityCrystalFurnace)tileEntity);
            }
            case 1: {
                return new CrystalWorkbenchGUI(player.inventory, world, x, y, z);
            }
        }
        return null;
    }
}

