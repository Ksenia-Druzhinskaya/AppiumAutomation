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

public class FirstTests
{
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidDevice");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "D:\\JavaAppiumAutomation\\apks\\org.wikipedia_50417_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

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

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String errorMessage){
        return waitForElementPresent(by, errorMessage, 5);
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expectedText, String errorMessage){
        WebElement element = waitForElementPresent(by, "Cannot find element.");
        String actualText = element.getText();
        Assert.assertEquals(errorMessage, expectedText, actualText);
    }

    private void assertSeveralListItemsExist(By by, String errorMessageForEmptyResults, String errorMessageForIncorrectItemNumber){
        waitForElementPresent(by, errorMessageForEmptyResults);
        List foundElements = driver.findElements(by);
        Assert.assertTrue(errorMessageForIncorrectItemNumber, foundElements.size() > 1 );
    }
}
