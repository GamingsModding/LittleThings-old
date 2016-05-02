package com.gamingsmod.littlethings.client.render;

import com.gamingsmod.littlethings.common.entity.EntityCrossBolt;
import com.gamingsmod.littlethings.common.lib.LibMisc;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CrossBoltRender implements IRenderFactory<EntityCrossBolt>
{
    @Override
    public Render<? super EntityCrossBolt> createRenderFor(RenderManager manager)
    {
        return new Render<EntityCrossBolt>(manager)
        {

            @Override
            public void doRender(EntityCrossBolt entity, double x, double y, double z, float entityYaw, float partialTicks)
            {
                this.bindEntityTexture(entity);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.pushMatrix();
                GlStateManager.disableLighting();
                GlStateManager.translate((float) x, (float) y, (float) z);
                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);
                Tessellator tessellator = Tessellator.getInstance();
                VertexBuffer vertexbuffer = tessellator.getBuffer();
                int i = 0;
                float f = 0.0F;
                float f1 = 0.5F;
                float f2 = (float) (i * 10) / 32.0F;
                float f3 = (float) (5 + i * 10) / 32.0F;
                float f4 = 0.0F;
                float f5 = 0.15625F;
                float f6 = (float) (5 + i * 10) / 32.0F;
                float f7 = (float) (10 + i * 10) / 32.0F;
                float f8 = 0.05625F;
                GlStateManager.enableRescaleNormal();
                float f9 = (float) partialTicks;

                if (f9 > 0.0F) {
                    float f10 = -MathHelper.sin(f9 * 3.0F) * f9;
                    GlStateManager.rotate(f10, 0.0F, 0.0F, 1.0F);
                }

                GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.scale(f8, f8, f8);
                GlStateManager.translate(-4.0F, 0.0F, 0.0F);

                if (this.renderOutlines) {
                    GlStateManager.enableColorMaterial();
                    GlStateManager.enableOutlineMode(this.getTeamColor(entity));
                }

                GlStateManager.glNormal3f(f8, 0.0F, 0.0F);
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                vertexbuffer.pos(-7.0D, -2.0D, -2.0D).tex((double) f4, (double) f6).endVertex();
                vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex((double) f5, (double) f6).endVertex();
                vertexbuffer.pos(-7.0D, 2.0D, 2.0D).tex((double) f5, (double) f7).endVertex();
                vertexbuffer.pos(-7.0D, 2.0D, -2.0D).tex((double) f4, (double) f7).endVertex();
                tessellator.draw();
                GlStateManager.glNormal3f(-f8, 0.0F, 0.0F);
                vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                vertexbuffer.pos(-7.0D, 2.0D, -2.0D).tex((double) f4, (double) f6).endVertex();
                vertexbuffer.pos(-7.0D, 2.0D, 2.0D).tex((double) f5, (double) f6).endVertex();
                vertexbuffer.pos(-7.0D, -2.0D, 2.0D).tex((double) f5, (double) f7).endVertex();
                vertexbuffer.pos(-7.0D, -2.0D, -2.0D).tex((double) f4, (double) f7).endVertex();
                tessellator.draw();

                for (int j = 0; j < 4; ++j) {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                    GlStateManager.glNormal3f(0.0F, 0.0F, f8);
                    vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                    vertexbuffer.pos(-8.0D, -2.0D, 0.0D).tex((double) f, (double) f2).endVertex();
                    vertexbuffer.pos(8.0D, -2.0D, 0.0D).tex((double) f1, (double) f2).endVertex();
                    vertexbuffer.pos(8.0D, 2.0D, 0.0D).tex((double) f1, (double) f3).endVertex();
                    vertexbuffer.pos(-8.0D, 2.0D, 0.0D).tex((double) f, (double) f3).endVertex();
                    tessellator.draw();
                }

                if (this.renderOutlines) {
                    GlStateManager.disableOutlineMode();
                    GlStateManager.disableColorMaterial();
                }

                GlStateManager.disableRescaleNormal();
                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
                super.doRender(entity, x, y, z, entityYaw, partialTicks);
            }

            @Override
            protected ResourceLocation getEntityTexture(EntityCrossBolt entity)
            {
                return new ResourceLocation(LibMisc.PREFIX_MOD + "models/entity/crossbowBolt.png");
            }
        };
    }
}
