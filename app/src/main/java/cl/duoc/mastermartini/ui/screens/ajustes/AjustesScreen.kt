package cl.duoc.mastermartini.ui.screens.ajustes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Brightness6
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.mastermartini.data.model.TemaApp
import cl.duoc.mastermartini.ui.components.MartiniAviso
import cl.duoc.mastermartini.ui.components.MartiniBottomBar
import cl.duoc.mastermartini.ui.components.MartiniFilaAjuste
import cl.duoc.mastermartini.ui.components.MartiniGrupoAjustes
import cl.duoc.mastermartini.ui.components.MartiniTopBar
import cl.duoc.mastermartini.ui.components.SeccionPrincipal
import cl.duoc.mastermartini.viewmodel.AjustesViewModel

/**
 * Ajustes de la aplicación: apariencia (claro / oscuro / según el sistema),
 * notificaciones y administración de la cuenta guardada en el dispositivo.
 *
 * Toda la lógica vive en [AjustesViewModel]; esta pantalla solo refleja el
 * estado y dispara acciones.
 */
@Composable
fun AjustesScreen(
    alVolver: () -> Unit,
    alEditarPerfil: () -> Unit,
    alCrearCuenta: () -> Unit,
    alSalirDeLaSesion: () -> Unit,
    alNavegar: (SeccionPrincipal) -> Unit,
    modelo: AjustesViewModel = viewModel()
) {
    val estado by modelo.estado.collectAsState()
    var confirmarCierre by remember { mutableStateOf(false) }
    var confirmarEliminacion by remember { mutableStateOf(false) }

    LaunchedEffect(estado.sesionCerrada, estado.cuentaEliminada) {
        if (estado.sesionCerrada || estado.cuentaEliminada) {
            modelo.consumirSalida()
            alSalirDeLaSesion()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MartiniTopBar(
                titulo = "Ajustes",
                subtitulo = "Apariencia, avisos y cuenta",
                alVolver = alVolver
            )
        },
        bottomBar = {
            MartiniBottomBar(
                seccionActual = SeccionPrincipal.AJUSTES,
                alNavegar = alNavegar
            )
        }
    ) { relleno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            item {
                MartiniAviso(mensaje = estado.error, esError = true)
            }
            item {
                MartiniAviso(mensaje = estado.mensaje)
            }

            item {
                MartiniGrupoAjustes(titulo = "Apariencia") {
                    TemaApp.entries.forEach { tema ->
                        MartiniFilaAjuste(
                            titulo = tema.etiqueta,
                            descripcion = descripcionTema(tema),
                            icono = iconoTema(tema),
                            alPulsar = { modelo.cambiarTema(tema) },
                            control = {
                                RadioButton(
                                    selected = estado.tema == tema,
                                    onClick = { modelo.cambiarTema(tema) }
                                )
                            }
                        )
                    }
                }
            }

            item {
                MartiniGrupoAjustes(titulo = "Avisos") {
                    MartiniFilaAjuste(
                        titulo = "Notificaciones",
                        descripcion = "Avisos de nuevas recetas y cursos",
                        icono = Icons.Filled.Notifications,
                        alPulsar = { modelo.cambiarNotificaciones(!estado.notificacionesActivas) },
                        control = {
                            Switch(
                                checked = estado.notificacionesActivas,
                                onCheckedChange = modelo::cambiarNotificaciones
                            )
                        }
                    )
                }
            }

            item {
                MartiniGrupoAjustes(titulo = "Cuenta") {
                    if (estado.existeCuenta) {
                        MartiniFilaAjuste(
                            titulo = estado.perfil.nombre.ifBlank { "Cuenta sin nombre" },
                            descripcion = estado.perfil.correo.ifBlank { "Sin correo registrado" },
                            icono = Icons.Filled.Person,
                            alPulsar = alEditarPerfil
                        )
                        MartiniFilaAjuste(
                            titulo = "Cerrar sesión",
                            descripcion = "Se conservan tus datos en este dispositivo",
                            icono = Icons.AutoMirrored.Filled.Logout,
                            habilitada = !estado.procesando,
                            alPulsar = { confirmarCierre = true }
                        )
                        MartiniFilaAjuste(
                            titulo = "Eliminar cuenta",
                            descripcion = "Borra perfil, intereses y recetas guardadas",
                            icono = Icons.Filled.DeleteForever,
                            destructiva = true,
                            habilitada = !estado.procesando,
                            alPulsar = { confirmarEliminacion = true }
                        )
                    } else {
                        MartiniFilaAjuste(
                            titulo = "Crear una cuenta",
                            descripcion = "Guarda tus intereses y recetas favoritas",
                            icono = Icons.Filled.PersonAdd,
                            alPulsar = alCrearCuenta
                        )
                    }
                }
            }

            item {
                MartiniGrupoAjustes(titulo = "Acerca de") {
                    MartiniFilaAjuste(
                        titulo = "Master Martini",
                        descripcion = "Versión 1.0 · recetario chileno con videos",
                        icono = Icons.Filled.Info
                    )
                    MartiniFilaAjuste(
                        titulo = "Origen de los videos",
                        descripcion = "Contenido de canales de YouTube; la app solo enlaza y no " +
                            "almacena los videos",
                        icono = Icons.Filled.PlayCircle
                    )
                }
            }
        }
    }

    if (confirmarCierre) {
        AlertDialog(
            onDismissRequest = { confirmarCierre = false },
            title = { Text("¿Cerrar sesión?", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Volverás a la pantalla de inicio. Tus datos quedan guardados en este " +
                        "dispositivo y podrás entrar de nuevo cuando quieras."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmarCierre = false
                        modelo.cerrarSesion()
                    }
                ) { Text("Cerrar sesión") }
            },
            dismissButton = {
                TextButton(onClick = { confirmarCierre = false }) { Text("Cancelar") }
            }
        )
    }

    if (confirmarEliminacion) {
        AlertDialog(
            onDismissRequest = { confirmarEliminacion = false },
            title = { Text("¿Eliminar la cuenta?", fontWeight = FontWeight.Bold) },
            text = {
                Text(
                    "Se borrarán el perfil, la foto, los intereses y las recetas guardadas de " +
                        "este dispositivo. Esta acción no se puede deshacer."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        confirmarEliminacion = false
                        modelo.eliminarCuenta()
                    }
                ) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { confirmarEliminacion = false }) { Text("Cancelar") }
            }
        )
    }
}

private fun descripcionTema(tema: TemaApp): String = when (tema) {
    TemaApp.SISTEMA -> "Sigue la configuración del teléfono"
    TemaApp.CLARO -> "Fondo claro, ideal con mucha luz"
    TemaApp.OSCURO -> "Fondo oscuro, descansa la vista de noche"
}

private fun iconoTema(tema: TemaApp) = when (tema) {
    TemaApp.SISTEMA -> Icons.Filled.Brightness6
    TemaApp.CLARO -> Icons.Filled.LightMode
    TemaApp.OSCURO -> Icons.Filled.DarkMode
}
