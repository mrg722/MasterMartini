package cl.duoc.mastermartini.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class InterestsUiState(
    val seleccionados: Set<String> = emptySet(),
    val guardadoCompleto: Boolean = false
) {
    val puedeContinuar: Boolean get() = seleccionados.isNotEmpty()
}

class InterestsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InterestsUiState())
    val uiState: StateFlow<InterestsUiState> = _uiState.asStateFlow()

    fun onToggleInterest(id: String) {
        _uiState.update { estado ->
            val nuevos = if (id in estado.seleccionados) {
                estado.seleccionados - id
            } else {
                estado.seleccionados + id
            }
            estado.copy(seleccionados = nuevos)
        }
    }

    fun onContinuar() {
        if (!_uiState.value.puedeContinuar) return
        _uiState.update { it.copy(guardadoCompleto = true) }
    }
}
