package com.diamondfire.dfnicker.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class MessageEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith("dfn!help")) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("DFNicker");
            builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
            builder.setDescription("DF Nicker allows you to nick and give people a verified role if they are verified using VerifyBot's verification system.");
            builder.addField("Invite", "https://discord.com/api/oauth2/authorize?client_id=477878457225969664&permissions=402653184&scope=bot", false);
            builder.addField("Verified Role", "If you have a role named **Verified** in your server they will be given that role. Turn this off by disallowing permissions.", false);
            builder.addField("Nickname Changing", "The users nickname is also changed to match their Minecraft username. Turn this off by disallowing permissions.", false);
            builder.addField("NOTICE!" ,"**Your DFNicker role MUST be at the top of the rank hierarchy or it will fail to do certain actions on people.**", false);
            builder.setFooter("Brought to you by Owen#1212");
            event.getMember().getUser().openPrivateChannel().queue((privateChannel -> privateChannel.sendMessage(builder.build()).queue()));
        }
    }
}
