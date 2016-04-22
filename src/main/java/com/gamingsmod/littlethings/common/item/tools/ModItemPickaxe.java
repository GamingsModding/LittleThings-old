package com.gamingsmod.littlethings.common.item.tools;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ModItemPickaxe extends ItemPickaxe
{
    public ModItemPickaxe(String name, ToolMaterial material)
    {
        super(material);
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
