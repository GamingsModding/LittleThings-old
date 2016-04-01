package com.gamingsmod.littlethings.common;

import com.gamingsmod.littlethings.common.command.*;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.handler.GuiHandler;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.init.ModTileEntities;
import com.gamingsmod.littlethings.common.override.ItemOverrides;
import com.gamingsmod.littlethings.common.recipe.Recipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        ConfigurationHandler.init(e.getSuggestedConfigurationFile());

        ModBlocks.init();
        ModTileEntities.init();
    }

    public void init(FMLInitializationEvent e)
    {
        Recipes.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(LittleThings.instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new ItemOverrides());
    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }

    public void serverStarting(FMLServerStartingEvent e)
    {
        e.registerServerCommand(new CommandHead());
        e.registerServerCommand(new CommandSurvivalShortcut());
        e.registerServerCommand(new CommandCreativeShortcut());
        e.registerServerCommand(new CommandAdventureShortcut());
        e.registerServerCommand(new CommandSpectatorShortcut());
        e.registerServerCommand(new CommandSkull());
    }
}
