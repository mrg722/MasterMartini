package cl.duoc.mastermartini.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.data.model.buscarCurso
import cl.duoc.mastermartini.ui.components.SeccionPrincipal
import cl.duoc.mastermartini.ui.screens.ajustes.AjustesScreen
import cl.duoc.mastermartini.ui.screens.auth.RegisterScreen
import cl.duoc.mastermartini.ui.screens.catalogo.CatalogScreen
import cl.duoc.mastermartini.ui.screens.cursos.CursosScreen
import cl.duoc.mastermartini.ui.screens.cursos.DetalleCursoScreen
import cl.duoc.mastermartini.ui.screens.detalle.DetalleRecetaScreen
import cl.duoc.mastermartini.ui.screens.detalle.PreparacionScreen
import cl.duoc.mastermartini.ui.screens.detalle.buscarReceta
import cl.duoc.mastermartini.ui.screens.detalle.buscarVideoPorId
import cl.duoc.mastermartini.ui.screens.detalle.recetasDisponibles
import cl.duoc.mastermartini.ui.screens.inicio.SplashScreen
import cl.duoc.mastermartini.ui.screens.intereses.InterestsScreen
import cl.duoc.mastermartini.ui.screens.perfil.EditarPerfil
import cl.duoc.mastermartini.ui.screens.perfil.PerfilScreen
import cl.duoc.mastermartini.ui.screens.video.ReproductorVideoScreen
import kotlinx.coroutines.launch

/**
 * Grafo de navegación de la aplicación.
 *
 * La pantalla de inicio nunca salta sola al catálogo: siempre hay que pulsar
 * "Comenzar". Lo único que cambia según el estado guardado es el destino de ese
 * botón (catálogo si la sesión sigue abierta, registro si no).
 */
