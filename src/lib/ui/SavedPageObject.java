package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SavedPageObject extends MainPageObject{

    private static final String
            SAVED_LABEL_SUBSTRING_TPL = "//android.widget.TextView[contains(@text, '{NUMBER} articles')]";

    public SavedPageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATE methods */
    private static String getResultSavedArticlesElement(Integer articleNumber) {
        return SAVED_LABEL_SUBSTRING_TPL.replace("{NUMBER}", articleNumber.toString());
    }
    /* TEMPLATE methods */

    public void clickSavedArticles(Integer articleNumber) {
        String resultXpath = getResultSavedArticlesElement(articleNumber);
        this.waitForElementAndClick(By.xpath(resultXpath), "Cannot find Saved for articles label.", 5);
    }
}
