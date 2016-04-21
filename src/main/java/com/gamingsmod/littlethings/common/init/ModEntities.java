package com.gamingsmod.littlethings.common.init;

import com.gamingsmod.littlethings.client.render.CrossBoltRender;
import com.gamingsmod.littlethings.common.LittleThings;
import com.gamingsmod.littlethings.common.entity.EntityCrossBolt;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities
{
    public static void init()
    {
        EntityRegistry.registerModEntity(EntityCrossBolt.class, "CrossBolt", 0, LittleThings.instance, 256, 10, true);
    }

    public static void initRender()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityCrossBolt.class, new CrossBoltRender());
    }
}
