package com.gamingsmod.littlethings.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDic
{
    public static void init()
    {
        add("nuggetIron", ModItems.IronNugget);
    }

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
}
