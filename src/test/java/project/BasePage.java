package project;

import java.time.Duration;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;
import utils.LoggerUtil;

/**
 * Base page class containing common setup and teardown logic for all tests
 */
public class BasePage {

	protected WebDriver driver;
	protected WebDriverWait wait;

	@BeforeClass
	public void setUp() {
		LoggerUtil.info("Initializing WebDriver and browser setup");
		try {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-popup-blocking");

			driver = new ChromeDriver(options);
			driver.manage().window().maximize();

			// Use wait time from config
			int waitTime = ConfigReader.getExplicitWait();
			wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));

			// Set implicit wait
			driver.manage().timeouts().implicitlyWait(
				Duration.ofSeconds(ConfigReader.getImplicitWait())
			);

			// Navigate to application URL
			String appUrl = ConfigReader.getAppUrl();
			driver.get(appUrl);
			LoggerUtil.info("Browser launched and navigated to: " + appUrl);
		} catch (Exception e) {
			LoggerUtil.error("Error during WebDriver initialization", e);
			throw e;
		}
	}

	@AfterClass
	public void tearDown() {
		LoggerUtil.info("Closing WebDriver");
		try {
			if (driver != null) {
				driver.quit();
				LoggerUtil.info("Browser closed successfully");
			}
		} catch (Exception e) {
			LoggerUtil.error("Error during WebDriver teardown", e);
		}
	}
}