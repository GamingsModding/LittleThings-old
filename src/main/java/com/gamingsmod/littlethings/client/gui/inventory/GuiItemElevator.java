package com.gamingsmod.littlethings.client.gui.inventory;

import com.gamingsmod.littlethings.common.gui.container.ContainerItemElevator;
import com.gamingsmod.littlethings.common.tileentity.TileEntityItemElevator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiItemElevator extends GuiContainer
{
    private IInventory playerInv;
    private TileEntityItemElevator te;

    public GuiItemElevator(IInventory player, TileEntityItemElevator te)
    {
        super(new ContainerItemElevator(player, te));

        this.playerInv = player;
        this.te = te;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("minecraft:textures/gui/container/dispenser.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    // Code adapted from BedrockMiner
    // @source http://bedrockminer.jimdo.com/modding-tutorials/advanced-modding/tile-entity-with-inventory-and-gui/
    @Override
    protected void drawGuiContainerForegroundLayer(int mousex, int mousey)
    {
        String s = this.te.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);            //#404040
        this.fontRendererObj.drawString("Inventory", 8, 72, 4210752);      //#404040
    }
}
