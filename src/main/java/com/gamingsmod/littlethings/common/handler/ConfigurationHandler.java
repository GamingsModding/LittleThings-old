package com.gamingsmod.littlethings.common.handler;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration configuration;

    public static boolean enableExtraCraftingTables = true;
    public static boolean enableItemElevator = true;
    public static boolean enableSkullOwner = true;
    public static boolean enableColorfulText = false;
    public static boolean enableUpgradedFurnaces = true;

    public static double percentOfSkullSpawn = 0.05;

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

        percentOfSkullSpawn = configuration.get(Configuration.CATEGORY_GENERAL, "Percent Of Zombie Spawning With Friend Skull", 0.05, "Higher the number the more common", 0.0, 1).getDouble();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
