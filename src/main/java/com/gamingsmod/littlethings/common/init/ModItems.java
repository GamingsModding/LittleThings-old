package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import com.gamingsmod.littlethings.common.item.*;
import com.gamingsmod.littlethings.common.item.food.ItemFoodBejeweledApple;
import com.gamingsmod.littlethings.common.item.tools.*;
import com.gamingsmod.littlethings.common.lib.LibItems;
import com.gamingsmod.littlethings.common.lib.LibMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
    public static Item IronNugget;

    public static Item WoodHammer;
    public static Item StoneHammer;
    public static Item IronHammer;
    public static Item GoldHammer;
    public static Item DiamondHammer;

    public static Item WoodExcavator;
    public static Item StoneExcavator;
    public static Item IronExcavator;
    public static Item GoldExcavator;
    public static Item DiamondExcavator;

    public static Item CrossBow;
    public static ItemCrossBolt CrossBolt;

    public static Item ObsidianSword;
    public static Item ObsidianPickaxe;
    public static Item ObsidianAxe;
    public static Item ObsidianSpade;
    public static Item ObsidianHoe;

    public static Item EmeraldSword;
    public static Item EmeraldPickaxe;
    public static Item EmeraldAxe;
    public static Item EmeraldSpade;
    public static Item EmeraldHoe;

    public static ItemFood BejeweledApple;

    public static void init()
    {
        register(IronNugget = new ItemIronNugget(), LibItems.IRONNUGGET);

        if (ConfigurationHandler.enableHammers) {
            register(WoodHammer = (new ItemToolHammer(Item.ToolMaterial.WOOD)).setUnlocalizedName(LibItems.HAMMER + "Wood"), LibItems.HAMMER + "Wood");
            register(StoneHammer = (new ItemToolHammer(Item.ToolMaterial.STONE)).setUnlocalizedName(LibItems.HAMMER + "Stone"), LibItems.HAMMER + "Stone");
            register(IronHammer = (new ItemToolHammer(Item.ToolMaterial.IRON)).setUnlocalizedName(LibItems.HAMMER + "Iron"), LibItems.HAMMER + "Iron");
            register(GoldHammer = (new ItemToolHammer(Item.ToolMaterial.GOLD)).setUnlocalizedName(LibItems.HAMMER + "Gold"), LibItems.HAMMER + "Gold");
            register(DiamondHammer = (new ItemToolHammer(Item.ToolMaterial.DIAMOND)).setUnlocalizedName(LibItems.HAMMER + "Diamond"), LibItems.HAMMER + "Diamond");
        }

        if (ConfigurationHandler.enableExcavators) {
            register(WoodExcavator = (new ItemToolExcavator(Item.ToolMaterial.WOOD)).setUnlocalizedName(LibItems.EXCAVATOR + "Wood"), LibItems.EXCAVATOR + "Wood");
            register(StoneExcavator = (new ItemToolExcavator(Item.ToolMaterial.STONE)).setUnlocalizedName(LibItems.EXCAVATOR + "Stone"), LibItems.EXCAVATOR + "Stone");
            register(IronExcavator = (new ItemToolExcavator(Item.ToolMaterial.IRON)).setUnlocalizedName(LibItems.EXCAVATOR + "Iron"), LibItems.EXCAVATOR + "Iron");
            register(GoldExcavator = (new ItemToolExcavator(Item.ToolMaterial.GOLD)).setUnlocalizedName(LibItems.EXCAVATOR + "Gold"), LibItems.EXCAVATOR + "Gold");
            register(DiamondExcavator = (new ItemToolExcavator(Item.ToolMaterial.DIAMOND)).setUnlocalizedName(LibItems.EXCAVATOR + "Diamond"), LibItems.EXCAVATOR + "Diamond");
        }

        if (ConfigurationHandler.enableCrossbow) {
            register(CrossBow = new ItemCrossbow(), LibItems.CROSSBOW);
            register(CrossBolt = new ItemCrossBolt(), LibItems.CROSSBOLT);
        }

        if (ConfigurationHandler.enableObsidianTools) {
            register(ObsidianSword = new ModItemSword("obsidian_sword", LibMaterials.Tools.OBSIDIAN), "obsidian_sword");
            register(ObsidianPickaxe = new ModItemPickaxe("obsidian_pickaxe", LibMaterials.Tools.OBSIDIAN), "obsidian_pickaxe");
            register(ObsidianAxe = new ModItemAxe("obsidian_axe", LibMaterials.Tools.OBSIDIAN), "obsidian_axe");
            register(ObsidianSpade = new ModItemSpade("obsidian_spade", LibMaterials.Tools.OBSIDIAN), "obsidian_spade");
            register(ObsidianHoe = new ModItemHoe("obsidian_hoe", LibMaterials.Tools.OBSIDIAN), "obsidian_hoe");
        }

        if (ConfigurationHandler.enableEmeraldTools) {
            register(EmeraldSword = new ModItemSword("emerald_sword", LibMaterials.Tools.EMERALD), "emerald_sword");
            register(EmeraldPickaxe = new ModItemPickaxe("emerald_pickaxe", LibMaterials.Tools.EMERALD), "emerald_pickaxe");
            register(EmeraldAxe = new ModItemAxe("emerald_axe", LibMaterials.Tools.EMERALD), "emerald_axe");
            register(EmeraldSpade = new ModItemSpade("emerald_spade", LibMaterials.Tools.EMERALD), "emerald_spade");
            register(EmeraldHoe = new ModItemHoe("emerald_hoe", LibMaterials.Tools.EMERALD), "emerald_hoe");

        }

        if (ConfigurationHandler.enableBejeweledApple)
            register(BejeweledApple = new ItemFoodBejeweledApple(), LibItems.BEJEWELED_APPLE);
    }

    public static void register(Item item, String name)
    {
        if (item.getRegistryName() == null)
            item.setRegistryName(name);

        GameRegistry.register(item);
    }

    public static void register(Item item)
    {
        GameRegistry.register(item);
    }
}