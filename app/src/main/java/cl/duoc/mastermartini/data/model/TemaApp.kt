package cl.duoc.mastermartini.data.model

/**
 * Preferencia de apariencia elegida por la persona en Ajustes.
 *
 * [SISTEMA] deja que mande la configuración del teléfono; las otras dos fuerzan
 * el modo, lo que ayuda a quienes leen mejor con un contraste determinado.
 */
enum class TemaApp(val clave: String, val etiqueta: String) {
    SISTEMA("sistema", "Según el sistema"),
    CLARO("claro", "Siempre claro"),
    OSCURO("oscuro", "Siempre oscuro");

    companion object {
        fun desdeClave(clave: String?): TemaApp =
            entries.firstOrNull { it.clave == clave } ?: SISTEMA
    }
}
