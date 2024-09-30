package utils;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log {

    private static final NumberFormat formatter = NumberFormat.getInstance(Locale.US);
    private static final Logger logger = Logger.getLogger("Log");
    public static final String UNEXPECTED_PREDATOR_MESSAGE = "Unexpected predator: ";
    public static final String UNEXPECTED_PREY_MESSAGE = "Unexpected prey: ";

    private Log() {
    }

    public static String formatNumber(int integer) {
        return formatter.format(integer);
    }

    public static String formatNumber(double d) {
        return formatter.format(d);
    }

    public static String titleCase(String text) {

        if (text == null)
            return null;

        Pattern pattern = Pattern.compile("\\b([a-zÀ-ÖØ-öø-ÿ])(\\w*)");
        Matcher matcher = pattern.matcher(text.toLowerCase());

        StringBuilder buffer = new StringBuilder();

        while (matcher.find())
            matcher.appendReplacement(buffer, matcher.group(1).toUpperCase() + matcher.group(2));

        return matcher.appendTail(buffer).toString();
    }

    public static void log3(String string) {
        logger.info(string);
    }

    public static void log4(String string) {
        logger.config(string);
    }

    public static void log5(String string) {
        logger.fine(string);
    }

    public static void log6(String string) {
        logger.finer(string);
    }

    public static void log7(String string) {
        logger.finest(string);
    }

}
