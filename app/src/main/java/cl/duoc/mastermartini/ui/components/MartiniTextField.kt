package cl.duoc.mastermartini.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.ui.theme.MartiniGreen

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
    Column(modifier = modificador.fillMaxWidth()) {
        OutlinedTextField(
            value = valor,
            onValueChange = alCambiar,
            label = { Text(etiqueta) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = habilitado,
            isError = mensajeError != null,
            leadingIcon = iconoInicial,
            visualTransformation = if (esContrasena && !contrasenaVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = if (esContrasena) KeyboardType.Password else tipoTeclado
            ),
            trailingIcon = {
                when {
                    esContrasena && alCambiarVisibilidad != null -> {
                        IconButton(onClick = alCambiarVisibilidad, enabled = habilitado) {
                            Icon(
                                imageVector = if (contrasenaVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                                contentDescription = if (contrasenaVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    }
                    mensajeError != null -> {
                        Icon(
                            imageVector = Icons.Filled.Error,
                            contentDescription = "Error",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                    valor.isNotBlank() -> {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Campo válido",
                            tint = MartiniGreen
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MartiniGreen,
                focusedLabelColor = MartiniGreen,
                cursorColor = MartiniGreen
            ),
            shape = MaterialTheme.shapes.medium
        )
        AnimatedVisibility(visible = mensajeError != null) {
            Text(
                text = mensajeError.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}
