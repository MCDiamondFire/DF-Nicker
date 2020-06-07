package com.diamondfire.dfnicker.events;

import com.diamondfire.dfnicker.DFNicker;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class MessageEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().startsWith("dfn!")) {
            CommandEvent commandEvent = new CommandEvent(event.getJDA(), event.getResponseNumber(), event.getMessage());
            DFNicker.handler.run(commandEvent);
        }

    }
}
