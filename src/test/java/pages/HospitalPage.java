package pages;

import constants.Locators;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtil;

/**
 * Page Object Model for Hospital listing page
 */
public class HospitalPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HospitalPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Data class to hold hospital information
     */
    public static class HospitalData {
        public String name;
        public double rating;
        public String openStatus;

        public HospitalData(String name, double rating, String openStatus) {
            this.name = name;
            this.rating = rating;
            this.openStatus = openStatus;
        }

        @Override
        public String toString() {
            return name + " | Rating: " + rating + " | " + openStatus;
        }
    }

    /**
     * Get all hospitals with rating above threshold and open 24x7
     *
     * @param ratingThreshold Minimum rating threshold
     * @return List of HospitalData matching criteria
     */
    public List<HospitalData> getHospitalsAboveRating(double ratingThreshold) {
        LoggerUtil.info("Fetching hospitals with rating above: " + ratingThreshold);
        List<HospitalData> hospitals = new ArrayList<>();

        try {
            List<WebElement> hospitalCards = driver.findElements(By.xpath(Locators.HOSPITAL_CARD));
            LoggerUtil.debug("Found " + hospitalCards.size() + " hospital cards");

            for (WebElement hospital : hospitalCards) {
                try {
                    // Get open status
                    WebElement openStatusElement = hospital.findElement(By.xpath(Locators.HOSPITAL_OPEN_STATUS));
                    String openStatus = openStatusElement.getText();

                    // Get rating
                    String ratingText = hospital.findElement(By.xpath(Locators.HOSPITAL_RATING)).getText();
                    double rating = Double.parseDouble(ratingText);

                    if (rating > ratingThreshold) {
                        // Get hospital name
                        String hospitalName = hospital.findElement(By.xpath(Locators.HOSPITAL_NAME)).getText();

                        HospitalData hospitalData = new HospitalData(hospitalName, rating, openStatus);
                        hospitals.add(hospitalData);
                        LoggerUtil.debug("Added hospital: " + hospitalData);
                    }
                } catch (NoSuchElementException e) {
                    // Ignore hospitals that don't match criteria
                    LoggerUtil.debug("Hospital card does not match filtering criteria, skipping");
                } catch (NumberFormatException e) {
                    LoggerUtil.warn("Could not parse rating for hospital, skipping");
                }
            }

            LoggerUtil.info("Retrieved " + hospitals.size() + " hospitals matching criteria");
        } catch (Exception e) {
            LoggerUtil.error("Error fetching hospitals", e);
        }

        return hospitals;
    }

    /**
     * Navigate back to previous page
     */
    public void navigateBack() {
        LoggerUtil.info("Navigating back");
        driver.navigate().back();
    }

    /**
     * Click on Lab Tests link
     */
    public void clickLabTests() {
        LoggerUtil.info("Clicking Lab Tests link");
        try {
            WebElement labTestsLink = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(Locators.LAB_TESTS_LINK))
            );
            labTestsLink.click();
            LoggerUtil.debug("Lab Tests link clicked");
        } catch (Exception e) {
            LoggerUtil.error("Failed to click Lab Tests link", e);
            throw e;
        }
    }
}

