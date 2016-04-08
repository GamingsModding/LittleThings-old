package com.gamingsmod.littlethings.jei;

import com.gamingsmod.littlethings.common.gui.container.ContainerVanillaCraftingTable;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

import javax.annotation.Nonnull;

@JEIPlugin
public class LittleThingsPlugin extends BlankModPlugin
{
    @Override
    public void register(@Nonnull IModRegistry registry)
    {
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(ContainerVanillaCraftingTable.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
    }
}
