package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.block.*;
import com.gamingsmod.littlethings.common.block.base.ModBlock;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
    public static final List<ModBlock> BLOCKS = new ArrayList<>();

    public static ModBlock VanillaCraftingTables;
    public static ModBlock ItemEleveator;
    public static ModBlock MetalFurnace;
    public static ModBlock MetalFurnace_Lit;
    public static ModBlock ClearGlass;
    public static ModBlock StainedClearGlass;
    public static ModBlock UnenchantingTable;
    public static ModBlock StoneTorch;
    public static ModBlock ExpStore;
    public static ModBlock BarbedWire;
    public static ModBlock MobChests;
    public static ModBlock Stove;
    public static ModBlock RedstoneClock;

    public static void init()
    {

        if (ConfigurationHandler.enableExtraCraftingTables)
            VanillaCraftingTables = new BlockVanillaCraftingTables();

        if (ConfigurationHandler.enableItemElevator)
            ItemEleveator = new BlockItemElevator();

        if (ConfigurationHandler.enableUpgradedFurnaces) {
            MetalFurnace = new BlockMetalFurnace(false);
            MetalFurnace_Lit = new BlockMetalFurnace(true);
        }

        if (ConfigurationHandler.enableAnimalChests) {
            MobChests = new BlockMobChest();
        }

        if (ConfigurationHandler.enableClearGlass) {
            ClearGlass = new BlockClearGlass();
            StainedClearGlass = new BlockStainedClearGlass();
        }

        if (ConfigurationHandler.enableUnenchantingTable)
            UnenchantingTable = new BlockUnenchantingTable();

        if (ConfigurationHandler.enableStoneTorches && (!Loader.isModLoaded("tconstruct") && ConfigurationHandler.removeWithTC))
            StoneTorch = new BlockStoneTorch();

        if (ConfigurationHandler.enableExpStore)
            ExpStore = new BlockExpStore();

        if (ConfigurationHandler.enableBarbedWire)
            BarbedWire = new BlockBarbedWire();

        if (ConfigurationHandler.enableStove)
            Stove = new BlockStove();

        if (ConfigurationHandler.enableRedstoneClock)
            RedstoneClock = new BlockRedstoneClock();
    }

    public static void registerBlockVariants()
    {
        BLOCKS.forEach(modBlock -> modBlock.registerBlockVariants(LibMisc.PREFIX_MOD));
    }

    public static void registerBlockRender()
    {
        BLOCKS.forEach(ModBlock::registerRender);
    }
}
