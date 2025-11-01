package com.swag.pe.pages.shopping;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class ShoppingCartPage extends PageObject {

    @FindBy(xpath = "//button[@id='checkout']")
    protected WebElementFacade checkoutButton;

    @FindBy(id = "first-name")
    protected WebElementFacade txt_firstname;

    @FindBy(id = "last-name")
    protected WebElementFacade txt_lastname;

    @FindBy(id = "postal-code")
    protected WebElementFacade txt_postal;

    @FindBy(xpath = "//input[@id='continue']")
    protected WebElementFacade continueButton;

    @FindBy(xpath = "//button[@id='finish']")
    protected WebElementFacade finishButton;
}
