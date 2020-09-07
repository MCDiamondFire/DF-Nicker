package com.diamondfire.dfnicker.bot;


import com.diamondfire.dfnicker.bot.command.CommandHandler;
import com.diamondfire.dfnicker.bot.command.impl.*;
import com.diamondfire.dfnicker.bot.config.Config;
import com.diamondfire.dfnicker.bot.events.*;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.*;

import javax.security.auth.login.LoginException;

public class NickBotInstance {

    private static final CommandHandler handler = new CommandHandler();
    private static final Config config = new Config();
    private static ShardManager shardManager;

    public static void initialize() throws LoginException {

        handler.register(
                new HelpCommand(),
                new SyncCommand(),
                new EvalCommand(),
                new RestartCommand(),
                new InviteCommand(),
                new PolicyCommand()
        );

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createLight(config.getToken(), GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES);
        builder.setActivity(Activity.watching("for dfn!help"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new MessageEvent(), new ReadyEvent(), new JoinGuildEvent(), new JoinEvent());
        shardManager = builder.build();
    }

    public static ShardManager getShardManager() {
        return shardManager;
    }

    public static CommandHandler getHandler() {
        return handler;
    }

    public static Config getConfig() {
        return config;
    }
}
