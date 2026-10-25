package cl.duoc.mastermartini.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
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

private val DarkColors = darkColorScheme(
    primary = MartiniGreenLight,
    onPrimary = MartiniGreenDark,
    secondary = MartiniOrange,
    background = Color(0xFF1C1B18),
    surface = Color(0xFF242320),
    error = MartiniError
)

@Composable
fun MasterMartiniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = MartiniTypography,
        content = content
    )
}
