package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.block.base.IExperienceStore;
import com.gamingsmod.littlethings.common.block.base.ModBlockContainer;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import com.gamingsmod.littlethings.common.network.MessageHandler;
import com.gamingsmod.littlethings.common.network.message.MessageHeldXP;
import com.gamingsmod.littlethings.common.network.message.MessageXP;
import com.gamingsmod.littlethings.common.tileentity.TileEntityExpStore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class BlockExpStore extends ModBlockContainer implements IExperienceStore
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
            MessageHandler.INSTANCE.sendToAll(new MessageHeldXP(pos, ((TileEntityExpStore) worldIn.getTileEntity(pos)).getXp()));
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


    @Override
    public IMessage updateStoredXP(EntityPlayer player, TileEntity te, int xp, int action)
    {
        if (te instanceof TileEntityExpStore) {
            if (action == MessageXP.GIVE_EXP) {
                if (xp > ((TileEntityExpStore) te).getXp() || xp == 5000)
                    xp = ((TileEntityExpStore) te).getXp();

                ((TileEntityExpStore) te).removeXp(xp);
                player.addExperienceLevel(xp);
            } else if (action == MessageXP.TAKE_EXP) {
                if (xp >= player.experienceLevel || xp == 5000)
                    xp = player.experienceLevel;

                ((TileEntityExpStore) te).addXp(xp);
                player.removeExperienceLevel(xp);
            }
            te.markDirty();
            MessageHandler.INSTANCE.sendToAll(new MessageHeldXP(te.getPos(), ((TileEntityExpStore) te).getXp()));
        }
        return null;
    }
}
