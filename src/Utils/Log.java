package Utils;

import Model.Enums.LogStatus;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Log {

    private static final NumberFormat formatter = NumberFormat.getInstance(Locale.US);
    private static final Logger logger = Logger.getLogger("Log");
    public static final String UNEXPECTED_VALUE_MESSAGE = "Unexpected value: ";

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

        Pattern pattern = Pattern.compile("\\b([a-zÀ-ÖØ-öø-ÿ])([\\w]*)");
        Matcher matcher = pattern.matcher(text.toLowerCase());

        StringBuilder buffer = new StringBuilder();

        while (matcher.find())
            matcher.appendReplacement(buffer, matcher.group(1).toUpperCase() + matcher.group(2));

        return matcher.appendTail(buffer).toString();
    }

    public static void info(String string) {
        logger.info(string);
    }

    // TODO: transform logging from formatted output to actual info, warnings etc.
    // TODO: temporarily disabled for performance analysis
    public static void logln(String string) {
        //logger.info(string);
    }

    // TODO: transform logging from formatted output to actual info, warnings etc.
    // TODO: temporarily disabled for performance analysis
    public static void logln(LogStatus logStatus, String string) {
        if (logStatus == LogStatus.ACTIVE) {
            //logger.info(string);
        }
    }

    // TODO: transform logging from formatted output to actual info, warnings etc.
    // TODO: temporarily disabled for performance analysis
    public static void log(LogStatus logStatus, String string) {
        if (logStatus == LogStatus.ACTIVE) {
            //logger.info(string);
        }
    }
    
}
