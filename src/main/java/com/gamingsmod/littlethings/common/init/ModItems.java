package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.item.ItemIronNugget;
import com.gamingsmod.littlethings.common.item.ItemToolExcavator;
import com.gamingsmod.littlethings.common.item.ItemToolHammer;
import com.gamingsmod.littlethings.common.lib.LibItems;
import net.minecraft.item.Item;
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

    public static void init()
    {
        register(IronNugget = new ItemIronNugget(), LibItems.IRONNUGGET);
        register(WoodHammer = (new ItemToolHammer(Item.ToolMaterial.WOOD)).setUnlocalizedName(LibItems.HAMMER + "Wood"), LibItems.HAMMER + "Wood");
        register(StoneHammer = (new ItemToolHammer(Item.ToolMaterial.STONE)).setUnlocalizedName(LibItems.HAMMER + "Stone"), LibItems.HAMMER + "Stone");
        register(IronHammer = (new ItemToolHammer(Item.ToolMaterial.IRON)).setUnlocalizedName(LibItems.HAMMER + "Iron"), LibItems.HAMMER + "Iron");
        register(GoldHammer = (new ItemToolHammer(Item.ToolMaterial.GOLD)).setUnlocalizedName(LibItems.HAMMER + "Gold"), LibItems.HAMMER + "Gold");
        register(DiamondHammer = (new ItemToolHammer(Item.ToolMaterial.DIAMOND)).setUnlocalizedName(LibItems.HAMMER + "Diamond"),LibItems.HAMMER + "Diamond");
        register(WoodExcavator = (new ItemToolExcavator(Item.ToolMaterial.WOOD)).setUnlocalizedName(LibItems.EXCAVATOR + "Wood"), LibItems.EXCAVATOR + "Wood");
        register(StoneExcavator = (new ItemToolExcavator(Item.ToolMaterial.STONE)).setUnlocalizedName(LibItems.EXCAVATOR + "Stone"), LibItems.EXCAVATOR + "Stone");
        register(IronExcavator = (new ItemToolExcavator(Item.ToolMaterial.IRON)).setUnlocalizedName(LibItems.EXCAVATOR + "Iron"), LibItems.EXCAVATOR + "Iron");
        register(GoldExcavator = (new ItemToolExcavator(Item.ToolMaterial.GOLD)).setUnlocalizedName(LibItems.EXCAVATOR + "Gold"), LibItems.EXCAVATOR + "Gold");
        register(DiamondExcavator = (new ItemToolExcavator(Item.ToolMaterial.DIAMOND)).setUnlocalizedName(LibItems.EXCAVATOR + "Diamond"),LibItems.EXCAVATOR + "Diamond");
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