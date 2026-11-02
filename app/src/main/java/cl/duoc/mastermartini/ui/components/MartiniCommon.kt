package cl.duoc.mastermartini.ui.components

import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

/** Secciones de la barra inferior, compartidas por las pantallas principales. */
enum class SeccionPrincipal(val etiqueta: String, val icono: ImageVector) {
    RECETAS("Recetas", Icons.Filled.Restaurant),
    CURSOS("Cursos", Icons.AutoMirrored.Filled.MenuBook),
    PERFIL("Perfil", Icons.Filled.Person),
    AJUSTES("Ajustes", Icons.Filled.Settings)
}

/**
 * Barra inferior única para todas las pantallas principales, de modo que la
 * navegación se vea y se comporte igual en cualquier parte de la app.
 */
@Composable
fun MartiniBottomBar(
    seccionActual: SeccionPrincipal,
    alNavegar: (SeccionPrincipal) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 3.dp
    ) {
        SeccionPrincipal.entries.forEach { seccion ->
            val seleccionada = seccion == seccionActual
            NavigationBarItem(
                selected = seleccionada,
                onClick = { if (!seleccionada) alNavegar(seccion) },
                icon = { Icon(seccion.icono, contentDescription = null) },
                label = {
                    Text(
                        text = seccion.etiqueta,
                        fontWeight = if (seleccionada) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )
        }
    }
}

/** Barra superior con título, subtítulo opcional y botón de volver. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MartiniTopBar(
    titulo: String,
    modifier: Modifier = Modifier,
    subtitulo: String? = null,
    alVolver: (() -> Unit)? = null,
    acciones: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!subtitulo.isNullOrBlank()) {
                    Text(
                        text = subtitulo,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        navigationIcon = {
            if (alVolver != null) {
                IconButton(onClick = alVolver) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        },
        actions = acciones,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

/** Encabezado de sección con acción opcional a la derecha. */
@Composable
fun MartiniTituloSeccion(
    titulo: String,
    modifier: Modifier = Modifier,
    detalle: String? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (!detalle.isNullOrBlank()) {
            Text(
                text = detalle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Etiqueta compacta con ícono, para metadatos como el tiempo o la dificultad. */
@Composable
fun MartiniEtiqueta(
    texto: String,
    icono: ImageVector,
    modifier: Modifier = Modifier,
    colorFondo: Color = MaterialTheme.colorScheme.surfaceVariant,
    colorContenido: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = colorFondo,
        contentColor = colorContenido
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(icono, contentDescription = null, modifier = Modifier.size(15.dp))
            Text(
                text = texto,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1
            )
        }
    }
}

/**
 * Portada de un contenido. Intenta descargar la miniatura real del video y,
 * mientras tanto o si falla, muestra un degradado con un ícono para que la
 * tarjeta nunca se vea vacía.
 */
@Composable
fun MartiniPortada(
    urlMiniatura: String?,
    descripcion: String,
    modifier: Modifier = Modifier,
    mostrarBotonPlay: Boolean = false,
    icono: ImageVector = Icons.Filled.RestaurantMenu
) {
    val imagen = recordarMiniatura(urlMiniatura)
    val colorInicio = MaterialTheme.colorScheme.primaryContainer
    val colorFin = MaterialTheme.colorScheme.secondaryContainer
    val colorIcono = MaterialTheme.colorScheme.onPrimaryContainer
    val opacidad by animateFloatAsState(
        targetValue = if (imagen != null) 1f else 0f,
        animationSpec = tween(durationMillis = 320),
        label = "aparicionPortada"
    )

    Box(
        modifier = modifier
            .background(Brush.linearGradient(listOf(colorInicio, colorFin)))
            .semantics { contentDescription = descripcion },
        contentAlignment = Alignment.Center
    ) {
        if (imagen == null) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = colorIcono.copy(alpha = 0.55f),
                modifier = Modifier.size(44.dp)
            )
        } else {
            Image(
                bitmap = imagen,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alpha = opacidad,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.35f))
                        )
                    )
            )
        }

        if (mostrarBotonPlay) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

/** Tarjeta de receta del catálogo: ocupa el ancho completo y se apila en vertical. */
@Composable
fun MartiniTarjetaReceta(
    titulo: String,
    descripcion: String,
    minutos: Int,
    dificultad: String,
    urlMiniatura: String?,
    tieneVideo: Boolean,
    esFavorita: Boolean,
    alAbrir: () -> Unit,
    alAlternarFavorita: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = alAbrir,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box {
                MartiniPortada(
                    urlMiniatura = urlMiniatura,
                    descripcion = "Portada de $titulo",
                    mostrarBotonPlay = tieneVideo,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                )
                if (tieneVideo) {
                    MartiniEtiqueta(
                        texto = "Con video",
                        icono = Icons.Filled.PlayArrow,
                        colorFondo = MaterialTheme.colorScheme.primary,
                        colorContenido = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                    )
                }
                IconButton(
                    onClick = alAlternarFavorita,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
                ) {
                    Icon(
                        imageVector = if (esFavorita) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (esFavorita) {
                            "Quitar $titulo de favoritas"
                        } else {
                            "Guardar $titulo en favoritas"
                        },
                        tint = if (esFavorita) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MartiniEtiqueta(texto = "$minutos min", icono = Icons.Filled.AccessTime)
                    MartiniEtiqueta(texto = dificultad, icono = Icons.Filled.Restaurant)
                }
            }
        }
    }
}

