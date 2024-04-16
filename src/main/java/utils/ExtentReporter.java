package utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.cdimascio.dotenv.Dotenv;

public class ExtentReporter {

    public static ExtentReports generateExtentReport() {

        ExtentReports extentReport = new ExtentReports();

        File extentReportFile = new File(System.getProperty("user.dir")+ File.separator + "test-output" + File.separator + "ExtentReports" + File.separator + "extentReport.html");
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setReportName("Test Automation Results Report");
        sparkReporter.config().setDocumentTitle("Test Report");
        sparkReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss");

        extentReport.attachReporter(sparkReporter);

        Dotenv env = Dotenv.load();

        extentReport.setSystemInfo("Application URL",env.get("url"));
        extentReport.setSystemInfo("Browser Name",env.get("browserName"));

        return extentReport;
    }

}