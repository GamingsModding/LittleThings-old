package com.gamingsmod.littlethings.common.entity;

import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCrossBoltExplosive extends EntityCrossBolt
{
    public EntityCrossBoltExplosive(World worldIn)
    {
        super(worldIn);
    }

    public EntityCrossBoltExplosive(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityCrossBoltExplosive(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(ModItems.CrossBolt, 1, 1);
    }

    @Override
    public void onBlockImpact(RayTraceResult rayTraceResult)
    {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float) 3, true);
        this.setDead();
    }

    @Override
    protected void arrowHit(EntityLivingBase living)
    {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float) 3, true);
        this.setDead();
    }
}
