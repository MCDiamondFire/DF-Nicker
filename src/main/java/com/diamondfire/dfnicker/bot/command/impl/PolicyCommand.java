package com.diamondfire.dfnicker.bot.command.impl;

import com.diamondfire.dfnicker.bot.command.argument.ArgumentSet;
import com.diamondfire.dfnicker.bot.command.help.*;
import com.diamondfire.dfnicker.bot.command.permissions.Permission;
import com.diamondfire.dfnicker.bot.command.reply.PresetBuilder;
import com.diamondfire.dfnicker.bot.command.reply.feature.informative.*;
import com.diamondfire.dfnicker.bot.events.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class PolicyCommand extends Command {

    @Override
    public String getName() {
        return "policy";
    }

    @Override
    public HelpContext getHelpContext() {
        return new HelpContext()
                .description("Gives you DFNicker's privacy policy.")
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
                new InformativeReply(InformativeReplyType.INFO, "Privacy Policy", "Questions about where your data comes from and where it is stored is listed here.")
        );
        EmbedBuilder embed = builder.getEmbed();

        embed.addField("Where is my data from?", "Your data is provided from **DiamondFire**, a Minecraft server. When you verify to get access to the Discord server you agree to have this data stored. Note that this data is not public.", false);
        embed.addField("How can I get my data removed?", "If you would like your data removed, you will need to contact an administrator on the DiamondFire discord server (https://discord.gg/EuGXnhD).", false);
        event.reply(builder);
    }

}
