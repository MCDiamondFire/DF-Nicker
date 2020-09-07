package com.diamondfire.dfnicker.bot.events;

import com.diamondfire.dfnicker.bot.restart.RestartHandler;
import com.diamondfire.dfnicker.util.NickUtil;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class ReadyEvent extends ListenerAdapter {

    @Override
    public void onReady(@Nonnull net.dv8tion.jda.api.events.ReadyEvent event) {
        super.onReady(event);

        NickUtil.initialize();
        RestartHandler.recover(event.getJDA());
    }
}
