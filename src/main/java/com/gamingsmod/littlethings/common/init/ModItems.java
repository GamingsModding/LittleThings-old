package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.item.ItemCrossBolt;
import com.gamingsmod.littlethings.common.item.ItemCrossbow;
import com.gamingsmod.littlethings.common.item.ItemIronNugget;
import com.gamingsmod.littlethings.common.item.base.*;
import com.gamingsmod.littlethings.common.item.food.ItemFoodBejeweledApple;
import com.gamingsmod.littlethings.common.item.food.ItemFoodSeedsPack;
import com.gamingsmod.littlethings.common.item.tools.*;
import com.gamingsmod.littlethings.common.lib.LibItems;
import com.gamingsmod.littlethings.common.lib.LibMaterials;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems
{
    public static final List<IModItem> ITEMS = new ArrayList<>();

    public static ModItem IronNugget;
    public static ModItem StoneStick;
    public static ModItem Pan;
    public static ModItem MobDust;

    public static ModItemTool WoodHammer;
    public static ModItemTool StoneHammer;
    public static ModItemTool IronHammer;
    public static ModItemTool GoldHammer;
    public static ModItemTool DiamondHammer;

    public static ModItemTool WoodExcavator;
    public static ModItemTool StoneExcavator;
    public static ModItemTool IronExcavator;
    public static ModItemTool GoldExcavator;
    public static ModItemTool DiamondExcavator;

    public static ModItem CrossBow;
    public static ItemCrossBolt CrossBolt;

    public static ModItemSword ObsidianSword;
    public static ModItemTool ObsidianPickaxe;
    public static ModItemTool ObsidianAxe;
    public static ModItemTool ObsidianSpade;
    public static ModItem ObsidianHoe;
    public static ModItemTool ObsidianHammer;
    public static ModItemTool ObsidianExcavator;

    public static ModItemSword EmeraldSword;
    public static ModItemTool EmeraldPickaxe;
    public static ModItemTool EmeraldAxe;
    public static ModItemTool EmeraldSpade;
    public static ModItem EmeraldHoe;
    public static ModItemTool EmeraldHammer;
    public static ModItemTool EmeraldExcavator;

    public static ModItemFood BejeweledApple;
    public static ModItemFood FriedEgg;
    public static ModItemFood SeedsPack;

    public static void init()
    {
        IronNugget = new ItemIronNugget();
        StoneStick = new ModItem(LibItems.STONE_STICK).setCreativeTab(CreativeTabs.tabMaterials);

        if (ConfigurationHandler.enableHammers) {
            WoodHammer = new ModItemHammer(LibItems.HAMMER + "Wood", Item.ToolMaterial.WOOD);
            StoneHammer = new ModItemHammer(LibItems.HAMMER + "Stone", Item.ToolMaterial.STONE);
            IronHammer = new ModItemHammer(LibItems.HAMMER + "Iron", Item.ToolMaterial.IRON);
            GoldHammer = new ModItemHammer(LibItems.HAMMER + "Gold", Item.ToolMaterial.GOLD);
            DiamondHammer = new ModItemHammer(LibItems.HAMMER + "Diamond", Item.ToolMaterial.DIAMOND);
        }

        if (ConfigurationHandler.enableExcavators) {
            WoodExcavator = new ModItemExcavator(LibItems.EXCAVATOR + "Wood", Item.ToolMaterial.WOOD);
            StoneExcavator = new ModItemExcavator(LibItems.EXCAVATOR + "Stone", Item.ToolMaterial.STONE);
            IronExcavator = new ModItemExcavator(LibItems.EXCAVATOR + "Iron", Item.ToolMaterial.IRON);
            GoldExcavator = new ModItemExcavator(LibItems.EXCAVATOR + "Gold", Item.ToolMaterial.GOLD);
            DiamondExcavator = new ModItemExcavator(LibItems.EXCAVATOR + "Diamond", Item.ToolMaterial.DIAMOND);
        }

        if (ConfigurationHandler.enableCrossbow) {
            CrossBow = new ItemCrossbow();
            CrossBolt = new ItemCrossBolt();
            MobDust = new ModItemVariants(LibItems.MOB_DUST, new String[]{"wither", "poison"}).setCreativeTab(CreativeTabs.tabMisc);
        }

        if (ConfigurationHandler.enableObsidianTools) {
            ObsidianSword = new ModItemSword("obsidian_sword", LibMaterials.Tools.OBSIDIAN);
            ObsidianPickaxe = new ModItemPickaxe("obsidian_pickaxe", LibMaterials.Tools.OBSIDIAN);
            ObsidianAxe = new ModItemAxe("obsidian_axe", LibMaterials.Tools.OBSIDIAN);
            ObsidianSpade = new ModItemSpade("obsidian_spade", LibMaterials.Tools.OBSIDIAN);
            ObsidianHoe = new ModItemHoe("obsidian_hoe", LibMaterials.Tools.OBSIDIAN);
            if (ConfigurationHandler.enableHammers)
                ObsidianHammer = new ModItemHammer(LibItems.HAMMER + "Obsidian", LibMaterials.Tools.OBSIDIAN);
            if (ConfigurationHandler.enableExcavators)
                ObsidianExcavator = new ModItemExcavator(LibItems.EXCAVATOR + "Obsidian", LibMaterials.Tools.OBSIDIAN);
        }

        if (ConfigurationHandler.enableEmeraldTools) {
            EmeraldSword = new ModItemSword("emerald_sword", LibMaterials.Tools.EMERALD);
            EmeraldPickaxe = new ModItemPickaxe("emerald_pickaxe", LibMaterials.Tools.EMERALD);
            EmeraldAxe = new ModItemAxe("emerald_axe", LibMaterials.Tools.EMERALD);
            EmeraldSpade = new ModItemSpade("emerald_spade", LibMaterials.Tools.EMERALD);
            EmeraldHoe = new ModItemHoe("emerald_hoe", LibMaterials.Tools.EMERALD);
            if (ConfigurationHandler.enableHammers)
                EmeraldHammer = new ModItemHammer(LibItems.HAMMER + "Emerald", LibMaterials.Tools.EMERALD);
            if (ConfigurationHandler.enableExcavators)
                EmeraldExcavator = new ModItemExcavator(LibItems.EXCAVATOR + "Emerald", LibMaterials.Tools.EMERALD);

        }

        if (ConfigurationHandler.enableBejeweledApple)
            BejeweledApple = new ItemFoodBejeweledApple();

        if (ConfigurationHandler.enableExtraFood) {
            FriedEgg = new ModItemFood(5, false, LibItems.FRIED_EGG);
            SeedsPack = new ItemFoodSeedsPack();
        }
        if (ConfigurationHandler.enableStove)
            Pan = (ModItem) (new ModItem(LibItems.PAN)).setCreativeTab(CreativeTabs.tabMisc).setMaxStackSize(1).setMaxDamage(150);

    }

    public static void registerItemVariants()
    {
        ITEMS.forEach(iModItem -> iModItem.registerItemVariants(LibMisc.PREFIX_MOD));
    }

    public static void registerItemRender()
    {
        ITEMS.forEach(IModItem::registerRender);
    }
}