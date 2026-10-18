package com.matias.paoliciassweet.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.matias.paoliciassweet.model.MAX_ASISTENTES
import com.matias.paoliciassweet.model.ReservaModel

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

    fun onConfirmarReservaClick(onSuccess: () -> Unit){
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

        Log.d("ReservaVM", "Reserva: ${state.fecha} ${state.hora} - ${state.asistentes} asistentes")
        uiState = uiState.copy(errorMessage = null)
        onSuccess()
    }
}
