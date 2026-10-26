package cl.duoc.mastermartini.ui.screens.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.ui.theme.MartiniCream
import cl.duoc.mastermartini.ui.theme.MartiniTextSecondary
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton

@Composable
fun PerfilScreen(alEditar: () -> Unit) {
    val contexto = LocalContext.current
    val preferencias = remember(contexto) { PreferenciasUsuario(contexto.applicationContext) }
    val nombre by preferencias.nombreUsuario.collectAsState(initial = "")
    val correo by preferencias.correoUsuario.collectAsState(initial = "")
    val intereses by preferencias.interesesUsuario.collectAsState(initial = emptySet())

    Column(Modifier.fillMaxSize().background(MartiniCream).padding(20.dp)) {
        Text("Mi perfil", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(nombre.ifBlank { "Invitado" }, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                if (correo.isNotBlank()) Text(correo, color = MartiniTextSecondary)
            }
        }
        Spacer(Modifier.height(20.dp))
        Text("Mis intereses", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(
            if (intereses.isEmpty()) "Aún no hay intereses seleccionados"
            else intereses.sorted().joinToString(" · ") { it.replace('_', ' ').replaceFirstChar(Char::uppercase) },
            color = MartiniTextSecondary
        )
        Spacer(Modifier.height(20.dp))
        MartiniPrimaryButton(text = "Editar perfil", onClick = alEditar)
    }
}
