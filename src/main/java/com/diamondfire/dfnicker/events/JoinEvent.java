package com.diamondfire.dfnicker.events;

import com.diamondfire.dfnicker.AutoNickTask;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class JoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        AutoNickTask.update(event.getMember());
    }


}
