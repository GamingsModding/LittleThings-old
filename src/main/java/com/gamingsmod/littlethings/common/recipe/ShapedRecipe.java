package com.gamingsmod.littlethings.common.recipe;

import com.gamingsmod.littlethings.common.block.BlockVanillaCraftingTables;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ShapedRecipe
{
    public static void init()
    {
        if (ConfigurationHandler.enableExtraCraftingTables) {
            Recipes.removeFirstRecipeFor(Blocks.crafting_table);
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
    }

    public static void addRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(output, recipe));
    }

    public static void addShapelessRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
    }
}
