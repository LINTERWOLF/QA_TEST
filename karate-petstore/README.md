---------------------------------------------------------------------------
Automatizacion de pruebas con Karate Framework
---------------------------------------------------------------------------

1. Requisitos
   - Windows 10 o superior
   - Java 17 (JDK disponible en PATH)
   - Apache Maven 3.9.x configurado en PATH

2. Ejecucion de pruebas
   - `mvn clean test` descarga dependencias y ejecuta todos los escenarios.
   - Alternativamente ejecuta `com.petstore.TestRunner` desde tu IDE.

3. Alcance de los escenarios
   - Crear un usuario en `https://petstore.swagger.io/v2/user`.
   - Consultar el usuario recien creado y validar los campos iniciales.
   - Actualizar nombre y correo, comprobando que los cambios se reflejen.
   - Eliminar al usuario y verificar que el endpoint responda 404 tras la baja.
   - Cada corrida genera credenciales unicas para evitar conflictos con ejecuciones previas.

4. Reportes
   - Karate publica un resumen en `target/karate-reports/karate-summary.html`.
   - El detalle paso a paso del feature se encuentra en `target/karate-reports/TestUser.html`.
