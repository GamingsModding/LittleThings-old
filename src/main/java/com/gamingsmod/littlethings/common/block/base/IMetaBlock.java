package com.gamingsmod.littlethings.common.block.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IMetaBlock extends IMetaBlockName
{

    IBlockState getStateFromMeta(int meta);

    int getMetaFromState(IBlockState state);

    void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list);
}