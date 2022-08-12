import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
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
}
