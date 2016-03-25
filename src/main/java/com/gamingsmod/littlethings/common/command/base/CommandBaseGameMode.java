package com.gamingsmod.littlethings.common.command.base;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandGameMode;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public abstract class CommandBaseGameMode extends CommandBase
{
    public String gamemode;

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        CommandGameMode gm = new CommandGameMode();
        String[] newArgs = new String[args.length + 1];
        if (args.length == 1) {
            newArgs[0] = gamemode;
            newArgs[1] = args[0];
        } else {
            newArgs[0] = gamemode;
        }

        gm.execute(server, sender, newArgs);
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 1;
    }
}
