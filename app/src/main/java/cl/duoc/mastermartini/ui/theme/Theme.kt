package cl.duoc.mastermartini.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colores = lightColorScheme(
    primary = MartiniGreen,
    onPrimary = MartiniSurface,
    primaryContainer = MartiniGreenLight,
    onPrimaryContainer = MartiniGreenDark,
    secondary = MartiniOrange,
    onSecondary = MartiniSurface,
    secondaryContainer = MartiniOrangeLight,
    onSecondaryContainer = MartiniGreenDark,
    background = MartiniCream,
    onBackground = MartiniTextPrimary,
    surface = MartiniSurface,
    onSurface = MartiniTextPrimary,
    surfaceVariant = MartiniCreamAlt,
    onSurfaceVariant = MartiniTextSecondary,
    outline = MartiniBorder,
    error = MartiniError
)

private val coloresOscuros = darkColorScheme(
    primary = Color(0xFF8FD5A4),
    onPrimary = Color(0xFF07351A),
    primaryContainer = Color(0xFF185C31),
    onPrimaryContainer = Color(0xFFE3F6E7),
    secondary = Color(0xFFFFB77D),
    onSecondary = Color(0xFF4C2400),
    secondaryContainer = Color(0xFF78420B),
    onSecondaryContainer = Color(0xFFFFE7D3),
    background = Color(0xFF171914),
    onBackground = Color(0xFFE7E4D9),
    surface = Color(0xFF20231E),
    onSurface = Color(0xFFE7E4D9),
    surfaceVariant = Color(0xFF34372F),
    onSurfaceVariant = Color(0xFFE1E3D5),
    outline = Color(0xFF9BA096),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

@Composable
fun MasterMartiniTheme(
    modoOscuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (modoOscuro) coloresOscuros else colores,
        typography = MartiniTypography,
        content = content
    )
}
