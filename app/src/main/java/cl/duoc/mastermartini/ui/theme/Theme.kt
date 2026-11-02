package cl.duoc.mastermartini.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Esquinas generosas y consistentes: chips y campos chicos (12dp),
 * tarjetas (20dp) y hojas / contenedores grandes (28dp).
 */
val MartiniShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

private val coloresClaros = lightColorScheme(
    primary = MartiniGreen,
    onPrimary = Color.White,
    primaryContainer = MartiniGreenLight,
    onPrimaryContainer = MartiniGreenDark,
    inversePrimary = Color(0xFF8ED99F),

    secondary = MartiniOrange,
    onSecondary = Color.White,
    secondaryContainer = MartiniOrangeLight,
    onSecondaryContainer = Color(0xFF4A1D00),

    tertiary = Color(0xFF7A5A12),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFF6E7BD),
    onTertiaryContainer = Color(0xFF3D2C00),

    background = MartiniCream,
    onBackground = MartiniTextPrimary,

    surface = MartiniSurface,
    onSurface = MartiniTextPrimary,
    surfaceVariant = MartiniCreamAlt,
    onSurfaceVariant = MartiniTextSecondary,
    surfaceTint = MartiniGreen,
    surfaceBright = Color(0xFFFFFCF6),
    surfaceDim = Color(0xFFE4DCCB),
    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFCF8EF),
    surfaceContainer = Color(0xFFF6F0E2),
    surfaceContainerHigh = Color(0xFFF0E8D7),
    surfaceContainerHighest = Color(0xFFE9E0CC),

    inverseSurface = Color(0xFF32352E),
    inverseOnSurface = Color(0xFFF4F0E5),

    outline = MartiniBorder,
    outlineVariant = MartiniBorderSuave,

    error = MartiniError,
    onError = Color.White,
    errorContainer = Color(0xFFFCDAD5),
    onErrorContainer = Color(0xFF480A05),

    scrim = Color(0xFF000000)
)

private val coloresOscuros = darkColorScheme(
    primary = Color(0xFF8ED99F),
    onPrimary = Color(0xFF063819),
    primaryContainer = Color(0xFF1E6A38),
    onPrimaryContainer = Color(0xFFC9F2D3),
    inversePrimary = MartiniGreen,

    secondary = Color(0xFFFFB68A),
    onSecondary = Color(0xFF4A1D00),
    secondaryContainer = Color(0xFF7A3610),
    onSecondaryContainer = Color(0xFFFFDBC4),

    tertiary = Color(0xFFE8C86A),
    onTertiary = Color(0xFF3C2D00),
    tertiaryContainer = Color(0xFF5A4208),
    onTertiaryContainer = Color(0xFFFBE9B8),

    background = Color(0xFF141612),
    onBackground = Color(0xFFEDE8DB),

    surface = Color(0xFF1B1E19),
    onSurface = Color(0xFFEDE8DB),
    surfaceVariant = Color(0xFF3A3F36),
    onSurfaceVariant = Color(0xFFD3CDBE),
    surfaceTint = Color(0xFF8ED99F),
    surfaceBright = Color(0xFF3A3E36),
    surfaceDim = Color(0xFF131511),
    surfaceContainerLowest = Color(0xFF0F110D),
    surfaceContainerLow = Color(0xFF1E211C),
    surfaceContainer = Color(0xFF22251F),
    surfaceContainerHigh = Color(0xFF2B2F28),
    surfaceContainerHighest = Color(0xFF363A33),

    inverseSurface = Color(0xFFEDE8DB),
    inverseOnSurface = Color(0xFF2F322B),

    outline = Color(0xFF9A9385),
    outlineVariant = Color(0xFF474A41),

    error = Color(0xFFFFB4A8),
    onError = Color(0xFF5C0A05),
    errorContainer = Color(0xFF8C1A13),
    onErrorContainer = Color(0xFFFFDAD5),

    scrim = Color(0xFF000000)
)

@Composable
fun MasterMartiniTheme(
    modoOscuro: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (modoOscuro) coloresOscuros else coloresClaros,
        typography = MartiniTypography,
        shapes = MartiniShapes,
        content = content
    )
}
