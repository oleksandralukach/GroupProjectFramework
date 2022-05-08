package step_defs;

import dbModels.UserProfile;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import utilities.Driver;
import utilities.EnvironmentManager;
import utilities.database.DBUtilV2;

import java.util.List;
import java.util.Locale;

public class BasicValidationSteps {

    WebDriver driver = Driver.getDriver();
    UserProfile user = new UserProfile();

    @When("^the user navigates to google$")
    public void the_user_navigates_to_google() {

        driver.navigate().to("https://www.google.com/");
        //driver.navigate().to(EnvironmentManager.baseUrl); later
    }

    @When("^the user searches for \"([^\"]*)\"$")
    public void the_user_searches_for(String searchCriteria) {
        driver.findElement(By.name("q")).sendKeys(searchCriteria + Keys.ENTER);

    }

    @Then("^verify \"([^\"]*)\" is in the title of the page$")
    public void verify_is_in_the_title_of_the_page(String searchCriteria) {
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
}
