package com.diamondfire.dfnicker.bot.events;

import com.diamondfire.dfnicker.bot.NickBotInstance;
import com.diamondfire.dfnicker.bot.command.argument.impl.parsing.ParsedArgumentSet;
import com.diamondfire.dfnicker.bot.command.argument.impl.parsing.exceptions.ArgumentException;
import com.diamondfire.dfnicker.bot.command.argument.impl.parsing.parser.ArgumentParser;
import com.diamondfire.dfnicker.bot.command.impl.Command;
import com.diamondfire.dfnicker.bot.command.reply.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;

public class CommandEvent extends GuildMessageReceivedEvent {

    private final Command command;
    private final ReplyHandler replyHandler;
    //TODO Cleanup and refactor this. I'd like to see stuff like replying be put into it's whole own section and refactored as well.
    private ParsedArgumentSet parsedArgumentSet = null;
    private String aliasedUsed = null;

    public CommandEvent(JDA api, long responseNumber, Message message, MessageChannel channel) {
        super(api, responseNumber, message);
        String[] rawArgs = getMessage().getContentDisplay().split(" ");
        String commandPrefix = rawArgs[0].substring(NickBotInstance.getConfig().getPrefix().length()).toLowerCase();


        Command cmd = NickBotInstance.getHandler().getCommands().get(commandPrefix.toLowerCase());
        if (cmd == null) {
            this.aliasedUsed = commandPrefix.toLowerCase();
            cmd = NickBotInstance.getHandler().getAliases().get(commandPrefix.toLowerCase());
        }

        this.command = cmd;
        this.replyHandler = new ReplyHandler(channel);
    }

    public void pushArguments(String[] rawArgs) throws ArgumentException {
        this.parsedArgumentSet = ArgumentParser.parseArgs(command, Arrays.copyOfRange(rawArgs, 1, rawArgs.length));
    }

    public Command getCommand() {
        return command;
    }

    public void reply(PresetBuilder preset) {
        replyHandler.reply(preset);
    }

    @SuppressWarnings("unchecked")
    public <T> T getArgument(String code) {
        return (T) parsedArgumentSet.getArgument(code);
    }

    public Map<String, ?> getArguments() {
        return parsedArgumentSet.getArguments();
    }

    public ReplyHandler getReplyHandler() {
        return replyHandler;
    }

    public String getAliasUsed() {
        return aliasedUsed;
    }
}
