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

    public static void init(File file)
    {
        if (configuration == null) {
            configuration = new Configuration(file);
            loadConfig();
        }
    }

    public static void loadConfig()
    {
        enableColorfulText = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Colorful Flavour Text (Mainly on friends skulls)", false).getBoolean();
        enableExtraCraftingTables = configuration.get(Configuration.CATEGORY_GENERAL, "Enable extra crafting tables", true).getBoolean();
        enableItemElevator = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Item Elevator", true).getBoolean();
        enableSkullOwner = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Skull Owner", true).getBoolean();
        enableUpgradedFurnaces = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Upgraded Furnaces", true).getBoolean();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
