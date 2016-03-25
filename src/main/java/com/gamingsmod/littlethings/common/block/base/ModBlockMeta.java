package com.gamingsmod.littlethings.common.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class ModBlockMeta extends Block implements IMetaBlock
{
    public ModBlockMeta(Material blockMaterialIn) {
        super(blockMaterialIn);
    }
}
