package com.gamingsmod.littlethings.common.events;

import net.minecraft.block.BlockBeetroot;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
            if (state.getBlock() instanceof BlockCrops) {
                BlockCrops block = (BlockCrops) state.getBlock();
                if ((block == Blocks.wheat || block == Blocks.carrots || block == Blocks.potatoes) && state.getValue(BlockCrops.AGE) == 7) {
                    performAction(e.getWorld(), e.getPos(), state, BlockCrops.AGE);
                } else if (block == Blocks.beetroots && state.getValue(BlockBeetroot.BEETROOT_AGE) == 3) {
                    performAction(e.getWorld(), e.getPos(), state, BlockBeetroot.BEETROOT_AGE);
                }
            }
        }
    }

    public void performAction(World world, BlockPos pos, IBlockState state, PropertyInteger prop)
    {
        world.setBlockState(pos, state.withProperty(prop, 0));
        state.getBlock().dropBlockAsItemWithChance(world, pos, state, 1, 0);
    }
}
