package cl.duoc.mastermartini.data.model

import cl.duoc.mastermartini.ui.screens.detalle.recetasDisponibles

/**
 * Clase de un curso. Cada clase se apoya en un video ya verificado del
 * recetario, de modo que no existan enlaces inventados: todos los
 * identificadores provienen de `recursos/videos-recetas/README.md`.
 *
 * @param recetaId receta del catálogo con la que se corresponde la clase,
 *   para poder saltar desde el curso a la ficha completa.
 */
data class ClaseCurso(
    val titulo: String,
    val resumen: String,
    val video: VideoReceta,
    val recetaId: String?
)

data class Curso(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val nivel: String,
    val clases: List<ClaseCurso>
) {
    val totalClases: Int get() = clases.size

    /** Texto corto para listados: "5 clases · Nivel básico". */
    val resumenCorto: String
        get() = "$totalClases ${if (totalClases == 1) "clase" else "clases"} · $nivel"
}

private fun claseDesdeReceta(recetaId: String, titulo: String, resumen: String): ClaseCurso? {
    val receta = recetasDisponibles.firstOrNull { it.id == recetaId } ?: return null
    val video = receta.video ?: return null
    return ClaseCurso(titulo = titulo, resumen = resumen, video = video, recetaId = receta.id)
}

/**
 * Catálogo de cursos. Se construye a partir de las recetas existentes para que
 * cada clase tenga un video real y alineado con la preparación que enseña.
 */
val cursosDisponibles: List<Curso> = listOfNotNull(
    Curso(
        id = "chilena_tradicional",
        titulo = "Cocina chilena tradicional",
        descripcion = "Recorrido por cinco preparaciones clásicas, desde el pino hasta los guisos de olla.",
        nivel = "Nivel básico",
        clases = listOfNotNull(
            claseDesdeReceta("empanadas_pino", "Empanadas de pino", "Masa, relleno y horneado paso a paso."),
            claseDesdeReceta("pastel_choclo", "Pastel de choclo", "Pino de carne y pollo bajo una cubierta de choclo molido."),
            claseDesdeReceta("porotos_granados", "Porotos granados con mazamorra", "Porotos frescos, zapallo y choclo molido con albahaca."),
            claseDesdeReceta("cazuela_vacuno", "Cazuela de vacuno", "Caldo de carne con papas, zapallo y verduras."),
            claseDesdeReceta("charquican", "Charquicán", "Guiso de carne con papas, zapallo y choclo.")
        )
    ).takeIf { it.clases.isNotEmpty() },
    Curso(
        id = "guisos_olla",
        titulo = "Guisos y cazuelas de olla",
        descripcion = "Preparaciones de olla para días fríos, con verduras de temporada y cocciones largas.",
        nivel = "Nivel básico",
        clases = listOfNotNull(
            claseDesdeReceta("porotos_granados", "Porotos granados", "Cocción de los porotos y punto de la mazamorra."),
            claseDesdeReceta("cazuela_vacuno", "Cazuela de vacuno", "Orden de la cocción para que el caldo quede claro."),
            claseDesdeReceta("charquican", "Charquicán", "Sofrito, verduras y punto final del guiso.")
        )
    ).takeIf { it.clases.isNotEmpty() },
    Curso(
        id = "horneados",
        titulo = "Horneados chilenos",
        descripcion = "Dos preparaciones al horno: el armado de las empanadas y el pastel de choclo.",
        nivel = "Nivel intermedio",
        clases = listOfNotNull(
            claseDesdeReceta("empanadas_pino", "Armado y horneado de empanadas", "Cierre de la masa y punto de horno."),
            claseDesdeReceta("pastel_choclo", "Pastel de choclo", "Armado por capas y gratinado de la cubierta.")
        )
    ).takeIf { it.clases.isNotEmpty() },
    run {
        val empanadas = recetasDisponibles.firstOrNull { it.id == "empanadas_pino" }
        val completo = empanadas?.video
        val clip = empanadas?.videoComplementario
        if (completo == null) {
            null
        } else {
            Curso(
                id = "tecnica_pino",
                titulo = "Técnica: el pino paso a paso",
                descripcion = "El relleno base de la cocina chilena, con la receta completa y un repaso breve.",
                nivel = "Nivel básico",
                clases = listOfNotNull(
                    ClaseCurso(
                        titulo = "Receta completa",
                        resumen = "Masa, pino y horneado en el tutorial completo.",
                        video = completo,
                        recetaId = "empanadas_pino"
                    ),
                    clip?.let {
                        ClaseCurso(
                            titulo = "Repaso rápido del pino",
                            resumen = "Clip breve de apoyo; no reemplaza la receta completa.",
                            video = it,
                            recetaId = "empanadas_pino"
                        )
                    }
                )
            )
        }
    }
)

/** Busca un curso por su identificador; `null` si no existe. */
fun buscarCurso(cursoId: String?): Curso? =
    cursosDisponibles.firstOrNull { it.id == cursoId }
