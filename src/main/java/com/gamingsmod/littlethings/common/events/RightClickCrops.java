package com.gamingsmod.littlethings.common.events;

import net.minecraft.block.BlockCrops;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RightClickCrops
{
    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock e)
    {
        if (!e.getWorld().isRemote) {
            BlockPos pos = e.getPos();
            if (e.getWorld().getBlockState(pos).getBlock() instanceof BlockCrops) {
                e.getWorld().setBlockState(pos, e.getWorld().getBlockState(pos).withProperty(BlockCrops.AGE, 0));
            }
        }
    }
}
