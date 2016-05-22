package com.gamingsmod.littlethings.common.entity;

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
    private ItemStack stack;

    public EntityCrossBoltPotion(World worldIn)
    {
        super(worldIn);
    }

    public EntityCrossBoltPotion(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityCrossBoltPotion(World worldIn, EntityLivingBase throwerIn, ItemStack stack)
    {
        super(worldIn, throwerIn);
        this.stack = stack.copy();
        this.stack.stackSize = 1;
    }

    public EntityCrossBoltPotion setPotionEffect(Potion effect)
    {
        return setPotionEffect(effect, 0);
    }

    public EntityCrossBoltPotion setPotionEffect(Potion effect, int amp)
    {
        return setPotionEffect(effect, duration, amp);
    }

    public EntityCrossBoltPotion setPotionEffect(Potion effect, int dur, int amp)
    {
        this.potionEffect = new PotionEffect(effect, dur, amp);
        return this;
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return stack;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);
        if (tagCompund.hasKey("Duration")) {
            this.duration = tagCompund.getInteger("Duration");
        }

        if (tagCompund.hasKey("OrigStack")) {
            this.stack = ItemStack.loadItemStackFromNBT(tagCompund.getCompoundTag("OrigStack"));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Duration", this.duration);
        NBTTagCompound stackTag = new NBTTagCompound();
        stack.writeToNBT(stackTag);
        tagCompound.setTag("OrigStack", stackTag);
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
