package pages;

import constants.Locators;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtil;

/**
 * Page Object Model for Cities/Lab Tests page
 */
public class CitiesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CitiesPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Get list of top cities
     *
     * @return List of city names
     */
    public List<String> getTopCities() {
        LoggerUtil.info("Fetching top cities");
        List<String> cities = new ArrayList<>();

        try {
            List<WebElement> topCitiesElements = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(Locators.TOP_CITIES))
            );
            LoggerUtil.debug("Found " + topCitiesElements.size() + " cities");

            for (WebElement cityElement : topCitiesElements) {
                String cityName = cityElement.getText();
                if (!cityName.isEmpty()) {
                    cities.add(cityName);
                    LoggerUtil.debug("Added city: " + cityName);
                }
            }

            LoggerUtil.info("Retrieved " + cities.size() + " top cities");
        } catch (Exception e) {
            LoggerUtil.error("Error fetching top cities", e);
        }

        return cities;
    }
}

