package tests;

import java.io.IOException;
import java.util.List;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import pages.CitiesPage;
import pages.CorporatePage;
import pages.HospitalPage;
import pages.HospitalPage.HospitalData;
import pages.SearchPage;
import project.BasePage;
import utils.ConfigReader;
import utils.ExcelUtilsRefactored;
import utils.LoggerUtil;
import utils.ScreenshotUtils;


/**
 * Practo Automation Test Suite
 * Tests are designed to be independent and can run in any order
 */
@Epic("Practo Automation")
@Feature("Hospital & Lab Test Flow")
public class PractoTest extends BasePage {

    private final String excelPath = System.getProperty("user.dir") + "/" + ConfigReader.getExcelOutputPath();
    private final String sheetName = "Sheet1";
    private final String citySheetName = "Sheet2";
    private final String outputDir = System.getProperty("user.dir") + "/" + ConfigReader.getScreenshotOutputPath();


    @Story("Search hospitals by city and keyword")
    @Test(description = "Search Hospital By City Name", priority = 1)
    public void testSearchHospitalByCity() throws IOException {
        LoggerUtil.info("Starting test: testSearchHospitalByCity");

        try {
            // Create output directory
            new java.io.File(outputDir).mkdirs();

            // Use Page Object for search
            SearchPage searchPage = new SearchPage(driver, wait);
            String testCity = ConfigReader.getProperty("test.city");
            String testKeyword = ConfigReader.getProperty("test.hospital.keyword");

            // Search for city
            searchPage.searchCity(testCity);
            searchPage.selectCity(testCity);

            // Search for hospital keyword
            searchPage.searchHospital(testKeyword);

            // Capture screenshot
            ScreenshotUtils.captureAndAttachScreenshot(driver, "Search Hospital By City Name");
            ScreenshotUtils.saveScreenshot(driver, "01_Search_Hospital", outputDir);

            LoggerUtil.info("Test completed: testSearchHospitalByCity");
        } catch (Exception e) {
            LoggerUtil.error("Test failed: testSearchHospitalByCity", e);
            ScreenshotUtils.captureAndAttachScreenshot(driver, "testSearchHospitalByCity_FAILED");
            throw e;
        }
    }
    @Story("Click Hospital button and initialize Excel headers")
    @Test(description = "Click Hospital Button and Setup Excel Headers", priority = 2)
    public void testClickHospitalButtonAndSetupHeaders() throws IOException {
        LoggerUtil.info("Starting test: testClickHospitalButtonAndSetupHeaders");

        try {
            new java.io.File(outputDir).mkdirs();

            SearchPage searchPage = new SearchPage(driver, wait);
            searchPage.clickHospitalButton();

            // Write headers
            ExcelUtilsRefactored.setCellData(excelPath, sheetName, 0, 0, "Hospital Name");
            ExcelUtilsRefactored.setCellData(excelPath, sheetName, 0, 1, "Ratings");
            ExcelUtilsRefactored.setCellData(excelPath, sheetName, 0, 2, "OpenStatus");

            ExcelUtilsRefactored.setCellData(excelPath, citySheetName, 0, 0, "Top Cities");

            ScreenshotUtils.captureAndAttachScreenshot(driver, "Hospital Button Clicked");
            ScreenshotUtils.saveScreenshot(driver, "02_Hospital_View", outputDir);

            LoggerUtil.info("Test completed: testClickHospitalButtonAndSetupHeaders");
        } catch (Exception e) {
            LoggerUtil.error("Test failed: testClickHospitalButtonAndSetupHeaders", e);
            ScreenshotUtils.captureAndAttachScreenshot(driver, "testClickHospitalButton_FAILED");
            throw e;
        }
    }


