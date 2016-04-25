package com.gamingsmod.littlethings.common.entity;

import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityCrossBolt extends EntityArrow
{
    public EntityCrossBolt(World worldIn)
    {
        super(worldIn);
        setDamage(5.0F);
    }

    public EntityCrossBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
        setDamage(5.0F);
    }

    public EntityCrossBolt(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
        setDamage(5.0F);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (!worldObj.isRemote) {
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
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(ModItems.CrossBolt, 1, 0);
    }

    public void onBlockImpact(RayTraceResult rayTraceResult)
    {
        //NO-OP
    }
}
