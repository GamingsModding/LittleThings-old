package com.gamingsmod.littlethings.common.item.food;

import com.gamingsmod.littlethings.common.item.base.ModItemFood;
import com.gamingsmod.littlethings.common.lib.LibItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFoodBejeweledApple extends ModItemFood
{
    public ItemFoodBejeweledApple()
    {
        super(4, 1.2F, false, LibItems.BEJEWELED_APPLE);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        player.addPotionEffect(new PotionEffect(MobEffects.regeneration, 1000, 2));
        player.addPotionEffect(new PotionEffect(MobEffects.resistance, 6000, 1));
        player.addPotionEffect(new PotionEffect(MobEffects.fireResistance, 6000, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.absorption, 3000, 5));
    }
}
