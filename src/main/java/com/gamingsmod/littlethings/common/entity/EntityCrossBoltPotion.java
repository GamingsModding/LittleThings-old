package com.gamingsmod.littlethings.common.entity;

import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCrossBoltPotion extends EntityCrossBolt
{
    //Length of Spectral Arrows
    public int duration = 200;
    private PotionEffect potionEffect;

    public EntityCrossBoltPotion(World worldIn)
    {
        super(worldIn);
    }

    public EntityCrossBoltPotion(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityCrossBoltPotion(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    public EntityCrossBoltPotion setPotionEffect(Potion effect)
    {
        return setPotionEffect(effect, 0);
    }

    public EntityCrossBoltPotion setPotionEffect(Potion effect, int amp)
    {
        this.potionEffect = new PotionEffect(effect, duration, amp);
        return this;
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(ModItems.CrossBolt, 1, 2);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        if (tagCompund.hasKey("Duration"))
        {
            this.duration = tagCompund.getInteger("Duration");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Duration", this.duration);
    }

    @Override
    protected void arrowHit(EntityLivingBase living)
    {
        living.addPotionEffect(this.potionEffect);
    }

    @Override
    public void onBlockImpact(RayTraceResult rayTraceResult)
    {
        //NO-OP
    }
}
