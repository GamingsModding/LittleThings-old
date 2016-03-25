package com.gamingsmod.littlethings.common;

import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.recipe.Recipes;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        ModBlocks.init();
    }

    public void init(FMLInitializationEvent e)
    {
        Recipes.init();
    }

    public void postInit(FMLPostInitializationEvent e)
    {

    }
}
