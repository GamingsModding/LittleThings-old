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
        worldIn.destroyBlock(pos.up(), true);
        worldIn.destroyBlock(pos.down(), true);
        if (entityLiving.getHorizontalFacing() == EnumFacing.NORTH || entityLiving.getHorizontalFacing() == EnumFacing.SOUTH) {
            worldIn.destroyBlock(pos.east(), true);
            worldIn.destroyBlock(pos.east().up(), true);
            worldIn.destroyBlock(pos.east().down(), true);
            worldIn.destroyBlock(pos.west(), true);
            worldIn.destroyBlock(pos.west().up(), true);
            worldIn.destroyBlock(pos.west().down(), true);
        } else if (entityLiving.getHorizontalFacing() == EnumFacing.EAST || entityLiving.getHorizontalFacing() == EnumFacing.WEST) {
            worldIn.destroyBlock(pos.north(), true);
            worldIn.destroyBlock(pos.north().up(), true);
            worldIn.destroyBlock(pos.north().down(), true);
            worldIn.destroyBlock(pos.south(), true);
            worldIn.destroyBlock(pos.south().up(), true);
            worldIn.destroyBlock(pos.south().down(), true);
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
}
