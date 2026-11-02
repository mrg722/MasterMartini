# Master Martini

Proyecto académico de Desarrollo de Aplicaciones Móviles (DSY1105), basado en el caso de Master Martini Chile SPA.

**Autor:** Martín Reyes  
**Planificación:** [Tablero MasterMartini en Trello](https://trello.com/b/ZhIslBEj/mastermartini)  
**Repositorio:** [GitHub — MasterMartini](https://github.com/mrg722/MasterMartini)

Master Martini es un prototipo Android para explorar recetas chilenas y contenidos de cocina mediante una experiencia móvil. Los datos de usuario y las preferencias se almacenan localmente en el dispositivo; no se utiliza un backend ni autenticación remota.

## Funcionalidades incluidas

### Inicio, registro e intereses

- La aplicación comienza en una pantalla de bienvenida y espera a que la persona pulse **Comenzar**.
- El registro valida nombre, correo, contraseña, confirmación y aceptación de términos.
- Los validadores están separados de los componentes visuales.
- La selección de intereses se guarda en Preferences DataStore.
- La sesión es local y de demostración. La opción «Inicia sesión» activa la sesión guardada y no comprueba credenciales contra un servidor.

### Catálogo y recetas

El catálogo presenta cinco recetas chilenas, con búsqueda por nombre, descripción o ingrediente:

1. Empanadas de pino.
2. Porotos granados con mazamorra.
3. Pastel de choclo.
4. Cazuela de vacuno.
5. Charquicán.

Cada ficha incluye descripción, ingredientes, tiempo, dificultad y preparación paso a paso. Se puede guardar o quitar una receta de favoritas; las favoritas se conservan localmente con DataStore y el catálogo permite filtrarlas.

### Videos asociados a las recetas

**La correspondencia entre los cinco videos principales y las recetas del catálogo fue revisada y verificada por título y contenido.** La duración es un dato distinto: solo se indica como confirmada cuando se pudo comprobar en la fuente consultada.

| Receta | Canal / video | Correspondencia con la receta | Duración |
|---|---|---|---|
| Empanadas de pino | [Javiera Reymont — receta completa](https://www.youtube.com/watch?v=BaqZU8LIClc) | Verificada | 13 min |
| Porotos granados con mazamorra | [Slucook](https://www.youtube.com/watch?v=PHTveSdrBdk) | Verificada | No confirmada |
| Pastel de choclo | [Jon Michelena](https://youtu.be/ZV4WS3AlAS0) | Verificada | No confirmada |
| Cazuela de vacuno | [Magefesa MGF](https://www.youtube.com/watch?v=Bz2A99mpa8A) | Verificada | No confirmada |
| Charquicán | [Silvana Cocina](https://www.youtube.com/watch?v=Bgob5Lje4Js) | Verificada | Menos de 6 min |

Las empanadas incluyen además un clip complementario de [Ricky Cocina sobre la preparación del pino](https://www.youtube.com/watch?v=OTsHlTlPcAE), declarado como 1 minuto. Es un apoyo parcial y no reemplaza el tutorial completo de empanadas.

Los videos no se descargan ni se alojan en el repositorio. La aplicación utiliza sus identificadores públicos, miniaturas de YouTube y enlaces a los canales originales. La reproducción se realiza en un WebView con el reproductor embebido de YouTube; si la inserción no está permitida o falla, queda disponible el botón **Abrir en YouTube**.

La selección de enlaces y sus referencias se documenta también en [recursos/videos-recetas/README.md](recursos/videos-recetas/README.md).

### Cursos

Los cursos agrupan clases construidas a partir de los mismos videos asociados a las recetas. Cada curso tiene una pantalla de detalle; cada clase permite abrir su video y, cuando corresponde, saltar a la ficha completa de la receta. Son rutas de contenido basadas en videos externos, no un sistema de cursos con seguimiento de progreso o evaluaciones propias.

### Perfil y ajustes

- Perfil editable con nombre, correo, fecha de nacimiento y preferencias alimentarias.
- Foto de perfil seleccionable desde galería o capturable con la cámara.
- Resumen de intereses y recetas favoritas.
- Preferencia de apariencia: modo claro, modo oscuro o según el sistema; se guarda en DataStore.
- Opción para cerrar la sesión local conservando los datos guardados.
- Opción para eliminar los datos de cuenta guardados en DataStore.
- Interruptor de notificaciones que guarda la preferencia seleccionada. **El proyecto no implementa envío ni programación de notificaciones reales.**

Al eliminar la cuenta se limpia la referencia de la foto en DataStore, pero el código actual no elimina físicamente el archivo de imagen que ya se haya copiado a la carpeta de fotos privada de la aplicación.

## Limitaciones conocidas del prototipo

- No hay backend, autenticación remota ni verificación de credenciales. El registro y la sesión son locales.
- La opción «Mis intereses» permite abrir la pantalla de selección, pero esta no carga inicialmente las preferencias ya guardadas para mostrarlas preseleccionadas.
- El acceso «Mis recetas guardadas» lleva al catálogo; desde allí se puede seleccionar el filtro «Favoritas».
- El interruptor de notificaciones solo conserva la preferencia. No existen notificaciones programadas ni push.
- Los cursos agrupan videos de terceros y no incluyen seguimiento del avance, cuestionarios ni certificaciones.
- Tres de las cinco duraciones de video no se pudieron confirmar en las fuentes consultadas. La aplicación las identifica como «Duración no verificada» en vez de asignar un tiempo estimado.

## Tecnologías y organización

- **Kotlin** y **Jetpack Compose**.
- **Material 3** para la interfaz y los temas claro/oscuro.
- **Navigation Compose** para la navegación.
- **AndroidX ViewModel** y **StateFlow** para la gestión del estado.
- **Preferences DataStore** para cuenta local, perfil, intereses, favoritas, tema y preferencia de notificaciones.
- **WebView** para el reproductor embebido de YouTube.
- **JUnit 4** para pruebas unitarias de validación.

La organización principal del código es:

- data/model: modelos de receta, video, curso, perfil y tema.
- data/local: persistencia con DataStore.
- data/validation: validaciones desacopladas de la interfaz.
- viewmodel: estado y lógica de registro, perfil, catálogo, intereses y ajustes.
- ui/components: componentes reutilizables.
- ui/theme: paleta, tipografía, formas y esquemas de color.
- ui/screens: pantallas de inicio, registro, catálogo, cursos, recetas, video, perfil y ajustes.
- ui/navigation: rutas y grafo de navegación.
- app/src/test: pruebas unitarias de validación.

## Requisitos de desarrollo

- Android Studio con **Android SDK API 37**.
- **JDK 25** para el Gradle Daemon, de acuerdo con gradle/gradle-daemon-jvm.properties.
- El módulo compila el código Java/Kotlin con compatibilidad Java 17.
- Gradle Wrapper **9.8.2**, Android Gradle Plugin **9.4.1** y Kotlin **2.4.21**.

La combinación de versiones debe comprobarse en el entorno de desarrollo utilizado. La compatibilidad completa publicada por Kotlin para KGP 2.4.21 no incluye expresamente Gradle 9.8.2 ni AGP 9.4.1; esas versiones son posteriores a los máximos de compatibilidad completa documentados, aunque Gradle 9.8.2 y AGP 9.4.1 sí son compatibles entre sí según sus requisitos publicados.

## Ejecutar en Windows

Desde la carpeta raíz del proyecto, con el Android SDK instalado y la ruta local configurada, ejecuta:

    gradlew.bat :app:testDebugUnitTest
    gradlew.bat :app:assembleDebug

El primer comando ejecuta las pruebas unitarias de validación. El segundo genera el APK de depuración en:

    app\build\outputs\apk\debug\app-debug.apk

Las pruebas incluidas comprueban reglas de registro y perfil. No sustituyen pruebas de interfaz, navegación, reproducción de videos ni comprobaciones de cámara y galería en un dispositivo físico.

---

**Estado de verificación de esta documentación:** los cinco enlaces se contrastaron con sus títulos y descripciones para comprobar que correspondan a las recetas indicadas. La compilación y las pruebas de la versión actual de main no pudieron ejecutarse desde el entorno de auditoría utilizado; deben ejecutarse en Android Studio o en un equipo con Gradle, SDK y acceso a los repositorios de dependencias.