@Suite
Feature: Compra exitosa en SauceDemo

  Background:
    Given el usuario navega al sitio web

  @Login
  Scenario: Usuario inicia sesion con credenciales validas
    When ingresa credenciales validas
    Then la aplicacion deberia mostrar el modulo principal de productos

  @Compra
  Scenario: Usuario completa una compra exitosa
    When ingresa credenciales validas
    Then la aplicacion deberia mostrar el modulo principal de productos
    And agregar productos al carrito de compras
    And da click en el icono del carrito
    Then el sistema deberia listar los productos agregados en el carrito de compras
    When completa todo el registro de la orden
    Then el sistema deberia procesar la compra
