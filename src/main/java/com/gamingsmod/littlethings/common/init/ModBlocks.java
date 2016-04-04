package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.block.BlockItemElevator;
import com.gamingsmod.littlethings.common.block.BlockUpgradedFurnace;
import com.gamingsmod.littlethings.common.block.BlockVanillaCraftingTables;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.item.base.ItemBlockMeta;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static Block VanillaCraftingTables;
    public static Block ItemEleveator;
    public static Block[] UpgradedFurnaces = new Block[8];

    public static void init()
    {
        if (ConfigurationHandler.enableExtraCraftingTables)
            addMetaBlock(VanillaCraftingTables = new BlockVanillaCraftingTables(), LibBlocks.VANILLACRAFTINGTABLE);

        if (ConfigurationHandler.enableItemElevator)
            addBlock(ItemEleveator = new BlockItemElevator(), LibBlocks.ITEMELEVATOR);

        if (ConfigurationHandler.enableUpgradedFurnaces) {
            int i = 0;
            for (BlockUpgradedFurnace.Types name : BlockUpgradedFurnace.Types.values()) {
                addBlock(UpgradedFurnaces[i+1] = new BlockUpgradedFurnace(LibBlocks.UPGRADEDFURNACE + name, true), LibBlocks.UPGRADEDFURNACE + name + "_lit");
                addBlock(UpgradedFurnaces[i] = new BlockUpgradedFurnace(LibBlocks.UPGRADEDFURNACE + name, false), LibBlocks.UPGRADEDFURNACE + name);
                i = i + 2;
            }
        }
    }

    private static void addBlock(Block block, String name)
    {
        if (block.getRegistryName() == null)
            block.setRegistryName(name);

        register(block);
        ModItems.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }

    private static void addMetaBlock(Block block, String name)
    {
        if (block.getRegistryName() == null)
            block.setRegistryName(name);

        register(block);
        ModItems.register(new ItemBlockMeta(block).setRegistryName(block.getRegistryName()));
    }

    public static void register(Block block)
    {
        GameRegistry.register(block);
    }
}
