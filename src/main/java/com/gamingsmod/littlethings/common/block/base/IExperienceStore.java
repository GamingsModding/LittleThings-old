package com.gamingsmod.littlethings.common.block.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public interface IExperienceStore
{
    IMessage updateStoredXP(EntityPlayer player, TileEntity te, int xp, int action);
}
