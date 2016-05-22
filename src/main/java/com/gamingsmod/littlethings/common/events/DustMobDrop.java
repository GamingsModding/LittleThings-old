package com.gamingsmod.littlethings.common.events;

import com.gamingsmod.littlethings.common.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

public class DustMobDrop
{
    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent e)
    {
        World world = e.getEntity().getEntityWorld();
        Random rng = world.rand;
        if (e.getEntity() instanceof EntitySkeleton) {
            EntitySkeleton skele = (EntitySkeleton) e.getEntity();
            if (skele.getSkeletonType() == 1) {
                if (rng.nextFloat() < 0.20 + (e.getLootingLevel() + 1 * 0.05))
                    e.getDrops().add(new EntityItem(world, skele.posX, skele.posY, skele.posZ, new ItemStack(ModItems.MobDust, 1, 0)));
            }
        }

        if (e.getEntity() instanceof EntityCaveSpider) {
            if (rng.nextFloat() < 0.20 + (e.getLootingLevel() + 1 * 0.05))
                e.getDrops().add(new EntityItem(world, e.getEntity().posX, e.getEntity().posY, e.getEntity().posZ, new ItemStack(ModItems.MobDust, 1, 1)));
        }
    }
}
