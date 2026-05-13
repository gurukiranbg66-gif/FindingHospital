package pages;

import constants.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtil;

/**
 * Page Object Model for Search functionality
 */
public class SearchPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SearchPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Search for a city
     *
     * @param cityName City name to search
     */
    public void searchCity(String cityName) {
        LoggerUtil.info("Searching for city: " + cityName);
        try {
            WebElement cityInput = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(Locators.CITY_INPUT))
            );
            cityInput.clear();
            cityInput.sendKeys(cityName);
            LoggerUtil.debug("Entered city name: " + cityName);
        } catch (Exception e) {
            LoggerUtil.error("Failed to search city", e);
            throw e;
        }
    }

    /**
     * Select city from dropdown
     *
     * @param cityName City name to select
     */
    public void selectCity(String cityName) {
        LoggerUtil.info("Selecting city from dropdown: " + cityName);
        try {
            WebElement cityOption = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.getCityDropdownOption(cityName)))
            );
            cityOption.click();
            LoggerUtil.debug("Selected city: " + cityName);
        } catch (Exception e) {
            LoggerUtil.error("Failed to select city", e);
            throw e;
        }
    }

    /**
     * Search for hospital keyword
     *
     * @param keyword Hospital keyword
     */
    public void searchHospital(String keyword) {
        LoggerUtil.info("Searching for hospital with keyword: " + keyword);
        try {
            WebElement hospitalInput = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(Locators.HOSPITAL_INPUT))
            );
            hospitalInput.sendKeys(keyword);
            LoggerUtil.debug("Entered hospital keyword: " + keyword);
        } catch (Exception e) {
            LoggerUtil.error("Failed to search hospital", e);
            throw e;
        }
    }

    /**
     * Click Hospital button to view results
     */
    public void clickHospitalButton() {
        LoggerUtil.info("Clicking Hospital button");
        try {
            WebElement hospitalButton = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.HOSPITAL_BUTTON))
            );
            hospitalButton.click();
            LoggerUtil.debug("Hospital button clicked");
        } catch (Exception e) {
            LoggerUtil.error("Failed to click Hospital button", e);
            throw e;
        }
    }
}

