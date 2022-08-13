package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SavedListPageObject extends MainPageObject{

    private static final String
            ARTICLE_SUBSTRING_TPL = "//*[@text='{ARTICLE_TITLE}']";

    public SavedListPageObject(AppiumDriver driver){
        super(driver);
    }

    /* TEMPLATE methods */
    private static String getResultArticleElement(String substring) {
        return ARTICLE_SUBSTRING_TPL.replace("{ARTICLE_TITLE}", substring);
    }
    /* TEMPLATE methods */

    public void deleteArticle(String articleTitle){
        String articleResultXpath = getResultArticleElement(articleTitle);
        this.swipeElementToLeft(By.xpath(articleResultXpath),  "Cannot find '" + articleTitle + "'.");
    }

    public void verifyArticleDeleted(String articleTitle){
        String articleResultXpath = getResultArticleElement(articleTitle);
        this.waitForElementNotPresent(By.xpath(articleResultXpath),  "The article '" + articleTitle + "' was not deleted.", 5);
    }

    public void openArticle(String articleTitle){
        String articleResultXpath = getResultArticleElement(articleTitle);
        this.waitForElementAndClick(By.xpath(articleResultXpath),  "Cannot find '" + articleTitle + "'.", 5);
    }
}
