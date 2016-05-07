package com.gamingsmod.littlethings.common.recipe.custom;

import com.gamingsmod.littlethings.common.init.ModItems;
import com.google.common.collect.Maps;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class StoveRecipes
{
    private static final StoveRecipes instance = new StoveRecipes();
    private Map<ItemStack, ItemStack> RECIPES = Maps.<ItemStack, ItemStack>newHashMap();

    public static StoveRecipes getInstance()
    {
        return instance;
    }

    private StoveRecipes()
    {
        this.addRecipe(new ItemStack(Items.egg), new ItemStack(ModItems.FriedEgg));
    }

    public void addRecipe(ItemStack input, ItemStack output)
    {
        this.RECIPES.put(input, output);
    }

    public ItemStack getOutput(ItemStack input)
    {
        for (Map.Entry<ItemStack, ItemStack> recipe : RECIPES.entrySet()) {
            if (areItemStacksTheSame(input, recipe.getKey()))
                return recipe.getValue();
        }
        return null;
    }

    public Map<ItemStack, ItemStack> getRECIPES()
    {
        return RECIPES;
    }

    private static boolean areItemStacksTheSame(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}
