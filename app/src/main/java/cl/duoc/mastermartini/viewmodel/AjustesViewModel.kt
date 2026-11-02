package cl.duoc.mastermartini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.data.model.PerfilUsuario
import cl.duoc.mastermartini.data.model.TemaApp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * Estado de la pantalla de ajustes.
 *
 * @param cuentaEliminada se vuelve `true` una sola vez para que la interfaz
 *   navegue de salida; se limpia con [AjustesViewModel.consumirSalida].
 */
data class EstadoAjustes(
    val tema: TemaApp = TemaApp.SISTEMA,
    val notificacionesActivas: Boolean = true,
    val perfil: PerfilUsuario = PerfilUsuario(),
    val existeCuenta: Boolean = false,
    val sesionActiva: Boolean = false,
    val procesando: Boolean = false,
    val mensaje: String? = null,
    val error: String? = null,
    val sesionCerrada: Boolean = false,
    val cuentaEliminada: Boolean = false
)

class AjustesViewModel(aplicacion: Application) : AndroidViewModel(aplicacion) {

    private val preferencias = PreferenciasUsuario(aplicacion)

    private val estadoMutable = MutableStateFlow(EstadoAjustes())
    val estado: StateFlow<EstadoAjustes> = estadoMutable.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                preferencias.temaApp,
                preferencias.notificacionesActivas,
                preferencias.perfilUsuario,
                preferencias.existeCuenta,
                preferencias.sesionActiva
            ) { tema, notificaciones, perfil, existeCuenta, sesionActiva ->
                Quintupla(tema, notificaciones, perfil, existeCuenta, sesionActiva)
            }.collect { datos ->
                estadoMutable.update {
                    it.copy(
                        tema = datos.tema,
                        notificacionesActivas = datos.notificaciones,
                        perfil = datos.perfil,
                        existeCuenta = datos.existeCuenta,
                        sesionActiva = datos.sesionActiva
                    )
                }
            }
        }
    }

    private data class Quintupla(
        val tema: TemaApp,
        val notificaciones: Boolean,
        val perfil: PerfilUsuario,
        val existeCuenta: Boolean,
        val sesionActiva: Boolean
    )

    fun cambiarTema(tema: TemaApp) {
        if (tema == estadoMutable.value.tema) return
        viewModelScope.launch {
            try {
                preferencias.guardarTema(tema)
                estadoMutable.update { it.copy(mensaje = "Apariencia: ${tema.etiqueta.lowercase()}", error = null) }
            } catch (_: IOException) {
                estadoMutable.update { it.copy(error = "No se pudo guardar la apariencia. Intenta nuevamente.") }
            }
        }
    }

    fun cambiarNotificaciones(activas: Boolean) {
        viewModelScope.launch {
            try {
                preferencias.guardarNotificaciones(activas)
                estadoMutable.update {
                    it.copy(
                        mensaje = if (activas) "Notificaciones activadas" else "Notificaciones desactivadas",
                        error = null
                    )
                }
            } catch (_: IOException) {
                estadoMutable.update { it.copy(error = "No se pudo guardar la preferencia. Intenta nuevamente.") }
            }
        }
    }

    fun cerrarSesion() {
        if (estadoMutable.value.procesando) return
        estadoMutable.update { it.copy(procesando = true, error = null, mensaje = null) }
        viewModelScope.launch {
            try {
                preferencias.cerrarSesion()
                estadoMutable.update { it.copy(procesando = false, sesionCerrada = true) }
            } catch (_: IOException) {
                estadoMutable.update { it.copy(procesando = false, error = "No se pudo cerrar la sesión. Intenta nuevamente.") }
            }
        }
    }

    fun eliminarCuenta() {
        if (estadoMutable.value.procesando) return
        estadoMutable.update { it.copy(procesando = true, error = null, mensaje = null) }
        viewModelScope.launch {
            try {
                preferencias.eliminarCuenta()
                estadoMutable.update { it.copy(procesando = false, cuentaEliminada = true) }
            } catch (_: IOException) {
                estadoMutable.update { it.copy(procesando = false, error = "No se pudo eliminar la cuenta. Intenta nuevamente.") }
            }
        }
    }

    /** Limpia las banderas de navegación una vez que la interfaz ya reaccionó. */
    fun consumirSalida() {
        estadoMutable.update { it.copy(sesionCerrada = false, cuentaEliminada = false) }
    }

    fun consumirMensaje() {
        estadoMutable.update { it.copy(mensaje = null, error = null) }
    }
}
