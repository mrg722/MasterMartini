# Master Martini

Aplicación Android de demostración para explorar recetas y cursos de cocina. Es un proyecto académico de Desarrollo de Aplicaciones Móviles (DSY1105), basado en el caso de Master Martini Chile SPA.

**Autor:** Martín Reyes  
**Tablero de trabajo:** [MasterMartini en Trello](https://trello.com/b/ZhIslBEj/mastermartini)

El proyecto es un prototipo local con contenido de ejemplo. El registro guarda datos en el dispositivo; no existe autenticación contra un servidor ni una cuenta real.

## Qué incluye actualmente

- **Bienvenida y registro:** formulario con nombre, correo, contraseña, confirmación y aceptación de términos. Los campos muestran errores de validación y permiten mostrar u ocultar la contraseña.
- **Intereses:** selección de preferencias que se guardan localmente.
- **Catálogo:** búsqueda por nombre, dos recetas destacadas y cinco recetas simples nuevas; cada tarjeta abre ingredientes, descripción y preparación paso a paso.
- **Recetas:** vista de ingredientes y descripción, seguida de una preparación guiada paso a paso.
- **Cursos:** listado de cursos de ejemplo.
- **Perfil:** muestra el nombre, correo e intereses guardados. Permite editar nombre, correo, fecha de nacimiento y preferencias alimentarias.
- **Respuestas visuales:** transiciones al cambiar de pantalla, animaciones al seleccionar intereses y estados de carga, error y éxito en los formularios.

Los datos y las recetas son de demostración. La lista de cursos no incluye reproducción de clases ni contenidos multimedia.

## Pendiente de implementar o comprobar

- **Cámara y galería:** el manifiesto declara permisos y existe un `FileProvider`, pero aún no está implementado el flujo para tomar o seleccionar una foto ni guardarla en el perfil.
- **Recetas guardadas:** el icono de favorito es visual; todavía no hay una sección funcional de “Mis recetas” ni persistencia de favoritos.
- **Controles del catálogo:** se quitó el botón de menú sin acción asignada. Las categorías son visuales; en la barra inferior, los accesos a Cursos y Perfil están conectados, mientras Inicio y Recetas no abren vistas independientes. El icono de favorito es indicativo y no guarda recetas.
- **Acceso:** la opción “Iniciar sesión” del registro lleva al catálogo, pero no hay un formulario de inicio de sesión ni autenticación real.
- **Pruebas de interfaz:** hace falta recorrer el flujo completo en un teléfono o emulador y comprobar el comportamiento visual y la navegación en distintos tamaños de pantalla.

## Tecnologías y organización

- Kotlin y Jetpack Compose.
- Material 3 para los componentes de interfaz.
- Navigation Compose para el flujo entre pantallas.
- AndroidX ViewModel y StateFlow para gestionar el estado.
- Preferences DataStore para conservar localmente el registro, los intereses y los datos editables del perfil.
- JUnit 4 para pruebas unitarias de validación.

La estructura principal del código está organizada así:

- `ui/screens`: pantallas de bienvenida, registro, intereses, catálogo, cursos, recetas y perfil.
- `ui/components`: campos y componentes compartidos de la interfaz.
- `ui/navigation`: rutas y navegación.
- `viewmodel`: estado y operaciones de las pantallas.
- `data/local`: persistencia local con DataStore.
- `data/model`: modelos de datos.
- `data/validation`: reglas de validación independientes de la interfaz.
- `app/src/test`: pruebas unitarias.

## Requisitos

- Android Studio con el SDK de Android **API 37** instalado.
- JDK **25** para ejecutar Gradle, según la configuración del proyecto.
- Un dispositivo o emulador con Android 8.0 (API 26) o posterior para probar la aplicación.

La configuración de compilación de la aplicación utiliza compatibilidad Java 17. Si Gradle no encuentra el SDK, revisa la ubicación de Android SDK en Android Studio y configura la ruta local en tu equipo; no subas a GitHub un archivo `local.properties` que contenga rutas personales.

## Ejecutar el proyecto en Windows

Abre el proyecto en Android Studio, espera a que termine la sincronización de Gradle y ejecuta el módulo `app`.

También puedes usar el CMD desde la carpeta raíz del repositorio:

```bat
gradlew.bat :app:testDebugUnitTest
gradlew.bat :app:assembleDebug
```

El primer comando ejecuta las pruebas unitarias. El segundo genera el APK de depuración en:

```text
app\build\outputs\apk\debug\app-debug.apk
```

## Alcance de las pruebas

Las recetas nuevas se basan en investigación de preparaciones sencillas de queque, pan amasado, muffins, brownie y galletas. Las pruebas incluidas en `ValidacionFormulariosTest.kt` comprueban reglas del registro y del perfil, incluyendo correo, contraseña, términos y fechas. No sustituyen las pruebas de navegación, de interfaz ni las comprobaciones de cámara y galería en un dispositivo.

El estado del proyecto descrito aquí distingue las funciones que ya tienen implementación en el código de las que todavía requieren desarrollo o una comprobación manual.
