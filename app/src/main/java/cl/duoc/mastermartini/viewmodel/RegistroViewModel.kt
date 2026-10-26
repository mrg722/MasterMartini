package cl.duoc.mastermartini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.data.validation.ValidadorRegistro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class EstadoRegistro(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmacion: String = "",
    val terminosAceptados: Boolean = false,
    val contrasenaVisible: Boolean = false,
    val confirmacionVisible: Boolean = false,
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorContrasena: String? = null,
    val errorConfirmacion: String? = null,
    val errorTerminos: String? = null,
    val registroExitoso: Boolean = false,
    val guardando: Boolean = false,
    val errorGuardado: String? = null
)

class RegistroViewModel(aplicacion: Application) : AndroidViewModel(aplicacion) {

    private val preferencias = PreferenciasUsuario(aplicacion)

    private val estadoMutable = MutableStateFlow(EstadoRegistro())
    val estado: StateFlow<EstadoRegistro> = estadoMutable.asStateFlow()

    fun cambiarNombre(valor: String) {
        if (estadoMutable.value.guardando) return
        estadoMutable.update { it.copy(nombre = valor, errorNombre = ValidadorRegistro.validarNombre(valor)) }
    }

    fun cambiarCorreo(valor: String) {
        if (estadoMutable.value.guardando) return
        estadoMutable.update { it.copy(correo = valor, errorCorreo = ValidadorRegistro.validarCorreo(valor)) }
    }

    fun cambiarContrasena(valor: String) {
        if (estadoMutable.value.guardando) return
        estadoMutable.update {
            it.copy(
                contrasena = valor,
                errorContrasena = ValidadorRegistro.validarContrasena(valor),
                errorConfirmacion = if (it.confirmacion.isNotEmpty()) {
                    ValidadorRegistro.validarConfirmacion(valor, it.confirmacion)
                } else {
                    it.errorConfirmacion
                }
            )
        }
    }

    fun cambiarConfirmacion(valor: String) {
        if (estadoMutable.value.guardando) return
        estadoMutable.update {
            it.copy(
                confirmacion = valor,
                errorConfirmacion = ValidadorRegistro.validarConfirmacion(it.contrasena, valor)
            )
        }
    }

    fun cambiarTerminos(aceptado: Boolean) {
        if (estadoMutable.value.guardando) return
        estadoMutable.update { it.copy(terminosAceptados = aceptado, errorTerminos = ValidadorRegistro.validarTerminos(aceptado)) }
    }

    fun cambiarVisibilidadContrasena() {
        estadoMutable.update { it.copy(contrasenaVisible = !it.contrasenaVisible) }
    }

    fun cambiarVisibilidadConfirmacion() {
        estadoMutable.update { it.copy(confirmacionVisible = !it.confirmacionVisible) }
    }

    fun validarFormularioCompleto(): Boolean {
        val actual = estadoMutable.value
        val resultado = ValidadorRegistro.validarFormulario(
            actual.nombre, actual.correo, actual.contrasena, actual.confirmacion, actual.terminosAceptados
        )
        estadoMutable.update {
            it.copy(
                errorNombre = resultado.errorNombre,
                errorCorreo = resultado.errorCorreo,
                errorContrasena = resultado.errorContrasena,
                errorConfirmacion = resultado.errorConfirmacion,
                errorTerminos = resultado.errorTerminos
            )
        }
        return resultado.esValido
    }

    fun crearCuenta() {
        if (estadoMutable.value.guardando || estadoMutable.value.registroExitoso) return
        estadoMutable.update { it.copy(errorGuardado = null) }
        if (!validarFormularioCompleto()) return
        val estadoActual = estadoMutable.value
        estadoMutable.update { it.copy(guardando = true) }
        viewModelScope.launch {
            try {
                preferencias.guardarCuenta(estadoActual.nombre.trim(), estadoActual.correo.trim())
                estadoMutable.update { it.copy(registroExitoso = true, guardando = false, contrasena = "", confirmacion = "") }
            } catch (_: IOException) {
                estadoMutable.update { it.copy(guardando = false, errorGuardado = "No se pudo guardar la cuenta. Intenta nuevamente.") }
            }
        }
    }
}
