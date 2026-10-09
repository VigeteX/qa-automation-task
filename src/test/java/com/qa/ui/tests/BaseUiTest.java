package com.qa.ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.qa.config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.chrome.ChromeOptions;

@Tag("ui")
public abstract class BaseUiTest {

    @BeforeAll
    static void configureBrowser() {
        Configuration.baseUrl = Config.uiBaseUrl();
        Configuration.browser = Config.browser();
        Configuration.headless = Config.headless();
        Configuration.browserSize = Config.browserSize();
        Configuration.timeout = Config.timeoutMs();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        Configuration.browserCapabilities = options;
    }

    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }
}