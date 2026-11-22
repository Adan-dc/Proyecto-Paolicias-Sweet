package com.example.app_en_desarollo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.model.ReservaAdminModel
import com.example.app_en_desarollo.model.TipoUsuario
import com.example.app_en_desarollo.model.TrabajadorModel
import kotlinx.coroutines.launch

class TrabajadorViewModel : ViewModel(){

    var trabajadores by mutableStateOf<List<TrabajadorModel>>(emptyList())
        private set

    var trabajadorSeleccionado by mutableStateOf<TrabajadorModel?>(null)
        private set

    var reservas by mutableStateOf<List<ReservaAdminModel>>(emptyList())
        private set

    var reservaSeleccionada by mutableStateOf<ReservaAdminModel?>(null)
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
        uiState = uiState.copy(correo = email,
            correoError = null,
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
        uiState = TrabajadorModel()
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

    fun crearTrabajador(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {

        if (!validarFormulario()) {
            return
        }

        val nuevoTrabajador = TrabajadorModel(
            nombreCompleto = uiState.nombreCompleto,
            rut = uiState.rut,
            correo = uiState.correo,
            direccion = uiState.direccion,
            estado = false
        )

        viewModelScope.launch {

            try {

                RetrofitGeneral.trabajadorApi
                    .crearTrabajador(nuevoTrabajador)

                listarTrabajadores()

                uiState = TrabajadorModel()

                onSuccess()

            } catch (e: Exception) {

                uiState = uiState.copy(
                    errorMessage = "No se pudo crear el trabajador"
                )

                onError()

                e.printStackTrace()
            }
        }
    }
    fun cargarTrabajador(trabajador: TrabajadorModel){
        uiState = trabajador.copy(
            nombreError = null,
            rutError = null,
            correoError = null,
            direccionError = null,
            errorMessage = null
        )
    }
    fun actualizarTrabajador(id: Long, onSuccess: () -> Unit, onError: () -> Unit){

        if (!validarFormulario()) {
            return
        }

        val trabajadorActualizado = TrabajadorModel(
            id = id,
            nombreCompleto = uiState.nombreCompleto,
            rut = uiState.rut,
            correo = uiState.correo,
            direccion = uiState.direccion,
            estado = uiState.estado,
            idReservaAsignada = uiState.idReservaAsignada
        )

        viewModelScope.launch {

            try {

                RetrofitGeneral.trabajadorApi
                    .actualizarTrabajador(id, trabajadorActualizado)

                listarTrabajadores()

                onSuccess()

            } catch (e: Exception) {

                uiState = uiState.copy(
                    errorMessage = "No se pudo modificar el trabajador"
                )

                onError()

                e.printStackTrace()
            }
        }

    }
    fun asignarReserva(idTrabajador: Long, idReserva: Long, onSuccess: () -> Unit, onError: () -> Unit){
        viewModelScope.launch {
            try {
                val datos = mapOf("idReserva" to idReserva)

                RetrofitGeneral.trabajadorApi.asignarEvento(idTrabajador, datos)

                listarTrabajadores()

                onSuccess()
            } catch (e: Exception){
                onError()
                e.printStackTrace()
            }
        }
    }

    fun cancelarAsignacion(idTrabajador: Long, onSuccess: () -> Unit, onError: () -> Unit) {

        viewModelScope.launch {

            try {

                RetrofitGeneral.trabajadorApi
                    .cancelarAsignacion(idTrabajador)

                listarTrabajadores()

                onSuccess()

            } catch (e: Exception) {
                onError()
                e.printStackTrace()
            }
        }
    }

    fun borrarTrabajador(idTrabajador: Long, onSuccess: () -> Unit, onError: () -> Unit){
        viewModelScope.launch {
            try {
                RetrofitGeneral.trabajadorApi.eliminarTrabajador(idTrabajador)

                listarTrabajadores()

                onSuccess()
            }catch (e: Exception){
                onError()
                e.printStackTrace()
            }
        }
    }

    fun listarReservas() {

        viewModelScope.launch {

            try {

                reservas = RetrofitGeneral.reservaAdminApi.listarReservas()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun seleccionarReserva(reserva: ReservaAdminModel) {
        reservaSeleccionada = reserva
    }

    fun limpiarReservaSeleccionada() {
        reservaSeleccionada = null
    }

    private fun validarFormulario(): Boolean {

        val state = uiState

        if (state.nombreCompleto.isBlank()) {
            uiState = uiState.copy(
                nombreError = "Debe ingresar el nombre"
            )
            return false
        }

        if (state.rut.isBlank()) {
            uiState = uiState.copy(
                rutError = "Debe ingresar el RUT"
            )
            return false
        }

        if (state.correo.isBlank()) {
            uiState = uiState.copy(
                correoError = "Debe ingresar el correo"
            )
            return false
        }

        if (
            !state.correo.contains("@") ||
            !state.correo.substringAfter("@").contains(".") ||
            state.correo.endsWith(".") ||
            state.correo.endsWith("@") ||
            state.correo.startsWith(".") ||
            state.correo.startsWith("@")
        ) {
            uiState = uiState.copy(
                correoError = "El correo debe ser válido"
            )
            return false
        }

        if (state.direccion.isBlank()) {
            uiState = uiState.copy(
                direccionError = "Debe ingresar la dirección"
            )
            return false
        }

        return true
    }
}