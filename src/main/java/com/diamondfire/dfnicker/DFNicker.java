package com.diamondfire.dfnicker;

import com.diamondfire.dfnicker.commands.CommandHandler;
import com.diamondfire.dfnicker.commands.HelpCommand;
import com.diamondfire.dfnicker.commands.SyncCommand;
import com.diamondfire.dfnicker.events.JoinEvent;
import com.diamondfire.dfnicker.events.MessageEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

public class DFNicker {

    public static JDA jda;
    public static CommandHandler handler = new CommandHandler();

    // Please note, I have plans to rewrite this in the future.

    public static void main(String[] args) throws LoginException, InterruptedException {
        ArrayList<GatewayIntent> intents = new ArrayList<>();
        intents.add(GatewayIntent.GUILD_MEMBERS);
        intents.add(GatewayIntent.GUILD_MESSAGES);

        JDABuilder builder = JDABuilder.create(SensitiveData.TOKEN, intents);

        builder.setActivity(Activity.watching("for dfn!help"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new JoinEvent(), new MessageEvent());
        builder.setMemberCachePolicy(MemberCachePolicy.NONE);
        builder.setChunkingFilter(ChunkingFilter.NONE);
        builder.disableCache(CacheFlag.EMOTE, CacheFlag.VOICE_STATE, CacheFlag.MEMBER_OVERRIDES, CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS);

        jda = builder.build();
        jda.awaitReady();

        handler.register(new HelpCommand(), new SyncCommand());
        AutoNickTask.initialize();


    }
}
