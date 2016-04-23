package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.item.base.ModItemVariants;
import com.gamingsmod.littlethings.common.lib.LibItems;

public class ItemCrossBolt extends ModItemVariants
{
    public ItemCrossBolt()
    {
        super(LibItems.CROSSBOLT, new String[]{"normal", "explosive"});
    }
}
