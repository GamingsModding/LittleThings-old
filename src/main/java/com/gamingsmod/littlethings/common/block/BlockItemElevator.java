package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.block.base.ModBlockContainer;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import com.gamingsmod.littlethings.common.tileentity.TileEntityItemElevator;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockItemElevator extends ModBlockContainer
{
    public BlockItemElevator()
    {
        super(Material.rock);
        this.setUnlocalizedName(LibBlocks.ITEMELEVATOR);
        this.setHardness(3.5F);
        this.setStepSound(SoundType.STONE);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityItemElevator();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
            playerIn.openGui(LittleThings.instance, LibGuiId.ITEMELEVATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof IInventory) {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)te);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack)
    {
        if (stack.hasDisplayName())
            ((TileEntityItemElevator) world.getTileEntity(pos)).setCustomName(stack.getDisplayName());
    }
}
