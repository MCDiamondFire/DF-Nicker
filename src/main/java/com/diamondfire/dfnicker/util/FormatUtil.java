package com.diamondfire.dfnicker.util;

import com.diamondfire.dfnicker.bot.NickBotInstance;
import com.diamondfire.dfnicker.bot.command.help.HelpContext;
import com.diamondfire.dfnicker.bot.command.impl.Command;

import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class FormatUtil {

    //<editor-fold desc="Time Formatting" defaultstate="collapsed">
    public static String formatMilliTime(long millis) {
        List<String> symbols = new ArrayList<>();
        applyTimeUnit(symbols, millis, TimeUnit.DAYS, 'd');
        applyTimeUnit(symbols, millis, TimeUnit.HOURS, 'h');
        applyTimeUnit(symbols, millis, TimeUnit.MINUTES, 'm');
        applyTimeUnit(symbols, millis, TimeUnit.SECONDS, 's');

        // Only show milliseconds if nothing else is visible.
        if (symbols.size() == 0) {
            symbols.add("0." + getTimeUnit(millis, TimeUnit.MILLISECONDS) + "s");
        }

        return String.join(" ", symbols);
    }

    private static void applyTimeUnit(List<String> symbols, long millis, TimeUnit unit, char symbol) {
        long amt = getTimeUnit(millis, unit);
        if (amt > 0) {
            symbols.add(amt + String.valueOf(symbol));
        }
    }

    public static long getTimeUnit(long millis, TimeUnit unit) {
        switch (unit) {
            case DAYS:
                return TimeUnit.MILLISECONDS.toDays(millis);
            case HOURS:
                return TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis));
            case MINUTES:
                return TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
            case SECONDS:
                return TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
            case MILLISECONDS:
                return TimeUnit.MILLISECONDS.toMillis(millis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millis));
            case MICROSECONDS:
                return millis;
            case NANOSECONDS:
                return TimeUnit.MILLISECONDS.toNanos(millis);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public static String formatTime(long duration, TimeUnit unit) {
        return formatMilliTime(unit.toMillis(duration));
    }

    public static String formatDate(Date date) {
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");
        return format.format(date);
    }
    //</editor-fold>

    //<editor-fold desc="Number Formatting" defaultstate="collapsed">
    public static String formatNumber(long number) {
        return getFormat().format(number);
    }

    public static String formatNumber(double number) {
        return getFormat().format(number);
    }

    private static NumberFormat getFormat() {
        return NumberFormat.getInstance(new Locale("en", "US"));
    }
    //</editor-fold>

    //<editor-fold desc="Command Formatting" defaultstate="collapsed">
    public static String displayArguments(HelpContext context) {
        return String.join(" ", getArgumentDisplay(context));
    }

    public static String[] getArgumentDisplay(HelpContext context) {
        return context.getArguments().stream()
                .map(argument -> argument.isOptional() ? String.format("[<%s>]", argument.getArgumentName()) : String.format("<%s>", argument.getArgumentName()))
                .toArray(String[]::new);
    }

    public static String displayCommand(Command cmd) {
        return NickBotInstance.getConfig().getPrefix() + cmd.getName();
    }
    //</editor-fold>

}
