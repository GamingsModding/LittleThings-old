package com.gamingsmod.littlethings.common.gui.container;

import com.gamingsmod.littlethings.common.init.ModItems;
import com.gamingsmod.littlethings.common.recipe.custom.StoveRecipes;
import com.gamingsmod.littlethings.common.tileentity.TileEntityStove;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerStove extends Container
{
    private TileEntityStove te;
    private int cookTime;
    private int totalCookTime;
    private int furnaceBurnTime;
    private int currentItemBurnTime;

    public ContainerStove(IInventory playerInventory, TileEntityStove te)
    {
        this.te = te;
        this.addSlotToContainer(new Slot(te, 0, 54, 20));
        this.addSlotToContainer(new SlotPan(te, 1, 54, 40));
        this.addSlotToContainer(new SlotFurnaceFuel(te, 2, 54, 76));
        this.addSlotToContainer(new SlotResult(te, 3, 114, 57));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 107 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 165));
        }
    }

    public void onCraftGuiOpened(ICrafting listener)
    {
        super.onCraftGuiOpened(listener);
        listener.sendAllWindowProperties(this, this.te);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = this.crafters.get(i);

            if (this.cookTime != this.te.getField(2)) {
                icrafting.sendProgressBarUpdate(this, 2, this.te.getField(2));
            }

            if (this.furnaceBurnTime != this.te.getField(0)) {
                icrafting.sendProgressBarUpdate(this, 0, this.te.getField(0));
            }

            if (this.currentItemBurnTime != this.te.getField(1)) {
                icrafting.sendProgressBarUpdate(this, 1, this.te.getField(1));
            }

            if (this.totalCookTime != this.te.getField(3)) {
                icrafting.sendProgressBarUpdate(this, 3, this.te.getField(3));
            }
        }

        this.cookTime = this.te.getField(2);
        this.furnaceBurnTime = this.te.getField(0);
        this.currentItemBurnTime = this.te.getField(1);
        this.totalCookTime = this.te.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.te.setField(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (StoveRecipes.getInstance().getOutput(itemstack1) != null) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                } else if (itemstack1.getItem() == ModItems.Pan) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return null;
                    }
                } else if (TileEntityFurnace.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 2, 3, false)) {
                        return null;
                    }
                } else if (index >= 3 && index < 30) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return null;
                    }
                } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.te.isUseableByPlayer(playerIn);
    }

    private class SlotPan extends Slot
    {
        public SlotPan(IInventory inventoryIn, int index, int xPosition, int yPosition)
        {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return stack.getItem() == ModItems.Pan;
        }
    }

    private class SlotResult extends Slot
    {
        public SlotResult(IInventory inventoryIn, int index, int xPosition, int yPosition)
        {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return false;
        }
    }
}
