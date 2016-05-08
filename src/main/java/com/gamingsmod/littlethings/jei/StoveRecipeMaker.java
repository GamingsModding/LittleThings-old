package com.gamingsmod.littlethings.jei;

import com.gamingsmod.littlethings.common.recipe.custom.StoveRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StoveRecipeMaker
{
    @Nonnull
    public static List<StoveRecipe> getCookingRecipes(IJeiHelpers helpers)
    {
        IStackHelper stackHelper = helpers.getStackHelper();
        StoveRecipes stoveRecipes = StoveRecipes.getInstance();
        Map<ItemStack, ItemStack> smeltingMap = stoveRecipes.getRECIPES();

        List<StoveRecipe> recipes = new ArrayList<>();

        for (Map.Entry<ItemStack, ItemStack> itemStackItemStackEntry : smeltingMap.entrySet()) {
            ItemStack input = itemStackItemStackEntry.getKey();
            ItemStack output = itemStackItemStackEntry.getValue();

            List<ItemStack> inputs = stackHelper.getSubtypes(input);
            StoveRecipe recipe = new StoveRecipe(inputs, output);
            recipes.add(recipe);
        }

        return recipes;
    }
}
