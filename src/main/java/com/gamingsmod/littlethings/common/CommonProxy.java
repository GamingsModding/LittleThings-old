package com.gamingsmod.littlethings.common;

import com.gamingsmod.littlethings.common.command.*;
import com.gamingsmod.littlethings.common.events.FoodEvents;
import com.gamingsmod.littlethings.common.events.FriendsSkulls;
import com.gamingsmod.littlethings.common.events.RightClickCrops;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.init.*;
import com.gamingsmod.littlethings.common.network.GuiHandler;
import com.gamingsmod.littlethings.common.network.MessageHandler;
import com.gamingsmod.littlethings.common.recipe.Recipes;
import com.gamingsmod.littlethings.common.recipe.Smelting;
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
        ModItems.init();
        ModTileEntities.init();
        ModEntities.init();
        MessageHandler.preInit();
    }

    public void init(FMLInitializationEvent e)
    {
        Recipes.init();
        Smelting.init();
        ModOreDic.init();
        ModEntities.initRender();
        NetworkRegistry.INSTANCE.registerGuiHandler(LittleThings.instance, new GuiHandler());
        MinecraftForge.EVENT_BUS.register(new FriendsSkulls());
        MinecraftForge.EVENT_BUS.register(new FoodEvents());
        MinecraftForge.EVENT_BUS.register(new RightClickCrops());
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
