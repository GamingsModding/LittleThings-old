package com.gamingsmod.littlethings.client;

import com.gamingsmod.littlethings.client.render.BlockRender;
import com.gamingsmod.littlethings.client.render.ItemRender;
import com.gamingsmod.littlethings.client.versioning.VersionChecker;
import com.gamingsmod.littlethings.common.CommonProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);

        BlockRender.preInit();
        new VersionChecker().init();
    }

    public void init(FMLInitializationEvent e)
    {
        super.init(e);

        BlockRender.registerBlockRenderer();
        ItemRender.registerItemRender();
    }
}
