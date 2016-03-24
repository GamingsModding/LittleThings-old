package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.lib.LibBlocks;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class VanillaCraftingTables extends BlockWorkbench implements IMetaBlockName
{
    public static final PropertyEnum TYPE = PropertyEnum.create("type", VanillaCraftingTables.Variant.class);

    public VanillaCraftingTables()
    {
        super();
        this.setUnlocalizedName(LibBlocks.VANILLACRAFTINGTABLE);
        this.setHardness(2.5F);
        this.setStepSound(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, Variant.BIRCH_WORKBENCH));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch (meta) {
            case 0:
                return getDefaultState().withProperty(TYPE, Variant.BIRCH_WORKBENCH);
            case 1:
                return getDefaultState().withProperty(TYPE, Variant.JUNGLE_WORKBENCH);
            case 2:
                return getDefaultState().withProperty(TYPE, Variant.SPRUCE_WORKBENCH);
            case 3:
                return getDefaultState().withProperty(TYPE, Variant.ACACIA_WORKBENCH);
            case 4:
                return getDefaultState().withProperty(TYPE, Variant.DARKOAK_WORKBENCH);
        }
        return getDefaultState().withProperty(TYPE, Variant.BIRCH_WORKBENCH);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        Variant type = (Variant) state.getValue(TYPE);
        return type.getId();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
        list.add(new ItemStack(itemIn, 1, 2));
        list.add(new ItemStack(itemIn, 1, 3));
        list.add(new ItemStack(itemIn, 1, 4));
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case 0:
                return "birch";
            case 1:
                return "jungle";
            case 2:
                return "spruce";
            case 3:
                return "acacia";
            case 4:
                return "darkoak";
        }
        return "";
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    public enum Variant implements IStringSerializable
    {
        BIRCH_WORKBENCH(0, "birch"),
        JUNGLE_WORKBENCH(1, "jungle"),
        SPRUCE_WORKBENCH(2, "spruce"),
        ACACIA_WORKBENCH(3, "acacia"),
        DARKOAK_WORKBENCH(4, "darkoak");

        private int id;
        private String name;

        Variant(int ID, String name)
        {
            this.id = ID;
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return getName();
        }
    }
}
