package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.common.item.ItemIronNugget;
import com.gamingsmod.littlethings.common.lib.LibItems;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems
{
    public static Item IronNugget;

    public static void init()
    {
        reg(IronNugget = new ItemIronNugget(), LibItems.IRONNUGGET);
    }

    private static void reg(Item item, String name)
    {
        GameRegistry.registerItem(item, name);
    }
}
