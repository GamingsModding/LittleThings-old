package com.gamingsmod.littlethings.client.gui.inventory;

import com.gamingsmod.littlethings.common.gui.container.ContainerVanillaCraftingTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Copied from GuiCrafting
 */
@SideOnly(Side.CLIENT)
public class GuiVanillaCraftingTable extends GuiContainer
{
    private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");

    public GuiVanillaCraftingTable(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition)
    {
        super(new ContainerVanillaCraftingTable(playerInv, worldIn, blockPosition));
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     *
     * @param mouseX Mouse x coordinate
     * @param mouseY Mouse y coordinate
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRendererObj.drawString(I18n.format("container.crafting"), 28, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draws the background layer of this container (behind the items).
     *
     * @param partialTicks How far into the current tick the game is, with 0.0 being the start of the tick and 1.0 being
     * the end.
     * @param mouseX Mouse x coordinate
     * @param mouseY Mouse y coordinate
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }
}