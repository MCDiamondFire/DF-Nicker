package com.diamondfire.dfnicker.bot.command.impl;

import com.diamondfire.dfnicker.bot.command.argument.ArgumentSet;
import com.diamondfire.dfnicker.bot.command.argument.impl.parsing.types.SingleArgumentContainer;
import com.diamondfire.dfnicker.bot.command.argument.impl.types.DiscordUserArgument;
import com.diamondfire.dfnicker.bot.command.help.*;
import com.diamondfire.dfnicker.bot.command.permissions.Permission;
import com.diamondfire.dfnicker.bot.command.reply.PresetBuilder;
import com.diamondfire.dfnicker.bot.command.reply.feature.informative.*;
import com.diamondfire.dfnicker.bot.events.CommandEvent;
import com.diamondfire.dfnicker.util.NickUtil;

public class SyncCommand extends Command {

    @Override
    public String getName() {
        return "sync";
    }

    @Override
    public HelpContext getHelpContext() {
        return new HelpContext()
                .description("Manually syncs a user's nickname/roles, typing no user will sync yourself.")
                .category(CommandCategory.GENERAL)
                .addArgument(
                        new HelpContextArgument()
                                .name("user")
                                .optional()
                );
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArgument("user",
                new SingleArgumentContainer<>(new DiscordUserArgument()).optional(null));
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public void run(CommandEvent event) {
        PresetBuilder builder = new PresetBuilder();
        long id;
        if (event.getArgument("user") == null) {
            id = event.getMember().getIdLong();
        } else {
            id = event.getArgument("user");
        }
        builder.withPreset(
                new InformativeReply(InformativeReplyType.INFO, String.format("Syncing <@!%s>...", id))
        );

        event.reply(builder);
        event.getGuild().retrieveMemberById(id).queue(NickUtil::updateMember);
    }

}
