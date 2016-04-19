package com.gamingsmod.littlethings.common.events;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
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
            IBlockState state = e.getWorld().getBlockState(pos);
            if (state.getBlock() instanceof BlockCrops && state.getValue(BlockCrops.AGE) == 7) {
                e.getWorld().setBlockState(pos, state.withProperty(BlockCrops.AGE, 0));
                state.getBlock().dropBlockAsItemWithChance(e.getWorld(), e.getPos(), state, 1, 0);
            }
        }
    }
}
