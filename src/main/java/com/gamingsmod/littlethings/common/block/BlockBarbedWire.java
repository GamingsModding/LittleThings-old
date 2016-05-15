package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.block.base.ModBlock;
import com.gamingsmod.littlethings.common.init.ModDamageSource;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockBarbedWire extends ModBlock
{
    public BlockBarbedWire()
    {
        super(LibBlocks.BARBED_WIRE, Material.web, MapColor.ironColor);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setLightOpacity(1);
        this.setHardness(6.0F);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }


    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!(entityIn instanceof EntityLivingBase))
            return;
        entityIn.setInWeb();
        entityIn.attackEntityFrom(ModDamageSource.BARBED_WIRE, 3.0F);
    }
}
