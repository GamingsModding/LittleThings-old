package com.gamingsmod.littlethings.common.recipe;

import com.gamingsmod.littlethings.common.block.BlockVanillaCraftingTables;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes
{
    public static void init()
    {
        addBlockRecipe(new ItemStack(Items.iron_ingot), new ItemStack(ModItems.IronNugget));

        if (ConfigurationHandler.enableExtraCraftingTables) recipesExtraCraftingTables();

        if (ConfigurationHandler.enableItemElevator) recipesItemElevator();
    }

    private static void recipesExtraCraftingTables()
    {
        removeFirstRecipeFor(Blocks.crafting_table);
        addRecipe(new ItemStack(Blocks.crafting_table),
                "xx", "xx",
                'x', new ItemStack(Blocks.planks));

        for (BlockVanillaCraftingTables.Variant variant :
                BlockVanillaCraftingTables.Variant.values()) {
            addRecipe(new ItemStack(ModBlocks.VanillaCraftingTables, 1, variant.getId()),
                    "xx", "xx",
                    'x', new ItemStack(Blocks.planks, 1, variant.getId() + 1));

            addShapelessRecipe(new ItemStack(Blocks.crafting_table), new ItemStack(ModBlocks.VanillaCraftingTables, 1, variant.getId()));
        }
    }

    private static void recipesItemElevator()
    {
        addRecipe(new ItemStack(ModBlocks.ItemEleveator),
                "sos", "sds", "sss",
                's', new ItemStack(Blocks.stone),
                'o', new ItemStack(Blocks.obsidian),
                'd', new ItemStack(Blocks.dispenser));

        addShapelessRecipe(new ItemStack(ModItems.IronNugget, 9), new ItemStack(Items.iron_ingot));
    }

    private static void removeFirstRecipeFor(Block block)
    {
        removeFirstRecipeFor(Item.getItemFromBlock(block));
    }

    private static void removeFirstRecipeFor(Item item)
    {
        for (Object recipe : CraftingManager.getInstance().getRecipeList()) {
            if (recipe != null) {
                ItemStack stack = ((IRecipe) recipe).getRecipeOutput();
                if (stack != null && stack.getItem() == item) {
                    CraftingManager.getInstance().getRecipeList().remove(recipe);
                    return;
                }
            }
        }
    }

    private static void addRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
    }

    private static void addShapelessRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
    }

    private static void addBlockRecipe(ItemStack output, ItemStack input)
    {
        addRecipe(output,
                "xxx", "xxx", "xxx",
                'x', input);
        input.stackSize = 9;
        addShapelessRecipe(input, output);
    }
}
