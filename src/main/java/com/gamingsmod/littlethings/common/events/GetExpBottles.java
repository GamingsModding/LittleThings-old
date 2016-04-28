package com.gamingsmod.littlethings.common.events;

import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GetExpBottles
{
    @SubscribeEvent
    public void onHover(ItemTooltipEvent e)
    {
        if (e.getItemStack().getItem() instanceof ItemExpBottle)
            e.getToolTip().add("Sneak + Right Click empty bottles to craft");
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem e)
    {
        if (e.getItemStack() != null && e.getItemStack().getItem() instanceof ItemGlassBottle && e.getEntityPlayer().isSneaking() && (e.getEntityPlayer().experienceLevel >= 1 || e.getEntityPlayer().isCreative())) {
            e.getItemStack().stackSize--;
            if (e.getItemStack().stackSize <= 0)
                e.getEntityPlayer().inventory.deleteStack(e.getItemStack());
            e.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(Items.experience_bottle));
            e.getEntityPlayer().removeExperienceLevel(1);
        }
    }
}
