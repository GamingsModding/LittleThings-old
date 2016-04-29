package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.block.*;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.item.base.ItemBlockMeta;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks
{
    public static Block VanillaCraftingTables;
    public static Block ItemEleveator;
    public static Block[] UpgradedFurnaces = new Block[8];
    public static Block[] AnimalChests = new Block[BlockAnimalChest.Types.values().length];
    public static Block ClearGlass;
    public static Block StainedClearGlass;
    public static Block UnenchantingTable;
    public static Block StoneTorch;
    public static Block ExpStore;

    public static void init()
    {
        int i;

        if (ConfigurationHandler.enableExtraCraftingTables)
            addMetaBlock(VanillaCraftingTables = new BlockVanillaCraftingTables(), LibBlocks.VANILLACRAFTINGTABLE);

        if (ConfigurationHandler.enableItemElevator)
            addBlock(ItemEleveator = new BlockItemElevator(), LibBlocks.ITEMELEVATOR);

        if (ConfigurationHandler.enableUpgradedFurnaces) {
            i = 0;
            for (BlockUpgradedFurnace.Types name : BlockUpgradedFurnace.Types.values()) {
                addBlock(UpgradedFurnaces[i+1] = new BlockUpgradedFurnace(LibBlocks.UPGRADEDFURNACE + name, true), LibBlocks.UPGRADEDFURNACE + name + "_lit");
                addBlock(UpgradedFurnaces[i] = new BlockUpgradedFurnace(LibBlocks.UPGRADEDFURNACE + name, false), LibBlocks.UPGRADEDFURNACE + name);
                i = i + 2;
            }
        }

        if (ConfigurationHandler.enableAnimalChests) {
            i = 0;
            for (BlockAnimalChest.Types name : BlockAnimalChest.Types.values()) {
                addBlock(AnimalChests[i] = new BlockAnimalChest(name.toString()), LibBlocks.ANIMALCHEST + name);
                i++;
            }
        }

        if (ConfigurationHandler.enableClearGlass) {
            addBlock(ClearGlass = new BlockClearGlass(), LibBlocks.CLEARGLASS);
            addMetaBlock(StainedClearGlass = new BlockStainedClearGlass(), LibBlocks.STAINEDCLEARGLASS);
        }

        if (ConfigurationHandler.enableUnenchantingTable)
            addBlock(UnenchantingTable = new BlockUnenchantingTable(), LibBlocks.UNENCHANTING_TABLE);

        if (ConfigurationHandler.enableStoneTorches && (!Loader.isModLoaded("tconstruct") && ConfigurationHandler.removeWithTC))
            addBlock(StoneTorch = new BlockStoneTorch(), LibBlocks.STONE_TORCH);

        if (ConfigurationHandler.enableExpStore)
            addBlock(ExpStore = new BlockExpStore(), LibBlocks.EXP_STORE);
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
