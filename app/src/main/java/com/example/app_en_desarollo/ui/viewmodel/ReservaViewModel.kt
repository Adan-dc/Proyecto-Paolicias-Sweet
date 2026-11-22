package com.example.app_en_desarollo.ui.viewmodel

import android.content.Context
import android.util.Log
import android.net.Uri
import android.provider.OpenableColumns

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.app_en_desarollo.data.api.RetrofitGeneral
import com.example.app_en_desarollo.data.api.CrearPagoRequest
import com.example.app_en_desarollo.data.database.AppDatabase
import com.example.app_en_desarollo.data.local.entity.ReservaEntity
import com.example.app_en_desarollo.model.MAX_ASISTENTES
import com.example.app_en_desarollo.model.ReservaGuardada
import com.example.app_en_desarollo.model.ReservaModel
import com.example.app_en_desarollo.data.api.CrearReservaRequest
import com.example.app_en_desarollo.data.api.PackSeleccionado
import com.example.app_en_desarollo.data.api.ProductoSeleccionado
import java.time.LocalDate
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody


import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import org.json.JSONArray


class ReservaViewModel : ViewModel(){

    var uiState by mutableStateOf(ReservaModel())
        private set

    fun onFechaChanged(fecha: String){
        uiState = uiState.copy(fecha=fecha.filter {it.isDigit() }.take(8), errorMessage = null)
    }

    fun onTipoEventoChanged(tipoEvento: String){
        uiState = uiState.copy(
            tipoEvento = tipoEvento.take(60),
            errorMessage = null
        )
    }

    fun onHoraChanged(hora: String){
        uiState = uiState.copy(hora = hora.filter { it.isDigit() }.take(4), errorMessage = null)
    }

    fun onAsistentesChanged(asistentes: String){
        val digitos = asistentes.filter { it.isDigit() }.take(3)
        val limitado = if ((digitos.toIntOrNull() ?: 0) > MAX_ASISTENTES) MAX_ASISTENTES.toString() else digitos
        uiState = uiState.copy(asistentes = limitado, errorMessage = null)
    }

    fun onIncrementarAsistentesClick(){
        val actual = uiState.asistentes.toIntOrNull() ?: 0
        uiState = uiState.copy(
            asistentes = (actual + 10).coerceAtMost(MAX_ASISTENTES).toString(),
            errorMessage = null
        )
    }

