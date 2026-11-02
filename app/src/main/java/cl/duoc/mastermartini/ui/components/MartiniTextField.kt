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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/** Alto mínimo del campo: deja el texto holgado y el área táctil sobre 48dp. */
private val AltoCampo = 60.dp

@Composable
fun MartiniTextField(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    modificador: Modifier = Modifier,
    iconoInicial: @Composable (() -> Unit)? = null,
    mensajeError: String? = null,
    esContrasena: Boolean = false,
    contrasenaVisible: Boolean = false,
    alCambiarVisibilidad: (() -> Unit)? = null,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    habilitado: Boolean = true
) {
    val esquema = MaterialTheme.colorScheme
    val hayError = mensajeError != null
    val estaValido = !hayError && valor.isNotBlank()

    Column(modifier = modificador.fillMaxWidth()) {
        OutlinedTextField(
            value = valor,
            onValueChange = alCambiar,
            label = { Text(etiqueta, style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = AltoCampo),
            singleLine = true,
            enabled = habilitado,
            isError = hayError,
            textStyle = MaterialTheme.typography.bodyLarge,
            leadingIcon = iconoInicial,
            visualTransformation = if (esContrasena && !contrasenaVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (esContrasena) KeyboardType.Password else tipoTeclado
            ),
            trailingIcon = {
                when {
                    // El ojo manda: si es contraseña, el usuario necesita poder verla.
                    esContrasena && alCambiarVisibilidad != null -> {
                        IconButton(
                            onClick = alCambiarVisibilidad,
                            enabled = habilitado,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = if (contrasenaVisible) {
                                    Icons.Filled.VisibilityOff
                                } else {
                                    Icons.Filled.Visibility
                                },
                                contentDescription = if (contrasenaVisible) {
                                    "Ocultar contraseña"
                                } else {
                                    "Mostrar contraseña"
                                },
                                tint = esquema.onSurfaceVariant
                            )
                        }
                    }
                    hayError -> {
                        Icon(
                            imageVector = Icons.Filled.ErrorOutline,
                            contentDescription = "Campo con error",
                            tint = esquema.error,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    estaValido -> {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Campo válido",
                            tint = esquema.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = esquema.onSurface,
                unfocusedTextColor = esquema.onSurface,
                disabledTextColor = esquema.onSurface.copy(alpha = 0.38f),
                errorTextColor = esquema.onSurface,
                // Un leve relleno separa el campo del fondo crema sin bajar el contraste.
                focusedContainerColor = esquema.surface,
                unfocusedContainerColor = esquema.surfaceContainerLow,
                disabledContainerColor = esquema.surfaceVariant.copy(alpha = 0.4f),
                errorContainerColor = esquema.errorContainer.copy(alpha = 0.22f),
                focusedBorderColor = esquema.primary,
                unfocusedBorderColor = esquema.outline,
                disabledBorderColor = esquema.outline.copy(alpha = 0.4f),
                errorBorderColor = esquema.error,
                focusedLabelColor = esquema.primary,
                unfocusedLabelColor = esquema.onSurfaceVariant,
                disabledLabelColor = esquema.onSurfaceVariant.copy(alpha = 0.5f),
                errorLabelColor = esquema.error,
                focusedLeadingIconColor = esquema.primary,
                unfocusedLeadingIconColor = esquema.onSurfaceVariant,
                errorLeadingIconColor = esquema.error,
                cursorColor = esquema.primary,
                errorCursorColor = esquema.error
            ),
            shape = MaterialTheme.shapes.small
        )

        AnimatedVisibility(
            visible = hayError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(
                modifier = Modifier.padding(start = 12.dp, top = 6.dp, end = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = Icons.Filled.ErrorOutline,
                    contentDescription = null,
                    tint = esquema.error,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = mensajeError.orEmpty(),
                    color = esquema.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
