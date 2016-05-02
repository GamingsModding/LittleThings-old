package com.gamingsmod.littlethings.common.tileentity;

import com.gamingsmod.littlethings.common.block.BlockAnimalChest;
import com.gamingsmod.littlethings.common.init.ModBlocks;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import com.gamingsmod.littlethings.common.tileentity.base.ModTileInventory;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

/**
 * Code adapted From TileEntityEnderChest and TileEntityChest
 */
@Deprecated
public class TileEntityAnimalChest extends ModTileInventory implements ITickable
{
    public float lidAngle;
    /** The angle of the ender chest lid last tick */
    public float prevLidAngle;
    public int numPlayersUsing;
    private int ticksSinceSync;

    public TileEntityAnimalChest()
    {
        super("animalChest");
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
            double d0 = (double)i + 0.5D;
            double d1 = (double)k + 0.5D;

            SoundEvent open = null;
            switch (getAnimal()) {
                case "cow":
                    open = SoundEvents.entity_cow_ambient;
                    break;
                case "chicken":
                    open = SoundEvents.entity_chicken_ambient;
                    break;
                case "pig":
                    open = SoundEvents.entity_pig_ambient;
                    break;
                case "sheep":
                    open = SoundEvents.entity_sheep_ambient;
                    break;
                case "horse":
                    open = SoundEvents.entity_horse_ambient;
                    break;
                case "dog":
                    open = SoundEvents.entity_wolf_ambient;
                    break;
                case "squid":
                    open = SoundEvents.entity_squid_ambient;
                    break;
                case "zombie":
                    open = SoundEvents.entity_zombie_ambient;
                    break;
                case "skeleton":
                    open = SoundEvents.entity_skeleton_ambient;
                    break;
                case "creeper":
                    open = SoundEvents.entity_creeper_primed;
                    break;
                case "spider":
                    open = SoundEvents.entity_spider_ambient;
                    break;
                default:
                    open = SoundEvents.entity_cow_death;
            }
            this.worldObj.playSound(null, d0, (double)j + 0.5D, d1, open, SoundCategory.BLOCKS, 0.75F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f2 = this.lidAngle;

            if (this.numPlayersUsing > 0) this.lidAngle += f;
            else this.lidAngle -= f;

            if (this.lidAngle > 1.0F)
                this.lidAngle = 1.0F;

            float f1 = 0.5F;

            if (this.lidAngle < f1 && f2 >= f1) {
                double d3 = (double)i + 0.5D;
                double d2 = (double)k + 0.5D;

                SoundEvent close = null;
                switch (getAnimal()) {
                    case "cow":
                        close = SoundEvents.entity_cow_death;
                        break;
                    case "chicken":
                        close = SoundEvents.entity_chicken_death;
                        break;
                    case "pig":
                        close = SoundEvents.entity_pig_death;
                        break;
                    case "sheep":
                        close = SoundEvents.entity_sheep_death;
                        break;
                    case "horse":
                        close = SoundEvents.entity_horse_step;
                        break;
                    case "dog":
                        close = SoundEvents.entity_wolf_growl;
                        break;
                    case "squid":
                        close = SoundEvents.entity_squid_death;
                        break;
                    case "zombie":
                        close = SoundEvents.entity_zombie_death;
                        break;
                    case "skeleton":
                        close = SoundEvents.entity_skeleton_death;
                        break;
                    case "creeper":
                        close = SoundEvents.entity_creeper_death;
                        break;
                    case "spider":
                        close = SoundEvents.entity_spider_death;
                        break;

                    default:
                        close = SoundEvents.entity_cow_death;
                }
                this.worldObj.playSound(null, d3, (double)j + 0.5D, d2, close, SoundCategory.BLOCKS, 0.75F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
                this.lidAngle = 0.0F;
        }
    }

    public void openChest()
    {
        ++this.numPlayersUsing;
        int i = 0;
        for (BlockAnimalChest.Types type : BlockAnimalChest.Types.values()) {
            if (type.toString().equals(getAnimal()))
                break;
            i++;
        }

        worldObj.addBlockEvent(pos, ModBlocks.AnimalChests[i], 1, this.numPlayersUsing);
    }

    public void closeChest()
    {
        --this.numPlayersUsing;
        int i = 0;
        for (BlockAnimalChest.Types type : BlockAnimalChest.Types.values()) {
            if (type.toString().equals(getAnimal()))
                break;
            i++;
        }

        worldObj.addBlockEvent(pos, ModBlocks.AnimalChests[i], 1, this.numPlayersUsing);
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
        return this.hasCustomName() ? this.customName : "container.animalChest." + getAnimal();
    }

    public String getAnimal()
    {
        Block block = worldObj.getBlockState(getPos()).getBlock();
        if (!(block instanceof BlockAnimalChest))
            return null;
        return block.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length() + "animalChest_".length());
    }
}
