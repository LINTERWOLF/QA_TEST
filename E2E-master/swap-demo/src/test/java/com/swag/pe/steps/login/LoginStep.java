package com.swag.pe.steps.login;

import com.swag.pe.pages.login.LoginPage;
import net.thucydides.core.annotations.Step;

public class LoginStep extends LoginPage {

    @Step("Ingresar nombre de usuario")
    public void typeUsername(String username) {
        txt_username.sendKeys(username);
    }

    @Step("Ingresar contrasena")
    public void typePassword(String password) {
        txt_password.sendKeys(password);
    }

    @Step("Hacer clic en el boton de inicio de sesion")
    public void clickLogin() {
        btn_login.click();
    }
}

