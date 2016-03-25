package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.block.BlockVanillaCraftingTables;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.item.ItemBlockMeta;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static Block VanillaCraftingTables;

    public static void init()
    {
        if (ConfigurationHandler.enableExtraCraftingTables)
            addMetaBlock(VanillaCraftingTables = new BlockVanillaCraftingTables(), LibBlocks.VANILLACRAFTINGTABLE);
    }

    private static void addBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, name);
    }

    private static void addMetaBlock(Block block, String name)
    {
        GameRegistry.registerBlock(block, ItemBlockMeta.class, name);
    }
}
