package com.gamingsmod.littlethings.common.handler;

import com.gamingsmod.littlethings.client.gui.inventory.GuiItemElevator;
import com.gamingsmod.littlethings.client.gui.inventory.GuiVanillaCraftingTable;
import com.gamingsmod.littlethings.common.gui.container.ContainerItemElevator;
import com.gamingsmod.littlethings.common.gui.container.ContainerVanillaCraftingTable;
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
        switch (ID) {
            case LibGuiId.ITEMELEVATOR:
                return new ContainerItemElevator(player.inventory, (TileEntityItemElevator) world.getTileEntity(new BlockPos(x, y, z)));
            case LibGuiId.VANILLACRAFTINGTABLES:
                return new ContainerVanillaCraftingTable(player.inventory, world, new BlockPos(x, y, z));
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case LibGuiId.ITEMELEVATOR:
                return new GuiItemElevator(player.inventory, (TileEntityItemElevator) world.getTileEntity(new BlockPos(x,y,z)));
            case LibGuiId.VANILLACRAFTINGTABLES:
                return new GuiVanillaCraftingTable(player.inventory, world, new BlockPos(x, y, z));
        }
        return null;
    }
}
