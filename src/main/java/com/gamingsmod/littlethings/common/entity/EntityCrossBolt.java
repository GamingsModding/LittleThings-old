package com.gamingsmod.littlethings.common.entity;

import com.gamingsmod.littlethings.common.entity.base.EntityBaseCrossBolt;
import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityCrossBolt extends EntityBaseCrossBolt
{
    public EntityCrossBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityCrossBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    public EntityCrossBolt(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
    }

    @Override
    protected ItemStack getArrowStack()
    {
        return new ItemStack(ModItems.CrossBolt, 1, 0);
    }

    @Override
    public void onBlockImpact(RayTraceResult rayTraceResult)
    {
        //NO-OP
    }

    @Override
    public void onEntityImpact(RayTraceResult rayTraceResult)
    {
        //NO-OP
    }
}