package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.tileentity.base.ModTileInventory;

public class TileEntityStove extends ModTileInventory
{
    public TileEntityStove()
    {
        super("stove");
    }

    @Override
    public int getSizeInventory()
    {
        return 4;
    }
}
