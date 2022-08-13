package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class StartPageObject extends MainPageObject{

    private static final String
            SKIP_BUTTON = "//*[@text='SKIP']";

    public StartPageObject(AppiumDriver driver){
        super(driver);
    }

    public void skipStartPage() {
        this.waitForElementAndClick(By.xpath(SKIP_BUTTON), "Cannot find Skip button on Start page.", 5);
    }
}
