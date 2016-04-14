package com.gamingsmod.littlethings.common.gui.container;

import com.gamingsmod.littlethings.common.tileentity.TileEntityUnenchantingTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;

public class ContainerUnenchantingTable extends Container
{
    private TileEntityUnenchantingTable te;

    public ContainerUnenchantingTable(IInventory player, TileEntityUnenchantingTable te)
    {
        this.te = te;
        this.addSlotToContainer(new Slot(te, 0, 30, 35));
        this.addSlotToContainer(new SlotBook(te, 1, 66, 35));
        this.addSlotToContainer(new SlotEnchantBook(te, 2, 124, 35));

        for (int k = 0; k < 3; ++k)
            for (int i1 = 0; i1 < 9; ++i1) //INVENTORY
                this.addSlotToContainer(new Slot(player, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));

        for (int l = 0; l < 9; ++l) //HOTBAR
            this.addSlotToContainer(new Slot(player, l, 8 + l * 18, 142));
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);
        int slots = inventorySlots.size();

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 3) {
                if (!this.mergeItemStack(itemstack1, 3, slots, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
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

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return this.te.isUseableByPlayer(playerIn);
    }

    private class SlotBook extends Slot
    {
        public SlotBook(IInventory inventoryIn, int index, int xPosition, int yPosition)
        {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return stack.getItem() instanceof ItemBook;
        }
    }

    private class SlotEnchantBook extends Slot
    {
        public SlotEnchantBook(IInventory inventoryIn, int index, int xPosition, int yPosition)
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
