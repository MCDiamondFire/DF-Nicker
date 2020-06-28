package com.diamondfire.dfnicker;


import com.diamondfire.dfnicker.database.SingleQueryBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoNickTask implements Runnable {

    public static void initialize() {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new AutoNickTask(),
                0,
                1,
                TimeUnit.DAYS);
    }

    public static void update(Member member, String verificationName, Role verificationRole) {
        Guild guild = member.getGuild();
        try {
            if (guild.getSelfMember().canInteract(member)) {
                if (!member.getEffectiveName().equals(verificationName)) {
                    member.modifyNickname(verificationName).reason("Verification Name Change").queue();
                }
                if (verificationRole != null) {
                    if (member.getRoles().stream().noneMatch((memberRole) -> memberRole.getIdLong() == verificationRole.getIdLong())) {
                        guild.addRoleToMember(member.getIdLong(), verificationRole).reason("Verification Role").queue();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void update(Member member) {
        new SingleQueryBuilder().query("SELECT player_name AS name FROM hypercube.linked_accounts WHERE discord_id = ?;", (statement -> {
            statement.setLong(1, member.getIdLong());
        })).onQuery((table) -> {
            List<Role> roles = member.getGuild().getRolesByName("Verified", true);
            Role role = roles.isEmpty() ? null : roles.get(0);
            AutoNickTask.update(member, table.getString("name"), role);
        }).execute();
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

        for (Guild guild : DFNicker.jda.getGuilds()) {
            List<Role> roles = guild.getRolesByName("Verified", true);
            Role role = roles.isEmpty() ? null : roles.get(0);
            guild.retrieveMembers().thenRun(() -> {
                for (Member member : guild.getMemberCache()) {
                    accounts.computeIfPresent(member.getIdLong(), (id, name) -> {
                        update(member, name, role);
                        return name;
                    });

                }
            }).thenRun(guild::pruneMemberCache);

        }

    }

}
