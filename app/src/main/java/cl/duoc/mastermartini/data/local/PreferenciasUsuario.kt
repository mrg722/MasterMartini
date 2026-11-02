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
import cl.duoc.mastermartini.data.model.TemaApp

private val Context.almacen by preferencesDataStore(name = "master_martini_prefs")

/**
 * Persistencia local de la aplicación (DataStore de preferencias).
 *
 * Guarda tres cosas distintas:
 *  - los datos de la cuenta y el perfil,
 *  - los intereses elegidos en el onboarding,
 *  - y los ajustes de la app (apariencia y notificaciones).
 *
 * Todo se expone como [Flow] para que la interfaz reaccione sola a los cambios.
 */
class PreferenciasUsuario(private val contexto: Context) {

    private object Claves {
        val NOMBRE = stringPreferencesKey("nombre_completo")
        val CORREO = stringPreferencesKey("correo")
        val INTERESES = stringSetPreferencesKey("intereses")
        val ONBOARDING_COMPLETO = booleanPreferencesKey("onboarding_completo")
        val FECHA_NACIMIENTO = stringPreferencesKey("fecha_nacimiento")
        val PREFERENCIAS_ALIMENTARIAS = stringPreferencesKey("preferencias_alimentarias")
        val FOTO_PERFIL = stringPreferencesKey("foto_perfil")
        val TEMA = stringPreferencesKey("tema_app")
        val NOTIFICACIONES = booleanPreferencesKey("notificaciones_activas")
        val SESION_ACTIVA = booleanPreferencesKey("sesion_activa")
        val FAVORITAS = stringSetPreferencesKey("recetas_favoritas")
    }

    val nombreUsuario: Flow<String> = contexto.almacen.data.map { it[Claves.NOMBRE] ?: "" }
    val correoUsuario: Flow<String> = contexto.almacen.data.map { it[Claves.CORREO] ?: "" }
    val interesesUsuario: Flow<Set<String>> = contexto.almacen.data.map { it[Claves.INTERESES] ?: emptySet() }
    val registroCompleto: Flow<Boolean> = contexto.almacen.data.map { it[Claves.ONBOARDING_COMPLETO] ?: false }

    /** `true` mientras la persona no cierre sesión. Al cerrarla, la cuenta se conserva. */
    val sesionActiva: Flow<Boolean> = contexto.almacen.data.map { it[Claves.SESION_ACTIVA] ?: false }

    /** `true` cuando existe una cuenta guardada en el dispositivo. */
    val existeCuenta: Flow<Boolean> = contexto.almacen.data.map { preferencias ->
        !preferencias[Claves.CORREO].isNullOrBlank() || !preferencias[Claves.NOMBRE].isNullOrBlank()
    }

    val temaApp: Flow<TemaApp> = contexto.almacen.data.map { TemaApp.desdeClave(it[Claves.TEMA]) }

    val notificacionesActivas: Flow<Boolean> = contexto.almacen.data.map { it[Claves.NOTIFICACIONES] ?: true }

    val recetasFavoritas: Flow<Set<String>> = contexto.almacen.data.map { it[Claves.FAVORITAS] ?: emptySet() }

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
            preferencias[Claves.SESION_ACTIVA] = true
        }
    }

    suspend fun guardarIntereses(intereses: Set<String>) {
        contexto.almacen.edit { preferencias ->
            preferencias[Claves.INTERESES] = intereses
            preferencias[Claves.ONBOARDING_COMPLETO] = true
            preferencias[Claves.SESION_ACTIVA] = true
        }
    }

    suspend fun guardarTema(tema: TemaApp) {
        contexto.almacen.edit { preferencias ->
            preferencias[Claves.TEMA] = tema.clave
        }
    }

    suspend fun guardarNotificaciones(activas: Boolean) {
        contexto.almacen.edit { preferencias ->
            preferencias[Claves.NOTIFICACIONES] = activas
        }
    }

    /** Marca la sesión como iniciada sin tocar el resto de los datos de la cuenta. */
    suspend fun iniciarSesion() {
        contexto.almacen.edit { preferencias ->
            preferencias[Claves.SESION_ACTIVA] = true
        }
    }

    /**
     * Cierra la sesión conservando la cuenta, los intereses y los ajustes, para
     * que al volver a entrar no haya que registrarse de nuevo.
     */
    suspend fun cerrarSesion() {
        contexto.almacen.edit { preferencias ->
            preferencias[Claves.SESION_ACTIVA] = false
        }
    }

    /**
     * Elimina por completo la cuenta guardada en el dispositivo: datos de
     * perfil, intereses, favoritas y estado de onboarding. Los ajustes de
     * apariencia se conservan porque son preferencias del dispositivo, no de la
     * cuenta.
     */
    suspend fun eliminarCuenta() {
        contexto.almacen.edit { preferencias ->
            val temaGuardado = preferencias[Claves.TEMA]
            val notificaciones = preferencias[Claves.NOTIFICACIONES]
            preferencias.clear()
            temaGuardado?.let { preferencias[Claves.TEMA] = it }
            notificaciones?.let { preferencias[Claves.NOTIFICACIONES] = it }
        }
    }

    /** Agrega o quita una receta de favoritas y devuelve el nuevo estado. */
    suspend fun alternarFavorita(recetaId: String) {
        contexto.almacen.edit { preferencias ->
            val actuales = preferencias[Claves.FAVORITAS] ?: emptySet()
            preferencias[Claves.FAVORITAS] =
                if (actuales.contains(recetaId)) actuales - recetaId else actuales + recetaId
        }
    }
}
