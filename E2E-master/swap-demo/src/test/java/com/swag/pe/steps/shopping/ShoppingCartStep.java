package com.swag.pe.steps.shopping;

import com.swag.pe.pages.shopping.ShoppingCartPage;
import com.swag.pe.utilities.browser.SecurityWarningHandler;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ShoppingCartStep extends ShoppingCartPage {

    @Step("Click en el boton Checkout")
    public void clickCheckoutButton() {
        waitForCondition()
                .withTimeout(Duration.ofSeconds(10))
                .until(driver -> getDriver().getCurrentUrl().contains("cart.html"));
        SecurityWarningHandler.dismissIfPresent();
        checkoutButton.withTimeoutOf(Duration.ofSeconds(10)).waitUntilClickable().click();
        waitForCondition()
                .withTimeout(Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(txt_firstname));
    }

    @Step("Ingresar nombre")
    public void typeFirstName(String firstName) {
        txt_firstname.withTimeoutOf(Duration.ofSeconds(10)).waitUntilVisible();
        SecurityWarningHandler.dismissIfPresent();
        txt_firstname.waitUntilEnabled().type(firstName);
    }

    @Step("Ingresar apellido")
    public void typeLastName(String lastName) {
        txt_lastname.withTimeoutOf(Duration.ofSeconds(10)).waitUntilVisible();
        txt_lastname.waitUntilEnabled().type(lastName);
    }

    @Step("Ingresar codigo postal")
    public void typePostalCode(String postalCode) {
        txt_postal.withTimeoutOf(Duration.ofSeconds(10)).waitUntilVisible();
        txt_postal.waitUntilEnabled().type(postalCode);
    }

    @Step("Click en continuar")
    public void clickContinueButton() {
        continueButton.withTimeoutOf(Duration.ofSeconds(10)).waitUntilClickable().click();
    }

    @Step("Click en finalizar")
    public void clickFinish() {
        finishButton.withTimeoutOf(Duration.ofSeconds(10)).waitUntilClickable().click();
    }
}
