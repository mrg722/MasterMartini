# Master Martini

Proyecto móvil para la asignatura Desarrollo de Aplicaciones Móviles (DSY1105), basado en el caso de Master Martini Chile SPA.

Integrante: Martín Reyes. Organizo las tareas en [Trello](https://trello.com/b/ZhIslBEj/mastermartini).

La idea es reunir recetas y cursos para el rubro gastronómico en una aplicación fácil de usar. Es un prototipo académico: se trabaja con datos ficticios y el registro es local, sin autenticación en un servidor.

## Qué se puede hacer

- Completar el registro con validación de nombre, correo, contraseña y confirmación.
- Elegir intereses y conservarlos al cerrar la aplicación.
- Buscar recetas y consultar su detalle y preparación. Cursos tiene un listado inicial.
- Ver el perfil y editar nombre, correo, fecha de nacimiento y preferencias alimentarias como texto libre.
- Recibir mensajes por campo y confirmaciones animadas al guardar. Los cambios del perfil se conservan en el dispositivo.

## Cómo ejecutarlo

Abre la carpeta del proyecto en Android Studio, sincroniza Gradle y ejecuta el módulo `app`. El proyecto usa SDK 37 para compilar y admite dispositivos desde Android 8 (API 26). Gradle solicita JDK 25; revisa que la ruta de Java exista. Para ahorrar memoria, conviene probar con un teléfono conectado por USB.

Está hecho con Kotlin, Jetpack Compose, Material 3, Navigation y DataStore. La interfaz está en `ui`, el estado y las acciones en `viewmodel`, y los datos y validaciones en `data`, siguiendo MVVM.

Las pruebas de validación se ejecutan con `gradlew.bat :app:testDebugUnitTest`; el APK de prueba se genera con `gradlew.bat :app:assembleDebug`.

Todavía quedan pendientes cámara y galería, reproducción de cursos, recetas guardadas y el ajuste visual completo de las pantallas. Las animaciones y los formularios deben revisarse también en un dispositivo.
