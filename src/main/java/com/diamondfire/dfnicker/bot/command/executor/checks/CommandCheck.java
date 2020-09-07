package com.diamondfire.dfnicker.bot.command.executor.checks;

import com.diamondfire.dfnicker.bot.command.reply.PresetBuilder;
import com.diamondfire.dfnicker.bot.events.CommandEvent;

// Command checks are run before a command is executed.
public interface CommandCheck {

    boolean check(CommandEvent event);

    void buildMessage(CommandEvent event, PresetBuilder builder);
}
