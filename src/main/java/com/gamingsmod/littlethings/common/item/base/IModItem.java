package com.gamingsmod.littlethings.common.item.base;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IModItem
{
    @SideOnly(Side.CLIENT)
    void registerItemVariants(String modId);

    @SideOnly(Side.CLIENT)
    void registerRender();
}
