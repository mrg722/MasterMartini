package cl.duoc.mastermartini.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * Franja de estado de un formulario: carga, error y éxito.
 * Cada estado es una tarjeta teñida con su color de contenedor, de modo que
 * el mensaje nunca queda como texto gris suelto sobre el fondo crema.
 */
@Composable
fun EstadoFormulario(
    ocupado: Boolean,
    mensajeCarga: String = "Guardando…",
    error: String? = null,
    exito: String? = null
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .semantics { liveRegion = LiveRegionMode.Polite }
    ) {
        AnimatedVisibility(
            visible = ocupado,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            FranjaEstado(
                contenedor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contenido = MaterialTheme.colorScheme.onSurface,
                texto = mensajeCarga
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.5.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        AnimatedVisibility(
            visible = error != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            FranjaEstado(
                contenedor = MaterialTheme.colorScheme.errorContainer,
                contenido = MaterialTheme.colorScheme.onErrorContainer,
                texto = error.orEmpty(),
                icono = Icons.Filled.ErrorOutline,
                descripcionIcono = "Error"
            )
        }

        AnimatedVisibility(
            visible = exito != null,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            FranjaEstado(
                contenedor = MaterialTheme.colorScheme.primaryContainer,
                contenido = MaterialTheme.colorScheme.onPrimaryContainer,
                texto = exito.orEmpty(),
                icono = Icons.Filled.CheckCircle,
                descripcionIcono = "Listo"
            )
        }
    }
}

@Composable
private fun FranjaEstado(
    contenedor: Color,
    contenido: Color,
    texto: String,
    icono: ImageVector? = null,
    descripcionIcono: String? = null,
    indicador: @Composable (() -> Unit)? = null
) {
    Surface(
        color = contenedor,
        contentColor = contenido,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = 52.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                indicador != null -> indicador()
                icono != null -> Icon(
                    imageVector = icono,
                    contentDescription = descripcionIcono,
                    tint = contenido,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = texto,
                style = MaterialTheme.typography.bodyMedium,
                color = contenido
            )
        }
    }
}
