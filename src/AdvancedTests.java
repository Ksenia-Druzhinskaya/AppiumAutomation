import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class AdvancedTests extends TestBase
{
    @Test
    public void testSwipeArticle()
    {
        openArticle(
                "Java",
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Object-oriented programming language']"));

        swipeUp(1000);
        swipeUp(1000);
        swipeUp(1000);
    }

    @Test
    public void testSwipeArticleUpToText()
    {
        openArticle(
                "Appium",
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Appium']"));

        swipeUpToFindElement(
                By.xpath("//*[@text='View article in browser']"),
                "Cannot find the end of the article.",
                20);
    }

    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        openArticle(
                "Java",
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Object-oriented programming language']"));

        String titleBeforeRotation = waitForElementAndGetText(
                By.xpath("//*[@resource-id='pcs-edit-section-title-description']//preceding-sibling::*"),
                "Article title is not found");

        driver.rotate(ScreenOrientation.LANDSCAPE);

        String titleAfterRotation = waitForElementAndGetText(
                By.xpath("//*[@resource-id='pcs-edit-section-title-description']//preceding-sibling::*"),
                "Article title is not found");

        Assert.assertEquals(
                "Article title has been changed after screen rotation.",
                titleBeforeRotation,
                titleAfterRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground(){
        waitForElementAndClick(
                By.xpath("//*[@text='SKIP']"),
                "Cannot find Skip button on Start page.",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input to click.",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Java",
                "Cannot find search input to send Keys.",
                5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Java' article in search.",
                5);

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Java' article after returning from background.",
                5);
    }

    @Test
    public void testSaveTwoArticlesAndDeleteOne() {
        String searchCriteria = "Java";
        String firstArticleTitle = "Java (programming language)";
        String secondArticleTitle = "JavaScript";

        // Open "Java (programming language)" article
        openArticle(
                searchCriteria,
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='" + firstArticleTitle + "']"));

        // Save "Java (programming language)" article
        saveArticle(firstArticleTitle);

        // Go back
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find Back button.",
                5);

        // Open "JavaScript" article
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='" + secondArticleTitle + "']"),
                "Cannot find 'JavaScript' article after returning to article list.",
                15);

        // Save "JavaScript" article
        saveArticle(secondArticleTitle);

        // Go back
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot find Back button.",
                5);

        // Go back
        waitForElementAndClick(
                By.xpath("//android.view.ViewGroup/*[contains(@class, 'ImageButton')]"),
                "Cannot find Back button.",
                5);

        // Click "Saved" button
        waitForElementAndClick(
                By.xpath("//*[@text='Saved']"),
                "Cannot find Saved button.",
                5);

        // Click "Saved 2 articles"
        waitForElementAndClick(
                By.xpath("//android.widget.TextView[contains(@text, '2 articles')]"),
                "Cannot find Saved for 2 articles page.",
                5);

        // Delete "Java (programming language)" article from Saved
        swipeElementToLeft(
                By.xpath("//*[@text='" + firstArticleTitle + "']"),
                "Cannot find '" + firstArticleTitle + "'.");

        // Verify that "Java (programming language)" article is deleted
        waitForElementNotPresent(
                By.xpath("//*[@text='" + firstArticleTitle + "']"),
                "The article '" + firstArticleTitle + "' was not deleted.",
                5);

        // Open "JavaScript" article
        waitForElementAndClick(
                By.xpath("//*[@text='" + secondArticleTitle + "']"),
                "The article '" + secondArticleTitle + "' is not present.",
                5);

        // Verify that the article has "JavaScript" title
        assertElementHasText(
                By.xpath("//*[@resource-id='pcs-edit-section-title-description']//preceding-sibling::*"),
                secondArticleTitle,
                "Article title is unexpected.");
    }

    @Test
    public void testVerifyArticleTitlePresent(){
        String searchCriteria = "Java";
        String firstArticleTitle = "Java (programming language)";

        waitForElementAndClick(
                By.xpath("//*[@text='SKIP']"),
                "Cannot find Skip button on Start page.",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input to click.",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                searchCriteria,
                "Cannot find search input to send Keys.",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='" + firstArticleTitle + "']"),
                "Cannot find '" + firstArticleTitle + "' article in search.",
                15);

        assertElementPresent(By.xpath("//*[@resource-id='pcs-edit-section-title-description']//preceding-sibling::*"),
                "Article title is not present.");
    }

    private void openArticle(String searchCrteria, By byToClick){
        waitForElementAndClick(
                By.xpath("//*[@text='SKIP']"),
                "Cannot find Skip button on Start page.",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input to click.",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                searchCrteria,
                "Cannot find search input to send Keys.",
                5);

        waitForElementAndClick(
                byToClick,
                "Cannot find '" + searchCrteria + "' article in search.",
                15);

        waitForElementPresent(
                By.xpath("//*[@resource-id='pcs-edit-section-title-description']//preceding-sibling::*"),
                "Cannot find article title",
                15);
    }

    private void saveArticle(String articleTitle)
    {
        waitForElementAndClick(
                By.xpath("//*[@text='Save']"),
                "Cannot find Save button on Start page.",
                5);

        waitForElementPresent(
                By.xpath("//*[contains(@text, 'Saved " + articleTitle + "')]"),
                "Article '" + articleTitle + "' is not saved.",
                5);
    }
}
