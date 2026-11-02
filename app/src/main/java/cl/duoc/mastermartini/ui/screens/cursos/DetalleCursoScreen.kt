package cl.duoc.mastermartini.ui.screens.cursos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.data.model.Curso
import cl.duoc.mastermartini.data.model.VideoReceta
import cl.duoc.mastermartini.ui.components.MartiniEtiqueta
import cl.duoc.mastermartini.ui.components.MartiniTarjetaVideo
import cl.duoc.mastermartini.ui.components.MartiniTituloSeccion
import cl.duoc.mastermartini.ui.components.MartiniTopBar

/**
 * Detalle de un curso: muestra sus clases en orden, cada una con su video y un
 * acceso directo a la receta correspondiente del catálogo.
 */
@Composable
fun DetalleCursoScreen(
    curso: Curso,
    alVolver: () -> Unit,
    alReproducirVideo: (VideoReceta) -> Unit,
    alAbrirReceta: (String) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MartiniTopBar(
                titulo = curso.titulo,
                subtitulo = curso.resumenCorto,
                alVolver = alVolver
            )
        }
    ) { relleno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = curso.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        MartiniEtiqueta(
                            texto = curso.nivel,
                            icono = Icons.AutoMirrored.Filled.MenuBook
                        )
                        MartiniEtiqueta(
                            texto = "${curso.totalClases} clases",
                            icono = Icons.Filled.Restaurant
                        )
                    }
                }
            }

            item {
                MartiniTituloSeccion(
                    titulo = "Clases del curso",
                    detalle = "Cada clase abre su video dentro de la aplicación"
                )
            }

            itemsIndexed(curso.clases) { indice, clase ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = MaterialTheme.shapes.small,
                            color = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${indice + 1}",
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = clase.titulo,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = clase.resumen,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    MartiniTarjetaVideo(
                        titulo = clase.video.titulo,
                        canal = clase.video.canal,
                        duracion = clase.video.duracion,
                        urlMiniatura = clase.video.urlMiniatura,
                        etiquetaSuperior = "Clase ${indice + 1}",
                        alReproducir = { alReproducirVideo(clase.video) }
                    )

                    val recetaId = clase.recetaId
                    if (recetaId != null) {
                        TextButton(
                            onClick = { alAbrirReceta(recetaId) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Restaurant,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "  Ver la receta completa",
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}
