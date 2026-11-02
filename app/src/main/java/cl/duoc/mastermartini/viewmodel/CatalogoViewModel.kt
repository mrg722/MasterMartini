package cl.duoc.mastermartini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.ui.screens.detalle.RecetaContenido
import cl.duoc.mastermartini.ui.screens.detalle.recetasDisponibles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/** Filtros disponibles sobre el catálogo de recetas. */
enum class FiltroCatalogo(val etiqueta: String) {
    TODAS("Todas"),
    CON_VIDEO("Con video"),
    FAVORITAS("Favoritas")
}

data class EstadoCatalogo(
    val busqueda: String = "",
    val filtro: FiltroCatalogo = FiltroCatalogo.TODAS,
    val favoritas: Set<String> = emptySet(),
    val nombreUsuario: String = "",
    val error: String? = null
) {
    /**
     * Recetas que se muestran, aplicando primero el filtro y después la
     * búsqueda por título o ingredientes.
     */
    val recetasVisibles: List<RecetaContenido>
        get() {
            val porFiltro = when (filtro) {
                FiltroCatalogo.TODAS -> recetasDisponibles
                FiltroCatalogo.CON_VIDEO -> recetasDisponibles.filter { it.video != null }
                FiltroCatalogo.FAVORITAS -> recetasDisponibles.filter { favoritas.contains(it.id) }
            }
            val texto = busqueda.trim()
            if (texto.isBlank()) return porFiltro
            return porFiltro.filter { receta ->
                receta.titulo.contains(texto, ignoreCase = true) ||
                    receta.descripcion.contains(texto, ignoreCase = true) ||
                    receta.ingredientes.any { it.contains(texto, ignoreCase = true) }
            }
        }

    val totalConVideo: Int
        get() = recetasDisponibles.count { it.video != null }
}

class CatalogoViewModel(aplicacion: Application) : AndroidViewModel(aplicacion) {

    private val preferencias = PreferenciasUsuario(aplicacion)

    private val estadoMutable = MutableStateFlow(EstadoCatalogo())
    val estado: StateFlow<EstadoCatalogo> = estadoMutable.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                preferencias.recetasFavoritas,
                preferencias.nombreUsuario
            ) { favoritas, nombre -> favoritas to nombre }
                .collect { (favoritas, nombre) ->
                    estadoMutable.update { it.copy(favoritas = favoritas, nombreUsuario = nombre) }
                }
        }
    }

    fun cambiarBusqueda(valor: String) {
        estadoMutable.update { it.copy(busqueda = valor) }
    }

    fun limpiarBusqueda() {
        estadoMutable.update { it.copy(busqueda = "") }
    }

    fun cambiarFiltro(filtro: FiltroCatalogo) {
        estadoMutable.update { it.copy(filtro = filtro) }
    }

    fun alternarFavorita(recetaId: String) {
        viewModelScope.launch {
            try {
                preferencias.alternarFavorita(recetaId)
            } catch (_: IOException) {
                estadoMutable.update { it.copy(error = "No se pudo guardar la receta en favoritas.") }
            }
        }
    }

    fun consumirError() {
        estadoMutable.update { it.copy(error = null) }
    }
}
