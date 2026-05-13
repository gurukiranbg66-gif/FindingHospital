package constants;

public class Locators {

    // Search Page Locators
    public static final String CITY_INPUT = "//input[@data-qa-id='omni-searchbox-locality']";
    public static final String CITY_DROPDOWN_OPTION = "//div[text()='%s']"; // Replace %s with city name
    public static final String HOSPITAL_INPUT = "//input[@data-qa-id='omni-searchbox-keyword']";
    public static final String HOSPITAL_BUTTON = "//div[text()='Hospital']";

    // Hospital Listing Page Locators
    public static final String HOSPITAL_CARD = "//div[@class='c-estb-card']";
    public static final String HOSPITAL_OPEN_STATUS = ".//span[contains(text(),'Open 24x7')]";
    public static final String HOSPITAL_RATING = ".//div[@class='text-1']/span[@class='u-bold']";
    public static final String HOSPITAL_NAME = ".//h2";

    // Lab Tests Page Locators
    public static final String LAB_TESTS_LINK = "//a[@aria-label='Lab Tests']";
    public static final String TOP_CITIES = "//div[@class='u-margint--standard o-f-color--primary']";

    // Corporate Page Locators
    public static final String FOR_CORPORATES = "//span[contains(@class, 'nav-interact') and normalize-space() = 'For Corporates']";
    public static final String HEALTH_WELLNESS_PLANS = "//a[normalize-space()='Health & Wellness Plans']";
    public static final String FORM_NAME = "//input[@id='name']";
    public static final String FORM_ORGANIZATION = "//input[@id='organizationName']";
    public static final String FORM_CONTACT_NUMBER = "//input[@id='contactNumber']";
    public static final String FORM_EMAIL = "//input[@id='officialEmailId']";
    public static final String FORM_ORG_SIZE = "//select[@id='organizationSize']";
    public static final String FORM_ORG_SIZE_OPTION = "//option[@value='10001+']";
    public static final String FORM_INTERESTED_IN = "//select[@id='interestedIn']";
    public static final String FORM_INTERESTED_OPTION = "//option[@value='Taking a demo']";

    // Dynamic locators
    public static String getCityDropdownOption(String cityName) {
        return String.format(CITY_DROPDOWN_OPTION, cityName);
    }
}

