package com.example.app_en_desarollo.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT

interface ReservaApi {

    @GET("api/reservas/usuario/{email}")
    suspend fun obtenerReservasPorUsuario(
        @Path("email") email: String
    ): List<ReservaBackend>


    @POST("api/reservas")
    suspend fun crearReserva(
        @Body reserva: CrearReservaRequest
    ): ReservaBackend

    @GET("api/reservas/{id}")
    suspend fun obtenerReservaPorId(
        @Path("id") id: Long
    ): ReservaBackend

    @GET("api/reservas")
    suspend fun obtenerTodasLasReservas(): List<ReservaBackend>


    @GET("api/reservas/{id}/productos")
    suspend fun obtenerProductosReserva(
        @Path("id") id: Long
    ): List<ReservaProductoAdmin>

    @GET("api/reservas/{id}/packs")
    suspend fun obtenerPacksReserva(
        @Path("id") id: Long
    ): List<ReservaPackAdmin>

    @PUT("api/reservas/{id}/confirmar")
    suspend fun confirmarReserva(
        @Path("id") id: Long
    ): ReservaBackend

    @PUT("api/reservas/{id}/cancelar")
    suspend fun cancelarReserva(
        @Path("id") id: Long
    ): ReservaBackend


}
