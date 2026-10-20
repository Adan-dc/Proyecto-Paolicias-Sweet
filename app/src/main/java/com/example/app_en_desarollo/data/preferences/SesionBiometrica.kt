package com.example.app_en_desarollo.data.preferences

import android.content.Context

object SesionBiometrica {

    private const val PREFS_NAME = "sesion_biometrica"
    private const val KEY_EMAIL = "email"
    private const val KEY_TIEMPO = "tiempo_login"

    // 30 minutos
    private const val DURACION_SESION = 30 * 60 * 1000L

    fun guardarSesion(
        context: Context,
        email: String
    ) {

        val preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        preferences.edit()
            .putString(KEY_EMAIL, email)
            .putLong(KEY_TIEMPO, System.currentTimeMillis())
            .apply()
    }

    fun sesionVigente(context: Context): Boolean {

        val preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        val email =
            preferences.getString(KEY_EMAIL, null)

        val tiempoLogin =
            preferences.getLong(KEY_TIEMPO, 0)

        val tiempoActual =
            System.currentTimeMillis()

        return email != null &&
                tiempoActual - tiempoLogin < DURACION_SESION
    }

    fun obtenerEmail(context: Context): String? {

        val preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        return preferences.getString(KEY_EMAIL, null)
    }

    fun cerrarSesion(context: Context) {

        val preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        preferences.edit()
            .clear()
            .apply()
    }
}