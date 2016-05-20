package com.gamingsmod.littlethings.common.events;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ReplaceArmor
{
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickItem e)
    {
        if (e.getItemStack() != null) {
            EntityPlayer player = e.getEntityPlayer();
            ItemStack stack = e.getItemStack();

            if (stack.getItem() instanceof ItemArmor) {
                EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(stack);
                ItemStack old = player.getItemStackFromSlot(slot);
                if (old != null) {
                    old = old.copy();
                    player.setItemStackToSlot(slot, stack.copy());
                    stack.stackSize = 0;
                    player.setHeldItem(e.getHand(), old);
                }
            }
        }
    }
}
