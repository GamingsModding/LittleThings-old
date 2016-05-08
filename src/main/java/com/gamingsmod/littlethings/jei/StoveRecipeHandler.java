package com.gamingsmod.littlethings.jei;

import com.gamingsmod.littlethings.common.lib.LibViewerUid;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;

import javax.annotation.Nonnull;

public class StoveRecipeHandler implements IRecipeHandler<StoveRecipe>
{
    @Nonnull
    @Override
    public Class<StoveRecipe> getRecipeClass()
    {
        return StoveRecipe.class;
    }

    @Nonnull
    @Override
    public String getRecipeCategoryUid()
    {
        return LibViewerUid.STOVE;
    }

    @Nonnull
    @Override
    public IRecipeWrapper getRecipeWrapper(@Nonnull StoveRecipe recipe)
    {
        return recipe;
    }

    @Override
    public boolean isRecipeValid(@Nonnull StoveRecipe recipe)
    {
        if (recipe.getInputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no inputs. {}", recipeInfo);
        }
        if (recipe.getOutputs().isEmpty()) {
            String recipeInfo = ErrorUtil.getInfoFromBrokenRecipe(recipe, this);
            Log.error("Recipe has no outputs. {}", recipeInfo);
        }
        return true;
    }
}
