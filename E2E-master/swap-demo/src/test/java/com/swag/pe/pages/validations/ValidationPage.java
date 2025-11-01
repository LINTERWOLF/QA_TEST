package com.swag.pe.pages.validations;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class ValidationPage extends PageObject {

    @FindBy(xpath = "//span[@class='title' and text()='Products']")
    protected WebElementFacade lbl_product;

    @FindBy(xpath = "//h3[@data-test='error']")
    protected WebElementFacade lbl_errorMessage;

    @FindBy(xpath = "//h2[@class='complete-header']")
    protected WebElementFacade lbl_order;
}
