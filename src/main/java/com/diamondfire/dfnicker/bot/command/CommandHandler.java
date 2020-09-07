package com.diamondfire.dfnicker.bot.command;


import com.diamondfire.dfnicker.bot.NickBotInstance;
import com.diamondfire.dfnicker.bot.command.executor.CommandExecutor;
import com.diamondfire.dfnicker.bot.command.impl.Command;
import com.diamondfire.dfnicker.bot.events.CommandEvent;

import java.util.HashMap;

public class CommandHandler {

    private final HashMap<String, Command> CMDS = new HashMap<>();
    private final HashMap<String, Command> ALIASES = new HashMap<>();
    private final CommandExecutor COMMAND_EXECUTOR = new CommandExecutor();

    public void initialize() {

    }

    public static Command getCommand(String name) {
        Command cmd = NickBotInstance.getHandler().getCommands().get(name.toLowerCase());
        if (cmd == null) {
            cmd = NickBotInstance.getHandler().getAliases().get(name.toLowerCase());
        }

        return cmd;
    }

    public void register(Command... commands) {
        for (Command command : commands) {
            this.CMDS.put(command.getName().toLowerCase(), command);
            for (String alias : command.getAliases()) {
                this.ALIASES.put(alias.toLowerCase(), command);
            }
        }

    }

    public void run(CommandEvent e) {
        COMMAND_EXECUTOR.run(e);
    }

    public HashMap<String, Command> getCommands() {
        return CMDS;
    }

    public HashMap<String, Command> getAliases() {
        return ALIASES;
    }

}
