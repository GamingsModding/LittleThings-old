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

    public static boolean enableOldSaddleRecipe;
    public static boolean enableOldArmorRecipe;

    public static double percentOfSkullSpawn;

    public static void init(File file)
    {
        if (configuration == null) {
            configuration = new Configuration(file);
            loadConfig();
        }
    }

    public static void loadConfig()
    {
        enableColorfulText = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Rude Flavour Text", false, "This is mainly for friend skull's flavour text").getBoolean();
        enableExtraCraftingTables = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Extra Crafting Tables", true).getBoolean();
        enableItemElevator = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Item Elevator", true).getBoolean();
        enableSkullOwner = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Skull Owner", true).getBoolean();
        enableUpgradedFurnaces = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Upgraded Furnaces", true).getBoolean();
        enableGlassPanesRecipe = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Recoloring Glass Panes", true).getBoolean();
        enableAnimalChests = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Animal Chests", true).getBoolean();
        enableClearGlass = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Clear Glass", true).getBoolean();
        enableHorseEquipment = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Horse Equipment Recipes", true).getBoolean();
        enableUnenchantingTable = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Unenchanting Table", true).getBoolean();
        enableHammers = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Hammers", true).getBoolean();
        enableExcavators = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Excavators", true).getBoolean();

        enableOldSaddleRecipe = configuration.get("Horse Equipment Recipes", "Enable Old Saddle Recipe", false, "Pre-13w18a Recipe").getBoolean();
        enableOldArmorRecipe = configuration.get("Horse Equipment Recipes", "Enable Old Armor Recipe", false, "Pre-13w16a Recipe").getBoolean();

        percentOfSkullSpawn = configuration.get(Configuration.CATEGORY_GENERAL, "Percent Of Zombie Spawning With Friend Skull", 0.01, "Higher the number the more common", 0.0, 1).getDouble();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
