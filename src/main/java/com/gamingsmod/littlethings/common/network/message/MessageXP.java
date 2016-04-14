package com.gamingsmod.littlethings.common.network.message;

import com.gamingsmod.littlethings.common.block.BlockUnenchantingTable;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageXP implements IMessage, IMessageHandler<MessageXP, IMessage>
{
    private int xp;
    private ItemStack stack;

    public MessageXP()
    {

    }

    public MessageXP(int xp, ItemStack stack)
    {
        this.xp = xp;
        this.stack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        xp = ByteBufUtils.readVarShort(buf);
        stack = ByteBufUtils.readItemStack(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf, xp);
        ByteBufUtils.writeItemStack(buf, stack);
    }

    @Override
    public IMessage onMessage(MessageXP message, MessageContext ctx)
    {
        IThreadListener listener = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
        listener.addScheduledTask(() -> BlockUnenchantingTable.runUnenchant(ctx.getServerHandler().playerEntity, message.xp, message.stack));
        return null;
    }
}
