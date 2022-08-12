import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class FirstTests extends TestBase
{
    @Test
    public void testSearchArticle(){
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
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15);
    }

    @Test
    public void testCancelSearch(){
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

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find Close button",
                5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Close button is still present on the page",
                5);
    }

    @Test
    public void testSearchAndClearSearch(){
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

        assertSeveralListItemsExist(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/*[@class='android.view.ViewGroup']"),
                "Search result is empty.",
                "Only one article is found.");

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5);

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']/*[@class='android.view.ViewGroup']"),
                "Search result is not empty.",
                5);
    }

    @Test
    public void testSearchResults(){
        String searchCriteria = "Java";

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

        assertListItemsContainSearchCriteria(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']"),
                searchCriteria,
                "Search result is empty.",
                "Incorrect Search result text.");
    }

    @Test
    public void testCompareArticleTitle()
    {
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

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15);

        assertElementHasText(
                By.xpath("//*[@resource-id='pcs-edit-section-title-description']//preceding-sibling::*"),
                "Java (programming language)",
                "Article title is unexpected.");
    }

    @Test
    public void testClearSearchText(){
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

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
        "Cannot find search field",
        5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Close button is still present on the page",
                5);
    }

    @Test
    public void testVerifyDefaultSearchText(){
        waitForElementAndClick(
                By.xpath("//*[@text='SKIP']"),
                "Cannot find Skip button on Start page.",
                5);

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find search input to click.",
                5);

        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                "Search Wikipedia",
                "Search default text is unexpected.");
    }

}
