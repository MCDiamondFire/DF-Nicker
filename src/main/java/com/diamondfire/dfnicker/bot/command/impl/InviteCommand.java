package com.diamondfire.dfnicker.bot.command.impl;

import com.diamondfire.dfnicker.bot.command.argument.ArgumentSet;
import com.diamondfire.dfnicker.bot.command.help.*;
import com.diamondfire.dfnicker.bot.command.permissions.Permission;
import com.diamondfire.dfnicker.bot.command.reply.PresetBuilder;
import com.diamondfire.dfnicker.bot.command.reply.feature.informative.*;
import com.diamondfire.dfnicker.bot.events.CommandEvent;

public class InviteCommand extends Command {

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public HelpContext getHelpContext() {
        return new HelpContext()
                .description("Gives you the invite.")
                .category(CommandCategory.GENERAL);

    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet();
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public void run(CommandEvent event) {
        PresetBuilder builder = new PresetBuilder();
        builder.withPreset(
                new InformativeReply(InformativeReplyType.INFO, "Invite", "https://discord.com/oauth2/authorize?client_id=477878457225969664&permissions=402653184&scope=applications.commands+bot")
        );

        event.reply(builder);
    }

}
