package com.gamingsmod.littlethings.common.block.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlock extends Block
{
    public ModBlock(String name)
    {
        this(name, Material.rock);
    }

    public ModBlock(String name, Material material)
    {
        super(material);
        this.setUnlocalizedName(name);
    }
}
