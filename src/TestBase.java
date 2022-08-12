import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class TestBase
{
    protected AppiumDriver driver;

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

    protected WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(presenceOfElementLocated(by));
    }

    protected WebElement waitForElementPresent(By by, String errorMessage){
        return waitForElementPresent(by, errorMessage, 5);
    }

    protected WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    protected WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    protected boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(invisibilityOfElementLocated(by));
    }

    protected WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    protected void assertElementHasText(By by, String expectedText, String errorMessage){
        WebElement element = waitForElementPresent(by, "Cannot find element.");
        String actualText = element.getText();
        Assert.assertEquals(errorMessage, expectedText, actualText);
    }

    protected String waitForElementAndGetText(By by, String errorMessage){
        WebElement element = waitForElementPresent(by, errorMessage);
        return element.getText();
    }

    protected void assertSeveralListItemsExist(By by, String errorMessageForEmptyResults, String errorMessageForIncorrectItemNumber){
        waitForElementPresent(by, errorMessageForEmptyResults);
        List foundElements = driver.findElements(by);
        Assert.assertTrue(errorMessageForIncorrectItemNumber, foundElements.size() > 1 );
    }

    protected void assertListItemsContainSearchCriteria(By by, String expectedText, String errorMessageForEmptyResults, String errorMessageForIncorrectItemText){
        waitForElementPresent(by, errorMessageForEmptyResults);
        List<WebElement> foundElements = driver.findElements(by);
        foundElements.forEach(k -> Assert.assertTrue(
                errorMessageForIncorrectItemText + " '" + k.getText() + "' item does not contain '" + expectedText + "'.",
                k.getText().contains(expectedText)));
    }

    protected void swipeUp(int timeOfSwipe){
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;                  // середина экрана по горизонтали
        int start_y = (int)(size.height * 0.8);  // по вертикали 80% от верха
        int end_y = (int)(size.height * 0.2);    // по вертикали 20% от верха

        TouchAction action = new TouchAction(driver);
        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeElementToLeft(By by, String errorMessage){
        WebElement element = waitForElementPresent(
                by,
                errorMessage);

        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String errorMessage, int maxSwipes){

        int alreadySwiped = 0;
        while(driver.findElements(by).size() == 0){

            if(alreadySwiped > maxSwipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + errorMessage, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }
}
