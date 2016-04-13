package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.gamingsmod.littlethings.common.tileentity.TileEntityAnimalChest;
import com.gamingsmod.littlethings.common.tileentity.TileEntityItemElevator;
import com.gamingsmod.littlethings.common.tileentity.TileEntityUnenchantingTable;
import com.gamingsmod.littlethings.common.tileentity.TileEntityUpgradedFurnace;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities
{
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityItemElevator.class, LibMisc.MOD_NAME + "_itemElevator");
        GameRegistry.registerTileEntity(TileEntityUpgradedFurnace.class, LibMisc.MOD_NAME + "_upgradedFurnace");
        GameRegistry.registerTileEntity(TileEntityAnimalChest.class, LibMisc.MOD_NAME + "_animalChest");
        GameRegistry.registerTileEntity(TileEntityUnenchantingTable.class, LibMisc.MOD_NAME + "_unenchantingTable");
    }
}
