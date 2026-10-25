package cl.duoc.mastermartini.data.model

data class Recipe(
    val id: String,
    val titulo: String,
    val minutos: Int,
    val dificultad: String,
    val esFavorita: Boolean = false
)

data class Category(
    val id: String,
    val titulo: String
)
