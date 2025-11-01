package com.swag.pe.hooks;

import com.swag.pe.utilities.browser.SecurityWarningHandler;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;

/**
 * Hook centralizado para vigilar y cerrar cualquier popup de alerta de contraseñas
 * que Chrome pueda mostrar durante la ejecución de los escenarios.
 */
public class PopupHooks {

    @Before(order = 1)
    public void beforeScenario() {
        SecurityWarningHandler.dismissIfPresent();
    }

    @BeforeStep(order = 1)
    public void beforeEachStep() {
        SecurityWarningHandler.dismissIfPresent();
    }

    @AfterStep
    public void afterEachStep() {
        SecurityWarningHandler.dismissIfPresent();
    }
}
