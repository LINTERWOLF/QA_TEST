package com.swag.pe.utilities.browser;

import net.serenitybdd.core.Serenity;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class SecurityWarningHandler {

    private static final By DIALOG_ROOT = By.cssSelector("[role='dialog'], div[aria-modal='true']");
    private static final By BUTTONS_WITHIN_DIALOG = By.cssSelector("button, [role='button']");
    private static final List<String> KEYWORDS = Arrays.asList(
            "cambia la contraseña",
            "cambiar la contraseña",
            "proteger tu cuenta",
            "contraseñas comprometidas",
            "contraseña comprometida",
            "password leak",
            "password check",
            "protect your account",
            "compromised password",
            "check passwords"
    );

    private SecurityWarningHandler() {
    }

    public static void dismissIfPresent() {
        WebDriver driver = Serenity.getDriver();
        if (driver == null) {
            return;
        }

        try {
            boolean handled = dismissDialog(driver);
            if (!handled) {
                removeOverlayDialog(driver);
            }
        } catch (org.openqa.selenium.WebDriverException ignored) {
            // Si el navegador ya no está disponible (p. ej. porque se cerró la ventana),
            // no es necesario escalar esta excepción: simplemente ignoramos el popup.
        }
    }

    private static boolean dismissDialog(WebDriver driver) {
        List<WebElement> dialogs = driver.findElements(DIALOG_ROOT);
        for (WebElement dialog : dialogs) {
            String dialogText = dialog.getText();
            if (dialogText == null) {
                continue;
            }
            String normalized = dialogText.toLowerCase(Locale.ROOT);
            boolean matchesKeyword = KEYWORDS.stream().anyMatch(normalized::contains);
            if (!matchesKeyword) {
                continue;
            }

            List<WebElement> buttons = dialog.findElements(BUTTONS_WITHIN_DIALOG);
            if (!buttons.isEmpty()) {
                WebElement button = buttons.get(0);
                clickWithFallback(driver, button);
                new WebDriverWait(driver, Duration.ofSeconds(3))
                        .until(ExpectedConditions.invisibilityOf(dialog));
                return true;
            }

            ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", dialog);
            return true;
        }
        return false;
    }

    private static void clickWithFallback(WebDriver driver, WebElement element) {
        try {
            element.click();
        } catch (Exception clickException) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    private static void removeOverlayDialog(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('[role=\"dialog\"], div[aria-modal=\"true\"]').forEach(e => e.remove());"
        );
    }
}
