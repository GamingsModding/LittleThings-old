package com.gamingsmod.littlethings.common.item.base;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ModItemVariants extends ModItem
{
    private final String[] Variants;
    private String baseName;

    public ModItemVariants(String name, String[] variants)
    {
        super(name);
        this.baseName = name;
        this.setHasSubtypes(true);
        this.Variants = variants;
    }

    @Override
    public void registerItemVariants(String modId)
    {
        for (String name : getVariants())
            ModelBakery.registerItemVariants(this, new ResourceLocation(LibMisc.PREFIX_MOD + baseName + "_" + name));
    }

    @Override
    public void registerRender()
    {
        for (int i = 0; i < Variants.length; i++)
            registerItemModel(i, baseName + "_" + Variants[i]);
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
        for (int i = 0; i < Variants.length; i++) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }
}
