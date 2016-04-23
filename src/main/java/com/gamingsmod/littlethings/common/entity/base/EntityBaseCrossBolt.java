package com.gamingsmod.littlethings.common.entity.base;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityBaseCrossBolt extends EntityArrow
{
    public EntityBaseCrossBolt(World worldIn)
    {
        super(worldIn);
        setDamage(5.0F);
    }

    public EntityBaseCrossBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        setDamage(5.0F);
    }

    public EntityBaseCrossBolt(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        setDamage(5.0F);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult raytraceresultBlock = this.worldObj.rayTraceBlocks(vec3d, vec3d1);

        if (raytraceresultBlock != null) {
            if (raytraceresultBlock.typeOfHit == RayTraceResult.Type.BLOCK && this.worldObj.getBlockState(raytraceresultBlock.getBlockPos()).getBlock() == Blocks.portal)
                this.setPortal(raytraceresultBlock.getBlockPos());
            else
                this.onBlockImpact(raytraceresultBlock);
        }

    }

    public abstract void onBlockImpact(RayTraceResult rayTraceResult);

    public abstract void onEntityImpact(RayTraceResult rayTraceResult);
}
