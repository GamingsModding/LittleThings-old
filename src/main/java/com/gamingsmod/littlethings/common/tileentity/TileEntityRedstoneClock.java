package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.block.BlockRedstoneClock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityRedstoneClock extends TileEntity implements ITickable
{
    public int currentTick = 0;
    public int maxTick = 20;

    @Override
    public void update()
    {
        IBlockState currentState = worldObj.getBlockState(getPos());
        if (worldObj.getStrongPower(getPos()) >= 1 && !currentState.getValue(BlockRedstoneClock.POWERED)) return;

        currentTick++;
        if (!currentState.getValue(BlockRedstoneClock.POWERED)) {
            if (currentTick >= maxTick) {
                worldObj.setBlockState(getPos(), currentState.withProperty(BlockRedstoneClock.POWERED, true));
                currentTick = 0;
            }
        } else {
            if (currentTick >= 4) {
                worldObj.setBlockState(getPos(), currentState.withProperty(BlockRedstoneClock.POWERED, false));
                currentTick = 0;
            }
        }
    }
}
