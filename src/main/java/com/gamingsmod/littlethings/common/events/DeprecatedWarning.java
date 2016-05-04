package com.gamingsmod.littlethings.common.events;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DeprecatedWarning
{
    public static boolean modHasDeprecatedClasses = false;
    public static String[] deprecatedAreas = new String[0];
    private static boolean warnedPlayer = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e)
    {
        if (Minecraft.getMinecraft().thePlayer != null && e.phase == TickEvent.Phase.END && !warnedPlayer && modHasDeprecatedClasses) {
            Style s = new Style().setColor(TextFormatting.RED);
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            player.addChatMessage(new TextComponentString(LibMisc.MOD_NAME + " has deprecated blocks! ").setChatStyle(s));
            player.addChatMessage(new TextComponentString("The following need to be replaced before the next update: ").setChatStyle(s));

            for (String str : deprecatedAreas)
                player.addChatMessage(new TextComponentString("- " + str).setChatStyle(s));

            warnedPlayer = true;
        }
    }
}
