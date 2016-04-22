package com.gamingsmod.littlethings.common.entity;

import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityCrossBolt extends EntityArrow
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
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.CrossBolt);
    }
}
