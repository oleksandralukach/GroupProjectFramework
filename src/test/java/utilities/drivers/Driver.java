package utilities.drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import utilities.ConfigReader;
import utilities.SaucelabsDriver;
import utilities.drivers.ChromeWebDriver;

import java.time.Duration;

//Singleton design pattern -
// Singleton means that the class set up in a way that only one object can be created in a time –
// if the driver is running no new driver can be created

public class Driver {

    private static final Logger LOGGER = LogManager.getLogger(Driver.class);

    private Driver() {
    }//only through static method of this class allow object creation of this class, only in this class

    private static WebDriver driver;

    public static WebDriver getDriver() {
        LOGGER.debug("Initializing a webdriver for selenium version 4.1.2");
        if (driver == null) {
            LOGGER.info("Loading " + ConfigReader.getProperty("browser").toLowerCase() + " browser");
            switch (ConfigReader.getProperty("browser").toLowerCase()) { //here everything need to be in lower cases as we're converting our configurations to lower cases

                default:
                    LOGGER.info("Loading Chrome WebDriver");
                    driver = ChromeWebDriver.loadChromeWebDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    //Safari browser comes with the driver integrated.
                    driver = new SafariDriver();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
                    driver.manage().window().maximize();
                    break;
                case "ie":
                    WebDriverManager.iedriver().setup();
                    driver = new InternetExplorerDriver();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(9));
                    driver.manage().window().maximize();
                    break;
                case "saucelabs":
                    driver = SaucelabsDriver.loadSaucelabsDriver();
                    break;
            }
        }

        //if it is not null we simply return existing driver
        return driver;
    }

    public static void closeDriver() {
        try {
            if (driver != null) {
                LOGGER.info("Closing driver");
                driver.close();
                driver.quit();
                driver = null;
            }
        } catch (Exception e) { // when we close it manually
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
