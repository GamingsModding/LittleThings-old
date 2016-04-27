package com.gamingsmod.littlethings.common.network.message;

import com.gamingsmod.littlethings.common.block.BlockExpStore;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageStoreXP implements IMessage, IMessageHandler<MessageStoreXP, IMessage>
{
    public static final int TAKE_EXP = 0;
    public static final int GIVE_EXP = 1;

    private int xp;
    private int action;
    private BlockPos pos;

    public MessageStoreXP()
    {

    }

    public MessageStoreXP(int xp, int action, BlockPos pos)
    {
        this.xp = xp;
        this.action = action;
        this.pos = pos;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        xp = ByteBufUtils.readVarShort(buf);
        action = ByteBufUtils.readVarShort(buf);
        pos = new BlockPos(ByteBufUtils.readVarShort(buf), ByteBufUtils.readVarShort(buf), ByteBufUtils.readVarShort(buf));
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeVarShort(buf, xp);
        ByteBufUtils.writeVarShort(buf, action);
        ByteBufUtils.writeVarShort(buf, pos.getX());
        ByteBufUtils.writeVarShort(buf, pos.getY());
        ByteBufUtils.writeVarShort(buf, pos.getZ());
    }

    @Override
    public IMessage onMessage(MessageStoreXP message, MessageContext ctx)
    {
        IThreadListener listener = (WorldServer) ctx.getServerHandler().playerEntity.worldObj;
        listener.addScheduledTask(() -> BlockExpStore.runAction(ctx.getServerHandler().playerEntity, message.xp, message.action, message.pos));
        return null;
    }
}
