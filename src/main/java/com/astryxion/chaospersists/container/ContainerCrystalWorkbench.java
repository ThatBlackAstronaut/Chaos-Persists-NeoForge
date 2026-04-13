/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.ContainerCrystalWorkbench
 *  com.astryxion.chaospersists.ChaosPersists
 *  net.minecraft.block.Block
 *  net.minecraft.entity.item.EntityItem
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.entity.player.InventoryPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.InventoryCraftResult
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.inventory.Slot
 *  net.minecraft.inventory.SlotCrafting
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.CraftingManager
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.container;

import com.astryxion.chaospersists.core.ChaosPersists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class ContainerCrystalWorkbench
extends Container {
    public InventoryCrafting craftMatrix;
    public IInventory craftResult;
    private World worldObj;
    private int posX;
    private int posY;
    private int posZ;

    public ContainerCrystalWorkbench(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
        int i1;
        int l;
        this.craftMatrix = new InventoryCrafting((Container)this, 3, 3);
        this.craftResult = new InventoryCraftResult();
        this.worldObj = par2World;
        this.posX = par3;
        this.posY = par4;
        this.posZ = par5;
        this.addSlotToContainer((Slot)new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
        for (l = 0; l < 3; ++l) {
            for (i1 = 0; i1 < 3; ++i1) {
                this.addSlotToContainer(new Slot((IInventory)this.craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
            }
        }
        for (l = 0; l < 3; ++l) {
            for (i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
            }
        }
        for (l = 0; l < 9; ++l) {
            this.addSlotToContainer(new Slot((IInventory)par1InventoryPlayer, l, 8 + l * 18, 142));
        }
        this.onCraftMatrixChanged((IInventory)this.craftMatrix);
    }

    public void onCraftMatrixChanged(IInventory par1IInventory) {
        net.minecraft.item.ItemStack result = net.minecraft.item.ItemStack.EMPTY;
        for (net.minecraft.item.crafting.IRecipe r : net.minecraft.item.crafting.CraftingManager.REGISTRY) {
            if (r.matches(this.craftMatrix, this.worldObj)) {
                result = r.getCraftingResult(this.craftMatrix);
                break;
            }
        }
        this.craftResult.setInventorySlotContents(0, result);
    }

    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        if (!this.worldObj.isRemote) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemstack = this.craftMatrix.removeStackFromSlot(i);
                if (itemstack.isEmpty()) continue;
                par1EntityPlayer.dropItem(itemstack, false);
            }
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockState(new net.minecraft.util.math.BlockPos(this.posX, this.posY, this.posZ)).getBlock() != ChaosPersists.CrystalWorkbenchBlock ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5, (double)this.posY + 0.5, (double)this.posZ + 0.5) <= 64.0;
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (par2 == 0) {
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return null;
                }
                slot.onSlotChange(itemstack1, itemstack);
            } else if (par2 >= 10 && par2 < 37 ? !this.mergeItemStack(itemstack1, 37, 46, false) : (par2 >= 37 && par2 < 46 ? !this.mergeItemStack(itemstack1, 10, 37, false) : !this.mergeItemStack(itemstack1, 10, 46, false))) {
                return null;
            }
            if (itemstack1.getCount() == 0) {
                slot.putStack(net.minecraft.item.ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemstack1.getCount() == itemstack.getCount()) {
                return null;
            }
            slot.onTake(par1EntityPlayer, itemstack1);
        }
        return itemstack;
    }

    public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
        return par2Slot.inventory != this.craftResult && super.canMergeSlot(par1ItemStack, par2Slot);
    }
}

