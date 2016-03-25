package com.gamingsmod.littlethings.common.command;

import com.gamingsmod.littlethings.common.command.base.CommandBaseGameMode;

public class CommandSpectatorShortcut extends CommandBaseGameMode
{
    public CommandSpectatorShortcut()
    {
        this.gamemode = "spectator";
    }

    @Override
    public String getCommandName() {
        return "gmsp";
    }
}
