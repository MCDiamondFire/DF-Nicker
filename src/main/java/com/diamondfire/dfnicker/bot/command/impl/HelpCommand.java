package com.diamondfire.dfnicker.bot.command.impl;

import com.diamondfire.dfnicker.bot.NickBotInstance;
import com.diamondfire.dfnicker.bot.command.argument.ArgumentSet;
import com.diamondfire.dfnicker.bot.command.argument.impl.parsing.types.SingleArgumentContainer;
import com.diamondfire.dfnicker.bot.command.argument.impl.types.DefinedObjectArgument;
import com.diamondfire.dfnicker.bot.command.help.*;
import com.diamondfire.dfnicker.bot.command.permissions.*;
import com.diamondfire.dfnicker.bot.events.CommandEvent;
import com.diamondfire.dfnicker.util.FormatUtil;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.*;

public class HelpCommand extends Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public HelpContext getHelpContext() {
        return new HelpContext()
                .description("Gives a list of all current commands.")
                .addArgument(
                        new HelpContextArgument()
                                .name("command")
                                .optional()
                );
    }

    @Override
    public ArgumentSet getArguments() {
        return new ArgumentSet().addArgument("help",
                new SingleArgumentContainer<>(new DefinedObjectArgument<>(NickBotInstance.getHandler().getCommands().values().stream()
                        .map(Command::getName)
                        .toArray(String[]::new))).optional(null));
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public void run(CommandEvent event) {
        String helpInfo = event.getArgument("help");
        if (helpInfo == null) {

            EmbedBuilder homeBuilder = new EmbedBuilder();
            homeBuilder.setAuthor("Invite", "https://discord.com/api/oauth2/authorize?client_id=477878457225969664&permissions=402653184&scope=bot", null);
            homeBuilder.setDescription("DF Nicker allows you to nick or give people a verified role if they are verified using VerifyBot's verification system.");
            homeBuilder.addField("How to setup?", "1. Invite the bot\n2. Move the DFNicker role all the way to the top.\n3. Create a new role called Verified (they will be given this if they are verified)\n4. Wait 5 minutes and DFNicker will automatically sync your guild.", false);
            homeBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
            homeBuilder.setFooter("Your permissions: " + PermissionHandler.getPermission(event.getMember()));

            List<Command> commandList = new ArrayList<>(NickBotInstance.getHandler().getCommands().values());
            commandList.sort(Comparator.comparing(Command::getName));
            for (Command command : commandList) {
                HelpContext context = command.getHelpContext();
                if (command.getPermission().hasPermission(event.getMember())) {
                    homeBuilder.addField(FormatUtil.displayCommand(command) + " " + FormatUtil.displayArguments(context), context.getDescription(), false);

                }

            }

            event.getReplyHandler().reply(homeBuilder);
        } else {
            Command command = NickBotInstance.getHandler().getCommands().get(helpInfo);
            HelpContext context = command.getHelpContext();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Command Information");
            builder.addField("Name", command.getName(), false);
            builder.addField("Description", context.getDescription(), false);
            builder.addField("Aliases", (command.getAliases().length == 0 ? "None" : String.join(", ", command.getAliases())), false);
            builder.addField("Argument", FormatUtil.displayArguments(context), true);
            builder.addField("Category", context.getCommandCategory().toString(), true);
            builder.addField("Permission Required", command.getPermission().toString(), true);

            event.getReplyHandler().reply(builder);
        }

    }

}
