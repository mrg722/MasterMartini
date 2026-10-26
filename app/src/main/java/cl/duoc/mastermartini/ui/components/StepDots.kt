package cl.duoc.mastermartini.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import cl.duoc.mastermartini.ui.theme.MartiniGreen

@Composable
fun StepDots(
    totalPasos: Int,
    pasoActual: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalPasos) { indice ->
            val activo = indice == pasoActual
            val color by animateColorAsState(
                targetValue = if (activo) MartiniGreen else MaterialTheme.colorScheme.outline,
                label = "colorPaso"
            )
            val ancho by animateDpAsState(
                targetValue = if (activo) 24.dp else 8.dp,
                label = "anchoPaso"
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(ancho)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
