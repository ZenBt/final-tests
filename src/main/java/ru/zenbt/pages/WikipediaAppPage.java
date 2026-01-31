package ru.zenbt.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class WikipediaAppPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By SEARCH_CONTAINER = By.id("org.wikipedia.alpha:id/search_container");
    private final By SEARCH_INPUT_FIELD = By.id("org.wikipedia.alpha:id/search_src_text");
    private final By FIRST_RESULT_TITLE = By.id("org.wikipedia.alpha:id/page_list_item_title");
    private final By ARTICLE_TITLE = By.id("org.wikipedia.alpha:id/view_page_title_text");
    private final By NAVIGATE_UP_BUTTON = AppiumBy.accessibilityId("Navigate up");
    private final By SKIP_BUTTON = By.id("org.wikipedia.alpha:id/fragment_onboarding_skip_button");
    private final By CLOSE_POPUP_BUTTON = By.id("org.wikipedia.alpha:id/closeButton");

    public WikipediaAppPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void skipOnboardingIfPresent() {
        if (isElementPresent(SKIP_BUTTON)) {
            driver.findElement(SKIP_BUTTON).click();
        }
    }

    public void closePopupIfPresent() {
        if (isElementPresent(CLOSE_POPUP_BUTTON)) {
            driver.findElement(CLOSE_POPUP_BUTTON).click();
        }
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSearchContainerDisplayed() {
        skipOnboardingIfPresent();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_CONTAINER)).isDisplayed();
    }

    public void searchForArticle(String query) {
        skipOnboardingIfPresent();

        wait.until(ExpectedConditions.elementToBeClickable(SEARCH_CONTAINER)).click();

        WebElement searchInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_INPUT_FIELD));
        searchInput.sendKeys(query);

        wait.until(ExpectedConditions.elementToBeClickable(FIRST_RESULT_TITLE)).click();

        closePopupIfPresent();
    }

    public String getArticleTitle() {
        closePopupIfPresent();
        return wait.until(ExpectedConditions.visibilityOfElementLocated(ARTICLE_TITLE)).getText();
    }

    public void navigateBack() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(NAVIGATE_UP_BUTTON)).click();
        } catch (Exception e) {
            driver.navigate().back();
        }
    }
}
