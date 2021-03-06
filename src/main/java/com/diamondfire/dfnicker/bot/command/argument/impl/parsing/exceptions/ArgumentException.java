package com.diamondfire.dfnicker.bot.command.argument.impl.parsing.exceptions;

import com.diamondfire.dfnicker.bot.command.impl.Command;
import com.diamondfire.dfnicker.util.FormatUtil;


public class ArgumentException extends Exception {

    private String message;

    public ArgumentException(String message) {
        super(message);
        this.message = message;
    }

    public String getEmbedMessage() {
        return message;
    }

    public void setContext(Command command, int pos) {
        String[] args = FormatUtil.getArgumentDisplay(command.getHelpContext());
        args[pos] = "**" + args[pos] + "**";
        String argMessage = FormatUtil.displayCommand(command) + " " + String.join(" ", args);

        message = argMessage + "\n\n" + getMessage();
    }

}
