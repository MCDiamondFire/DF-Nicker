package com.diamondfire.dfnicker.events;

import com.diamondfire.dfnicker.AutoNickTask;
import com.diamondfire.dfnicker.database.SingleQueryBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        Member member = event.getMember();

        new SingleQueryBuilder().query("SELECT player_name AS name FROM hypercube.linked_accounts WHERE discord_id = ?;", (statement -> {
            statement.setLong(1, member.getIdLong());
        })).onQuery((table) -> {
            List<Role> roles = event.getGuild().getRolesByName("Verified", true);
            Role role = roles.isEmpty() ? null : roles.get(0);
            AutoNickTask.update(member,table.getString("name"), role);
        }).execute();
    }


}
