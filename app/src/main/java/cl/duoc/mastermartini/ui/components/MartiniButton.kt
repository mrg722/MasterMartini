package cl.duoc.mastermartini.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/** Alto cómodo para dedo grueso y vista cansada (muy por sobre los 48dp mínimos). */
private val AltoBoton = 56.dp

@Composable
private fun MartiniBotonBase(
    text: String,
    onClick: () -> Unit,
    contenedor: Color,
    contenido: Color,
    modifier: Modifier,
    enabled: Boolean
) {
    val superficie = MaterialTheme.colorScheme.onSurface
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = AltoBoton),
        shape = MaterialTheme.shapes.large,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = contenedor,
            contentColor = contenido,
            // Deshabilitado: plano, sin sombra y con 38% de opacidad en el texto,
            // que es el patrón accesible de Material 3.
            disabledContainerColor = superficie.copy(alpha = 0.12f),
            disabledContentColor = superficie.copy(alpha = 0.38f)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

/** Acción principal de la pantalla: verde de marca, relleno sólido. */
@Composable
fun MartiniPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    MartiniBotonBase(
        text = text,
        onClick = onClick,
        contenedor = MaterialTheme.colorScheme.primary,
        contenido = MaterialTheme.colorScheme.onPrimary,
        modifier = modifier,
        enabled = enabled
    )
}

/** Acción de acento (cobre tostado): segunda prioridad, nunca dos en la misma vista. */
@Composable
fun MartiniAccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    MartiniBotonBase(
        text = text,
        onClick = onClick,
        contenedor = MaterialTheme.colorScheme.secondary,
        contenido = MaterialTheme.colorScheme.onSecondary,
        modifier = modifier,
        enabled = enabled
    )
}
