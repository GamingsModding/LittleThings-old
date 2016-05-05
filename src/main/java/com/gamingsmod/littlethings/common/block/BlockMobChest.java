package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.client.model.TEMobChestRenderer;
import com.gamingsmod.littlethings.common.block.base.IMetaBlockName;
import com.gamingsmod.littlethings.common.block.base.ModBlockInventory;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.tileentity.TileEntityMobChest;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.List;

public class BlockMobChest extends ModBlockInventory implements IMetaBlockName
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<Mobs> MOB = PropertyEnum.create("mob", Mobs.class);
    protected static final AxisAlignedBB field_185569_b = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

    public BlockMobChest()
    {
        super(LibBlocks.MOBCHEST, Material.wood);
        this.setHardness(2.5F);
        this.setStepSound(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MOB, Mobs.cow));
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return field_185569_b;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(MOB, Mobs.byMeta(meta));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack stack)
    {
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (!worldIn.isRemote) playerIn.displayGUIChest((TileEntityMobChest) worldIn.getTileEntity(pos));
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMobChest();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        Mobs found = Mobs.cow;
        for (Mobs mob : Mobs.values()) {
            if ((meta & mob.getMeta()) > 0)
                found = mob;
        }

        EnumFacing enumfacing = EnumFacing.getFront(meta - found.getMeta());

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            enumfacing = EnumFacing.NORTH;

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(MOB, found);
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex() + state.getValue(MOB).getMeta();
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
        return new BlockStateContainer(this, FACING, MOB);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (Mobs mob : Mobs.values())
            list.add(new ItemStack(itemIn, 1, mob.getMeta()));
    }

    public int damageDropped(IBlockState state)
    {
        return state.getValue(MOB).getMeta();
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return Mobs.byMeta(stack.getMetadata()).getName();
    }

    @Override
    public void registerBlockVariants(String modId)
    {
        ResourceLocation[] rl = new ResourceLocation[BlockMobChest.Mobs.values().length];
        int i = 0;
        for (BlockMobChest.Mobs mob : BlockMobChest.Mobs.values()) {
            rl[i] = new ResourceLocation(modId + "mobChest_" + mob.getName());
            i++;
        }

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.MobChests), rl);
    }

    @Override
    public void registerRender()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMobChest.class, new TEMobChestRenderer());

        for (BlockMobChest.Mobs mob : BlockMobChest.Mobs.values())
            registerItemModel(mob.getMeta(), "mobChest_" + mob.getName());
    }

    public enum Mobs implements IStringSerializable
    {
        cow("cow", 0, SoundEvents.entity_cow_ambient, SoundEvents.entity_cow_death),
        chicken("chicken", 1, SoundEvents.entity_chicken_ambient, SoundEvents.entity_chicken_death),
        pig("pig", 2, SoundEvents.entity_pig_ambient, SoundEvents.entity_pig_death),
        sheep("sheep", 3, SoundEvents.entity_sheep_ambient, SoundEvents.entity_sheep_death),
        dog("dog", 4, SoundEvents.entity_wolf_ambient, SoundEvents.entity_wolf_growl),
        squid("squid", 5, SoundEvents.entity_squid_ambient, SoundEvents.entity_squid_death),
        zombie("zombie", 6, SoundEvents.entity_zombie_ambient, SoundEvents.entity_zombie_death),
        skeleton("skeleton", 7, SoundEvents.entity_skeleton_ambient, SoundEvents.entity_skeleton_death),
        creeper("creeper", 8, SoundEvents.entity_creeper_primed, SoundEvents.entity_creeper_death),
        spider("spider", 9, SoundEvents.entity_spider_ambient, SoundEvents.entity_spider_death);

        private SoundEvent closeSound;
        private SoundEvent openSound;
        private String name;
        private int meta;

        Mobs(String name, int meta, SoundEvent open, SoundEvent close)
        {
            this.name = name;
            this.meta = meta;
            this.openSound = open;
            this.closeSound = close;
        }

        @Override
        public String getName()
        {
            return this.name;
        }

        public int getMeta()
        {
            return this.meta;
        }

        @Override
        public String toString()
        {
            return getName();
        }

        public static Mobs byMeta(int meta)
        {
            return Mobs.values()[meta];
        }

        public SoundEvent getCloseSound()
        {
            return closeSound;
        }

        public SoundEvent getOpenSound()
        {
            return openSound;
        }
    }
}
