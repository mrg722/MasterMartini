package cl.duoc.mastermartini.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

/*
 * Escala tipográfica Master Martini.
 * Solo fuentes del sistema (FontFamily.Default / SansSerif).
 * Pensada para lectura cómoda en adultos mayores: cuerpo desde 15sp,
 * nada bajo 13sp, interlineado 1.45-1.55 en texto corrido y jerarquía
 * marcada por peso + tamaño (no por color).
 */

private val Titulos = FontFamily.Default
private val Cuerpo = FontFamily.SansSerif

/** Recorta el interlineado sobrante arriba y abajo del párrafo. */
private val alineacionLinea = LineHeightStyle(
    alignment = LineHeightStyle.Alignment.Center,
    trim = LineHeightStyle.Trim.None
)

private fun titulo(
    tamano: Int,
    alto: Int,
    peso: FontWeight,
    espaciado: Double = 0.0
) = TextStyle(
    fontFamily = Titulos,
    fontWeight = peso,
    fontSize = tamano.sp,
    lineHeight = alto.sp,
    letterSpacing = espaciado.sp,
    lineHeightStyle = alineacionLinea
)

private fun cuerpo(
    tamano: Int,
    alto: Int,
    peso: FontWeight = FontWeight.Normal,
    espaciado: Double = 0.1
) = TextStyle(
    fontFamily = Cuerpo,
    fontWeight = peso,
    fontSize = tamano.sp,
    lineHeight = alto.sp,
    letterSpacing = espaciado.sp,
    lineHeightStyle = alineacionLinea
)

val MartiniTypography = Typography(
    // Portadas y splash
    displayLarge = titulo(44, 52, FontWeight.Bold, -0.5),
    displayMedium = titulo(38, 46, FontWeight.Bold, -0.4),
    displaySmall = titulo(32, 40, FontWeight.Bold, -0.2),

    // Encabezados de pantalla
    headlineLarge = titulo(30, 38, FontWeight.Bold, -0.2),
    headlineMedium = titulo(26, 34, FontWeight.Bold),
    headlineSmall = titulo(22, 30, FontWeight.Bold),

    // Títulos de sección y de tarjeta
    titleLarge = titulo(20, 28, FontWeight.SemiBold),
    titleMedium = titulo(18, 26, FontWeight.SemiBold, 0.1),
    titleSmall = titulo(16, 22, FontWeight.Medium, 0.1),

    // Texto corrido
    bodyLarge = cuerpo(17, 26),
    bodyMedium = cuerpo(15, 23, espaciado = 0.15),
    bodySmall = cuerpo(13, 19, espaciado = 0.2),

    // Botones, chips y etiquetas
    labelLarge = cuerpo(16, 22, FontWeight.SemiBold, 0.3),
    labelMedium = cuerpo(14, 20, FontWeight.Medium, 0.4),
    labelSmall = cuerpo(13, 18, FontWeight.Medium, 0.4)
)
