package com.diamondfire.dfnicker.bot.command.argument.impl.parsing.types;

import com.diamondfire.dfnicker.bot.command.argument.impl.parsing.parser.AlternateArgumentParser;

public class AlternateArgumentContainer<T> extends ArgumentContainer<T> {

    private final ArgumentContainer<?>[] argumentContainers;

    public AlternateArgumentContainer(ArgumentContainer<?>... argumentContainers) {
        this.argumentContainers = argumentContainers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AlternateArgumentParser<T> getParser() {
        return new AlternateArgumentParser<>(this);
    }

    public ArgumentContainer<?>[] getAlternatives() {
        return argumentContainers;
    }

}
