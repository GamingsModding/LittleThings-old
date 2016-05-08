package com.gamingsmod.littlethings.jei;

import com.gamingsmod.littlethings.common.gui.container.ContainerVanillaCraftingTable;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibViewerUid;
import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

@JEIPlugin
public class LittleThingsPlugin extends BlankModPlugin
{
    @Override
    public void register(@Nonnull IModRegistry registry)
    {
        IItemRegistry itemRegistry = registry.getItemRegistry();
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        IRecipeTransferRegistry recipeTransferHandler = registry.getRecipeTransferRegistry();

        recipeTransferHandler.addRecipeTransferHandler(ContainerVanillaCraftingTable.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.VanillaCraftingTables, 1, OreDictionary.WILDCARD_VALUE), VanillaRecipeCategoryUid.CRAFTING);
        registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.Stove), LibViewerUid.STOVE);

        registry.addRecipeCategories(new StoveCookingCategory(guiHelper));
        registry.addRecipeHandlers(new StoveRecipeHandler());
        registry.addRecipes(StoveRecipeMaker.getCookingRecipes(jeiHelpers));
    }
}
