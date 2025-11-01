
Feature: Gestionar ciclo de vida de un usuario en Petstore

  Background:
    * url 'https://petstore.swagger.io/v2'
    * configure retry = { count: 5, interval: 500 }
    * def userData = callonce read('classpath:user-data.js')
    * def username = userData.username
    * def userId = userData.userId
    * def createPayload =
    """
    {
      "id": #(userId),
      "username": "#(username)",
      "firstName": "Carlos",
      "lastName": "Test",
      "email": "carlos@test.com",
      "password": "pwd123",
      "phone": "1234567890",
      "userStatus": 1
    }
    """
    * def updatePayload =
    """
    {
      "id": #(userId),
      "username": "#(username)",
      "firstName": "Juan",
      "lastName": "Test",
      "email": "juan@test.com",
      "password": "pwd123",
      "phone": "1234567890",
      "userStatus": 1
    }
    """

  Scenario: Crear un nuevo usuario
    Given path 'user'
    And request createPayload
    When method post
    Then status 200
    And match response.code == 200
    And match response.message == (userId + '')
    And print response

  Scenario: Consultar el usuario creado
    Given path 'user', username
    And retry until responseStatus == 200
    When method get
    Then status 200
    And match response.username == username
    And match response.firstName == 'Carlos'
    And match response.email == 'carlos@test.com'
    And print response

  Scenario: Actualizar nombre y correo del usuario
    Given path 'user', username
    And request updatePayload
    When method put
    Then status 200
    And match response.message == (userId + '')
    And print response

  Scenario: Consultar el usuario actualizado
    Given path 'user', username
    And retry until response.firstName == 'Juan'
    When method get
    Then status 200
    And match response.username == username
    And match response.firstName == 'Juan'
    And match response.email == 'juan@test.com'
    And print response

  Scenario: Eliminar al usuario y validar la baja
    Given path 'user', username
    And retry until responseStatus == 200
    When method delete
    Then status 200
    And match response.message == username
    And print response

  Scenario: Consultar el usuario eliminado
    Given path 'user', username
    And retry until responseStatus == 404
    When method get
    Then status 200
    And match response.message == 'User not found'
    And print response
