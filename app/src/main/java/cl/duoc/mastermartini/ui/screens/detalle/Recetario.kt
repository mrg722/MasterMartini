package cl.duoc.mastermartini.ui.screens.detalle

data class RecetaContenido(
    val id: String,
    val titulo: String,
    val minutos: Int,
    val dificultad: String,
    val descripcion: String,
    val ingredientes: List<String>,
    val pasos: List<Pair<String, String>>
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
        )
    )
)
