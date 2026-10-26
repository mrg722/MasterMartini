package cl.duoc.mastermartini.ui.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.mastermartini.ui.components.EstadoFormulario
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton
import cl.duoc.mastermartini.ui.components.MartiniTextField
import cl.duoc.mastermartini.ui.theme.MartiniCream
import cl.duoc.mastermartini.viewmodel.PerfilViewModel

@Composable
fun EditarPerfil(alVolver: () -> Unit, modelo: PerfilViewModel = viewModel()) {
    val estado by modelo.estado.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().background(MartiniCream).imePadding()
            .verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = alVolver, enabled = !estado.guardando) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver al perfil")
            }
            Text("Editar perfil", style = MaterialTheme.typography.headlineSmall)
        }
        EstadoFormulario(
            ocupado = estado.cargando || estado.guardando,
            mensajeCarga = if (estado.cargando) "Cargando perfil…" else "Guardando cambios…",
            error = estado.errorOperacion,
            exito = if (estado.guardado) "Cambios guardados" else null
        )
        if (!estado.cargando && !estado.cargaCompleta) {
            TextButton(onClick = modelo::cargarPerfil) { Text("Reintentar carga") }
        }
        if (estado.cargaCompleta) {
            MartiniTextField(
                valor = estado.perfil.nombre, alCambiar = modelo::cambiarNombre,
                etiqueta = "Nombre completo", mensajeError = estado.errores.nombre,
                iconoInicial = { Icon(Icons.Filled.Person, contentDescription = null) },
                habilitado = !estado.guardando
            )
            MartiniTextField(
                valor = estado.perfil.correo, alCambiar = modelo::cambiarCorreo,
                etiqueta = "Correo electrónico", mensajeError = estado.errores.correo,
                iconoInicial = { Icon(Icons.Filled.Email, contentDescription = null) },
                tipoTeclado = KeyboardType.Email, habilitado = !estado.guardando
            )
            MartiniTextField(
                valor = estado.perfil.fechaNacimiento, alCambiar = modelo::cambiarFecha,
                etiqueta = "Fecha de nacimiento (dd/mm/aaaa)", mensajeError = estado.errores.fechaNacimiento,
                iconoInicial = { Icon(Icons.Filled.DateRange, contentDescription = null) },
                habilitado = !estado.guardando
            )
            MartiniTextField(
                valor = estado.perfil.preferenciasAlimentarias, alCambiar = modelo::cambiarPreferencias,
                etiqueta = "Preferencias alimentarias", mensajeError = estado.errores.preferenciasAlimentarias,
                iconoInicial = { Icon(Icons.Filled.Restaurant, contentDescription = null) },
                habilitado = !estado.guardando
            )
            MartiniPrimaryButton(text = "Guardar cambios", onClick = modelo::guardarPerfil, enabled = !estado.guardando)
        }
    }
}
