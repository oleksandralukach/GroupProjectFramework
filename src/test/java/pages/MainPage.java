package pages;

import org.openqa.selenium.support.PageFactory;
import utilities.drivers.Driver;

public class MainPage {

    public MainPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    //@FindBy(xpath = "")
}
