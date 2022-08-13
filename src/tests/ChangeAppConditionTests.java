package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.StartPageObject;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase
{
    @Test
    public void testChangeScreenOrientationOnSearchResults(){
        String searchLine = "Java";
        String articleTitle = "Java (programming language)";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.searchAndOpenArticle(searchLine, articleTitle);

        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String titleBeforeRotation = articlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();

        assertEquals(
                "Article title has been changed after landscape screen rotation.",
                titleBeforeRotation,
                titleAfterRotation);

        this.rotateScreenPortrait();
        String titleAfterSecondRotation = articlePageObject.getArticleTitle();

        assertEquals(
                "Article title has been changed after portrait screen rotation.",
                titleBeforeRotation,
                titleAfterSecondRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground(){
        String searchCriteria = "Java";
        String articleTitle = "Java (programming language)";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchCriteria);
        searchPageObject.waitForSearchResult(articleTitle);

        this.backgroundApp(2);
        searchPageObject.waitForSearchResult(articleTitle);
    }
}
