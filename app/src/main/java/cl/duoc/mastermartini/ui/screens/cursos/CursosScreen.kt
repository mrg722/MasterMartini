package cl.duoc.mastermartini.ui.screens.cursos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.data.model.cursosDisponibles
import cl.duoc.mastermartini.ui.components.MartiniBottomBar
import cl.duoc.mastermartini.ui.components.MartiniEstadoVacio
import cl.duoc.mastermartini.ui.components.MartiniTarjetaCurso
import cl.duoc.mastermartini.ui.components.MartiniTituloSeccion
import cl.duoc.mastermartini.ui.components.MartiniTopBar
import cl.duoc.mastermartini.ui.components.SeccionPrincipal

/**
 * Listado de cursos. Cada curso agrupa clases que se apoyan en los videos ya
 * verificados del recetario, de modo que no haya enlaces sin respaldo.
 */
@Composable
fun CursosScreen(
    alAbrirCurso: (String) -> Unit,
    alNavegar: (SeccionPrincipal) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MartiniTopBar(
                titulo = "Cursos de cocina",
                subtitulo = "Clases con video, de lo básico a lo horneado"
            )
        },
        bottomBar = {
            MartiniBottomBar(
                seccionActual = SeccionPrincipal.CURSOS,
                alNavegar = alNavegar
            )
        }
    ) { relleno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            item {
                MartiniTituloSeccion(
                    titulo = "Rutas de aprendizaje",
                    detalle = "${cursosDisponibles.size} cursos disponibles"
                )
            }

            if (cursosDisponibles.isEmpty()) {
                item {
                    MartiniEstadoVacio(
                        titulo = "Sin cursos por ahora",
                        mensaje = "Pronto se agregarán nuevas clases al catálogo.",
                        icono = Icons.AutoMirrored.Filled.MenuBook
                    )
                }
            } else {
                items(cursosDisponibles, key = { it.id }) { curso ->
                    MartiniTarjetaCurso(
                        titulo = curso.titulo,
                        descripcion = curso.descripcion,
                        resumen = curso.resumenCorto,
                        urlMiniatura = curso.clases.firstOrNull()?.video?.urlMiniatura,
                        alAbrir = { alAbrirCurso(curso.id) }
                    )
                }
            }
        }
    }
}
