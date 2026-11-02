package cl.duoc.mastermartini.ui.screens.perfil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.data.model.PerfilUsuario
import cl.duoc.mastermartini.ui.components.MartiniFilaAjuste
import cl.duoc.mastermartini.ui.components.MartiniGrupoAjustes
import cl.duoc.mastermartini.ui.components.MartiniBottomBar
import cl.duoc.mastermartini.ui.components.MartiniTituloSeccion
import cl.duoc.mastermartini.ui.components.MartiniTopBar
import cl.duoc.mastermartini.ui.components.SeccionPrincipal

/**
 * Perfil de la persona: datos de la cuenta, resumen de actividad y accesos a
 * editar el perfil, revisar intereses y abrir los ajustes.
 */
@Composable
fun PerfilScreen(
    alEditar: () -> Unit,
    alAbrirAjustes: () -> Unit,
    alAbrirFavoritas: () -> Unit,
    alAbrirIntereses: () -> Unit,
    alNavegar: (SeccionPrincipal) -> Unit
) {
    val contexto = LocalContext.current
    val preferencias = remember(contexto) { PreferenciasUsuario(contexto.applicationContext) }
    val perfil by preferencias.perfilUsuario.collectAsState(initial = PerfilUsuario())
    val intereses by preferencias.interesesUsuario.collectAsState(initial = emptySet())
    val favoritas by preferencias.recetasFavoritas.collectAsState(initial = emptySet())

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MartiniTopBar(
                titulo = "Mi perfil",
                acciones = {
                    IconButton(onClick = alAbrirAjustes) {
                        Icon(Icons.Filled.Settings, contentDescription = "Abrir ajustes")
                    }
                }
            )
        },
        bottomBar = {
            MartiniBottomBar(
                seccionActual = SeccionPrincipal.PERFIL,
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
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FotoPerfil(uriTexto = perfil.fotoUri)
                        Text(
                            text = perfil.nombre.ifBlank { "Invitado" },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (perfil.correo.isNotBlank()) {
                            Text(
                                text = perfil.correo,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            DatoResumen(valor = favoritas.size.toString(), etiqueta = "Guardadas")
                            DatoResumen(valor = intereses.size.toString(), etiqueta = "Intereses")
                        }
                    }
                }
            }

            item {
                MartiniGrupoAjustes(titulo = "Tu cuenta") {
                    MartiniFilaAjuste(
                        titulo = "Editar perfil",
                        descripcion = "Nombre, correo, foto y preferencias",
                        icono = Icons.Filled.Person,
                        alPulsar = alEditar
                    )
                    MartiniFilaAjuste(
                        titulo = "Mis recetas guardadas",
                        descripcion = if (favoritas.isEmpty()) {
                            "Aún no guardas recetas"
                        } else {
                            "${favoritas.size} recetas en favoritas"
                        },
                        icono = Icons.Filled.Favorite,
                        alPulsar = alAbrirFavoritas
                    )
                    MartiniFilaAjuste(
                        titulo = "Mis intereses",
                        descripcion = if (intereses.isEmpty()) {
                            "Elige los temas que te interesan"
                        } else {
                            intereses.sorted().joinToString(" · ") { interes ->
                                interes.replace('_', ' ').replaceFirstChar(Char::uppercase)
                            }
                        },
                        icono = Icons.Filled.Category,
                        alPulsar = alAbrirIntereses
                    )
                    MartiniFilaAjuste(
                        titulo = "Ajustes",
                        descripcion = "Apariencia, notificaciones y cuenta",
                        icono = Icons.Filled.Settings,
                        alPulsar = alAbrirAjustes
                    )
                }
            }

            item {
                MartiniTituloSeccion(
                    titulo = "Sobre la aplicación",
                    detalle = "Master Martini · recetario chileno con videos de cada preparación"
                )
            }

            item {
                MartiniGrupoAjustes {
                    MartiniFilaAjuste(
                        titulo = "Cursos disponibles",
                        descripcion = "Clases con video, desde guisos a horneados",
                        icono = Icons.AutoMirrored.Filled.MenuBook,
                        alPulsar = { alNavegar(SeccionPrincipal.CURSOS) }
                    )
                }
            }
        }
    }
}

@Composable
private fun DatoResumen(valor: String, etiqueta: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = valor,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
