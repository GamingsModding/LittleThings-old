package com.gamingsmod.littlethings.common.network.message;

import com.gamingsmod.littlethings.client.gui.inventory.GuiExpStore;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHeldXP implements IMessage, IMessageHandler<MessageHeldXP, IMessage>
{
    private int xp;

    public MessageHeldXP()
    {

    }

    public MessageHeldXP(int xp)
    {
        this.xp = xp;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        xp = ByteBufUtils.readVarShort(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf, xp);
    }

    @Override
    public IMessage onMessage(MessageHeldXP message, MessageContext ctx)
    {
        IThreadListener listener = Minecraft.getMinecraft();
        listener.addScheduledTask(() -> GuiExpStore.setHeldXP(message.xp));
        return null;
    }
}
