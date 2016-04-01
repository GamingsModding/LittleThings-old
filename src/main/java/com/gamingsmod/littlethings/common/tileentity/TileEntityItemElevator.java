package com.gamingsmod.littlethings.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

// Code adapted from BedrockMiner because I am an idiot
// @source http://bedrockminer.jimdo.com/modding-tutorials/advanced-modding/tile-entity-with-inventory/
public class TileEntityItemElevator extends TileEntity implements IInventory, ITickable
{
    private ItemStack[] inventory;
    private String customName;

    public TileEntityItemElevator()
    {
        this.inventory = new ItemStack[this.getSizeInventory()];
    }

    public String getCustomName()
    {
        return this.customName;
    }

    public void setCustomName(String customName)
    {
        this.customName = customName;
    }

    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.itemElevator";
    }

    @Override
    public boolean hasCustomName()
    {
        return this.customName != null && !this.customName.equals("");
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
    }

    @Override
    public int getSizeInventory()
    {
        return 9;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        if (index < 0 || index >= this.getSizeInventory())
            return null;
        return this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if (this.getStackInSlot(index) != null) {
            ItemStack itemstack;

            if (this.getStackInSlot(index).stackSize <= count){
                itemstack = this.getStackInSlot(index);
                this.setInventorySlotContents(index, null);
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.getStackInSlot(index).splitStack(count);

                if (this.getStackInSlot(index).stackSize <= 0) {
                    this.setInventorySlotContents(index, null);
                } else {
                    //Just to show that changes happened
                    this.setInventorySlotContents(index, this.getStackInSlot(index));
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack stack = this.getStackInSlot(index);
        this.setInventorySlotContents(index, null);
        return stack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        if (index < 0 || index >= this.getSizeInventory())
            return;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();

        if (stack != null && stack.stackSize == 0)
            stack = null;

        this.inventory[index] = stack;
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.pos.add(0.5, 0.5, 0.5)) <= 64;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return true;
    }

    @Override
    public int getField(int id)
    {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < this.getSizeInventory(); i++)
            this.setInventorySlotContents(i, null);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        NBTTagList list = new NBTTagList();
        for (int i = 0; i < this.getSizeInventory(); ++i) {
            if (this.getStackInSlot(i) != null) {
                NBTTagCompound stackTag = new NBTTagCompound();
                stackTag.setByte("Slot", (byte) i);
                this.getStackInSlot(i).writeToNBT(stackTag);
                list.appendTag(stackTag);
            }
        }
        nbt.setTag("Items", list);

        if (this.hasCustomName()) {
            nbt.setString("CustomName", this.getCustomName());
        }
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        NBTTagList list = nbt.getTagList("Items", 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound stackTag = list.getCompoundTagAt(i);
            int slot = stackTag.getByte("Slot") & 255;
            this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
        }

        if (nbt.hasKey("CustomName", 8)) {
            this.setCustomName(nbt.getString("CustomName"));
        }
    }

    private int previousRedstone = 0;

    @Override
    public void update() {
        if (!worldObj.isRemote) {
            int currentRedstone = worldObj.getStrongPower(getPos());

            if (previousRedstone == 0 && currentRedstone!= 0) {

                for (int i = 0; i < getSizeInventory(); i++) {
                    ItemStack stack = getStackInSlot(i);

                    if (stack != null) {
                        IInventory toInventory = null;

                        for (int j = 1; j < 64; j++) {
                            TileEntity foundte = worldObj.getTileEntity(getPos().add(0, j, 0));
                            Block foundBlock = worldObj.getBlockState(getPos().add(0, j, 0)).getBlock();

                            if (foundte != null && foundte instanceof IInventory) {
                                toInventory = (IInventory) foundte;
                                break;
                            } else if (foundte == null && !((foundBlock instanceof BlockGlass) || foundBlock instanceof BlockStainedGlass)) {
                                break;
                            }
                        }

                        if (toInventory != null) {
                            ItemStack move = new ItemStack(stack.getItem(), 1, stack.getItemDamage(), stack.getTagCompound());
                            boolean moved = false;

                            for (int j = 0; j < (toInventory.getSizeInventory()); j++) {
                                if (toInventory.isItemValidForSlot(j, move)) {
                                    //Move the stack
                                    ItemStack currentStack = toInventory.getStackInSlot(j);

                                    if (currentStack != null && currentStack.getItem() == move.getItem() && currentStack.stackSize < 64 && inventory[i] != null) {
                                        if (inventory[i].stackSize <= 0)
                                            inventory[i] = null;
                                        else inventory[i].stackSize--;

                                        if (currentStack.getMaxStackSize() > currentStack.stackSize)
                                            currentStack.stackSize++;
                                        else continue;

                                        moved = true;
                                        break;
                                    }
                                }
                            }

                            if (!moved) {
                                for (int j = 0; j < (toInventory.getSizeInventory()); j++) {
                                    if (toInventory.getStackInSlot(j) == null && inventory[i] != null) {
                                        if (inventory[i].stackSize <= 0)
                                            inventory[i] = null;

                                        toInventory.setInventorySlotContents(j, move);
                                        break;
                                    }
                                }
                            }
                        }

                        break;
                    }
                }
            }

            previousRedstone = currentRedstone;
        }
    }
}
