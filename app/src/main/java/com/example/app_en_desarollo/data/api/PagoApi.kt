package com.example.app_en_desarollo.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PagoApi {

    @POST("api/pagos")
    suspend fun crearPago(
        @Body solicitud: CrearPagoRequest
    ): PagoBackend

    @GET("api/pagos")
    suspend fun obtenerTodosLosPagos(): List<PagoBackend>

    @GET("api/pagos/reserva/{reservaId}")
    suspend fun obtenerPagosPorReserva(
        @Path("reservaId") reservaId: Long
    ): List<PagoBackend>

    @PUT("api/pagos/{id}/aprobar")
    suspend fun aprobarPago(
        @Path("id") id: Long
    ): PagoBackend

    @PUT("api/pagos/{id}/rechazar")
    suspend fun rechazarPago(
        @Path("id") id: Long
    ): PagoBackend
}
