package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.tileentity.base.ModTileInventory;

public class TileEntityUnenchantingTable extends ModTileInventory
{
    public TileEntityUnenchantingTable()
    {
        super("unenchantingTable");
    }

    @Override
    public int getSizeInventory()
    {
        return 3;
    }
}
