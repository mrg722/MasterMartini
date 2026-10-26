# Master Martini

Proyecto académico de Desarrollo de Aplicaciones Móviles (DSY1105), basado en Master Martini Chile SPA.

Autor: Martín Reyes. Planificación: [Trello](https://trello.com/b/ZhIslBEj/mastermartini).

La aplicación permite registrarse, elegir intereses y explorar cinco recetas chilenas: empanadas de pino, porotos granados con mazamorra, pastel de choclo, cazuela de vacuno y charquicán. Cada receta tiene ingredientes y preparación paso a paso.

También incluye cursos, perfil editable y acceso a cámara o galería para la foto de perfil. Los datos son ficticios y se guardan solo en el dispositivo mediante DataStore.

Se utiliza Kotlin, Jetpack Compose, Material 3, Navigation, ViewModel y StateFlow. Los formularios muestran validaciones por campo y mensajes de carga, error o confirmación.

Para ejecutar, abre el proyecto en Android Studio con API 37 y ejecuta el módulo `app`. En Windows también puedes usar:

```bat
gradlew.bat :app:testDebugUnitTest
gradlew.bat :app:assembleDebug
```

El APK de depuración queda en `app\build\outputs\apk\debug\app-debug.apk`.
