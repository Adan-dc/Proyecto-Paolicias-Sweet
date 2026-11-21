package com.example.app_en_desarollo.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.model.CostoCalculoModel
import com.example.app_en_desarollo.model.ProductoCostoModel
import com.example.app_en_desarollo.model.ReservaAdminModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class CostoViewModel : ViewModel() {
    var reservas by mutableStateOf<List<ReservaAdminModel>>(
        emptyList()
    )
        private set


    var reservaSeleccionada by mutableStateOf<ReservaAdminModel?>(
        null
    )
        private set


    var productosCosto by mutableStateOf<List<ProductoCostoModel>>(
        emptyList()
    )
        private set


    var calculoFinal by mutableStateOf<CostoCalculoModel?>(
        null
    )
        private set


    var cargando by mutableStateOf(false)
        private set


    var errorMessage by mutableStateOf<String?>(null)
        private set


    private val reservasCompradas =
        mutableStateMapOf<Long, Boolean>()


    private val productosComprados =
        mutableStateMapOf<Long, Boolean>()

    fun listarReservasVigentes() {
        viewModelScope.launch {
            try {
                val lista =
                    RetrofitGeneral.reservaAdminApi
                        .listarReservas()
                val hoy = LocalDate.now()

                reservas = lista.filter { reserva ->

                    try {
                        val fecha =
                            LocalDate.parse(reserva.fecha)
                        !fecha.isBefore(hoy)
                    } catch (e: Exception) {
                        true
                    }
                }

            } catch (e: Exception) {
                errorMessage =
                    "No se pudieron cargar las reservas"
                e.printStackTrace()
            }
        }
    }
    fun seleccionarReserva(reserva: ReservaAdminModel) {
        reservaSeleccionada = reserva
        productosCosto = emptyList()
        calculoFinal = null
        productosComprados.clear()
    }
    fun cargarDetalleCosto() {
        val idReserva =
            reservaSeleccionada?.id ?: return

        viewModelScope.launch {

            cargando = true
            errorMessage = null

            try {
                productosCosto =
                    RetrofitGeneral.costoApi
                        .obtenerDetalleCosto(
                            idReserva
                        )
            } catch (e: Exception) {
                errorMessage =
                    "No se pudo obtener el detalle del costo"
                e.printStackTrace()
            } finally {
                cargando = false
            }
        }
    }
    fun calcularCostoTotal(onSuccess: () -> Unit) {
        val idReserva =
            reservaSeleccionada?.id ?: return

        viewModelScope.launch {

            cargando = true
            errorMessage = null

            try {
                calculoFinal =
                    RetrofitGeneral.costoApi
                        .calcularCosto(
                            mapOf(
                                "idReserva" to idReserva
                            )
                        )
                onSuccess()

            } catch (e: Exception) {
                errorMessage =
                    "No se pudo calcular el costo total"
                e.printStackTrace()
            } finally {
                cargando = false
            }
        }
    }
    fun productoComprado(
        productoId: Long
    ): Boolean {
        return productosComprados[productoId]
            ?: false
    }
    fun cambiarProductoComprado(productoId: Long, comprado: Boolean) {
        productosComprados[productoId] =
            comprado
        val todosComprados =
            productosCosto.isNotEmpty() &&
                    productosCosto.all {
                        productosComprados[it.productoId] ?: false
                    }
        reservaSeleccionada?.id?.let { idReserva ->
            reservasCompradas[idReserva] =
                todosComprados
        }
    }
    fun reservaComprada(idReserva: Long): Boolean {
        return reservasCompradas[idReserva]
            ?: false
    }
    fun cambiarReservaComprada(idReserva: Long, comprado: Boolean) {
        reservasCompradas[idReserva] = comprado
    }
    fun limpiarCosto() {
        reservaSeleccionada = null
        productosCosto = emptyList()
        calculoFinal = null
        errorMessage = null
    }
}