package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.item.base.ModItem;
import com.gamingsmod.littlethings.common.lib.LibItems;
import net.minecraft.creativetab.CreativeTabs;

public class ItemIronNugget extends ModItem
{
    public ItemIronNugget() {
        super(LibItems.IRONNUGGET);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
}
