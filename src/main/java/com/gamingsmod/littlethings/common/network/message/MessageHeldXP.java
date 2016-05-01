package com.gamingsmod.littlethings.common.network.message;

import com.gamingsmod.littlethings.common.network.Message;
import com.gamingsmod.littlethings.common.tileentity.TileEntityExpStore;
import com.gamingsmod.littlethings.common.tileentity.base.TileEntityXpStore;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        World world = Minecraft.getMinecraft().theWorld;
        if (world.getTileEntity(pos) instanceof TileEntityExpStore)
            ((TileEntityXpStore) world.getTileEntity(pos)).setXp(xp);
        return null;
    }
}
