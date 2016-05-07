package com.gamingsmod.littlethings.common.events;

import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WitherDustDrop
{
    @SubscribeEvent
    public void onEntityKilled(LivingDropsEvent e)
    {
        if (e.getEntity() instanceof EntitySkeleton) {
            EntitySkeleton skele = (EntitySkeleton) e.getEntity();
            if (skele.getSkeletonType() == 1) {
                World world = skele.getEntityWorld();
//                if (world.rand.nextFloat() > 0.5)
                e.getDrops().add(new EntityItem(world, skele.posX, skele.posY, skele.posZ, new ItemStack(ModItems.WitherDust)));
            }
        }
    }
}
