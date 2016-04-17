package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Set;

/**
 * Again, Inspired by Tinkers Construct
 */
public class ItemToolExcavator extends ItemSpade
{
    private static final Material[] effective = new Material[]{
            Material.grass,
            Material.ground,
            Material.sand,
            Material.craftedSnow,
            Material.snow,
            Material.clay,
            Material.cake
    };

    private static final Set<Block> vanilla_effective = Sets.newHashSet(Blocks.clay, Blocks.dirt, Blocks.farmland, Blocks.grass, Blocks.gravel, Blocks.mycelium, Blocks.sand, Blocks.snow, Blocks.snow_layer, Blocks.soul_sand, Blocks.grass_path);

    public ItemToolExcavator(ToolMaterial material)
    {
        super(material);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving)
    {
        if (isEffective(blockIn)) {
            if (entityLiving.getLookVec().yCoord < -0.7 || entityLiving.getLookVec().yCoord > 0.7) {
                mineOrOtherwise(worldIn, pos.north(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.south(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.east(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.west(), stack, entityLiving);

                mineOrOtherwise(worldIn, pos.east().north(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.east().south(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.west().north(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.west().south(), stack, entityLiving);
            } else {
                mineOrOtherwise(worldIn, pos.up(), stack, entityLiving);
                mineOrOtherwise(worldIn, pos.down(), stack, entityLiving);
                if (entityLiving.getHorizontalFacing() == EnumFacing.NORTH || entityLiving.getHorizontalFacing() == EnumFacing.SOUTH) {
                    mineOrOtherwise(worldIn, pos.east(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.east().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.east().down(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west().down(), stack, entityLiving);
                } else if (entityLiving.getHorizontalFacing() == EnumFacing.EAST || entityLiving.getHorizontalFacing() == EnumFacing.WEST) {
                    mineOrOtherwise(worldIn, pos.north(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.north().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.north().down(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.south(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.south().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.south().down(), stack, entityLiving);
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

    protected void mineOrOtherwise(World world, BlockPos pos, ItemStack tool, EntityLivingBase player)
    {
        IBlockState state = world.getBlockState(pos);

        if (isEffective(state)) {
            state.getBlock().harvestBlock(world, (EntityPlayer) player, pos, state, world.getTileEntity(pos), tool);
            world.destroyBlock(pos, false);
        }
    }

    private boolean isEffective(IBlockState state)
    {
        return Arrays.asList(effective).contains(state.getMaterial()) || vanilla_effective.contains(state);
    }
}
