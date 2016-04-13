package com.gamingsmod.littlethings.common.block.base;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public abstract class ModBlockContainer extends BlockContainer
{
    protected ModBlockContainer(Material materialIn)
    {
        super(materialIn);
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
