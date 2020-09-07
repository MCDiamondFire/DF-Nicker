package com.diamondfire.dfnicker.bot.command.help;

public enum CommandCategory {

    GENERAL("General", "General commands that DFNicker has.", "\uD83D\uDCCE");

    private final String name;
    private final String description;
    private final String emoji;

    CommandCategory(String name, String description, String emoji) {
        this.name = name;
        this.description = description;
        this.emoji = emoji;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEmoji() {
        return emoji;
    }
}
