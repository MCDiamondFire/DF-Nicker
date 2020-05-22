package com.diamondfire.dfnicker;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Events extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        nick(event.getUser());
    }

    @Override
    public void onUserUpdateOnlineStatus(@Nonnull UserUpdateOnlineStatusEvent event) {
        nick(event.getUser());
    }

    public void nick(User user) {
        if (user.isBot()) return;

        try (Connection connection = ConnectionProvider.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT player_name from hypercube.linked_accounts WHERE discord_id = ?;");
            statement.setString(1, user.getId());
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                String name = set.getString("player_name");
                for(Guild guild : DFNicker.jda.getMutualGuilds(user)) {
                    Member member = guild.getMember(user);
                    if (!name.equals(member.getNickname())) {
                        try {
                            guild.modifyNickname(member, name).queue();
                        } catch (Exception ignored) {}
                    }

                }
            }
            set.close();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }
}
