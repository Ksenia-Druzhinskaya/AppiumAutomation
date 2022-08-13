package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject
{

    private static final String
            SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT = "//*[contains(@text,'Search Wikipedia')]",
            SEARCH_INPUT_ID = "org.wikipedia:id/search_src_text",
            SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
            SEARCH_RESULT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@class='android.view.ViewGroup']",
            SEARCH_RESULT_TITLE = "//*[@resource-id='org.wikipedia:id/search_results_list']//*[@resource-id='org.wikipedia:id/page_list_item_title']",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/search_results_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_LIST = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@class='android.view.ViewGroup']",
            BACK_BUTTON = "//android.view.ViewGroup/*[contains(@class, 'ImageButton')]";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATE methods */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATE methods */

    public void initSearchInput()
    {
        this.waitForElementPresent(By.xpath(SEARCH_INIT_ELEMENT),
                "Cannot find search input after clicking search init element.");
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element.", 5);
    }

    public void typeSearchLine(String searchLine)
    {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine, "Cannot find and type into search input.", 5);
    }

    public void waitForSearchResult(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath), "Cannot find search result with '" + substring + "'.", 15);
    }

    public void waitAtLeastOneSearchResult()
    {
        this.waitForElementPresent(By.xpath(SEARCH_RESULT), "Search result is empty.", 15);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String searchResultXpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(searchResultXpath), "Cannot find and click search result with '" + substring + "'.", 15);
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find Search Cancel button.", 5);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search Cancel button is still present.", 5);
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click Search Cancel button.", 5);
    }

    public void assertAllFoundArticlesContainSearchCriteria(String searchLine)
    {
        this.assertListItemsContainSearchCriteria(
                By.xpath(SEARCH_RESULT_TITLE),
                searchLine,
                "Found article does not contain search line '" + searchLine + "'.");
    }

    public WebElement waitForSearchInputPresent(){
        return waitForElementPresent(By.id(SEARCH_INPUT_ID), "Cannot find search input.", 5);
    }

    public void assertSeveralArticlesFound(){
        this.assertSeveralListItemsExist(By.xpath(SEARCH_RESULT_LIST), "Only one article is found.");
    }

    public boolean waitForArticleListEmpty(){
        return this.waitForElementNotPresent(By.xpath(SEARCH_RESULT), "Search result is not empty.", 5);
    }

    public void clickBack() {
        this.waitForElementAndClick(By.xpath(BACK_BUTTON), "Cannot find Back button.", 5);
    }

    public void searchAndOpenArticle(String searchLine, String articleTitle){
        initSearchInput();
        typeSearchLine(searchLine);
        clickByArticleWithSubstring(articleTitle);
    }
}
