package com.gamingsmod.littlethings.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityExpStore extends TileEntity
{
    public int xp;

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        System.out.println("Writing XP=" + xp);
        compound.setInteger("XP", xp);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        xp = compound.getInteger("XP");
        System.out.println("Reading XP=" + xp);
    }
}
