package com.gamingsmod.littlethings.common.container;

import com.gamingsmod.littlethings.common.tileentity.TileEntityItemElevator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerItemElevator extends Container
{
    private TileEntityItemElevator te;

    public ContainerItemElevator(IInventory player, TileEntityItemElevator tileEntityItemElevator)
    {
        this.te = tileEntityItemElevator;

        //Grid
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                this.addSlotToContainer(new Slot(te, x + y * 3, 30 + y * 18, 17 + x * 18));
            }
        }

        //Player inventory
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(player, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        //Player Hotbar
        for (int x = 0; x < 9; ++x) {
            this.addSlotToContainer((new Slot(player, x, 8 + x * 18, 142)));
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.te.isUseableByPlayer(playerIn);
    }

    /**
     * Take a stack from the specified inventory slot.
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 9)
            {
                if (!this.mergeItemStack(itemstack1, 10, 46, true))
                    return null;
            }
            else {
                if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
                    return null;
                }
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }
}