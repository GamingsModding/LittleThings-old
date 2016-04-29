package com.gamingsmod.littlethings.common.handler;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration configuration;

    public static boolean enableExtraCraftingTables;
    public static boolean enableItemElevator;
    public static boolean enableSkullOwner;
    public static boolean enableColorfulText;
    public static boolean enableUpgradedFurnaces;
    public static boolean enableGlassPanesRecipe;
    public static boolean enableAnimalChests;
    public static boolean enableClearGlass;
    public static boolean enableHorseEquipment;
    public static boolean enableUnenchantingTable;
    public static boolean enableHammers;
    public static boolean enableExcavators;
    public static boolean enableCrossbow;
    public static boolean enableObsidianTools;
    public static boolean enableEmeraldTools;
    public static boolean enableEmeraldsEverywhere;
    public static boolean enableBejeweledApple;
    public static boolean enableStoneTorches;
    public static boolean removeWithTC;
    public static boolean enableExtraFood;
    public static boolean enableExpStore;
    public static boolean enableBarbedWire;

    public static boolean enableOldSaddleRecipe;
    public static boolean enableOldArmorRecipe;

    public static double percentOfSkullSpawn;

    public static final String CATEGORY_ITEMS = "Items";
    public static final String CATEGORY_TOOLS = "Tools and Weapons";
    public static final String CATEGORY_BLOCKS = "Blocks";
    public static final String CATEGORY_RECIPES = "Recipes";
    public static final String CATEGORY_HORSE_ARMOR = "Horse Armor Recipes";
    public static final String CATEGORY_WORLD = "World";

    public static void init(File file)
    {
        if (configuration == null) {
            configuration = new Configuration(file);
            loadConfig();
        }
    }

    public static void loadConfig()
    {
        //General
        enableColorfulText = getBoolean(Configuration.CATEGORY_GENERAL, "Enable Rude Flavour Text", false, "This is mainly for friend skull's flavour text");
        enableSkullOwner = getBoolean(Configuration.CATEGORY_GENERAL, "Enable Skull Owner", true);
        percentOfSkullSpawn = getDouble(Configuration.CATEGORY_GENERAL, "Percent Of Zombie Spawning With Friend Skull", 0.01, 0.0, 1, "Higher the number the more common");

        //Blocks
        enableExtraCraftingTables = getBoolean(CATEGORY_BLOCKS, "Enable Extra Crafting Tables", true);
        enableItemElevator = getBoolean(CATEGORY_BLOCKS, "Enable Item Elevator", true);
        enableUpgradedFurnaces = getBoolean(CATEGORY_BLOCKS, "Enable Upgraded Furnaces", true);
        enableAnimalChests = getBoolean(CATEGORY_BLOCKS, "Enable Animal Chests", true);
        enableClearGlass = getBoolean(CATEGORY_BLOCKS, "Enable Clear Glass", true);
        enableUnenchantingTable = getBoolean(CATEGORY_BLOCKS, "Enable Unenchanting Table", true);
        enableExpStore = getBoolean(CATEGORY_BLOCKS, "Enable Experience Store", true);
        enableStoneTorches = getBoolean(CATEGORY_BLOCKS, "Enable Stone Torches", true);
        removeWithTC = getBoolean(CATEGORY_BLOCKS, "Stone Torches Leave To Tinkers", true, "This will not register Stone Torches if Tinkers Construct is loaded");
        enableBarbedWire = getBoolean(CATEGORY_BLOCKS, "Enable Barbed Wire", true);

        //Items
        enableHammers = getBoolean(CATEGORY_ITEMS, "Enable Hammers", true);
        enableExcavators = getBoolean(CATEGORY_ITEMS, "Enable Excavators", true);
        enableBejeweledApple = getBoolean(CATEGORY_ITEMS, "Enable Bejeweled Apples", true);
        enableExtraFood = getBoolean(CATEGORY_ITEMS, "Enable Extra Food", true);

        //Tools and Weapons
        enableCrossbow = getBoolean(CATEGORY_TOOLS, "Enable Crossbow", true);
        enableObsidianTools = getBoolean(CATEGORY_TOOLS, "Enable Obsidian Tool Set", true);
        enableEmeraldTools = getBoolean(CATEGORY_TOOLS, "Enable Emerald Tool Set", true);

        //Recipes
        enableGlassPanesRecipe = getBoolean(CATEGORY_RECIPES, "Enable Recoloring Glass Panes", true);
        enableHorseEquipment = getBoolean(CATEGORY_RECIPES, "Enable Horse Equipment Recipes", true);
        enableOldSaddleRecipe = getBoolean(CATEGORY_HORSE_ARMOR, "Enable Old Saddle Recipe", false, "Pre-13w18a Recipe");
        enableOldArmorRecipe = getBoolean(CATEGORY_HORSE_ARMOR, "Enable Old Armor Recipe", false, "Pre-13w16a Recipe");

        //World
        enableEmeraldsEverywhere = getBoolean(CATEGORY_WORLD, "Enable Emerald Generating Everywhere", true);

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    private static boolean getBoolean(String cat, String key, boolean def, String desc)
    {
        return configuration.get(cat, key, def, desc).getBoolean();
    }

    private static boolean getBoolean(String cat, String key, boolean def)
    {
        return getBoolean(cat, key, def, null);
    }

    private static int getInteger(String cat, String key, int def, int min, int max, String desc)
    {
        return configuration.get(cat, key, def, desc, min, max).getInt();
    }

    private static int getInteger(String cat, String key, int def, int min, int max)
    {
        return getInteger(cat, key, def, min, max, null);
    }

    private static double getDouble(String cat, String key, double def, double min, double max, String desc)
    {
        return configuration.get(cat, key, def, desc, min, max).getDouble();
    }

    private static double getDouble(String cat, String key, double def, double min, double max)
    {
        return getDouble(cat, key, def, min, max, null);
    }
}
