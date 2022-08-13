package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject{

    private static final String
            TITLE = "//*[@resource-id='pcs-edit-section-title-description']//preceding-sibling::*",
            FOOTER_ELEMENT = "//*[@text='View article in browser']";

    public ArticlePageObject(AppiumDriver driver){
        super(driver);
    }

    public WebElement waitForTitleElement(){
        return this.waitForElementPresent(By.xpath(TITLE), "Cannot find article title on page.", 15);
    }

    public String getArticleTitle(){
        WebElement titleElement = waitForTitleElement();
        return titleElement.getText();
    }

    public void swipeToFooter(){
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of the article.",
                20);
    }

    public void assertArticleTitlePresent(){
        this.assertElementPresent(By.xpath(TITLE), "Article title is not present.");
    }
}
