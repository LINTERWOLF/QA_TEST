package com.swag.pe.steps.validations;

import com.swag.pe.pages.validations.ValidationPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ValidationStep extends ValidationPage {

    @Step("Validar que se muestre el modulo de productos")
    public boolean titleIsVisible() {
        lbl_product.waitUntilVisible();
        return lbl_product.isVisible();
    }

    @Step("Validar que se muestre un mensaje de error")
    public boolean errorMessageIsDisplayed() {
        lbl_errorMessage.waitUntilVisible();
        return lbl_errorMessage.isVisible();
    }

    @Step("Validar los productos listados en el carrito")
    public boolean productsIsDisplayed() {
        waitForCondition().until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".cart_item"), 0));
        List<WebElementFacade> cartItems = findAll("//div[@class='cart_item']");
        return !cartItems.isEmpty();
    }

    @Step("Validar que el carrito este vacio de productos")
    public boolean shoppingCartIsEmpty() {
        waitForCondition().until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".cart_item"), 0));
        return findAll("//div[@class='cart_item']").isEmpty();
    }

    @Step("Validar finalizacion de la orden")
    public boolean orderTextIsDisplayed() {
        lbl_order.waitUntilVisible();
        return lbl_order.isVisible();
    }
}
