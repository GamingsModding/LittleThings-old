package com.gamingsmod.littlethings.common.item.base;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ModItemFood extends ItemFood
{
    public ModItemFood(int amount, float saturation, boolean isWolfFood, String name)
    {
        super(amount, saturation, isWolfFood);
        this.setUnlocalizedName(name);
        this.setAlwaysEdible();
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
