package com.gamingsmod.littlethings.common.network;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.gamingsmod.littlethings.common.network.message.MessageStoreXP;
import com.gamingsmod.littlethings.common.network.message.MessageXP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_NAME + "_network");

    public static void preInit()
    {
        int i = 0;
        INSTANCE.registerMessage(MessageXP.class, MessageXP.class, ++i, Side.SERVER);
        INSTANCE.registerMessage(MessageStoreXP.class, MessageStoreXP.class, ++i, Side.SERVER);
    }
}