    @Story("Extract hospital data and save to Excel")
    @Test(description = "Extract Hospital Data and Save to Excel",  priority = 3)
    @Description("")
    public void testExtractHospitalsAndSaveToExcel() throws IOException {
        LoggerUtil.info("Starting test: testExtractHospitalsAndSaveToExcel");

        try {
            new java.io.File(outputDir).mkdirs();

            HospitalPage hospitalPage = new HospitalPage(driver, wait);
            double ratingThreshold = Double.parseDouble(ConfigReader.getProperty("test.rating.threshold"));

            // Get hospitals above rating threshold
            List<HospitalData> hospitals = hospitalPage.getHospitalsAboveRating(ratingThreshold);

            // Save to Excel
            int excelRow = 1; // Start after header
            for (HospitalData hospital : hospitals) {
                LoggerUtil.debug("Writing hospital to Excel: " + hospital);
                ExcelUtilsRefactored.setCellData(excelPath, sheetName, excelRow, 0, hospital.name);
                ExcelUtilsRefactored.setCellData(excelPath, sheetName, excelRow, 1, String.valueOf(hospital.rating));
                ExcelUtilsRefactored.setCellData(excelPath, sheetName, excelRow, 2, hospital.openStatus);
                excelRow++;
            }

            LoggerUtil.info("Extracted " + hospitals.size() + " hospitals and saved to Excel");

            ScreenshotUtils.captureAndAttachScreenshot(driver, "Hospital Data Extraction Complete");
            ScreenshotUtils.saveScreenshot(driver, "03_Hospital_Data", outputDir);

            LoggerUtil.info("Test completed: testExtractHospitalsAndSaveToExcel");
        } catch (Exception e) {
            LoggerUtil.error("Test failed: testExtractHospitalsAndSaveToExcel", e);
            ScreenshotUtils.captureAndAttachScreenshot(driver, "testExtractHospitals_FAILED");
            throw e;
        }

    }

    @Story("Navigate and extract top cities")
    @Test(description = "Extract Top Cities and Save to Excel",  priority = 4)
    public void testExtractTopCitiesAndSaveToExcel() throws IOException {
        LoggerUtil.info("Starting test: testExtractTopCitiesAndSaveToExcel");

        try {
            new java.io.File(outputDir).mkdirs();

            HospitalPage hospitalPage = new HospitalPage(driver, wait);
            hospitalPage.navigateBack();
            hospitalPage.clickLabTests();

            CitiesPage citiesPage = new CitiesPage(driver, wait);
            List<String> topCities = citiesPage.getTopCities();

            // Save to Excel
            int cityRow = 1; // Start after header
            for (String city : topCities) {
                LoggerUtil.debug("Writing city to Excel: " + city);
                ExcelUtilsRefactored.setCellData(excelPath, citySheetName, cityRow, 0, city);
                cityRow++;
            }

            LoggerUtil.info("Extracted " + topCities.size() + " top cities and saved to Excel");

            ScreenshotUtils.captureAndAttachScreenshot(driver, "Top Cities Extraction Complete");
            ScreenshotUtils.saveScreenshot(driver, "04_Top_Cities", outputDir);

            LoggerUtil.info("Test completed: testExtractTopCitiesAndSaveToExcel");
        } catch (Exception e) {
            LoggerUtil.error("Test failed: testExtractTopCitiesAndSaveToExcel", e);
            ScreenshotUtils.captureAndAttachScreenshot(driver, "testExtractTopCities_FAILED");
            throw e;
        }
    }

    @Story("Fill valid form and validate it with invalid data")
    @Test(description = "Validate Corporate Health & Wellness Form", priority = 5)
    public void testCorporateFormValidation() throws IOException {
        LoggerUtil.info("Starting test: testCorporateFormValidation");

        try {
            new java.io.File(outputDir).mkdirs();

            CorporatePage corporatePage = new CorporatePage(driver, wait);

            // Navigate to corporate section

            corporatePage.navigateToHealthWellnessPlans();

            // Fill form with valid data
            String name = ConfigReader.getProperty("test.form.name");
            String organization = ConfigReader.getProperty("test.form.organization");
            String phone = ConfigReader.getProperty("test.form.phone");
            String email = ConfigReader.getProperty("test.form.email");

            corporatePage.fillCorporateForm(name, organization, phone, email, "10001+", "Taking a demo");

            corporatePage.scrollDown();

            LoggerUtil.info("Form filled with valid data");
            ScreenshotUtils.captureAndAttachScreenshot(driver, "Valid Form Filled");
            ScreenshotUtils.saveScreenshot(driver, "05_Valid_Form", outputDir);

            // Update with invalid data to test validation
            LoggerUtil.info("Testing form validation with invalid data");
            corporatePage.updatePhoneNumber("0");
            corporatePage.updateEmail("invalid Email");

            ScreenshotUtils.captureAndAttachScreenshot(driver, "Invalid Form Data");
            ScreenshotUtils.saveScreenshot(driver, "06_Invalid_Form", outputDir);

            LoggerUtil.info("Test completed: testCorporateFormValidation");
        } catch (Exception e) {
            LoggerUtil.error("Test failed: testCorporateFormValidation", e);
            ScreenshotUtils.captureAndAttachScreenshot(driver, "testCorporateForm_FAILED");
            throw e;
        }
    }
}