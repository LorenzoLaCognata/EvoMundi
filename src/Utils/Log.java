package Utils;

import Model.Enums.LogStatus;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;

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
