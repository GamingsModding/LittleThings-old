package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.item.base.ModItemVariants;
import com.gamingsmod.littlethings.common.lib.LibItems;
import net.minecraft.creativetab.CreativeTabs;

public class ItemCrossBolt extends ModItemVariants
{
    public ItemCrossBolt()
    {
        super(LibItems.CROSSBOLT, new String[]{"normal", "explosive", "spectral", "wither", "poison", "slowness"});
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
}
