package com.gamingsmod.littlethings.common.item.base;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItem extends Item
{
    public ModItem(String name)
    {
        super();
        this.setUnlocalizedName(name);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", LibMisc.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return getUnlocalizedName();
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
