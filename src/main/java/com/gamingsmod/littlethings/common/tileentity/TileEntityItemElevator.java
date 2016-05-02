package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.tileentity.base.ModTileInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

// Code adapted from BedrockMiner because I am an idiot
// @source http://bedrockminer.jimdo.com/modding-tutorials/advanced-modding/tile-entity-with-inventory/
public class TileEntityItemElevator extends ModTileInventory implements ITickable
{
    public TileEntityItemElevator()
    {
        super("itemElevator");
    }

    @Override
    public int getSizeInventory()
    {
        return 9;
    }

    private int previousRedstone = 0;
    private BlockPos foundInventory = null;
    private Block oldBlock = null;

    @Override
    public void update()
    {
        int currentRedstone = worldObj.getStrongPower(getPos());

        if (previousRedstone == 0 && currentRedstone != 0) {
            if (!worldObj.isRemote) this.updateElevator();
            else this.updatePartials();
        }
        previousRedstone = currentRedstone;
    }

    private void updatePartials()
    {
        for (int j = 1; j < 64; j++) {
            BlockPos added = getPos().add(0, j, 0);
            if (worldObj.getBlockState(added).getBlock() instanceof BlockGlass || worldObj.getBlockState(added).getBlock() instanceof BlockStainedGlass)
                for (int i = 10; i >= 1; i--)
                    worldObj.spawnParticle(EnumParticleTypes.PORTAL,
                            added.getX() + 0.5, added.getY() - 1 + i / 10, added.getZ() + 0.5,
                            0, 0.3, 0);
            else break;
        }
    }

    private void updateElevator()
    {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getStackInSlot(i);

            if (stack != null) {
                IInventory toInventory = findInventory();

                if (toInventory != null) {
                    ItemStack move = new ItemStack(stack.getItem(), 1, stack.getItemDamage(), stack.getTagCompound());
                    boolean moved = false;

                    for (int j = 0; j < (toInventory.getSizeInventory()); j++) {
                        if (toInventory.isItemValidForSlot(j, move)) {
                            //Move the stack
                            ItemStack currentStack = toInventory.getStackInSlot(j);
                            if (currentStack != null &&
                                    currentStack.getItem() == move.getItem() &&
                                    currentStack.getMetadata() == move.getMetadata() &&
                                    currentStack.getTagCompound() == move.getTagCompound() &&
                                    currentStack.stackSize < currentStack.getMaxStackSize() &&
                                    inventory[i] != null) {

                                inventory[i].stackSize--;

                                if (inventory[i].stackSize <= 0)
                                    inventory[i] = null;

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
                                inventory[i].stackSize--;
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

    private IInventory findInventory()
    {
        if (this.foundInventory == null || this.oldBlock == null) {
            return searchForInventory();
        }
        if (this.oldBlock != worldObj.getBlockState(this.foundInventory).getBlock()) {
            return searchForInventory();
        }

        return (IInventory) worldObj.getTileEntity(this.foundInventory);
    }

    private IInventory searchForInventory()
    {
        for (int j = 1; j < 64; j++) {
            TileEntity foundte = worldObj.getTileEntity(getPos().add(0, j, 0));
            Block foundBlock = worldObj.getBlockState(getPos().add(0, j, 0)).getBlock();

            if (foundte != null && foundte instanceof IInventory) {
                this.foundInventory = getPos().add(0, j, 0);
                this.oldBlock = foundBlock;
                return (IInventory) foundte;
            } else if (foundte == null && !((foundBlock instanceof BlockGlass) || foundBlock instanceof BlockStainedGlass)) {
                this.foundInventory = null;
                this.oldBlock = null;
                return null;
            }
        }

        this.foundInventory = null;
        this.oldBlock = null;
        return null;
    }
}
