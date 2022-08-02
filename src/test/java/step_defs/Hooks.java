package step_defs;


import dbModels.UserProfile;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utilities.database.DBUtils;
import utilities.drivers.Driver;
import utilities.EnvironmentManager;

import java.io.File;

public class Hooks {
    private static final Logger LOGGER = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp() throws Exception {
        //to set up environment
        //we will have environment manager that will store all the env variables
        //and our before hook will call the environment manager to set up all
        //the variables correctly depending on the env we are running our tests against
        //EnvironmentManager.setUpEnvironment();
        EnvironmentManager.setUpEnvironment();
       // to print version of browser. Other option to specify in properties file and get it as ConfigReader.getProperty("browserVersion")
        Capabilities capabilities = ((RemoteWebDriver)Driver.getDriver()).getCapabilities();
        String browserName = capabilities.getBrowserName();
        String browserVersion = capabilities.getBrowserVersion();
        LOGGER.info(browserName + " " + browserVersion);

        //set up remote execution sauce lab
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
//                TakesScreenshot screenshot = (TakesScreenshot) Driver.getDriver();
//                File file = screenshot.getScreenshotAs(OutputType.FILE);
//                FileUtils.copyFile(file, new File("./failed_tests/screenshot.png"));

                 //I can attach a screenshot to a failing scenario
                //we will take a screenshot
                final byte[] screenshot2 = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
                //add screenshot to html report
                Thread.sleep(3000);
                scenario.attach(screenshot2, "image/png", "My screenshot");

            }
        } catch (Exception e) {
            System.out.println("Error happened while taking screenshot");
            e.getMessage();
        }

        DBUtils.close(); //close connection to DB
        Driver.closeDriver();
    }
}
