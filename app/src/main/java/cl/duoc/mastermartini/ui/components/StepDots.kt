package cl.duoc.mastermartini.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * Indicador de pasos. El paso actual es una píldora ancha en color primario,
 * los pasos ya completados quedan en primario atenuado y los pendientes en
 * el contorno del tema. Así el avance se lee por forma y por color, no solo
 * por color.
 */
@Composable
fun StepDots(
    totalPasos: Int,
    pasoActual: Int,
    modifier: Modifier = Modifier
) {
    if (totalPasos <= 0) return
    val visible = pasoActual.coerceIn(0, totalPasos - 1)

    Row(
        modifier = modifier
            // Fila alta para que no quede pegada al contenido de arriba.
            .heightIn(min = 24.dp)
            .semantics {
                contentDescription = "Paso ${visible + 1} de $totalPasos"
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val esquema = MaterialTheme.colorScheme
        repeat(totalPasos) { indice ->
            val activo = indice == visible
            val completado = indice < visible

            val color by animateColorAsState(
                targetValue = when {
                    activo -> esquema.primary
                    completado -> esquema.primary.copy(alpha = 0.55f)
                    else -> esquema.outlineVariant
                },
                label = "colorPaso"
            )
            val ancho by animateDpAsState(
                targetValue = if (activo) 30.dp else 10.dp,
                label = "anchoPaso"
            )
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(ancho)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
