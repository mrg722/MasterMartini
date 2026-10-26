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
        id = "ensalada_mediterranea",
        titulo = "Ensalada mediterránea",
        minutos = 20,
        dificultad = "Fácil",
        descripcion = "Una preparación fresca con verduras y aceite de oliva.",
        ingredientes = listOf("2 tomates", "1 pepino", "1 pimentón", "Aceite de oliva"),
        pasos = listOf(
            "Lava las verduras" to "Lava bien los tomates, el pepino y el pimentón.",
            "Corta las verduras" to "Corta las verduras en trozos medianos.",
            "Mezcla y sirve" to "Mezcla en un recipiente y añade aceite de oliva."
        )
    ),
    RecetaContenido(
        id = "salmon_horno",
        titulo = "Salmón al horno con verduras",
        minutos = 35,
        dificultad = "Fácil",
        descripcion = "Una receta rica en omega 3 para una alimentación equilibrada.",
        ingredientes = listOf("2 filetes de salmón", "1 zanahoria", "1 zucchini", "1 pimentón rojo"),
        pasos = listOf(
            "Precalienta el horno" to "Precalienta el horno a 180 °C.",
            "Corta las verduras" to "Lava y corta el zucchini, la zanahoria y el pimentón en trozos medianos.",
            "Prepara la bandeja" to "Coloca el salmón y las verduras en una bandeja para horno.",
            "Hornea" to "Hornea durante 25 minutos o hasta que el salmón esté cocido.",
            "Comprueba la cocción" to "Verifica que el salmón esté bien cocido antes de servir.",
            "Sirve" to "Sirve el salmón con las verduras calientes."
        )
    )
)
