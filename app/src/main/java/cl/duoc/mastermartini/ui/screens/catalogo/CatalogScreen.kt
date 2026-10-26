package cl.duoc.mastermartini.ui.screens.catalogo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import cl.duoc.mastermartini.ui.theme.MartiniCream
import cl.duoc.mastermartini.ui.theme.MartiniGreen
import cl.duoc.mastermartini.ui.theme.MartiniOrangeLight
import cl.duoc.mastermartini.ui.theme.MartiniTextSecondary
import cl.duoc.mastermartini.ui.theme.MasterMartiniTheme

private val categorias = listOf(
    Category("recetas", "Recetas"),
    Category("cursos", "Cursos"),
    Category("mis_recetas", "Mis recetas")
)

private val recetasDestacadas = listOf(
    Recipe("ensalada_mediterranea", "Ensalada mediterránea", 20, "Fácil"),
    Recipe("salmon_horno", "Salmón al horno con verduras", 35, "Fácil", esFavorita = true)
)

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    alAbrirReceta: (String) -> Unit = {},
    alAbrirCursos: () -> Unit = {},
    alAbrirPerfil: () -> Unit = {}
) {
    var busqueda by remember { mutableStateOf("") }
    var pestanaSeleccionada by remember { mutableStateOf(0) }
    val recetasFiltradas = remember(busqueda) {
        if (busqueda.isBlank()) recetasDestacadas
        else recetasDestacadas.filter { receta ->
            receta.titulo.contains(busqueda, ignoreCase = true)
        }
    }

    Scaffold(
        containerColor = MartiniCream,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Master Martini",
                        fontWeight = FontWeight.Bold,
                        color = MartiniGreen
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = MartiniGreen)
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Notifications, contentDescription = "Notificaciones", tint = MartiniGreen)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                val items = listOf(
                    "Inicio" to Icons.Filled.Home,
                    "Recetas" to Icons.Filled.Restaurant,
                    "Cursos" to Icons.Filled.MenuBook,
                    "Perfil" to Icons.Filled.Person
                )
                items.forEachIndexed { index, (label, icono) ->
                    NavigationBarItem(
                        selected = pestanaSeleccionada == index,
                        onClick = {
                            pestanaSeleccionada = index
                            when (index) {
                                2 -> alAbrirCursos()
                                3 -> alAbrirPerfil()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = icono,
                                contentDescription = label
                            )
                        },
                        label = { Text(label) },
                        colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                            selectedIconColor = MartiniGreen,
                            selectedTextColor = MartiniGreen,
                            indicatorColor = MartiniOrangeLight
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
                placeholder = { Text("Buscar recetas, cursos...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            SeccionTitulo(titulo = "Categorías")
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                categorias.forEach { categoria ->
                    CategoryChip(categoria = categoria, modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MartiniOrangeLight)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Cocina saludable después de los 50",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(MartiniGreen, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("→", color = androidx.compose.ui.graphics.Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            SeccionTitulo(titulo = "Recetas destacadas")
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
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text("Ver todas", color = MartiniGreen, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun CategoryChip(categoria: Category, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(76.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
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
            Text(categoria.titulo, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .background(MartiniOrangeLight),
                contentAlignment = Alignment.TopEnd
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = if (receta.esFavorita) Icons.Filled.Bookmark else Icons.Filled.FavoriteBorder,
                        contentDescription = "Guardar receta",
                        tint = MartiniGreen
                    )
                }
            }
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = receta.titulo,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.AccessTime,
                        contentDescription = null,
                        tint = MartiniTextSecondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${receta.minutos} min · ${receta.dificultad}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MartiniTextSecondary
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
