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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton

@Composable
fun PreparacionScreen(receta: RecetaContenido, alVolver: () -> Unit) {
    var pasoActual by rememberSaveable(receta.id) { mutableIntStateOf(0) }
    val paso = receta.pasos[pasoActual]

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = alVolver) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver al detalle")
            }
            Text(
                "Paso ${pasoActual + 1} de ${receta.pasos.size}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(20.dp))
        LinearProgressIndicator(
            progress = { (pasoActual + 1).toFloat() / receta.pasos.size },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(28.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(18.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                receta.titulo,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(paso.first, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Text(paso.second, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.weight(1f))
        if (pasoActual > 0) {
            TextButton(onClick = { pasoActual-- }) { Text("Anterior") }
        }
        MartiniPrimaryButton(
            text = if (pasoActual == receta.pasos.lastIndex) "Finalizar" else "Siguiente",
            onClick = {
                if (pasoActual == receta.pasos.lastIndex) alVolver() else pasoActual++
            }
        )
    }
}
