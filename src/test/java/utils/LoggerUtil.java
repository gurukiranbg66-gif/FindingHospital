package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for consistent logging
 * Uses Log4j2 with a compatibility wrapper for existing call sites.
 */
public class LoggerUtil {

    private static final Logger LOGGER = LogManager.getLogger(LoggerUtil.class);

    private LoggerUtil() {
        // utility class
    }

    /**
     * Log info level messages
     */
    public static void info(String message) {
        LOGGER.info(message);
    }

    /**
     * Log info level messages with class name
     */
    public static void info(Class<?> clazz, String message) {
        LogManager.getLogger(clazz).info(message);
    }

    /**
     * Log debug level messages
     */
    public static void debug(String message) {
        LOGGER.debug(message);
    }

    /**
     * Log debug level messages with class name
     */
    public static void debug(Class<?> clazz, String message) {
        LogManager.getLogger(clazz).debug(message);
    }

    /**
     * Log error level messages
     */
    public static void error(String message) {
        LOGGER.error(message);
    }

    /**
     * Log error level messages with exception
     */
    public static void error(String message, Throwable e) {
        LOGGER.error(message, e);
    }

    /**
     * Log error level messages with class name and exception
     */
    public static void error(Class<?> clazz, String message, Throwable e) {
        LogManager.getLogger(clazz).error(message, e);
    }

    /**
     * Log warning level messages
     */
    public static void warn(String message) {
        LOGGER.warn(message);
    }

    /**
     * Log warning level messages with class name
     */
    public static void warn(Class<?> clazz, String message) {
        LogManager.getLogger(clazz).warn(message);
    }
}

