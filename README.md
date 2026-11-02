# Master Martini

Proyecto académico de Desarrollo de Aplicaciones Móviles (DSY1105), basado en Master Martini Chile SPA.

Autor: Martín Reyes. Planificación: [Trello](https://trello.com/b/ZhIslBEj/mastermartini).

## Qué hace la aplicación

La aplicación parte en una pantalla de inicio que **espera a que la persona pulse "Comenzar"**: nunca entra sola al catálogo. Desde ahí se registra una cuenta, se eligen intereses y se accede al recetario.

El catálogo muestra las recetas **una bajo la otra, en una lista vertical**, con buscador por nombre o ingrediente, filtros (todas / con video / favoritas) y un corazón para guardar cada receta en el dispositivo.

### Videos por receta

Las cinco recetas tienen su video correspondiente, verificado uno a uno en [`recursos/videos-recetas/README.md`](recursos/videos-recetas/README.md):

| Receta | Canal | Duración |
|---|---|---|
| Empanadas de pino | Javiera Reymont | 13 min |
| Porotos granados con mazamorra | Slucook | no verificada |
| Pastel de choclo | Jon Michelena | no verificada |
| Cazuela de vacuno | Magefesa MGF | no verificada |
| Charquicán | Silvana Cocina | menos de 6 min |

La portada de cada tarjeta es la miniatura real del video. Al tocarla se abre un reproductor **dentro de la aplicación** (WebView con el reproductor embebido de YouTube) que además ofrece el botón "Abrir en YouTube" como respaldo, por si el canal no permite la reproducción embebida. Los videos no se descargan ni se almacenan: solo se enlazan, dando el crédito al canal.

### Cursos

Los cursos agrupan clases que se apoyan en esos mismos videos verificados, de modo que no haya enlaces inventados. Cada clase abre su video y enlaza a la receta completa del catálogo.

### Ajustes

- **Apariencia**: modo claro, modo oscuro o seguir la configuración del sistema. La preferencia se guarda y se aplica a toda la aplicación.
- **Notificaciones**: activar o desactivar los avisos.
- **Cuenta**: editar el perfil, cerrar sesión (conserva los datos en el dispositivo) y eliminar la cuenta (borra perfil, intereses y recetas guardadas, con diálogo de confirmación). Si no hay cuenta, se ofrece crearla.

### Perfil

Perfil editable con foto tomada con la cámara o elegida de la galería, resumen de recetas guardadas e intereses, y acceso directo a los ajustes.

## Cómo está construido

Kotlin, Jetpack Compose, Material 3, Navigation Compose, ViewModel y StateFlow, con arquitectura MVVM:

- `data/model` — modelos (receta, video, curso, perfil, tema).
- `data/local/PreferenciasUsuario` — persistencia local con DataStore (cuenta, intereses, favoritas, apariencia, notificaciones).
- `data/validation` — validaciones de formularios, separadas de la interfaz.
- `viewmodel` — `RegistroViewModel`, `PerfilViewModel`, `CatalogoViewModel`, `InteresesViewModel` y `AjustesViewModel`: toda la lógica de estado vive aquí, no en los composables.
- `ui/components` — sistema de diseño reutilizable (botones, campos, tarjetas, barras y filas de ajustes).
- `ui/theme` — paleta, tipografía y formas, con esquemas completos para claro y oscuro.
- `ui/screens` — pantallas; `ui/navigation` — grafo de navegación.

Los formularios validan campo por campo con íconos y mensajes de error, carga y confirmación. Las transiciones entre pantallas, la aparición de las portadas y el cambio de paso en la preparación están animados.

Recursos nativos del dispositivo: **cámara** y **galería** para la foto de perfil, e **internet** para reproducir los videos y cargar sus portadas.

Todos los datos son ficticios y se guardan solo en el dispositivo.

## Cómo ejecutarlo

Abre el proyecto en Android Studio con API 37 y ejecuta el módulo `app`. En Windows también puedes usar:

```bat
gradlew.bat :app:testDebugUnitTest
gradlew.bat :app:assembleDebug
```

El APK de depuración queda en `app\build\outputs\apk\debug\app-debug.apk`.
