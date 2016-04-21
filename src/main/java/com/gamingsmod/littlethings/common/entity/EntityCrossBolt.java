package com.gamingsmod.littlethings.common.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCrossBolt extends EntityThrowable
{
    public EntityCrossBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityCrossBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityCrossBolt(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onImpact(RayTraceResult result)
    {
        if (result.entityHit != null) {
            int i = 6;

            result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)i);
        }

        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
}
