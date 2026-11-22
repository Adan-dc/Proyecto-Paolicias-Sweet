package com.example.app_en_desarollo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.model.IngredienteRequest
import com.example.app_en_desarollo.model.IngredienteResponse
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AdminIngredientesViewModel : ViewModel() {

    var ingredientes by mutableStateOf<List<IngredienteResponse>>(emptyList())
        private set

    var mensaje by mutableStateOf<String?>(null)
        private set

    fun limpiarMensaje() {
        mensaje = null
    }

    fun cargar() {
        viewModelScope.launch {
            try {
                ingredientes = RetrofitGeneral.ingredienteApi.listar()
            } catch (e: Exception) {
                mensaje = "No se pudieron cargar los ingredientes"
            }
        }
    }

    fun guardar(
        id: Long?,
        nombre: String,
        precioUnidad: Double,
        unidadMedida: String,
        onListo: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = IngredienteRequest(
                    nombre = nombre,
                    precioUnidad = precioUnidad,
                    unidadMedida = unidadMedida
                )

                if (id == null) {
                    RetrofitGeneral.ingredienteApi.crear(request)
                } else {
                    RetrofitGeneral.ingredienteApi.actualizar(id, request)
                }

                ingredientes = RetrofitGeneral.ingredienteApi.listar()
                onListo()
            } catch (e: Exception) {
                mensaje = "No se pudo guardar el ingrediente"
            }
        }
    }

    fun eliminar(id: Long) {
        viewModelScope.launch {
            try {
                RetrofitGeneral.ingredienteApi.eliminar(id)
                ingredientes = RetrofitGeneral.ingredienteApi.listar()
            } catch (e: HttpException) {
                mensaje = if (e.code() == 409) {
                    "No se puede eliminar, el ingrediente esta en uso en un producto"
                } else {
                    "No se pudo eliminar el ingrediente"
                }
            } catch (e: Exception) {
                mensaje = "No se pudo conectar con el servidor"
            }
        }
    }
}