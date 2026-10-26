package cl.duoc.mastermartini.ui.screens.perfil

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun FotoPerfil(uriTexto: String?, modifier: Modifier = Modifier) {
    val contexto = LocalContext.current
    val foto by produceState<Bitmap?>(initialValue = null, uriTexto) {
        value = uriTexto?.let { texto ->
            withContext(Dispatchers.IO) {
                runCatching {
                    contexto.contentResolver.openInputStream(Uri.parse(texto))?.use(BitmapFactory::decodeStream)
                }.getOrNull()
            }
        }
    }
    Surface(modifier = modifier.size(112.dp), shape = CircleShape) {
        if (foto != null) {
            Image(
                bitmap = foto!!.asImageBitmap(),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(112.dp).clip(CircleShape)
            )
        } else {
            Icon(Icons.Filled.Person, contentDescription = "Sin foto de perfil", modifier = Modifier.size(64.dp))
        }
    }
}
