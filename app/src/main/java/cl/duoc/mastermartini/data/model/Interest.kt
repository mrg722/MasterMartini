package cl.duoc.mastermartini.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Interest(
    val id: String,
    val titulo: String,
    val icono: ImageVector,
    val colorFondo: Color
)
