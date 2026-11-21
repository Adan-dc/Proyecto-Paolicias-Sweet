package com.example.app_en_desarollo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.model.TipoUsuario
import com.example.app_en_desarollo.model.TrabajadorModel
import kotlinx.coroutines.launch

class TrabajadorViewModel : ViewModel(){

    var trabajadores by mutableStateOf<List<TrabajadorModel>>(emptyList())
        private set

    var trabajadorSeleccionado by mutableStateOf<TrabajadorModel?>(null)
        private set

    var uiState by mutableStateOf(TrabajadorModel())
        private set

    fun onNombreChanged(nombre: String){
        uiState = uiState.copy(nombreCompleto = nombre,
            nombreError = null,
            errorMessage = null)
    }

    fun onRutChanged(rut: String){
        uiState = uiState.copy(rut = rut,
            rutError = null,
            errorMessage = null)
    }

    fun onEmailChanged(email: String){
        uiState = uiState.copy(email = email,
            emailError = null,
            errorMessage = null)
    }

    fun onDireccionChanged(direccion: String){
        uiState = uiState.copy(direccion = direccion,
            direccionError = null,
            errorMessage = null)
    }

    fun seleccionarTrabajador(trabajador: TrabajadorModel) {
        trabajadorSeleccionado = trabajador
        cargarTrabajador(trabajador)
    }

    fun limpiarTrabajadorSeleccionado() {
        trabajadorSeleccionado = null
    }

    fun listarTrabajadores() {
        viewModelScope.launch {
            try {
                trabajadores = RetrofitGeneral.trabajadorApi.listarTrabajadores()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun crearTrabajador() {

        if (uiState.nombreCompleto.isBlank()) {
            uiState = uiState.copy(
                nombreError = "Debe ingresar el nombre"
            )
            return
        }

        if (uiState.rut.isBlank()) {
            uiState = uiState.copy(
                rutError = "Debe ingresar el RUT"
            )
            return
        }

        if (uiState.email.isBlank()) {
            uiState = uiState.copy(
                emailError = "Debe ingresar el correo"
            )
            return
        }

        if (uiState.direccion.isBlank()) {
            uiState = uiState.copy(
                direccionError = "Debe ingresar la dirección"
            )
            return
        }

        val nuevoTrabajador = TrabajadorModel(
            nombreCompleto = uiState.nombreCompleto,
            rut = uiState.rut,
            email = uiState.email,
            direccion = uiState.direccion,
            estado = false
        )

        viewModelScope.launch {

            try {

                RetrofitGeneral.trabajadorApi
                    .crearTrabajador(nuevoTrabajador)

                listarTrabajadores()

            } catch (e: Exception) {

                uiState = uiState.copy(
                    errorMessage = "No se pudo crear el trabajador"
                )

                e.printStackTrace()
            }
        }
    }
    fun cargarTrabajador(trabajador: TrabajadorModel){
        uiState = trabajador.copy(
            nombreError = null,
            rutError = null,
            emailError = null,
            direccionError = null,
            errorMessage = null
        )
    }
    fun actualizarTrabajador(id: Long){

        val trabajadorActualizado = TrabajadorModel(
            id = id,
            nombreCompleto = uiState.nombreCompleto,
            rut = uiState.rut,
            email = uiState.email,
            direccion = uiState.direccion,
            estado = uiState.estado,
            idReservaAsignada = uiState.idReservaAsignada
        )
        viewModelScope.launch {
            try {
                RetrofitGeneral.trabajadorApi.actualizarTrabajador(id, trabajadorActualizado)

                listarTrabajadores()

            } catch (e: Exception){
                e.printStackTrace()
            }
        }

    }
    fun asignarReserva(idTrabajador: Long, idReserva: Long){
        viewModelScope.launch {
            try {
                val datos = mapOf("idReserva" to idReserva)

                RetrofitGeneral.trabajadorApi.asignarEvento(idTrabajador, datos)

                listarTrabajadores()
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    fun borrarTrabajador(idTrabajador: Long){
        viewModelScope.launch {
            try {
                RetrofitGeneral.trabajadorApi.eliminarTrabajador(idTrabajador)

                listarTrabajadores()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}