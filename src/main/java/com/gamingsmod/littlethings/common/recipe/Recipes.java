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
    public static String[] oreDicDyes = {"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};

    public static void init()
    {
        addBlockRecipe(new ItemStack(Items.iron_ingot), new ItemStack(ModItems.IronNugget));

        if (ConfigurationHandler.enableExtraCraftingTables) recipesExtraCraftingTables();

        if (ConfigurationHandler.enableItemElevator) recipesItemElevator();

        if (ConfigurationHandler.enableUpgradedFurnaces) recipesUpgradedFurnaces();

        if (ConfigurationHandler.enableGlassPanesRecipe) recipesRecoloringGlassPanes();

        if (ConfigurationHandler.enableAnimalChests) recipesAnimalChests();
    }

    private static void recipesAnimalChests()
    {
        addRecipe(new ItemStack(ModBlocks.AnimalChests[0]),
                "mmm", "mcm", "mmm",
                'm', new ItemStack(Items.beef),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[1]),
                "mmm", "mcm", "mmm",
                'm', new ItemStack(Items.chicken),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[2]),
                "mmm", "mcm", "mmm",
                'm', new ItemStack(Items.porkchop),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[3]),
                "mmm", "mcm", "mmm",
                'm', new ItemStack(Items.mutton),
                'c', "chestWood");
    }

    private static void recipesRecoloringGlassPanes()
    {
        int i = 0;
        for (String dye : oreDicDyes) {
            addRecipe(new ItemStack(Blocks.stained_glass_pane, 8, 15 - i),
                    "ggg", "gdg", "ggg",
                    'g', "paneGlass",
                    'd', dye);
            i++;
        }
    }

    private static void recipesUpgradedFurnaces()
    {
        addRecipe(new ItemStack(ModBlocks.UpgradedFurnaces[0]),
                "iii", "ifi", "iii",
                'i', "ingotIron",
                'f', new ItemStack(Blocks.furnace));

        addRecipe(new ItemStack(ModBlocks.UpgradedFurnaces[2]),
                "iii", "ifi", "iii",
                'i', "ingotGold",
                'f', new ItemStack(ModBlocks.UpgradedFurnaces[0]));

        addRecipe(new ItemStack(ModBlocks.UpgradedFurnaces[4]),
                "iii", "ifi", "iii",
                'i', "gemDiamond",
                'f', new ItemStack(ModBlocks.UpgradedFurnaces[2]));

        addRecipe(new ItemStack(ModBlocks.UpgradedFurnaces[6]),
                "iii", "ifi", "iii",
                'i', "gemEmerald",
                'f', new ItemStack(ModBlocks.UpgradedFurnaces[2]));
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
                's', "stone",
                'o', new ItemStack(Blocks.obsidian),
                'd', new ItemStack(Blocks.dispenser));
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