@Composable
fun MartiniNavGraph(navController: NavHostController = rememberNavController()) {
    val contexto = LocalContext.current
    val preferencias = remember(contexto) { PreferenciasUsuario(contexto.applicationContext) }
    val alcance = rememberCoroutineScope()

    val sesionActiva by preferencias.sesionActiva.collectAsState(initial = false)
    val registroCompleto by preferencias.registroCompleto.collectAsState(initial = false)
    val favoritas by preferencias.recetasFavoritas.collectAsState(initial = emptySet())

    /** Lleva a una de las secciones principales sin apilar pantallas repetidas. */
    fun navegarASeccion(seccion: SeccionPrincipal) {
        val destino = when (seccion) {
            SeccionPrincipal.RECETAS -> MartiniRoutes.CATALOG
            SeccionPrincipal.CURSOS -> MartiniRoutes.CURSOS
            SeccionPrincipal.PERFIL -> MartiniRoutes.PERFIL
            SeccionPrincipal.AJUSTES -> MartiniRoutes.AJUSTES
        }
        navController.navigate(destino) {
            popUpTo(MartiniRoutes.CATALOG) { inclusive = false }
            launchSingleTop = true
        }
    }

    /** Vuelve al inicio dejando la pila limpia (tras cerrar sesión o eliminar la cuenta). */
    fun volverAlInicio() {
        navController.navigate(MartiniRoutes.SPLASH) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
            launchSingleTop = true
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
                sesionIniciada = sesionActiva && registroCompleto,
                onComenzar = {
                    val destino = if (sesionActiva && registroCompleto) {
                        MartiniRoutes.CATALOG
                    } else {
                        MartiniRoutes.REGISTER
                    }
                    navController.navigate(destino) {
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
                    alcance.launch { preferencias.iniciarSesion() }
                    navController.navigate(MartiniRoutes.CATALOG) {
                        popUpTo(MartiniRoutes.REGISTER) { inclusive = true }
                    }
                }
            )
        }

        composable(MartiniRoutes.INTERESTS) {
            InterestsScreen(
                onContinuar = {
                    navController.navigate(MartiniRoutes.CATALOG) {
                        popUpTo(MartiniRoutes.INTERESTS) { inclusive = true }
                    }
                }
            )
        }

        composable(MartiniRoutes.CATALOG) {
            CatalogScreen(
                alAbrirReceta = { recetaId -> navController.navigate(MartiniRoutes.detalle(recetaId)) },
                alNavegar = { seccion -> navegarASeccion(seccion) }
            )
        }

        composable(MartiniRoutes.CURSOS) {
            CursosScreen(
                alAbrirCurso = { cursoId -> navController.navigate(MartiniRoutes.curso(cursoId)) },
                alNavegar = { seccion -> navegarASeccion(seccion) }
            )
        }

        composable(MartiniRoutes.DETALLE_CURSO) { entrada ->
            val curso = buscarCurso(entrada.arguments?.getString("cursoId"))
            if (curso == null) {
                navController.popBackStack()
            } else {
                DetalleCursoScreen(
                    curso = curso,
                    alVolver = { navController.popBackStack() },
                    alReproducirVideo = { video ->
                        navController.navigate(MartiniRoutes.video(video.youtubeId))
                    },
                    alAbrirReceta = { recetaId ->
                        navController.navigate(MartiniRoutes.detalle(recetaId))
                    }
                )
            }
        }

        composable(MartiniRoutes.PERFIL) {
            PerfilScreen(
                alEditar = { navController.navigate(MartiniRoutes.EDITAR_PERFIL) },
                alAbrirAjustes = { navController.navigate(MartiniRoutes.AJUSTES) },
                alAbrirFavoritas = { navegarASeccion(SeccionPrincipal.RECETAS) },
                alAbrirIntereses = { navController.navigate(MartiniRoutes.INTERESTS) },
                alNavegar = { seccion -> navegarASeccion(seccion) }
            )
        }

        composable(MartiniRoutes.EDITAR_PERFIL) {
            EditarPerfil(alVolver = { navController.popBackStack() })
        }

        composable(MartiniRoutes.AJUSTES) {
            AjustesScreen(
                alVolver = { navController.popBackStack() },
                alEditarPerfil = { navController.navigate(MartiniRoutes.EDITAR_PERFIL) },
                alCrearCuenta = { navController.navigate(MartiniRoutes.REGISTER) },
                alSalirDeLaSesion = { volverAlInicio() },
                alNavegar = { seccion -> navegarASeccion(seccion) }
            )
        }

        composable(MartiniRoutes.DETALLE) { entrada ->
            val receta = buscarReceta(entrada.arguments?.getString("recetaId"))
            if (receta == null) {
                navController.popBackStack()
            } else {
                DetalleRecetaScreen(
                    receta = receta,
                    esFavorita = favoritas.contains(receta.id),
                    alVolver = { navController.popBackStack() },
                    alPreparar = { navController.navigate(MartiniRoutes.preparacion(receta.id)) },
                    alAlternarFavorita = {
                        alcance.launch { preferencias.alternarFavorita(receta.id) }
                    },
                    alReproducirVideo = { video ->
                        navController.navigate(MartiniRoutes.video(video.youtubeId))
                    }
                )
            }
        }

        composable(MartiniRoutes.PREPARACION) { entrada ->
            val receta = buscarReceta(entrada.arguments?.getString("recetaId"))
            if (receta == null) {
                navController.popBackStack()
            } else {
                PreparacionScreen(
                    receta = receta,
                    alVolver = { navController.popBackStack() },
                    alReproducirVideo = { video ->
                        navController.navigate(MartiniRoutes.video(video.youtubeId))
                    }
                )
            }
        }

        composable(MartiniRoutes.VIDEO) { entrada ->
            val videoId = entrada.arguments?.getString("videoId")
            val video = buscarVideoPorId(videoId)
            if (video == null) {
                navController.popBackStack()
            } else {
                val receta = recetaDelVideo(videoId)
                ReproductorVideoScreen(
                    video = video,
                    contextoReceta = receta,
                    alVolver = { navController.popBackStack() }
                )
            }
        }
    }
}

/** Título de la receta a la que pertenece un video, para mostrarlo en el reproductor. */
private fun recetaDelVideo(videoId: String?): String? {
    if (videoId.isNullOrBlank()) return null
    return recetasDisponibles.firstOrNull { receta ->
        receta.video?.youtubeId == videoId || receta.videoComplementario?.youtubeId == videoId
    }?.titulo
}
