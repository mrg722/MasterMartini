package cl.duoc.mastermartini.ui.screens.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton
import cl.duoc.mastermartini.ui.theme.MartiniOrangeLight

@Composable
fun DetalleRecetaScreen(receta: RecetaContenido, alVolver: () -> Unit, alPreparar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        IconButton(onClick = alVolver) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
        }
        Spacer(Modifier.height(12.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(MartiniOrangeLight, RoundedCornerShape(18.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Master Martini", color = MaterialTheme.colorScheme.primary)
            Text(receta.titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(20.dp))
        Text(receta.titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("${receta.minutos} min · ${receta.dificultad}", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(24.dp))
        Text("Descripción", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(receta.descripcion)
        Spacer(Modifier.height(20.dp))
        Text("Ingredientes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        receta.ingredientes.forEach { ingrediente ->
            Row(Modifier.padding(vertical = 4.dp)) {
                Text("✓  ", color = MaterialTheme.colorScheme.primary)
                Text(ingrediente)
            }
        }
        Spacer(Modifier.height(24.dp))
        MartiniPrimaryButton(text = "Ver preparación", onClick = alPreparar)
    }
}
