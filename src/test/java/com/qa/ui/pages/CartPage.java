package com.qa.ui.pages;

import com.codeborne.selenide.ElementsCollection;

import java.util.List;

import static com.codeborne.selenide.Selenide.$$;

public class CartPage {

    private final ElementsCollection cartItemNames = $$("[data-test='inventory-item-name']");

    public List<String> itemNames() {
        return cartItemNames.texts();
    }
}