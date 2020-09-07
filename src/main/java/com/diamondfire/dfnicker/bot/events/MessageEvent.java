package com.diamondfire.dfnicker.bot.events;

import com.diamondfire.dfnicker.bot.NickBotInstance;
import com.diamondfire.dfnicker.bot.command.reply.PresetBuilder;
import com.diamondfire.dfnicker.bot.command.reply.feature.informative.*;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class MessageEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        if (message.getContentDisplay().startsWith(NickBotInstance.getConfig().getPrefix()) && !event.getAuthor().isBot()) {
            event.getAuthor().openPrivateChannel().queue((channel) -> {
                event.getMessage().addReaction("\u2705").queue();
                NickBotInstance.getHandler().run(new CommandEvent(event.getJDA(), event.getResponseNumber(), message, channel));
            }, (error) -> {
                PresetBuilder builder = new PresetBuilder()
                        .withPreset(
                                new InformativeReply(InformativeReplyType.ERROR, "Couldn't send you a private message, make sure DMs are on!", "Make sure your DMS are turned on!")
                        );

                event.getMessage().getChannel().sendMessage(builder.getEmbed().build()).queue();
            });
        }

    }

}
