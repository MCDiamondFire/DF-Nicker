package com.diamondfire.dfnicker.bot.events;

import com.diamondfire.dfnicker.util.NickUtil;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.concurrent.*;

public class JoinGuildEvent extends ListenerAdapter {

    private final Executor delayedExecutor = CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS);

    @Override
    public void onGuildJoin(@Nonnull GuildJoinEvent event) {
        CompletableFuture.runAsync(() -> {
            NickUtil.updateGuild(event.getGuild());
        }, delayedExecutor);
    }
}
