package ru.zenbt.mobile;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import ru.zenbt.pages.WikipediaAppPage;
import ru.zenbt.utils.WebDriverFactory;

public class WikipediaMobileTests {

    private AndroidDriver driver;
    private WikipediaAppPage appPage;

    @BeforeMethod
    public void setup() throws Exception {
        driver = WebDriverFactory.createAndroidDriver();
        appPage = new WikipediaAppPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testMainScreenSearchIsDisplayed() {
        Assert.assertTrue(
                appPage.isSearchContainerDisplayed(),
                "Поле поиска должно отображаться на главном экране"
        );
    }

    @Test
    public void testSearchAndOpenArticle() {
        appPage.searchForArticle("Appium");

        String title = appPage.getArticleTitle();
        Assert.assertTrue(
                title.toLowerCase().contains("appium"),
                "Заголовок статьи должен содержать 'Appium'"
        );
    }

    @Test
    public void testSearchAndNavigateBack() {
        appPage.searchForArticle("Selenium");
        Assert.assertFalse(appPage.getArticleTitle().isEmpty());

        appPage.navigateBack();

        Assert.assertTrue(
                appPage.isSearchContainerDisplayed(),
                "После возврата должно быть видно поле поиска"
        );
    }

    @Test
    public void testRepeatSearchWithoutRestart() {
        appPage.searchForArticle("Java");
        String firstTitle = appPage.getArticleTitle();
        Assert.assertTrue(firstTitle.contains("Java"));

        appPage.navigateBack();

        appPage.searchForArticle("Python");
        String secondTitle = appPage.getArticleTitle();

        Assert.assertTrue(
                secondTitle.contains("Python"),
                "Повторный поиск должен работать без перезапуска приложения"
        );
    }

    @Test
    public void testExactArticleTitle() {
        appPage.searchForArticle("Android");

        String title = appPage.getArticleTitle();
        Assert.assertEquals(
                title,
                "Android",
                "Заголовок статьи должен точно совпадать с ожидаемым"
        );
    }
}
