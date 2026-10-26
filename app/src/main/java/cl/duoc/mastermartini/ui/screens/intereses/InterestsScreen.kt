package cl.duoc.mastermartini.ui.screens.intereses

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.mastermartini.data.model.Interest
import cl.duoc.mastermartini.ui.components.MartiniPrimaryButton
import cl.duoc.mastermartini.ui.components.StepDots
import cl.duoc.mastermartini.ui.theme.MartiniBlue
import cl.duoc.mastermartini.ui.theme.MartiniCream
import cl.duoc.mastermartini.ui.theme.MartiniOrange
import cl.duoc.mastermartini.ui.theme.MartiniPeach
import cl.duoc.mastermartini.ui.theme.MartiniPink
import cl.duoc.mastermartini.ui.theme.MartiniTeal
import cl.duoc.mastermartini.ui.theme.MartiniTextSecondary
import cl.duoc.mastermartini.ui.theme.MasterMartiniTheme
import cl.duoc.mastermartini.viewmodel.InterestsViewModel

private fun interesesDisponibles() = listOf(
    Interest("recetas_saludables", "Recetas saludables", Icons.Filled.RestaurantMenu, MartiniPeach),
    Interest("cocina_facil", "Cocina fácil", Icons.Filled.Kitchen, MartiniTeal),
    Interest("alimentacion_salud", "Alimentación para la salud", Icons.Filled.Favorite, MartiniPink),
    Interest("cocina_internacional", "Cocina internacional", Icons.Filled.Public, MartiniBlue),
    Interest("postres_saludables", "Postres saludables", Icons.Filled.Cake, MartiniPink),
    Interest("recetas_rapidas", "Recetas rápidas", Icons.Filled.Timer, MartiniBlue)
)

@Composable
fun InterestsScreen(
    onContinuar: () -> Unit,
    viewModel: InterestsViewModel = viewModel()
) {
    val estado by viewModel.uiState.collectAsState()
    val intereses = remember { interesesDisponibles() }

    LaunchedEffect(estado.guardadoCompleto) {
        if (estado.guardadoCompleto) {
            onContinuar()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MartiniCream)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        StepDots(totalPasos = 3, pasoActual = 1)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¿Qué te interesa?",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Selecciona tus preferencias para personalizar tu experiencia",
            style = MaterialTheme.typography.bodyMedium,
            color = MartiniTextSecondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(intereses) { interes ->
                InterestCard(
                    interes = interes,
                    seleccionado = interes.id in estado.seleccionados,
                    onClick = { viewModel.onToggleInterest(interes.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        MartiniPrimaryButton(
            text = "Continuar →",
            onClick = viewModel::onContinuar,
            enabled = estado.puedeContinuar
        )
    }
}

@Composable
private fun InterestCard(
    interes: Interest,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (seleccionado) MartiniOrange else Color.Transparent,
        label = "bordeInteres"
    )
    val elevacionTarjeta by animateDpAsState(
        targetValue = if (seleccionado) 4.dp else 1.dp,
        animationSpec = tween(durationMillis = 180),
        label = "elevacionTarjeta"
    )
    val tamanoIcono by animateDpAsState(
        targetValue = if (seleccionado) 48.dp else 44.dp,
        animationSpec = tween(durationMillis = 180),
        label = "tamanoIcono"
    )
    Card(
        onClick = onClick,
        modifier = Modifier
            .aspectRatio(1f)
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = elevacionTarjeta)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(tamanoIcono)
                    .background(interes.colorFondo.copy(alpha = 0.18f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = interes.icono,
                    contentDescription = null,
                    tint = interes.colorFondo
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = interes.titulo,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 800)
@Composable
private fun InterestsScreenPreview() {
    MasterMartiniTheme {
        InterestsScreen(onContinuar = {})
    }
}
