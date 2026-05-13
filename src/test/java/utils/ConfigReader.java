package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Centralized configuration reader for managing all test configurations
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    static {
        try {
            properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.err.println("Error loading configuration file: " + e.getMessage());
        }
    }

    /**
     * Get configuration value as String
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get configuration value as Integer
     */
    public static Integer getPropertyAsInteger(String key) {
        String value = properties.getProperty(key);
        return value != null ? Integer.parseInt(value) : 0;
    }

    /**
     * Get configuration value as Double
     */
    public static Double getPropertyAsDouble(String key) {
        String value = properties.getProperty(key);
        return value != null ? Double.parseDouble(value) : 0.0;
    }

    /**
     * Get configuration value as Boolean
     */
    public static Boolean getPropertyAsBoolean(String key) {
        String value = properties.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : false;
    }

    // Convenience methods for commonly used configurations
    public static String getAppUrl() {
        return getProperty("app.url");
    }

    public static String getBrowser() {
        return getProperty("app.browser");
    }

    public static Integer getExplicitWait() {
        return getPropertyAsInteger("explicit.wait");
    }

    public static Integer getImplicitWait() {
        return getPropertyAsInteger("implicit.wait");
    }

    public static Integer getPageLoadTimeout() {
        return getPropertyAsInteger("page.load.timeout");
    }

    public static String getExcelOutputPath() {
        return getProperty("excel.output.path");
    }

    public static String getScreenshotOutputPath() {
        return getProperty("screenshot.output.path");
    }

    public static Integer getRetryCount() {
        return getPropertyAsInteger("retry.count");
    }

    public static Boolean isParallelExecution() {
        return getPropertyAsBoolean("parallel.execution");
    }
}

