package com.matias.paoliciassweet.model

import android.os.Message

const val MAX_ASISTENTES = 100
const val PRECIO_PERSONA = 9000

data class ReservaModel(
    val fecha: String = "",
    val hora: String = "",
    val asistentes: String ="",
    val observaciones: List<String> = listOf("", ""),
    val errorMessage: String? = null,
    val reservasGuardadas: List<ReservaGuardada> = listOf(
        ReservaGuardada(13, "Evento de boda", "20/10/2026", "20:00",60,false)
    )
){
    val fechaValida: Boolean
        get(){
            if (fecha.length != 8) return false
            val dia = fecha.substring(0, 2).toInt()
            val mes = fecha.substring(2, 4).toInt()
            return dia in 1 .. 31 && mes in 1 .. 12
        }

    val horaValida: Boolean
        get(){
            if(hora.length != 4) return false
            val h = hora.substring(0, 2).toInt()
            val m = hora.substring(2, 4).toInt()
            return h in 0 .. 23 && m in 0 .. 59
        }

    val asistentesValido: Boolean
        get() = (asistentes.toIntOrNull() ?: 0) in 1 .. MAX_ASISTENTES

    val tieneObservaciones: Boolean
        get() = observaciones.any {it.isNotBlank()}

    val cotizacion: Int
        get(){
            val n = asistentes.toIntOrNull() ?: return 0
            if (n !in 1 .. MAX_ASISTENTES) return 0
            return ((n+9) / 10) * 10 * PRECIO_PERSONA
        }
}

