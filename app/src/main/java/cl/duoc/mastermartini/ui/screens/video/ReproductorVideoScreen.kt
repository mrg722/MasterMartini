package cl.duoc.mastermartini.ui.screens.video

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import cl.duoc.mastermartini.data.model.VideoReceta
import cl.duoc.mastermartini.ui.components.MartiniAccentButton
import cl.duoc.mastermartini.ui.components.MartiniAviso
import cl.duoc.mastermartini.ui.components.MartiniEtiqueta
import cl.duoc.mastermartini.ui.components.MartiniTopBar

/**
 * Reproduce un video de YouTube dentro de la aplicación.
 *
 * La reproducción ocurre en un [WebView] con el reproductor embebido de
 * YouTube. Como algunos canales no permiten la reproducción embebida, siempre
 * queda disponible el botón para abrir el video en la aplicación de YouTube o
 * en el navegador.
 */
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ReproductorVideoScreen(
    video: VideoReceta,
    contextoReceta: String?,
    alVolver: () -> Unit
) {
    val contexto = LocalContext.current
    var cargando by remember(video.youtubeId) { mutableStateOf(true) }
    var errorCarga by remember(video.youtubeId) { mutableStateOf<String?>(null) }

    val vistaWeb = remember(video.youtubeId) {
        WebView(contexto).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            setBackgroundColor(android.graphics.Color.BLACK)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    cargando = false
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    if (request?.isForMainFrame == true) {
                        cargando = false
                        errorCarga = "No se pudo cargar el reproductor. Abre el video en YouTube."
                    }
                }
            }
            loadUrl(video.urlEmbebida)
        }
    }

    DisposableEffect(vistaWeb) {
        onDispose {
            vistaWeb.stopLoading()
            vistaWeb.loadUrl("about:blank")
            vistaWeb.destroy()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MartiniTopBar(
                titulo = "Video de la receta",
                subtitulo = contextoReceta,
                alVolver = alVolver
            )
        }
    ) { relleno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AndroidView(
                    factory = { vistaWeb },
                    modifier = Modifier.fillMaxSize()
                )
                if (cargando) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            MartiniAviso(mensaje = errorCarga, esError = true)

            Text(
                text = video.titulo,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Canal: ${video.canal}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            MartiniEtiqueta(
                texto = video.duracion,
                icono = Icons.Filled.PlayCircle
            )

            MartiniAccentButton(
                text = "Abrir en YouTube",
                onClick = {
                    val intencion = Intent(Intent.ACTION_VIEW, Uri.parse(video.urlPublica))
                    try {
                        contexto.startActivity(intencion)
                    } catch (_: ActivityNotFoundException) {
                        errorCarga = "No hay una aplicación disponible para abrir el video."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "El video pertenece a su canal original. La aplicación solo enlaza " +
                    "el contenido publicado por terceros; no lo descarga ni lo redistribuye.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}
