package com.diamondfire.dfnicker.commands;


import com.diamondfire.dfnicker.events.CommandEvent;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandHandler {

    private final HashMap<String, Command> commands = new HashMap<>();
    private final ExecutorService POOL = Executors.newCachedThreadPool();

    public void register(Command... commands) {
        for (Command command : commands) {
            this.commands.put(command.getName(), command);
        }

    }

    public void run(CommandEvent e) {
        Command commandToRun = commands.get(e.getCommand());
        System.out.println(e.getCommand());
        if (commandToRun != null) {
            CompletableFuture.runAsync(
                    () -> {
                        try {
                            commandToRun.run(e);
                        } catch (Exception error) {
                            error.printStackTrace();
                        }
                    }, POOL
            );
        }
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
