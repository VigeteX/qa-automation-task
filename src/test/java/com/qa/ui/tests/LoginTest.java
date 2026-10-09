package com.qa.ui.tests;

import com.qa.config.Config;
import com.qa.ui.pages.LoginPage;
import com.qa.ui.pages.ProductsPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

class LoginTest extends BaseUiTest {

    @Test
    void user_can_login_with_valid_credentials() {
        ProductsPage productsPage = new LoginPage()
                .open()
                .loginAs(Config.username(), Config.password());

        webdriver().shouldHave(urlContaining("/inventory.html"));
        productsPage.shouldBeOpened();
        productsPage.products().shouldHave(sizeGreaterThan(0));
    }
}