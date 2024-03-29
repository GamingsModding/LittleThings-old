package com.gamingsmod.littlethings.client.versioning;

import com.gamingsmod.littlethings.common.helper.PlayerHelper;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class VersionChecker
{

    public static boolean doneChecking = false;
    public static String version = "";
    public static String alpha_version = "";
    public static boolean warnedPlayer = false;

    public void init()
    {
        new ThreadVersionChecker();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent e)
    {
        if (doneChecking && e.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null && !warnedPlayer) {
            if (!version.isEmpty() && !alpha_version.isEmpty()) {
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;

                int[] onlineSplit = getIntegers(version.split("\\."));
//                int[] onlineSplit = getIntegers(new String[]{"0", "1", "5"});
                int[] onlineSplit_alpha = getIntegers(alpha_version.split("\\."));
//                int[] onlineSplit_alpha = getIntegers(new String[]{"0", "1", "7"});

                int[] clientSplit = LibMisc.BUILD.contains("GRADLE") ? new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE} : getIntegers(LibMisc.BUILD.split("\\."));
//                int[] clientSplit = getIntegers(new String[]{"0", "1", "1"});

                if (isVersionGreater(onlineSplit, onlineSplit_alpha))
                    if (onlineSplit[0] > clientSplit[0]) {
                        //RELEASE
                        player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.other"));
                        player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageOther")));
                    } else if (onlineSplit[1] > clientSplit[1]) {
                        //MAJOR
                        player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.other"));
                        player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageOther")));
                    } else if (onlineSplit[2] > clientSplit[2]) {
                        //MINOR
                        player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.other"));
                        player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageOther")));
                    } else if (PlayerHelper.isAlphaTester(player))
                        if (onlineSplit_alpha[0] > clientSplit[0]) {
                            //RELEASE
                            player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.alpha"));
                            player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageAlpha")));
                        } else if (onlineSplit_alpha[1] > clientSplit[1]) {
                            //MAJOR
                            player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.alpha"));
                            player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageAlpha")));
                        } else if (onlineSplit_alpha[2] > clientSplit[2]) {
                            //MINOR
                            player.addChatComponentMessage(new TextComponentTranslation("littlethings.versioning.alpha"));
                            player.addChatComponentMessage(ITextComponent.Serializer.jsonToComponent(I18n.translateToLocal("littlethings.versioning.messageAlpha")));
                        }
            }

            warnedPlayer = true;
        }
    }

    private boolean isVersionGreater(int[] version1, int[] version2)
    {
        if (version1[0] > version2[0]) {
            return true;
        } else if (version1[1] > version2[2]) {
            return true;
        } else if (version1[1] > version2[2]) {
            return true;
        }
        return false;
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
