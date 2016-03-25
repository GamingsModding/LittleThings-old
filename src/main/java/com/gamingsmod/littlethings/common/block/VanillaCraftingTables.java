package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

import java.util.List;

public class VanillaCraftingTables extends Block implements IMetaBlockName
{
    public static final PropertyEnum TYPE = PropertyEnum.create("type", VanillaCraftingTables.Variant.class);

    public VanillaCraftingTables()
    {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setUnlocalizedName(LibBlocks.VANILLACRAFTINGTABLE);
        this.setHardness(2.5F);
        this.setStepSound(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, Variant.SPRUCE));
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote) {
            playerIn.displayGui(new InterfaceCraftingTable(worldIn, pos));
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
        switch (meta) {
            case 0:
                return getDefaultState().withProperty(TYPE, Variant.SPRUCE);
            case 1:
                return getDefaultState().withProperty(TYPE, Variant.BIRCH);
            case 2:
                return getDefaultState().withProperty(TYPE, Variant.JUNGLE);
            case 3:
                return getDefaultState().withProperty(TYPE, Variant.ACACIA);
            case 4:
                return getDefaultState().withProperty(TYPE, Variant.DARK_OAK);
        }
        return getDefaultState().withProperty(TYPE, Variant.SPRUCE);
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
                return "spruce";
            case 1:
                return "birch";
            case 2:
                return "jungle";
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


    public static class InterfaceCraftingTable implements IInteractionObject
    {
        private final World world;
        private final BlockPos position;

        public InterfaceCraftingTable(World worldIn, BlockPos pos)
        {
            this.world = worldIn;
            this.position = pos;
        }

        /**
         * Get the name of this object. For players this returns their username
         */
        public String getName()
        {
            return null;
        }

        /**
         * Returns true if this thing is named
         */
        public boolean hasCustomName()
        {
            return false;
        }

        /**
         * Get the formatted ChatComponent that will be used for the sender's username in chat
         */
        public ITextComponent getDisplayName()
        {
            return new TextComponentTranslation(Blocks.crafting_table.getUnlocalizedName() + ".name", new Object[0]);
        }

        public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
        {
            return new ContainerWorkbench(playerInventory, this.world, this.position);
        }

        public String getGuiID()
        {
            return "minecraft:crafting_table";
        }
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
