package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.block.base.ModBlockContainer;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.gamingsmod.littlethings.common.tileentity.TileEntityUpgradedFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class BlockUpgradedFurnace extends ModBlockContainer
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private final boolean isBurning;
    private static boolean keepInventory;

    public BlockUpgradedFurnace(String name, boolean isBurning)
    {
        super(Material.iron);
        this.setUnlocalizedName(name);
        this.setHardness(3.5F);
        this.setStepSound(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.isBurning = isBurning;
        if (!isBurning) {
            this.setCreativeTab(CreativeTabs.tabDecorations);
        } else {
            this.setLightLevel(0.875F);
        }
    }

    /**
     * Get the Item that this Block should drop when harvested.
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        int foundIndex = findIndex(state);
        if (foundIndex == -1)
            return null;

        return  Item.getItemFromBlock(ModBlocks.UpgradedFurnaces[foundIndex]);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityUpgradedFurnace)
            {
                playerIn.displayGUIChest((TileEntityUpgradedFurnace)tileentity);
                playerIn.addStat(StatList.furnaceInteraction);
            }

            return true;
        }
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        int foundIndex = findIndex(worldIn.getBlockState(pos));
        if (foundIndex == -1)
            return;

        keepInventory = true;
        if (active)
        {
            foundIndex += 1;
            worldIn.setBlockState(pos, ModBlocks.UpgradedFurnaces[foundIndex].getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, ModBlocks.UpgradedFurnaces[foundIndex].getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }
        else
        {
            worldIn.setBlockState(pos, ModBlocks.UpgradedFurnaces[foundIndex].getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(pos, ModBlocks.UpgradedFurnaces[foundIndex].getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        keepInventory = false;

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityUpgradedFurnace();
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityUpgradedFurnace)
            {
                ((TileEntityUpgradedFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory)
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityUpgradedFurnace)
            {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityUpgradedFurnace)tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        int index = findIndex(state);
        if (index == -1)
            return null;

        return new ItemStack(ModBlocks.UpgradedFurnaces[index]);
    }

    /**
     * The type of render function called. 3 for standard block models, 2 for TESR's, 1 for liquids, -1 is no render
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    public static int findIndex(IBlockState state)
    {
        Block block = state.getBlock();
        String unlocName = block.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length() + "upgradedFurnace_".length());
        if (unlocName.endsWith("_lit")) {
            unlocName = unlocName.substring(0, unlocName.length() - 4);
        }

        int foundIndex = -1;
        for (int i = 0; i < Types.values().length; i++) {
            if (Types.values()[i].toString().equals(unlocName)) {
                foundIndex = ((i + 1) * 2) - 2;
                break;
            }
        }

        return foundIndex;
    }

    public enum Types
    {
        iron,
        gold,
        diamond,
        emerald
    }
}