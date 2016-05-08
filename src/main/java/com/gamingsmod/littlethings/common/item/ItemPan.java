package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.item.base.ModItem;
import com.gamingsmod.littlethings.common.lib.LibItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ItemPan extends ModItem
{
    public ItemPan()
    {
        super(LibItems.PAN);
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setMaxStackSize(1);
        this.setMaxDamage(150);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        target.knockBack(target, 2.0F, (double) MathHelper.sin(attacker.rotationYaw * 0.017453292F), (double) (-MathHelper.cos(attacker.rotationYaw * 0.017453292F)));
        return false;
    }
}
