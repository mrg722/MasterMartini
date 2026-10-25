package cl.duoc.mastermartini.data.validation

object RegisterValidator {

    private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private const val MIN_PASSWORD_LENGTH = 6

    fun validarNombre(nombre: String): String? = when {
        nombre.isBlank() -> "Ingresa tu nombre completo"
        nombre.trim().length < 3 -> "El nombre es demasiado corto"
        else -> null
    }

    fun validarCorreo(correo: String): String? = when {
        correo.isBlank() -> "Ingresa tu correo electrónico"
        !EMAIL_REGEX.matches(correo.trim()) -> "Ingresa un correo válido"
        else -> null
    }

    fun validarContrasena(contrasena: String): String? = when {
        contrasena.isBlank() -> "Ingresa una contraseña"
        contrasena.length < MIN_PASSWORD_LENGTH -> "Debe tener al menos $MIN_PASSWORD_LENGTH caracteres"
        else -> null
    }

    fun validarConfirmacion(contrasena: String, confirmacion: String): String? = when {
        confirmacion.isBlank() -> "Confirma tu contraseña"
        confirmacion != contrasena -> "Las contraseñas no coinciden"
        else -> null
    }

    fun validarTerminos(aceptado: Boolean): String? =
        if (!aceptado) "Debes aceptar los términos y condiciones" else null
}
