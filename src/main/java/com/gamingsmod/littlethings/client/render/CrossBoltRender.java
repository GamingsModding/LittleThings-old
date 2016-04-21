package com.gamingsmod.littlethings.client.render;

import com.gamingsmod.littlethings.common.entity.EntityCrossBolt;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CrossBoltRender implements IRenderFactory<EntityCrossBolt>
{

    @Override
    public Render<? super EntityCrossBolt> createRenderFor(RenderManager manager)
    {
        return new Render<EntityCrossBolt>(manager) {
            @Override
            protected ResourceLocation getEntityTexture(EntityCrossBolt entity) {
                return new ResourceLocation(LibMisc.PREFIX_MOD  + "textures/items/crossbowBolt.png");
            }
        };
    }
}
