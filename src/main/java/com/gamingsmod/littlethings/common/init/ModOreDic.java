package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.block.BlockVanillaCraftingTables;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDic
{
    public static void init()
    {
        ItemStack[] workbenches = new ItemStack[1 + BlockVanillaCraftingTables.Variant.values().length];
        workbenches[0] = new ItemStack(Blocks.crafting_table);
        for (BlockVanillaCraftingTables.Variant variant : BlockVanillaCraftingTables.Variant.values())
            workbenches[variant.getId() + 1] = new ItemStack(ModBlocks.VanillaCraftingTables, 1, variant.getId());

        add("nuggetIron", ModItems.IronNugget);
        add("workbench", workbenches);
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
