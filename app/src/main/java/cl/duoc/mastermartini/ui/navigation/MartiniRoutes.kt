package cl.duoc.mastermartini.ui.navigation

object MartiniRoutes {
    const val SPLASH = "splash"
    const val REGISTER = "register"
    const val INTERESTS = "interests"
    const val CATALOG = "catalog"
    const val CURSOS = "cursos"
    const val PERFIL = "perfil"
    const val EDITAR_PERFIL = "editar_perfil"
    const val AJUSTES = "ajustes"
    const val DETALLE = "detalle/{recetaId}"
    const val PREPARACION = "preparacion/{recetaId}"
    const val DETALLE_CURSO = "curso/{cursoId}"
    const val VIDEO = "video/{videoId}"

    fun detalle(recetaId: String) = "detalle/$recetaId"
    fun preparacion(recetaId: String) = "preparacion/$recetaId"
    fun curso(cursoId: String) = "curso/$cursoId"
    fun video(videoId: String) = "video/$videoId"
}
