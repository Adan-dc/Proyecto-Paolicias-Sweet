package com.example.app_en_desarollo.data.preferences

import android.content.Context
import com.example.app_en_desarollo.model.TipoUsuario

object SesionBiometrica {

    private const val PREFS_NAME = "sesion_biometrica"
    private const val KEY_IDENTIFICADOR = "identificador"
    private const val KEY_TIPO_USUARIO = "tipo_usuario"
    private const val KEY_TIEMPO = "tiempo_login"

    // 30 minutos
    private const val DURACION_SESION = 30 * 60 * 1000L

    fun guardarSesion(
        context: Context,
        identificador: String,
        tipoUsuario: TipoUsuario
    ) {

        val preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        preferences.edit()
            .putString(KEY_IDENTIFICADOR, identificador)
            .putString(KEY_TIPO_USUARIO, tipoUsuario.name)
            .putLong(KEY_TIEMPO, System.currentTimeMillis())
            .apply()
    }

    fun obtenerIdentificador(context: Context): String?{
        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
        return preferences.getString(KEY_IDENTIFICADOR, null)
    }

    fun obtenerTipoUsuario(context: Context): TipoUsuario? {
        val preferences = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val tipo = preferences.getString(KEY_TIPO_USUARIO, null)

        return tipo?.let {
            TipoUsuario.valueOf(it)
        }
    }

    fun sesionVigente(context: Context): Boolean {

        val preferences =
            context.getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )

        val tiempoLogin =
            preferences.getLong(KEY_TIEMPO, 0)

        val tiempoActual =
            System.currentTimeMillis()

        return tiempoActual - tiempoLogin < DURACION_SESION
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