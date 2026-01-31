package ru.zenbt.utils;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class WebDriverFactory {

    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    public static AndroidDriver createAndroidDriver() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", "org.wikipedia.alpha");
        caps.setCapability("appActivity", "org.wikipedia.main.MainActivity");
        caps.setCapability("noReset", false);

        return new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
    }
}
