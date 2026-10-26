package cl.duoc.mastermartini.data.validation

import cl.duoc.mastermartini.data.model.PerfilUsuario
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.ResolverStyle

data class ErroresPerfil(
    val nombre: String? = null,
    val correo: String? = null,
    val fechaNacimiento: String? = null,
    val preferenciasAlimentarias: String? = null
) {
    val esValido: Boolean
        get() = nombre == null && correo == null && fechaNacimiento == null && preferenciasAlimentarias == null
}

object ValidadorPerfil {
    private val formatoFecha = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT)

    fun validarFecha(fecha: String, hoy: LocalDate = LocalDate.now()): String? {
        if (fecha.isBlank()) return "Ingresa tu fecha de nacimiento"
        if (!Regex("\\d{2}/\\d{2}/\\d{4}").matches(fecha.trim())) return "Usa el formato dd/mm/aaaa"
        return try {
            val nacimiento = LocalDate.parse(fecha.trim(), formatoFecha)
            when {
                nacimiento.year < 1 -> "Ingresa una fecha válida"
                nacimiento.isAfter(hoy) -> "La fecha no puede ser futura"
                else -> null
            }
        } catch (_: DateTimeParseException) {
            "Ingresa una fecha válida"
        }
    }

    fun validarPreferencias(preferencias: String): String? =
        if (preferencias.isBlank()) "Indica tus preferencias o escribe Sin restricciones" else null

    fun validar(perfil: PerfilUsuario) = ErroresPerfil(
        nombre = ValidadorRegistro.validarNombre(perfil.nombre),
        correo = ValidadorRegistro.validarCorreo(perfil.correo),
        fechaNacimiento = validarFecha(perfil.fechaNacimiento),
        preferenciasAlimentarias = validarPreferencias(perfil.preferenciasAlimentarias)
    )
}
