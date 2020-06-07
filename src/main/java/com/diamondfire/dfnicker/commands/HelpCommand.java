package com.diamondfire.dfnicker.commands;

import com.diamondfire.dfnicker.events.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class HelpCommand extends Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void run(CommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("DFNicker");
        builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        builder.setDescription("DF Nicker allows you to nick and give people a verified role if they are verified using VerifyBot's verification system.");
        builder.addField("Updating", "DFNicker updates people once every day, when they join a guild OR if they run **dfn!sync**.", false);
        builder.addField("Invite", "https://discord.com/api/oauth2/authorize?client_id=477878457225969664&permissions=402653184&scope=bot", false);
        builder.addField("Verified Role", "If you have a role named **Verified** in your server they will be given that role. Turn this off by disallowing permissions.", false);
        builder.addField("Nickname Changing", "The users nickname is also changed to match their Minecraft username. Turn this off by disallowing permissions.", false);
        builder.addField("NOTICE!", "**Your DFNicker role MUST be at the top of the rank hierarchy or it will fail to do certain actions on people.**", false);
        builder.setFooter("Brought to you by Owen#1212");
        event.getMember().getUser().openPrivateChannel().queue((privateChannel -> privateChannel.sendMessage(builder.build()).queue()));

    }
}
