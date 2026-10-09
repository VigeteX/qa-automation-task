package com.qa.ui.tests;

import com.qa.config.Config;
import com.qa.ui.pages.LoginPage;
import com.qa.ui.pages.ProductsPage;
import com.qa.ui.pages.ProductsPage.SortOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductsTest extends BaseUiTest {

    private ProductsPage productsPage;

    @BeforeEach
    void login() {
        productsPage = new LoginPage()
                .open()
                .loginAs(Config.username(), Config.password())
                .shouldBeOpened();
    }

    @Test
    void products_can_be_sorted_by_price_low_to_high() {
        productsPage.sortBy(SortOption.PRICE_LOW_TO_HIGH);

        List<Double> actual = productsPage.productPrices();
        List<Double> expected = actual.stream().sorted(Comparator.naturalOrder()).toList();

        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void added_product_appears_in_cart() {
        String product = "Sauce Labs Backpack";

        List<String> cartItems = productsPage
                .addToCart(product)
                .openCart()
                .itemNames();

        assertThat(cartItems).containsExactly(product);
    }
}