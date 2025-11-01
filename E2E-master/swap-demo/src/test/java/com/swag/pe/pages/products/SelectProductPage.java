package com.swag.pe.pages.products;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.support.FindBy;

public class SelectProductPage extends PageObject {

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    protected WebElementFacade shoppingCartIcon;

}
