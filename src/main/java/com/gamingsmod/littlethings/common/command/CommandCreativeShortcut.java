package com.gamingsmod.littlethings.common.command;

import com.gamingsmod.littlethings.common.command.base.CommandBaseGameMode;

public class CommandCreativeShortcut extends CommandBaseGameMode
{
    public CommandCreativeShortcut()
    {
        this.gamemode = "creative";
    }

    @Override
    public String getCommandName() {
        return "gmc";
    }
}
