# E2E Swap Demo

Suite de pruebas end-to-end que automatiza el flujo completo de compra en [https://www.saucedemo.com](https://www.saucedemo.com) usando Serenity BDD + Cucumber sobre Google Chrome.

## Stack y versionamiento de referencia
- Java JDK 17.0.12
- Apache Maven 3.9.4
- Serenity BDD 3.9.0
- Selenium 4.10.0 (transitivo)
- WebDriverManager 5.9.2
- Google Chrome 142.0.7444.59

> Si usas otra combinacion, asegurate de mantener la compatibilidad entre Chrome y ChromeDriver. Serenity descargara el driver correcto de forma automatica (`webdriver.autodownload=true`). 

## Estructura principal
- `swap-demo/pom.xml`: dependencias y plugins Maven.
- `swap-demo/src/test/resources/features/purchase-success.feature`: escenarios Gherkin (@Login y @Compra).
- `swap-demo/src/test/java/com/swag/pe/...`: definiciones de steps, paginas, hooks y utilidades.
- `conclusiones.txt`: conclusiones del ejercicio.

## Preparacion del entorno
1. Instala Java 17 y Maven 3.9 o superior. Verifica con `java -version` y `mvn -version`.
2. Asegura que Google Chrome este instalado y actualizado (idealmente 142.0.7444.59).
3. Clona el repositorio o copia el contenido en tu maquina Windows.
4. (Opcional) Configura la variable `JAVA_HOME` apuntando al JDK 17.

## Ejecucion de pruebas
En la raiz del repositorio ejecuta:

```powershell
mvn -f swap-demo/pom.xml test
```

Esto dispara los dos escenarios encadenados del feature `purchase-success.feature` dentro del runner `com.swag.pe.Runner`. Serenity reinicia el navegador entre escenarios y abre Chrome en modo incognito con preferencias de contrasenas deshabilitadas para minimizar popups.

### Ejecucion selectiva por tags
Si necesitas correr un solo escenario:

```powershell
mvn -f swap-demo/pom.xml test "-Dcucumber.filter.tags=@Login"
mvn -f swap-demo/pom.xml test "-Dcucumber.filter.tags=@Compra"
```

## Intervencion manual ante el popup de seguridad
Chrome puede seguir mostrando el dialogo de seguridad "Cambiar la contrasena" porque las credenciales `standard_user/secret_sauce` son publicas. El proyecto incluye hooks (`com.swag.pe.hooks.PopupHooks`) y un manejador (`SecurityWarningHandler`) que intentan cerrarlo automaticamente, pero **si el popup reaparece** debe cerrarse manualmente (boton "Entendido"/"Got it" o cerrar en la X) para que la ejecucion continue sin fallos. Esta es la unica accion manual esperada.

## Reportes
Al finalizar la corrida, revisa el reporte de Serenity en:

```
swap-demo/target/site/serenity/index.html
```

Abre el archivo en un navegador para visualizar evidencias, pasos y resultados.

## Solucion de problemas comunes
- **ChromeDriver mismatch**: si Chrome recibio una actualizacion mayor, borra `C:\Users\<usuario>\.cache\selenium` o ejecuta nuevamente el comando; WebDriverManager descargara la nueva version compatible.
- **Popup persiste**: valida que tengas permisos para cerrar el dialogo manualmente; sin esa accion ninguna automatizacion (ni en modo incognito) puede avanzar porque Chrome bloquea la pagina.
- **Locales o codificaciones**: el proyecto usa ASCII; evita caracteres especiales al modificar archivos para mantener consistencia.

## Conclusiones
Revisa `conclusiones.txt` para conocer el resumen del trabajo realizado, lecciones aprendidas y consideraciones finales al replicar las pruebas.
