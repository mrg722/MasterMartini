package cl.duoc.mastermartini.ui.navigation

object MartiniRoutes {
    const val SPLASH = "splash"
    const val REGISTER = "register"
    const val INTERESTS = "interests"
    const val CATALOG = "catalog"
    const val RECETAS = "recetas"
    const val CURSOS = "cursos"
    const val PERFIL = "perfil"
    const val EDITAR_PERFIL = "editar_perfil"
    const val DETALLE = "detalle/{recetaId}"
    const val PREPARACION = "preparacion/{recetaId}"

    fun detalle(recetaId: String) = "detalle/$recetaId"
    fun preparacion(recetaId: String) = "preparacion/$recetaId"
}
