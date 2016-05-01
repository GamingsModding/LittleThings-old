package com.gamingsmod.littlethings.common.network.message;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.network.Message;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHeldXP extends Message
{
    public BlockPos pos;
    public int xp;

    public MessageHeldXP()
    {

    }

    public MessageHeldXP(BlockPos pos, int xp)
    {
        this.pos = pos;
        this.xp = xp;
    }

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        LittleThings.proxy.updateXpBlock(pos, xp);
        return null;
    }
}
