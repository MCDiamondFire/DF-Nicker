package com.diamondfire.dfnicker.util;


import com.diamondfire.dfnicker.bot.NickBotInstance;
import com.diamondfire.dfnicker.database.SingleQueryBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

public class NickUtil implements Runnable {

    public static void initialize() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new NickUtil(),
                0,
                1,
                TimeUnit.DAYS);
    }

    public static void updateMember(Member member, String verifyName, Role verifiedRole) {
        Guild guild = member.getGuild();
        String name = verifyName;
        boolean canUpdateNickName = guild.getSelfMember().hasPermission(Permission.NICKNAME_MANAGE);
        boolean canUpdateRole = guild.getSelfMember().hasPermission(Permission.MANAGE_ROLES);

        // If user isn't verified
        if (guild.getSelfMember().canInteract(member)) {
            if (name == null) {
                // Remove verified role if they have it
                if (member.getRoles().contains(verifiedRole) && verifiedRole != null && canUpdateRole) {
                    guild.removeRoleFromMember(member, verifiedRole)
                            .reason("Removing user's verified role, because they are not verified on DiamondFire.")
                            .queue();
                }
                if (canUpdateNickName) {
                    guild.modifyNickname(member, null)
                            .reason("Removing user's nickname, because they are not verified on DiamondFire.")
                            .queue();
                }

            } else {
                // Remove verified role if they have it
                if (!member.getRoles().contains(verifiedRole) && verifiedRole != null && canUpdateRole) {
                    guild.addRoleToMember(member, verifiedRole)
                            .reason("Giving user verified role, because they are verified on DiamondFire.")
                            .queue();
                }

                if (!member.getEffectiveName().equals(name) && canUpdateNickName) {
                    guild.modifyNickname(member, name)
                            .reason("Updating user's nickname to reflect their name on DiamondFire.")
                            .queue();
                }

            }
        }
    }

    // Updates a member individually, note that this sends a query.
    public static void updateMember(Member member) {
        new SingleQueryBuilder().query("SELECT player_name AS name FROM hypercube.linked_accounts WHERE discord_id = ?;", (statement -> {
            statement.setLong(1, member.getIdLong());
        })).onQuery((table) -> {
            List<Role> roles = member.getGuild().getRolesByName("Verified", true);
            Role role = roles.isEmpty() ? null : roles.get(0);

            updateMember(member, table.getString("name"), role);
        }).execute();
    }


    public static void updateGuild(HashMap<Long, String> accounts, Guild guild) {
        List<Role> verifiedRoles = guild.getRolesByName("verified", true);
        Role verifiedRole;
        if (!verifiedRoles.isEmpty()) {
            verifiedRole = verifiedRoles.get(0);
        } else {
            verifiedRole = null;
        }

        guild.loadMembers((member) -> {
            updateMember(member, accounts.get(member.getIdLong()), verifiedRole);
        });
    }

    public static void updateGuild(Guild guild) {
        HashMap<Long, String> accounts = new HashMap<>();
        new SingleQueryBuilder().query("SELECT discord_id AS discord_id, player_name AS player FROM hypercube.linked_accounts WHERE discord_id IS NOT NULL AND player_name IS NOT NULL;")
                .onQuery((table) -> {
                    do {
                        try {
                            if (!accounts.containsKey(table.getLong("discord_id"))) {
                                accounts.put(table.getLong("discord_id"), table.getString("player"));
                            }
                        } catch (SQLException ignored) {
                        }
                    } while (table.next());
                }).execute();

        updateGuild(accounts, guild);
    }

    @Override
    public void run() {
        HashMap<Long, String> accounts = new HashMap<>();
        new SingleQueryBuilder().query("SELECT discord_id AS discord_id, player_name AS player FROM hypercube.linked_accounts WHERE discord_id IS NOT NULL AND player_name IS NOT NULL;")
                .onQuery((table) -> {
                    do {
                        try {
                            if (!accounts.containsKey(table.getLong("discord_id"))) {
                                accounts.put(table.getLong("discord_id"), table.getString("player"));
                            }
                        } catch (SQLException ignored) {
                        }
                    } while (table.next());
                }).execute();

        for (Guild guild : NickBotInstance.getShardManager().getGuilds()) {
            try {
                updateGuild(accounts, guild);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
