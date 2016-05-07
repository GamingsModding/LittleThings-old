package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.block.BlockStove;
import com.gamingsmod.littlethings.common.init.ModItems;
import com.gamingsmod.littlethings.common.recipe.custom.StoveRecipes;
import com.gamingsmod.littlethings.common.tileentity.base.ModTileInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityStove extends ModTileInventory implements ITickable
{
    private int stoveBurnTime;
    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime;
    private boolean burning;

    public TileEntityStove()
    {
        super("stove");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.stoveBurnTime = nbt.getInteger("BurnTime");
        this.cookTime = nbt.getInteger("CookTime");
        this.totalCookTime = nbt.getInteger("CookTimeTotal");
        this.currentItemBurnTime = TileEntityFurnace.getItemBurnTime(this.inventory[1]);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("BurnTime", this.stoveBurnTime);
        nbt.setInteger("CookTime", this.cookTime);
        nbt.setInteger("CookTimeTotal", this.totalCookTime);
    }

    @Override
    public int getSizeInventory()
    {
        return 4;
    }

    public int getField(int id)
    {
        switch (id) {
            case 0:
                return this.stoveBurnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.totalCookTime;
            default:
                return 0;
        }
    }

    public void setField(int id, int value)
    {
        switch (id) {
            case 0:
                this.stoveBurnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.totalCookTime = value;
        }
    }

    @Override
    public void update()
    {
        if (this.isBurning())
            for (Entity entity : worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB((double) pos.getX(), (double) (pos.getY() + 1), (double) pos.getZ(), (double) (pos.getX() + 1), (double) (pos.getY() + 2), (double) (pos.getZ() + 1))))
                entity.setFire(1);

        boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning()) {
            --this.stoveBurnTime;
        }

        if (!this.worldObj.isRemote) {
            if (this.isBurning() || this.inventory[2] != null && this.inventory[0] != null && this.inventory[1] != null) {
                if (!this.isBurning() && this.canSmelt() && this.inventory[1].getItem() == ModItems.Pan) {
                    this.currentItemBurnTime = this.stoveBurnTime = TileEntityFurnace.getItemBurnTime(this.inventory[2]);

                    if (this.isBurning()) {
                        flag1 = true;

                        if (this.inventory[2] != null) {
                            --this.inventory[2].stackSize;

                            if (this.inventory[2].stackSize == 0) {
                                this.inventory[2] = inventory[1].getItem().getContainerItem(inventory[2]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canSmelt() && this.inventory[1] != null) {
                    ++this.cookTime;
                    if (this.cookTime == this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.inventory[0]);
                        this.smeltItem();
                        this.inventory[1].setItemDamage(this.inventory[1].getItemDamage() + 1);
                        if (this.inventory[1].getItemDamage() == this.inventory[1].getMaxDamage())
                            this.inventory[1] = null;
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                BlockStove.setState(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    private void smeltItem()
    {
        if (this.canSmelt()) {
            ItemStack itemstack = StoveRecipes.getInstance().getOutput(this.inventory[0]);

            if (this.inventory[3] == null) {
                this.inventory[3] = itemstack.copy();
            } else if (this.inventory[3].getItem() == itemstack.getItem()) {
                this.inventory[3].stackSize += itemstack.stackSize;
            }

            --this.inventory[0].stackSize;

            if (this.inventory[0].stackSize <= 0) {
                this.inventory[0] = null;
            }
        }
    }

    private boolean canSmelt()
    {
        if (this.inventory[0] == null) {
            return false;
        } else {
            ItemStack itemstack = StoveRecipes.getInstance().getOutput(this.inventory[0]);
            if (itemstack == null) return false;
            if (this.inventory[3] == null) return true;
            if (!this.inventory[3].isItemEqual(itemstack)) return false;
            int result = inventory[3].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inventory[3].getMaxStackSize();
        }
    }

    public int getCookTime(ItemStack stack)
    {
        return 200;
    }

    public boolean isBurning()
    {
        return this.stoveBurnTime > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory p_174903_0_)
    {
        return p_174903_0_.getField(0) > 0;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return false;
    }

    public void setInventorySlotContents(int index, ItemStack stack)
    {
        boolean flag = stack != null && stack.isItemEqual(this.inventory[index]) && ItemStack.areItemStackTagsEqual(stack, this.inventory[index]);
        this.inventory[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if (index == 0 && !flag) {
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }
}
