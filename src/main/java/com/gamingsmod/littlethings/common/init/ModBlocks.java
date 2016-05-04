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
    public static Block MetalFurnace;
    public static Block MetalFurnace_Lit;
    public static Block ClearGlass;
    public static Block StainedClearGlass;
    public static Block UnenchantingTable;
    public static Block StoneTorch;
    public static Block ExpStore;
    public static Block BarbedWire;
    public static Block MobChests;

    public static void init()
    {
        int i;

        if (ConfigurationHandler.enableExtraCraftingTables)
            addMetaBlock(VanillaCraftingTables = new BlockVanillaCraftingTables(), LibBlocks.VANILLACRAFTINGTABLE);

        if (ConfigurationHandler.enableItemElevator)
            addBlock(ItemEleveator = new BlockItemElevator(), LibBlocks.ITEMELEVATOR);

        if (ConfigurationHandler.enableUpgradedFurnaces) {
            addMetaBlock(MetalFurnace = new BlockMetalFurnace(LibBlocks.METAL_FURNACE, false), LibBlocks.METAL_FURNACE);
            addMetaBlock(MetalFurnace_Lit = new BlockMetalFurnace(LibBlocks.METAL_FURNACE, true), LibBlocks.METAL_FURNACE + "_lit");
        }

        if (ConfigurationHandler.enableAnimalChests) {
            addMetaBlock(MobChests = new BlockMobChest(), LibBlocks.MOBCHEST);
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

        if (ConfigurationHandler.enableBarbedWire)
            addBlock(BarbedWire = new BlockBarbedWire(), LibBlocks.BARBED_WIRE);
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
