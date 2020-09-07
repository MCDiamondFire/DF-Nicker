package com.diamondfire.dfnicker.bot.command.impl;


import com.diamondfire.dfnicker.bot.command.argument.ArgumentSet;
import com.diamondfire.dfnicker.bot.command.help.HelpContext;
import com.diamondfire.dfnicker.bot.command.permissions.Permission;
import com.diamondfire.dfnicker.bot.events.CommandEvent;

public abstract class Command {

    public abstract String getName();

    public String[] getAliases() {
        return new String[0];
    }

    public abstract HelpContext getHelpContext();

    public abstract ArgumentSet getArguments();

    public abstract Permission getPermission();

    public abstract void run(CommandEvent event);
}
