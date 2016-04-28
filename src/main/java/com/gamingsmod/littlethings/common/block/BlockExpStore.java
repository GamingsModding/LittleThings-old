package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.block.base.ModBlockContainer;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import com.gamingsmod.littlethings.common.network.MessageHandler;
import com.gamingsmod.littlethings.common.network.message.MessageHeldXP;
import com.gamingsmod.littlethings.common.network.message.MessageStoreXP;
import com.gamingsmod.littlethings.common.tileentity.TileEntityExpStore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockExpStore extends ModBlockContainer
{
    protected static final AxisAlignedBB boundingbox = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);

    public BlockExpStore()
    {
        super(Material.iron);
        this.setUnlocalizedName(LibBlocks.EXP_STORE);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(5.0F);
        this.setResistance(2000.0F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return boundingbox;
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
            playerIn.openGui(LittleThings.instance, LibGuiId.EXPSTORE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        else
            MessageHandler.INSTANCE.sendTo(new MessageHeldXP(((TileEntityExpStore)worldIn.getTileEntity(pos)).getXp()), (EntityPlayerMP) playerIn);
        return true;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntityExpStore te = (TileEntityExpStore) worldIn.getTileEntity(pos);
        if (te.getXp() > 0) {
            int dropXp = te.getXp() >= 30 ? 112 + (te.getXp() - 30) * 9 : (te.getXp() >= 15 ? 37 + (te.getXp() - 15) * 5 : 7 + te.getXp() * 2);
            worldIn.spawnEntityInWorld(new EntityXPOrb(worldIn, pos.getX(), pos.getY(), pos.getZ(), dropXp));
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityExpStore();
    }

    public static void runAction(EntityPlayerMP playerEntity, int xp, int action, BlockPos pos)
    {
        World world = playerEntity.worldObj;
        TileEntityExpStore te = null;
        if (world.getTileEntity(pos) instanceof TileEntityExpStore)
            te = (TileEntityExpStore) world.getTileEntity(pos);

        if (te == null)
            return;

        if (action == MessageStoreXP.TAKE_EXP) {
            if (xp >= playerEntity.experienceLevel || xp == 5000)
                xp = playerEntity.experienceLevel;

            te.setXp(te.getXp() + xp);
            te.markDirty();
            playerEntity.removeExperienceLevel(xp);

        } else if (action == MessageStoreXP.GIVE_EXP) {
            if (xp > te.getXp() || xp == 5000)
                xp = te.getXp();

            te.setXp(te.getXp() - xp);
            te.markDirty();
            playerEntity.addExperienceLevel(xp);
        }

        MessageHandler.INSTANCE.sendTo(new MessageHeldXP(te.getXp()), playerEntity);
    }
}
