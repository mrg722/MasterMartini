package cl.duoc.mastermartini.ui.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton
import cl.duoc.mastermartini.ui.components.MartiniTextField
import cl.duoc.mastermartini.ui.components.EstadoFormulario
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.imePadding
import cl.duoc.mastermartini.ui.components.StepDots
import cl.duoc.mastermartini.ui.theme.MartiniGreen
import cl.duoc.mastermartini.ui.theme.MasterMartiniTheme
import cl.duoc.mastermartini.viewmodel.RegistroViewModel

@Composable
fun RegisterScreen(
    onCuentaCreada: () -> Unit,
    onIniciarSesion: () -> Unit,
    modelo: RegistroViewModel = viewModel()
) {
    val estado by modelo.estado.collectAsState()

    LaunchedEffect(estado.registroExitoso) {
        if (estado.registroExitoso) {
            onCuentaCreada()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding()
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
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        MartiniTextField(
            valor = estado.nombre,
            alCambiar = modelo::cambiarNombre,
            etiqueta = "Nombre completo",
            habilitado = !estado.guardando,
            mensajeError = estado.errorNombre,
            iconoInicial = { androidx.compose.material3.Icon(Icons.Filled.Person, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MartiniTextField(
            valor = estado.correo,
            alCambiar = modelo::cambiarCorreo,
            etiqueta = "Correo electrónico",
            tipoTeclado = KeyboardType.Email,
            habilitado = !estado.guardando,
            mensajeError = estado.errorCorreo,
            iconoInicial = { androidx.compose.material3.Icon(Icons.Filled.Email, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MartiniTextField(
            valor = estado.contrasena,
            alCambiar = modelo::cambiarContrasena,
            etiqueta = "Contraseña",
            habilitado = !estado.guardando,
            mensajeError = estado.errorContrasena,
            esContrasena = true,
            contrasenaVisible = estado.contrasenaVisible,
            alCambiarVisibilidad = modelo::cambiarVisibilidadContrasena,
            iconoInicial = { androidx.compose.material3.Icon(Icons.Filled.Lock, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        MartiniTextField(
            valor = estado.confirmacion,
            alCambiar = modelo::cambiarConfirmacion,
            etiqueta = "Confirmar contraseña",
            habilitado = !estado.guardando,
            mensajeError = estado.errorConfirmacion,
            esContrasena = true,
            contrasenaVisible = estado.confirmacionVisible,
            alCambiarVisibilidad = modelo::cambiarVisibilidadConfirmacion,
            iconoInicial = { androidx.compose.material3.Icon(Icons.Filled.Lock, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = estado.terminosAceptados,
                enabled = !estado.guardando,
                onCheckedChange = modelo::cambiarTerminos,
                colors = CheckboxDefaults.colors(checkedColor = MartiniGreen)
            )
            Text(
                text = "Acepto los términos y condiciones",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        AnimatedVisibility(
            visible = estado.errorTerminos != null,
            enter = fadeIn(animationSpec = tween(180)) +
                expandVertically(animationSpec = tween(180)),
            exit = fadeOut(animationSpec = tween(120)) +
                shrinkVertically(animationSpec = tween(120))
        ) {
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
            onClick = modelo::crearCuenta,
            enabled = !estado.guardando && !estado.registroExitoso
        )

        EstadoFormulario(ocupado = estado.guardando, error = estado.errorGuardado)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("¿Ya tienes cuenta? ", color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextButton(onClick = onIniciarSesion, enabled = !estado.guardando, contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)) {
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
