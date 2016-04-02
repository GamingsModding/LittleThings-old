package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.block.base.ModBlockMeta;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class BlockVanillaCraftingTables extends ModBlockMeta
{
    public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockVanillaCraftingTables.Variant.class);

    public BlockVanillaCraftingTables()
    {
        super(LibBlocks.VANILLACRAFTINGTABLE, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(2.5F);
        this.setStepSound(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, Variant.SPRUCE));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(LittleThings.instance, LibGuiId.VANILLACRAFTINGTABLES, worldIn, pos.getX(), pos.getY(), pos.getZ());
            playerIn.addStat(StatList.craftingTableInteraction);
        }

        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(TYPE, Variant.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        Variant type = (Variant) state.getValue(TYPE);
        return type.getId();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (Variant v : Variant.values()) {
            list.add(new ItemStack(itemIn, 1, v.getId()));
        }
    }

    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, this.getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return Variant.values()[stack.getItemDamage()].getName();
    }

    public enum Variant implements IStringSerializable
    {
        SPRUCE(0, "spruce"),
        BIRCH(1, "birch"),
        JUNGLE(2, "jungle"),
        ACACIA(3, "acacia"),
        DARK_OAK(4, "darkoak");

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
