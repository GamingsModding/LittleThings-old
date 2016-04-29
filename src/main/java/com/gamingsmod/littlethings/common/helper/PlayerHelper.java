package com.gamingsmod.littlethings.common.helper;

import com.gamingsmod.littlethings.common.events.FriendsSkulls;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;

public class PlayerHelper
{
    public static boolean isAlphaTester(String playerName)
    {
        return Arrays.asList(FriendsSkulls.otherNames).contains(playerName) || Arrays.asList(FriendsSkulls.otherNames).contains(playerName);
    }

    public static boolean isAlphaTester(EntityPlayer player)
    {
        return isAlphaTester(player.getDisplayNameString());
    }
}
