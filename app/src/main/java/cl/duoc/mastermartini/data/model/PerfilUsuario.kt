package cl.duoc.mastermartini.data.model

data class PerfilUsuario(
    val nombre: String = "",
    val correo: String = "",
    val fechaNacimiento: String = "",
    val preferenciasAlimentarias: String = "Sin restricciones"
)
