package com.gamingsmod.littlethings.common.tileentity.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityXpStore extends TileEntity
{
    private int xp;

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("XP", xp);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        xp = compound.getInteger("XP");
    }

    public int getXp()
    {
        return xp;
    }

    public void addXp(int xp)
    {
        this.xp += xp;
    }

    public void removeXp(int xp)
    {
        if (xp > this.xp)
            xp = this.xp;

        this.xp -= xp;
    }

    public void setXp(int xp)
    {
        this.xp = xp;
    }
}
