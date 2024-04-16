package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.ExtentReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class TestListeners implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListeners.class);
    ExtentReports extentReport;
    ExtentTest extentTest;

    @Override
    public void onStart(ITestContext context) {
        logger.info("Test execution started");
        extentReport = ExtentReporter.generateExtentReport();

    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Test started: " + result.getName());
        extentTest = extentReport.createTest(result.getName());
        extentTest.log(Status.INFO,result.getName()+" started executing");

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test PASSED : " + result.getName());
        extentTest.log(Status.PASS,result.getName()+" got successfully executed");

    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;

        try {
            Object testInstance = result.getInstance();
            Class<?> realClass = result.getTestClass().getRealClass();
            Field driverField = realClass.getDeclaredField("driver");
            driverField.setAccessible(true);
            driver = (WebDriver) driverField.get(testInstance);
        } catch (Exception e) {
            logger.error("Error retrieving WebDriver instance: ", e);
        }

        if (driver != null) {
            String destinationScreenshotPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "Screenshots-" + result.getName() + ".png";

            try {
                File srcScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileHandler.copy(srcScreenshot, new File(destinationScreenshotPath));
            } catch (WebDriverException | IOException e) {
                logger.error("Failed to take screenshot: ", e);
            }

            extentTest.addScreenCaptureFromPath(destinationScreenshotPath);
        } else {
            logger.error("WebDriver instance is null, cannot take screenshot");
        }

        extentTest.log(Status.INFO, result.getThrowable());
        extentTest.log(Status.FAIL, result.getName() + " failed");
        logger.error("Test failed: " + result.getName(), result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.log(Status.INFO,result.getThrowable());
        extentTest.log(Status.SKIP, result.getName()+" got skipped");
        logger.info("Test Skipped: " + result.getName());

    }

    @Override
    public void onFinish(ITestContext context) {

        extentReport.flush();

        String pathOfExtentReport = System.getProperty("user.dir")+ File.separator + "test-output" + File.separator + "ExtentReports" + File.separator + "extentReport.html";
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            logger.error("Error : ", e);
        }

    }
}
