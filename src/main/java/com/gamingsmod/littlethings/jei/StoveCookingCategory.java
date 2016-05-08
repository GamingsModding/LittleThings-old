package com.gamingsmod.littlethings.jei;

import com.gamingsmod.littlethings.common.init.ModItems;
import com.gamingsmod.littlethings.common.lib.LibViewerUid;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class StoveCookingCategory extends BlankRecipeCategory
{
    protected static final int SLOT_INPUT = 0;
    protected static final int SLOT_PAN = 1;
    protected static final int SLOT_FUEL = 2;
    protected static final int SLOT_OUTPUT = 3;

    private final IDrawable background;
    private IDrawableAnimated arrow;
    private IDrawableAnimated flame;

    public StoveCookingCategory(IGuiHelper guiHelper)
    {
        ResourceLocation location = new ResourceLocation("littlethings:textures/gui/stove.png");
        background = guiHelper.createDrawable(location, 53, 19, 85, 75);
        IDrawableStatic flameDrawable = guiHelper.createDrawable(location, 176, 0, 14, 14);
        this.flame = guiHelper.createAnimatedDrawable(flameDrawable, 300, IDrawableAnimated.StartDirection.TOP, true);

        IDrawableStatic arrowStatic = guiHelper.createDrawable(location, 176, 14, 24, 17);
        this.arrow = guiHelper.createAnimatedDrawable(arrowStatic, 200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Nonnull
    @Override
    public String getUid()
    {
        return LibViewerUid.STOVE;
    }

    @Nonnull
    @Override
    public String getTitle()
    {
        return "Cooking";
    }

    @Nonnull
    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Override
    public void drawAnimations(@Nonnull Minecraft minecraft)
    {
        arrow.draw(minecraft, 24, 38);
        flame.draw(minecraft, 2, 40);
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper)
    {
        IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
        itemStackGroup.init(SLOT_INPUT, true, 0, 0);
        itemStackGroup.init(SLOT_PAN, true, 0, 20);
        itemStackGroup.init(SLOT_OUTPUT, false, 60, 37);

        itemStackGroup.setFromRecipe(SLOT_INPUT, recipeWrapper.getInputs());
        itemStackGroup.set(SLOT_PAN, new ItemStack(ModItems.Pan));
        itemStackGroup.setFromRecipe(SLOT_OUTPUT, recipeWrapper.getOutputs());
    }
}
