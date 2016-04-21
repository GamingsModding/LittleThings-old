package com.gamingsmod.littlethings.client.versioning;

import com.gamingsmod.littlethings.common.events.FriendsSkulls;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Arrays;

public class VersionChecker
{

    public static boolean doneChecking = false;
    public static String version = "";
    public static boolean warnedPlayer = false;

    public void init()
    {
        new ThreadVersionChecker();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e)
    {
        if (doneChecking && e.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null && !warnedPlayer)
        {
            if (!version.isEmpty()) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;

//                int[] onlineSplit = getIntegers(version.split("\\."));
                int[] onlineSplit = getIntegers(new String[]{"0", "1", "5"});

//                int[] clientSplit = LibMisc.BUILD.contains("GRADLE") ? new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE} : getIntegers( LibMisc.BUILD.split("\\."));
                int[] clientSplit = getIntegers(new String[]{"0", "1", "1"});

                if (onlineSplit[0] > clientSplit[0]) {
                    //MAJOR
                    player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.other"));
                    player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageOther")));
                } else if (onlineSplit[1] > clientSplit[1]) {
                    //MINOR
                    player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.other"));
                    player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageOther")));
                } else if (onlineSplit[2] > clientSplit[2]) {
                    //BUILD - Alpha Test Group Only
                    if (Arrays.asList(FriendsSkulls.playerNames).contains(player.getDisplayNameString()) || Arrays.asList(FriendsSkulls.otherNames).contains(player.getDisplayNameString())) {
                        player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.alpha"));
                        player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageAlpha")));
                    }
                }
            }

            warnedPlayer = true;
        }
    }

    public int[] getIntegers(String[] numbers)
    {
        int[] temp = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            temp[i] = Integer.valueOf(numbers[i]);
        }
        return temp;
    }
}
