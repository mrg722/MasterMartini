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
    ),
    RecetaContenido(
        id = "queque_casero",
        titulo = "Queque casero",
        minutos = 60,
        dificultad = "Fácil",
        descripcion = "Un queque tradicional para practicar el batido y la incorporación de ingredientes secos.",
        ingredientes = listOf(
            "3 huevos",
            "1 taza de azúcar",
            "1 taza de harina sin polvos de hornear",
            "2 cucharaditas de polvos de hornear",
            "1/2 taza de leche",
            "1/2 taza de aceite o mantequilla derretida",
            "1 cucharadita de esencia de vainilla",
            "1 pizca de sal"
        ),
        pasos = listOf(
            "Prepara el horno" to "Precalienta el horno a 180 °C y engrasa un molde para queque.",
            "Bate los huevos" to "Bate los huevos con el azúcar hasta obtener una mezcla clara y espumosa.",
            "Agrega los ingredientes líquidos" to "Incorpora el aceite o la mantequilla derretida y la vainilla.",
            "Forma la mezcla" to "Añade la harina, los polvos de hornear y la sal alternando con la leche. Mezcla solo hasta integrar.",
            "Hornea" to "Vierte la preparación en el molde y hornea entre 40 y 45 minutos. Comprueba la cocción con un palillo.",
            "Enfría y desmolda" to "Deja reposar unos minutos, enfría y desmolda con cuidado."
        )
    ),
    RecetaContenido(
        id = "pan_amasado",
        titulo = "Pan amasado",
        minutos = 45,
        dificultad = "Fácil",
        descripcion = "Pan casero chileno de preparación sencilla, con una corteza dorada y una miga suave.",
        ingredientes = listOf(
            "1 kg de harina sin polvos de hornear",
            "2 cucharaditas de sal",
            "2 cucharaditas de polvos de hornear",
            "100 g de manteca o mantequilla derretida",
            "Aproximadamente 2 tazas de agua tibia"
        ),
        pasos = listOf(
            "Mezcla los ingredientes secos" to "En un recipiente amplio, mezcla la harina, la sal y los polvos de hornear.",
            "Incorpora la materia grasa" to "Agrega la manteca o mantequilla derretida e intégrala con las manos.",
            "Agrega el agua" to "Vierte el agua tibia poco a poco hasta conseguir una masa suave que se pueda trabajar.",
            "Amasa" to "Amasa durante 8 a 10 minutos, hasta que la mezcla esté homogénea.",
            "Forma los panes" to "Divide la masa en porciones, forma bollos, aplánalos ligeramente y pínchalos con un tenedor.",
            "Hornea" to "Hornea a 200 °C durante 20 a 25 minutos, hasta que los panes estén dorados."
        )
    ),
    RecetaContenido(
        id = "muffins_avena_platano",
        titulo = "Muffins de avena y plátano",
        minutos = 25,
        dificultad = "Fácil",
        descripcion = "Muffins de avena y plátano que se pueden preparar con una licuadora y pocos pasos.",
        ingredientes = listOf(
            "2 tazas de avena",
            "2 plátanos maduros grandes",
            "2 huevos",
            "1 taza de yogur griego natural",
            "2 cucharadas de miel",
            "1 1/2 cucharadita de polvos de hornear",
            "1/2 cucharadita de bicarbonato",
            "1 pizca de sal",
            "Opcional: nueces, almendras o chips de chocolate"
        ),
        pasos = listOf(
            "Prepara el horno y los moldes" to "Precalienta el horno a 180 °C y prepara una bandeja para muffins con moldes.",
            "Licúa la mezcla" to "Procesa la avena, los plátanos, los huevos, el yogur y la miel hasta obtener una mezcla homogénea.",
            "Añade los ingredientes secos" to "Incorpora los polvos de hornear, el bicarbonato y la sal. Agrega los opcionales con una espátula.",
            "Reparte la mezcla" to "Distribuye la preparación en los moldes sin llenarlos por completo, porque crecerá durante el horneado.",
            "Hornea y comprueba" to "Hornea alrededor de 15 minutos. Comprueba con un palillo que el centro esté cocido y deja enfriar."
        )
    ),
    RecetaContenido(
        id = "brownie_chocolate",
        titulo = "Brownie de chocolate",
        minutos = 50,
        dificultad = "Fácil",
        descripcion = "Brownie de chocolate con interior húmedo, ideal para una preparación básica de pastelería.",
        ingredientes = listOf(
            "200 g de chocolate semiamargo",
            "115 g de mantequilla sin sal",
            "3 cucharadas de cacao amargo",
            "3 huevos",
            "1 1/4 taza de azúcar",
            "1 taza de harina sin polvos de hornear"
        ),
        pasos = listOf(
            "Prepara el molde" to "Precalienta el horno a 180 °C y cubre un molde cuadrado o rectangular con papel para hornear.",
            "Derrite el chocolate" to "Derrite el chocolate junto con la mantequilla y mezcla hasta que quede homogéneo.",
            "Bate huevos y azúcar" to "En otro recipiente, bate los huevos con el azúcar hasta integrar.",
            "Une las mezclas" to "Incorpora el chocolate derretido a los huevos y mezcla con cuidado.",
            "Agrega los secos" to "Añade el cacao y la harina, mezclando solo hasta que no queden partes secas.",
            "Hornea y enfría" to "Vierte en el molde y hornea entre 20 y 25 minutos. Deja enfriar antes de cortar en cuadrados."
        )
    ),
    RecetaContenido(
        id = "galletas_avena_cacao",
        titulo = "Galletas de avena y cacao",
        minutos = 50,
        dificultad = "Fácil",
        descripcion = "Galletas sencillas con avena y cacao para combinar ingredientes de despensa y practicar el horneado.",
        ingredientes = listOf(
            "1 taza de harina de avena",
            "1 taza de avena",
            "1 huevo",
            "3 cucharadas de miel",
            "1 cucharada de cacao amargo",
            "1 cucharadita de polvos de hornear",
            "2 cucharadas de aceite vegetal",
            "2 a 3 cucharadas de leche, según la textura"
        ),
        pasos = listOf(
            "Prepara la bandeja" to "Precalienta el horno a 180 °C y prepara una bandeja con papel para hornear.",
            "Mezcla los ingredientes húmedos" to "Bate el huevo con la miel, el aceite y la leche.",
            "Incorpora avena y cacao" to "Añade la harina de avena, la avena, el cacao y los polvos de hornear. Mezcla hasta formar una masa que se pueda porcionar.",
            "Forma las galletas" to "Arma pequeñas porciones y aplástalas ligeramente sobre la bandeja, dejando espacio entre ellas.",
            "Hornea y enfría" to "Hornea de 12 a 15 minutos, hasta que los bordes estén firmes. Déjalas enfriar antes de moverlas."
        )
    )
)
