package cl.duoc.mastermartini.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "master_martini_prefs")

class UserPreferences(private val context: Context) {

    private object Keys {
        val NOMBRE = stringPreferencesKey("nombre_completo")
        val CORREO = stringPreferencesKey("correo")
        val INTERESES = stringSetPreferencesKey("intereses")
        val ONBOARDING_COMPLETO = booleanPreferencesKey("onboarding_completo")
    }

    val nombreUsuario: Flow<String> = context.dataStore.data.map { it[Keys.NOMBRE] ?: "" }
    val correoUsuario: Flow<String> = context.dataStore.data.map { it[Keys.CORREO] ?: "" }
    val interesesUsuario: Flow<Set<String>> = context.dataStore.data.map { it[Keys.INTERESES] ?: emptySet() }
    val onboardingCompleto: Flow<Boolean> = context.dataStore.data.map { it[Keys.ONBOARDING_COMPLETO] ?: false }

    suspend fun guardarCuenta(nombre: String, correo: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.NOMBRE] = nombre
            prefs[Keys.CORREO] = correo
        }
    }

    suspend fun guardarIntereses(intereses: Set<String>) {
        context.dataStore.edit { prefs ->
            prefs[Keys.INTERESES] = intereses
            prefs[Keys.ONBOARDING_COMPLETO] = true
        }
    }
}
