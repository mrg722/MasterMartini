package cl.duoc.mastermartini.data.model

/**
 * Video de YouTube asociado a una receta o a una clase de un curso.
 *
 * Los videos NO se descargan ni se alojan en el proyecto: solo se guardan los
 * metadatos y el identificador público del video, y la reproducción ocurre
 * contra YouTube. Cada identificador que aparece en la app está documentado y
 * verificado en `recursos/videos-recetas/README.md`.
 *
 * @param youtubeId identificador público del video (lo que viene después de `watch?v=`).
 * @param titulo título original publicado por el canal.
 * @param canal canal que publicó el contenido, para dar el crédito correspondiente.
 * @param duracion duración declarada; se usa [DURACION_NO_VERIFICADA] cuando la
 *   fuente consultada no permitió confirmarla.
 */
data class VideoReceta(
    val youtubeId: String,
    val titulo: String,
    val canal: String,
    val duracion: String = DURACION_NO_VERIFICADA
) {
    /** Enlace para abrir el video en la aplicación de YouTube o en el navegador. */
    val urlPublica: String
        get() = "https://www.youtube.com/watch?v=$youtubeId"

    /** Enlace del reproductor embebido que usa la pantalla interna de reproducción. */
    val urlEmbebida: String
        get() = "https://www.youtube-nocookie.com/embed/$youtubeId?rel=0&playsinline=1"

    /** Miniatura oficial del video, usada como portada de la receta. */
    val urlMiniatura: String
        get() = "https://img.youtube.com/vi/$youtubeId/hqdefault.jpg"

    /** `true` cuando la duración pudo confirmarse en la fuente consultada. */
    val duracionVerificada: Boolean
        get() = duracion != DURACION_NO_VERIFICADA

    companion object {
        const val DURACION_NO_VERIFICADA = "Duración no verificada"
    }
}
