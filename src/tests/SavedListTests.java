package tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class SavedListTests extends CoreTestCase
{
    @Test
    public void testSaveTwoArticlesAndDeleteOne()
    {
        String searchCriteria = "Java";
        String firstArticleTitle = "Java (programming language)";
        String secondArticleTitle = "JavaScript";

        StartPageObject startPageObject = new StartPageObject(driver);
        startPageObject.skipStartPage();

        // Open "Java (programming language)" article
        SearchPageObject searchPageObject = new SearchPageObject(driver);
        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine(searchCriteria);
        searchPageObject.clickByArticleWithSubstring(firstArticleTitle);

        // Save "Java (programming language)" article
        NavigationUI navigationUI = new NavigationUI(driver);
        navigationUI.saveAndWaitSaved();
        // Go back
        navigationUI.clickBack();

        // Open "JavaScript" article
        searchPageObject.clickByArticleWithSubstring(secondArticleTitle);
        // Save "JavaScript" article
        navigationUI.saveAndWaitSaved();
        // Go back
        navigationUI.clickBack();

        // Go back from Search page
        searchPageObject.clickBack();

        // Click "Saved" button to see saved articles
        navigationUI.clickSaved();

        // Click "Saved 2 articles"
        SavedPageObject savedPageObject = new SavedPageObject(driver);
        savedPageObject.clickSavedArticles(2);

        // Delete "Java (programming language)" article from Saved
        SavedListPageObject savedListPageObject = new SavedListPageObject(driver);
        savedListPageObject.deleteArticle(firstArticleTitle);

        // Verify that "Java (programming language)" article is deleted
        savedListPageObject.verifyArticleDeleted(firstArticleTitle);

        // Open "JavaScript" article
        savedListPageObject.openArticle(secondArticleTitle);

        // Verify article title
        ArticlePageObject articlePageObject = new ArticlePageObject(driver);
        String actualArticleTitle = articlePageObject.getArticleTitle();
        assertEquals("Article title is unexpected.", secondArticleTitle, actualArticleTitle);
    }
}
