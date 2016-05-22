package com.gamingsmod.littlethings.common.network.message;

import com.gamingsmod.littlethings.common.network.Message;
import com.gamingsmod.littlethings.common.tileentity.base.TileEntityXpStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageXP extends Message
{
    public static final int GIVE_EXP = 0;
    public static final int TAKE_EXP = 1;

    public BlockPos pos;
    public int action;
    public int xp;

    public MessageXP()
    {

    }

    public MessageXP(BlockPos pos, int action, int xp)
    {
        this.pos = pos;
        this.action = action;
        this.xp = xp;
    }

    public MessageXP(BlockPos pos, int action)
    {
        this(pos, action, 0);
    }

    @Override
    public IMessage handleMessage(MessageContext context)
    {
        EntityPlayer player = context.getServerHandler().playerEntity;
        World world = player.worldObj;

        if (world.getTileEntity(pos) instanceof TileEntityXpStore)
            return ((TileEntityXpStore) world.getTileEntity(pos)).updateStoredXP(player, action, xp);

        return null;
    }
}
