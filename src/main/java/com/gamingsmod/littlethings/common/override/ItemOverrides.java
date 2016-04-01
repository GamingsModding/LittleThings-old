package com.gamingsmod.littlethings.common.override;

import com.gamingsmod.littlethings.common.handler.ConfigurationHandler;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemOverrides
{
    @SubscribeEvent
    @SuppressWarnings("unused")
    public void onHover(ItemTooltipEvent e)
    {
        //TODO When waila updates - Add to tooltip
        if (ConfigurationHandler.enableSkullOwner && e.getItemStack().getItem() instanceof ItemSkull && e.getItemStack().getMetadata() == 3) {
            ItemStack skull = e.getItemStack();
            e.getToolTip().remove(0);
            e.getToolTip().add(0, I18n.translateToLocal("tooltip.littlethings.playerSkull"));
            if (skull.getTagCompound() != null && skull.getTagCompound().hasKey("SkullOwner", 8)) {
                e.getToolTip().add(1, I18n.translateToLocalFormatted("tooltip.littlethings.skullOwner", skull.getTagCompound().getString("SkullOwner")));
            }

            if (skull.getTagCompound() != null && skull.getTagCompound().hasKey("SkullOwner", 10)) {
                NBTTagCompound nbttagcompound = skull.getTagCompound().getCompoundTag("SkullOwner");

                if (nbttagcompound.hasKey("Name", 8)) {
                    e.getToolTip().add(1, I18n.translateToLocalFormatted("tooltip.littlethings.skullOwner", nbttagcompound.getString("Name")));
                }
            }
        }
    }
}
