package step_defs;

import dbModels.UserProfile;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.*;
import utilities.drivers.Driver;

public class BasicValidationSteps {
    private static final Logger LOGGER = LogManager.getLogger(BasicValidationSteps.class);

    WebDriver driver = Driver.getDriver();
    UserProfile user = new UserProfile();



    @When("^the user navigates to google$")
    public void the_user_navigates_to_google() {
        System.out.println(Thread.currentThread().getId());
        driver.navigate().to("https://www.google.com/");
        //driver.navigate().to(EnvironmentManager.baseUrl); later
        LOGGER.info("Navigated to https://www.google.com");
    }

    @When("^the user searches for \"([^\"]*)\"$")
    public void the_user_searches_for(String searchCriteria) {
        LOGGER.info("Searched for: " + searchCriteria);
        driver.findElement(By.name("q")).sendKeys(searchCriteria + Keys.ENTER);
    }

    @Then("^verify \"([^\"]*)\" is in the title of the page$")
    public void verify_is_in_the_title_of_the_page(String searchCriteria) {
       LOGGER.info("Verifying if page title has " + searchCriteria);
        Assert.assertTrue(driver.getTitle().toLowerCase().contains(searchCriteria));
    }


//    @And("I get user with id {string} from database")
//    public Object iGetUserWithIdFromDatabase(String id) {
//        String q = "Select * From users Where id = ?";
//        List<UserProfile> users = DBUtilV2.query(q,id).convertResultSetToBeans(UserProfile.class);
//        user = user.getAddress().isEmpty() ? null : users.get(0);
//        Assert.assertTrue(user.getAddress().isEmpty());
//        return new Object();
//    }

//     }
    //    public static void main(String[] args) {
//        Result result = JUnitCore.runClasses(JunitAnnotationsExample.class);
//        for (Failure failure : result.getFailures()) {
//            System.out.println(failure.toString());
//        }
//        System.out.println("Result=="+result.wasSuccessful());
//    }
}
