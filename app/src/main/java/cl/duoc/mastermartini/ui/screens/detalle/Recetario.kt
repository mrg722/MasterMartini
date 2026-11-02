package cl.duoc.mastermartini.ui.screens.detalle

import cl.duoc.mastermartini.data.model.VideoReceta

data class RecetaContenido(
    val id: String,
    val titulo: String,
    val minutos: Int,
    val dificultad: String,
    val descripcion: String,
    val ingredientes: List<String>,
    val pasos: List<Pair<String, String>>,
    /**
     * Video principal de la preparación. La correspondencia entre cada receta y
     * su video está documentada y verificada en `recursos/videos-recetas/README.md`.
     */
    val video: VideoReceta? = null,
    /**
     * Clip breve complementario. No reemplaza al video principal: muestra solo
     * una parte de la preparación.
     */
    val videoComplementario: VideoReceta? = null
)

val recetasDisponibles = listOf(
    RecetaContenido(
        id = "empanadas_pino",
        titulo = "Empanadas de pino",
        minutos = 70,
        dificultad = "Fácil",
        descripcion = "Masa horneada rellena de carne, cebolla, huevo y aceituna.",
        ingredientes = listOf("Masa para empanadas", "Carne picada", "Cebolla", "Huevo duro", "Aceitunas", "Pasas"),
        pasos = listOf(
            "Prepara el pino" to "Cocina la cebolla y la carne hasta que estén bien cocidas.",
            "Rellena la masa" to "Agrega pino, huevo, aceituna y pasas a cada disco de masa.",
            "Hornea" to "Cierra las empanadas y hornéalas hasta que estén doradas."
        ),
        video = VideoReceta(
            youtubeId = "BaqZU8LIClc",
            titulo = "Empanada de pino chilena · receta completa",
            canal = "Javiera Reymont",
            duracion = "13 min"
        ),
        videoComplementario = VideoReceta(
            youtubeId = "OTsHlTlPcAE",
            titulo = "Preparación del pino en 1 minuto",
            canal = "Ricky Cocina",
            duracion = "1 min"
        )
    ),
    RecetaContenido(
        id = "porotos_granados",
        titulo = "Porotos granados con mazamorra",
        minutos = 50,
        dificultad = "Fácil",
        descripcion = "Guiso de porotos frescos, zapallo y choclo molido.",
        ingredientes = listOf("Porotos granados", "Zapallo", "Choclo", "Albahaca", "Cebolla", "Ajo"),
        pasos = listOf(
            "Cocina los porotos" to "Hierve los porotos con zapallo hasta que estén blandos.",
            "Prepara la mazamorra" to "Muele el choclo y mézclalo con albahaca.",
            "Integra y sirve" to "Agrega la mazamorra a la olla, cocina unos minutos y sirve caliente."
        ),
        video = VideoReceta(
            youtubeId = "PHTveSdrBdk",
            titulo = "Porotos granados con mazamorra (cocina chilena)",
            canal = "Slucook"
        )
    ),
    RecetaContenido(
        id = "pastel_choclo",
        titulo = "Pastel de choclo",
        minutos = 75,
        dificultad = "Media",
        descripcion = "Preparación horneada de choclo molido sobre pino de carne y pollo.",
        ingredientes = listOf("Choclo", "Carne picada", "Pollo", "Cebolla", "Huevo duro", "Aceitunas"),
        pasos = listOf(
            "Prepara el pino" to "Cocina carne, cebolla y aliños hasta formar el relleno.",
            "Muele el choclo" to "Procesa el choclo con albahaca hasta obtener una pasta.",
            "Arma el pastel" to "Dispón el relleno en una fuente y cúbrelo con choclo.",
            "Hornea" to "Hornea hasta que la cubierta quede dorada."
        ),
        video = VideoReceta(
            youtubeId = "ZV4WS3AlAS0",
            titulo = "Pastel de choclo",
            canal = "Jon Michelena"
        )
    ),
    RecetaContenido(
        id = "cazuela_vacuno",
        titulo = "Cazuela de vacuno",
        minutos = 60,
        dificultad = "Fácil",
        descripcion = "Caldo de carne con papas, zapallo, choclo y verduras.",
        ingredientes = listOf("Asado de tira", "Papas", "Zapallo", "Choclo", "Zanahoria", "Porotos verdes"),
        pasos = listOf(
            "Cocina la carne" to "Hierve la carne con cebolla y aliños hasta formar un caldo.",
            "Agrega verduras" to "Incorpora papas, zapallo, zanahoria y choclo.",
            "Termina la cocción" to "Añade porotos verdes y cocina hasta que todo esté blando."
        ),
        video = VideoReceta(
            youtubeId = "Bz2A99mpa8A",
            titulo = "Cazuela chilena de vacuno en olla a presión",
            canal = "Magefesa MGF"
        )
    ),
    RecetaContenido(
        id = "charquican",
        titulo = "Charquicán",
        minutos = 45,
        dificultad = "Fácil",
        descripcion = "Guiso de carne, papas, zapallo y choclo.",
        ingredientes = listOf("Carne en cubos", "Papas", "Zapallo", "Choclo", "Cebolla", "Ajo"),
        pasos = listOf(
            "Sofríe la carne" to "Cocina la carne con cebolla y ajo.",
            "Incorpora verduras" to "Agrega papas, zapallo y caldo hasta que hiervan.",
            "Termina el guiso" to "Añade el choclo y cocina hasta que las verduras estén tiernas."
        ),
        video = VideoReceta(
            youtubeId = "Bgob5Lje4Js",
            titulo = "Receta fácil de charquicán",
            canal = "Silvana Cocina",
            duracion = "Menos de 6 min"
        )
    )
)

/** Busca una receta por su identificador; `null` si no existe. */
fun buscarReceta(recetaId: String?): RecetaContenido? =
    recetasDisponibles.firstOrNull { it.id == recetaId }

/** Busca un video por el id de YouTube dentro de todo el recetario. */
fun buscarVideoPorId(youtubeId: String?): VideoReceta? {
    if (youtubeId.isNullOrBlank()) return null
    recetasDisponibles.forEach { receta ->
        receta.video?.let { if (it.youtubeId == youtubeId) return it }
        receta.videoComplementario?.let { if (it.youtubeId == youtubeId) return it }
    }
    return null
}
