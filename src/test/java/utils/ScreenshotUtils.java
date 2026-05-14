package utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtils {

    @Attachment(value = "{name}", type = "image/png")
    public static byte[] captureAndAttachScreenshot(
            WebDriver driver,
            String name) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    public static void saveScreenshot(WebDriver driver, String name, String baseOutputDir) {

        byte[] screenshotBytes =
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        

        // Generate timestamp
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
           String timestamp = LocalDateTime.now().format(formatter);


        // Create screenshot directory
        Path screenshotDir = Paths.get(baseOutputDir, "screenshots");
        try {
            Files.createDirectories(screenshotDir);

            // Save screenshot file
            Path screenshotPath =
                    screenshotDir.resolve(name.replace(" ", "_") + timestamp +".png");

            Files.write(screenshotPath, screenshotBytes);

            System.out.println("Screenshot saved at: " + screenshotPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}