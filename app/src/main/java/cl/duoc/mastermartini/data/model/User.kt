package cl.duoc.mastermartini.data.model

data class User(
    val nombreCompleto: String,
    val correo: String,
    val intereses: List<String> = emptyList()
)
