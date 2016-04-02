package com.gamingsmod.littlethings.client.render;

import com.gamingsmod.littlethings.common.init.ModItems;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemRender
{
    public static void registerItemRender()
    {
        reg(ModItems.IronNugget);
    }

    private static void reg(Item item)
    {
        String modId = LibMisc.PREFIX_MOD;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, 0, new ModelResourceLocation(modId + item.getUnlocalizedName().substring(6 + LibMisc.MOD_ID.length()), "inventory"));
    }
}
