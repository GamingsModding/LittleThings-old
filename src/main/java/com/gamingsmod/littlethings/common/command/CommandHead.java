package com.gamingsmod.littlethings.common.command;

import com.gamingsmod.littlethings.common.lib.LibCommandLevel;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
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
    public String getCommandName()
    {
        return "head";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return LibCommandLevel.NORMAL;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return I18n.format("command.littlethings.head");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        EntityPlayerMP entityPlayerMP = getCommandSenderAsPlayer(sender);
        ItemStack stack = entityPlayerMP.getHeldItem(EnumHand.MAIN_HAND);

        entityPlayerMP.inventoryContainer.detectAndSendChanges();
        entityPlayerMP.replaceItemInInventory(100 + EntityEquipmentSlot.HEAD.getIndex(), stack);
        if (!entityPlayerMP.isCreative()) {
            entityPlayerMP.setHeldItem(EnumHand.MAIN_HAND, null);
        }
        sender.addChatMessage(new TextComponentString("Success").setChatStyle(new Style().setColor(TextFormatting.GREEN)));
        entityPlayerMP.inventoryContainer.detectAndSendChanges();
    }
}
