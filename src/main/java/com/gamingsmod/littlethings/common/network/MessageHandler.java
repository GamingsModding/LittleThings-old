package com.gamingsmod.littlethings.common.network;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.gamingsmod.littlethings.common.network.message.MessageHeldXP;
import com.gamingsmod.littlethings.common.network.message.MessageUnenchant;
import com.gamingsmod.littlethings.common.network.message.MessageXP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class MessageHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_NAME);

    private static int i = 0;

    public static void init()
    {
        register(MessageUnenchant.class, Side.SERVER);
        register(MessageXP.class, Side.SERVER);
        register(MessageHeldXP.class, Side.CLIENT);
    }

    @SuppressWarnings("unchecked")
    private static void register(Class clazz, Side handlerSide)
    {
        INSTANCE.registerMessage(clazz, clazz, i++, handlerSide);
    }
}
