// Plugins declarados a nivel raíz, aplicados en los módulos que los necesitan.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
