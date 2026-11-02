package cl.duoc.mastermartini.ui.screens.detalle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.data.model.VideoReceta
import cl.duoc.mastermartini.ui.components.MartiniEtiqueta
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton
import cl.duoc.mastermartini.ui.components.MartiniTarjetaVideo
import cl.duoc.mastermartini.ui.components.MartiniTituloSeccion
import cl.duoc.mastermartini.ui.components.MartiniTopBar

/**
 * Ficha de una receta: video, datos, ingredientes y acceso a la preparación.
 */
@Composable
fun DetalleRecetaScreen(
    receta: RecetaContenido,
    esFavorita: Boolean,
    alVolver: () -> Unit,
    alPreparar: () -> Unit,
    alAlternarFavorita: () -> Unit,
    alReproducirVideo: (VideoReceta) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MartiniTopBar(
                titulo = receta.titulo,
                subtitulo = "${receta.minutos} min · ${receta.dificultad}",
                alVolver = alVolver,
                acciones = {
                    IconButton(onClick = alAlternarFavorita) {
                        Icon(
                            imageVector = if (esFavorita) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (esFavorita) {
                                "Quitar de favoritas"
                            } else {
                                "Guardar en favoritas"
                            },
                            tint = if (esFavorita) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            )
        }
    ) { relleno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            val video = receta.video
            if (video != null) {
                MartiniTarjetaVideo(
                    titulo = video.titulo,
                    canal = video.canal,
                    duracion = video.duracion,
                    urlMiniatura = video.urlMiniatura,
                    etiquetaSuperior = "Video de la receta",
                    alReproducir = { alReproducirVideo(video) }
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MartiniEtiqueta(texto = "${receta.minutos} min", icono = Icons.Filled.AccessTime)
                MartiniEtiqueta(texto = receta.dificultad, icono = Icons.Filled.Restaurant)
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                MartiniTituloSeccion(titulo = "Descripción")
                Text(
                    text = receta.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                MartiniTituloSeccion(
                    titulo = "Ingredientes",
                    detalle = "${receta.ingredientes.size} ingredientes"
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        receta.ingredientes.forEach { ingrediente ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = ingrediente,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            val complementario = receta.videoComplementario
            if (complementario != null) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    MartiniTituloSeccion(
                        titulo = "Clip de apoyo",
                        detalle = "No reemplaza al video completo de la receta"
                    )
                    MartiniTarjetaVideo(
                        titulo = complementario.titulo,
                        canal = complementario.canal,
                        duracion = complementario.duracion,
                        urlMiniatura = complementario.urlMiniatura,
                        etiquetaSuperior = "Complemento",
                        alReproducir = { alReproducirVideo(complementario) }
                    )
                }
            }

            Text(
                text = "Los videos son de sus canales originales y se reproducen desde YouTube.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            MartiniPrimaryButton(
                text = "Ver preparación paso a paso",
                onClick = alPreparar,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
