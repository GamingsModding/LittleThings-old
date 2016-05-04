package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.gamingsmod.littlethings.common.tileentity.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityItemElevator.class, LibMisc.MOD_NAME + "_itemElevator");
        GameRegistry.registerTileEntity(TileEntityMetalFurnace.class, LibMisc.MOD_NAME + "_metalFurnace");
        GameRegistry.registerTileEntity(TileEntityMobChest.class, LibMisc.MOD_NAME + "_mobChest");
        GameRegistry.registerTileEntity(TileEntityUnenchantingTable.class, LibMisc.MOD_NAME + "_unenchantingTable");
        GameRegistry.registerTileEntity(TileEntityExpStore.class, LibMisc.MOD_NAME + "_expStore");
    }
}
