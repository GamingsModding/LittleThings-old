package com.gamingsmod.littlethings.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DisplayDeathCoords
{
    @SubscribeEvent
    public void onPlayerDeath(PlayerDropsEvent e)
    {
        EntityPlayer player = e.getEntityPlayer();
        int x = (int) player.posX;
        int y = (int) player.posY;
        int z = (int) player.posZ;
        int dim = player.dimension;
        player.addChatComponentMessage(new TextComponentTranslation("message.littlethings.deathCoords", x, y, z));
        player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("message.littlethings.deathLinks")
                .replaceAll("%w%", String.valueOf(dim))
                .replaceAll("%x%", String.valueOf(x))
                .replaceAll("%y%", String.valueOf(y))
                .replaceAll("%z%", String.valueOf(z))));
    }
}
