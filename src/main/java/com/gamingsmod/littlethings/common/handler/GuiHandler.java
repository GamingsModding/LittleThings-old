package com.gamingsmod.littlethings.common.handler;

import com.gamingsmod.littlethings.client.gui.inventory.GuiItemElevator;
import com.gamingsmod.littlethings.common.container.ContainerItemElevator;
import com.gamingsmod.littlethings.common.lib.LibGuiId;
import com.gamingsmod.littlethings.common.tileentity.TileEntityItemElevator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        System.out.println("Fired! - Server");
        switch (ID) {
            case LibGuiId.ITEMELEVATOR:
                return new ContainerItemElevator(player.inventory, (TileEntityItemElevator) world.getTileEntity(new BlockPos(x, y, z)));
        }
        System.out.println("Nothing Found!");
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        System.out.println("Fired! - Client");
        switch (ID) {
            case LibGuiId.ITEMELEVATOR:
                return new GuiItemElevator(player.inventory, (TileEntityItemElevator) world.getTileEntity(new BlockPos(x,y,z)));
        }
        System.out.println("Nothing Found!");
        return null;
    }
}
