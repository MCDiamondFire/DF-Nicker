package com.diamondfire.dfnicker.commands;

import com.diamondfire.dfnicker.AutoNickTask;
import com.diamondfire.dfnicker.events.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;

public class SyncCommand extends Command{

    @Override
    public String getName() {
        return "sync";
    }

    @Override
    public void run(CommandEvent event) {
        AutoNickTask.update(event.getMember());
    }
}