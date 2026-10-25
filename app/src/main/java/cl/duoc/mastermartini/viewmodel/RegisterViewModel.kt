package cl.duoc.mastermartini.viewmodel

import androidx.lifecycle.ViewModel
import cl.duoc.mastermartini.data.validation.RegisterValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegisterUiState(
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
    val registroExitoso: Boolean = false
) {
    val esValido: Boolean
        get() = errorNombre == null && errorCorreo == null && errorContrasena == null &&
            errorConfirmacion == null && errorTerminos == null &&
            nombre.isNotBlank() && correo.isNotBlank() && contrasena.isNotBlank() &&
            confirmacion.isNotBlank() && terminosAceptados
}

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNombreChange(valor: String) {
        _uiState.update { it.copy(nombre = valor, errorNombre = RegisterValidator.validarNombre(valor)) }
    }

    fun onCorreoChange(valor: String) {
        _uiState.update { it.copy(correo = valor, errorCorreo = RegisterValidator.validarCorreo(valor)) }
    }

    fun onContrasenaChange(valor: String) {
        _uiState.update {
            it.copy(
                contrasena = valor,
                errorContrasena = RegisterValidator.validarContrasena(valor),
                errorConfirmacion = if (it.confirmacion.isNotEmpty()) {
                    RegisterValidator.validarConfirmacion(valor, it.confirmacion)
                } else {
                    it.errorConfirmacion
                }
            )
        }
    }

    fun onConfirmacionChange(valor: String) {
        _uiState.update {
            it.copy(
                confirmacion = valor,
                errorConfirmacion = RegisterValidator.validarConfirmacion(it.contrasena, valor)
            )
        }
    }

    fun onTerminosChange(aceptado: Boolean) {
        _uiState.update { it.copy(terminosAceptados = aceptado, errorTerminos = RegisterValidator.validarTerminos(aceptado)) }
    }

    fun onToggleContrasenaVisible() {
        _uiState.update { it.copy(contrasenaVisible = !it.contrasenaVisible) }
    }

    fun onToggleConfirmacionVisible() {
        _uiState.update { it.copy(confirmacionVisible = !it.confirmacionVisible) }
    }

    fun validarFormularioCompleto(): Boolean {
        _uiState.update {
            it.copy(
                errorNombre = RegisterValidator.validarNombre(it.nombre),
                errorCorreo = RegisterValidator.validarCorreo(it.correo),
                errorContrasena = RegisterValidator.validarContrasena(it.contrasena),
                errorConfirmacion = RegisterValidator.validarConfirmacion(it.contrasena, it.confirmacion),
                errorTerminos = RegisterValidator.validarTerminos(it.terminosAceptados)
            )
        }
        return _uiState.value.esValido
    }

    fun onCrearCuenta() {
        if (!validarFormularioCompleto()) return
        _uiState.update { it.copy(registroExitoso = true) }
    }
}
