package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class MainPageObject
{
    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String errorMessage){
        return waitForElementPresent(by, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(invisibilityOfElementLocated(by));
    }

    public void assertElementPresent(By by, String errorMessage){
        try
        {
            driver.findElement(by);
        }
        catch(NoSuchElementException exception){
            throw new AssertionError(errorMessage + " " + exception.getMessage());
        }
    }

    public void assertSeveralListItemsExist(By by, String errorMessageForIncorrectItemNumber){
        List foundElements = driver.findElements(by);
        Assert.assertTrue(errorMessageForIncorrectItemNumber, foundElements.size() > 1 );
    }

    public void assertListItemsContainSearchCriteria(By by, String searchLine, String errorMessageForIncorrectItemText){
        List<WebElement> foundElements = driver.findElements(by);
        foundElements.forEach(k -> Assert.assertTrue(
                errorMessageForIncorrectItemText + " '" + k.getText() + "' item does not contain '" + searchLine + "'.",
                k.getText().contains(searchLine)));
    }

    public void swipeUp(int timeOfSwipe){
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

    public void swipeElementToLeft(By by, String errorMessage){
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

    public void swipeUpQuick(){
        swipeUp(200);
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes){

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
