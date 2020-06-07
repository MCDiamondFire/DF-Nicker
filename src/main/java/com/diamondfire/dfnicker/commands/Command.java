package com.diamondfire.dfnicker.commands;


import com.diamondfire.dfnicker.events.CommandEvent;

public abstract class Command {

    public abstract String getName();

    public abstract void run(CommandEvent event);
}
