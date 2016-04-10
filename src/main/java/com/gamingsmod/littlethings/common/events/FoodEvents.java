package com.gamingsmod.littlethings.common.events;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FoodEvents
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

    @SubscribeEvent
    public void OnFinish(LivingEntityUseItemEvent.Finish e) //Why not PlayerUseItemEvent.Finish... FORGE 1.9
    {
        if (e.getEntity() instanceof EntityPlayer && e.getItem() != null && e.getItem().getItem() instanceof ItemFood) {
            ItemFood food = (ItemFood) e.getItem().getItem();
            World world = e.getEntity().getEntityWorld();

            if (!world.isRemote) {
                String unlocName = food.getUnlocalizedName().substring(5).toLowerCase();
                System.out.println(unlocName);
                System.out.println(unlocName.startsWith("raw"));
                System.out.println(unlocName.endsWith("raw"));
                if (unlocName.startsWith("raw") || unlocName.endsWith("raw") || unlocName.equals("fish")) {
                    if (world.rand.nextFloat() > 0.3F)
                        ((EntityPlayer) e.getEntity()).addPotionEffect(new PotionEffect(MobEffects.hunger, 600, 0));
                }
            }
        }
    }
}
