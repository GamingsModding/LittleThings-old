package com.gamingsmod.littlethings.client.gui.inventory;

import com.gamingsmod.littlethings.common.network.MessageHandler;
import com.gamingsmod.littlethings.common.network.message.MessageXP;
import com.gamingsmod.littlethings.common.tileentity.TileEntityExpStore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiExpStore extends GuiScreen
{
    private TileEntityExpStore te;
    private static int heldXP = 0;
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

        int calcWidth = this.width / 2;
        int calcHeight = this.height / 2;

        this.buttonList.add(buttons[0] = new GuiButton(0, calcWidth - 50, calcHeight - 30, 20, 20, "+"));
        this.buttonList.add(buttons[1] = new GuiButton(1, calcWidth - 10, calcHeight - 30, 20, 20, "++"));
        this.buttonList.add(buttons[2] = new GuiButton(2, calcWidth + 30, calcHeight - 30, 20, 20, "+++"));

        this.buttonList.add(buttons[3] = new GuiButton(3, calcWidth - 50, calcHeight + 10, 20, 20, "-"));
        this.buttonList.add(buttons[4] = new GuiButton(4, calcWidth - 10, calcHeight + 10, 20, 20, "--"));
        this.buttonList.add(buttons[5] = new GuiButton(5, calcWidth + 30, calcHeight + 10, 20, 20, "---"));
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("littlethings:textures/gui/expStore.png"));
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        String text = this.te.getXp() + "";
        fr.drawString(text, (this.width - fr.getStringWidth(text)) / 2, (this.height / 2) - 5, 8453920, true);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button == buttons[0]) sendMessage(1, MessageXP.TAKE_EXP);
        else if (button == buttons[1]) sendMessage(10, MessageXP.TAKE_EXP);
        else if (button == buttons[2]) sendMessage(5000, MessageXP.TAKE_EXP);
        else if (button == buttons[3]) sendMessage(1, MessageXP.GIVE_EXP);
        else if (button == buttons[4]) sendMessage(10, MessageXP.GIVE_EXP);
        else if (button == buttons[5]) sendMessage(5000, MessageXP.GIVE_EXP);
    }

    private void sendMessage(int lvls, int action)
    {
        MessageHandler.INSTANCE.sendToServer(new MessageXP(this.te.getPos(), lvls, action));
    }
}
