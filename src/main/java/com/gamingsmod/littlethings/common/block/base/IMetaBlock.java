package com.gamingsmod.littlethings.common.block.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public interface IMetaBlock extends IMetaBlockName {

    IBlockState getStateFromMeta(int meta);

    default int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    int getMetaFromState(IBlockState state);

    void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list);

    default ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, this.getMetaFromState(world.getBlockState(pos)));
    }
}