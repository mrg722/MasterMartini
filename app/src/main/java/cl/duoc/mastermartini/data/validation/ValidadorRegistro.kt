package cl.duoc.mastermartini.data.validation

object ValidadorRegistro {

    private val PATRON_CORREO = Regex("^[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z]{2,}$")
    private const val LARGO_MINIMO_CONTRASENA = 6

    data class Resultado(
        val errorNombre: String?,
        val errorCorreo: String?,
        val errorContrasena: String?,
        val errorConfirmacion: String?,
        val errorTerminos: String?
    ) {
        val esValido: Boolean
            get() = errorNombre == null && errorCorreo == null && errorContrasena == null &&
                errorConfirmacion == null && errorTerminos == null
    }

    fun validarFormulario(
        nombre: String,
        correo: String,
        contrasena: String,
        confirmacion: String,
        terminosAceptados: Boolean
    ) = Resultado(
        errorNombre = validarNombre(nombre),
        errorCorreo = validarCorreo(correo),
        errorContrasena = validarContrasena(contrasena),
        errorConfirmacion = validarConfirmacion(contrasena, confirmacion),
        errorTerminos = validarTerminos(terminosAceptados)
    )

    fun validarNombre(nombre: String): String? = when {
        nombre.isBlank() -> "Ingresa tu nombre completo"
        nombre.trim().length < 3 -> "El nombre es demasiado corto"
        else -> null
    }

    fun validarCorreo(correo: String): String? = when {
        correo.isBlank() -> "Ingresa tu correo electrónico"
        !PATRON_CORREO.matches(correo.trim()) -> "Ingresa un correo válido"
        else -> null
    }

    fun validarContrasena(contrasena: String): String? = when {
        contrasena.isBlank() -> "Ingresa una contraseña"
        contrasena.length < LARGO_MINIMO_CONTRASENA -> "Debe tener al menos $LARGO_MINIMO_CONTRASENA caracteres"
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
