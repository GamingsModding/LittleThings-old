package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemToolHammer extends ItemPickaxe
{
    public ItemToolHammer(ToolMaterial material)
    {
        super(material);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (canHarvestBlock(blockIn)) {
            if (entityLiving.getLookVec().yCoord < -0.7 || entityLiving.getLookVec().yCoord > 0.7) {
                mineOrOtherwise(worldIn, pos.north());
                mineOrOtherwise(worldIn, pos.south());
                mineOrOtherwise(worldIn, pos.east());
                mineOrOtherwise(worldIn, pos.west());

                mineOrOtherwise(worldIn, pos.east().north());
                mineOrOtherwise(worldIn, pos.east().south());
                mineOrOtherwise(worldIn, pos.west().north());
                mineOrOtherwise(worldIn, pos.west().south());
            } else {
                mineOrOtherwise(worldIn, pos.up());
                mineOrOtherwise(worldIn, pos.down());
                if (entityLiving.getHorizontalFacing() == EnumFacing.NORTH || entityLiving.getHorizontalFacing() == EnumFacing.SOUTH) {
                    mineOrOtherwise(worldIn, pos.east());
                    mineOrOtherwise(worldIn, pos.east().up());
                    mineOrOtherwise(worldIn, pos.east().down());
                    mineOrOtherwise(worldIn, pos.west());
                    mineOrOtherwise(worldIn, pos.west().up());
                    mineOrOtherwise(worldIn, pos.west().down());
                } else if (entityLiving.getHorizontalFacing() == EnumFacing.EAST || entityLiving.getHorizontalFacing() == EnumFacing.WEST) {
                    mineOrOtherwise(worldIn, pos.north());
                    mineOrOtherwise(worldIn, pos.north().up());
                    mineOrOtherwise(worldIn, pos.north().down());
                    mineOrOtherwise(worldIn, pos.south());
                    mineOrOtherwise(worldIn, pos.south().up());
                    mineOrOtherwise(worldIn, pos.south().down());
                }
            }
        }

        return super.onBlockDestroyed(stack, worldIn, blockIn, pos, entityLiving);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", LibMisc.MOD_ID.toLowerCase() + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return getUnlocalizedName();
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    protected void mineOrOtherwise(World world, BlockPos pos)
    {
        if (canHarvestBlock(world.getBlockState(pos))) {
            world.destroyBlock(pos, true);
        }
    }
}
