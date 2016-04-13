package com.gamingsmod.littlethings.common.command;

import com.gamingsmod.littlethings.common.command.base.CommandBaseGameMode;

public class CommandAdventureShortcut extends CommandBaseGameMode
{
    public CommandAdventureShortcut()
    {
        this.gamemode = "adventure";
    }

    @Override
    public String getCommandName()
    {
        return "gma";
    }
}
