package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.block.base.IMetaBlockName;
import com.gamingsmod.littlethings.common.block.base.ModBlock;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockStainedClearGlass extends ModBlock implements IMetaBlockName
{
    private static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("color", EnumDyeColor.class);
    private static final PropertyBool UP = PropertyBool.create("up");
    private static final PropertyBool DOWN = PropertyBool.create("down");
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool WEST = PropertyBool.create("west");

    public BlockStainedClearGlass()
    {
        super(LibBlocks.STAINEDCLEARGLASS, Material.glass);
        this.setHardness(0.3F);
        this.setStepSound(SoundType.GLASS);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setDefaultState(blockState
                .getBaseState()
                .withProperty(COLOR, EnumDyeColor.WHITE)
                .withProperty(UP, false)
                .withProperty(DOWN, false)
                .withProperty(NORTH, false)
                .withProperty(SOUTH, false)
                .withProperty(EAST, false)
                .withProperty(WEST, false));
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(COLOR).getMetadata();
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (EnumDyeColor enumdyecolor : EnumDyeColor.values())
            list.add(new ItemStack(itemIn, 1, enumdyecolor.getMetadata()));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        IBlockState origState = state;
        state = state.withProperty(UP, worldIn.getBlockState(pos.up()).equals(origState));
        state = state.withProperty(DOWN, worldIn.getBlockState(pos.down()).equals(origState));
        state = state.withProperty(NORTH, worldIn.getBlockState(pos.north()).equals(origState));
        state = state.withProperty(SOUTH, worldIn.getBlockState(pos.south()).equals(origState));
        state = state.withProperty(WEST, worldIn.getBlockState(pos.west()).equals(origState));
        state = state.withProperty(EAST, worldIn.getBlockState(pos.east()).equals(origState));
        return state;
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
        super.onNeighborChange(world, pos, neighbor);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COLOR, UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return EnumDyeColor.byMetadata(stack.getMetadata()).getUnlocalizedName();
    }

    @Override
    public void registerBlockVariants(String modId)
    {
        ResourceLocation[] rl = new ResourceLocation[16];
        int i = 0;
        for (EnumDyeColor color : EnumDyeColor.values()) {
            rl[i] = new ResourceLocation(modId + "stainedClearGlass_" + color.getName());
            i++;
        }

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.StainedClearGlass), rl);
    }

    @Override
    public void registerRender()
    {
        for (EnumDyeColor color : EnumDyeColor.values())
            registerItemModel(color.getMetadata(), "stainedClearGlass_" + color.getName());
    }
}
