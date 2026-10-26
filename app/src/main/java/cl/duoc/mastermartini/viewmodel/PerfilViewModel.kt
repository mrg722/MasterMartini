package cl.duoc.mastermartini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.data.model.PerfilUsuario
import cl.duoc.mastermartini.data.validation.ErroresPerfil
import cl.duoc.mastermartini.data.validation.ValidadorRegistro
import cl.duoc.mastermartini.data.validation.ValidadorPerfil
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EstadoPerfil(
    val perfil: PerfilUsuario = PerfilUsuario(),
    val errores: ErroresPerfil = ErroresPerfil(),
    val cargando: Boolean = true,
    val cargaCompleta: Boolean = false,
    val guardando: Boolean = false,
    val guardado: Boolean = false,
    val errorOperacion: String? = null
)

class PerfilViewModel(aplicacion: Application) : AndroidViewModel(aplicacion) {
    private val preferencias = PreferenciasUsuario(aplicacion)
    private val estadoMutable = MutableStateFlow(EstadoPerfil())
    val estado = estadoMutable.asStateFlow()

    init {
        cargarPerfil()
    }

    fun cargarPerfil() {
        estadoMutable.update { it.copy(cargando = true, errorOperacion = null) }
        viewModelScope.launch {
            try {
                val perfil = preferencias.perfilUsuario.first()
                estadoMutable.value = EstadoPerfil(perfil = perfil, cargando = false, cargaCompleta = true)
            } catch (_: IOException) {
                estadoMutable.update { it.copy(cargando = false, errorOperacion = "No se pudo cargar el perfil. Intenta nuevamente.") }
            }
        }
    }

    fun cambiarNombre(valor: String) {
        if (!puedeEditar()) return
        estadoMutable.update { it.copy(perfil = it.perfil.copy(nombre = valor), errores = it.errores.copy(nombre = ValidadorRegistro.validarNombre(valor)), guardado = false, errorOperacion = null) }
    }

    fun cambiarCorreo(valor: String) {
        if (!puedeEditar()) return
        estadoMutable.update { it.copy(perfil = it.perfil.copy(correo = valor), errores = it.errores.copy(correo = ValidadorRegistro.validarCorreo(valor)), guardado = false, errorOperacion = null) }
    }

    fun cambiarFecha(valor: String) {
        if (!puedeEditar()) return
        estadoMutable.update { it.copy(perfil = it.perfil.copy(fechaNacimiento = valor), errores = it.errores.copy(fechaNacimiento = ValidadorPerfil.validarFecha(valor)), guardado = false, errorOperacion = null) }
    }

    fun cambiarPreferencias(valor: String) {
        if (!puedeEditar()) return
        estadoMutable.update { it.copy(perfil = it.perfil.copy(preferenciasAlimentarias = valor), errores = it.errores.copy(preferenciasAlimentarias = ValidadorPerfil.validarPreferencias(valor)), guardado = false, errorOperacion = null) }
    }

    private fun puedeEditar() = estadoMutable.value.cargaCompleta && !estadoMutable.value.guardando

    fun guardarPerfil() {
        if (!puedeEditar()) return
        val perfil = estadoMutable.value.perfil
        val errores = ValidadorPerfil.validar(perfil)
        estadoMutable.update { it.copy(errores = errores, guardado = false, errorOperacion = null) }
        if (!errores.esValido) return
        estadoMutable.update { it.copy(guardando = true) }
        viewModelScope.launch {
            try {
                preferencias.guardarPerfil(perfil)
                estadoMutable.update { it.copy(guardando = false, guardado = true) }
            } catch (_: IOException) {
                estadoMutable.update { it.copy(guardando = false, errorOperacion = "No se pudieron guardar los cambios. Intenta nuevamente.") }
            }
        }
    }
}
