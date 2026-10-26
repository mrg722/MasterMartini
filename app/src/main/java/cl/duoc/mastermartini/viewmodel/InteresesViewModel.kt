package cl.duoc.mastermartini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class EstadoIntereses(
    val seleccionados: Set<String> = emptySet(),
    val guardadoCompleto: Boolean = false,
    val guardando: Boolean = false,
    val errorGuardado: String? = null
) {
    val puedeContinuar: Boolean get() = seleccionados.isNotEmpty() && !guardando && !guardadoCompleto
}

class InteresesViewModel(aplicacion: Application) : AndroidViewModel(aplicacion) {

    private val preferencias = PreferenciasUsuario(aplicacion)

    private val estadoMutable = MutableStateFlow(EstadoIntereses())
    val estado: StateFlow<EstadoIntereses> = estadoMutable.asStateFlow()

    fun seleccionarInteres(id: String) {
        if (estadoMutable.value.guardando) return
        estadoMutable.update { estado ->
            val nuevos = if (id in estado.seleccionados) {
                estado.seleccionados - id
            } else {
                estado.seleccionados + id
            }
            estado.copy(seleccionados = nuevos)
        }
    }

    fun onContinuar() {
        if (!estadoMutable.value.puedeContinuar) return
        val interesesActuales = estadoMutable.value.seleccionados
        estadoMutable.update { it.copy(guardando = true, errorGuardado = null) }
        viewModelScope.launch {
            try {
                preferencias.guardarIntereses(interesesActuales)
                estadoMutable.update { it.copy(guardadoCompleto = true, guardando = false) }
            } catch (_: IOException) {
                estadoMutable.update { it.copy(guardando = false, errorGuardado = "No se pudieron guardar los intereses. Intenta nuevamente.") }
            }
        }
    }
}
