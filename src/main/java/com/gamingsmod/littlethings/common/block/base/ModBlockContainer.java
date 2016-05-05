package com.gamingsmod.littlethings.common.block.base;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class ModBlockContainer extends ModBlock implements ITileEntityProvider
{
    protected ModBlockContainer(Material material)
    {
        super(material);
    }

    protected ModBlockContainer(String name, Material materialIn)
    {
        this(name, materialIn, materialIn.getMaterialMapColor());
    }

    protected ModBlockContainer(String name, Material materialIn, MapColor color)
    {
        super(name, materialIn, color);
        this.isBlockContainer = true;
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
    {
        super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(eventID, eventParam);
    }
}
