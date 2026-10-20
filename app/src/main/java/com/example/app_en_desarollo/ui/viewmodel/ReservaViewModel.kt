package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.database.AppDatabase
import com.example.app_en_desarollo.data.local.entity.ReservaEntity
import com.example.app_en_desarollo.model.MAX_ASISTENTES
import com.example.app_en_desarollo.model.ReservaGuardada
import com.example.app_en_desarollo.model.ReservaModel
import kotlinx.coroutines.launch

class ReservaViewModel : ViewModel(){

    var uiState by mutableStateOf(ReservaModel())
        private set

    fun onFechaChanged(fecha: String){
        uiState = uiState.copy(fecha=fecha.filter {it.isDigit() }.take(8), errorMessage = null)
    }
    fun onHoraChanged(hora: String){
        uiState = uiState.copy(hora = hora.filter { it.isDigit() }.take(4), errorMessage = null)
    }

    fun onAsistentesChanged(asistentes: String){
        val digitos = asistentes.filter { it.isDigit() }.take(3)
        val limitado = if ((digitos.toIntOrNull() ?: 0) > MAX_ASISTENTES) MAX_ASISTENTES.toString() else digitos
        uiState = uiState.copy(asistentes = limitado, errorMessage = null)
    }

    fun onObservacionChanged(index: Int, texto: String){
        val nuevas = uiState.observaciones.mapIndexed { i, actual ->
            if (i == index) texto else actual
        }
        uiState = uiState.copy(observaciones = nuevas, errorMessage = null)
    }

    fun onAgregarObservacionClick(){
        uiState = uiState.copy(observaciones = uiState.observaciones + "")
    }

    // correo del usuario con sesión iniciada ("" mientras no haya login)
    private var usuarioEmail = ""

    fun setUsuario(email: String){
        usuarioEmail = email
    }

    // lee las reservas guardadas en SQLite y las deja en el estado
    fun cargarReservas(context: Context){
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val lista = db.reservaDao().getReservasPorUsuario(usuarioEmail).map { r ->
                ReservaGuardada(
                    id = r.id,
                    tipoEvento = r.tipoEvento,
                    fecha = r.fecha,
                    hora = r.hora,
                    asistentes = r.asistentes,
                    tieneObservaciones = r.tieneObservaciones
                )
            }
            uiState = uiState.copy(reservasGuardadas = lista)
        }
    }

    fun onConfirmarReservaClick(context: Context, onSuccess: () -> Unit){
        val state = uiState

        if(!state.fechaValida){
            uiState = uiState.copy(errorMessage = "Ingresa una fecha válida (DD/MM/AAAA).")
            return
        }

        if(!state.horaValida){
            uiState = uiState.copy(errorMessage = "Ingresa una hora válida (HH:MM).")
            return
        }

        if(!state.asistentesValido){
            uiState = uiState.copy(errorMessage = "Los asistentes deben ser entre 1 y $MAX_ASISTENTES.")
            return
        }

        val nueva = ReservaEntity(
            usuarioEmail = usuarioEmail,
            tipoEvento = "Evento",
            fecha = "${state.fecha.substring(0, 2)}/${state.fecha.substring(2, 4)}/${state.fecha.substring(4)}",
            hora = "${state.hora.substring(0, 2)}:${state.hora.substring(2)}",
            asistentes = state.asistentes.toInt(),
            tieneObservaciones = state.tieneObservaciones,
            observaciones = state.observaciones.filter { it.isNotBlank() }.joinToString("; ")
        )

        // método asíncronico en SQLite con Room
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.reservaDao().insertReserva(nueva)
            // formulario limpio (la lista se vuelve a leer en la pantalla de reservas)
            uiState = ReservaModel(reservasGuardadas = uiState.reservasGuardadas)
            onSuccess()
        }
    }
}
