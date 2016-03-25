package com.gamingsmod.littlethings.common.command;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandHead extends CommandBase
{
    @Override
    public String getCommandName() {
        return "head";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return I18n.format("command.littlethings.head");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        Entity entity = sender.getCommandSenderEntity();
        if (entity == null)
            throw new CommandException("Must be performed by a player");

        ItemStack stack = ((EntityPlayer) sender.getCommandSenderEntity()).getHeldItem(EnumHand.MAIN_HAND);

        if (entity instanceof EntityPlayer)
        {
            ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
        }

        entity.replaceItemInInventory(100 + EntityEquipmentSlot.HEAD.getIndex(), stack);
        sender.addChatMessage(new TextComponentString("Success").setChatStyle(new Style().setColor(TextFormatting.GREEN)));

        if (entity instanceof EntityPlayer)
        {
            ((EntityPlayer)entity).inventoryContainer.detectAndSendChanges();
        }
    }

    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}
