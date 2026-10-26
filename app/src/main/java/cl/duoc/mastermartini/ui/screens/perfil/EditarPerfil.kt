package cl.duoc.mastermartini.ui.screens.perfil

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.mastermartini.ui.components.EstadoFormulario
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton
import cl.duoc.mastermartini.ui.components.MartiniTextField
import cl.duoc.mastermartini.viewmodel.PerfilViewModel
import androidx.core.content.FileProvider
import java.io.File

@Composable
fun EditarPerfil(alVolver: () -> Unit, modelo: PerfilViewModel = viewModel()) {
    val estado by modelo.estado.collectAsState()
    val contexto = LocalContext.current
    val archivoPendiente = remember { mutableStateOf<File?>(null) }
    val lanzadorGaleria = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let(modelo::guardarFotoSeleccionada)
    }
    val lanzadorCamara = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { capturada ->
        val archivo = archivoPendiente.value
        if (capturada && archivo != null) {
            val uri = FileProvider.getUriForFile(contexto, "${contexto.packageName}.fileprovider", archivo)
            modelo.guardarFotoCapturada(uri)
        } else {
            archivo?.delete()
        }
        archivoPendiente.value = null
    }
    val solicitarGaleria = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
        if (concedido) lanzadorGaleria.launch("image/*")
        else modelo.informarError("Se necesita permiso para acceder a tus fotos.")
    }
    fun abrirCamara() {
        val directorio = contexto.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (directorio == null || (!directorio.exists() && !directorio.mkdirs())) {
            modelo.informarError("No se pudo preparar la cámara.")
            return
        }
        val archivo = File(directorio, "perfil_${System.currentTimeMillis()}.jpg")
        archivoPendiente.value = archivo
        val uri = FileProvider.getUriForFile(contexto, "${contexto.packageName}.fileprovider", archivo)
        lanzadorCamara.launch(uri)
    }
    val solicitarCamara = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
        if (concedido) abrirCamara()
        else modelo.informarError("Se necesita permiso para usar la cámara.")
    }
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).imePadding()
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
            FotoPerfil(uriTexto = estado.fotoUri)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = {
                        if (contexto.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) abrirCamara()
                        else solicitarCamara.launch(Manifest.permission.CAMERA)
                    },
                    enabled = !estado.guardando,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                    Text("Cámara", modifier = Modifier.padding(start = 8.dp))
                }
                OutlinedButton(
                    onClick = {
                        val permiso = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
                        if (contexto.checkSelfPermission(permiso) == PackageManager.PERMISSION_GRANTED) lanzadorGaleria.launch("image/*")
                        else solicitarGaleria.launch(permiso)
                    },
                    enabled = !estado.guardando,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Filled.PhotoLibrary, contentDescription = null)
                    Text("Galería", modifier = Modifier.padding(start = 8.dp))
                }
            }
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
