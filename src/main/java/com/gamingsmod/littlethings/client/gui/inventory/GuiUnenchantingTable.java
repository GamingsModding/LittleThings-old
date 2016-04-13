package com.gamingsmod.littlethings.client.gui.inventory;

import com.gamingsmod.littlethings.common.gui.container.ContainerUnenchantingTable;
import com.gamingsmod.littlethings.common.tileentity.TileEntityUnenchantingTable;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiUnenchantingTable extends GuiContainer
{
    private IInventory playerInv;
    private TileEntityUnenchantingTable te;
    private GuiButton[] buttons = new GuiButton[1];

    public GuiUnenchantingTable(IInventory player, TileEntityUnenchantingTable te)
    {
        super(new ContainerUnenchantingTable(player, te));

        this.playerInv = player;
        this.te = te;
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("littlethings:textures/gui/unenchanting_table.png"));
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
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(this.buttons[0] = new GuiButton(0, this.width / 2 - 25, this.height / 2 - 24, 50, 15, "Give XP"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button == this.buttons[0]) {

        }
    }
}
