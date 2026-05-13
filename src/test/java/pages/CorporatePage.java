package pages;

import constants.Locators;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtil;

/**
 * Page Object Model for Corporate section and forms
 */
public class CorporatePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CorporatePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    /**
     * Navigate to Health & Wellness Plans
     */
    public void navigateToHealthWellnessPlans() {
        LoggerUtil.info("Navigating to Health & Wellness Plans");
        try {
            WebElement forCorporates = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(Locators.FOR_CORPORATES))
            );
            forCorporates.click();
            LoggerUtil.debug("Clicked 'For Corporates' navigation");

            WebElement healthPlans = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath(Locators.HEALTH_WELLNESS_PLANS))
            );
            healthPlans.click();
            LoggerUtil.debug("Clicked 'Health & Wellness Plans'");
        } catch (Exception e) {
            LoggerUtil.error("Failed to navigate to Health & Wellness Plans", e);
            throw e;
        }
    }

    /**
     * Fill corporate form with valid data
     *
     * @param name Name
     * @param organization Organization name
     * @param contactNumber Contact number
     * @param email Email address
     * @param orgSize Organization size
     * @param interestedIn Interest option
     */
    public void fillCorporateForm(String name, String organization, String contactNumber,
                                   String email, String orgSize, String interestedIn) {
        LoggerUtil.info("Filling corporate form with user data");
        try {
            // Name
            WebElement nameField = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.FORM_NAME))
            );
            nameField.sendKeys(name);
            LoggerUtil.debug("Entered name: " + name);

            // Organization
            WebElement orgField = driver.findElement(By.xpath(Locators.FORM_ORGANIZATION));
            orgField.sendKeys(organization);
            LoggerUtil.debug("Entered organization: " + organization);

            // Contact Number
            WebElement phoneField = driver.findElement(By.xpath(Locators.FORM_CONTACT_NUMBER));
            phoneField.sendKeys(contactNumber);
            LoggerUtil.debug("Entered contact number: " + contactNumber);

            // Email
            WebElement emailField = driver.findElement(By.xpath(Locators.FORM_EMAIL));
            emailField.sendKeys(email);
            LoggerUtil.debug("Entered email: " + email);

            // Organization Size
            WebElement sizeDropdown = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.FORM_ORG_SIZE))
            );
            selectDropdownByValue(sizeDropdown, orgSize);
            LoggerUtil.debug("Selected organization size: " + orgSize);

            // Interested In
            WebElement interestedDropdown = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath(Locators.FORM_INTERESTED_IN))
            );
            selectDropdownByVisibleText(interestedDropdown, interestedIn);
            LoggerUtil.debug("Selected interested in: " + interestedIn);

            LoggerUtil.info("Corporate form filled successfully");
        } catch (Exception e) {
            LoggerUtil.error("Error filling corporate form", e);
            throw e;
        }
    }

    /**
     * Update phone field with new value
     *
     * @param phone New phone number
     */
    public void updatePhoneNumber(String phone) {
        LoggerUtil.info("Updating phone number to: " + phone);
        try {
            WebElement phoneField = driver.findElement(By.xpath(Locators.FORM_CONTACT_NUMBER));
            phoneField.clear();
            phoneField.sendKeys(phone);
            LoggerUtil.debug("Phone number updated");
        } catch (Exception e) {
            LoggerUtil.error("Failed to update phone number", e);
            throw e;
        }
    }

    /**
     * Update email field with new value
     *
     * @param email New email address
     */
    public void updateEmail(String email) {
        LoggerUtil.info("Updating email to: " + email);
        try {
            WebElement emailField = driver.findElement(By.xpath(Locators.FORM_EMAIL));
            emailField.clear();
            emailField.sendKeys(email);
            LoggerUtil.debug("Email updated");
        } catch (Exception e) {
            LoggerUtil.error("Failed to update email", e);
            throw e;
        }
    }

    /**
     * Scroll down the page
     */
    public void scrollDown() {
        LoggerUtil.debug("Scrolling down");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,100)");
    }

    /**
     * Select dropdown option by value attribute
     *
     * @param dropdown Dropdown element
     * @param value Value to select
     */
    private void selectDropdownByValue(WebElement dropdown, String value) {
        Select select = new Select(dropdown);
        select.selectByValue(value);
    }

    /**
     * Select dropdown option by visible text
     *
     * @param dropdown Dropdown element
     * @param visibleText Text to select
     */
    private void selectDropdownByVisibleText(WebElement dropdown, String visibleText) {
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
    }
}

