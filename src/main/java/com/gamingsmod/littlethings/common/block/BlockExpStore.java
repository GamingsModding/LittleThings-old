package com.gamingsmod.littlethings.common.block;

import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.block.base.ModBlockContainer;
import com.gamingsmod.littlethings.common.lib.LibBlocks;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import com.gamingsmod.littlethings.common.network.message.MessageStoreXP;
import com.gamingsmod.littlethings.common.tileentity.TileEntityExpStore;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockExpStore extends ModBlockContainer
{
    public BlockExpStore()
    {
        super(Material.iron);
        this.setUnlocalizedName(LibBlocks.EXP_STORE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
            playerIn.openGui(LittleThings.instance, LibGuiId.EXPSTORE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
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

            te.xp += xp;
            playerEntity.removeExperienceLevel(xp);

        } else if (action == MessageStoreXP.GIVE_EXP) {
            if (xp > te.xp || xp == 5000)
                xp = te.xp;

            te.xp -= xp;
            playerEntity.addExperienceLevel(xp);
        }
    }
}
