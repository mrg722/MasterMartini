package cl.duoc.mastermartini.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton
import cl.duoc.mastermartini.ui.components.MartiniTextField
import cl.duoc.mastermartini.ui.components.StepDots
import cl.duoc.mastermartini.ui.theme.MartiniCream
import cl.duoc.mastermartini.ui.theme.MartiniGreen
import cl.duoc.mastermartini.ui.theme.MartiniTextSecondary
import cl.duoc.mastermartini.ui.theme.MasterMartiniTheme
import cl.duoc.mastermartini.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onCuentaCreada: () -> Unit,
    onIniciarSesion: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()

    if (estado.registroExitoso) {
        onCuentaCreada()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MartiniCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        StepDots(totalPasos = 3, pasoActual = 0)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Crea tu cuenta",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Únete a Master Martini y comienza a disfrutar de una vida más saludable",
            style = MaterialTheme.typography.bodyMedium,
            color = MartiniTextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        MartiniTextField(
            value = estado.nombre,
            onValueChange = viewModel::onNombreChange,
            label = "Nombre completo",
            errorMessage = estado.errorNombre,
            leadingIcon = { androidx.compose.material3.Icon(Icons.Filled.Person, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MartiniTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            label = "Correo electrónico",
            errorMessage = estado.errorCorreo,
            leadingIcon = { androidx.compose.material3.Icon(Icons.Filled.Email, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MartiniTextField(
            value = estado.contrasena,
            onValueChange = viewModel::onContrasenaChange,
            label = "Contraseña",
            errorMessage = estado.errorContrasena,
            isPassword = true,
            passwordVisible = estado.contrasenaVisible,
            onTogglePasswordVisibility = viewModel::onToggleContrasenaVisible,
            leadingIcon = { androidx.compose.material3.Icon(Icons.Filled.Lock, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MartiniTextField(
            value = estado.confirmacion,
            onValueChange = viewModel::onConfirmacionChange,
            label = "Confirmar contraseña",
            errorMessage = estado.errorConfirmacion,
            isPassword = true,
            passwordVisible = estado.confirmacionVisible,
            onTogglePasswordVisibility = viewModel::onToggleConfirmacionVisible,
            leadingIcon = { androidx.compose.material3.Icon(Icons.Filled.Lock, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = estado.terminosAceptados,
                onCheckedChange = viewModel::onTerminosChange,
                colors = CheckboxDefaults.colors(checkedColor = MartiniGreen)
            )
            Text(
                text = "Acepto los términos y condiciones",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (estado.errorTerminos != null) {
            Text(
                text = estado.errorTerminos.orEmpty(),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        MartiniPrimaryButton(
            text = "Crear cuenta",
            onClick = viewModel::onCrearCuenta
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("¿Ya tienes cuenta? ", color = MartiniTextSecondary)
            TextButton(onClick = onIniciarSesion, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
                Text("Inicia sesión", color = MartiniGreen, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun RegisterScreenPreview() {
    MasterMartiniTheme {
        RegisterScreen(onCuentaCreada = {}, onIniciarSesion = {})
    }
}
