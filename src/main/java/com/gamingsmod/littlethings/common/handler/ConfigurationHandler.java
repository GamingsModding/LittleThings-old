package com.gamingsmod.littlethings.common.handler;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigurationHandler
{
    public static Configuration configuration;

    public static boolean enableExtraCraftingTables = true;
    public static boolean enableItemElevator = true;

    public static void init(File file)
    {
        if (configuration == null) {
            configuration = new Configuration(file);
            loadConfig();
        }
    }

    public static void loadConfig()
    {
        enableExtraCraftingTables = configuration.get(Configuration.CATEGORY_GENERAL, "Enable extra crafting tables", true).getBoolean();
        enableItemElevator = configuration.get(Configuration.CATEGORY_GENERAL, "Enable Item Elevator", true).getBoolean();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
