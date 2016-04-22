package com.gamingsmod.littlethings.common.lib;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class LibMaterials
{
    public static class Tools
    {
        public static final Item.ToolMaterial OBSIDIAN = EnumHelper.addToolMaterial("obsidian", 3, 2000, 8.0F, 2.0F, 10);
        public static final Item.ToolMaterial EMERALD = EnumHelper.addToolMaterial("emerald", 3, 1561, 9.0F, 3.0F, 22);
    }
}
