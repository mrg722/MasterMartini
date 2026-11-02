package cl.duoc.mastermartini.ui.screens.detalle

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.data.model.VideoReceta
import cl.duoc.mastermartini.ui.components.MartiniPortada
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton
import cl.duoc.mastermartini.ui.components.MartiniTopBar
import cl.duoc.mastermartini.ui.components.StepDots

/**
 * Preparación paso a paso. Mantiene a la vista el video de la receta para
 * poder consultarlo en cualquier momento de la cocción.
 */
@Composable
fun PreparacionScreen(
    receta: RecetaContenido,
    alVolver: () -> Unit,
    alReproducirVideo: (VideoReceta) -> Unit = {}
) {
    var pasoActual by rememberSaveable(receta.id) { mutableIntStateOf(0) }
    val totalPasos = receta.pasos.size
    val paso = receta.pasos[pasoActual]
    val video = receta.video

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MartiniTopBar(
                titulo = receta.titulo,
                subtitulo = "Paso ${pasoActual + 1} de $totalPasos",
                alVolver = alVolver
            )
        }
    ) { relleno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .padding(horizontal = 20.dp)
        ) {
            LinearProgressIndicator(
                progress = { (pasoActual + 1).toFloat() / totalPasos },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .height(8.dp)
            )

            Spacer(Modifier.height(14.dp))

            StepDots(totalPasos = totalPasos, pasoActual = pasoActual)

            Spacer(Modifier.height(20.dp))

            MartiniPortada(
                urlMiniatura = video?.urlMiniatura,
                descripcion = "Imagen de ${receta.titulo}",
                mostrarBotonPlay = video != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(MaterialTheme.shapes.large)
            )

            if (video != null) {
                TextButton(
                    onClick = { alReproducirVideo(video) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayCircle,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "  Ver el video completo",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            AnimatedContent(
                targetState = pasoActual,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(220))) togetherWith
                        (fadeOut(animationSpec = tween(160)))
                },
                label = "cambioDePaso"
            ) { indice ->
                val pasoMostrado = receta.pasos[indice]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = pasoMostrado.first,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = pasoMostrado.second,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pasoActual > 0) {
                    TextButton(
                        onClick = { pasoActual-- },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Anterior", fontWeight = FontWeight.SemiBold)
                    }
                }
                MartiniPrimaryButton(
                    text = if (pasoActual == receta.pasos.lastIndex) "Finalizar" else "Siguiente",
                    onClick = {
                        if (pasoActual == receta.pasos.lastIndex) alVolver() else pasoActual++
                    },
                    modifier = Modifier.weight(2f)
                )
            }
        }
    }
}
