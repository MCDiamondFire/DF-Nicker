package com.diamondfire.dfnicker.bot.command.impl;

import com.diamondfire.dfnicker.bot.command.argument.ArgumentSet;
import com.diamondfire.dfnicker.bot.command.help.*;
import com.diamondfire.dfnicker.bot.command.permissions.Permission;
import com.diamondfire.dfnicker.bot.events.CommandEvent;
import com.diamondfire.dfnicker.bot.restart.RestartHandler;
import net.dv8tion.jda.api.EmbedBuilder;

public class RestartCommand extends Command {

    @Override
    public String getName() {
        return "restart";
    }

    @Override
    public HelpContext getHelpContext() {
        return new HelpContext()
                .description("Restarts the bot")
                .category(CommandCategory.GENERAL);
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public Permission getPermission() {
        return Permission.STAFF;
    }

    @Override
    public void run(CommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Restarting!");
        builder.setDescription("This may take a moment");

        event.getChannel().sendMessage(builder.build()).queue((msg) -> {
            RestartHandler.logRestart(msg);
            System.exit(0);
        });

    }
}
