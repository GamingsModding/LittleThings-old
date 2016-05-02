package com.gamingsmod.littlethings.common.item.food;

import com.gamingsmod.littlethings.common.item.base.ModItemFood;
import com.gamingsmod.littlethings.common.lib.LibItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFoodSeedsPack extends ModItemFood
{
    public ItemFoodSeedsPack()
    {
        super(2, false, LibItems.PACK_SEEDS);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        boolean ran;

        ran = placeSeeds(stack, playerIn, worldIn, pos, hand, facing);
        ran = placeSeeds(stack, playerIn, worldIn, pos.north(), hand, facing) || ran;
        ran = placeSeeds(stack, playerIn, worldIn, pos.south(), hand, facing) || ran;
        ran = placeSeeds(stack, playerIn, worldIn, pos.east(), hand, facing) || ran;
        ran = placeSeeds(stack, playerIn, worldIn, pos.west(), hand, facing) || ran;
        ran = placeSeeds(stack, playerIn, worldIn, pos.north().east(), hand, facing) || ran;
        ran = placeSeeds(stack, playerIn, worldIn, pos.north().west(), hand, facing) || ran;
        ran = placeSeeds(stack, playerIn, worldIn, pos.south().east(), hand, facing) || ran;
        ran = placeSeeds(stack, playerIn, worldIn, pos.south().west(), hand, facing) || ran;

        if (ran) {
            --stack.stackSize;
            return EnumActionResult.SUCCESS;
        } else
            return EnumActionResult.FAIL;
    }

    protected boolean placeSeeds(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing)
    {
        if (facing == EnumFacing.UP &&
                playerIn.canPlayerEdit(pos.offset(facing), facing, stack) &&
                worldIn.getBlockState(pos).getBlock().canSustainPlant(worldIn.getBlockState(pos), worldIn, pos, EnumFacing.UP, new ItemSeeds(Blocks.wheat, Blocks.farmland)) &&
                worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), Blocks.wheat.getDefaultState());
            return true;
        } else {
            return false;
        }
    }
}
