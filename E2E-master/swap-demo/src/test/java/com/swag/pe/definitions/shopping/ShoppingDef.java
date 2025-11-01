package com.swag.pe.definitions.shopping;

import com.swag.pe.steps.products.SelectProductStep;
import com.swag.pe.steps.shopping.ShoppingCartStep;
import com.swag.pe.steps.validations.ValidationStep;
import com.swag.pe.utilities.JSONReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

public class ShoppingDef {

    @Steps(shared = true)
    SelectProductStep selectProduct;

    @Steps(shared = true)
    ValidationStep validate;

    @Steps(shared = true)
    ShoppingCartStep shoppingCart;

    private final String firstName = JSONReader.getValue("firstname");
    private final String lastName = JSONReader.getValue("lastname");
    private final String postalCode = JSONReader.getValue("postalcode");
    private final int productsToAdd = Integer.parseInt(JSONReader.getValue("productsToAdd"));

    @And("agregar productos al carrito de compras")
    public void userAddProducts() {
        selectProduct.addProducts(productsToAdd);
    }

    @And("da click en el icono del carrito")
    public void userClickShoppingCartIcon() {
        selectProduct.clickShoppingCartIcon();
    }

    @Then("el sistema deberia listar los productos agregados en el carrito de compras")
    public void systemListProducts() {
        Assert.assertTrue(validate.productsIsDisplayed());
    }

    @When("completa todo el registro de la orden")
    public void userCompleteOrder() {
        ingresarDatosComprador();
        shoppingCart.clickContinueButton();
        shoppingCart.clickFinish();
    }

    @Then("el sistema deberia procesar la compra")
    public void systemProcessShoppingCart() {
        Assert.assertTrue(validate.orderTextIsDisplayed());
    }

    @Then("el sistema deberia mostrar la cesta sin productos")
    public void systemDisplayEmptyCart() {
        Assert.assertTrue(validate.shoppingCartIsEmpty());
    }

    private void ingresarDatosComprador() {
        shoppingCart.clickCheckoutButton();
        shoppingCart.typeFirstName(firstName);
        shoppingCart.typeLastName(lastName);
        shoppingCart.typePostalCode(postalCode);
    }
}
