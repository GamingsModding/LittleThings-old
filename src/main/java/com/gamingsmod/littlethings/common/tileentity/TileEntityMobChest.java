package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.block.BlockMobChest;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.tileentity.base.ModTileInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileEntityMobChest extends ModTileInventory implements ITickable
{
    public float lidAngle;
    /**
     * The angle of the ender chest lid last tick
     */
    public float prevLidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;

    public TileEntityMobChest()
    {
        super("mobChest");
    }

    @Override
    public int getSizeInventory()
    {
        return 27;
    }

    @Override
    public void openInventory(EntityPlayer player)
    {
        openChest();
    }

    @Override
    public void closeInventory(EntityPlayer player)
    {
        closeChest();
    }

    @Override
    public void update()
    {
        this.prevLidAngle = this.lidAngle;
        int i = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        float f = 0.1F;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            double d0 = (double) i + 0.5D;
            double d1 = (double) k + 0.5D;

            this.worldObj.playSound(null, d0, (double) j + 0.5D, d1, getMob().getOpenSound(), SoundCategory.BLOCKS, 0.75F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0) this.lidAngle += f;
            else this.lidAngle -= f;

            if (this.lidAngle > 1.0F)
                this.lidAngle = 1.0F;

            float f1 = 0.5F;

            if (this.lidAngle < f1 && f2 >= f1) {
                double d3 = (double) i + 0.5D;
                double d2 = (double) k + 0.5D;

                this.worldObj.playSound(null, d3, (double) j + 0.5D, d2, getMob().getCloseSound(), SoundCategory.BLOCKS, 0.75F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
                this.lidAngle = 0.0F;
        }
    }

    public void openChest()
    {
        ++this.numPlayersUsing;
        worldObj.addBlockEvent(pos, ModBlocks.MobChests, 1, this.numPlayersUsing);
    }

    public void closeChest()
    {
        --this.numPlayersUsing;
        worldObj.addBlockEvent(pos, ModBlocks.MobChests, 1, this.numPlayersUsing);
    }

    @Override
    public boolean receiveClientEvent(int id, int type)
    {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.receiveClientEvent(id, type);
        }
    }

    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "container.animalChest." + getMob().getName();
    }

    public BlockMobChest.Mobs getMob()
    {
        return worldObj.getBlockState(this.getPos()).getValue(BlockMobChest.MOB);
    }
}