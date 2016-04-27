package com.gamingsmod.littlethings.client.gui.inventory;

import com.gamingsmod.littlethings.common.tileentity.TileEntityExpStore;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class GuiExpStore extends GuiScreen
{
    private TileEntityExpStore te;
    private int xSize;
    private int ySize;

    private GuiButton[] buttons = new GuiButton[6];

    public GuiExpStore(TileEntityExpStore te)
    {
        this.te = te;
        xSize = 176;
        ySize = 86;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.add(buttons[0] = new GuiButton(0, 14, 14, 20, 20, "+"));
        this.buttonList.add(buttons[1] = new GuiButton(1, 14, 44, 20, 20, "++"));
        this.buttonList.add(buttons[2] = new GuiButton(2, 14, 74, 20, 20, "+++"));

        this.buttonList.add(buttons[3] = new GuiButton(3, 44, 14, 20, 20, "-"));
        this.buttonList.add(buttons[4] = new GuiButton(4, 44, 44, 20, 20, "--"));
        this.buttonList.add(buttons[5] = new GuiButton(5, 44, 74, 20, 20, "---"));
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("littlethings:textures/gui/expStore.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {

    }
}
