package com.gamingsmod.littlethings.common.item.base;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ModItemVariants extends ModItem
{
    private final String[] Variants;

    public ModItemVariants(String name, String[] variants)
    {
        super(name);
        this.setHasSubtypes(true);
        this.Variants = variants;
    }

    public String[] getVariants()
    {
        return Variants;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + Variants[stack.getItemDamage()];
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        for (int i = 0; i <= Variants.length; i++) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }
}
