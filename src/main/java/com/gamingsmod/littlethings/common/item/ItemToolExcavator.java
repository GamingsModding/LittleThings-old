package com.gamingsmod.littlethings.common.item;

import com.gamingsmod.littlethings.common.helper.Vector3;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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
        EnumFacing facing = getFacing(entityLiving);

        if (isEffective(blockIn)) {
            if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
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
                if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                    mineOrOtherwise(worldIn, pos.east(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.east().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.east().down(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west().up(), stack, entityLiving);
                    mineOrOtherwise(worldIn, pos.west().down(), stack, entityLiving);
                } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
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
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        boolean ran;
        ran = flatten(worldIn, facing, pos, stack, playerIn);
        ran = flatten(worldIn, facing, pos.north(), stack, playerIn) || ran;
        ran = flatten(worldIn, facing, pos.north().east(), stack, playerIn) || ran;
        ran = flatten(worldIn, facing, pos.north().west(), stack, playerIn) || ran;
        ran = flatten(worldIn, facing, pos.south(), stack, playerIn) || ran;
        ran = flatten(worldIn, facing, pos.south().east(), stack, playerIn) || ran;
        ran = flatten(worldIn, facing, pos.south().west(), stack, playerIn) || ran;
        ran = flatten(worldIn, facing, pos.east(), stack, playerIn) || ran;
        ran = flatten(worldIn, facing, pos.west(), stack, playerIn) || ran;

        if (ran)
            return EnumActionResult.SUCCESS;
        else
            return EnumActionResult.PASS;
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

    protected EnumFacing getFacing(Entity player)
    {
        Vector3 origin = Vector3.fromEntity(player);
        if (player instanceof EntityPlayer)
            origin.add(0, player.getEyeHeight(), 0);

        Vector3 look = new Vector3(player.getLookVec());

        Vector3 end = origin.copy().add(look.copy().normalize().multiply(10));
        RayTraceResult pos = player.worldObj.rayTraceBlocks(origin.toVec3D(), end.toVec3D());
        return pos.sideHit;
    }

    protected boolean flatten(World worldIn, EnumFacing facing, BlockPos pos, ItemStack stack, EntityPlayer playerIn)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if (facing != EnumFacing.DOWN && worldIn.getBlockState(pos.up()).getMaterial() == Material.air && block == Blocks.grass) {
            IBlockState iblockstate1 = Blocks.grass_path.getDefaultState();
            worldIn.playSound(playerIn, pos, SoundEvents.item_shovel_flatten, SoundCategory.BLOCKS, 1.0F, 1.0F);

            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos, iblockstate1, 11);
                stack.damageItem(1, playerIn);
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean isEffective(IBlockState state)
    {
        return Arrays.asList(effective).contains(state.getMaterial()) || vanilla_effective.contains(state);
    }
}
