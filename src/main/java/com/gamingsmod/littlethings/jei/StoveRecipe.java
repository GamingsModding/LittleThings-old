package com.gamingsmod.littlethings.jei;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class StoveRecipe implements IRecipeWrapper
{
    private final List<List<ItemStack>> input;
    private final List<ItemStack> outputs;

    public StoveRecipe(@Nonnull List<ItemStack> input, @Nonnull ItemStack output)
    {
        this.input = Collections.singletonList(input);
        this.outputs = Collections.singletonList(output);
    }

    @Override
    public List getInputs()
    {
        return input;
    }

    @Override
    public List getOutputs()
    {
        return outputs;
    }

    @Override
    public List<FluidStack> getFluidInputs()
    {
        return null;
    }

    @Override
    public List<FluidStack> getFluidOutputs()
    {
        return null;
    }

    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
    {

    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight)
    {

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY)
    {
        return null;
    }

    @Override
    public boolean handleClick(@Nonnull Minecraft minecraft, int mouseX, int mouseY, int mouseButton)
    {
        return false;
    }
}
