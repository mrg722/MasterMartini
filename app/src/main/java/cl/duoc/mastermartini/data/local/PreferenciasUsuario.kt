package cl.duoc.mastermartini.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import cl.duoc.mastermartini.data.model.PerfilUsuario

private val Context.almacen by preferencesDataStore(name = "master_martini_prefs")

class PreferenciasUsuario(private val contexto: Context) {

    private object Claves {
        val NOMBRE = stringPreferencesKey("nombre_completo")
        val CORREO = stringPreferencesKey("correo")
        val INTERESES = stringSetPreferencesKey("intereses")
        val ONBOARDING_COMPLETO = booleanPreferencesKey("onboarding_completo")
        val FECHA_NACIMIENTO = stringPreferencesKey("fecha_nacimiento")
        val PREFERENCIAS_ALIMENTARIAS = stringPreferencesKey("preferencias_alimentarias")
        val FOTO_PERFIL = stringPreferencesKey("foto_perfil")
    }

    val nombreUsuario: Flow<String> = contexto.almacen.data.map { it[Claves.NOMBRE] ?: "" }
    val correoUsuario: Flow<String> = contexto.almacen.data.map { it[Claves.CORREO] ?: "" }
    val interesesUsuario: Flow<Set<String>> = contexto.almacen.data.map { it[Claves.INTERESES] ?: emptySet() }
    val registroCompleto: Flow<Boolean> = contexto.almacen.data.map { it[Claves.ONBOARDING_COMPLETO] ?: false }
    val perfilUsuario: Flow<PerfilUsuario> = contexto.almacen.data.map { preferencias ->
        PerfilUsuario(
            nombre = preferencias[Claves.NOMBRE].orEmpty(),
            correo = preferencias[Claves.CORREO].orEmpty(),
            fechaNacimiento = preferencias[Claves.FECHA_NACIMIENTO].orEmpty(),
            preferenciasAlimentarias = preferencias[Claves.PREFERENCIAS_ALIMENTARIAS] ?: "Sin restricciones",
            fotoUri = preferencias[Claves.FOTO_PERFIL]
        )
    }

    suspend fun guardarPerfil(perfil: PerfilUsuario) {
        contexto.almacen.edit { preferencias ->
            preferencias[Claves.NOMBRE] = perfil.nombre.trim()
            preferencias[Claves.CORREO] = perfil.correo.trim()
            preferencias[Claves.FECHA_NACIMIENTO] = perfil.fechaNacimiento.trim()
            preferencias[Claves.PREFERENCIAS_ALIMENTARIAS] = perfil.preferenciasAlimentarias.trim()
            perfil.fotoUri?.let { preferencias[Claves.FOTO_PERFIL] = it }
        }
    }

    suspend fun guardarCuenta(nombre: String, correo: String) {
        contexto.almacen.edit { preferencias ->
            preferencias[Claves.NOMBRE] = nombre
            preferencias[Claves.CORREO] = correo
        }
    }

    suspend fun guardarIntereses(intereses: Set<String>) {
        contexto.almacen.edit { preferencias ->
            preferencias[Claves.INTERESES] = intereses
            preferencias[Claves.ONBOARDING_COMPLETO] = true
        }
    }
}
