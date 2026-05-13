package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.LoggerUtil;

/**
 * Test listener for test lifecycle management and reporting
 */
public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        LoggerUtil.info("======================================");
        LoggerUtil.info("Test Suite Started: " + context.getName());
        LoggerUtil.info("======================================");
    }

    @Override
    public void onFinish(ITestContext context) {
        LoggerUtil.info("======================================");
        LoggerUtil.info("Test Suite Finished: " + context.getName());
        LoggerUtil.info("Total Tests Run: " + context.getAllTestMethods().length);
        LoggerUtil.info("Passed: " + context.getPassedTests().size());
        LoggerUtil.info("Failed: " + context.getFailedTests().size());
        LoggerUtil.info("Skipped: " + context.getSkippedTests().size());
        LoggerUtil.info("======================================");
    }

    @Override
    public void onTestStart(ITestResult result) {
        LoggerUtil.info("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LoggerUtil.info("✓ Test Passed: " + result.getMethod().getMethodName()
                + " | Duration: " + (result.getEndMillis() - result.getStartMillis()) + "ms");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LoggerUtil.error("✗ Test Failed: " + result.getMethod().getMethodName());
        LoggerUtil.error("Failure Message: " + result.getThrowable().getMessage(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LoggerUtil.warn("⊘ Test Skipped: " + result.getMethod().getMethodName());
        if (result.getThrowable() != null) {
            LoggerUtil.warn("Reason: " + result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LoggerUtil.warn("Test Failed but within success percentage: " + result.getMethod().getMethodName());
    }
}

