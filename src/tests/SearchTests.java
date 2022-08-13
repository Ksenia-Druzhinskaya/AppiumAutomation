package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.StartPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class SearchTests extends CoreTestCase
{
    @Test
    public void testSearchArticle(){

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.waitForSearchResult("Java (programming language)");
    }

    @Test
    public void testAllFoundArticlesContainSearchLine(){
        String searchLine = "Java";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.waitAtLeastOneSearchResult();
        searchPageObject.assertAllFoundArticlesContainSearchCriteria(searchLine);
    }

    @Test
    public void testVerifyDefaultSearchText(){
        String expectedSearchInputText = "Search Wikipedia";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();

        WebElement searchInput = searchPageObject.waitForSearchInputPresent();
        String actualSearchInputText = searchInput.getText();
        assertEquals("Search input does not have default text.", expectedSearchInputText, actualSearchInputText);
    }

    @Test
    public void testCancelSearch(){

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");

        searchPageObject.waitForCancelButtonToAppear();
        searchPageObject.clickCancelSearch();
        searchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testSearchAndClearSearch(){
        String searchLine = "Java";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        searchPageObject.waitAtLeastOneSearchResult();
        searchPageObject.assertSeveralArticlesFound();

        WebElement searchInput = searchPageObject.waitForSearchInputPresent();
        searchInput.clear();
        searchPageObject.waitForArticleListEmpty();
    }

    @Test
    public void testClearSearchAndVerifyCloseButtonDissapears(){
        String searchLine = "Java";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);

        searchPageObject.waitAtLeastOneSearchResult();
        searchPageObject.assertSeveralArticlesFound();

        WebElement searchInput = searchPageObject.waitForSearchInputPresent();
        searchInput.clear();
        searchPageObject.waitForCancelButtonToDisappear();
    }
}
