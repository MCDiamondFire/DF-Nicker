package com.diamondfire.dfnicker.bot.events;

import com.diamondfire.dfnicker.util.NickUtil;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class JoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        NickUtil.updateMember(event.getMember());
    }
}
