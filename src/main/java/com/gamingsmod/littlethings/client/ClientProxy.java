package com.gamingsmod.littlethings.client;

import com.gamingsmod.littlethings.client.model.TEAnimalChestRenderer;
import com.gamingsmod.littlethings.client.render.BlockRender;
import com.gamingsmod.littlethings.client.render.ItemRender;
import com.gamingsmod.littlethings.common.CommonProxy;
import com.gamingsmod.littlethings.common.tileentity.TileEntityAnimalChest;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
    public void preInit(FMLPreInitializationEvent e)
    {
        super.preInit(e);

        BlockRender.preInit();
    }

    public void init(FMLInitializationEvent e)
    {
        super.init(e);

        BlockRender.registerBlockRenderer();
        ItemRender.registerItemRender();
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnimalChest.class, new TEAnimalChestRenderer());
    }
}
