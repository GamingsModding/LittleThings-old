package com.gamingsmod.littlethings.common.events;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemFood;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FoodInformation
{
    private DecimalFormat df = new DecimalFormat("#.##");

    @SubscribeEvent
    public void onFoodHover(ItemTooltipEvent e)
    {
        if (e.getItemStack() != null && e.getItemStack().getItem() instanceof ItemFood) {
            ItemFood food = (ItemFood) e.getItemStack().getItem();
            double healAmount = food.getHealAmount(e.getItemStack());
            double saturationModifier = food.getSaturationModifier(e.getItemStack());
            df.setRoundingMode(RoundingMode.FLOOR);

            if (GuiScreen.isShiftKeyDown())
                e.getToolTip().add("H: " + healAmount + " | S: " + df.format(saturationModifier));
            else
                e.getToolTip().add(I18n.translateToLocalFormatted("tooltip.littlethings.foodrating", String.valueOf(new Double(healAmount * saturationModifier).intValue())));
        }
    }
}
