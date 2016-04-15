package com.gamingsmod.littlethings.common.recipe;

import com.gamingsmod.littlethings.common.block.BlockVanillaCraftingTables;
import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.init.ModItems;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.gamingsmod.littlethings.common.recipe.custom.ShapedReturnRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes
{
    public static String[] oreDicDyes = {"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};

    public static void init()
    {
        RecipeSorter.register(LibMisc.PREFIX_MOD + "shapedReturnRecipe", ShapedReturnRecipe.class, RecipeSorter.Category.SHAPED, "after:forge:shapedore");

        addBlockRecipe(new ItemStack(Items.iron_ingot), new ItemStack(ModItems.IronNugget));
        addBlockRecipe(new ItemStack(Blocks.web), new ItemStack(Items.string), false);
        addRecipe(new ItemStack(Blocks.end_stone, 3),
                "ccc", "cec", "ccc",
                'e', new ItemStack(Items.ender_eye),
                'c', new ItemStack(Blocks.sandstone, 1, 1));
        recipesSingleDye();

        if (ConfigurationHandler.enableExtraCraftingTables) recipesExtraCraftingTables();

        if (ConfigurationHandler.enableItemElevator) recipesItemElevator();

        if (ConfigurationHandler.enableUpgradedFurnaces) recipesUpgradedFurnaces();

        if (ConfigurationHandler.enableGlassPanesRecipe) recipesRecoloringGlassPanes();

        if (ConfigurationHandler.enableAnimalChests) recipesAnimalChests();

        if (ConfigurationHandler.enableClearGlass) recipesClearGlass();

        if (ConfigurationHandler.enableHorseEquipment) recipesHorseEquipment();

        if (ConfigurationHandler.enableUnenchantingTable) recipesUnenchantingTable();
    }

    private static void recipesUnenchantingTable()
    {
        addRecipe(new ItemStack(ModBlocks.UnenchantingTable),
                "rbr", "ded", "sss",
                'e', new ItemStack(Blocks.enchanting_table),
                's', new ItemStack(Blocks.end_stone),
                'd', "gemDiamond",
                'r', new ItemStack(Blocks.wool, 1, 14),
                'b', new ItemStack(Blocks.wool, 1, 3));
    }

    private static void recipesSingleDye()
    {
        int i = 0;
        for (String dye : oreDicDyes) {
            addShapelessRecipe(new ItemStack(Blocks.stained_glass_pane, 1, 15 - i),
                    "paneGlass", dye);
            addShapelessRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, 15 - i),
                    new ItemStack(Blocks.hardened_clay), dye);
            i++;
        }
    }

    private static void recipesHorseEquipment()
    {
        if (ConfigurationHandler.enableOldSaddleRecipe)
            addRecipe(new ItemStack(Items.saddle),
                    "lll", "lil", "i i",
                    'l', new ItemStack(Items.leather),
                    'i', "ingotIron");
        else
            addRecipe(new ItemStack(Items.saddle),
                    "l l", "lll", "lil",
                    'l', new ItemStack(Items.leather),
                    's', new ItemStack(Items.string),
                    'i', "ingotIron");

        if (ConfigurationHandler.enableOldArmorRecipe) {
            addRecipe(new ItemStack(Items.iron_horse_armor),
                    "  i", "iwi", "iii",
                    'i', "ingotIron",
                    'w', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
            addRecipe(new ItemStack(Items.golden_horse_armor),
                    "  i", "iwi", "iii",
                    'i', "ingotGold",
                    'w', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
            addRecipe(new ItemStack(Items.diamond_horse_armor),
                    "  i", "iwi", "iii",
                    'i', "gemDiamond",
                    'w', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
        } else {
            addRecipe(new ItemStack(Items.iron_horse_armor),
                    "  h", "iwi", "i i",
                    'i', "ingotIron",
                    'h', new ItemStack(Items.iron_helmet),
                    'w', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
            addRecipe(new ItemStack(Items.golden_horse_armor),
                    "  h", "iwi", "i i",
                    'i', "ingotGold",
                    'h', new ItemStack(Items.golden_helmet),
                    'w', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
            addRecipe(new ItemStack(Items.diamond_horse_armor),
                    "  h", "iwi", "i i",
                    'i', "gemDiamond",
                    'h', new ItemStack(Items.diamond_helmet),
                    'w', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE));
        }
    }

    private static void recipesClearGlass()
    {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            addRecipe(new ItemStack(ModBlocks.StainedClearGlass, 8, color.getMetadata()),
                    "ggg", "gdg", "ggg",
                    'g', new ItemStack(ModBlocks.ClearGlass),
                    'd', oreDicDyes[color.getDyeDamage()]);
            addRecipe(new ItemStack(ModBlocks.StainedClearGlass, 8, color.getMetadata()),
                    "ggg", "gdg", "ggg",
                    'g', new ItemStack(ModBlocks.StainedClearGlass, 1, OreDictionary.WILDCARD_VALUE),
                    'd', oreDicDyes[color.getDyeDamage()]);
        }

        addRecipe(new ShapedReturnRecipe(new ItemStack(ModBlocks.ClearGlass, 8),
                "ggg", "gbg", "ggg",
                'g', new ItemStack(ModBlocks.StainedClearGlass, 1, OreDictionary.WILDCARD_VALUE),
                'b', new ItemStack(Items.water_bucket)));
    }

    private static void recipesAnimalChests()
    {
        addRecipe(new ItemStack(ModBlocks.AnimalChests[0]), //cow
                "mmm", "mcm", "mmm",
                'm', new ItemStack(Items.beef),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[1]), //chicken
                "mmm", "mcm", "mmm",
                'm', new ItemStack(Items.chicken),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[2]), //pig
                "mmm", "mcm", "mmm",
                'm', new ItemStack(Items.porkchop),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[3]), //sheep
                "mmm", "mcm", "mmm",
                'm', new ItemStack(Items.mutton),
                'c', "chestWood");
//        addRecipe(new ItemStack(ModBlocks.AnimalChests[4])); //horse
        addRecipe(new ItemStack(ModBlocks.AnimalChests[5]), //dog
                "pbp", "bcb", "sbs",
                'b', new ItemStack(Items.bone),
                'p', new ItemStack(Items.cooked_porkchop),
                's', new ItemStack(Items.cooked_beef),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[6]), //squid
                "iii", "ici", "iii",
                'i', new ItemStack(Items.dye, 1, 0),
                'c', "chestWood");

        addRecipe(new ItemStack(ModBlocks.AnimalChests[7]), //zombie
                "fff", "fcf", "fff",
                'f', new ItemStack(Items.rotten_flesh),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[8]), //skeleton
                "bbb", "bcb", "bbb",
                'b', new ItemStack(Items.bone),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[9]), //creeper
                "sss", "scs", "sss",
                's', new ItemStack(Items.gunpowder),
                'c', "chestWood");
        addRecipe(new ItemStack(ModBlocks.AnimalChests[10]), //spider
                "ooo", "oco", "ooo",
                'o', new ItemStack(Blocks.web),
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

        addRecipe(new ShapedReturnRecipe(new ItemStack(Blocks.glass, 8),
                "ggg", "gbg", "ggg",
                'g', new ItemStack(Blocks.stained_glass, 1, OreDictionary.WILDCARD_VALUE),
                'b', new ItemStack(Items.water_bucket)));
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

    private static void addRecipe(IRecipe recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(recipe);
    }

    private static void addShapelessRecipe(ItemStack output, Object... recipe)
    {
        CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(output, recipe));
    }

    private static void addBlockRecipe(ItemStack output, ItemStack input, boolean reverse)
    {
        addRecipe(output,
                "xxx", "xxx", "xxx",
                'x', input);
        if (reverse) {
            input.stackSize = 9;
            addShapelessRecipe(input, output);
        }
    }

    private static void addBlockRecipe(ItemStack output, ItemStack input)
    {
        addBlockRecipe(output, input, true);
    }
}
