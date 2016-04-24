package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.SoundType;

public class BlockStoneTorch extends BlockTorch
{
    public BlockStoneTorch()
    {
        super();
        setHardness(0.0F);
        setLightLevel(0.9375F);
        setStepSound(SoundType.WOOD);
        setUnlocalizedName(LibBlocks.STONE_TORCH);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", LibMisc.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