/** Tarjeta de un curso en el listado de cursos. */
@Composable
fun MartiniTarjetaCurso(
    titulo: String,
    descripcion: String,
    resumen: String,
    urlMiniatura: String?,
    alAbrir: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = alAbrir,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            MartiniPortada(
                urlMiniatura = urlMiniatura,
                descripcion = "Portada del curso $titulo",
                icono = Icons.AutoMirrored.Filled.MenuBook,
                modifier = Modifier
                    .size(width = 104.dp, height = 104.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                MartiniEtiqueta(texto = resumen, icono = Icons.AutoMirrored.Filled.MenuBook)
            }
        }
    }
}

/**
 * Tarjeta de video: portada con botón de reproducción, título, canal y
 * duración. Es la pieza que aparece dentro de cada receta y de cada clase.
 */
@Composable
fun MartiniTarjetaVideo(
    titulo: String,
    canal: String,
    duracion: String,
    urlMiniatura: String?,
    alReproducir: () -> Unit,
    modifier: Modifier = Modifier,
    etiquetaSuperior: String? = null
) {
    Card(
        onClick = alReproducir,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            MartiniPortada(
                urlMiniatura = urlMiniatura,
                descripcion = "Portada del video $titulo",
                mostrarBotonPlay = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                if (!etiquetaSuperior.isNullOrBlank()) {
                    Text(
                        text = etiquetaSuperior.uppercase(),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "$canal · $duracion",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/** Fila de ajuste: ícono, título, descripción y un control opcional a la derecha. */
@Composable
fun MartiniFilaAjuste(
    titulo: String,
    icono: ImageVector,
    modifier: Modifier = Modifier,
    descripcion: String? = null,
    destructiva: Boolean = false,
    habilitada: Boolean = true,
    alPulsar: (() -> Unit)? = null,
    control: @Composable (() -> Unit)? = null
) {
    val colorPrincipal = when {
        destructiva -> MaterialTheme.colorScheme.error
        !habilitada -> MaterialTheme.colorScheme.onSurfaceVariant
        else -> MaterialTheme.colorScheme.onSurface
    }
    val modificadorFila = if (alPulsar != null && habilitada) {
        modifier
            .fillMaxWidth()
            .clickable(onClick = alPulsar)
    } else {
        modifier.fillMaxWidth()
    }

    Row(
        modifier = modificadorFila.padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (destructiva) {
                        MaterialTheme.colorScheme.errorContainer
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = if (destructiva) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.onSecondaryContainer
                },
                modifier = Modifier.size(20.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = colorPrincipal
            )
            if (!descripcion.isNullOrBlank()) {
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        control?.invoke()
    }
}

/** Contenedor con fondo de tarjeta para agrupar filas de ajustes. */
@Composable
fun MartiniGrupoAjustes(
    modifier: Modifier = Modifier,
    titulo: String? = null,
    contenido: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (!titulo.isNullOrBlank()) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(vertical = 4.dp)) { contenido() }
        }
    }
}

/** Mensaje para listas vacías, en vez de dejar la pantalla en blanco. */
@Composable
fun MartiniEstadoVacio(
    titulo: String,
    mensaje: String,
    icono: ImageVector,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(34.dp)
            )
        }
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** Aviso breve que aparece y desaparece con animación. */
@Composable
fun MartiniAviso(
    mensaje: String?,
    modifier: Modifier = Modifier,
    esError: Boolean = false
) {
    AnimatedVisibility(
        visible = mensaje != null,
        enter = fadeIn(animationSpec = tween(180)),
        exit = fadeOut(animationSpec = tween(140)),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            color = if (esError) {
                MaterialTheme.colorScheme.errorContainer
            } else {
                MaterialTheme.colorScheme.secondaryContainer
            }
        ) {
            Text(
                text = mensaje.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = if (esError) {
                    MaterialTheme.colorScheme.onErrorContainer
                } else {
                    MaterialTheme.colorScheme.onSecondaryContainer
                },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Carga de miniaturas
// ---------------------------------------------------------------------------

/**
 * Caché en memoria de las miniaturas ya descargadas. Evita volver a pedir la
 * misma imagen cada vez que la lista se recompone o se vuelve a desplazar.
 */
private val cacheMiniaturas = mutableMapOf<String, ImageBitmap>()

/**
 * Descarga (una sola vez) la miniatura indicada y la entrega como estado de
 * Compose. Devuelve `null` mientras carga o si no se pudo obtener, para que
 * quien la use muestre su alternativa.
 */
@Composable
private fun recordarMiniatura(url: String?): ImageBitmap? {
    if (url.isNullOrBlank()) return null
    var imagen by remember(url) { mutableStateOf(cacheMiniaturas[url]) }

    LaunchedEffect(url) {
        if (imagen != null) return@LaunchedEffect
        val descargada = descargarImagen(url)
        if (descargada != null) {
            cacheMiniaturas[url] = descargada
            imagen = descargada
        }
    }
    return imagen
}

private suspend fun descargarImagen(url: String): ImageBitmap? = withContext(Dispatchers.IO) {
    var conexion: HttpURLConnection? = null
    try {
        conexion = (URL(url).openConnection() as HttpURLConnection).apply {
            connectTimeout = 8000
            readTimeout = 8000
            instanceFollowRedirects = true
            requestMethod = "GET"
        }
        if (conexion.responseCode !in 200..299) return@withContext null
        conexion.inputStream.use { flujo ->
            BitmapFactory.decodeStream(flujo)?.asImageBitmap()
        }
    } catch (_: Exception) {
        null
    } finally {
        conexion?.disconnect()
    }
}
