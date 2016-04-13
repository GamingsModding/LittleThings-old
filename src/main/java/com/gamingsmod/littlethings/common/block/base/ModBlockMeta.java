package com.gamingsmod.littlethings.common.block.base;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class ModBlockMeta extends ModBlock implements IMetaBlock
{
    public ModBlockMeta(String name, Material blockMaterialIn)
    {
        super(name, blockMaterialIn);
    }

    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }
}
