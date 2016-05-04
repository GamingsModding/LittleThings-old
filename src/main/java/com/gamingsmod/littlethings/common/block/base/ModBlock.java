package com.gamingsmod.littlethings.common.block.base;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
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

    public ModBlock(String name, Material material, MapColor color)
    {
        super(material, color);
        this.setUnlocalizedName(name);
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
