package com.gamingsmod.littlethings.common.network.message;

import com.gamingsmod.littlethings.common.block.BlockUnenchantingTable;
import com.gamingsmod.littlethings.common.network.Message;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageUnenchant extends Message
{
    public BlockPos pos;

    public MessageUnenchant(){}

    public MessageUnenchant(BlockPos pos)
    {
        this.pos = pos;
    }

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        BlockUnenchantingTable.runUnenchant(context.getServerHandler().playerEntity, pos);
        return null;
    }
}
