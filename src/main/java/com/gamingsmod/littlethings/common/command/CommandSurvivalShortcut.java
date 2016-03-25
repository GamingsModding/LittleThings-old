package com.gamingsmod.littlethings.common.command;

import com.gamingsmod.littlethings.common.command.base.CommandBaseGameMode;

public class CommandSurvivalShortcut extends CommandBaseGameMode
{
    public CommandSurvivalShortcut()
    {
        this.gamemode = "survival";
    }

    @Override
    public String getCommandName() {
        return "gms";
    }
}
