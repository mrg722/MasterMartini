package cl.duoc.mastermartini

import cl.duoc.mastermartini.data.model.PerfilUsuario
import cl.duoc.mastermartini.data.validation.ValidadorRegistro
import cl.duoc.mastermartini.data.validation.ValidadorPerfil
import java.time.LocalDate
import org.junit.Assert.*
import org.junit.Test

class ValidacionFormulariosTest {
    @Test
    fun registroVacioMuestraTodosLosErrores() {
        val errores = ValidadorRegistro.validarFormulario("", "", "", "", false)
        assertFalse(errores.esValido)
        assertNotNull(errores.errorNombre)
        assertNotNull(errores.errorCorreo)
        assertNotNull(errores.errorContrasena)
        assertNotNull(errores.errorConfirmacion)
        assertNotNull(errores.errorTerminos)
    }

    @Test
    fun registroValidoAceptaDatosDePrueba() {
        assertTrue(ValidadorRegistro.validarFormulario("Persona de prueba", "prueba@example.com", "clave1", "clave1", true).esValido)
    }

    @Test
    fun rechazaCorreoIncorrectoYConfirmacionDistinta() {
        listOf("sin-arroba", "a..b@example.com", "a@-dominio.com", "a@dominio").forEach {
            assertNotNull(ValidadorRegistro.validarCorreo(it))
        }
        assertNotNull(ValidadorRegistro.validarConfirmacion("clave1", "clave2"))
        assertNotNull(ValidadorRegistro.validarContrasena("12345"))
        assertNotNull(ValidadorRegistro.validarTerminos(false))
    }

    @Test
    fun perfilReutilizaValidacionesDeNombreYCorreo() {
        val errores = ValidadorPerfil.validar(PerfilUsuario())
        assertNotNull(errores.nombre)
        assertNotNull(errores.correo)
        assertNotNull(errores.fechaNacimiento)
    }

    @Test
    fun fechaRechazaDiasImposiblesYFuturo() {
        val hoy = LocalDate.of(2026, 10, 26)
        listOf("31/04/2000", "29/02/2001", "01/13/2000", "27/10/2026", "01/01/0000", "2000-01-01", "").forEach {
            assertNotNull(it, ValidadorPerfil.validarFecha(it, hoy))
        }
        assertNull(ValidadorPerfil.validarFecha("29/02/2000", hoy))
        assertNull(ValidadorPerfil.validarFecha("26/10/2026", hoy))
    }

    @Test
    fun perfilValidoAceptaPreferenciasLibres() {
        assertTrue(ValidadorPerfil.validar(PerfilUsuario("Persona de prueba", "prueba@example.com", "15/03/1968", "Sin restricciones")).esValido)
        assertNull(ValidadorPerfil.validarPreferencias("Preferencia de prueba"))
        assertNotNull(ValidadorPerfil.validarPreferencias("   "))
    }
}
