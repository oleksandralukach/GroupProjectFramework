package step_defs;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentReportTest {
        static ExtentTest test;
        static ExtentReports report;
        static ExtentHtmlReporter htmlReporter;
        static WebDriver driver;

        @BeforeClass
        public static void startTest() {
            htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "ExtentReport.html");
            htmlReporter.config().setEncoding("utf-8");
            htmlReporter.config().setDocumentTitle("Automation Report");
            htmlReporter.config().setReportName("Test Results");
            htmlReporter.config().setTheme(Theme.STANDARD);
            report = new ExtentReports();
            report.setSystemInfo("Tester: ", "Alex Dykun");
            report.attachReporter(htmlReporter);

            test = report.createTest("extentReportsDemo");
        }

        @Test
        public void extentReportsDemo() {

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
            driver.get("https://www.google.co.in");
            if (driver.getTitle().equals("")) {
                test.log(Status.PASS, "Navigated to the specified URL");
            } else {
                test.log(Status.FAIL, "Test Failed");
                Assert.assertEquals(driver.getTitle(), " ");
            }

        }

        @AfterClass
        public static void endTest() {
            //report.removeTest(test);
            report.flush();
            driver.quit();
            
        }
}
