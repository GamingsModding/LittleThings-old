package com.gamingsmod.littlethings.common.command;

import com.gamingsmod.littlethings.common.lib.LibCommandLevel;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.*;
import net.minecraft.server.MinecraftServer;

public class CommandSkull extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "skull";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return LibCommandLevel.MODERATOR;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return I18n.format("command.littlethings.skull");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length < 1)
            throw new WrongUsageException(getCommandUsage(sender));

        String playerName = args[0];
        String amount = (args.length >= 2) ? args[1] : "1";

        CommandGive commandGive = new CommandGive();
        commandGive.execute(server, sender, new String[]{sender.getName(), "minecraft:skull", amount, "3", "{SkullOwner:" + playerName + "}"});
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}
