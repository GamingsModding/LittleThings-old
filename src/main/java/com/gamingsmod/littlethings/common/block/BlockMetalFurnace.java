package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.block.base.IMetaBlockName;
import com.gamingsmod.littlethings.common.block.base.ModBlockContainer;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.tileentity.TileEntityMetalFurnace;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockMetalFurnace extends ModBlockContainer implements IMetaBlockName
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<Types> TYPE = PropertyEnum.create("type", Types.class);
    private static boolean keepInventory;
    private boolean isBurning;

    public BlockMetalFurnace(boolean isBurning)
    {
        super(Material.iron);
        this.setRegistryName(LibBlocks.METAL_FURNACE + (isBurning ? "_lit" : ""));
        this.setUnlocalizedName(LibBlocks.METAL_FURNACE);
        this.setHardness(3.5F);
        this.setStepSound(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH)
                .withProperty(TYPE, Types.iron));
        this.isBurning = isBurning;
        if (!isBurning) this.setCreativeTab(CreativeTabs.tabDecorations);
        else this.setLightLevel(0.875F);
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        if (!this.isBurning)
            for (Types t : Types.values())
                list.add(new ItemStack(this, 1, t.getId()));
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote) {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
                enumfacing = EnumFacing.SOUTH;
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
                enumfacing = EnumFacing.NORTH;
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
                enumfacing = EnumFacing.EAST;
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
                enumfacing = EnumFacing.WEST;

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(TYPE).getId();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMetalFurnace) {
                playerIn.displayGUIChest((TileEntityMetalFurnace) tileentity);
                playerIn.addStat(StatList.furnaceInteraction);
            }
        }
        return true;
    }

    public static void setState(boolean active, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        keepInventory = true;
        if (active) {
            worldIn.setBlockState(pos, ModBlocks.MetalFurnace_Lit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(TYPE, iblockstate.getValue(TYPE)), 3);
            worldIn.setBlockState(pos, ModBlocks.MetalFurnace_Lit.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(TYPE, iblockstate.getValue(TYPE)), 3);
        } else {
            worldIn.setBlockState(pos, ModBlocks.MetalFurnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(TYPE, iblockstate.getValue(TYPE)), 3);
            worldIn.setBlockState(pos, ModBlocks.MetalFurnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(TYPE, iblockstate.getValue(TYPE)), 3);
        }
        keepInventory = false;

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMetalFurnace();
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(TYPE, Types.values()[meta]);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName()) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMetalFurnace) {
                ((TileEntityMetalFurnace) tileentity).setCustomInventoryName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!keepInventory) {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityMetalFurnace) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityMetalFurnace) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(state.getBlock(), 1, state.getValue(TYPE).getId());
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        Types type = Types.iron;
        for (Types t : Types.values())
            if ((t.getId() & meta) > 0)
                type = t;

        EnumFacing enumfacing = EnumFacing.getFront(meta - type.getId());

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(TYPE, type);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex() + state.getValue(TYPE).getId();
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
        return new BlockStateContainer(this, FACING, TYPE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("incomplete-switch")
    public void randomDisplayTick(IBlockState worldIn, World pos, BlockPos state, Random rand)
    {
        if (this.isBurning) {
            EnumFacing enumfacing = worldIn.getValue(FACING);
            double d0 = (double) state.getX() + 0.5D;
            double d1 = (double) state.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double) state.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            if (rand.nextDouble() < 0.1D)
                pos.playSound((double) state.getX() + 0.5D, (double) state.getY(), (double) state.getZ() + 0.5D, SoundEvents.block_furnace_fire_crackle, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

            switch (enumfacing) {
                case WEST:
                    pos.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    pos.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case EAST:
                    pos.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    pos.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case NORTH:
                    pos.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                    pos.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                    break;
                case SOUTH:
                    pos.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
                    pos.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return Types.values()[stack.getMetadata()].getName();
    }

    @Override
    public void registerBlockVariants(String modId)
    {
        ResourceLocation[] rl = new ResourceLocation[4];
        int i = 0;
        for (BlockMetalFurnace.Types type : BlockMetalFurnace.Types.values()) {
            if (!isBurning)
                rl[i] = new ResourceLocation(modId + "upgradedFurnace_" + type.getName());
            else
                rl[i] = new ResourceLocation(modId + "upgradedFurnace_" + type.getName() + "_lit");
            i++;
        }
        if (!isBurning)
            ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.MetalFurnace), rl);
        else
            ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.MetalFurnace_Lit), rl);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerRender()
    {
        for (BlockMetalFurnace.Types type : BlockMetalFurnace.Types.values()) {
            if (!isBurning)
                registerItemModel(type.getId(), "upgradedFurnace_" + type.getName());
            else
                registerItemModel(type.getId(), "upgradedFurnace_" + type.getName() + "_lit");
        }
    }

    public enum Types implements IStringSerializable
    {
        iron("iron", 0, 150),
        gold("gold", 1, 100),
        diamond("diamond", 2, 50),
        emerald("emerald", 3, 50);

        private String name;
        private int id;
        private int burn;

        Types(String name, int id, int burn)
        {
            this.name = name;
            this.id = id;
            this.burn = burn;
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        public int getId()
        {
            return id;
        }

        public int getBurn()
        {
            return burn;
        }
    }
}
