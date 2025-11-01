package com.swag.pe.steps.products;

import com.swag.pe.pages.products.SelectProductPage;
import com.swag.pe.utilities.browser.SecurityWarningHandler;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class SelectProductStep extends SelectProductPage {

    @Step("Agregar productos al carrito")
    public void addProducts(int productsCount) {
        waitForCondition()
                .withTimeout(Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".inventory_list")));

        SecurityWarningHandler.dismissIfPresent();
        waitForCondition()
                .withTimeout(Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("button.btn_inventory"), 0));

        int itemsToAdd = Math.max(0, productsCount);
        int added = 0;
        while (added < itemsToAdd) {
            WebElementFacade button = firstAvailableAddButton();
            addSingleProductToCart(button, added + 1);
            added++;
        }

        if (added < itemsToAdd) {
            throw new IllegalStateException("No fue posible agregar la cantidad requerida de productos al carrito.");
        }
    }

    @Step("Click en el icono del carrito de compras")
    public void clickShoppingCartIcon() {
        shoppingCartIcon.withTimeoutOf(Duration.ofSeconds(10)).waitUntilClickable();
        try {
            shoppingCartIcon.click();
        } catch (Exception ex) {
            evaluateJavascript("arguments[0].click();", shoppingCartIcon);
        }
        try {
            waitForCondition()
                    .withTimeout(Duration.ofSeconds(10))
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.urlContains("cart.html"));
        } catch (Exception timeout) {
            getDriver().navigate().to("https://www.saucedemo.com/cart.html");
            waitForCondition()
                    .ignoring(StaleElementReferenceException.class)
                    .until(ExpectedConditions.urlContains("cart.html"));
        }
    }

    private void addSingleProductToCart(WebElementFacade productButton, int expectedCount) {
        SecurityWarningHandler.dismissIfPresent();
        String addDataTest = productButton.getAttribute("data-test");
        if (addDataTest == null || !addDataTest.startsWith("add-to-cart-")) {
            throw new IllegalStateException("No se pudo determinar el boton de agregar para el producto.");
        }

        By addLocator = By.cssSelector(String.format("button[data-test='%s']", addDataTest));
        By removeLocator = By.cssSelector(String.format("button[data-test='%s']", addDataTest.replaceFirst("add-to-cart-", "remove-")));
        By anyRemoveLocator = By.cssSelector("button[data-test^='remove-']");

        boolean added = false;
        for (int attempt = 0; attempt < 3 && !added; attempt++) {
            SecurityWarningHandler.dismissIfPresent();
            WebElementFacade buttonToClick = find(addLocator);
            buttonToClick.waitUntilEnabled();
            buttonToClick.waitUntilVisible();
            evaluateJavascript("arguments[0].scrollIntoView({block: 'center'});", buttonToClick);
            try {
                buttonToClick.click();
            } catch (Exception ex) {
                evaluateJavascript("arguments[0].click();", buttonToClick);
            }
            try {
                waitForCondition()
                        .withTimeout(Duration.ofSeconds(12))
                        .pollingEvery(Duration.ofMillis(300))
                        .ignoring(StaleElementReferenceException.class)
                        .until(driver -> {
                            SecurityWarningHandler.dismissIfPresent();
                            boolean addButtonGone = driver.findElements(addLocator).isEmpty();
                            boolean removePresentForProduct = !driver.findElements(removeLocator).isEmpty();
                            if (removePresentForProduct) {
                                return true;
                            }
                            boolean removeCountReached = driver.findElements(anyRemoveLocator).size() >= expectedCount;
                            if (removeCountReached) {
                                return true;
                            }
                            List<WebElementFacade> badges = findAll(By.cssSelector(".shopping_cart_badge"));
                            return badges.stream()
                                    .map(WebElementFacade::getText)
                                    .map(String::trim)
                                    .anyMatch(text -> text.equals(String.valueOf(expectedCount)));
                        });
                added = true;
            } catch (Exception retry) {
                if (attempt == 2) {
                    throw retry;
                }
            }
        }
    }

    private WebElementFacade firstAvailableAddButton() {
        WebElementFacade available = waitForCondition()
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(driver -> {
                    List<WebElementFacade> buttons = findAll(By.cssSelector("button[data-test^='add-to-cart']"));
                    if (buttons.isEmpty()) {
                        return null;
                    }
                    return buttons.stream()
                            .filter(WebElementFacade::isVisible)
                            .filter(WebElementFacade::isEnabled)
                            .findFirst()
                            .orElse(null);
                });
        if (available == null) {
            throw new IllegalStateException("No hay botones disponibles para agregar al carrito.");
        }
        return available;
    }
}
