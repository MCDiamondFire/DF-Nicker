package com.diamondfire.dfnicker;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;

public class DFNicker {
    public static JDA jda;

    // Please note, I have plans to rewrite this in the future.

    public static void main(String[] args) throws LoginException {
        ArrayList<GatewayIntent> intents = new ArrayList<>();
        intents.add(GatewayIntent.GUILD_PRESENCES);
        intents.add(GatewayIntent.GUILD_MEMBERS);

        JDABuilder builder = JDABuilder.create(SensitiveData.TOKEN, intents);

        builder.setStatus(OnlineStatus.ONLINE);
        builder.addEventListeners(new Events());

        builder.disableCache(CacheFlag.EMOTE, CacheFlag.VOICE_STATE, CacheFlag.MEMBER_OVERRIDES, CacheFlag.ACTIVITY, CacheFlag.CLIENT_STATUS);

        jda = builder.build();


    }
}
