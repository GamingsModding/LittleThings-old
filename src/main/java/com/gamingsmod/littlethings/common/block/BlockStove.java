package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.block.base.ModBlockContainer;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import com.gamingsmod.littlethings.common.tileentity.TileEntityStove;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
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
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStove extends ModBlockContainer
{
    public static final PropertyBool ON = PropertyBool.create("on");
    public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.create("facing", EnumFacing.class);
    private static boolean keepInventory;

    public BlockStove()
    {
        super(LibBlocks.STOVE, Material.iron);
        this.setHardness(2.5F);
        this.setStepSound(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ON, false));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote)
            playerIn.openGui(LittleThings.instance, LibGuiId.STOVE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityStove();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityStove) {
                ((TileEntityStove) tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof IInventory) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) te);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        boolean on = (meta & 8) > 0;
        EnumFacing facing = EnumFacing.getFront(meta - (on ? 8 : 0));
        return this.getDefaultState().withProperty(FACING, facing).withProperty(ON, on);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex() + (state.getValue(ON) ? 8 : 0);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ON, FACING);
    }

    public static void setState(boolean burning, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        keepInventory = true;

        worldIn.setBlockState(pos, ModBlocks.Stove.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(ON, burning), 3);
        worldIn.setBlockState(pos, ModBlocks.Stove.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(ON, burning), 3);

        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
}
