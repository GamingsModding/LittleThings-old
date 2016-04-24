package com.gamingsmod.littlethings.common.recipe;

import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Smelting
{
    public static void init()
    {
        reg(Blocks.rail, new ItemStack(ModItems.IronNugget, 2), 0.3F);
        reg(Blocks.golden_rail, new ItemStack(Items.gold_ingot), 0.3F);
        reg(Blocks.activator_rail, new ItemStack(Items.iron_ingot), 0.3F);
        reg(Blocks.detector_rail, new ItemStack(Items.iron_ingot), 0.3F);


        if (ConfigurationHandler.enableClearGlass) recipesClearGlass();

        if (ConfigurationHandler.enableExtraFood) recipesExtraFood();
    }

    private static void recipesExtraFood()
    {
        reg(Items.egg, new ItemStack(ModItems.FriedEgg), 0.2F);
    }

    private static void recipesClearGlass()
    {
        reg(Blocks.glass, new ItemStack(ModBlocks.ClearGlass), 0.2F);
    }

    private static void reg(Item input, ItemStack output, float xp)
    {
        GameRegistry.addSmelting(input, output, xp);
    }

    private static void reg(Block input, ItemStack output, float xp)
    {
        GameRegistry.addSmelting(input, output, xp);
    }
}
