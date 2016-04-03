package com.gamingsmod.littlethings.common.override;

import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;

public class ItemOverrides
{
    protected String[] playerNames = new String[]{"GingerMikie", "LilMissSpl3nd0r", "The_EliteAngel", "CamGaming69", "HCGamingMC"};
    private DecimalFormat df = new DecimalFormat("#.##");

    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onHover(ItemTooltipEvent e)
    {
        if (e.getItemStack().getItem() instanceof ItemSkull && e.getItemStack().getMetadata() == 3) {
            onSkullHover(e);
        } else if (e.getItemStack().getItem() instanceof ItemFood) {
            onFoodHover(e);
        }
    }

    private void onFoodHover(ItemTooltipEvent e)
    {
        ItemFood food = (ItemFood) e.getItemStack().getItem();
        double healAmount = food.getHealAmount(e.getItemStack());
        double saturationModifier = food.getSaturationModifier(e.getItemStack());
        df.setRoundingMode(RoundingMode.FLOOR);

        if (GuiScreen.isShiftKeyDown())
            e.getToolTip().add("H: " + healAmount + " | S: " + df.format(saturationModifier));
        else {
            e.getToolTip().add(I18n.translateToLocalFormatted("tooltip.littlethings.foodrating", String.valueOf(new Double(healAmount * saturationModifier).intValue())));
        }
    }

    private void onSkullHover(ItemTooltipEvent e)
    {
        ItemStack skull = e.getItemStack();
        String playerName = "";
        if (skull.getTagCompound() != null && skull.getTagCompound().hasKey("SkullOwner", 8)) {
            playerName = skull.getTagCompound().getString("SkullOwner");
        }

        if (skull.getTagCompound() != null && skull.getTagCompound().hasKey("SkullOwner", 10)) {
            NBTTagCompound nbttagcompound = skull.getTagCompound().getCompoundTag("SkullOwner");

            if (nbttagcompound.hasKey("Name", 8)) {
                playerName = nbttagcompound.getString("Name");
            }
        }

        if (ConfigurationHandler.enableSkullOwner) {
            //TODO When waila updates - Add to tooltip
            e.getToolTip().remove(0);
            e.getToolTip().add(0, I18n.translateToLocal("tooltip.littlethings.playerSkull"));
            e.getToolTip().add(1, I18n.translateToLocalFormatted("tooltip.littlethings.skullOwner", playerName));
        }

        if (Arrays.asList(playerNames).contains(playerName)) {
            if (ConfigurationHandler.enableColorfulText || !I18n.canTranslate("tooltip.littlethings.skull." + playerName + ".clean"))
                e.getToolTip().add(2, TextFormatting.ITALIC + I18n.translateToLocalFormatted("tooltip.littlethings.skull." + playerName));
            else
                e.getToolTip().add(2, TextFormatting.ITALIC + I18n.translateToLocalFormatted("tooltip.littlethings.skull." + playerName + ".clean"));
        }
    }
}
