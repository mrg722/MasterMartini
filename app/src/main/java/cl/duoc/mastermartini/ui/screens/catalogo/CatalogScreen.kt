package cl.duoc.mastermartini.ui.screens.catalogo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.data.model.Category
import cl.duoc.mastermartini.data.model.Recipe
import cl.duoc.mastermartini.ui.screens.detalle.recetasDisponibles
import cl.duoc.mastermartini.ui.theme.MartiniGreen
import cl.duoc.mastermartini.ui.theme.MasterMartiniTheme

private val categorias = listOf(
    Category("recetas", "Recetas"),
    Category("cursos", "Cursos")
)

private val recetasDestacadas = recetasDisponibles.map { receta ->
    Recipe(receta.id, receta.titulo, receta.minutos, receta.dificultad)
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    alAbrirReceta: (String) -> Unit = {},
    alAbrirCursos: () -> Unit = {},
    alAbrirPerfil: () -> Unit = {}
) {
    var busqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("recetas") }
    val recetasFiltradas = remember(busqueda) {
        if (busqueda.isBlank()) recetasDestacadas
        else recetasDestacadas.filter { receta ->
            receta.titulo.contains(busqueda, ignoreCase = true)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Master Martini",
                        fontWeight = FontWeight.Bold,
                        color = MartiniGreen
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MartiniGreen
                )
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                val elementosNavegacion = listOf(
                    "Recetas" to Icons.Filled.Restaurant,
                    "Cursos" to Icons.AutoMirrored.Filled.MenuBook,
                    "Perfil" to Icons.Filled.Person
                )
                elementosNavegacion.forEachIndexed { indice, (etiqueta, icono) ->
                    NavigationBarItem(
                        selected = indice == 0,
                        onClick = {
                            when (indice) {
                                0 -> {
                                    categoriaSeleccionada = "recetas"
                                    busqueda = ""
                                }
                                1 -> alAbrirCursos()
                                2 -> alAbrirPerfil()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = icono,
                                contentDescription = etiqueta
                            )
                        },
                        label = { Text(etiqueta) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MartiniGreen,
                            selectedTextColor = MartiniGreen,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Buscar recetas", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null, tint = MartiniGreen)
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    cursorColor = MartiniGreen,
                    focusedBorderColor = MartiniGreen,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            SeccionTitulo(titulo = "Categorías")
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                categorias.forEach { categoria ->
                    CategoryChip(
                        categoria = categoria,
                        seleccionada = categoria.id == categoriaSeleccionada,
                        alSeleccionar = {
                            if (categoria.id == "cursos") alAbrirCursos()
                            else categoriaSeleccionada = categoria.id
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            SeccionTitulo(
                titulo = "Recetas destacadas"
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                items(recetasFiltradas) { receta ->
                    RecipeCard(receta, alAbrirReceta)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SeccionTitulo(titulo: String) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun CategoryChip(
    categoria: Category,
    seleccionada: Boolean,
    alSeleccionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = alSeleccionar,
        modifier = modifier.height(76.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionada) {
                MaterialTheme.colorScheme.secondaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.Restaurant, contentDescription = null, tint = MartiniGreen)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = categoria.titulo,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun RecipeCard(receta: Recipe, alAbrirReceta: (String) -> Unit) {
    Card(
        onClick = { alAbrirReceta(receta.id) },
        modifier = Modifier.width(170.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = receta.titulo,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.AccessTime,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${receta.minutos} min · ${receta.dificultad}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
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
        CatalogScreen()
    }
}
