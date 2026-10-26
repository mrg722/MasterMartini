package cl.duoc.mastermartini.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.mastermartini.ui.screens.auth.RegisterScreen
import cl.duoc.mastermartini.ui.screens.catalogo.CatalogScreen
import cl.duoc.mastermartini.ui.screens.inicio.SplashScreen
import cl.duoc.mastermartini.ui.screens.intereses.InterestsScreen

@Composable
fun MartiniNavGraph(navController: NavHostController = rememberNavController()) {
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
            CatalogScreen()
        }
    }
}
