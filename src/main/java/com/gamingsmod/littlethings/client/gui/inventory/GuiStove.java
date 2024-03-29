package com.gamingsmod.littlethings.client.gui.inventory;

import com.gamingsmod.littlethings.common.gui.container.ContainerStove;
import com.gamingsmod.littlethings.common.tileentity.TileEntityStove;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiStove extends GuiContainer
{
    private IInventory playerInv;
    private TileEntityStove te;

    public GuiStove(IInventory player, TileEntityStove te)
    {
        super(new ContainerStove(player, te));

        this.playerInv = player;
        this.te = te;
        this.xSize = 176;
        this.ySize = 189;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("littlethings:textures/gui/stove.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        if (TileEntityStove.isBurning(this.te)) {
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(i + 55, j + 60 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(i + 77, j + 57, 176, 14, l + 1, 16);
    }

    // Code adapted from BedrockMiner
    // @source http://bedrockminer.jimdo.com/modding-tutorials/advanced-modding/tile-entity-with-inventory-and-gui/
    @Override
    protected void drawGuiContainerForegroundLayer(int mousex, int mousey)
    {
        String s = this.te.getDisplayName().getUnformattedText();
        this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);            //#404040
        this.fontRendererObj.drawString("Inventory", 8, 95, 4210752);      //#404040

    }

    private int getCookProgressScaled(int pixels)
    {
        int i = this.te.getField(2);
        int j = this.te.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        int i = this.te.getField(1);

        if (i == 0) {
            i = 200;
        }

        return this.te.getField(0) * pixels / i;
    }
}
