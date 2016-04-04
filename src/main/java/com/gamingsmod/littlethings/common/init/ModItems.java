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
        register(IronNugget = new ItemIronNugget(), LibItems.IRONNUGGET);
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