    fun onDisminuirAsistentesClick(){
        val actual = uiState.asistentes.toIntOrNull() ?: 0
        uiState = uiState.copy(
            asistentes = (actual - 10).coerceAtLeast(0).toString(),
            errorMessage = null
        )
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

    private var usuarioEmail = ""

    fun setUsuario(email: String){
        usuarioEmail = email
    }

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
                    tieneObservaciones = r.tieneObservaciones,
                    estadoAbono = r.estadoAbono
                )
            }
            uiState = uiState.copy(reservasGuardadas = lista)
        }
    }




    fun cargarReservasBackend() {
        viewModelScope.launch {
            uiState = uiState.copy(
                reservasGuardadas = emptyList(),
                errorMessage = null
            )

            try {
                if (usuarioEmail.isBlank()) {
                    uiState = uiState.copy(
                        reservasGuardadas = emptyList(),
                        errorMessage = "No hay un usuario con sesión iniciada"
                    )
                    return@launch
                }

                val reservas = RetrofitGeneral.reservaApi
                    .obtenerReservasPorUsuario(usuarioEmail)

                val lista = reservas.map { r ->
                    ReservaGuardada(
                        id = r.id,
                        tipoEvento = r.tipoEvento.orEmpty(),
                        fecha = r.fecha.orEmpty(),
                        hora = r.hora.orEmpty(),
                        asistentes = r.asistentes,
                        tieneObservaciones = r.tieneObservaciones,
                        estadoAbono = r.estadoAbono.orEmpty()
                    )
                }

                uiState = uiState.copy(
                    reservasGuardadas = lista,
                    errorMessage = null
                )

                Log.d(
                    "PAOLICIAS_API",
                    "Reservas recibidas: ${lista.size}"
                )

            } catch (e: Exception) {
                Log.e(
                    "PAOLICIAS_API",
                    "Error al cargar reservas",
                    e
                )

                uiState = uiState.copy(
                    reservasGuardadas = emptyList(),
                    errorMessage = "Error al cargar reservas: ${e.message}"
                )
            }
        }
    }



    fun guardarComprobante(
        context: Context,
        reservaId: Int,
        uri: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val comprobante = withContext(Dispatchers.IO) {

                    val reserva = RetrofitGeneral.reservaApi
                        .obtenerReservaPorId(reservaId.toLong())

                    val presupuesto = reserva.presupuestoTotal

                    require(presupuesto > 0) {
                        "La reserva no tiene presupuesto válido"
                    }

                    val abono = presupuesto / 2

                    val pagos = RetrofitGeneral.pagoApi
                        .obtenerPagosPorReserva(reservaId.toLong())

                    val abonoExistente = pagos.firstOrNull {
                        it.tipoPago == "ABONO" &&
                                it.estadoPago == "PENDIENTE"
                    }
                    require(
                        pagos.none {
                            it.tipoPago == "ABONO" &&
                                    it.estadoPago == "APROBADO"
                        }
                    ) {
                        "El abono ya fue aprobado"
                    }

                    val pago = abonoExistente
                        ?: RetrofitGeneral.pagoApi.crearPago(
                            CrearPagoRequest(
                                reservaId = reservaId.toLong(),
                                monto = abono
                            )
                        )

                    val comprobantes = RetrofitGeneral.comprobanteApi
                        .obtenerPorPago(pago.id)

                    require(
                        comprobantes.none {
                            it.estadoComprobante == "PENDIENTE" ||
                                    it.estadoComprobante == "APROBADO"
                        }
                    ) {
                        "Este pago ya tiene un comprobante activo"
                    }

                    val archivoUri = Uri.parse(uri)

                    val tipoArchivo = context.contentResolver
                        .getType(archivoUri)
                        ?: "application/octet-stream"
                    require(
                        tipoArchivo.startsWith("image/") ||
                                tipoArchivo == "application/pdf"
                    ) {
                        "Selecciona una imagen o un PDF"
                    }

                    val nombreArchivo = context.contentResolver
                        .query(archivoUri, null, null, null, null)
                        ?.use { cursor ->
                            val columna = cursor.getColumnIndex(
                                OpenableColumns.DISPLAY_NAME
                            )
                            if (columna >= 0 && cursor.moveToFirst()) {
                                cursor.getString(columna)
                            } else null
                        } ?: "comprobante"

                    val bytes = context.contentResolver
                        .openInputStream(archivoUri)
                        ?.use { it.readBytes() }
                        ?: error("No se pudo leer el archivo")

                    require(bytes.isNotEmpty()) {
                        "El archivo está vacío"
                    }

                    require(bytes.size <= 10 * 1024 * 1024) {
                        "El archivo supera los 10 MB"
                    }

                    val archivoBody = bytes.toRequestBody(tipoArchivo.toMediaTypeOrNull())

                    val archivoPart = MultipartBody.Part
                        .createFormData(
                            "archivo",
                            nombreArchivo,
                            archivoBody
                        )

                    val pagoIdBody = pago.id.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                    RetrofitGeneral.comprobanteApi.subirComprobante(
                        pagoId = pagoIdBody,
                        archivo = archivoPart
                    )
                }

                Log.d(
                    "PAOLICIAS_API",
                    "Comprobante subido con ID: ${comprobante.id}"
                )

                cargarReservasBackend()
                onSuccess()

            } catch (e: Exception) {
                Log.e(
                    "PAOLICIAS_API",
                    "Error al subir comprobante",
                    e
                )
                uiState = uiState.copy(
                    errorMessage = e.message
                )
                onError()
            }
        }
    }



    fun onConfirmarReservaClick(
        catalogoViewModel: CatalogoViewModel,
        onSuccess: () -> Unit
    ) {
        val state = uiState

        if(state.tipoEvento.isBlank()){
            uiState = uiState.copy(
                errorMessage = "Debes indicar el tipo de evento"
            )
            return
        }
        if (usuarioEmail.isBlank()) {
            uiState = uiState.copy(
                errorMessage = "Debes iniciar sesión para reservar."
            )
            return
        }

        if (!state.fechaValida || state.fecha.length != 8) {
            uiState = uiState.copy(
                errorMessage = "Ingresa una fecha válida."
            )
            return
        }

        if (!state.horaValida) {
            uiState = uiState.copy(
                errorMessage = "Ingresa una hora válida."
            )
            return
        }

        if (!state.asistentesValido) {
            uiState = uiState.copy(
                errorMessage = "Cantidad de invitados inválida."
            )
            return
        }

        if (catalogoViewModel.productosReserva.isEmpty()) {
            uiState = uiState.copy(
                errorMessage = "Selecciona al menos un producto."
            )
            return
        }

        val productos = mutableListOf<ProductoSeleccionado>()
        val packs = mutableListOf<PackSeleccionado>()

        for (seleccion in catalogoViewModel.productosReserva) {
            if (seleccion.producto.seccion == "Packs") {
                packs.add(
                    PackSeleccionado(
                        packId = seleccion.producto.id,
                        cantidad = seleccion.cantidad
                    )
                )
            } else {
                productos.add(
                    ProductoSeleccionado(
                        productoId = seleccion.producto.id,
                        cantidad = seleccion.cantidad
                    )
                )
            }
        }

        val fechaBackend = try {
            val dia = state.fecha.substring(0, 2).toInt()
            val mes = state.fecha.substring(2, 4).toInt()
            val anio = state.fecha.substring(4, 8).toInt()

            LocalDate.of(anio, mes, dia).toString()

        } catch (e: Exception) {
            uiState = uiState.copy(
                errorMessage = "La fecha ingresada no existe."
            )
            return
        }

        val solicitud = CrearReservaRequest(
            usuarioEmail = usuarioEmail,
            tipoEvento = state.tipoEvento.trim(),
            fecha = fechaBackend,
            hora = "${state.hora.substring(0, 2)}:${state.hora.substring(2)}",
            asistentes = state.asistentes.toInt(),
            tieneObservaciones = state.tieneObservaciones,
            observaciones = state.observaciones
                .filter { it.isNotBlank() }
                .joinToString("; "),
            packs = packs,
            productos = productos
        )

        viewModelScope.launch {
            try {
                val reservaCreada = RetrofitGeneral.reservaApi
                    .crearReserva(solicitud)

                Log.d(
                    "PAOLICIAS_API",
                    "Reserva creada con ID: ${reservaCreada.id}"
                )

                catalogoViewModel.productosReserva.clear()

                uiState = ReservaModel(
                    reservasGuardadas = uiState.reservasGuardadas
                )

                onSuccess()

            } catch (e: Exception) {
                Log.e(
                    "PAOLICIAS_API",
                    "Error al crear reserva",
                    e
                )

                uiState = uiState.copy(
                    errorMessage = "No se pudo crear la reserva: " +
                            (e.message ?: "Error desconocido")
                )
            }
        }
    }

}