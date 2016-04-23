package com.gamingsmod.littlethings.common.init;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDic
{
    public static void init()
    {

        add("nuggetIron", ModItems.IronNugget);

        add("workbench", Blocks.crafting_table);
        add("workbench", new ItemStack(ModBlocks.VanillaCraftingTables, 1, OreDictionary.WILDCARD_VALUE));

        add("blockGlass", ModBlocks.ClearGlass);
        add("blockClearGlass", new ItemStack(ModBlocks.ClearGlass), new ItemStack(ModBlocks.StainedClearGlass, 1, OreDictionary.WILDCARD_VALUE));
;    }

    private static void add(String entry, Item... items)
    {
        for (Item item : items)
            OreDictionary.registerOre(entry, item);
    }

    private static void add(String entry, Block... blocks)
    {
        for (Block block : blocks)
            OreDictionary.registerOre(entry, block);
    }

    private static void add(String entry, ItemStack... itemStacks)
    {
        for (ItemStack stack : itemStacks)
            OreDictionary.registerOre(entry, stack);
    }
}
