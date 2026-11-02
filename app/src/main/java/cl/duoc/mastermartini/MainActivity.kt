package cl.duoc.mastermartini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cl.duoc.mastermartini.data.local.PreferenciasUsuario
import cl.duoc.mastermartini.data.model.TemaApp
import cl.duoc.mastermartini.ui.navigation.MartiniNavGraph
import cl.duoc.mastermartini.ui.theme.MasterMartiniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val contexto = LocalContext.current
            val preferencias = remember(contexto) { PreferenciasUsuario(contexto.applicationContext) }
            val temaGuardado by preferencias.temaApp.collectAsState(initial = TemaApp.SISTEMA)
            val oscuroEnElSistema = isSystemInDarkTheme()

            // La preferencia de Ajustes manda; "Según el sistema" delega en Android.
            val modoOscuro = when (temaGuardado) {
                TemaApp.SISTEMA -> oscuroEnElSistema
                TemaApp.CLARO -> false
                TemaApp.OSCURO -> true
            }

            MasterMartiniTheme(modoOscuro = modoOscuro) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MartiniNavGraph()
                }
            }
        }
    }
}
