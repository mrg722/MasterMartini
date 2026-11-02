package cl.duoc.mastermartini.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance

/*
 * Paleta Master Martini
 * Dirección: cocina chilena de campo — papel crema tibio, verde de hierba fresca
 * y acento cobre/naranjo tostado. Todos los tonos de texto y de acento están
 * elegidos para cumplir 4.5:1 sobre su fondo previsto (usuarios mayores de 50).
 *
 * Los `val` de este archivo son constantes: NO cambian con el modo oscuro.
 * Para color que deba invertirse usa MaterialTheme.colorScheme.
 */

// ---------------------------------------------------------------------------
// Verdes (marca)
// ---------------------------------------------------------------------------
/** Verde de marca. 4.8:1 sobre crema, 3.3:1 sobre superficie oscura. */
val MartiniGreen = Color(0xFF27803F)
val MartiniGreenDark = Color(0xFF11431F)
val MartiniGreenLight = Color(0xFFD7EDDC)

// ---------------------------------------------------------------------------
// Naranjos / cobre (acento)
// ---------------------------------------------------------------------------
/** Cobre tostado. 5.1:1 con texto blanco encima. */
val MartiniOrange = Color(0xFFB9501A)
val MartiniOrangeLight = Color(0xFFFBE0CD)

// ---------------------------------------------------------------------------
// Fondos y superficies (modo claro)
// ---------------------------------------------------------------------------
val MartiniCream = Color(0xFFFBF6EC)
val MartiniCreamAlt = Color(0xFFF2EADA)
val MartiniSurface = Color(0xFFFFFCF6)

// ---------------------------------------------------------------------------
// Texto, bordes y estados
// ---------------------------------------------------------------------------
val MartiniTextPrimary = Color(0xFF221E19)
val MartiniTextSecondary = Color(0xFF5A5146)

/** Borde visible (3.6:1 sobre superficie clara): campos, contornos de tarjeta. */
val MartiniBorder = Color(0xFF8E8373)

/** Línea suave, solo decorativa (divisores). No usar para contornos de campos. */
val MartiniBorderSuave = Color(0xFFE0D6C4)

val MartiniError = Color(0xFFB3261E)

// ---------------------------------------------------------------------------
// Colores de apoyo (iconos y chips de categoría). Tonos medios pensados para
// seguir siendo visibles tanto en claro como en oscuro (>= 3.5:1 en ambos).
// ---------------------------------------------------------------------------
val MartiniPeach = Color(0xFFC06A1E)
val MartiniTeal = Color(0xFF1F7A6B)
val MartiniPink = Color(0xFFC04B6E)
val MartiniBlue = Color(0xFF2E6FA8)

// ---------------------------------------------------------------------------
// Tokens nuevos para pantallas
// ---------------------------------------------------------------------------

/** Base de categoría para modo CLARO. Texto blanco encima cumple 4.5:1. */
val coloresDeCategoria: List<Color> = listOf(
    Color(0xFFBE5A1E), // zapallo tostado
    Color(0xFF1F7A5A), // cilantro
    Color(0xFF9C3A5A), // betarraga
    Color(0xFF2B6AA3), // loza azul
    Color(0xFF8A6A12), // mostaza
    Color(0xFF6B4A8F)  // maqui
)

/** Mismas categorías, en el mismo orden, para modo OSCURO (texto oscuro encima). */
val coloresDeCategoriaOscuro: List<Color> = listOf(
    Color(0xFFE8A05C),
    Color(0xFF66C9A3),
    Color(0xFFE590AC),
    Color(0xFF86B6E6),
    Color(0xFFD9BE5F),
    Color(0xFFBCA3DE)
)

/** Velo para poner texto sobre fotografías de recetas. */
val MartiniScrimImagen = Color(0xB3000000)

private val MartiniNegroHorno = Color(0xFF141612)

private fun esTemaOscuro(fondo: Color): Boolean = fondo.luminance() < 0.5f

/**
 * Color base de la categoría [indice] ya resuelto para el tema vigente.
 * El índice se envuelve, así que cualquier entero es válido.
 */
@Composable
fun colorDeCategoria(indice: Int): Color {
    val oscuro = esTemaOscuro(MaterialTheme.colorScheme.surface)
    val paleta = if (oscuro) coloresDeCategoriaOscuro else coloresDeCategoria
    return paleta[((indice % paleta.size) + paleta.size) % paleta.size]
}

/**
 * Dos colores para `Brush.linearGradient(...)` en tarjetas de receta,
 * resueltos según el tema vigente.
 */
@Composable
fun gradienteDeCategoria(indice: Int): List<Color> {
    val oscuro = esTemaOscuro(MaterialTheme.colorScheme.surface)
    val base = colorDeCategoria(indice)
    val fin = if (oscuro) lerp(base, MartiniNegroHorno, 0.38f) else lerp(base, Color.Black, 0.28f)
    return listOf(base, fin)
}

/** Color de texto/icono legible encima de [colorDeCategoria] o su gradiente. */
@Composable
fun contenidoSobreCategoria(): Color =
    if (esTemaOscuro(MaterialTheme.colorScheme.surface)) Color(0xFF1A1206) else Color.White
