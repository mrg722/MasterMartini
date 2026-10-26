package cl.duoc.mastermartini.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.ui.screens.auth.RegisterScreen
import cl.duoc.mastermartini.ui.screens.catalogo.CatalogScreen
import cl.duoc.mastermartini.ui.screens.inicio.SplashScreen
import cl.duoc.mastermartini.ui.screens.intereses.InterestsScreen
import cl.duoc.mastermartini.ui.screens.cursos.CursosScreen
import cl.duoc.mastermartini.ui.screens.detalle.DetalleRecetaScreen
import cl.duoc.mastermartini.ui.screens.detalle.PreparacionScreen
import cl.duoc.mastermartini.ui.screens.detalle.recetasDisponibles
import cl.duoc.mastermartini.ui.screens.perfil.PerfilScreen
import cl.duoc.mastermartini.ui.screens.perfil.EditarPerfil

@Composable
fun MartiniNavGraph(navController: NavHostController = rememberNavController()) {
    val contexto = LocalContext.current
    val preferencias = remember(contexto) { PreferenciasUsuario(contexto.applicationContext) }
    val registroCompleto by preferencias.registroCompleto.collectAsState(initial = null)
    val rutaActual = navController.currentBackStackEntryAsState().value?.destination?.route

    LaunchedEffect(registroCompleto, rutaActual) {
        if (registroCompleto == true && rutaActual == MartiniRoutes.SPLASH) {
            navController.navigate(MartiniRoutes.CATALOG) {
                popUpTo(MartiniRoutes.SPLASH) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = MartiniRoutes.SPLASH,
        enterTransition = {
            fadeIn(animationSpec = tween(220)) +
                slideInHorizontally(animationSpec = tween(220)) { anchoCompleto -> anchoCompleto / 12 }
        },
        exitTransition = {
            fadeOut(animationSpec = tween(160)) +
                slideOutHorizontally(animationSpec = tween(160)) { anchoCompleto -> -anchoCompleto / 12 }
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(220)) +
                slideInHorizontally(animationSpec = tween(220)) { anchoCompleto -> -anchoCompleto / 12 }
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(160)) +
                slideOutHorizontally(animationSpec = tween(160)) { anchoCompleto -> anchoCompleto / 12 }
        }
    ) {
        composable(MartiniRoutes.SPLASH) {
            SplashScreen(
                onComenzar = {
                    navController.navigate(MartiniRoutes.REGISTER) {
                        popUpTo(MartiniRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(MartiniRoutes.REGISTER) {
            RegisterScreen(
                onCuentaCreada = {
                    navController.navigate(MartiniRoutes.INTERESTS) {
                        popUpTo(MartiniRoutes.REGISTER) { inclusive = true }
                    }
                },
                onIniciarSesion = {
                    navController.navigate(MartiniRoutes.CATALOG) {
                        popUpTo(MartiniRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(MartiniRoutes.INTERESTS) {
            InterestsScreen(
                onContinuar = {
                    navController.navigate(MartiniRoutes.CATALOG) {
                        popUpTo(MartiniRoutes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(MartiniRoutes.CATALOG) {
            CatalogScreen(
                alAbrirReceta = { recetaId -> navController.navigate(MartiniRoutes.detalle(recetaId)) },
                alAbrirCursos = { navController.navigate(MartiniRoutes.CURSOS) },
                alAbrirPerfil = { navController.navigate(MartiniRoutes.PERFIL) }
            )
        }

        composable(MartiniRoutes.CURSOS) {
            CursosScreen()
        }

        composable(MartiniRoutes.PERFIL) {
            PerfilScreen(alEditar = { navController.navigate(MartiniRoutes.EDITAR_PERFIL) })
        }

        composable(MartiniRoutes.EDITAR_PERFIL) {
            EditarPerfil(alVolver = { navController.popBackStack() })
        }

        composable(MartiniRoutes.DETALLE) { entrada ->
            val recetaId = entrada.arguments?.getString("recetaId")
            val receta = recetasDisponibles.firstOrNull { it.id == recetaId }
            if (receta != null) {
                DetalleRecetaScreen(
                    receta = receta,
                    alVolver = { navController.popBackStack() },
                    alPreparar = { navController.navigate(MartiniRoutes.preparacion(receta.id)) }
                )
            }
        }

        composable(MartiniRoutes.PREPARACION) { entrada ->
            val recetaId = entrada.arguments?.getString("recetaId")
            val receta = recetasDisponibles.firstOrNull { it.id == recetaId }
            if (receta != null) {
                PreparacionScreen(
                    receta = receta,
                    alVolver = { navController.popBackStack() }
                )
            }
        }
    }
}
