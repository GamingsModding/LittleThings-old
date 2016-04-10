package com.gamingsmod.littlethings.common.recipe.custom;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ShapedReturnRecipe extends ShapedOreRecipe
{

    public ShapedReturnRecipe(ItemStack result, Object... recipe) {
        super(result, recipe);
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv) {
        ItemStack[] stacks = new ItemStack[inv.getSizeInventory()];
        for (int i = 0; i < inv.getSizeInventory(); ++i)
        {
            ItemStack stack = inv.getStackInSlot(i);
            if (stack != null && stack.getItem() == Items.water_bucket)
                stacks[i] = new ItemStack(Items.bucket);
            else
                stacks[i] = null;
        }

        return stacks;
    }
}