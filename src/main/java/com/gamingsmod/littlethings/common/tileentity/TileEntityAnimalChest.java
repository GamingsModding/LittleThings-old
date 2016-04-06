package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.block.BlockAnimalChest;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityAnimalChest extends TileEntity implements IInventory, ITickable
{
    private ItemStack[] inventory;
    private String customName;
    public float lidAngle;
    /** The angle of the ender chest lid last tick */
    public float prevLidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;

    public TileEntityAnimalChest()
    {
        inventory = new ItemStack[this.getSizeInventory()];
    }

    @Override
    public int getSizeInventory()
    {
        return 27;
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
    public void openInventory(EntityPlayer player)
    {
        openChest();
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        closeChest();
    }

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
    public void setField(int id, int value)
    {
    }

    @Override
    public int getFieldCount()
    {
        return 0;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < getSizeInventory(); i++)
            this.setInventorySlotContents(i, null);
    }

    @Override
    public void update()
    {
        this.prevLidAngle = this.lidAngle;
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        float f = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
        {
            double d0 = (double)i + 0.5D;
            double d1 = (double)k + 0.5D;

            SoundEvent open = null;
            switch (getAnimal()) {
                case "cow":
                    open = SoundEvents.entity_cow_ambient;
                    break;
                case "chicken":
                    open = SoundEvents.entity_chicken_ambient;
                    break;
                case "pig":
                    open = SoundEvents.entity_pig_ambient;
                    break;
                case "sheep":
                    open = SoundEvents.entity_sheep_ambient;
                    break;
            }
            this.worldObj.playSound((EntityPlayer)null, d0, (double)j + 0.5D, d1, open, SoundCategory.BLOCKS, 0.75F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
        {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0)
            {
                this.lidAngle += f;
            }
            else
            {
                this.lidAngle -= f;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f1 = 0.5F;

            if (this.lidAngle < f1 && f2 >= f1)
            {
                double d3 = (double)i + 0.5D;
                double d2 = (double)k + 0.5D;

                SoundEvent close = null;
                switch (getAnimal()) {
                    case "cow":
                        close = SoundEvents.entity_cow_death;
                        break;
                    case "chicken":
                        close = SoundEvents.entity_chicken_death;
                        break;
                    case "pig":
                        close = SoundEvents.entity_pig_death;
                        break;
                    case "sheep":
                        close = SoundEvents.entity_sheep_death;
                        break;
                }
                this.worldObj.playSound((EntityPlayer)null, d3, (double)j + 0.5D, d2, close, SoundCategory.BLOCKS, 0.75F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }

    public void openChest()
    {
        ++this.numPlayersUsing;
        int i = 0;
        for (BlockAnimalChest.Types type : BlockAnimalChest.Types.values()) {
            if (type.toString().equals(getAnimal()))
                break;
            i++;
        }

        worldObj.addBlockEvent(pos, ModBlocks.AnimalChests[i], 1, this.numPlayersUsing);
    }

    public void closeChest()
    {
        --this.numPlayersUsing;
        int i = 0;
        for (BlockAnimalChest.Types type : BlockAnimalChest.Types.values()) {
            if (type.toString().equals(getAnimal()))
                break;
            i++;
        }

        worldObj.addBlockEvent(pos, ModBlocks.AnimalChests[i], 1, this.numPlayersUsing);
    }

    @Override
    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1)
        {
            this.numPlayersUsing = type;
            return true;
        }
        else
        {
            return super.receiveClientEvent(id, type);
        }
    }

    public String getCustomName() {
        return this.customName;
    }

    public void setCustomName(String name)
    {
        this.customName = name;
    }

    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.animalChest." + getAnimal();
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

    public String getAnimal()
    {
        Block block = worldObj.getBlockState(getPos()).getBlock();
        return block.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length() + "animalChest_".length());
    }
}
