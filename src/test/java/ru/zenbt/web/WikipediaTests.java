package ru.zenbt.web;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import ru.zenbt.pages.WikipediaPage;
import ru.zenbt.utils.WebDriverFactory;

public class WikipediaTests {

    private WebDriver driver;
    private WikipediaPage wikipediaPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = WebDriverFactory.createDriver();
        wikipediaPage = new WikipediaPage(driver);
    }

    @Test
    public void testMainPageLoadAndElementsDisplay() {
        Assert.assertTrue(
                wikipediaPage.isMainPageContentDisplayed(),
                "Контент главной страницы Wikipedia не отображается"
        );
    }

    @Test
    public void testSearchFunctionality() {
        wikipediaPage.openMainPage();
        wikipediaPage.searchFor("Россия");

        Assert.assertEquals(
                wikipediaPage.getFirstHeadingText(),
                "Россия",
                "Результат поиска некорректен"
        );
    }

    @Test
    public void testRandomPageNavigation() {
        wikipediaPage.openMainPage();
        String originalUrl = driver.getCurrentUrl();

        wikipediaPage.clickRandomPageLink();

        Assert.assertNotEquals(
                driver.getCurrentUrl(),
                originalUrl,
                "Переход на случайную страницу не произошёл"
        );
    }

    @Test
    public void testSearchInputInteractivity() {
        wikipediaPage.openMainPage();

        Assert.assertTrue(
                wikipediaPage.isSearchInputDisplayedAndEnabled(),
                "Поле поиска неактивно или не отображается"
        );
    }

    @Test
    public void testUrlChangesAfterSearch() {
        wikipediaPage.openMainPage();
        String mainPageUrl = driver.getCurrentUrl();

        wikipediaPage.searchFor("Москва");

        Assert.assertNotEquals(
                driver.getCurrentUrl(),
                mainPageUrl,
                "URL должен измениться после выполнения поиска"
        );
    }

    @Test
    public void testArticleHeadingIsNotEmpty() {
        wikipediaPage.openMainPage();
        wikipediaPage.searchFor("Санкт-Петербург");

        String heading = wikipediaPage.getFirstHeadingText();

        Assert.assertTrue(
                heading != null && !heading.trim().isEmpty(),
                "Заголовок статьи не должен быть пустым"
        );
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
