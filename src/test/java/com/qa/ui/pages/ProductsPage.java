package com.qa.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProductsPage {

    public enum SortOption {
        NAME_A_TO_Z("az"),
        NAME_Z_TO_A("za"),
        PRICE_LOW_TO_HIGH("lohi"),
        PRICE_HIGH_TO_LOW("hilo");

        private final String value;

        SortOption(String value) {
            this.value = value;
        }
    }

    private final SelenideElement title = $("[data-test='title']");
    private final SelenideElement sortDropdown = $("[data-test='product-sort-container']");
    private final ElementsCollection products = $$("[data-test='inventory-item']");
    private final ElementsCollection productNames = $$("[data-test='inventory-item-name']");
    private final ElementsCollection productPrices = $$("[data-test='inventory-item-price']");

    public ProductsPage shouldBeOpened() {
        title.shouldHave(exactText("Products"));
        return this;
    }

    public ElementsCollection products() {
        return products;
    }

    public ProductsPage sortBy(SortOption option) {
        sortDropdown.selectOptionByValue(option.value);
        return this;
    }

    public List<String> productNames() {
        return productNames.texts();
    }

    public List<Double> productPrices() {
        return productPrices.texts().stream()
                .map(price -> Double.parseDouble(price.replace("$", "")))
                .toList();
    }

    public ProductsPage addToCart(String productName) {
        products.findBy(text(productName))
                .$("button")
                .click();
        return this;
    }

    public CartPage openCart() {
        $("[data-test='shopping-cart-link']").click();
        return new CartPage();
    }
}