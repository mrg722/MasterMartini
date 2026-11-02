package cl.duoc.mastermartini.ui.screens.catalogo

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.mastermartini.ui.components.MartiniAviso
import cl.duoc.mastermartini.ui.components.MartiniBottomBar
import cl.duoc.mastermartini.ui.components.MartiniEstadoVacio
import cl.duoc.mastermartini.ui.components.MartiniTarjetaReceta
import cl.duoc.mastermartini.ui.components.MartiniTituloSeccion
import cl.duoc.mastermartini.ui.components.MartiniTopBar
import cl.duoc.mastermartini.ui.components.SeccionPrincipal
import cl.duoc.mastermartini.ui.theme.MasterMartiniTheme
import cl.duoc.mastermartini.viewmodel.CatalogoViewModel
import cl.duoc.mastermartini.viewmodel.FiltroCatalogo

/**
 * Catálogo de recetas. La lista se desplaza en vertical (una receta bajo la
 * otra) y cada tarjeta muestra la portada del video cuando existe.
 */
@Composable
fun CatalogScreen(
    alAbrirReceta: (String) -> Unit = {},
    alNavegar: (SeccionPrincipal) -> Unit = {},
    modelo: CatalogoViewModel = viewModel()
) {
    val estado by modelo.estado.collectAsState()
    val recetas = estado.recetasVisibles
    val saludo = estado.nombreUsuario.trim().substringBefore(' ').ifBlank { "" }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            MartiniTopBar(
                titulo = "Master Martini",
                subtitulo = if (saludo.isBlank()) {
                    "Recetas chilenas paso a paso"
                } else {
                    "Hola, $saludo · recetas para hoy"
                }
            )
        },
        bottomBar = {
            MartiniBottomBar(
                seccionActual = SeccionPrincipal.RECETAS,
                alNavegar = alNavegar
            )
        }
    ) { relleno ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 12.dp)
        ) {
            item {
                OutlinedTextField(
                    value = estado.busqueda,
                    onValueChange = modelo::cambiarBusqueda,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar receta o ingrediente") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (estado.busqueda.isNotBlank()) {
                            IconButton(onClick = modelo::limpiarBusqueda) {
                                Icon(Icons.Filled.Close, contentDescription = "Limpiar búsqueda")
                            }
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.primary,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FiltroCatalogo.entries.forEach { filtro ->
                        val seleccionado = filtro == estado.filtro
                        FilterChip(
                            selected = seleccionado,
                            onClick = { modelo.cambiarFiltro(filtro) },
                            label = { Text(filtro.etiqueta) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

            item {
                MartiniAviso(mensaje = estado.error, esError = true)
            }

            item {
                MartiniTituloSeccion(
                    titulo = when (estado.filtro) {
                        FiltroCatalogo.TODAS -> "Recetas del recetario"
                        FiltroCatalogo.CON_VIDEO -> "Recetas con video"
                        FiltroCatalogo.FAVORITAS -> "Tus recetas guardadas"
                    },
                    detalle = "${recetas.size} de ${estado.totalConVideo} recetas tienen video",
                    modifier = Modifier.animateContentSize()
                )
            }

            if (recetas.isEmpty()) {
                item {
                    MartiniEstadoVacio(
                        titulo = "Sin resultados",
                        mensaje = when (estado.filtro) {
                            FiltroCatalogo.FAVORITAS ->
                                "Toca el corazón de una receta para guardarla aquí."
                            else ->
                                "Prueba con otro nombre o revisa el filtro seleccionado."
                        },
                        icono = Icons.Filled.MenuBook
                    )
                }
            } else {
                items(recetas, key = { it.id }) { receta ->
                    MartiniTarjetaReceta(
                        titulo = receta.titulo,
                        descripcion = receta.descripcion,
                        minutos = receta.minutos,
                        dificultad = receta.dificultad,
                        urlMiniatura = receta.video?.urlMiniatura,
                        tieneVideo = receta.video != null,
                        esFavorita = estado.favoritas.contains(receta.id),
                        alAbrir = { alAbrirReceta(receta.id) },
                        alAlternarFavorita = { modelo.alternarFavorita(receta.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun CatalogScreenPreview() {
    MasterMartiniTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Text("Vista previa del catálogo", modifier = Modifier.padding(16.dp))
        }
    }
}
