package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.client.render.CrossBoltRender;
import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.entity.EntityCrossBolt;
import com.gamingsmod.littlethings.common.entity.EntityCrossBoltExplosive;
import com.gamingsmod.littlethings.common.entity.EntityCrossBoltPotion;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
    public static void init()
    {
        EntityRegistry.registerModEntity(EntityCrossBolt.class, "CrossBolt", 0, LittleThings.instance, 256, 10, true);
        EntityRegistry.registerModEntity(EntityCrossBoltExplosive.class, "CrossBolt_Explosive", 1, LittleThings.instance, 256, 10, true);
        EntityRegistry.registerModEntity(EntityCrossBoltPotion.class, "CrossBolt_Potion", 1, LittleThings.instance, 256, 10, true);
    }

    public static void initRender()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBolt.class, new CrossBoltRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBoltExplosive.class, new CrossBoltRender());
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBoltPotion.class, new CrossBoltRender());
    }
}
