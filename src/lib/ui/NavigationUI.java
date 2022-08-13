package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{

    private static final String
            SAVE_BUTTON = "//*[@text='Save']",
            SAVED_LABEL = "//*[contains(@text, 'Saved ')]",
            SAVED_BUTTON = "//*[@text='Saved']",
            BACK_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

    public NavigationUI(AppiumDriver driver){
        super(driver);
    }

    public void clickSave() {
        this.waitForElementAndClick(By.xpath(SAVE_BUTTON), "Cannot find Save button.", 5);
    }

    public void waitSaved() {
        this.waitForElementPresent(By.xpath(SAVED_LABEL), "Article is not saved.", 5);
    }

    public void saveAndWaitSaved(){
        clickSave();
        waitSaved();
    }

    public void clickBack() {
        this.waitForElementAndClick(By.xpath(BACK_BUTTON), "Cannot find Back button.", 5);
    }

    public void clickSaved() {
        this.waitForElementAndClick(By.xpath(SAVED_BUTTON), "Cannot find Saved button.", 5);
    }
}
