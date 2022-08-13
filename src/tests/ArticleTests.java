package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.StartPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase
{
    @Test
    public void testCompareArticleTitle()
    {
        String searchLine = "Java";
        String expectedArticleTitle = "Java (programming language)";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchLine);
        searchPageObject.clickByArticleWithSubstring(expectedArticleTitle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String actualArticleTitle = articlePageObject.getArticleTitle();

        assertEquals("Article title is unexpected.", expectedArticleTitle, actualArticleTitle);
    }

    @Test
    public void testVerifyArticleTitlePresent(){
        String searchLine = "Java";
        String articleTitle = "Java (programming language)";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchAndOpenArticle(searchLine, articleTitle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.assertArticleTitlePresent();
    }

    @Test
    public void testSwipeArticle()
    {
        String searchLine = "Java";
        String articleTitle = "Java (programming language)";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchAndOpenArticle(searchLine, articleTitle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();

        articlePageObject.swipeUp(1000);
        articlePageObject.swipeUp(1000);
        articlePageObject.swipeUp(1000);
    }

    @Test
    public void testSwipeArticleUpToText()
    {
        String searchLine = "Appium";
        String articleTitle = "Appium";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchAndOpenArticle(searchLine, articleTitle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        articlePageObject.waitForTitleElement();
        articlePageObject.swipeToFooter();
    }
}
