package cl.duoc.mastermartini.ui.navigation

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
    NavHost(navController = navController, startDestination = MartiniRoutes.SPLASH) {
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
