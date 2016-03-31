package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.tileentity.base.TEInventory;
import net.minecraft.entity.player.EntityPlayer;

public class TileEntityItemElevator extends TEInventory
{
    public TileEntityItemElevator() {
        super("itemElevator");
    }

    @Override
    public int getSizeInventory() {
        return 9;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }
}