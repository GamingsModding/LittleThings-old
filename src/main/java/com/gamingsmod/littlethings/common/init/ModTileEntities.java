package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.tileentity.TileEntityItemElevator;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityItemElevator.class, "itemElevator");
    }
}
